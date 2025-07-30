package com.dofast.module.mes.api.WorkShopApi;

import com.dofast.module.mes.api.WorkShopApi.dto.MdWorkshopDTO;

public interface MdWorkshopApi {

    MdWorkshopDTO getWorkShopById(Long id);

    MdWorkshopDTO getWorkShopByCode(String workshopCode);

}
