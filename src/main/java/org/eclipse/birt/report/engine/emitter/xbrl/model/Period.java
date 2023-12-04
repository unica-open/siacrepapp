/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.engine.emitter.xbrl.model;

public class Period {
	private PeriodTypeEnum type;
	private String instant;
	private String startDate;
	private String endDate;

	public Period(String instant) {
		this.type = PeriodTypeEnum.instant;
		this.instant = instant;
	}

	public Period(String startDate, String endDate) {
		this.type = PeriodTypeEnum.duration;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getInstant() {
		return instant;
	}

	public void setInstant(String instant) {
		this.instant = instant;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public PeriodTypeEnum getType() {
		return type;
	}

	public void setType(PeriodTypeEnum type) {
		this.type = type;
	}
}
