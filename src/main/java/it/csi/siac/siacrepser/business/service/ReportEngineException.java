/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.business.service;

public class ReportEngineException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5484568192584979466L;

	public ReportEngineException(){
		super();
	}

	public ReportEngineException(String message) {
		super(message);
	}

	public ReportEngineException(Throwable cause) {
		super(cause);
	}

	public ReportEngineException(String message, Throwable cause) {
		super(message, cause);
	}

}
