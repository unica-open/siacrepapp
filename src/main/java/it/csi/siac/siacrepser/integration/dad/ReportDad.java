/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.integration.dad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.siac.siaccommonser.integration.dad.base.BaseDadImpl;
import it.csi.siac.siaccorser.model.Ente;
import it.csi.siac.siaccorser.model.file.TipoFile;
import it.csi.siac.siacrepser.integration.entity.SiacTFile;
import it.csi.siac.siacrepser.integration.entity.SiacTReportTemplate;
import it.csi.siac.siacrepser.integration.repository.SiacDFileTipoRepository;
import it.csi.siac.siacrepser.integration.repository.SiacTFileRepository;
import it.csi.siac.siacrepser.integration.repository.SiacTReportTemplateRepository;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class ReportDad extends BaseDadImpl {
	private Ente ente;

	@Autowired(required=false)
	private SiacTReportTemplateRepository siacTReportTemplateRepository;

	@Autowired
	private SiacTFileRepository siacTFileRepository;
	@Autowired
	private SiacDFileTipoRepository siacDFileTipoRepository;

	public SiacTReportTemplate getTemplateInfo(String codice) {
		return siacTReportTemplateRepository.getTemplateInfo(ente.getUid(), codice);
	}

	public Ente getEnte() {
		return ente;
	}

	public void setEnte(Ente ente) {
		this.ente = ente;
	}

	public TipoFile ricercaTipoFile(String codiceTipoFile) {
		return map(siacDFileTipoRepository.ricercaTipoFile(codiceTipoFile, ente.getUid()), TipoFile.class);
	}
	
	public byte[] ricercaContenutoFile(Integer uid) {
		SiacTFile siacTFile = siacTFileRepository.findOne(uid);
		if(siacTFile == null) {
			throw new IllegalArgumentException("Nessun file per uid " + uid  + " presente su base dati");
		}
		return siacTFile.getFile();
	}

}
