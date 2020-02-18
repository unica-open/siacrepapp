/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.frontend.webservice;

import javax.annotation.PostConstruct;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import it.csi.siac.siaccorser.frontend.webservice.CORSvcDictionary;
import it.csi.siac.siaccorser.frontend.webservice.ReportService;
import it.csi.siac.siaccorser.frontend.webservice.msg.report.GeneraReport;
import it.csi.siac.siaccorser.frontend.webservice.msg.report.GeneraReportResponse;
import it.csi.siac.siacrepser.business.service.GeneraReportAsyncService;
import it.csi.siac.siacrepser.business.service.GeneraReportService;

@WebService(serviceName = "ReportService", 
portName = "ReportServicePort", 
targetNamespace = CORSvcDictionary.NAMESPACE, 
endpointInterface = "it.csi.siac.siaccorser.frontend.webservice.ReportService")
public class ReportServiceImpl implements ReportService {
	@Autowired
	private ApplicationContext appCtx;

	@PostConstruct
	public void init() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	@WebMethod
	public GeneraReportResponse generaReport(@WebParam GeneraReport arg) {
		return appCtx.getBean(GeneraReportService.class).executeService(arg);
	}

	@Override
	@WebMethod
	@Oneway
	public void generaReportAsync(@WebParam GeneraReport arg) {
		appCtx.getBean(GeneraReportAsyncService.class).executeService(arg);   
	}

}
