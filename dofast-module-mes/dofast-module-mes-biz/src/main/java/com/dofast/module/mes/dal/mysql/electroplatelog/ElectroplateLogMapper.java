package com.dofast.module.mes.dal.mysql.electroplatelog;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.electroplatelog.ElectroplateLogDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.electroplatelog.vo.*;

/**
 * 制版房记录 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface ElectroplateLogMapper extends BaseMapperX<ElectroplateLogDO> {

    default PageResult<ElectroplateLogDO> selectPage(ElectroplateLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ElectroplateLogDO>()
                .eqIfPresent(ElectroplateLogDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(ElectroplateLogDO::getMachineryCode, reqVO.getMachineryCode())
                .likeIfPresent(ElectroplateLogDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(ElectroplateLogDO::getProportion, reqVO.getProportion())
                .eqIfPresent(ElectroplateLogDO::getTemperature, reqVO.getTemperature())
                .eqIfPresent(ElectroplateLogDO::getPhValue, reqVO.getPhValue())
                .eqIfPresent(ElectroplateLogDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(ElectroplateLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ElectroplateLogDO::getId));
    }

    default List<ElectroplateLogDO> selectList(ElectroplateLogExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ElectroplateLogDO>()
                .eqIfPresent(ElectroplateLogDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(ElectroplateLogDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(ElectroplateLogDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(ElectroplateLogDO::getProportion, reqVO.getProportion())
                .eqIfPresent(ElectroplateLogDO::getTemperature, reqVO.getTemperature())
                .eqIfPresent(ElectroplateLogDO::getPhValue, reqVO.getPhValue())
                .eqIfPresent(ElectroplateLogDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(ElectroplateLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ElectroplateLogDO::getId));
    }

}
