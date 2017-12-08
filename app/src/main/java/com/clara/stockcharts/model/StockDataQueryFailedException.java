package com.clara.stockcharts.model;

/**
 * Created by admin on 12/8/17.
 */

public class StockDataQueryFailedException extends Exception {

	public static final String ERR_NOT_FOUND = "Stock symbol not found";
	public static final String ERR_INVALID_STOCK_PARAMETER = "Stock symbol missing or invalid";
	public static final String ERR_NO_CONNECTION = "Can't connect to stock price server";
	public static final String ERR_BAD_RESONSE = "Error fetching data from stock price server";


	public StockDataQueryFailedException(String msg) {
		super(msg);
	}

}
