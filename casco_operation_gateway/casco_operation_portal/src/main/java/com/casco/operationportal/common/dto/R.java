package com.casco.operationportal.common.dto;

import java.io.Serializable;

/**
 * @program xiot_platform
 * @description:
 * @author: fredric
 * @create: 2020/03/03 10:20
 */

public class R<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String NO_LOGIN = "-1";

	public static final String SUCCESS = "0000";

	public static final String FAIL = "500";

	public static final String NO_PERMISSION = "403";

	private String msg = "success";

	private String code = SUCCESS;

	private T data;

	public R() {
		super();
	}

	public R(T data) {
		super();
		this.data = data;
	}

	public R(T data, String msg) {
		super();
		this.data = data;
		this.msg = msg;
	}

	public R(Throwable e) {
		super();
		this.msg = e.getMessage();
		this.code = FAIL;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static R<Boolean> rest(boolean result) {
		R<Boolean> r = new R<Boolean>();
		if (!result) {
			r.setCode(R.FAIL);
			r.setData(false);
		}
		return r;
	}
}
