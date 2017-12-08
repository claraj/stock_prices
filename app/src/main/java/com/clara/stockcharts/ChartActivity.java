package com.clara.stockcharts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.clara.stockcharts.model.StockDataQueryResultListener;
import com.clara.stockcharts.model.StockPricePoint;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

public class ChartActivity extends AppCompatActivity implements StockDataQueryResultListener {

	private static final String TAG = "ChartActivity";
	private EditText stockText;
	private Button chartButton;

	private LineChart lineChart;
	private ProgressBar progressSpinner;

	private String currentSymbol;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get symbol
		// Get data for symbol
		// Draw chart

		stockText = (EditText) findViewById(R.id.stock_symbol);
		chartButton = (Button) findViewById(R.id.chart_button);
		lineChart = (LineChart) findViewById(R.id.line_chart);
		progressSpinner = (ProgressBar) findViewById(R.id.chart_load_progress);

		// for testing, can remove
		currentSymbol = "SNAP";
		fetchData(currentSymbol);


		chartButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				currentSymbol = stockText.getText().toString().trim();
				if (currentSymbol.isEmpty()) {
					Toast.makeText(ChartActivity.this, "Enter a stock symbol", Toast.LENGTH_LONG).show();
					return;
				}

				fetchData(currentSymbol);

			}


		});

	}


	private void setUIEnabled(boolean enable) {

		chartButton.setEnabled(enable);
		stockText.setEnabled(enable);

		int chartVis = enable ? View.VISIBLE : View.INVISIBLE;
		int progressVis = enable ? View.INVISIBLE : View.VISIBLE;

		progressSpinner.setVisibility(progressVis);
		lineChart.setVisibility(chartVis);

	}



	private void fetchData(String symbol) {

		setUIEnabled(false);

		FetchStockDataTask task = new FetchStockDataTask(this);
		task.execute(symbol);


	}


	@Override
	public void chartDataFetched(List<StockPricePoint> pricePointList) {
		setUIEnabled(true);
		drawChart(pricePointList);
	}

	@Override
	public void chartDataError(String errorMessage) {
		setUIEnabled(true);
		Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
	}




	private void drawChart(List<StockPricePoint> pricePointList) {

		Log.d(TAG, "Data for chart " + pricePointList);

		ChartUtils.configure(lineChart, pricePointList, currentSymbol);

		lineChart.getLineData().notifyDataChanged();
		lineChart.notifyDataSetChanged();

	}

}
