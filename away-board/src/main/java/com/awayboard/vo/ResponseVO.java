package com.awayboard.vo;

/**
 * The Class ResponseVO.
 *
 * @param <T> the generic type
 */
public class ResponseVO<T> {
	
	/** The results. */
	private T results;
	
	/**
	 * Instantiates a new response VO.
	 *
	 * @param results the results
	 */
	public ResponseVO(T results) {
		this.results = results;
	}
	
	/**
	 * Gets the results.
	 *
	 * @return the results
	 */
	public T getResults() {
		return this.results;
	}
}
