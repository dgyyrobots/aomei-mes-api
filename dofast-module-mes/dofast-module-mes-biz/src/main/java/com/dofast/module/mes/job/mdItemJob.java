package com.dofast.module.mes.job;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.framework.tenant.core.job.TenantJob;
import com.dofast.module.mes.controller.admin.mditem.vo.MdItemBaseVO;
import com.dofast.module.mes.dal.dataobject.mditem.MdItemDO;
import com.dofast.module.mes.dal.mysql.mditem.MdItemMapper;
import com.dofast.module.mes.service.mditem.MdItemOracleService;
import com.dofast.module.mes.service.mditem.MdItemService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;

@Component
public class mdItemJob implements JobHandler {

    @Resource
    private MdItemService mdItemService;

    @Resource
    private MdItemMapper mdItemMapper;

    @Resource
    private MdItemOracleService mdItemOracleService;

    @Override
    public String execute(String param) throws Exception {
        // 获取ERP所有物料信息
        List<Map<String, Object>> result = mdItemOracleService.initMdItemInfo();
        List<MdItemDO> itemsToInsert = new ArrayList<>(); // 待插入数据
        List<MdItemDO> itemsToUp = new ArrayList<>(); // 待更新数据
        // 根据ERP物料数据校验MES中是否存在
        for (int i = 0; i < result.size(); i++) {
            Map<String, Object> map = result.get(i);
            String itemCode =String.valueOf(map.get("IMAA001")); // 物料编码
            MdItemDO itemInfo = mdItemService.getMdItemByItemCode(itemCode);
            if (itemInfo != null) {
                // 已存在, 修改当前物料信息
                itemInfo.setItemName(String.valueOf(map.get("IMAAL003"))); // 物料名称
                itemInfo.setUnitOfMeasure(Optional.ofNullable(String.valueOf(map.get("IMAA006"))).orElse("")); // 单位
                itemInfo.setSpecification(String.valueOf(map.get("IMAAL004"))); // 规格
                itemInfo.setItemTypeCode(String.valueOf(map.get("IMCA004"))); // 物料类型编码
                itemInfo.setItemOrProduct("ITEM"); // 默认设置为物料
                itemInfo.setEnableFlag("Y"); // 启用
                itemInfo.setSafeStockFlag("Y"); // 默认启用安全库存
                itemInfo.setDeleted(false); // 默认使用
                itemInfo.setItemTypeName(String.valueOf(map.get("ITEMTYPENAME"))); // 物料类型名称
                itemInfo.setItemTypeId((map.get("itemTypeId") != null) ? Long.valueOf(map.get("ITEMTYPEID").toString()) : null); // 物料类型Id
                itemInfo.setSuppliers(String.valueOf(map.get("IMAA101"))); // 供应商
                itemsToUp.add(itemInfo);
            }else{
                // 不存在, 新增数据
                MdItemDO item = new MdItemDO();
                item.setItemCode(String.valueOf(map.get("IMAA001"))); // 物料编码
                item.setItemName(String.valueOf(map.get("IMAAL003"))); // 物料名称
                item.setUnitOfMeasure(Optional.ofNullable(String.valueOf(map.get("IMAA006"))).orElse("")); // 单位
                item.setSpecification(String.valueOf(map.get("IMAAL004"))); // 规格
                item.setItemTypeCode(String.valueOf(map.get("IMCA004"))); // 物料类型编码
                item.setItemOrProduct("ITEM"); // 默认设置为物料
                item.setEnableFlag("Y"); // 启用
                item.setSafeStockFlag("Y"); // 默认启用安全库存
                item.setDeleted(false); // 默认使用
                item.setItemTypeName(String.valueOf(map.get("ITEMTYPENAME"))); // 物料类型名称
                item.setItemTypeId((map.get("ITEMTYPEID") != null) ? Long.valueOf(map.get("ITEMTYPEID").toString()) : null); // 物料类型Id
                item.setSuppliers(String.valueOf(map.get("IMAA101"))); // 供应商
                itemsToInsert.add(item);
            }
        }
        if(!itemsToInsert.isEmpty()){
            mdItemMapper.insertBatch(itemsToInsert);
        }
        if(!itemsToUp.isEmpty()){
            mdItemMapper.updateBatch(itemsToUp);
        }
        return null;
    }

}
