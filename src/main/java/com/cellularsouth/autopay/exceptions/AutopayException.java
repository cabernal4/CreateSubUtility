package com.cellularsouth.autopay.exceptions;

public class AutopayException extends RuntimeException {
	private static final long serialVersionUID = 7927687410352809420L;
	private String recommendedAction;
	
	public AutopayException(String message) {
		super(message);
	}
	
	public AutopayException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public AutopayException(String message, String recommendedAction) {
		super(message);
		this.recommendedAction = recommendedAction;
	}
	
	public AutopayException(String message, String recommendedAction, Throwable throwable) {
		super(message, throwable);
		this.recommendedAction = recommendedAction;
	}

	public void setRecommendedAction(String recommendedAction) {
		this.recommendedAction = recommendedAction;
	}

	public String getRecommendedAction() {
		return recommendedAction;
	}
}
