package com.casco.opgw.com.message;

import lombok.Data;

@Data
public class EnumMessage  extends BaseMessage{

    private Integer value;
    private Long timestamp;
}
