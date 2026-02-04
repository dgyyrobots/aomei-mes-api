package com.dofast.module.pro.dal.mysql.processdefect;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.processdefect.ProcessDefectDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.processdefect.vo.*;

/**
 * 工序异常缺陷名称 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface ProcessDefectMapper extends BaseMapperX<ProcessDefectDO> {

    default PageResult<ProcessDefectDO> selectPage(ProcessDefectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProcessDefectDO>()
                .betweenIfPresent(ProcessDefectDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ProcessDefectDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(ProcessDefectDO::getDefectName, reqVO.getDefectName())
                .orderByDesc(ProcessDefectDO::getId));
    }

    default List<ProcessDefectDO> selectList(ProcessDefectExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProcessDefectDO>()
                .betweenIfPresent(ProcessDefectDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ProcessDefectDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(ProcessDefectDO::getDefectName, reqVO.getDefectName())
                .orderByDesc(ProcessDefectDO::getId));
    }

}
