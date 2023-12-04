/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package test.main;

public class TestBirt {
	public static void main(String[] args) throws Exception {
		new TestBirt();
	}

	public TestBirt() {
//		EngineConfig config = new EngineConfig();
//
//		Platform.startup(); 
//
//		IReportEngineFactory factory = (IReportEngineFactory) Platform
//				.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
//
//		IReportEngine engine = factory.createReportEngine(config);
//
//		IReportRunnable design = engine
//				.openReportDesign("D:\\tmp\\pippobirt\\OP-GESREP1-BilPrev\\report_xml.rptdesign");
//
//		ReportDesignHandle report = (ReportDesignHandle) design.getDesignHandle();
//
//		SlotHandle datasourceSlotHandle = report.getDataSources();
//
//		OdaDataSourceHandle dataSource = null;
//
//		if (datasourceSlotHandle.getCount() > 0)
//		{
//			dataSource = (OdaDataSourceHandle) (datasourceSlotHandle.get(0));
//			// dataSource.setStringProperty("FILELIST", dataSourceFileList);
//
//			dataSource.getStringProperty("FILELIST");
//		}
//
//		// report.
//
//		IRunAndRenderTask task = engine.createRunAndRenderTask(design);
//
//		PDFRenderOption options = new PDFRenderOption();
//		options.setOutputFileName("d:\\proooooova.pdf");
//		options.setOutputFormat("pdf");
//
//		task.setRenderOption(options);
//
//		List<Missione> elencoStatoFile = new ArrayList<Missione>();
//		
//		elencoStatoFile.add(new Missione("m1", "mmmmmm1"));
//		elencoStatoFile.add(new Missione("m2", "mmmmmm2"));
//
////
//////		JAXBContext x = JAXBContext.newInstance(new Class[]{ElencoEntita.class});
//////		Marshaller m = x.createMarshaller();
//////		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//////		
//////		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//////		m.marshal(new ElencoEntita(elencoStatoFile), baos);
//////
//////		System.out.println( baos.toString());
////		
////	   System.out.println(JAXBUtility.marshall(new ElencoEntita(elencoStatoFile)));
//	    
//	    System.out.println("---------------------");
//		
//	    XStream xstream = new XStream() {
//
//			@Override
//			protected MapperWrapper wrapMapper(MapperWrapper next)
//			{
//				return new EntitaAliasingMapper(next);
//			}
//	    };
//	   
//	    
//	    String xml= xstream.toXML(elencoStatoFile);
//	    
//	   
//	    
//		System.out.println(xml);
//	    System.out.println("---------------------");
//
//		if (true)
//			return;
//
//		// String xml
//		// ="<?xml version=\"1.0\"?><library>  <book category=\"COOKING\">  <title lang=\"en\">Everyday Italian</title>  <author country=\"it\">Pino Gino De Laurentiis </author> <year>2005</year> </book>  <book category=\"CHILDREN\">  <title lang=\"en\">Harry Potter</title>  <author name=\"J K. Rowling\" country=\"uk\" />  <year>2005</year> </book>  </library>";
//
//		Map<String, Object> appContext = task.getAppContext();
//		InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
//		appContext.put("org.eclipse.datatools.enablement.oda.xml.inputStream", inputStream);
//		// Use closeInputStream (true) to let BIRT automatically close the
//		// inputStream.
//		appContext.put("org.eclipse.datatools.enablement.oda.xml.closeInputStream", "true");
//
//		task.run();
//		task.close();
//
//		System.out.println("zzzzzzzzzz");
	}

}
