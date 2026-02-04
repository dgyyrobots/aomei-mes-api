package com.dofast.module.cmms.dal.mysql.dvcheckmachinery;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.cmms.dal.dataobject.dvcheckmachinery.DvCheckMachineryDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.cmms.controller.admin.dvcheckmachinery.vo.*;

/**
 * 点检设备 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DvCheckMachineryMapper extends BaseMapperX<DvCheckMachineryDO> {
    /**
     * 查询点检设备
     *
     * @param recordId 点检设备主键
     * @return 点检设备
     */
    default DvCheckMachineryDO selectDvCheckMachineryByRecordId(Long recordId){
        return selectOne(new LambdaQueryWrapperX<DvCheckMachineryDO>().eq(DvCheckMachineryDO::getId,recordId));
    }

    public DvCheckMachineryDO checkMachineryUnique(DvCheckMachineryBaseVO dvCheckMachinery);
    default PageResult<DvCheckMachineryDO> selectPage(DvCheckMachineryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DvCheckMachineryDO>()
                .eqIfPresent(DvCheckMachineryDO::getPlanId, reqVO.getPlanId())
                .eqIfPresent(DvCheckMachineryDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(DvCheckMachineryDO::getMachineryCode, reqVO.getMachineryCode())
                .likeIfPresent(DvCheckMachineryDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(DvCheckMachineryDO::getMachineryBrand, reqVO.getMachineryBrand())
                .eqIfPresent(DvCheckMachineryDO::getMachinerySpec, reqVO.getMachinerySpec())
                .eqIfPresent(DvCheckMachineryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvCheckMachineryDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvCheckMachineryDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvCheckMachineryDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvCheckMachineryDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvCheckMachineryDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DvCheckMachineryDO::getErpMachineryCode, reqVO.getErpMachineryCode())
                .orderByDesc(DvCheckMachineryDO::getId));
    }

    default List<DvCheckMachineryDO> selectList(DvCheckMachineryExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DvCheckMachineryDO>()
                .eqIfPresent(DvCheckMachineryDO::getPlanId, reqVO.getPlanId())
                .eqIfPresent(DvCheckMachineryDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(DvCheckMachineryDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(DvCheckMachineryDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(DvCheckMachineryDO::getMachineryBrand, reqVO.getMachineryBrand())
                .eqIfPresent(DvCheckMachineryDO::getMachinerySpec, reqVO.getMachinerySpec())
                .eqIfPresent(DvCheckMachineryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvCheckMachineryDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvCheckMachineryDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvCheckMachineryDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvCheckMachineryDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvCheckMachineryDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DvCheckMachineryDO::getErpMachineryCode, reqVO.getErpMachineryCode())
                .orderByDesc(DvCheckMachineryDO::getId));
    }

}
