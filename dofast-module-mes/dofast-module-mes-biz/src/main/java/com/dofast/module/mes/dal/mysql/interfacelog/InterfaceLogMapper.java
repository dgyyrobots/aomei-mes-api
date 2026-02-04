package com.dofast.module.mes.dal.mysql.interfacelog;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.interfacelog.InterfaceLogDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.interfacelog.vo.*;

/**
 * 接口操作日志 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface InterfaceLogMapper extends BaseMapperX<InterfaceLogDO> {

    default PageResult<InterfaceLogDO> selectPage(InterfaceLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InterfaceLogDO>()
                .betweenIfPresent(InterfaceLogDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(InterfaceLogDO::getInterfaceName, reqVO.getInterfaceName())
                .eqIfPresent(InterfaceLogDO::getRequester, reqVO.getRequester())
                .eqIfPresent(InterfaceLogDO::getReceiver, reqVO.getReceiver())
                .eqIfPresent(InterfaceLogDO::getRequestType, reqVO.getRequestType())
                .eqIfPresent(InterfaceLogDO::getRequestMap, reqVO.getRequestMap())
                .eqIfPresent(InterfaceLogDO::getResultMap, reqVO.getResultMap())
                .orderByDesc(InterfaceLogDO::getId));
    }

    default List<InterfaceLogDO> selectList(InterfaceLogExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<InterfaceLogDO>()
                .betweenIfPresent(InterfaceLogDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(InterfaceLogDO::getInterfaceName, reqVO.getInterfaceName())
                .eqIfPresent(InterfaceLogDO::getRequester, reqVO.getRequester())
                .eqIfPresent(InterfaceLogDO::getReceiver, reqVO.getReceiver())
                .eqIfPresent(InterfaceLogDO::getRequestType, reqVO.getRequestType())
                .eqIfPresent(InterfaceLogDO::getRequestMap, reqVO.getRequestMap())
                .eqIfPresent(InterfaceLogDO::getResultMap, reqVO.getResultMap())
                .orderByDesc(InterfaceLogDO::getId));
    }

}
