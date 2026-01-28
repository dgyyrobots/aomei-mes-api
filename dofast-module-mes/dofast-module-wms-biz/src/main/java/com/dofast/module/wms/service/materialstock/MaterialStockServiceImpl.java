package com.dofast.module.wms.service.materialstock;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;
import com.dofast.module.wms.controller.admin.materialstock.vo.*;
import com.dofast.module.wms.dal.dataobject.materialstock.MaterialStockDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.wms.convert.materialstock.MaterialStockConvert;
import com.dofast.module.wms.dal.mysql.materialstock.MaterialStockMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.wms.enums.ErrorCodeConstants.*;

/**
 * 库存记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class MaterialStockServiceImpl implements MaterialStockService {

    @Resource
    private MaterialStockMapper materialStockMapper;

    @Override
    public Long createMaterialStock(MaterialStockCreateReqVO createReqVO) {
        // 插入
        MaterialStockDO materialStock = MaterialStockConvert.INSTANCE.convert(createReqVO);
        materialStock.setRecptDate( LocalDateTime.now());
        materialStockMapper.insert(materialStock);
        // 返回
        return materialStock.getId();
    }

    @Override
    public void updateMaterialStock(MaterialStockUpdateReqVO updateReqVO) {
        // 校验存在
        validateMaterialStockExists(updateReqVO.getId());
        // 更新
        MaterialStockDO updateObj = MaterialStockConvert.INSTANCE.convert(updateReqVO);
        materialStockMapper.updateById(updateObj);
    }

    @Override
    public void deleteMaterialStock(Long id) {
        // 校验存在
        validateMaterialStockExists(id);
        // 删除
        materialStockMapper.deleteById(id);
    }

    @Override
    public void deleteMaterialStockList(List<Long> ids){
        // 删除
        materialStockMapper.deleteBatchIds(ids);
    }


    private void validateMaterialStockExists(Long id) {
        if (materialStockMapper.selectById(id) == null) {
            throw exception(MATERIAL_STOCK_NOT_EXISTS);
        }
    }

    @Override
    public MaterialStockDO getMaterialStock(Long id) {
        return materialStockMapper.selectById(id);
    }

    @Override
    public List<MaterialStockDO> getMaterialStockList(Collection<Long> ids) {
        return materialStockMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MaterialStockDO> getMaterialStockPage(MaterialStockPageReqVO pageReqVO) {
        return materialStockMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MaterialStockDO> getMaterialStockList(MaterialStockExportReqVO exportReqVO) {
        return materialStockMapper.selectList(exportReqVO);
    }

    @Override
    public List<MaterialStockDO> getMaterialStockListContainZero(MaterialStockExportReqVO exportReqVO){
        return materialStockMapper.selectListContainZero(exportReqVO);
    }


    @Override
    public Long getOriginIdByBatchCode(String batchCode){
        return materialStockMapper.getOriginIdByBatchCode(batchCode);
    }


    @Override
    public MaterialStockDO getNewMaterialStockByBatchCode(String batchCode){
        return materialStockMapper.getNewMaterialStockByBatchCode(batchCode);
    }


}
