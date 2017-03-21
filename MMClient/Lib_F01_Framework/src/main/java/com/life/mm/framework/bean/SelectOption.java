/*
 * Copyright (C) 2013 China Construction Bank
 *
 * 本程序中所包含的信息属于机密信息，如无中国建设银行的书面许可，任何人都无权复制或利用。
 */
package com.life.mm.framework.bean;

/**
 * $Id: Option.java 584 2014-03-19 08:38:18Z zhangyb $
 * 
 * 
 * 
 * @author Genty
 * 
 */
public class SelectOption {

	private String label;

	private Object value;

	private boolean checked;

	/** 可否点击 */
	private boolean clickEnabled;
	
	public SelectOption() {
	}

	public SelectOption(String label, Object value) {
		this(label, value, false);
	}

	public SelectOption(String label, Object value, boolean checked) {
		this(label, value, checked, true);
	}

	public SelectOption(String label, Object value, boolean checked, boolean clickEnabled) {
		super();
		this.label = label;
		this.value = value;
		this.checked = checked;
		this.clickEnabled = clickEnabled;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * @return the clickEnabled
	 */
	public boolean isClickEnabled() {
		return clickEnabled;
	}

	/**
	 * @param clickEnabled the clickEnabled to set
	 */
	public void setClickEnabled(boolean clickEnabled) {
		this.clickEnabled = clickEnabled;
	}

}
