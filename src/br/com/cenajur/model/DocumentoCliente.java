package br.com.cenajur.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "documentos_clientes")
public class DocumentoCliente extends TSActiveRecordAb<DocumentoCliente>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9129315171269941055L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="documentos_clientes_id")
	@SequenceGenerator(name="documentos_clientes_id", sequenceName="documentos_clientes_id_seq")
	private Long id;
	
	@ManyToOne
	private Cliente cliente;
	
	private String arquivo;
	
	private String descricao;
	
	@Column(name = "descricao_busca")
	private String descricaoBusca;
	
	@ManyToOne
	@JoinColumn(name = "categoria_documento_id")
	private CategoriaDocumento categoriaDocumento;

	@Column(name = "flag_permissao_cliente")
	private Boolean flagPermissaoCliente;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricaoBusca() {
		return descricaoBusca;
	}

	public void setDescricaoBusca(String descricaoBusca) {
		this.descricaoBusca = descricaoBusca;
	}

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}
	
	public Boolean getFlagPermissaoCliente() {
		return flagPermissaoCliente;
	}

	public void setFlagPermissaoCliente(Boolean flagPermissaoCliente) {
		this.flagPermissaoCliente = flagPermissaoCliente;
	}

	public String getCaminhoUploadCompleto(){
		return Constantes.PASTA_UPLOAD_ARQUIVO + CenajurUtil.getAnoMes(cliente.getDataCadastro()) + Constantes.PASTA_CLIENTE + arquivo;
	}
	
	public String getCaminhoDownloadCompleto(){
		return Constantes.PASTA_DOWNLOAD_ARQUIVO + CenajurUtil.getAnoMesWeb(cliente.getDataCadastro()) + Constantes.PASTA_CLIENTE + arquivo;
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
		DocumentoCliente other = (DocumentoCliente) obj;
		if (arquivo == null) {
			if (other.arquivo != null)
				return false;
		} else if (!arquivo.equals(other.arquivo))
			return false;
		if (categoriaDocumento == null) {
			if (other.categoriaDocumento != null)
				return false;
		} else if (!categoriaDocumento.equals(other.categoriaDocumento))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public List<DocumentoCliente> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from DocumentoCliente dc where 1 = 1 ");
		
		if(!TSUtil.isEmpty(cliente) && !TSUtil.isEmpty(cliente.getId())){
			query.append("and dc.cliente.id = ? ");
		}
		
		if(!TSUtil.isEmpty(categoriaDocumento) && !TSUtil.isEmpty(categoriaDocumento.getId())){
			query.append("and dc.categoriaDocumento.id = ? ");
		}
		
		List<Object> params = new ArrayList<Object>();
		
		if(!TSUtil.isEmpty(cliente) && !TSUtil.isEmpty(cliente.getId())){
			params.add(cliente.getId());
		}
		
		if(!TSUtil.isEmpty(categoriaDocumento) && !TSUtil.isEmpty(categoriaDocumento.getId())){
			params.add(categoriaDocumento.getId());
		}
		
		return super.find(query.toString(), null, params.toArray());
	}
	
	@Override
	public DocumentoCliente getByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();
		
		query.append(" from DocumentoCliente dc where 1 = 1 ");
		
		query.append("and dc.cliente.id = ? ");
		
		query.append("and dc.categoriaDocumento.id = ? ");

		query.append("and dc.arquivo = ? ");
	
		query.append("and dc.descricao = ? ");
	
		List<Object> params = new ArrayList<Object>();
		
		params.add(cliente.getId());
		
		params.add(categoriaDocumento.getId());
		
		params.add(arquivo);
		
		params.add(descricao);
		
		return super.get(query.toString(), params.toArray());
	}
	
}
