/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.util.config;

import java.util.Properties;

import it.csi.siac.siaccommon.util.log.LogUtil;

public class ReportJdbcDataSourceConfig {
	private static LogUtil log = new LogUtil(ReportJdbcDataSourceConfig.class);

	private static volatile Properties config = null;

	private static String getProperty(String key) {
		Properties result = config;
		if (result == null) {
			synchronized(ReportJdbcDataSourceConfig.class) {
				result = config;
				if(result == null) {
					result = new Properties();
					config = result;

					try {
						result.load(Thread.currentThread().getContextClassLoader()
								.getResourceAsStream("reportJdbcDataSourceConfig.properties"));
					} catch (Exception e) {
						log.error("getProperty", e.getMessage(), e);
					}
				}
			}
		}
		return result.getProperty(key);
	}

	/**
	 * Ottiene l'URL per la connessione ODA
	 * @deprecated utilizzare l'aggancio via JNDI
	 * @return l'URL
	 */
	@Deprecated
	public static String getOdaUrl() {
		return getProperty("report.datasource.oda.url");
	}

	/**
	 * Ottiene lo username per la connessione ODA
	 * @deprecated utilizzare l'aggancio via JNDI
	 * @return l'username
	 */
	@Deprecated
	public static String getOdaUser() {
		return getProperty("report.datasource.oda.user");
	}

	/**
	 * Ottiene la passwored per la connessione ODA
	 * @deprecated utilizzare l'aggancio via JNDI
	 * @return la password
	 */
	@Deprecated
	public static String getOdaPassword() {
		return getProperty("report.datasource.oda.password");
	}
	
	public static String getOdaJndiName() {
		return getProperty("report.datasource.oda.jndiname");
	}
}
