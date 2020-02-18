/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepser.integration.repository;


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import it.csi.siac.siacrepser.integration.entity.SiacTFile;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public interface SiacTFileRepository extends JpaRepository<SiacTFile, Integer> {
}
