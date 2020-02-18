/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.ui.exception;


/**
 * Eccezione rilanciata in caso di errori generici  
 * 
 *
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 2549202494981605991L;

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message) {
		super(message);
	}
	
}
