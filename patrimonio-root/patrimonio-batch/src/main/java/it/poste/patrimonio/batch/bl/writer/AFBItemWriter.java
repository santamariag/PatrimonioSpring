package it.poste.patrimonio.batch.bl.writer;

import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.repository.IFoeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AFBItemWriter implements ItemStreamWriter<List<Foe>>{


	private final IFoeRepository foeRepository;

	public AFBItemWriter(IFoeRepository foeRepository) {
		this.foeRepository = foeRepository;
	}

	@Override
	@Transactional
	public void write(Chunk<? extends List<Foe>> foeList) throws Exception {

		log.info("Saving {} foe objects", foeList.getItems().size());
		if(foeList.size()!=0 && foeList.getItems().size()!=0) {
			final List<Foe> consolidatedList = new ArrayList<>();
			for (final List<Foe> list : foeList) 
				consolidatedList.addAll(list);
			foeRepository.saveAll(consolidatedList);
		}

	}

}

