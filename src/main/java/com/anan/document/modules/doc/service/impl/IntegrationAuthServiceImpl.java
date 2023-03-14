package com.anan.document.modules.doc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anan.document.modules.doc.entity.IntegrationAuth;
import com.anan.document.modules.doc.service.IntegrationAuthService;
import com.anan.document.modules.doc.mapper.IntegrationAuthMapper;
import org.springframework.stereotype.Service;

/**
* @author anan
* @description 针对表【integration_auth(一体化文件权限表)】的数据库操作Service实现
* @createDate 2023-01-20 12:41:46
*/
@Service
public class IntegrationAuthServiceImpl extends ServiceImpl<IntegrationAuthMapper, IntegrationAuth>
    implements IntegrationAuthService{

}




