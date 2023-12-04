/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.birt.xbrl.type;

import org.eclipse.birt.report.engine.emitter.xbrl.business.BaseXbrlTypeHandler;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Item;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Tuple;
import org.eclipse.birt.report.engine.emitter.xbrl.model.Xbrl;

public abstract class ItemManagedXbrlTypeHandler extends BaseXbrlTypeHandler<Item> {
	
	@Override
	public void handleFact(Xbrl<Item> xbrl, Item item, Tuple tuple) {
		xbrl.addFact(item);
	}
}
