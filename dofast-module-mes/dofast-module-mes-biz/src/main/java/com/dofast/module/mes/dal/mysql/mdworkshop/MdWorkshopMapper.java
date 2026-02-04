package com.dofast.module.mes.dal.mysql.mdworkshop;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.mdworkshop.MdWorkshopDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.mdworkshop.vo.*;

/**
 * 车间 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MdWorkshopMapper extends BaseMapperX<MdWorkshopDO> {
    /**
     * 检查车间编码是否唯一
     * @param mdWorkshop
     * @return
     */
    default MdWorkshopDO checkWorkshopCodeUnique(MdWorkshopBaseVO mdWorkshop){
        return selectOne(new LambdaQueryWrapperX<MdWorkshopDO>().eq(MdWorkshopDO::getWorkshopCode,mdWorkshop.getWorkshopCode()));
    }

    /**
     * 检查车间名称是否唯一
     * @param mdWorkshop
     * @return
     */
    default MdWorkshopDO checkWorkshopNameUnique(MdWorkshopBaseVO mdWorkshop){
        return selectOne(new LambdaQueryWrapperX<MdWorkshopDO>().eq(MdWorkshopDO::getWorkshopName,mdWorkshop.getWorkshopName()));
    }
    default PageResult<MdWorkshopDO> selectPage(MdWorkshopPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MdWorkshopDO>()
                .eqIfPresent(MdWorkshopDO::getWorkshopCode, reqVO.getWorkshopCode())
                .likeIfPresent(MdWorkshopDO::getWorkshopName, reqVO.getWorkshopName())
                .eqIfPresent(MdWorkshopDO::getArea, reqVO.getArea())
                .eqIfPresent(MdWorkshopDO::getCharge, reqVO.getCharge())
                .eqIfPresent(MdWorkshopDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdWorkshopDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdWorkshopDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdWorkshopDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdWorkshopDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdWorkshopDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdWorkshopDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdWorkshopDO::getId));
    }

    default List<MdWorkshopDO> selectList(MdWorkshopExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdWorkshopDO>()
                .eqIfPresent(MdWorkshopDO::getWorkshopCode, reqVO.getWorkshopCode())
                .eqIfPresent(MdWorkshopDO::getWorkshopName, reqVO.getWorkshopName())
                .eqIfPresent(MdWorkshopDO::getArea, reqVO.getArea())
                .eqIfPresent(MdWorkshopDO::getCharge, reqVO.getCharge())
                .eqIfPresent(MdWorkshopDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(MdWorkshopDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdWorkshopDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdWorkshopDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdWorkshopDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdWorkshopDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdWorkshopDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdWorkshopDO::getId));
    }

    /**
     * 查询车间信息简单列表
     *
     * @return 车间简单列表
     */
    List<MdWorkShopSimpleVO> selectSimpleList();
}
