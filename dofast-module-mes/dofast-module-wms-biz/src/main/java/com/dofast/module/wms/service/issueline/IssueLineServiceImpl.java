package com.dofast.module.wms.service.issueline;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.wms.controller.admin.issueline.vo.*;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.wms.convert.issueline.IssueLineConvert;
import com.dofast.module.wms.dal.mysql.issueline.IssueLineMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.wms.enums.ErrorCodeConstants.*;

/**
 * 生产领料单行 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class IssueLineServiceImpl implements IssueLineService {

    @Resource
    private IssueLineMapper issueLineMapper;

    @Override
    public List<IssueLineDO> selectList(IssueLineListVO listVO) {
        return issueLineMapper.selectList(listVO);
    }

    @Override
    public Long  createIssueLine(IssueLineCreateReqVO createReqVO) {
        // 插入
        IssueLineDO issueLine = IssueLineConvert.INSTANCE.convert(createReqVO);
        issueLineMapper.insert(issueLine);
        // 返回
        return issueLine.getId();
    }

    @Override
    public void updateIssueLine(IssueLineUpdateReqVO updateReqVO) {
        // 校验存在
        validateIssueLineExists(updateReqVO.getId());
        // 更新
        IssueLineDO updateObj = IssueLineConvert.INSTANCE.convert(updateReqVO);
        int count = issueLineMapper.updateById(updateObj);
        IssueLineDO line = issueLineMapper.selectById(updateObj.getId());
    }

    @Override
    public void deleteIssueLine(Long id) {
        // 校验存在
        validateIssueLineExists(id);
        // 删除
        issueLineMapper.deleteById(id);
    }

    @Override
    public int deleteByIssueHeaderId(Long issueId) {
        return issueLineMapper.deleteByIssueHeaderId(issueId);
    }

    @Override
    public void batchDeleteIssueLine(Collection<Long> ids) {
        issueLineMapper.deleteBatchIds(ids);
    }

    private void validateIssueLineExists(Long id) {
        if (issueLineMapper.selectById(id) == null) {
            throw exception(ISSUE_LINE_NOT_EXISTS);
        }
    }

    @Override
    public IssueLineDO getIssueLine(Long id) {
        return issueLineMapper.selectById(id);
    }

    @Override
    public List<IssueLineDO> getIssueLineList(Collection<Long> ids) {
        return issueLineMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<IssueLineDO> getIssueLinePage(IssueLinePageReqVO pageReqVO) {
        return issueLineMapper.selectPage(pageReqVO);
    }

    @Override
    public List<IssueLineDO> getIssueLineList(IssueLineExportReqVO exportReqVO) {
        return issueLineMapper.selectList(exportReqVO);
    }

    @Override
    public List<IssueLineDO> getIssueLineByHeaderId(Long headerId){
        return issueLineMapper.selectList(IssueLineDO::getIssueId, headerId);
    }

    @Override
    public void updateIssueLineBatch(List<IssueLineDO> issueLineDOS){
        issueLineMapper.updateBatch(issueLineDOS);
    }

    @Override
    public  List<IssueLineDO> getIssueLineListByFeedbackCodes(List<String> feedbackCodes){
    return issueLineMapper.getIssueLineListByFeedbackCodes(feedbackCodes);
    }

    @Override
    public  Map<Long, List<IssueLineDO>> getIssueLineListByHeadIds(List<Long> issueHeadIds){
        Map<Long, List<IssueLineDO>> result = new HashMap<>();
        for (Long id : issueHeadIds) {
            List<IssueLineDO> issueLine = issueLineMapper.selectList(IssueLineDO::getIssueId, id);
            result.put(id, issueLine);
        }
        return result;
    }


}
