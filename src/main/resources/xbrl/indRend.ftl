<?xml version="1.0" encoding="utf-8"?>

<xbrli:xbrl 
	xmlns:ire="http://www.rgs.mef.gov.it/xbrl/bdap/ind/rend/enti/2017-01-27" 
	xmlns:ir="http://www.rgs.mef.gov.it/xbrl/bdap/ind/rend/2017-01-27" 
	xmlns:link="http://www.xbrl.org/2003/linkbase" 
	xmlns:num="http://www.xbrl.org/dtr/type/numeric" 
	xmlns:iso4217="http://www.xbrl.org/2003/iso4217" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns:xbrli="http://www.xbrl.org/2003/instance" 
	xmlns:xlink="http://www.w3.org/1999/xlink">

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
<#list factList as fact>
   <ir:${fact.name} contextRef="${fact.context}"<#if fact.unit??> unitRef="${fact.unit}"</#if><#if fact.decimals??> decimals="${fact.decimals}"</#if>>${fact.value}</ir:${fact.name}>
</#list>


</xbrli:xbrl>
