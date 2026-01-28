package com.dofast.module.pro.service.workorder;

import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.mes.constant.Constant;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import com.dofast.module.pro.controller.admin.workorder.vo.*;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.pro.convert.workorder.WorkorderConvert;
import com.dofast.module.pro.dal.mysql.workorder.WorkorderMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.pro.enums.ErrorCodeConstants.*;

/**
 * 生产工单 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class WorkorderServiceImpl implements WorkorderService {

    @Resource
    private WorkorderMapper workorderMapper;

    @Override
    public String checkWorkorderCodeUnique(WorkorderBaseVO baseVO) {
        WorkorderDO workorder = workorderMapper.checkWorkorderCodeUnique(baseVO);
        Long workorderId = baseVO.getId() == null? -1L: baseVO.getId();
        if(StrUtils.isNotNull(workorder) && workorder.getId().longValue() != workorderId.longValue()){
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public Long createWorkorder(WorkorderCreateReqVO createReqVO) {
        if(createReqVO.getParentId()!= null){
            WorkorderDO parent = workorderMapper.selectById(createReqVO.getParentId());
            if(StrUtils.isNotNull(parent)){
                createReqVO.setAncestors(parent.getAncestors()+","+parent.getParentId());
            }
        }
        // 插入
        WorkorderDO workorder = WorkorderConvert.INSTANCE.convert(createReqVO);
        workorderMapper.insert(workorder);
        // 返回
        return workorder.getId();
    }

    @Override
    public void updateWorkorder(WorkorderUpdateReqVO updateReqVO) {
        // 校验存在
        validateWorkorderExists(updateReqVO.getId());
        // 更新
        WorkorderDO updateObj = WorkorderConvert.INSTANCE.convert(updateReqVO);
        int i = workorderMapper.updateById(updateObj);
        if (i<=0){
            throw exception(WORKORER_UPDATE_COUNT);
        }
    }

    @Override
    public void deleteWorkorder(Long id) {
        // 校验存在
        validateWorkorderExists(id);
        // 删除
        workorderMapper.deleteById(id);
    }

    private void validateWorkorderExists(Long id) {
        if (workorderMapper.selectById(id) == null) {
            throw exception(WORKORDER_NOT_EXISTS);
        }
    }

    @Override
    public WorkorderDO getWorkorder(Long id) {
        return workorderMapper.selectById(id);
    }

    @Override
    public WorkorderDO getWorkorder(String workorderCode){
        return workorderMapper.selectOne(WorkorderDO::getWorkorderCode, workorderCode);
    }


    @Override
    public List<WorkorderDO> getWorkorderList(Collection<Long> ids) {
        return workorderMapper.selectBatchIds(ids);
    }

    @Override
    public List<WorkorderDO> getWorkorderList(WorkorderListAllReqVO reqVO) {
        return workorderMapper.selectList(WorkorderDO::getMixinOrderId, reqVO.getMixinOrderId());
    }

    @Override
    public PageResult<WorkorderDO> getWorkorderPage(WorkorderPageReqVO pageReqVO) {
        PageResult<WorkorderDO> result = workorderMapper.selectPage(pageReqVO);
        /*Long totle = workorderMapper.selectCount(pageReqVO);
        List<WorkorderDO> workorders = result.getList();
        List<WorkorderDO> sortedWorkorders = workorders.stream()
                .sorted(Comparator.comparing((WorkorderDO wo) -> {
                    if (wo.getStatus().equals("CONFIRMED") || wo.getStatus().equals("PRAPARE")) {
                        return 0;
                    } else if (wo.getStatus().equals("FINISHED")) {
                        return 1;
                    } else {
                        return 2;
                    }
                })).collect(Collectors.toList());
        result.setList(sortedWorkorders);
        result.setTotal(totle);*/
        return result;

    }

    @Override
    public List<WorkorderDO> getWorkorderList(WorkorderExportReqVO exportReqVO) {
        return workorderMapper.selectList(exportReqVO);
    }

    @Override
    public List<WorkorderDO> getWorkorderList(WorkorderListVO listVO) {
        return workorderMapper.selectList(listVO);
    }

    @Override
    public List<WorkorderDO> getWorkderByParentId(BigInteger parentId) {
        return workorderMapper.selectList(WorkorderDO::getParentId,parentId);
    }

    @Override
    public List<WorkorderDO> getWorkorderBySourceCode(String sourceCode) {
        return workorderMapper.selectList(new WorkorderListVO().setSourceCode(sourceCode));
    }


    @Override
    public Map<String, Integer>  getCountMonthWorkorderLastYear(){
        return workorderMapper.getCountMonthWorkorderLastYear();
    }

    @Override
    public Map<String, Integer>  getCountMonthWorkorderThisYear(){
        return workorderMapper.getCountMonthWorkorderThisYear();
    }

    @Override
    public void updateBatch(List<WorkorderDO> workorderList){
    workorderMapper.updateBatch(workorderList);
    }

}
