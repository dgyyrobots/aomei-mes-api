package com.dofast.module.mes.job;

import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.mes.convert.mdproductbom.MdProductBomConvert;
import com.dofast.module.mes.dal.dataobject.mditem.MdItemDO;
import com.dofast.module.mes.dal.dataobject.mdproductbom.MdProductBomDO;
import com.dofast.module.mes.dal.mysql.mditem.MdItemMapper;
import com.dofast.module.mes.dal.mysql.mdproductbom.MdProductBomMapper;
import com.dofast.module.mes.service.mdproductbom.MdProductBomOracleService;
import com.dofast.module.mes.service.mdproductbom.MdProductBomOracleServiceImpl;
import com.dofast.module.mes.service.mdproductbom.MdProductBomService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class mdProductBomJob implements JobHandler {

    @Resource
    private MdProductBomOracleService mdProductBomOracleService;

    @Resource
    private MdItemMapper mdItemMapper;

    @Resource
    private MdProductBomService mdProductBomService;

    @Resource
    private MdProductBomMapper mdProductBomMapper;

    @Override
    public String execute(String param) throws Exception {
        List<Map<String, Object>> productBomList = mdProductBomOracleService.initProductBomInfo();
        if (productBomList.isEmpty()) {
            return "获取产品BOM信息失败!";
        }
        List<MdProductBomDO> insertList = new ArrayList<>();
        List<MdProductBomDO> updateList = new ArrayList<>();

        for (Map<String, Object> productBom : productBomList) {
            // 获取当前产成品对应的物料ID
            MdItemDO product = mdItemMapper.selectOne(MdItemDO::getItemCode, productBom.get("ITEM_ID"));
            if (product == null) {
                continue;
            }
            // 校验当前产品料号是否存在
            // MdProductBomDO::getQuantity, productBom.get("BOM_QUANTITY"),  MdProductBomDO::getInverted , productBom.get("INVERTED") ,
            //MdProductBomDO query = mdProductBomMapper.selectOne(MdProductBomDO::getItemId, product.getId(), MdProductBomDO::getBomItemCode, productBom.get("BOM_ITEM_CODE"),  MdProductBomDO::getSequence , productBom.get("SEQUENCE") , MdProductBomDO::getUnitOfMeasure , productBom.get("UNIT_OF_MEASURE") , MdProductBomDO::getQuantity , productBom.get("BOM_QUANTITY"), MdProductBomDO::getInverted , productBom.get("INVERTED") );
            MdProductBomDO query = mdProductBomMapper.selectOne(MdProductBomDO::getItemId, product.getId(), MdProductBomDO::getBomItemCode, productBom.get("BOM_ITEM_CODE"),  MdProductBomDO::getUnitOfMeasure , productBom.get("UNIT_OF_MEASURE") , MdProductBomDO::getSequence , productBom.get("SEQUENCE"), MdProductBomDO::getInverted , productBom.get("INVERTED") );

            if (query == null) {
                // 插入产品BOM信息
                MdProductBomDO mdProductBomDO = new MdProductBomDO();
                mdProductBomDO.setItemId(product.getId());
                /*Integer itemId = Integer.valueOf(String.valueOf(productBom.get("ITEM_ID")));
                mdProductBomDO.setItemId(itemId.longValue());*/
                mdProductBomDO.setBomItemCode((String) productBom.get("BOM_ITEM_CODE"));
                // 根据物料料号获取当前的物料ID
                MdItemDO item = mdItemMapper.selectOne(MdItemDO::getItemCode, productBom.get("BOM_ITEM_CODE"));
                if (item != null) {
                    mdProductBomDO.setBomItemId(item.getId());
                }
                if (item.getId() == null) {
                    System.out.println("物料不存在!" + productBom.get("BOM_ITEM_ID"));
                }
                mdProductBomDO.setBomItemName((String) productBom.get("BOM_ITEM_NAME"));
                mdProductBomDO.setBomItemSpec((String) productBom.get("BOM_ITEM_SPEC"));
                mdProductBomDO.setUnitOfMeasure((String) productBom.get("UNIT_OF_MEASURE"));
                mdProductBomDO.setItemOrProduct("PRODUCT");
                mdProductBomDO.setInverted((String) productBom.get("INVERTED"));
                BigDecimal bigSequence = (BigDecimal) productBom.get("SEQUENCE");
                mdProductBomDO.setSequence(bigSequence.longValue());
                mdProductBomDO.setEnableFlag("Y");
                BigDecimal measuereQuantity = (BigDecimal) productBom.get("BOM_QUANTITY");
                mdProductBomDO.setQuantity(measuereQuantity.doubleValue());
                mdProductBomService.createMdProductBom(MdProductBomConvert.INSTANCE.convert01(mdProductBomDO));
                //insertList.add(mdProductBomDO);
            } else {
                // 更新产品BOM信息
                query.setItemId(product.getId());
                query.setBomItemCode((String) productBom.get("BOM_ITEM_CODE"));
                // 根据物料料号获取当前的物料ID
                MdItemDO item = mdItemMapper.selectOne(MdItemDO::getItemCode, productBom.get("BOM_ITEM_CODE"));
                if (item != null) {
                    query.setBomItemId(item.getId());
                }
                query.setBomItemName((String) productBom.get("BOM_ITEM_NAME"));
                query.setBomItemSpec((String) productBom.get("BOM_ITEM_SPEC"));
                query.setUnitOfMeasure((String) productBom.get("UNIT_OF_MEASURE"));
                query.setInverted((String) productBom.get("INVERTED"));
                BigDecimal bigSequence = (BigDecimal) productBom.get("SEQUENCE");
                query.setSequence(bigSequence.longValue());
                query.setItemOrProduct("PRODUCT");
                BigDecimal measuereQuantity = (BigDecimal) productBom.get("BOM_QUANTITY");
                query.setQuantity(measuereQuantity.doubleValue());
                //updateList.add(query);
                mdProductBomService.updateMdProductBom(MdProductBomConvert.INSTANCE.convert02(query));
            }
        }
       /* if (!insertList.isEmpty()) {
            mdProductBomMapper.insertBatch(insertList);
        }*/
        /*if (!updateList.isEmpty()) {
            mdProductBomMapper.updateBatch(updateList);
        }*/
        return "success";
    }
}
