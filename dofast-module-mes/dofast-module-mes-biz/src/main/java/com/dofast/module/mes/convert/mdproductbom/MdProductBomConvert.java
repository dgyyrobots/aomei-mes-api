package com.dofast.module.mes.convert.mdproductbom;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.mes.api.ProductBomApi.dto.MdProductBomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.mes.controller.admin.mdproductbom.vo.*;
import com.dofast.module.mes.dal.dataobject.mdproductbom.MdProductBomDO;

/**
 * 产品BOM关系 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface MdProductBomConvert {

    MdProductBomConvert INSTANCE = Mappers.getMapper(MdProductBomConvert.class);

    MdProductBomDO convert(MdProductBomCreateReqVO bean);

    MdProductBomDO convert(MdProductBomUpdateReqVO bean);

    MdProductBomRespVO convert(MdProductBomDO bean);

    MdProductBomCreateReqVO convert01(MdProductBomDO bean);

    MdProductBomUpdateReqVO convert02(MdProductBomDO bean);

    List<MdProductBomRespVO> convertList(List<MdProductBomDO> list);

    PageResult<MdProductBomRespVO> convertPage(PageResult<MdProductBomDO> page);

    List<MdProductBomExcelVO> convertList02(List<MdProductBomDO> list);

    List<MdProductBomDTO> convertList03(List<MdProductBomDO> list);


}
