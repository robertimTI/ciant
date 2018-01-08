package com.robertim.ciandt.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robertim.ciandt.api.dtos.CompeticaoDto;
import com.robertim.ciandt.api.entities.Competicao;
import com.robertim.ciandt.api.enums.EtapaEnum;
import com.robertim.ciandt.api.response.Response;
import com.robertim.ciandt.api.services.CompeticaoService;
@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
public class CompeticaoController {
	private static final Logger log = LoggerFactory.getLogger(CompeticaoController.class);
	
	@Autowired
	private CompeticaoService competicaoService;
	
	@Value("${paginacao.qtd_por_pagina}")
    private int qtdPorPagina;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final SimpleDateFormat dateFormatSearch = new SimpleDateFormat("yyyy-MM-dd");
	public CompeticaoController() {
	}
	
	/**
     * Cadastra uma disputa no sistema.
     *
     * @param CompeticaoDto
     * @param result
     * @return ResponseEntity<Response<CompeticaoDto>>
     * @throws NoSuchAlgorithmException
	 * @throws ParseException 
     */
    @PostMapping(value = "/cadastrar")
    public ResponseEntity<Response<CompeticaoDto>> cadastrar(@Valid @RequestBody CompeticaoDto competicaoDto,
                                                             BindingResult result) throws NoSuchAlgorithmException, ParseException {
        log.info("Cadastrando Competição : {}", competicaoDto.toString());
        Response<CompeticaoDto> response = new Response<CompeticaoDto>();

        validarDadosExistentes(competicaoDto, result);
        Competicao competicao = this.converterDtoParaCompeticao(competicaoDto, result);

        if (result.hasErrors()) {
            log.error("Erro validando dados de cadastro Competicao: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        this.competicaoService.persistir(competicao);

        response.setData(this.converterCompeticaoDto(competicao));
        return ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "/modalidade/{modalidade}")
    public ResponseEntity<Response<Page<CompeticaoDto>>> listarPorModalidade(
            @PathVariable("modalidade") String modalidade,
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "dataInicial") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir){
        log.info("Buscando por modalidade {}", modalidade);
        Response<Page<CompeticaoDto>> response = new Response<Page<CompeticaoDto>>();
        PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Sort.Direction.valueOf(dir), ord);
        Page<Competicao> competicoes = this.competicaoService.buscarPorModalidade(modalidade, pageRequest);
        Page<CompeticaoDto> competicaoDto = competicoes.map(competicao -> this.converterCompeticaoDto(competicao));
        response.setData(competicaoDto);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "/modalidade/")
    public ResponseEntity<Response<Page<CompeticaoDto>>> listarTodos(
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "dataInicial") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir){
        Response<Page<CompeticaoDto>> response = new Response<Page<CompeticaoDto>>();
        PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Sort.Direction.valueOf(dir), ord);
        Page<Competicao> competicoes = this.competicaoService.buscarTodos(pageRequest);
        Page<CompeticaoDto> competicaoDto = competicoes.map(competicao -> this.converterCompeticaoDto(competicao));
        
        response.setData(competicaoDto);
        return ResponseEntity.ok(response);
    }
    /**
     * Converte os dados do DTO para competicao.
     *
     * @param competicaoDto
     * @param result
     * @return Competicao
     * @throws NoSuchAlgorithmException
     * @throws ParseException 
     */
    private void validarDadosExistentes(CompeticaoDto competicaoDto, BindingResult result) {
        if(competicaoDto.getEtapa().toString().equals(EtapaEnum.ELIMINATORIAS.toString()) ||
        		competicaoDto.getEtapa().toString().equals(EtapaEnum.OITAVAS_FINAL.toString())||(
        				competicaoDto.getEtapa().toString().equals(EtapaEnum.QUARTAS_FINAL.toString()))) {
        	if((competicaoDto.getAdversario().equals(competicaoDto.getVisitante()))) {
        		result.addError(new ObjectError("competicao", "O adversário somente podem ser iguais em semi-finais e finais."));
        	}
        }
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime startDate = formatter.parseDateTime(competicaoDto.getDataInicial());
        DateTime endDate = formatter.parseDateTime(competicaoDto.getDataFinal());
        Duration duration = new Duration(startDate,endDate);
        if(duration.getStandardMinutes()<30) {
        	System.out.println("Duração da partida deve ser no mínimo de 30 minutos.");
        	result.addError(new ObjectError("competicao", "Duração da partida deve ser no mínimo de 30 minutos."));
        }
        
        /* Optional<List<Competicao>> competicao;
        DateTime formattedTime = formatter.parseDateTime(competicaoDto.getDataInicial());
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd");
        log.info("Printando a data: {}", dtfOut.print(formattedTime));
        
        
			//competicao = this.competicaoService.buscarPorDataInicialAndLocal(this.dateFormatSearch.parse(competicaoDto.getDataInicial()), competicaoDto.getLocal());
			try {
				
				competicao = this.competicaoService.buscarPorData(formattedTime, competicaoDto.getLocal());
				List<Competicao> competicaoLocalData = competicao.get();
				 if(competicaoLocalData.size()>4) {
					 System.out.println("Maximo de 4 partidas por dia");
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
        
    }
    private Competicao converterDtoParaCompeticao(CompeticaoDto competicaoDto, BindingResult result)
            throws NoSuchAlgorithmException, ParseException {
        Competicao competicao = new Competicao();
        competicao.setModalidade(competicaoDto.getModalidade());
        competicao.setAdversario(competicaoDto.getAdversario());
        competicao.setDataInicial(this.dateFormat.parse(competicaoDto.getDataInicial()));
        competicao.setDataFinal(this.dateFormat.parse(competicaoDto.getDataFinal()));
        if(EnumUtils.isValidEnum(EtapaEnum.class, competicaoDto.getEtapa())) {
        	competicao.setEtapa(EtapaEnum.valueOf(competicaoDto.getEtapa()));
        }else {
        	result.addError(new ObjectError("etapa", "Etapa inválida."));
        }
        competicao.setLocal(competicaoDto.getLocal());
        competicao.setVisitante(competicaoDto.getVisitante());
        return competicao;
    }

    private CompeticaoDto converterCompeticaoDto(Competicao competicao) {
        CompeticaoDto competicaoDto = new CompeticaoDto();
        competicaoDto.setId(competicao.getId());
        competicaoDto.setAdversario(competicao.getAdversario());
        competicaoDto.setDataInicial(this.dateFormat.format(competicao.getDataInicial()));
        competicaoDto.setDataFinal(this.dateFormat.format(competicao.getDataFinal()));
        competicaoDto.setLocal(competicao.getLocal());
        competicaoDto.setModalidade(competicao.getModalidade());
        competicaoDto.setEtapa(competicao.getEtapa().toString());
        competicaoDto.setVisitante(competicao.getVisitante());
        return competicaoDto;
    }
	
}
