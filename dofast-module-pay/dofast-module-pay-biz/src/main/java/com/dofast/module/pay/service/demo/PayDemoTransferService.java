package com.dofast.module.pay.service.demo;

import com.dofast.framework.common.pojo.PageParam;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferCreateReqVO;
import com.dofast.module.pay.dal.dataobject.demo.PayDemoTransferDO;

import javax.validation.Valid;

/**
 * 示例转账业务 Service 接口
 *
 * @author jason
 */
public interface PayDemoTransferService {

    /**
     * 创建转账业务示例订单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDemoTransfer(@Valid PayDemoTransferCreateReqVO createReqVO);



    /**
     * 获得转账业务示例订单分页
     *
     * @param pageVO 分页查询参数
     */
    PageResult<PayDemoTransferDO> getDemoTransferPage(PageParam pageVO);
}
