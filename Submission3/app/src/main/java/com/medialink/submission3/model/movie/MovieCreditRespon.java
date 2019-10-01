package com.medialink.submission3.model.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCreditRespon{

	@SerializedName("cast")
	private List<CastItem> cast;

	@SerializedName("id")
	private int id;

	@SerializedName("crew")
	private List<CrewItem> crew;

	public List<CastItem> getCast() {
		return cast;
	}

	public void setCast(List<CastItem> cast) {
		this.cast = cast;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<CrewItem> getCrew() {
		return crew;
	}

	public void setCrew(List<CrewItem> crew) {
		this.crew = crew;
	}
}