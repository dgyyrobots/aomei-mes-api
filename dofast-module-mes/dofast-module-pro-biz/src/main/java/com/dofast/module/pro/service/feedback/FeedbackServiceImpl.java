package com.dofast.module.pro.service.feedback;

import cn.hutool.core.bean.BeanUtil;
import com.dofast.framework.common.pad.util.PadStringUtils;
import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.mes.controller.admin.mdworkstation.vo.MdWorkstationExportReqVO;
import com.dofast.module.mes.dal.dataobject.mdworkstation.MdWorkstationDO;
import com.dofast.module.mes.dal.mysql.mdworkstation.MdWorkstationMapper;
import com.dofast.module.mes.service.mdworkstation.MdWorkstationService;
import com.dofast.module.pro.dal.dataobject.task.TaskDO;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.module.pro.enums.ErrorCodeConstants;
import com.dofast.module.pro.service.task.TaskService;
import com.dofast.module.pro.service.workorder.WorkorderService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;

import com.dofast.module.pro.controller.admin.feedback.vo.*;
import com.dofast.module.pro.dal.dataobject.feedback.FeedbackDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.pro.convert.feedback.FeedbackConvert;
import com.dofast.module.pro.dal.mysql.feedback.FeedbackMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.common.pojo.CommonResult.success;
import static com.dofast.module.pro.enums.ErrorCodeConstants.*;

/**
 * 生产报工记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;

    @Resource
    private MdWorkstationMapper mdWorkstationMapper;

    @Resource
    private MdWorkstationService mdWorkstationService;

    @Resource
    private MdWorkstationService workstationService;

    @Resource
    private WorkorderService workorderService;

    @Resource
    private TaskService taskService;

    @Override
    public Long createFeedback(FeedbackCreateReqVO createReqVO) {
        // 插入
        MdWorkstationDO mdWorkstationDO = mdWorkstationService.getMdWorkstation(createReqVO.getWorkstationId());
        // 获取当前时间, 类型为LocalDateTime
        createReqVO.setFeedbackTime(LocalDateTime.now()); // 设置报工时间
        if (StrUtils.isNotNull(mdWorkstationDO)) {
            createReqVO.setProcessId(mdWorkstationDO.getProcessId());
            createReqVO.setProcessCode(mdWorkstationDO.getProcessCode());
            createReqVO.setProcessName(mdWorkstationDO.getProcessName());
        } else {
            throw exception(MD_WORKSTATION_NOT_EXISTS);
        }
        FeedbackDO feedback = FeedbackConvert.INSTANCE.convert(createReqVO);
        // 获取本次任务单数量， 作为报工的衡量依据
        TaskDO task = taskService.getTask(createReqVO.getTaskId());
        if (task == null) {
            throw exception(FEEDBACK_TASK_NOT_EXISTS);
        }
        feedback.setQuantity(task.getQuantity());
        feedbackMapper.insert(feedback);
        // 返回
        return feedback.getId();
    }

    @Override
    public void updateFeedback(FeedbackUpdateReqVO updateReqVO) {
        // 校验存在
        validateFeedbackExists(updateReqVO.getId());
        // 更新
        FeedbackDO updateObj = FeedbackConvert.INSTANCE.convert(updateReqVO);
        int i = feedbackMapper.updateById(updateObj);
        if (i <= 0) {
            throw exception(UPDATE_PROUDTC_STATUS);
        }
    }


    @Override
    public void deleteFeedback(Long id) {
        // 校验存在
        validateFeedbackExists(id);
        // 删除
        feedbackMapper.deleteById(id);
    }

    @Override
    public void deleteFeedback(Collection<Long> ids) {
        ids.forEach(id -> {
            // 校验存在
            validateFeedbackExists(id);
            // 删除
            feedbackMapper.deleteById(id);
        });
    }

    private void validateFeedbackExists(Long id) {
        if (feedbackMapper.selectById(id) == null) {
            throw exception(FEEDBACK_NOT_EXISTS);
        }
    }

    @Override
    public FeedbackDO getFeedback(Long id) {
        return feedbackMapper.selectById(id);
    }

    @Override
    public List<FeedbackDO> getFeedbackList(Collection<Long> ids) {
        return feedbackMapper.selectBatchIds(ids);
    }


    @Override
    public List<FeedbackDO> getFeedbackListByTaskCods(Collection<String> taskCodes) {
        return feedbackMapper.selectFeedbackListByTaskCods(taskCodes);
    }

    @Override
    public List<FeedbackDO> getFeedbackListByFeedbackCodes(Collection<String> feedbackCodes) {
        return feedbackMapper.selectListByFeedbackCodes(feedbackCodes);
    }


    @Override
    public PageResult<FeedbackDO> getFeedbackPage(FeedbackPageReqVO pageReqVO) {
        return feedbackMapper.selectPage(pageReqVO);
    }

  
    @Override
    public PageResult<FeedbackDO> selectPageContainMerge(FeedbackPageReqVO pageReqVO){
        return feedbackMapper.selectPageContainMerge(pageReqVO);
    }


    @Override
    public List<FeedbackDO> getFeedbackList(FeedbackExportReqVO exportReqVO) {
        return feedbackMapper.selectList(exportReqVO);
    }

    @Override
    public List<FeedbackDO> getFeedbackListNoMerge(FeedbackExportReqVO exportReqVO) {
        return feedbackMapper.selectListNoMerge(exportReqVO);
    }



    @Override
    public List<FeedbackDO> getFeedbackListByTaskId(Long taskId) {
        return feedbackMapper.getListByTaskId(taskId);
    }

    @Override
    public Boolean OneClickCreateFeedback(FeedbackDO proFeedback) {
        TaskDO task = taskService.getTask(proFeedback.getTaskId());
        WorkorderDO workorder = workorderService.getWorkorder(task.getWorkorderId());
        if (workorder == null) {
            throw exception(ErrorCodeConstants.WORKORDER_IS_NOT);
        }
        proFeedback.setTaskId(task.getId());
        proFeedback.setStatus("FINISHED");
        proFeedback.setQuantityQualified(task.getQuantityQuanlify());
        proFeedback.setQuantityUnquanlified(task.getQuantityUnquanlify());
        proFeedback.setFeedbackType("SELF");
        proFeedback.setQuantityFeedback(Double.valueOf(1));
        MdWorkstationDO workstation = workstationService.getMdWorkstation(proFeedback.getWorkstationId());
        if (PadStringUtils.isNotNull(workstation)) {
            proFeedback.setProcessId(workstation.getProcessId());
            proFeedback.setProcessCode(workstation.getProcessCode());
            proFeedback.setProcessName(workstation.getProcessName());
        } else {
            throw exception(MD_WORKSTATION_NOT_EXISTS);
        }

        int insert = feedbackMapper.insert(proFeedback);
        if (insert > 0) {
            return true;
        } else {
            throw exception(ADD_FEEDBACK_FAIL);
        }
    }

    @Override
    public List<Map<String, Object>> getCapacity() {
        return feedbackMapper.getCapacity();
    }

    @Override
    public Map<String, Object> getFeedbackCount(String workorderCode, String taskCode) {
        return feedbackMapper.getFeedbackCount(workorderCode, taskCode);
    }

    @Override
    public Map<String, Object> getIotFeedbackLog(String machineryCode) {
        return feedbackMapper.getIotFeedbackLog(machineryCode);
    }

    @Override
    public List<Map<String, Object>> initFeedbackReport(Map<String, Object> params) {
        return feedbackMapper.initFeedbackReport(params);
    }

    @Override
    public void updateFeedbackBatch(List<FeedbackDO> feedbackDOList){
        feedbackMapper.updateBatch(feedbackDOList);
    }

    @Override
    public  boolean isBatchCodeExists(String batchCode){
        return feedbackMapper.selectList(FeedbackDO::getBatchCode, batchCode).size() > 0;
    }

    @Override
    public List<FeedbackDO> getFeedbackListByTaskCodes(List<String> taskCodes , LocalDateTime startTime, LocalDateTime endTime){
        return feedbackMapper.getFeedbackListByTaskCodes(taskCodes, startTime, endTime);
    }

    @Override
    public List<FeedbackDO> selectErpPendingList(){
        return feedbackMapper.selectErpPendingList();
    }

}
