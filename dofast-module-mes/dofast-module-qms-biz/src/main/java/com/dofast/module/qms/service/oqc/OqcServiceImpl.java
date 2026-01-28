package com.dofast.module.qms.service.oqc;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.mes.api.autocode.AutoCodeApi;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.pro.api.FeedbackApi.dto.FeedbackDTO;
import com.dofast.module.pro.api.WorkorderApi.dto.WorkorderDTO;
import com.dofast.module.qms.controller.admin.oqcline.vo.OqcLineCreateReqVO;
import com.dofast.module.qms.controller.admin.templateindex.vo.TemplateIndexListVO;
import com.dofast.module.qms.controller.admin.templateproduct.vo.TemplateProductExportReqVO;
import com.dofast.module.qms.dal.dataobject.templateindex.TemplateIndexDO;
import com.dofast.module.qms.dal.dataobject.templateproduct.TemplateProductDO;
import com.dofast.module.qms.service.oqcline.OqcLineService;
import com.dofast.module.qms.service.templateindex.TemplateIndexService;
import com.dofast.module.qms.service.templateproduct.TemplateProductService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import com.dofast.module.qms.controller.admin.oqc.vo.*;
import com.dofast.module.qms.dal.dataobject.oqc.OqcDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.qms.convert.oqc.OqcConvert;
import com.dofast.module.qms.dal.mysql.oqc.OqcMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.qms.enums.ErrorCodeConstants.*;

/**
 * 出货检验单 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OqcServiceImpl implements OqcService {

    @Resource
    private OqcMapper oqcMapper;

    @Resource
    private AutoCodeApi autoCodeApi;

    @Resource
    private TemplateProductService templateProductService;
    @Resource
    private TemplateIndexService templateIndexService;
    @Resource
    private OqcLineService oqcLineService;


    @Override
    public String checkOqcCodeUnique(OqcBaseVO qcIqc) {
        OqcDO oqc = oqcMapper.checkOqcCodeUnique(qcIqc);
        Long oqcId = qcIqc.getId() == null? -1L : qcIqc.getId();
        if(StrUtils.isNotNull(oqc) && oqc.getId().longValue() != oqcId.longValue()){
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public int updateCrMajMinQuaAndRate(Long oqcId) {
        return oqcMapper.updateCrMajMinQuaAndRate(oqcId);
    }

    @Override
    public Long createOqc(OqcCreateReqVO createReqVO) {
        // 插入
        OqcDO oqc = OqcConvert.INSTANCE.convert(createReqVO);
        oqc.setInspectDate( LocalDateTime.now());
        oqcMapper.insert(oqc);
        // 返回
        return oqc.getId();
    }

    @Override
    public void updateOqc(OqcUpdateReqVO updateReqVO) {
        // 校验存在
        validateOqcExists(updateReqVO.getId());
        // 更新
        OqcDO updateObj = OqcConvert.INSTANCE.convert(updateReqVO);
        oqcMapper.updateById(updateObj);
    }

    @Override
    public void deleteOqc(Long id) {
        // 校验存在
        validateOqcExists(id);
        // 删除
        oqcMapper.deleteById(id);
    }

    private void validateOqcExists(Long id) {
        if (oqcMapper.selectById(id) == null) {
            throw exception(OQC_NOT_EXISTS);
        }
    }

    @Override
    public OqcDO getOqc(Long id) {
        return oqcMapper.selectById(id);
    }

    @Override
    public List<OqcDO> getOqcList(Collection<Long> ids) {
        return oqcMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<OqcDO> getOqcPage(OqcPageReqVO pageReqVO) {
        return oqcMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OqcDO> getOqcList(OqcExportReqVO exportReqVO) {
        return oqcMapper.selectList(exportReqVO);
    }

    /**
     * 根据头信息生成行信息
     * @param oqc
     */
    @Override
    public void generateLine(OqcBaseVO oqc){
        TemplateIndexListVO param = new TemplateIndexListVO();
        param.setTemplateId(oqc.getTemplateId());
        List<TemplateIndexDO> indexs = templateIndexService.getTemplateIndexList(param);
        if(CollUtil.isNotEmpty(indexs)){
            for (TemplateIndexDO index:indexs
            ) {
                OqcLineCreateReqVO line = new OqcLineCreateReqVO();
                line.setOqcId(oqc.getId());
                line.setIndexId(index.getIndexId());
                line.setIndexCode(index.getIndexCode());
                line.setIndexName(index.getIndexName());
                line.setIndexType(index.getIndexType());
                line.setQcTool(index.getQcTool());
                line.setCheckMethod(index.getCheckMethod());
                line.setStanderVal(index.getStanderVal());
                line.setUnitOfMeasure(index.getUnitOfMeasure());
                line.setThresholdMax(index.getThresholdMax());
                line.setThresholdMin(index.getThresholdMin());
                line.setCrQuantity(new BigDecimal(0L));
                line.setMajQuantity(new BigDecimal(0L));
                line.setMajQuantity(new BigDecimal(0L));
                oqcLineService.createOqcLine(line);
            }
        }
    }

    /**
     * 根据工单和报工信息生成出货检验单信息
     * @param
     */
    @Override
    public OqcDO generateOqc(FeedbackDTO feedback, WorkorderDTO workorderDTO) {
        OqcDO oqcDO = new OqcDO();
        String oqcCode = autoCodeApi.genSerialCode("OQC_CODE", null);
        oqcDO.setOqcCode(oqcCode);
        oqcDO.setOqcName(workorderDTO.getWorkorderName());
        oqcDO.setClientId(workorderDTO.getClientId());
        oqcDO.setClientCode(workorderDTO.getClientCode());
        oqcDO.setClientName(workorderDTO.getClientName());
        //根据物料查模板
        TemplateProductExportReqVO templateProductExportReqVO = new TemplateProductExportReqVO();
        templateProductExportReqVO.setItemId(feedback.getItemId());
        TemplateProductDO templateProductDO = templateProductService.getTemplateProductList(templateProductExportReqVO).get(0);
        oqcDO.setTemplateId(templateProductDO.getTemplateId());
        oqcDO.setUnitOfMeasure(templateProductDO.getUnitOfMeasure());
        oqcDO.setSpecification(templateProductDO.getSpecification());
        oqcDO.setBatchCode(workorderDTO.getBatchCode());
        oqcDO.setItemId(feedback.getItemId());
        oqcDO.setItemCode(feedback.getItemCode());
        oqcDO.setItemName(feedback.getItemName());
        oqcDO.setRemark(templateProductDO.getRemark());
        oqcDO.setQuantityOut(new BigDecimal(workorderDTO.getQuantity().toString()));
        oqcDO.setQuantityCheck(new BigDecimal(templateProductDO.getQuantityCheck().toString()));
        oqcMapper.insert(oqcDO);
        OqcBaseVO oqcBaseVO = BeanUtil.toBean(oqcDO, OqcBaseVO.class);
        generateLine(oqcBaseVO);
        return oqcDO;
    }
}
