package com.dofast.module.member.service.user;



import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.validation.Mobile;
import com.dofast.module.member.controller.admin.user.vo.MemberUserCreateReqVO;
import com.dofast.module.member.controller.admin.user.vo.MemberUserExportReqVO;
import com.dofast.framework.common.enums.TerminalEnum;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.validation.Mobile;

import com.dofast.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import com.dofast.module.member.controller.admin.user.vo.MemberUserUpdateReqVO;
import com.dofast.module.member.controller.app.user.vo.AppMemberUserResetPasswordReqVO;
import com.dofast.module.member.controller.app.user.vo.AppMemberUserUpdateMobileReqVO;
import com.dofast.module.member.controller.app.user.vo.AppMemberUserUpdatePasswordReqVO;
import com.dofast.module.member.controller.app.user.vo.AppMemberUserUpdateReqVO;
import com.dofast.module.member.dal.dataobject.user.MemberUserDO;


import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.validation.Mobile;
import com.dofast.module.member.controller.admin.user.vo.MemberUserCreateReqVO;
import com.dofast.module.member.controller.admin.user.vo.MemberUserExportReqVO;
import com.dofast.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import com.dofast.module.member.controller.admin.user.vo.MemberUserUpdateReqVO;
import com.dofast.module.member.controller.app.user.vo.AppMemberUserResetPasswordReqVO;
import com.dofast.module.member.controller.app.user.vo.AppMemberUserUpdateMobileReqVO;
import com.dofast.module.member.controller.app.user.vo.AppMemberUserUpdatePasswordReqVO;
import com.dofast.module.member.controller.app.user.vo.AppMemberUserUpdateReqVO;
import com.dofast.module.member.dal.dataobject.user.MemberUserDO;


import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 会员用户 Service 接口
 *
 * @author 芋道源码
 */
public interface MemberUserService {






    /**
     * 创建商城用户
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUser(@Valid MemberUserCreateReqVO createReqVO);

    /**
     * 通过手机查询用户
     *
     * @param mobile 手机
     * @return 用户对象
     */
    MemberUserDO getUserByMobile(String mobile);

    /**
     * 基于用户昵称，模糊匹配用户列表
     *
     * @param nickname 用户昵称，模糊匹配
     * @return 用户信息的列表
     */
    List<MemberUserDO> getUserListByNickname(String nickname);

    /**
     * 基于手机号创建用户。
     * 如果用户已经存在，则直接进行返回
     *
     * @param mobile     手机号

     * @param registerIp 注册 IP

     * @return 用户对象
     */
//    MemberUserDO createUserIfAbsent(@Mobile String mobile, String registerIp);

    /**
     * @param terminal   终端 {@link TerminalEnum}
     * @return 用户对象
     */
    MemberUserDO createUserIfAbsent(@Mobile String mobile, String registerIp, Integer terminal);


    /**
     * 基于手机号创建用户。
     * 如果用户已经存在，则直接进行返回
     *
     * @param mobile 手机号


     * @param registerIp 注册 IP
     * @return 用户对象
     */
    MemberUserDO createUserIfAbsent(@Mobile String mobile, String registerIp);
    /**
     * 更新用户的最后登陆信息
     *
     * @param id      用户编号
     * @param loginIp 登陆 IP
     */
    void updateUserLogin(Long id, String loginIp);

    /**
     * 通过用户 ID 查询用户
     *
     * @param id 用户ID
     * @return 用户对象信息
     */
    MemberUserDO getUser(Long id);

    /**
     * 通过用户 ID 查询用户们
     *
     * @param ids 用户 ID
     * @return 用户对象信息数组
     */
    List<MemberUserDO> getUserList(Collection<Long> ids);

    /**
     * 【会员】修改基本信息
     *
     * @param userId 用户编号
     * @param reqVO  基本信息
     */
    void updateUser(Long userId, AppMemberUserUpdateReqVO reqVO);

    /**
     * 【会员】修改手机
     *
     * @param userId 用户编号
     * @param reqVO  请求信息
     */
    void updateUserMobile(Long userId, AppMemberUserUpdateMobileReqVO reqVO);

    /**
     * 【会员】修改密码
     *
     * @param userId 用户编号
     * @param reqVO  请求信息
     */
    void updateUserPassword(Long userId, AppMemberUserUpdatePasswordReqVO reqVO);

    /**
     * 【会员】忘记密码
     *
     * @param reqVO 请求信息
     */
    void resetUserPassword(AppMemberUserResetPasswordReqVO reqVO);

    /**
     * 判断密码是否匹配
     *
     * @param rawPassword     未加密的密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);

    /**
     * 【管理员】更新会员用户
     *
     * @param updateReqVO 更新信息
     */
    void updateUser(@Valid MemberUserUpdateReqVO updateReqVO);

    /**
     * 【管理员】获得会员用户分页
     *
     * @param pageReqVO 分页查询
     * @return 会员用户分页
     */
    PageResult<MemberUserDO> getUserPage(MemberUserPageReqVO pageReqVO);

    /**
     * 更新用户的等级和经验
     *
     * @param id         用户编号
     * @param levelId    用户等级
     * @param experience 用户经验
     */
    void updateUserLevel(Long id, Long levelId, Integer experience);

    /**
     * 获得指定用户分组下的用户数量
     *
     * @param groupId 用户分组编号
     * @return 用户数量
     */
    Long getUserCountByGroupId(Long groupId);

    /**
     * 获得指定用户等级下的用户数量
     *
     * @param levelId 用户等级编号
     * @return 用户数量
     */
    Long getUserCountByLevelId(Long levelId);

    /**
     * 获得指定会员标签下的用户数量
     *
     * @param tagId 用户标签编号
     * @return 用户数量
     */
    Long getUserCountByTagId(Long tagId);

    /**
     * 更新用户的积分
     *
     * @param userId 用户编号
     * @param point  积分数量
     * @return 更新结果
     */
    boolean updateUserPoint(Long userId, Integer point);



    Long getUserList(MemberUserExportReqVO exportReqVO);



    /**
     * 获得商城用户列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 商城用户列表
     */
    List<MemberUserDO> getUserList1(MemberUserExportReqVO exportReqVO);



}
