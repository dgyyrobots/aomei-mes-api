package com.dofast.module.wms.dal.mysql.barcode;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.barcode.BarcodeDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.barcode.vo.*;

/**
 * 条码清单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface BarcodeMapper extends BaseMapperX<BarcodeDO> {

    /**
     * 检查当前条码类型下，对应的业务是否已经生成了条码
     * @param
     * @return
     */
    default BarcodeDO checkBarcodeUnique(BarcodeBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<BarcodeDO>().eq(BarcodeDO::getBussinessId,baseVO.getBussinessId()));
    };
    default PageResult<BarcodeDO> selectPage(BarcodePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BarcodeDO>()
                .eqIfPresent(BarcodeDO::getBarcodeFormart, reqVO.getBarcodeFormart())
                .eqIfPresent(BarcodeDO::getBarcodeType, reqVO.getBarcodeType())
                .eqIfPresent(BarcodeDO::getBarcodeContent, reqVO.getBarcodeContent())
                .eqIfPresent(BarcodeDO::getBussinessId, reqVO.getBussinessId())
                .eqIfPresent(BarcodeDO::getBussinessCode, reqVO.getBussinessCode())
                .likeIfPresent(BarcodeDO::getBussinessName, reqVO.getBussinessName())
                .eqIfPresent(BarcodeDO::getBarcodeUrl, reqVO.getBarcodeUrl())
                .eqIfPresent(BarcodeDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(BarcodeDO::getRemark, reqVO.getRemark())
                .eqIfPresent(BarcodeDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(BarcodeDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(BarcodeDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(BarcodeDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(BarcodeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BarcodeDO::getId));
    }

    default List<BarcodeDO> selectList(BarcodeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<BarcodeDO>()
                .eqIfPresent(BarcodeDO::getBarcodeFormart, reqVO.getBarcodeFormart())
                .eqIfPresent(BarcodeDO::getBarcodeType, reqVO.getBarcodeType())
                .eqIfPresent(BarcodeDO::getBarcodeContent, reqVO.getBarcodeContent())
                .eqIfPresent(BarcodeDO::getBussinessId, reqVO.getBussinessId())
                .eqIfPresent(BarcodeDO::getBussinessCode, reqVO.getBussinessCode())
                .eqIfPresent(BarcodeDO::getBussinessName, reqVO.getBussinessName())
                .eqIfPresent(BarcodeDO::getBarcodeUrl, reqVO.getBarcodeUrl())
                .eqIfPresent(BarcodeDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(BarcodeDO::getRemark, reqVO.getRemark())
                .eqIfPresent(BarcodeDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(BarcodeDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(BarcodeDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(BarcodeDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(BarcodeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BarcodeDO::getId));
    }

}
