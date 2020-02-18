/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.utility.filename;

import java.util.Map;

import org.eclipse.birt.report.engine.api.EmitterInfo;
import org.eclipse.birt.report.utility.ParameterAccessor;

public class FilenameGeneratorDelegate implements IFilenameGenerator {
	private IFilenameGenerator filenameGenerator;

	@Override
	public String getFilename(String baseName, String extension, String outputType,
			@SuppressWarnings("rawtypes") Map options) {
		filenameGenerator = new DefaultFilenameGenerator();

		EmitterInfo emitterInfo = (EmitterInfo) options.get(OPTIONS_EMITTER_INFO);

		if (emitterInfo != null) {
			String filenameGeneratorClassName = ParameterAccessor
					.getInitProp(String.format("viewer.emitter.filename.generator.class.%s", emitterInfo.getFormat()));

			if (filenameGeneratorClassName != null)
				try {
					filenameGenerator = (IFilenameGenerator) Class.forName(filenameGeneratorClassName).newInstance();
				} catch (Exception e){
					// Non faccio nulla
				}
		}
	
		return filenameGenerator.getFilename(baseName, extension, outputType, options);
	}
}