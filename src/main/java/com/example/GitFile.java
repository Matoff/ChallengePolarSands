package com.example;

import java.util.Comparator;

public class GitFile implements Comparable<GitFile>{
	private String url ; 
	private String rawUrl ;
	private String type ;
	private int Lines;
	private int Bytes;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRawUrl() {
		return rawUrl;
	}
	public void setRawUrl(String rawUrl) {
		this.rawUrl = rawUrl;
	}
	@Override
	public int compareTo(GitFile o) {
		if (getType() == null || o.getType() == null) {
		      return 0;
		    }
		    return getType().compareTo(o.getType());
	}
	public int getLines() {
		return Lines;
	}
	public void setLines(int lines) {
		Lines = lines;
	}
	public int getBytes() {
		return Bytes;
	}
	public void setBytes(int bytes) {
		Bytes = bytes;
	}

}
