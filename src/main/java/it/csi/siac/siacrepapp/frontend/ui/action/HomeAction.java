/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.ui.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import it.csi.siac.siacrepapp.frontend.ui.model.HomeModel;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class HomeAction extends BaseHomeAction<HomeModel> {

	private static final long serialVersionUID = 1L;
}
