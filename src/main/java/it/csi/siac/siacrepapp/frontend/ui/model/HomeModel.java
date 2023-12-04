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

import it.csi.siac.siaccommon.util.CoreUtil;
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
	
	public static class Report {
		private String name;
		private String fileName;
		private boolean allowPdf = true;
		private boolean allowXls = true;
		private boolean allowXbrl = false;
		private boolean allowCad = false;

		public Report(String fileName) {
			this.fileName = fileName;
		}

		public Report(String fileLine, String separator) {
			try {
				String[] tmp = StringUtils.substringAfter(fileLine, "=").split(separator);

				name = getString(tmp, 0);
				allowPdf = getBoolean(tmp, 1, true);
				allowXls = getBoolean(tmp, 2, true);
				allowXbrl = getBoolean(tmp, 3, false);
				allowCad = getBoolean(tmp, 4, false);
			}
			catch (Exception e) {}

		}

		public boolean isAllowCad() {
			return allowCad;
		}

		public void setAllowCad(boolean allowCad) {
			this.allowCad = allowCad;
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
				
		private String getString(String[] tmp, int i) {
			return CoreUtil.arrayGet(tmp, i);
		}

		private Boolean getBoolean(String[] tmp, int i, boolean defaultValue) {
			String s = getString(tmp, i);
			return s == null ? Boolean.valueOf(defaultValue) : Boolean.valueOf(s);
		}


	}
}
