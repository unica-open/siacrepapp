<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>




<h4><s:property value="titolo"/></h4>


<table class="table table-hover" summary="....">

	<thead>
	  <tr>
		<th scope="col">Nome report</th>
		<th scope="col">&nbsp;</th>
        <th scope="col">&nbsp;</th>
        <th scope="col">&nbsp;</th>
        <th scope="col">&nbsp;</th>
     </tr>
   </thead>

	<tbody>
		<s:iterator value="elencoReport">
			<tr>
				<td scope="row"><s:property value="name"/></td>
				<td>
					<s:if test="allowPdf">
						<a href="frameset?__report=<s:property value="fileName"/><s:if 
							test="hideForContentOnly != null">&hideForContentOnly=1</s:if>&outputFormat=pdf&__asattachment=true&<s:property 
							value="queryStringParams" />">PDF</a>
					</s:if>				
				</td>
				<td>
					<s:if test="allowXls">
						<a href="frameset?__report=<s:property value="fileName"/><s:if 
							test="hideForContentOnly != null">&hideForContentOnly=1</s:if>&sample=my+parameter&outputFormat=xls&__asattachment=true&<s:property 
							value="queryStringParams" />">XLS</a>
					</s:if>				
				</td>
				<td>
					<s:if test="allowXbrl">
						<a class="xbrl" href="frameset?__report=<s:property value="fileName"/><s:if 
							test="hideForContentOnly != null">&hideForContentOnly=1</s:if>&sample=my+parameter&outputFormat=xbrl&__asattachment=true&__parameterpage=true&__emitterid=org.eclipse.birt.report.engine.emitter.xbrl&<s:property 
							value="queryStringParams" />">XBRL</a>
					</s:if>				
				</td>
				<td>
					<s:if test="allowCad">
						<a  href="frameset?__report=<s:property value="fileName"/><s:if 
							test="hideForContentOnly != null">&hideForContentOnly=1</s:if>&t400cad&<s:property value="queryStringParams" />">TXT</a>
					</s:if>				
				</td>
			</tr>
		</s:iterator>
    </tbody>
	
</table>

