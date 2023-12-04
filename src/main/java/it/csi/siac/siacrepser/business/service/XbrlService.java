/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import it.csi.siac.siacrepser.integration.dao.SiacTPeriodoDao;
import it.csi.siac.siacrepser.integration.dao.SiacTXbrlDao;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XbrlService {
	@Autowired
	private SiacTXbrlDao siacTXbrlDao;

	@Autowired
	private SiacTPeriodoDao siacTPeriodoDao;

	public Map<String, Object> readSiacTXbrlReport(Integer idEnte, String reportCode) {
		return siacTXbrlDao.readSiacTXbrlReport(idEnte, reportCode);
	}

	public List<Map<String, Object>> readSiacTXbrlMappingFatti(Integer idEnte, String reportCode) {
		return siacTXbrlDao.readSiacTXbrlMappingFatti(idEnte, reportCode);
	}

	public Map<String, Object> readSiacTPeriodo(Integer idEnte, String contextId) {
		return siacTPeriodoDao.readSiacTPeriodo(idEnte, contextId);
	}

	public String readXbrlEnteBdapCodice(Integer idEnte) {
		return siacTXbrlDao.readXbrlEnteBdapCodice(idEnte);
	}
}
