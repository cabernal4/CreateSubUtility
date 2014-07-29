package com.cspire.si.autopay.models;

public enum RbmProductTypeValue {
	
	C_SPIRE_SERVICE (1),
	VOICE (2),
	SMS (3),
	MMS (4),
	DATA_3G (5),
	DATA_LTE (6),
	CONTENT (7),
	C_SPIRE_SUBSCRIPTION (8),
	CONTRACT(9),
	INTERNATIONAL_ROAMING (10),
	RINGBACK_TONES (11),
	DATA_FEATURE (12),
	DATA_SERVICE (13);
	
	private int value;
	
	RbmProductTypeValue(int value) {
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}
	
	public static RbmProductTypeValue fromValue(int value) {
		for(RbmProductTypeValue rbmProductTypeValue : RbmProductTypeValue.values()) {
			if(rbmProductTypeValue.value() == value) {
				return rbmProductTypeValue;
			}
		}
		
		return null;
	}
}
