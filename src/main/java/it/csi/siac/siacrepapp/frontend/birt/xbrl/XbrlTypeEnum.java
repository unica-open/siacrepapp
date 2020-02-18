/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.birt.xbrl;

import org.eclipse.birt.report.engine.emitter.xbrl.business.XbrlTypeHandler;

import it.csi.siac.siacrepapp.frontend.birt.xbrl.type.DcaPrevXbrlTypeHandler;
import it.csi.siac.siacrepapp.frontend.birt.xbrl.type.DcaRendXbrlTypeHandler;
import it.csi.siac.siacrepapp.frontend.birt.xbrl.type.IndPrevXbrlTypeHandler;
import it.csi.siac.siacrepapp.frontend.birt.xbrl.type.IndRendXbrlTypeHandler;
import it.csi.siac.siacrepapp.frontend.birt.xbrl.type.SdbConsXbrlTypeHandler;
import it.csi.siac.siacrepapp.frontend.birt.xbrl.type.SdbPrevXbrlTypeHandler;
import it.csi.siac.siacrepapp.frontend.birt.xbrl.type.SdbRendXbrlTypeHandler;

public enum XbrlTypeEnum {
	SDB_PREV(SdbPrevXbrlTypeHandler.class), 
	SDB_CONS(SdbConsXbrlTypeHandler.class), 
	SDB_REND(SdbRendXbrlTypeHandler.class), 
	
	DCA_PREV(DcaPrevXbrlTypeHandler.class),
	DCA_REND(DcaRendXbrlTypeHandler.class),
	
	IND_PREV(IndPrevXbrlTypeHandler.class),
	IND_REND(IndRendXbrlTypeHandler.class),
	
	;

	@SuppressWarnings("rawtypes")
	private Class<? extends XbrlTypeHandler> typeHandlerClass;

	@SuppressWarnings("rawtypes") 
	XbrlTypeEnum(Class<? extends XbrlTypeHandler> processEngineClass) {
		this.typeHandlerClass = processEngineClass;
	}

	@SuppressWarnings("rawtypes") 
	public Class<? extends XbrlTypeHandler> getTypeHandlerClass() {
		return typeHandlerClass;
	}
}
