package com.jpmorgan.stockmarket.common;

public abstract class DataProviderItemBase {

    public Object[] transform() {
        return new Object[] {this};
    }

    public static Object[][] transformData(DataProviderItemBase... items) {
        Object[][] result = new Object[items.length][1];
        for (int i = 0; i < items.length; i++) {
            result[i][0] = items[i];
        }
        return result;
    }

    public abstract String getCaseDescription();

    @Override
    public String toString() {
        return getCaseDescription();
    }
}
