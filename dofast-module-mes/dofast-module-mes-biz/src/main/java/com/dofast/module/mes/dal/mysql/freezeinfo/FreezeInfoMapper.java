package com.dofast.module.mes.dal.mysql.freezeinfo;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.freezeinfo.FreezeInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.freezeinfo.vo.*;

/**
 * 产品冻结信息 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface FreezeInfoMapper extends BaseMapperX<FreezeInfoDO> {

    default PageResult<FreezeInfoDO> selectPage(FreezeInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FreezeInfoDO>()
                .betweenIfPresent(FreezeInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FreezeInfoDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(FreezeInfoDO::getItemName, reqVO.getItemName())
                .eqIfPresent(FreezeInfoDO::getItenQty, reqVO.getItenQty())
                .eqIfPresent(FreezeInfoDO::getState, reqVO.getState())
                .eqIfPresent(FreezeInfoDO::getWhCode, reqVO.getWhCode())
                .eqIfPresent(FreezeInfoDO::getStorageCode, reqVO.getStorageCode())
                .eqIfPresent(FreezeInfoDO::getFreezeMemo, reqVO.getFreezeMemo())
                .eqIfPresent(FreezeInfoDO::getFreezer, reqVO.getFreezer())
                .betweenIfPresent(FreezeInfoDO::getFreezeTime, reqVO.getFreezeTime())
                .eqIfPresent(FreezeInfoDO::getThawPerson, reqVO.getThawPerson())
                .eqIfPresent(FreezeInfoDO::getThawMemo, reqVO.getThawMemo())
                .betweenIfPresent(FreezeInfoDO::getThawTime, reqVO.getThawTime())
                .eqIfPresent(FreezeInfoDO::getItenSn, reqVO.getItenSn())
                .orderByDesc(FreezeInfoDO::getId));
    }

    default List<FreezeInfoDO> selectList(FreezeInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FreezeInfoDO>()
                .betweenIfPresent(FreezeInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FreezeInfoDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(FreezeInfoDO::getItemName, reqVO.getItemName())
                .eqIfPresent(FreezeInfoDO::getItenQty, reqVO.getItenQty())
                .eqIfPresent(FreezeInfoDO::getState, reqVO.getState())
                .eqIfPresent(FreezeInfoDO::getWhCode, reqVO.getWhCode())
                .eqIfPresent(FreezeInfoDO::getStorageCode, reqVO.getStorageCode())
                .eqIfPresent(FreezeInfoDO::getFreezeMemo, reqVO.getFreezeMemo())
                .eqIfPresent(FreezeInfoDO::getFreezer, reqVO.getFreezer())
                .betweenIfPresent(FreezeInfoDO::getFreezeTime, reqVO.getFreezeTime())
                .eqIfPresent(FreezeInfoDO::getThawPerson, reqVO.getThawPerson())
                .eqIfPresent(FreezeInfoDO::getThawMemo, reqVO.getThawMemo())
                .betweenIfPresent(FreezeInfoDO::getThawTime, reqVO.getThawTime())
                .eqIfPresent(FreezeInfoDO::getItenSn, reqVO.getItenSn())
                .orderByDesc(FreezeInfoDO::getId));
    }

}
