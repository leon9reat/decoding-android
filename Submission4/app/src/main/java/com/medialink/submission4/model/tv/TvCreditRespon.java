package com.medialink.submission4.model.tv;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvCreditRespon{

	@SerializedName("cast")
	private List<TvCastItem> cast;

	@SerializedName("id")
	private int id;

	@SerializedName("crew")
	private List<Object> crew;

	public void setCast(List<TvCastItem> cast){
		this.cast = cast;
	}

	public List<TvCastItem> getCast(){
		return cast;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCrew(List<Object> crew){
		this.crew = crew;
	}

	public List<Object> getCrew(){
		return crew;
	}
}