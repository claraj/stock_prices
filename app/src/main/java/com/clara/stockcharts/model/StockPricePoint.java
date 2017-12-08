package com.clara.stockcharts.model;


/* http://pojo.sodhanalibrary.com/Convert made this from example JSON from iextrading. Very useful!! */

public class StockPricePoint
{
	private String open;

	private String vwap;

	private String changePercent;

	private String change;

	private String unadjustedVolume;

	private String volume;

	private String label;

	private String high;

	private String changeOverTime;

	private String low;

	private String date;

	private String close;


	public long getTimeStamp() {
		return 12345678;  // TODO!!
	}


	public float getCloseNumber() {
		return Float.parseFloat(this.close);
	}



	public String getOpen ()
	{
		return open;
	}

	public void setOpen (String open)
	{
		this.open = open;
	}

	public String getVwap ()
	{
		return vwap;
	}

	public void setVwap (String vwap)
	{
		this.vwap = vwap;
	}

	public String getChangePercent ()
	{
		return changePercent;
	}

	public void setChangePercent (String changePercent)
	{
		this.changePercent = changePercent;
	}

	public String getChange ()
	{
		return change;
	}

	public void setChange (String change)
	{
		this.change = change;
	}

	public String getUnadjustedVolume ()
	{
		return unadjustedVolume;
	}

	public void setUnadjustedVolume (String unadjustedVolume)
	{
		this.unadjustedVolume = unadjustedVolume;
	}

	public String getVolume ()
	{
		return volume;
	}

	public void setVolume (String volume)
	{
		this.volume = volume;
	}

	public String getLabel ()
	{
		return label;
	}

	public void setLabel (String label)
	{
		this.label = label;
	}

	public String getHigh ()
	{
		return high;
	}

	public void setHigh (String high)
	{
		this.high = high;
	}

	public String getChangeOverTime ()
	{
		return changeOverTime;
	}

	public void setChangeOverTime (String changeOverTime)
	{
		this.changeOverTime = changeOverTime;
	}

	public String getLow ()
	{
		return low;
	}

	public void setLow (String low)
	{
		this.low = low;
	}

	public String getDate ()
	{
		return date;
	}

	public void setDate (String date)
	{
		this.date = date;
	}

	public String getClose ()
	{
		return close;
	}

	public void setClose (String close)
	{
		this.close = close;
	}

	@Override
	public String toString()
	{
		return "StockPricePoint [open = "+open+", vwap = "+vwap+", changePercent = "+changePercent+", change = "+change+", unadjustedVolume = "+unadjustedVolume+", volume = "+volume+", label = "+label+", high = "+high+", changeOverTime = "+changeOverTime+", low = "+low+", date = "+date+", close = "+close+"]";
	}
}