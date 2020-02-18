/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.business.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.siac.siaccommonser.business.service.base.BaseService;
import it.csi.siac.siaccommonser.business.service.base.ServiceResponseUtil;
import it.csi.siac.siaccommonser.business.service.base.exception.ServiceParamError;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.UploadFileResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.report.GeneraReport;
import it.csi.siac.siaccorser.frontend.webservice.msg.report.GeneraReportResponse;
import it.csi.siac.siaccorser.model.errore.ErroreCore;
import it.csi.siac.siaccorser.model.file.File;
import it.csi.siac.siaccorser.model.file.StatoFile.CodiceStatoFile;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GeneraReportService extends BaseService<GeneraReport, GeneraReportResponse> {
	@Autowired
	private GeneraReportUtil generaReportUtil;

	@Autowired
	private ServiceResponseUtil serviceResponseUtil;

	@Override
	protected void checkServiceParam() throws ServiceParamError {
		checkNotNull(req.getEnte(), ErroreCore.PARAMETRO_NON_INIZIALIZZATO.getErrore("ente"));
		checkCondition(req.getObjectXml() != null || req.getXmlFileUid() != null,
				ErroreCore.PARAMETRO_NON_INIZIALIZZATO.getErrore("object xml o uid file"));
		checkNotNull(req.getCodiceTemplate(),
				ErroreCore.PARAMETRO_NON_INIZIALIZZATO.getErrore("codice template"));
	}

	@Override
	protected void init() {
		generaReportUtil.setReq(req);
		generaReportUtil.init();
	}

	// Remedy INC000002213744
	@Transactional(timeout=60 * 5)
	@Override
	public GeneraReportResponse executeService(GeneraReport serviceRequest) {
		return super.executeService(serviceRequest);
	}

	@Override
	protected void execute() {
		if(req.getXmlFileUid() == null) {
			generaReportUtil.uploadObjectXml();
		} else {
			generaReportUtil.readObjectXml();
		}

		InputStream templateInputStream = generaReportUtil.getTemplateInputStream();

		Report report = generaReportUtil.createReport(templateInputStream);

		File file = new File();

		file.setCodice(String.format("PDF-%s", req.getCodiceTemplate()));
		file.setNome(String.format("%s.pdf", req.getCodiceTemplate()));
		file.setTipo(generaReportUtil.ricercaTipoFile("REPORT_PDF"));
		file.setMimeType("application/pdf");
		file.setContenuto(report.getContent());
		file.setStatoFile(CodiceStatoFile.CARICATO); 

		UploadFileResponse uploadFileResponse = generaReportUtil.uploadFile(file);

		serviceResponseUtil.checkFallimento(uploadFileResponse);

		res.setReport(uploadFileResponse.getFile());
		res.setNumeroPagineGenerate(report.getPagesNumber());
		
		// SIAC-6110
		if(req.isCleanReportContent()) {
			res.getReport().setContenuto(new byte[0]);
		}

	}

}
