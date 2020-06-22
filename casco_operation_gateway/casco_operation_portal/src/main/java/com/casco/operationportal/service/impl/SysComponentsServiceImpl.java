package com.casco.operationportal.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.entity.SysComponents;
import com.casco.operationportal.mapper.SysComponentsMapper;
import com.casco.operationportal.service.SysComponentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组件表 服务实现类
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@Service
public class SysComponentsServiceImpl extends ServiceImpl<SysComponentsMapper, SysComponents> implements SysComponentsService {

    @Autowired
    SysComponentsMapper sysComponentsMapper;

    @Override
    public void getList(Page<SysComponents> page, Integer type, Long dictId) {
        sysComponentsMapper.getList(page, type, dictId);
    }
}
