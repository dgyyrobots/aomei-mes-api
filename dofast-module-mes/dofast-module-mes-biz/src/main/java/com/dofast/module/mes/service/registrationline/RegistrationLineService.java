package com.dofast.module.mes.service.registrationline;

import java.util.*;
import javax.validation.*;
import com.dofast.module.mes.controller.admin.registrationline.vo.*;
import com.dofast.module.mes.dal.dataobject.registrationline.RegistrationLineDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 计时登记记录 Service 接口
 *
 * @author 惠智造
 */
public interface RegistrationLineService {

    /**
     * 创建计时登记记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRegistrationLine(@Valid RegistrationLineCreateReqVO createReqVO);

    /**
     * 更新计时登记记录
     *
     * @param updateReqVO 更新信息
     */
    void updateRegistrationLine(@Valid RegistrationLineUpdateReqVO updateReqVO);

    /**
     * 删除计时登记记录
     *
     * @param id 编号
     */
    void deleteRegistrationLine(Long id);

    /**
     * 获得计时登记记录
     *
     * @param id 编号
     * @return 计时登记记录
     */
    RegistrationLineDO getRegistrationLine(Long id);

    /**
     * 获得计时登记记录列表
     *
     * @param ids 编号
     * @return 计时登记记录列表
     */
    List<RegistrationLineDO> getRegistrationLineList(Collection<Long> ids);

    /**
     * 获得计时登记记录分页
     *
     * @param pageReqVO 分页查询
     * @return 计时登记记录分页
     */
    PageResult<RegistrationLineDO> getRegistrationLinePage(RegistrationLinePageReqVO pageReqVO);

    /**
     * 获得计时登记记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 计时登记记录列表
     */
    List<RegistrationLineDO> getRegistrationLineList(RegistrationLineExportReqVO exportReqVO);

}
