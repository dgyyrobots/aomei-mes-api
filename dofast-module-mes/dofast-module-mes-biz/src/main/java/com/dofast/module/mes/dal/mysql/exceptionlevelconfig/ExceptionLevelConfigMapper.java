package com.dofast.module.mes.dal.mysql.exceptionlevelconfig;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.exceptionlevelconfig.ExceptionLevelConfigDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.exceptionlevelconfig.vo.*;

/**
 * 异常等级配置 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface ExceptionLevelConfigMapper extends BaseMapperX<ExceptionLevelConfigDO> {

    default PageResult<ExceptionLevelConfigDO> selectPage(ExceptionLevelConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ExceptionLevelConfigDO>()
                .eqIfPresent(ExceptionLevelConfigDO::getLevelCode, reqVO.getLevelCode())
                .likeIfPresent(ExceptionLevelConfigDO::getLevelName, reqVO.getLevelName())
                .eqIfPresent(ExceptionLevelConfigDO::getColor, reqVO.getColor())
                .betweenIfPresent(ExceptionLevelConfigDO::getResponseTime, reqVO.getResponseTime())
                .eqIfPresent(ExceptionLevelConfigDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ExceptionLevelConfigDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ExceptionLevelConfigDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ExceptionLevelConfigDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ExceptionLevelConfigDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ExceptionLevelConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ExceptionLevelConfigDO::getId));
    }

    default List<ExceptionLevelConfigDO> selectList(ExceptionLevelConfigExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ExceptionLevelConfigDO>()
                .eqIfPresent(ExceptionLevelConfigDO::getLevelCode, reqVO.getLevelCode())
                .likeIfPresent(ExceptionLevelConfigDO::getLevelName, reqVO.getLevelName())
                .eqIfPresent(ExceptionLevelConfigDO::getColor, reqVO.getColor())
                .betweenIfPresent(ExceptionLevelConfigDO::getResponseTime, reqVO.getResponseTime())
                .eqIfPresent(ExceptionLevelConfigDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ExceptionLevelConfigDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ExceptionLevelConfigDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ExceptionLevelConfigDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ExceptionLevelConfigDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ExceptionLevelConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ExceptionLevelConfigDO::getId));
    }

}
