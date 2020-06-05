package com.casco.opgw.com.message.signal;

import lombok.Data;

@Data
public class AnalogMessage extends BaseMessage{

    private Float value;
    private Long timestamp;
}
