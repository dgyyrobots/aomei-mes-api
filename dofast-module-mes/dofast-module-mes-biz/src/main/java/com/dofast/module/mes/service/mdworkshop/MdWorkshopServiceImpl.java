package com.dofast.module.mes.service.mdworkshop;

import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.mes.constant.Constant;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.mes.controller.admin.mdworkshop.vo.*;
import com.dofast.module.mes.dal.dataobject.mdworkshop.MdWorkshopDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.mes.convert.mdworkshop.MdWorkshopConvert;
import com.dofast.module.mes.dal.mysql.mdworkshop.MdWorkshopMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.mes.enums.ErrorCodeConstants.*;

/**
 * 车间 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class MdWorkshopServiceImpl implements MdWorkshopService {

    @Resource
    private MdWorkshopMapper mdWorkshopMapper;

    @Override
    public String checkWorkshopCodeUnique(MdWorkshopBaseVO mdWorkshop) {
        MdWorkshopDO workshop = mdWorkshopMapper.checkWorkshopCodeUnique(mdWorkshop);
        Long workshopId = mdWorkshop.getId()==null?-1L:mdWorkshop.getId();
        if(StrUtils.isNotNull(workshop) && workshop.getId().longValue() != workshopId.longValue()){
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public String checkWorkshopNameUnique(MdWorkshopBaseVO mdWorkshop) {
        MdWorkshopDO workshop = mdWorkshopMapper.checkWorkshopNameUnique(mdWorkshop);
        Long workshopId = mdWorkshop.getId()==null?-1L:mdWorkshop.getId();
        if(StrUtils.isNotNull(workshop) && workshop.getId().longValue() != workshopId.longValue()){
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public Long createMdWorkshop(MdWorkshopCreateReqVO createReqVO) {
        // 插入
        MdWorkshopDO mdWorkshop = MdWorkshopConvert.INSTANCE.convert(createReqVO);
        mdWorkshopMapper.insert(mdWorkshop);
        // 返回
        return mdWorkshop.getId();
    }

    @Override
    public void updateMdWorkshop(MdWorkshopUpdateReqVO updateReqVO) {
        // 校验存在
        validateMdWorkshopExists(updateReqVO.getId());
        // 更新
        MdWorkshopDO updateObj = MdWorkshopConvert.INSTANCE.convert(updateReqVO);
        mdWorkshopMapper.updateById(updateObj);
    }

    @Override
    public void deleteMdWorkshop(Long id) {
        // 校验存在
        validateMdWorkshopExists(id);
        // 删除
        mdWorkshopMapper.deleteById(id);
    }

    private void validateMdWorkshopExists(Long id) {
        if (mdWorkshopMapper.selectById(id) == null) {
            throw exception(MD_WORKSHOP_NOT_EXISTS);
        }
    }

    @Override
    public MdWorkshopDO getMdWorkshop(Long id) {
        return mdWorkshopMapper.selectById(id);
    }


    @Override
    public MdWorkshopDO getMdWorkshop(String workshopCode){
        return mdWorkshopMapper.selectOne(MdWorkshopDO::getWorkshopCode, workshopCode);
    }


    @Override
    public List<MdWorkshopDO> getMdWorkshopList(Collection<Long> ids) {
        return mdWorkshopMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MdWorkshopDO> getMdWorkshopPage(MdWorkshopPageReqVO pageReqVO) {
        return mdWorkshopMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MdWorkshopDO> getMdWorkshopList(MdWorkshopExportReqVO exportReqVO) {
        return mdWorkshopMapper.selectList(exportReqVO);
    }

    @Override
    public List<MdWorkShopSimpleVO> getMdWorkshopSimpleList() {
        return mdWorkshopMapper.selectSimpleList();
    }

}
