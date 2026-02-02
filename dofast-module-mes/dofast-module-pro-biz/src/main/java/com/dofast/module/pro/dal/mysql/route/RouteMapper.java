package com.dofast.module.pro.dal.mysql.route;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.route.RouteDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.route.vo.*;

/**
 * 工艺路线 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RouteMapper extends BaseMapperX<RouteDO> {

    default RouteDO checkRouteCodeUnique(RouteBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<RouteDO>().eq(RouteDO::getRouteCode,baseVO.getRouteCode()));
    }
    default PageResult<RouteDO> selectPage(RoutePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RouteDO>()
                .likeIfPresent(RouteDO::getRouteCode, reqVO.getRouteCode())
                .likeIfPresent(RouteDO::getRouteName, reqVO.getRouteName())
                .likeIfPresent(RouteDO::getRouteDesc, reqVO.getRouteDesc())
                .eqIfPresent(RouteDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(RouteDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RouteDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RouteDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RouteDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RouteDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(RouteDO::getProductCode, reqVO.getProductCode())
                .eqIfPresent(RouteDO::getFile, reqVO.getFile())
                .betweenIfPresent(RouteDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RouteDO::getId));
    }

    default List<RouteDO> selectList(RouteExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RouteDO>()
                .eqIfPresent(RouteDO::getRouteCode, reqVO.getRouteCode())
                .eqIfPresent(RouteDO::getRouteName, reqVO.getRouteName())
                .eqIfPresent(RouteDO::getRouteDesc, reqVO.getRouteDesc())
                .eqIfPresent(RouteDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(RouteDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RouteDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RouteDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RouteDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RouteDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(RouteDO::getProductCode, reqVO.getProductCode())
                .eqIfPresent(RouteDO::getFile, reqVO.getFile())
                .betweenIfPresent(RouteDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RouteDO::getId));
    }

    RouteDO getRouteByProductId(Long itemId);
}
