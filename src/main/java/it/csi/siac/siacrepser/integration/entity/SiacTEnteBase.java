/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.integration.entity;



import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import it.csi.siac.siaccommonser.integration.entity.SiacTBase;

@MappedSuperclass
public abstract class SiacTEnteBase extends SiacTBase {
	private static final long serialVersionUID = -3887394797704237638L;
	
	@ManyToOne
	@JoinColumn(name = "ente_proprietario_id")
	private SiacTEnteProprietario enteProprietario;

	public void init(int enteId, String loginOperazione, Date dataCreazione) {
		setEnteProprietario(new SiacTEnteProprietario(enteId));
		setLoginOperazione(loginOperazione);
		setDataCreazione(dataCreazione);
	}

	public void init(int enteId, String loginOperazione) {
		init(enteId, loginOperazione, new Date());
	}

	public SiacTEnteProprietario getEnteProprietario() {
		return enteProprietario;
	}

	public void setEnteProprietario(SiacTEnteProprietario enteProprietario) {
		this.enteProprietario = enteProprietario;
	}

}