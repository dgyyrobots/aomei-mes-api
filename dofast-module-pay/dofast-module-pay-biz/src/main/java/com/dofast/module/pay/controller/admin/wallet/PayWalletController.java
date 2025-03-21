package com.dofast.module.pay.controller.admin.wallet;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.dofast.framework.common.pojo.CommonResult;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.util.collection.CollectionUtils;
import com.dofast.module.member.api.user.MemberUserApi;
import com.dofast.module.member.api.user.dto.MemberUserRespDTO;
import com.dofast.module.pay.controller.admin.wallet.vo.wallet.PayWalletPageReqVO;
import com.dofast.module.pay.controller.admin.wallet.vo.wallet.PayWalletRespVO;
import com.dofast.module.pay.controller.admin.wallet.vo.wallet.PayWalletUserReqVO;
import com.dofast.module.pay.convert.wallet.PayWalletConvert;
import com.dofast.module.pay.dal.dataobject.wallet.PayWalletDO;
import com.dofast.module.pay.service.wallet.PayWalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.dofast.framework.common.enums.UserTypeEnum.MEMBER;
import static com.dofast.framework.common.pojo.CommonResult.success;
import static com.dofast.framework.common.util.collection.CollectionUtils.convertList;
import static com.dofast.framework.common.util.collection.CollectionUtils.convertMap;

@Tag(name = "管理后台 - 用户钱包")
@RestController
@RequestMapping("/pay/wallet")
@Validated
@Slf4j
public class PayWalletController {

    @Resource
    private PayWalletService payWalletService;
    @Resource
    private MemberUserApi memberUserApi;

    @GetMapping("/get")
    @PreAuthorize("@ss.hasPermission('pay:wallet:query')")
    @Operation(summary = "获得用户钱包明细")
    public CommonResult<PayWalletRespVO> getWallet(PayWalletUserReqVO reqVO) {
        PayWalletDO wallet = payWalletService.getOrCreateWallet(reqVO.getUserId(), MEMBER.getValue());
        MemberUserRespDTO memberUser = memberUserApi.getUser(reqVO.getUserId());
        String nickname = memberUser == null ? "" : memberUser.getNickname();
        String avatar = memberUser == null ? "" : memberUser.getAvatar();
        return success(PayWalletConvert.INSTANCE.convert02(nickname, avatar, wallet));
    }

    @GetMapping("/page")
    @Operation(summary = "获得会员钱包分页")
    @PreAuthorize("@ss.hasPermission('pay:wallet:query')")
    public CommonResult<PageResult<PayWalletRespVO>> getWalletPage(@Valid PayWalletPageReqVO pageVO) {
        if (StrUtil.isNotEmpty(pageVO.getNickname())) {
            Set<Long> userIds = CollectionUtils.convertSet(memberUserApi.getUserListByNickname(pageVO.getNickname()),
                    MemberUserRespDTO::getId);
            pageVO.setUserIds(userIds);
        }
        // TODO @jason：管理员也可以先查询下。。
        // 暂时支持查询 userType 会员类型。管理员类型还不知道使用场景
        PageResult<PayWalletDO> pageResult = payWalletService.getWalletPage(MEMBER.getValue(),pageVO);
        if (CollectionUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }
        List<Long> userIds = convertList(pageResult.getList(), PayWalletDO::getUserId);
        Map<Long, MemberUserRespDTO> userMap = convertMap(memberUserApi.getUserList(userIds),MemberUserRespDTO::getId);
        return success(PayWalletConvert.INSTANCE.convertPage(pageResult, userMap));
    }

}
