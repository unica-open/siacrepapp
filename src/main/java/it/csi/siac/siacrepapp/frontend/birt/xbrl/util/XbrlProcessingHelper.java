/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.birt.xbrl.util;

import java.math.BigDecimal;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.stereotype.Component;

@Component
public class XbrlProcessingHelper {
	private static final String INNER_EXPR_START = "<[=";
	private static final String INNER_EXPR_END = "=]>";
	
	public String evalFactExpression(String expr) {
		String evaluated = "";

		if (expr.length() > 1) {
			ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
			ScriptEngine jsEngine = scriptEngineManager.getEngineByName("javascript");

			try {
				evaluated = jsEngine.eval(expr).toString();
			} catch (ScriptException e) {
				evaluated = String.format("[Expression error: %s (expr: %s)]", e.getMessage(), expr.substring(1));
			}
		}

		return evaluated;
	}
	
	public String evalFactInnerExpression(String fact) {
		String evaluated = fact;

		while (evaluated.contains(INNER_EXPR_START) && evaluated.contains(INNER_EXPR_END)) {
			int i1 = evaluated.indexOf(INNER_EXPR_START);
			int i2 = evaluated.indexOf(INNER_EXPR_END);

			String before = evaluated.substring(0, i1);
			String inner = evaluated.substring(i1 + INNER_EXPR_START.length(), i2);
			String after = evaluated.substring(i2 + INNER_EXPR_END.length());

			evaluated = before + evalFactExpression(inner) + after;
		}

		return evaluated;
	}
	
	public String evalFactVariables(String fact, Map<String, Object> dataContentMap) {
		StrSubstitutor sub = new StrSubstitutor(dataContentMap);

		return sub.replace(fact);  
	}

	public BigDecimal toBigDecimal(Object value, String decimals) {
		if (value instanceof Double)
			return BigDecimal.valueOf((Double) value).setScale(Integer.parseInt(decimals), BigDecimal.ROUND_HALF_UP);
		
		if (value instanceof BigDecimal)
			return ((BigDecimal) value).setScale(Integer.parseInt(decimals), BigDecimal.ROUND_HALF_UP);
		
		return null;
	}
	
	/**
	 * 
	 * @param periodoCode e.g.: anno/anno_bilancio*-1/
	 * @param dataContentMap 
	 * @return
	 */
	public String decodeContextId(String periodoCode, Map<String, Object> dataContentMap) {
		String result = periodoCode;
		
		while (result.contains("/")) {
			int i1 = result.indexOf("/");
			int i2 = result.indexOf("/", i1 + 1);

			String before = result.substring(0, i1);
			String encoded = result.substring(i1 + 1, i2);
			String after = result.substring(i2 + 1);

			String[] s = encoded.split("\\*");

			result = String.format("%s%d%s", before,
					Integer.parseInt((String) dataContentMap.get(s[0])) + Integer.valueOf(s[1]), after);
		}

		return result;
	}
}
