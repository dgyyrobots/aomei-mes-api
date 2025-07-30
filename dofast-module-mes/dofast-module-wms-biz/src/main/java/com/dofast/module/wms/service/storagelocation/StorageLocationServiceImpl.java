package com.dofast.module.wms.service.storagelocation;

import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.mes.constant.Constant;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.wms.controller.admin.storagelocation.vo.*;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.wms.convert.storagelocation.StorageLocationConvert;
import com.dofast.module.wms.dal.mysql.storagelocation.StorageLocationMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.wms.enums.ErrorCodeConstants.*;

/**
 * 库区 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class StorageLocationServiceImpl implements StorageLocationService {

    @Resource
    private StorageLocationMapper storageLocationMapper;

    @Override
    public StorageLocationDO selectWmStorageLocationByLocationCode(String locationCode) {
        return storageLocationMapper.selectWmStorageLocationByLocationCode(locationCode);
    }

    @Override
    public StorageLocationDO selectWmStorageLocationByLocationCodeAndProcessCode(String locationCode, String processCode){
        return storageLocationMapper.selectWmStorageLocationByLocationCodeAndProcessCode(locationCode, processCode);
    }



    @Override
    public int deleteByWarehouseId(Long warehouseId) {
        return storageLocationMapper.deleteByWarehouseId(warehouseId);
    }

    @Override
    public String checkLocationCodeUnique(StorageLocationBaseVO wmStorageLocation) {
        StorageLocationDO location = storageLocationMapper.checkLocationCodeUnique(wmStorageLocation);
        Long locationId = wmStorageLocation.getId()==null?-1L:wmStorageLocation.getId();
        if(!StrUtils.isNull(location) && location.getId().longValue() != locationId.longValue()){
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public String checkLocationNameUnique(StorageLocationBaseVO wmStorageLocation) {
        StorageLocationDO location = storageLocationMapper.checkLocationCodeUnique(wmStorageLocation);
        Long locationId = wmStorageLocation.getId()==null?-1L:wmStorageLocation.getId();
        if(!StrUtils.isNull(location) && location.getId().longValue() != locationId.longValue()){
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public Long createStorageLocation(StorageLocationCreateReqVO createReqVO) {
        // 插入
        StorageLocationDO storageLocation = StorageLocationConvert.INSTANCE.convert(createReqVO);
        storageLocationMapper.insert(storageLocation);
        // 返回
        return storageLocation.getId();
    }

    @Override
    public void updateStorageLocation(StorageLocationUpdateReqVO updateReqVO) {
        // 校验存在
        validateStorageLocationExists(updateReqVO.getId());
        // 更新
        StorageLocationDO updateObj = StorageLocationConvert.INSTANCE.convert(updateReqVO);
        storageLocationMapper.updateById(updateObj);
    }

    @Override
    public void deleteStorageLocation(Long id) {
        // 校验存在
        validateStorageLocationExists(id);
        // 删除
        storageLocationMapper.deleteById(id);
    }

    private void validateStorageLocationExists(Long id) {
        if (storageLocationMapper.selectById(id) == null) {
            throw exception(STORAGE_LOCATION_NOT_EXISTS);
        }
    }

    @Override
    public StorageLocationDO getStorageLocation(Long id) {
        return storageLocationMapper.selectById(id);
    }

    @Override
    public  StorageLocationDO getStorageLocation(String processCode){
        return storageLocationMapper.selectOne(StorageLocationDO::getProcessCode,processCode);
    }


    @Override
    public List<StorageLocationDO> getStorageLocationList(Collection<Long> ids) {
        return storageLocationMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<StorageLocationDO> getStorageLocationPage(StorageLocationPageReqVO pageReqVO) {
        return storageLocationMapper.selectPage(pageReqVO);
    }

    @Override
    public List<StorageLocationDO> getStorageLocationList(StorageLocationExportReqVO exportReqVO) {
        return storageLocationMapper.selectList(exportReqVO);
    }

}
