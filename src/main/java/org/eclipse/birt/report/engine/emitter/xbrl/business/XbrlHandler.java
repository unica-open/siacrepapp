/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.engine.emitter.xbrl.business;

import java.io.OutputStream;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.content.IReportContent;

public interface XbrlHandler {
	void init(IReportContext reportContext) throws BirtException;

	void process(String dataVar, Object value) throws BirtException;

	void render(IReportContent reportContent, OutputStream outputStream) throws BirtException;
}
