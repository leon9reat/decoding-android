package com.medialink.submission4.model.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCreditRespon{

	@SerializedName("cast")
	private List<MovieCastItem> cast;

	@SerializedName("id")
	private int id;

	@SerializedName("crew")
	private List<CrewItem> crew;

	public List<MovieCastItem> getCast() {
		return cast;
	}

	public void setCast(List<MovieCastItem> cast) {
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