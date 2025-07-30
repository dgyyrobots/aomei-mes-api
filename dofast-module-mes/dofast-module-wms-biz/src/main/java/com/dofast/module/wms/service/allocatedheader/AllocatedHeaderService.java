package com.dofast.module.wms.service.allocatedheader;

import java.util.*;
import javax.validation.*;
import com.dofast.module.wms.controller.admin.allocatedheader.vo.*;
import com.dofast.module.wms.dal.dataobject.allocatedheader.AllocatedHeaderDO;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.module.wms.dal.dataobject.allocatedheader.AllocatedTxBean;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueTxBean;
import com.dofast.module.wms.dal.dataobject.allocatedheader.AllocatedTxBean;

/**
 * 调拨单头 Service 接口
 *
 * @author 惠智造
 */
public interface AllocatedHeaderService {

    /**
     * 创建调拨单头
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAllocatedHeader(@Valid AllocatedHeaderCreateReqVO createReqVO);

    /**
     * 更新调拨单头
     *
     * @param updateReqVO 更新信息
     */
    void updateAllocatedHeader(@Valid AllocatedHeaderUpdateReqVO updateReqVO);

    /**
     * 删除调拨单头
     *
     * @param id 编号
     */
    void deleteAllocatedHeader(Long id);

    /**
     * 获得调拨单头
     *
     * @param id 编号
     * @return 调拨单头
     */
    AllocatedHeaderDO getAllocatedHeader(Long id);

    /**
     * 获得调拨单头列表
     *
     * @param ids 编号
     * @return 调拨单头列表
     */
    List<AllocatedHeaderDO> getAllocatedHeaderList(Collection<Long> ids);

    /**
     * 获得调拨单头分页
     *
     * @param pageReqVO 分页查询
     * @return 调拨单头分页
     */
    PageResult<AllocatedHeaderDO> getAllocatedHeaderPage(AllocatedHeaderPageReqVO pageReqVO);

    /**
     * 获得调拨单头列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 调拨单头列表
     */
    List<AllocatedHeaderDO> getAllocatedHeaderList(AllocatedHeaderExportReqVO exportReqVO);

    /**
     * 调拨单绑定工单后, 获取工单的BOM信息并校验库存是否足够
     * @param workOrderNo
     * @return
     */
    List<Map<String,Object>> initWorkOrderBom(String workOrderNo);

    List<AllocatedTxBean> getTxBeans(Long allocatedId);


    List<AllocatedHeaderDO> getAllocatedHeaderListByBatchCodeList(List<String> batchCodeList);

}
