package com.dofast.module.mes.dal.mysql.mdworkstationworker;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.mdworkstationworker.MdWorkstationWorkerDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.mdworkstationworker.vo.*;

/**
 * 人力资源 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MdWorkstationWorkerMapper extends BaseMapperX<MdWorkstationWorkerDO> {
    default int deleteByWorkstationId(Long workstationId){
        return delete(new LambdaQueryWrapperX<MdWorkstationWorkerDO>().eq(MdWorkstationWorkerDO::getWorkstationId,workstationId));
    }
    default MdWorkstationWorkerDO checkPostExist(MdWorkstationWorkerBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<MdWorkstationWorkerDO>().eq(MdWorkstationWorkerDO::getPostCode,baseVO.getPostCode()).eq(MdWorkstationWorkerDO::getWorkstationId,baseVO.getWorkstationId()));
    }
    default PageResult<MdWorkstationWorkerDO> selectPage(MdWorkstationWorkerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MdWorkstationWorkerDO>()
                .eqIfPresent(MdWorkstationWorkerDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(MdWorkstationWorkerDO::getPostId, reqVO.getPostId())
                .eqIfPresent(MdWorkstationWorkerDO::getPostCode, reqVO.getPostCode())
                .likeIfPresent(MdWorkstationWorkerDO::getPostName, reqVO.getPostName())
                .eqIfPresent(MdWorkstationWorkerDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(MdWorkstationWorkerDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdWorkstationWorkerDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdWorkstationWorkerDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdWorkstationWorkerDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdWorkstationWorkerDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdWorkstationWorkerDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdWorkstationWorkerDO::getId));
    }

    default List<MdWorkstationWorkerDO> selectList(MdWorkstationWorkerExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdWorkstationWorkerDO>()
                .eqIfPresent(MdWorkstationWorkerDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(MdWorkstationWorkerDO::getPostId, reqVO.getPostId())
                .eqIfPresent(MdWorkstationWorkerDO::getPostCode, reqVO.getPostCode())
                .eqIfPresent(MdWorkstationWorkerDO::getPostName, reqVO.getPostName())
                .eqIfPresent(MdWorkstationWorkerDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(MdWorkstationWorkerDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdWorkstationWorkerDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdWorkstationWorkerDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdWorkstationWorkerDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdWorkstationWorkerDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdWorkstationWorkerDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdWorkstationWorkerDO::getId));
    }

    default List<MdWorkstationWorkerDO> selectList(Set<Long> postIds) {
        return selectList(new LambdaQueryWrapperX<MdWorkstationWorkerDO>()
                .in(MdWorkstationWorkerDO::getPostId, postIds)
                .orderByDesc(MdWorkstationWorkerDO::getId));
    }
}
