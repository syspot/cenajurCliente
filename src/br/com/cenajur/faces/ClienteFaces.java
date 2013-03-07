package br.com.cenajur.faces;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Agenda;
import br.com.cenajur.model.AndamentoProcesso;
import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.Banco;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Graduacao;
import br.com.cenajur.model.MotivoCancelamento;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.ProcessoParteContraria;
import br.com.cenajur.model.SituacaoProcessoCliente;
import br.com.cenajur.model.SituacaoProcessoParteContraria;
import br.com.cenajur.model.Turno;
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
