package com.clara.stockcharts;

import android.util.Log;

import com.clara.stockcharts.model.StockPricePoint;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;



/**
 * Created by clara on 12/8/17.
 * Create line chart from API results.
 */

public class ChartUtils {

	private final static String TAG = "ChartUtils";

	private final static String DATEFORMAT = "yyyy-MM-dd";

	private static SimpleDateFormat readDatesformatter  = new SimpleDateFormat(DATEFORMAT, Locale.US);  // read dates from API call in US format
	private static SimpleDateFormat displayDatesFormatter  = new SimpleDateFormat(DATEFORMAT, Locale.getDefault());   // For creating localized dates to display in chart X axis


	// Turn list of StockPricePoint objects into data set for chart.
	// Since I know next to nothing about stock charts,
	// I'm going to draw the stock close price in this chart.
	// Anyone reading this presumably knows enough about Android and stock price data to
	// customize it for their own needs.

	static void configure(LineChart chart, List<StockPricePoint> pricePoints, String symbol) {

		List<Entry> entryList = listToLineEntry(pricePoints);
		LineDataSet lineDataSet = new LineDataSet(entryList, "Close Prices");

		LineData data = new LineData(lineDataSet);

		Description description = new Description();
		description.setText("Close Prices for " + symbol);
		chart.setDescription(description);
		chart.setData(data);

		// format x axis as dates, not timestamps

		XAxis xAxis = chart.getXAxis();
		xAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				return displayDatesFormatter.format( new Date((long)value) );
			}
		});

		xAxis.setLabelCount(5);   // Five labels on the X axis

		// Display at top & bottom of chart (default is at the top)
		xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

	}



	private static List<Entry> listToLineEntry(List<StockPricePoint> pricePoints) {

		// Java 8 new stuff. Replace with a regular loop if building with Java 7
		List<Entry> closePricesLineEntryList = pricePoints
				.stream()
				.map(p -> new Entry(getDate(p), getClose(p)))
				.collect(Collectors.toList());

		Log.d(TAG, closePricesLineEntryList.toString());

		return closePricesLineEntryList;

	}

	private static float getDate(StockPricePoint p)  {
		try {
			String dateStr = p.getDate();
			Date d = readDatesformatter.parse(dateStr);
			Log.d(TAG, "DATA : " + dateStr + " " + d.getTime() + " " + d);
			return d.getTime();
		} catch (ParseException e) {
			Log.e(TAG, "Error processing API response data", e);
			return -1;
		}
	}

	private static float getClose(StockPricePoint p) {
		return Float.parseFloat(p.getClose());
	}


}
