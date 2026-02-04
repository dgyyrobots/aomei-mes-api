package com.dofast.module.pro.dal.mysql.routeproduct;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.routeproduct.RouteProductDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.routeproduct.vo.*;

/**
 * 产品制程 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RouteProductMapper extends BaseMapperX<RouteProductDO> {
    default RouteProductDO checkItemUnique(RouteProductBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<RouteProductDO>().eq(RouteProductDO::getItemId,baseVO.getItemId()));
    }

    default int deleteByRouteId(Long routeId){
        return delete(new LambdaQueryWrapperX<RouteProductDO>().eq(RouteProductDO::getRouteId,routeId));
    }
    default PageResult<RouteProductDO> selectPage(RouteProductPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RouteProductDO>()
                .eqIfPresent(RouteProductDO::getRouteId, reqVO.getRouteId())
                .eqIfPresent(RouteProductDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RouteProductDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(RouteProductDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RouteProductDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RouteProductDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RouteProductDO::getQuantity, reqVO.getQuantity())
                .betweenIfPresent(RouteProductDO::getProductionTime, reqVO.getProductionTime())
                .eqIfPresent(RouteProductDO::getTimeUnitType, reqVO.getTimeUnitType())
                .eqIfPresent(RouteProductDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RouteProductDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RouteProductDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RouteProductDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RouteProductDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RouteProductDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RouteProductDO::getId));
    }
    default List<RouteProductDO> selectList(RouteProductListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RouteProductDO>()
                .eqIfPresent(RouteProductDO::getRouteId, reqVO.getRouteId())
                .eqIfPresent(RouteProductDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RouteProductDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(RouteProductDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RouteProductDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RouteProductDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RouteProductDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(RouteProductDO::getProductionTime, reqVO.getProductionTime())
                .eqIfPresent(RouteProductDO::getTimeUnitType, reqVO.getTimeUnitType())
                .eqIfPresent(RouteProductDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RouteProductDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RouteProductDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RouteProductDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RouteProductDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(RouteProductDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RouteProductDO::getId));
    }

    default List<RouteProductDO> selectList(RouteProductExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RouteProductDO>()
                .eqIfPresent(RouteProductDO::getRouteId, reqVO.getRouteId())
                .eqIfPresent(RouteProductDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RouteProductDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(RouteProductDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RouteProductDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RouteProductDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RouteProductDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(RouteProductDO::getProductionTime, reqVO.getProductionTime())
                .eqIfPresent(RouteProductDO::getTimeUnitType, reqVO.getTimeUnitType())
                .eqIfPresent(RouteProductDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RouteProductDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RouteProductDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RouteProductDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RouteProductDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(RouteProductDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RouteProductDO::getId));
    }

}
