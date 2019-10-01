package com.medialink.submission3.model.tv;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvResponse{

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<TvItem> results;

	@SerializedName("total_results")
	private int totalResults;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<TvItem> getResults() {
		return results;
	}

	public void setResults(List<TvItem> results) {
		this.results = results;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
}