/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.ui.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import it.csi.siac.siaccommonapp.action.AzioneRichiestaAction;
import it.csi.siac.siacrepapp.frontend.ui.handler.session.RepSessionParameter;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class RepAzioneRichiestaAction extends AzioneRichiestaAction {

	private static final long serialVersionUID = -9058844170907395425L;

	@Override
	public String execute() throws Exception {
		String res = super.execute();

		sessionHandler.setParametro(RepSessionParameter.REPORT_FOLDER, res);

		this.log.info("RepAzioneRichiestaAction::execute",
				"REPORT_FOLDER: " + sessionHandler.getParametro(RepSessionParameter.REPORT_FOLDER));

		return "azioneRichiestaResult";
	}
}