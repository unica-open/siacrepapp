/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.service.actionhandler;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.eclipse.birt.report.context.IContext;
import org.eclipse.birt.report.context.ViewerAttributeBean;
import org.eclipse.birt.report.service.BirtReportServiceFactory;
import org.eclipse.birt.report.service.api.IViewerReportService;
import org.eclipse.birt.report.service.api.InputOptions;
import org.eclipse.birt.report.service.api.ReportServiceException;
import org.eclipse.birt.report.soapengine.api.GetUpdatedObjectsResponse;
import org.eclipse.birt.report.soapengine.api.Operation;
import org.eclipse.birt.report.utility.ParameterAccessor;

import com.lowagie.text.PageSize;

import it.csi.siac.siacrepser.util.pdf.PdfUtil;

public class BirtRenderReportActionHandler extends AbstractBaseActionHandler {

	/**
	 * Output stream to store the report.
	 */
	private OutputStream os = null;

	/**
	 * Constructor.
	 * 
	 * @param context
	 * @param operation
	 */
	public BirtRenderReportActionHandler(IContext context, Operation operation,
			GetUpdatedObjectsResponse response, OutputStream os) {
		super(context, operation, response);
		assert os != null;
		this.os = os;
	}

	/**
	 * Local execution.
	 * 
	 * @exception ReportServiceException
	 * @return
	 */
	@Override
	public void __execute() throws Exception {
		ViewerAttributeBean attrBean = (ViewerAttributeBean) context.getBean();
		assert attrBean != null;

		String docName = attrBean.getReportDocumentName();

		InputOptions options = createInputOptions(attrBean,
				ParameterAccessor.getSVGFlag(context.getRequest()));

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		getReportService().renderReport(docName, attrBean.getReportPage(),
				attrBean.getReportPageRange(), options, baos);

		byte[] b = baos.toByteArray();

		if ("pdf".equals(attrBean.getFormat()))
			b = new PdfUtil().resizePdf(b, PageSize.A4);
		
		os.write(b);
	}

	@Override
	protected IViewerReportService getReportService() {
		return BirtReportServiceFactory.getReportService();
	}

}
