/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.birt.xbrl;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.emitter.xbrl.business.BaseXbrlHandler;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Context;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Fact;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Item;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Period;
import org.eclipse.birt.report.engine.emitter.xbrl.model.PeriodTypeEnum;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Tuple;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Unit;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Xbrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.csi.siac.siaccommonapp.handler.session.SessionHandler;
import it.csi.siac.siacrepapp.frontend.birt.xbrl.util.XbrlFileHelper;
import it.csi.siac.siacrepapp.frontend.birt.xbrl.util.XbrlFilenameGenerator;
import it.csi.siac.siacrepapp.frontend.birt.xbrl.util.XbrlProcessingHelper;
import it.csi.siac.siacrepapp.frontend.ui.model.HomeModel.ImplicitParams;
import it.csi.siac.siacrepser.business.service.XbrlService;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XbrlHandlerImpl<F extends Fact> extends BaseXbrlHandler<F> {
	
	private static final ThreadLocal<SimpleDateFormat> TL_SDF = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	
	private Map<String, Map<String, Object>> siacTXbrlMappingFattiMap;

	@Autowired
	private XbrlService xbrlService;

	@Autowired
	private XbrlProcessingHelper xbrlProcessingHelper;

	@Autowired
	private XbrlFileHelper xbrlFileHelper;

	protected Xbrl<F> xbrl;
	
	private Integer idEnte;
	private Integer annoBilancio;
	private String reportCode;

	@Override
	public void init() throws BirtException {
		xbrl = new Xbrl<F>();
		
		idEnte = getReportContextParameter(ImplicitParams.ENTE.getName());
		annoBilancio = Integer.valueOf((String) getReportContextParameter(ImplicitParams.ANNO_BILANCIO.getName()));
		reportCode = StringUtils.substringBefore((String) getRenderOption("report"), "_");

		Map<String, Object> siacTXbrlReport = readSiacTXbrlReport();
		
		xbrl.setTaxonomyRef((String) siacTXbrlReport.get("xbrl_rep_xsd_tassonomia"));

		initXbrlTypeHandler(siacTXbrlReport);

		readSiacTXbrlMappingFattiToMap();

		addUnits();
	}

	@Override
	protected void process(String dataVar) throws BirtException {
		Map<String, Object> siacTXbrlMappingFatti = siacTXbrlMappingFattiMap.get(dataVar);

		if (siacTXbrlMappingFatti != null) {
			String factName = evalFact((String) siacTXbrlMappingFatti.get("xbrl_mapfat_fatto"));
			String tupleName = xbrlProcessingHelper
					.evalFactVariables((String) siacTXbrlMappingFatti.get("xbrl_mapfat_tupla_nome"), dataContentMap);
			String tupleKey = xbrlProcessingHelper.evalFactVariables(
					(String) siacTXbrlMappingFatti.get("xbrl_mapfat_tupla_group_key"), dataContentMap);
			String periodoCode = xbrlProcessingHelper
					.decodeContextId((String) siacTXbrlMappingFatti.get("xbrl_mapfat_periodo_code"), dataContentMap);
			PeriodTypeEnum periodoType = PeriodTypeEnum
					.valueOf((String) siacTXbrlMappingFatti.get("xbrl_mapfat_periodo_tipo"));
			
			String unitCode = (String) siacTXbrlMappingFatti.get("xbrl_mapfat_unit_code");
			String decimals = (String) siacTXbrlMappingFatti.get("xbrl_mapfat_decimali");
			Boolean forceVisib = (Boolean) siacTXbrlMappingFatti.get("xbrl_mapfat_forza_visibilita");

			Object value = dataContentMap.get(dataVar);
			
			if (!forceVisib && value == null) {
				return;
			}

			if (decimals != null) {
				BigDecimal bdValue = xbrlProcessingHelper.toBigDecimal(value, decimals);

				if (forceVisib || bdValue != null) {
					if (!forceVisib && bdValue.compareTo(BigDecimal.ZERO) == 0) {
						return;
					}
					value = bdValue == null ? BigDecimal.ZERO.setScale(Integer.parseInt(decimals), BigDecimal.ROUND_HALF_UP) : bdValue; 
				}
			}

			Item item = new Item(factName, periodoCode, unitCode, decimals, value == null ? "" : value.toString());
			//Item item = new Item(factName, periodoCode, unitCode, decimals, value.toString());

			xbrlTypeHandler.handleFact(xbrl, item, new Tuple(tupleKey, tupleName));

			addContext(periodoCode, periodoType);
		}
	}

	private void readSiacTXbrlMappingFattiToMap() throws BirtException {
		List<Map<String, Object>> siacTXbrlMappingFattiList = xbrlService.readSiacTXbrlMappingFatti(idEnte, reportCode);

		if (siacTXbrlMappingFattiList == null)
			throw new BirtException("readSiacTXbrlMappingFatti: nessun record trovato");

		siacTXbrlMappingFattiMap = new HashMap<String, Map<String, Object>>();

		for (Map<String, Object> siacTXbrlMappingFatti : siacTXbrlMappingFattiList)
			siacTXbrlMappingFattiMap.put((String) siacTXbrlMappingFatti.get("xbrl_mapfat_variabile"),
					siacTXbrlMappingFatti);
	}

	private Map<String, Object> readSiacTXbrlReport() throws BirtException {
		Map<String, Object> siacTXbrlReport = xbrlService.readSiacTXbrlReport(idEnte, reportCode);

		if (siacTXbrlReport == null)
			throw new BirtException("readSiacTXbrlReport: nessun record trovato");

		return siacTXbrlReport;
	}

	private void addUnits() {
		xbrl.addUnit(Unit.EURO);
		xbrl.addUnit(Unit.PURE);
	}

	private String evalFact(String fact) {
		String s = fact.trim();

		s = xbrlProcessingHelper.evalFactVariables(s, dataContentMap);

		s = xbrlProcessingHelper.evalFactInnerExpression(s);

		if (s.startsWith("="))
			s = xbrlProcessingHelper.evalFactExpression(s);

		return s;
	}

	private void addContext(String contextId, PeriodTypeEnum contextType) throws BirtException {
		if (xbrl.getContext(contextId) == null) {
			Map<String, Object> siacTPeriodo = xbrlService.readSiacTPeriodo(idEnte, contextId);
			
			if (siacTPeriodo == null)
				throw new BirtException("readSiacTPeriodo: nessun record trovato");
		
			Period period;
			switch (contextType) {
			case duration:
				period = new Period(formatPeriodDate((Date) siacTPeriodo.get("data_inizio")),
						formatPeriodDate((Date) siacTPeriodo.get("data_fine")));
				break;
			case instant:
				period = new Period(formatPeriodDate((Date) siacTPeriodo.get("data_inizio")));
				break;
			default:
				throw new BirtException("contextType non valido");
			}

			String entecodicebdap = xbrlService.readXbrlEnteBdapCodice(idEnte);

			if (entecodicebdap == null)
				throw new BirtException("readXbrlEnteBdapCodice: nessun record trovato");

			xbrl.addContext(new Context(contextId, entecodicebdap, period));
		}
	}

	private static String formatPeriodDate(Date d) {
		return TL_SDF.get().format(d);
	}

	
	@Override
	protected Map<String, Object> buildXbrlRenderDataMap() {
		Map<String, Object> xbrlDataMap = new HashMap<String, Object>();

		xbrlDataMap.put("taxonomyRef", xbrl.getTaxonomyRef());
		xbrlDataMap.put("contextList", xbrl.getContextList());
		xbrlDataMap.put("unitList", xbrl.getUnitList());
		xbrlDataMap.put("factList", xbrl.getFactList());
		
		return xbrlDataMap;
	}
	
	@Override
	protected void handleReportByteArrayOutputStream(ByteArrayOutputStream baos) throws BirtException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) reportContext.getAppContext()
				.get(EngineConstants.APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST);

		HttpSession s = httpServletRequest.getSession(false);
		
		SessionHandler sessionHandler = (SessionHandler) s.getAttribute("sessionHandler");

		log.info("", String.format("session class: %s, id: %s, hashCode: %d, creation time: %d", s.getClass().getName(), s.getId(), s.hashCode(), s.getCreationTime()));
		log.info("", String.format("sessionHandler hashCode: %d", sessionHandler.hashCode()));
		
		if (log.isDebugEnabled())
			log.debug("", String.format("sessionHandler content: %s", sessionHandler.toString()));
	
		
		xbrlFileHelper.uploadFile(reportCode,
				(String) httpServletRequest.getAttribute(XbrlFilenameGenerator.REQUEST_ATTR_XBRL_GENERATED_FILENAME),
				parametersMap, baos.toByteArray(), sessionHandler.getEnte(), sessionHandler.getRichiedente());
	}
	
	private void initXbrlTypeHandler(Map<String, Object> siacTXbrlReport) {
		List<String> xbrlTypeParts = new ArrayList<String>();
		
		xbrlTypeParts.add((String) siacTXbrlReport.get("xbrl_rep_tipologia_code"));
		xbrlTypeParts.add((String) siacTXbrlReport.get("xbrl_rep_fase_code"));
		
		String xbrlType = StringUtils.join(xbrlTypeParts, "_");
		
		xbrlTypeHandler = appCtx.getBean(XbrlTypeEnum.valueOf(xbrlType).getTypeHandlerClass());
	}
}
