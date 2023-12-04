/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.ui.model;

import java.util.List;

import it.csi.siac.siaccommonapp.model.GenericModel;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.RicercaFile;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.RicercaFile.CriteriRicercaFile;
import it.csi.siac.siaccorser.model.Ente;
import it.csi.siac.siaccorser.model.Richiedente;
import it.csi.siac.siaccorser.model.file.File;
import it.csi.siac.siaccorser.model.paginazione.ParametriPaginazione;

public class GestioneXbrlFileModel extends GenericModel {
	private static final long serialVersionUID = 7412218795938397858L;

	private List<File> elencoFileXbrl;
	private Integer[] selectedXbrl;

	public static RicercaFile buildRicercaFileRequest(Integer idTipo, Richiedente richiedente, Ente ente) {
		CriteriRicercaFile criteri = new CriteriRicercaFile();

		criteri.setIdTipo(idTipo);

		RicercaFile req = new RicercaFile();

		req.setCriteri(criteri);
		req.setParametriPaginazione(ParametriPaginazione.TUTTI_GLI_ELEMENTI);
		req.setRichiedente(richiedente);
		req.setEnte(ente);

		return req;
	}

	public List<File> getElencoFileXbrl() {
		return elencoFileXbrl;
	}

	public void setElencoFileXbrl(List<File> elencoFileXbrl) {
		this.elencoFileXbrl = elencoFileXbrl;
	}

	public Integer[] getSelectedXbrl() {
		return selectedXbrl;
	}

	public void setSelectedXbrl(Integer[] selectedXbrl) {
		this.selectedXbrl = selectedXbrl;
	}

}
