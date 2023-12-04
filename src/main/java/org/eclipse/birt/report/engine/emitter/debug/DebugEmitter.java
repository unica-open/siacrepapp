/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.engine.emitter.debug;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.xerces.impl.dv.util.Base64;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.content.IAutoTextContent;
import org.eclipse.birt.report.engine.content.IBandContent;
import org.eclipse.birt.report.engine.content.ICellContent;
import org.eclipse.birt.report.engine.content.IContainerContent;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IDataContent;
import org.eclipse.birt.report.engine.content.IForeignContent;
import org.eclipse.birt.report.engine.content.IGroupContent;
import org.eclipse.birt.report.engine.content.IImageContent;
import org.eclipse.birt.report.engine.content.ILabelContent;
import org.eclipse.birt.report.engine.content.IListBandContent;
import org.eclipse.birt.report.engine.content.IListContent;
import org.eclipse.birt.report.engine.content.IListGroupContent;
import org.eclipse.birt.report.engine.content.IPageContent;
import org.eclipse.birt.report.engine.content.IReportContent;
import org.eclipse.birt.report.engine.content.IRowContent;
import org.eclipse.birt.report.engine.content.ITableBandContent;
import org.eclipse.birt.report.engine.content.ITableContent;
import org.eclipse.birt.report.engine.content.ITableGroupContent;
import org.eclipse.birt.report.engine.content.ITextContent;
import org.eclipse.birt.report.engine.emitter.ContentEmitterAdapter;
import org.eclipse.birt.report.engine.emitter.IEmitterServices;
import org.eclipse.birt.report.engine.ir.CellDesign;
import org.eclipse.birt.report.engine.ir.DataItemDesign;
import org.eclipse.birt.report.engine.ir.ReportItemDesign;

public class DebugEmitter extends ContentEmitterAdapter {
	public static final String MIME_TYPE = "text/xml";

	private StringBuilder stringBuilder;
	private OutputStream outputStream;
	
	protected Map<String, Object> dataContentMap = new HashMap<String, Object>();

	private void appendAttribute(IContent content) {
		if (content.getBookmark() != null) {
			appendAttribute("id", content.getBookmark());
		}
		
		if (content.getName() != null) {
			appendAttribute("name", content.getName());
		}
	}
	
	/**
	 * A utility method that will append a XML attribute
	 * to the stringbuilder
	 * @param key
	 * @param value
	 */
	private void appendAttribute(String key, String value) {
		stringBuilder.append(" ");
		stringBuilder.append(key);
		stringBuilder.append("=\"");
		stringBuilder.append(value);
		stringBuilder.append("\"");
	}
	
	/**
	 * Given a initialized output stream
	 * and a populated stringbuilder
	 * when end is called
	 * then stringbuilder should be written to the outputstream.
	 */
	@Override
	public void end(IReportContent reportContent) throws BirtException {
		stringBuilder.append("</report>");
		
		stringBuilder.append("\n\n");

		stringBuilder.append("<data-set-variables>\n");
		stringBuilder.append(dataContentMapToString());
		stringBuilder.append("</data-set-variables>\n\n");

		stringBuilder.append("</debug-info>\n");

		try {
			outputStream.write(stringBuilder.toString().getBytes());
			outputStream.close();
		} catch (IOException e) {
			throw new BirtException(e.getMessage());
		}
	}

	private String dataContentMapToString() {
		StringBuilder sb = new StringBuilder();
		
		for (String k : dataContentMap.keySet())
			sb.append(String.format("\t%s\n", k));
		
		return sb.toString();
	}

	@Override
	public void endCell(ICellContent cellContent) throws BirtException {
		stringBuilder.append("</cell>");
	}

	@Override
	public void endContainer(IContainerContent containerContent) throws BirtException {
		stringBuilder.append("</container>");
	}

	@Override
	public void endContent(IContent content) throws BirtException {
		stringBuilder.append("</content>");
	}

	@Override
	public void endGroup(IGroupContent groupContent) throws BirtException {
		stringBuilder.append("</group>");
	}

	@Override
	public void endList(IListContent listContent) throws BirtException {
		stringBuilder.append("</list>");
	}

	@Override
	public void endListBand(IListBandContent listBandContent) throws BirtException {
		stringBuilder.append("</list-band>");
	}

	@Override
	public void endListGroup(IListGroupContent listGroupContent) throws BirtException {
		stringBuilder.append("</list-group>");
	}

	@Override
	public void endPage(IPageContent pageContent) throws BirtException {
		stringBuilder.append("</page>");
	}

	@Override
	public void endRow(IRowContent rowContent) throws BirtException {
		stringBuilder.append("</row>");
	}

	@Override
	public void endTable(ITableContent tableContent) throws BirtException {
		stringBuilder.append("</table>");
	}

	@Override
	public void endTableBand(ITableBandContent tableBandContent) throws BirtException {
		stringBuilder.append("</table-band>");
	}

	@Override
	public void endTableGroup(ITableGroupContent tableGroupContent) throws BirtException {
		stringBuilder.append("</table-group>");
	}

	@Override
	public String getOutputFormat() {
		return "DEBUG";
	}

	@Override
	public void initialize(IEmitterServices services) throws BirtException {

		outputStream = services.getRenderOption().getOutputStream();
	}

	@Override
	public void start(IReportContent reportContent) throws BirtException {
		stringBuilder = new StringBuilder();
		
		//add the XML header
		stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
		
		stringBuilder.append("<debug-info>\n\n");

		stringBuilder.append("<report>");
	}

	@Override
	public void startAutoText(IAutoTextContent autoText) throws BirtException {
		//autotext is self contained
		stringBuilder.append("<auto-text");
		
		appendAttribute(autoText);
		
		stringBuilder.append(">");
		stringBuilder.append(autoText.getText());
		stringBuilder.append("</auto-text>");
	}

	@Override
	public void startCell(ICellContent cell) throws BirtException {
		stringBuilder.append("<cell");
		
		appendAttribute(cell);
		
		Object generateBy = cell.getGenerateBy();
		
		if (generateBy instanceof CellDesign) {
			Collection<ReportItemDesign> reportItemDesignList = ((CellDesign) generateBy).getContents();
			
			if (reportItemDesignList != null )
				for (ReportItemDesign reportItemDesign : reportItemDesignList)
					if (reportItemDesign instanceof DataItemDesign) {
						String bindingColumn = ((DataItemDesign) reportItemDesign).getBindingColumn();

						dataContentMap.put(bindingColumn, "");
						
						stringBuilder.append(String.format(" dataVarName=\"%s\"", StringUtils.defaultString(bindingColumn)));
					}
		}
		
		stringBuilder.append(">");
	}
	
	@Override
	public void startContainer(IContainerContent containerContent) throws BirtException {
		stringBuilder.append("<container");
		appendAttribute(containerContent);
		stringBuilder.append(">");
	}

	@Override
	public void startContent(IContent content) throws BirtException {
		stringBuilder.append("<content");
		appendAttribute(content);
		stringBuilder.append(">");
	}

	@Override
	public void startData(IDataContent dataContent) throws BirtException {
		Object generateBy = dataContent.getGenerateBy();

		String bindingColumn = null;
		
		if (generateBy instanceof DataItemDesign) {
			bindingColumn = ((DataItemDesign) generateBy).getBindingColumn();

			Object value = dataContent.getValue();

			dataContentMap.put(bindingColumn, value);
		}
		
		stringBuilder.append("<data");
		appendAttribute(dataContent);
		stringBuilder.append(String.format(" varName=\"%s\" textFmt=\"%s\">", StringUtils.defaultString(bindingColumn),
				dataContent.getText()));
		stringBuilder.append(dataContent.getValue());
		stringBuilder.append("</data>");
	}

	@Override
	/**
	 * As a report developer
	 * when I have a report with a dynamic text item
	 * then I need the XML format to display the result
	 * 
	 * Foreigns appear to be resulted from Dynamic Text Items
	 */
	public void startForeign(IForeignContent foreign) throws BirtException {
		stringBuilder.append("<foreign");
		appendAttribute(foreign);	
		stringBuilder.append(">");
		
		String o = (String) foreign.getRawValue();
		stringBuilder.append(o);
		stringBuilder.append("</foreign>");
	}

	@Override
	public void startGroup(IGroupContent groupContent) throws BirtException {
		stringBuilder.append("<group");
		appendAttribute(groupContent);
		stringBuilder.append(">");
	}

	@Override
	public void startImage(IImageContent imageContent) throws BirtException {
		stringBuilder.append("<image");
		appendAttribute(imageContent);
		stringBuilder.append(">");
		
		//encode image data as Base64
		stringBuilder.append(Base64.encode(imageContent.getData()));
		
		//there is no end image method. WRite out the closing tag here
		stringBuilder.append("</image>");
	}

	@Override
	public void startLabel(ILabelContent label) throws BirtException {
		stringBuilder.append("<label");
		appendAttribute(label);
		stringBuilder.append(">");
		stringBuilder.append(label.getLabelText());
		
		//there is no end label method, write out here
		stringBuilder.append("</label>");
	}

	@Override
	public void startList(IListContent listContent) throws BirtException {
		stringBuilder.append("<list");
		appendAttribute(listContent);
		stringBuilder.append(">");
	}

	@Override
	public void startListBand(IListBandContent listBandContent) throws BirtException {
		stringBuilder.append("<list-band");
		appendAttribute(listBandContent);
		stringBuilder.append(">");
	}

	@Override
	public void startListGroup(IListGroupContent listGroupContent) throws BirtException {
		stringBuilder.append("<list-group");
		appendAttribute(listGroupContent);
		stringBuilder.append(">");
	}

	@Override
	public void startPage(IPageContent pageContent) throws BirtException {
		stringBuilder.append("<page");
		appendAttribute(pageContent);
		stringBuilder.append(">");
	}

	@Override
	public void startRow(IRowContent rowContent) throws BirtException {
		stringBuilder.append("<row");
		appendAttribute(rowContent);
		stringBuilder.append(">");
	}

	@Override
	public void startTable(ITableContent tableContent) throws BirtException {
		stringBuilder.append("<table");
		appendAttribute(tableContent);
		stringBuilder.append(">");
	}

	@Override
	public void startTableBand(ITableBandContent tableBandContent) throws BirtException {
		stringBuilder.append("<table-band");
		appendAttribute(tableBandContent);
		String bandType = null;
		switch (tableBandContent.getBandType()) {
			case IBandContent.BAND_DETAIL:
				bandType = "BAND_DETAIL";
				break;
			case IBandContent.BAND_FOOTER:
				bandType = "BAND_FOOTER";
				break;
			case IBandContent.BAND_HEADER:
				bandType = "BAND_HEADER";
				break;
			case IBandContent.BAND_GROUP_FOOTER:
				bandType = "BAND_GROUP_FOOTER";
				break;
			case IBandContent.BAND_GROUP_HEADER:
				bandType = "BAND_GROUP_HEADER";
				break;
		}
		
		if (bandType != null) {
			appendAttribute("band-type", bandType);
		}
		stringBuilder.append(">");
	}

	@Override
	public void startTableGroup(ITableGroupContent tableGroupContent) throws BirtException {
		stringBuilder.append("<table-group");
		appendAttribute(tableGroupContent);
		stringBuilder.append(">");
	}

	@Override
	public void startText(ITextContent textContent) throws BirtException {
		stringBuilder.append("<text");
		stringBuilder.append(">");
		stringBuilder.append(textContent.getText());
		
		//there is no endText method, so write out closing tag here.
		stringBuilder.append("</text>");
	}
	
	
	
}
