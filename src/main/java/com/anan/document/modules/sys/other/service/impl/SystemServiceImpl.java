package com.anan.document.modules.sys.other.service.impl;

import com.anan.document.modules.user.UserUtils;
import com.yf.boot.base.aspect.data.BaseDataService;
import com.anan.document.modules.sys.other.mapper.SystemMapper;
import com.anan.document.modules.sys.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据保护的实现
 * @author bool
 */
@Service
public class SystemServiceImpl implements BaseDataService {

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public int countSystemData(String tableName, List<String> ids) {
        return systemMapper.countSysData(tableName, ids);
    }

    @Override
    public String currentUserId() {
        return UserUtils.getUserId();
    }

}
