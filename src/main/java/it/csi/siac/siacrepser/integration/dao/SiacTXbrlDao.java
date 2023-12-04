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
public class SiacTXbrlDao extends RepBaseDao {
	@Autowired
	public SiacTXbrlDao(DataSource dataSource) {
		super(dataSource);
	}

	public Map<String, Object> readSiacTXbrlReport(Integer idEnte, String reportCode) {
		Map<String, Object> par = new HashMap<String, Object>();

		par.put("ente_proprietario_id", idEnte);
		par.put("xbrl_rep_codice", reportCode);

		try {
			return getNamedParameterJdbcTemplate().queryForMap(
					"SELECT * FROM siac_t_xbrl_report "
					+ " WHERE ente_proprietario_id=:ente_proprietario_id "
					+ " AND xbrl_rep_codice=:xbrl_rep_codice "
					+ " AND data_cancellazione IS NULL " 
					+ " AND validita_inizio<=CURRENT_TIMESTAMP "
					+ " AND (validita_fine IS NULL OR validita_fine>CURRENT_TIMESTAMP) ",
					par);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public String readXbrlEnteBdapCodice(Integer idEnte) {
		Map<String, Object> par = new HashMap<String, Object>();

		par.put("ente_proprietario_id", idEnte);

		try {
			return getNamedParameterJdbcTemplate().queryForObject(
					"SELECT xbrl_ente_bdap_codice FROM siac_t_xbrl_ente "
					+ " WHERE ente_proprietario_id=:ente_proprietario_id "
					+ " AND data_cancellazione IS NULL " 
					+ " AND validita_inizio<=CURRENT_TIMESTAMP "
					+ " AND (validita_fine IS NULL OR validita_fine>CURRENT_TIMESTAMP) ",
					par, String.class);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Map<String, Object>> readSiacTXbrlMappingFatti(Integer idEnte, String reportCode) {		
		Map<String, Object> par = new HashMap<String, Object>();

		par.put("ente_proprietario_id", idEnte);
		par.put("xbrl_mapfat_rep_codice", reportCode);

		try {
			return getNamedParameterJdbcTemplate().queryForList(
					"SELECT * FROM siac_t_xbrl_mapping_fatti "
					+ " WHERE ente_proprietario_id=:ente_proprietario_id "
					+ " AND xbrl_mapfat_rep_codice=:xbrl_mapfat_rep_codice "
					+ " AND data_cancellazione IS NULL " 
					+ " AND validita_inizio<=CURRENT_TIMESTAMP "
					+ " AND (validita_fine IS NULL OR validita_fine>CURRENT_TIMESTAMP) ",
					par);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
