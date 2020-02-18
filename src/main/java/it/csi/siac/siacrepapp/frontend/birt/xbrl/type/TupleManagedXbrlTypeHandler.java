/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.birt.xbrl.type;

import org.eclipse.birt.report.engine.emitter.xbrl.business.BaseXbrlTypeHandler;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Item;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Tuple;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Xbrl;

public abstract class TupleManagedXbrlTypeHandler extends BaseXbrlTypeHandler<Tuple> {
	
	@Override
	public void handleFact(Xbrl<Tuple> xbrl, Item item, Tuple tuple) {
		Tuple t = xbrl.getFact(tuple.getId());
		
		if (t == null)
			xbrl.addFact(t = tuple);
		
		t.addItem(item);
	}
}
