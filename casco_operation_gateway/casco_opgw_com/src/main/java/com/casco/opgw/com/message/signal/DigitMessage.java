package com.casco.opgw.com.message.signal;

import lombok.Data;

@Data
public class DigitMessage  extends BaseMessage{

    private Integer value;
    private Long timestamp;
}
