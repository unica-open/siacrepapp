/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.ui.util.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import it.csi.siac.siaccommon.util.log.LogUtil;

/**
 * Interceptor di log. 
 * Questo interceptor si occupa di centralizzare il logging delle varie Actions
 * che richiamano un servizio. Si propone di uniformare il log prima e a seguito dell'esecuzione
 * delle Action corrispondenti.
 * 
 * @author Alessandro Marchino
 * @see it.csi.siac.siaccorser.util.log.CoreLogHandler
 *
 */
public class LogInterceptor extends AbstractInterceptor {

	/** Per la serializzazione */
	private static final long serialVersionUID = 1518258887614549162L;
	
	/** Per il logging */
	private LogUtil log;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		final String methodName = invocation.getProxy().getMethod();
		
		log = new LogUtil(invocation.getAction().getClass());
		log.debugStart(methodName, "");
		
		String risultatoInvocazione;
		
		try{
			risultatoInvocazione = invocation.invoke();
		} finally {		
			log.debugEnd(methodName, "");
		}
		
		return risultatoInvocazione;
	}

}
