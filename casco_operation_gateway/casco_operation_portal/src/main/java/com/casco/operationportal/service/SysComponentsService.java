package com.casco.operationportal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.casco.operationportal.entity.SysComponents;

/**
 * <p>
 * 组件表 服务类
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
public interface SysComponentsService extends IService<SysComponents> {

    void getList(Page<SysComponents> page, Integer type, Long dictId);

}
