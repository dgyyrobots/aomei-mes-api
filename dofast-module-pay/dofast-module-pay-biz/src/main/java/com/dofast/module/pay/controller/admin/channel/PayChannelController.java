package com.dofast.module.pay.controller.admin.channel;

import com.dofast.framework.common.pojo.CommonResult;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.excel.core.util.ExcelUtils;
import com.dofast.framework.operatelog.core.annotations.OperateLog;
import com.dofast.module.pay.controller.admin.channel.vo.PayChannelCreateReqVO;
import com.dofast.module.pay.controller.admin.channel.vo.PayChannelRespVO;
import com.dofast.module.pay.controller.admin.channel.vo.PayChannelUpdateReqVO;
import com.dofast.module.pay.controller.admin.merchant.vo.channel.PayChannelExcelVO;
import com.dofast.module.pay.controller.admin.merchant.vo.channel.PayChannelExportReqVO;
import com.dofast.module.pay.controller.admin.merchant.vo.channel.PayChannelPageReqVO;
import com.dofast.module.pay.convert.channel.PayChannelConvert;
import com.dofast.module.pay.dal.dataobject.channel.PayChannelDO;
import com.dofast.module.pay.service.channel.PayChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.dofast.framework.common.pojo.CommonResult.success;
import static com.dofast.framework.common.util.collection.CollectionUtils.convertSet;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 支付渠道")
@RestController
@RequestMapping("/pay/channel")
@Validated
public class PayChannelController {

    @Resource
    private PayChannelService channelService;

    @PostMapping("/create")
    @Operation(summary = "创建支付渠道 ")
    @PreAuthorize("@ss.hasPermission('pay:channel:create')")
    public CommonResult<Long> createChannel(@Valid @RequestBody PayChannelCreateReqVO createReqVO) {
        return success(channelService.createChannel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新支付渠道 ")
    @PreAuthorize("@ss.hasPermission('pay:channel:update')")
    public CommonResult<Boolean> updateChannel(@Valid @RequestBody PayChannelUpdateReqVO updateReqVO) {
        channelService.updateChannel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除支付渠道 ")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pay:channel:delete')")
    public CommonResult<Boolean> deleteChannel(@RequestParam("id") Long id) {
        channelService.deleteChannel(id);
        return success(true);
    }


    @GetMapping("/get-enable-code-list")
    @Operation(summary = "获得指定应用的开启的支付渠道编码列表")
    @Parameter(name = "appId", description = "应用编号", required = true, example = "1")
    public CommonResult<Set<String>> getEnableChannelCodeList(@RequestParam("appId") Long appId) {
        List<PayChannelDO> channels = channelService.getEnableChannelList(appId);
        return success(convertSet(channels, PayChannelDO::getCode));
    }



    @GetMapping("/get")
    @Operation(summary = "获得支付渠道")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pay:channel:query')")
    public CommonResult<PayChannelRespVO> getChannel(@RequestParam(value = "id", required = false) Long id,
                                                     @RequestParam(value = "appId", required = false) Long appId,
                                                     @RequestParam(value = "code", required = false) String code) {
        PayChannelDO channel = null;
        if (id != null) {
            channel = channelService.getChannel(id);
        } else if (appId != null && code != null) {
            channel = channelService.getChannelByAppIdAndCode(appId, code);
        }
        return success(PayChannelConvert.INSTANCE.convert(channel));



    }


    /*@GetMapping("/get-channel")
    @Operation(summary = "根据条件查询微信支付渠道")
    @Parameters({
            @Parameter(name = "merchantId", description = "商户编号",
                    required = true, example = "1"),
            @Parameter(name = "appId", description = "应用编号",
                    required = true, example = "1"),
            @Parameter(name = "code", description = "支付渠道编码",
                    required = true, example = "wx_pub")
    })
    @PreAuthorize("@ss.hasPermission('pay:channel:query')")
    public CommonResult<PayChannelRespVO> getChannel(
            @RequestParam Long merchantId, @RequestParam Long appId, @RequestParam String code) {
        // 獲取渠道
        PayChannelDO channel = channelService.getChannelByConditions(merchantId, appId, code);
        if (channel == null) {
            return success(new PayChannelRespVO());
        }
        // 拼凑数据
        PayChannelRespVO respVo = PayChannelConvert.INSTANCE.convert(channel);
        if (respVo.getBankCard()==null){
            respVo.setBankCard(channel.getBankCard());
        }
        return success(respVo);
    }*/










    @GetMapping("/get1")
    @Operation(summary = "获得支付渠道 ")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pay:channel:query')")
    public CommonResult<PayChannelRespVO> getChannel(@RequestParam("id") Long id) {
        PayChannelDO channel = channelService.getChannel(id);
        return success(PayChannelConvert.INSTANCE.convert(channel));
    }

    @GetMapping("/list1")
    @Operation(summary = "获得支付渠道列表")
    @Parameter(name = "ids", description = "编号列表",
            required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('pay:channel:query')")
    public CommonResult<List<PayChannelRespVO>> getChannelList(@RequestParam("ids") Collection<Long> ids) {
        List<PayChannelDO> list = channelService.getChannelList(ids);
        return success(PayChannelConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page1")
    @Operation(summary = "获得支付渠道分页")
    @PreAuthorize("@ss.hasPermission('pay:channel:query')")
    public CommonResult<PageResult<PayChannelRespVO>> getChannelPage(@Valid PayChannelPageReqVO pageVO) {
        PageResult<PayChannelDO> pageResult = channelService.getChannelPage(pageVO);
        return success(PayChannelConvert.INSTANCE.convertPage11(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出支付渠道Excel")
    @PreAuthorize("@ss.hasPermission('pay:channel:export')")
    @OperateLog(type = EXPORT)
    public void exportChannelExcel(@Valid PayChannelExportReqVO exportReqVO,
                                   HttpServletResponse response) throws IOException {
        List<PayChannelDO> list = channelService.getChannelList(exportReqVO);
        // 导出 Excel
        List<PayChannelExcelVO> datas = PayChannelConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "支付渠道.xls", "数据", PayChannelExcelVO.class, datas);
    }



    @PostMapping("/create-NEW")
    @Operation(summary = "创建支付渠道 ")
    @PreAuthorize("@ss.hasPermission('pay:channel:create')")
    public CommonResult<Long> createChannelNew(@Valid @RequestBody PayChannelCreateReqVO createReqVO) {
        return success(channelService.createChannel(createReqVO));
    }

    @PutMapping("/update-NEW")
    @Operation(summary = "更新支付渠道 ")
    @PreAuthorize("@ss.hasPermission('pay:channel:update')")
    public CommonResult<Boolean> updateChannelNew(@Valid @RequestBody PayChannelUpdateReqVO updateReqVO) {
        channelService.updateChannel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete-NEW")
    @Operation(summary = "删除支付渠道 ")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pay:channel:delete')")
    public CommonResult<Boolean> deleteChannelNew(@RequestParam("id") Long id) {
        channelService.deleteChannel(id);
        return success(true);
    }

    @GetMapping("/get-NEW")
    @Operation(summary = "获得支付渠道")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pay:channel:query')")
    public CommonResult<PayChannelRespVO> getChannelNew(@RequestParam(value = "id", required = false) Long id,
                                                     @RequestParam(value = "appId", required = false) Long appId,
                                                     @RequestParam(value = "code", required = false) String code) {
        PayChannelDO channel = null;
        if (id != null) {
            channel = channelService.getChannel(id);
        } else if (appId != null && code != null) {
            channel = channelService.getChannelByAppIdAndCode(appId, code);
        }
        return success(PayChannelConvert.INSTANCE.convert(channel));
    }




}
