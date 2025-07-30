package com.dofast.module.pro.service.feedbackwarehousinglog;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.pro.controller.admin.feedbackwarehousinglog.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackwarehousinglog.FeedbackWarehousingLogDO;
import com.dofast.module.pro.dal.mysql.feedbackwarehousinglog.FeedbackWarehousingLogMapper;
import com.dofast.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static com.dofast.module.pro.enums.ErrorCodeConstants.*;
import static com.dofast.framework.test.core.util.AssertUtils.*;
import static com.dofast.framework.test.core.util.RandomUtils.*;
import static com.dofast.framework.common.util.date.LocalDateTimeUtils.*;
import static com.dofast.framework.common.util.object.ObjectUtils.*;
import static com.dofast.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link FeedbackWarehousingLogServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(FeedbackWarehousingLogServiceImpl.class)
public class FeedbackWarehousingLogServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FeedbackWarehousingLogServiceImpl feedbackWarehousingLogService;

    @Resource
    private FeedbackWarehousingLogMapper feedbackWarehousingLogMapper;

    @Test
    public void testCreateFeedbackWarehousingLog_success() {
        // 准备参数
        FeedbackWarehousingLogCreateReqVO reqVO = randomPojo(FeedbackWarehousingLogCreateReqVO.class);

        // 调用
        Long feedbackWarehousingLogId = feedbackWarehousingLogService.createFeedbackWarehousingLog(reqVO);
        // 断言
        assertNotNull(feedbackWarehousingLogId);
        // 校验记录的属性是否正确
        FeedbackWarehousingLogDO feedbackWarehousingLog = feedbackWarehousingLogMapper.selectById(feedbackWarehousingLogId);
        assertPojoEquals(reqVO, feedbackWarehousingLog);
    }

    @Test
    public void testUpdateFeedbackWarehousingLog_success() {
        // mock 数据
        FeedbackWarehousingLogDO dbFeedbackWarehousingLog = randomPojo(FeedbackWarehousingLogDO.class);
        feedbackWarehousingLogMapper.insert(dbFeedbackWarehousingLog);// @Sql: 先插入出一条存在的数据
        // 准备参数
        FeedbackWarehousingLogUpdateReqVO reqVO = randomPojo(FeedbackWarehousingLogUpdateReqVO.class, o -> {
            o.setId(dbFeedbackWarehousingLog.getId()); // 设置更新的 ID
        });

        // 调用
        feedbackWarehousingLogService.updateFeedbackWarehousingLog(reqVO);
        // 校验是否更新正确
        FeedbackWarehousingLogDO feedbackWarehousingLog = feedbackWarehousingLogMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, feedbackWarehousingLog);
    }

    @Test
    public void testUpdateFeedbackWarehousingLog_notExists() {
        // 准备参数
        FeedbackWarehousingLogUpdateReqVO reqVO = randomPojo(FeedbackWarehousingLogUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> feedbackWarehousingLogService.updateFeedbackWarehousingLog(reqVO), FEEDBACK_WAREHOUSING_LOG_NOT_EXISTS);
    }

    @Test
    public void testDeleteFeedbackWarehousingLog_success() {
        // mock 数据
        FeedbackWarehousingLogDO dbFeedbackWarehousingLog = randomPojo(FeedbackWarehousingLogDO.class);
        feedbackWarehousingLogMapper.insert(dbFeedbackWarehousingLog);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFeedbackWarehousingLog.getId();

        // 调用
        feedbackWarehousingLogService.deleteFeedbackWarehousingLog(id);
       // 校验数据不存在了
       assertNull(feedbackWarehousingLogMapper.selectById(id));
    }

    @Test
    public void testDeleteFeedbackWarehousingLog_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> feedbackWarehousingLogService.deleteFeedbackWarehousingLog(id), FEEDBACK_WAREHOUSING_LOG_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFeedbackWarehousingLogPage() {
       // mock 数据
       FeedbackWarehousingLogDO dbFeedbackWarehousingLog = randomPojo(FeedbackWarehousingLogDO.class, o -> { // 等会查询到
           o.setFeedbackCode(null);
           o.setWorkstationId(null);
           o.setWorkstationCode(null);
           o.setWorkstationName(null);
           o.setWorkorderId(null);
           o.setWorkorderCode(null);
           o.setWorkorderName(null);
           o.setProcessId(null);
           o.setProcessCode(null);
           o.setProcessName(null);
           o.setTaskId(null);
           o.setTaskCode(null);
           o.setItemId(null);
           o.setItemCode(null);
           o.setItemName(null);
           o.setUnitOfMeasure(null);
           o.setSpecification(null);
           o.setQuantityFeedback(null);
           o.setQuantityQualified(null);
           o.setQuantityUnquanlified(null);
           o.setUserName(null);
           o.setNickName(null);
           o.setBatchCode(null);
           o.setErpBatchCode(null);
           o.setMachineryName(null);
           o.setMachineryCode(null);
           o.setMachineryId(null);
           o.setErpFeedback(null);
           o.setErpFeedbackStatus(null);
           o.setErpWarehousingStatus(null);
           o.setConversionQuantity(null);
           o.setConversionUnit(null);
           o.setConversionQuantityUnquanlified(null);
           o.setStatus(null);
           o.setRemark(null);
           o.setAttr1(null);
           o.setAttr2(null);
           o.setAttr3(null);
           o.setAttr4(null);
           o.setCreateTime(null);
       });
       feedbackWarehousingLogMapper.insert(dbFeedbackWarehousingLog);
       // 测试 feedbackCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setFeedbackCode(null)));
       // 测试 workstationId 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setWorkstationId(null)));
       // 测试 workstationCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setWorkstationCode(null)));
       // 测试 workstationName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setWorkstationName(null)));
       // 测试 workorderId 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setWorkorderId(null)));
       // 测试 workorderCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setWorkorderCode(null)));
       // 测试 workorderName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setWorkorderName(null)));
       // 测试 processId 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setProcessId(null)));
       // 测试 processCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setProcessCode(null)));
       // 测试 processName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setProcessName(null)));
       // 测试 taskId 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setTaskId(null)));
       // 测试 taskCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setTaskCode(null)));
       // 测试 itemId 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setItemId(null)));
       // 测试 itemCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setItemCode(null)));
       // 测试 itemName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setItemName(null)));
       // 测试 unitOfMeasure 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setUnitOfMeasure(null)));
       // 测试 specification 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setSpecification(null)));
       // 测试 quantityFeedback 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setQuantityFeedback(null)));
       // 测试 quantityQualified 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setQuantityQualified(null)));
       // 测试 quantityUnquanlified 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setQuantityUnquanlified(null)));
       // 测试 userName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setUserName(null)));
       // 测试 nickName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setNickName(null)));
       // 测试 batchCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setBatchCode(null)));
       // 测试 erpBatchCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setErpBatchCode(null)));
       // 测试 machineryName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setMachineryName(null)));
       // 测试 machineryCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setMachineryCode(null)));
       // 测试 machineryId 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setMachineryId(null)));
       // 测试 erpFeedback 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setErpFeedback(null)));
       // 测试 erpFeedbackStatus 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setErpFeedbackStatus(null)));
       // 测试 erpWarehousingStatus 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setErpWarehousingStatus(null)));
       // 测试 conversionQuantity 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setConversionQuantity(null)));
       // 测试 conversionUnit 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setConversionUnit(null)));
       // 测试 conversionQuantityUnquanlified 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setConversionQuantityUnquanlified(null)));
       // 测试 status 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setStatus(null)));
       // 测试 remark 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setRemark(null)));
       // 测试 attr1 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setAttr1(null)));
       // 测试 attr2 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setAttr2(null)));
       // 测试 attr3 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setAttr3(null)));
       // 测试 attr4 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setAttr4(null)));
       // 测试 createTime 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setCreateTime(null)));
       // 准备参数
       FeedbackWarehousingLogPageReqVO reqVO = new FeedbackWarehousingLogPageReqVO();
       reqVO.setFeedbackCode(null);
       reqVO.setWorkstationId(null);
       reqVO.setWorkstationCode(null);
       reqVO.setWorkstationName(null);
       reqVO.setWorkorderId(null);
       reqVO.setWorkorderCode(null);
       reqVO.setWorkorderName(null);
       reqVO.setProcessId(null);
       reqVO.setProcessCode(null);
       reqVO.setProcessName(null);
       reqVO.setTaskId(null);
       reqVO.setTaskCode(null);
       reqVO.setItemId(null);
       reqVO.setItemCode(null);
       reqVO.setItemName(null);
       reqVO.setUnitOfMeasure(null);
       reqVO.setSpecification(null);
       reqVO.setQuantityFeedback(null);
       reqVO.setQuantityQualified(null);
       reqVO.setQuantityUnquanlified(null);
       reqVO.setUserName(null);
       reqVO.setNickName(null);
       reqVO.setBatchCode(null);
       reqVO.setErpBatchCode(null);
       reqVO.setMachineryName(null);
       reqVO.setMachineryCode(null);
       reqVO.setMachineryId(null);
       reqVO.setErpFeedback(null);
       reqVO.setErpFeedbackStatus(null);
       reqVO.setErpWarehousingStatus(null);
       reqVO.setConversionQuantity(null);
       reqVO.setConversionUnit(null);
       reqVO.setConversionQuantityUnquanlified(null);
       reqVO.setStatus(null);
       reqVO.setRemark(null);
       reqVO.setAttr1(null);
       reqVO.setAttr2(null);
       reqVO.setAttr3(null);
       reqVO.setAttr4(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<FeedbackWarehousingLogDO> pageResult = feedbackWarehousingLogService.getFeedbackWarehousingLogPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbFeedbackWarehousingLog, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFeedbackWarehousingLogList() {
       // mock 数据
       FeedbackWarehousingLogDO dbFeedbackWarehousingLog = randomPojo(FeedbackWarehousingLogDO.class, o -> { // 等会查询到
           o.setFeedbackCode(null);
           o.setWorkstationId(null);
           o.setWorkstationCode(null);
           o.setWorkstationName(null);
           o.setWorkorderId(null);
           o.setWorkorderCode(null);
           o.setWorkorderName(null);
           o.setProcessId(null);
           o.setProcessCode(null);
           o.setProcessName(null);
           o.setTaskId(null);
           o.setTaskCode(null);
           o.setItemId(null);
           o.setItemCode(null);
           o.setItemName(null);
           o.setUnitOfMeasure(null);
           o.setSpecification(null);
           o.setQuantityFeedback(null);
           o.setQuantityQualified(null);
           o.setQuantityUnquanlified(null);
           o.setUserName(null);
           o.setNickName(null);
           o.setBatchCode(null);
           o.setErpBatchCode(null);
           o.setMachineryName(null);
           o.setMachineryCode(null);
           o.setMachineryId(null);
           o.setErpFeedback(null);
           o.setErpFeedbackStatus(null);
           o.setErpWarehousingStatus(null);
           o.setConversionQuantity(null);
           o.setConversionUnit(null);
           o.setConversionQuantityUnquanlified(null);
           o.setStatus(null);
           o.setRemark(null);
           o.setAttr1(null);
           o.setAttr2(null);
           o.setAttr3(null);
           o.setAttr4(null);
           o.setCreateTime(null);
       });
       feedbackWarehousingLogMapper.insert(dbFeedbackWarehousingLog);
       // 测试 feedbackCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setFeedbackCode(null)));
       // 测试 workstationId 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setWorkstationId(null)));
       // 测试 workstationCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setWorkstationCode(null)));
       // 测试 workstationName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setWorkstationName(null)));
       // 测试 workorderId 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setWorkorderId(null)));
       // 测试 workorderCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setWorkorderCode(null)));
       // 测试 workorderName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setWorkorderName(null)));
       // 测试 processId 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setProcessId(null)));
       // 测试 processCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setProcessCode(null)));
       // 测试 processName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setProcessName(null)));
       // 测试 taskId 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setTaskId(null)));
       // 测试 taskCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setTaskCode(null)));
       // 测试 itemId 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setItemId(null)));
       // 测试 itemCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setItemCode(null)));
       // 测试 itemName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setItemName(null)));
       // 测试 unitOfMeasure 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setUnitOfMeasure(null)));
       // 测试 specification 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setSpecification(null)));
       // 测试 quantityFeedback 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setQuantityFeedback(null)));
       // 测试 quantityQualified 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setQuantityQualified(null)));
       // 测试 quantityUnquanlified 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setQuantityUnquanlified(null)));
       // 测试 userName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setUserName(null)));
       // 测试 nickName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setNickName(null)));
       // 测试 batchCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setBatchCode(null)));
       // 测试 erpBatchCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setErpBatchCode(null)));
       // 测试 machineryName 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setMachineryName(null)));
       // 测试 machineryCode 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setMachineryCode(null)));
       // 测试 machineryId 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setMachineryId(null)));
       // 测试 erpFeedback 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setErpFeedback(null)));
       // 测试 erpFeedbackStatus 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setErpFeedbackStatus(null)));
       // 测试 erpWarehousingStatus 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setErpWarehousingStatus(null)));
       // 测试 conversionQuantity 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setConversionQuantity(null)));
       // 测试 conversionUnit 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setConversionUnit(null)));
       // 测试 conversionQuantityUnquanlified 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setConversionQuantityUnquanlified(null)));
       // 测试 status 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setStatus(null)));
       // 测试 remark 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setRemark(null)));
       // 测试 attr1 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setAttr1(null)));
       // 测试 attr2 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setAttr2(null)));
       // 测试 attr3 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setAttr3(null)));
       // 测试 attr4 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setAttr4(null)));
       // 测试 createTime 不匹配
       feedbackWarehousingLogMapper.insert(cloneIgnoreId(dbFeedbackWarehousingLog, o -> o.setCreateTime(null)));
       // 准备参数
       FeedbackWarehousingLogExportReqVO reqVO = new FeedbackWarehousingLogExportReqVO();
       reqVO.setFeedbackCode(null);
       reqVO.setWorkstationId(null);
       reqVO.setWorkstationCode(null);
       reqVO.setWorkstationName(null);
       reqVO.setWorkorderId(null);
       reqVO.setWorkorderCode(null);
       reqVO.setWorkorderName(null);
       reqVO.setProcessId(null);
       reqVO.setProcessCode(null);
       reqVO.setProcessName(null);
       reqVO.setTaskId(null);
       reqVO.setTaskCode(null);
       reqVO.setItemId(null);
       reqVO.setItemCode(null);
       reqVO.setItemName(null);
       reqVO.setUnitOfMeasure(null);
       reqVO.setSpecification(null);
       reqVO.setQuantityFeedback(null);
       reqVO.setQuantityQualified(null);
       reqVO.setQuantityUnquanlified(null);
       reqVO.setUserName(null);
       reqVO.setNickName(null);
       reqVO.setBatchCode(null);
       reqVO.setErpBatchCode(null);
       reqVO.setMachineryName(null);
       reqVO.setMachineryCode(null);
       reqVO.setMachineryId(null);
       reqVO.setErpFeedback(null);
       reqVO.setErpFeedbackStatus(null);
       reqVO.setErpWarehousingStatus(null);
       reqVO.setConversionQuantity(null);
       reqVO.setConversionUnit(null);
       reqVO.setConversionQuantityUnquanlified(null);
       reqVO.setStatus(null);
       reqVO.setRemark(null);
       reqVO.setAttr1(null);
       reqVO.setAttr2(null);
       reqVO.setAttr3(null);
       reqVO.setAttr4(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<FeedbackWarehousingLogDO> list = feedbackWarehousingLogService.getFeedbackWarehousingLogList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbFeedbackWarehousingLog, list.get(0));
    }

}
