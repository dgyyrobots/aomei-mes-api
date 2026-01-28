package com.dofast.module.qms.service.iqc;

import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.mes.constant.Constant;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;
import com.dofast.module.qms.controller.admin.iqc.vo.*;
import com.dofast.module.qms.dal.dataobject.iqc.IqcDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.qms.convert.iqc.IqcConvert;
import com.dofast.module.qms.dal.mysql.iqc.IqcMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.qms.enums.ErrorCodeConstants.*;

/**
 * 来料检验单 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class IqcServiceImpl implements IqcService {

    @Resource
    private IqcMapper iqcMapper;

    @Override
    public int updateCrMajMinQuaAndRate(Long iqcId) {
        return iqcMapper.updateCrMajMinQuaAndRate(iqcId);
    }

    @Override
    public String checkIqcCodeUnique(IqcBaseVO qcIqc) {
        IqcDO iqc = iqcMapper.checkIqcCodeUnique(qcIqc);
        Long iqcId = qcIqc.getId()==null?-1L:qcIqc.getId();
        if(StrUtils.isNotNull(iqc) && iqc.getId().longValue() != iqcId.longValue()){
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public Long createIqc(IqcCreateReqVO createReqVO) {
        // 插入
        IqcDO iqc = IqcConvert.INSTANCE.convert(createReqVO);
        iqc.setInspectDate( LocalDateTime.now());
        iqcMapper.insert(iqc);
        // 返回
        return iqc.getId();
    }

    @Override
    public void updateIqc(IqcUpdateReqVO updateReqVO) {
        // 校验存在
        validateIqcExists(updateReqVO.getId());
        // 更新
        IqcDO updateObj = IqcConvert.INSTANCE.convert(updateReqVO);
        iqcMapper.updateById(updateObj);
    }

    @Override
    public void deleteIqc(Long id) {
        // 校验存在
        validateIqcExists(id);
        // 删除
        iqcMapper.deleteById(id);
    }

    private void validateIqcExists(Long id) {
        if (iqcMapper.selectById(id) == null) {
            throw exception(IQC_NOT_EXISTS);
        }
    }

    @Override
    public IqcDO getIqc(Long id) {
        return iqcMapper.selectById(id);
    }

    @Override
    public List<IqcDO> getIqcList(Collection<Long> ids) {
        return iqcMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<IqcDO> getIqcPage(IqcPageReqVO pageReqVO) {
        return iqcMapper.selectPage(pageReqVO);
    }

    @Override
    public List<IqcDO> getIqcList(IqcExportReqVO exportReqVO) {
        return iqcMapper.selectList(exportReqVO);
    }

}
