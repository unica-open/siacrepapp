/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.ui.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.csi.siac.siaccommonapp.model.GenericModel;

public class HomeModel extends GenericModel {

	private static final long serialVersionUID = -2329998296960769722L;

	private List<Report> elencoReport = new ArrayList<Report>();
	private List<String> queryStringParams = new ArrayList<String>();;

	public static enum ImplicitParams {
		ENTE("ente_proprietario"), 
		ANNO_BILANCIO("anno_bilancio"), 
		ACCOUNT("account"),
		;

		private String name;

		private ImplicitParams(String name) {
			setName(name);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	public List<Report> getElencoReport() {
		return elencoReport;
	}

	public void setElencoReport(List<Report> elencoReport) {
		this.elencoReport = elencoReport;
	}

	public String getQueryStringParams() {
		return StringUtils.join(queryStringParams, "&");
	}


	public void addParamToQueryString(ImplicitParams param, String value) throws UnsupportedEncodingException {
		addParamToQueryString(param.getName(), value);
	}

	public void addParamToQueryString(String param, String value) throws UnsupportedEncodingException {
		queryStringParams.add(String.format("%s=%s", param, URLEncoder.encode(StringUtils.defaultString(value), "UTF-8")));
	}

	
	////////////////
	
	public static class Report {
		private String name;
		private String fileName;
		private boolean allowPdf = true;
		private boolean allowXls = true;
		private boolean allowXbrl = false;

		public Report(String fileName) {
			this.fileName = fileName;
		}

		public Report(String fileLine, String separator) {
			try {
				String[] tmp = StringUtils.substringAfter(fileLine, "=").split(separator);

				name = tmp[0];
				allowPdf = Boolean.valueOf(tmp[1]);
				allowXls = Boolean.valueOf(tmp[2]);
				setAllowXbrl(Boolean.valueOf(tmp[3]));
			}
			catch (Exception e) {
				// Ignoro l'eccezione
			}
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public boolean isAllowPdf() {
			return allowPdf;
		}

		public void setAllowPdf(boolean allowPdf) {
			this.allowPdf = allowPdf;
		}

		public boolean isAllowXls() {
			return allowXls;
		}

		public void setAllowXls(boolean allowXls) {
			this.allowXls = allowXls;
		}

		public boolean isAllowXbrl() {
			return allowXbrl;
		}

		public void setAllowXbrl(boolean allowXbrl) {
			this.allowXbrl = allowXbrl;
		}
	}
}
