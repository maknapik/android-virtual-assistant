package pl.edu.agh.virtualassistant.model.weather;

public class Temp{
	private double min;
	private double max;
	private int eve;
	private double night;
	private double day;
	private double morn;

	public void setMin(double min){
		this.min = min;
	}

	public double getMin(){
		return min;
	}

	public void setMax(double max){
		this.max = max;
	}

	public double getMax(){
		return max;
	}

	public void setEve(int eve){
		this.eve = eve;
	}

	public int getEve(){
		return eve;
	}

	public void setNight(double night){
		this.night = night;
	}

	public double getNight(){
		return night;
	}

	public void setDay(double day){
		this.day = day;
	}

	public double getDay(){
		return day;
	}

	public void setMorn(double morn){
		this.morn = morn;
	}

	public double getMorn(){
		return morn;
	}
}
