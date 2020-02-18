/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
/*************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *  
 *************************************************************************/
package org.eclipse.birt.data.engine.executor;

import java.util.Map;

import org.eclipse.birt.data.engine.core.DataException;
import org.eclipse.birt.data.engine.impl.DataEngineSession;
import org.eclipse.birt.data.engine.odi.IDataSource;
import org.eclipse.birt.data.engine.odi.IDataSourceFactory;
import org.eclipse.birt.report.data.oda.jdbc.Connection;

import it.csi.siac.siacrepapp.util.config.ReportJdbcDataSourceConfig;

/**
 * 
 */
public class DataSourceFactory implements IDataSourceFactory {
	/**
	 * volatile modifier is used here to ensure the DataSourceFactory, when
	 * being constructed by JVM, will be locked by current thread until the
	 * finish of construction.
	 */
	private static volatile DataSourceFactory instance = null;

	/**
	 * @return
	 */
	public static IDataSourceFactory getFactory() {
		if (instance == null) {
			synchronized (DataSourceFactory.class) {
				if (instance == null)
					instance = new DataSourceFactory();
			}
		}

		return instance;
	}

	/**
	 *
	 */
	private DataSourceFactory() {
	}

	/*
	 * @see
	 * org.eclipse.birt.data.engine.odi.IDataSourceFactory#getNullDataSource()
	 */
	@Override
	public IDataSource getEmptyDataSource(DataEngineSession session) {
		// TODO: connection pooling
		return new DataSource(null, null, session);
	}

	/*
	 * @see
	 * org.eclipse.birt.data.engine.odi.IDataSourceFactory#getDataSource(java
	 * .lang.String, java.util.Map,
	 * org.eclipse.birt.data.engine.api.IBaseDataSourceDesign,
	 * org.eclipse.birt.data.engine.api.IBaseDataSetDesign,
	 * java.util.Collection, int, int)
	 */
	@Override
	public IDataSource getDataSource(String driverName, Map connProperties,
			DataEngineSession session) throws DataException {
//		Per usare il Datasource definito con Spring, invece di url, user e password:
//			- iniettare il Datasource con @Autowire private DataSource datasource;
//			- aggiungere a connProperties queste due entry:
//				connProperties.put("OdaJDBCDriverPassInConnection", datasource.getConnection());
//				connProperties.put("OdaJDBCDriverPassInConnectionCloseAfterUse", false);
//			- eliminare da connProperties le tre entry:
//				connProperties.remove(Connection.Constants.ODAURL);
//				connProperties.remove(Connection.Constants.ODAUser);
//				connProperties.remove(Connection.Constants.ODAPassword);
		
		if ("org.eclipse.birt.report.data.oda.jdbc".equals(driverName)) {
//			connProperties.put(Connection.Constants.ODAURL, ReportJdbcDataSourceConfig.getOdaUrl());
//			connProperties.put(Connection.Constants.ODAUser, ReportJdbcDataSourceConfig.getOdaUser());
//			connProperties.put(Connection.Constants.ODAPassword, ReportJdbcDataSourceConfig.getOdaPassword());
			
			// Aggancio direttamente via JNDI per evitare problemi dovuti a user/pwd/URL errati o modificati
			connProperties.put(Connection.Constants.ODAJndiName, ReportJdbcDataSourceConfig.getOdaJndiName());
		}

		return new DataSource(driverName, connProperties, session);
	}

}
