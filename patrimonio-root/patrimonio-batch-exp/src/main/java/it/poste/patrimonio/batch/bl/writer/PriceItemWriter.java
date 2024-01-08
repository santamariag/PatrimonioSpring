package it.poste.patrimonio.batch.bl.writer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Range;

import it.poste.patrimonio.batch.bl.config.WorkerConfig;
import it.poste.patrimonio.batch.bl.parallel.Result;
import it.poste.patrimonio.batch.bl.parallel.UpdateGpmPrice;
import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.db.model.Price;
import it.poste.patrimonio.db.repository.IGpmRepository;
import it.poste.patrimonio.db.repository.IPriceRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceItemWriter implements ItemStreamWriter<Price>{


	private final IPriceRepository priceRepository;

	private final IGpmRepository gpmRepository;

	@Autowired
	private WorkerConfig workerConfig;

	public PriceItemWriter(IPriceRepository priceRepository, IGpmRepository gpmRepository) {
		this.priceRepository = priceRepository;
		this.gpmRepository = gpmRepository;
	}

	@Override
	@Transactional
	public void write(Chunk<? extends Price> priceList) throws Exception {

		log.info("Saving {} prices objects", priceList.size());
		for(Price p:  priceList.getItems())
			log.info("PRICE "+p);
		priceRepository.saveAll(priceList);

		Map<String, BigDecimal> pricemap=priceList.getItems().stream().collect(Collectors.toMap(Price::getIsin, Price::getPrice));
		
		log.info("MAP "+pricemap);
		
		if(!pricemap.isEmpty()) {
			//updatePositions(pricemap); 
	
			updatePositionsPagedWorkers(pricemap, workerConfig.getPageSize());
			/** for (Price p: priceList)
	        	gpmRepository.updatePriceOld(p.getIsin(), p.getPrice());*/
		}


	}

	@SuppressWarnings("unchecked")
	private void updatePositionsPagedWorkers(Map<String, BigDecimal> pricemap, int pageSize) {

		Long total=gpmRepository.countByIsin(pricemap.keySet());

		log.info("TOTAL "+total);

		if(total!=0) {
			Long pagesNumber=calculatePagesCount(pageSize, total);

			log.info("PAGES "+pagesNumber);

			Map<?, ?> partitions=calculatePartitions(pageSize,pagesNumber,workerConfig.getMaxNumber());

			log.info("PARTITIONS "+partitions);

			ExecutorService executor = Executors.newFixedThreadPool(partitions.size());

			List<UpdateGpmPrice> taskList = new ArrayList<>();
			partitions.forEach((K, V)->{
				UpdateGpmPrice task=new UpdateGpmPrice((Long)K, (Range<Long>)V, pageSize, gpmRepository, pricemap);
				taskList.add(task);
			});

			//Execute all tasks and get reference to Future objects
			List<Future<Result>> resultList = null;

			try {
				resultList = executor.invokeAll(taskList);

			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}

			executor.shutdown();

			log.info("========Printing the results======");

			for (int i = 0; i < resultList.size(); i++) {
				Future<Result> future = resultList.get(i);
				try {
					Result result = future.get();
					log.info((result.getName() + ": " + result.getTimestamp()));
				} catch (InterruptedException | ExecutionException e) {
					log.error(e.getMessage());
				}
			}
		}
	}

	private Map<Long, Range<Long>> calculatePartitions(int pageSize, Long pagesNumber, Long maxWorkers) {

		Map<Long, Range<Long>> partitions = new HashMap<>();

		Long num_workers=pagesNumber;
		if (num_workers>0) {
			
			if (maxWorkers!=null && maxWorkers<num_workers)
				num_workers=maxWorkers;

			Long numPagesPerWorker=new BigDecimal(pagesNumber).divide(new BigDecimal(num_workers), 0, RoundingMode.CEILING).longValue();

			Long from=1L;
			Long to=numPagesPerWorker;

			for (Long i=1L; i<=num_workers; i++) {

				if (to>pagesNumber)
					to=pagesNumber;
				
				partitions.put(i, Range.closed(from, to));

				from=to+1;
				to+=numPagesPerWorker;
			}
		}
		return partitions;
	}

	private Long calculatePagesCount(long pageSize, long totalCount) {

		return totalCount < pageSize ? 1 : (long) Math.ceil((double) totalCount / (double) pageSize);
	}



	private void updatePositionsPaged(Map<String, BigDecimal> pricemap, int pageSize) {

		log.info("START "+Thread.currentThread().getName());

		Pageable pageable = PageRequest.of(0, pageSize, Sort.by(Direction.ASC, "ndg"));
		Page<Gpm> gpmPage=gpmRepository.findByIsinIn(pricemap.keySet(), pageable);
		log.debug("NUMERO TOT "+gpmPage.getTotalElements());
		log.debug("NUMERO PAGINE "+gpmPage.getTotalPages());
		if(!gpmPage.isEmpty()) {

			for (int i=1; i<=gpmPage.getTotalPages(); i++) {

				log.debug("Elaboro {} elementi di pagina {}",gpmPage.getNumberOfElements(), gpmPage.getNumber()+1);

				for(Gpm gpm : gpmPage.getContent()) {
					elaborateGpm(pricemap, gpm);
				}
				if(i<gpmPage.getTotalPages()) {
					log.debug("Cerco pagina "+(i+1));
					pageable= PageRequest.of(i, pageSize, Sort.by(Direction.ASC, "ndg"));
					gpmPage=gpmRepository.findByIsinIn(pricemap.keySet(), pageable);
				}
			}
		}

		log.info("END "+Thread.currentThread().getName());


	}

	private void elaborateGpm(Map<String, BigDecimal> pricemap, Gpm gpm) {

		log.info("+++++++"+gpm);
		gpm.getPatrimonioOld().getPosizioni().forEach(p->{
			if(pricemap.containsKey(p.getIsin())){
				BigDecimal newPrice=pricemap.get(p.getIsin());
				//if(p.getIprzat().compareTo(newPrice)!=0) {
				p.setIprzat(newPrice);
				p.setIvalbas(p.getIprzat().multiply(new BigDecimal(p.getQqta())));
				gpmRepository.saveGpm(gpm);
				/*} else {
				log.debug("La posizione con isin {} ha già lo stesso prezzo", p.getIsin());
			}*/
			}
		});
	}

	private void updatePositions(Map<String, BigDecimal> pricemap) {

		log.info("START "+Thread.currentThread().getName());

		Iterator<Gpm> it=gpmRepository.findByIsinIn(pricemap.keySet()).iterator();

		while(it.hasNext()) {

			elaborateGpm(pricemap, it.next());
		}

		log.info("END "+Thread.currentThread().getName());
	}
}

