/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.integration.dao;

import java.util.HashMap;
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
public class SiacTPeriodoDao extends RepBaseDao {
	@Autowired
	public SiacTPeriodoDao(DataSource dataSource) {
		super(dataSource);
	}

	public Map<String, Object> readSiacTPeriodo(Integer idEnte, String codicePeriodo) {
		Map<String, Object> par = new HashMap<String, Object>();

		par.put("ente_proprietario_id", idEnte);
		par.put("periodo_code", codicePeriodo);

		try {
			return getNamedParameterJdbcTemplate().queryForMap(
				"SELECT * FROM siac_t_periodo "
				+ " WHERE ente_proprietario_id=:ente_proprietario_id "
				+ " AND periodo_code=:periodo_code" 
				+ " AND data_cancellazione IS NULL " 
				+ " AND validita_inizio<=CURRENT_TIMESTAMP "
				+ " AND (validita_fine IS NULL OR validita_fine>CURRENT_TIMESTAMP) ",
				par);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
