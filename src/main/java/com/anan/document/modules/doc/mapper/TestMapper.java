package com.anan.document.modules.doc.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anan.document.modules.doc.entity.Test;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author anan
* @description 针对表【test(实验室表)】的数据库操作Mapper
* @createDate 2023-02-14 13:33:50
* @Entity com.yf.document.modules.doc.entity.Test
*/
public interface TestMapper extends BaseMapper<Test> {

    Page<Test> myPage(@Param("current") int current, @Param("size") int size);
}




