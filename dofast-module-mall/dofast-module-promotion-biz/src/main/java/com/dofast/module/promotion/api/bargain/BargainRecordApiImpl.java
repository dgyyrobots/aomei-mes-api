package com.dofast.module.promotion.api.bargain;

import com.dofast.module.promotion.api.bargain.dto.BargainValidateJoinRespDTO;
import com.dofast.module.promotion.service.bargain.BargainRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 砍价活动 API 实现类
 *
 * @author HUIHUI
 */
@Service
public class BargainRecordApiImpl implements BargainRecordApi {

    @Resource
    private BargainRecordService bargainRecordService;

    @Override
    public BargainValidateJoinRespDTO validateJoinBargain(Long userId, Long bargainRecordId, Long skuId) {
        return bargainRecordService.validateJoinBargain(userId, bargainRecordId, skuId);
    }

    @Override
    public void updateBargainRecordOrderId(Long id, Long orderId) {
        bargainRecordService.updateBargainRecordOrderId(id, orderId);
    }

}
