package com.dofast.module.pro.service.feedbackauditdetail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.pro.controller.admin.feedbackauditdetail.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackauditdetail.FeedbackAuditDetailDO;
import com.dofast.module.pro.dal.mysql.feedbackauditdetail.FeedbackAuditDetailMapper;
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
 * {@link FeedbackAuditDetailServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(FeedbackAuditDetailServiceImpl.class)
public class FeedbackAuditDetailServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FeedbackAuditDetailServiceImpl feedbackAuditDetailService;

    @Resource
    private FeedbackAuditDetailMapper feedbackAuditDetailMapper;

    @Test
    public void testCreateFeedbackAuditDetail_success() {
        // 准备参数
        FeedbackAuditDetailCreateReqVO reqVO = randomPojo(FeedbackAuditDetailCreateReqVO.class);

        // 调用
        Long feedbackAuditDetailId = feedbackAuditDetailService.createFeedbackAuditDetail(reqVO);
        // 断言
        assertNotNull(feedbackAuditDetailId);
        // 校验记录的属性是否正确
        FeedbackAuditDetailDO feedbackAuditDetail = feedbackAuditDetailMapper.selectById(feedbackAuditDetailId);
        assertPojoEquals(reqVO, feedbackAuditDetail);
    }

    @Test
    public void testUpdateFeedbackAuditDetail_success() {
        // mock 数据
        FeedbackAuditDetailDO dbFeedbackAuditDetail = randomPojo(FeedbackAuditDetailDO.class);
        feedbackAuditDetailMapper.insert(dbFeedbackAuditDetail);// @Sql: 先插入出一条存在的数据
        // 准备参数
        FeedbackAuditDetailUpdateReqVO reqVO = randomPojo(FeedbackAuditDetailUpdateReqVO.class, o -> {
            o.setId(dbFeedbackAuditDetail.getId()); // 设置更新的 ID
        });

        // 调用
        feedbackAuditDetailService.updateFeedbackAuditDetail(reqVO);
        // 校验是否更新正确
        FeedbackAuditDetailDO feedbackAuditDetail = feedbackAuditDetailMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, feedbackAuditDetail);
    }

    @Test
    public void testUpdateFeedbackAuditDetail_notExists() {
        // 准备参数
        FeedbackAuditDetailUpdateReqVO reqVO = randomPojo(FeedbackAuditDetailUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> feedbackAuditDetailService.updateFeedbackAuditDetail(reqVO), FEEDBACK_AUDIT_DETAIL_NOT_EXISTS);
    }

    @Test
    public void testDeleteFeedbackAuditDetail_success() {
        // mock 数据
        FeedbackAuditDetailDO dbFeedbackAuditDetail = randomPojo(FeedbackAuditDetailDO.class);
        feedbackAuditDetailMapper.insert(dbFeedbackAuditDetail);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFeedbackAuditDetail.getId();

        // 调用
        feedbackAuditDetailService.deleteFeedbackAuditDetail(id);
       // 校验数据不存在了
       assertNull(feedbackAuditDetailMapper.selectById(id));
    }

    @Test
    public void testDeleteFeedbackAuditDetail_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> feedbackAuditDetailService.deleteFeedbackAuditDetail(id), FEEDBACK_AUDIT_DETAIL_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFeedbackAuditDetailPage() {
       // mock 数据
       FeedbackAuditDetailDO dbFeedbackAuditDetail = randomPojo(FeedbackAuditDetailDO.class, o -> { // 等会查询到
           o.setAuditId(null);
           o.setFeedbackId(null);
           o.setFeedbackType(null);
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
           o.setQuantity(null);
           o.setQuantityFeedback(null);
           o.setQuantityQualified(null);
           o.setQuantityUnquanlified(null);
           o.setUserName(null);
           o.setNickName(null);
           o.setFeedbackChannel(null);
           o.setFeedbackTime(null);
           o.setRecordUser(null);
           o.setRecordNick(null);
           o.setBatchCode(null);
           o.setErpBatchCode(null);
           o.setTeamCode(null);
           o.setPrincipalName(null);
           o.setPrincipalId(null);
           o.setShiftInfo(null);
           o.setOriginCode(null);
           o.setMachineryName(null);
           o.setMachineryCode(null);
           o.setMachineryId(null);
           o.setErpFeedbackStatus(null);
           o.setErpWarehousingStatus(null);
           o.setMergeStatus(null);
           o.setConversionQuantity(null);
           o.setConversionUnit(null);
           o.setConversionQuantityUnquanlified(null);
           o.setCreateTime(null);
       });
       feedbackAuditDetailMapper.insert(dbFeedbackAuditDetail);
       // 测试 auditId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setAuditId(null)));
       // 测试 feedbackId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setFeedbackId(null)));
       // 测试 feedbackType 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setFeedbackType(null)));
       // 测试 feedbackCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setFeedbackCode(null)));
       // 测试 workstationId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setWorkstationId(null)));
       // 测试 workstationCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setWorkstationCode(null)));
       // 测试 workstationName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setWorkstationName(null)));
       // 测试 workorderId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setWorkorderId(null)));
       // 测试 workorderCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setWorkorderCode(null)));
       // 测试 workorderName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setWorkorderName(null)));
       // 测试 processId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setProcessId(null)));
       // 测试 processCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setProcessCode(null)));
       // 测试 processName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setProcessName(null)));
       // 测试 taskId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setTaskId(null)));
       // 测试 taskCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setTaskCode(null)));
       // 测试 itemId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setItemId(null)));
       // 测试 itemCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setItemCode(null)));
       // 测试 itemName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setItemName(null)));
       // 测试 unitOfMeasure 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setUnitOfMeasure(null)));
       // 测试 specification 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setSpecification(null)));
       // 测试 quantity 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setQuantity(null)));
       // 测试 quantityFeedback 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setQuantityFeedback(null)));
       // 测试 quantityQualified 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setQuantityQualified(null)));
       // 测试 quantityUnquanlified 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setQuantityUnquanlified(null)));
       // 测试 userName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setUserName(null)));
       // 测试 nickName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setNickName(null)));
       // 测试 feedbackChannel 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setFeedbackChannel(null)));
       // 测试 feedbackTime 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setFeedbackTime(null)));
       // 测试 recordUser 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setRecordUser(null)));
       // 测试 recordNick 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setRecordNick(null)));
       // 测试 batchCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setBatchCode(null)));
       // 测试 erpBatchCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setErpBatchCode(null)));
       // 测试 teamCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setTeamCode(null)));
       // 测试 principalName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setPrincipalName(null)));
       // 测试 principalId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setPrincipalId(null)));
       // 测试 shiftInfo 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setShiftInfo(null)));
       // 测试 originCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setOriginCode(null)));
       // 测试 machineryName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setMachineryName(null)));
       // 测试 machineryCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setMachineryCode(null)));
       // 测试 machineryId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setMachineryId(null)));
       // 测试 erpFeedbackStatus 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setErpFeedbackStatus(null)));
       // 测试 erpWarehousingStatus 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setErpWarehousingStatus(null)));
       // 测试 mergeStatus 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setMergeStatus(null)));
       // 测试 conversionQuantity 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setConversionQuantity(null)));
       // 测试 conversionUnit 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setConversionUnit(null)));
       // 测试 conversionQuantityUnquanlified 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setConversionQuantityUnquanlified(null)));
       // 测试 createTime 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setCreateTime(null)));
       // 准备参数
       FeedbackAuditDetailPageReqVO reqVO = new FeedbackAuditDetailPageReqVO();
       reqVO.setAuditId(null);
       reqVO.setFeedbackId(null);
       reqVO.setFeedbackType(null);
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
       reqVO.setQuantity(null);
       reqVO.setQuantityFeedback(null);
       reqVO.setQuantityQualified(null);
       reqVO.setQuantityUnquanlified(null);
       reqVO.setUserName(null);
       reqVO.setNickName(null);
       reqVO.setFeedbackChannel(null);
       reqVO.setFeedbackTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRecordUser(null);
       reqVO.setRecordNick(null);
       reqVO.setBatchCode(null);
       reqVO.setErpBatchCode(null);
       reqVO.setTeamCode(null);
       reqVO.setPrincipalName(null);
       reqVO.setPrincipalId(null);
       reqVO.setShiftInfo(null);
       reqVO.setOriginCode(null);
       reqVO.setMachineryName(null);
       reqVO.setMachineryCode(null);
       reqVO.setMachineryId(null);
       reqVO.setErpFeedbackStatus(null);
       reqVO.setErpWarehousingStatus(null);
       reqVO.setMergeStatus(null);
       reqVO.setConversionQuantity(null);
       reqVO.setConversionUnit(null);
       reqVO.setConversionQuantityUnquanlified(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<FeedbackAuditDetailDO> pageResult = feedbackAuditDetailService.getFeedbackAuditDetailPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbFeedbackAuditDetail, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFeedbackAuditDetailList() {
       // mock 数据
       FeedbackAuditDetailDO dbFeedbackAuditDetail = randomPojo(FeedbackAuditDetailDO.class, o -> { // 等会查询到
           o.setAuditId(null);
           o.setFeedbackId(null);
           o.setFeedbackType(null);
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
           o.setQuantity(null);
           o.setQuantityFeedback(null);
           o.setQuantityQualified(null);
           o.setQuantityUnquanlified(null);
           o.setUserName(null);
           o.setNickName(null);
           o.setFeedbackChannel(null);
           o.setFeedbackTime(null);
           o.setRecordUser(null);
           o.setRecordNick(null);
           o.setBatchCode(null);
           o.setErpBatchCode(null);
           o.setTeamCode(null);
           o.setPrincipalName(null);
           o.setPrincipalId(null);
           o.setShiftInfo(null);
           o.setOriginCode(null);
           o.setMachineryName(null);
           o.setMachineryCode(null);
           o.setMachineryId(null);
           o.setErpFeedbackStatus(null);
           o.setErpWarehousingStatus(null);
           o.setMergeStatus(null);
           o.setConversionQuantity(null);
           o.setConversionUnit(null);
           o.setConversionQuantityUnquanlified(null);
           o.setCreateTime(null);
       });
       feedbackAuditDetailMapper.insert(dbFeedbackAuditDetail);
       // 测试 auditId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setAuditId(null)));
       // 测试 feedbackId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setFeedbackId(null)));
       // 测试 feedbackType 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setFeedbackType(null)));
       // 测试 feedbackCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setFeedbackCode(null)));
       // 测试 workstationId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setWorkstationId(null)));
       // 测试 workstationCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setWorkstationCode(null)));
       // 测试 workstationName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setWorkstationName(null)));
       // 测试 workorderId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setWorkorderId(null)));
       // 测试 workorderCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setWorkorderCode(null)));
       // 测试 workorderName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setWorkorderName(null)));
       // 测试 processId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setProcessId(null)));
       // 测试 processCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setProcessCode(null)));
       // 测试 processName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setProcessName(null)));
       // 测试 taskId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setTaskId(null)));
       // 测试 taskCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setTaskCode(null)));
       // 测试 itemId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setItemId(null)));
       // 测试 itemCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setItemCode(null)));
       // 测试 itemName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setItemName(null)));
       // 测试 unitOfMeasure 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setUnitOfMeasure(null)));
       // 测试 specification 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setSpecification(null)));
       // 测试 quantity 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setQuantity(null)));
       // 测试 quantityFeedback 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setQuantityFeedback(null)));
       // 测试 quantityQualified 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setQuantityQualified(null)));
       // 测试 quantityUnquanlified 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setQuantityUnquanlified(null)));
       // 测试 userName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setUserName(null)));
       // 测试 nickName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setNickName(null)));
       // 测试 feedbackChannel 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setFeedbackChannel(null)));
       // 测试 feedbackTime 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setFeedbackTime(null)));
       // 测试 recordUser 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setRecordUser(null)));
       // 测试 recordNick 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setRecordNick(null)));
       // 测试 batchCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setBatchCode(null)));
       // 测试 erpBatchCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setErpBatchCode(null)));
       // 测试 teamCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setTeamCode(null)));
       // 测试 principalName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setPrincipalName(null)));
       // 测试 principalId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setPrincipalId(null)));
       // 测试 shiftInfo 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setShiftInfo(null)));
       // 测试 originCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setOriginCode(null)));
       // 测试 machineryName 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setMachineryName(null)));
       // 测试 machineryCode 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setMachineryCode(null)));
       // 测试 machineryId 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setMachineryId(null)));
       // 测试 erpFeedbackStatus 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setErpFeedbackStatus(null)));
       // 测试 erpWarehousingStatus 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setErpWarehousingStatus(null)));
       // 测试 mergeStatus 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setMergeStatus(null)));
       // 测试 conversionQuantity 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setConversionQuantity(null)));
       // 测试 conversionUnit 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setConversionUnit(null)));
       // 测试 conversionQuantityUnquanlified 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setConversionQuantityUnquanlified(null)));
       // 测试 createTime 不匹配
       feedbackAuditDetailMapper.insert(cloneIgnoreId(dbFeedbackAuditDetail, o -> o.setCreateTime(null)));
       // 准备参数
       FeedbackAuditDetailExportReqVO reqVO = new FeedbackAuditDetailExportReqVO();
       reqVO.setAuditId(null);
       reqVO.setFeedbackId(null);
       reqVO.setFeedbackType(null);
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
       reqVO.setQuantity(null);
       reqVO.setQuantityFeedback(null);
       reqVO.setQuantityQualified(null);
       reqVO.setQuantityUnquanlified(null);
       reqVO.setUserName(null);
       reqVO.setNickName(null);
       reqVO.setFeedbackChannel(null);
       reqVO.setFeedbackTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRecordUser(null);
       reqVO.setRecordNick(null);
       reqVO.setBatchCode(null);
       reqVO.setErpBatchCode(null);
       reqVO.setTeamCode(null);
       reqVO.setPrincipalName(null);
       reqVO.setPrincipalId(null);
       reqVO.setShiftInfo(null);
       reqVO.setOriginCode(null);
       reqVO.setMachineryName(null);
       reqVO.setMachineryCode(null);
       reqVO.setMachineryId(null);
       reqVO.setErpFeedbackStatus(null);
       reqVO.setErpWarehousingStatus(null);
       reqVO.setMergeStatus(null);
       reqVO.setConversionQuantity(null);
       reqVO.setConversionUnit(null);
       reqVO.setConversionQuantityUnquanlified(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<FeedbackAuditDetailDO> list = feedbackAuditDetailService.getFeedbackAuditDetailList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbFeedbackAuditDetail, list.get(0));
    }

}
