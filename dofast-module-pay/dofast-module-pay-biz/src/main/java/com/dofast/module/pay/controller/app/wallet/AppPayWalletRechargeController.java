package com.dofast.module.pay.controller.app.wallet;

import com.dofast.framework.common.pojo.CommonResult;

 
import com.dofast.framework.operatelog.core.annotations.OperateLog;
import com.dofast.module.pay.api.notify.dto.PayOrderNotifyReqDTO;


import com.dofast.framework.operatelog.core.annotations.OperateLog;
import com.dofast.module.pay.api.notify.dto.PayOrderNotifyReqDTO;

import com.dofast.module.pay.controller.app.wallet.vo.recharge.AppPayWalletRechargeCreateReqVO;
import com.dofast.module.pay.controller.app.wallet.vo.recharge.AppPayWalletRechargeCreateRespVO;
import com.dofast.module.pay.convert.wallet.PayWalletRechargeConvert;
import com.dofast.module.pay.dal.dataobject.wallet.PayWalletRechargeDO;
import com.dofast.module.pay.service.wallet.PayWalletRechargeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

 


import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static com.dofast.framework.common.pojo.CommonResult.success;

import static com.dofast.framework.common.util.servlet.ServletUtils.getClientIP;



import static com.dofast.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static com.dofast.framework.web.core.util.WebFrameworkUtils.getLoginUserType;

@Tag(name = "用户 APP - 钱包充值")
@RestController
@RequestMapping("/pay/wallet-recharge")
@Validated
@Slf4j
public class AppPayWalletRechargeController {

    @Resource
    private PayWalletRechargeService walletRechargeService;

    @PostMapping("/create")
    @Operation(summary = "创建钱包充值记录（发起充值）")
    public CommonResult<AppPayWalletRechargeCreateRespVO> createWalletRecharge(




            @Valid @RequestBody AppPayWalletRechargeCreateReqVO reqVO) {
        PayWalletRechargeDO walletRecharge = walletRechargeService.createWalletRecharge(
                getLoginUserId(), getLoginUserType(), reqVO);
        return success(PayWalletRechargeConvert.INSTANCE.convert(walletRecharge));
    }

    @PostMapping("/update-paid")
    @Operation(summary = "更新钱包充值为已充值") // 由 pay-module 支付服务，进行回调，可见 PayNotifyJob
    @PermitAll // 无需登录，安全由 内部校验实现
    @OperateLog(enable = false) // 禁用操作日志，因为没有操作人
    public CommonResult<Boolean> updateWalletRechargerPaid(@Valid @RequestBody PayOrderNotifyReqDTO notifyReqDTO) {
        walletRechargeService.updateWalletRechargerPaid(Long.valueOf(notifyReqDTO.getMerchantOrderId()),
                notifyReqDTO.getPayOrderId());
        return success(true);
    }







}
