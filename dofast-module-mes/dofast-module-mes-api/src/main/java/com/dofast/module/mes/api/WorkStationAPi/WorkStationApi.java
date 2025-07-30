package com.dofast.module.mes.api.WorkStationAPi;

import com.dofast.module.mes.api.WorkStationAPi.dto.WorkStationDTO;

public interface WorkStationApi {
    public WorkStationDTO getWorkstation(Long id);

    public WorkStationDTO getWorkstation(String workStationCode, String processCode);

    public WorkStationDTO getWorkstationByProcessCode(String processCode);


}
