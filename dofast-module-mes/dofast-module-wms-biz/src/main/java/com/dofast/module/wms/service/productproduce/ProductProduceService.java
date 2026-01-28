package com.dofast.module.wms.service.productproduce;

import java.util.*;
import javax.validation.*;

import com.dofast.module.pro.api.FeedbackApi.dto.FeedbackDTO;
import com.dofast.module.wms.controller.admin.productproduce.vo.*;
import com.dofast.module.wms.dal.dataobject.productproduce.ProductProduceDO;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.module.wms.dal.dataobject.productproduce.ProductProductTxBean;

/**
 * 产品产出记录 Service 接口
 *
 * @author 惠智造
 */
public interface ProductProduceService {

    /**
     * 创建产品产出记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductProduce(@Valid ProductProduceCreateReqVO createReqVO);

    /**
     * 更新产品产出记录
     *
     * @param updateReqVO 更新信息
     */
    void updateProductProduce(@Valid ProductProduceUpdateReqVO updateReqVO);

    /**
     * 删除产品产出记录
     *
     * @param id 编号
     */
    void deleteProductProduce(Long id);

    /**
     * 获得产品产出记录
     *
     * @param id 编号
     * @return 产品产出记录
     */
    ProductProduceDO getProductProduce(Long id);

    /**
     * 获得产品产出记录列表
     *
     * @param ids 编号
     * @return 产品产出记录列表
     */
    List<ProductProduceDO> getProductProduceList(Collection<Long> ids);

    /**
     * 获得产品产出记录分页
     *
     * @param pageReqVO 分页查询
     * @return 产品产出记录分页
     */
    PageResult<ProductProduceDO> getProductProducePage(ProductProducePageReqVO pageReqVO);

    /**
     * 获得产品产出记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 产品产出记录列表
     */
    List<ProductProduceDO> getProductProduceList(ProductProduceExportReqVO exportReqVO);

    /**
     *
     * @return
     */
    public ProductProduceDO generateProductProduce(FeedbackDTO feedback, String batchCode);

    public List<ProductProductTxBean> getTxBeans(Long recordId);
}
