package com.dofast.module.pro.dal.mysql.process;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.pro.dal.dataobject.process.ProcessDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.pro.controller.admin.process.vo.*;

/**
 * 生产工序 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ProcessMapper extends BaseMapperX<ProcessDO> {

    default ProcessDO checkProcessNameUnique(ProcessBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<ProcessDO>().eq(ProcessDO::getProcessName,baseVO.getProcessName()));
    }
    default ProcessDO checkProcessCodeUnique(ProcessBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<ProcessDO>().eq(ProcessDO::getProcessCode,baseVO.getProcessCode()));
    }
    default PageResult<ProcessDO> selectPage(ProcessPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProcessDO>()
                .likeIfPresent(ProcessDO::getProcessCode, reqVO.getProcessCode())
                .likeIfPresent(ProcessDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(ProcessDO::getAttention, reqVO.getAttention())
                .eqIfPresent(ProcessDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(ProcessDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ProcessDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ProcessDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ProcessDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ProcessDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ProcessDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProcessDO::getId));
    }

    default List<ProcessDO> selectList(ProcessExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProcessDO>()
                .eqIfPresent(ProcessDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(ProcessDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(ProcessDO::getAttention, reqVO.getAttention())
                .eqIfPresent(ProcessDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(ProcessDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ProcessDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ProcessDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ProcessDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ProcessDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ProcessDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProcessDO::getId));
    }

    default List<ProcessDO> selectList(ProcessListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProcessDO>()
                .eqIfPresent(ProcessDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(ProcessDO::getProcessName, reqVO.getProcessName())
                .eqIfPresent(ProcessDO::getAttention, reqVO.getAttention())
                .eqIfPresent(ProcessDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(ProcessDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ProcessDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ProcessDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ProcessDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ProcessDO::getAttr4, reqVO.getAttr4())
                .orderByDesc(ProcessDO::getId));
    }

    /**
     * 查询工序简单列表
     *
     * @return 工序简单列表
     */
    List<ProcessSimpleVO> selectSimpleList();

}
