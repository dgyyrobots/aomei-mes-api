package com.dofast.module.wms.service.allocatedline;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.wms.controller.admin.allocatedline.vo.*;
import com.dofast.module.wms.dal.dataobject.allocatedline.AllocatedLineDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.wms.convert.allocatedline.AllocatedLineConvert;
import com.dofast.module.wms.dal.mysql.allocatedline.AllocatedLineMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.wms.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 调拨单身 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class AllocatedLineServiceImpl implements AllocatedLineService {

    @Resource
    private AllocatedLineMapper allocatedLineMapper;

    @Override
    public Long createAllocatedLine(AllocatedLineCreateReqVO createReqVO) {
        // 插入
        AllocatedLineDO allocatedLine = AllocatedLineConvert.INSTANCE.convert(createReqVO);
        allocatedLineMapper.insert(allocatedLine);
        // 返回
        return allocatedLine.getId();
    }

    @Override
    public void updateAllocatedLine(AllocatedLineUpdateReqVO updateReqVO) {
        // 校验存在
        validateAllocatedLineExists(updateReqVO.getId());
        // 更新
        AllocatedLineDO updateObj = AllocatedLineConvert.INSTANCE.convert(updateReqVO);
        allocatedLineMapper.updateById(updateObj);
    }

    @Override
    public void deleteAllocatedLine(Long id) {
        // 校验存在
        validateAllocatedLineExists(id);
        // 删除
        allocatedLineMapper.deleteById(id);
    }

    private void validateAllocatedLineExists(Long id) {
        if (allocatedLineMapper.selectById(id) == null) {
            throw exception(ALLOCATED_LINE_NOT_EXISTS);
        }
    }

    @Override
    public AllocatedLineDO getAllocatedLine(Long id) {
        return allocatedLineMapper.selectById(id);
    }

    @Override
    public List<AllocatedLineDO> getAllocatedLineList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return allocatedLineMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<AllocatedLineDO> getAllocatedLinePage(AllocatedLinePageReqVO pageReqVO) {
        return allocatedLineMapper.selectPage(pageReqVO);
    }

    @Override
    public List<AllocatedLineDO> getAllocatedLineList(AllocatedLineExportReqVO exportReqVO) {
        return allocatedLineMapper.selectList(exportReqVO);
    }

    @Override
    public void deleteAllocatedLineByHeaderId(Long headerId){
        Map<String, Object> map = new HashMap<>();
        map.put("allocated_id", headerId.longValue());
        allocatedLineMapper.deleteByMap(map);
    }

    @Override
    public void createAllocatedBatch(List<AllocatedLineDO> allocatedLineDOList){
        allocatedLineMapper.insertBatch(allocatedLineDOList);
    }

}
