/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.engine.emitter.xbrl.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerHandler {
	private Configuration cfg;
	private Template template;

	public FreemarkerHandler(String resourceTemplate) throws IOException  {
		cfg = new Configuration();
		cfg.setClassForTemplateLoading(this.getClass(), "/");

		template = cfg.getTemplate(resourceTemplate);
	}

	public void process(Map<String, Object> data, Writer out) throws TemplateException, IOException  {
		template.process(data, out);
		out.flush();
	}

	public void process(Map<String, Object> data, OutputStream os) throws Exception {
		process(data, new OutputStreamWriter(os));
	}

	public String process(Map<String, Object> data) throws Exception {
		StringWriter sw = new StringWriter();
		
		process(data, sw);
		
		return sw.toString();
	}
}
