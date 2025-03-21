package com.dofast.module.member.convert.user;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.module.member.api.user.dto.MemberUserRespDTO;


import com.dofast.module.member.controller.admin.user.vo.MemberUserCreateReqVO;


import com.dofast.module.member.controller.admin.user.vo.MemberUserCreateReqVO;

import com.dofast.module.member.controller.admin.user.vo.MemberUserRespVO;
import com.dofast.module.member.controller.admin.user.vo.MemberUserUpdateReqVO;
import com.dofast.module.member.controller.app.user.vo.AppMemberUserInfoRespVO;
import com.dofast.module.member.convert.address.AddressConvert;
import com.dofast.module.member.dal.dataobject.group.MemberGroupDO;
import com.dofast.module.member.dal.dataobject.level.MemberLevelDO;
import com.dofast.module.member.dal.dataobject.tag.MemberTagDO;
import com.dofast.module.member.dal.dataobject.user.MemberUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static com.dofast.framework.common.util.collection.CollectionUtils.convertList;
import static com.dofast.framework.common.util.collection.CollectionUtils.convertMap;

@Mapper(uses = {AddressConvert.class})
public interface MemberUserConvert {

    MemberUserConvert INSTANCE = Mappers.getMapper(MemberUserConvert.class);

    AppMemberUserInfoRespVO convert(MemberUserDO bean);

    @Mapping(source = "level", target = "level")
    @Mapping(source = "bean.experience", target = "experience")
    AppMemberUserInfoRespVO convert(MemberUserDO bean, MemberLevelDO level);





    MemberUserDO convert(MemberUserCreateReqVO bean);
    MemberUserRespDTO convert2(MemberUserDO bean);

    List<MemberUserRespDTO> convertList2(List<MemberUserDO> list);

    MemberUserDO convert(MemberUserUpdateReqVO bean);

    PageResult<MemberUserRespVO> convertPage(PageResult<MemberUserDO> page);

    @Mapping(source = "areaId", target = "areaName", qualifiedByName = "convertAreaIdToAreaName")
    MemberUserRespVO convert03(MemberUserDO bean);

    default PageResult<MemberUserRespVO> convertPage(PageResult<MemberUserDO> pageResult,
                                                     List<MemberTagDO> tags,
                                                     List<MemberLevelDO> levels,
                                                     List<MemberGroupDO> groups) {
        PageResult<MemberUserRespVO> result = convertPage(pageResult);
        // 处理关联数据
        Map<Long, String> tagMap = convertMap(tags, MemberTagDO::getId, MemberTagDO::getName);
        Map<Long, String> levelMap = convertMap(levels, MemberLevelDO::getId, MemberLevelDO::getName);
        Map<Long, String> groupMap = convertMap(groups, MemberGroupDO::getId, MemberGroupDO::getName);
        // 填充关联数据
        result.getList().forEach(user -> {
            user.setTagNames(convertList(user.getTagIds(), tagMap::get));
            user.setLevelName(levelMap.get(user.getLevelId()));
            user.setGroupName(groupMap.get(user.getGroupId()));
        });
        return result;
    }



    List<MemberUserRespVO> convertList1(List<MemberUserDO> list);


}
