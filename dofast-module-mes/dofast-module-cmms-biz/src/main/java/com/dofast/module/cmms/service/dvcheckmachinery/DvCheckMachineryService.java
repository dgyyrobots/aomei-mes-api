package com.dofast.module.cmms.service.dvcheckmachinery;

import java.util.*;
import javax.validation.*;
import com.dofast.module.cmms.controller.admin.dvcheckmachinery.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckmachinery.DvCheckMachineryDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 点检设备 Service 接口
 *
 * @author 芋道源码
 */
public interface DvCheckMachineryService {
    public String  checkMachineryUnique(DvCheckMachineryBaseVO dvCheckMachinery);
    /**
     * 查询点检设备
     *
     * @param recordId 点检设备主键
     * @return 点检设备
     */
    public DvCheckMachineryDO selectDvCheckMachineryByRecordId(Long recordId);
    /**
     * 创建点检设备
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDvCheckMachinery(@Valid DvCheckMachineryCreateReqVO createReqVO);

    /**
     * 更新点检设备
     *
     * @param updateReqVO 更新信息
     */
    void updateDvCheckMachinery(@Valid DvCheckMachineryUpdateReqVO updateReqVO);

    /**
     * 删除点检设备
     *
     * @param id 编号
     */
    void deleteDvCheckMachinery(Long id);

    /**
     * 获得点检设备
     *
     * @param id 编号
     * @return 点检设备
     */
    DvCheckMachineryDO getDvCheckMachinery(Long id);

    DvCheckMachineryDO getDvCheckMachineryByMachineryCode(String machineryCode);

    /**
     * 获得点检设备列表
     *
     * @param ids 编号
     * @return 点检设备列表
     */
    List<DvCheckMachineryDO> getDvCheckMachineryList(Collection<Long> ids);

    /**
     * 获得点检设备分页
     *
     * @param pageReqVO 分页查询
     * @return 点检设备分页
     */
    PageResult<DvCheckMachineryDO> getDvCheckMachineryPage(DvCheckMachineryPageReqVO pageReqVO);

    /**
     * 获得点检设备列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 点检设备列表
     */
    List<DvCheckMachineryDO> getDvCheckMachineryList(DvCheckMachineryExportReqVO exportReqVO);

}
