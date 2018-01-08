package com.robertim.ciandt.api.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.robertim.ciandt.api.entities.Competicao;

public interface CompeticaoService {

	Optional<Competicao> buscarPorDataInicial(Date data);
	
	Optional<List<Competicao>> buscarPorDataInicialAndLocal(Date data,String local); 
	
	Optional<List<Competicao>> buscarPorData(Date data,String local); 
	
	Competicao persistir(Competicao competicao);
	
	Page<Competicao> buscarPorModalidade(String modalidade,PageRequest pageRequest);
	
	Page<Competicao> buscarTodos(PageRequest pageRequest);
	
}
