package com.dofast.module.wms.service.issueline;

import java.util.*;
import javax.validation.*;
import com.dofast.module.wms.controller.admin.issueline.vo.*;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 生产领料单行 Service 接口
 *
 * @author 芋道源码
 */
public interface IssueLineService {

    List<IssueLineDO> selectList(IssueLineListVO listVO);
    /**
     * 创建生产领料单行
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createIssueLine(@Valid IssueLineCreateReqVO createReqVO);

    /**
     * 更新生产领料单行
     *
     * @param updateReqVO 更新信息
     */
    void updateIssueLine(@Valid IssueLineUpdateReqVO updateReqVO);

    /**
     * 删除生产领料单行
     *
     * @param id 编号
     */
    void deleteIssueLine(Long id);
    public int deleteByIssueHeaderId(Long issueId);

    void batchDeleteIssueLine(Collection<Long> ids);

    /**
     * 获得生产领料单行
     *
     * @param id 编号
     * @return 生产领料单行
     */
    IssueLineDO getIssueLine(Long id);

    /**
     * 获得生产领料单行列表
     *
     * @param ids 编号
     * @return 生产领料单行列表
     */
    List<IssueLineDO> getIssueLineList(Collection<Long> ids);

    /**
     * 获得生产领料单行分页
     *
     * @param pageReqVO 分页查询
     * @return 生产领料单行分页
     */
    PageResult<IssueLineDO> getIssueLinePage(IssueLinePageReqVO pageReqVO);

    /**
     * 获得生产领料单行列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 生产领料单行列表
     */
    List<IssueLineDO> getIssueLineList(IssueLineExportReqVO exportReqVO);

    List<IssueLineDO> getIssueLineByHeaderId(Long headerId);

    void updateIssueLineBatch(List<IssueLineDO> issueLineDOS);

    List<IssueLineDO> getIssueLineListByFeedbackCodes(List<String> feedbackCodes);

}
