package com.casco.operationportal.common.exception;

public enum ErrorCodeEnum implements BaseErrCodeInterface {

	COMPONENT_STATUS_ERR("1001", "当前组件正在运行，请停止后再尝试该操作"),
	COMPONENT_STARTED("1002", "当前组件正在运行，请勿重复启动"),

	SYS_PARAMETER_INVALID("9980", "参数不合法"),
	SYS_DATA_ERR("9990", "系统数据异常"),
	SYS_ERR("9999", "系统错误");


	private String code;

	private String msg;

	public String msg() {
		return msg;
	}

	public String code() {
		return code;
	}

	ErrorCodeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static ErrorCodeEnum getEnum(int code) {
		for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
			if (ele.code().equals(code)) {
				return ele;
			}
		}
		return null;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public ErrorCodeEnum setCode(String code) {
		this.code = code;
		return this;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public ErrorCodeEnum setMsg(String msg) {
		this.msg = msg;
		return this;
	}
}
