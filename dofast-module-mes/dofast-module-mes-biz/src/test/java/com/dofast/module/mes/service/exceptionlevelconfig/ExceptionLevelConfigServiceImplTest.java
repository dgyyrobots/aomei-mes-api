package com.dofast.module.mes.service.exceptionlevelconfig;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.mes.controller.admin.exceptionlevelconfig.vo.*;
import com.dofast.module.mes.dal.dataobject.exceptionlevelconfig.ExceptionLevelConfigDO;
import com.dofast.module.mes.dal.mysql.exceptionlevelconfig.ExceptionLevelConfigMapper;
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
 * {@link ExceptionLevelConfigServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(ExceptionLevelConfigServiceImpl.class)
public class ExceptionLevelConfigServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ExceptionLevelConfigServiceImpl exceptionLevelConfigService;

    @Resource
    private ExceptionLevelConfigMapper exceptionLevelConfigMapper;

    @Test
    public void testCreateExceptionLevelConfig_success() {
        // 准备参数
        ExceptionLevelConfigCreateReqVO reqVO = randomPojo(ExceptionLevelConfigCreateReqVO.class);

        // 调用
        Long exceptionLevelConfigId = exceptionLevelConfigService.createExceptionLevelConfig(reqVO);
        // 断言
        assertNotNull(exceptionLevelConfigId);
        // 校验记录的属性是否正确
        ExceptionLevelConfigDO exceptionLevelConfig = exceptionLevelConfigMapper.selectById(exceptionLevelConfigId);
        assertPojoEquals(reqVO, exceptionLevelConfig);
    }

    @Test
    public void testUpdateExceptionLevelConfig_success() {
        // mock 数据
        ExceptionLevelConfigDO dbExceptionLevelConfig = randomPojo(ExceptionLevelConfigDO.class);
        exceptionLevelConfigMapper.insert(dbExceptionLevelConfig);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ExceptionLevelConfigUpdateReqVO reqVO = randomPojo(ExceptionLevelConfigUpdateReqVO.class, o -> {
            o.setId(dbExceptionLevelConfig.getId()); // 设置更新的 ID
        });

        // 调用
        exceptionLevelConfigService.updateExceptionLevelConfig(reqVO);
        // 校验是否更新正确
        ExceptionLevelConfigDO exceptionLevelConfig = exceptionLevelConfigMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, exceptionLevelConfig);
    }

    @Test
    public void testUpdateExceptionLevelConfig_notExists() {
        // 准备参数
        ExceptionLevelConfigUpdateReqVO reqVO = randomPojo(ExceptionLevelConfigUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> exceptionLevelConfigService.updateExceptionLevelConfig(reqVO), EXCEPTION_LEVEL_CONFIG_NOT_EXISTS);
    }

    @Test
    public void testDeleteExceptionLevelConfig_success() {
        // mock 数据
        ExceptionLevelConfigDO dbExceptionLevelConfig = randomPojo(ExceptionLevelConfigDO.class);
        exceptionLevelConfigMapper.insert(dbExceptionLevelConfig);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbExceptionLevelConfig.getId();

        // 调用
        exceptionLevelConfigService.deleteExceptionLevelConfig(id);
        // 校验数据不存在了
        assertNull(exceptionLevelConfigMapper.selectById(id));
    }

    @Test
    public void testDeleteExceptionLevelConfig_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> exceptionLevelConfigService.deleteExceptionLevelConfig(id), EXCEPTION_LEVEL_CONFIG_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetExceptionLevelConfigPage() {
        // mock 数据
        ExceptionLevelConfigDO dbExceptionLevelConfig = randomPojo(ExceptionLevelConfigDO.class, o -> { // 等会查询到
            o.setLevelCode(null);
            o.setLevelName(null);
            o.setColor(null);
            o.setResponseTime(null);
            o.setRemark(null);
            o.setAttr1(null);
            o.setAttr2(null);
            o.setAttr3(null);
            o.setAttr4(null);
            o.setCreateTime(null);
        });
        exceptionLevelConfigMapper.insert(dbExceptionLevelConfig);
        // 测试 levelCode 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setLevelCode(null)));
        // 测试 levelName 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setLevelName(null)));
        // 测试 color 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setColor(null)));
        // 测试 responseTime 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setResponseTime(null)));
        // 测试 remark 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setRemark(null)));
        // 测试 attr1 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setAttr1(null)));
        // 测试 attr2 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setAttr2(null)));
        // 测试 attr3 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setAttr3(null)));
        // 测试 attr4 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setAttr4(null)));
        // 测试 createTime 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setCreateTime(null)));
        // 准备参数
        ExceptionLevelConfigPageReqVO reqVO = new ExceptionLevelConfigPageReqVO();
        reqVO.setLevelCode(null);
        reqVO.setLevelName(null);
        reqVO.setColor(null);
        //reqVO.setResponseTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
        reqVO.setRemark(null);
        reqVO.setAttr1(null);
        reqVO.setAttr2(null);
        reqVO.setAttr3(null);
        reqVO.setAttr4(null);
        //reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

        // 调用
        PageResult<ExceptionLevelConfigDO> pageResult = exceptionLevelConfigService.getExceptionLevelConfigPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbExceptionLevelConfig, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetExceptionLevelConfigList() {
        // mock 数据
        ExceptionLevelConfigDO dbExceptionLevelConfig = randomPojo(ExceptionLevelConfigDO.class, o -> { // 等会查询到
            o.setLevelCode(null);
            o.setLevelName(null);
            o.setColor(null);
            o.setResponseTime(null);
            o.setRemark(null);
            o.setAttr1(null);
            o.setAttr2(null);
            o.setAttr3(null);
            o.setAttr4(null);
            o.setCreateTime(null);
        });
        exceptionLevelConfigMapper.insert(dbExceptionLevelConfig);
        // 测试 levelCode 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setLevelCode(null)));
        // 测试 levelName 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setLevelName(null)));
        // 测试 color 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setColor(null)));
        // 测试 responseTime 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setResponseTime(null)));
        // 测试 remark 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setRemark(null)));
        // 测试 attr1 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setAttr1(null)));
        // 测试 attr2 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setAttr2(null)));
        // 测试 attr3 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setAttr3(null)));
        // 测试 attr4 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setAttr4(null)));
        // 测试 createTime 不匹配
        exceptionLevelConfigMapper.insert(cloneIgnoreId(dbExceptionLevelConfig, o -> o.setCreateTime(null)));
        // 准备参数
        ExceptionLevelConfigExportReqVO reqVO = new ExceptionLevelConfigExportReqVO();
        reqVO.setLevelCode(null);
        reqVO.setLevelName(null);
        reqVO.setColor(null);
        //reqVO.setResponseTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
        reqVO.setRemark(null);
        reqVO.setAttr1(null);
        reqVO.setAttr2(null);
        reqVO.setAttr3(null);
        reqVO.setAttr4(null);
        // reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

        // 调用
        List<ExceptionLevelConfigDO> list = exceptionLevelConfigService.getExceptionLevelConfigList(reqVO);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(dbExceptionLevelConfig, list.get(0));
    }

}
