package br.com.cenajur.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@Table(name = "colaboradores")
public class Colaborador extends TSActiveRecordAb<Colaborador>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1762162497629022408L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="colaboradores_id")
	@SequenceGenerator(name="colaboradores_id", sequenceName="colaboradores_id_seq")
	private Long id;
	
	private String nome;
	
	private String apelido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	
		
}
	