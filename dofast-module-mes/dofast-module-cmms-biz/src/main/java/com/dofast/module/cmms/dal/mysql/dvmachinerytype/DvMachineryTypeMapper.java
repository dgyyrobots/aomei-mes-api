package com.dofast.module.cmms.dal.mysql.dvmachinerytype;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.cmms.dal.dataobject.dvmachinerytype.DvMachineryTypeDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.cmms.controller.admin.dvmachinerytype.vo.*;

/**
 * 设备类型 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DvMachineryTypeMapper extends BaseMapperX<DvMachineryTypeDO> {
    /**
     * 查询设备类型
     *
     * @param machineryTypeId 设备类型主键
     * @return 设备类型
     */

    default DvMachineryTypeDO selectDvMachineryTypeByMachineryTypeId(Long machineryTypeId){
        return selectOne(new LambdaQueryWrapperX<DvMachineryTypeDO>().eq(DvMachineryTypeDO::getId,machineryTypeId));
    }
    default PageResult<DvMachineryTypeDO> selectPage(DvMachineryTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DvMachineryTypeDO>()
                .eqIfPresent(DvMachineryTypeDO::getMachineryTypeCode, reqVO.getMachineryTypeCode())
                .likeIfPresent(DvMachineryTypeDO::getMachineryTypeName, reqVO.getMachineryTypeName())
                .eqIfPresent(DvMachineryTypeDO::getParentTypeId, reqVO.getParentTypeId())
                .eqIfPresent(DvMachineryTypeDO::getAncestors, reqVO.getAncestors())
                .eqIfPresent(DvMachineryTypeDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(DvMachineryTypeDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvMachineryTypeDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvMachineryTypeDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvMachineryTypeDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvMachineryTypeDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvMachineryTypeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvMachineryTypeDO::getId));
    }

    default List<DvMachineryTypeDO> selectList(DvMachineryTypeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DvMachineryTypeDO>()
                .eqIfPresent(DvMachineryTypeDO::getMachineryTypeCode, reqVO.getMachineryTypeCode())
                .eqIfPresent(DvMachineryTypeDO::getMachineryTypeName, reqVO.getMachineryTypeName())
                .eqIfPresent(DvMachineryTypeDO::getParentTypeId, reqVO.getParentTypeId())
                .eqIfPresent(DvMachineryTypeDO::getAncestors, reqVO.getAncestors())
                .eqIfPresent(DvMachineryTypeDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(DvMachineryTypeDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvMachineryTypeDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvMachineryTypeDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvMachineryTypeDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvMachineryTypeDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvMachineryTypeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvMachineryTypeDO::getId));
    }

    /**
     * 查询设备类型简单列表
     *
     * @return 设备类型简单列表
     */
    List<DvMachineryTypeSimpleVO> selectSimpleList();
}
