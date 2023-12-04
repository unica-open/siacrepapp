/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.engine.emitter.xbrl.model;

public abstract class BaseFact implements Fact {
	private String name;

	public BaseFact(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
