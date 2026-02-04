package com.dofast.module.mes.dal.mysql.mditemtype;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.mditemtype.MdItemTypeDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.mditemtype.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 物料产品分类 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MdItemTypeMapper extends BaseMapperX<MdItemTypeDO> {
    /**
     * 检查同一个父类下子类名称是否重复
     * @param itemTypeName
     * @param parentTypeId
     * @return
     */
    default MdItemTypeDO checkItemTypeNameUnique(String itemTypeName, Long parentTypeId){
        return selectOne(new LambdaQueryWrapperX<MdItemTypeDO>()
                .eq(MdItemTypeDO::getItemTypeName,itemTypeName)
                .eq(MdItemTypeDO::getParentTypeId,parentTypeId));
    }

    /**
     * 检查同一个父类下子类编码是否重复
     * @param itemTypeCode
     * @param parentTypeId
     * @return
     */
    default MdItemTypeDO checkItemTypeCodeUnique(String itemTypeCode,Long parentTypeId){
        return selectOne(new LambdaQueryWrapperX<MdItemTypeDO>()
                .eq(MdItemTypeDO::getItemTypeCode,itemTypeCode)
                .eq(MdItemTypeDO::getParentTypeId,parentTypeId));
    }

    /**
     * 根据父类ID查询是否有子类
     * @param parentTypeId
     * @return
     */
    default Long hasChildByItemTypeId(Long parentTypeId){
        return selectCount(new LambdaQueryWrapperX<MdItemTypeDO>().eq(MdItemTypeDO::getParentTypeId,parentTypeId));
    }


    /**
     * 根据物料分类查询是否有对应的物料和产品
     * @param itemTypeId
     * @return
     */
    default Long hasItemByItemTypeId(Long itemTypeId){
        return selectCount(new LambdaQueryWrapperX<MdItemTypeDO>().eq(MdItemTypeDO::getId,itemTypeId));
    }

    default List<MdItemTypeDO> selectAll(){
        return selectList(new LambdaQueryWrapperX<MdItemTypeDO>());
    }

    default PageResult<MdItemTypeDO> selectPage(MdItemTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MdItemTypeDO>()
                .eqIfPresent(MdItemTypeDO::getItemTypeCode, reqVO.getItemTypeCode())
                .likeIfPresent(MdItemTypeDO::getItemTypeName, reqVO.getItemTypeName())
                .eqIfPresent(MdItemTypeDO::getParentTypeId, reqVO.getParentTypeId())
                .eqIfPresent(MdItemTypeDO::getAncestors, reqVO.getAncestors())
                .eqIfPresent(MdItemTypeDO::getItemOrProduct, reqVO.getItemOrProduct())
                .eqIfPresent(MdItemTypeDO::getOrderNum, reqVO.getOrderNum())
                .eqIfPresent(MdItemTypeDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdItemTypeDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdItemTypeDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdItemTypeDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdItemTypeDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdItemTypeDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(MdItemTypeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdItemTypeDO::getId));
    }
    default List<MdItemTypeDO> selectList(MdItemTypeListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdItemTypeDO>()
                .eqIfPresent(MdItemTypeDO::getItemTypeCode, reqVO.getItemTypeCode())
                .eqIfPresent(MdItemTypeDO::getItemTypeName, reqVO.getItemTypeName())
                .eqIfPresent(MdItemTypeDO::getParentTypeId, reqVO.getParentTypeId())
                .eqIfPresent(MdItemTypeDO::getAncestors, reqVO.getAncestors())
                .eqIfPresent(MdItemTypeDO::getItemOrProduct, reqVO.getItemOrProduct())
                .eqIfPresent(MdItemTypeDO::getOrderNum, reqVO.getOrderNum())
                .eqIfPresent(MdItemTypeDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdItemTypeDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdItemTypeDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdItemTypeDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdItemTypeDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdItemTypeDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(MdItemTypeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdItemTypeDO::getId));
    }
    default List<MdItemTypeDO> selectList(MdItemTypeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdItemTypeDO>()
                .eqIfPresent(MdItemTypeDO::getItemTypeCode, reqVO.getItemTypeCode())
                .likeIfPresent(MdItemTypeDO::getItemTypeName, reqVO.getItemTypeName())
                .eqIfPresent(MdItemTypeDO::getParentTypeId, reqVO.getParentTypeId())
                .eqIfPresent(MdItemTypeDO::getAncestors, reqVO.getAncestors())
                .eqIfPresent(MdItemTypeDO::getItemOrProduct, reqVO.getItemOrProduct())
                .eqIfPresent(MdItemTypeDO::getOrderNum, reqVO.getOrderNum())
                .eqIfPresent(MdItemTypeDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdItemTypeDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdItemTypeDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdItemTypeDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdItemTypeDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdItemTypeDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(MdItemTypeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdItemTypeDO::getId));
    }
}
