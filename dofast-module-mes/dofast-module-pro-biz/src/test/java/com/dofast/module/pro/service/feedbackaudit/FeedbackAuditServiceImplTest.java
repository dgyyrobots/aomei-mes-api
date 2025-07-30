package com.dofast.module.pro.service.feedbackaudit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.pro.controller.admin.feedbackaudit.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackaudit.FeedbackAuditDO;
import com.dofast.module.pro.dal.mysql.feedbackaudit.FeedbackAuditMapper;
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
 * {@link FeedbackAuditServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(FeedbackAuditServiceImpl.class)
public class FeedbackAuditServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FeedbackAuditServiceImpl feedbackAuditService;

    @Resource
    private FeedbackAuditMapper feedbackAuditMapper;

    @Test
    public void testCreateFeedbackAudit_success() {
        // 准备参数
        FeedbackAuditCreateReqVO reqVO = randomPojo(FeedbackAuditCreateReqVO.class);

        // 调用
        Long feedbackAuditId = feedbackAuditService.createFeedbackAudit(reqVO);
        // 断言
        assertNotNull(feedbackAuditId);
        // 校验记录的属性是否正确
        FeedbackAuditDO feedbackAudit = feedbackAuditMapper.selectById(feedbackAuditId);
        assertPojoEquals(reqVO, feedbackAudit);
    }

    @Test
    public void testUpdateFeedbackAudit_success() {
        // mock 数据
        FeedbackAuditDO dbFeedbackAudit = randomPojo(FeedbackAuditDO.class);
        feedbackAuditMapper.insert(dbFeedbackAudit);// @Sql: 先插入出一条存在的数据
        // 准备参数
        FeedbackAuditUpdateReqVO reqVO = randomPojo(FeedbackAuditUpdateReqVO.class, o -> {
            o.setId(dbFeedbackAudit.getId()); // 设置更新的 ID
        });

        // 调用
        feedbackAuditService.updateFeedbackAudit(reqVO);
        // 校验是否更新正确
        FeedbackAuditDO feedbackAudit = feedbackAuditMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, feedbackAudit);
    }

    @Test
    public void testUpdateFeedbackAudit_notExists() {
        // 准备参数
        FeedbackAuditUpdateReqVO reqVO = randomPojo(FeedbackAuditUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> feedbackAuditService.updateFeedbackAudit(reqVO), FEEDBACK_AUDIT_NOT_EXISTS);
    }

    @Test
    public void testDeleteFeedbackAudit_success() {
        // mock 数据
        FeedbackAuditDO dbFeedbackAudit = randomPojo(FeedbackAuditDO.class);
        feedbackAuditMapper.insert(dbFeedbackAudit);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFeedbackAudit.getId();

        // 调用
        feedbackAuditService.deleteFeedbackAudit(id);
       // 校验数据不存在了
       assertNull(feedbackAuditMapper.selectById(id));
    }

    @Test
    public void testDeleteFeedbackAudit_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> feedbackAuditService.deleteFeedbackAudit(id), FEEDBACK_AUDIT_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFeedbackAuditPage() {
       // mock 数据
       FeedbackAuditDO dbFeedbackAudit = randomPojo(FeedbackAuditDO.class, o -> { // 等会查询到
           o.setAuditCode(null);
           o.setSubmitUserId(null);
           o.setSubmitUserName(null);
           o.setSubmitTime(null);
           o.setAuditUserId(null);
           o.setAuditUserName(null);
           o.setAuditTime(null);
           o.setStatus(null);
           o.setRemark(null);
           o.setCreateTime(null);
       });
       feedbackAuditMapper.insert(dbFeedbackAudit);
       // 测试 auditCode 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setAuditCode(null)));
       // 测试 submitUserId 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setSubmitUserId(null)));
       // 测试 submitUserName 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setSubmitUserName(null)));
       // 测试 submitTime 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setSubmitTime(null)));
       // 测试 auditUserId 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setAuditUserId(null)));
       // 测试 auditUserName 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setAuditUserName(null)));
       // 测试 auditTime 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setAuditTime(null)));
       // 测试 status 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setStatus(null)));
       // 测试 remark 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setCreateTime(null)));
       // 准备参数
       FeedbackAuditPageReqVO reqVO = new FeedbackAuditPageReqVO();
       reqVO.setAuditCode(null);
       reqVO.setSubmitUserId(null);
       reqVO.setSubmitUserName(null);
       reqVO.setSubmitTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setAuditUserId(null);
       reqVO.setAuditUserName(null);
       reqVO.setAuditTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setStatus(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<FeedbackAuditDO> pageResult = feedbackAuditService.getFeedbackAuditPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbFeedbackAudit, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFeedbackAuditList() {
       // mock 数据
       FeedbackAuditDO dbFeedbackAudit = randomPojo(FeedbackAuditDO.class, o -> { // 等会查询到
           o.setAuditCode(null);
           o.setSubmitUserId(null);
           o.setSubmitUserName(null);
           o.setSubmitTime(null);
           o.setAuditUserId(null);
           o.setAuditUserName(null);
           o.setAuditTime(null);
           o.setStatus(null);
           o.setRemark(null);
           o.setCreateTime(null);
       });
       feedbackAuditMapper.insert(dbFeedbackAudit);
       // 测试 auditCode 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setAuditCode(null)));
       // 测试 submitUserId 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setSubmitUserId(null)));
       // 测试 submitUserName 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setSubmitUserName(null)));
       // 测试 submitTime 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setSubmitTime(null)));
       // 测试 auditUserId 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setAuditUserId(null)));
       // 测试 auditUserName 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setAuditUserName(null)));
       // 测试 auditTime 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setAuditTime(null)));
       // 测试 status 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setStatus(null)));
       // 测试 remark 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       feedbackAuditMapper.insert(cloneIgnoreId(dbFeedbackAudit, o -> o.setCreateTime(null)));
       // 准备参数
       FeedbackAuditExportReqVO reqVO = new FeedbackAuditExportReqVO();
       reqVO.setAuditCode(null);
       reqVO.setSubmitUserId(null);
       reqVO.setSubmitUserName(null);
       reqVO.setSubmitTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setAuditUserId(null);
       reqVO.setAuditUserName(null);
       reqVO.setAuditTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setStatus(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<FeedbackAuditDO> list = feedbackAuditService.getFeedbackAuditList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbFeedbackAudit, list.get(0));
    }

}
