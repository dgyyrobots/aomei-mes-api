package com.dofast.module.mes.api.WorkShopApi;

import com.dofast.module.mes.api.WorkShopApi.dto.MdWorkshopDTO;
import com.dofast.module.mes.convert.mdworkshop.MdWorkshopConvert;
import com.dofast.module.mes.dal.dataobject.mdworkshop.MdWorkshopDO;
import com.dofast.module.mes.service.mdworkshop.MdWorkshopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MdWorkshopApiImpl implements MdWorkshopApi{

    @Resource
    private MdWorkshopService mdWorkshopService;

    @Override
    public MdWorkshopDTO getWorkShopById(Long id) {
        MdWorkshopDO workshopDO = mdWorkshopService.getMdWorkshop(id);
        return MdWorkshopConvert.INSTANCE.convert03(workshopDO);
    }

    @Override
    public MdWorkshopDTO getWorkShopByCode(String workshopCode) {
        MdWorkshopDO workshopDO = mdWorkshopService.getMdWorkshop(workshopCode);
        return MdWorkshopConvert.INSTANCE.convert03(workshopDO);
    }
}
