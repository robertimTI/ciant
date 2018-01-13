package com.robertim.ciandt.api.services;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.robertim.ciandt.api.entities.Competicao;
import com.robertim.ciandt.api.repositories.CompeticaoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CompeticaoServiceTest {

	@MockBean
	private CompeticaoRepository competicaoRepository;
	
	@Autowired
	private CompeticaoService competicaoService;
	
	@Before
	public void setUp() throws Exception {
		BDDMockito
				.given(this.competicaoRepository.findByModalidade(Mockito.anyString(), Mockito.any(PageRequest.class)))
				.willReturn(new PageImpl<Competicao>(new ArrayList<Competicao>()));
		BDDMockito.given(this.competicaoRepository.save(Mockito.any(Competicao.class))).willReturn(new Competicao());
	}
	
	@Test
	public void testBuscarPorModalidade() {
		Page<Competicao> competicao = this.competicaoService.buscarPorModalidade("Futebol", new PageRequest(0, 10));

		assertNotNull(competicao);
	}
	
	@Test
	public void testPersistirCompeticao() {
		Competicao competicao = this.competicaoService.persistir(new Competicao());

		assertNotNull(competicao);
	}
	
}
