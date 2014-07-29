package com.cspire.si.autopay.utils;

public enum Environment {
	
	SAND("SAND"), DEV("DEV"), INT("INT"), TEST("TEST"), LOCALSAND("LOCALSAND"), LOCALDEV("LOCALDEV"),
	LOCALINT("LOCALINT"), LOCALTEST("LOCALTEST");
	
	
	private Environment(String env) {
		value = env;
	}
	
	private String value;
	
	public String getValue() {
		return value;
	}
	
	public static Environment getFromString(String value) {
		for(Environment env : values()) {
			if(env.getValue().equals(value)){
				return env;
			}
		}
		
		return null;
	}
}
