package com.medialink.submission3.model.movie;

import com.google.gson.annotations.SerializedName;

public class ProductionCountriesItem{
	public String getIso31661() {
		return iso31661;
	}

	public void setIso31661(String iso31661) {
		this.iso31661 = iso31661;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SerializedName("iso_3166_1")
	private String iso31661;

	@SerializedName("name")
	private String name;
}