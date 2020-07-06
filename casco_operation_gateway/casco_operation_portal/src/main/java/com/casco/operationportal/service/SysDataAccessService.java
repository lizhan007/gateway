package com.casco.operationportal.service;

import com.casco.operationportal.entity.SysDataAccess;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 数据接入表 服务类
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
public interface SysDataAccessService extends IService<SysDataAccess> {

    void start(Long id);
    void stop(Long id);
}
