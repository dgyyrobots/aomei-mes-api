package com.dofast.module.cmms.service.dvrepair;

import com.dofast.module.mes.constant.Constant;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;
import com.dofast.module.cmms.controller.admin.dvrepair.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvrepair.DvRepairDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.cmms.convert.dvrepair.DvRepairConvert;
import com.dofast.module.cmms.dal.mysql.dvrepair.DvRepairMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.cmms.enums.ErrorCodeConstants.*;

/**
 * 设备维修单 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DvRepairServiceImpl implements DvRepairService {

    @Resource
    private DvRepairMapper dvRepairMapper;

    @Override
    public String checkCodeUnique(DvRepairBaseVO dvRepair) {
        DvRepairDO rp = dvRepairMapper.checkCodeUnique(dvRepair);
        Long repairId = dvRepair.getId() ==null?-1L: dvRepair.getId();
        if(null != (rp) && repairId.longValue() != rp.getId().longValue()){
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public Long createDvRepair(DvRepairCreateReqVO createReqVO) {
        // 插入
        DvRepairDO dvRepair = DvRepairConvert.INSTANCE.convert(createReqVO);
        dvRepairMapper.insert(dvRepair);
        // 返回
        return dvRepair.getId();
    }

    @Override
    public void updateDvRepair(DvRepairUpdateReqVO updateReqVO) {
        // 校验存在
        validateDvRepairExists(updateReqVO.getId());
        // 更新
        DvRepairDO updateObj = DvRepairConvert.INSTANCE.convert(updateReqVO);
        dvRepairMapper.updateById(updateObj);
    }

    @Override
    public void deleteDvRepair(Long id) {
        // 校验存在
        validateDvRepairExists(id);
        // 删除
        dvRepairMapper.deleteById(id);
    }

    private void validateDvRepairExists(Long id) {
        if (dvRepairMapper.selectById(id) == null) {
            throw exception(DV_REPAIR_NOT_EXISTS);
        }
    }

    @Override
    public DvRepairDO getDvRepair(Long id) {
        return dvRepairMapper.selectById(id);
    }

    @Override
    public List<DvRepairDO> getDvRepairList(Collection<Long> ids) {
        return dvRepairMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DvRepairDO> getDvRepairPage(DvRepairPageReqVO pageReqVO) {
        return dvRepairMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DvRepairDO> getDvRepairList(DvRepairExportReqVO exportReqVO) {
        return dvRepairMapper.selectList(exportReqVO);
    }

    @Override
    public int countInspectionRepairInCycle(Long planId, Long machineryId, LocalDateTime cycleStart, LocalDateTime cycleEnd) {
        return dvRepairMapper.countInspectionRepairInCycle(planId, machineryId, "INSPECTION", cycleStart, cycleEnd);
    }

    @Override
    public void updateBatch(List<DvRepairDO> repairList){
        dvRepairMapper.updateBatch(repairList);
    }

}
