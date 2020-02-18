/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.integration.repository;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import it.csi.siac.siacrepser.integration.entity.SiacTReportTemplate;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public interface SiacTReportTemplateRepository extends JpaRepository<SiacTReportTemplate, Integer> {
	@Query("FROM SiacTReportTemplate r " 
			+ " WHERE r.enteProprietario.uid=:idEnte AND r.dataCancellazione IS NULL "
			+ " AND r.codice=:codice ")
	SiacTReportTemplate getTemplateInfo(
			@Param("idEnte") Integer idEnte,
			@Param("codice") String codice);

}
