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

import it.csi.siac.siaccommon.util.MimeType;
import it.csi.siac.siaccommonser.business.service.base.BaseServiceOneWay;
import it.csi.siac.siaccommonser.business.service.base.ServiceResponseUtil;
import it.csi.siac.siaccommonser.business.service.base.exception.ServiceParamError;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.UploadFileResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.report.GeneraReport;
import it.csi.siac.siaccorser.model.errore.ErroreCore;
import it.csi.siac.siaccorser.model.file.File;
import it.csi.siac.siaccorser.model.file.StatoFile.CodiceStatoFile;
import it.csi.siac.siaccorser.model.file.TipoFileEnum;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GeneraReportAsyncService extends BaseServiceOneWay<GeneraReport> {
	@Autowired
	private ServiceResponseUtil serviceResponseUtil;

	@Autowired
	private GeneraReportUtil generaReportUtil;


	@Override
	protected void checkServiceParam() throws ServiceParamError {
		checkNotNull(req.getEnte(), ErroreCore.PARAMETRO_NON_INIZIALIZZATO.getErrore("ente"));
		checkNotNull(req.getObjectXml(),
				ErroreCore.PARAMETRO_NON_INIZIALIZZATO.getErrore("object xml"));
		checkNotNull(req.getCodiceTemplate(),
				ErroreCore.PARAMETRO_NON_INIZIALIZZATO.getErrore("codice template"));
	}

	@Override
	protected void init() {
		generaReportUtil.setReq(req);
		generaReportUtil.init();
	}

	@Transactional
	@Override
	public void executeService(GeneraReport serviceRequest) {
		super.executeService(serviceRequest);
	}

	@Override
	protected void execute() {
		UploadFileResponse uploadFileResponse = generaReportUtil.uploadObjectXml();
		checkServiceResponseFallimento(uploadFileResponse);
		
		InputStream templateInputStream = generaReportUtil.getTemplateInputStream();
		
		Report report = generaReportUtil.createReport(templateInputStream);

		uploadReportFile(report.getContent());
	}

	

	private void uploadReportFile(byte[] content) {
		
		File file = new File();
		// FIXME
		file.setCodice("BOH???");
		file.setNome("BOH???");
		file.setTipo(generaReportUtil.ricercaTipoFile(TipoFileEnum.REPORT_PDF.getCodice()));
		file.setMimeType(MimeType.PDF);
		file.setContenuto(content);
		file.setStatoFile(CodiceStatoFile.CARICATO); // TODO

		UploadFileResponse uploadFileResponse = generaReportUtil.uploadFile(file);
		serviceResponseUtil.checkFallimento(uploadFileResponse);

		inserisciDettaglioOperazioneAsinc("codice", // FIXME
				String.format("inserito file id %d", uploadFileResponse.getFile().getUid())
		, uploadFileResponse.getEsito() );
	}

}
