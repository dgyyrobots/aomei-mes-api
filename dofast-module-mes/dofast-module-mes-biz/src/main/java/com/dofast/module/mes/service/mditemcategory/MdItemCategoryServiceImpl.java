package com.dofast.module.mes.service.mditemcategory;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.mes.controller.admin.mditemcategory.vo.*;
import com.dofast.module.mes.dal.dataobject.mditemcategory.MdItemCategoryDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.mes.convert.mditemcategory.MdItemCategoryConvert;
import com.dofast.module.mes.dal.mysql.mditemcategory.MdItemCategoryMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.mes.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 物料类别 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class MdItemCategoryServiceImpl implements MdItemCategoryService {

    @Resource
    private MdItemCategoryMapper mdItemCategoryMapper;

    @Override
    public Integer createMdItemCategory(MdItemCategoryCreateReqVO createReqVO) {
        // 插入
        MdItemCategoryDO mdItemCategory = MdItemCategoryConvert.INSTANCE.convert(createReqVO);
        mdItemCategoryMapper.insert(mdItemCategory);
        // 返回
        return mdItemCategory.getId();
    }

    @Override
    public void updateMdItemCategory(MdItemCategoryUpdateReqVO updateReqVO) {
        // 校验存在
        validateMdItemCategoryExists(updateReqVO.getId());
        // 更新
        MdItemCategoryDO updateObj = MdItemCategoryConvert.INSTANCE.convert(updateReqVO);
        mdItemCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteMdItemCategory(Integer id) {
        // 校验存在
        validateMdItemCategoryExists(id);
        // 删除
        mdItemCategoryMapper.deleteById(id);
    }

    private void validateMdItemCategoryExists(Integer id) {
        if (mdItemCategoryMapper.selectById(id) == null) {
            throw exception(MD_ITEM_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public MdItemCategoryDO getMdItemCategory(Integer id) {
        return mdItemCategoryMapper.selectById(id);
    }

    @Override
    public List<MdItemCategoryDO> getMdItemCategoryList(Collection<Integer> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return mdItemCategoryMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MdItemCategoryDO> getMdItemCategoryPage(MdItemCategoryPageReqVO pageReqVO) {
        return mdItemCategoryMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MdItemCategoryDO> getMdItemCategoryList(MdItemCategoryExportReqVO exportReqVO) {
        return mdItemCategoryMapper.selectList(exportReqVO);
    }

}
