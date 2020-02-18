/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.engine.emitter.xbrl.business;

import org.eclipse.birt.report.engine.emitter.xbrl.model.Fact;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Item;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Tuple;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Xbrl;

public interface XbrlTypeHandler<F extends Fact> {
	void handleFact(Xbrl<F> xbrl, Item item, Tuple tuple);

	String getCode();
}
