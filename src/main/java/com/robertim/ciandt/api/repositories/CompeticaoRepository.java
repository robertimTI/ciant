package com.robertim.ciandt.api.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robertim.ciandt.api.entities.Competicao;
@Transactional(readOnly = true)
/*@NamedQueries({
    @NamedQuery(name = "CompeticaoRepository.findByData",
            query = "SELECT comp FROM Competicao comp WHERE comp.dataInicial like :data% and local = :local") })*/
@Repository
public interface CompeticaoRepository  extends JpaRepository<Competicao, Long>{
 
	Competicao findByDataInicial(Date data);
	
	List<Competicao> findByDataInicialAndLocal(Date data,String local);
	
	@Query("SELECT comp FROM Competicao comp WHERE comp.dataInicial like :data AND comp.local = :local")
	List<Competicao> findByData(@Param("data")Date data ,@Param("local")String local);
	
	Page<Competicao> findByModalidade(String modalidade,Pageable pageable);
	
	Page<Competicao> findAll(Pageable pageable);
}

