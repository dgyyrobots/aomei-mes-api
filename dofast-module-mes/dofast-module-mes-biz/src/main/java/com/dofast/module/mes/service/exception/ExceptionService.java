package com.dofast.module.mes.service.exception;

import java.util.*;
import javax.validation.*;
import com.dofast.module.mes.controller.admin.exception.vo.*;
import com.dofast.module.mes.dal.dataobject.exception.ExceptionDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 异常登记 Service 接口
 *
 * @author 惠智造
 */
public interface ExceptionService {

    /**
     * 创建异常登记
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createException(@Valid ExceptionCreateReqVO createReqVO);

    /**
     * 更新异常登记
     *
     * @param updateReqVO 更新信息
     */
    void updateException(@Valid ExceptionUpdateReqVO updateReqVO);

    /**
     * 删除异常登记
     *
     * @param id 编号
     */
    void deleteException(Long id);

    /**
     * 获得异常登记
     *
     * @param id 编号
     * @return 异常登记
     */
    ExceptionDO getException(Long id);

    /**
     * 获得异常登记列表
     *
     * @param ids 编号
     * @return 异常登记列表
     */
    List<ExceptionDO> getExceptionList(Collection<Long> ids);

    /**
     * 获得异常登记分页
     *
     * @param pageReqVO 分页查询
     * @return 异常登记分页
     */
    PageResult<ExceptionDO> getExceptionPage(ExceptionPageReqVO pageReqVO);

    /**
     * 获得异常登记列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 异常登记列表
     */
    List<ExceptionDO> getExceptionList(ExceptionExportReqVO exportReqVO);

}
