package com.dofast.module.cmms.service.dvcheckplan;

import com.dofast.module.mes.constant.Constant;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.cmms.controller.admin.dvcheckplan.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckplan.DvCheckPlanDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.cmms.convert.dvcheckplan.DvCheckPlanConvert;
import com.dofast.module.cmms.dal.mysql.dvcheckplan.DvCheckPlanMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.cmms.enums.ErrorCodeConstants.*;

/**
 * 设备点检保养计划头 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DvCheckPlanServiceImpl implements DvCheckPlanService {

    @Resource
    private DvCheckPlanMapper dvCheckPlanMapper;

    @Override
    public String checkPlanCodeUnique(DvCheckPlanBaseVO baseVO) {
        DvCheckPlanDO plan = dvCheckPlanMapper.checkPlanCodeUnique(baseVO);
        Long planId = baseVO.getId()==null?-1L:baseVO.getId();
        System.out.println("planId="+planId);
        System.out.println("plan="+plan);
        if(null != (plan) && plan.getId().longValue()==planId.longValue()){
            return Constant.UNIQUE;
        }
        return Constant.NOT_UNIQUE;
    }

    @Override
    public Long createDvCheckPlan(DvCheckPlanCreateReqVO createReqVO) {
        // 插入
        DvCheckPlanDO dvCheckPlan = DvCheckPlanConvert.INSTANCE.convert(createReqVO);
        dvCheckPlanMapper.insert(dvCheckPlan);
        // 返回
        return dvCheckPlan.getId();
    }

    @Override
    public void updateDvCheckPlan(DvCheckPlanUpdateReqVO updateReqVO) {
        // 校验存在
        validateDvCheckPlanExists(updateReqVO.getId());
        // 更新
        DvCheckPlanDO updateObj = DvCheckPlanConvert.INSTANCE.convert(updateReqVO);
        dvCheckPlanMapper.updateById(updateObj);
    }

    @Override
    public void deleteDvCheckPlan(Long id) {
        // 校验存在
        validateDvCheckPlanExists(id);
        // 删除
        dvCheckPlanMapper.deleteById(id);
    }

    private void validateDvCheckPlanExists(Long id) {
        if (dvCheckPlanMapper.selectById(id) == null) {
            throw exception(DV_CHECK_PLAN_NOT_EXISTS);
        }
    }

    @Override
    public DvCheckPlanDO getDvCheckPlan(Long id) {
        return dvCheckPlanMapper.selectById(id);
    }

    @Override
    public List<DvCheckPlanDO> getDvCheckPlanList(Collection<Long> ids) {
        return dvCheckPlanMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DvCheckPlanDO> getDvCheckPlanPage(DvCheckPlanPageReqVO pageReqVO) {
        return dvCheckPlanMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DvCheckPlanDO> getDvCheckPlanList(DvCheckPlanExportReqVO exportReqVO) {
        return dvCheckPlanMapper.selectList(exportReqVO);
    }

}
