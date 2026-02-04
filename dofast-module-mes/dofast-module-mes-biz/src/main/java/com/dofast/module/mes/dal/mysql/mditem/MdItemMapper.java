package com.dofast.module.mes.dal.mysql.mditem;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.mditem.MdItemDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.mditem.vo.*;

/**
 * 物料产品 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MdItemMapper extends BaseMapperX<MdItemDO> {

    default PageResult<MdItemDO> selectPage(MdItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MdItemDO>()
                .likeIfPresent(MdItemDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(MdItemDO::getItemTypeId, reqVO.getItemTypeId())
                .likeIfPresent(MdItemDO::getItemName, reqVO.getItemName())
                .orderByDesc(MdItemDO::getId));
    }

    default List<MdItemDO> selectList(MdItemExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdItemDO>()
                .eqIfPresent(MdItemDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(MdItemDO::getItemName, reqVO.getItemName())
                .orderByDesc(MdItemDO::getId));
    }

    /**
     * 检查物料编码是否唯一
     * @param createReqVO
     * @return
     */
    default MdItemDO checkItemCodeUnique(MdItemBaseVO createReqVO){
        return selectOne(new LambdaQueryWrapperX<MdItemDO>().eq(MdItemDO::getItemCode,createReqVO.getItemCode()));
    }
    /**
     * 检查物料名称是否唯一checkItemCodeUnique
     * @param mdItem
     * @return
     */
    default MdItemDO  checkItemNameUnique(MdItemBaseVO mdItem){
        return selectOne(new LambdaQueryWrapperX<MdItemDO>().eq(MdItemDO::getItemName,mdItem.getItemCode()));

    }

    default List<MdItemDO> selectListByItemReq(MdItemExportReqVO mdItemExportReqVO) {
        return selectList(new LambdaQueryWrapperX<MdItemDO>()
                .eqIfPresent(MdItemDO::getItemCode, mdItemExportReqVO.getItemCode())
                .eqIfPresent(MdItemDO::getItemName, mdItemExportReqVO.getItemName())
                .eqIfPresent(MdItemDO::getEnableFlag, mdItemExportReqVO.getEnableFlag())
                .eqIfPresent(MdItemDO::getItemTypeId, mdItemExportReqVO.getItemTypeId())
                .orderByDesc(MdItemDO::getId));
    }



}
