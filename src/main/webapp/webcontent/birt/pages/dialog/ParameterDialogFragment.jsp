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
<%@ page import="java.util.Iterator,
				 java.util.Collection,
 				 org.eclipse.birt.report.resource.BirtResources,
 				 org.eclipse.birt.report.presentation.aggregation.IFragment" %>

<%-----------------------------------------------------------------------------
	Expected java beans
-----------------------------------------------------------------------------%>
<jsp:useBean id="fragments" type="java.util.Collection" scope="request" />

<%-----------------------------------------------------------------------------
	Parameter dialog fragment
-----------------------------------------------------------------------------%>

<DIV CLASS="birtviewer_parameter_dialog">
	<TABLE CELLSPACING="2" CELLPADDING="2" ID="parameter_table" CLASS="">
		<TR VALIGN="top">
			<TD>
				<TABLE class="contentParameter_table">

					<%
					if ( fragments.size( ) <= 0 )
					{
					%>
						<TR>
							<TD><%= BirtResources.getMessage( "birt.viewer.error.noparameter" ) %></TD>
						</TR>
					<%
					}
					else
					{
					%>
						<TR><TD COLSPAN="2" class="margin-medium"><%= BirtResources.getMessage( "birt.viewer.required" ) %></TD></TR>
					<%
						if ( fragments != null )
						{
							Iterator childIterator = fragments.iterator( );
							while ( childIterator.hasNext( ) )
							{
							    IFragment subfragment = ( IFragment ) childIterator.next( );
								if ( subfragment != null )
								{
									%> <!--   <%= subfragment.toString() %>  -->  <% 
									
									subfragment.service( request, response );
								}
							}
						}
					}
					%>
					<TR HEIGHT="5px"><TD></TD></TR>
				</TABLE>
			</TD>
		</TR>
		<TR>
			<TD>
					<div id="birt_hint" 
					style="font-size:12px;color:#000000;display:none;position:absolute; z-index:300;background-color: #F7F7F7; layer-background-color: #0099FF; border: 1px #000000 solid;filter:Alpha(style=0,opacity=80,finishOpacity=100);">
					</div>			
			</TD>
		</TR>
		
	</TABLE>	
</DIV>




