package com.dofast.module.wms.controller.admin.materialstock;

import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.wms.dal.dataobject.storagearea.StorageAreaDO;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import com.dofast.module.wms.dal.dataobject.warehouse.WarehouseDO;
import com.dofast.module.wms.service.storagearea.StorageAreaService;
import com.dofast.module.wms.service.storagelocation.StorageLocationService;
import com.dofast.module.wms.service.warehouse.WarehouseService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.wms.controller.admin.materialstock.vo.*;
import com.dofast.module.wms.dal.dataobject.materialstock.MaterialStockDO;
import com.dofast.module.wms.convert.materialstock.MaterialStockConvert;
import com.dofast.module.wms.service.materialstock.MaterialStockService;

@Tag(name = "仓储管理 - 库存记录")
@RestController
@RequestMapping("/mes/wms/material-stock")
@Validated
public class MaterialStockController {

    @Resource
    private MaterialStockService materialStockService;

    @Resource
    private WarehouseService warehouseService;

    @Resource
    private StorageLocationService storageLocationService;

    @Resource
    private StorageAreaService storageAreaService;

    @PostMapping("/create")
    @Operation(summary = "创建库存记录")
   //  @PreAuthorize("@ss.hasPermission('wms:material-stock:create')")
    public CommonResult<Long> createMaterialStock(@Valid @RequestBody MaterialStockCreateReqVO createReqVO) {
        if(StrUtils.isNotNull(createReqVO.getWarehouseId())){
            WarehouseDO warehouseDO = warehouseService.getWarehouse(createReqVO.getWarehouseId());
            createReqVO.setWarehouseCode(warehouseDO.getWarehouseCode());
            createReqVO.setWarehouseName(warehouseDO.getWarehouseName());
        }
        if(StrUtils.isNotNull(createReqVO.getLocationId())){
            StorageLocationDO storageLocationDO = storageLocationService.getStorageLocation(createReqVO.getLocationId());
            createReqVO.setLocationCode(storageLocationDO.getLocationCode());
            createReqVO.setLocationName(storageLocationDO.getLocationName());
        }
        if(StrUtils.isNotNull(createReqVO.getAreaId())){
            StorageAreaDO storageAreaDO = storageAreaService.getStorageArea(createReqVO.getAreaId());
            createReqVO.setAreaCode(storageAreaDO.getAreaCode());
            createReqVO.setAreaName(storageAreaDO.getAreaName());
        }

        return success(materialStockService.createMaterialStock(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新库存记录")
    @PreAuthorize("@ss.hasPermission('wms:material-stock:update')")
    public CommonResult<Boolean> updateMaterialStock(@Valid @RequestBody MaterialStockUpdateReqVO updateReqVO) {
        if(StrUtils.isNotNull(updateReqVO.getWarehouseId())){
            WarehouseDO warehouse = warehouseService.getWarehouse(updateReqVO.getWarehouseId());
            updateReqVO.setWarehouseCode(warehouse.getWarehouseCode());
            updateReqVO.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StrUtils.isNotNull(updateReqVO.getLocationId())){
            StorageLocationDO location = storageLocationService.getStorageLocation(updateReqVO.getLocationId());
            updateReqVO.setLocationCode(location.getLocationCode());
            updateReqVO.setLocationName(location.getLocationName());
        }
        if(StrUtils.isNotNull(updateReqVO.getAreaId())){
            StorageAreaDO area = storageAreaService.getStorageArea(updateReqVO.getAreaId());
            updateReqVO.setAreaCode(area.getAreaCode());
            updateReqVO.setAreaName(area.getAreaName());
        }
        materialStockService.updateMaterialStock(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除库存记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wms:material-stock:delete')")
    public CommonResult<Boolean> deleteMaterialStock(@RequestParam("id") Long id) {
        materialStockService.deleteMaterialStock(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得库存记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wms:material-stock:query')")
    public CommonResult<MaterialStockRespVO> getMaterialStock(@RequestParam("id") Long id) {
        MaterialStockDO materialStock = materialStockService.getMaterialStock(id);
        return success(MaterialStockConvert.INSTANCE.convert(materialStock));
    }


    @GetMapping("/list")
    @Operation(summary = "获得库存记录列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('wms:material-stock:query')")
    public CommonResult<List<MaterialStockRespVO>> getMaterialStockList(@RequestParam("ids") Collection<Long> ids) {
        List<MaterialStockDO> list = materialStockService.getMaterialStockList(ids);
        return success(MaterialStockConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得库存记录分页")
    @PreAuthorize("@ss.hasPermission('wms:material-stock:query')")
    public CommonResult<PageResult<MaterialStockRespVO>> getMaterialStockPage(@Valid MaterialStockPageReqVO pageVO) {
        PageResult<MaterialStockDO> pageResult = materialStockService.getMaterialStockPage(pageVO);
        return success(MaterialStockConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出库存记录 Excel")
    @PreAuthorize("@ss.hasPermission('wms:material-stock:export')")
    @OperateLog(type = EXPORT)
    public void exportMaterialStockExcel(@Valid MaterialStockExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<MaterialStockDO> list = materialStockService.getMaterialStockList(exportReqVO);
        // 导出 Excel
        List<MaterialStockExcelVO> datas = MaterialStockConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "库存记录.xls", "数据", MaterialStockExcelVO.class, datas);
    }

}
