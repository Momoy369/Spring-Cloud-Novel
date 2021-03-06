package com.java2nb.novel.core.utils;

public enum OperationType {
	UPLOAD("Upload"),
	FETCH("Fetch"),
	DOWNLOAD("Download"),
	DELETE("Delete");
	
	private String values;
	
	public String getValues() {
		return this.values;
	}
	
	private OperationType( String values) {
		this.values = values;
	}
}