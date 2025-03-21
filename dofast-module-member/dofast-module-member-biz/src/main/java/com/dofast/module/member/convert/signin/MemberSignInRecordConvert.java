package com.dofast.module.member.convert.signin;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;



import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.util.collection.MapUtils;
import com.dofast.module.member.api.user.dto.MemberUserRespDTO;
import com.dofast.module.member.controller.admin.signin.vo.record.MemberSignInRecordRespVO;
import com.dofast.module.member.controller.app.signin.vo.record.AppMemberSignInRecordRespVO;


import com.dofast.module.member.dal.dataobject.signin.MemberSignInConfigDO;



import com.dofast.module.member.dal.dataobject.signin.MemberSignInRecordDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;



import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.dofast.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * 签到记录 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface MemberSignInRecordConvert {

    MemberSignInRecordConvert INSTANCE = Mappers.getMapper(MemberSignInRecordConvert.class);

    default PageResult<MemberSignInRecordRespVO> convertPage(PageResult<MemberSignInRecordDO> pageResult, List<MemberUserRespDTO> users) {
        PageResult<MemberSignInRecordRespVO> voPageResult = convertPage(pageResult);
        // user 拼接
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);
        voPageResult.getList().forEach(record -> MapUtils.findAndThen(userMap, record.getUserId(),
                memberUserRespDTO -> record.setNickname(memberUserRespDTO.getNickname())));
        return voPageResult;
    }






    PageResult<MemberSignInRecordRespVO> convertPage(PageResult<MemberSignInRecordDO> pageResult);

    PageResult<AppMemberSignInRecordRespVO> convertPage02(PageResult<MemberSignInRecordDO> pageResult);

    AppMemberSignInRecordRespVO coverRecordToAppRecordVo(MemberSignInRecordDO memberSignInRecordDO);



    default MemberSignInRecordDO convert(Long userId, MemberSignInRecordDO lastRecord, List<MemberSignInConfigDO> configs) {
        // 1. 计算是第几天签到
        configs.sort(Comparator.comparing(MemberSignInConfigDO::getDay));
        MemberSignInConfigDO lastConfig = CollUtil.getLast(configs); // 最大签到天数配置
        // 1.2. 计算今天是第几天签到
        int day = 1;
        // TODO @puhui999：要判断是不是昨天签到的；是否是昨天的判断，可以抽个方法到 util 里
        if (lastRecord  != null) {
            day = lastRecord .getDay() + 1;
        }
        // 1.3 判断是否超出了最大签到配置
        if (day > lastConfig.getDay()) {
            day = 1; // 超过最大配置的天数，重置到第一天。(也就是说开启下一轮签到)
        }

        // 2.1 初始化签到信息
        MemberSignInRecordDO record = new MemberSignInRecordDO().setUserId(userId)
                .setDay(day).setPoint(0).setExperience(0);
        // 2.2 获取签到对应的积分
        MemberSignInConfigDO config = CollUtil.findOne(configs, item -> ObjUtil.equal(item.getDay(), record.getDay()));
        if (config == null) {
            return record;
        }
        record.setPoint(config.getPoint());
        record.setExperience(config.getExperience());
        return record;
    }





}
