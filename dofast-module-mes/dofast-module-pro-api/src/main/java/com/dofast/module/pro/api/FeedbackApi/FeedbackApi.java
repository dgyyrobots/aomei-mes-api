package com.dofast.module.pro.api.FeedbackApi;

import com.dofast.module.pro.api.FeedbackApi.dto.FeedbackDTO;

import java.util.List;

public interface FeedbackApi {

    FeedbackDTO getFeedBack(Long id);

    FeedbackDTO getFeedBackByTaskCode(String taskCode);

    FeedbackDTO getFeedBackByBatchCode(String feedback);


}
