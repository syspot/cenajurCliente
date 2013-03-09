package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Menu;
import br.com.cenajur.model.Permissao;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

@SessionScoped
@ManagedBean(name = "autenticacaoFaces")
public class AutenticacaoFaces extends TSMainFaces{


	private String nomeTela;
	private String tela;
	private List<Menu> menusPrime;
	private Cliente cliente;
	
	
    public AutenticacaoFaces() {
        clearFields();
    }
    
    public String logout() {
        TSFacesUtil.getRequest().getSession().invalidate();
        return "login";
    }

    protected void clearFields() {
    	this.cliente = new Cliente();
    	this.menusPrime = new ArrayList<Menu>();
    }

    public String limpar() {
    	
    	ColaboradorUtil.remover();

        clearFields();

        TSFacesUtil.removeObjectInSession("clienteConectado");

        TSFacesUtil.getRequest().getSession().invalidate();
        
        return "sair";
    }

    public String entrar() {
    	
    	Cliente cliente = this.cliente.autenticarPorEmailSenha();
    	
    	if(!TSUtil.isEmpty(cliente)){
    		
			this.menusPrime.clear();
			
			TSFacesUtil.addObjectInSession("clienteConectado", cliente);
			
			Permissao permissao = new Permissao(Constantes.PERMISSAO_CLIENTE).getById();
			
			Menu menu = permissao.getMenu();
			menu.getPermissoes().clear();
			menu.getPermissoes().add(permissao);
			
			this.menusPrime.add(menu);
			
			this.tela = permissao.getUrl();
			this.nomeTela = permissao.getDescricao();
			
			return "entrar";
    			
    	} else{
    		
    		CenajurUtil.addErrorMessage("Usuário ou senha não conferem");
			return "login";
    	}
    }
    

	public String recuperarSenha(){
    	return "sucesso";
    }
    	
    public String alterarSenha() throws TSApplicationException{
    	return "sucesso";
    }
    
    public List<Menu> getMenusPrime() {
		return menusPrime;
	}

	public void setMenusPrime(List<Menu> menusPrime) {
		this.menusPrime = menusPrime;
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
    	
}