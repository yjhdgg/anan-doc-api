package com.anan.document.modules.sys.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.anan.document.modules.sys.log.dto.SysLogDTO;
import com.anan.document.modules.sys.log.entity.SysLog;
import com.yf.boot.base.api.api.dto.PagingReqDTO;

/**
* <p>
* 系统日志业务类
* </p>
*
* @author 聪明笨狗
* @since 2020-04-28 10:23
*/
public interface SysLogService extends IService<SysLog> {

    /**
    * 分页查询数据
    * @param reqDTO
    * @return
    */
    IPage<SysLogDTO> paging(PagingReqDTO<SysLogDTO> reqDTO);
}
