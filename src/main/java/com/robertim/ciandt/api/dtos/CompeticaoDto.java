package com.robertim.ciandt.api.dtos;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class CompeticaoDto {
	
	private Long id;
	private String visitante;
	private String adversario;
	private String local;
	private String modalidade;
	private String dataInicial;
	private String dataFinal;
	private String etapa;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@NotEmpty(message = "Campo do visitante deve ser preenchido")
	public String getVisitante() {
		return visitante;
	}
	public void setVisitante(String visitante) {
		this.visitante = visitante;
	}
	@NotEmpty(message = "Campo do adversário deve ser preenchido")
	public String getAdversario() {
		return adversario;
	}
	public void setAdversario(String adversario) {
		this.adversario = adversario;
	}
	@NotEmpty(message = "Campo local deve ser preenchido")
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	@NotEmpty(message = "Modalidade deve ser preenchida")
	public String getModalidade() {
		return modalidade;
	}
	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}
	@NotEmpty(message = "Data inicial Obrigatória formato padrão yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(iso = ISO.DATE)
	public String getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}
	@NotEmpty(message = "Data Obrigatória final formato padrão yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(iso = ISO.DATE)
	public String getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}
	@NotEmpty(message = "Etapa obrigatória")
	public String getEtapa() {
		return etapa;
	}
	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}
	
	@Override
	public String toString() {
		return "CompeticaoDto [id=" + id + ", visitante=" + visitante + ", adversario=" + adversario + ", local="
				+ local + ", modalidade=" + modalidade + ", dataInicial=" + dataInicial + ", dataFinal=" + dataFinal
				+ ", etapa=" + etapa + "]";
	}
	
	
}
