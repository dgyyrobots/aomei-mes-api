package com.dofast.module.wms.dal.mysql.electroformheader;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.electroformheader.ElectroformHeaderDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.electroformheader.vo.*;

/**
 * 制版房领料单头 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface ElectroformHeaderMapper extends BaseMapperX<ElectroformHeaderDO> {

    default PageResult<ElectroformHeaderDO> selectPage(ElectroformHeaderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ElectroformHeaderDO>()
                .eqIfPresent(ElectroformHeaderDO::getIssueCode, reqVO.getIssueCode())
                .likeIfPresent(ElectroformHeaderDO::getIssueName, reqVO.getIssueName())
                .betweenIfPresent(ElectroformHeaderDO::getIssueDate, reqVO.getIssueDate())
                .eqIfPresent(ElectroformHeaderDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ElectroformHeaderDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(ElectroformHeaderDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(ElectroformHeaderDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(ElectroformHeaderDO::getMachineryId, reqVO.getMachineryId())
                .orderByDesc(ElectroformHeaderDO::getId));
    }

    default List<ElectroformHeaderDO> selectList(ElectroformHeaderExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ElectroformHeaderDO>()
                .eqIfPresent(ElectroformHeaderDO::getIssueCode, reqVO.getIssueCode())
                .eqIfPresent(ElectroformHeaderDO::getIssueName, reqVO.getIssueName())
                .betweenIfPresent(ElectroformHeaderDO::getIssueDate, reqVO.getIssueDate())
                .eqIfPresent(ElectroformHeaderDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ElectroformHeaderDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ElectroformHeaderDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(ElectroformHeaderDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(ElectroformHeaderDO::getMachineryId, reqVO.getMachineryId())
                .orderByDesc(ElectroformHeaderDO::getId));
    }

}
