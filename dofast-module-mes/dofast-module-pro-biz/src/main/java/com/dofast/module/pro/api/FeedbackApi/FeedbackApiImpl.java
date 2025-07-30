package com.dofast.module.pro.api.FeedbackApi;

import cn.hutool.core.bean.BeanUtil;
import com.dofast.module.pro.api.FeedbackApi.dto.FeedbackDTO;
import com.dofast.module.pro.api.RouteApi.RouteDTO;
import com.dofast.module.pro.controller.admin.feedback.vo.FeedbackExportReqVO;
import com.dofast.module.pro.dal.dataobject.feedback.FeedbackDO;
import com.dofast.module.pro.service.feedback.FeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FeedbackApiImpl implements FeedbackApi{

    @Resource
    private FeedbackService feedbackService;

    @Override
    public FeedbackDTO getFeedBack(Long id) {
        FeedbackDO feedbackDO = feedbackService.getFeedback(id);
        FeedbackDTO feedbackDTO = BeanUtil.toBean(feedbackDO, FeedbackDTO.class);
        return feedbackDTO;
    }

    @Override
    public FeedbackDTO getFeedBackByTaskCode(String taskCode){
        FeedbackExportReqVO exportReqVO = new FeedbackExportReqVO();
        exportReqVO.setTaskCode(taskCode);
        List<FeedbackDO> feedbackDO = feedbackService.getFeedbackList(exportReqVO);
        if(feedbackDO.isEmpty()){
            return null;
        }
        FeedbackDTO feedbackDTO = BeanUtil.toBean(feedbackDO.get(0), FeedbackDTO.class);
        return feedbackDTO;
    }


    @Override
    public FeedbackDTO getFeedBackByBatchCode(String batchCode){
        FeedbackExportReqVO exportReqVO = new FeedbackExportReqVO();
        exportReqVO.setBatchCode(batchCode);
        List<FeedbackDO> feedbackDO = feedbackService.getFeedbackList(exportReqVO);
        if(feedbackDO.isEmpty()){
            return null;
        }
        FeedbackDTO feedbackDTO = BeanUtil.toBean(feedbackDO.get(0), FeedbackDTO.class);
        return feedbackDTO;
    }



}
