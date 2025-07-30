package com.dofast.module.cmms.service.dvcheckplanheaderlog;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.cmms.controller.admin.dvcheckplanheaderlog.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanheaderlog.DvCheckPlanHeaderLogDO;
import com.dofast.module.cmms.dal.mysql.dvcheckplanheaderlog.DvCheckPlanHeaderLogMapper;
import com.dofast.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static com.dofast.module.cmms.enums.ErrorCodeConstants.*;
import static com.dofast.framework.test.core.util.AssertUtils.*;
import static com.dofast.framework.test.core.util.RandomUtils.*;
import static com.dofast.framework.common.util.date.LocalDateTimeUtils.*;
import static com.dofast.framework.common.util.object.ObjectUtils.*;
import static com.dofast.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link DvCheckPlanHeaderLogServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(DvCheckPlanHeaderLogServiceImpl.class)
public class DvCheckPlanHeaderLogServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DvCheckPlanHeaderLogServiceImpl dvCheckPlanHeaderLogService;

    @Resource
    private DvCheckPlanHeaderLogMapper dvCheckPlanHeaderLogMapper;

    @Test
    public void testCreateDvCheckPlanHeaderLog_success() {
        // 准备参数
        DvCheckPlanHeaderLogCreateReqVO reqVO = randomPojo(DvCheckPlanHeaderLogCreateReqVO.class);

        // 调用
        Long dvCheckPlanHeaderLogId = dvCheckPlanHeaderLogService.createDvCheckPlanHeaderLog(reqVO);
        // 断言
        assertNotNull(dvCheckPlanHeaderLogId);
        // 校验记录的属性是否正确
        DvCheckPlanHeaderLogDO dvCheckPlanHeaderLog = dvCheckPlanHeaderLogMapper.selectById(dvCheckPlanHeaderLogId);
        assertPojoEquals(reqVO, dvCheckPlanHeaderLog);
    }

    @Test
    public void testUpdateDvCheckPlanHeaderLog_success() {
        // mock 数据
        DvCheckPlanHeaderLogDO dbDvCheckPlanHeaderLog = randomPojo(DvCheckPlanHeaderLogDO.class);
        dvCheckPlanHeaderLogMapper.insert(dbDvCheckPlanHeaderLog);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DvCheckPlanHeaderLogUpdateReqVO reqVO = randomPojo(DvCheckPlanHeaderLogUpdateReqVO.class, o -> {
            o.setId(dbDvCheckPlanHeaderLog.getId()); // 设置更新的 ID
        });

        // 调用
        dvCheckPlanHeaderLogService.updateDvCheckPlanHeaderLog(reqVO);
        // 校验是否更新正确
        DvCheckPlanHeaderLogDO dvCheckPlanHeaderLog = dvCheckPlanHeaderLogMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, dvCheckPlanHeaderLog);
    }

    @Test
    public void testUpdateDvCheckPlanHeaderLog_notExists() {
        // 准备参数
        DvCheckPlanHeaderLogUpdateReqVO reqVO = randomPojo(DvCheckPlanHeaderLogUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> dvCheckPlanHeaderLogService.updateDvCheckPlanHeaderLog(reqVO), DV_CHECK_PLAN_HEADER_LOG_NOT_EXISTS);
    }

    @Test
    public void testDeleteDvCheckPlanHeaderLog_success() {
        // mock 数据
        DvCheckPlanHeaderLogDO dbDvCheckPlanHeaderLog = randomPojo(DvCheckPlanHeaderLogDO.class);
        dvCheckPlanHeaderLogMapper.insert(dbDvCheckPlanHeaderLog);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbDvCheckPlanHeaderLog.getId();

        // 调用
        dvCheckPlanHeaderLogService.deleteDvCheckPlanHeaderLog(id);
       // 校验数据不存在了
       assertNull(dvCheckPlanHeaderLogMapper.selectById(id));
    }

    @Test
    public void testDeleteDvCheckPlanHeaderLog_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> dvCheckPlanHeaderLogService.deleteDvCheckPlanHeaderLog(id), DV_CHECK_PLAN_HEADER_LOG_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDvCheckPlanHeaderLogPage() {
       // mock 数据
       DvCheckPlanHeaderLogDO dbDvCheckPlanHeaderLog = randomPojo(DvCheckPlanHeaderLogDO.class, o -> { // 等会查询到
           o.setPlanCode(null);
           o.setPlanName(null);
           o.setPlanType(null);
           o.setMachineryId(null);
           o.setMachineryCode(null);
           o.setMachineryName(null);
           o.setRemark(null);
           o.setAttr1(null);
           o.setAttr2(null);
           o.setAttr3(null);
           o.setAttr4(null);
           o.setCreateTime(null);
       });
       dvCheckPlanHeaderLogMapper.insert(dbDvCheckPlanHeaderLog);
       // 测试 planCode 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setPlanCode(null)));
       // 测试 planName 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setPlanName(null)));
       // 测试 planType 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setPlanType(null)));
       // 测试 machineryId 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setMachineryId(null)));
       // 测试 machineryCode 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setMachineryCode(null)));
       // 测试 machineryName 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setMachineryName(null)));
       // 测试 remark 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setRemark(null)));
       // 测试 attr1 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setAttr1(null)));
       // 测试 attr2 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setAttr2(null)));
       // 测试 attr3 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setAttr3(null)));
       // 测试 attr4 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setAttr4(null)));
       // 测试 createTime 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setCreateTime(null)));
       // 准备参数
       DvCheckPlanHeaderLogPageReqVO reqVO = new DvCheckPlanHeaderLogPageReqVO();
       reqVO.setPlanCode(null);
       reqVO.setPlanName(null);
       reqVO.setPlanType(null);
       reqVO.setMachineryId(null);
       reqVO.setMachineryCode(null);
       reqVO.setMachineryName(null);
       reqVO.setRemark(null);
       reqVO.setAttr1(null);
       reqVO.setAttr2(null);
       reqVO.setAttr3(null);
       reqVO.setAttr4(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<DvCheckPlanHeaderLogDO> pageResult = dvCheckPlanHeaderLogService.getDvCheckPlanHeaderLogPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbDvCheckPlanHeaderLog, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDvCheckPlanHeaderLogList() {
       // mock 数据
       DvCheckPlanHeaderLogDO dbDvCheckPlanHeaderLog = randomPojo(DvCheckPlanHeaderLogDO.class, o -> { // 等会查询到
           o.setPlanCode(null);
           o.setPlanName(null);
           o.setPlanType(null);
           o.setMachineryId(null);
           o.setMachineryCode(null);
           o.setMachineryName(null);
           o.setRemark(null);
           o.setAttr1(null);
           o.setAttr2(null);
           o.setAttr3(null);
           o.setAttr4(null);
           o.setCreateTime(null);
       });
       dvCheckPlanHeaderLogMapper.insert(dbDvCheckPlanHeaderLog);
       // 测试 planCode 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setPlanCode(null)));
       // 测试 planName 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setPlanName(null)));
       // 测试 planType 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setPlanType(null)));
       // 测试 machineryId 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setMachineryId(null)));
       // 测试 machineryCode 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setMachineryCode(null)));
       // 测试 machineryName 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setMachineryName(null)));
       // 测试 remark 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setRemark(null)));
       // 测试 attr1 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setAttr1(null)));
       // 测试 attr2 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setAttr2(null)));
       // 测试 attr3 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setAttr3(null)));
       // 测试 attr4 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setAttr4(null)));
       // 测试 createTime 不匹配
       dvCheckPlanHeaderLogMapper.insert(cloneIgnoreId(dbDvCheckPlanHeaderLog, o -> o.setCreateTime(null)));
       // 准备参数
       DvCheckPlanHeaderLogExportReqVO reqVO = new DvCheckPlanHeaderLogExportReqVO();
       reqVO.setPlanCode(null);
       reqVO.setPlanName(null);
       reqVO.setPlanType(null);
       reqVO.setMachineryId(null);
       reqVO.setMachineryCode(null);
       reqVO.setMachineryName(null);
       reqVO.setRemark(null);
       reqVO.setAttr1(null);
       reqVO.setAttr2(null);
       reqVO.setAttr3(null);
       reqVO.setAttr4(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<DvCheckPlanHeaderLogDO> list = dvCheckPlanHeaderLogService.getDvCheckPlanHeaderLogList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbDvCheckPlanHeaderLog, list.get(0));
    }

}
