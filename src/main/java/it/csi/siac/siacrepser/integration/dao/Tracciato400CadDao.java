/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.integration.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import it.csi.siac.siacrepser.integration.base.RepBaseDao;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Tracciato400CadDao extends RepBaseDao {
	@Autowired
	public Tracciato400CadDao(DataSource dataSource) {
		super(dataSource);
	}

	public List<Map<String, Object>> readTracciato400CadData(Integer idEnte, Integer annoBilancio, Integer annoDelibera, Integer numeroDelibera, String tipoDelibera, 
			Integer annoCompetenza, String elencoVariazioni, String organoProvv, String nomeReport) {

		Map<String, Object> par = new HashMap<String, Object>();

		par.put("idEnte", idEnte);
		par.put("annoBilancio", annoBilancio);
		par.put("numeroDelibera", numeroDelibera);
		par.put("annoDelibera", annoDelibera);
		par.put("tipoDelibera", tipoDelibera);
		par.put("annoCompetenza", annoCompetenza);
		par.put("elencoVariazioni", elencoVariazioni);
		par.put("organoProvv", organoProvv);
		par.put("nomeReport", nomeReport);

		try {
			return getNamedParameterJdbcTemplate()
					.queryForList("SELECT * FROM fnc_bilr_tracciato_400_cad(:idEnte::integer, :annoBilancio::varchar, :numeroDelibera::integer, :annoDelibera::varchar, :tipoDelibera::varchar, :annoCompetenza::varchar, :elencoVariazioni::varchar, :organoProvv::varchar, :nomeReport::varchar)",
									par);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
