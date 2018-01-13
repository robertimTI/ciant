package com.robertim.ciandt.api.repositories;


import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.robertim.ciandt.api.entities.Competicao;
import com.robertim.ciandt.api.enums.EtapaEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CompeticaoRepositoryTest {

	@Autowired
	private CompeticaoRepository competicaoRepository;
	
	private static final String modalidade = "Futebol";
	
	static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
	
	@Before
	public void setUp() throws Exception {
		Competicao competicao = new Competicao();
		competicao.setLocal("Alianz Park");
		competicao.setEtapa(EtapaEnum.ELIMINATORIAS);
		competicao.setAdversario("Brasil");
		competicao.setVisitante("Peru");
		competicao.setDataInicial(new Date());
		Calendar date = Calendar.getInstance();
		long t = date.getTimeInMillis();
		Date addMinutes = new Date(t+(40 * ONE_MINUTE_IN_MILLIS));
		competicao.setDataFinal(addMinutes);
		competicao.setModalidade(modalidade);
		this.competicaoRepository.save(competicao);
	}
	
	@After
    public final void tearDown() { 
		this.competicaoRepository.deleteAll();
	}
	
	@Test
	public void testBuscarCompeticaoPorModalidadePaginado() {
		PageRequest page = new PageRequest(0, 10);
		Page<Competicao> lancamentos = this.competicaoRepository.findByModalidade(modalidade, page);
		
		assertEquals(1, lancamentos.getTotalElements());
	}
}
