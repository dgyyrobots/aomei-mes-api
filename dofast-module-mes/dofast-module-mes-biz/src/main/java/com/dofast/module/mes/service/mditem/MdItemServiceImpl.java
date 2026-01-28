package com.dofast.module.mes.service.mditem;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.mes.controller.admin.mditem.vo.*;
import com.dofast.module.mes.controller.admin.mdproductbom.vo.MdProductBomExportReqVO;
import com.dofast.module.mes.convert.mditem.MdItemConvert;
import com.dofast.module.mes.dal.dataobject.mditem.MdItemDO;
import com.dofast.module.mes.dal.dataobject.mditemtype.MdItemTypeDO;
import com.dofast.module.mes.dal.dataobject.mdproductbom.MdProductBomDO;
import com.dofast.module.mes.dal.mysql.mditem.MdItemMapper;
import com.dofast.module.mes.dal.mysql.mditemtype.MdItemTypeMapper;
import com.dofast.module.mes.dal.mysql.mdproductbom.MdProductBomMapper;
import com.dofast.module.wms.api.StorageAreaApi.StorageAreaApi;
import com.dofast.module.wms.api.StorageAreaApi.dto.StorageAreaDTO;
import com.dofast.module.wms.api.StorageLocationApi.StorageLocationApi;
import com.dofast.module.wms.api.StorageLocationApi.dto.StorageLocationDTO;
import com.dofast.module.wms.api.WarehosueApi.WarehouseApi;
import com.dofast.module.wms.api.WarehosueApi.dto.WarehouseDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.mes.enums.ErrorCodeConstants.MD_ITEM_NOT_EXISTS;
import static com.dofast.module.pro.enums.ErrorCodeConstants.UPDATE_INFO;

/**
 * 物料产品 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class MdItemServiceImpl implements MdItemService {

    @Resource
    private MdItemMapper mdItemMapper;

    @Resource
    private MdItemTypeMapper mdItemTypeMapper;

    @Resource
    private MdProductBomMapper mdProductBomMapper;

    @Override
    public String checkItemCodeUnique(@Valid MdItemBaseVO createReqVO) {
        MdItemDO item = mdItemMapper.checkItemCodeUnique(createReqVO);
        Long itemId = createReqVO.getId() == null? -1L:createReqVO.getId();
        if(item!=null && item.getId().longValue() != itemId.longValue()){
            return Constant.NOT_UNIQUE;
        }else{
            return Constant.UNIQUE;
        }
    }
    @Override
    public String checkItemNameUnique(@Valid MdItemBaseVO mdItem) {
        MdItemDO item = mdItemMapper.checkItemNameUnique(mdItem);
        Long itemId = mdItem.getId() == null? -1L:mdItem.getId();
        if(item !=null && item.getId().longValue() != itemId.longValue()){
            return Constant.NOT_UNIQUE;
        }else{
            return Constant.UNIQUE;
        }
    }

    @Resource
    private WarehouseApi warehouseApi;

    @Resource
    private StorageLocationApi storageLocationApi;

    @Resource
    private StorageAreaApi storageAreaApi;

    @Override
    public Long createMdItem(MdItemCreateReqVO createReqVO) {
        // 插入
        MdItemDO mdItem = MdItemConvert.INSTANCE.convert(createReqVO);
        //仓库
        //
        /*WarehouseDTO warehouse = warehouseApi.getWarehouse(createReqVO.getWarehouseId());
        mdItem.setWarehouseCode(warehouse.getWarehouseCode());
        mdItem.setWarehouseName(warehouse.getWarehouseName());
        //库区
        StorageLocationDTO location = storageLocationApi.getLocation(createReqVO.getLocationId());
        mdItem.setLocationCode(location.getLocationCode());
        mdItem.setLocationName(location.getLocationName());
        //库位
        StorageAreaDTO area = storageAreaApi.getArea(createReqVO.getAreaId());
        mdItem.setAreaCode(area.getAreaCode());
        mdItem.setAreaName(area.getAreaName());
        mdItemMapper.insert(mdItem);*/
        // 返回
        return mdItem.getId();
    }

    @Override
    public void updateMdItem(MdItemUpdateReqVO updateReqVO) {
        // 校验存在
        validateMdItemExists(updateReqVO.getId());
        // 更新
        MdItemDO updateObj = MdItemConvert.INSTANCE.convert(updateReqVO);
        int i = mdItemMapper.updateById(updateObj);
        if (i<=0){
            throw exception(UPDATE_INFO);
        }
    }

    @Override
    public void deleteMdItem(Long id) {
        // 校验存在
        validateMdItemExists(id);
        // 删除
        mdItemMapper.deleteById(id);
    }


    private void validateMdItemExists(Long id) {
        if (mdItemMapper.selectById(id) == null) {
            throw exception(MD_ITEM_NOT_EXISTS);
        }
    }

    @Override
    public MdItemDO getMdItem(Long id) {
        return mdItemMapper.selectById(id);
    }

    @Override
    public MdItemDO getMdItem(String itemCode) {
        return mdItemMapper.selectOne(MdItemDO::getItemCode, itemCode);
    }

    @Override
    public List<MdItemDO> getMdItemList(Collection<Long> ids) {
        return mdItemMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MdItemDO> getMdItemPage(MdItemPageReqVO pageReqVO) {
        return mdItemMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MdItemDO> getMdItemList(MdItemExportReqVO mdItemExportReqVO) {
        //因为查询语句是orMapper里没法关联两个表，所以查两次，并过滤，最后组装到一起
        List<MdItemDO> mdItemDOS = mdItemMapper.selectListByItemReq(mdItemExportReqVO);
        if (!StrUtils.isNotNull(mdItemExportReqVO.getItemTypeId())){
            return mdItemDOS;
        }
        QueryWrapper<MdItemTypeDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id")
                .like("ancestors", "%" + mdItemExportReqVO.getItemTypeId() + "%");
        List<MdItemTypeDO> resultList = mdItemTypeMapper.selectList(queryWrapper);
        mdItemExportReqVO.setItemTypeId(null);
        List<MdItemDO> list2 = mdItemMapper.selectListByItemReq(mdItemExportReqVO);
        List<MdItemDO> filteredList = list2.stream()
                .filter(item -> resultList.stream()
                        .anyMatch(type -> Objects.equals(item.getItemTypeId(), type.getId())))
                .collect(Collectors.toList());
        //拼接数据
        List<MdItemDO> mergedResultList = Stream.concat(filteredList.stream(), mdItemDOS.stream())
                .distinct()
                .collect(Collectors.toList());
        return mergedResultList;
    }

    /**
     * 根据物料料号获取物料信息
     * @param code
     * @return
     */
    @Override
    public MdItemDO getMdItemByItemCode(String code){
        return mdItemMapper.selectOne(MdItemDO::getItemCode, code);
    }

    @Override
    public List<MdItemRequestVO> getMdItemRequest(Collection<Long> ids) {
        // 查询商品
        List<MdItemDO> mdItemDOS = mdItemMapper.selectBatchIds(ids);
        List<MdItemRequestVO> result = new ArrayList<>();
        for (MdItemDO mdItemDO: mdItemDOS){
            MdItemRequestVO start = translateClass(mdItemDO);
            result.add(start);
            retrieveItems(start);
        }
        return result;
    }

    public MdItemRequestVO translateClass(MdItemDO mdItemDO){
        MdItemRequestVO mdItemRequestVO = BeanUtil.copyProperties(mdItemDO, MdItemRequestVO.class);
        return mdItemRequestVO;
    }

    public void retrieveItems(MdItemRequestVO start){
        MdProductBomExportReqVO mdProductBomExportReqVO = new MdProductBomExportReqVO();
        mdProductBomExportReqVO.setItemId(start.getId());
        List<MdProductBomDO> list = mdProductBomMapper.selectList(mdProductBomExportReqVO);
        if (list == null || list.isEmpty()) {
            start.setNode(null);
            return;
        }
        for (MdProductBomDO mdProductBomDO : list){
            MdItemDO mdItemDO = mdItemMapper.selectById(mdProductBomDO.getBomItemId());
            MdItemRequestVO node = translateClass(mdItemDO);
            start.addNode(node);
            retrieveItems(node);
        }
    }

    /**
     * 从ERP获取物料信息
     * @return
     */
    public String initMdItemInfo1(){
        //TODO 从ERP获取物料信息
        String oracleUrl = "jdbc:oracle:thin:@//192.168.127.7:1521/TOPPRD";
        String oracleUser = "amuser";
        String oraclePassword = "am123456";
        // MySQL 数据源配置
        String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/ruoyi-vue-update?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true";
        String mysqlUser = "root";
        String mysqlPassword = "root";
        try (Connection oracleConn = DriverManager.getConnection(oracleUrl, oracleUser, oraclePassword);
             Connection mysqlConn = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword)) {
            mysqlConn.setAutoCommit(false); // 关闭自动提交
            String oracleQuery = "select distinct a.imaa001, a.imaa006, a.imaaent, b.imafsite , c.imaal003, c.imaal004 , a.imaa004, DECODE(a.imaa004, 'A', '组合/加工品', 'E', '费用/软件', 'F', '事务用品', 'M', '材料/零件/商品' , 'T', '范本' , 'X', '虚拟品' , '其它') as itemTypeName , DECODE(a.imaa004, 'A', '22', 'E', '23', 'F', '24', 'M', '25' , 'T', '26' , 'X', '27' ) as itemTypeId , a.imaa101 from DSDATA.imaa_t a\n" +
                    "left join DSDATA.IMAF_T b on a.imaa001 = b.imaf001 and a.imaaent = b.imafent\n" +
                    "left join DSDATA.IMAAL_T c on a.imaa001 = c.imaal001 and a.imaaent = c.imaalent\n" +
                    "left join DSDATA.IMAE_T d on a.imaa001 = d.imae001 and a.imaaent = d.imaeent and b.imafsite = d.imaesite\n" +
                    "left join DSDATA.IMAAUC_T e on c.imaal003 = e.imaauc001 and\n" +
                    "a.imaaent = e.imaaucent and e.imaaucsite = b.imafsite\n" +
                    "where b.IMAFSITE = 'AM01' and a.imaaent = 100 and c.imaal002 = 'zh_CN'\n";
            String mysqlCheck = "SELECT item_code FROM mes_md_item_copy1";
            String mysqlInsert = "INSERT INTO mes_md_item_copy1 (item_code, item_name, unit_of_measure, item_or_product, enable_flag, safe_stock_flag, deleted, tenant_id, specification, item_type_code , item_type_name , item_type_id , create_time , suppliers) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ? , ? )";
            try (PreparedStatement oracleStmt = oracleConn.prepareStatement(oracleQuery);
                 ResultSet rs = oracleStmt.executeQuery();
                 PreparedStatement mysqlCheckStmt = mysqlConn.prepareStatement(mysqlCheck);
                 PreparedStatement mysqlPstmt = mysqlConn.prepareStatement(mysqlInsert)) {
                Set<String> existingItemCodes = new HashSet<>();
                try (ResultSet checkRs = mysqlCheckStmt.executeQuery()) {
                    while (checkRs.next()) {
                        existingItemCodes.add(checkRs.getString("item_code"));
                    }
                }
                while (rs.next()) {
                    String itemCode = rs.getString("imaa001");
                    String itemName = rs.getString("imaal003");
                    String unit = Optional.ofNullable(rs.getString("imaa006")).orElse("米");
                    String specification = rs.getString("imaal004");
                    String itemTypeCode = rs.getString("imaa004");
                    String itemOrProduct = "ITEM";
                    String enable = "Y";
                    String safeFlag = "Y";
                    int deleted = 0;
                    int tenant = 1;
                    String itemTypeName = rs.getString("itemTypeName");
                    int itemTypeId = rs.getInt("itemTypeId");
                    String suppliers = rs.getString("imaa101");
                    if (!existingItemCodes.contains(itemCode)) {
                        mysqlPstmt.setString(1, itemCode);
                        mysqlPstmt.setString(2, itemName);
                        mysqlPstmt.setString(3, unit);
                        mysqlPstmt.setString(4, itemOrProduct);
                        mysqlPstmt.setString(5, enable);
                        mysqlPstmt.setString(6, safeFlag);
                        mysqlPstmt.setInt(7, deleted);
                        mysqlPstmt.setInt(8, tenant);
                        mysqlPstmt.setString(9, specification);
                        mysqlPstmt.setString(10, itemTypeCode);
                        mysqlPstmt.setString(11, itemTypeName);
                        mysqlPstmt.setInt(12, itemTypeId);
                        mysqlPstmt.setString(13, DateUtil.now());
                        mysqlPstmt.setString(14, suppliers);
                        mysqlPstmt.addBatch();
                    }
                }
                mysqlPstmt.executeBatch();
                mysqlConn.commit(); // 提交事务
                System.out.println("物料基础数据迁移成功!");
            } catch (SQLException e) {
                mysqlConn.rollback(); // 回滚事务
                e.printStackTrace();
                System.out.println("异常!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("异常!");
        }
        return "success";
    }

    /**
     * 从ERP获取物料信息
     * @return
     */
    @Override
    public String initMdItemInfo() {
        //TODO 从ERP获取物料信息
        String oracleUrl = "jdbc:oracle:thin:@//192.168.127.7:1521/TOPPRD";
        String oracleUser = "amuser";
        String oraclePassword = "am123456";
        try (Connection oracleConn = DriverManager.getConnection(oracleUrl, oracleUser, oraclePassword)) {
            /*String oracleQuery = "select distinct a.imaa001, a.imaa006, a.imaaent, b.imafsite , c.imaal003, c.imaal004 , a.imaa004, DECODE(a.imaa004, 'A', '组合/加工品', 'E', '费用/软件', 'F', '事务用品', 'M', '材料/零件/商品' , 'T', '范本' , 'X', '虚拟品' , '其它') as itemTypeName , DECODE(a.imaa004, 'A', '22', 'E', '23', 'F', '24', 'M', '25' , 'T', '26' , 'X', '27' ) as itemTypeId , a.imaa101 from DSDATA.imaa_t a\n" +
                    "left join DSDATA.IMAF_T b on a.imaa001 = b.imaf001 and a.imaaent = b.imafent\n" +
                    "left join DSDATA.IMAAL_T c on a.imaa001 = c.imaal001 and a.imaaent = c.imaalent\n" +
                    "left join DSDATA.IMAE_T d on a.imaa001 = d.imae001 and a.imaaent = d.imaeent and b.imafsite = d.imaesite\n" +
                    "left join DSDATA.IMAAUC_T e on c.imaal003 = e.imaauc001 and\n" +
                    "a.imaaent = e.imaaucent and e.imaaucsite = b.imafsite\n" +
                    "where b.IMAFSITE = 'AM01' and a.imaaent = 100 and c.imaal002 = 'zh_CN'\n";*/

            String oracleQuery ="select distinct a.imaa001, a.imaa006, a.imaaent, b.imafsite , c.imaal003, c.imaal004 , f.imca004, DECODE(f.imca004, 'A', '组合/加工品', 'E', '费用/软件', 'F', '事务用品', 'M', '材料/零件/商品' , 'T', '范本' , 'X', '虚拟品' , '其它') as itemTypeName , DECODE(f.imca004 , 'A', '22', 'E', '23', 'F', '24', 'M', '25' , 'T', '26' , 'X', '27' ,  '28' ) as itemTypeId , a.imaa101  from DSDATA.imaa_t a\n" +
                    "left join DSDATA.IMAF_T b on a.imaa001 = b.imaf001 and a.imaaent = b.imafent\n" +
                    "left join DSDATA.IMAAL_T c on a.imaa001 = c.imaal001 and a.imaaent = c.imaalent\n" +
                    "left join DSDATA.IMAE_T d on a.imaa001 = d.imae001 and a.imaaent = d.imaeent and b.imafsite = d.imaesite\n" +
                    "left join DSDATA.IMAAUC_T e on c.imaal003 = e.imaauc001 and a.imaaent = e.imaaucent and e.imaaucsite = b.imafsite\n" +
                    "left join DSDATA.IMCA_T f on a.imaa003 = f.imca001 and imaaent = f.imcaent\n" +
                    "where b.IMAFSITE = 'AM01' and a.imaaent = 100 and c.imaal002 = 'zh_CN'";
            try {
                PreparedStatement oracleStmt = oracleConn.prepareStatement(oracleQuery);
                ResultSet rs = oracleStmt.executeQuery();
                List<MdItemDO> itemsToInsert = new ArrayList<>();
                List<MdItemDO> itemsToUp = new ArrayList<>();
                while (rs.next()) {
                    String itemCode = rs.getString("imaa001");
                    // 校验当前物料是否存在
                    MdItemBaseVO createReqVO = new MdItemBaseVO();
                    createReqVO.setItemCode(itemCode);

                    MdItemDO itemInfo = mdItemMapper.checkItemCodeUnique(createReqVO);
                    if (itemInfo != null) {
                        //continue; // 如果物料已存在，则跳过
                        // 已存在, 修改当前物料信息
                        itemInfo.setItemName(rs.getString("imaal003")); // 物料名称
                        itemInfo.setUnitOfMeasure(Optional.ofNullable(rs.getString("imaa006")).orElse("")); // 单位
                        itemInfo.setSpecification(rs.getString("imaal004")); // 规格
                        itemInfo.setItemTypeCode(rs.getString("imca004")); // 物料类型编码
                        itemInfo.setItemOrProduct("ITEM"); // 默认设置为物料
                        itemInfo.setEnableFlag("Y"); // 启用
                        itemInfo.setSafeStockFlag("Y"); // 默认启用安全库存
                        itemInfo.setDeleted(false); // 默认使用
                        itemInfo.setItemTypeName(rs.getString("itemTypeName")); // 物料类型名称
                        itemInfo.setItemTypeId(rs.getLong("itemTypeId")); // 物料类型Id
                        itemInfo.setSuppliers(rs.getString("imaa101")); // 供应商
                        itemsToUp.add(itemInfo);
                    }else{
                        MdItemDO item = new MdItemDO();
                        item.setItemCode(rs.getString("imaa001")); // 物料编码
                        item.setItemName(rs.getString("imaal003")); // 物料名称
                        item.setUnitOfMeasure(Optional.ofNullable(rs.getString("imaa006")).orElse("")); // 单位
                        item.setSpecification(rs.getString("imaal004")); // 规格
                        item.setItemTypeCode(rs.getString("imca004")); // 物料类型编码
                        item.setItemOrProduct("ITEM"); // 默认设置为物料
                        item.setEnableFlag("Y"); // 启用
                        item.setSafeStockFlag("Y"); // 默认启用安全库存
                        item.setDeleted(false); // 默认使用
                        item.setItemTypeName(rs.getString("itemTypeName")); // 物料类型名称
                        item.setItemTypeId(rs.getLong("itemTypeId")); // 物料类型Id
                        item.setSuppliers(rs.getString("imaa101")); // 供应商
                        itemsToInsert.add(item);
                    }
                }
                if(itemsToInsert.size()!=0 || itemsToInsert != null){
                    mdItemMapper.insertBatch(itemsToInsert);
                }
                if(itemsToUp.size()!=0 || itemsToUp != null){
                    mdItemMapper.updateBatch(itemsToUp);
                }
                System.out.println("物料基础数据迁移成功!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("异常!");
            }
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("异常!");
        }
        return "success";
    }


}
