package com.robertim.ciandt.api.services;

import java.util.List;
import java.util.Optional;

import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.robertim.ciandt.api.entities.Competicao;

public interface CompeticaoService {

	Optional<Competicao> buscarPorDataInicial(String data);
	
	Optional<List<Competicao>> buscarPorDataInicialAndLocal(LocalDate data,String local); 
	
	Optional<List<Competicao>> buscarPorData(String data,String local); 
	
	Competicao persistir(Competicao competicao);
	
	Page<Competicao> buscarPorModalidade(String modalidade,PageRequest pageRequest);
	
	Page<Competicao> buscarTodos(PageRequest pageRequest);
	
	Optional<List<Competicao>> buscarTodosPorData(String data);
	
}
