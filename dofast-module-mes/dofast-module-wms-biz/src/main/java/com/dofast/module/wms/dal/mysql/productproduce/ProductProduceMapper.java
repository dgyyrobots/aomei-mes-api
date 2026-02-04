package com.dofast.module.wms.dal.mysql.productproduce;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.productproduce.ProductProduceDO;
import com.dofast.module.wms.dal.dataobject.productproduce.ProductProductTxBean;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.productproduce.vo.*;

/**
 * 产品产出记录 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface ProductProduceMapper extends BaseMapperX<ProductProduceDO> {

    default PageResult<ProductProduceDO> selectPage(ProductProducePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductProduceDO>()
                .eqIfPresent(ProductProduceDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(ProductProduceDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(ProductProduceDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(ProductProduceDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(ProductProduceDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(ProductProduceDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(ProductProduceDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(ProductProduceDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(ProductProduceDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(ProductProduceDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(ProductProduceDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(ProductProduceDO::getProcessName, reqVO.getProcessName())
                .betweenIfPresent(ProductProduceDO::getProduceDate, reqVO.getProduceDate())
                .eqIfPresent(ProductProduceDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ProductProduceDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ProductProduceDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ProductProduceDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ProductProduceDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ProductProduceDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ProductProduceDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ProductProduceDO::getOrderSource, reqVO.getOrderSource())
                .orderByDesc(ProductProduceDO::getId));
    }

    default List<ProductProduceDO> selectList(ProductProduceExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProductProduceDO>()
                .eqIfPresent(ProductProduceDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(ProductProduceDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(ProductProduceDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(ProductProduceDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(ProductProduceDO::getTaskCode, reqVO.getTaskCode())
                .eqIfPresent(ProductProduceDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(ProductProduceDO::getWorkstationId, reqVO.getWorkstationId())
                .eqIfPresent(ProductProduceDO::getWorkstationCode, reqVO.getWorkstationCode())
                .eqIfPresent(ProductProduceDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(ProductProduceDO::getProcessId, reqVO.getProcessId())
                .eqIfPresent(ProductProduceDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(ProductProduceDO::getProcessName, reqVO.getProcessName())
                .betweenIfPresent(ProductProduceDO::getProduceDate, reqVO.getProduceDate())
                .eqIfPresent(ProductProduceDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ProductProduceDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ProductProduceDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ProductProduceDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ProductProduceDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ProductProduceDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ProductProduceDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ProductProduceDO::getOrderSource, reqVO.getOrderSource())
                .orderByDesc(ProductProduceDO::getId));
    }

    public List<ProductProductTxBean> getTxBeans(Long recordId);
}
