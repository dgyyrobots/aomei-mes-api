package com.dofast.module.mes.dal.mysql.mdvendor;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.mdvendor.MdVendorDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.mdvendor.vo.*;

/**
 * 供应商 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PadMdVendorMapper extends BaseMapperX<MdVendorDO> {
    default MdVendorDO checkVendorCodeUnique(MdVendorBaseVO mdVendor){
        return selectOne(new LambdaQueryWrapperX<MdVendorDO>().eq(MdVendorDO::getVendorCode,mdVendor.getVendorCode()));
    }
    default MdVendorDO checkVendorNameUnique(MdVendorBaseVO mdVendor){
        return selectOne(new LambdaQueryWrapperX<MdVendorDO>().eq(MdVendorDO::getVendorName,mdVendor.getVendorName()));
    }
    default MdVendorDO checkVendorNickUnique(MdVendorBaseVO mdVendor){
        return selectOne(new LambdaQueryWrapperX<MdVendorDO>().eq(MdVendorDO::getVendorNick,mdVendor.getVendorNick()));
    }
    default PageResult<MdVendorDO> selectPage(MdVendorPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MdVendorDO>()
                .eqIfPresent(MdVendorDO::getVendorCode, reqVO.getVendorCode())
                .likeIfPresent(MdVendorDO::getVendorName, reqVO.getVendorName())
                .eqIfPresent(MdVendorDO::getVendorNick, reqVO.getVendorNick())
                .eqIfPresent(MdVendorDO::getVendorEn, reqVO.getVendorEn())
                .eqIfPresent(MdVendorDO::getVendorDes, reqVO.getVendorDes())
                .eqIfPresent(MdVendorDO::getVendorLogo, reqVO.getVendorLogo())
                .eqIfPresent(MdVendorDO::getVendorLevel, reqVO.getVendorLevel())
                .eqIfPresent(MdVendorDO::getVendorScore, reqVO.getVendorScore())
                .eqIfPresent(MdVendorDO::getAddress, reqVO.getAddress())
                .eqIfPresent(MdVendorDO::getWebsite, reqVO.getWebsite())
                .eqIfPresent(MdVendorDO::getEmail, reqVO.getEmail())
                .eqIfPresent(MdVendorDO::getTel, reqVO.getTel())
                .eqIfPresent(MdVendorDO::getContact1, reqVO.getContact1())
                .eqIfPresent(MdVendorDO::getContact1Tel, reqVO.getContact1Tel())
                .eqIfPresent(MdVendorDO::getContact1Email, reqVO.getContact1Email())
                .eqIfPresent(MdVendorDO::getContact2, reqVO.getContact2())
                .eqIfPresent(MdVendorDO::getContact2Tel, reqVO.getContact2Tel())
                .eqIfPresent(MdVendorDO::getContact2Email, reqVO.getContact2Email())
                .eqIfPresent(MdVendorDO::getCreditCode, reqVO.getCreditCode())
                .eqIfPresent(MdVendorDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdVendorDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdVendorDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdVendorDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdVendorDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdVendorDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdVendorDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdVendorDO::getId));
    }

    default List<MdVendorDO> selectList(MdVendorExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdVendorDO>()
                .eqIfPresent(MdVendorDO::getVendorCode, reqVO.getVendorCode())
                .eqIfPresent(MdVendorDO::getVendorName, reqVO.getVendorName())
                .eqIfPresent(MdVendorDO::getVendorNick, reqVO.getVendorNick())
                .eqIfPresent(MdVendorDO::getVendorEn, reqVO.getVendorEn())
                .eqIfPresent(MdVendorDO::getVendorDes, reqVO.getVendorDes())
                .eqIfPresent(MdVendorDO::getVendorLogo, reqVO.getVendorLogo())
                .eqIfPresent(MdVendorDO::getVendorLevel, reqVO.getVendorLevel())
                .eqIfPresent(MdVendorDO::getVendorScore, reqVO.getVendorScore())
                .eqIfPresent(MdVendorDO::getAddress, reqVO.getAddress())
                .eqIfPresent(MdVendorDO::getWebsite, reqVO.getWebsite())
                .eqIfPresent(MdVendorDO::getEmail, reqVO.getEmail())
                .eqIfPresent(MdVendorDO::getTel, reqVO.getTel())
                .eqIfPresent(MdVendorDO::getContact1, reqVO.getContact1())
                .eqIfPresent(MdVendorDO::getContact1Tel, reqVO.getContact1Tel())
                .eqIfPresent(MdVendorDO::getContact1Email, reqVO.getContact1Email())
                .eqIfPresent(MdVendorDO::getContact2, reqVO.getContact2())
                .eqIfPresent(MdVendorDO::getContact2Tel, reqVO.getContact2Tel())
                .eqIfPresent(MdVendorDO::getContact2Email, reqVO.getContact2Email())
                .eqIfPresent(MdVendorDO::getCreditCode, reqVO.getCreditCode())
                .eqIfPresent(MdVendorDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdVendorDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdVendorDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdVendorDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdVendorDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdVendorDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdVendorDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdVendorDO::getId));
    }

}
