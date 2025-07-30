package com.dofast.module.cmms.api.dvmachinery;

import com.dofast.module.cmms.api.dvmachinery.dto.DvMachineryDTO;
import com.dofast.module.cmms.controller.admin.dvmachinery.vo.DvMachineryExportReqVO;
import com.dofast.module.cmms.convert.dvmachinery.DvMachineryConvert;
import com.dofast.module.cmms.dal.dataobject.dvmachinery.DvMachineryDO;
import com.dofast.module.cmms.service.dvmachinery.DvMachineryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DvMachineryApiImpl  implements DvMachineryApi {

    @Resource
    private DvMachineryService dvMachineryService;

    @Override
    public DvMachineryDTO getMachineryInfo(String machineryCode){
        DvMachineryDO dvMachineryDO =  dvMachineryService.getDvMachinery(machineryCode);
        return DvMachineryConvert.INSTANCE.convert01(dvMachineryDO);
    }

    @Override
    public DvMachineryDTO getErpMachineryInfo(String machineryCode){
        DvMachineryDO dvMachineryDO =  dvMachineryService.getDvMachineryList(new DvMachineryExportReqVO().setErpMachineryCode(machineryCode)).get(0);
        return DvMachineryConvert.INSTANCE.convert01(dvMachineryDO);
    }

    @Override
    public DvMachineryDTO getMachineryInfo(Long machineryId){
        DvMachineryDO dvMachineryDO =  dvMachineryService.getDvMachinery(machineryId);
        return DvMachineryConvert.INSTANCE.convert01(dvMachineryDO);
    }

    @Override
    public void updateMachineryInfo(DvMachineryDTO machineryDTO){
        dvMachineryService.updateDvMachinery(DvMachineryConvert.INSTANCE.convert02(machineryDTO));
    }

}
