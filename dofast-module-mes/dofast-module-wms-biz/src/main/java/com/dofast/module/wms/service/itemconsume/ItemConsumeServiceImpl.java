package com.dofast.module.wms.service.itemconsume;

import com.dofast.framework.common.pojo.UserConstants;
import com.dofast.module.mes.api.WorkStationAPi.WorkStationApi;
import com.dofast.module.mes.api.WorkStationAPi.dto.WorkStationDTO;
import com.dofast.module.pro.api.FeedbackApi.FeedbackApi;
import com.dofast.module.pro.api.FeedbackApi.dto.FeedbackDTO;
import com.dofast.module.pro.api.ProcessApi.ProcessApi;
import com.dofast.module.pro.api.ProcessApi.dto.ProcessDTO;
import com.dofast.module.pro.api.RouteApi.RouteApi;
import com.dofast.module.pro.api.RouteApi.RouteDTO;
import com.dofast.module.pro.api.RouteProductBomApi.RouteProductBomApi;
import com.dofast.module.pro.api.TaskApi.TaskApi;
import com.dofast.module.pro.api.TaskApi.dto.TaskDTO;
import com.dofast.module.pro.api.WorkorderApi.WorkorderApi;
import com.dofast.module.pro.api.WorkorderApi.dto.WorkorderDTO;
import com.dofast.module.wms.api.ERPApi.WorkorderERPAPI;
import com.dofast.module.wms.controller.admin.feedline.vo.FeedLineExportReqVO;
import com.dofast.module.wms.controller.admin.issueheader.vo.IssueHeaderExportReqVO;
import com.dofast.module.wms.controller.admin.issueline.vo.IssueLineExportReqVO;
import com.dofast.module.wms.dal.dataobject.feedline.FeedLineDO;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueHeaderDO;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import com.dofast.module.wms.dal.dataobject.itemconsume.ItemConsumeTxBean;
import com.dofast.module.wms.dal.dataobject.itemconsumeline.ItemConsumeLineDO;
import com.dofast.module.wms.dal.mysql.itemconsumeline.ItemConsumeLineMapper;
import com.dofast.module.wms.dal.mysql.materialstock.MaterialStockMapper;
import com.dofast.module.wms.service.feedline.FeedLineService;
import com.dofast.module.wms.service.issueheader.IssueHeaderService;
import com.dofast.module.wms.service.issueline.IssueLineService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dofast.module.wms.controller.admin.itemconsume.vo.*;
import com.dofast.module.wms.dal.dataobject.itemconsume.ItemConsumeDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.wms.convert.itemconsume.ItemConsumeConvert;
import com.dofast.module.wms.dal.mysql.itemconsume.ItemConsumeMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.wms.enums.ErrorCodeConstants.*;

/**
 * 物料消耗记录 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class ItemConsumeServiceImpl implements ItemConsumeService {

    @Resource
    private ItemConsumeMapper itemConsumeMapper;

    @Resource
    private ProcessApi processApi;

    @Resource
    private WorkStationApi workStationApi;

    @Resource
    private WorkorderApi workorderApi;

    @Resource
    private TaskApi taskApi;

    @Resource
    private RouteApi routeApi;

    @Resource
    private RouteProductBomApi routeProductBomApi;

    @Resource
    private ItemConsumeLineMapper itemConsumeLineMapper;

    @Resource
    private MaterialStockMapper materialStockMapper;

    @Resource
    private FeedLineService feedLineService;

    @Resource
    private IssueHeaderService issueHeaderService;

    @Resource
    private IssueLineService issueLineService;

    @Resource
    private FeedbackApi feedbackApi;

    @Resource
    private WorkorderERPAPI workorderERPAPI;


    @Override
    public Long createItemConsume(ItemConsumeCreateReqVO createReqVO) {
        // 插入
        ItemConsumeDO itemConsume = ItemConsumeConvert.INSTANCE.convert(createReqVO);
        itemConsumeMapper.insert(itemConsume);
        // 返回
        return itemConsume.getId();
    }

    @Override
    public void updateItemConsume(ItemConsumeUpdateReqVO updateReqVO) {
        // 校验存在
        validateItemConsumeExists(updateReqVO.getId());
        // 更新
        ItemConsumeDO updateObj = ItemConsumeConvert.INSTANCE.convert(updateReqVO);
        itemConsumeMapper.updateById(updateObj);
    }

    @Override
    public void deleteItemConsume(Long id) {
        // 校验存在
        validateItemConsumeExists(id);
        // 删除
        itemConsumeMapper.deleteById(id);
    }

    private void validateItemConsumeExists(Long id) {
        if (itemConsumeMapper.selectById(id) == null) {
            throw exception(ITEM_CONSUME_NOT_EXISTS);
        }
    }

    @Override
    public ItemConsumeDO getItemConsume(Long id) {
        return itemConsumeMapper.selectById(id);
    }

    @Override
    public List<ItemConsumeDO> getItemConsumeList(Collection<Long> ids) {
        return itemConsumeMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ItemConsumeDO> getItemConsumePage(ItemConsumePageReqVO pageReqVO) {
        return itemConsumeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ItemConsumeDO> getItemConsumeList(ItemConsumeExportReqVO exportReqVO) {
        return itemConsumeMapper.selectList(exportReqVO);
    }

    /**
     * 物料消耗记录
     * @param feedback
     * @return
     */
    @Override
    public ItemConsumeDO generateItemConsume(FeedbackDTO feedback) {
        WorkorderDTO workorder = workorderApi.getWorkorder(feedback.getWorkorderId());
        WorkStationDTO workstation = workStationApi.getWorkstation(feedback.getWorkstationId());
        ProcessDTO process = processApi.getcess(workstation.getProcessId());
        TaskDTO task = taskApi.getTask(feedback.getTaskId());
        String routeCode =workorder.getProductCode() + "-" + workorder.getRouteCode();
        RouteDTO route = routeApi.getRoute(feedback.getItemId(), routeCode);

        //生成消耗单头信息
        ItemConsumeDO itemConsume = new ItemConsumeDO();
        itemConsume.setWorkorderId(feedback.getWorkorderId());
        itemConsume.setWorkorderCode(workorder.getWorkorderCode());
        itemConsume.setWorkorderName(workorder.getWorkorderName());

        itemConsume.setWorkstationId(feedback.getWorkstationId());
        itemConsume.setWorkstationCode(workstation.getWorkstationCode());
        itemConsume.setWorkstationName(workstation.getWorkstationName());

        itemConsume.setTaskId(feedback.getTaskId());
        itemConsume.setTaskCode(task.getTaskCode());
        itemConsume.setTaskName(task.getTaskName());

        itemConsume.setProcessId(process.getId());
        itemConsume.setProcessCode(process.getProcessCode());
        itemConsume.setProcessName(process.getProcessName());

        itemConsume.setConsumeDate(LocalDateTime.now());
        itemConsume.setStatus(UserConstants.ORDER_STATUS_PREPARE);
        itemConsumeMapper.insert(itemConsume);

        //生成行信息
        //先获取当前生产的产品在此道工序中配置的物料BOM
       /* RouteProductBomDTO param = new RouteProductBomDTO();
        param.setProductId(feedback.getItemId());
        param.setRouteId(route.getId());
        List<RouteProductBomDTO> boms = routeProductBomApi.getList(param);
        if(CollectionUtil.isNotEmpty(boms)){
            for (RouteProductBomDTO bom: boms
            ) {
                //这里根据需要消耗的原材料/半成品信息 匹配出对应的线边库库存记录。
                BigDecimal quantityToConsume = BigDecimal.valueOf(bom.getQuantity()).multiply(BigDecimal.valueOf(feedback.getQuantityFeedback())); //总的消耗量

                //从线边库中，根据生产工单、物料按照先进先出的原则查询库存现有量
                MaterialStockExportReqVO p = new MaterialStockExportReqVO();
                p.setWorkorderCode(feedback.getWorkorderCode()); //当前工单
                p.setItemId(bom.getItemId()); //指定物料
                p.setWarehouseCode(UserConstants.VIRTUAL_WH); //线边库
                List<MaterialStockDO> ms = materialStockMapper.selectList(p);
                if(CollectionUtil.isNotEmpty(ms)){
                    MaterialStockDO theStock = null;
                    for(int i=0;i<ms.size();i++){
                        theStock = ms.get(i);
                        if(theStock.getQuantityOnhand().compareTo(quantityToConsume)>=0){
                            //当前库存记录的库存量大于等于本次需要消耗的库存量, 则直接使用当前记录
                            ItemConsumeLineDO line = new ItemConsumeLineDO();
                            line.setMaterialStockId(theStock.getId());
                            line.setRecordId(itemConsume.getId());
                            line.setItemId(bom.getItemId());
                            line.setItemCode(bom.getItemCode());
                            line.setItemName(bom.getItemName());
                            line.setSpecification(bom.getSpecification());
                            line.setUnitOfMeasure(bom.getUnitOfMeasure());
                            line.setQuantityConsume(quantityToConsume);
                            line.setBatchCode(workorder.getBatchCode());
                            itemConsumeLineMapper.insert(line);

                            quantityToConsume= BigDecimal.ZERO;
                        }else if(theStock.getQuantityOnhand().compareTo(BigDecimal.ZERO)==1){
                            //当前记录的库存量大于0 并且小于需要扣减的量，只从当前库存记录上扣减在库量，并更新剩余需要扣减的量
                            ItemConsumeLineDO line = new ItemConsumeLineDO();
                            line.setMaterialStockId(theStock.getId());
                            line.setRecordId(itemConsume.getId());
                            line.setItemId(bom.getItemId());
                            line.setItemCode(bom.getItemCode());
                            line.setItemName(bom.getItemName());
                            line.setSpecification(bom.getSpecification());
                            line.setUnitOfMeasure(bom.getUnitOfMeasure());
                            line.setQuantityConsume(theStock.getQuantityOnhand());
                            line.setBatchCode(workorder.getBatchCode());
                            itemConsumeLineMapper.insert(line);
                            quantityToConsume = quantityToConsume.subtract(theStock.getQuantityOnhand());
                        } else {
                            //查出的库存量为负，不做处理
                        }

                        if(quantityToConsume.compareTo(BigDecimal.ZERO)==0){
                            //量已经扣减完，则退出
                            break;
                        }
                    }

                    //循环完成后还有剩余未扣除的数量，直接在库中新增一条为负的记录（后期手工核销）
                    if(quantityToConsume.compareTo(BigDecimal.ZERO)==1){
                        ItemConsumeLineDO line = new ItemConsumeLineDO();
                        line.setRecordId(itemConsume.getId());
                        line.setItemId(bom.getItemId());
                        line.setItemCode(bom.getItemCode());
                        line.setItemName(bom.getItemName());
                        line.setSpecification(bom.getSpecification());
                        line.setUnitOfMeasure(bom.getUnitOfMeasure());
                        line.setQuantityConsume(quantityToConsume);
                        line.setBatchCode(workorder.getBatchCode());
                        itemConsumeLineMapper.insert(line);
                    }

                }else {
                    //没有查到领出到线边库的物料，直接在库中新增一条为负的记录(后期可能需要手工核销)
                    ItemConsumeLineDO line = new ItemConsumeLineDO();
                    line.setRecordId(itemConsume.getId());
                    line.setItemId(bom.getItemId());
                    line.setItemCode(bom.getItemCode());
                    line.setItemName(bom.getItemName());
                    line.setSpecification(bom.getSpecification());
                    line.setUnitOfMeasure(bom.getUnitOfMeasure());
                    line.setQuantityConsume(BigDecimal.valueOf(bom.getQuantity()).multiply(BigDecimal.valueOf(feedback.getQuantityFeedback())));
                    line.setBatchCode(workorder.getBatchCode());
                    itemConsumeLineMapper.insert(line);
                }
            }
        }else {
            return  null; //如果本道工序没有配置BOM物料，则直接返回空
        }*/
        // 根据当前的任务编号， 找寻Bom上料详情的物料信息

        // 更新领料单身信息, 绑定报工单号
        IssueHeaderExportReqVO issueHeaderExportReqVO =   new IssueHeaderExportReqVO();
        issueHeaderExportReqVO.setTaskCode(task.getTaskCode()); // 一个任务单绑定一个领料单
        IssueHeaderDO issueHeader = Optional.ofNullable(issueHeaderService.getIssueHeaderList(issueHeaderExportReqVO).get(0)).orElse(null);
        if(issueHeader != null){
            IssueLineExportReqVO issueLineExportReqVO = new IssueLineExportReqVO();
            issueLineExportReqVO.setIssueId(issueHeader.getId());
            issueLineExportReqVO.setStatus("Y"); // 已上料
            issueLineExportReqVO.setFeedbackStatus("N"); // 未报工
            issueLineExportReqVO.setMachineryId(String.valueOf(feedback.getMachineryId()));
            List<IssueLineDO> issueLines = issueLineService.getIssueLineList(issueLineExportReqVO);

            if(!issueLines.isEmpty()){
                // 更新上料详情
                issueLines.forEach(issueLine -> {
                    issueLine.setFeedbackCode(feedback.getFeedbackCode()); // 绑定报工单号
                    issueLine.setFeedbackStatus("Y"); // 已报工
                });
                issueLineService.updateIssueLineBatch(issueLines);
                // 校验当前报工单对应领料单, 机台信息是否存在已启用信息
                // 基于启用信息绑定物料
                IssueLineExportReqVO ReqVO = new IssueLineExportReqVO();
                ReqVO.setMachineryId(String.valueOf(feedback.getMachineryId()));
                ReqVO.setEnableFlag("true"); // 已启用
                ReqVO.setIssueId(issueHeader.getId());
                List<IssueLineDO> doubleCheckLines = issueLineService.getIssueLineList(ReqVO);
                if(!doubleCheckLines.isEmpty()){
                    // 校验当前报工单是否已绑定
                    // 基于,拆分doubleCheckLines的feedbackCode字段, 与issueLines每一行对应的feedbackCode字段进行比对
                    // 确保doubleCheckLines的feedbackCode字段, 都与issueLines每一行对应的feedbackCode字段进行比对, 出现不匹配的, 则进行追加关联关系
                    // 否则, 则直接跳过
                    doubleCheckLines.forEach(issueLine -> {
                        if(issueLine.getFeedbackCode().contains(feedback.getFeedbackCode())){
                            // 匹配, 则跳过
                        }else{
                            // 不匹配, 则追加关联关系
                            StringBuffer feedBackStr = new StringBuffer();
                            feedBackStr.append(issueLine.getFeedbackCode()).append(",").append(feedback.getFeedbackCode());
                            issueLine.setFeedbackCode(feedBackStr.toString()); // 绑定报工单号
                            issueLine.setEnableFlag("false"); // 清除启用标识信息
                        }
                    });
                }
            }else{
                // 校验当前报工单对应领料单, 机台信息是否存在已启用信息
                // 基于启用信息绑定物料
                IssueLineExportReqVO ReqVO = new IssueLineExportReqVO();
                ReqVO.setMachineryId(String.valueOf(feedback.getMachineryId()));
                ReqVO.setEnableFlag("true"); // 已启用
                ReqVO.setIssueId(issueHeader.getId());
                List<IssueLineDO> doubleCheckLines = issueLineService.getIssueLineList(ReqVO);
                if(!doubleCheckLines.isEmpty()){
                    // 开始追加关联关系
                    doubleCheckLines.forEach(issueLine -> {
                        StringBuffer feedBackStr = new StringBuffer();
                        feedBackStr.append(issueLine.getFeedbackCode()).append(",").append(feedback.getFeedbackCode());
                        issueLine.setFeedbackCode(feedBackStr.toString()); // 绑定报工单号
                        issueLine.setEnableFlag("false"); // 清除启用标识信息
                    });
                    issueLineService.updateIssueLineBatch(doubleCheckLines);
                }
            }
        }

        FeedLineExportReqVO exportReqVO = new FeedLineExportReqVO();
        exportReqVO.setTaskCode(feedback.getTaskCode());
        exportReqVO.setFeedbackStatus("N"); // 获取当前未报工的上料详情
        exportReqVO.setMachineryId(String.valueOf(feedback.getMachineryId())); // 追加设备Id进行卡控
        List<FeedLineDO> feedLines = feedLineService.getFeedLineList(exportReqVO);
        if(feedLines.isEmpty()){
            //如果没有找到Bom上料详情，则直接返回空
            return null;
        }
        List<ItemConsumeLineDO> lines = new ArrayList<>();
        for (FeedLineDO feedLine: feedLines) {
            // 根据当前上料记录创建物料消耗
            ItemConsumeLineDO line = new ItemConsumeLineDO();
            line.setRecordId(itemConsume.getId());
            line.setItemId(feedLine.getItemId());
            line.setItemCode(feedLine.getItemCode());
            line.setItemName(feedLine.getItemName());
            line.setSpecification(feedLine.getSpecification());
            line.setUnitOfMeasure(feedLine.getUnitOfMeasure());
            line.setQuantityConsume(BigDecimal.valueOf(feedLine.getQuantity()));
            line.setBatchCode(feedLine.getBatchCode());
            line.setMaterialStockId(feedLine.getMaterialStockId());
            line.setLocationCode(feedLine.getLocationCode());
            line.setLocationId(feedLine.getLocationId());
            line.setLocationName(feedLine.getLocationName());
            line.setAreaCode(feedLine.getAreaCode());
            line.setAreaId(feedLine.getAreaId());
            line.setAreaName(feedLine.getAreaName());
            lines.add(line);
        }
        itemConsumeLineMapper.insertBatch(lines);

        // 更新上料详情
        feedLines.forEach(feedLine -> {
            feedLine.setFeedbackCode(feedback.getFeedbackCode()); // 绑定报工单号
            feedLine.setFeedbackStatus("Y"); // 已报工
        });
        feedLineService.updateFeedLineBatch(feedLines);

        return itemConsume;
    }

    @Override
    public List<ItemConsumeTxBean> getTxBeans(Long recordId) {
        return itemConsumeMapper.getTxBeans(recordId);
    }

    @Override
    public List<Map<String, Object>> getWeeklyConsumeSummary() {
        LocalDate now = LocalDate.now();

        // 计算时间范围
        LocalDate currentWeekStart = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate currentWeekEnd = currentWeekStart.plusDays(6);
        LocalDate lastWeekStart = currentWeekStart.minusWeeks(1);
        LocalDate lastWeekEnd = lastWeekStart.plusDays(6);

        // 转换为DateTime
        LocalDateTime currentWeekStartTime = currentWeekStart.atStartOfDay();
        LocalDateTime currentWeekEndTime = currentWeekEnd.atTime(23, 59, 59);
        LocalDateTime lastWeekStartTime = lastWeekStart.atStartOfDay();
        LocalDateTime lastWeekEndTime = lastWeekEnd.atTime(23, 59, 59);

        // 查询数据
        List<Map<String, Object>> dataList = itemConsumeMapper.selectWeeklyConsume(
                currentWeekStartTime, currentWeekEndTime,
                lastWeekStartTime, lastWeekEndTime
        );

        // 构建响应
        List<Map<String, Object>> source = dataList.stream()
                .map(data -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("product", data.get("product"));
                    map.put("data1", data.get("current_week"));
                    map.put("data2", data.get("last_week"));
                    return map;
                })
                .collect(Collectors.toList());
        return source;
    }

}
