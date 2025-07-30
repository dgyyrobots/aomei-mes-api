package com.dofast.module.mes.job;


import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.mes.dal.dataobject.mdworkshop.MdWorkshopDO;
import com.dofast.module.mes.dal.dataobject.mdworkstation.MdWorkstationDO;
import com.dofast.module.mes.dal.mysql.mdworkshop.MdWorkshopMapper;
import com.dofast.module.mes.dal.mysql.mdworkstation.MdWorkstationMapper;
import com.dofast.module.mes.dal.oracle.mdworkstation.MdWorkstationOracleMapper;
import com.dofast.module.mes.service.mdworkshop.MdWorkshopOracleService;
import com.dofast.module.mes.service.mdworkstation.MdWorkstationOracleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


// 车间与工作站定时器
@Component
public class mdWorkShopJob implements JobHandler {

    @Resource
    private MdWorkshopOracleService mdWorkshopOracleService;

    @Resource
    private MdWorkstationOracleService mdWorkstationOracleService;


    @Resource
    private MdWorkshopMapper mdWorkshopmapper;

    @Resource
    private MdWorkstationMapper mdWorkstationMapper;

    @Override
    public String execute(String param) throws Exception {
        // 从ERP获取车间信息
        List<Map<String, Object>> workshopList = mdWorkshopOracleService.initWorkshop();
        if (workshopList.size() == 0) {
            return "未获取车间信息, 定时器结束!";
        }
        // 车间信息
        List<MdWorkshopDO> workshopToInsert = new ArrayList<>(); // 待插入的车间信息
        List<MdWorkshopDO> workshopToUp = new ArrayList<>(); // 待更新的车间信息
        // 循环车间信息
        for (Map<String, Object> workshop : workshopList) {
            // 循环工作站信息
            String workshopCode = (String) workshop.get("WORKSHOPCODE");
            // 判定MES中是否存在车间信息
            MdWorkshopDO exist = mdWorkshopmapper.selectOne(MdWorkshopDO::getWorkshopCode, workshopCode);
            if (exist == null) {
                // 若不存在, 则新增
                MdWorkshopDO addWorkshop = new MdWorkshopDO();
                addWorkshop.setWorkshopCode(workshopCode); // 车间编码
                addWorkshop.setWorkshopName((String) workshop.get("WORKSHOPNAME"));
                String enableFlag = (String) workshop.get("DELETED");
                addWorkshop.setDeleted(!"Y".equals(enableFlag)); // 是否删除
                addWorkshop.setEnableFlag(enableFlag); // 是否启用
                workshopToInsert.add(addWorkshop);
            } else {
                // 若存在, 则更新
                exist.setWorkshopName((String) workshop.get("WORKSHOPNAME"));
                String enableFlag = (String) workshop.get("DELETED");
                exist.setDeleted(!"Y".equals(enableFlag)); // 是否删除
                exist.setEnableFlag(enableFlag); // 是否启用
                workshopToUp.add(exist);
            }
        }
        if (!workshopToInsert.isEmpty()) {
            mdWorkshopmapper.insertBatch(workshopToInsert);
        }
        if (!workshopToUp.isEmpty()) {
            mdWorkshopmapper.updateBatch(workshopToUp);
        }

        // 开始追加工作站信息
        List<Map<String, Object>> workstationList = mdWorkstationOracleService.initWorkstation();
        if (workstationList.size() == 0) {
            return "未获取工作站信息, 定时器结束!";
        }
        // 单位信息
        List<MdWorkstationDO> workstationToInsert = new ArrayList<>(); // 待插入的工作站信息
        List<MdWorkstationDO> workstationToUp = new ArrayList<>(); // 待更新的工作站信息
        // ERP中工作站不绑定线边仓, 库区, 库位关系
        // 由MES进行管控
        for (Map<String, Object> workstation : workstationList) {
            // 判定MES中是否存在工作站信息
            String workstationCode = (String) workstation.get("WORKSTATIONCODE");
            MdWorkstationDO exist = mdWorkstationMapper.selectOne(MdWorkstationDO::getWorkstationCode, workstationCode);
            if (exist == null) {
                // 若不存在, 则新增
                MdWorkstationDO addWorkstation = new MdWorkstationDO();
                addWorkstation.setWorkstationCode(workstationCode);
                addWorkstation.setWorkstationName((String) workstation.get("WORKSTATIONNAME"));
                //addWorkstation.setWorkshopId((Long) workstation.get("WORKSHOPID"));
                BigDecimal workshopId = (BigDecimal)workstation.get("WORKSHOPID");// 车间ID
                addWorkstation.setWorkshopId(workshopId.longValue());
                addWorkstation.setWorkshopCode((String) workstation.get("WORKSHOPCODE"));
                addWorkstation.setWorkshopName((String) workstation.get("WORKSHOPNAME"));
                addWorkstation.setDeleted(!"Y".equals(workstation.get("DELETED"))); // 是否删除
                workstationToInsert.add(addWorkstation);
            } else {
                // 若存在, 则更新
                exist.setWorkstationCode(workstationCode);
                exist.setWorkstationName((String) workstation.get("WORKSTATIONNAME"));
                // exist.setWorkshopId((Long) workstation.get("WORKSHOPID")); // 车间ID
                BigDecimal workshopId = (BigDecimal)workstation.get("WORKSHOPID");// 车间ID
                exist.setWorkshopId(workshopId.longValue());
                exist.setWorkshopCode((String) workstation.get("WORKSHOPCODE"));
                exist.setWorkshopName((String) workstation.get("WORKSHOPNAME"));
                exist.setDeleted(!"Y".equals(workstation.get("DELETED"))); // 是否删除
                workstationToUp.add(exist);
            }
        }
        if (!workstationToInsert.isEmpty()) {
            mdWorkstationMapper.insertBatch(workstationToInsert);
        }
        if (!workstationToUp.isEmpty()) {
            mdWorkstationMapper.updateBatch(workstationToUp);
        }
        return "success";
    }
}
