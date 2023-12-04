/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.engine.emitter.xbrl.business;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.output.TeeOutputStream;
import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.content.IReportContent;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Fact;
import org.eclipse.birt.report.engine.emitter.xbrl.util.FreemarkerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import it.csi.siac.siaccommonapp.util.log.LogWebUtil;

public abstract class BaseXbrlHandler<F extends Fact> implements XbrlHandler {
	protected Map<String, Object> parametersMap;
	protected Map<String, Object> dataContentMap;
	protected IReportContext reportContext;
	
	protected XbrlTypeHandler<F> xbrlTypeHandler;
	
	protected transient LogWebUtil log = new LogWebUtil(this.getClass());

	@Autowired
	protected ApplicationContext appCtx;

	@Override
	public void init(IReportContext reportContext) throws BirtException  {
		dataContentMap = new HashMap<String, Object>();  
		
		this.reportContext = reportContext;		
				
        readDisplayParameter(reportContext.getRenderOption().getOptions());
		
		init();
	}

	/**
	 * Initializzatore
	 * @throws BirtException in caso di eccezione
	 */
	protected void init() throws BirtException {
		// Empty
	}

	@Override
	public void process(String dataVar, Object value) throws BirtException {
		dataContentMap.put(dataVar, value);

		process(dataVar);
	}

	protected abstract void process(String dataVar) throws BirtException;
	
	@Override
	public void render(IReportContent reportContent, OutputStream outputStream) throws BirtException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		TeeOutputStream tos = null;
		try {
			tos = new TeeOutputStream(outputStream, baos);
			freemarkerRender(tos);
			handleReportByteArrayOutputStream(baos);
		} finally {
			if(tos != null) {
				try {
					tos.close();
				} catch (IOException e) {
					// ignora l'eccezione
				}
			}
		}
		
	}

	/**
	 * @param baos
	 * @throws BirtException
	 */
	protected void handleReportByteArrayOutputStream(ByteArrayOutputStream baos) throws BirtException {
		// Empty
	}

	private void freemarkerRender(OutputStream outputStream) throws BirtException {
		try {
			FreemarkerHandler fh = new FreemarkerHandler(String.format("xbrl/%s.ftl", xbrlTypeHandler.getCode()));

			fh.process(buildXbrlRenderDataMap(), outputStream); 
		} catch (Exception e) {
			throw new BirtException(e.getMessage());
		}
	}

	protected Map<String, Object> buildXbrlRenderDataMap() {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getReportContextParameter(String param) {
		return (T) reportContext.getParameterValue(param);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getRenderOption(String name) {
		return (T) reportContext.getRenderOption().getOption(name);
	}
	
	private void readDisplayParameter(Map<?, ?> options) {
		parametersMap = new LinkedHashMap<String, Object>();
				
		for (Object e : options.entrySet()) {
			@SuppressWarnings("unchecked")
			Entry<Object, Object> entry = (Entry<Object, Object>) e;
			if (entry.getKey().toString().startsWith("isdisplay__"))
				parametersMap.put(StringUtils.substringAfter(entry.getKey().toString(), "isdisplay__"), entry.getValue());
		}
		
		dataContentMap.putAll(parametersMap);
	}

}

