package pl.edu.agh.virtualassistant.model.weather;

public class MinutelyItem{
	private int dt;
	private int precipitation;

	public void setDt(int dt){
		this.dt = dt;
	}

	public int getDt(){
		return dt;
	}

	public void setPrecipitation(int precipitation){
		this.precipitation = precipitation;
	}

	public int getPrecipitation(){
		return precipitation;
	}
}
