package com.dofast.module.mes.service.subclassexception;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.mes.controller.admin.subclassexception.vo.*;
import com.dofast.module.mes.dal.dataobject.subclassexception.SubclassExceptionDO;
import com.dofast.module.mes.dal.mysql.subclassexception.SubclassExceptionMapper;
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
 * {@link SubclassExceptionServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(SubclassExceptionServiceImpl.class)
public class SubclassExceptionServiceImplTest extends BaseDbUnitTest {

    @Resource
    private SubclassExceptionServiceImpl subclassExceptionService;

    @Resource
    private SubclassExceptionMapper subclassExceptionMapper;

    @Test
    public void testCreateSubclassException_success() {
        // 准备参数
        SubclassExceptionCreateReqVO reqVO = randomPojo(SubclassExceptionCreateReqVO.class);

        // 调用
        Long subclassExceptionId = subclassExceptionService.createSubclassException(reqVO);
        // 断言
        assertNotNull(subclassExceptionId);
        // 校验记录的属性是否正确
        SubclassExceptionDO subclassException = subclassExceptionMapper.selectById(subclassExceptionId);
        assertPojoEquals(reqVO, subclassException);
    }

    @Test
    public void testUpdateSubclassException_success() {
        // mock 数据
        SubclassExceptionDO dbSubclassException = randomPojo(SubclassExceptionDO.class);
        subclassExceptionMapper.insert(dbSubclassException);// @Sql: 先插入出一条存在的数据
        // 准备参数
        SubclassExceptionUpdateReqVO reqVO = randomPojo(SubclassExceptionUpdateReqVO.class, o -> {
            o.setId(dbSubclassException.getId()); // 设置更新的 ID
        });

        // 调用
        subclassExceptionService.updateSubclassException(reqVO);
        // 校验是否更新正确
        SubclassExceptionDO subclassException = subclassExceptionMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, subclassException);
    }

    @Test
    public void testUpdateSubclassException_notExists() {
        // 准备参数
        SubclassExceptionUpdateReqVO reqVO = randomPojo(SubclassExceptionUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> subclassExceptionService.updateSubclassException(reqVO), SUBCLASS_EXCEPTION_NOT_EXISTS);
    }

    @Test
    public void testDeleteSubclassException_success() {
        // mock 数据
        SubclassExceptionDO dbSubclassException = randomPojo(SubclassExceptionDO.class);
        subclassExceptionMapper.insert(dbSubclassException);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbSubclassException.getId();

        // 调用
        subclassExceptionService.deleteSubclassException(id);
       // 校验数据不存在了
       assertNull(subclassExceptionMapper.selectById(id));
    }

    @Test
    public void testDeleteSubclassException_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> subclassExceptionService.deleteSubclassException(id), SUBCLASS_EXCEPTION_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetSubclassExceptionPage() {
       // mock 数据
       SubclassExceptionDO dbSubclassException = randomPojo(SubclassExceptionDO.class, o -> { // 等会查询到
           o.setExceptionType(null);
           o.setSubclassExceptionCode(null);
           o.setSubclassExceptionName(null);
           o.setRemark(null);
           o.setCreateTime(null);
       });
       subclassExceptionMapper.insert(dbSubclassException);
       // 测试 exceptionType 不匹配
       subclassExceptionMapper.insert(cloneIgnoreId(dbSubclassException, o -> o.setExceptionType(null)));
       // 测试 subclassExceptionCode 不匹配
       subclassExceptionMapper.insert(cloneIgnoreId(dbSubclassException, o -> o.setSubclassExceptionCode(null)));
       // 测试 subclassExceptionName 不匹配
       subclassExceptionMapper.insert(cloneIgnoreId(dbSubclassException, o -> o.setSubclassExceptionName(null)));
       // 测试 remark 不匹配
       subclassExceptionMapper.insert(cloneIgnoreId(dbSubclassException, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       subclassExceptionMapper.insert(cloneIgnoreId(dbSubclassException, o -> o.setCreateTime(null)));
       // 准备参数
       SubclassExceptionPageReqVO reqVO = new SubclassExceptionPageReqVO();
       reqVO.setExceptionType(null);
       reqVO.setSubclassExceptionCode(null);
       reqVO.setSubclassExceptionName(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<SubclassExceptionDO> pageResult = subclassExceptionService.getSubclassExceptionPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbSubclassException, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetSubclassExceptionList() {
       // mock 数据
       SubclassExceptionDO dbSubclassException = randomPojo(SubclassExceptionDO.class, o -> { // 等会查询到
           o.setExceptionType(null);
           o.setSubclassExceptionCode(null);
           o.setSubclassExceptionName(null);
           o.setRemark(null);
           o.setCreateTime(null);
       });
       subclassExceptionMapper.insert(dbSubclassException);
       // 测试 exceptionType 不匹配
       subclassExceptionMapper.insert(cloneIgnoreId(dbSubclassException, o -> o.setExceptionType(null)));
       // 测试 subclassExceptionCode 不匹配
       subclassExceptionMapper.insert(cloneIgnoreId(dbSubclassException, o -> o.setSubclassExceptionCode(null)));
       // 测试 subclassExceptionName 不匹配
       subclassExceptionMapper.insert(cloneIgnoreId(dbSubclassException, o -> o.setSubclassExceptionName(null)));
       // 测试 remark 不匹配
       subclassExceptionMapper.insert(cloneIgnoreId(dbSubclassException, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       subclassExceptionMapper.insert(cloneIgnoreId(dbSubclassException, o -> o.setCreateTime(null)));
       // 准备参数
       SubclassExceptionExportReqVO reqVO = new SubclassExceptionExportReqVO();
       reqVO.setExceptionType(null);
       reqVO.setSubclassExceptionCode(null);
       reqVO.setSubclassExceptionName(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<SubclassExceptionDO> list = subclassExceptionService.getSubclassExceptionList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbSubclassException, list.get(0));
    }

}
