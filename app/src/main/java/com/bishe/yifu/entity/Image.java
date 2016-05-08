package com.bishe.yifu.entity;

import java.io.Serializable;

public class Image implements Serializable {
	public String name;
	public String path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Image(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}

	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}

}
