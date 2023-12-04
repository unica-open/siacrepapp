/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package org.eclipse.birt.report.engine.emitter.xbrl.model;

import java.util.ArrayList;
import java.util.List;

public class Tuple extends BaseFact {
	private List<Item> itemList = new ArrayList<Item>();
	private String id;

	public Tuple(String id, String name) {
		super(name);
		this.id = id;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void addItem(Item item) {
		itemList.add(item);
	}

	@Override
	public String getId() {
		return id;
	}
}
