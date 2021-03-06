package br.com.cenajur.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.primefaces.model.UploadedFile;

import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Faturamento;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

public class CenajurUtil {

	private CenajurUtil(){
		
	}
	
	public static String[] getVetor(String... parameters){
		
		String[] vetor = null;
		
		if(!TSUtil.isEmpty(parameters)){
			
			vetor = new String[parameters.length];
			int i = 0;
			
			for(String str : parameters){
				
				vetor[i] = str;
				i++;
				
			}
			
		}
		
		return vetor;
		
	}
	
	public static void addDangerMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ATEN��O", msg));
	}
	
	public static void addErrorMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", msg));
	}
	
	public static void addInfoMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", msg));
	}
	
	public static String tratarString(String str){
		return "%" + str.toLowerCase() + "%"; 
	}
	
	public static String formataNumeroProcessoBusca(String campo){
		return " and translate(lower(trim(".concat(campo).concat(")), '.-_*/\\<>|=+;:', '%%%%%%%%%%%%%')"
				+ " like translate(lower(trim(?)), '.-_*/\\<>|=+;:', '%%%%%%%%%%%%%') ");
	}
	
	public static String semAcento(String campo){
		return "translate(lower(trim(".concat(campo).concat(")), '����������������������������������������������', 'aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC')");
	}
	
	public static String getParamSemAcento(String campo){
		return " and translate(lower(trim(".concat(campo).concat(")), '����������������������������������������������', 'aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC')"
				+ " like translate(lower(trim(?)), '����������������������������������������������', 'aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC') ");
	}
	
	public static Date getTrimestreAnterior(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, c.get(Calendar.MONTH)-3);
		return c.getTime();
	}
	
	public static Date getDiaAtual(){
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}
	
	public static Date getMesAtual(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}
	
	public static Date getMesProximo(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MONTH, 1);
		return c.getTime();
	}
	
	public static Date getDiaAmanha(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}
	
	public static Date getDataMaisMeiaHora(Date data){
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.MINUTE, 30);
		return c.getTime();
	}
	
	public static Date getDataMenosDias(int qtdDias){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, qtdDias);
		return c.getTime();
	}
	
	public static void criaArquivo(UploadedFile file, String arquivo) {
		
		try {
			FileUtils.copyInputStreamToFile(file.getInputstream(), new File(arquivo));
		} catch (Exception ex) {
			throw new TSSystemException(ex);
		}
		
	}
	
	public static void criaArquivo(byte[] bytes, String arquivo) {
		
		try {
			FileUtils.copyInputStreamToFile(new ByteArrayInputStream(bytes), new File(arquivo));
		} catch (Exception ex) {
			throw new TSSystemException(ex);
		}
		
	}
	
	public static String obterNomeArquivo(UploadedFile file) {
		
		if(TSUtil.isEmpty(file)){
			return null;
		}
			
		if(file.getFileName().contains("\\")){
			
			String[] fileName = file.getFileName().split("\\\\");
			
			return fileName[fileName.length-1];
			
		} else{
			
			return file.getFileName();
			
		}
	}
	
	public static String obterNomeTemporarioArquivo(UploadedFile file) {
		return Constantes.DOC_TEMP + TSFile.obterExtensaoArquivo(obterNomeArquivo(file));
	}
	
	public static String getAnoMes(Date data) {
		return TSUtil.isEmpty(data) ? null : TSParseUtil.dateToString(data, TSDateUtil.YYYY) + File.separator + TSParseUtil.dateToString(data, TSDateUtil.MM) + File.separator;
	}

	public static String getAnoMesWeb(Date data) {
		return TSUtil.isEmpty(data) ? null : TSParseUtil.dateToString(data, TSDateUtil.YYYY) + "/" + TSParseUtil.dateToString(data, TSDateUtil.MM) + "/";
	}
		
	public static long gerarNumeroAleatorio() {
		return (long) ((10000 * Math.random()) * (100 * Math.random()));
	}
	
	public static String obterResumoGrid(String texto, int tamanho){
		return TSUtil.isEmpty(texto) ? "" : texto.length() < tamanho ? texto : texto.substring(0, tamanho) + "...";
	}
	
	public static String getDescricaoPDF(UploadedFile event){
		try {
			return getText(event.getInputstream());
		} catch (IOException e) {
			new TSApplicationException(e);
		}
		return null;
	}
	
	public static String getText(InputStream stream){
		PDDocument document=null;
		PDFTextStripper text=null;

		try {
			document=PDDocument.load(stream);
	
			text = new PDFTextStripper();
	
			return text.getText(document);

		} catch (Exception e) {

			throw new TSSystemException(e);

		}finally{
			try {
				document.close();
			} catch (IOException e) {}
		}
	}
	
	public static String getParamData(String field){
		StringBuilder query = new StringBuilder();
		return query.append("and day(").append(field).append(") = ? and month(").append(field).append(") = ? and year(").append(field).append(") = ?").toString();
	}
	
	public static List<Object> obterParamsDataAtual2(Date data){
		List<Object> params = new ArrayList<Object>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		params.add(calendar.get(Calendar.DAY_OF_MONTH));
		params.add(calendar.get(Calendar.MONTH) + 1);
		params.add(calendar.get(Calendar.YEAR));
		return params;
	}

	public static Double tratarDouble(Double valor) {

		if (!TSUtil.isEmpty(valor) && valor.equals(0.0)) {
			valor = null;
		}
		
		return valor;
	}
	
	public static Integer obterAnoAtual(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}
	
	public static Integer obterMesAtual(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH + 1);
	}
	
	public static Integer converterStringInteiro(String string){
		
		try{
			
			Integer inteiro = Integer.valueOf(string);
			
			return inteiro;
			
		} catch(NumberFormatException e){
			
			return null;
		}
		
	}
	
	public static boolean isDocumentoPdf(UploadedFile file){
		return TSFile.obterExtensaoArquivo(obterNomeArquivo(file)).equalsIgnoreCase(".pdf");
	}
	
	public static String getTsVectorBusca(String texto){
		
		StringBuilder textoFormatado = new StringBuilder();
		StringBuilder textoFormatado2 = new StringBuilder();
		
		while(texto.contains("  ")){
			texto = texto.replace("  ", " ");
		}
		
		texto = texto.replace(";", ",");
		
		for(String str : texto.split(",")){
			
			textoFormatado.append("(" + str.trim() + ")|");
			
		}
		
		for(String str : textoFormatado.toString().split(" ")){
			
			textoFormatado2.append(str.trim() + "&");
			
		}
		
		String retorno = textoFormatado2.toString();
		
		while(retorno.endsWith("&") || retorno.endsWith("|")){
			
			retorno = retorno.substring(0, retorno.length()-1);
			
		}
		
		return retorno;
	}
	
	public static Cliente getUsuarioConectado(){
		return (Cliente)TSFacesUtil.getObjectInSession("clienteConectado");
	}
	
	public static Map<String, Object> getHashMapReport(){
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("SUBREPORT_DIR", TSFacesUtil.getServletContext().getRealPath("WEB-INF/relatorios/"));
		parametros.put("REPORT_LOCALE", new Locale("pt","BR"));
		
		return parametros;
	}
	
	public static Faturamento obterFaturamentoDevedor(){
		
		Calendar c = Calendar.getInstance();
		
		Faturamento faturamento = new Faturamento();
		
		faturamento.setAno(c.get(Calendar.YEAR));
		faturamento.setMes(c.get(Calendar.MONTH) + 1);
		
		return faturamento;
	}
	
}
