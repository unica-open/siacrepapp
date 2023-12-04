/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.business.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.lowagie.text.DocumentException;

import it.csi.siac.siaccommon.util.MimeType;
import it.csi.siac.siaccommonser.business.service.base.ServiceResponseUtil;
import it.csi.siac.siaccorser.frontend.webservice.FileService;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.UploadFile;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.UploadFileResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.report.GeneraReport;
import it.csi.siac.siaccorser.model.file.File;
import it.csi.siac.siaccorser.model.file.StatoFile.CodiceStatoFile;
import it.csi.siac.siaccorser.model.file.TipoFile;
import it.csi.siac.siaccorser.model.file.TipoFileEnum;
import it.csi.siac.siacrepser.integration.dad.ReportDad;
import it.csi.siac.siacrepser.integration.entity.SiacTReportTemplate;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GeneraReportUtil {
	private GeneraReport req;

	@Autowired
	private FileService fileService;

	@Autowired
	private ReportDad reportDad;

	@Autowired
	private ServiceResponseUtil serviceResponseUtil;

	@Value("${birtWorkingFolder}")
	private String birtWorkingFolder;

	public UploadFileResponse uploadObjectXml() {
		File file = new File();
		// FIXME
		file.setCodice(String.format("XML-%s", req.getCodiceTemplate()));
		file.setNome(String.format("%s.xml", req.getCodiceTemplate()));
		file.setTipo(ricercaTipoFile(TipoFileEnum.REPORT_XML.getCodice()));
		file.setMimeType(MimeType.XML);
		file.setContenuto(req.getObjectXml().getBytes());
		file.setStatoFile(CodiceStatoFile.CARICATO);

		UploadFileResponse uploadFileResponse = uploadFile(file);

		serviceResponseUtil.checkFallimento(uploadFileResponse);

		return uploadFileResponse;
	}
	
	public void readObjectXml() {
		byte[] contenuto = reportDad.ricercaContenutoFile(req.getXmlFileUid());
		String objectXml = new String(contenuto);
		req.setObjectXml(objectXml);
	}

	public UploadFileResponse uploadFile(File file) {
		UploadFile uploadFileRequest = new UploadFile();
		uploadFileRequest.setFile(file);

		uploadFileRequest.setRichiedente(req.getRichiedente());
		uploadFileRequest.setEnte(req.getEnte());
		UploadFileResponse uploadFileResponse = fileService.uploadFile(uploadFileRequest);

		serviceResponseUtil.checkFallimento(uploadFileResponse);

		return uploadFileResponse;
	}

	public Report createReport(InputStream templateInputStream) {

		Report report;
		try {
			report = ReportEngine.createReport(templateInputStream, req.getObjectXml());

		} catch (ReportEngineException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}

		return report;
	}

	public InputStream getTemplateInputStream() {
		SiacTReportTemplate template = reportDad.getTemplateInfo(req.getCodiceTemplate());

		if (template == null) {
			throw new RuntimeException("Impossibile trovare il template " + req.getCodiceTemplate()
					+ " per l'ente " + req.getEnte().getUid());
		}

		try {
			FileInputStream templateInputStream = new FileInputStream(String.format(
					"%s/%s/%s.rptdesign", birtWorkingFolder, template.getPath(),
					template.getNomeFile()));

			return templateInputStream;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public GeneraReport getReq() {
		return req;
	}

	public void setReq(GeneraReport req) {
		this.req = req;
	}

	public void init() {
		reportDad.setEnte(req.getEnte());

	}

	public TipoFile ricercaTipoFile(String codiceTipoFile) {
		return reportDad.ricercaTipoFile(codiceTipoFile);
	}

}
