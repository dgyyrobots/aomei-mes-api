package com.dofast.module.cmms.service.dvrepairline;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.cmms.controller.admin.dvrepairline.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvrepairline.DvRepairLineDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.cmms.convert.dvrepairline.DvRepairLineConvert;
import com.dofast.module.cmms.dal.mysql.dvrepairline.DvRepairLineMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.cmms.enums.ErrorCodeConstants.*;

/**
 * 设备维修单行 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DvRepairLineServiceImpl implements DvRepairLineService {

    @Resource
    private DvRepairLineMapper dvRepairLineMapper;

    @Override
    public Long createDvRepairLine(DvRepairLineCreateReqVO createReqVO) {
        // 插入
        DvRepairLineDO dvRepairLine = DvRepairLineConvert.INSTANCE.convert(createReqVO);
        dvRepairLineMapper.insert(dvRepairLine);
        // 返回
        return dvRepairLine.getId();
    }

    @Override
    public void updateDvRepairLine(DvRepairLineUpdateReqVO updateReqVO) {
        // 校验存在
        validateDvRepairLineExists(updateReqVO.getId());
        // 更新
        DvRepairLineDO updateObj = DvRepairLineConvert.INSTANCE.convert(updateReqVO);
        dvRepairLineMapper.updateById(updateObj);
    }

    @Override
    public void deleteDvRepairLine(Long id) {
        // 校验存在
        validateDvRepairLineExists(id);
        // 删除
        dvRepairLineMapper.deleteById(id);
    }

    private void validateDvRepairLineExists(Long id) {
        if (dvRepairLineMapper.selectById(id) == null) {
            throw exception(DV_REPAIR_LINE_NOT_EXISTS);
        }
    }

    @Override
    public DvRepairLineDO getDvRepairLine(Long id) {
        return dvRepairLineMapper.selectById(id);
    }

    @Override
    public List<DvRepairLineDO> getDvRepairLineList(Collection<Long> ids) {
        return dvRepairLineMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DvRepairLineDO> getDvRepairLinePage(DvRepairLinePageReqVO pageReqVO) {
        return dvRepairLineMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DvRepairLineDO> getDvRepairLineList(DvRepairLineExportReqVO exportReqVO) {
        return dvRepairLineMapper.selectList(exportReqVO);
    }

    @Override
    public void insertBatch(List<DvRepairLineDO> repairLineList){
        dvRepairLineMapper.insertBatch(repairLineList);
    }


}
