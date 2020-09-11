package uy.com.tmwc.facturator.entity;

import java.io.Serializable;

import uy.com.tmwc.facturator.utils.MessageKey;

public class RemoteQueryResponse<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private T data;

	protected MessageKey messageKey = MessageKey.OK;

	protected String message;

	protected String detail;

	protected boolean success;

	public RemoteQueryResponse() {
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean value) {
		this.success = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public MessageKey getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(MessageKey value) {
		this.messageKey = value;
	}

}
