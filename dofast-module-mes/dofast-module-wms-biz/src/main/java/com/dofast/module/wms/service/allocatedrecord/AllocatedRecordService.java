package com.dofast.module.wms.service.allocatedrecord;

import java.util.*;
import javax.validation.*;
import com.dofast.module.wms.controller.admin.allocatedrecord.vo.*;
import com.dofast.module.wms.dal.dataobject.allocatedrecord.AllocatedRecordDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 调拨单身记录 Service 接口
 *
 * @author 惠智造
 */
public interface AllocatedRecordService {

    /**
     * 创建调拨单身记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAllocatedRecord(@Valid AllocatedRecordCreateReqVO createReqVO);

    /**
     * 更新调拨单身记录
     *
     * @param updateReqVO 更新信息
     */
    void updateAllocatedRecord(@Valid AllocatedRecordUpdateReqVO updateReqVO);

    /**
     * 删除调拨单身记录
     *
     * @param id 编号
     */
    void deleteAllocatedRecord(Long id);

    /**
     * 获得调拨单身记录
     *
     * @param id 编号
     * @return 调拨单身记录
     */
    AllocatedRecordDO getAllocatedRecord(Long id);

    /**
     * 获得调拨单身记录列表
     *
     * @param ids 编号
     * @return 调拨单身记录列表
     */
    List<AllocatedRecordDO> getAllocatedRecordList(Collection<Long> ids);

    /**
     * 获得调拨单身记录分页
     *
     * @param pageReqVO 分页查询
     * @return 调拨单身记录分页
     */
    PageResult<AllocatedRecordDO> getAllocatedRecordPage(AllocatedRecordPageReqVO pageReqVO);

    /**
     * 获得调拨单身记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 调拨单身记录列表
     */
    List<AllocatedRecordDO> getAllocatedRecordList(AllocatedRecordExportReqVO exportReqVO);

    void createBatchAllocatedRecord(List<AllocatedRecordDO> createList);

    void updateAllocatedRecordBatch(List<AllocatedRecordDO> updateList);

    void deleteAllocatedRecordBatch(List<Long> ids);


}
