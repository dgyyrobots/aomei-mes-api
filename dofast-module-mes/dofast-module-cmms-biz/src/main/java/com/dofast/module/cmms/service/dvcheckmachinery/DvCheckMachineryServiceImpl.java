package com.dofast.module.cmms.service.dvcheckmachinery;

import com.dofast.module.cmms.enums.ErrorCodeConstants;
import com.dofast.module.mes.constant.Constant;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.cmms.controller.admin.dvcheckmachinery.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckmachinery.DvCheckMachineryDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.cmms.convert.dvcheckmachinery.DvCheckMachineryConvert;
import com.dofast.module.cmms.dal.mysql.dvcheckmachinery.DvCheckMachineryMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.cmms.enums.ErrorCodeConstants.*;

/**
 * 点检设备 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DvCheckMachineryServiceImpl implements DvCheckMachineryService {

    @Resource
    private DvCheckMachineryMapper dvCheckMachineryMapper;

    @Override
    public String checkMachineryUnique(DvCheckMachineryBaseVO dvCheckMachinery) {
        DvCheckMachineryDO machinery = dvCheckMachineryMapper.checkMachineryUnique(dvCheckMachinery);
        Long recordId = dvCheckMachinery.getId()==null?-1L:dvCheckMachinery.getId();
        if(null != (machinery) && machinery.getId().longValue() != recordId.longValue()){
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public DvCheckMachineryDO selectDvCheckMachineryByRecordId(Long recordId) {
        return dvCheckMachineryMapper.selectDvCheckMachineryByRecordId(recordId);
    }

    @Override
    public Long createDvCheckMachinery(DvCheckMachineryCreateReqVO createReqVO) {
        // 插入
        DvCheckMachineryDO dvCheckMachinery = DvCheckMachineryConvert.INSTANCE.convert(createReqVO);
        dvCheckMachineryMapper.insert(dvCheckMachinery);
        // 返回
        return dvCheckMachinery.getId();
    }

    @Override
    public void updateDvCheckMachinery(DvCheckMachineryUpdateReqVO updateReqVO) {
        // 校验存在
        validateDvCheckMachineryExists(updateReqVO.getId());
        // 更新
        DvCheckMachineryDO updateObj = DvCheckMachineryConvert.INSTANCE.convert(updateReqVO);
        dvCheckMachineryMapper.updateById(updateObj);
    }

    @Override
    public void deleteDvCheckMachinery(Long id) {
        // 校验存在
        validateDvCheckMachineryExists(id);
        // 删除
        dvCheckMachineryMapper.deleteById(id);
    }

    private void validateDvCheckMachineryExists(Long id) {
        if (dvCheckMachineryMapper.selectById(id) == null) {
            throw exception(DV_CHECK_MACHINERY_NOT_EXISTS);
        }
    }

    @Override
    public DvCheckMachineryDO getDvCheckMachinery(Long id) {
        return dvCheckMachineryMapper.selectById(id);
    }

    @Override
    public DvCheckMachineryDO getDvCheckMachineryByMachineryCode(String machineryCode){
        return dvCheckMachineryMapper.selectOne(DvCheckMachineryDO::getMachineryCode , machineryCode);
    }


    @Override
    public List<DvCheckMachineryDO> getDvCheckMachineryList(Collection<Long> ids) {
        return dvCheckMachineryMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DvCheckMachineryDO> getDvCheckMachineryPage(DvCheckMachineryPageReqVO pageReqVO) {
        return dvCheckMachineryMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DvCheckMachineryDO> getDvCheckMachineryList(DvCheckMachineryExportReqVO exportReqVO) {
        return dvCheckMachineryMapper.selectList(exportReqVO);
    }

}
