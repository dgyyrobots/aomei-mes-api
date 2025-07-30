package com.dofast.module.mes.service.registration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.mes.controller.admin.registration.vo.*;
import com.dofast.module.mes.dal.dataobject.registration.RegistrationDO;
import com.dofast.module.mes.dal.mysql.registration.RegistrationMapper;
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
 * {@link RegistrationServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(RegistrationServiceImpl.class)
public class RegistrationServiceImplTest extends BaseDbUnitTest {

    @Resource
    private RegistrationServiceImpl registrationService;

    @Resource
    private RegistrationMapper registrationMapper;

    @Test
    public void testCreateRegistration_success() {
        // 准备参数
        RegistrationCreateReqVO reqVO = randomPojo(RegistrationCreateReqVO.class);

        // 调用
        Long registrationId = registrationService.createRegistration(reqVO);
        // 断言
        assertNotNull(registrationId);
        // 校验记录的属性是否正确
        RegistrationDO registration = registrationMapper.selectById(registrationId);
        assertPojoEquals(reqVO, registration);
    }

    @Test
    public void testUpdateRegistration_success() {
        // mock 数据
        RegistrationDO dbRegistration = randomPojo(RegistrationDO.class);
        registrationMapper.insert(dbRegistration);// @Sql: 先插入出一条存在的数据
        // 准备参数
        RegistrationUpdateReqVO reqVO = randomPojo(RegistrationUpdateReqVO.class, o -> {
            o.setId(dbRegistration.getId()); // 设置更新的 ID
        });

        // 调用
        registrationService.updateRegistration(reqVO);
        // 校验是否更新正确
        RegistrationDO registration = registrationMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, registration);
    }

    @Test
    public void testUpdateRegistration_notExists() {
        // 准备参数
        RegistrationUpdateReqVO reqVO = randomPojo(RegistrationUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> registrationService.updateRegistration(reqVO), REGISTRATION_NOT_EXISTS);
    }

    @Test
    public void testDeleteRegistration_success() {
        // mock 数据
        RegistrationDO dbRegistration = randomPojo(RegistrationDO.class);
        registrationMapper.insert(dbRegistration);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbRegistration.getId();

        // 调用
        registrationService.deleteRegistration(id);
       // 校验数据不存在了
       assertNull(registrationMapper.selectById(id));
    }

    @Test
    public void testDeleteRegistration_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> registrationService.deleteRegistration(id), REGISTRATION_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetRegistrationPage() {
       // mock 数据
       RegistrationDO dbRegistration = randomPojo(RegistrationDO.class, o -> { // 等会查询到
           o.setRegistrationCode(null);
           o.setRegistrationName(null);
           o.setRegistrationType(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setWorkshopId(null);
           o.setWorkshopName(null);
           o.setWorkshopCode(null);
           o.setRelatedMachineryCode(null);
           o.setRelatedMachineryName(null);
           o.setRelatedErpMachineryCode(null);
           o.setRelatedWorkorder(null);
           o.setRelatedMaterialCode(null);
           o.setRelatedMaterialName(null);
           o.setRemark(null);
           o.setAttr1(null);
           o.setAttr2(null);
           o.setAttr3(null);
           o.setAttr4(null);
           o.setCreateTime(null);
       });
       registrationMapper.insert(dbRegistration);
       // 测试 registrationCode 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRegistrationCode(null)));
       // 测试 registrationName 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRegistrationName(null)));
       // 测试 registrationType 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRegistrationType(null)));
       // 测试 startTime 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setEndTime(null)));
       // 测试 workshopId 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setWorkshopId(null)));
       // 测试 workshopName 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setWorkshopName(null)));
       // 测试 workshopCode 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setWorkshopCode(null)));
       // 测试 relatedMachineryCode 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRelatedMachineryCode(null)));
       // 测试 relatedMachineryName 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRelatedMachineryName(null)));
       // 测试 relatedErpMachineryCode 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRelatedErpMachineryCode(null)));
       // 测试 relatedWorkorder 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRelatedWorkorder(null)));
       // 测试 relatedMaterialCode 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRelatedMaterialCode(null)));
       // 测试 relatedMaterialName 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRelatedMaterialName(null)));
       // 测试 remark 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRemark(null)));
       // 测试 attr1 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setAttr1(null)));
       // 测试 attr2 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setAttr2(null)));
       // 测试 attr3 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setAttr3(null)));
       // 测试 attr4 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setAttr4(null)));
       // 测试 createTime 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setCreateTime(null)));
       // 准备参数
       RegistrationPageReqVO reqVO = new RegistrationPageReqVO();
       reqVO.setRegistrationCode(null);
       reqVO.setRegistrationName(null);
       reqVO.setRegistrationType(null);
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setWorkshopId(null);
       reqVO.setWorkshopName(null);
       reqVO.setWorkshopCode(null);
       reqVO.setRelatedMachineryCode(null);
       reqVO.setRelatedMachineryName(null);
       reqVO.setRelatedErpMachineryCode(null);
       reqVO.setRelatedWorkorder(null);
       reqVO.setRelatedMaterialCode(null);
       reqVO.setRelatedMaterialName(null);
       reqVO.setRemark(null);
       reqVO.setAttr1(null);
       reqVO.setAttr2(null);
       reqVO.setAttr3(null);
       reqVO.setAttr4(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<RegistrationDO> pageResult = registrationService.getRegistrationPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbRegistration, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetRegistrationList() {
       // mock 数据
       RegistrationDO dbRegistration = randomPojo(RegistrationDO.class, o -> { // 等会查询到
           o.setRegistrationCode(null);
           o.setRegistrationName(null);
           o.setRegistrationType(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setWorkshopId(null);
           o.setWorkshopName(null);
           o.setWorkshopCode(null);
           o.setRelatedMachineryCode(null);
           o.setRelatedMachineryName(null);
           o.setRelatedErpMachineryCode(null);
           o.setRelatedWorkorder(null);
           o.setRelatedMaterialCode(null);
           o.setRelatedMaterialName(null);
           o.setRemark(null);
           o.setAttr1(null);
           o.setAttr2(null);
           o.setAttr3(null);
           o.setAttr4(null);
           o.setCreateTime(null);
       });
       registrationMapper.insert(dbRegistration);
       // 测试 registrationCode 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRegistrationCode(null)));
       // 测试 registrationName 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRegistrationName(null)));
       // 测试 registrationType 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRegistrationType(null)));
       // 测试 startTime 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setEndTime(null)));
       // 测试 workshopId 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setWorkshopId(null)));
       // 测试 workshopName 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setWorkshopName(null)));
       // 测试 workshopCode 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setWorkshopCode(null)));
       // 测试 relatedMachineryCode 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRelatedMachineryCode(null)));
       // 测试 relatedMachineryName 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRelatedMachineryName(null)));
       // 测试 relatedErpMachineryCode 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRelatedErpMachineryCode(null)));
       // 测试 relatedWorkorder 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRelatedWorkorder(null)));
       // 测试 relatedMaterialCode 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRelatedMaterialCode(null)));
       // 测试 relatedMaterialName 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRelatedMaterialName(null)));
       // 测试 remark 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setRemark(null)));
       // 测试 attr1 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setAttr1(null)));
       // 测试 attr2 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setAttr2(null)));
       // 测试 attr3 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setAttr3(null)));
       // 测试 attr4 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setAttr4(null)));
       // 测试 createTime 不匹配
       registrationMapper.insert(cloneIgnoreId(dbRegistration, o -> o.setCreateTime(null)));
       // 准备参数
       RegistrationExportReqVO reqVO = new RegistrationExportReqVO();
       reqVO.setRegistrationCode(null);
       reqVO.setRegistrationName(null);
       reqVO.setRegistrationType(null);
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setWorkshopId(null);
       reqVO.setWorkshopName(null);
       reqVO.setWorkshopCode(null);
       reqVO.setRelatedMachineryCode(null);
       reqVO.setRelatedMachineryName(null);
       reqVO.setRelatedErpMachineryCode(null);
       reqVO.setRelatedWorkorder(null);
       reqVO.setRelatedMaterialCode(null);
       reqVO.setRelatedMaterialName(null);
       reqVO.setRemark(null);
       reqVO.setAttr1(null);
       reqVO.setAttr2(null);
       reqVO.setAttr3(null);
       reqVO.setAttr4(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<RegistrationDO> list = registrationService.getRegistrationList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbRegistration, list.get(0));
    }

}
