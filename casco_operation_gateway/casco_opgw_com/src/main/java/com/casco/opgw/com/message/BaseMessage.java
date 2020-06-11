package com.casco.opgw.com.message;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BaseMessage {

    private String msgType;
    private List<String> keys = new ArrayList<>();
    private String major;

    private String lineTag;
    private String regionTag;
    private String srcIdTag;
    private String typeTag;
    private String pointcodeTag;

}
