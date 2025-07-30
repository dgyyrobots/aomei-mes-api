package com.dofast.module.mes.service.subclassexception;

import java.util.*;
import javax.validation.*;
import com.dofast.module.mes.controller.admin.subclassexception.vo.*;
import com.dofast.module.mes.dal.dataobject.subclassexception.SubclassExceptionDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 子类异常项配置 Service 接口
 *
 * @author 惠智造
 */
public interface SubclassExceptionService {

    /**
     * 创建子类异常项配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSubclassException(@Valid SubclassExceptionCreateReqVO createReqVO);

    /**
     * 更新子类异常项配置
     *
     * @param updateReqVO 更新信息
     */
    void updateSubclassException(@Valid SubclassExceptionUpdateReqVO updateReqVO);

    /**
     * 删除子类异常项配置
     *
     * @param id 编号
     */
    void deleteSubclassException(Long id);

    /**
     * 获得子类异常项配置
     *
     * @param id 编号
     * @return 子类异常项配置
     */
    SubclassExceptionDO getSubclassException(Long id);

    /**
     * 获得子类异常项配置列表
     *
     * @param ids 编号
     * @return 子类异常项配置列表
     */
    List<SubclassExceptionDO> getSubclassExceptionList(Collection<Long> ids);

    /**
     * 获得子类异常项配置分页
     *
     * @param pageReqVO 分页查询
     * @return 子类异常项配置分页
     */
    PageResult<SubclassExceptionDO> getSubclassExceptionPage(SubclassExceptionPageReqVO pageReqVO);

    /**
     * 获得子类异常项配置列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 子类异常项配置列表
     */
    List<SubclassExceptionDO> getSubclassExceptionList(SubclassExceptionExportReqVO exportReqVO);

}
