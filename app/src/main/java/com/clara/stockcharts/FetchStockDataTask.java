package com.clara.stockcharts;

import android.os.AsyncTask;
import android.util.Log;

import com.clara.stockcharts.model.StockDataQueryFailedException;
import com.clara.stockcharts.model.StockDataQueryResultListener;
import com.clara.stockcharts.model.StockPricePoint;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by clara on 12/8/17.
 */

public class FetchStockDataTask extends AsyncTask<String, Void, List<StockPricePoint>> {

	// Url should be in the form "https://api.iextrading.com/1.0/stock/aapl/chart/1m"

	private static final String TAG = "FetchStockDataTask";

	private static final String URL_BASE = "https://api.iextrading.com/1.0/stock/%s/chart/1m";

	private StockDataQueryResultListener listener;

	private Exception exception;


	FetchStockDataTask(StockDataQueryResultListener listener) {
		this.listener = listener;
	}


	@Override
	protected List<StockPricePoint> doInBackground(String... params) {

		try {

			if (params != null && params.length == 1) {

				// TODO better validation

				String symbol = params[0];
				String urlString = String.format(URL_BASE, symbol);
				URL url = new URL(urlString);
				HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

				// What's the response code? If 404 stop and report error

				int code = connection.getResponseCode();

				if (code == 404) {
					exception = new StockDataQueryFailedException(StockDataQueryFailedException.ERR_NOT_FOUND);
					return null;
				}

				if (code != 200) {    // for all other possible responses, to catch other errors
					Log.e(TAG, "Error response from server, response code = " + code);
					exception = new StockDataQueryFailedException(StockDataQueryFailedException.ERR_BAD_RESPONSE);
					return null;
				}

				InputStream responseStream = connection.getInputStream();
				InputStreamReader streamReader = new InputStreamReader(responseStream);

				JsonReader reader = new JsonReader(streamReader);

				Gson gson = new Gson();

				Type collectionType = new TypeToken<List<StockPricePoint>>(){}.getType();

				List<StockPricePoint> pricePoints = gson.fromJson(reader,collectionType);

				return pricePoints;

			} else {
				exception = new StockDataQueryFailedException(StockDataQueryFailedException.ERR_INVALID_STOCK_PARAMETER);
				return null;
			}

		}

		catch (MalformedURLException e) {
			exception = e;
			return null;
		}

		catch (IOException e) {
			Log.e(TAG, "Error opening connection API server", e);
			exception = new StockDataQueryFailedException(StockDataQueryFailedException.ERR_NO_CONNECTION);
			return null;
		}

	}


	@Override
	protected void onPostExecute(List<StockPricePoint> stockPrices) {

		if (exception != null) {
			// report error to listener
			listener.chartDataError(exception.getMessage());

		} else {
			listener.chartDataFetched(stockPrices);;
		}



	}

}
