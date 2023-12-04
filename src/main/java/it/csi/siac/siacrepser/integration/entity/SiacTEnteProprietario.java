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

import it.csi.siac.siaccommonser.integration.entity.SiacTBase;

/**
 * The persistent class for the siac_t_ente_proprietario database table.
 * 
 */
@Entity
@Table(name = "siac_t_ente_proprietario")
public class SiacTEnteProprietario extends SiacTBase {

	private static final long serialVersionUID = -6120671511243844841L;

	@Id
	@SequenceGenerator(name = "SIAC_T_ENTE_PROPRIETARIO_ENTEPROPRIETARIOID_GENERATOR", allocationSize = 1, sequenceName = "SIAC_T_ENTE_PROPRIETARIO_ENTE_PROPRIETARIO_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIAC_T_ENTE_PROPRIETARIO_ENTEPROPRIETARIOID_GENERATOR")
	@Column(name = "ente_proprietario_id")
	private Integer uid;

	@Column(name = "codice_fiscale")
	private String codiceFiscale;

	@Column(name = "ente_denominazione")
	private String denominazione;

	public SiacTEnteProprietario(int uid) {
		this();
		setUid(uid);
	}

	public SiacTEnteProprietario() {
		super();
	}

	@Override
	public Integer getUid() {
		// TODO Auto-generated method stub
		return this.uid;
	}

	@Override
	public void setUid(Integer uid) {
		// TODO Auto-generated method stub
		this.uid = uid;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}


}