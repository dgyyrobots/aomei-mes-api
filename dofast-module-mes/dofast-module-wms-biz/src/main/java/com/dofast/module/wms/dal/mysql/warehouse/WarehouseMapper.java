package com.dofast.module.wms.dal.mysql.warehouse;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.warehouse.WarehouseDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.warehouse.vo.*;

/**
 * 仓库 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface WarehouseMapper extends BaseMapperX<WarehouseDO> {

    default WarehouseDO selectWmWarehouseByWarehouseCode(String houseCode){
        return selectOne(new LambdaQueryWrapperX<WarehouseDO>().eq(WarehouseDO::getWarehouseCode,houseCode));
    }

    public List<WarehouseDO> getTreeList();

    default WarehouseDO checkWarehouseCodeUnique(WarehouseBaseVO wmWarehouse){
        return selectOne(new LambdaQueryWrapperX<WarehouseDO>().eq(WarehouseDO::getWarehouseCode,wmWarehouse.getWarehouseCode()));
    }
    default WarehouseDO checkWarehouseNameUnique(WarehouseBaseVO wmWarehouse){
        return selectOne(new LambdaQueryWrapperX<WarehouseDO>().eq(WarehouseDO::getWarehouseName,wmWarehouse.getWarehouseName()));
    }


    default PageResult<WarehouseDO> selectPage(WarehousePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WarehouseDO>()
                .eqIfPresent(WarehouseDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(WarehouseDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(WarehouseDO::getLocation, reqVO.getLocation())
                .eqIfPresent(WarehouseDO::getArea, reqVO.getArea())
                .eqIfPresent(WarehouseDO::getCharge, reqVO.getCharge())
                .eqIfPresent(WarehouseDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(WarehouseDO::getRemark, reqVO.getRemark())
                .eqIfPresent(WarehouseDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(WarehouseDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(WarehouseDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(WarehouseDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(WarehouseDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(WarehouseDO::getSendState, reqVO.getSendState())
                .eqIfPresent(WarehouseDO::getSendCity, reqVO.getSendCity())
                .eqIfPresent(WarehouseDO::getSendDistrict, reqVO.getSendDistrict())
                .eqIfPresent(WarehouseDO::getSendStreet, reqVO.getSendStreet())
                .eqIfPresent(WarehouseDO::getSendDetail, reqVO.getSendDetail())
                .likeIfPresent(WarehouseDO::getSendName, reqVO.getSendName())
                .eqIfPresent(WarehouseDO::getSendPhone, reqVO.getSendPhone())
                .eqIfPresent(WarehouseDO::getSendMobile, reqVO.getSendMobile())
                .orderByDesc(WarehouseDO::getId));
    }

    default List<WarehouseDO> selectList(WarehouseExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<WarehouseDO>()
                .eqIfPresent(WarehouseDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(WarehouseDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(WarehouseDO::getLocation, reqVO.getLocation())
                .eqIfPresent(WarehouseDO::getArea, reqVO.getArea())
                .eqIfPresent(WarehouseDO::getCharge, reqVO.getCharge())
                .eqIfPresent(WarehouseDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(WarehouseDO::getRemark, reqVO.getRemark())
                .eqIfPresent(WarehouseDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(WarehouseDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(WarehouseDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(WarehouseDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(WarehouseDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(WarehouseDO::getSendState, reqVO.getSendState())
                .eqIfPresent(WarehouseDO::getSendCity, reqVO.getSendCity())
                .eqIfPresent(WarehouseDO::getSendDistrict, reqVO.getSendDistrict())
                .eqIfPresent(WarehouseDO::getSendStreet, reqVO.getSendStreet())
                .eqIfPresent(WarehouseDO::getSendDetail, reqVO.getSendDetail())
                .eqIfPresent(WarehouseDO::getSendName, reqVO.getSendName())
                .eqIfPresent(WarehouseDO::getSendPhone, reqVO.getSendPhone())
                .eqIfPresent(WarehouseDO::getSendMobile, reqVO.getSendMobile())
                .orderByDesc(WarehouseDO::getId));
    }

}
