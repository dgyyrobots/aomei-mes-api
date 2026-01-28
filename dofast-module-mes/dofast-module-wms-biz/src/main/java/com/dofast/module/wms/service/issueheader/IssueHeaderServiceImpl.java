package com.dofast.module.wms.service.issueheader;

import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueTxBean;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.wms.controller.admin.issueheader.vo.*;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueHeaderDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.wms.convert.issueheader.IssueHeaderConvert;
import com.dofast.module.wms.dal.mysql.issueheader.IssueHeaderMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.wms.enums.ErrorCodeConstants.*;

/**
 * 生产领料单头 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class IssueHeaderServiceImpl implements IssueHeaderService {

    @Resource
    private IssueHeaderMapper issueHeaderMapper;

    @Override
    public List<IssueTxBean> getTxBeans(Long issueId) {
        return issueHeaderMapper.getTxBeans(issueId);
    }

    @Override
    public String checkIssueCodeUnique(IssueHeaderBaseVO baseVO) {
        IssueHeaderDO header = issueHeaderMapper.checkIssueCodeUnique(baseVO);
        Long headerId = baseVO.getId()==null?-1l:baseVO.getId();
        if(StrUtils.isNotNull(header) && headerId.longValue() !=header.getId().longValue()){
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    @Override
    public Long createIssueHeader(IssueHeaderCreateReqVO createReqVO) {
        // 插入
        IssueHeaderDO issueHeader = IssueHeaderConvert.INSTANCE.convert(createReqVO);
        issueHeaderMapper.insert(issueHeader);
        // 返回
        return issueHeader.getId();
    }

    @Override
    public void updateIssueHeader(IssueHeaderUpdateReqVO updateReqVO) {
        // 校验存在
        validateIssueHeaderExists(updateReqVO.getId());
        // 更新
        IssueHeaderDO updateObj = IssueHeaderConvert.INSTANCE.convert(updateReqVO);
        issueHeaderMapper.updateById(updateObj);
    }

    @Override
    public void deleteIssueHeader(Long id) {
        // 校验存在
        validateIssueHeaderExists(id);
        // 删除
        issueHeaderMapper.deleteById(id);
    }

    private void validateIssueHeaderExists(Long id) {
        if (issueHeaderMapper.selectById(id) == null) {
            throw exception(ISSUE_HEADER_NOT_EXISTS);
        }
    }

    @Override
    public IssueHeaderDO getIssueHeader(Long id) {
        return issueHeaderMapper.selectById(id);
    }

    @Override
    public List<IssueHeaderDO> getIssueHeaderList(Collection<Long> ids) {
        return issueHeaderMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<IssueHeaderDO> getIssueHeaderPage(IssueHeaderPageReqVO pageReqVO) {
        return issueHeaderMapper.selectPage(pageReqVO);
    }

    @Override
    public List<IssueHeaderDO> getIssueHeaderList(IssueHeaderExportReqVO exportReqVO) {
        return issueHeaderMapper.selectList(exportReqVO);
    }

    @Override
    public List<Map<String, Object>> initBomByWorkOrder(String workOrderNo){
        return issueHeaderMapper.initBomByWorkOrder(workOrderNo);
    }

    @Override
    public  Map<String, IssueHeaderDO> getIssueHeadListByTaskCodes(ArrayList<String> taskCodes){
        Map<String, IssueHeaderDO> issueHeaderMap = new HashMap<>();
        for (String taskCode : taskCodes) {
            IssueHeaderDO issueHeaderDO = issueHeaderMapper.selectOne(IssueHeaderDO::getTaskCode , taskCode);
            issueHeaderMap.put(taskCode,issueHeaderDO);
        }
        return issueHeaderMap;
    }


}
