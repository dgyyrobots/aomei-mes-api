package com.dofast.module.cmms.service.dvmachinery;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.*;
import com.dofast.module.cmms.controller.admin.dvmachinery.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvmachinery.DvMachineryDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.cmms.convert.dvmachinery.DvMachineryConvert;
import com.dofast.module.cmms.dal.mysql.dvmachinery.DvMachineryMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.cmms.enums.ErrorCodeConstants.*;

/**
 * 设备台账 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DvMachineryServiceImpl implements DvMachineryService {

    @Resource
    private DvMachineryMapper dvMachineryMapper;

    @Override
    public Long createDvMachinery(DvMachineryCreateReqVO createReqVO) {
        // 插入
        DvMachineryDO dvMachinery = DvMachineryConvert.INSTANCE.convert(createReqVO);
        dvMachineryMapper.insert(dvMachinery);
        // 返回
        return dvMachinery.getId();
    }

    @Override
    public void updateDvMachinery(DvMachineryUpdateReqVO updateReqVO) {
        // 校验存在
        validateDvMachineryExists(updateReqVO.getId());
        // 更新
        DvMachineryDO updateObj = DvMachineryConvert.INSTANCE.convert(updateReqVO);
        dvMachineryMapper.updateById(updateObj);
    }

    @Override
    public void deleteDvMachinery(Long id) {
        // 校验存在
        validateDvMachineryExists(id);
        // 删除
        dvMachineryMapper.deleteById(id);
    }

    private void validateDvMachineryExists(Long id) {
        if (dvMachineryMapper.selectById(id) == null) {
            throw exception(DV_MACHINERY_NOT_EXISTS);
        }
    }

    @Override
    public DvMachineryDO getDvMachinery(Long id) {
        return dvMachineryMapper.selectById(id);
    }

    @Override
    public  DvMachineryDO getDvMachinery(String machineryCode){
        return dvMachineryMapper.selectOne(DvMachineryDO::getMachineryCode, machineryCode);
    }


    @Override
    public List<DvMachineryDO> getDvMachineryList(Collection<Long> ids) {
        return dvMachineryMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DvMachineryDO> getDvMachineryPage(DvMachineryPageReqVO pageReqVO) {
        return dvMachineryMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DvMachineryDO> getDvMachineryList(DvMachineryExportReqVO exportReqVO) {
        return dvMachineryMapper.selectList(exportReqVO);
    }


    /**
     * 获取设备状态
     * @return
     */
    @Override
    @DS("iot")
    public Map<String, Object> selectDeviceProductAlertCount(){
        return dvMachineryMapper.selectDeviceProductAlertCount();
    }

    /**
     * 查询设备服务下发日志列表
     */
    @Override
    @DS("iot")
    public List<Map<String, Object>> selectFunctionLogList(){
        return dvMachineryMapper.selectFunctionLogList();
    }

    /**
     * 获得设备服务下发上下线列表
     * @return
     */
    @Override
    @DS("iot")
    public List<Map<String, Object>> selectOnlineLogList(LocalDate startDate, LocalDate endDate){
        return dvMachineryMapper.selectOnlineLogList(startDate, endDate);
    }

}
