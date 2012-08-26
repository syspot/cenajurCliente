package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.cenajur.model.AndamentoProcesso;
import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Comarca;
import br.com.cenajur.model.DocumentoProcesso;
import br.com.cenajur.model.Objeto;
import br.com.cenajur.model.ParteContraria;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.ProcessoParteContraria;
import br.com.cenajur.model.SituacaoProcesso;
import br.com.cenajur.model.SituacaoProcessoCliente;
import br.com.cenajur.model.SituacaoProcessoParteContraria;
import br.com.cenajur.model.TipoAndamentoProcesso;
import br.com.cenajur.model.TipoCategoria;
import br.com.cenajur.model.TipoParte;
import br.com.cenajur.model.TipoProcesso;
import br.com.cenajur.model.Turno;
import br.com.cenajur.model.Vara;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;

@ViewScoped
@ManagedBean(name = "processoFaces")
public class ProcessoFaces extends CrudFaces<Processo> {

	private List<SelectItem> objetos;
	private List<SelectItem> tiposProcessos;
	private List<SelectItem> varas;
	private List<SelectItem> comarcas;
	private List<SelectItem> tiposPartes;
	private List<SelectItem> situacoesProcessos;
	private List<SelectItem> situacoesProcessosClientes;
	private List<SelectItem> situacoesProcessosPartesContrarias;
	private List<SelectItem> advogados;
	private List<SelectItem> tiposAndamentosProcessos;
	private List<SelectItem> situacoesAudiencias;
	private List<SelectItem> categoriasDocumentos;
	private List<SelectItem> turnos;
	
	
	private Cliente clienteSelecionado;
	private ParteContraria parteContrariaSelecionada;
	
	private ProcessoCliente processoClienteSelecionado;
	private ProcessoParteContraria processoParteContrariaSelecionada;
	
	private Integer indexProcessoCliente;
	private Integer indexProcessoParteContraria;
	
	private CategoriaDocumento categoriaDocumento;
	
	private DocumentoProcesso documentoProcesso;
	private DocumentoProcesso documentoSelecionado;
	
	private ProcessoAndamentoUtil processoAndamentoUtil;
	private ProcessoAudienciaUtil processoAudienciaUtil;
	
	private ProcessoNumero processoNumeroSelecionado;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombos();
	}
	
	private void initCombos(){
		this.objetos = this.initCombo(new Objeto().findAll("descricao"), "id", "descricao");
		this.tiposProcessos = this.initCombo(new TipoProcesso().findAll("descricao"), "id", "descricao");
		this.varas = this.initCombo(new Vara().findAll("descricao"), "id", "descricao");
		this.comarcas = this.initCombo(new Comarca().findAll("descricao"), "id", "descricao");
		this.tiposPartes = this.initCombo(new TipoParte().findAll("descricao"), "id", "descricao");
		this.situacoesProcessos = this.initCombo(new SituacaoProcesso().findAll("descricao"), "id", "descricao");
		this.situacoesProcessosClientes = this.initCombo(new SituacaoProcessoCliente().findAll("descricao"), "id", "descricao");
		this.situacoesProcessosPartesContrarias = this.initCombo(new SituacaoProcessoParteContraria().findAll("descricao"), "id", "descricao");
		this.advogados = this.initCombo(new Colaborador().findAllAdvogados(), "id", "apelido");
		this.tiposAndamentosProcessos = this.initCombo(new TipoAndamentoProcesso().findAll("descricao"), "id", "descricao");
		this.situacoesAudiencias = this.initCombo(new SituacaoProcesso().findAll("descricao"), "id", "descricao");
		this.categoriasDocumentos = this.initCombo(getCategoriaDocumento().findByModel("descricao"), "id", "descricao");
		this.turnos = this.initCombo(new Turno().findAll(), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Processo());
		getCrudModel().setObjeto(new Objeto());
		getCrudModel().setTipoProcesso(new TipoProcesso());
		getCrudModel().setVara(new Vara());
		getCrudModel().setComarca(new Comarca());
		getCrudModel().setTipoParte(new TipoParte());
		getCrudModel().setSituacaoProcesso(new SituacaoProcesso());
		getCrudModel().setAdvogado(new Colaborador());
		getCrudModel().setTurno(new Turno());
		getCrudModel().setProcessosClientes(new ArrayList<ProcessoCliente>());
		getCrudModel().setProcessosPartesContrarias(new ArrayList<ProcessoParteContraria>());
		getCrudModel().setProcessosNumerosTemp(new ArrayList<ProcessoNumero>());
		getCrudModel().setProcessoNumeroPrincipal(new ProcessoNumero());
		setCategoriaDocumento(new CategoriaDocumento());
		getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_PROCESSO));
		getCrudModel().setDocumentos(new ArrayList<DocumentoProcesso>());
		setDocumentoProcesso(new DocumentoProcesso());
		this.processoAndamentoUtil = new ProcessoAndamentoUtil(getCrudModel());
		this.processoAudienciaUtil = new ProcessoAudienciaUtil(getCrudModel());
		setFlagAlterar(Boolean.FALSE);
		return null;
	}
	
	@Override
	public String limparPesquisa(){
		setCrudPesquisaModel(new Processo());
		getCrudPesquisaModel().setObjeto(new Objeto());
		getCrudPesquisaModel().setTipoProcesso(new TipoProcesso());
		getCrudPesquisaModel().setVara(new Vara());
		getCrudPesquisaModel().setComarca(new Comarca());
		getCrudPesquisaModel().setTipoParte(new TipoParte());
		getCrudPesquisaModel().setSituacaoProcesso(new SituacaoProcesso());
		getCrudPesquisaModel().setAdvogado(new Colaborador());
		getCrudPesquisaModel().setTurno(new Turno());
		getCrudPesquisaModel().setProcessoNumeroPrincipal(new ProcessoNumero());
		setGrid(new ArrayList<Processo>());
		return null;
	}
	
	@Override
	protected void prePersist() {
		
		getCrudModel().setColaboradorAtualizacao(ColaboradorUtil.obterColaboradorConectado());
		getCrudModel().setDataAtualizacao(new Date());

		if(TSUtil.isEmpty(getCrudModel().getTurno()) || TSUtil.isEmpty(getCrudModel().getTurno().getId())){
			getCrudModel().setTurno(null);
		}
		
		ProcessoNumero processoNumero = getCrudModel().getProcessoNumeroPrincipal();
		processoNumero.setFlagPrincipal(Boolean.TRUE);
		processoNumero.setProcesso(getCrudModel());
		
		getCrudModel().setProcessosNumeros(getCrudModel().getProcessosNumerosTemp());
		getCrudModel().getProcessosNumeros().add(processoNumero);
		
		for(ProcessoCliente processoCliente : getCrudModel().getProcessosClientes()){
			processoCliente.setSituacaoProcessoCliente(processoCliente.getSituacaoProcessoClienteTemp());
		}
		
		for(ProcessoParteContraria processoParteContraria : getCrudModel().getProcessosPartesContrarias()){
			processoParteContraria.setSituacaoProcessoParteContraria(processoParteContraria.getSituacaoProcessoParteContrariaTemp());
		}
		
	}
	
	@Override
	protected void preInsert() {
		getCrudModel().setDataCadastro(new Date());
	}
	
	@Override
	protected void posPersist() throws TSApplicationException{

		getCrudModel().setProcessosNumerosTemp(new ProcessoNumero().pesquisarOutrosNumerosProcessos(getCrudModel()));
		
		for(DocumentoProcesso doc : getCrudModel().getDocumentos()){
			
			if(!TSUtil.isEmpty(doc.getDocumento())){
				
				DocumentoProcesso documento = doc.getByModel();
				
				doc.setId(documento.getId());
				doc.setArquivo(doc.getId() + TSFile.obterExtensaoArquivo(doc.getArquivo()));
				CenajurUtil.criaArquivo(doc.getDocumento(), doc.getCaminhoUploadCompleto());
				
				doc.update();
				
			}
			
		}
		
	}
	
	@Override
	protected boolean validaCampos() {

		boolean erro = false;
		
		if(getCrudModel().getProcessosClientes().isEmpty()){
			this.addErrorMessage("Selecione ao menos um cliente");
			erro = true;
		}

		if(getCrudModel().getProcessosPartesContrarias().isEmpty()){
			this.addErrorMessage("Selecione ao menos uma parte contr�ria");
			erro = true;
		}
		
		return erro;
	}
	
	@Override
	protected void posDetail() {
		
		this.processoAndamentoUtil.setCrudModel(getCrudModel());
		this.processoAudienciaUtil.setCrudModel(getCrudModel());
		this.processoAndamentoUtil.getAndamentoProcesso().setProcessoNumero(new ProcessoNumero().obterNumeroProcessoPrincipal(getCrudModel()));
		this.processoAudienciaUtil.getAudiencia().setProcessoNumero(new ProcessoNumero().obterNumeroProcessoPrincipal(getCrudModel()));
		
		if(TSUtil.isEmpty(getCrudModel().getTurno()) || TSUtil.isEmpty(getCrudModel().getTurno().getId())){
			getCrudModel().setTurno(new Turno());
		}
		
		getCrudModel().setProcessoNumeroPrincipal(new ProcessoNumero().obterNumeroProcessoPrincipal(getCrudModel()));
		getCrudModel().setProcessosNumerosTemp(new ProcessoNumero().pesquisarOutrosNumerosProcessos(getCrudModel()));
		
		getCrudModel().setAudiencias(new Audiencia().findByProcesso(getCrudModel()));
		getCrudModel().setAndamentos(new AndamentoProcesso().findByProcesso(getCrudModel()));
		
		for(ProcessoCliente processoCliente : getCrudModel().getProcessosClientes()){
			processoCliente.setSituacaoProcessoClienteTemp(new SituacaoProcessoCliente(processoCliente.getSituacaoProcessoCliente().getId()));
		}
		
		for(ProcessoParteContraria processoParteContraria : getCrudModel().getProcessosPartesContrarias()){
			processoParteContraria.setSituacaoProcessoParteContrariaTemp(new SituacaoProcessoParteContraria(processoParteContraria.getSituacaoProcessoParteContraria().getId()));
		}
		
	}
	
	public void enviarDocumento(FileUploadEvent event) {
		getDocumentoProcesso().setDocumento(event.getFile());
		getDocumentoProcesso().setArquivo(CenajurUtil.obterNomeTemporarioArquivo(event.getFile()));
		getDocumentoProcesso().setDescricaoBusca(CenajurUtil.getDescricaoPDF(event.getFile()));
	}
	
	public String addDocumento(){
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(getDocumentoProcesso().getDocumento())){
			CenajurUtil.addErrorMessage("Documento: Campo obrigat�rio");
			context.addCallbackParam("sucesso", false);
			return null;
		}
		
		context.addCallbackParam("sucesso", true);
		
		getDocumentoProcesso().setProcesso(getCrudModel());
		getDocumentoProcesso().setCategoriaDocumento(getCategoriaDocumento().getById());
		getCrudModel().getDocumentos().add(getDocumentoProcesso());
		getCategoriaDocumento().setId(null);
		setDocumentoProcesso(new DocumentoProcesso());
		return null;
	}
	
	public String removerDocumento(){
		getCrudModel().getDocumentos().remove(this.documentoSelecionado);
		return null;
	}
	
	public String removeCliente(){
		getCrudModel().getProcessosClientes().remove(this.processoClienteSelecionado);
		CenajurUtil.addInfoMessage("Associado removido com sucesso");
		return null;
	}
	
	public String removeParteContraria(){
		getCrudModel().getProcessosPartesContrarias().remove(this.processoParteContrariaSelecionada);
		CenajurUtil.addInfoMessage("Parte Contr�ria removida com sucesso");
		return null;
	}
	
	public String addCliente(){
		
		ProcessoCliente processoClienteSelecionado = new ProcessoCliente(this.clienteSelecionado);
		processoClienteSelecionado.setProcesso(getCrudModel());
		processoClienteSelecionado.setSituacaoProcessoClienteTemp(new SituacaoProcessoCliente(Constantes.SITUACAO_PROCESSO_CLIENTE_ATIVO));
		
		if(!this.getCrudModel().getProcessosClientes().contains(processoClienteSelecionado)){
			
			this.getCrudModel().getProcessosClientes().add(processoClienteSelecionado);
			CenajurUtil.addInfoMessage("Associado adicionado com sucesso");
			
		} else{
			
			CenajurUtil.addErrorMessage("Esse associado j� foi adicionado");
			
		}
		
		return null;
	}
	
	public String addParteContraria(){
		
		ProcessoParteContraria processoClienteSelecionado = new ProcessoParteContraria(this.parteContrariaSelecionada);
		processoClienteSelecionado.setProcesso(getCrudModel());
		processoClienteSelecionado.setSituacaoProcessoParteContrariaTemp(new SituacaoProcessoParteContraria(Constantes.SITUACAO_PROCESSO_PARTE_CONTRARIA_ATIVO));
		
		if(!this.getCrudModel().getProcessosPartesContrarias().contains(processoClienteSelecionado)){
			
			getCrudModel().getProcessosPartesContrarias().add(processoClienteSelecionado);
			CenajurUtil.addInfoMessage("Parte contr�ria adicionada com sucesso");
			
		} else{
			
			CenajurUtil.addErrorMessage("Essa parte contr�ria j� foi adicionada");
			
		}
		
		return null;
	}
	
	public String addNumeroProcesso(){
		ProcessoNumero processoNumero = new ProcessoNumero();
		processoNumero.setFlagPrincipal(Boolean.FALSE);
		processoNumero.setProcesso(getCrudModel());
		getCrudModel().getProcessosNumerosTemp().add(processoNumero);
		return null;
	}
	
	public String removerNumeroProcesso(){
		getCrudModel().getProcessosNumeros().remove(this.processoNumeroSelecionado);
		return null;
	}
	
	public String atualizarProcessoCliente(){
		CenajurUtil.addInfoMessage("Altera��o realizada com sucesso");
		return null;
	}
	
	public String atualizarProcessoParteContraria(){
		CenajurUtil.addInfoMessage("Altera��o realizada com sucesso");
		return null;
	}
	
	public String limparDataArquivamentoProcessoCliente(){
		this.processoClienteSelecionado.setDataArquivamento(null);
		return null;
	}
	
	public String limparDataArquivamentoProcessoParteContraria(){
		this.processoParteContrariaSelecionada.setDataArquivamento(null);
		return null;
	}
	
	public List<SelectItem> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<SelectItem> objetos) {
		this.objetos = objetos;
	}

	public List<SelectItem> getTiposProcessos() {
		return tiposProcessos;
	}

	public void setTiposProcessos(List<SelectItem> tiposProcessos) {
		this.tiposProcessos = tiposProcessos;
	}

	public List<SelectItem> getVaras() {
		return varas;
	}

	public void setVaras(List<SelectItem> varas) {
		this.varas = varas;
	}

	public List<SelectItem> getComarcas() {
		return comarcas;
	}

	public void setComarcas(List<SelectItem> comarcas) {
		this.comarcas = comarcas;
	}

	public List<SelectItem> getTiposPartes() {
		return tiposPartes;
	}

	public void setTiposPartes(List<SelectItem> tiposPartes) {
		this.tiposPartes = tiposPartes;
	}

	public List<SelectItem> getSituacoesProcessos() {
		return situacoesProcessos;
	}

	public List<SelectItem> getSituacoesProcessosClientes() {
		return situacoesProcessosClientes;
	}

	public void setSituacoesProcessosClientes(List<SelectItem> situacoesProcessosClientes) {
		this.situacoesProcessosClientes = situacoesProcessosClientes;
	}

	public List<SelectItem> getSituacoesProcessosPartesContrarias() {
		return situacoesProcessosPartesContrarias;
	}

	public void setSituacoesProcessosPartesContrarias(List<SelectItem> situacoesProcessosPartesContrarias) {
		this.situacoesProcessosPartesContrarias = situacoesProcessosPartesContrarias;
	}

	public void setSituacoesProcessos(List<SelectItem> situacoesProcessos) {
		this.situacoesProcessos = situacoesProcessos;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public ParteContraria getParteContrariaSelecionada() {
		return parteContrariaSelecionada;
	}

	public void setParteContrariaSelecionada(ParteContraria parteContrariaSelecionada) {
		this.parteContrariaSelecionada = parteContrariaSelecionada;
	}

	public ProcessoCliente getProcessoClienteSelecionado() {
		return processoClienteSelecionado;
	}

	public void setProcessoClienteSelecionado(ProcessoCliente processoClienteSelecionado) {
		this.processoClienteSelecionado = processoClienteSelecionado;
	}

	public ProcessoParteContraria getProcessoParteContrariaSelecionada() {
		return processoParteContrariaSelecionada;
	}

	public void setProcessoParteContrariaSelecionada(ProcessoParteContraria processoParteContrariaSelecionada) {
		this.processoParteContrariaSelecionada = processoParteContrariaSelecionada;
	}

	public List<SelectItem> getAdvogados() {
		return advogados;
	}

	public void setAdvogados(List<SelectItem> advogados) {
		this.advogados = advogados;
	}

	public List<SelectItem> getTiposAndamentosProcessos() {
		return tiposAndamentosProcessos;
	}

	public void setTiposAndamentosProcessos(List<SelectItem> tiposAndamentosProcessos) {
		this.tiposAndamentosProcessos = tiposAndamentosProcessos;
	}

	public List<SelectItem> getSituacoesAudiencias() {
		return situacoesAudiencias;
	}

	public void setSituacoesAudiencias(List<SelectItem> situacoesAudiencias) {
		this.situacoesAudiencias = situacoesAudiencias;
	}

	public List<SelectItem> getCategoriasDocumentos() {
		return categoriasDocumentos;
	}

	public void setCategoriasDocumentos(List<SelectItem> categoriasDocumentos) {
		this.categoriasDocumentos = categoriasDocumentos;
	}

	public List<SelectItem> getTurnos() {
		return turnos;
	}

	public void setTurnos(List<SelectItem> turnos) {
		this.turnos = turnos;
	}

	public Integer getIndexProcessoCliente() {
		return indexProcessoCliente;
	}

	public void setIndexProcessoCliente(Integer indexProcessoCliente) {
		this.indexProcessoCliente = indexProcessoCliente;
	}

	public Integer getIndexProcessoParteContraria() {
		return indexProcessoParteContraria;
	}

	public void setIndexProcessoParteContraria(Integer indexProcessoParteContraria) {
		this.indexProcessoParteContraria = indexProcessoParteContraria;
	}

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}

	public DocumentoProcesso getDocumentoProcesso() {
		return documentoProcesso;
	}

	public void setDocumentoProcesso(DocumentoProcesso documentoProcesso) {
		this.documentoProcesso = documentoProcesso;
	}

	public DocumentoProcesso getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(DocumentoProcesso documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}

	public ProcessoAndamentoUtil getProcessoAndamentoUtil() {
		return processoAndamentoUtil;
	}

	public void setProcessoAndamentoUtil(ProcessoAndamentoUtil processoAndamentoUtil) {
		this.processoAndamentoUtil = processoAndamentoUtil;
	}

	public ProcessoAudienciaUtil getProcessoAudienciaUtil() {
		return processoAudienciaUtil;
	}

	public void setProcessoAudienciaUtil(ProcessoAudienciaUtil processoAudienciaUtil) {
		this.processoAudienciaUtil = processoAudienciaUtil;
	}

	public ProcessoNumero getProcessoNumeroSelecionado() {
		return processoNumeroSelecionado;
	}

	public void setProcessoNumeroSelecionado(ProcessoNumero processoNumeroSelecionado) {
		this.processoNumeroSelecionado = processoNumeroSelecionado;
	}

}
