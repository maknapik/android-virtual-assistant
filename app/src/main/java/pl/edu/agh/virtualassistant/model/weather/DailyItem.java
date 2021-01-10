package pl.edu.agh.virtualassistant.model.weather;

import java.util.List;

public class DailyItem{
	private double rain;
	private int sunrise;
	private Temp temp;
	private double uvi;
	private int pressure;
	private int clouds;
	private FeelsLike feelsLike;
	private int dt;
	private int pop;
	private int windDeg;
	private double dewPoint;
	private int sunset;
	private List<WeatherItem> weather;
	private int humidity;
	private double windSpeed;

	public void setRain(double rain){
		this.rain = rain;
	}

	public double getRain(){
		return rain;
	}

	public void setSunrise(int sunrise){
		this.sunrise = sunrise;
	}

	public int getSunrise(){
		return sunrise;
	}

	public void setTemp(Temp temp){
		this.temp = temp;
	}

	public Temp getTemp(){
		return temp;
	}

	public void setUvi(double uvi){
		this.uvi = uvi;
	}

	public double getUvi(){
		return uvi;
	}

	public void setPressure(int pressure){
		this.pressure = pressure;
	}

	public int getPressure(){
		return pressure;
	}

	public void setClouds(int clouds){
		this.clouds = clouds;
	}

	public int getClouds(){
		return clouds;
	}

	public void setFeelsLike(FeelsLike feelsLike){
		this.feelsLike = feelsLike;
	}

	public FeelsLike getFeelsLike(){
		return feelsLike;
	}

	public void setDt(int dt){
		this.dt = dt;
	}

	public int getDt(){
		return dt;
	}

	public void setPop(int pop){
		this.pop = pop;
	}

	public int getPop(){
		return pop;
	}

	public void setWindDeg(int windDeg){
		this.windDeg = windDeg;
	}

	public int getWindDeg(){
		return windDeg;
	}

	public void setDewPoint(double dewPoint){
		this.dewPoint = dewPoint;
	}

	public double getDewPoint(){
		return dewPoint;
	}

	public void setSunset(int sunset){
		this.sunset = sunset;
	}

	public int getSunset(){
		return sunset;
	}

	public void setWeather(List<WeatherItem> weather){
		this.weather = weather;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public void setHumidity(int humidity){
		this.humidity = humidity;
	}

	public int getHumidity(){
		return humidity;
	}

	public void setWindSpeed(double windSpeed){
		this.windSpeed = windSpeed;
	}

	public double getWindSpeed(){
		return windSpeed;
	}
}