package br.com.cenajur.faces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Cliente;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.EmailUtil;
import br.com.cenajur.util.Utilitarios;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

@SessionScoped
@ManagedBean(name = "autenticacaoFaces")
public class AutenticacaoFaces extends TSMainFaces{


	private String nomeTela;
	private String tela;
	private Cliente cliente;
	private Long opcao;
	private String confirmaSenha;
	private String textoEmail;
	
	
    public AutenticacaoFaces() {
        clearFields();
    }
    
    public String logout() {
        TSFacesUtil.getRequest().getSession().invalidate();
        return "login";
    }

    protected void clearFields() {
    	this.cliente = new Cliente();
    	this.opcao = 1L;
    }

    public String limpar() {
    	
        clearFields();

        TSFacesUtil.removeObjectInSession("clienteConectado");

        TSFacesUtil.getRequest().getSession().invalidate();
        
        return "sair";
    }
    
    public String redirecionar() {
        return "sucesso";
    }

    public String entrar() {
    	
    	Cliente cliente = this.cliente.autenticarPorMatriculaSenha();
    	
    	if(!TSUtil.isEmpty(cliente)){
    		
    		TSFacesUtil.removeObjectInSession("clienteConectado");
			TSFacesUtil.addObjectInSession("clienteConectado", cliente);
			
			this.tela = "/pages/processo/cliente.xhtml";
			this.nomeTela = "Associado";
			
			return "entrar";
    			
    	} else {
    		
    		CenajurUtil.addErrorMessage("Usuário ou senha não conferem");
			return "login";
    	}
    }

	public String recuperarSenha(){
		
		Cliente cliente = this.cliente.autenticarPorMatricula();
    	
    	if (TSUtil.isEmpty(cliente)) {
    		
    		super.addErrorMessage("Usuário não localizado");
    		
    	} else {
    		
    		try {
    			
    			String novaSenha = "" + CenajurUtil.gerarNumeroAleatorio();
    			
    			cliente.setSenha(Utilitarios.gerarHash(novaSenha));
    		
				cliente.update();
	    		
	    		String texto = "Prezado(a), sua nova senha para acessar o sistema do Cenajur é: " + novaSenha;
	    		
	    		new EmailUtil().enviarEmailTratado(cliente.getEmail(), "Recuperação de Senha", texto, null);
	    		
	    		super.addInfoMessage("Uma nova senha foi enviada para seu e-mail");
	    		
	    		this.opcao = 1L;
	    		
			} catch (Exception e) { 
				e.printStackTrace();
				super.addErrorMessage("Ocorreu um erro ao tentar enviar a senha para o email cadastrado");
			}
    		
    	}
    	
    	return "login";
    }
    	
    public String alterarSenha() throws TSApplicationException{

    	Cliente cliente = this.cliente.autenticarPorMatriculaSenha();
    	
    	if(TSUtil.isEmpty(cliente)){
    		
    		CenajurUtil.addErrorMessage("Usuário ou Senha inválidos");
    		
    	} else {
    		
    		cliente.setSenha(Utilitarios.gerarHash(this.cliente.getSenha2()));
    		cliente.update();
    		CenajurUtil.addInfoMessage("Senha alterada com sucesso");
    		this.opcao = 1L;
    		
    	}
    	
    	return "login";
    }
    
    public String enviarEmail(){
    	
    	StringBuilder texto = new StringBuilder();
    	
    	texto.append("Matrícula: ").append(CenajurUtil.getUsuarioConectado().getMatricula()).append("<br/>");
    	texto.append("Nome: ").append(CenajurUtil.getUsuarioConectado().getNome()).append("<br/><br/>");
    	texto.append(this.textoEmail);
    	
    	new EmailUtil().enviarEmailTratado("cenajur@cenajur.com.br", "Fale Conosco - Associado", texto.toString(), "text/html");
    	
    	super.addInfoMessage("Email enviado com sucesso");
    	
    	this.textoEmail = null;
    	
    	return "sucesso";
    }
    
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNomeTela() {
		return nomeTela;
	}

	public void setNomeTela(String nomeTela) {
		this.nomeTela = nomeTela;
	}

	public String getTela() {
		return tela;
	}

	public void setTela(String tela) {
		this.tela = tela;
	}

	public Long getOpcao() {
		return opcao;
	}

	public void setOpcao(Long opcao) {
		this.opcao = opcao;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public String getTextoEmail() {
		return textoEmail;
	}

	public void setTextoEmail(String textoEmail) {
		this.textoEmail = textoEmail;
	}
    	
}