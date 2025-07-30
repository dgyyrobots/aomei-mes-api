package com.dofast.module.cmms.service.dvcheckplanlinelog;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.cmms.controller.admin.dvcheckplanlinelog.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanlinelog.DvCheckPlanLineLogDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.cmms.convert.dvcheckplanlinelog.DvCheckPlanLineLogConvert;
import com.dofast.module.cmms.dal.mysql.dvcheckplanlinelog.DvCheckPlanLineLogMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.cmms.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 点检计划记录单身 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class DvCheckPlanLineLogServiceImpl implements DvCheckPlanLineLogService {

    @Resource
    private DvCheckPlanLineLogMapper dvCheckPlanLineLogMapper;

    @Override
    public Long createDvCheckPlanLineLog(DvCheckPlanLineLogCreateReqVO createReqVO) {
        // 插入
        DvCheckPlanLineLogDO dvCheckPlanLineLog = DvCheckPlanLineLogConvert.INSTANCE.convert(createReqVO);
        dvCheckPlanLineLogMapper.insert(dvCheckPlanLineLog);
        // 返回
        return dvCheckPlanLineLog.getId();
    }

    @Override
    public void updateDvCheckPlanLineLog(DvCheckPlanLineLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateDvCheckPlanLineLogExists(updateReqVO.getId());
        // 更新
        DvCheckPlanLineLogDO updateObj = DvCheckPlanLineLogConvert.INSTANCE.convert(updateReqVO);
        dvCheckPlanLineLogMapper.updateById(updateObj);
    }

    @Override
    public void deleteDvCheckPlanLineLog(Long id) {
        // 校验存在
        validateDvCheckPlanLineLogExists(id);
        // 删除
        dvCheckPlanLineLogMapper.deleteById(id);
    }

    private void validateDvCheckPlanLineLogExists(Long id) {
        if (dvCheckPlanLineLogMapper.selectById(id) == null) {
            throw exception(DV_CHECK_PLAN_LINE_LOG_NOT_EXISTS);
        }
    }

    @Override
    public DvCheckPlanLineLogDO getDvCheckPlanLineLog(Long id) {
        return dvCheckPlanLineLogMapper.selectById(id);
    }

    @Override
    public List<DvCheckPlanLineLogDO> getDvCheckPlanLineLogList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return dvCheckPlanLineLogMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DvCheckPlanLineLogDO> getDvCheckPlanLineLogPage(DvCheckPlanLineLogPageReqVO pageReqVO) {
        return dvCheckPlanLineLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DvCheckPlanLineLogDO> getDvCheckPlanLineLogList(DvCheckPlanLineLogExportReqVO exportReqVO) {
        return dvCheckPlanLineLogMapper.selectList(exportReqVO);
    }

}
