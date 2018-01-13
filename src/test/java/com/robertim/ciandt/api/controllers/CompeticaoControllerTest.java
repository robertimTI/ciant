package com.robertim.ciandt.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robertim.ciandt.api.dtos.CompeticaoDto;
import com.robertim.ciandt.api.entities.Competicao;
import com.robertim.ciandt.api.enums.EtapaEnum;
import com.robertim.ciandt.api.services.CompeticaoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CompeticaoControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CompeticaoService competicaoService;
	
	private static String URL_BASE = "/api/cadastrar/";
	private static Long ID_COMPETICAO = 1L;
	private static String ETAPA = EtapaEnum.ELIMINATORIAS.name();
	static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
	
	Calendar date = Calendar.getInstance();
	long t = date.getTimeInMillis();
	private Date DATA_INICIAL = new Date(t+(30 * ONE_MINUTE_IN_MILLIS));
	private static String LOCAL = "Alianz Park";
	private static String MODALIDADE = "Futebol"; 
	private static String ADVERSARIO = "Brasil";
	private static String VISITANTE = "Peru";
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void testCadastraHorarioInvalido() throws Exception {
		Competicao competicao = obterDadosCompeticaoInvalidos();
		BDDMockito.given(this.competicaoService.persistir(Mockito.any(Competicao.class))).willReturn(competicao);
		System.out.println(ID_COMPETICAO);
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPostInvalidos())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400))
     			.andExpect(jsonPath("$.errors").value("Duração da partida deve ser no mínimo de 30 minutos."))
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	private Competicao obterDadosCompeticaoInvalidos() {
		Competicao competicao = new Competicao();
		competicao.setId(ID_COMPETICAO);
		competicao.setVisitante(VISITANTE);
		competicao.setAdversario(ADVERSARIO);
		competicao.setDataInicial(DATA_INICIAL);
		competicao.setDataFinal(DATA_INICIAL);
		competicao.setLocal(LOCAL);
		competicao.setModalidade(MODALIDADE);
		competicao.setEtapa(EtapaEnum.valueOf(ETAPA));
		return competicao;
	}	
	

	private String obterJsonRequisicaoPostInvalidos() throws JsonProcessingException {
		CompeticaoDto competicaoDto = new CompeticaoDto();
		competicaoDto.setId(ID_COMPETICAO);
		competicaoDto.setVisitante(VISITANTE);
		competicaoDto.setAdversario(ADVERSARIO);
		competicaoDto.setDataInicial(this.dateFormat.format((DATA_INICIAL)));
		competicaoDto.setDataFinal(this.dateFormat.format((DATA_INICIAL)));
		competicaoDto.setLocal(LOCAL);
		competicaoDto.setModalidade(MODALIDADE);
		competicaoDto.setEtapa(ETAPA);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(competicaoDto);
	}
	
}	
