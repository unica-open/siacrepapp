/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.birt.xbrl.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.report.context.ViewerAttributeBean;
import org.eclipse.birt.report.utility.filename.IFilenameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import it.csi.siac.siacrepapp.frontend.ui.model.HomeModel.ImplicitParams;
import it.csi.siac.siacrepser.business.service.XbrlService;

public class XbrlFilenameGenerator implements IFilenameGenerator {
	public static final String REQUEST_ATTR_XBRL_GENERATED_FILENAME = "REQUEST_ATTR_XBRL_GENERATED_FILENAME";

	@Autowired
	private XbrlService xbrlService;

	@Override
	public String getFilename(String baseName, String extension, String outputType,
			@SuppressWarnings("rawtypes") Map options) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		ViewerAttributeBean viewerAttributeBean = (ViewerAttributeBean) options.get(OPTIONS_VIEWER_ATTRIBUTES_BEAN);

		@SuppressWarnings("rawtypes")
		Map parameters = viewerAttributeBean.getParameters();

		Integer idEnte = (Integer) parameters.get(ImplicitParams.ENTE.getName());
		String annoBilancio = (String) parameters.get(ImplicitParams.ANNO_BILANCIO.getName());

		String codiceBdap = xbrlService.readXbrlEnteBdapCodice(idEnte);
		String reportCode = StringUtils.substringBefore((String) options.get(OPTIONS_REPORT_DESIGN), "_");

		Map<String, Object> siacTXbrlReport = xbrlService.readSiacTXbrlReport(idEnte, reportCode);

		String filename = String.format("%s%s%s%s.%s", annoBilancio, codiceBdap,
				siacTXbrlReport.get("xbrl_rep_fase_code"), siacTXbrlReport.get("xbrl_rep_tipologia_code"), extension);

		HttpServletRequest httpServletRequest = (HttpServletRequest) options.get(OPTIONS_HTTP_REQUEST);

		httpServletRequest.setAttribute(REQUEST_ATTR_XBRL_GENERATED_FILENAME, filename);

		return filename;
	}
}
