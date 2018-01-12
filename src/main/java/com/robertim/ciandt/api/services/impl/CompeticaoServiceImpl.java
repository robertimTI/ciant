package com.robertim.ciandt.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.robertim.ciandt.api.entities.Competicao;
import com.robertim.ciandt.api.repositories.CompeticaoRepository;
import com.robertim.ciandt.api.services.CompeticaoService;

@Service	
public class CompeticaoServiceImpl implements CompeticaoService{
	private static final Logger log = LoggerFactory.getLogger(CompeticaoServiceImpl.class);
	
	@Autowired
    private CompeticaoRepository competicaoRepository;
	
	@Override
	public Optional<Competicao> buscarPorDataInicial(String data) {
		log.info("Buscando uma data inicial {}", data);
		return Optional.ofNullable(competicaoRepository.findByDataInicial(data));
	}

	@Override
	public Competicao persistir(Competicao competicao) {
		// TODO Auto-generated method stub
		log.info("Persistindo a competicao: {}", competicao);
		return this.competicaoRepository.save(competicao);
	}

	@Override
	public Optional<List<Competicao>> buscarPorDataInicialAndLocal(LocalDate data, String local) {
		log.info("Buscando por data e local: {} , {}", data,local);
		return Optional.ofNullable(competicaoRepository.findByDataInicialAndLocal(data, local));
	}

	@Override
	public Optional<List<Competicao>> buscarPorData(String data, String local) {
		log.info("Buscando por data e local: {} , {}", data,local);
		return Optional.ofNullable(competicaoRepository.findByData(data, local));
	}

	@Override
	public Page<Competicao> buscarPorModalidade(String modalidade, PageRequest pageRequest) {
		 log.info("Buscando por modalidade {}", modalidade);
		return this.competicaoRepository.findByModalidade(modalidade, pageRequest);
	}

	@Override
	public Page<Competicao> buscarTodos(PageRequest pageRequest) {
		 log.info("Buscando todos");
		return this.competicaoRepository.findAll(pageRequest);
	}

	@Override
	public Optional<List<Competicao>> buscarTodosPorData(String data) {
		 log.info("Buscando todos por data busca refinada");
		return Optional.ofNullable(this.competicaoRepository.findAllByData(data));
	}
}
