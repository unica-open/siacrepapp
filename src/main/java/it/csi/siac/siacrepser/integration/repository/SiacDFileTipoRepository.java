/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.integration.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.csi.siac.siacrepser.integration.entity.SiacDFileTipo;

public interface SiacDFileTipoRepository extends JpaRepository<SiacDFileTipo, Integer> {

	@Query("SELECT ft FROM SiacDFileTipo ft "
			+ " WHERE ft.enteProprietario.uid=:idEnte AND ft.dataCancellazione IS NULL " + " AND ft.codice=:codice")
	SiacDFileTipo ricercaTipoFile(
			@Param("codice") String codice,
			@Param("idEnte") Integer idEnte);

}
