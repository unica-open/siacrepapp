/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.business.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;

import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;

import it.csi.siac.siacrepser.util.pdf.PdfUtil;

public class ReportEngine {

	public static Report createReport(InputStream template, String xml)
			throws ReportEngineException, IOException, DocumentException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		createReport(template, xml, baos);

		PdfUtil pdfUtil = new PdfUtil();

		Report report = new Report();
		
		report.setContent(pdfUtil.resizePdf(baos.toByteArray(), PageSize.A4));
		
		report.setPagesNumber(pdfUtil.getNumberOfPages(report.getContent()));
		
		return report;
	}

	public static void createReport(InputStream template, String xml, OutputStream outputStream)
			throws ReportEngineException {
		EngineConfig config = new EngineConfig();

		try {
			// FIXME 
			Platform.startup();
		} catch (BirtException be) {
			throw new ReportEngineException("Errore inizializzazione Birt", be);
		}

		IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);

		IReportEngine engine = factory.createReportEngine(config);

		// FIXME
		IReportRunnable design;
		try {
			design = engine.openReportDesign(template);
		} catch (EngineException ee) {
			throw new ReportEngineException("Errore apertura template", ee);
		}

		// ReportDesignHandle report = (ReportDesignHandle)
		// design.getDesignHandle();

		// SlotHandle datasourceSlotHandle = report.getDataSources();
		//
		// OdaDataSourceHandle dataSource = null;

		// if (datasourceSlotHandle.getCount() > 0)
		// {
		// dataSource = (OdaDataSourceHandle) (datasourceSlotHandle.get(0));
		// // dataSource.setStringProperty("FILELIST", dataSourceFileList);
		//
		// dataSource.getStringProperty("FILELIST");
		// }

		IRunAndRenderTask task = engine.createRunAndRenderTask(design);

		PDFRenderOption options = new PDFRenderOption();
		options.setOutputFormat("pdf");
		options.setOutputStream(outputStream);

		task.setRenderOption(options);

		@SuppressWarnings("unchecked")
		Map<String, Object> appContext = task.getAppContext();
		InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
		appContext.put("org.eclipse.datatools.enablement.oda.xml.inputStream", inputStream);
		appContext.put("org.eclipse.datatools.enablement.oda.xml.closeInputStream", "true");

		try {
			task.run();        
		} catch (EngineException ee) {
			throw new ReportEngineException("Errore durante l'elaborazione.", ee);
		}

		task.close(); 
		
		//Platform.shutdown();

	}
	

	
	
	
	

}
