package com.dofast.module.pro.service.workorder;

import java.math.BigInteger;
import java.util.*;
import javax.validation.*;
import com.dofast.module.pro.controller.admin.workorder.vo.*;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 生产工单 Service 接口
 *
 * @author 芋道源码
 */
public interface WorkorderService {
    public String checkWorkorderCodeUnique(WorkorderBaseVO baseVO);

    /**
     * 创建生产工单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWorkorder(@Valid WorkorderCreateReqVO createReqVO);

    /**
     * 更新生产工单
     *
     * @param updateReqVO 更新信息
     */
    void updateWorkorder(@Valid WorkorderUpdateReqVO updateReqVO);


    /**
     * 删除生产工单
     *
     * @param id 编号
     */
    void deleteWorkorder(Long id);

    /**
     * 获得生产工单
     *
     * @param id 编号
     * @return 生产工单
     */
    WorkorderDO getWorkorder(Long id);

    /**
     * 获得生产工单
     *
     * @param workorderCode 工单编号
     * @return 生产工单
     */
    WorkorderDO getWorkorder(String workorderCode);


    /**
     * 获得生产工单列表
     *
     * @param ids 编号
     * @return 生产工单列表
     */
    List<WorkorderDO> getWorkorderList(Collection<Long> ids);
    /**
     * 获得生产工单列表
     *
     * @param reqVO 条件
     * @return 生产工单列表
     */
    List<WorkorderDO> getWorkorderList(WorkorderListAllReqVO reqVO);

    /**
     * 获得生产工单分页
     *
     * @param pageReqVO 分页查询
     * @return 生产工单分页
     */
    PageResult<WorkorderDO> getWorkorderPage(WorkorderPageReqVO pageReqVO);

    /**
     * 获得生产工单列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 生产工单列表
     */
    List<WorkorderDO> getWorkorderList(WorkorderExportReqVO exportReqVO);

    List<WorkorderDO> getWorkorderList(WorkorderListVO listVO);

    List<WorkorderDO> getWorkderByParentId(BigInteger parentId);

    /**
     * 根据sourceCode获取Workorder
     * @param sourceCode
     * @return
     */
    List<WorkorderDO> getWorkorderBySourceCode(String sourceCode);

    Map<String, Integer>  getCountMonthWorkorderLastYear();

    Map<String, Integer>  getCountMonthWorkorderThisYear();

    void updateBatch(List<WorkorderDO> workorderList);
}
