package com.dofast.module.wms.service.feedline;

import java.util.*;
import javax.validation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dofast.framework.common.pojo.PageParam;
import com.dofast.module.wms.controller.admin.feedline.vo.*;
import com.dofast.module.wms.dal.dataobject.feedline.FeedLineDO;
import com.dofast.framework.common.pojo.PageResult;
import org.apache.ibatis.annotations.Param;

/**
 * 上料详情 Service 接口
 *
 * @author 惠智造
 */
public interface FeedLineService {

    /**
     * 创建上料详情
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFeedLine(@Valid FeedLineCreateReqVO createReqVO);

    /**
     * 更新上料详情
     *
     * @param updateReqVO 更新信息
     */
    void updateFeedLine(@Valid FeedLineUpdateReqVO updateReqVO);

    /**
     * 删除上料详情
     *
     * @param id 编号
     */
    void deleteFeedLine(Long id);

    /**
     * 获得上料详情
     *
     * @param id 编号
     * @return 上料详情
     */
    FeedLineDO getFeedLine(Long id);

    /**
     * 获得上料详情列表
     *
     * @param ids 编号
     * @return 上料详情列表
     */
    List<FeedLineDO> getFeedLineList(Collection<Long> ids);

    /**
     * 获得上料详情分页
     *
     * @param pageReqVO 分页查询
     * @return 上料详情分页
     */
    PageResult<FeedLineDO> getFeedLinePage(FeedLinePageReqVO pageReqVO);

    /**
     * 获得上料详情列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 上料详情列表
     */
    List<FeedLineDO> getFeedLineList(FeedLineExportReqVO exportReqVO);

    void updateFeedLineBatch(List<FeedLineDO> feedLineDOS);

    void insertBatch(List<FeedLineDO> feedLineDOS);

    IPage<FeedLineSummaryVO> selectMaterialUsageSummary(FeedLinePageReqVO pageReqVO);

    List<FeedLineSummaryVO> selectMaterialUsageSummaryList(FeedLinePageReqVO pageReqVO);


}
