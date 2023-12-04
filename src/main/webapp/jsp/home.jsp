<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%> 


<%-- Inclusione head e CSS --%>
<s:include value="/jsp/include/head.jsp" />




</head>
<body>
	<%-- Inclusione header --%>
	<s:include value="/jsp/include/header.jsp" />
	
	<%-- 
	<s:include value="/jsp/include/spinner.jsp" />
	--%>
	
	<div class="row-fluid">
		<div class="span12">
			<ul class="breadcrumb">
				<li><a href="<s:property value="#urlCruscotto" />">Home</a></li>
							<li><span class="divider">&gt;</span></li>
		        <li class="active"><s:property value="titolo"/></li>
			</ul>
		</div>
	</div>
	
	
	<%-- Pagina JSP vera e propria --%>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12 ">
			
				
				<div class="contentPage">
	
					<s:include value="/jsp/include/homeContent.jsp" />
		
					<br/>
					
					<p> 
					<a href="/siaccruapp/home.do" class="btn">indietro</a>
					
								<a class="btn btn-primary pull-right nascosto" id="gestioneXbrlFile" href="gestioneXbrlFile.do">gestione xbrl</a>
					 </p>
				</div>
			</div>
		</div>
	</div>
	
	<%-- Caricamento del footer --%>
	<s:include value="/jsp/include/footer.jsp" />
	
	
	<s:include value="/jsp/include/javascript.jsp" />
	
	
	<script type="text/javascript" src="/siacrepapp/js/local/homeContent.js"></script>
	
	
	
	
	
</body>
</html>