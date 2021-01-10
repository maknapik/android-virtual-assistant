package pl.edu.agh.virtualassistant.model.weather;

import java.util.List;

public class CitiesInCircleWeather {
	private int count;
	private String cod;
	private String message;
	private List<ListItem> list;

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setCod(String cod){
		this.cod = cod;
	}

	public String getCod(){
		return cod;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setList(List<ListItem> list){
		this.list = list;
	}

	public List<ListItem> getList(){
		return list;
	}
}