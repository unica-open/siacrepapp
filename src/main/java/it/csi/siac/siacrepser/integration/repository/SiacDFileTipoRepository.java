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

import it.csi.siac.siacrepser.integration.entity.SiacDFileTipo;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public interface SiacDFileTipoRepository extends JpaRepository<SiacDFileTipo, Integer> {

	@Query("SELECT ft FROM SiacDFileTipo ft "
			+ " WHERE ft.enteProprietario.uid=:idEnte AND ft.dataCancellazione IS NULL " + " AND ft.codice=:codice")
	SiacDFileTipo ricercaTipoFile(
			@Param("codice") String codice,
			@Param("idEnte") Integer idEnte);

}
