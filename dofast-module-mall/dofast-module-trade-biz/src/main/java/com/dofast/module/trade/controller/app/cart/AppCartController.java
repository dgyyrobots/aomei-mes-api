package com.dofast.module.trade.controller.app.cart;

import com.dofast.framework.common.pojo.CommonResult;
import com.dofast.framework.security.core.annotations.PreAuthenticated;
import com.dofast.module.trade.controller.app.cart.vo.*;
import com.dofast.module.trade.service.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




import oracle.jdbc.proxy.annotation.Post;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.dofast.framework.common.pojo.CommonResult.success;
import static com.dofast.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 App - 购物车")
@RestController
@RequestMapping("/trade/cart")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AppCartController {

    @Resource
    private CartService cartService;

    @PostMapping("/add")
    @Operation(summary = "添加购物车商品")
    @PreAuthenticated
    public CommonResult<Long> addCart(@Valid @RequestBody AppCartAddReqVO addCountReqVO) {
        return success(cartService.addCart(getLoginUserId(), addCountReqVO));
    }

    @PutMapping("/update-count")
    @Operation(summary = "更新购物车商品数量")
    @PreAuthenticated
    public CommonResult<Boolean> updateCartCount(@Valid @RequestBody AppCartUpdateCountReqVO updateReqVO) {
        cartService.updateCartCount(getLoginUserId(), updateReqVO);
        return success(true);
    }

    @PutMapping("/update-selected")
    @Operation(summary = "更新购物车商品选中")
    @PreAuthenticated
    public CommonResult<Boolean> updateCartSelected(@Valid @RequestBody AppCartUpdateSelectedReqVO updateReqVO) {
        cartService.updateCartSelected(getLoginUserId(), updateReqVO);
        return success(true);
    }

    @PutMapping("/reset")
    @Operation(summary = "重置购物车商品")
    @PreAuthenticated
    public CommonResult<Boolean> resetCart(@Valid @RequestBody AppCartResetReqVO updateReqVO) {
        cartService.resetCart(getLoginUserId(), updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除购物车商品")
    @Parameter(name = "ids", description = "购物车商品编号", required = true, example = "1024,2048")
    @PreAuthenticated
    public CommonResult<Boolean> deleteCart(@RequestParam("ids") List<Long> ids) {
        cartService.deleteCart(getLoginUserId(), ids);
        return success(true);
    }

    @GetMapping("get-count")
    @Operation(summary = "查询用户在购物车中的商品数量")
    @PreAuthenticated
    public CommonResult<Integer> getCartCount() {
        return success(cartService.getCartCount(getLoginUserId()));
    }

    @GetMapping("/list")
    @Operation(summary = "查询用户的购物车列表")
    @PreAuthenticated
    public CommonResult<AppCartListRespVO> getCartList() {
        return success(cartService.getCartList(getLoginUserId()));
    }





    @PostMapping("/settlement")
    @Operation(summary = "结算")
    @PreAuthenticated
    public CommonResult<Boolean> settlement(){
        return success(true);
    }





}
