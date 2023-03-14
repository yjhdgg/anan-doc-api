package com.anan.document.modules.constant;

import org.quartz.JobDataMap;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/2/3 9:17
 * @Version 1.0
 */
public class QuartzConstant {
    /**
     * 环境物质报告
     */
    public static final String[] ENVIRONMENT_REPORT_EMAIL = new String[]{"zhaogd@henghuiic.com", "jiangcuilian@henghuiic.com"};

    /**
     * MSDS
     */
    public static final String[] MSDS_EMAIL = new String[]{
            "zhaogd@henghuiic.com", "jiangcuilian@henghuiic.com",
    };
    /**
     * 供应商资质
     */
    public static final String[] SUPPLIER_EMAIL = new String[]{"carol.zhang@henghuiic.com", "zhaogd@henghuiic.com", "jiangcuilian@henghuiic.com"};

    /**
     * 计量管理
     */
    public static final String[] MEA_MANAGEMENT_EMAIL = new String[]{
            "yw@henghuiic.com", "tianj@henghuiic.com", "carol.zhang@henghuiic.com"
    };
    /**
     * 临时文件
     */
    public static final String[] TEMPT_FILE_EMAIL = new String[]{
            "leadframe-pe@henghuiic.com", "wangying@henghuiic.com", "luoxiaohua@henghuiic.com",
            "sunxuejiao@henghuiic.com", "wangcc@henghuiic.com", "leadframe-pe@henghuiic.com",
            "zcb@henghuiic.com", "yangdonghai@henghuiic.com", "jyh@henghuiic.com",
            "leadframe-qa@henghuiic.com", "zhangna@henghuiic.com"
    };

    /**
     * 临时文件载带
     */
    public static final String[] TEMPT_2_FILE_EMAIL = new String[]{
            "luoxiaohua@henghuiic.com","zcb@henghuiic.com"
    };

    /**
     * 临时文件eSIM
     */
    public static final String[] TEMPT_3_FILE_EMAIL = new String[]{
            "sunxuejiao@henghuiic.com","yangdonghai@henghuiic.com"
    };

    /**
     * 临时文件模块
     */
    public static final String[] TEMPT_5_FILE_EMAIL = new String[]{
            "wcc@henghuiic.com","jyh@henghuiic.com"
    };

    /**
     * 临时文件引线框架
     */
    public static final String[] TEMPT_6_FILE_EMAIL = new String[]{
            "zhangna@henghuiic.com","wangying@henghuiic.com","leadframe-qa@henghuiic.com","leadframe-pe@henghuiic.com"
    };

    /**
     * 变更通知和技术文件
     */
    public static final String[] CHANGE_NOTICE_OR_TECHNICAL_DOC_EMAIL = new String[]{
            "leadframe-qa@henghuiic.com", "zhangna@henghuiic.com", "leadframe-pe@henghuiic.com",
            "wangying@henghuiic.com", "luoxiaohua@henghuiic.com", "sunxuejiao@henghuiic.com",
            "wangcc@henghuiic.com", "leadframe-pe@henghuiic.com", "zcb@henghuiic.com",
            "yangdonghai@henghuiic.com", "jyh@henghuiic.com"
    };

    public static final String TODAY = "today";
    public static final String DOC_TYPE = "docType";
    public static final String CUSTOMER_NAME = "customerName";

    public static final String SUPPLIER_DOC_TYPE = "1607169208351711234";
    public static final String MEA_MANAGEMENT_DOC_TYPE = "1603576853188853762";
    public static final String MEA_MANAGEMENT_EQUIP_NUM = "equipNumber";
    public static final String MEA_MANAGEMENT_EQUIP_NAME = "equipName";
    public static final String FILE_CODE = "fileCode";
    public static final String TEMPT_FILE_DOC_TYPE = "1603584109112475650";
    public static final String ENVIRONMENT_REPORT_DOC_TYPE = "1603569246575280129";
    public static final String DOC_ENTITY = "docEntity";
    public static final String FILE_NAME = "fileName";
    public static final String MSDS_DOC_TYPE = "1607564404251893761";
    public static final String ONETYPE = "oneType";
}
