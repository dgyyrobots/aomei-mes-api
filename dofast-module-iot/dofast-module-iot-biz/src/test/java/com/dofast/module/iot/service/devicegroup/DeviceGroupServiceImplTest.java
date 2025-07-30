package com.dofast.module.iot.service.devicegroup;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.iot.controller.admin.devicegroup.vo.*;
import com.dofast.module.iot.dal.dataobject.devicegroup.DeviceGroupDO;
import com.dofast.module.iot.dal.mysql.devicegroup.DeviceGroupMapper;
import com.dofast.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static com.dofast.module.iot.enums.ErrorCodeConstants.*;
import static com.dofast.framework.test.core.util.AssertUtils.*;
import static com.dofast.framework.test.core.util.RandomUtils.*;
import static com.dofast.framework.common.util.date.LocalDateTimeUtils.*;
import static com.dofast.framework.common.util.object.ObjectUtils.*;
import static com.dofast.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link DeviceGroupServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(DeviceGroupServiceImpl.class)
public class DeviceGroupServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DeviceGroupServiceImpl deviceGroupService;

    @Resource
    private DeviceGroupMapper deviceGroupMapper;

    @Test
    public void testCreateDeviceGroup_success() {
        // 准备参数
        DeviceGroupCreateReqVO reqVO = randomPojo(DeviceGroupCreateReqVO.class);

        // 调用
        Long deviceGroupId = deviceGroupService.createDeviceGroup(reqVO);
        // 断言
        assertNotNull(deviceGroupId);
        // 校验记录的属性是否正确
        DeviceGroupDO deviceGroup = deviceGroupMapper.selectById(deviceGroupId);
        assertPojoEquals(reqVO, deviceGroup);
    }

    @Test
    public void testUpdateDeviceGroup_success() {
        // mock 数据
        DeviceGroupDO dbDeviceGroup = randomPojo(DeviceGroupDO.class);
        deviceGroupMapper.insert(dbDeviceGroup);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DeviceGroupUpdateReqVO reqVO = randomPojo(DeviceGroupUpdateReqVO.class, o -> {
            o.setId(dbDeviceGroup.getId()); // 设置更新的 ID
        });

        // 调用
        deviceGroupService.updateDeviceGroup(reqVO);
        // 校验是否更新正确
        DeviceGroupDO deviceGroup = deviceGroupMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, deviceGroup);
    }

    @Test
    public void testUpdateDeviceGroup_notExists() {
        // 准备参数
        DeviceGroupUpdateReqVO reqVO = randomPojo(DeviceGroupUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> deviceGroupService.updateDeviceGroup(reqVO), DEVICE_GROUP_NOT_EXISTS);
    }

    @Test
    public void testDeleteDeviceGroup_success() {
        // mock 数据
        DeviceGroupDO dbDeviceGroup = randomPojo(DeviceGroupDO.class);
        deviceGroupMapper.insert(dbDeviceGroup);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbDeviceGroup.getId();

        // 调用
        deviceGroupService.deleteDeviceGroup(id);
       // 校验数据不存在了
       assertNull(deviceGroupMapper.selectById(id));
    }

    @Test
    public void testDeleteDeviceGroup_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> deviceGroupService.deleteDeviceGroup(id), DEVICE_GROUP_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDeviceGroupPage() {
       // mock 数据
       DeviceGroupDO dbDeviceGroup = randomPojo(DeviceGroupDO.class, o -> { // 等会查询到
           o.setCreateTime(null);
       });
       deviceGroupMapper.insert(dbDeviceGroup);
       // 测试 createTime 不匹配
       deviceGroupMapper.insert(cloneIgnoreId(dbDeviceGroup, o -> o.setCreateTime(null)));
       // 准备参数
       DeviceGroupPageReqVO reqVO = new DeviceGroupPageReqVO();
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<DeviceGroupDO> pageResult = deviceGroupService.getDeviceGroupPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbDeviceGroup, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDeviceGroupList() {
       // mock 数据
       DeviceGroupDO dbDeviceGroup = randomPojo(DeviceGroupDO.class, o -> { // 等会查询到
           o.setCreateTime(null);
       });
       deviceGroupMapper.insert(dbDeviceGroup);
       // 测试 createTime 不匹配
       deviceGroupMapper.insert(cloneIgnoreId(dbDeviceGroup, o -> o.setCreateTime(null)));
       // 准备参数
       DeviceGroupExportReqVO reqVO = new DeviceGroupExportReqVO();
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<DeviceGroupDO> list = deviceGroupService.getDeviceGroupList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbDeviceGroup, list.get(0));
    }


}
