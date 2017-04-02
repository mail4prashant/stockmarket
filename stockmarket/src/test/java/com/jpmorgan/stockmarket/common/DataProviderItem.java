package com.jpmorgan.stockmarket.common;

public abstract class DataProviderItem extends DataProviderItemBase {

	private final String caseDescription;

	public DataProviderItem(String caseDescription) {
		this.caseDescription = caseDescription;
	}

	public String getCaseDescription() {
		return caseDescription;
	}

}
