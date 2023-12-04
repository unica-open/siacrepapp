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
	

	<div class="row-fluid">
		<div class="span12">
			<ul class="breadcrumb">
				<li><a href="<s:property value="#urlCruscotto" />">Home</a>
					<span class="divider">&gt;</span></li>
				<li><a href="home.do">Report</a>
					<span class="divider">&gt;</span></li>
		        <li class="active">XBRL generati</li>
			</ul>
		</div>
	</div>

 


<div class="container-fluid">
  <div class="row-fluid">
    <div class="span12 contentPage">    
    
<s:form  method="post">

<s:if test="hasActionMessages()">
						<%-- Messaggio di WARNING --%>
						<div class="alert alert-warning">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Attenzione!!</strong><br>
							<ul>
								<s:iterator value="messaggi">
									<li><s:property value="codice"/> - <s:property value="descrizione"/> </li>
								</s:iterator>
							</ul>
						</div>
					</s:if>


			<s:if test="hasActionErrors()">
						<%-- Messaggio di ERROR --%>
						<div class="alert alert-error">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Attenzione!!</strong><br>
							<ul>
								<s:iterator value="errori">
									<li><s:property value="testo"/> </li>
								</s:iterator>
							</ul>
						</div>
			</s:if>


		
		<h3>XBRL generati</h3>   
		<fieldset class="form-horizontal">

		<h4 class="step-pane">Elenco</h4>
		
			<table class="table table-hover tab_left"  summary="...." >
				<thead>
					<tr>
						<th class="span2"><input type="checkbox" id="ord-sel-all" /></th>
						<th>Codice</th>
						<th>Note</th>
						<th class="span2">Data caricamento</th>	
						<th class="span2 tab_Right">&nbsp;</th>			
					</tr>
				</thead>
				<tbody>
				
				
				<s:iterator value="elencoFileXbrl" status="st">

	
	<tr data-index="<s:property value="#st.index" />">
		<td class="ord-sel"> <s:checkbox name="selectedXbrl" fieldValue="%{#st.index}"  disabled="contenuto == null"  />  </td>
		<td class="file-codice"><s:property value="codice" /></td>
		<td><s:property value="note" /></td>
		<td class="file-dataUpload"><s:date name="dataCreazione" format="dd/MM/yyyy H:mm" /></td>
		
		
		
		<td class="tab_Right">
			<div class="btn-group">
				<button class="btn dropdown-toggle" data-toggle="dropdown">Azioni<span class="caret"></span></button>
				<ul class="dropdown-menu pull-right">
					
					<li><a  class="azione-file" href="#msgElimina" data-toggle="modal">elimina</a></li> 								
				</ul>
			</div>
		</td>
		
				
		
		

	</tr>
	
					
				</s:iterator>	
				

				</tbody>
				<tfoot>
				</tfoot>

			</table>
			
			<div class="Border_line"></div>


			
<!-- Modal msgAnnulla -->
	<div id="msgElimina" class="modal hide fade modal-azione" tabindex="-1" role="dialog" aria-labelledby="msgAnnullaLabel" aria-hidden="true">
	  <div class="modal-body">
		<div class="alert alert-error">
		  <button type="button" class="close" data-dismiss="alert">&times;</button>
		  <p><strong>Attenzione!</strong></p>
		  <p>Stai per eliminare l'elemento selezionato: sei sicuro di voler proseguire?</p>
		</div>
	  </div>
	  <div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">no, indietro</button>
		<button class="btn btn-primary conferma-azione" data-dismiss="modal" aria-hidden="true">si, prosegui</button>
		<a class="anchor-azione" href="<s:url action="gestioneXbrlFile_elimina"/>"><span class="hide"></span></a>
	  </div>
	</div>  
<!--/modale annulla  -->




		</fieldset>
		<p class="margin-medium"><a class="btn btn-secondary" href="home.do">indietro</a>  
		
					  	<s:if test="not elencoFileXbrl.empty">	        
				  <s:submit cssClass="btn btn-primary pull-right" action="gestioneXbrlFile_unisci" id="unisci"  value="unisci" />
				</s:if>
		 
          </p>     
      </s:form>
    </div>
  </div>	 
</div>	

	
	<%-- Caricamento del footer --%>
	<s:include value="/jsp/include/footer.jsp" />
	
<s:include value="/jsp/include/javascript.jsp" />
	
<r:include url="/ris/servizi/siac/include/myCheckbox.html" resourceProvider="rp"/>

<script type="text/javascript" src="${jspath}xbrl/gestioneXbrlFile.js" charset="utf-8"></script>

	
	
	
</body>
</html>