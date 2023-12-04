/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.ui.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import it.csi.siac.siaccommonapp.action.GenericAction;
import it.csi.siac.siaccorser.frontend.webservice.FileService;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.EliminaFile;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.EliminaFileResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.RicercaFileResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.RicercaTipoFile;
import it.csi.siac.siaccorser.frontend.webservice.msg.file.RicercaTipoFileResponse;
import it.csi.siac.siaccorser.model.errore.ErroreCore;
import it.csi.siac.siaccorser.model.file.File;
import it.csi.siac.siaccorser.model.file.TipoFileEnum;
import it.csi.siac.siacrepapp.frontend.birt.xbrl.util.XbrlFileMergingHandler;
import it.csi.siac.siacrepapp.frontend.ui.model.GestioneXbrlFileModel;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class GestioneXbrlFileAction extends GenericAction<GestioneXbrlFileModel> {
	private static final long serialVersionUID = 6546026061074017809L;

	@Autowired
	private transient FileService fileService;

	@Autowired
	private XbrlFileMergingHandler xbrlFileMergingHandler;

	private Integer fi;

	@Override
	public void prepare() throws Exception {
		final String methodName = "prepare";

		log.debugStart(methodName, "Preparazione della action");

		super.prepare();

		readElencoFileXbrl();

		log.debugEnd(methodName, "");
	}

	private void readElencoFileXbrl() throws Exception {
		RicercaTipoFile ricercaTipoFile = new RicercaTipoFile();

		ricercaTipoFile.setEnte(sessionHandler.getEnte());
		ricercaTipoFile.setRichiedente(sessionHandler.getRichiedente());
		ricercaTipoFile.setCodice(TipoFileEnum.REPORT_XBRL.getCodice());

		RicercaTipoFileResponse ricercaTipoFileResponse = fileService.ricercaTipoFile(ricercaTipoFile);

		if (ricercaTipoFileResponse.isFallimento())
			throw new Exception(ricercaTipoFileResponse.getDescrizioneErrori());

		RicercaFileResponse ricercaFileResponse = fileService.ricercaFile(
				GestioneXbrlFileModel.buildRicercaFileRequest(ricercaTipoFileResponse.getTipoFile().getUid(),
						sessionHandler.getRichiedente(), sessionHandler.getEnte()));

		if (ricercaFileResponse.isFallimento())
			throw new Exception(ricercaFileResponse.getDescrizioneErrori());

		model.setElencoFileXbrl(ricercaFileResponse.getElencoPaginato());
	}

	@Override
	protected void initModel() {
		super.initModel();

		model.setTitolo(sessionHandler.getAzione().getTitolo());
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public void validateUnisci() throws Exception {
		if (model.getSelectedXbrl() == null)
			addErrore(ErroreCore.NESSUN_ELEMENTO_SELEZIONATO.getErrore());
	}

	public String unisci() throws Exception {
		mergeFileXbrl();

		return "downloadFile";
	}

	public String elimina() throws Exception {
		File fileXbrl = model.getElencoFileXbrl().get(fi);

		EliminaFile eliminaFile = new EliminaFile();
		eliminaFile.setUid(fileXbrl.getUid());
		eliminaFile.setRichiedente(sessionHandler.getRichiedente());

		EliminaFileResponse eliminaFileResponse = fileService.eliminaFile(eliminaFile);

		if (eliminaFileResponse.isFallimento()) {
			addErrori(eliminaFileResponse);

			return INPUT;
		}

		return SUCCESS;
	}

	private void mergeFileXbrl() throws Exception {
		
		List<File> xbrlList = new ArrayList<File>();

		for (Integer sel : model.getSelectedXbrl())
			xbrlList.add(model.getElencoFileXbrl().get(sel));
		
		String merged = xbrlFileMergingHandler.merge(xbrlList);

		File xbrlFile0 = xbrlList.get(0);

		initFileDownload(xbrlFile0.getNome(), xbrlFile0.getMimeType(), merged.getBytes("utf-8"));
	}


	public Integer getFi() {
		return fi;
	}

	public void setFi(Integer fi) {
		this.fi = fi;
	}

}
