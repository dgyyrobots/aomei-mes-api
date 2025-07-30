package com.dofast.module.iot.service.devicefeedbacklog;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.iot.controller.admin.devicefeedbacklog.vo.*;
import com.dofast.module.iot.dal.dataobject.devicefeedbacklog.DeviceFeedbackLogDO;
import com.dofast.module.iot.dal.mysql.devicefeedbacklog.DeviceFeedbackLogMapper;
import com.dofast.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
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
 * {@link DeviceFeedbackLogServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(DeviceFeedbackLogServiceImpl.class)
public class DeviceFeedbackLogServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DeviceFeedbackLogServiceImpl deviceFeedbackLogService;

    @Resource
    private DeviceFeedbackLogMapper deviceFeedbackLogMapper;

    @Test
    public void testCreateDeviceFeedbackLog_success() {
        // 准备参数
        DeviceFeedbackLogCreateReqVO reqVO = randomPojo(DeviceFeedbackLogCreateReqVO.class);

        // 调用
        Long deviceFeedbackLogId = deviceFeedbackLogService.createDeviceFeedbackLog(reqVO);
        // 断言
        assertNotNull(deviceFeedbackLogId);
        // 校验记录的属性是否正确
        DeviceFeedbackLogDO deviceFeedbackLog = deviceFeedbackLogMapper.selectById(deviceFeedbackLogId);
        assertPojoEquals(reqVO, deviceFeedbackLog);
    }

    @Test
    public void testUpdateDeviceFeedbackLog_success() {
        // mock 数据
        DeviceFeedbackLogDO dbDeviceFeedbackLog = randomPojo(DeviceFeedbackLogDO.class);
        deviceFeedbackLogMapper.insert(dbDeviceFeedbackLog);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DeviceFeedbackLogUpdateReqVO reqVO = randomPojo(DeviceFeedbackLogUpdateReqVO.class, o -> {
            o.setId(dbDeviceFeedbackLog.getId()); // 设置更新的 ID
        });

        // 调用
        deviceFeedbackLogService.updateDeviceFeedbackLog(reqVO);
        // 校验是否更新正确
        DeviceFeedbackLogDO deviceFeedbackLog = deviceFeedbackLogMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, deviceFeedbackLog);
    }

    @Test
    public void testUpdateDeviceFeedbackLog_notExists() {
        // 准备参数
        DeviceFeedbackLogUpdateReqVO reqVO = randomPojo(DeviceFeedbackLogUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> deviceFeedbackLogService.updateDeviceFeedbackLog(reqVO), DEVICE_FEEDBACK_LOG_NOT_EXISTS);
    }

    @Test
    public void testDeleteDeviceFeedbackLog_success() {
        // mock 数据
        DeviceFeedbackLogDO dbDeviceFeedbackLog = randomPojo(DeviceFeedbackLogDO.class);
        deviceFeedbackLogMapper.insert(dbDeviceFeedbackLog);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbDeviceFeedbackLog.getId();

        // 调用
        deviceFeedbackLogService.deleteDeviceFeedbackLog(id);
       // 校验数据不存在了
       assertNull(deviceFeedbackLogMapper.selectById(id));
    }

    @Test
    public void testDeleteDeviceFeedbackLog_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> deviceFeedbackLogService.deleteDeviceFeedbackLog(id), DEVICE_FEEDBACK_LOG_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDeviceFeedbackLogPage() {
       // mock 数据
       DeviceFeedbackLogDO dbDeviceFeedbackLog = randomPojo(DeviceFeedbackLogDO.class, o -> { // 等会查询到
           o.setDeviceId(null);
           o.setDeviceName(null);
           o.setDeviceCode(null);
           o.setQuantity(null);
           o.setRemark(null);
           o.setCreateTime(null);
       });
       deviceFeedbackLogMapper.insert(dbDeviceFeedbackLog);
       // 测试 deviceId 不匹配
       deviceFeedbackLogMapper.insert(cloneIgnoreId(dbDeviceFeedbackLog, o -> o.setDeviceId(null)));
       // 测试 deviceName 不匹配
       deviceFeedbackLogMapper.insert(cloneIgnoreId(dbDeviceFeedbackLog, o -> o.setDeviceName(null)));
       // 测试 deviceCode 不匹配
       deviceFeedbackLogMapper.insert(cloneIgnoreId(dbDeviceFeedbackLog, o -> o.setDeviceCode(null)));
       // 测试 quantity 不匹配
       deviceFeedbackLogMapper.insert(cloneIgnoreId(dbDeviceFeedbackLog, o -> o.setQuantity(null)));
       // 测试 remark 不匹配
       deviceFeedbackLogMapper.insert(cloneIgnoreId(dbDeviceFeedbackLog, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       deviceFeedbackLogMapper.insert(cloneIgnoreId(dbDeviceFeedbackLog, o -> o.setCreateTime(null)));
       // 准备参数
       DeviceFeedbackLogPageReqVO reqVO = new DeviceFeedbackLogPageReqVO();
       reqVO.setDeviceId(null);
       reqVO.setDeviceName(null);
       reqVO.setDeviceCode(null);
       reqVO.setQuantity(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<DeviceFeedbackLogDO> pageResult = deviceFeedbackLogService.getDeviceFeedbackLogPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbDeviceFeedbackLog, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDeviceFeedbackLogList() {
       // mock 数据
       DeviceFeedbackLogDO dbDeviceFeedbackLog = randomPojo(DeviceFeedbackLogDO.class, o -> { // 等会查询到
           o.setDeviceId(null);
           o.setDeviceName(null);
           o.setDeviceCode(null);
           o.setQuantity(null);
           o.setRemark(null);
           o.setCreateTime(null);
       });
       deviceFeedbackLogMapper.insert(dbDeviceFeedbackLog);
       // 测试 deviceId 不匹配
       deviceFeedbackLogMapper.insert(cloneIgnoreId(dbDeviceFeedbackLog, o -> o.setDeviceId(null)));
       // 测试 deviceName 不匹配
       deviceFeedbackLogMapper.insert(cloneIgnoreId(dbDeviceFeedbackLog, o -> o.setDeviceName(null)));
       // 测试 deviceCode 不匹配
       deviceFeedbackLogMapper.insert(cloneIgnoreId(dbDeviceFeedbackLog, o -> o.setDeviceCode(null)));
       // 测试 quantity 不匹配
       deviceFeedbackLogMapper.insert(cloneIgnoreId(dbDeviceFeedbackLog, o -> o.setQuantity(null)));
       // 测试 remark 不匹配
       deviceFeedbackLogMapper.insert(cloneIgnoreId(dbDeviceFeedbackLog, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       deviceFeedbackLogMapper.insert(cloneIgnoreId(dbDeviceFeedbackLog, o -> o.setCreateTime(null)));
       // 准备参数
       DeviceFeedbackLogExportReqVO reqVO = new DeviceFeedbackLogExportReqVO();
       reqVO.setDeviceId(null);
       reqVO.setDeviceName(null);
       reqVO.setDeviceCode(null);
       reqVO.setQuantity(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<DeviceFeedbackLogDO> list = deviceFeedbackLogService.getDeviceFeedbackLogList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbDeviceFeedbackLog, list.get(0));
    }

}
