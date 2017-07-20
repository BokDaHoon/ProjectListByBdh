package kr.co.lotte.vo;

import java.io.Serializable;

public class BodyInfo implements Serializable {
	private String codeValue;
	private String value;
	
	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
