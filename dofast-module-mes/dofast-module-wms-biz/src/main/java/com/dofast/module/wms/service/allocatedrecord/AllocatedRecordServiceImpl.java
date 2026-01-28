package com.dofast.module.wms.service.allocatedrecord;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.wms.controller.admin.allocatedrecord.vo.*;
import com.dofast.module.wms.dal.dataobject.allocatedrecord.AllocatedRecordDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.wms.convert.allocatedrecord.AllocatedRecordConvert;
import com.dofast.module.wms.dal.mysql.allocatedrecord.AllocatedRecordMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.wms.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 调拨单身记录 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class AllocatedRecordServiceImpl implements AllocatedRecordService {

    @Resource
    private AllocatedRecordMapper allocatedRecordMapper;

    @Override
    public Long createAllocatedRecord(AllocatedRecordCreateReqVO createReqVO) {
        // 插入
        AllocatedRecordDO allocatedRecord = AllocatedRecordConvert.INSTANCE.convert(createReqVO);
        allocatedRecordMapper.insert(allocatedRecord);
        // 返回
        return allocatedRecord.getId();
    }

    @Override
    public void updateAllocatedRecord(AllocatedRecordUpdateReqVO updateReqVO) {
        // 校验存在
        validateAllocatedRecordExists(updateReqVO.getId());
        // 更新
        AllocatedRecordDO updateObj = AllocatedRecordConvert.INSTANCE.convert(updateReqVO);
        allocatedRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteAllocatedRecord(Long id) {
        // 校验存在
        validateAllocatedRecordExists(id);
        // 删除
        allocatedRecordMapper.deleteById(id);
    }

    private void validateAllocatedRecordExists(Long id) {
        if (allocatedRecordMapper.selectById(id) == null) {
            throw exception(ALLOCATED_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public AllocatedRecordDO getAllocatedRecord(Long id) {
        return allocatedRecordMapper.selectById(id);
    }

    @Override
    public List<AllocatedRecordDO> getAllocatedRecordList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return allocatedRecordMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<AllocatedRecordDO> getAllocatedRecordPage(AllocatedRecordPageReqVO pageReqVO) {
        return allocatedRecordMapper.selectPage(pageReqVO);
    }

    @Override
    public List<AllocatedRecordDO> getAllocatedRecordList(AllocatedRecordExportReqVO exportReqVO) {
        return allocatedRecordMapper.selectList(exportReqVO);
    }

    @Override
    public  void createBatchAllocatedRecord(List<AllocatedRecordDO> createList){
        allocatedRecordMapper.insertBatch(createList);
    }

    @Override
    public  void updateAllocatedRecordBatch(List<AllocatedRecordDO> updateList){
        allocatedRecordMapper.updateBatch(updateList);
    }

    @Override
    public void deleteAllocatedRecordBatch(List<Long> ids){
        allocatedRecordMapper.deleteBatchIds(ids);
    }


}
