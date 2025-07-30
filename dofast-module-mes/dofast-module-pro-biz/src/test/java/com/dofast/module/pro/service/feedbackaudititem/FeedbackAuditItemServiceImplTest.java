package com.dofast.module.pro.service.feedbackaudititem;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.pro.controller.admin.feedbackaudititem.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackaudititem.FeedbackAuditItemDO;
import com.dofast.module.pro.dal.mysql.feedbackaudititem.FeedbackAuditItemMapper;
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
 * {@link FeedbackAuditItemServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(FeedbackAuditItemServiceImpl.class)
public class FeedbackAuditItemServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FeedbackAuditItemServiceImpl feedbackAuditItemService;

    @Resource
    private FeedbackAuditItemMapper feedbackAuditItemMapper;

    @Test
    public void testCreateFeedbackAuditItem_success() {
        // 准备参数
        FeedbackAuditItemCreateReqVO reqVO = randomPojo(FeedbackAuditItemCreateReqVO.class);

        // 调用
        Long feedbackAuditItemId = feedbackAuditItemService.createFeedbackAuditItem(reqVO);
        // 断言
        assertNotNull(feedbackAuditItemId);
        // 校验记录的属性是否正确
        FeedbackAuditItemDO feedbackAuditItem = feedbackAuditItemMapper.selectById(feedbackAuditItemId);
        assertPojoEquals(reqVO, feedbackAuditItem);
    }

    @Test
    public void testUpdateFeedbackAuditItem_success() {
        // mock 数据
        FeedbackAuditItemDO dbFeedbackAuditItem = randomPojo(FeedbackAuditItemDO.class);
        feedbackAuditItemMapper.insert(dbFeedbackAuditItem);// @Sql: 先插入出一条存在的数据
        // 准备参数
        FeedbackAuditItemUpdateReqVO reqVO = randomPojo(FeedbackAuditItemUpdateReqVO.class, o -> {
            o.setId(dbFeedbackAuditItem.getId()); // 设置更新的 ID
        });

        // 调用
        feedbackAuditItemService.updateFeedbackAuditItem(reqVO);
        // 校验是否更新正确
        FeedbackAuditItemDO feedbackAuditItem = feedbackAuditItemMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, feedbackAuditItem);
    }

    @Test
    public void testUpdateFeedbackAuditItem_notExists() {
        // 准备参数
        FeedbackAuditItemUpdateReqVO reqVO = randomPojo(FeedbackAuditItemUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> feedbackAuditItemService.updateFeedbackAuditItem(reqVO), FEEDBACK_AUDIT_ITEM_NOT_EXISTS);
    }

    @Test
    public void testDeleteFeedbackAuditItem_success() {
        // mock 数据
        FeedbackAuditItemDO dbFeedbackAuditItem = randomPojo(FeedbackAuditItemDO.class);
        feedbackAuditItemMapper.insert(dbFeedbackAuditItem);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFeedbackAuditItem.getId();

        // 调用
        feedbackAuditItemService.deleteFeedbackAuditItem(id);
       // 校验数据不存在了
       assertNull(feedbackAuditItemMapper.selectById(id));
    }

    @Test
    public void testDeleteFeedbackAuditItem_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> feedbackAuditItemService.deleteFeedbackAuditItem(id), FEEDBACK_AUDIT_ITEM_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFeedbackAuditItemPage() {
       // mock 数据
       FeedbackAuditItemDO dbFeedbackAuditItem = randomPojo(FeedbackAuditItemDO.class, o -> { // 等会查询到
           o.setAuditId(null);
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
           o.setSumQuantityFeedback(null);
           o.setSumQuantityQualified(null);
           o.setSumQuantityUnquanlified(null);
           o.setUserName(null);
           o.setNickName(null);
           o.setRemark(null);
           o.setCreateTime(null);
           o.setAllPrincipal(null);
       });
       feedbackAuditItemMapper.insert(dbFeedbackAuditItem);
       // 测试 auditId 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setAuditId(null)));
       // 测试 workorderId 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setWorkorderId(null)));
       // 测试 workorderCode 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setWorkorderCode(null)));
       // 测试 workorderName 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setWorkorderName(null)));
       // 测试 processId 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setProcessId(null)));
       // 测试 processCode 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setProcessCode(null)));
       // 测试 processName 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setProcessName(null)));
       // 测试 taskId 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setTaskId(null)));
       // 测试 taskCode 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setTaskCode(null)));
       // 测试 itemId 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setItemId(null)));
       // 测试 itemCode 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setItemCode(null)));
       // 测试 itemName 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setItemName(null)));
       // 测试 unitOfMeasure 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setUnitOfMeasure(null)));
       // 测试 specification 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setSpecification(null)));
       // 测试 sumQuantityFeedback 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setSumQuantityFeedback(null)));
       // 测试 sumQuantityQualified 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setSumQuantityQualified(null)));
       // 测试 sumQuantityUnquanlified 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setSumQuantityUnquanlified(null)));
       // 测试 userName 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setUserName(null)));
       // 测试 nickName 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setNickName(null)));
       // 测试 remark 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setCreateTime(null)));
       // 测试 allPrincipal 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setAllPrincipal(null)));
       // 准备参数
       FeedbackAuditItemPageReqVO reqVO = new FeedbackAuditItemPageReqVO();
       reqVO.setAuditId(null);
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
       reqVO.setSumQuantityFeedback(null);
       reqVO.setSumQuantityQualified(null);
       reqVO.setSumQuantityUnquanlified(null);
       reqVO.setUserName(null);
       reqVO.setNickName(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setAllPrincipal(null);

       // 调用
       PageResult<FeedbackAuditItemDO> pageResult = feedbackAuditItemService.getFeedbackAuditItemPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbFeedbackAuditItem, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFeedbackAuditItemList() {
       // mock 数据
       FeedbackAuditItemDO dbFeedbackAuditItem = randomPojo(FeedbackAuditItemDO.class, o -> { // 等会查询到
           o.setAuditId(null);
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
           o.setSumQuantityFeedback(null);
           o.setSumQuantityQualified(null);
           o.setSumQuantityUnquanlified(null);
           o.setUserName(null);
           o.setNickName(null);
           o.setRemark(null);
           o.setCreateTime(null);
           o.setAllPrincipal(null);
       });
       feedbackAuditItemMapper.insert(dbFeedbackAuditItem);
       // 测试 auditId 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setAuditId(null)));
       // 测试 workorderId 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setWorkorderId(null)));
       // 测试 workorderCode 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setWorkorderCode(null)));
       // 测试 workorderName 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setWorkorderName(null)));
       // 测试 processId 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setProcessId(null)));
       // 测试 processCode 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setProcessCode(null)));
       // 测试 processName 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setProcessName(null)));
       // 测试 taskId 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setTaskId(null)));
       // 测试 taskCode 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setTaskCode(null)));
       // 测试 itemId 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setItemId(null)));
       // 测试 itemCode 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setItemCode(null)));
       // 测试 itemName 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setItemName(null)));
       // 测试 unitOfMeasure 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setUnitOfMeasure(null)));
       // 测试 specification 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setSpecification(null)));
       // 测试 sumQuantityFeedback 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setSumQuantityFeedback(null)));
       // 测试 sumQuantityQualified 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setSumQuantityQualified(null)));
       // 测试 sumQuantityUnquanlified 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setSumQuantityUnquanlified(null)));
       // 测试 userName 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setUserName(null)));
       // 测试 nickName 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setNickName(null)));
       // 测试 remark 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setCreateTime(null)));
       // 测试 allPrincipal 不匹配
       feedbackAuditItemMapper.insert(cloneIgnoreId(dbFeedbackAuditItem, o -> o.setAllPrincipal(null)));
       // 准备参数
       FeedbackAuditItemExportReqVO reqVO = new FeedbackAuditItemExportReqVO();
       reqVO.setAuditId(null);
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
       reqVO.setSumQuantityFeedback(null);
       reqVO.setSumQuantityQualified(null);
       reqVO.setSumQuantityUnquanlified(null);
       reqVO.setUserName(null);
       reqVO.setNickName(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setAllPrincipal(null);

       // 调用
       List<FeedbackAuditItemDO> list = feedbackAuditItemService.getFeedbackAuditItemList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbFeedbackAuditItem, list.get(0));
    }

}
