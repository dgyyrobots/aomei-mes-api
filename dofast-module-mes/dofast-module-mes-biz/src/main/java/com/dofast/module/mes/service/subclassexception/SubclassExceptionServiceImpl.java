package com.dofast.module.mes.service.subclassexception;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.mes.controller.admin.subclassexception.vo.*;
import com.dofast.module.mes.dal.dataobject.subclassexception.SubclassExceptionDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.mes.convert.subclassexception.SubclassExceptionConvert;
import com.dofast.module.mes.dal.mysql.subclassexception.SubclassExceptionMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.mes.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 子类异常项配置 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class SubclassExceptionServiceImpl implements SubclassExceptionService {

    @Resource
    private SubclassExceptionMapper subclassExceptionMapper;

    @Override
    public Long createSubclassException(SubclassExceptionCreateReqVO createReqVO) {
        // 插入
        SubclassExceptionDO subclassException = SubclassExceptionConvert.INSTANCE.convert(createReqVO);
        subclassExceptionMapper.insert(subclassException);
        // 返回
        return subclassException.getId();
    }

    @Override
    public void updateSubclassException(SubclassExceptionUpdateReqVO updateReqVO) {
        // 校验存在
        validateSubclassExceptionExists(updateReqVO.getId());
        // 更新
        SubclassExceptionDO updateObj = SubclassExceptionConvert.INSTANCE.convert(updateReqVO);
        subclassExceptionMapper.updateById(updateObj);
    }

    @Override
    public void deleteSubclassException(Long id) {
        // 校验存在
        validateSubclassExceptionExists(id);
        // 删除
        subclassExceptionMapper.deleteById(id);
    }

    private void validateSubclassExceptionExists(Long id) {
        if (subclassExceptionMapper.selectById(id) == null) {
            throw exception(SUBCLASS_EXCEPTION_NOT_EXISTS);
        }
    }

    @Override
    public SubclassExceptionDO getSubclassException(Long id) {
        return subclassExceptionMapper.selectById(id);
    }

    @Override
    public List<SubclassExceptionDO> getSubclassExceptionList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return subclassExceptionMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<SubclassExceptionDO> getSubclassExceptionPage(SubclassExceptionPageReqVO pageReqVO) {
        return subclassExceptionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SubclassExceptionDO> getSubclassExceptionList(SubclassExceptionExportReqVO exportReqVO) {
        return subclassExceptionMapper.selectList(exportReqVO);
    }

}
