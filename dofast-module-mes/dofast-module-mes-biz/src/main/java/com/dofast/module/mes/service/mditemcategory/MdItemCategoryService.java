package com.dofast.module.mes.service.mditemcategory;

import java.util.*;
import javax.validation.*;
import com.dofast.module.mes.controller.admin.mditemcategory.vo.*;
import com.dofast.module.mes.dal.dataobject.mditemcategory.MdItemCategoryDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 物料类别 Service 接口
 *
 * @author 惠智造
 */
public interface MdItemCategoryService {

    /**
     * 创建物料类别
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createMdItemCategory(@Valid MdItemCategoryCreateReqVO createReqVO);

    /**
     * 更新物料类别
     *
     * @param updateReqVO 更新信息
     */
    void updateMdItemCategory(@Valid MdItemCategoryUpdateReqVO updateReqVO);

    /**
     * 删除物料类别
     *
     * @param id 编号
     */
    void deleteMdItemCategory(Integer id);

    /**
     * 获得物料类别
     *
     * @param id 编号
     * @return 物料类别
     */
    MdItemCategoryDO getMdItemCategory(Integer id);

    /**
     * 获得物料类别列表
     *
     * @param ids 编号
     * @return 物料类别列表
     */
    List<MdItemCategoryDO> getMdItemCategoryList(Collection<Integer> ids);

    /**
     * 获得物料类别分页
     *
     * @param pageReqVO 分页查询
     * @return 物料类别分页
     */
    PageResult<MdItemCategoryDO> getMdItemCategoryPage(MdItemCategoryPageReqVO pageReqVO);

    /**
     * 获得物料类别列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 物料类别列表
     */
    List<MdItemCategoryDO> getMdItemCategoryList(MdItemCategoryExportReqVO exportReqVO);

}
