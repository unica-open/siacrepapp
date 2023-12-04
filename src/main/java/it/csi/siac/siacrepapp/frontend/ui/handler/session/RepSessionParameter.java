/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.ui.handler.session;

import it.csi.siac.siaccommonapp.handler.session.SessionParameter;

public enum RepSessionParameter implements SessionParameter {
	REPORT_FOLDER;

	@Override
	public boolean isEliminabile() {
		return true;
	}

	@Override
	public String getName() {
		return name();
	}

}
