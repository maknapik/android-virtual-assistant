package pl.edu.agh.virtualassistant.model.country;

import java.util.List;

public class RegionalBlocsItem{
	private List<Object> otherNames;
	private String acronym;
	private String name;
	private List<Object> otherAcronyms;

	public void setOtherNames(List<Object> otherNames){
		this.otherNames = otherNames;
	}

	public List<Object> getOtherNames(){
		return otherNames;
	}

	public void setAcronym(String acronym){
		this.acronym = acronym;
	}

	public String getAcronym(){
		return acronym;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setOtherAcronyms(List<Object> otherAcronyms){
		this.otherAcronyms = otherAcronyms;
	}

	public List<Object> getOtherAcronyms(){
		return otherAcronyms;
	}
}