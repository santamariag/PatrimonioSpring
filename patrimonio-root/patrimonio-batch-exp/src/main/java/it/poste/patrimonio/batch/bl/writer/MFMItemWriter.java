package it.poste.patrimonio.batch.bl.writer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.transaction.annotation.Transactional;

import it.poste.patrimonio.db.model.gpmfoe.Gpm;
import it.poste.patrimonio.db.repository.IGpmRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MFMItemWriter implements ItemStreamWriter<List<Gpm>>{


	private final IGpmRepository gpmRepository;

	public MFMItemWriter(IGpmRepository gpmRepository) {
		this.gpmRepository = gpmRepository;
	}

	@Override
	@Transactional
	public void write(Chunk<? extends List<Gpm>> gpmList) throws Exception {

		log.info("Saving {} gpm objects", gpmList.getItems().size());
		if(gpmList.size()!=0 && gpmList.getItems().size()!=0) {
			final List<Gpm> consolidatedList = new ArrayList<>();
			for (final List<Gpm> list : gpmList) 
				consolidatedList.addAll(list);
			gpmRepository.saveAll(consolidatedList);
		}

	}

}

