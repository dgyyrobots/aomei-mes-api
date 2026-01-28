package com.dofast.module.purchase.convert.goods;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.purchase.controller.admin.goods.vo.*;
import com.dofast.module.purchase.dal.dataobject.goods.GoodsDO;

/**
 * 采购商品明细 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface GoodsConvert {

    GoodsConvert INSTANCE = Mappers.getMapper(GoodsConvert.class);

    GoodsDO convert(GoodsCreateReqVO bean);

    GoodsDO convert(GoodsUpdateReqVO bean);

    GoodsRespVO convert(GoodsDO bean);

    GoodsCreateReqVO convert02(GoodsDO bean);

    GoodsUpdateReqVO convert01(GoodsDO bean);

    GoodsExportReqVO convert03(GoodsPageReqVO bean);

    List<GoodsRespVO> convertList(List<GoodsDO> list);

    PageResult<GoodsRespVO> convertPage(PageResult<GoodsDO> page);

    List<GoodsExcelVO> convertList02(List<GoodsDO> list);

}
