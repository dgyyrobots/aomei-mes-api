package com.dofast.module.cmms.api.dvmachinery;

import com.dofast.module.cmms.api.dvmachinery.dto.DvMachineryDTO;

public interface DvMachineryApi {
    DvMachineryDTO getMachineryInfo(String machineryCode);

    DvMachineryDTO getErpMachineryInfo(String machineryCode);


    DvMachineryDTO getMachineryInfo(Long machineryId);

    void updateMachineryInfo(DvMachineryDTO machineryDTO);
}
