package com.medialink.submission4.model.movie;

import com.google.gson.annotations.SerializedName;

public class GenresItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}