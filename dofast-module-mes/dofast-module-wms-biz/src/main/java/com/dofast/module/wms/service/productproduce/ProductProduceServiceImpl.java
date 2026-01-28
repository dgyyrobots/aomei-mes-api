package com.dofast.module.wms.service.productproduce;

import cn.hutool.core.bean.BeanUtil;
import com.dofast.framework.common.pojo.UserConstants;
import com.dofast.module.mes.api.WorkStationAPi.WorkStationApi;
import com.dofast.module.mes.api.WorkStationAPi.dto.WorkStationDTO;
import com.dofast.module.mes.controller.admin.mditem.vo.MdItemUpdateReqVO;
import com.dofast.module.mes.dal.dataobject.mditem.MdItemDO;
import com.dofast.module.mes.service.mditem.MdItemService;
import com.dofast.module.pro.api.FeedbackApi.dto.FeedbackDTO;
import com.dofast.module.pro.api.ProcessApi.ProcessApi;
import com.dofast.module.pro.api.ProcessApi.dto.ProcessDTO;
import com.dofast.module.pro.api.TaskApi.TaskApi;
import com.dofast.module.pro.api.TaskApi.dto.TaskDTO;
import com.dofast.module.pro.api.WorkorderApi.WorkorderApi;
import com.dofast.module.pro.api.WorkorderApi.dto.WorkorderDTO;
import com.dofast.module.wms.dal.dataobject.productproduce.ProductProductTxBean;
import com.dofast.module.wms.dal.dataobject.productproduceline.ProductProduceLineDO;
import com.dofast.module.wms.dal.dataobject.storagearea.StorageAreaDO;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import com.dofast.module.wms.dal.dataobject.warehouse.WarehouseDO;
import com.dofast.module.wms.dal.mysql.productproduceline.ProductProduceLineMapper;
import com.dofast.module.wms.service.storagearea.StorageAreaService;
import com.dofast.module.wms.service.storagelocation.StorageLocationService;
import com.dofast.module.wms.service.warehouse.WarehouseService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.dofast.module.wms.controller.admin.productproduce.vo.*;
import com.dofast.module.wms.dal.dataobject.productproduce.ProductProduceDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.wms.convert.productproduce.ProductProduceConvert;
import com.dofast.module.wms.dal.mysql.productproduce.ProductProduceMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.pro.enums.ErrorCodeConstants.*;
import static com.dofast.module.wms.enums.ErrorCodeConstants.*;
import static com.dofast.module.wms.enums.ErrorCodeConstants.PRODUCT_PRODUCE_NOT_EXISTS;

/**
 * 产品产出记录 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class ProductProduceServiceImpl implements ProductProduceService {

    @Resource
    private ProductProduceMapper productProduceMapper;

    @Resource
    private ProcessApi processApi;

    @Resource
    private WorkStationApi workStationApi;

    @Resource
    private WorkorderApi workorderApi;

    @Resource
    private TaskApi taskApi;

    @Resource
    private ProductProduceLineMapper productProduceLineMapper;

    @Resource
    private MdItemService mdItemService;

    @Resource
    private WarehouseService warehouseService;

    @Resource
    private StorageLocationService storageLocationService;

    @Resource
    private StorageAreaService storageAreaService;

    @Override
    public Long createProductProduce(ProductProduceCreateReqVO createReqVO) {
        // 插入
        ProductProduceDO productProduce = ProductProduceConvert.INSTANCE.convert(createReqVO);
        productProduceMapper.insert(productProduce);
        // 返回
        return productProduce.getId();
    }

    @Override
    public void updateProductProduce(ProductProduceUpdateReqVO updateReqVO) {
        // 校验存在
        validateProductProduceExists(updateReqVO.getId());
        // 更新
        ProductProduceDO updateObj = ProductProduceConvert.INSTANCE.convert(updateReqVO);
        int i = productProduceMapper.updateById(updateObj);
        if (i<=0){
            throw exception(PROUDT_PRODUCT_LOG);
        }
    }

    @Override
    public void deleteProductProduce(Long id) {
        // 校验存在
        validateProductProduceExists(id);
        // 删除
        productProduceMapper.deleteById(id);
    }

    private void validateProductProduceExists(Long id) {
        if (productProduceMapper.selectById(id) == null) {
            throw exception(PRODUCT_PRODUCE_NOT_EXISTS);
        }
    }

    @Override
    public ProductProduceDO getProductProduce(Long id) {
        return productProduceMapper.selectById(id);
    }

    @Override
    public List<ProductProduceDO> getProductProduceList(Collection<Long> ids) {
        return productProduceMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ProductProduceDO> getProductProducePage(ProductProducePageReqVO pageReqVO) {
        return productProduceMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ProductProduceDO> getProductProduceList(ProductProduceExportReqVO exportReqVO) {
        return productProduceMapper.selectList(exportReqVO);
    }

    @Override
    public ProductProduceDO generateProductProduce(FeedbackDTO feedback, String batchCode) {
        WorkorderDTO workorder = workorderApi.getWorkorder(feedback.getWorkorderId());
        WorkStationDTO workstation = workStationApi.getWorkstation(feedback.getWorkstationId());
        ProcessDTO process = processApi.getcess(workstation.getProcessId());
        TaskDTO task = taskApi.getTask(feedback.getTaskId());
        //MdItemDO itemDO = mdItemService.getMdItem(workorder.getProductId());

        //生成产成品单据头信息
        ProductProduceDO productProduce = new ProductProduceDO();
        productProduce.setWorkorderId(feedback.getWorkorderId());
        productProduce.setWorkorderCode(feedback.getWorkorderCode());
        productProduce.setWorkorderName(feedback.getWorkorderName());
        productProduce.setOrderSource(workorder.getOrderSource());

        productProduce.setTaskId(feedback.getTaskId());
        productProduce.setTaskCode(task.getTaskCode());
        productProduce.setTaskName(task.getTaskName());

        productProduce.setWorkstationId(feedback.getWorkstationId());
        productProduce.setWorkstationCode(workstation.getWorkstationCode());
        productProduce.setWorkstationName(workstation.getWorkstationName());

        productProduce.setProcessId(process.getId());
        productProduce.setProcessCode(process.getProcessCode());
        productProduce.setProcessName(process.getProcessName());

        productProduce.setProduceDate(LocalDateTime.now());
        productProduce.setStatus(UserConstants.ORDER_STATUS_PREPARE);
        int insert = productProduceMapper.insert(productProduce);
        if (insert<=0){
            throw exception(STACK_UPDATE_LOG);
        }
        //生成产成品单据行信息; 以后如果是在生产过程中产生多种副产品可以在这里添加更多的行信息进行支持
        ProductProduceLineDO line = new ProductProduceLineDO();
        line.setRecordId(productProduce.getId());
        line.setItemId(feedback.getItemId());
        line.setItemCode(feedback.getItemCode());
        line.setItemName(feedback.getItemName());
        line.setSpecification(feedback.getSpecification());
        line.setUnitOfMeasure(feedback.getUnitOfMeasure());
        line.setQuantityProduce(feedback.getQuantityFeedback());
        // 产成品批次号规则, 任务单号+2位流水号
        // 流水号在管控在任务单号内
        // 校验流水号是否存在


        /*if (task.getParentBatchCode() == null) {
            // 若母批次号为空，生成母批次号
            String serial = "001";
            // 修改任务单表
            task.setParentBatchCode(task.getTaskCode());
            taskApi.updateTask(task);
            line.setBatchCode(task.getTaskCode() + "-" + serial);
        } else {
            line.setBatchCode(task.getParentBatchCode());
            String serial = task.getSerial();
            if (serial == null) {
                serial = "01";
            } else {
                int serialInt = Integer.parseInt(serial);
                serialInt++;
                serial = String.format("%03d", serialInt);
            }
            line.setBatchCode(task.getTaskCode() + "-" + serial);
            //task.setSerial(serial);
            taskApi.updateTask(task);
        }*/

        line.setBatchCode(batchCode);
        line.setOrderSource(workorder.getOrderSource());
        int insert1 = productProduceLineMapper.insert(line);
        if (insert1<=0){
            throw exception(PRODUCT_PEODUCT_ROW);
        }

        //不需要更新物料信息
        // 在其他方法内将产成品追加之下一个制成的线边仓或主仓
       /* WarehouseDO warehouse = warehouseService.getWarehouse(workstation.getWarehouseId());
        StorageLocationDO location = storageLocationService.getStorageLocation(workstation.getLocationId());
        StorageAreaDO area = storageAreaService.getStorageArea(workstation.getAreaId());
        itemDO.setWarehouseId(warehouse.getId());
        itemDO.setWarehouseCode(warehouse.getWarehouseCode());
        itemDO.setWarehouseName(warehouse.getWarehouseName());
        itemDO.setLocationId(location.getId());
        itemDO.setLocationCode(location.getLocationCode());
        itemDO.setLocationName(location.getLocationName());
        itemDO.setAreaId(area.getId());
        itemDO.setAreaCode(area.getAreaCode());
        itemDO.setAreaName(area.getAreaName());
        MdItemUpdateReqVO mdItemUpdateReqVO = BeanUtil.toBean(itemDO, MdItemUpdateReqVO.class);
        mdItemUpdateReqVO.setRecptDate(LocalDateTime.now());
        mdItemService.updateMdItem(mdItemUpdateReqVO);*/

        return productProduce;
    }

    @Override
    public List<ProductProductTxBean> getTxBeans(Long recordId) {
        return productProduceMapper.getTxBeans(recordId);
    }
}
