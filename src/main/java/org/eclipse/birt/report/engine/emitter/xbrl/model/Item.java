/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.engine.emitter.xbrl.model;

public class Item extends BaseFact {
	private String context;
	private String unit;
	private String decimals;
	private String value;

	public Item(String name, String context, String unit, String decimals, String value) {
		super(name);
		this.context = context;
		this.unit = unit;
		this.decimals = decimals;
		this.value = value;
	}

	public String getContext() {
		return context;
	}

	public String getUnit() {
		return unit;
	}

	public String getDecimals() {
		return decimals;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String getId() {
		return String.format("%s/%s", getName(), context);
	}
}
