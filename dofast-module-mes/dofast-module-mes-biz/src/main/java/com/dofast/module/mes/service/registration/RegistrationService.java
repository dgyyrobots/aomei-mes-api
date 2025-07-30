package com.dofast.module.mes.service.registration;

import java.util.*;
import javax.validation.*;
import com.dofast.module.mes.controller.admin.registration.vo.*;
import com.dofast.module.mes.dal.dataobject.registration.RegistrationDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 计时登记 Service 接口
 *
 * @author 惠智造
 */
public interface RegistrationService {

    /**
     * 创建计时登记
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRegistration(@Valid RegistrationCreateReqVO createReqVO);

    /**
     * 更新计时登记
     *
     * @param updateReqVO 更新信息
     */
    void updateRegistration(@Valid RegistrationUpdateReqVO updateReqVO);

    /**
     * 删除计时登记
     *
     * @param id 编号
     */
    void deleteRegistration(Long id);

    /**
     * 获得计时登记
     *
     * @param id 编号
     * @return 计时登记
     */
    RegistrationDO getRegistration(Long id);

    /**
     * 获得计时登记列表
     *
     * @param ids 编号
     * @return 计时登记列表
     */
    List<RegistrationDO> getRegistrationList(Collection<Long> ids);

    /**
     * 获得计时登记分页
     *
     * @param pageReqVO 分页查询
     * @return 计时登记分页
     */
    PageResult<RegistrationDO> getRegistrationPage(RegistrationPageReqVO pageReqVO);

    /**
     * 获得计时登记列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 计时登记列表
     */
    List<RegistrationDO> getRegistrationList(RegistrationExportReqVO exportReqVO);

}
