/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package test.main;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "list")
public class JaxbList<T> {
	protected List<T> list = new ArrayList<T>();

	public JaxbList() {
		// Default empty constructor
	}

	public JaxbList(List<T> list) {
		this.list = list;
	}

	@XmlElement(name = "item")
	public List<T> getList() {
		return list;
	}
}
