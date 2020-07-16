package com.casco.opgw.kafkaalarm.service.impl;

import com.casco.opgw.kafkaalarm.entity.AdsAlarmCountTable;
import com.casco.opgw.kafkaalarm.mapper.AdsAlarmCountTableMapper;
import com.casco.opgw.kafkaalarm.service.AdsAlarmCountTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author yeexun
 * @since 2020-07-15
 */
@Service
public class AdsAlarmCountTableServiceImp extends ServiceImpl<AdsAlarmCountTableMapper, AdsAlarmCountTable> implements AdsAlarmCountTableService {
}
