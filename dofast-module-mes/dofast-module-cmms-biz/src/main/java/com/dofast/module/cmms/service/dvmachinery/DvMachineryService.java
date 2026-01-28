package com.dofast.module.cmms.service.dvmachinery;

import java.time.LocalDate;
import java.util.*;
import javax.validation.*;
import com.dofast.module.cmms.controller.admin.dvmachinery.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvmachinery.DvMachineryDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 设备台账 Service 接口
 *
 * @author 芋道源码
 */
public interface DvMachineryService {

    /**
     * 创建设备台账
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDvMachinery(@Valid DvMachineryCreateReqVO createReqVO);

    /**
     * 更新设备台账
     *
     * @param updateReqVO 更新信息
     */
    void updateDvMachinery(@Valid DvMachineryUpdateReqVO updateReqVO);

    /**
     * 删除设备台账
     *
     * @param id 编号
     */
    void deleteDvMachinery(Long id);

    /**
     * 获得设备台账
     *
     * @param id 编号
     * @return 设备台账
     */
    DvMachineryDO getDvMachinery(Long id);

    /**
     * 获得设备台账
     *
     * @param machineryCode 编号
     * @return 设备台账
     */
    DvMachineryDO getDvMachinery(String machineryCode);


    /**
     * 获得设备台账列表
     *
     * @param ids 编号
     * @return 设备台账列表
     */
    List<DvMachineryDO> getDvMachineryList(Collection<Long> ids);

    /**
     * 获得设备台账分页
     *
     * @param pageReqVO 分页查询
     * @return 设备台账分页
     */
    PageResult<DvMachineryDO> getDvMachineryPage(DvMachineryPageReqVO pageReqVO);

    /**
     * 获得设备台账列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 设备台账列表
     */
    List<DvMachineryDO> getDvMachineryList(DvMachineryExportReqVO exportReqVO);

    /**
     * 获取设备状态
     * @return
     */
    Map<String, Object> selectDeviceProductAlertCount();

    /**
     * 查询设备服务下发日志列表
     */
    List<Map<String, Object>> selectFunctionLogList();

    /**
     * 获得设备服务下发上下线列表
     */
    List<Map<String, Object>> selectOnlineLogList(LocalDate startDate, LocalDate endDate);
}
