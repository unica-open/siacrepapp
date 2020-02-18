/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.engine.emitter.xbrl.business;

import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Fact;

public abstract class BaseXbrlTypeHandler<F extends Fact> implements XbrlTypeHandler<F> {
	private String code = StringUtils
			.uncapitalize(StringUtils.substringBefore(this.getClass().getSimpleName(), "XbrlTypeHandler"));

	@Override
	public String getCode() {
		return code;
	}
}
