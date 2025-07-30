package com.dofast.module.mes.service.registrationline;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.mes.controller.admin.registrationline.vo.*;
import com.dofast.module.mes.dal.dataobject.registrationline.RegistrationLineDO;
import com.dofast.module.mes.dal.mysql.registrationline.RegistrationLineMapper;
import com.dofast.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static com.dofast.module.mes.enums.ErrorCodeConstants.*;
import static com.dofast.framework.test.core.util.AssertUtils.*;
import static com.dofast.framework.test.core.util.RandomUtils.*;
import static com.dofast.framework.common.util.date.LocalDateTimeUtils.*;
import static com.dofast.framework.common.util.object.ObjectUtils.*;
import static com.dofast.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link RegistrationLineServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(RegistrationLineServiceImpl.class)
public class RegistrationLineServiceImplTest extends BaseDbUnitTest {

    @Resource
    private RegistrationLineServiceImpl registrationLineService;

    @Resource
    private RegistrationLineMapper registrationLineMapper;

    @Test
    public void testCreateRegistrationLine_success() {
        // 准备参数
        RegistrationLineCreateReqVO reqVO = randomPojo(RegistrationLineCreateReqVO.class);

        // 调用
        Long registrationLineId = registrationLineService.createRegistrationLine(reqVO);
        // 断言
        assertNotNull(registrationLineId);
        // 校验记录的属性是否正确
        RegistrationLineDO registrationLine = registrationLineMapper.selectById(registrationLineId);
        assertPojoEquals(reqVO, registrationLine);
    }

    @Test
    public void testUpdateRegistrationLine_success() {
        // mock 数据
        RegistrationLineDO dbRegistrationLine = randomPojo(RegistrationLineDO.class);
        registrationLineMapper.insert(dbRegistrationLine);// @Sql: 先插入出一条存在的数据
        // 准备参数
        RegistrationLineUpdateReqVO reqVO = randomPojo(RegistrationLineUpdateReqVO.class, o -> {
            o.setId(dbRegistrationLine.getId()); // 设置更新的 ID
        });

        // 调用
        registrationLineService.updateRegistrationLine(reqVO);
        // 校验是否更新正确
        RegistrationLineDO registrationLine = registrationLineMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, registrationLine);
    }

    @Test
    public void testUpdateRegistrationLine_notExists() {
        // 准备参数
        RegistrationLineUpdateReqVO reqVO = randomPojo(RegistrationLineUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> registrationLineService.updateRegistrationLine(reqVO), REGISTRATION_LINE_NOT_EXISTS);
    }

    @Test
    public void testDeleteRegistrationLine_success() {
        // mock 数据
        RegistrationLineDO dbRegistrationLine = randomPojo(RegistrationLineDO.class);
        registrationLineMapper.insert(dbRegistrationLine);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbRegistrationLine.getId();

        // 调用
        registrationLineService.deleteRegistrationLine(id);
       // 校验数据不存在了
       assertNull(registrationLineMapper.selectById(id));
    }

    @Test
    public void testDeleteRegistrationLine_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> registrationLineService.deleteRegistrationLine(id), REGISTRATION_LINE_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetRegistrationLinePage() {
       // mock 数据
       RegistrationLineDO dbRegistrationLine = randomPojo(RegistrationLineDO.class, o -> { // 等会查询到
           o.setRegistrationCode(null);
           o.setRegistrationId(null);
           o.setRegistrationType(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setRelatedMachineryCode(null);
           o.setRelatedMachineryName(null);
           o.setRelatedErpMachineryCode(null);
           o.setWorkshopId(null);
           o.setWorkshopName(null);
           o.setWorkshopCode(null);
           o.setRelatedWorkorder(null);
           o.setRelatedTaskCode(null);
           o.setRelatedTaskId(null);
           o.setDurationTime(null);
           o.setRemark(null);
           o.setAttr1(null);
           o.setAttr2(null);
           o.setAttr3(null);
           o.setAttr4(null);
           o.setCreateTime(null);
       });
       registrationLineMapper.insert(dbRegistrationLine);
       // 测试 registrationCode 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRegistrationCode(null)));
       // 测试 registrationId 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRegistrationId(null)));
       // 测试 registrationType 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRegistrationType(null)));
       // 测试 startTime 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setEndTime(null)));
       // 测试 relatedMachineryCode 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRelatedMachineryCode(null)));
       // 测试 relatedMachineryName 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRelatedMachineryName(null)));
       // 测试 relatedErpMachineryCode 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRelatedErpMachineryCode(null)));
       // 测试 workshopId 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setWorkshopId(null)));
       // 测试 workshopName 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setWorkshopName(null)));
       // 测试 workshopCode 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setWorkshopCode(null)));
       // 测试 relatedWorkorder 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRelatedWorkorder(null)));
       // 测试 relatedTaskCode 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRelatedTaskCode(null)));
       // 测试 relatedTaskId 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRelatedTaskId(null)));
       // 测试 durationTime 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setDurationTime(null)));
       // 测试 remark 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRemark(null)));
       // 测试 attr1 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setAttr1(null)));
       // 测试 attr2 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setAttr2(null)));
       // 测试 attr3 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setAttr3(null)));
       // 测试 attr4 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setAttr4(null)));
       // 测试 createTime 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setCreateTime(null)));
       // 准备参数
       RegistrationLinePageReqVO reqVO = new RegistrationLinePageReqVO();
       reqVO.setRegistrationCode(null);
       reqVO.setRegistrationId(null);
       reqVO.setRegistrationType(null);
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRelatedMachineryCode(null);
       reqVO.setRelatedMachineryName(null);
       reqVO.setRelatedErpMachineryCode(null);
       reqVO.setWorkshopId(null);
       reqVO.setWorkshopName(null);
       reqVO.setWorkshopCode(null);
       reqVO.setRelatedWorkorder(null);
       reqVO.setRelatedTaskCode(null);
       reqVO.setRelatedTaskId(null);
       //reqVO.setDurationTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRemark(null);
       reqVO.setAttr1(null);
       reqVO.setAttr2(null);
       reqVO.setAttr3(null);
       reqVO.setAttr4(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<RegistrationLineDO> pageResult = registrationLineService.getRegistrationLinePage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbRegistrationLine, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetRegistrationLineList() {
       // mock 数据
       RegistrationLineDO dbRegistrationLine = randomPojo(RegistrationLineDO.class, o -> { // 等会查询到
           o.setRegistrationCode(null);
           o.setRegistrationId(null);
           o.setRegistrationType(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setRelatedMachineryCode(null);
           o.setRelatedMachineryName(null);
           o.setRelatedErpMachineryCode(null);
           o.setWorkshopId(null);
           o.setWorkshopName(null);
           o.setWorkshopCode(null);
           o.setRelatedWorkorder(null);
           o.setRelatedTaskCode(null);
           o.setRelatedTaskId(null);
           o.setDurationTime(null);
           o.setRemark(null);
           o.setAttr1(null);
           o.setAttr2(null);
           o.setAttr3(null);
           o.setAttr4(null);
           o.setCreateTime(null);
       });
       registrationLineMapper.insert(dbRegistrationLine);
       // 测试 registrationCode 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRegistrationCode(null)));
       // 测试 registrationId 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRegistrationId(null)));
       // 测试 registrationType 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRegistrationType(null)));
       // 测试 startTime 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setEndTime(null)));
       // 测试 relatedMachineryCode 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRelatedMachineryCode(null)));
       // 测试 relatedMachineryName 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRelatedMachineryName(null)));
       // 测试 relatedErpMachineryCode 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRelatedErpMachineryCode(null)));
       // 测试 workshopId 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setWorkshopId(null)));
       // 测试 workshopName 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setWorkshopName(null)));
       // 测试 workshopCode 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setWorkshopCode(null)));
       // 测试 relatedWorkorder 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRelatedWorkorder(null)));
       // 测试 relatedTaskCode 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRelatedTaskCode(null)));
       // 测试 relatedTaskId 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRelatedTaskId(null)));
       // 测试 durationTime 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setDurationTime(null)));
       // 测试 remark 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setRemark(null)));
       // 测试 attr1 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setAttr1(null)));
       // 测试 attr2 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setAttr2(null)));
       // 测试 attr3 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setAttr3(null)));
       // 测试 attr4 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setAttr4(null)));
       // 测试 createTime 不匹配
       registrationLineMapper.insert(cloneIgnoreId(dbRegistrationLine, o -> o.setCreateTime(null)));
       // 准备参数
       RegistrationLineExportReqVO reqVO = new RegistrationLineExportReqVO();
       reqVO.setRegistrationCode(null);
       reqVO.setRegistrationId(null);
       reqVO.setRegistrationType(null);
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRelatedMachineryCode(null);
       reqVO.setRelatedMachineryName(null);
       reqVO.setRelatedErpMachineryCode(null);
       reqVO.setWorkshopId(null);
       reqVO.setWorkshopName(null);
       reqVO.setWorkshopCode(null);
       reqVO.setRelatedWorkorder(null);
       reqVO.setRelatedTaskCode(null);
       reqVO.setRelatedTaskId(null);
       //reqVO.setDurationTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRemark(null);
       reqVO.setAttr1(null);
       reqVO.setAttr2(null);
       reqVO.setAttr3(null);
       reqVO.setAttr4(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<RegistrationLineDO> list = registrationLineService.getRegistrationLineList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbRegistrationLine, list.get(0));
    }

}
