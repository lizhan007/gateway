package org.flume.hive.entity;

import org.flume.hive.entity.BaseMessage;

public class DigitEntity extends BaseMessage {

	private Integer value;
    private Long timestamp;
    
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return "EnumEntity [value=" + value + ", timestamp=" + timestamp + "]" + super.toString();
	}
    
    
}
