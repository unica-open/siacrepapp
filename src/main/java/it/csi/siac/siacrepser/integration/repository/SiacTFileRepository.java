/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.integration.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import it.csi.siac.siacrepser.integration.entity.SiacTFile;

public interface SiacTFileRepository extends JpaRepository<SiacTFile, Integer> {
}
