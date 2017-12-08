package com.clara.stockcharts.model;

import java.util.List;

/**
 * Created by admin on 12/8/17.
 */

public interface StockDataQueryResultListener {

	void chartDataFetched(List<StockPricePoint> pricePointList);
	void chartDataError(String message);     // Message suitable for showing to user

}
