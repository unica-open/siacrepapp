/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.integration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "siac_t_repj_template")
public class SiacTReportTemplate extends SiacTEnteBase {
	private static final long serialVersionUID = 8113678184285749032L;

	@Id
	@SequenceGenerator(name = "siac_t_repj_template_repjt_id_GENERATOR", allocationSize = 1, sequenceName = "siac_t_repj_template_repjt_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "siac_t_repj_template_repjt_id_GENERATOR")
	@Column(name = "repjt_id")
	private Integer uid;

	@Column(name = "repjt_code")
	private String codice;

	@Column(name = "repjt_desc")
	private String descrizione;

	@Column(name = "repjt_path")
	private String path;

	@Column(name = "repjt_filename")
	private String nomeFile;

	public SiacTReportTemplate() {
		super();
	}

	@Override
	public Integer getUid() {
		return uid;
	}

	@Override
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}