package com.dofast.module.mes.service.exception;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.mes.controller.admin.exception.vo.*;
import com.dofast.module.mes.dal.dataobject.exception.ExceptionDO;
import com.dofast.module.mes.dal.mysql.exception.ExceptionMapper;
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
 * {@link ExceptionServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(ExceptionServiceImpl.class)
public class ExceptionServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ExceptionServiceImpl exceptionService;

    @Resource
    private ExceptionMapper exceptionMapper;

    @Test
    public void testCreateException_success() {
        // 准备参数
        ExceptionCreateReqVO reqVO = randomPojo(ExceptionCreateReqVO.class);

        // 调用
        Long exceptionId = exceptionService.createException(reqVO);
        // 断言
        assertNotNull(exceptionId);
        // 校验记录的属性是否正确
        ExceptionDO exception = exceptionMapper.selectById(exceptionId);
        assertPojoEquals(reqVO, exception);
    }

    @Test
    public void testUpdateException_success() {
        // mock 数据
        ExceptionDO dbException = randomPojo(ExceptionDO.class);
        exceptionMapper.insert(dbException);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ExceptionUpdateReqVO reqVO = randomPojo(ExceptionUpdateReqVO.class, o -> {
            o.setId(dbException.getId()); // 设置更新的 ID
        });

        // 调用
        exceptionService.updateException(reqVO);
        // 校验是否更新正确
        ExceptionDO exception = exceptionMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, exception);
    }

    @Test
    public void testUpdateException_notExists() {
        // 准备参数
        ExceptionUpdateReqVO reqVO = randomPojo(ExceptionUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> exceptionService.updateException(reqVO), EXCEPTION_NOT_EXISTS);
    }

    @Test
    public void testDeleteException_success() {
        // mock 数据
        ExceptionDO dbException = randomPojo(ExceptionDO.class);
        exceptionMapper.insert(dbException);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbException.getId();

        // 调用
        exceptionService.deleteException(id);
        // 校验数据不存在了
        assertNull(exceptionMapper.selectById(id));
    }

    @Test
    public void testDeleteException_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> exceptionService.deleteException(id), EXCEPTION_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetExceptionPage() {
        // mock 数据
        ExceptionDO dbException = randomPojo(ExceptionDO.class, o -> { // 等会查询到
            o.setExceptionCode(null);
            o.setExceptionType(null);
            o.setSubclassExceptionCode(null);
            o.setSubclassExceptionName(null);
            o.setExceptionLevelCode(null);
            o.setExceptionLevelName(null);
            o.setExceptionLevelColor(null);
            o.setTitle(null);
            o.setDescription(null);
            o.setRegisterTime(null);
            o.setRegisterUser(null);
            o.setWorkshopId(null);
            o.setWorkshopCode(null);
            o.setWorkshopName(null);
            o.setProcessId(null);
            o.setProcessName(null);
            o.setProcessCode(null);
            o.setRelatedMachineryCode(null);
            o.setRelatedMachineryName(null);
            o.setRelatedErpMachineryCode(null);
            o.setRelatedMaterial(null);
            o.setRelatedWorkorder(null);
            o.setStatus(null);
            o.setEstimatedImpact(null);
            o.setSolution(null);
            o.setCloseTime(null);
            o.setAttachments(null);
            o.setRemark(null);
            o.setAttr1(null);
            o.setAttr2(null);
            o.setAttr3(null);
            o.setAttr4(null);
            o.setCreateTime(null);
        });
        exceptionMapper.insert(dbException);
        // 测试 exceptionCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setExceptionCode(null)));
        // 测试 exceptionType 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setExceptionType(null)));
        // 测试 subclassExceptionCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setSubclassExceptionCode(null)));
        // 测试 subclassExceptionName 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setSubclassExceptionName(null)));
        // 测试 exceptionLevelCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setExceptionLevelCode(null)));
        // 测试 exceptionLevelName 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setExceptionLevelName(null)));
        // 测试 exceptionLevelColor 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setExceptionLevelColor(null)));
        // 测试 title 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setTitle(null)));
        // 测试 description 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setDescription(null)));
        // 测试 registerTime 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRegisterTime(null)));
        // 测试 registerUser 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRegisterUser(null)));
        // 测试 workshopId 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setWorkshopId(null)));
        // 测试 workshopCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setWorkshopCode(null)));
        // 测试 workshopName 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setWorkshopName(null)));
        // 测试 processId 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setProcessId(null)));
        // 测试 processName 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setProcessName(null)));
        // 测试 processCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setProcessCode(null)));
        // 测试 relatedMachineryCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRelatedMachineryCode(null)));
        // 测试 relatedMachineryName 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRelatedMachineryName(null)));
        // 测试 relatedErpMachineryCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRelatedErpMachineryCode(null)));
        // 测试 relatedMaterial 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRelatedMaterial(null)));
        // 测试 relatedWorkorder 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRelatedWorkorder(null)));
        // 测试 status 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setStatus(null)));
        // 测试 estimatedImpact 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setEstimatedImpact(null)));
        // 测试 solution 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setSolution(null)));
        // 测试 closeTime 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setCloseTime(null)));
        // 测试 attachments 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setAttachments(null)));
        // 测试 remark 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRemark(null)));
        // 测试 attr1 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setAttr1(null)));
        // 测试 attr2 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setAttr2(null)));
        // 测试 attr3 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setAttr3(null)));
        // 测试 attr4 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setAttr4(null)));
        // 测试 createTime 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setCreateTime(null)));
        // 准备参数
        ExceptionPageReqVO reqVO = new ExceptionPageReqVO();
        reqVO.setExceptionCode(null);
        reqVO.setExceptionType(null);
        reqVO.setSubclassExceptionCode(null);
        reqVO.setSubclassExceptionName(null);
        reqVO.setExceptionLevelCode(null);
        reqVO.setExceptionLevelName(null);
        reqVO.setExceptionLevelColor(null);
        reqVO.setTitle(null);
        reqVO.setDescription(null);
        reqVO.setRegisterTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
        reqVO.setRegisterUser(null);
        reqVO.setWorkshopId(null);
        reqVO.setWorkshopCode(null);
        reqVO.setWorkshopName(null);
        reqVO.setProcessId(null);
        reqVO.setProcessName(null);
        reqVO.setProcessCode(null);
        reqVO.setRelatedMachineryCode(null);
        reqVO.setRelatedMachineryName(null);
        reqVO.setRelatedErpMachineryCode(null);
        reqVO.setRelatedMaterial(null);
        reqVO.setRelatedWorkorder(null);
        reqVO.setStatus(null);
        reqVO.setEstimatedImpact(null);
        reqVO.setSolution(null);
        reqVO.setCloseTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
        reqVO.setAttachments(null);
        reqVO.setRemark(null);
        reqVO.setAttr1(null);
        reqVO.setAttr2(null);
        reqVO.setAttr3(null);
        reqVO.setAttr4(null);
        reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

        // 调用
        PageResult<ExceptionDO> pageResult = exceptionService.getExceptionPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbException, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetExceptionList() {
        // mock 数据
        ExceptionDO dbException = randomPojo(ExceptionDO.class, o -> { // 等会查询到
            o.setExceptionCode(null);
            o.setExceptionType(null);
            o.setSubclassExceptionCode(null);
            o.setSubclassExceptionName(null);
            o.setExceptionLevelCode(null);
            o.setExceptionLevelName(null);
            o.setExceptionLevelColor(null);
            o.setTitle(null);
            o.setDescription(null);
            o.setRegisterTime(null);
            o.setRegisterUser(null);
            o.setWorkshopId(null);
            o.setWorkshopCode(null);
            o.setWorkshopName(null);
            o.setProcessId(null);
            o.setProcessName(null);
            o.setProcessCode(null);
            o.setRelatedMachineryCode(null);
            o.setRelatedMachineryName(null);
            o.setRelatedErpMachineryCode(null);
            o.setRelatedMaterial(null);
            o.setRelatedWorkorder(null);
            o.setStatus(null);
            o.setEstimatedImpact(null);
            o.setSolution(null);
            o.setCloseTime(null);
            o.setAttachments(null);
            o.setRemark(null);
            o.setAttr1(null);
            o.setAttr2(null);
            o.setAttr3(null);
            o.setAttr4(null);
            o.setCreateTime(null);
        });
        exceptionMapper.insert(dbException);
        // 测试 exceptionCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setExceptionCode(null)));
        // 测试 exceptionType 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setExceptionType(null)));
        // 测试 subclassExceptionCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setSubclassExceptionCode(null)));
        // 测试 subclassExceptionName 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setSubclassExceptionName(null)));
        // 测试 exceptionLevelCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setExceptionLevelCode(null)));
        // 测试 exceptionLevelName 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setExceptionLevelName(null)));
        // 测试 exceptionLevelColor 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setExceptionLevelColor(null)));
        // 测试 title 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setTitle(null)));
        // 测试 description 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setDescription(null)));
        // 测试 registerTime 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRegisterTime(null)));
        // 测试 registerUser 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRegisterUser(null)));
        // 测试 workshopId 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setWorkshopId(null)));
        // 测试 workshopCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setWorkshopCode(null)));
        // 测试 workshopName 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setWorkshopName(null)));
        // 测试 processId 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setProcessId(null)));
        // 测试 processName 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setProcessName(null)));
        // 测试 processCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setProcessCode(null)));
        // 测试 relatedMachineryCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRelatedMachineryCode(null)));
        // 测试 relatedMachineryName 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRelatedMachineryName(null)));
        // 测试 relatedErpMachineryCode 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRelatedErpMachineryCode(null)));
        // 测试 relatedMaterial 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRelatedMaterial(null)));
        // 测试 relatedWorkorder 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRelatedWorkorder(null)));
        // 测试 status 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setStatus(null)));
        // 测试 estimatedImpact 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setEstimatedImpact(null)));
        // 测试 solution 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setSolution(null)));
        // 测试 closeTime 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setCloseTime(null)));
        // 测试 attachments 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setAttachments(null)));
        // 测试 remark 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setRemark(null)));
        // 测试 attr1 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setAttr1(null)));
        // 测试 attr2 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setAttr2(null)));
        // 测试 attr3 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setAttr3(null)));
        // 测试 attr4 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setAttr4(null)));
        // 测试 createTime 不匹配
        exceptionMapper.insert(cloneIgnoreId(dbException, o -> o.setCreateTime(null)));
        // 准备参数
        ExceptionExportReqVO reqVO = new ExceptionExportReqVO();
        reqVO.setExceptionCode(null);
        reqVO.setExceptionType(null);
        reqVO.setSubclassExceptionCode(null);
        reqVO.setSubclassExceptionName(null);
        reqVO.setExceptionLevelCode(null);
        reqVO.setExceptionLevelName(null);
        reqVO.setExceptionLevelColor(null);
        reqVO.setTitle(null);
        reqVO.setDescription(null);
        reqVO.setRegisterTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
        reqVO.setRegisterUser(null);
        reqVO.setWorkshopId(null);
        reqVO.setWorkshopCode(null);
        reqVO.setWorkshopName(null);
        reqVO.setProcessId(null);
        reqVO.setProcessName(null);
        reqVO.setProcessCode(null);
        reqVO.setRelatedMachineryCode(null);
        reqVO.setRelatedMachineryName(null);
        reqVO.setRelatedErpMachineryCode(null);
        reqVO.setRelatedMaterial(null);
        reqVO.setRelatedWorkorder(null);
        reqVO.setStatus(null);
        reqVO.setEstimatedImpact(null);
        reqVO.setSolution(null);
        reqVO.setCloseTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
        reqVO.setAttachments(null);
        reqVO.setRemark(null);
        reqVO.setAttr1(null);
        reqVO.setAttr2(null);
        reqVO.setAttr3(null);
        reqVO.setAttr4(null);
        reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

        // 调用
        List<ExceptionDO> list = exceptionService.getExceptionList(reqVO);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(dbException, list.get(0));
    }

}
