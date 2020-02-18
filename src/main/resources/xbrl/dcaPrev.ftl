<?xml version="1.0" encoding="utf-8"?>

<xbrli:xbrl  
	xmlns:dp="http://www.rgs.mef.gov.it/xbrl/bdap/dca/prev/2016-08-26" 
	xmlns:link="http://www.xbrl.org/2003/linkbase" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns:iso4217="http://www.xbrl.org/2003/iso4217" 
	xmlns:xlink="http://www.w3.org/1999/xlink" 
	xmlns:xbrli="http://www.xbrl.org/2003/instance">


   <link:schemaRef xlink:type='simple' xlink:href='${taxonomyRef}' />

<#list contextList as context>
  <xbrli:context id='${context.id}'>
      <xbrli:entity>
         <xbrli:identifier scheme='http://www.rgs.mef.gov.it/xbrl/idente/codicebdap'>${context.entityIdentifier}</xbrli:identifier>
      </xbrli:entity>
      <xbrli:period>
      <#if context.period.type == "duration">
         <xbrli:startDate>${context.period.startDate}</xbrli:startDate>
         <xbrli:endDate>${context.period.endDate}</xbrli:endDate>
      <#elseif context.period.type == "instant">
         <xbrli:instant>${context.period.instant}</xbrli:instant>
      </#if>
      </xbrli:period>
   </xbrli:context>
</#list>


   <!-- Units -->
 <#list unitList as unit>
   <xbrli:unit id='${unit.id}'>
      <xbrli:measure>${unit.value}</xbrli:measure>
   </xbrli:unit>
</#list>

    <!-- Fact values -->
<#list factList as tuple>
	<dp:${tuple.name}>
	<#list tuple.itemList as item>
		<dp:${item.name} contextRef="${item.context}"<#if item.unit??> unitRef="${item.unit}"</#if><#if item.decimals??> decimals="${item.decimals}"</#if>>${item.value}</dp:${item.name}>
	</#list>
	</dp:${tuple.name}>
	       
</#list>


</xbrli:xbrl>