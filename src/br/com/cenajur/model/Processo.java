package br.com.cenajur.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "processo")
public class Processo extends TSActiveRecordAb<Processo>{

	@Id
	@GeneratedValue
	private Long id;
	
	private Long numero;
	
	private List<Cliente> clientes;
	
	private Objeto objetoModel;
	
	private Integer lote;
	
	private List<ParteContraria> parteContrariaModel;
	
	@Column(name = "tipo_processo_id")
	private TipoProcesso tipoProcessoModel;
	
	private Vara varaModel;
	
	private Comarca comarcaModel;
	
	private Colaborador advogado;
	
	private Date dataAbertura;
	
	private Parte parteModel;
	
	private SituacaoProcessoModel situacaoProcessoModel;
	
	private Date dataArquivamento;
	
	private Processo processoPrincipal;
	
	private String observacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Objeto getObjetoModel() {
		return objetoModel;
	}

	public void setObjetoModel(Objeto objetoModel) {
		this.objetoModel = objetoModel;
	}

	public Integer getLote() {
		return lote;
	}

	public void setLote(Integer lote) {
		this.lote = lote;
	}

	public List<ParteContraria> getParteContrariaModel() {
		return parteContrariaModel;
	}

	public void setParteContrariaModel(List<ParteContraria> parteContrariaModel) {
		this.parteContrariaModel = parteContrariaModel;
	}

	public TipoProcesso getTipoProcessoModel() {
		return tipoProcessoModel;
	}

	public void setTipoProcessoModel(TipoProcesso tipoProcessoModel) {
		this.tipoProcessoModel = tipoProcessoModel;
	}

	public Vara getVaraModel() {
		return varaModel;
	}

	public void setVaraModel(Vara varaModel) {
		this.varaModel = varaModel;
	}

	public Comarca getComarcaModel() {
		return comarcaModel;
	}

	public void setComarcaModel(Comarca comarcaModel) {
		this.comarcaModel = comarcaModel;
	}

	public Colaborador getAdvogado() {
		return advogado;
	}

	public void setAdvogado(Colaborador advogado) {
		this.advogado = advogado;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Parte getParteModel() {
		return parteModel;
	}

	public void setParteModel(Parte parteModel) {
		this.parteModel = parteModel;
	}

	public SituacaoProcessoModel getSituacaoProcessoModel() {
		return situacaoProcessoModel;
	}

	public void setSituacaoProcessoModel(SituacaoProcessoModel situacaoProcessoModel) {
		this.situacaoProcessoModel = situacaoProcessoModel;
	}

	public Date getDataArquivamento() {
		return dataArquivamento;
	}

	public void setDataArquivamento(Date dataArquivamento) {
		this.dataArquivamento = dataArquivamento;
	}

	public Processo getProcessoPrincipal() {
		return processoPrincipal;
	}

	public void setProcessoPrincipal(Processo processoPrincipal) {
		this.processoPrincipal = processoPrincipal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Processo other = (Processo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
