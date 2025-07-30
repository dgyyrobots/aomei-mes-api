package com.dofast.module.mes.service.registrationline;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.mes.controller.admin.registrationline.vo.*;
import com.dofast.module.mes.dal.dataobject.registrationline.RegistrationLineDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.mes.convert.registrationline.RegistrationLineConvert;
import com.dofast.module.mes.dal.mysql.registrationline.RegistrationLineMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.mes.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 计时登记记录 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class RegistrationLineServiceImpl implements RegistrationLineService {

    @Resource
    private RegistrationLineMapper registrationLineMapper;

    @Override
    public Long createRegistrationLine(RegistrationLineCreateReqVO createReqVO) {
        // 插入
        RegistrationLineDO registrationLine = RegistrationLineConvert.INSTANCE.convert(createReqVO);
        registrationLineMapper.insert(registrationLine);
        // 返回
        return registrationLine.getId();
    }

    @Override
    public void updateRegistrationLine(RegistrationLineUpdateReqVO updateReqVO) {
        // 校验存在
        validateRegistrationLineExists(updateReqVO.getId());
        // 更新
        RegistrationLineDO updateObj = RegistrationLineConvert.INSTANCE.convert(updateReqVO);
        registrationLineMapper.updateById(updateObj);
    }

    @Override
    public void deleteRegistrationLine(Long id) {
        // 校验存在
        validateRegistrationLineExists(id);
        // 删除
        registrationLineMapper.deleteById(id);
    }

    private void validateRegistrationLineExists(Long id) {
        if (registrationLineMapper.selectById(id) == null) {
            throw exception(REGISTRATION_LINE_NOT_EXISTS);
        }
    }

    @Override
    public RegistrationLineDO getRegistrationLine(Long id) {
        return registrationLineMapper.selectById(id);
    }

    @Override
    public List<RegistrationLineDO> getRegistrationLineList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return registrationLineMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<RegistrationLineDO> getRegistrationLinePage(RegistrationLinePageReqVO pageReqVO) {
        return registrationLineMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RegistrationLineDO> getRegistrationLineList(RegistrationLineExportReqVO exportReqVO) {
        return registrationLineMapper.selectList(exportReqVO);
    }

}
