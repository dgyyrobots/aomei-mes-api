package com.dofast.module.product.dal.mysql.brand;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.module.product.controller.admin.brand.vo.ProductBrandListReqVO;
import com.dofast.module.product.controller.admin.brand.vo.ProductBrandPageReqVO;
import com.dofast.module.product.dal.dataobject.brand.ProductBrandDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductBrandMapper extends BaseMapperX<ProductBrandDO> {

    default PageResult<ProductBrandDO> selectPage(ProductBrandPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductBrandDO>()
                .likeIfPresent(ProductBrandDO::getName, reqVO.getName())
                .eqIfPresent(ProductBrandDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ProductBrandDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProductBrandDO::getId));
    }


    default List<ProductBrandDO> selectList(ProductBrandListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProductBrandDO>()
                .likeIfPresent(ProductBrandDO::getName, reqVO.getName()));
    }

    default ProductBrandDO selectByName(String name) {
        return selectOne(ProductBrandDO::getName, name);
    }

    default List<ProductBrandDO> selectListByStatus(Integer status) {
        return selectList(ProductBrandDO::getStatus, status);
    }
}
