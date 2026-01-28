package com.dofast.module.pro.controller.pad.profeedbackmob;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.dofast.framework.common.pad.controller.PadBaseController;
import com.dofast.framework.common.pad.page.PadTableDataInfo;
import com.dofast.framework.common.pad.pojo.PadLoginUser;
import com.dofast.framework.common.pad.util.PadSecurityUtils;
import com.dofast.framework.common.pad.util.PadStringUtils;
import com.dofast.framework.common.pojo.AjaxResult;
import com.dofast.framework.common.pojo.UserConstants;
import com.dofast.framework.common.util.collection.CollectionUtils;
import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.framework.security.core.annotations.PreAuthenticated;
import com.dofast.framework.tenant.core.aop.TenantIgnore;
import com.dofast.framework.web.core.util.WebFrameworkUtils;
import com.dofast.module.mes.dal.dataobject.mditem.MdItemDO;
import com.dofast.module.mes.dal.dataobject.mdworkstation.MdWorkstationDO;
import com.dofast.module.mes.dal.dataobject.mdworkstationworker.MdWorkstationWorkerDO;
import com.dofast.module.mes.service.mditem.MdItemService;
import com.dofast.module.mes.service.mdworkstation.MdWorkstationService;
import com.dofast.module.mes.service.mdworkstationworker.MdWorkstationWorkerService;
import com.dofast.module.pro.api.FeedbackApi.dto.FeedbackDTO;
import com.dofast.module.pro.api.WorkorderApi.dto.WorkorderDTO;
import com.dofast.module.pro.controller.admin.feedback.vo.FeedbackCreateReqVO;
import com.dofast.module.pro.controller.admin.feedback.vo.FeedbackExportReqVO;
import com.dofast.module.pro.controller.admin.feedback.vo.FeedbackUpdateReqVO;
import com.dofast.module.pro.controller.admin.task.vo.TaskUpdateReqVO;
import com.dofast.module.pro.controller.admin.workorder.vo.WorkorderExportReqVO;
import com.dofast.module.pro.controller.admin.workorder.vo.WorkorderUpdateReqVO;
import com.dofast.module.pro.dal.dataobject.feedback.FeedbackDO;
import com.dofast.module.pro.dal.dataobject.task.TaskDO;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.module.pro.service.feedback.FeedbackService;
import com.dofast.module.pro.service.routeprocess.RouteProcessService;
import com.dofast.module.pro.service.task.TaskService;
import com.dofast.module.pro.service.workorder.WorkorderService;
import com.dofast.module.qms.api.oqcApi.OqcApi;
import com.dofast.module.qms.api.oqcApi.dto.OqcDTO;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
import com.dofast.module.trade.api.mixinorder.MixinOrderApi;
import com.dofast.module.trade.api.mixinorder.dto.MixinOrderDTO;
import com.dofast.module.wms.controller.admin.itemconsume.vo.ItemConsumeUpdateReqVO;
import com.dofast.module.wms.controller.admin.itemrecpt.vo.ItemRecptUpdateReqVO;
import com.dofast.module.wms.controller.admin.productproduce.vo.ProductProduceUpdateReqVO;
import com.dofast.module.wms.dal.dataobject.itemconsume.ItemConsumeDO;
import com.dofast.module.wms.dal.dataobject.itemconsume.ItemConsumeTxBean;
import com.dofast.module.wms.dal.dataobject.itemrecpt.ItemRecptDO;
import com.dofast.module.wms.dal.dataobject.itemrecpt.ItemRecptTxBean;
import com.dofast.module.wms.dal.dataobject.productproduce.ProductProduceDO;
import com.dofast.module.wms.dal.dataobject.productproduce.ProductProductTxBean;
import com.dofast.module.wms.service.itemconsume.ItemConsumeService;
import com.dofast.module.wms.service.itemrecpt.ItemRecptService;
import com.dofast.module.wms.service.productproduce.ProductProduceService;
import com.dofast.module.wms.service.productsalse.ProductSalseService;
import com.dofast.module.wms.service.storagecore.StorageCoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Tag(name = "PAD生产管理 - 生产报工记录")
@RestController
@RequestMapping("/mobile/pro/feedback")
@Validated
public class PadFeedbackController extends PadBaseController {

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private WorkorderService workorderService;

    @Resource
    private TaskService taskService;

    @Resource
    private RouteProcessService routeProcessService;

    @Resource
    private ProductProduceService productProduceService;

    @Resource
    private ItemConsumeService itemConsumeService;

    @Resource
    private StorageCoreService storageCoreService;

    @Resource
    private MdItemService mdItemService;

    @Resource
    private ItemRecptService itemRecptService;

    @Resource
    private OqcApi oqcApi;

    @Resource
    private ProductSalseService productSalseService;

    @Resource
    private MixinOrderApi mixinOrderApi;

    @Resource
    private AdminUserApi adminUserApi;

    @Autowired
    private MdWorkstationWorkerService workstationWorkerService;

    @Resource
    private MdWorkstationService mdWorkstationService;



    public PadFeedbackController() {
    }

    /**
     * 新增生产报工记录
     */
    @Operation(summary = "新增报工单接口")
    @PreAuthenticated
    @PostMapping
    @TenantIgnore
    @PermitAll
    public AjaxResult add(@RequestBody FeedbackDO proFeedback)
    {
        MdWorkstationDO workstation = mdWorkstationService.getMdWorkstation(proFeedback.getWorkstationId());
        if(PadStringUtils.isNotNull(workstation)){
            proFeedback.setProcessId(workstation.getProcessId());
            proFeedback.setProcessCode(workstation.getProcessCode());
            proFeedback.setProcessName(workstation.getProcessName());
        }else {
            return AjaxResult.error("当前生产任务对应的工作站不存在！");
        }
        Long userId = PadSecurityUtils.getUserId();

        AdminUserRespDTO user = adminUserApi.getUser(userId);
        proFeedback.setCreator(user.getUsername());
        FeedbackCreateReqVO feedbackCreateReqVO  = BeanUtil.toBean(proFeedback, FeedbackCreateReqVO.class);
        feedbackService.createFeedback(feedbackCreateReqVO);
        return AjaxResult.success(proFeedback);
    }

    @PutMapping
    @Operation(summary = "报工修改接口")
    @PreAuthenticated
    @TenantIgnore
    @PermitAll
    public AjaxResult edit(@RequestBody FeedbackDO proFeedback)
    {
        FeedbackUpdateReqVO feedbackUpdateReqVO = BeanUtil.toBean(proFeedback, FeedbackUpdateReqVO.class);
        feedbackService.updateFeedback(feedbackUpdateReqVO);
        return toAjax(true);
    }

    /**
     * 查询生产报工记录列表
     */
    @Operation(summary = "查询报工单清单-全部")
    @PreAuthenticated
    @TenantIgnore
    @PermitAll
    @GetMapping("/list")
    public PadTableDataInfo list(FeedbackDO proFeedback)
    {
        FeedbackExportReqVO feedbackExportReqVO = BeanUtil.toBean(proFeedback, FeedbackExportReqVO.class);
        List<FeedbackDO> list = feedbackService.getFeedbackList(feedbackExportReqVO);
        return getDataTable(list);
    }

    /**
     * 查询生产报工记录列表
     */
    @Operation(summary = "查询报工单清单-未审批通过的")
    @PreAuthenticated
    @TenantIgnore
    @PermitAll
    @GetMapping("/listUnApproved")
    public PadTableDataInfo listUnApproved(FeedbackDO feedbackDO) {
        FeedbackExportReqVO feedbackExportReqVO = BeanUtil.toBean(feedbackDO, FeedbackExportReqVO.class);
        List<FeedbackDO> all = new ArrayList<>();
        feedbackExportReqVO.setStatus(UserConstants.ORDER_STATUS_PREPARE);
        List<FeedbackDO> list1 = feedbackService.getFeedbackList(feedbackExportReqVO);
        all.addAll(list1);
        feedbackExportReqVO.setStatus(UserConstants.ORDER_STATUS_APPROVING);
        List<FeedbackDO> list2 = feedbackService.getFeedbackList(feedbackExportReqVO);
        all.addAll(list2);
        return getDataTable(all);
    }

    /**
     * 查询生产报工记录列表
     */
    @Operation(summary = "查询报工单清单-已审批通过的")
    @PreAuthenticated
    @PermitAll
    @TenantIgnore
    @GetMapping("/listApproved")
    public PadTableDataInfo listApproved(FeedbackDO feedbackDO) {
        FeedbackExportReqVO feedbackExportReqVO = BeanUtil.toBean(feedbackDO, FeedbackExportReqVO.class);
        feedbackExportReqVO.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        List<FeedbackDO> list = feedbackService.getFeedbackList(feedbackExportReqVO);
        return getDataTable(list);
    }

    /**
     * 删除生产报工记录
     */
    @Operation(summary = "删除报工单")
    @PreAuthenticated
    @TenantIgnore
    @PermitAll
    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        if (recordIds != null){
            for(Long id : recordIds){
                feedbackService.deleteFeedback(id);
            }
        }
        return toAjax(true);
    }

    @PutMapping("/one-click-create")
    @Operation(summary = "一键报工")
    @Transactional
    @PreAuthenticated
    @TenantIgnore
    @PermitAll
    public AjaxResult OneClickCreate(@RequestBody FeedbackDO feedback) {
        WorkorderDO workorder = workorderService.getWorkorder(feedback.getWorkorderId());
        if (workorder.getQuantityProduced() > feedback.getQuantity()){
            return AjaxResult.error(716004, "请勿重复报工");
        }

        if(feedback.getQuantityFeedback().compareTo(Double.valueOf(0)) !=1){
            return AjaxResult.error(716002, "报工数量必须大于0");
        }

        //更新生产任务的生产数量
        TaskDO task = taskService.getTask(feedback.getTaskId());
        Double quantityProduced,quantityQuanlify,quantityUnquanlify;
        quantityQuanlify = task.getQuantityQuanlify()==null? 0:task.getQuantityQuanlify();
        quantityUnquanlify = task.getQuantityUnquanlify() ==null? 0:task.getQuantityUnquanlify();
        quantityProduced = task.getQuantityProduced()==null? 0:task.getQuantityProduced();
        task.setQuantityProduced(feedback.getQuantityFeedback());
        task.setQuantityQuanlify(quantityQuanlify + (feedback.getQuantityQualified()));
        task.setQuantityUnquanlify(quantityUnquanlify + feedback.getQuantityUnquanlified());
        task.setDeleted(true);
        TaskUpdateReqVO taskUpdateReqVO = BeanUtil.toBean(task, TaskUpdateReqVO.class);
        taskService.updateTask(taskUpdateReqVO);
        //更新工单的生产数量
        workorder.setQuantityProduced(quantityProduced + feedback.getQuantityFeedback());
        WorkorderUpdateReqVO workorderUpdateReqVO = BeanUtil.toBean(workorder, WorkorderUpdateReqVO.class);
        workorderService.updateWorkorder(workorderUpdateReqVO);

        //如果是关键工序，则更新当前工单的已生产数量，进行产品产出动作
        checkKeyProcess(feedback, workorder);

        //父工单操作
        parentOrder(feedback, workorder);

        //根据当前工序的物料BOM配置，进行物料消耗
        //先生成消耗单
        FeedbackDTO feedbackDTO = BeanUtil.toBean(feedback, FeedbackDTO.class);
        ItemConsumeDO itemConsume = itemConsumeService.generateItemConsume(feedbackDTO);
        if(StrUtils.isNotNull(itemConsume)){
            //再执行库存消耗动作
            executeItemConsume(itemConsume);
        }

        //更新报工单的状态
        feedback.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        FeedbackUpdateReqVO feedbackUpdateReqVO = BeanUtil.toBean(feedback, FeedbackUpdateReqVO.class);
        feedbackService.updateFeedback(feedbackUpdateReqVO);
        return success("报工成功，请继续扫码");
    }

    private void checkKeyProcess(FeedbackDO feedback, WorkorderDO workorder){
        //如果是关键工序，则更新当前工单的已生产数量，进行产品产出动作
        if(routeProcessService.checkKeyProcess(feedback)){
            MdItemDO itemDO = mdItemService.getMdItem(workorder.getProductId());
            //更新生产工单的生产数量
            Double produced = workorder.getQuantityProduced() == null?0:workorder.getQuantityProduced();
            Double feedBackQuantity = feedback.getQuantityFeedback() ==null?0:feedback.getQuantityFeedback();
            workorder.setQuantityProduced( produced + feedBackQuantity);
            WorkorderUpdateReqVO workorderUpdateReqVO = BeanUtil.toBean(workorder, WorkorderUpdateReqVO.class);
            workorderService.updateWorkorder(workorderUpdateReqVO);

            FeedbackDTO feedbackDTO = BeanUtil.toBean(feedback, FeedbackDTO.class);

            //判断产品里的是产品还是物料
            if(itemDO.getItemOrProduct().equals("ITEM")){
                //生成物料产出记录单
                ItemRecptDO itemRecptDO = itemRecptService.generateItemRecpt(feedbackDTO);
                //执行物料产出入线边库
                executeItemProduce(itemRecptDO);
            }else {
                //生成产品产出记录单
                // ProductProduceDO productRecord = productProduceService.generateProductProduce(feedbackDTO);
                //执行产品产出入线边库
                // executeProductProduce(productRecord);
            }

        }
    }

    private void parentOrder(FeedbackDO feedback, WorkorderDO workorder){
        //父工单操作
        if (workorder.getParentId() != null && workorder.getParentId() != 0 && routeProcessService.checkKeyProcess(feedback)){
            WorkorderDO parentOrder = workorderService.getWorkorder(workorder.getParentId());
            if (parentOrder.getParentId() != null && parentOrder.getParentId() != 0){
                parentOrder(feedback, parentOrder);
            }
            checkKeyProcess(feedback, parentOrder);
            //创建出货检验单
            FeedbackDTO feedbackDTO = BeanUtil.toBean(feedback, FeedbackDTO.class);
            WorkorderDTO workorderDTO = BeanUtil.toBean(parentOrder, WorkorderDTO.class);
            OqcDTO oqcDTO = oqcApi.generateOqc(feedbackDTO, workorderDTO);
            //创建销售出库单
            productSalseService.generateProductSale(feedbackDTO, workorderDTO, oqcDTO);
        }
    }

    /**
     * 执行物料消耗库存动作
     * @param record
     */
    private void executeItemConsume(ItemConsumeDO record){
        //需要在此处进行分批次领料的线边库扣减
        List<ItemConsumeTxBean> beans = itemConsumeService.getTxBeans(record.getId());
        storageCoreService.processItemConsume(beans);
        record.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        ItemConsumeUpdateReqVO itemConsumeUpdateReqVO = BeanUtil.toBean(record, ItemConsumeUpdateReqVO.class);
        itemConsumeService.updateItemConsume(itemConsumeUpdateReqVO);
    }

    /**
     * 执行物料产出入线边库动作
     * @param record
     */
    private void executeItemProduce(ItemRecptDO record){
        List<ItemRecptTxBean> beans = itemRecptService.getTxBeans(record.getId());
        storageCoreService.processItemRecpt(beans);
        record.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        ItemRecptUpdateReqVO itemRecptUpdateReqVO = BeanUtil.toBean(record, ItemRecptUpdateReqVO.class);
        itemRecptService.updateItemRecpt(itemRecptUpdateReqVO);
    }

    /**
     * 执行产品产出入线边库动作
     * @param record
     */
    private void executeProductProduce(ProductProduceDO record){
        List<ProductProductTxBean> beans = productProduceService.getTxBeans(record.getId());
        storageCoreService.processProductProduce(beans);
        record.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        ProductProduceUpdateReqVO productProduceUpdateReqVO = BeanUtil.toBean(record, ProductProduceUpdateReqVO.class);
        productProduceService.updateProductProduce(productProduceUpdateReqVO);
    }

    @PostMapping("/one-click-feedback-create")
    @Operation(summary = "一键报工创建报工记录")
    @PreAuthenticated
    @TenantIgnore
    @PermitAll
    public AjaxResult OneClickFeedbackCreate(@RequestBody TaskDO task) {
//        FeedbackDO proFeedback = BeanUtil.toBean(task, FeedbackDO.class);
        FeedbackDO proFeedback = new FeedbackDO();

        BeanUtil.copyProperties(task,proFeedback,new String[]{"id"});
        proFeedback.setTaskId(task.getId());
        proFeedback.setStatus("PREPARE");
        proFeedback.setQuantityQualified(task.getQuantityQuanlify());
        proFeedback.setQuantityUnquanlified(task.getQuantityUnquanlify());
        MdWorkstationDO workstation = mdWorkstationService.getMdWorkstation(proFeedback.getWorkstationId());
        if(PadStringUtils.isNotNull(workstation)){
            proFeedback.setProcessId(workstation.getProcessId());
            proFeedback.setProcessCode(workstation.getProcessCode());
            proFeedback.setProcessName(workstation.getProcessName());
        }else {
            return AjaxResult.error("当前生产任务对应的工作站不存在！");
        }
        Long userId = PadSecurityUtils.getUserId();

        AdminUserRespDTO user = adminUserApi.getUser(userId);
        proFeedback.setCreator(user.getUsername());
        FeedbackCreateReqVO feedbackCreateReqVO  = BeanUtil.toBean(proFeedback, FeedbackCreateReqVO.class);
        Long id = feedbackService.createFeedback(feedbackCreateReqVO);
        proFeedback.setId(id);
        return AjaxResult.success(proFeedback);
    }
}
