package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.List;

import br.com.cenajur.model.CategoriaDocumento;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.DocumentoProcesso;
import br.com.cenajur.model.ParteContraria;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.ProcessoParteContraria;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;

public class ProcessoAux extends TSMainFaces{

	private List<Processo> processos;
	
	private Processo processoSelecionado;
	private Cliente clienteSelecionado;
	private ProcessoNumero processoNumeroSelecionado;
	private ProcessoCliente processoClienteSelecionado;
	private ProcessoParteContraria processoParteContrariaSelecionada;
	private ParteContraria parteContrariaSelecionada;
	private DocumentoProcesso documentoProcesso;
	private CategoriaDocumento categoriaDocumento;
	private DocumentoProcesso documentoSelecionado;
	private ProcessoAndamentoUtil processoAndamentoUtil;
	private ProcessoAudienciaUtil processoAudienciaUtil;
	
	private int indexProcesso;
	
	public ProcessoAux() {
		this.documentoProcesso = new DocumentoProcesso();
		this.categoriaDocumento = new CategoriaDocumento();
		this.processoAndamentoUtil = new ProcessoAndamentoUtil();
		this.processoAudienciaUtil = new ProcessoAudienciaUtil();
	}
	
	public String iniciarNumerosProcessos(){

		if(TSUtil.isEmpty(this.processos.get(indexProcesso).getProcessosNumerosTemp())){
			this.processos.get(indexProcesso).setProcessosNumerosTemp(new ArrayList<ProcessoNumero>());
		}
		
		this.processoSelecionado = this.processos.get(indexProcesso); 
		
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

	public int getIndexProcesso() {
		return indexProcesso;
	}

	public void setIndexProcesso(int indexProcesso) {
		this.indexProcesso = indexProcesso;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}

	public ProcessoNumero getProcessoNumeroSelecionado() {
		return processoNumeroSelecionado;
	}

	public void setProcessoNumeroSelecionado(ProcessoNumero processoNumeroSelecionado) {
		this.processoNumeroSelecionado = processoNumeroSelecionado;
	}

	public Processo getProcessoSelecionado() {
		return processoSelecionado;
	}

	public void setProcessoSelecionado(Processo processoSelecionado) {
		this.processoSelecionado = processoSelecionado;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public ProcessoCliente getProcessoClienteSelecionado() {
		return processoClienteSelecionado;
	}

	public void setProcessoClienteSelecionado(ProcessoCliente processoClienteSelecionado) {
		this.processoClienteSelecionado = processoClienteSelecionado;
	}

	public ParteContraria getParteContrariaSelecionada() {
		return parteContrariaSelecionada;
	}

	public void setParteContrariaSelecionada(ParteContraria parteContrariaSelecionada) {
		this.parteContrariaSelecionada = parteContrariaSelecionada;
	}

	public ProcessoParteContraria getProcessoParteContrariaSelecionada() {
		return processoParteContrariaSelecionada;
	}

	public void setProcessoParteContrariaSelecionada(ProcessoParteContraria processoParteContrariaSelecionada) {
		this.processoParteContrariaSelecionada = processoParteContrariaSelecionada;
	}

	public DocumentoProcesso getDocumentoProcesso() {
		return documentoProcesso;
	}

	public void setDocumentoProcesso(DocumentoProcesso documentoProcesso) {
		this.documentoProcesso = documentoProcesso;
	}

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
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
	
}
