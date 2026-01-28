package com.dofast.module.wms.service.issueheader;

import java.util.*;
import javax.validation.*;
import com.dofast.module.wms.controller.admin.issueheader.vo.*;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueHeaderDO;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueTxBean;

/**
 * 生产领料单头 Service 接口
 *
 * @author 芋道源码
 */
public interface IssueHeaderService {
    public List<IssueTxBean> getTxBeans(Long issueId);

    String checkIssueCodeUnique(IssueHeaderBaseVO baseVO);
    /**
     * 创建生产领料单头
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createIssueHeader(@Valid IssueHeaderCreateReqVO createReqVO);

    /**
     * 更新生产领料单头
     *
     * @param updateReqVO 更新信息
     */
    void updateIssueHeader(@Valid IssueHeaderUpdateReqVO updateReqVO);

    /**
     * 删除生产领料单头
     *
     * @param id 编号
     */
    void deleteIssueHeader(Long id);

    /**
     * 获得生产领料单头
     *
     * @param id 编号
     * @return 生产领料单头
     */
    IssueHeaderDO getIssueHeader(Long id);

    /**
     * 获得生产领料单头列表
     *
     * @param ids 编号
     * @return 生产领料单头列表
     */
    List<IssueHeaderDO> getIssueHeaderList(Collection<Long> ids);

    /**
     * 获得生产领料单头分页
     *
     * @param pageReqVO 分页查询
     * @return 生产领料单头分页
     */
    PageResult<IssueHeaderDO> getIssueHeaderPage(IssueHeaderPageReqVO pageReqVO);

    /**
     * 获得生产领料单头列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 生产领料单头列表
     */
    List<IssueHeaderDO> getIssueHeaderList(IssueHeaderExportReqVO exportReqVO);

    /**
     * 根据工单号获取BOM信息
     * @param workOrderNo
     * @return
     */
    List<Map<String, Object>> initBomByWorkOrder(String workOrderNo);

    Map<String, IssueHeaderDO> getIssueHeadListByTaskCodes(ArrayList<String> taskCodes);

}
