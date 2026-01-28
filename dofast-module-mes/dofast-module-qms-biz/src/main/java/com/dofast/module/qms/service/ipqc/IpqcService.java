package com.dofast.module.qms.service.ipqc;

import java.util.*;
import javax.validation.*;
import com.dofast.module.qms.controller.admin.ipqc.vo.*;
import com.dofast.module.qms.dal.dataobject.ipqc.IpqcDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 过程检验单 Service 接口
 *
 * @author 芋道源码
 */
public interface IpqcService {
    /**
     * 更新头上的cr、maj、min数量
     * @param qcId
     * @return
     */
    public int updateCrMajMinQuaAndRate(Long qcId);
    /**
     * 检查检测编码是否唯一
     * @param qcIpqc
     * @return
     */
    public String checkIpqcCodeUnique(IpqcBaseVO qcIpqc);
    /**
     * 创建过程检验单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createIpqc(@Valid IpqcCreateReqVO createReqVO);

    /**
     * 更新过程检验单
     *
     * @param updateReqVO 更新信息
     */
    void updateIpqc(@Valid IpqcUpdateReqVO updateReqVO);

    /**
     * 删除过程检验单
     *
     * @param id 编号
     */
    void deleteIpqc(Long id);

    /**
     * 获得过程检验单
     *
     * @param id 编号
     * @return 过程检验单
     */
    IpqcDO getIpqc(Long id);

    /**
     * 获得过程检验单列表
     *
     * @param ids 编号
     * @return 过程检验单列表
     */
    List<IpqcDO> getIpqcList(Collection<Long> ids);

    /**
     * 获得过程检验单分页
     *
     * @param pageReqVO 分页查询
     * @return 过程检验单分页
     */
    PageResult<IpqcDO> getIpqcPage(IpqcPageReqVO pageReqVO);

    /**
     * 获得过程检验单列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 过程检验单列表
     */
    List<IpqcDO> getIpqcList(IpqcExportReqVO exportReqVO);

    List<Map<String, Object>> getProcessQuality();

}
