package com.dofast.module.pro.dal.mysql.routeproductbom;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.routeproductbom.RouteProductBomDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.routeproductbom.vo.*;

/**
 * 产品制程物料BOM Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RouteProductBomMapper extends BaseMapperX<RouteProductBomDO> {

    default int deleteByRouteId(Long  routeId){
        return delete(new LambdaQueryWrapperX<RouteProductBomDO>()
        .eq(RouteProductBomDO::getRouteId,routeId));
    }

    default int deleteByRouteIdAndProductId(Long routeId,Long productId){
        return delete(new LambdaQueryWrapperX<RouteProductBomDO>()
        .eq(RouteProductBomDO::getRouteId,routeId)
        .eq(RouteProductBomDO::getProductId,productId));
    }


    default RouteProductBomDO checkUnique(RouteProductBomBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<RouteProductBomDO>()
                .eq(RouteProductBomDO::getItemId,baseVO.getItemId())
                .eq(RouteProductBomDO::getProcessId,baseVO.getProcessId())
        .eq(RouteProductBomDO::getProductId,baseVO.getProductId()));
    }

    default PageResult<RouteProductBomDO> selectPage(RouteProductBomPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RouteProductBomDO>()
                .eqIfPresent(RouteProductBomDO::getRouteId, reqVO.getRouteId())
                .eqIfPresent(RouteProductBomDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(RouteProductBomDO::getProductId, reqVO.getProductId())
                .eqIfPresent(RouteProductBomDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RouteProductBomDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(RouteProductBomDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RouteProductBomDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RouteProductBomDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RouteProductBomDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(RouteProductBomDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RouteProductBomDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RouteProductBomDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RouteProductBomDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RouteProductBomDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RouteProductBomDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RouteProductBomDO::getId));
    }

    default List<RouteProductBomDO> selectList(RouteProductBomExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RouteProductBomDO>()
                .eqIfPresent(RouteProductBomDO::getRouteId, reqVO.getRouteId())
                .eqIfPresent(RouteProductBomDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(RouteProductBomDO::getProductId, reqVO.getProductId())
                .eqIfPresent(RouteProductBomDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RouteProductBomDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(RouteProductBomDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RouteProductBomDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RouteProductBomDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RouteProductBomDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(RouteProductBomDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RouteProductBomDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RouteProductBomDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RouteProductBomDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RouteProductBomDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RouteProductBomDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RouteProductBomDO::getId));
    }
    default List<RouteProductBomDO> selectList(RouteProductBomListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RouteProductBomDO>()
                .eqIfPresent(RouteProductBomDO::getRouteId, reqVO.getRouteId())
                .eqIfPresent(RouteProductBomDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(RouteProductBomDO::getProductId, reqVO.getProductId())
                .eqIfPresent(RouteProductBomDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RouteProductBomDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(RouteProductBomDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RouteProductBomDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RouteProductBomDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RouteProductBomDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(RouteProductBomDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RouteProductBomDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RouteProductBomDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RouteProductBomDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RouteProductBomDO::getAttr4, reqVO.getAttr4())
                .orderByDesc(RouteProductBomDO::getId));
    }
}
