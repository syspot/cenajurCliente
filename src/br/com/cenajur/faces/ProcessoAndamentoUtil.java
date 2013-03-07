package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.cenajur.model.AndamentoProcesso;
import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.DocumentoAndamentoProcesso;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.TipoAndamentoProcesso;
import br.com.cenajur.model.TipoCategoria;
import br.com.cenajur.util.Constantes;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

public class ProcessoAndamentoUtil {
	
	private ProcessoNumero processoNumeroPrincipal;
	private ProcessoNumero processoNumeroBackup;

	private Processo crudModel;
	private CategoriaDocumento categoriaDocumento;
	private List<SelectItem> categoriasDocumentos;
	
	private AndamentoProcesso andamentoProcesso;
	private AndamentoProcesso andamentoProcessoSelecionado;
	
	private DocumentoAndamentoProcesso documentoAndamentoProcesso;
	private DocumentoAndamentoProcesso documentoAndamentoProcessoSelecionado;
	
	public ProcessoAndamentoUtil() {
		this.crudModel = new Processo();
		setCategoriaDocumento(new CategoriaDocumento());
		getCategoriaDocumento().setTipoCategoria(new TipoCategoria(Constantes.TIPO_CATEGORIA_ANDAMENTO_PROCESSO));
		setDocumentoAndamentoProcesso(new DocumentoAndamentoProcesso());
		this.initAndamentoProcesso();
		this.initCombo();
	}
	
	public ProcessoAndamentoUtil(Processo processo) {
		this();
		setCrudModel(processo);
	}
	
	private void initAndamentoProcesso(){
		
		this.andamentoProcesso = new AndamentoProcesso();
		this.andamentoProcesso.setTipoAndamentoProcesso(new TipoAndamentoProcesso());
		this.andamentoProcesso.setDocumentos(new ArrayList<DocumentoAndamentoProcesso>());
		this.processoNumeroPrincipal = this.processoNumeroBackup;
		
		if(TSUtil.isEmpty(processoNumeroPrincipal)){
			this.processoNumeroPrincipal = new ProcessoNumero();
		}
		
	}
	
	private void initCombo(){
		this.categoriasDocumentos = TSFacesUtil.initCombo(getCategoriaDocumento().findByModel("descricao"), "id", "descricao");
	}
	
	public String limpar(){
		this.initAndamentoProcesso();
		return null;
	}
	
	public String abrirDialogAndamento(){
		this.andamentoProcesso = this.andamentoProcesso.getById();
		this.processoNumeroPrincipal = this.processoNumeroPrincipal.getById();
		this.crudModel = this.crudModel.getById();
		return null;
	}
	
	public String obterAndamentoProcesso(){
		this.andamentoProcesso = this.andamentoProcesso.getById();
		return null;
	}
	
	public List<SelectItem> getCategoriasDocumentos() {
		return categoriasDocumentos;
	}

	public void setCategoriasDocumentos(List<SelectItem> categoriasDocumentos) {
		this.categoriasDocumentos = categoriasDocumentos;
	}

	public Processo getCrudModel() {
		return crudModel;
	}

	public void setCrudModel(Processo crudModel) {
		this.crudModel = crudModel;
	}

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}

	public AndamentoProcesso getAndamentoProcesso() {
		return andamentoProcesso;
	}

	public void setAndamentoProcesso(AndamentoProcesso andamentoProcesso) {
		this.andamentoProcesso = andamentoProcesso;
	}

	public AndamentoProcesso getAndamentoProcessoSelecionado() {
		return andamentoProcessoSelecionado;
	}

	public void setAndamentoProcessoSelecionado(AndamentoProcesso andamentoProcessoSelecionado) {
		this.andamentoProcessoSelecionado = andamentoProcessoSelecionado;
	}

	public DocumentoAndamentoProcesso getDocumentoAndamentoProcesso() {
		return documentoAndamentoProcesso;
	}

	public void setDocumentoAndamentoProcesso(DocumentoAndamentoProcesso documentoAndamentoProcesso) {
		this.documentoAndamentoProcesso = documentoAndamentoProcesso;
	}

	public DocumentoAndamentoProcesso getDocumentoAndamentoProcessoSelecionado() {
		return documentoAndamentoProcessoSelecionado;
	}

	public void setDocumentoAndamentoProcessoSelecionado(DocumentoAndamentoProcesso documentoAndamentoProcessoSelecionado) {
		this.documentoAndamentoProcessoSelecionado = documentoAndamentoProcessoSelecionado;
	}

	public ProcessoNumero getProcessoNumeroPrincipal() {
		return processoNumeroPrincipal;
	}

	public void setProcessoNumeroPrincipal(ProcessoNumero processoNumeroPrincipal) {
		this.processoNumeroPrincipal = processoNumeroPrincipal;
	}

	public ProcessoNumero getProcessoNumeroBackup() {
		return processoNumeroBackup;
	}

	public void setProcessoNumeroBackup(ProcessoNumero processoNumeroBackup) {
		this.processoNumeroBackup = processoNumeroBackup;
	}

}
