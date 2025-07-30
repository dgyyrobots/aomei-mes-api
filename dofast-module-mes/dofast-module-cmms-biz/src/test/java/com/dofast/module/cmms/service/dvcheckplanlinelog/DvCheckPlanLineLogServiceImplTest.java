package com.dofast.module.cmms.service.dvcheckplanlinelog;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.cmms.controller.admin.dvcheckplanlinelog.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanlinelog.DvCheckPlanLineLogDO;
import com.dofast.module.cmms.dal.mysql.dvcheckplanlinelog.DvCheckPlanLineLogMapper;
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
 * {@link DvCheckPlanLineLogServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(DvCheckPlanLineLogServiceImpl.class)
public class DvCheckPlanLineLogServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DvCheckPlanLineLogServiceImpl dvCheckPlanLineLogService;

    @Resource
    private DvCheckPlanLineLogMapper dvCheckPlanLineLogMapper;

    @Test
    public void testCreateDvCheckPlanLineLog_success() {
        // 准备参数
        DvCheckPlanLineLogCreateReqVO reqVO = randomPojo(DvCheckPlanLineLogCreateReqVO.class);

        // 调用
        Long dvCheckPlanLineLogId = dvCheckPlanLineLogService.createDvCheckPlanLineLog(reqVO);
        // 断言
        assertNotNull(dvCheckPlanLineLogId);
        // 校验记录的属性是否正确
        DvCheckPlanLineLogDO dvCheckPlanLineLog = dvCheckPlanLineLogMapper.selectById(dvCheckPlanLineLogId);
        assertPojoEquals(reqVO, dvCheckPlanLineLog);
    }

    @Test
    public void testUpdateDvCheckPlanLineLog_success() {
        // mock 数据
        DvCheckPlanLineLogDO dbDvCheckPlanLineLog = randomPojo(DvCheckPlanLineLogDO.class);
        dvCheckPlanLineLogMapper.insert(dbDvCheckPlanLineLog);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DvCheckPlanLineLogUpdateReqVO reqVO = randomPojo(DvCheckPlanLineLogUpdateReqVO.class, o -> {
            o.setId(dbDvCheckPlanLineLog.getId()); // 设置更新的 ID
        });

        // 调用
        dvCheckPlanLineLogService.updateDvCheckPlanLineLog(reqVO);
        // 校验是否更新正确
        DvCheckPlanLineLogDO dvCheckPlanLineLog = dvCheckPlanLineLogMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, dvCheckPlanLineLog);
    }

    @Test
    public void testUpdateDvCheckPlanLineLog_notExists() {
        // 准备参数
        DvCheckPlanLineLogUpdateReqVO reqVO = randomPojo(DvCheckPlanLineLogUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> dvCheckPlanLineLogService.updateDvCheckPlanLineLog(reqVO), DV_CHECK_PLAN_LINE_LOG_NOT_EXISTS);
    }

    @Test
    public void testDeleteDvCheckPlanLineLog_success() {
        // mock 数据
        DvCheckPlanLineLogDO dbDvCheckPlanLineLog = randomPojo(DvCheckPlanLineLogDO.class);
        dvCheckPlanLineLogMapper.insert(dbDvCheckPlanLineLog);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbDvCheckPlanLineLog.getId();

        // 调用
        dvCheckPlanLineLogService.deleteDvCheckPlanLineLog(id);
       // 校验数据不存在了
       assertNull(dvCheckPlanLineLogMapper.selectById(id));
    }

    @Test
    public void testDeleteDvCheckPlanLineLog_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> dvCheckPlanLineLogService.deleteDvCheckPlanLineLog(id), DV_CHECK_PLAN_LINE_LOG_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDvCheckPlanLineLogPage() {
       // mock 数据
       DvCheckPlanLineLogDO dbDvCheckPlanLineLog = randomPojo(DvCheckPlanLineLogDO.class, o -> { // 等会查询到
           o.setHeaderId(null);
           o.setSubjectId(null);
           o.setSubjectCode(null);
           o.setSubjectName(null);
           o.setSubjectType(null);
           o.setSubjectContent(null);
           o.setSubjectStandard(null);
           o.setRemark(null);
           o.setAttr1(null);
           o.setAttr2(null);
           o.setAttr3(null);
           o.setAttr4(null);
           o.setCreateTime(null);
       });
       dvCheckPlanLineLogMapper.insert(dbDvCheckPlanLineLog);
       // 测试 headerId 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setHeaderId(null)));
       // 测试 subjectId 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setSubjectId(null)));
       // 测试 subjectCode 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setSubjectCode(null)));
       // 测试 subjectName 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setSubjectName(null)));
       // 测试 subjectType 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setSubjectType(null)));
       // 测试 subjectContent 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setSubjectContent(null)));
       // 测试 subjectStandard 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setSubjectStandard(null)));
       // 测试 remark 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setRemark(null)));
       // 测试 attr1 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setAttr1(null)));
       // 测试 attr2 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setAttr2(null)));
       // 测试 attr3 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setAttr3(null)));
       // 测试 attr4 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setAttr4(null)));
       // 测试 createTime 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setCreateTime(null)));
       // 准备参数
       DvCheckPlanLineLogPageReqVO reqVO = new DvCheckPlanLineLogPageReqVO();
       reqVO.setHeaderId(null);
       reqVO.setSubjectId(null);
       reqVO.setSubjectCode(null);
       reqVO.setSubjectName(null);
       reqVO.setSubjectType(null);
       reqVO.setSubjectContent(null);
       reqVO.setSubjectStandard(null);
       reqVO.setRemark(null);
       reqVO.setAttr1(null);
       reqVO.setAttr2(null);
       reqVO.setAttr3(null);
       reqVO.setAttr4(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<DvCheckPlanLineLogDO> pageResult = dvCheckPlanLineLogService.getDvCheckPlanLineLogPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbDvCheckPlanLineLog, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDvCheckPlanLineLogList() {
       // mock 数据
       DvCheckPlanLineLogDO dbDvCheckPlanLineLog = randomPojo(DvCheckPlanLineLogDO.class, o -> { // 等会查询到
           o.setHeaderId(null);
           o.setSubjectId(null);
           o.setSubjectCode(null);
           o.setSubjectName(null);
           o.setSubjectType(null);
           o.setSubjectContent(null);
           o.setSubjectStandard(null);
           o.setRemark(null);
           o.setAttr1(null);
           o.setAttr2(null);
           o.setAttr3(null);
           o.setAttr4(null);
           o.setCreateTime(null);
       });
       dvCheckPlanLineLogMapper.insert(dbDvCheckPlanLineLog);
       // 测试 headerId 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setHeaderId(null)));
       // 测试 subjectId 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setSubjectId(null)));
       // 测试 subjectCode 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setSubjectCode(null)));
       // 测试 subjectName 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setSubjectName(null)));
       // 测试 subjectType 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setSubjectType(null)));
       // 测试 subjectContent 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setSubjectContent(null)));
       // 测试 subjectStandard 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setSubjectStandard(null)));
       // 测试 remark 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setRemark(null)));
       // 测试 attr1 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setAttr1(null)));
       // 测试 attr2 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setAttr2(null)));
       // 测试 attr3 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setAttr3(null)));
       // 测试 attr4 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setAttr4(null)));
       // 测试 createTime 不匹配
       dvCheckPlanLineLogMapper.insert(cloneIgnoreId(dbDvCheckPlanLineLog, o -> o.setCreateTime(null)));
       // 准备参数
       DvCheckPlanLineLogExportReqVO reqVO = new DvCheckPlanLineLogExportReqVO();
       reqVO.setHeaderId(null);
       reqVO.setSubjectId(null);
       reqVO.setSubjectCode(null);
       reqVO.setSubjectName(null);
       reqVO.setSubjectType(null);
       reqVO.setSubjectContent(null);
       reqVO.setSubjectStandard(null);
       reqVO.setRemark(null);
       reqVO.setAttr1(null);
       reqVO.setAttr2(null);
       reqVO.setAttr3(null);
       reqVO.setAttr4(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<DvCheckPlanLineLogDO> list = dvCheckPlanLineLogService.getDvCheckPlanLineLogList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbDvCheckPlanLineLog, list.get(0));
    }

}
