package br.com.cenajur.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.util.Constantes;
import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "colaboradores")
public class Colaborador extends TSActiveRecordAb<Colaborador>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1762162497629022408L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="colaboradores_id")
	@SequenceGenerator(name="colaboradores_id", sequenceName="colaboradores_id_seq")
	private Long id;
	
	private String nome;
	
	private String apelido;
	
	private String oab;
	
	private String rg;
	
	@Column(name = "ordem_impressao")
	private Integer ordemImpressao;
	
	@ManyToOne
	@JoinColumn(name = "tipo_colaborador_id")
	private TipoColaborador tipoColaborador;
	
	@Column(name = "flag_ativo")
	private Boolean flagAtivo;
	
	@Column(name = "flag_procuracao_individual")
	private Boolean flagProcuracaoIndividual;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getOab() {
		return oab;
	}

	public void setOab(String oab) {
		this.oab = oab;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}
	
	public Integer getOrdemImpressao() {
		return ordemImpressao;
	}

	public void setOrdemImpressao(Integer ordemImpressao) {
		this.ordemImpressao = ordemImpressao;
	}

	public TipoColaborador getTipoColaborador() {
		return tipoColaborador;
	}

	public void setTipoColaborador(TipoColaborador tipoColaborador) {
		this.tipoColaborador = tipoColaborador;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Boolean getFlagProcuracaoIndividual() {
		return flagProcuracaoIndividual;
	}

	public void setFlagProcuracaoIndividual(Boolean flagProcuracaoIndividual) {
		this.flagProcuracaoIndividual = flagProcuracaoIndividual;
	}

	public List<Colaborador> findAdvogadosProcuracaoIndividual(){
		return super.find(" from Colaborador c where c.tipoColaborador.id = ? and c.flagAtivo = true and c.flagProcuracaoIndividual = true ", "ordemImpressao", Constantes.TIPO_COLABORADOR_ADVOGADO);
	}
	
	public List<Colaborador> findColaboradoresProcuracaoColetiva(){
		return super.find(" from Colaborador c where (c.tipoColaborador.id = ? or c.tipoColaborador.id = ?) and c.flagAtivo = true ", "ordemImpressao", Constantes.TIPO_COLABORADOR_ADVOGADO, Constantes.TIPO_COLABORADOR_ESTAGIARIO);
	}	
}
	