package com.dofast.module.system.convert.dict;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.module.system.api.dict.dto.DictDataRespDTO;
import com.dofast.module.system.controller.admin.dict.vo.data.*;
import com.dofast.module.system.controller.app.dict.vo.AppDictDataRespVO;
import com.dofast.module.system.dal.dataobject.dict.DictDataDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface DictDataConvert {

    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);

    List<DictDataSimpleRespVO> convertList(List<DictDataDO> list);

    DictDataRespVO convert(DictDataDO bean);

    PageResult<DictDataRespVO> convertPage(PageResult<DictDataDO> page);

    DictDataDO convert(DictDataUpdateReqVO bean);

    DictDataDO convert(DictDataCreateReqVO bean);

    DictDataCreateReqVO convert01(DictDataDO bean);

    List<DictDataExcelVO> convertList02(List<DictDataDO> bean);

    DictDataRespDTO convert02(DictDataDO bean);

    List<AppDictDataRespVO> convertList03(List<DictDataDO> list);

    List<DictDataRespDTO> convertList04(List<DictDataDO> list);

    DictDataUpdateReqVO convert05(DictDataDO bean);

}
