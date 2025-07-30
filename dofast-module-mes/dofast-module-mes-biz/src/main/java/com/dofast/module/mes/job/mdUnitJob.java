package com.dofast.module.mes.job;

import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.mes.controller.admin.mdunitconverse.vo.MdUnitConverseExcelVO;
import com.dofast.module.mes.controller.admin.mdunitconverse.vo.MdUnitConverseExportReqVO;
import com.dofast.module.mes.dal.dataobject.mdunitconverse.MdUnitConverseDO;
import com.dofast.module.mes.dal.dataobject.mdunitmeasure.MdUnitMeasureDO;
import com.dofast.module.mes.dal.mysql.mdunitconverse.MdUnitConverseMapper;
import com.dofast.module.mes.dal.mysql.mdunitmeasure.MdUnitMeasureMapper;
import com.dofast.module.mes.service.mditem.MdItemService;
import com.dofast.module.mes.service.mdunitconverse.MdUnitConverseService;
import com.dofast.module.mes.service.mdunitmeasure.MdUnitMeasureOracleService;
import com.dofast.module.mes.service.mdunitmeasure.MdUnitMeasureService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class mdUnitJob implements JobHandler {

    @Resource
    private MdUnitMeasureService mdUnitService;

    @Resource
    private MdUnitConverseService mdUnitConverseService;

    @Resource
    private MdUnitMeasureMapper mdUnitMeasureMapper;

    @Resource
    private MdUnitMeasureOracleService mdUnitOracleService;

    @Resource
    private MdUnitConverseMapper mdUnitConverseMapper;

    @Override
    public String execute(String param) throws Exception {
        List<Map<String, Object>> unitInfo = mdUnitOracleService.initUnitMeasure(); // 初始化单位
        List<Map<String, Object>> converseInfo = mdUnitOracleService.initUnitConverse(); // 初始化单位换算数据

        // 单位信息
        List<MdUnitMeasureDO> unitToInsert = new ArrayList<>(); // 待插入的单位
        List<MdUnitMeasureDO> unitToUp = new ArrayList<>(); // 待更新的单位

        // 单位换算
        List<MdUnitConverseDO> converseToInsert = new ArrayList<>(); // 待插入的单位换算
        List<MdUnitConverseDO> converseToUp = new ArrayList<>(); // 待更新的单位换算

        // 单位类型
        if (unitInfo.size() > 0) {
            for (Map<String, Object> map : unitInfo) {
                String measurecode = (String) map.get("MEASURECODE");
                // 校验是否存在
                // 校验当前单位信息是否存在
                MdUnitMeasureDO unit = mdUnitMeasureMapper.selectOne(MdUnitMeasureDO::getMeasureCode, measurecode);
                if (unit != null) {
                    // 已存在, 修改当前单位信息
                    unit.setMeasureCode((String) map.get("MEASURECODE")); // 单位编码
                    unit.setMeasureName((String) map.get("MEASURENAME")); // 单位名称
                    unit.setEnableFlag("Y"); // 启用标识
                    unit.setDeleted(false); // 默认使用
                    unitToUp.add(unit);
                } else {
                    MdUnitMeasureDO addUnit = new MdUnitMeasureDO();
                    addUnit.setMeasureCode((String) map.get("MEASURECODE")); // 单位编码
                    addUnit.setMeasureName((String) map.get("MEASURENAME")); // 单位名称
                    addUnit.setEnableFlag("Y"); // 启用标识
                    addUnit.setDeleted(false); // 默认使用
                    unitToInsert.add(addUnit);
                }
            }
        }
        // 单位换算
        if (converseInfo.size() > 0) {
            for (Map<String, Object> map : converseInfo) {
                String measurecode = (String) map.get("MEASURECODE");
                String conversecode = (String) map.get("CONVERCODE");
                // 校验当前原编码与转换单位是否存在
                MdUnitConverseDO converInfo = mdUnitConverseMapper.selectOne(MdUnitConverseDO::getMeasureCode, measurecode, MdUnitConverseDO::getConverseCode, conversecode);
              /*  MdUnitConverseExportReqVO conver = new MdUnitConverseExportReqVO();
                conver.setMeasureCode(measurecode);
                conver.setConverseCode(conversecode);
                List<MdUnitConverseDO> converList = mdUnitConverseService.getMdUnitConverseList(conver);*/
                if (converInfo !=null) {
                    // 已存在, 修改当前单位信息
                    converInfo.setOriginCount((BigDecimal) map.get("ORIGINCOUNT")); // 原单位数量
                    converInfo.setConverseCount((BigDecimal) map.get("CONVERCOUNT")); // 转换单位数量
                    converseToUp.add(converInfo);
                } else {
                    MdUnitConverseDO addConverse = new MdUnitConverseDO();
                    addConverse.setMeasureCode((String) map.get("MEASURECODE")); // 单位编码
                    addConverse.setConverseCode((String) map.get("CONVERCODE")); // 单位换算编码
                    addConverse.setOriginCount((BigDecimal) map.get("ORIGINCOUNT")); // 原单位数量
                    addConverse.setConverseCount((BigDecimal) map.get("CONVERCOUNT")); // 转换单位数量
                    addConverse.setDeleted((String) map.get("DELETED") == "Y" ? true : false); // 是否禁用
                    converseToInsert.add(addConverse);
                }
            }
        }
        if (!unitToInsert.isEmpty()) {
            mdUnitMeasureMapper.insertBatch(unitToInsert);
        }
        if (!unitToUp.isEmpty()) {
            mdUnitMeasureMapper.updateBatch(unitToUp);
        }
        if (!converseToInsert.isEmpty()) {
            mdUnitConverseMapper.insertBatch(converseToInsert);
        }
        if(!converseToUp.isEmpty()){
            mdUnitConverseMapper.updateBatch(converseToUp);
        }
        return "success";
    }

}
