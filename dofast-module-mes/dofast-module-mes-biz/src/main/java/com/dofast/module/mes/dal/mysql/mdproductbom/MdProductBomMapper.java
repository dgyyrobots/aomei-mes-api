package com.dofast.module.mes.dal.mysql.mdproductbom;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.mdproductbom.MdProductBomDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.mdproductbom.vo.*;

/**
 * 产品BOM关系 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MdProductBomMapper extends BaseMapperX<MdProductBomDO> {
    default List<MdProductBomDO> selectList(MdProductBomListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdProductBomDO>()
                .eqIfPresent(MdProductBomDO::getItemId, reqVO.getItemId())
                .eqIfPresent(MdProductBomDO::getBomItemId, reqVO.getBomItemId())
                .eqIfPresent(MdProductBomDO::getBomItemCode, reqVO.getBomItemCode())
                .likeIfPresent(MdProductBomDO::getBomItemName, reqVO.getBomItemName())
                .eqIfPresent(MdProductBomDO::getBomItemSpec, reqVO.getBomItemSpec())
                .eqIfPresent(MdProductBomDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(MdProductBomDO::getItemOrProduct, reqVO.getItemOrProduct())
                .eqIfPresent(MdProductBomDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(MdProductBomDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdProductBomDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdProductBomDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdProductBomDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdProductBomDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdProductBomDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(MdProductBomDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MdProductBomDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(MdProductBomDO::getInverted, reqVO.getInverted())
                .eqIfPresent(MdProductBomDO::getSequence, reqVO.getSequence())
                .orderByDesc(MdProductBomDO::getId));
    }

    default PageResult<MdProductBomDO> selectPage(MdProductBomPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MdProductBomDO>()
                .eqIfPresent(MdProductBomDO::getItemId, reqVO.getItemId())
                .eqIfPresent(MdProductBomDO::getBomItemId, reqVO.getBomItemId())
                .eqIfPresent(MdProductBomDO::getBomItemCode, reqVO.getBomItemCode())
                .likeIfPresent(MdProductBomDO::getBomItemName, reqVO.getBomItemName())
                .eqIfPresent(MdProductBomDO::getBomItemSpec, reqVO.getBomItemSpec())
                .eqIfPresent(MdProductBomDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(MdProductBomDO::getItemOrProduct, reqVO.getItemOrProduct())
                .eqIfPresent(MdProductBomDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdProductBomDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdProductBomDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdProductBomDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdProductBomDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdProductBomDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdProductBomDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MdProductBomDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(MdProductBomDO::getInverted, reqVO.getInverted())
                .eqIfPresent(MdProductBomDO::getSequence, reqVO.getSequence())
                .orderByDesc(MdProductBomDO::getId));
    }

    default List<MdProductBomDO> selectList(MdProductBomExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdProductBomDO>()
                .eqIfPresent(MdProductBomDO::getItemId, reqVO.getItemId())
                .eqIfPresent(MdProductBomDO::getBomItemId, reqVO.getBomItemId())
                .eqIfPresent(MdProductBomDO::getBomItemCode, reqVO.getBomItemCode())
                .eqIfPresent(MdProductBomDO::getBomItemName, reqVO.getBomItemName())
                .eqIfPresent(MdProductBomDO::getBomItemSpec, reqVO.getBomItemSpec())
                .eqIfPresent(MdProductBomDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(MdProductBomDO::getItemOrProduct, reqVO.getItemOrProduct())
                .eqIfPresent(MdProductBomDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdProductBomDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdProductBomDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdProductBomDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdProductBomDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdProductBomDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdProductBomDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MdProductBomDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(MdProductBomDO::getInverted, reqVO.getInverted())
                .eqIfPresent(MdProductBomDO::getSequence, reqVO.getSequence())
                .orderByDesc(MdProductBomDO::getId));
    }

}
