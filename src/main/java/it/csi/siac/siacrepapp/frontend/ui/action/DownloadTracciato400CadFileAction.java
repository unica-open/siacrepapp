/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.ui.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import it.csi.siac.siaccommon.util.MimeType;
import it.csi.siac.siaccommon.util.number.NumberUtil;
import it.csi.siac.siaccommonapp.action.GenericAction;
import it.csi.siac.siacrepapp.frontend.ui.model.DownloadTracciato400CadFileModel;
import it.csi.siac.siacrepapp.frontend.ui.model.HomeModel.ImplicitParams;
import it.csi.siac.siacrepser.business.service.Tracciato400CadService;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class DownloadTracciato400CadFileAction extends GenericAction<DownloadTracciato400CadFileModel> {

	private static final long serialVersionUID = -3354044795381630266L;

	@Autowired
	private Tracciato400CadService tracciato400CadService;

	@Override
	public String execute() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Integer idEnte = NumberUtil.safeParseInt(request.getParameter(ImplicitParams.ENTE.getName()));
		Integer annoBilancio = NumberUtil.safeParseInt(request.getParameter(ImplicitParams.ANNO_BILANCIO.getName()));
		String nomeReport = request.getParameter("nome_report");
		Integer annoDelibera = NumberUtil.safeParseInt(request.getParameter("Anno delibera"));
		Integer numeroDelibera = NumberUtil.safeParseInt(request.getParameter("Numero della delibera"));
		String tipoDelibera = request.getParameter("tipo_atto");
		Integer annoCompetenza = NumberUtil.safeParseInt(request.getParameter("Anno Competenza"));
		String elencoVariazioni = request.getParameter("Numero Variazione");
		String organoProvv = request.getParameter("Organo emettitore delibera");			
		
		List<String> datiTracciato400Cad = tracciato400CadService.leggiDatiTracciato400Cad(idEnte, annoBilancio, annoDelibera, numeroDelibera, tipoDelibera, annoCompetenza,
				elencoVariazioni, organoProvv, nomeReport);

		initFileDownload(String.format("Tracciato-400-%s.txt", nomeReport), MimeType.PLAIN_TEXT, StringUtils.join(datiTracciato400Cad, "\n").getBytes("utf-8"));

		return "downloadFile";
	}
}
