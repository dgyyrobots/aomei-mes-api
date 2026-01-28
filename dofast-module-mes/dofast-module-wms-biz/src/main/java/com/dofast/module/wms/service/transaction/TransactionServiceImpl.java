package com.dofast.module.wms.service.transaction;

import cn.hutool.core.bean.BeanUtil;
import com.dofast.framework.common.util.date.DateUtils;
import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.mes.api.ItemApi.MdItemApi;
import com.dofast.module.mes.api.ItemApi.dto.MdItemDTO;
import com.dofast.module.wms.controller.admin.materialstock.vo.MaterialStockBaseVO;
import com.dofast.module.wms.controller.admin.materialstock.vo.MaterialStockUpdateReqVO;
import com.dofast.module.wms.convert.materialstock.MaterialStockConvert;
import com.dofast.module.wms.dal.dataobject.materialstock.MaterialStockDO;
import com.dofast.module.wms.dal.mysql.materialstock.MaterialStockMapper;
import com.dofast.module.wms.enums.ErrorCodeConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import com.dofast.module.wms.controller.admin.transaction.vo.*;
import com.dofast.module.wms.dal.dataobject.transaction.TransactionDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.wms.convert.transaction.TransactionConvert;
import com.dofast.module.wms.dal.mysql.transaction.TransactionMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.pro.enums.ErrorCodeConstants.STACK_UPDATE_LOG;
import static com.dofast.module.pro.enums.ErrorCodeConstants.STOCK_COUNT_UPDATE;
import static com.dofast.module.wms.enums.ErrorCodeConstants.*;

/**
 * 库存事务 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TransactionServiceImpl implements TransactionService {

    @Resource
    private TransactionMapper transactionMapper;
    @Resource
    private MaterialStockMapper materialStockMapper;

    @Resource
    private MdItemApi mdItemApi;

    @Override
    public synchronized TransactionDO processTransaction(TransactionUpdateReqVO baseVO) {
//        MaterialStockUpdateReqVO stock = new MaterialStockUpdateReqVO();

        validate(baseVO);
        MaterialStockUpdateReqVO stock =BeanUtil.toBean(baseVO, MaterialStockUpdateReqVO.class);
        initStock(baseVO,stock);

        MaterialStockBaseVO materialStockBaseVO = BeanUtil.toBean(stock, MaterialStockBaseVO.class);
        MaterialStockDO ms =materialStockMapper.loadMaterialStockContainZero(materialStockBaseVO);

        BigDecimal quantity = baseVO.getTransactionQuantity().multiply(new BigDecimal(baseVO.getTransactionFlag()));
        if(StrUtils.isNotNull(ms)){
            //MES已存在
            BigDecimal resultQuantity =ms.getQuantityOnhand().add(quantity);
            if(baseVO.isStorageCheckFlag() && resultQuantity.compareTo(new BigDecimal(0))<0){
                throw exception(MATERIAL_STOCK_COUNT_NOT_ENOUGH);
            }
            stock.setQuantityOnhand(resultQuantity);
            stock.setId(ms.getId());

            MaterialStockDO updateObj = MaterialStockConvert.INSTANCE.convert(stock);
            int i = materialStockMapper.updateById(updateObj);
            if (i<=0){
                throw exception(STOCK_COUNT_UPDATE);
            }
        }else {
            //MES不存在
            stock.setQuantityOnhand(quantity);
            MaterialStockDO updateObj = MaterialStockConvert.INSTANCE.convert(stock);
            int insert = materialStockMapper.insert(updateObj);
            if (insert<=0){
                throw exception(STACK_UPDATE_LOG);
            }
            stock.setId(updateObj.getId());
        }
        baseVO.setMaterialStockId(stock.getId());
        baseVO.setTransactionQuantity(quantity);

        TransactionDO inserObject = TransactionConvert.INSTANCE.convert(baseVO);

        return inserObject;
    }
    public void initStock(TransactionBaseVO transaction, MaterialStockBaseVO stock){

        if(StrUtils.isNotNull(transaction.getMaterialStockId())){
            MaterialStockDO st = materialStockMapper.selectById(transaction.getMaterialStockId());
            BeanUtils.copyProperties(st,stock);
        }else{
            MdItemDTO item =mdItemApi.getMdItemByCode(transaction.getItemCode());
            stock.setItemTypeId(item.getItemTypeId());
            stock.setItemId(transaction.getItemId());
            stock.setItemCode(transaction.getItemCode());
            stock.setItemName(transaction.getItemName());
            stock.setSpecification(transaction.getSpecification());
            stock.setUnitOfMeasure(transaction.getUnitOfMeasure());
            stock.setBatchCode(transaction.getBatchCode());
            stock.setWarehouseId(transaction.getWarehouseId());
            stock.setWarehouseCode(transaction.getWarehouseCode());
            stock.setWarehouseName(transaction.getWarehouseName());
            stock.setLocationId(transaction.getLocationId());
            stock.setLocationCode(transaction.getLocationCode());
            stock.setLocationName(transaction.getLocationName());
            if(StrUtils.isNotNull(transaction.getAreaId())){
                stock.setAreaId(transaction.getAreaId());
                stock.setAreaCode(transaction.getAreaCode());
                stock.setAreaName(transaction.getAreaName());
            }
            if(StrUtils.isNotNull(transaction.getVendorId())){
                stock.setVendorId(transaction.getVendorId());
                stock.setVendorCode(transaction.getVendorCode());
                stock.setVendorName(transaction.getVendorName());
                stock.setVendorNick(transaction.getVendorNick());
            }
            //使用库存事务日期初始化入库日期
            //一般在入库的时候才会涉及到materialStock的新增，出库的时候都是出的现有库存
            if(StrUtils.isNotNull(transaction.getRecptDate())){
                stock.setRecptDate(transaction.getRecptDate());
            }else{
                stock.setRecptDate(LocalDateTime.now());
            }

            //使用库存事务上的生产工单初始化库存记录上的生产工单，以支持某些情况下库存需要标记生产工单的情况
            if(StrUtils.isNotNull(transaction.getWorkorderId())){
                stock.setWorkorderId(transaction.getWorkorderId());
                stock.setWorkorderCode(transaction.getWorkorderCode());
            }
            stock.setExpireDate(transaction.getExpireDate());
        }
    }

    @Override
    public Long createTransaction(TransactionCreateReqVO createReqVO) {
        // 插入
        TransactionDO transaction = TransactionConvert.INSTANCE.convert(createReqVO);
        transactionMapper.insert(transaction);
        // 返回
        return transaction.getId();
    }

    @Override
    public void updateTransaction(TransactionUpdateReqVO updateReqVO) {
        // 校验存在
        validateTransactionExists(updateReqVO.getId());
        // 更新
        TransactionDO updateObj = TransactionConvert.INSTANCE.convert(updateReqVO);
        transactionMapper.updateById(updateObj);
    }

    @Override
    public void deleteTransaction(Long id) {
        // 校验存在
        validateTransactionExists(id);
        // 删除
        transactionMapper.deleteById(id);
    }

    private void validateTransactionExists(Long id) {
        if (transactionMapper.selectById(id) == null) {
            throw exception(TRANSACTION_NOT_EXISTS);
        }
    }

    @Override
    public TransactionDO getTransaction(Long id) {
        return transactionMapper.selectById(id);
    }

    @Override
    public List<TransactionDO> getTransactionList(Collection<Long> ids) {
        return transactionMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TransactionDO> getTransactionPage(TransactionPageReqVO pageReqVO) {
        return transactionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TransactionDO> getTransactionList(TransactionExportReqVO exportReqVO) {
        return transactionMapper.selectList(exportReqVO);
    }

    private void validate(TransactionUpdateReqVO transaction){
        if(StrUtils.isNull(transaction.getTransactionType())){
            throw exception(TRANSACTION_NOT_NULL);
        }

        if(StrUtils.isNull(transaction.getTransactionQuantity())){
            throw exception(TRANSACTION_QUANTITY_NOT_NULL);
        }

        /*if(StrUtils.isNull(transaction.getSourceDocCode())){
            throw exception(TRANSACTION_SourceDocCode_NOT_NULL);
        }*/

        /*if(StrUtils.isNull(transaction.getSourceDocLineId())){
            throw exception(TRANSACTION_SourceDocLineId_NOT_NULL);
        }*/

        if(StrUtils.isNull(transaction.getTransactionDate())){
            transaction.setTransactionDate(LocalDateTime.now());
        }
    }
}
