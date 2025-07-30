package com.dofast.module.mes.dal.mysql.subclassexception;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.subclassexception.SubclassExceptionDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.subclassexception.vo.*;

/**
 * 子类异常项配置 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface SubclassExceptionMapper extends BaseMapperX<SubclassExceptionDO> {

    default PageResult<SubclassExceptionDO> selectPage(SubclassExceptionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SubclassExceptionDO>()
                .eqIfPresent(SubclassExceptionDO::getExceptionType, reqVO.getExceptionType())
                .eqIfPresent(SubclassExceptionDO::getSubclassExceptionCode, reqVO.getSubclassExceptionCode())
                .likeIfPresent(SubclassExceptionDO::getSubclassExceptionName, reqVO.getSubclassExceptionName())
                .eqIfPresent(SubclassExceptionDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(SubclassExceptionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SubclassExceptionDO::getId));
    }

    default List<SubclassExceptionDO> selectList(SubclassExceptionExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SubclassExceptionDO>()
                .eqIfPresent(SubclassExceptionDO::getExceptionType, reqVO.getExceptionType())
                .eqIfPresent(SubclassExceptionDO::getSubclassExceptionCode, reqVO.getSubclassExceptionCode())
                .likeIfPresent(SubclassExceptionDO::getSubclassExceptionName, reqVO.getSubclassExceptionName())
                .eqIfPresent(SubclassExceptionDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(SubclassExceptionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SubclassExceptionDO::getId));
    }

}
