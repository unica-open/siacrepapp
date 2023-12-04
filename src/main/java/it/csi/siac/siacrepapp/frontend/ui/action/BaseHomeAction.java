/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacrepapp.frontend.ui.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.eclipse.birt.report.utility.ParameterAccessor;
import org.springframework.web.context.ServletContextAware;

import it.csi.siac.siaccommonapp.action.GenericAction;
import it.csi.siac.siacrepapp.frontend.ui.handler.session.RepSessionParameter;
import it.csi.siac.siacrepapp.frontend.ui.model.HomeModel;
import it.csi.siac.siacrepapp.frontend.ui.model.HomeModel.ImplicitParams;

public abstract class BaseHomeAction<HM extends HomeModel> extends GenericAction<HM> implements ServletContextAware {
	private static final long serialVersionUID = 5221371874273974078L;

	private ServletContext servletContext;

	@Override
	public void prepare() throws Exception {
		final String methodName = "prepare";

		log.debugStart(methodName, "Preparazione della action");

		super.prepare();

		log.debugEnd(methodName, "");
	}

	@Override
	protected void initModel() {
		super.initModel();

		model.setTitolo(sessionHandler.getAzione().getTitolo());
	}

	@Override
	public String execute() throws Exception {
		String workingDir = servletContext
				.getInitParameter(ParameterAccessor.INIT_PARAM_WORKING_DIR);
		String reportFolder = sessionHandler.getParametro(RepSessionParameter.REPORT_FOLDER);    

		File reportPath = new File(workingDir + "/" + reportFolder);

		this.log.info("HomeAction::execute", "reportPath: " + reportPath);

		if (reportPath.isDirectory()) {
			File indexFile = new File(reportPath.getPath() + "/index.properties");

			Set<String> fileList = new HashSet<String>();

			if (indexFile.exists()) {
				for (String fileLine : FileUtils.readLines(indexFile, "UTF-8")) {
					if(fileLine.startsWith("#")) {
						// Ignora commenti
						continue;
					}
					HomeModel.Report report = new HomeModel.Report(fileLine, "::");

					String title = report.getName();
					
					String fileBaseName = StringUtils.substringBefore(fileLine, "=");

					File f = new File(reportPath + "/" + fileBaseName + ".rptdesign");
					
					if (f.isFile() && StringUtils.isNotBlank(title)) {
						report.setFileName(f.getName());
						
						model.getElencoReport().add(report);

						fileList.add(fileBaseName);
					}
				}
			}

			for (File f : FileUtils.listFiles(reportPath, new String[] { "rptdesign" }, true)) {

				if (f.isFile()) {
					String fileBaseName = StringUtils.substringBeforeLast(f.getName(), ".");

					if (!fileList.contains(fileBaseName)) {
						HomeModel.Report report = new HomeModel.Report(f.getName());

						report.setName(fileBaseName);
						
						this.log.info("", "aggiungo report (*): " + report.getName());

						model.getElencoReport().add(report);
					}
				}
				// TODO: ramo else
			}
		}
		
		addParamsToQueryString();

		saveSessionHandler();

		return SUCCESS;
	}

	private void addParamsToQueryString() throws UnsupportedEncodingException {
		model.addParamToQueryString(ImplicitParams.ENTE, String.valueOf(sessionHandler.getEnte().getUid()));
		model.addParamToQueryString(ImplicitParams.ANNO_BILANCIO, String.valueOf(sessionHandler.getBilancio().getAnno()));
		model.addParamToQueryString(ImplicitParams.ACCOUNT, String.valueOf(sessionHandler.getRichiedente().getAccount().getCodice()));
		
		if (sessionHandler.getParametriAzioneSelezionata() == null) {
			return;
		}
		
		for (String pv: StringUtils.split(sessionHandler.getParametriAzioneSelezionata(), ";")) {
			String[] pva = StringUtils.split(pv, ":");
			if (pva.length > 0) {
				model.addParamToQueryString(pva[0], pva.length > 1 ? pva[1] : null);
			}
		}
	}

	private void saveSessionHandler() {
		HttpSession s = ServletActionContext.getRequest().getSession(false);
		
		s.setAttribute("sessionHandler", sessionHandler);

		log.info("", String.format("session class: %s, id: %s, hashCode: %d, creation time: %d", s.getClass().getName(), s.getId(), s.hashCode(), s.getCreationTime()));
		log.info("", String.format("sessionHandler hashCode: %d", sessionHandler.hashCode()));
		
		if (log.isDebugEnabled())
			log.debug("", String.format("sessionHandler content: %s", sessionHandler.toString()));

	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
