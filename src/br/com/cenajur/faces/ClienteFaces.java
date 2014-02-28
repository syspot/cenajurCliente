package br.com.cenajur.faces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Agenda;
import br.com.cenajur.model.AndamentoProcesso;
import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.Banco;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Graduacao;
import br.com.cenajur.model.MotivoCancelamento;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.ProcessoParteContraria;
import br.com.cenajur.model.SituacaoProcessoCliente;
import br.com.cenajur.model.SituacaoProcessoParteContraria;
import br.com.cenajur.model.Turno;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.JasperUtil;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

@SessionScoped
@SuppressWarnings("serial")
@ManagedBean(name = "clienteFaces")
public class ClienteFaces implements Serializable {

	private Cliente cliente;
	
	private AndamentoProcesso andamentoSelecionado;
	private Audiencia audienciaSelecionada;
	
	private ProcessoAux processoAux;
	
	@PostConstruct
	protected void init() {
		
		Cliente cliente = (Cliente) TSFacesUtil.getManagedBean("clienteConectado");
		
		if(!TSUtil.isEmpty(cliente)){
			
			this.cliente = cliente.getById();
			
			this.posDetail();
			
		}
		
	}
	
	private void iniciaObjetosCombo(){
		if(TSUtil.isEmpty(this.cliente.getBanco())){
			this.cliente.setBanco(new Banco());
		}
		if(TSUtil.isEmpty(this.cliente.getGraduacao())){
			this.cliente.setGraduacao(new Graduacao());
		}
		if(TSUtil.isEmpty(this.cliente.getMotivoCancelamento())){
			this.cliente.setMotivoCancelamento(new MotivoCancelamento());
		}
	}
	
	protected void posDetail() {
		
		for(Processo processo : this.cliente.getProcessos()){
			
			processo.setProcessoNumeroPrincipal(new ProcessoNumero().obterNumeroProcessoPrincipal(processo));
			processo.setAudiencias(new Audiencia().findByProcesso(processo));
			processo.setAndamentos(new AndamentoProcesso().findByProcesso(processo));
			processo.setProcessosNumerosTemp(new ProcessoNumero().pesquisarOutrosNumerosProcessos(processo));
			
			if(TSUtil.isEmpty(processo.getTurno())){
				processo.setTurno(new Turno());
			}
			
			for(ProcessoParteContraria processoParteContraria : processo.getProcessosPartesContrarias()){
				processoParteContraria.setSituacaoProcessoParteContrariaTemp(new SituacaoProcessoParteContraria(processoParteContraria.getSituacaoProcessoParteContraria().getId()));
			}

			for(ProcessoCliente processoCliente : processo.getProcessosClientes()){
				processoCliente.setSituacaoProcessoClienteTemp(new SituacaoProcessoCliente(processoCliente.getSituacaoProcessoCliente().getId()));
			}
			
		}
		
		this.iniciaObjetosCombo();
		
		this.processoAux = new ProcessoAux();
		this.andamentoSelecionado = new AndamentoProcesso();
		this.audienciaSelecionada = new Audiencia();
		
		this.processoAux.setProcessos(this.cliente.getProcessos());
		
		this.cliente.setVisitas(new Agenda().pesquisarVisitasPorCliente(this.cliente));
		
	}
	
	private String gerarProcuracao(List<Colaborador> advogados) {

		try {

			StringBuilder outorgante = new StringBuilder("OUTORGANTE: ");

			outorgante.append("<style isBold=\"true\" pdfFontName=\"Helvetica-Bold\">").append(this.cliente.getNome()).append("</style>");

			if (!TSUtil.isEmpty(this.cliente.getRg())) {
				outorgante.append(" RG: ").append(this.cliente.getRg());
			}

			if (!TSUtil.isEmpty(this.cliente.getCpf())) {
				outorgante.append(" CPF: ").append(this.cliente.getCpf());
			}

			outorgante.append(TSUtil.isEmpty(this.cliente.getLogradouro()) ? "" : " ENDEREÇO: " + this.cliente.getLogradouro() + ", ");
			outorgante.append(TSUtil.isEmpty(this.cliente.getNumero()) ? "" : this.cliente.getNumero() + ", ");
			outorgante.append(TSUtil.isEmpty(this.cliente.getComplemento()) ? "" : this.cliente.getComplemento() + ", ");
			outorgante.append(TSUtil.isEmpty(this.cliente.getBairro()) ? "" : this.cliente.getBairro() + ", ");
			outorgante.append(TSUtil.isEmpty(this.cliente.getCidade()) || TSUtil.isEmpty(this.cliente.getCidade().getId()) ? "" : this.cliente
					.getCidade().getNomeCompleto());
			outorgante.append(TSUtil.isEmpty(this.cliente.getCep()) ? "" : this.cliente.getCep());

			if (!TSUtil.isEmpty(this.cliente.getTelefone())) {
				outorgante.append(" TEL: ").append(this.cliente.getTelefone());
			}

			StringBuilder outorgados = new StringBuilder("OUTORGADOS: ");

			for (Colaborador advogado : advogados) {
				outorgados.append("<style isBold=\"true\" pdfFontName=\"Helvetica-Bold\">").append(advogado.getNome()).append("</style>")
						.append(!TSUtil.isEmpty(advogado.getOab()) ? " (OAB/BA n. " + advogado.getOab() + "), " : " (RG " + advogado.getRg() + "), ");
			}

			outorgados.delete(outorgados.length() - 2, outorgados.length() - 1);

			outorgados.append("todos com escritório profissional na Alameda dos Umbuzeiros, n. 638, Edf. Alameda Centro, "
					+ "Terraço - Caminho das Árvores, Salvador - BA, CEP 41.820-680, nesta Capital.");

			String texto = outorgante.toString()
					+ "\n\n"
					+ outorgados.toString()
					+ "\n\n"
					+ "Pelo presente instrumento particular de mandato e na melhor  forma de direito, o outorgante acima qualificado, nomeia e constitui seu procurador o outorgado supramencionado com o fim de representá-lo junto aos Órgãos Federais, Estaduais e Municipais, Autarquias e Fundações, Juízos Comuns e Especiais, Instituições Financeiras e seguradoras em geral, onde figure como autor ou réu, assistente ou opoente, podendo desistir, transigir, fazer acordo, assumir compromissos, receber, passar recibos e dar quitação, exercer a adjudicação e assinar o auto e carta respectiva, substabelecer com ou sem reservas e praticar os atos necessários ao bom desempenho deste mandato, por mais especiais que sejam, além dos poderes citados na cláusula Ad Judicia.";

			Map<String, Object> parametros = CenajurUtil.getHashMapReport();

			parametros.put("P_TEXTO", texto);

			new JasperUtil().gerarRelatorio("procuracao.jasper", "procuracao", parametros);

		} catch (Exception ex) {

			CenajurUtil.addErrorMessage("Não foi possível gerar relatório.");

			ex.printStackTrace();

		}

		return null;

	}
	
	public String imprimirAtestadoPobreza() {
		return this.gerarRelatorio("declaracaoSituacaoEconomica.jasper", "declaracao_situacao_economica",
				"Não foi possível gerar a declaração de situação econômica");
	}
	
	private String gerarRelatorio(String nomeRelatorio, String nomeImpressao, String msgErro) {
		try {

			Map<String, Object> parametros = CenajurUtil.getHashMapReport();
			
			parametros.put("P_CLIENTE_ID", this.cliente.getId());

			new JasperUtil().gerarRelatorio(nomeRelatorio, nomeImpressao, parametros);

		} catch (Exception ex) {
			CenajurUtil.addErrorMessage(msgErro);
			ex.printStackTrace();
		}

		return null;

	}

	
	public String imprimirProcuracaoColetiva() {
		return this.gerarProcuracao(new Colaborador().findColaboradoresProcuracaoColetiva());
	}
	
	public String imprimirProcuracaoIndividual() {
		return this.gerarProcuracao(new Colaborador().findAdvogadosProcuracaoIndividual());
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public AndamentoProcesso getAndamentoSelecionado() {
		return andamentoSelecionado;
	}

	public void setAndamentoSelecionado(AndamentoProcesso andamentoSelecionado) {
		this.andamentoSelecionado = andamentoSelecionado;
	}

	public Audiencia getAudienciaSelecionada() {
		return audienciaSelecionada;
	}

	public void setAudienciaSelecionada(Audiencia audienciaSelecionada) {
		this.audienciaSelecionada = audienciaSelecionada;
	}

	public ProcessoAux getProcessoAux() {
		return processoAux;
	}

	public void setProcessoAux(ProcessoAux processoAux) {
		this.processoAux = processoAux;
	}

}
