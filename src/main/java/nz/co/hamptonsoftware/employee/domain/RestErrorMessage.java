package nz.co.hamptonsoftware.employee.domain;

import java.io.Serializable;

public class RestErrorMessage implements Serializable {

	private static final long serialVersionUID = -3723321275931458991L;
 
	private String summaryMessage;
	private String detailMessage;
	
	public RestErrorMessage(String summaryMessage) {
		this.summaryMessage = summaryMessage;
	}
	
	public RestErrorMessage(String summaryMessage, String detailMessage) {
		this.summaryMessage = summaryMessage;
		this.detailMessage = detailMessage;
	}
	
	/**
	 * @return the summaryMessage
	 */
	public String getSummaryMessage() {
		return this.summaryMessage;
	}
	/**
	 * @param summarymessage the summaryMessage to set
	 */
	public void setSummaryMessage(String summaryMessage) {
		this.summaryMessage = summaryMessage;
	}
	/**
	 * @return the detailMessage
	 */
	public String getDetailMessage() {
		return this.detailMessage;
	}
	/**
	 * @param detailMessage the detailMessage to set
	 */
	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}
	
}
