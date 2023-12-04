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
@Table(name = "siac_d_file_tipo")
public class SiacDFileTipo extends SiacTEnteBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 811069845510782001L;

	@Id
	@SequenceGenerator(name = "SIAC_D_FILE_TIPO_FILETIPOID_GENERATOR", allocationSize = 1, sequenceName = "SIAC_D_FILE_TIPO_FILE_TIPO_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIAC_D_FILE_TIPO_FILETIPOID_GENERATOR")
	@Column(name = "file_tipo_id")
	private Integer uid;

	@Column(name = "file_tipo_code")
	private String codice;

	@Column(name = "file_tipo_desc")
	private String descrizione;

	public SiacDFileTipo(Integer uid) {
		this();
		this.uid = uid;
	}

	public SiacDFileTipo() {
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

}