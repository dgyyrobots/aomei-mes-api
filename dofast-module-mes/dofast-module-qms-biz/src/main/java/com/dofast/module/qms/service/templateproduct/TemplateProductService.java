package com.dofast.module.qms.service.templateproduct;

import java.util.*;
import javax.validation.*;
import com.dofast.module.qms.controller.admin.templateproduct.vo.*;
import com.dofast.module.qms.dal.dataobject.templateproduct.TemplateProductDO;
import com.dofast.framework.common.pojo.PageResult;
import org.checkerframework.checker.units.qual.C;

/**
 * 检测模板-产品 Service 接口
 *
 * @author 芋道源码
 */
public interface TemplateProductService {
    /**
     * 根据检测模板ID删除产品
     * @param templateId
     * @return
     */
    public int deleteByTemplateId(Long templateId);
     String checkProductUnique(TemplateProductBaseVO qcTemplateProduct);
    /**
     * 创建检测模板-产品
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTemplateProduct(@Valid TemplateProductCreateReqVO createReqVO);

    /**
     * 更新检测模板-产品
     *
     * @param updateReqVO 更新信息
     */
    void updateTemplateProduct(@Valid TemplateProductUpdateReqVO updateReqVO);

    /**
     * 删除检测模板-产品
     *
     * @param id 编号
     */
    void deleteTemplateProduct(Long id);

    /**
     * 获得检测模板-产品
     *
     * @param id 编号
     * @return 检测模板-产品
     */
    TemplateProductDO getTemplateProduct(Long id);

    /**
     * 获得检测模板-产品列表
     *
     * @param ids 编号
     * @return 检测模板-产品列表
     */
    List<TemplateProductDO> getTemplateProductList(Collection<Long> ids);

    /**
     * 获得检测模板-产品分页
     *
     * @param pageReqVO 分页查询
     * @return 检测模板-产品分页
     */
    PageResult<TemplateProductDO> getTemplateProductPage(TemplateProductPageReqVO pageReqVO);

    /**
     * 获得检测模板-产品列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 检测模板-产品列表
     */
    List<TemplateProductDO> getTemplateProductList(TemplateProductExportReqVO exportReqVO);

    List<TemplateProductDO> getTemplateProductList(TemplateProductListVO listVO);

    List<TemplateProductDO> getTemplateProductListNotContainProcessCode(TemplateProductListVO listVO);


}
