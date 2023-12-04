/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.birt.xbrl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.emitter.xbrl.XbrlEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.csi.siac.siaccorser.frontend.webservice.FileService;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.RicercaTipoFile;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.RicercaTipoFileResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.UploadFile;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.UploadFileResponse;
import it.csi.siac.siaccorser.model.Ente;
import it.csi.siac.siaccorser.model.Richiedente;
import it.csi.siac.siaccorser.model.ServiceResponse;
import it.csi.siac.siaccorser.model.file.File;
import it.csi.siac.siaccorser.model.file.StatoFile.CodiceStatoFile;
import it.csi.siac.siaccorser.model.file.TipoFile;
import it.csi.siac.siaccorser.model.file.TipoFileEnum;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XbrlFileHelper {
	@Autowired private ApplicationContext applicationContext;
	
	@Autowired
	private FileService fileService;

//	//@Autowired
//	protected SessionHandler sessionHandler;
//
//	private SessionHandler getSessionHandler()
//	{
//		if (sessionHandler == null)
//			sessionHandler = applicationContext.getBean("sessionHandlerBean", SessionHandler.class);
//
//		return sessionHandler;
//	}
	
	public void uploadFile(String code, String filename, Map<String, Object> parametersMap, byte[] byteArray, Ente ente, Richiedente richiedente) throws BirtException {
		File file = new File();

		file.setCodice(code);    
		file.setNome(filename);
		file.setNote(parametersToString(parametersMap));
		file.setTipo(ricercaTipoFile(TipoFileEnum.REPORT_XBRL.getCodice(), ente, richiedente));
		file.setMimeType(XbrlEmitter.MIME_TYPE);
		file.setContenuto(byteArray);
		file.setStatoFile(CodiceStatoFile.CARICATO);

		UploadFile uploadFileRequest = new UploadFile();

		uploadFileRequest.setFile(file);
		uploadFileRequest.setRichiedente(richiedente);
		uploadFileRequest.setEnte(ente);

		UploadFileResponse uploadFileResponse = fileService.uploadFile(uploadFileRequest);

		checkFallimento(uploadFileResponse);
	}


	private String parametersToString(Map<String, Object> parametersMap) {
		List<String> tmp = new ArrayList<String>();
		
		for (Entry<String, Object> e : parametersMap.entrySet())
			tmp.add(String.format("%s: %s", e.getKey(), e.getValue()));
		
		return StringUtils.join(tmp, " ,");
	}

	private TipoFile ricercaTipoFile(String codiceTipoFile, Ente ente, Richiedente richiedente) throws BirtException {
		RicercaTipoFile ricercaTipoFile = new RicercaTipoFile();

		ricercaTipoFile.setEnte(ente);
		ricercaTipoFile.setRichiedente(richiedente);
		ricercaTipoFile.setCodice(codiceTipoFile);

		RicercaTipoFileResponse ricercaTipoFileResponse = fileService.ricercaTipoFile(ricercaTipoFile);

		checkFallimento(ricercaTipoFileResponse);

		return ricercaTipoFileResponse.getTipoFile();
	}

//	private Ente getEnte()
//	{
//		return getSessionHandler().getEnte();
//	}
//
//	private Richiedente getRichiedente()
//	{
//		return getSessionHandler().getRichiedente();
//	}

	private void checkFallimento(ServiceResponse response) throws BirtException {
		if (response.isFallimento())
			throw new BirtException(response.getDescrizioneErrori());
	}
}
