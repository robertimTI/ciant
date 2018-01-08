package com.robertim.ciandt.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.robertim.ciandt.api.enums.EtapaEnum;

@Entity
@Table(name = "competicao")
public class Competicao implements Serializable{
	 private static final long serialVersionUID = 3960436649365666213L;

	    private Long id;
	    private String visitante;
	    private String adversario;
	    private String local;
	    private String modalidade;
	    private Date dataInicial;
	    private Date dataFinal;
	    private EtapaEnum etapa;
	   
	    
		public Competicao() {
		}
			
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
		public Long getId() {
			return id;
		}
		
		public void setId(Long id) {
			this.id = id;
		}
		
		@Column(name = "visitante", nullable = false)
		public String getVisitante() {
			return visitante;
		}
		public void setVisitante(String visitante) {
			this.visitante = visitante;
		}
		
		@Column(name = "adversario", nullable = false)
		public String getAdversario() {
			return adversario;
		}
		public void setAdversario(String adversario) {
			this.adversario = adversario;
		}
		
		@Column(name = "local", nullable = false)
		public String getLocal() {
			return local;
		}
		public void setLocal(String local) {
			this.local = local;
		}
		@Column(name = "modalidade", nullable = false)
		public String getModalidade() {
			return modalidade;
		}
		public void setModalidade(String modalidade) {
			this.modalidade = modalidade;
		}
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name = "data_inicial", nullable = false)
		public Date getDataInicial() {
			return dataInicial;
		}
		public void setDataInicial(Date dataInicial) {
			this.dataInicial = dataInicial;
		}
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name = "data_final", nullable = false)
		public Date getDataFinal() {
			return dataFinal;
		}
		public void setDataFinal(Date dataFinal) {
			this.dataFinal = dataFinal;
		}
		
		@Enumerated(EnumType.STRING)
	    @Column(name = "etapa", nullable = false)	
		public EtapaEnum getEtapa() {
			return etapa;
		}
		public void setEtapa(EtapaEnum etapa) {
			this.etapa = etapa;
		}

		@Override
		public String toString() {
			return "Competicao [id=" + id + ", visitante=" + visitante + ", adversario=" + adversario + ", local="
					+ local + ", modalidade=" + modalidade + ", dataInicial=" + dataInicial + ", dataFinal=" + dataFinal
					+ ", etapa=" + etapa + "]";
		}	    
}
