package com.dofast.module.mes.service.mdworkshop;

import java.util.*;
import javax.validation.*;
import com.dofast.module.mes.controller.admin.mdworkshop.vo.*;
import com.dofast.module.mes.dal.dataobject.mdworkshop.MdWorkshopDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 车间 Service 接口
 *
 * @author 芋道源码
 */
public interface MdWorkshopService {
    public String checkWorkshopCodeUnique(MdWorkshopBaseVO mdWorkshop);

    public String checkWorkshopNameUnique(MdWorkshopBaseVO mdWorkshop);
    /**
     * 创建车间
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMdWorkshop(@Valid MdWorkshopCreateReqVO createReqVO);

    /**
     * 更新车间
     *
     * @param updateReqVO 更新信息
     */
    void updateMdWorkshop(@Valid MdWorkshopUpdateReqVO updateReqVO);

    /**
     * 删除车间
     *
     * @param id 编号
     */
    void deleteMdWorkshop(Long id);

    /**
     * 获得车间
     *
     * @param id 编号
     * @return 车间
     */
    MdWorkshopDO getMdWorkshop(Long id);

    /**
     * 获得车间
     *
     * @param workshopCode 车间编号
     * @return 车间
     */
    MdWorkshopDO getMdWorkshop(String workshopCode);


    /**
     * 获得车间列表
     *
     * @param ids 编号
     * @return 车间列表
     */
    List<MdWorkshopDO> getMdWorkshopList(Collection<Long> ids);

    /**
     * 获得车间分页
     *
     * @param pageReqVO 分页查询
     * @return 车间分页
     */
    PageResult<MdWorkshopDO> getMdWorkshopPage(MdWorkshopPageReqVO pageReqVO);

    /**
     * 获得车间列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 车间列表
     */
    List<MdWorkshopDO> getMdWorkshopList(MdWorkshopExportReqVO exportReqVO);

    /**
     * 获得车间列表
     *
     * @return 车间列表
     */
    List<MdWorkShopSimpleVO> getMdWorkshopSimpleList();
}
