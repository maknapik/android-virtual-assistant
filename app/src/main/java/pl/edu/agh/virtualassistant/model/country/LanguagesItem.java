package pl.edu.agh.virtualassistant.model.country;

public class LanguagesItem{
	private String nativeName;
	private String iso6392;
	private String name;
	private String iso6391;

	public void setNativeName(String nativeName){
		this.nativeName = nativeName;
	}

	public String getNativeName(){
		return nativeName;
	}

	public void setIso6392(String iso6392){
		this.iso6392 = iso6392;
	}

	public String getIso6392(){
		return iso6392;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setIso6391(String iso6391){
		this.iso6391 = iso6391;
	}

	public String getIso6391(){
		return iso6391;
	}
}
