package com.dofast.module.mes.service.exceptionlevelconfig;

import java.util.*;
import javax.validation.*;
import com.dofast.module.mes.controller.admin.exceptionlevelconfig.vo.*;
import com.dofast.module.mes.dal.dataobject.exceptionlevelconfig.ExceptionLevelConfigDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 异常等级配置 Service 接口
 *
 * @author 惠智造
 */
public interface ExceptionLevelConfigService {

    /**
     * 创建异常等级配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createExceptionLevelConfig(@Valid ExceptionLevelConfigCreateReqVO createReqVO);

    /**
     * 更新异常等级配置
     *
     * @param updateReqVO 更新信息
     */
    void updateExceptionLevelConfig(@Valid ExceptionLevelConfigUpdateReqVO updateReqVO);

    /**
     * 删除异常等级配置
     *
     * @param id 编号
     */
    void deleteExceptionLevelConfig(Long id);

    /**
     * 获得异常等级配置
     *
     * @param id 编号
     * @return 异常等级配置
     */
    ExceptionLevelConfigDO getExceptionLevelConfig(Long id);

    /**
     * 获得异常等级配置列表
     *
     * @param ids 编号
     * @return 异常等级配置列表
     */
    List<ExceptionLevelConfigDO> getExceptionLevelConfigList(Collection<Long> ids);

    /**
     * 获得异常等级配置分页
     *
     * @param pageReqVO 分页查询
     * @return 异常等级配置分页
     */
    PageResult<ExceptionLevelConfigDO> getExceptionLevelConfigPage(ExceptionLevelConfigPageReqVO pageReqVO);

    /**
     * 获得异常等级配置列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 异常等级配置列表
     */
    List<ExceptionLevelConfigDO> getExceptionLevelConfigList(ExceptionLevelConfigExportReqVO exportReqVO);

}
