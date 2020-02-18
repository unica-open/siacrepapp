/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.engine.emitter.xbrl.model;

public class Unit {
	private String id;
	private String value;

	public static final Unit EURO = new Unit("eur", "iso4217:EUR");
	public static final Unit PURE = new Unit("pure", "xbrli:pure");

	public Unit(String id, String value) {
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

}
