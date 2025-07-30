package com.dofast.module.mes.service.exception;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.mes.controller.admin.exception.vo.*;
import com.dofast.module.mes.dal.dataobject.exception.ExceptionDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.mes.convert.exception.ExceptionConvert;
import com.dofast.module.mes.dal.mysql.exception.ExceptionMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.mes.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 异常登记 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class ExceptionServiceImpl implements ExceptionService {

    @Resource
    private ExceptionMapper exceptionMapper;

    @Override
    public Long createException(ExceptionCreateReqVO createReqVO) {
        // 插入
        ExceptionDO exception = ExceptionConvert.INSTANCE.convert(createReqVO);
        exceptionMapper.insert(exception);
        // 返回
        return exception.getId();
    }

    @Override
    public void updateException(ExceptionUpdateReqVO updateReqVO) {
        // 校验存在
        validateExceptionExists(updateReqVO.getId());
        // 更新
        ExceptionDO updateObj = ExceptionConvert.INSTANCE.convert(updateReqVO);
        exceptionMapper.updateById(updateObj);
    }

    @Override
    public void deleteException(Long id) {
        // 校验存在
        validateExceptionExists(id);
        // 删除
        exceptionMapper.deleteById(id);
    }

    private void validateExceptionExists(Long id) {
        if (exceptionMapper.selectById(id) == null) {
            throw exception(EXCEPTION_NOT_EXISTS);
        }
    }

    @Override
    public ExceptionDO getException(Long id) {
        return exceptionMapper.selectById(id);
    }

    @Override
    public List<ExceptionDO> getExceptionList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return exceptionMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ExceptionDO> getExceptionPage(ExceptionPageReqVO pageReqVO) {
        return exceptionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ExceptionDO> getExceptionList(ExceptionExportReqVO exportReqVO) {
        return exceptionMapper.selectList(exportReqVO);
    }

}
