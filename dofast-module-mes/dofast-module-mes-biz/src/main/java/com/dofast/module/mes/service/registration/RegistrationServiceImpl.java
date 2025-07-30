package com.dofast.module.mes.service.registration;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.mes.controller.admin.registration.vo.*;
import com.dofast.module.mes.dal.dataobject.registration.RegistrationDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.mes.convert.registration.RegistrationConvert;
import com.dofast.module.mes.dal.mysql.registration.RegistrationMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.mes.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 计时登记 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class RegistrationServiceImpl implements RegistrationService {

    @Resource
    private RegistrationMapper registrationMapper;

    @Override
    public Long createRegistration(RegistrationCreateReqVO createReqVO) {
        // 插入
        RegistrationDO registration = RegistrationConvert.INSTANCE.convert(createReqVO);
        registrationMapper.insert(registration);
        // 返回
        return registration.getId();
    }

    @Override
    public void updateRegistration(RegistrationUpdateReqVO updateReqVO) {
        // 校验存在
        validateRegistrationExists(updateReqVO.getId());
        // 更新
        RegistrationDO updateObj = RegistrationConvert.INSTANCE.convert(updateReqVO);
        registrationMapper.updateById(updateObj);
    }

    @Override
    public void deleteRegistration(Long id) {
        // 校验存在
        validateRegistrationExists(id);
        // 删除
        registrationMapper.deleteById(id);
    }

    private void validateRegistrationExists(Long id) {
        if (registrationMapper.selectById(id) == null) {
            throw exception(REGISTRATION_NOT_EXISTS);
        }
    }

    @Override
    public RegistrationDO getRegistration(Long id) {
        return registrationMapper.selectById(id);
    }

    @Override
    public List<RegistrationDO> getRegistrationList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return registrationMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<RegistrationDO> getRegistrationPage(RegistrationPageReqVO pageReqVO) {
        return registrationMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RegistrationDO> getRegistrationList(RegistrationExportReqVO exportReqVO) {
        return registrationMapper.selectList(exportReqVO);
    }

}
