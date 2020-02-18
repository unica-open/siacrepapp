/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.integration.base;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public abstract class RepBaseDao extends NamedParameterJdbcDaoSupport {
	public RepBaseDao(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
}
