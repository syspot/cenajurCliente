package br.com.cenajur.faces;

import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.cenajur.model.TipoProcessoModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.web.faces.TSMainFaces;

@RequestScoped
@ManagedBean(name = "tipoProcessoFaces")
public class TipoProcessoFaces extends TSMainFaces {

	private TipoProcessoModel tipoProcessoModel;
	
	private TipoProcessoModel tipoProcessoPesquisaModel;
	
	private List<TipoProcessoModel> grid;
	
	
	public TipoProcessoFaces() {
		this.tipoProcessoModel = new TipoProcessoModel();
		this.tipoProcessoModel.setFlagAtivo(Boolean.TRUE);
		this.tipoProcessoPesquisaModel = new TipoProcessoModel();  
		this.grid = Collections.emptyList();
	}
	
	@Override
	protected void clearFields() {
		this.tipoProcessoModel = new TipoProcessoModel();  
		this.tipoProcessoPesquisaModel = new TipoProcessoModel();  
	}
	
	@Override
	protected String insert() throws TSApplicationException {
		
		this.tipoProcessoModel.save();
		
		this.grid = this.tipoProcessoModel.findAll();
		
		return null;
	}
	
	@Override
	protected String find() {
		
		this.grid = this.tipoProcessoModel.findByModel();
		
		return null;
	}

	public TipoProcessoModel getTipoProcessoModel() {
		return tipoProcessoModel;
	}

	public void setTipoProcessoModel(TipoProcessoModel tipoProcessoModel) {
		this.tipoProcessoModel = tipoProcessoModel;
	}

	public List<TipoProcessoModel> getGrid() {
		return grid;
	}

	public void setGrid(List<TipoProcessoModel> grid) {
		this.grid = grid;
	}

	public TipoProcessoModel getTipoProcessoPesquisaModel() {
		return tipoProcessoPesquisaModel;
	}

	public void setTipoProcessoPesquisaModel(
			TipoProcessoModel tipoProcessoPesquisaModel) {
		this.tipoProcessoPesquisaModel = tipoProcessoPesquisaModel;
	}

}