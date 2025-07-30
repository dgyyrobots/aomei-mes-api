package com.dofast.module.mes.convert.mdworkshop;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.mes.api.WorkShopApi.dto.MdWorkshopDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.mes.controller.admin.mdworkshop.vo.*;
import com.dofast.module.mes.dal.dataobject.mdworkshop.MdWorkshopDO;

/**
 * 车间 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface MdWorkshopConvert {

    MdWorkshopConvert INSTANCE = Mappers.getMapper(MdWorkshopConvert.class);

    MdWorkshopDO convert(MdWorkshopCreateReqVO bean);

    MdWorkshopDO convert(MdWorkshopUpdateReqVO bean);

    MdWorkshopRespVO convert(MdWorkshopDO bean);

    MdWorkshopDTO convert03(MdWorkshopDO bean);


    List<MdWorkshopRespVO> convertList(List<MdWorkshopDO> list);

    PageResult<MdWorkshopRespVO> convertPage(PageResult<MdWorkshopDO> page);

    List<MdWorkshopExcelVO> convertList02(List<MdWorkshopDO> list);

}
