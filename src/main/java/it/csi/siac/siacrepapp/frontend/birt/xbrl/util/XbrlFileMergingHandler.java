/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.birt.xbrl.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import it.csi.siac.siaccorser.model.file.File;

@Component
public class XbrlFileMergingHandler {
	private static final String TAXONOMY_START_STRING = "<link:schemaRef";

	private static final String CONTEXT_START_STRING = "<xbrli:context";
	private static final String CONTEXT_END_STRING = "</xbrli:context>";

	private static final String UNIT_START_STRING = "<xbrli:unit";
	private static final String UNIT_END_STRING = "</xbrli:unit>";

	private static final String XBRL_END_STRING = "</xbrli:xbrl>";

	private static final String TAG_END_STRING = "/>";

	public String merge(List<File> elencoFileXbrl) throws UnsupportedEncodingException {
		StringBuilder merged = new StringBuilder();

		String xbrl0 = xbrlFileToString(elencoFileXbrl.get(0));

		merged.append(getHeader(xbrl0));

		merged.append(getTaxonomy(xbrl0));

		merged.append(mergeContexts(elencoFileXbrl));

		merged.append(getUnits(xbrl0));

		merged.append(mergeFacts(elencoFileXbrl));

		merged.append(XBRL_END_STRING);

		return merged.toString();
	}

	private String getHeader(String xbrl) {
		return StringUtils.substringBefore(xbrl, TAXONOMY_START_STRING);
	}

	private String getTaxonomy(String xbrl) {
		int i = xbrl.indexOf(TAXONOMY_START_STRING);

		return xbrl.substring(i, xbrl.indexOf(TAG_END_STRING, i) + TAG_END_STRING.length()) + "\n\n";
	}

	private String mergeContexts(List<File> elencoFileXbrl) throws UnsupportedEncodingException {
		Map<String, String> contextMap = new HashMap<String, String>();

		for (File fileXbrl : elencoFileXbrl) {
			
			String xbrl = xbrlFileToString(fileXbrl);
			
			int i = xbrl.indexOf(CONTEXT_START_STRING);

			while (i != -1) {
				String context = xbrl.substring(i, xbrl.indexOf(CONTEXT_END_STRING, i) + CONTEXT_END_STRING.length());
				String contextId = context.substring(context.indexOf("id=") + 4, context.indexOf(">") - 1);

				if (!contextMap.containsKey(contextId))
					contextMap.put(contextId, context);

				i = xbrl.indexOf(CONTEXT_START_STRING, i + CONTEXT_START_STRING.length());
			}
		}

		return StringUtils.join(contextMap.values(), "\n") + "\n\n";
	}

	private String getUnits(String xbrl) {
		StringBuilder s = new StringBuilder();

		int i = xbrl.indexOf(UNIT_START_STRING);

		while (i != -1) {
			s.append(xbrl.substring(i, xbrl.indexOf(UNIT_END_STRING, i) + UNIT_END_STRING.length()));
			i = xbrl.indexOf(UNIT_START_STRING, i + UNIT_START_STRING.length());
		}

		return s.toString();
	}

	private String mergeFacts(List<File> elencoFileXbrl) throws UnsupportedEncodingException {
		StringBuilder s = new StringBuilder();

		for (File fileXbrl : elencoFileXbrl) {
			String xbrl = xbrlFileToString(fileXbrl);
			
			s.append("\n\n")
			 .append(String.format("<!-- inizio xbrl: %s -->", fileXbrl.getCodice()))
			 .append(xbrl.substring(StringUtils.lastIndexOf(xbrl, UNIT_END_STRING) + UNIT_END_STRING.length(),
					xbrl.indexOf(XBRL_END_STRING)))
			 .append(String.format("<!-- fine xbrl: %s -->", fileXbrl.getCodice()))
			 .append("\n\n");
		}

		return s.toString();
	}

	private String xbrlFileToString(File xbrlFile) throws UnsupportedEncodingException {
		return new String(xbrlFile.getContenuto(), "utf-8");
	}
}
