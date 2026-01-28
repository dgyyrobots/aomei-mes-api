package com.dofast.module.mes.service.mditemcategory;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.dofast.framework.test.core.ut.BaseDbUnitTest;

import com.dofast.module.mes.controller.admin.mditemcategory.vo.*;
import com.dofast.module.mes.dal.dataobject.mditemcategory.MdItemCategoryDO;
import com.dofast.module.mes.dal.mysql.mditemcategory.MdItemCategoryMapper;
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
 * {@link MdItemCategoryServiceImpl} 的单元测试类
 *
 * @author 惠智造
 */
@Import(MdItemCategoryServiceImpl.class)
public class MdItemCategoryServiceImplTest extends BaseDbUnitTest {

    @Resource
    private MdItemCategoryServiceImpl mdItemCategoryService;

    @Resource
    private MdItemCategoryMapper mdItemCategoryMapper;

    @Test
    public void testCreateMdItemCategory_success() {
        // 准备参数
        MdItemCategoryCreateReqVO reqVO = randomPojo(MdItemCategoryCreateReqVO.class);

        // 调用
        Integer mdItemCategoryId = mdItemCategoryService.createMdItemCategory(reqVO);
        // 断言
        assertNotNull(mdItemCategoryId);
        // 校验记录的属性是否正确
        MdItemCategoryDO mdItemCategory = mdItemCategoryMapper.selectById(mdItemCategoryId);
        assertPojoEquals(reqVO, mdItemCategory);
    }

    @Test
    public void testUpdateMdItemCategory_success() {
        // mock 数据
        MdItemCategoryDO dbMdItemCategory = randomPojo(MdItemCategoryDO.class);
        mdItemCategoryMapper.insert(dbMdItemCategory);// @Sql: 先插入出一条存在的数据
        // 准备参数
        MdItemCategoryUpdateReqVO reqVO = randomPojo(MdItemCategoryUpdateReqVO.class, o -> {
            o.setId(dbMdItemCategory.getId()); // 设置更新的 ID
        });

        // 调用
        mdItemCategoryService.updateMdItemCategory(reqVO);
        // 校验是否更新正确
        MdItemCategoryDO mdItemCategory = mdItemCategoryMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, mdItemCategory);
    }

    @Test
    public void testUpdateMdItemCategory_notExists() {
        // 准备参数
        MdItemCategoryUpdateReqVO reqVO = randomPojo(MdItemCategoryUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> mdItemCategoryService.updateMdItemCategory(reqVO), MD_ITEM_CATEGORY_NOT_EXISTS);
    }

    @Test
    public void testDeleteMdItemCategory_success() {
        // mock 数据
        MdItemCategoryDO dbMdItemCategory = randomPojo(MdItemCategoryDO.class);
        mdItemCategoryMapper.insert(dbMdItemCategory);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Integer id = dbMdItemCategory.getId();

        // 调用
        mdItemCategoryService.deleteMdItemCategory(id);
       // 校验数据不存在了
       assertNull(mdItemCategoryMapper.selectById(id));
    }

    @Test
    public void testDeleteMdItemCategory_notExists() {
        // 准备参数
        Integer id = 55;

        // 调用, 并断言异常
        assertServiceException(() -> mdItemCategoryService.deleteMdItemCategory(id), MD_ITEM_CATEGORY_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetMdItemCategoryPage() {
       // mock 数据
       MdItemCategoryDO dbMdItemCategory = randomPojo(MdItemCategoryDO.class, o -> { // 等会查询到
           o.setItemCategoryName(null);
           o.setItemCategoryCode(null);
           o.setItemRoute(null);
           o.setRemark(null);
           o.setAttr1(null);
           o.setAttr2(null);
           o.setAttr3(null);
           o.setAttr4(null);
           o.setCreateTime(null);
       });
       mdItemCategoryMapper.insert(dbMdItemCategory);
       // 测试 itemCategoryName 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setItemCategoryName(null)));
       // 测试 itemCategoryCode 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setItemCategoryCode(null)));
       // 测试 itemRoute 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setItemRoute(null)));
       // 测试 remark 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setRemark(null)));
       // 测试 attr1 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setAttr1(null)));
       // 测试 attr2 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setAttr2(null)));
       // 测试 attr3 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setAttr3(null)));
       // 测试 attr4 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setAttr4(null)));
       // 测试 createTime 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setCreateTime(null)));
       // 准备参数
       MdItemCategoryPageReqVO reqVO = new MdItemCategoryPageReqVO();
       reqVO.setItemCategoryName(null);
       reqVO.setItemCategoryCode(null);
       reqVO.setItemRoute(null);
       reqVO.setRemark(null);
       reqVO.setAttr1(null);
       reqVO.setAttr2(null);
       reqVO.setAttr3(null);
       reqVO.setAttr4(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<MdItemCategoryDO> pageResult = mdItemCategoryService.getMdItemCategoryPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbMdItemCategory, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetMdItemCategoryList() {
       // mock 数据
       MdItemCategoryDO dbMdItemCategory = randomPojo(MdItemCategoryDO.class, o -> { // 等会查询到
           o.setItemCategoryName(null);
           o.setItemCategoryCode(null);
           o.setItemRoute(null);
           o.setRemark(null);
           o.setAttr1(null);
           o.setAttr2(null);
           o.setAttr3(null);
           o.setAttr4(null);
           o.setCreateTime(null);
       });
       mdItemCategoryMapper.insert(dbMdItemCategory);
       // 测试 itemCategoryName 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setItemCategoryName(null)));
       // 测试 itemCategoryCode 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setItemCategoryCode(null)));
       // 测试 itemRoute 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setItemRoute(null)));
       // 测试 remark 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setRemark(null)));
       // 测试 attr1 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setAttr1(null)));
       // 测试 attr2 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setAttr2(null)));
       // 测试 attr3 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setAttr3(null)));
       // 测试 attr4 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setAttr4(null)));
       // 测试 createTime 不匹配
       mdItemCategoryMapper.insert(cloneIgnoreId(dbMdItemCategory, o -> o.setCreateTime(null)));
       // 准备参数
       MdItemCategoryExportReqVO reqVO = new MdItemCategoryExportReqVO();
       reqVO.setItemCategoryName(null);
       reqVO.setItemCategoryCode(null);
       reqVO.setItemRoute(null);
       reqVO.setRemark(null);
       reqVO.setAttr1(null);
       reqVO.setAttr2(null);
       reqVO.setAttr3(null);
       reqVO.setAttr4(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<MdItemCategoryDO> list = mdItemCategoryService.getMdItemCategoryList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbMdItemCategory, list.get(0));
    }

}
