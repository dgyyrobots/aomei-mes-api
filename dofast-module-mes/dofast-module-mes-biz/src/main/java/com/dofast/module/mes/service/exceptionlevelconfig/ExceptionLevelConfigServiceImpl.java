package com.dofast.module.mes.service.exceptionlevelconfig;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.mes.controller.admin.exceptionlevelconfig.vo.*;
import com.dofast.module.mes.dal.dataobject.exceptionlevelconfig.ExceptionLevelConfigDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.mes.convert.exceptionlevelconfig.ExceptionLevelConfigConvert;
import com.dofast.module.mes.dal.mysql.exceptionlevelconfig.ExceptionLevelConfigMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.mes.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 异常等级配置 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class ExceptionLevelConfigServiceImpl implements ExceptionLevelConfigService {

    @Resource
    private ExceptionLevelConfigMapper exceptionLevelConfigMapper;

    @Override
    public Long createExceptionLevelConfig(ExceptionLevelConfigCreateReqVO createReqVO) {
        // 插入
        ExceptionLevelConfigDO exceptionLevelConfig = ExceptionLevelConfigConvert.INSTANCE.convert(createReqVO);
        exceptionLevelConfigMapper.insert(exceptionLevelConfig);
        // 返回
        return exceptionLevelConfig.getId();
    }

    @Override
    public void updateExceptionLevelConfig(ExceptionLevelConfigUpdateReqVO updateReqVO) {
        // 校验存在
        validateExceptionLevelConfigExists(updateReqVO.getId());
        // 更新
        ExceptionLevelConfigDO updateObj = ExceptionLevelConfigConvert.INSTANCE.convert(updateReqVO);
        exceptionLevelConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteExceptionLevelConfig(Long id) {
        // 校验存在
        validateExceptionLevelConfigExists(id);
        // 删除
        exceptionLevelConfigMapper.deleteById(id);
    }

    private void validateExceptionLevelConfigExists(Long id) {
        if (exceptionLevelConfigMapper.selectById(id) == null) {
            throw exception(EXCEPTION_LEVEL_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public ExceptionLevelConfigDO getExceptionLevelConfig(Long id) {
        return exceptionLevelConfigMapper.selectById(id);
    }

    @Override
    public List<ExceptionLevelConfigDO> getExceptionLevelConfigList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return exceptionLevelConfigMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ExceptionLevelConfigDO> getExceptionLevelConfigPage(ExceptionLevelConfigPageReqVO pageReqVO) {
        return exceptionLevelConfigMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ExceptionLevelConfigDO> getExceptionLevelConfigList(ExceptionLevelConfigExportReqVO exportReqVO) {
        return exceptionLevelConfigMapper.selectList(exportReqVO);
    }

}
