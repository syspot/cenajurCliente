package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.cenajur.util.Constantes;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "processos")
public class Processo extends TSActiveRecordAb<Processo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4629137037446817513L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="processos_id")
	@SequenceGenerator(name="processos_id", sequenceName="processos_id_seq")
	private Long id;
	
	@Column(name = "data_ajuizamento")
	private Date dataAjuizamento;
	
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	
	@Transient
	private ProcessoNumero processoNumeroPrincipal;
	
	@OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
	@org.hibernate.annotations.OrderBy(clause = "flag_principal asc, numero")
	private List<ProcessoNumero> processosNumeros;
	
	@OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProcessoCliente> processosClientes;
	
	@ManyToOne
	private Objeto objeto;
	
	private String lote;
	
	@OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProcessoParteContraria> processosPartesContrarias;
	
	@ManyToOne
	@JoinColumn(name = "tipo_processo_id")
	private TipoProcesso tipoProcesso;
	
	@ManyToOne
	private Vara vara;
	
	@ManyToOne
	private Comarca comarca;
	
	@ManyToOne
	private Colaborador advogado;
	
	@Column(name = "data_abertura")
	private Date dataAbertura;
	
	@ManyToOne
	@JoinColumn(name = "tipo_parte_id")
	private TipoParte tipoParte;
	
	@ManyToOne
	@JoinColumn(name = "situacao_processo_id")
	private SituacaoProcesso situacaoProcesso;
	
	@Column(name = "data_arquivamento")
	private Date dataArquivamento;
	
	@ManyToOne
	private Processo processo;
	
	private String observacoes;
	
	@Column(name = "data_atualizacao")
	private Date dataAtualizacao;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_atualizacao_id")
	private Colaborador colaboradorAtualizacao;
	
	@OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@org.hibernate.annotations.Where(clause = "flag_permissao_cliente")
	private List<DocumentoProcesso> documentos;
	
	@ManyToOne
	private Turno turno;
	
	@Transient
	private List<AndamentoProcesso> andamentos;
	
	@Transient
	private List<Audiencia> audiencias;
	
	@Transient
	private List<ProcessoNumero> processosNumerosTemp;
	
	@Transient
	private Long situacaoProcessoId;
	
	@Transient
	private String ano;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public List<ProcessoNumero> getProcessosNumeros() {
		return processosNumeros;
	}

	public void setProcessosNumeros(List<ProcessoNumero> processosNumeros) {
		this.processosNumeros = processosNumeros;
	}

	public List<ProcessoCliente> getProcessosClientes() {
		return processosClientes;
	}

	public void setProcessosClientes(List<ProcessoCliente> processosClientes) {
		this.processosClientes = processosClientes;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public List<ProcessoParteContraria> getProcessosPartesContrarias() {
		return processosPartesContrarias;
	}

	public void setProcessosPartesContrarias(List<ProcessoParteContraria> processosPartesContrarias) {
		this.processosPartesContrarias = processosPartesContrarias;
	}

	public TipoProcesso getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(TipoProcesso tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	public Vara getVara() {
		return vara;
	}

	public void setVara(Vara vara) {
		this.vara = vara;
	}

	public Comarca getComarca() {
		return comarca;
	}

	public void setComarca(Comarca comarca) {
		this.comarca = comarca;
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public TipoParte getTipoParte() {
		return tipoParte;
	}

	public void setTipoParte(TipoParte tipoParte) {
		this.tipoParte = tipoParte;
	}

	public SituacaoProcesso getSituacaoProcesso() {
		return situacaoProcesso;
	}

	public void setSituacaoProcesso(SituacaoProcesso situacaoProcesso) {
		this.situacaoProcesso = situacaoProcesso;
	}

	public Date getDataArquivamento() {
		return dataArquivamento;
	}

	public void setDataArquivamento(Date dataArquivamento) {
		this.dataArquivamento = dataArquivamento;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public Date getDataAjuizamento() {
		return dataAjuizamento;
	}

	public void setDataAjuizamento(Date dataAjuizamento) {
		this.dataAjuizamento = dataAjuizamento;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Colaborador getColaboradorAtualizacao() {
		return colaboradorAtualizacao;
	}

	public void setColaboradorAtualizacao(Colaborador colaboradorAtualizacao) {
		this.colaboradorAtualizacao = colaboradorAtualizacao;
	}

	public List<DocumentoProcesso> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoProcesso> documentos) {
		this.documentos = documentos;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public ProcessoNumero getProcessoNumeroPrincipal() {
		return processoNumeroPrincipal;
	}

	public void setProcessoNumeroPrincipal(ProcessoNumero processoNumeroPrincipal) {
		this.processoNumeroPrincipal = processoNumeroPrincipal;
	}

	public List<AndamentoProcesso> getAndamentos() {
		return andamentos;
	}

	public void setAndamentos(List<AndamentoProcesso> andamentos) {
		this.andamentos = andamentos;
	}

	public List<Audiencia> getAudiencias() {
		return audiencias;
	}

	public void setAudiencias(List<Audiencia> audiencias) {
		this.audiencias = audiencias;
	}
	
	public List<ProcessoNumero> getProcessosNumerosTemp() {
		return processosNumerosTemp;
	}

	public void setProcessosNumerosTemp(List<ProcessoNumero> processosNumerosTemp) {
		this.processosNumerosTemp = processosNumerosTemp;
	}

	public Long getSituacaoProcessoId() {
		return situacaoProcessoId;
	}

	public void setSituacaoProcessoId(Long situacaoProcessoId) {
		this.situacaoProcessoId = situacaoProcessoId;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public boolean isProcessoUnico(){
		return (TSUtil.isEmpty(getProcessosNumeros()) || getProcessosNumeros().size() < 2);
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
	
	public List<Processo> pesquisarPorCliente(Cliente cliente) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" select distinct p from Processo p left outer join fetch p.processosNumeros pn inner join p.processosClientes pc  where 1 = 1 and pc.cliente.id = ?");
		
		List<Object> params = new ArrayList<Object>();
		
		params.add(cliente.getId());
		
		return super.find(query.toString(), "p.situacaoProcesso, p.tipoProcesso", params.toArray());
	}
	
	public boolean isProcessoArquivado(){
		return Constantes.SITUACAO_PROCESSO_ARQUIVADO.equals(situacaoProcesso.getId());
	}

}
