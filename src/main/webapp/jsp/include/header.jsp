<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r" %>
<%@ taglib uri="/struts-tags" prefix="s" %>    


<s:set name="urlCruscotto" 
	value="%{'/siacrepapp/redirectToCruscotto.do'}" /> 

<%-- Gestione della navigazione --%>
<p class="nascosto">
	<a name="A-sommario" title="A-sommario"></a>
</p>

<ul id="sommario" class="nascosto">
	<li><a href="#A-contenuti">Salta ai contenuti</a></li>
</ul>
<%-- Termine navigazione --%>

<hr/>

<%-- Banner --%>
<div class="container-fluid-banner">
	<%-- Inclusione Banner del portale --%>
	<r:include url="/ris/servizi/siac/include/portalheader.html" resourceProvider="rp"/>

	<%-- Inclusione informazioni Utente loggato --%>
	<s:include value="/jsp/include/infoUtenteLogin.jsp" />
	
	<%-- Inclusione Banner dell'applicativo --%>
	<r:include url="/ris/servizi/siac/include/applicationHeader.html" resourceProvider="rp"/>

	<a name="A-contenuti" title="A-contenuti"></a>
	
</div>