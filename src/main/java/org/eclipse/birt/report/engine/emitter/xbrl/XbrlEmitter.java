/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.engine.emitter.xbrl;

import java.io.OutputStream;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.content.IDataContent;
import org.eclipse.birt.report.engine.content.IReportContent;
import org.eclipse.birt.report.engine.emitter.ContentEmitterAdapter;
import org.eclipse.birt.report.engine.emitter.IEmitterServices;
import org.eclipse.birt.report.engine.emitter.xbrl.business.XbrlHandler;
import org.eclipse.birt.report.engine.ir.DataItemDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class XbrlEmitter extends ContentEmitterAdapter {
	public static final String MIME_TYPE = "application/xml";

	private OutputStream outputStream;

	@Autowired
	private XbrlHandler xbrlHandler;

	@Override
	public void initialize(IEmitterServices services) throws BirtException {
		//SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, ((javax.servlet.http.HttpServletRequest)services.getReportContext().getHttpServletRequest()).getServletContext());

		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		outputStream = services.getRenderOption().getOutputStream();

		xbrlHandler.init(services.getReportContext());   
	}

	@Override
	public void startData(IDataContent dataContent) throws BirtException {
		Object generateBy = dataContent.getGenerateBy();

		if (generateBy instanceof DataItemDesign) {
			String bindingColumn = ((DataItemDesign) generateBy).getBindingColumn();

			Object value = dataContent.getValue();

			xbrlHandler.process(bindingColumn, value);
		}
	}

	@Override
	public void end(IReportContent reportContent) throws BirtException {
		xbrlHandler.render(reportContent, outputStream);
	}

	@Override
	public String getOutputFormat() {
		return "xbrl";
	}
	
	
	
}
