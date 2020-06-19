package org.flume.hive.entity;

import java.util.ArrayList;
import java.util.List;

public class BaseMessage {
	private String msgType;
    private List<String> keys = new ArrayList<>();
    private String major;

    private String lineTag;
    private String regionTag;
    private String srcIdTag;
    private String typeTag;
    private String pointcodeTag;
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public List<String> getKeys() {
		return keys;
	}
	public void setKeys(List<String> keys) {
		this.keys = keys;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getLineTag() {
		return lineTag;
	}
	public void setLineTag(String lineTag) {
		this.lineTag = lineTag;
	}
	public String getRegionTag() {
		return regionTag;
	}
	public void setRegionTag(String regionTag) {
		this.regionTag = regionTag;
	}
	public String getSrcIdTag() {
		return srcIdTag;
	}
	public void setSrcIdTag(String srcIdTag) {
		this.srcIdTag = srcIdTag;
	}
	public String getTypeTag() {
		return typeTag;
	}
	public void setTypeTag(String typeTag) {
		this.typeTag = typeTag;
	}
	public String getPointcodeTag() {
		return pointcodeTag;
	}
	public void setPointcodeTag(String pointcodeTag) {
		this.pointcodeTag = pointcodeTag;
	}
	@Override
	public String toString() {
		return "BaseMessage [msgType=" + msgType + ", keys=" + keys + ", major=" + major + ", lineTag=" + lineTag
				+ ", regionTag=" + regionTag + ", srcIdTag=" + srcIdTag + ", typeTag=" + typeTag + ", pointcodeTag="
				+ pointcodeTag + "]";
	}
	
    
}
