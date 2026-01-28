package com.dofast.module.qms.service.templateproduct;

import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.mes.constant.Constant;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.qms.controller.admin.templateproduct.vo.*;
import com.dofast.module.qms.dal.dataobject.templateproduct.TemplateProductDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.qms.convert.templateproduct.TemplateProductConvert;
import com.dofast.module.qms.dal.mysql.templateproduct.TemplateProductMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.qms.enums.ErrorCodeConstants.*;

/**
 * 检测模板-产品 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TemplateProductServiceImpl implements TemplateProductService {

    @Resource
    private TemplateProductMapper templateProductMapper;

    @Override
    public int deleteByTemplateId(Long templateId) {
        return templateProductMapper.deleteByTemplateId(templateId);
    }

    @Override
    public String checkProductUnique(TemplateProductBaseVO qcTemplateProduct) {
        TemplateProductDO product = templateProductMapper.checkProductUnique(qcTemplateProduct);
        Long recordId = qcTemplateProduct.getId() ==null? -1L:qcTemplateProduct.getId();
        if(StrUtils.isNotNull(product) && product.getId().longValue()!=recordId.longValue()){
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public Long createTemplateProduct(TemplateProductCreateReqVO createReqVO) {
        // 插入
        TemplateProductDO templateProduct = TemplateProductConvert.INSTANCE.convert(createReqVO);
        templateProductMapper.insert(templateProduct);
        // 返回
        return templateProduct.getId();
    }

    @Override
    public void updateTemplateProduct(TemplateProductUpdateReqVO updateReqVO) {
        // 校验存在
        validateTemplateProductExists(updateReqVO.getId());
        // 更新
        TemplateProductDO updateObj = TemplateProductConvert.INSTANCE.convert(updateReqVO);
        templateProductMapper.updateById(updateObj);
    }

    @Override
    public void deleteTemplateProduct(Long id) {
        // 校验存在
        validateTemplateProductExists(id);
        // 删除
        templateProductMapper.deleteById(id);
    }

    private void validateTemplateProductExists(Long id) {
        if (templateProductMapper.selectById(id) == null) {
            throw exception(TEMPLATE_PRODUCT_NOT_EXISTS);
        }
    }

    @Override
    public TemplateProductDO getTemplateProduct(Long id) {
        return templateProductMapper.selectById(id);
    }

    @Override
    public List<TemplateProductDO> getTemplateProductList(Collection<Long> ids) {
        return templateProductMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TemplateProductDO> getTemplateProductPage(TemplateProductPageReqVO pageReqVO) {
        return templateProductMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TemplateProductDO> getTemplateProductList(TemplateProductExportReqVO exportReqVO) {
        return templateProductMapper.selectList(exportReqVO);
    }

    @Override
    public List<TemplateProductDO> getTemplateProductList(TemplateProductListVO listVO) {
        return templateProductMapper.selectList(listVO);
    }

    @Override
    public  List<TemplateProductDO> getTemplateProductListNotContainProcessCode(TemplateProductListVO listVO){
        return templateProductMapper.selectListNotContainProcessCode(listVO);
    }


}
