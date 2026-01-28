package com.dofast.module.mes.convert.mditemcategory;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.mes.controller.admin.mditemcategory.vo.*;
import com.dofast.module.mes.dal.dataobject.mditemcategory.MdItemCategoryDO;

/**
 * 物料类别 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface MdItemCategoryConvert {

    MdItemCategoryConvert INSTANCE = Mappers.getMapper(MdItemCategoryConvert.class);

    MdItemCategoryDO convert(MdItemCategoryCreateReqVO bean);

    MdItemCategoryDO convert(MdItemCategoryUpdateReqVO bean);

    MdItemCategoryRespVO convert(MdItemCategoryDO bean);

    List<MdItemCategoryRespVO> convertList(List<MdItemCategoryDO> list);

    PageResult<MdItemCategoryRespVO> convertPage(PageResult<MdItemCategoryDO> page);

    List<MdItemCategoryExcelVO> convertList02(List<MdItemCategoryDO> list);

}
