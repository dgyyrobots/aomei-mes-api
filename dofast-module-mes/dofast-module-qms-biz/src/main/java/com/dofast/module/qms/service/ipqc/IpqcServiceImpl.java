package com.dofast.module.qms.service.ipqc;

import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.mes.constant.Constant;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;
import com.dofast.module.qms.controller.admin.ipqc.vo.*;
import com.dofast.module.qms.dal.dataobject.ipqc.IpqcDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.qms.convert.ipqc.IpqcConvert;
import com.dofast.module.qms.dal.mysql.ipqc.IpqcMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.qms.enums.ErrorCodeConstants.*;

/**
 * 过程检验单 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class IpqcServiceImpl implements IpqcService {

    @Resource
    private IpqcMapper ipqcMapper;

    @Override
    public int updateCrMajMinQuaAndRate(Long qcId) {
        return ipqcMapper.updateCrMajMinQuaAndRate(qcId);
    }

    @Override
    public String checkIpqcCodeUnique(IpqcBaseVO qcIpqc) {
        IpqcDO ipqc = ipqcMapper.checkIpqcCodeUnique(qcIpqc);
        Long ipqcId = qcIpqc.getId()==null?-1:qcIpqc.getId();
        if(StrUtils.isNotNull(ipqc) && ipqc.getId().longValue() != ipqcId.longValue()){
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public Long createIpqc(IpqcCreateReqVO createReqVO) {
        // 插入
        IpqcDO ipqc = IpqcConvert.INSTANCE.convert(createReqVO);
        ipqc.setInspectDate( LocalDateTime.now());
        ipqcMapper.insert(ipqc);
        // 返回
        return ipqc.getId();
    }

    @Override
    public void updateIpqc(IpqcUpdateReqVO updateReqVO) {
        // 校验存在
        validateIpqcExists(updateReqVO.getId());
        // 更新
        IpqcDO updateObj = IpqcConvert.INSTANCE.convert(updateReqVO);
        ipqcMapper.updateById(updateObj);
    }

    @Override
    public void deleteIpqc(Long id) {
        // 校验存在
        validateIpqcExists(id);
        // 删除
        ipqcMapper.deleteById(id);
    }

    private void validateIpqcExists(Long id) {
        if (ipqcMapper.selectById(id) == null) {
            throw exception(IPQC_NOT_EXISTS);
        }
    }

    @Override
    public IpqcDO getIpqc(Long id) {
        return ipqcMapper.selectById(id);
    }

    @Override
    public List<IpqcDO> getIpqcList(Collection<Long> ids) {
        return ipqcMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<IpqcDO> getIpqcPage(IpqcPageReqVO pageReqVO) {
        return ipqcMapper.selectPage(pageReqVO);
    }

    @Override
    public List<IpqcDO> getIpqcList(IpqcExportReqVO exportReqVO) {
        return ipqcMapper.selectList(exportReqVO);
    }

    @Override
    public List<Map<String, Object>> getProcessQuality(){
        return ipqcMapper.getProcessQuality();
    }


}
