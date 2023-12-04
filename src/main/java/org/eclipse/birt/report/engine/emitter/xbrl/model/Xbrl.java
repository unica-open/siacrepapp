/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.engine.emitter.xbrl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Xbrl<F extends Fact> {
	private String taxonomyRef;
	private String headerParts;
	private Map<String, Context> contextMap = new HashMap<String, Context>();
	private List<Unit> unitList = new ArrayList<Unit>();
	private Map<String, F> factMap = new LinkedHashMap<String, F>();
	
	public String getTaxonomyRef() {
		return taxonomyRef;
	}

	public Collection<Context> getContextList() {
		return contextMap.values();
	}

	public List<Unit> getUnitList() {
		return unitList;
	}

	public Collection<F> getFactList() {
		return factMap.values();
	}
	
	public void setTaxonomyRef(String taxonomyRef) {
		this.taxonomyRef = taxonomyRef;
	}

	public void addContext(Context context) {
		contextMap.put(context.getId(), context);
	}
	
	public Context getContext(String contextId) {
		return contextMap.get(contextId);
	}
	
	public void addUnit(Unit unit) {
		unitList.add(unit);
	}
	
	public void addFact(F fact) {
		factMap.put(fact.getId(), fact);
	}

	public F getFact(String factName) {
		return factMap.get(factName);
	}

	public String getHeaderParts() {
		return headerParts;
	}

	public void setHeaderParts(String headerParts) {
		this.headerParts = headerParts;
	}
}
