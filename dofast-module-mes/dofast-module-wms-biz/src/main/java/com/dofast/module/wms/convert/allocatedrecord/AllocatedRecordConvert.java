package com.dofast.module.wms.convert.allocatedrecord;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.wms.controller.admin.allocatedrecord.vo.*;
import com.dofast.module.wms.dal.dataobject.allocatedrecord.AllocatedRecordDO;

/**
 * 调拨单身记录 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface AllocatedRecordConvert {

    AllocatedRecordConvert INSTANCE = Mappers.getMapper(AllocatedRecordConvert.class);

    AllocatedRecordDO convert(AllocatedRecordCreateReqVO bean);

    AllocatedRecordDO convert(AllocatedRecordUpdateReqVO bean);

    AllocatedRecordRespVO convert(AllocatedRecordDO bean);

    AllocatedRecordUpdateReqVO convert01(AllocatedRecordDO bean);


    List<AllocatedRecordRespVO> convertList(List<AllocatedRecordDO> list);

    PageResult<AllocatedRecordRespVO> convertPage(PageResult<AllocatedRecordDO> page);

    List<AllocatedRecordExcelVO> convertList02(List<AllocatedRecordDO> list);

}
