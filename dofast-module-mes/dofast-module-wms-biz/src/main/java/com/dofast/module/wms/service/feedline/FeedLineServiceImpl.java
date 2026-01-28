package com.dofast.module.wms.service.feedline;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dofast.framework.common.pojo.PageParam;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.wms.controller.admin.feedline.vo.*;
import com.dofast.module.wms.dal.dataobject.feedline.FeedLineDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.wms.convert.feedline.FeedLineConvert;
import com.dofast.module.wms.dal.mysql.feedline.FeedLineMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.wms.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 上料详情 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class FeedLineServiceImpl implements FeedLineService {

    @Resource
    private FeedLineMapper feedLineMapper;

    @Override
    public Long createFeedLine(FeedLineCreateReqVO createReqVO) {
        // 插入
        FeedLineDO feedLine = FeedLineConvert.INSTANCE.convert(createReqVO);
        feedLineMapper.insert(feedLine);
        // 返回
        return feedLine.getId();
    }

    @Override
    public void updateFeedLine(FeedLineUpdateReqVO updateReqVO) {
        // 校验存在
        validateFeedLineExists(updateReqVO.getId());
        // 更新
        FeedLineDO updateObj = FeedLineConvert.INSTANCE.convert(updateReqVO);
        feedLineMapper.updateById(updateObj);
    }

    @Override
    public void deleteFeedLine(Long id) {
        // 校验存在
        validateFeedLineExists(id);
        // 删除
        feedLineMapper.deleteById(id);
    }

    private void validateFeedLineExists(Long id) {
        if (feedLineMapper.selectById(id) == null) {
            throw exception(FEED_LINE_NOT_EXISTS);
        }
    }

    @Override
    public FeedLineDO getFeedLine(Long id) {
        return feedLineMapper.selectById(id);
    }

    @Override
    public List<FeedLineDO> getFeedLineList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return feedLineMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FeedLineDO> getFeedLinePage(FeedLinePageReqVO pageReqVO) {
        return feedLineMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FeedLineDO> getFeedLineList(FeedLineExportReqVO exportReqVO) {
        return feedLineMapper.selectList(exportReqVO);
    }

    @Override
    public  void updateFeedLineBatch(List<FeedLineDO> feedLineDOS){
        feedLineMapper.updateBatch(feedLineDOS);
    }

    @Override
    public  void insertBatch(List<FeedLineDO> feedLineDOS){
        feedLineMapper.insertBatch(feedLineDOS);
    }


    public IPage<FeedLineSummaryVO> selectMaterialUsageSummary(FeedLinePageReqVO reqVO) {
        // 创建分页对象
        Page<FeedLineSummaryVO> page = new Page<>(
                reqVO.getPageNo() == null ? 1 : reqVO.getPageNo(),
                reqVO.getPageSize() == null ? 10 : reqVO.getPageSize()
        );

        // 执行查询
        return feedLineMapper.selectMaterialUsageSummary(page, reqVO);
    }

    public List<FeedLineSummaryVO> selectMaterialUsageSummaryList(FeedLinePageReqVO pageReqVO){
        return feedLineMapper.selectMaterialUsageSummaryList(pageReqVO);
    }

}
