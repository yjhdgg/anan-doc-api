package com.anan.document.modules.doc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anan.document.modules.doc.entity.Test;
import com.anan.document.modules.doc.service.TestService;
import com.anan.document.modules.doc.mapper.TestMapper;
import org.springframework.stereotype.Service;

/**
* @author anan
* @description 针对表【test(实验室表)】的数据库操作Service实现
* @createDate 2023-02-14 13:33:50
*/
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test>
    implements TestService{

}




