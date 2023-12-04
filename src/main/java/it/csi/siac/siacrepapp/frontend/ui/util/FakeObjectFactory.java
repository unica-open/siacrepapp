/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.ui.util;

import java.util.ArrayList;
import java.util.List;

import it.csi.siac.siaccorser.model.Azione;
import it.csi.siac.siaccorser.model.AzioneRichiesta;
import it.csi.siac.siaccorser.model.Ente;
import it.csi.siac.siaccorser.model.ParametroAzioneRichiesta;
import it.csi.siac.siaccorser.model.Richiedente;

public class FakeObjectFactory {

	private static int seq = 0;

	private static int getSequenceNextVal() {
		return ++seq;
	}

	public static Ente getEnte() {

		Ente ente = new Ente();

		ente.setUid(1);

		return ente;
	}

	public static AzioneRichiesta getAzioneRichiesta() {

		AzioneRichiesta ar = new AzioneRichiesta();

		List<ParametroAzioneRichiesta> parametri = new ArrayList<ParametroAzioneRichiesta>();

		ParametroAzioneRichiesta p = new ParametroAzioneRichiesta();

		p.setNome("annoEsercizio");
		p.setValore("2012");

		parametri.add(p);

		ar.setParametri(parametri);

		ar.setAzione(new Azione());

		return ar;
	}

	public static Richiedente getRichiedente() {
		Richiedente r = new Richiedente();

		return r;
	}

}
