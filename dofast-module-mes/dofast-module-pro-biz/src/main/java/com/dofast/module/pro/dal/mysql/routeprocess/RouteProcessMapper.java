package com.dofast.module.pro.dal.mysql.routeprocess;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.routeprocess.RouteProcessDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.routeprocess.vo.*;

/**
 * 工艺组成 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RouteProcessMapper extends BaseMapperX<RouteProcessDO> {
    default int deleteByRouteId(Long routeId){
        return delete(new LambdaQueryWrapperX<RouteProcessDO>().eq(RouteProcessDO::getRouteId,routeId));
    }
    RouteProcessDO findNextProcess(RouteProcessBaseVO baseVO);
    RouteProcessDO findPreProcess(RouteProcessBaseVO baseVO);
    default RouteProcessDO checkOrderNumExists(RouteProcessBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<RouteProcessDO>().eq(RouteProcessDO::getRouteId,baseVO.getRouteId())
        .eq(RouteProcessDO::getOrderNum,baseVO.getOrderNum()));
    }
    default RouteProcessDO checkProcessExists(RouteProcessBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<RouteProcessDO>().eq(RouteProcessDO::getRouteId,baseVO.getRouteId())
                .eq(RouteProcessDO::getProcessId,baseVO.getProcessId()));
    }
    default RouteProcessDO checkUpdateFlagUnique(RouteProcessBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<RouteProcessDO>().eq(RouteProcessDO::getRouteId,baseVO.getRouteId())
                .eq(RouteProcessDO::getKeyFlag,baseVO.getKeyFlag()));
    }
    default PageResult<RouteProcessDO> selectPage(RouteProcessPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RouteProcessDO>()
                .eqIfPresent(RouteProcessDO::getRouteId, reqVO.getRouteId())
                .eqIfPresent(RouteProcessDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(RouteProcessDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(RouteProcessDO::getOrderNum, reqVO.getOrderNum())
                .eqIfPresent(RouteProcessDO::getNextProcessCode, reqVO.getNextProcessCode())
                .likeIfPresent(RouteProcessDO::getNextProcessName, reqVO.getNextProcessName())
                .eqIfPresent(RouteProcessDO::getLinkType, reqVO.getLinkType())
                .betweenIfPresent(RouteProcessDO::getDefaultPreTime, reqVO.getDefaultPreTime())
                .betweenIfPresent(RouteProcessDO::getDefaultSufTime, reqVO.getDefaultSufTime())
                .eqIfPresent(RouteProcessDO::getColorCode, reqVO.getColorCode())
                .eqIfPresent(RouteProcessDO::getKeyFlag, reqVO.getKeyFlag())
                .eqIfPresent(RouteProcessDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RouteProcessDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RouteProcessDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RouteProcessDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RouteProcessDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RouteProcessDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RouteProcessDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(RouteProcessDO::getNextProcessId, reqVO.getNextProcessId())
                .eqIfPresent(RouteProcessDO::getSequence, reqVO.getSequence())
                .eqIfPresent(RouteProcessDO::getWorkorderSequence, reqVO.getWorkorderSequence())
                .eqIfPresent(RouteProcessDO::getReceivingUnits, reqVO.getReceivingUnits())
                .eqIfPresent(RouteProcessDO::getReceivingUnitsConversionNumerator, reqVO.getReceivingUnitsConversionNumerator())
                .eqIfPresent(RouteProcessDO::getReceivingUnitsConversionDenominator, reqVO.getReceivingUnitsConversionDenominator())
                .eqIfPresent(RouteProcessDO::getOutUnits, reqVO.getOutUnits())
                .eqIfPresent(RouteProcessDO::getOutUnitsConversionNumerator, reqVO.getOutUnitsConversionNumerator())
                .eqIfPresent(RouteProcessDO::getOutUnitsConversionDenominator, reqVO.getOutUnitsConversionDenominator())
                .orderByDesc(RouteProcessDO::getId));
    }
    default List<RouteProcessDO> selectList(RouteProcessListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RouteProcessDO>()
                .eqIfPresent(RouteProcessDO::getRouteId, reqVO.getRouteId())
                .eqIfPresent(RouteProcessDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(RouteProcessDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(RouteProcessDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(RouteProcessDO::getOrderNum, reqVO.getOrderNum())
                .eqIfPresent(RouteProcessDO::getNextProcessId, reqVO.getNextProcessId())
                .eqIfPresent(RouteProcessDO::getNextProcessCode, reqVO.getNextProcessCode())
                .likeIfPresent(RouteProcessDO::getNextProcessName, reqVO.getNextProcessName())
                .eqIfPresent(RouteProcessDO::getLinkType, reqVO.getLinkType())
                .eqIfPresent(RouteProcessDO::getDefaultPreTime, reqVO.getDefaultPreTime())
                .eqIfPresent(RouteProcessDO::getDefaultSufTime, reqVO.getDefaultSufTime())
                .eqIfPresent(RouteProcessDO::getColorCode, reqVO.getColorCode())
                .eqIfPresent(RouteProcessDO::getKeyFlag, reqVO.getKeyFlag())
                .eqIfPresent(RouteProcessDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RouteProcessDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RouteProcessDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RouteProcessDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RouteProcessDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(RouteProcessDO::getSequence, reqVO.getSequence())
                .eqIfPresent(RouteProcessDO::getWorkorderSequence, reqVO.getWorkorderSequence())
                .eqIfPresent(RouteProcessDO::getReceivingUnits, reqVO.getReceivingUnits())
                .eqIfPresent(RouteProcessDO::getReceivingUnitsConversionNumerator, reqVO.getReceivingUnitsConversionNumerator())
                .eqIfPresent(RouteProcessDO::getReceivingUnitsConversionDenominator, reqVO.getReceivingUnitsConversionDenominator())
                .eqIfPresent(RouteProcessDO::getOutUnits, reqVO.getOutUnits())
                .eqIfPresent(RouteProcessDO::getOutUnitsConversionNumerator, reqVO.getOutUnitsConversionNumerator())
                .eqIfPresent(RouteProcessDO::getOutUnitsConversionDenominator, reqVO.getOutUnitsConversionDenominator())

                .eqIfPresent(RouteProcessDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(RouteProcessDO::getOrderNum));
    }

    default List<RouteProcessDO> selectList(RouteProcessExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RouteProcessDO>()
                .eqIfPresent(RouteProcessDO::getRouteId, reqVO.getRouteId())
                .eqIfPresent(RouteProcessDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(RouteProcessDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(RouteProcessDO::getOrderNum, reqVO.getOrderNum())
                .eqIfPresent(RouteProcessDO::getNextProcessCode, reqVO.getNextProcessCode())
                .likeIfPresent(RouteProcessDO::getNextProcessName, reqVO.getNextProcessName())
                .eqIfPresent(RouteProcessDO::getLinkType, reqVO.getLinkType())
                .betweenIfPresent(RouteProcessDO::getDefaultPreTime, reqVO.getDefaultPreTime())
                .betweenIfPresent(RouteProcessDO::getDefaultSufTime, reqVO.getDefaultSufTime())
                .eqIfPresent(RouteProcessDO::getColorCode, reqVO.getColorCode())
                .eqIfPresent(RouteProcessDO::getKeyFlag, reqVO.getKeyFlag())
                .eqIfPresent(RouteProcessDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RouteProcessDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RouteProcessDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RouteProcessDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RouteProcessDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RouteProcessDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RouteProcessDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(RouteProcessDO::getNextProcessId, reqVO.getNextProcessId())
                .eqIfPresent(RouteProcessDO::getSequence, reqVO.getSequence())
                .eqIfPresent(RouteProcessDO::getWorkorderSequence, reqVO.getWorkorderSequence())
                .eqIfPresent(RouteProcessDO::getReceivingUnits, reqVO.getReceivingUnits())
                .eqIfPresent(RouteProcessDO::getReceivingUnitsConversionNumerator, reqVO.getReceivingUnitsConversionNumerator())
                .eqIfPresent(RouteProcessDO::getReceivingUnitsConversionDenominator, reqVO.getReceivingUnitsConversionDenominator())
                .eqIfPresent(RouteProcessDO::getOutUnits, reqVO.getOutUnits())
                .eqIfPresent(RouteProcessDO::getOutUnitsConversionNumerator, reqVO.getOutUnitsConversionNumerator())
                .eqIfPresent(RouteProcessDO::getOutUnitsConversionDenominator, reqVO.getOutUnitsConversionDenominator())
                .orderByDesc(RouteProcessDO::getId));
    }

}
