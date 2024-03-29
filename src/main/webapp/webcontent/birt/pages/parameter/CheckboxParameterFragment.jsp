<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%-----------------------------------------------------------------------------
	Copyright (c) 2004 Actuate Corporation and others.
	All rights reserved. This program and the accompanying materials 
	are made available under the terms of the Eclipse Public License v1.0
	which accompanies this distribution, and is available at
	http://www.eclipse.org/legal/epl-v10.html
	
	Contributors:
		Actuate Corporation - Initial implementation.
-----------------------------------------------------------------------------%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page session="false" buffer="none" %>
<%@ page import="org.eclipse.birt.report.context.ScalarParameterBean,
				 org.eclipse.birt.report.context.BaseAttributeBean,
				 org.eclipse.birt.report.utility.ParameterAccessor" %>

<%-----------------------------------------------------------------------------
	Expected java beans
-----------------------------------------------------------------------------%>
<jsp:useBean id="attributeBean" type="org.eclipse.birt.report.context.BaseAttributeBean" scope="request" />

<%-----------------------------------------------------------------------------
	Checkbox parameter control
-----------------------------------------------------------------------------%>
<%
	ScalarParameterBean parameterBean = ( ScalarParameterBean ) attributeBean.getParameterBean( );
	String encodedParameterName = ParameterAccessor.htmlEncode( parameterBean.getName( ) );
%>
<TR>
	
	<TD NOWRAP COLSPAN="2">
		<FONT TITLE="<%= parameterBean.getToolTip( ) %>"><LABEL FOR="<%= encodedParameterName %>"><%= parameterBean.getDisplayName( ) %>:</LABEL></FONT>
		<%-- is required --%>
		<%
		if ( parameterBean.isRequired( ) )
		{
		%>
			<FONT COLOR="red"><LABEL FOR="<%= encodedParameterName %>">*</LABEL></FONT>
		<%
		}
		%>
	</TD>
</TR>
<TR>
	<TD NOWRAP COLSPAN="2">
		<%-- Parameter control --%>
		<INPUT TYPE="HIDDEN" ID="control_type" VALUE="checkbox">
		<INPUT TYPE="HIDDEN"
			ID="<%= encodedParameterName + "_hidden" %>"
			NAME="<%= encodedParameterName %>"
			VALUE="<%= parameterBean.getValue( ) %>">
		<INPUT TYPE="CHECKBOX"
			ID="<%= encodedParameterName %>"
			TITLE="<%= parameterBean.getToolTip( ) %>"
			VALUE="<%= encodedParameterName %>"
			<%= "true".equalsIgnoreCase( parameterBean.getValue( ) ) ? "CHECKED" : "" %>
			>
	</TD>
</TR>