package com.dofast.module.cmms.service.dvcheckplanheaderlog;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;
import com.dofast.module.cmms.controller.admin.dvcheckplanheaderlog.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanheaderlog.DvCheckPlanHeaderLogDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.cmms.convert.dvcheckplanheaderlog.DvCheckPlanHeaderLogConvert;
import com.dofast.module.cmms.dal.mysql.dvcheckplanheaderlog.DvCheckPlanHeaderLogMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.cmms.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 点检计划记录单头 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class DvCheckPlanHeaderLogServiceImpl implements DvCheckPlanHeaderLogService {

    @Resource
    private DvCheckPlanHeaderLogMapper dvCheckPlanHeaderLogMapper;

    @Override
    public Long createDvCheckPlanHeaderLog(DvCheckPlanHeaderLogCreateReqVO createReqVO) {
        // 插入
        DvCheckPlanHeaderLogDO dvCheckPlanHeaderLog = DvCheckPlanHeaderLogConvert.INSTANCE.convert(createReqVO);
        dvCheckPlanHeaderLogMapper.insert(dvCheckPlanHeaderLog);
        // 返回
        return dvCheckPlanHeaderLog.getId();
    }

    @Override
    public void updateDvCheckPlanHeaderLog(DvCheckPlanHeaderLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateDvCheckPlanHeaderLogExists(updateReqVO.getId());
        // 更新
        DvCheckPlanHeaderLogDO updateObj = DvCheckPlanHeaderLogConvert.INSTANCE.convert(updateReqVO);
        dvCheckPlanHeaderLogMapper.updateById(updateObj);
    }

    @Override
    public void deleteDvCheckPlanHeaderLog(Long id) {
        // 校验存在
        validateDvCheckPlanHeaderLogExists(id);
        // 删除
        dvCheckPlanHeaderLogMapper.deleteById(id);
    }

    private void validateDvCheckPlanHeaderLogExists(Long id) {
        if (dvCheckPlanHeaderLogMapper.selectById(id) == null) {
            throw exception(DV_CHECK_PLAN_HEADER_LOG_NOT_EXISTS);
        }
    }

    @Override
    public DvCheckPlanHeaderLogDO getDvCheckPlanHeaderLog(Long id) {
        return dvCheckPlanHeaderLogMapper.selectById(id);
    }

    @Override
    public List<DvCheckPlanHeaderLogDO> getDvCheckPlanHeaderLogList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return dvCheckPlanHeaderLogMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DvCheckPlanHeaderLogDO> getDvCheckPlanHeaderLogPage(DvCheckPlanHeaderLogPageReqVO pageReqVO) {
        return dvCheckPlanHeaderLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DvCheckPlanHeaderLogDO> getDvCheckPlanHeaderLogList(DvCheckPlanHeaderLogExportReqVO exportReqVO) {
        return dvCheckPlanHeaderLogMapper.selectList(exportReqVO);
    }

    @Override
    public int selectCountInCycle(String planCode , LocalDateTime startTime , LocalDateTime endTime){
        return dvCheckPlanHeaderLogMapper.selectCountInCycle(planCode, startTime, endTime);
    }


}
