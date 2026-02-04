package com.dofast.module.mes.dal.mysql.mditemcategory;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.mditemcategory.MdItemCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.mditemcategory.vo.*;

/**
 * 物料类别 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface MdItemCategoryMapper extends BaseMapperX<MdItemCategoryDO> {

    default PageResult<MdItemCategoryDO> selectPage(MdItemCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MdItemCategoryDO>()
                .likeIfPresent(MdItemCategoryDO::getItemCategoryName, reqVO.getItemCategoryName())
                .eqIfPresent(MdItemCategoryDO::getItemCategoryCode, reqVO.getItemCategoryCode())
                .eqIfPresent(MdItemCategoryDO::getItemRoute, reqVO.getItemRoute())
                .eqIfPresent(MdItemCategoryDO::getItemRouteCode, reqVO.getItemRouteCode())

                .eqIfPresent(MdItemCategoryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdItemCategoryDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdItemCategoryDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdItemCategoryDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdItemCategoryDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdItemCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdItemCategoryDO::getId));
    }

    default List<MdItemCategoryDO> selectList(MdItemCategoryExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MdItemCategoryDO>()
                .eqIfPresent(MdItemCategoryDO::getItemCategoryName, reqVO.getItemCategoryName())
                .eqIfPresent(MdItemCategoryDO::getItemCategoryCode, reqVO.getItemCategoryCode())
                .eqIfPresent(MdItemCategoryDO::getItemRoute, reqVO.getItemRoute())
                .eqIfPresent(MdItemCategoryDO::getItemRouteCode, reqVO.getItemRouteCode())

                .eqIfPresent(MdItemCategoryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MdItemCategoryDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(MdItemCategoryDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(MdItemCategoryDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(MdItemCategoryDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(MdItemCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MdItemCategoryDO::getId));
    }

}
