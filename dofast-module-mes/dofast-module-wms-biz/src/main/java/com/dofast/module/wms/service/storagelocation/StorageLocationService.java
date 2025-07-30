package com.dofast.module.wms.service.storagelocation;

import java.util.*;
import javax.validation.*;
import com.dofast.module.wms.controller.admin.storagelocation.vo.*;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 库区 Service 接口
 *
 * @author 芋道源码
 */
public interface StorageLocationService {
    /**
     * 根据库区编码查询库区
     * @param locationCode
     * @return
     */
    public StorageLocationDO selectWmStorageLocationByLocationCode(String locationCode);

    public StorageLocationDO selectWmStorageLocationByLocationCodeAndProcessCode(String locationCode, String processCode);

    /**
     * 根据仓库删除对应的库区
     * @param warehouseId
     * @return
     */
    public int deleteByWarehouseId(Long warehouseId);
    /**
     * 检查库区编码是否唯一
     * @param wmStorageLocation
     * @return
     */
    public String checkLocationCodeUnique(StorageLocationBaseVO wmStorageLocation);

    /**
     * 检查库区名称是否唯一
     * @param wmStorageLocation
     * @return
     */
    public String checkLocationNameUnique(StorageLocationBaseVO wmStorageLocation);
    /**
     * 创建库区
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createStorageLocation(@Valid StorageLocationCreateReqVO createReqVO);

    /**
     * 更新库区
     *
     * @param updateReqVO 更新信息
     */
    void updateStorageLocation(@Valid StorageLocationUpdateReqVO updateReqVO);

    /**
     * 删除库区
     *
     * @param id 编号
     */
    void deleteStorageLocation(Long id);

    /**
     * 获得库区
     *
     * @param id 编号
     * @return 库区
     */
    StorageLocationDO getStorageLocation(Long id);

    /**
     * 获得库区
     *
     * @param processCode 工序编号
     * @return 库区
     */
    StorageLocationDO getStorageLocation(String processCode);

    /**
     * 获得库区列表
     *
     * @param ids 编号
     * @return 库区列表
     */
    List<StorageLocationDO> getStorageLocationList(Collection<Long> ids);

    /**
     * 获得库区分页
     *
     * @param pageReqVO 分页查询
     * @return 库区分页
     */
    PageResult<StorageLocationDO> getStorageLocationPage(StorageLocationPageReqVO pageReqVO);

    /**
     * 获得库区列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 库区列表
     */
    List<StorageLocationDO> getStorageLocationList(StorageLocationExportReqVO exportReqVO);

}
