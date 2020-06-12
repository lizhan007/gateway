package com.casco.opgw.alarmhandler.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.casco.opgw.alarmhandler.entity.SysAlarmTable;
import com.casco.opgw.alarmhandler.mapper.SysAlarmTableMapper;
import com.casco.opgw.alarmhandler.service.SysAlarmTableService;
import org.springframework.stereotype.Service;

@Service
public class SysAlarmTableServiceImpl extends ServiceImpl<SysAlarmTableMapper, SysAlarmTable> implements SysAlarmTableService {

}
