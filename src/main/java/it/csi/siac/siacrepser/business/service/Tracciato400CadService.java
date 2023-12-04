/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import it.csi.siac.siacrepser.integration.dao.Tracciato400CadDao;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Tracciato400CadService {
	@Autowired
	private Tracciato400CadDao tracciato400CadDao;
	
	public List<String> leggiDatiTracciato400Cad(Integer idEnte, Integer annoBilancio, Integer annoDelibera, Integer numeroDelibera, String tipoDelibera, 
			Integer annoCompetenza, String elencoVariazioni, String organoProvv, String nomeReport) {
		List<String> tracciato400CadData = new ArrayList<String>();
		
		for (Map<String, Object> map : 
			tracciato400CadDao.readTracciato400CadData(idEnte, annoBilancio, annoDelibera, numeroDelibera, tipoDelibera, annoCompetenza, elencoVariazioni, organoProvv, nomeReport)) {
			tracciato400CadData.add(String.valueOf(map.values().iterator().next()));
		}
		
		return tracciato400CadData;
	}
}
