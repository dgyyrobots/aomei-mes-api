package com.dofast.module.pro.controller.admin.feedback;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.dofast.framework.common.exception.ErrorCode;
import com.dofast.framework.common.exception.ServiceException;
import com.dofast.framework.common.pad.util.PadStringUtils;
import com.dofast.framework.common.pojo.AjaxResult;
import com.dofast.framework.common.pojo.CommonResult;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.UserConstants;
import com.dofast.framework.common.util.bean.BeanUtils;
import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.framework.excel.core.util.ExcelUtils;
import com.dofast.framework.operatelog.core.annotations.OperateLog;
import com.dofast.framework.security.core.annotations.PreAuthenticated;
import com.dofast.framework.security.core.util.SecurityFrameworkUtils;
import com.dofast.framework.tenant.core.aop.TenantIgnore;
import com.dofast.framework.web.core.util.WebFrameworkUtils;
import com.dofast.module.cal.api.team.TeamApi;
import com.dofast.module.cal.api.team.dto.TeamDTO;
import com.dofast.module.cal.api.team.dto.TeamMemberDTO;
import com.dofast.module.cal.dal.dataobject.teammember.TeamMemberDO;
import com.dofast.module.cmms.api.dvmachinery.DvMachineryApi;
import com.dofast.module.cmms.api.dvmachinery.dto.DvMachineryDTO;
import com.dofast.module.mes.aspect.BarcodeGen;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.mes.controller.admin.mditem.vo.MdItemExportReqVO;
import com.dofast.module.mes.dal.dataobject.mditem.MdItemDO;
import com.dofast.module.mes.dal.dataobject.mditemtype.MdItemTypeDO;
import com.dofast.module.mes.dal.dataobject.mdworkstation.MdWorkstationDO;
import com.dofast.module.mes.service.mditem.MdItemService;
import com.dofast.module.mes.service.mditemtype.MdItemTypeService;
import com.dofast.module.mes.service.mdworkstation.MdWorkstationService;
import com.dofast.module.mes.service.mdworkstationworker.MdWorkstationWorkerService;
import com.dofast.module.pro.api.FeedbackApi.dto.FeedbackDTO;
import com.dofast.module.pro.api.TaskApi.TaskApi;
import com.dofast.module.pro.api.TaskApi.dto.TaskDTO;
import com.dofast.module.pro.api.WorkorderApi.WorkorderApi;
import com.dofast.module.pro.api.WorkorderApi.dto.WorkorderDTO;
import com.dofast.module.pro.controller.admin.feedback.vo.*;
import com.dofast.module.pro.controller.admin.feedbackdefect.vo.FeedbackDefectCreateReqVO;
import com.dofast.module.pro.controller.admin.feedbackdefect.vo.FeedbackDefectExportReqVO;
import com.dofast.module.pro.controller.admin.feedbackdefect.vo.FeedbackDefectUpdateReqVO;
import com.dofast.module.pro.controller.admin.feedbackmember.vo.FeedbackMemberCreateReqVO;
import com.dofast.module.pro.controller.admin.feedbackmember.vo.FeedbackMemberExportReqVO;
import com.dofast.module.pro.controller.admin.feedbackmember.vo.FeedbackMemberPageReqVO;
import com.dofast.module.pro.controller.admin.feedbackmember.vo.FeedbackMemberUpdateReqVO;
import com.dofast.module.pro.controller.admin.routeprocess.vo.RouteProcessExportReqVO;
import com.dofast.module.pro.controller.admin.task.vo.TaskExportReqVO;
import com.dofast.module.pro.controller.admin.task.vo.TaskUpdateReqVO;
import com.dofast.module.pro.controller.admin.workorder.vo.WorkorderUpdateReqVO;
import com.dofast.module.pro.convert.feedback.FeedbackConvert;
import com.dofast.module.pro.convert.feedbackdefect.FeedbackDefectConvert;
import com.dofast.module.pro.convert.feedbackmember.FeedbackMemberConvert;
import com.dofast.module.pro.convert.task.TaskConvert;
import com.dofast.module.pro.dal.dataobject.feedback.FeedbackDO;
import com.dofast.module.pro.dal.dataobject.feedbackdefect.FeedbackDefectDO;
import com.dofast.module.pro.dal.dataobject.feedbackmember.FeedbackMemberDO;
import com.dofast.module.pro.dal.dataobject.feedbackwarehousinglog.FeedbackWarehousingLogDO;
import com.dofast.module.pro.dal.dataobject.routeprocess.RouteProcessDO;
import com.dofast.module.pro.dal.dataobject.task.TaskDO;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.module.pro.enums.ErrorCodeConstants;
import com.dofast.module.pro.service.feedback.FeedbackService;
import com.dofast.module.pro.service.feedbackdefect.FeedbackDefectService;
import com.dofast.module.pro.service.feedbackmember.FeedbackMemberService;
import com.dofast.module.pro.service.feedbackwarehousinglog.FeedbackWarehousingLogService;
import com.dofast.module.pro.service.process.ProcessOracleService;
import com.dofast.module.pro.service.route.RouteOracleService;
import com.dofast.module.pro.service.route.RouteService;
import com.dofast.module.pro.service.routeprocess.RouteProcessService;
import com.dofast.module.pro.service.task.TaskService;
import com.dofast.module.pro.service.workorder.WorkorderService;
import com.dofast.module.qms.api.oqcApi.OqcApi;
import com.dofast.module.qms.api.oqcApi.dto.OqcDTO;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
import com.dofast.module.trade.api.mixinorder.MixinOrderApi;
import com.dofast.module.wms.api.ERPApi.WorkorderERPAPI;
import com.dofast.module.wms.api.Issueheader.IssueApi;
import com.dofast.module.wms.api.Issueheader.dto.IssueLineDTO;
import com.dofast.module.wms.api.Issueheader.dto.IssueheaderDTO;
import com.dofast.module.wms.api.WarehosueApi.dto.WarehouseDTO;
import com.dofast.module.wms.api.WarehouseApi.WarehouseApiImpl;
import com.dofast.module.wms.controller.admin.issueheader.vo.IssueHeaderExportReqVO;
import com.dofast.module.wms.controller.admin.issueline.vo.IssueLineExportReqVO;
import com.dofast.module.wms.controller.admin.itemconsume.vo.ItemConsumeExportReqVO;
import com.dofast.module.wms.controller.admin.itemconsume.vo.ItemConsumeUpdateReqVO;
import com.dofast.module.wms.controller.admin.itemconsumeline.vo.ItemConsumeLineExportReqVO;
import com.dofast.module.wms.controller.admin.itemrecpt.vo.ItemRecptUpdateReqVO;
import com.dofast.module.wms.controller.admin.materialstock.vo.MaterialStockCreateReqVO;
import com.dofast.module.wms.controller.admin.materialstock.vo.MaterialStockExportReqVO;
import com.dofast.module.wms.controller.admin.materialstock.vo.MaterialStockUpdateReqVO;
import com.dofast.module.wms.controller.admin.productproduce.vo.ProductProduceExportReqVO;
import com.dofast.module.wms.controller.admin.productproduce.vo.ProductProduceUpdateReqVO;
import com.dofast.module.wms.controller.admin.productproduceline.vo.ProductProduceLineExportReqVO;
import com.dofast.module.wms.controller.admin.storagearea.vo.StorageAreaExportReqVO;
import com.dofast.module.wms.controller.admin.transaction.vo.TransactionUpdateReqVO;
import com.dofast.module.wms.convert.issueline.IssueLineConvert;
import com.dofast.module.wms.convert.itemconsume.ItemConsumeConvert;
import com.dofast.module.wms.convert.materialstock.MaterialStockConvert;
import com.dofast.module.wms.convert.productproduce.ProductProduceConvert;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import com.dofast.module.wms.dal.dataobject.itemconsume.ItemConsumeDO;
import com.dofast.module.wms.dal.dataobject.itemconsume.ItemConsumeTxBean;
import com.dofast.module.wms.dal.dataobject.itemconsumeline.ItemConsumeLineDO;
import com.dofast.module.wms.dal.dataobject.itemrecpt.ItemRecptDO;
import com.dofast.module.wms.dal.dataobject.itemrecpt.ItemRecptTxBean;
import com.dofast.module.wms.dal.dataobject.materialstock.MaterialStockDO;
import com.dofast.module.wms.dal.dataobject.productproduce.ProductProduceDO;
import com.dofast.module.wms.dal.dataobject.productproduce.ProductProductTxBean;
import com.dofast.module.wms.dal.dataobject.productproduceline.ProductProduceLineDO;
import com.dofast.module.wms.dal.dataobject.storagearea.StorageAreaDO;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import com.dofast.module.wms.dal.dataobject.warehouse.WarehouseDO;
import com.dofast.module.wms.dal.mysql.itemconsumeline.ItemConsumeLineMapper;
import com.dofast.module.wms.dal.mysql.productproduceline.ProductProduceLineMapper;
import com.dofast.module.wms.service.issueheader.IssueHeaderService;
import com.dofast.module.wms.service.issueline.IssueLineService;
import com.dofast.module.wms.service.itemconsume.ItemConsumeService;
import com.dofast.module.wms.service.itemrecpt.ItemRecptService;
import com.dofast.module.wms.service.materialstock.MaterialStockService;
import com.dofast.module.wms.service.productproduce.ProductProduceService;
import com.dofast.module.wms.service.productproduceline.ProductProduceLineService;
import com.dofast.module.wms.service.productsalse.ProductSalseService;
import com.dofast.module.wms.service.storagearea.StorageAreaService;
import com.dofast.module.wms.service.storagecore.StorageCoreService;
import com.dofast.module.wms.service.storagelocation.StorageLocationService;
import com.dofast.module.wms.service.transaction.TransactionService;
import com.dofast.module.wms.service.warehouse.WarehouseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.poi.ss.formula.constant.ErrorConstant;
import org.apache.poi.ss.formula.functions.T;
import org.apache.tika.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.dofast.module.pro.dal.dataobject.route.RouteDO;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import static com.dofast.framework.common.pad.util.PadSecurityUtils.getUsername;
import static cn.hutool.core.date.DateUtil.formatDate;
import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.common.pojo.CommonResult.success;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static com.dofast.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.dofast.module.pro.enums.ErrorCodeConstants.QUENTITYP_RODUCED_IS_MORE;
import org.springframework.data.redis.core.RedisTemplate;

@Tag(name = "生产管理 - 生产报工记录")
@RestController
@RequestMapping("/mes/pro/feedback")
@Validated
public class FeedbackController {

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
    private MdWorkstationService workstationService;

    @Resource
    private MdWorkstationService mdWorkstationService;

    @Resource
    private RouteService routeService;

    @Resource
    private StorageLocationService locationService;

    @Resource
    private StorageAreaService areaService;

    @Resource
    private MaterialStockService materialStockService;

    @Resource
    private MdItemTypeService mdItemTypeService;

    @Resource
    private WarehouseApiImpl warehouseApiImpl;

    @Resource
    private FeedbackMemberService feedBackMemberService;

    @Resource
    private FeedbackDefectService feedbackDefectService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WarehouseService warehouseService;

    @Resource
    private StorageLocationService storageLocationService;

    @Resource
    private StorageAreaService storageAreaService;

    @Resource
    private DvMachineryApi dvMachineryApi;

    @Resource
    private IssueApi issueApi;

    @Resource
    private IssueLineService issueLineService;

    @Resource
    private ProductProduceLineMapper productProduceLineMapper;

    @Resource
    private ItemConsumeLineMapper itemConsumeLineMapper;

    @Resource
    private WorkorderERPAPI workorderERPAPI;

    @Resource
    private RouteOracleService routeOracleService;

    @Resource
    private ProcessOracleService processOracleService;

    @Resource
    private TeamApi teamApi;

    @Resource
    private FeedbackWarehousingLogService feedbackWarehousingLogService;

    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping("/create")
    @Operation(summary = "创建生产报工记录")
    @PreAuthorize("@ss.hasPermission('pro:feedback:create')")
    @Transactional
    public CommonResult<Long> createFeedback(@Valid @RequestBody FeedbackCreateReqVO createReqVO) {
        List<Map<String, Object>> list = createReqVO.getFeedbackMemberList();
        List<Map<String, Object>> defectlist = createReqVO.getProcessDefectList();
        // 打样工单 判定标识
        boolean proof = "AMGD01".equals(createReqVO.getWorkorderCode().split("-")[0]);

        WorkorderDO workorderDO = workorderService.getWorkorder(createReqVO.getWorkorderCode());
        if ("FINISHED".equals(workorderDO.getStatus())) {
            return error(com.dofast.module.wms.enums.ErrorCodeConstants.WORKORDER_FINSHED_NOT_AVALIABLE);
        }

        if (createReqVO.getQuantityQualified().equals(0)) {
            return error(com.dofast.module.wms.enums.ErrorCodeConstants.QUENTITYP_QUALIFIED_NULL);
        }

        // 获得用户基本信息
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        AdminUserRespDTO userDTO = adminUserApi.getUser(loginUserId);
        createReqVO.setNickName(userDTO.getNickname());
        createReqVO.setUserName(userDTO.getUsername());

        // 2025-6-5 追加转换单位信息
        if ("AM006".equals(createReqVO.getProcessCode()) && "BF".equals(createReqVO.getMachineryCode().split("-")[1].substring(0, 2))) {
            Double quantity = createReqVO.getQuantityQualified();
            Double quantityUnqualified = createReqVO.getQuantityUnquanlified();

            // 获取工序
            Map<String, Object> conversionMap = initConversionQuantity(createReqVO);
            String conversionUnit = conversionMap.get("outUnit").toString();
            BigDecimal convert = new BigDecimal(conversionMap.get("outNumber").toString());

            BigDecimal qualityQuantity = new BigDecimal(quantity.toString());
            // 将qualityQuantity除以convert, 取4位小数
            BigDecimal conversionQuantity = qualityQuantity.divide(convert, 1, BigDecimal.ROUND_HALF_UP);

            createReqVO.setConversionUnit(conversionUnit);
            createReqVO.setConversionQuantity(conversionQuantity);
            // 追加不合格数量
            BigDecimal unqualityQuantity = new BigDecimal(quantityUnqualified.toString());
            if (unqualityQuantity.compareTo(BigDecimal.ZERO) != 0) {
                // 将qualityQuantity除以convert, 取4位小数
                BigDecimal conversionQuantityUnquality = unqualityQuantity.divide(convert, 1, BigDecimal.ROUND_HALF_UP);
                createReqVO.setConversionQuantityUnquanlified(conversionQuantityUnquality);
            }
        }

        // 判定任务状态是否正常
        if (!"STARTED".equals(createReqVO.getTaskStatus()) && !"FINISHED".equals(createReqVO.getTaskStatus())) {
            return error(ErrorCodeConstants.FEEDBACK_TASK_STATUS_ERROR);
        }

        // 2025-03-13 追加需求: 判定当前任务单对应的领料单是否存在未上料单据信息, 存在则不允许其进行报工操作
        // 基于任务单获取生产领料单
        String taskCode = createReqVO.getTaskCode();
        TaskDO task = taskService.getTask(taskCode);

        IssueheaderDTO issueHeader = new IssueheaderDTO();
        issueHeader.setTaskId(task.getId());
        issueHeader.setWorkorderCode(createReqVO.getWorkorderCode());
        List<IssueheaderDTO> issueHeaderList = issueApi.listIssueHeader(issueHeader);

        String frontBatchCode = null;
        String frontFeedbackCode = null;
        String frontTeamCode = null;

        if (proof) {
            if (issueHeaderList.isEmpty()) {
                return error(ErrorCodeConstants.ISSUE_NOT_EXISTS);
            }
            IssueheaderDTO issueHeaderDTO = issueHeaderList.get(0);
            // 基于生产领料单获取已上料未报工的生产领料单行
            IssueLineDTO issueLine = new IssueLineDTO();
            issueLine.setIssueId(issueHeaderDTO.getId());
            issueLine.setStatus("Y");
            issueLine.setFeedbackStatus("N");
            issueLine.setMachineryCode(createReqVO.getMachineryCode());
            List<IssueLineDTO> issueLineList = issueApi.listIssueLine(issueLine);

            // 获取当前已报工且勾选已启用的物料
            IssueLineDTO enableIssue = new IssueLineDTO();
            enableIssue.setIssueId(issueHeaderDTO.getId());
            enableIssue.setFeedbackStatus("Y");
            enableIssue.setEnableFlag("true");
            enableIssue.setMachineryCode(createReqVO.getMachineryCode());
            List<IssueLineDTO> enableIssueList = issueApi.listIssueLine(enableIssue);

            if (issueLineList.isEmpty() && enableIssueList.isEmpty()) {
                return error(ErrorCodeConstants.TASK_NOT_RECEPT);
            }

            // 检查所有批次号是否都有对应的报工单
            List<String> allBatchCodes = Stream.concat(issueLineList.stream(), enableIssueList.stream())
                    .filter(Objects::nonNull)
                    .map(IssueLineDTO::getBatchCode)
                    .collect(Collectors.toList());

            for (String batchCode : allBatchCodes) {
                // 判定batchCode 是否为TASK开头
                if (batchCode.startsWith("TASK")) {
                    List<FeedbackDO> feedbackDOList = feedbackService.getFeedbackList(
                            new FeedbackExportReqVO().setBatchCode(batchCode));
                    if (feedbackDOList.isEmpty()) {
                        return error(ErrorCodeConstants.FEEDBACK_NOT_EXISTS);
                    }
                }
            }

            Map<String, Map<String, String>> batchToFeedbackMap = Stream.concat(issueLineList.stream(), enableIssueList.stream())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(
                            IssueLineDTO::getBatchCode,  // key: 批次号
                            line -> {
                                // 获取对应的报工单信息
                                List<FeedbackDO> feedbackDOList = feedbackService.getFeedbackList(
                                        new FeedbackExportReqVO().setBatchCode(line.getBatchCode()));
                                if (feedbackDOList.isEmpty()) {
                                    return new HashMap<String, String>() {{
                                        put("feedbackCode", "");
                                        put("teamCode", "");
                                    }};
                                }
                                FeedbackDO feedback = feedbackDOList.get(0);
                                Map<String, String> feedbackInfo = new HashMap<>();
                                feedbackInfo.put("feedbackCode", feedback != null ? feedback.getFeedbackCode() : "");
                                feedbackInfo.put("teamCode", feedback != null ? feedback.getTeamCode() : "");
                                return feedbackInfo;
                            },
                            (existing, replacement) -> existing
                    ));

            frontBatchCode = String.join(",", batchToFeedbackMap.keySet());
            frontFeedbackCode = batchToFeedbackMap.values().stream()
                    .map(map -> map.get("feedbackCode"))
                    .collect(Collectors.joining(","));
            frontTeamCode = batchToFeedbackMap.values().stream()
                    .map(map -> map.get("teamCode"))
                    .collect(Collectors.joining(","));
        }

        // 使用任务单号作为锁的键，确保同一任务单的报工操作串行化
        String lockKey = "feedback:task:" + createReqVO.getTaskCode();
        String serial;

        // ========== 分布式锁开始 ==========
        String lockValue = UUID.randomUUID().toString();

        boolean locked = false;
        Long feedbackId = null;
        try {
            // 获取分布式锁
            locked = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofSeconds(60));
            if (!locked) {
                return error(ErrorCodeConstants.LOCK_FAIL);
            }
            int random = (int) ((Math.random() * 9 + 1) * 100);
            // 重新获取任务单信息，确保获取最新数据
            task = taskService.getTask(taskCode);
            serial = task.getFeedbackSerial();
            if (serial == null) {
                serial = "001";
            } else {
                int serialInt = Integer.parseInt(serial);
                serialInt++;
                serial = String.format("%03d", serialInt);
            }
            createReqVO.setFeedbackCode(new StringBuffer().append("AMBG01").append("-").append(createReqVO.getTaskCode()).append("-").append(serial).toString());
            // 更新任务单的序列号
            task.setFeedbackSerial(serial);
            taskService.updateTask(TaskConvert.INSTANCE.convert01(task));

            feedbackId = feedbackService.createFeedback(createReqVO);
            task.setFeedbackStatus("Y");
            // 修改任务单最新批次信息 - 注意：这里已经在同步块中更新过了，不需要再次更新
            taskService.updateTask(TaskConvert.INSTANCE.convert01(task));
            for (Map<String, Object> map : list) {
                FeedbackMemberCreateReqVO req = new FeedbackMemberCreateReqVO();
                List<Integer> postIdsStr = (List<Integer>) map.get("postIds");
                req.setFeedbackId(String.valueOf(feedbackId));
                req.setNickName((String) map.get("nickname"));
                AdminUserRespDTO user = adminUserApi.getUser((String) map.get("username"));
                Integer userId = (Integer) map.get("id");
                req.setUserId(user.getId());
                req.setUserName((String) map.get("username"));
                req.setTaskCode(createReqVO.getTaskCode());
                req.setTeamCode(createReqVO.getTeamCode());
                // 2025-8-18 追加余料数量
                // req.setQuantity(BigDecimal.valueOf(createReqVO.getQuantityQualified()));
                req.setQuantity(BigDecimal.valueOf(createReqVO.getQuantityQualified()).add(BigDecimal.valueOf(createReqVO.getQuantityExcess())));
                req.setPostIds(postIdsStr.toString());
                feedBackMemberService.createFeedbackMember(req);
            }

            for (Map<String, Object> map : defectlist) {
                FeedbackDefectCreateReqVO req = new FeedbackDefectCreateReqVO();
                Object startMeterObj = map.get("startMeter");
                Object endMeterObj = map.get("endMeter");
                Object defectMeterObj = map.get("defectMeter");
                String processCode = (String) map.get("processCode");
                String processName = (String) map.get("processName");
                Integer startMeter = parseInteger(startMeterObj, 0); // 提供默认值 0
                Integer endMeter = parseInteger(endMeterObj, 0); // 提供默认值 0
                Integer defectMeter = parseInteger(defectMeterObj, 0); // 提供默认值 0
                req.setFeedbackId(String.valueOf(feedbackId));
                req.setDefectId(Long.valueOf((Integer) map.get("id")));
                req.setDefectName((String) map.get("defectName"));
                req.setStartMeter(String.valueOf(startMeter));
                req.setEndMeter(String.valueOf(endMeter));
                req.setDefectMeter(String.valueOf(defectMeter));
                req.setTaskCode(createReqVO.getTaskCode());
                req.setProcessCode(processCode);
                req.setProcessName(processName);
                req.setOriginBatchCode(frontBatchCode);
                req.setOriginFeedbackCode(frontFeedbackCode);
                req.setOriginTeamCode(frontTeamCode);
                feedbackDefectService.createFeedbackDefect(req);
            }

        } finally {
            if (locked) {
                try {
                    String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                            "return redis.call('del', KEYS[1]) " +
                            "else return 0 end";
                    Long deleteResult = (Long) redisTemplate.execute(
                            new DefaultRedisScript<>(luaScript, Long.class),
                            Arrays.asList(lockKey),
                            lockValue
                    );
                } catch (Exception e) {
                    System.out.println("释放分布式锁异常: " + e + ", Key: " + lockKey);
                }
            }
        }
        return success(feedbackId);
    }


    private Integer parseInteger(Object obj, Integer defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        try {
            if (obj instanceof Integer) {
                return (Integer) obj;
            } else if (obj instanceof String) {
                return Integer.parseInt((String) obj);
            } else if (obj instanceof Number) {
                return ((Number) obj).intValue();
            } else {
                return defaultValue;
            }
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新生产报工记录")
    @PreAuthorize("@ss.hasPermission('pro:feedback:update')")
    @Transactional
    public CommonResult<Boolean> updateFeedback(@Valid @RequestBody FeedbackUpdateReqVO updateReqVO) {
        // 2025-6-5 追加转换单位信息
        if ("AM006".equals(updateReqVO.getProcessCode()) && "BF".equals(updateReqVO.getMachineryCode().split("-")[1].substring(0, 2))) {
            Double quantity = updateReqVO.getQuantityQualified();
            Double quantityUnqualified = updateReqVO.getQuantityUnquanlified();

            // 获取工序
            Map<String, Object> conversionMap = initConversionQuantity(FeedbackConvert.INSTANCE.convert03(updateReqVO));
            String conversionUnit = conversionMap.get("outUnit").toString();
            BigDecimal convert = new BigDecimal(conversionMap.get("outNumber").toString());

            BigDecimal qualityQuantity = new BigDecimal(quantity.toString());
            // 将qualityQuantity除以convert, 取4位小数
            BigDecimal conversionQuantity = qualityQuantity.divide(convert, 1, BigDecimal.ROUND_HALF_UP);

            updateReqVO.setConversionUnit(conversionUnit);
            updateReqVO.setConversionQuantity(conversionQuantity);
            // 追加不合格数量
            BigDecimal unqualityQuantity = new BigDecimal(quantityUnqualified.toString());
            if (unqualityQuantity.compareTo(BigDecimal.ZERO) != 0) {
                // 将qualityQuantity除以convert, 取4位小数
                BigDecimal conversionQuantityUnquality = unqualityQuantity.divide(convert, 1, BigDecimal.ROUND_HALF_UP);
                updateReqVO.setConversionQuantityUnquanlified(conversionQuantityUnquality);
            }
        }

        String frontBatchCode = null;
        String frontFeedbackCode = null;
        String frontTeamCode = null;

        // 打样工单 判定标识
        boolean proof = "AMGD01".equals(updateReqVO.getWorkorderCode().split("-")[0]);
        TaskDO task = taskService.getTask(updateReqVO.getTaskCode());

        // 2025-03-13 追加需求: 判定当前任务单对应的领料单是否存在未上料单据信息, 存在则不允许其进行报工操作
        // 基于任务单获取生产领料单
        IssueheaderDTO issueHeader = new IssueheaderDTO();
        issueHeader.setTaskId(task.getId());
        issueHeader.setWorkorderCode(updateReqVO.getWorkorderCode());
        List<IssueheaderDTO> issueHeaderList = issueApi.listIssueHeader(issueHeader);

        if (proof) {
            if (issueHeaderList.isEmpty()) {
                return error(ErrorCodeConstants.ISSUE_NOT_EXISTS);
            }
            IssueheaderDTO issueHeaderDTO = issueHeaderList.get(0);
            // 基于生产领料单获取已上料未报工的生产领料单行
            IssueLineDTO issueLine = new IssueLineDTO();
            issueLine.setIssueId(issueHeaderDTO.getId());
            issueLine.setStatus("Y");
            issueLine.setFeedbackStatus("N");
            issueLine.setMachineryCode(updateReqVO.getMachineryCode());
            List<IssueLineDTO> issueLineList = issueApi.listIssueLine(issueLine);

            // 获取当前已报工且勾选已启用的物料
            IssueLineDTO enableIssue = new IssueLineDTO();
            enableIssue.setIssueId(issueHeaderDTO.getId());
            enableIssue.setFeedbackStatus("Y");
            enableIssue.setEnableFlag("true");
            enableIssue.setMachineryCode(updateReqVO.getMachineryCode());
            List<IssueLineDTO> enableIssueList = issueApi.listIssueLine(enableIssue);

            if (issueLineList.isEmpty() && enableIssueList.isEmpty()) {
                return error(ErrorCodeConstants.TASK_NOT_RECEPT);
            }

            // 检查所有批次号是否都有对应的报工单
            List<String> allBatchCodes = Stream.concat(issueLineList.stream(), enableIssueList.stream())
                    .filter(Objects::nonNull)
                    .map(IssueLineDTO::getBatchCode)
                    .collect(Collectors.toList());

            for (String batchCode : allBatchCodes) {
                // 判定batchCode 是否为TASK开头
                if (batchCode.startsWith("TASK")) {
                    List<FeedbackDO> feedbackDOList = feedbackService.getFeedbackList(
                            new FeedbackExportReqVO().setBatchCode(batchCode));
                    if (feedbackDOList.isEmpty()) {
                        return error(ErrorCodeConstants.FEEDBACK_NOT_EXISTS);
                    }
                }
            }

            Map<String, Map<String, String>> batchToFeedbackMap = Stream.concat(issueLineList.stream(), enableIssueList.stream())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(
                            IssueLineDTO::getBatchCode,  // key: 批次号
                            line -> {
                                // 获取对应的报工单信息
                                List<FeedbackDO> feedbackDOList = feedbackService.getFeedbackList(
                                        new FeedbackExportReqVO().setBatchCode(line.getBatchCode()));
                                if (feedbackDOList.isEmpty()) {
                                    return new HashMap<String, String>() {{
                                        put("feedbackCode", "");
                                        put("teamCode", "");
                                    }};
                                }
                                FeedbackDO feedback = feedbackDOList.get(0);
                                Map<String, String> feedbackInfo = new HashMap<>();
                                feedbackInfo.put("feedbackCode", feedback != null ? feedback.getFeedbackCode() : "");
                                feedbackInfo.put("teamCode", feedback != null ? feedback.getTeamCode() : "");
                                return feedbackInfo;
                            },
                            (existing, replacement) -> existing
                    ));

            frontBatchCode = String.join(",", batchToFeedbackMap.keySet());

            frontFeedbackCode = batchToFeedbackMap.values().stream().map(map -> map.get("feedbackCode")).collect(Collectors.joining(","));

            frontTeamCode = batchToFeedbackMap.values().stream().map(map -> map.get("teamCode")).collect(Collectors.joining(","));
        }

        feedbackService.updateFeedback(updateReqVO);

        // 处理成员列表
        // 2025/11/5 改为比对处理
        // 处理成员列表
        List<Map<String, Object>> list = Optional.ofNullable(updateReqVO.getFeedbackMemberList()).orElse(Collections.emptyList());

        FeedbackMemberExportReqVO pageReqVO = new FeedbackMemberExportReqVO();
        pageReqVO.setFeedbackId(String.valueOf(updateReqVO.getId()));
        List<FeedbackMemberDO> existingMemberList = feedBackMemberService.getFeedbackMemberList(pageReqVO);

        // 将现有成员转换为以用户名(userName)为key的Map，方便查找
        Map<String, FeedbackMemberDO> existingMemberMap = existingMemberList.stream()
                .collect(Collectors.toMap(FeedbackMemberDO::getUserName, Function.identity()));

        // 用于记录新数据中出现的用户名，便于后续找出需要删除的成员
        Set<String> newUserNames = new HashSet<>();

        if (!list.isEmpty()) {
            for (Map<String, Object> map : list) {
                List<Integer> postIdsStr = (List<Integer>) map.get("postIds");
                String userName = (String) map.get("username");
                newUserNames.add(userName);

                AdminUserRespDTO user = adminUserApi.getUser(userName);
                if (user == null) {
                    // 用户不存在，跳过处理
                    continue;
                }

                // 检查该成员是否已存在
                FeedbackMemberDO existingMember = existingMemberMap.get(userName);
                if (existingMember != null) {
                    // 成员已存在，检查是否需要更新
                    boolean needsUpdate = !Objects.equals(existingMember.getNickName(), map.get("nickname")) ||
                            !Objects.equals(existingMember.getPostIds(), postIdsStr.toString()) ||
                            !Objects.equals(existingMember.getQuantity(),
                                    BigDecimal.valueOf(updateReqVO.getQuantityQualified())
                                            .add(BigDecimal.valueOf(updateReqVO.getQuantityExcess())));
                    if (needsUpdate) {
                        // 执行更新操作
                        FeedbackMemberUpdateReqVO updateReq = new FeedbackMemberUpdateReqVO();
                        updateReq.setId(existingMember.getId());
                        updateReq.setUserId(user.getId());
                        updateReq.setNickName((String) map.get("nickname"));
                        updateReq.setUserName((String) map.get("username"));
                        updateReq.setPostIds(postIdsStr.toString());
                        updateReq.setQuantity(BigDecimal.valueOf(updateReqVO.getQuantityQualified())
                                .add(BigDecimal.valueOf(updateReqVO.getQuantityExcess())));
                        feedBackMemberService.updateFeedbackMember(updateReq);
                    }
                    // 从map中移除，表示这个成员已处理
                    existingMemberMap.remove(userName);
                } else {
                    // 成员不存在，创建新记录
                    FeedbackMemberCreateReqVO req = new FeedbackMemberCreateReqVO();
                    req.setFeedbackId(String.valueOf(updateReqVO.getId()));
                    req.setNickName((String) map.get("nickname"));
                    req.setUserId(user.getId());
                    req.setUserName(userName);
                    req.setTaskCode(updateReqVO.getTaskCode());
                    req.setTeamCode(updateReqVO.getTeamCode());
                    req.setQuantity(BigDecimal.valueOf(updateReqVO.getQuantityQualified())
                            .add(BigDecimal.valueOf(updateReqVO.getQuantityExcess())));
                    req.setPostIds(postIdsStr.toString());
                    feedBackMemberService.createFeedbackMember(req);
                }
            }
        }

        // 删除在新数据中不存在的原有成员
        for (FeedbackMemberDO memberToDelete : existingMemberMap.values()) {
            feedBackMemberService.deleteFeedbackMember(memberToDelete.getId());
        }

        // 优化后的缺陷项处理逻辑
        List<Map<String, Object>> queryList = Optional.ofNullable(updateReqVO.getProcessDefectList()).orElse(Collections.emptyList());

        FeedbackDefectExportReqVO defectReqVO = new FeedbackDefectExportReqVO();
        defectReqVO.setFeedbackId(String.valueOf(updateReqVO.getId()));
        List<FeedbackDefectDO> existingDefects = feedbackDefectService.getFeedbackDefectList(defectReqVO);

        // 创建映射以便快速查找
        Map<Integer, FeedbackDefectDO> existingDefectMap = existingDefects.stream()
                .collect(Collectors.toMap(defect -> defect.getDefectId().intValue(), Function.identity()));

        Set<Integer> processedDefectIds = new HashSet<>();

        // 处理请求中的缺陷项
        for (Map<String, Object> defectMap : queryList) {
            Integer defectId = (Integer) defectMap.get("defectId");
            Integer id = (Integer) defectMap.get("id");
            processedDefectIds.add(defectId);

            // 解析米数数据
            Integer startMeter = parseInteger(defectMap.get("startMeter"), 0);
            Integer endMeter = parseInteger(defectMap.get("endMeter"), 0);
            Integer defectMeter = parseInteger(defectMap.get("defectMeter"), 0);

            // 获取对应工序
            String processCode = Optional.ofNullable((String) defectMap.get("processCode")).orElse(null);
            String processName = Optional.ofNullable((String) defectMap.get("processName")).orElse(null);

            if (existingDefectMap.containsKey(defectId)) {
                // 更新现有缺陷项
                FeedbackDefectDO existingDefect = existingDefectMap.get(defectId);
                FeedbackDefectUpdateReqVO updateDefectReq = new FeedbackDefectUpdateReqVO();
                updateDefectReq.setId(existingDefect.getId());
                updateDefectReq.setStartMeter(String.valueOf(startMeter));
                updateDefectReq.setEndMeter(String.valueOf(endMeter));
                updateDefectReq.setDefectMeter(String.valueOf(defectMeter));
                updateDefectReq.setOriginBatchCode(frontBatchCode);
                updateDefectReq.setOriginFeedbackCode(frontFeedbackCode);
                updateDefectReq.setOriginTeamCode(frontTeamCode);
                updateDefectReq.setProcessCode(processCode);
                updateDefectReq.setProcessCode(processName);
                feedbackDefectService.updateFeedbackDefect(updateDefectReq);
            } else {
                // 创建新缺陷项
                FeedbackDefectCreateReqVO createDefectReq = new FeedbackDefectCreateReqVO();
                createDefectReq.setFeedbackId(String.valueOf(updateReqVO.getId()));
                createDefectReq.setDefectId(id.longValue());
                createDefectReq.setStartMeter(String.valueOf(startMeter));
                createDefectReq.setEndMeter(String.valueOf(endMeter));
                createDefectReq.setDefectMeter(String.valueOf(defectMeter));
                createDefectReq.setDefectName((String) defectMap.get("defectName"));
                createDefectReq.setTaskCode(updateReqVO.getTaskCode());
                createDefectReq.setOriginBatchCode(frontBatchCode);
                createDefectReq.setOriginFeedbackCode(frontFeedbackCode);
                createDefectReq.setOriginTeamCode(frontTeamCode);
                createDefectReq.setProcessCode(processCode);
                createDefectReq.setProcessName(processName);
                feedbackDefectService.createFeedbackDefect(createDefectReq);
            }
        }

        // 删除不再存在的缺陷项
        for (FeedbackDefectDO existingDefect : existingDefects) {
            Integer defectId = existingDefect.getDefectId().intValue();
            if (!processedDefectIds.contains(defectId)) {
                feedbackDefectService.deleteFeedbackDefect(existingDefect.getId());
            }
        }

        return success(true);
    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete")
    @Operation(summary = "删除生产报工记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pro:feedback:delete')")
    public CommonResult<Boolean> deleteFeedback(@RequestParam("id") ArrayList<Long> ids) {
        List<FeedbackDO> feedbackList = feedbackService.getFeedbackList(ids);
        if (feedbackList.size() != ids.size()) {
            return error(ErrorCodeConstants.FEEDBACK_NOT_EXISTS);
        }

        List<String> invalidStatusList = feedbackList.stream()
                .filter(feedback -> !"PREPARE".equals(feedback.getStatus()))
                .map(FeedbackDO::getId)
                .map(String::valueOf)
                .collect(Collectors.toList());

        if (!invalidStatusList.isEmpty()) {
            return error(ErrorCodeConstants.FEEDBACK_DELETE_NOT_LICENCE);
        }

        List<Long> stockIdsToDelete = feedbackList.stream()
                .filter(feedback -> feedback.getBatchCode() != null)
                .flatMap(feedback -> {
                    List<MaterialStockDO> stockDOList = materialStockService.getMaterialStockListContainZero(
                            new MaterialStockExportReqVO()
                                    .setBatchCode(feedback.getBatchCode())
                                    .setWorkorderCode(feedback.getWorkorderCode())
                    );
                    if (stockDOList.isEmpty()) {
                        return Stream.empty();
                    }
                    stockDOList.sort(Comparator.comparing(MaterialStockDO::getCreateTime).reversed());
                    return Stream.of(stockDOList.get(0).getId());
                })
                .collect(Collectors.toList());

        if (!stockIdsToDelete.isEmpty()) {
            materialStockService.deleteMaterialStockList(stockIdsToDelete);
        }

        feedbackService.deleteFeedback(ids);

        return success(true);
    }


    @GetMapping("/get")
    @Operation(summary = "获得生产报工记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:feedback:query')")
    public CommonResult<FeedbackRespVO> getFeedback(@RequestParam("id") Long id) {
        FeedbackDO feedback = feedbackService.getFeedback(id);
        FeedbackRespVO req = FeedbackConvert.INSTANCE.convert(feedback);
        FeedbackMemberExportReqVO memberReq = new FeedbackMemberExportReqVO();
        memberReq.setFeedbackId(String.valueOf(id));
        List<FeedbackMemberDO> memberList = feedBackMemberService.getFeedbackMemberList(memberReq);
        req.setMemberList(memberList);
        FeedbackDefectExportReqVO defectReq = new FeedbackDefectExportReqVO();
        defectReq.setFeedbackId(String.valueOf(id));
        req.setProcessDefectList(feedbackDefectService.getFeedbackDefectList(defectReq));
        return success(req);
    }

    @GetMapping("/getDetail")
    @Operation(summary = "获得生产报工记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:feedback:query')")
    public CommonResult<Map<String, Object>> getFeedbackDetail(@RequestParam("id") Long id) {

        FeedbackDO feedback = feedbackService.getFeedback(id);
        if (feedback == null) {
            return error(ErrorCodeConstants.FEEDBACK_NOT_EXISTS);
        }

        String batchCode = feedback.getBatchCode();
        if (batchCode == null || batchCode.isEmpty()) {
            return error(ErrorCodeConstants.MATERIAL_STOCK_NOT_EXISTS);
        }

        // 2026-1-26 新增方法 基于批次号, 只获取最新的一条
        MaterialStockDO stockDO = materialStockService.getNewMaterialStockByBatchCode(batchCode);

        if (stockDO == null) {
            return error(ErrorCodeConstants.MATERIAL_STOCK_NOT_EXISTS);
        }

        Map<String, Object> result = new HashMap<>(64);

        result.put("id", feedback.getId());
        result.put("feedbackCode", feedback.getFeedbackCode());
        result.put("itemId", feedback.getItemId());
        result.put("itemCode", feedback.getItemCode());
        result.put("itemName", feedback.getItemName());

        result.put("quantity", feedback.getQuantity());
        result.put("quantityFeedback", feedback.getQuantityFeedback());
        result.put("quantityQualified", feedback.getQuantityQualified());
        result.put("quantityUnquanlified", feedback.getQuantityUnquanlified());

        result.put("unitOfMeasure", feedback.getUnitOfMeasure());

        result.put("taskId", feedback.getTaskId());
        result.put("taskCode", feedback.getTaskCode());

        result.put("workorderId", feedback.getWorkorderId());
        result.put("workorderCode", feedback.getWorkorderCode());
        result.put("workorderName", feedback.getWorkorderName());

        result.put("workstationId", feedback.getWorkstationId());
        result.put("workstationCode", feedback.getWorkstationCode());
        result.put("workstationName", feedback.getWorkstationName());

        result.put("processCode", feedback.getProcessCode());
        result.put("processName", feedback.getProcessName());

        result.put("machineryId", feedback.getMachineryId());
        result.put("machineryCode", feedback.getMachineryCode());
        result.put("machineryName", feedback.getMachineryName());

        result.put("userName", feedback.getUserName());
        result.put("feedbackTime", feedback.getFeedbackTime());

        result.put("batchCode", batchCode);

        result.put("erpFeedback", feedback.getErpFeedback());
        result.put("erpFeedbackStatus", feedback.getErpFeedbackStatus());
        result.put("erpWarehousingStatus", feedback.getErpWarehousingStatus());

        result.put("remark", feedback.getRemark());

        result.put("warehouseId", stockDO.getWarehouseId());
        result.put("warehouseCode", stockDO.getWarehouseCode());
        result.put("warehouseName", stockDO.getWarehouseName());

        result.put("locationId", stockDO.getLocationId());
        result.put("locationCode", stockDO.getLocationCode());
        result.put("locationName", stockDO.getLocationName());

        result.put("areaId", stockDO.getAreaId());
        result.put("areaCode", stockDO.getAreaCode());
        result.put("areaName", stockDO.getAreaName());

        result.put("confirmStatus", stockDO.getConfirmStatus());
        result.put("recptStatus", stockDO.getRecptStatus());

        return success(result);
    }


    @GetMapping("/list")
    @Operation(summary = "获得生产报工记录列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('pro:feedback:query')")
    public CommonResult<List<FeedbackRespVO>> getFeedbackList(@RequestParam("ids") Collection<Long> ids) {
        List<FeedbackDO> list = feedbackService.getFeedbackList(ids);
        return success(FeedbackConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得生产报工记录分页")
    @PreAuthorize("@ss.hasPermission('pro:feedback:query')")
    public CommonResult<PageResult<FeedbackRespVO>> getFeedbackPage(@Valid FeedbackPageReqVO pageVO) {

        PageResult<FeedbackDO> pageResult = feedbackService.getFeedbackPage(pageVO);
        List<FeedbackDO> doList = new ArrayList<>(pageResult.getList());

        if (!StringUtils.isBlank(pageVO.getProcessCode()) && pageVO.getPageNo() == 1) {
            Set<Long> existIdSet = new HashSet<>();
            for (FeedbackDO feedbackDO : doList) {
                existIdSet.add(feedbackDO.getId());
            }

            List<FeedbackDO> mergeList = feedbackService.getFeedbackListNoMerge(new FeedbackExportReqVO().setProcessCode(pageVO.getProcessCode()).setMergeStatus("Y"));

            for (FeedbackDO feedbackDO : mergeList) {
                Long id = feedbackDO.getId();
                if (!existIdSet.contains(id)) {
                    existIdSet.add(id);
                    doList.add(feedbackDO);
                }
            }
        }

        pageResult.setList(doList);
        PageResult<FeedbackRespVO> result = FeedbackConvert.INSTANCE.convertPage(pageResult);
        return success(result);
    }

    @GetMapping("/auditPage")
    @Operation(summary = "获得生产报工记录分页")
    @PreAuthorize("@ss.hasPermission('pro:feedback:query')")
    public CommonResult<PageResult<FeedbackRespVO>> getFeedbackPageNotContainMerge(@Valid FeedbackPageReqVO pageVO) {
        PageResult<FeedbackDO> pageResult = feedbackService.selectPageContainMerge(pageVO);
        // PageResult<FeedbackDO> pageResult = feedbackService.getFeedbackPage(pageVO);
        PageResult<FeedbackRespVO> result = FeedbackConvert.INSTANCE.convertPage(pageResult);
        for (FeedbackRespVO feedbackRespVO : result.getList()) {
            FeedbackMemberExportReqVO memberReq = new FeedbackMemberExportReqVO();
            memberReq.setFeedbackId(String.valueOf(feedbackRespVO.getId()));
            List<FeedbackMemberDO> memberList = feedBackMemberService.getFeedbackMemberList(memberReq);
            feedbackRespVO.setMemberList(memberList);
            FeedbackDefectExportReqVO defectReq = new FeedbackDefectExportReqVO();
            defectReq.setFeedbackId(String.valueOf(feedbackRespVO.getId()));
            feedbackRespVO.setProcessDefectList(feedbackDefectService.getFeedbackDefectList(defectReq));
        }
        return success(result);
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出生产报工记录 Excel")
    // 暂时不卡控打印权限
    // @PreAuthorize("@ss.hasPermission('pro:feedback:export')")
    @OperateLog(type = EXPORT)
    public void exportFeedbackExcel(@Valid FeedbackExportReqVO exportReqVO,
                                    HttpServletResponse response) throws IOException {
        List<FeedbackDO> list = feedbackService.getFeedbackList(exportReqVO);
        // 导出 Excel
        List<FeedbackExcelVO> datas = FeedbackConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "生产报工记录.xls", "数据", FeedbackExcelVO.class, datas);
    }

    @GetMapping("/export-jimu")
    @Operation(summary = "导出生产报工记录-积木报表")
    // 暂时不卡控打印权限
    // @PreAuthorize("@ss.hasPermission('pro:feedback:export')")
    @OperateLog(type = EXPORT)
    public CommonResult<List<Long>> exportFeedbackJimu(@Valid FeedbackExportReqVO exportReqVO,
                                                       HttpServletResponse response) throws IOException {
        List<FeedbackDO> list = feedbackService.getFeedbackListNoMerge(exportReqVO);
        List<Long> ids = list.stream().map(FeedbackDO::getId).collect(Collectors.toList());
        return success(ids);
    }


    /**
     * 执行报工
     * 1.更新生产任务和生产工单的进度
     * 2.物料消耗
     * 3.产品产出
     *
     * @param recordId
     * @return
     */
   /* @PreAuthorize("@ss.hasPermission('pro:feedback:update')")
    @Operation(summary = "执行生产报工")
    @Transactional
    @PutMapping("/{recordId}")
    public CommonResult execute(@PathVariable("recordId") Long recordId) {

        if (!StrUtils.isNotNull(recordId)) {
            return error(ErrorCodeConstants.FEEDBACK_NEED_SAVE_FIRST);
        }

        FeedbackDO feedback = feedbackService.getFeedback(recordId);

        if (feedback.getQuantityFeedback().compareTo(Double.valueOf(0)) != 1) {
            return error(ErrorCodeConstants.FEEDBACK_NUM_IS_ZERO);
        }
        WorkorderDO workorder = workorderService.getWorkorder(feedback.getWorkorderId());
        if (workorder.getQuantityProduced() > feedback.getQuantity()) {
            return error(QUENTITYP_RODUCED_IS_MORE);
        }
        //更新生产任务的生产数量
        TaskDO task = taskService.getTask(feedback.getTaskId());
        Double quantityProduced, quantityQuanlify, quantityUnquanlify;
        quantityQuanlify = task.getQuantityQuanlify() == null ? 0 : task.getQuantityQuanlify();
        quantityUnquanlify = task.getQuantityUnquanlify() == null ? 0 : task.getQuantityUnquanlify();
        quantityProduced = task.getQuantityProduced() == null ? 0 : task.getQuantityProduced();
//        task.setQuantityProduced(feedback.getQuantityFeedback());
        task.setQuantityQuanlify(quantityQuanlify + (feedback.getQuantityQualified()));
        task.setQuantityUnquanlify(quantityUnquanlify + feedback.getQuantityUnquanlified());
        task.setDeleted(true);
        TaskUpdateReqVO taskUpdateReqVO = BeanUtil.toBean(task, TaskUpdateReqVO.class);
        taskService.updateTask(taskUpdateReqVO);
        *//**
     *  2024-11-15注释, 澳美没有关键工序概念
     *  根据当前任务校验当前是否有下一道工序
     *      有: 入下一道制成线边仓(状态待入库)
     *          线边仓库区有工序编码
     *      无: 入本制程线边仓(状态待入库)
     *//*
        // 澳美的前制程的产成品是下道制程的半半成品, 均视为产品
        //生成产品产出记录单
        FeedbackDTO feedbackDTO = BeanUtil.toBean(feedback, FeedbackDTO.class);
        ProductProduceDO productRecord = productProduceService.generateProductProduce(feedbackDTO);
        //执行产品产出入线边库
        executeProductProduce(feedback, productRecord, workorder);
        *//*if(routeProcessService.checkKeyProcess(feedback)){
            //更新生产工单的生产数量
            Double produced = workorder.getQuantityProduced() == null?0:workorder.getQuantityProduced();
            Double feedBackQuantity = feedback.getQuantityFeedback() ==null?0:feedback.getQuantityFeedback();
            workorder.setQuantityProduced( produced + feedBackQuantity);
            WorkorderUpdateReqVO workorderUpdateReqVO = BeanUtil.toBean(workorder, WorkorderUpdateReqVO.class);
            workorderService.updateWorkorder(workorderUpdateReqVO);

            //生成产品产出记录单
            FeedbackDTO feedbackDTO = BeanUtil.toBean(feedback, FeedbackDTO.class);
            ProductProduceDO productRecord = productProduceService.generateProductProduce(feedbackDTO);
            //执行产品产出入线边库
            executeProductProduce(productRecord,workorder);
        }*//*
        //根据当前工序的物料BOM配置，进行物料消耗
        //先生成消耗单
        //FeedbackDTO feedbackDTO = BeanUtil.toBean(feedback, FeedbackDTO.class);
        ItemConsumeDO itemConsume = itemConsumeService.generateItemConsume(feedbackDTO);
        if (StrUtils.isNotNull(itemConsume)) {
            //执行库存消耗动作
            executeItemConsume(itemConsume);
        }
        //更新报工单的状态
        feedback.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        //feedback.setFeedbackTime(LocalDateTime.now());
        FeedbackUpdateReqVO feedbackUpdateReqVO = BeanUtil.toBean(feedback, FeedbackUpdateReqVO.class);
        feedbackService.updateFeedback(feedbackUpdateReqVO);
        //更新设备状态
        DvMachineryDTO dvMachineryDTO = dvMachineryApi.getMachineryInfo(task.getMachineryCode());
        dvMachineryDTO.setStatus("STOP"); // 报工后设备停机
        dvMachineryApi.updateMachineryInfo(dvMachineryDTO);
        return CommonResult.success();
    }*/

    /**
     * 初始化仓库信息
     *
     * @param feedBack
     * @return
     */
    @PreAuthorize("@ss.hasPermission('pro:feedback:query')")
    @Operation(summary = "初始化仓库信息")
    @Transactional
    @GetMapping("/initWarehouse")
    public Map<String, Object> checkWarehouse(FeedbackRespVO feedBack) {
        Map<String, Object> result = new HashMap<>();
        Long taskId = feedBack.getTaskId();
        TaskDO task = taskService.getTask(taskId);
        Long workorderId = feedBack.getWorkorderId();
        WorkorderDO workorder = workorderService.getWorkorder(workorderId);

        String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
        // 获取工艺路线详情
        RouteDO route = routeService.getRoute(routeCode);
        RouteProcessExportReqVO exportReqVO = new RouteProcessExportReqVO();
        exportReqVO.setRouteId(route.getId());
        // 基于任务单判定所属工序
        // 基于工序查看是否存在下道工序
        // 存在->入下道制程线边仓  无->入本仓
        exportReqVO.setProcessCode(task.getProcessCode());
        exportReqVO.setProcessSequence(task.getProcessSequence());
        List<RouteProcessDO> routeProcess = routeProcessService.getRouteProcessList(exportReqVO);
        RouteProcessDO process = routeProcess.get(0);
        String nextProcessCode = Optional.ofNullable(routeProcess.get(0).getNextProcessCode()).orElse(null);
        WarehouseDTO warehouse = null;
        StorageLocationDO location = null;
        StorageAreaDO area = null;

        if (nextProcessCode != null) {
            // 入下一个制成线边仓
            // 仓库
            warehouse = warehouseApiImpl.selectWmWarehouseByWarehouseCode(Constant.LINE_EDGE_CODE);
            // 库区
            location = locationService.getStorageLocation(nextProcessCode);
            // 库位, 目前没有WMS, 仅获取第一个库位(MES管控, 无需回传ERP)
            area = areaService.getStorageAreaByLocationId(location.getId()).get(0);
        } else {
            // 当前工序若为涂布, 入模压线边仓
            if ("AM001".equals(task.getProcessCode())) {
                warehouse = warehouseApiImpl.selectWmWarehouseByWarehouseCode(Constant.LINE_EDGE_CODE);
                location = locationService.getStorageLocation("AM002");
                area = areaService.getStorageAreaByLocationId(location.getId()).get(0);
            } else if ("AM004".equals(task.getProcessCode())) {
                warehouse = warehouseApiImpl.selectWmWarehouseByWarehouseCode(Constant.LINE_EDGE_CODE);
                location = locationService.getStorageLocation("AM005");
                area = areaService.getStorageAreaByLocationId(location.getId()).get(0);
            } else {
                warehouse = warehouseApiImpl.selectWmWarehouseByWarehouseCode(Constant.WAREHOUSE_CODE);
                // 传递至成品仓-基于正式库决定, 暂时写死
                location = locationService.getStorageLocation(40L);
                area = areaService.getStorageArea(42L);
            }
        }
        result.put("warehouse", warehouse);
        result.put("location", location);
        result.put("area", area);
        return result;
    }

    /**
     * 转换单位信息
     *
     * @param feedBack
     * @return
     */
    @PreAuthorize("@ss.hasPermission('pro:feedback:query')")
    @Operation(summary = "初始化转换单位信息")
    @GetMapping("/initConversionQuantity")
    public Map<String, Object> initConversionQuantity(FeedbackCreateReqVO feedBack) {
        Map<String, Object> result = new HashMap<>();
        Long taskId = feedBack.getTaskId();
        TaskDO task = taskService.getTask(taskId);
        Long workorderId = feedBack.getWorkorderId();
        WorkorderDO workorder = workorderService.getWorkorder(workorderId);

        String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
        // 获取工艺路线详情
        RouteDO route = routeService.getRoute(routeCode);
        RouteProcessExportReqVO exportReqVO = new RouteProcessExportReqVO();
        exportReqVO.setRouteId(route.getId());

        exportReqVO.setProcessCode(task.getProcessCode());
        List<RouteProcessDO> routeProcess = routeProcessService.getRouteProcessList(exportReqVO);
        RouteProcessDO process = routeProcess.get(0);
        BigDecimal numerator = process.getOutUnitsConversionNumerator();
        BigDecimal denominator = process.getOutUnitsConversionDenominator() == null ? BigDecimal.ONE : process.getOutUnitsConversionDenominator();

        if (denominator.compareTo(BigDecimal.ZERO) == 0) {
            // 若denominator为0则自动改为1
            denominator = BigDecimal.ONE;
        }

        BigDecimal conversionQuantity = numerator.divide(denominator, 4, BigDecimal.ROUND_HALF_UP);

        result.put("outUnit", process.getOutUnits());
        result.put("outNumber", conversionQuantity);
        return result;
    }

    /**
     * 执行产品产出入线边库动作
     *
     * @param record
     */
    private void executeProductProduce(FeedbackDO feedBack, ProductProduceDO record, WorkorderDO workorder, Long warehouseId, Long locationId, Long areaId , String batchCode) {
        /*List<ProductProductTxBean> beans = productProduceService.getTxBeans(record.getId());
        for (ProductProductTxBean bean : beans) {
            MdItemDO mdItem = mdItemService.getMdItem(bean.getItemId());
            bean.setWarehouseCode(mdItem.getWarehouseCode());
            bean.setLocationCode(mdItem.getLocationCode());
            bean.setAreaCode(mdItem.getAreaCode());
            bean.setLocationName(mdItem.getLocationName());
        }
        beans.stream().forEach(v->{
            v.setItemName(v.getItemName()+" "+workorder.getWorkorderName());
        });
        storageCoreService.processProductProduce(beans);
        record.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        ProductProduceUpdateReqVO productProduceUpdateReqVO = BeanUtil.toBean(record, ProductProduceUpdateReqVO.class);
        productProduceService.updateProductProduce(productProduceUpdateReqVO);*/
        // 2024-11-1注释
        Long taskId = record.getTaskId();
        TaskDO task = taskService.getTask(taskId);
        String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
        // 获取工艺路线详情
        RouteDO route = routeService.getRoute(routeCode);
        RouteProcessExportReqVO exportReqVO = new RouteProcessExportReqVO();
        exportReqVO.setRouteId(route.getId());
        // 基于任务单判定所属工序
        // 基于工序查看是否存在下道工序
        // 存在->入下道制程线边仓  无->入本仓
        exportReqVO.setProcessCode(task.getProcessCode());
        exportReqVO.setProcessSequence(task.getProcessSequence());
        List<RouteProcessDO> routeProcess = routeProcessService.getRouteProcessList(exportReqVO);
        RouteProcessDO process = routeProcess.get(0);
        String nextProcessCode = Optional.ofNullable(routeProcess.get(0).getNextProcessCode()).orElse(null);
        MaterialStockCreateReqVO materialStock = new MaterialStockCreateReqVO();
        MdItemDO item = mdItemService.getMdItemByItemCode(task.getItemCode());
        MdItemTypeDO itemType = mdItemTypeService.getMdItemType(item.getItemTypeId());
        // 开始追加库存信息
        materialStock.setItemId(item.getId());
        materialStock.setItemCode(item.getItemCode());
        materialStock.setItemTypeId(itemType.getId());
        materialStock.setWorkorderId(workorder.getId());
        materialStock.setWorkorderCode(workorder.getWorkorderCode());
        materialStock.setUnitOfMeasure(feedBack.getUnitOfMeasure());
        materialStock.setQuantityOnhand(BigDecimal.valueOf(feedBack.getQuantityQualified())); // 产成品数量为报工单合格数量
        materialStock.setOriginId(feedBack.getId());
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String parentBatchCode = currentDate.format(formatter) + ThreadLocalRandom.current().nextInt(1000, 10000);

        // 仓库
        WarehouseDTO warehouse = warehouseApiImpl.getWarehouse(warehouseId);
        // 库区
        StorageLocationDO location = locationService.getStorageLocation(locationId);
        // 库位, 目前没有WMS, 仅获取第一个库位(MES管控, 无需回传ERP)
        StorageAreaDO area = areaService.getStorageArea(areaId);
        // 开始录入库存
        // 后缀配置: 若warehouse的编码为WH165视为半成品, WH166视为成品
        String warehouseCode = warehouse.getWarehouseCode();
        if (warehouseCode.equals(Constant.LINE_EDGE_CODE)) {
            materialStock.setItemName(item.getItemName() + "-" + process.getProcessCode() + "半成品");
        } else {
            materialStock.setItemName(item.getItemName() + "-" + process.getProcessCode() + "产成品");
        }
        materialStock.setWarehouseCode(warehouse.getWarehouseCode()); // 线边仓
        materialStock.setWarehouseId(warehouse.getId());
        materialStock.setWarehouseName(warehouse.getWarehouseName());
        materialStock.setLocationCode(location.getLocationCode()); // 线边仓库区
        materialStock.setLocationId(location.getId());
        materialStock.setLocationName(location.getLocationName());
        materialStock.setAreaCode(area.getAreaCode()); // 线边仓库区
        materialStock.setAreaId(area.getId());
        materialStock.setAreaName(area.getAreaName());
        materialStock.setRecptStatus("N");// 需等待打印条码后质检合格开始入库
        //批次: 获取当前任务单及最新的流水号
        String serial = task.getSerial();
        if (serial == null) {
            serial = "001";
        } else {
            int serialInt = Integer.parseInt(serial);
            serialInt++;
            serial = String.format("%03d", serialInt);
        }
        // materialStock.setBatchCode(task.getParentBatchCode() + "-" + serial);
        materialStock.setBatchCode(batchCode);
        // 2025-06-08 追加母批次
        materialStock.setParentBatchCode(task.getParentBatchCode());
        // feedBack.setBatchCode(task.getParentBatchCode() + "-" + serial);
        feedBack.setErpBatchCode(parentBatchCode);
        // 修改任务单最新批次信息
        // task.setSerial(serial);
        // taskService.updateTask(TaskConvert.INSTANCE.convert01(task));

        // 追加库存
        materialStockService.createMaterialStock(materialStock);
        FeedbackUpdateReqVO feedbackUpdateReqVO = BeanUtil.toBean(feedBack, FeedbackUpdateReqVO.class);
        feedbackService.updateFeedback(feedbackUpdateReqVO);
    }


    /**
     * 执行物料消耗库存动作
     *
     * @param record
     */
    private void executeItemConsume(ItemConsumeDO record) {
        //需要在此处进行分批次领料的线边库扣减
        List<ItemConsumeTxBean> beans = itemConsumeService.getTxBeans(record.getId());
        storageCoreService.processItemConsume(beans);
        record.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        ItemConsumeUpdateReqVO itemConsumeUpdateReqVO = BeanUtil.toBean(record, ItemConsumeUpdateReqVO.class);
        itemConsumeService.updateItemConsume(itemConsumeUpdateReqVO);
    }

    /**
     * 执行物料产出入线边库动作
     *
     * @param record
     */
    private void executeItemProduce(ItemRecptDO record) {
        List<ItemRecptTxBean> beans = itemRecptService.getTxBeans(record.getId());
        storageCoreService.processItemRecpt(beans);
        record.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        ItemRecptUpdateReqVO itemRecptUpdateReqVO = BeanUtil.toBean(record, ItemRecptUpdateReqVO.class);
        itemRecptService.updateItemRecpt(itemRecptUpdateReqVO);
    }

    @PutMapping("/update-feedback-status")
    @Operation(summary = "更新生产报工记录的状态")
    @Transactional
    @PreAuthorize("@ss.hasPermission('pro:feedback:update')")
    public CommonResult updateFeedbackStatus1(@RequestParam("id") Long id, @RequestParam("status") String status, @RequestParam("warehouseId") Long warehouseId, @RequestParam("locationId") Long locationId, @RequestParam("areaId") Long areaId) {
        if (!StrUtils.isNotNull(id)) {
            return error(ErrorCodeConstants.FEEDBACK_NEED_SAVE_FIRST);
        }
        // 前置校验逻辑
        FeedbackDO feedback = feedbackService.getFeedback(id);
        WorkorderDO workorderDO = workorderService.getWorkorder(feedback.getWorkorderCode());
        if ("FINISHED".equals(workorderDO.getStatus())) {
            return error(com.dofast.module.wms.enums.ErrorCodeConstants.WORKORDER_FINSHED_NOT_AVALIABLE);
        }
        // 打样工单 判定标识
        boolean proof = "AMGD01".equals(feedback.getWorkorderCode().split("-")[0]);
        // 获得用户基本信息
        Long loginUserId = getLoginUserId();
        AdminUserRespDTO userDTO = adminUserApi.getUser(loginUserId);
        feedback.setNickName(userDTO.getNickname());
        feedback.setUserName(userDTO.getUsername());
        if (status.equals("UNAPPROVED")) {
            FeedbackUpdateReqVO feedbackUpdateReqVO = BeanUtil.toBean(feedback, FeedbackUpdateReqVO.class);
            feedbackUpdateReqVO.setStatus("UNAPPROVED");
            feedbackService.updateFeedback(feedbackUpdateReqVO);
            return error(ErrorCodeConstants.FEEDBACK_NOT_APPROVED);
        }
        if (feedback.getQuantityFeedback().compareTo(Double.valueOf(0)) != 1) {
            return error(ErrorCodeConstants.FEEDBACK_NUM_IS_ZERO);
        }
        // ========== 分布式锁开始 ==========
        // 使用固定的业务Key，确保同一任务同一机器的请求使用相同的锁
        String lockKey = String.format("feedback:batch:task:%s",
                feedback.getTaskId());
        String lockValue = UUID.randomUUID().toString();

        boolean locked = false;
        try {
            // 获取分布式锁
            locked = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofSeconds(60));
            if (!locked) {
                return error(ErrorCodeConstants.LOCK_FAIL);
            }
            return executeBusinessLogicInLock(id, status, warehouseId, locationId, areaId, feedback);
        } finally {
            if (locked) {
                try {
                    String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                            "return redis.call('del', KEYS[1]) " +
                            "else return 0 end";
                    Long deleteResult = (Long) redisTemplate.execute(
                            new DefaultRedisScript<>(luaScript, Long.class),
                            Arrays.asList(lockKey),
                            lockValue
                    );
                } catch (Exception e) {
                    System.out.println("释放分布式锁异常: " + e + ", Key: " + lockKey);
                }
            }
        }
    }


    /**
     * 执行业务逻辑
     */
    private CommonResult executeBusinessLogicInLock(Long id, String status, Long warehouseId,
                                                    Long locationId, Long areaId, FeedbackDO feedback) {
        // 打样工单 判定标识
        boolean proof = "AMGD01".equals(feedback.getWorkorderCode().split("-")[0]);
        // 重新获取最新数据
        TaskDO taskForBatch = taskService.getTaskWithLock(feedback.getTaskId());
        // ========== 批次号生成开始 ==========
        String batchCode;
        String parentBatchCode= taskForBatch.getParentBatchCode();
        String serial;
        // 生成批次号
        batchCode = generateUniqueBatchCode(taskForBatch, feedback);
        if (batchCode == null) {
            return error(ErrorCodeConstants.BATCH_CODE_DUPLICATE);
        }
        // 更新任务单的父批次号和序列号
        taskForBatch.setParentBatchCode(parentBatchCode);
        taskForBatch.setSerial(batchCode.split("-")[1]);
        taskService.updateTask(TaskConvert.INSTANCE.convert01(taskForBatch));

        // 设置批次号
        feedback.setBatchCode(batchCode);
        // ========== 批次号生成结束 ==========

        WorkorderDO workorder = workorderService.getWorkorder(feedback.getWorkorderId());
        //更新生产任务的生产数量
        TaskDO task = taskService.getTask(feedback.getTaskId());
        // 追加ERP报工接口调用
        // 2025-03-13 追加需求: 判定当前任务单对应的领料单是否存在未上料单据信息, 存在则不允许其进行报工操作
        // 基于任务单获取生产领料单
        IssueheaderDTO issueHeader = new IssueheaderDTO();
        issueHeader.setTaskId(task.getId());
        issueHeader.setWorkorderCode(workorder.getWorkorderCode());
        List<IssueheaderDTO> issueHeaderList = issueApi.listIssueHeader(issueHeader);
        List<IssueLineDTO> issueLineList = new ArrayList<>();
        List<IssueLineDTO> enableIssueList = new ArrayList<>();
        if (proof) {
            if (issueHeaderList.isEmpty()) {
                return error(ErrorCodeConstants.ISSUE_NOT_EXISTS);
            }
            IssueheaderDTO issueHeaderDTO = issueHeaderList.get(0);
            // 基于生产领料单获取已上料未报工的生产领料单行
            IssueLineDTO issueLine = new IssueLineDTO();
            issueLine.setIssueId(issueHeaderDTO.getId());
            issueLine.setStatus("Y");
            issueLine.setFeedbackStatus("N");
            issueLine.setMachineryCode(feedback.getMachineryCode());
            issueLineList = issueApi.listIssueLine(issueLine);

            // 获取当前已报工且勾选已启用的物料
            IssueLineDTO enableIssue = new IssueLineDTO();
            enableIssue.setIssueId(issueHeaderDTO.getId());
            enableIssue.setFeedbackStatus("Y");
            enableIssue.setEnableFlag("true");
            issueLine.setMachineryCode(feedback.getMachineryCode());
            enableIssueList = issueApi.listIssueLine(enableIssue);
            if (issueLineList.isEmpty() && enableIssueList.isEmpty()) {
                // 不存在已上料未报工信息与残留物料
                return error(ErrorCodeConstants.TASK_NOT_RECEPT);
            }
            // 2025-06-09 修改为每次MES报工就调用ERP报工接口, 若需要传批次, 以母批为准
            //if (feedback.getTaskStatus() == "FINISHED") {
            // 获取当前批次报工信息
            /*List<FeedbackDO> feedbackDOS = feedbackService.getFeedbackList(new FeedbackExportReqVO().setTaskCode(task.getTaskCode()));
            BigDecimal sumQuality = null;
            BigDecimal sumUnQuality = null;
            for (FeedbackDO feedbackDO : feedbackDOS) {
                if ("AM006".equals(feedbackDO.getProcessCode()) && "公斤".equals(feedbackDO.getUnitOfMeasure())) {
                    sumQuality.add(feedbackDO.getConversionQuantity());
                    sumUnQuality.add(feedbackDO.getConversionQuantityUnquanlified());
                } else {
                    sumQuality.add(BigDecimal.valueOf(feedbackDO.getQuantityQualified()));
                    sumUnQuality.add(BigDecimal.valueOf(feedbackDO.getQuantityUnquanlified()));
                }
            }*/
        }

        if (!feedback.getWorkorderCode().startsWith("MO")) {
            // 将当前上料时间与报工时间进行比对, 算出上料到报工所经历的时长信息
            LocalDateTime feedbackTime = feedback.getFeedbackTime();
            // 判定issueLineList与enableIssueList哪个不为空, 取不为空的数据
            List<IssueLineDTO> issueLineListNotEmpty = issueLineList.isEmpty() ? enableIssueList : issueLineList;

            Date issueTime = new Date(); // 获取上料时间
            if (!issueLineListNotEmpty.isEmpty()) {
                issueTime = issueLineListNotEmpty.get(0).getCreateTime();
            }

            String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
            // 获取工作序
            RouteDO route = routeService.getRoute(routeCode);
            RouteProcessExportReqVO exportReqVO = new RouteProcessExportReqVO();
            exportReqVO.setRouteId(route.getId());
            exportReqVO.setProcessCode(task.getProcessCode());
            List<RouteProcessDO> routeProcess = routeProcessService.getRouteProcessList(exportReqVO);
            RouteProcessDO process = routeProcess.get(0);
            // 20025-10-16 修改工单工作序取值
            // 若两者都为空, 是否会出现问题?
            Long workorderSequence = Optional.ofNullable(task.getWorkorderSequence())
                    .orElseGet(() -> process.getWorkorderSequence());

            // 获取ERP设备编码
            DvMachineryDTO machineryDTO = dvMachineryApi.getMachineryInfo(feedback.getMachineryCode());
            Map<String, Object> erpParams = new HashMap<>();
            // 基础信息
            erpParams.put("source_no", feedback.getFeedbackCode()); // MES报工单号
            erpParams.put("sffb002", feedback.getUserName()); // 报工人员工号
            erpParams.put("sffb005", workorder.getWorkorderCode()); // 工单单号
            erpParams.put("sffb007", feedback.getProcessCode()); // 作业编号(工序编号)
            erpParams.put("sffb009", feedback.getWorkstationCode()); // 工作站
            erpParams.put("sffb010", machineryDTO.getErpMachineryCode() + "#"); // 设备编号
            erpParams.put("sffb012", feedbackTime.toLocalDate()); // 完成日期
            erpParams.put("sffb013", feedbackTime.toLocalTime()); // 完成时间
            erpParams.put("sffb008", String.valueOf(workorderSequence)); // 工作序
            Date feedbackDate = Date.from(feedbackTime.atZone(ZoneId.systemDefault()).toInstant());
            //Date taskDate = Date.from(task.getCreateTime().atZone(ZoneId.systemDefault()).toInstant());
            long durationInMillis = feedbackDate.getTime() - issueTime.getTime(); // 计算时间跨度毫秒
            long durationInMinutes = durationInMillis / (60 * 1000); // 转为分钟

            erpParams.put("sffb014", durationInMinutes); // 工时（分）
            erpParams.put("sffb015", durationInMinutes); // 机时（分）

            if ("AM006".equals(feedback.getProcessCode()) && "BF".equals(feedback.getMachineryCode().split("-")[1].substring(0, 2))) {
                erpParams.put("sffb017", feedback.getConversionQuantity()); // 良品数量
                erpParams.put("sffb018", feedback.getConversionQuantityUnquanlified()); // 报废数量
                erpParams.put("sffb016", feedback.getConversionUnit()); // 单位
            } else {
                erpParams.put("sffb017", feedback.getQuantityQualified()); // 良品数量
                erpParams.put("sffb018", feedback.getQuantityUnquanlified()); // 报废数量
                erpParams.put("sffb016", feedback.getUnitOfMeasure()); // 单位
            }
            // 调用ERP接口
            String erpResult = workorderERPAPI.workOrderReportCreate(erpParams);
            // String erpResult = "ERROR";
            // 解析响应结果
            if (erpResult.contains("SUCCESS")) { // 根据实际接口返回判断
                //return error(ErrorCodeConstants.FEEDBACK_ERP_ERROR);
                String erpFeedback = erpResult.split(",")[1];
                feedback.setErpFeedback(erpFeedback);
                feedback.setErpFeedbackStatus("Y");
                feedbackService.updateFeedback(FeedbackConvert.INSTANCE.convert02(feedback));
            }
        }
        task.setActualEndTime(LocalDateTime.now());

        Double quantityProduced, quantityQuanlify, quantityUnquanlify;
        quantityQuanlify = task.getQuantityQuanlify() == null ? 0 : task.getQuantityQuanlify();
        quantityUnquanlify = task.getQuantityUnquanlify() == null ? 0 : task.getQuantityUnquanlify();
        quantityProduced = task.getQuantityProduced() == null ? 0 : task.getQuantityProduced();
        task.setQuantityProduced(quantityProduced + feedback.getQuantityFeedback());
        task.setQuantityQuanlify(quantityQuanlify + feedback.getQuantityQualified());
        task.setQuantityUnquanlify(quantityUnquanlify + feedback.getQuantityUnquanlified());
        TaskUpdateReqVO taskUpdateReqVO = BeanUtil.toBean(task, TaskUpdateReqVO.class);
        taskService.updateTask(taskUpdateReqVO);
        //更新工单的生产数量
        //workorder.setQuantityProduced(quantityProduced + feedback.getQuantityFeedback());
        //WorkorderUpdateReqVO workorderUpdateReqVO = BeanUtil.toBean(workorder, WorkorderUpdateReqVO.class);
        //workorderService.updateWorkorder(workorderUpdateReqVO);
        //如果是关键工序，则更新当前工单的已生产数量
        //checkKeyProcess(feedback, workorder);
        //父工单操作
        //parentOrder(feedback, workorder);
        if (routeProcessService.checkFinProcess(task)) {
            //更新生产工单的生产数量
            Double produced = workorder.getQuantityProduced() == null ? 0 : workorder.getQuantityProduced();
            Double feedBackQuantity = feedback.getQuantityFeedback() == null ? 0 : feedback.getQuantityFeedback();
            workorder.setQuantityProduced(produced + feedBackQuantity);
            WorkorderUpdateReqVO workorderUpdateReqVO = BeanUtil.toBean(workorder, WorkorderUpdateReqVO.class);
            workorderService.updateWorkorder(workorderUpdateReqVO);
        }
        //根据当前工序的物料BOM配置，进行物料消耗
        //先生成消耗单
        FeedbackDTO feedbackDTO = BeanUtil.toBean(feedback, FeedbackDTO.class);
        ItemConsumeDO itemConsume = itemConsumeService.generateItemConsume(feedbackDTO);
        if (StrUtils.isNotNull(itemConsume)) {
            //再执行库存消耗动作
            executeItemConsume(itemConsume);
        }
        //生成产品产出记录单
        ProductProduceDO productRecord = productProduceService.generateProductProduce(feedbackDTO, batchCode);
        //执行产品产出入线边库
        executeProductProduce(feedback, productRecord, workorder, warehouseId, locationId, areaId , batchCode);
        //更新报工单的状态
        feedback.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        FeedbackUpdateReqVO feedbackUpdateReqVO = BeanUtil.toBean(feedback, FeedbackUpdateReqVO.class);
        feedbackService.updateFeedback(feedbackUpdateReqVO);

        // 将任务单信息修改为已完成
        if ("FINISHED".equals(feedback.getTaskStatus())) {
            TaskDO reqTask = taskService.getTask(feedback.getTaskCode());
            reqTask.setStatus("FINISHED");
            taskService.updateTask(BeanUtil.toBean(reqTask, TaskUpdateReqVO.class));
        }
        return success();
    }

    private String generateUniqueBatchCode(TaskDO task, FeedbackDO feedback) {
        String parentBatchCode;
        String serial;
        String batchCode;
        int maxRetry = 100; // 最大重试次数，防止无限循环
        // 获取初始的父批次号和序列号
        parentBatchCode = task.getParentBatchCode();
        if (parentBatchCode == null) {
            parentBatchCode = task.getTaskCode();
            serial = "001";
        } else {
            serial = task.getSerial();
            if (serial == null) {
                serial = "001";
            }
        }
        // 循环生成批次号，直到找到不重复的
        for (int i = 0; i < maxRetry; i++) {
            batchCode = parentBatchCode + "-" + serial;
            // 检查批次号是否已存在
            if (!feedbackService.isBatchCodeExists(batchCode)) {
                // 批次号可用，更新任务单的父批次号和序列号
                task.setParentBatchCode(parentBatchCode);
                task.setSerial(serial);
                taskService.updateTask(TaskConvert.INSTANCE.convert01(task));
                return batchCode;
            }
            // 批次号重复，递增序列号
            try {
                int serialInt = Integer.parseInt(serial);
                serialInt++;
                serial = String.format("%03d", serialInt);
            } catch (NumberFormatException e) {
                // 序列号格式错误，重置为001
                System.out.println("批次号序列号格式错误，任务ID: "+task.getId()+", 序列号: "+serial);
            }
        }
        return null;
    }

    private void checkKeyProcess(FeedbackDO feedback, WorkorderDO workorder) {
        //如果是关键工序，则更新当前工单的已生产数量，进行产品产出动作
        if (routeProcessService.checkKeyProcess(feedback)) {
            MdItemDO itemDO = mdItemService.getMdItem(workorder.getProductId());
            //更新生产工单的生产数量
            Double produced = workorder.getQuantityProduced() == null ? 0 : workorder.getQuantityProduced();
            Double feedBackQuantity = feedback.getQuantityFeedback() == null ? 0 : feedback.getQuantityFeedback();
            workorder.setQuantityProduced(produced + feedBackQuantity);
            WorkorderUpdateReqVO workorderUpdateReqVO = BeanUtil.toBean(workorder, WorkorderUpdateReqVO.class);
            //判断是否排产数量加上已生产数量是否与排产数量相等
            workorderUpdateReqVO.setStatus(UserConstants.ORDER_STATUS_FINISHED);
            workorderService.updateWorkorder(workorderUpdateReqVO);
            FeedbackDTO feedbackDTO = BeanUtil.toBean(feedback, FeedbackDTO.class);
            //判断产品里的是产品还是物料
            // 澳美中每道制成都需要产出
            /*if (itemDO.getItemOrProduct().equals("ITEM")) {
                //生成物料产出记录单
                ItemRecptDO itemRecptDO = itemRecptService.generateItemRecpt(feedbackDTO);
                //执行物料产出入线边库
                executeItemProduce(itemRecptDO);
            } else {
                //生成产品产出记录单
                ProductProduceDO productRecord = productProduceService.generateProductProduce(feedbackDTO);
                //执行产品产出入线边库
                executeProductProduce(productRecord, workorder);
            }*/
        }
    }

    private String generateBatchCode(TaskDO task) {
        String parentBatchCode = task.getParentBatchCode();
        String serial = task.getSerial();
        if (parentBatchCode == null) {
            parentBatchCode = task.getTaskCode();
            task.setParentBatchCode(parentBatchCode);
            serial = "001";
        } else {
            if (serial == null) {
                serial = "001";
            } else {
                int serialInt = Integer.parseInt(serial);
                serialInt++;
                serial = String.format("%03d", serialInt);
            }
        }
        task.setSerial(serial);
        taskService.updateTask(BeanUtil.toBean(task, TaskUpdateReqVO.class)); // 更新任务单
        return parentBatchCode + "-" + serial;
    }

    private void parentOrder(FeedbackDO feedback, WorkorderDO workorder) {
        //父工单操作
        if (workorder.getParentId() != null && workorder.getParentId() != 0 && routeProcessService.checkKeyProcess(feedback)) {
            WorkorderDO parentOrder = workorderService.getWorkorder(workorder.getParentId());
            if (parentOrder.getParentId() != null && parentOrder.getParentId() != 0) {
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

    /*
    *  取消一键报工
    @PutMapping("/one-click-create")
    @Operation(summary = "一键报工")
    @Transactional
    @PreAuthorize("@ss.hasPermission('pro:feedback:update')")
    public CommonResult<String> OneClickCreate(@RequestBody FeedbackDO feedback) {
        TaskDO task = taskService.getTask(feedback.getTaskId(), feedback.getProcessId());
        feedback.setQuantityFeedback(feedback.getQuantity());
        //判断报工是否超出排产数量
        if (task.getQuantityProduced() + feedback.getQuantityFeedback() > task.getQuantity()) {
            return error(QUENTITYP_RODUCED_IS_MORE);
        }
        //报工数量不能为空
        if (feedback.getQuantityFeedback() <= Double.valueOf(0)) {
            return error(ErrorCodeConstants.FEEDBACK_NUM_IS_ZERO);
        }
        task.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        //更新任务已完成数量
        task.setQuantityProduced(task.getQuantityProduced() + feedback.getQuantityFeedback());
        taskService.updateTask(BeanUtil.toBean(task, TaskUpdateReqVO.class));

        //创建一键报工报工记录
        feedbackService.OneClickCreateFeedback(feedback);


        //如果是关键工序，则更新当前工单的已生产数量，进行产品产出动作
        WorkorderDO workorder = workorderService.getWorkorder(feedback.getWorkorderId());

        //判断是否为关键工序
        checkKeyProcessOneClick(feedback, workorder);

        //根据当前工序的物料BOM配置，进行物料消耗
        //先生成消耗单
        FeedbackDTO feedbackDTO = BeanUtil.toBean(feedback, FeedbackDTO.class);
        ItemConsumeDO itemConsume = itemConsumeService.generateItemConsume(feedbackDTO);
        if (StrUtils.isNotNull(itemConsume)) {
            //再执行库存消耗动作
            executeItemConsume(itemConsume);
        }
        return success("报工成功，请继续扫码");
    }

    private void checkKeyProcessOneClick(FeedbackDO feedback, WorkorderDO workorder) {
        //如果是关键工序，则更新当前工单的已生产数量，进行产品产出动作
        if (routeProcessService.checkKeyProcess(feedback)) {

            //判断报工是否超出工单排产数量
            if (workorder.getQuantityProduced() + feedback.getQuantityFeedback() > workorder.getQuantity()) {
                throw exception(QUENTITYP_RODUCED_IS_MORE);
            }

            MdItemDO itemDO = mdItemService.getMdItem(workorder.getProductId());
            //更新生产工单的生产数量
            Double produced = workorder.getQuantityProduced() == null ? 0 : workorder.getQuantityProduced();
            Double feedBackQuantity = feedback.getQuantityFeedback() == null ? 0 : feedback.getQuantityFeedback();
            workorder.setQuantityProduced(produced + feedBackQuantity);
            WorkorderUpdateReqVO workorderUpdateReqVO = BeanUtil.toBean(workorder, WorkorderUpdateReqVO.class);
            //判断是否排产数量加上已生产数量是否与排产数量相等
            workorderUpdateReqVO.setStatus(UserConstants.ORDER_STATUS_FINISHED);
            workorderService.updateWorkorder(workorderUpdateReqVO);

            FeedbackDTO feedbackDTO = BeanUtil.toBean(feedback, FeedbackDTO.class);

            //判断产品里的是产品还是物料
            if (itemDO.getItemOrProduct().equals("ITEM")) {
                //生成物料产出记录单
                ItemRecptDO itemRecptDO = itemRecptService.generateItemRecpt(feedbackDTO);
                //执行物料产出入线边库
                executeItemProduce(itemRecptDO);
            } else {
                //生成产品产出记录单
                ProductProduceDO productRecord = productProduceService.generateProductProduce(feedbackDTO);
                //执行产品产出入线边库
                executeProductProduce(feedback, productRecord, workorder);
            }
        }
    }*/

    /**
     * 报工产成品入库
     * 将待入库状态改为已入库
     *
     * @param params
     * @return
     */
    @PostMapping("/wareHousing")
    public CommonResult<String> wareHousing(@RequestBody Map<String, Object> params) {
        List<Map<String, Object>> objList = (List<Map<String, Object>>) params.get("wareList");

        // 2025-06-25 完工入库接口改为扫码确认后调用, 当前方法内接口注释
        /*// 准备调用ERP接口的参数容器
        Map<String, Object> erpParams = new HashMap<>();
        List<Map<String, Object>> workOrders = new ArrayList<>();

        // 遍历每个报工单进行数据转换
        for (Map<String, Object> ware : objList) {
            // 构建goodsList明细（根据ERP接口要求）
            List<Map<String, Object>> goodsList = new ArrayList<>();

            // 追加校验, 判定当前完工单任务是否为末工序
            WorkorderDO workorder = workorderService.getWorkorder((String) ware.get("workorderCode"));
            TaskDO task = taskService.getTask((String) ware.get("taskCode"));
            Integer id = (Integer) ware.get("id");
            FeedbackDO feedbackDO = feedbackService.getFeedback(id.longValue());

            String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
            // 获取工艺路线详情
            RouteDO route = routeService.getRoute(routeCode);
            RouteProcessExportReqVO routeProcessExportReqVO = new RouteProcessExportReqVO();
            routeProcessExportReqVO.setRouteId(route.getId());
            routeProcessExportReqVO.setProcessCode((String) ware.get("processCode"));
            List<RouteProcessDO> routeProcess = routeProcessService.getRouteProcessList(routeProcessExportReqVO);
            RouteProcessDO process = routeProcess.get(0);
            String nextProcessCode = Optional.ofNullable(routeProcess.get(0).getNextProcessCode()).orElse(null);

            if (nextProcessCode != null) {
                // 存在下道制程, 不调用ERP接口
                continue;
            }
            // 判定当前任务单是否已完成
            *//*if (!"FINISHED".equals(feedbackDO.getTaskStatus())) {
                // 当前并非末卷
                continue;
            }*//*

            // 开始获取当前任务单报工总数
            *//*Double sumQuantity = 0.0;
            List<FeedbackDO> feedbackDOList = feedbackService.getFeedbackList(new FeedbackExportReqVO().setTaskCode(task.getTaskCode()).setErpFeedbackStatus("N"));
            for (FeedbackDO feedback : feedbackDOList) {
                //sumQuantity.add(BigDecimal.valueOf(feedbackDO.getQuantityQualified()));
                if ("AM006".equals(feedback.getProcessCode()) && "公斤".equals(feedback.getUnitOfMeasure())) {
                    sumQuantity += feedback.getConversionQuantity().doubleValue();
                }else{
                    sumQuantity += feedback.getQuantityQualified();
                }
            }*//*

            Map<String, Object> detail = new HashMap<>();
            detail.put("sfeb001", ware.get("workorderCode"));       // 工单单号
            detail.put("sfeb003", "1");                             // 入库类型（示例值，需确认）
            detail.put("sfeb004", ware.get("itemCode"));            // 料号
            detail.put("sfeb005", "");       // 产品特征 ware.get("specification")
            //detail.put("sfeb008", ware.get("quantityFeedback"));    // 申请数量

            if ("AM006".equals(feedbackDO.getProcessCode()) && "BF".equals(feedbackDO.getMachineryCode().split("-")[1].substring(0, 2))) {
                detail.put("sfeb008", feedbackDO.getConversionQuantity());    // 申请数量
            } else {
                detail.put("sfeb008", feedbackDO.getQuantityQualified());    // 申请数量
            }

            // 基于当前的库存信息
            MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
            exportReqVO.setItemCode((String) ware.get("itemCode"));
            exportReqVO.setBatchCode((String) ware.get("batchCode")); // 子批次
            List<MaterialStockDO> materialStock = materialStockService.getMaterialStockList(exportReqVO);

            detail.put("sfeb013", materialStock.get(0).getLocationCode());   // 库位
            detail.put("sfeb014", materialStock.get(0).getAreaCode());       // 储位
            detail.put("sfeb015", ware.get("taskCode"));        // 2025-06-08 改为母批号
            detail.put("source_seq", "");     // MES项次

            goodsList.add(detail);
            // 构建单个工单的master数据
            Map<String, Object> workOrder = new HashMap<>();
            workOrder.put("source_no", ware.get("feedbackCode"));   // MES报工单号
            workOrder.put("sfeadocno", "");                  // 单别（示例值）
            workOrder.put("sfeadocdt", formatDate(new Date()));     // 单据日期
            workOrder.put("sfea001", formatDate(new Date()));       // 过账日期
            AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
            workOrder.put("sfea002", adminUserRespDTO.getUsername());         // 申请人员
            workOrder.put("goodsList", goodsList);                  // 当前工单明细
            workOrder.put("feedback", feedbackDO);
            workOrders.add(workOrder);
        }

        // 组装最终ERP接口参数
        erpParams.put("workOrders", workOrders);
        // 校验当前完工入库是否为末工序, 末工序则回传ERP接口
        if (workOrders.size() > 0) {
            // 存在入库信息
            // 调用接口方法
            *//*String result = workorderERPAPI.workOrderFinishCreate(erpParams);
            if (result.contains("SUCCESS")) {
                //return error(ErrorCodeConstants.WAREHOUSING_ERP_ERROR);
                for(Map<String, Object> map :workOrders){
                    FeedbackDO feedbackDO = (FeedbackDO) map.get("feedback");
                    feedbackDO.setErpWarehousingStatus("Y");
                    feedbackService.updateFeedback(FeedbackConvert.INSTANCE.convert02(feedbackDO));
                }
            }*//*
        }*/

        for (Map<String, Object> map : objList) {
            // 追加校验, 判定当前完工单任务是否为末工序
            WorkorderDO workorder = workorderService.getWorkorder((String) map.get("workorderCode"));
            TaskDO task = taskService.getTask((String) map.get("taskCode"));
            Integer id = (Integer) map.get("id");
            FeedbackDO feedbackDO = feedbackService.getFeedback(id.longValue());

            String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
            // 获取工艺路线详情
            RouteDO route = routeService.getRoute(routeCode);
            RouteProcessExportReqVO routeProcessExportReqVO = new RouteProcessExportReqVO();
            routeProcessExportReqVO.setRouteId(route.getId());
            routeProcessExportReqVO.setProcessCode((String) map.get("processCode"));
            routeProcessExportReqVO.setProcessSequence(feedbackDO.getProcessSequence());
            List<RouteProcessDO> routeProcess = routeProcessService.getRouteProcessList(routeProcessExportReqVO);
            RouteProcessDO process = routeProcess.get(0);
            String nextProcessCode = Optional.ofNullable(routeProcess.get(0).getNextProcessCode()).orElse(null);

            // 基于当前的库存信息
            MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
            exportReqVO.setItemCode((String) map.get("itemCode"));
            exportReqVO.setBatchCode((String) map.get("batchCode"));
            List<MaterialStockDO> materialStock = materialStockService.getMaterialStockList(exportReqVO);
            if(materialStock.isEmpty()){
                return error(ErrorCodeConstants.INVENTORY_NOT_EXISTS);
            }
            materialStock.get(0).setRecptStatus("Y");
            // 2025-06-25 追加卡控, 末工序需现场人员确认后才调用接口
            // 若当前为末工序, 追加带确认字段
            if (nextProcessCode == null) {
                materialStock.get(0).setConfirmStatus("N");
            }
            materialStockService.updateMaterialStock(BeanUtil.toBean(materialStock.get(0), MaterialStockUpdateReqVO.class));

            // 修改当前单据状态为已入库
            Integer feedbackId = (Integer) map.get("id");
            FeedbackDO queryFeedbackDO = feedbackService.getFeedback(feedbackId.longValue());
            queryFeedbackDO.setStatus("WAREHOUSED");

            // 2025-07-21 追加卷数编号
            // 2025-08-11 追加卷号改至入库按钮
            if (StringUtils.isBlank(queryFeedbackDO.getVolumesNumber())) {
                String machineryName = queryFeedbackDO.getMachineryName();
                String lastChar = machineryName.substring(machineryName.length() - 1);
                // String shiftInfo = "0".equals(queryFeedbackDO.getShiftInfo()) ? "A" : "B";
                String shiftInfo = "A";
                String date = DateUtil.format(new Date(), "yyMMdd");
                Map<String, Object> countMap = feedbackService.getFeedbackCount(queryFeedbackDO.getWorkorderCode(), queryFeedbackDO.getTaskCode());
                Number count = (Number) countMap.get("count");
                String serialStr = String.format("%02d", count.intValue() + 1);
                String str = Integer.parseInt(serialStr) % 2 == 1 ? "A" : "B";
                String volumesNumber = "AM" + lastChar + shiftInfo + date + serialStr + str;
                // 2025-8-18 追加卡控, 若生成的卷号已存在, 则自增1
                List<FeedbackDO> volumesList = feedbackService.getFeedbackList(new FeedbackExportReqVO().setVolumesNumber(volumesNumber).setTaskCode(queryFeedbackDO.getTaskCode()));
                while (!volumesList.isEmpty()) {
                    // 开始自增
                    int newCount = Integer.parseInt(serialStr) + 1;
                    serialStr = String.format("%02d", newCount);
                    str = newCount % 2 == 1 ? "A" : "B";
                    volumesNumber = "AM" + lastChar + shiftInfo + date + serialStr + str;
                    // 检查新的卷号是否已存在
                    volumesList = feedbackService.getFeedbackList(new FeedbackExportReqVO().setVolumesNumber(volumesNumber));
                }
                queryFeedbackDO.setVolumesNumber(volumesNumber);
            }
            feedbackService.updateFeedback(FeedbackConvert.INSTANCE.convert02(queryFeedbackDO));
        }
        return success("success");
    }

    @PostMapping("/splitFeedback")
    @Transactional
    public CommonResult<String> splitFeedback(@RequestBody Map<String, Object> params) {
        String workorderCode = (String) params.get("workorderCode");
        Integer id = (Integer) params.get("id");
        String itemCode = (String) params.get("itemCode");
        String unitOfMeasure = (String) params.get("unitOfMeasure");
        List<Map<String, Object>> splitList = (List<Map<String, Object>>) params.get("splitDetails");


        // 来源报工单
        FeedbackDO feedbackDO = feedbackService.getFeedback(id.longValue());
        TaskDO task = taskService.getTask(feedbackDO.getTaskId());
        BigDecimal updateCount = BigDecimal.ZERO;
        MdItemDO mdItemDO = mdItemService.getMdItemByItemCode(itemCode);

        String transactionType_out = Constant.TRANSACTION_TYPE_WAREHOUSE_TRANS_OUT;
        String transactionType_in = Constant.TRANSACTION_TYPE_WAREHOUSE_TRANS_IN;

        // 获取当前工单任务下的报工单数量（用于卷数编号基数）
        Map<String, Object> countMap = feedbackService.getFeedbackCount(
                feedbackDO.getWorkorderCode(),
                feedbackDO.getTaskCode()
        );
        int baseCount = ((Number) countMap.get("count")).intValue();
        int splitIndex = 1;  // 拆分序号计数器

        // 获取原报工单合格数量和总数量
        BigDecimal originalQualifiedQuantity = BigDecimal.valueOf(feedbackDO.getQuantityQualified());
        BigDecimal originalQuantity = BigDecimal.valueOf(feedbackDO.getQuantityFeedback());
        BigDecimal originalUnqualifiedQuantity = BigDecimal.valueOf(feedbackDO.getQuantityUnquanlified());
        // 2025-11-26: 追加工艺损耗拆分
        BigDecimal originalExcessQuantity = feedbackDO.getQuantityExcess();

        // 计算原始合格率
        BigDecimal originalQualifiedRate = originalQuantity.compareTo(BigDecimal.ZERO) == 0 ?
                BigDecimal.ZERO :
                originalQualifiedQuantity.divide(originalQuantity, 4, RoundingMode.HALF_UP);

        // 计算原始不合格率
        BigDecimal originalUnqualifiedRate = originalQuantity.compareTo(BigDecimal.ZERO) == 0 ?
                BigDecimal.ZERO :
                originalUnqualifiedQuantity.divide(originalQuantity, 4, RoundingMode.HALF_UP);

        List<String> splitFeedbackCode = new ArrayList<>();

        // 获取原始班组成员信息
        FeedbackMemberExportReqVO req = new FeedbackMemberExportReqVO();
        req.setFeedbackId(String.valueOf(feedbackDO.getId()));
        List<FeedbackMemberDO> originMemberList = feedBackMemberService.getFeedbackMemberList(req);

        // 计算总拆分数量
        BigDecimal totalSplitQuantity = BigDecimal.ZERO;
        for (Map<String, Object> split : splitList) {
            String splitStr = (String) split.get("quantity");
            BigDecimal quantityBig = new BigDecimal(splitStr);
            totalSplitQuantity = totalSplitQuantity.add(quantityBig);
        }

        // 计算拆分比率（基于原合格数量和拆分数量）
        BigDecimal splitRatio = originalQualifiedQuantity.compareTo(BigDecimal.ZERO) == 0 ?
                BigDecimal.ZERO :
                totalSplitQuantity.divide(originalQualifiedQuantity, 4, RoundingMode.HALF_UP);

        // 汇总拆分出去的工艺损耗总数
        BigDecimal sumSplitExcessQuantity = BigDecimal.ZERO;

        // 开始追加报工单信息
        for (Map<String, Object> split : splitList) {
            String splitStr = (String) split.get("quantity");
            BigDecimal quantityBig = new BigDecimal(splitStr); // 拆分数量
            updateCount = updateCount.add(quantityBig);

            // 计算当前拆分单的比率
            BigDecimal currentSplitRatio = totalSplitQuantity.compareTo(BigDecimal.ZERO) == 0 ?
                    BigDecimal.ZERO :
                    quantityBig.divide(totalSplitQuantity, 4, RoundingMode.HALF_UP);

            // 计算拆分后的合格数量（按原始合格率计算）
            /*BigDecimal splitQualifiedQuantity = quantityBig.multiply(originalQualifiedRate)
                    .setScale(6, RoundingMode.HALF_UP);*/

            BigDecimal splitQuantity = originalQualifiedRate.compareTo(BigDecimal.ZERO) == 0 ?
                    BigDecimal.ZERO :
                    quantityBig.divide(originalQualifiedRate, 6, RoundingMode.HALF_UP);

            // 拆分不良数量
            BigDecimal splitUnqualifiedQuantity = originalUnqualifiedQuantity.multiply(splitRatio).multiply(currentSplitRatio);
            // 拆分数量
            BigDecimal splitExcessQuantity = originalExcessQuantity.multiply(splitRatio).multiply(currentSplitRatio).setScale(0, RoundingMode.HALF_UP);

            // 汇总拆分的工艺损耗数量
            sumSplitExcessQuantity = sumSplitExcessQuantity.add(splitExcessQuantity);

            FeedbackDO addFeedback = new FeedbackDO();
            BeanUtils.copyProperties(feedbackDO, addFeedback);
            addFeedback.setId(null); // 清空ID
            addFeedback.setOriginCode(feedbackDO.getFeedbackCode()); // 记录来源单号
            //  addFeedback.setQuantityFeedback(quantityBig.doubleValue());
            addFeedback.setQuantityFeedback(splitQuantity.setScale(0, RoundingMode.HALF_UP).doubleValue());
            // addFeedback.setQuantityUnquanlified(0.0);
            addFeedback.setQuantityUnquanlified(splitUnqualifiedQuantity.setScale(0, RoundingMode.HALF_UP).doubleValue());
            // addFeedback.setQuantityExcess(BigDecimal.ZERO);
            // TODO: 拆分单据追加损耗
            addFeedback.setQuantityExcess(splitExcessQuantity);
            addFeedback.setQuantityQualified(quantityBig.setScale(0, RoundingMode.HALF_UP).doubleValue()); // 设置拆分后的合格数量
            addFeedback.setTeamCode(feedbackDO.getTeamCode());
            // 2025-8-12 拆分的报工单需生成不同的报工单号以应对ERP接口异常

            String batchCode;
            String parentBatchCode;
            String feedbackSerial;
            String serial;
            // TaskDO taskForBatch = null;
            /*synchronized(lockKey.intern()) {
                // 重新获取任务单信息，确保获取最新数据
                taskForBatch = taskService.getTaskWithLock(addFeedback.getTaskId());
                parentBatchCode = taskForBatch.getParentBatchCode();
                if (parentBatchCode == null) {
                    // 若母批次号为空，生成母批次号
                    parentBatchCode = taskForBatch.getTaskCode();
                    serial = "001";
                    batchCode = parentBatchCode + "-" + serial;
                } else {
                    serial = taskForBatch.getSerial();
                    if (serial == null) {
                        serial = "001";
                    } else {
                        int serialInt = Integer.parseInt(serial);
                        serialInt++;
                        serial = String.format("%03d", serialInt);
                    }
                    batchCode = parentBatchCode + "-" + serial;
                }

                if (taskForBatch.getFeedbackSerial() == null) {
                    feedbackSerial = "001";
                } else {
                    int serialInt = Integer.parseInt(serial);
                    serialInt++;
                    feedbackSerial = String.format("%03d", serialInt);
                }

                // 更新任务单的父批次号和序列号
                taskForBatch.setParentBatchCode(parentBatchCode);
                taskForBatch.setSerial(serial);
                taskForBatch.setFeedbackSerial(feedbackSerial);

                taskService.updateTask(TaskConvert.INSTANCE.convert01(taskForBatch));
            }

            String newFeedbackCode = new StringBuffer().append("AMBG01").append("-").append(addFeedback.getTaskCode()).append("-").append(feedbackSerial).toString();
            // AMGD01-202501006-001 拆分到最后一个"-"之前
            String batchBegin = feedbackDO.getBatchCode().substring(0, feedbackDO.getBatchCode().lastIndexOf("-"));
            addFeedback.setBatchCode(batchBegin + "-" + serial); // 更新子批次
            addFeedback.setFeedbackCode(newFeedbackCode);
            Long lineId = feedbackService.createFeedback(FeedbackConvert.INSTANCE.convert01(addFeedback));*/


            // ========== 分布式锁开始 ==========
            // 使用任务单号作为锁的键，确保同一任务单的报工操作串行化
            String lockKey = "feedback:task:" + feedbackDO.getTaskCode();
            String lockValue = UUID.randomUUID().toString();

            boolean locked = false;
            Long lineId = null;
            try {
                locked = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofSeconds(60));
                if (!locked) {
                    return error(ErrorCodeConstants.LOCK_FAIL);
                }
                // 重新获取任务单信息
                TaskDO taskForBatch = taskService.getTaskWithLock(addFeedback.getTaskId());
                parentBatchCode = taskForBatch.getParentBatchCode();
                if (parentBatchCode == null) {
                    // 若母批次号为空，生成母批次号
                    parentBatchCode = taskForBatch.getTaskCode();
                    serial = "001";
                    batchCode = parentBatchCode + "-" + serial;
                } else {
                    serial = taskForBatch.getSerial();
                    if (serial == null) {
                        serial = "001";
                    } else {
                        int serialInt = Integer.parseInt(serial);
                        serialInt++;
                        serial = String.format("%03d", serialInt);
                    }
                    batchCode = parentBatchCode + "-" + serial;
                }

                if (taskForBatch.getFeedbackSerial() == null) {
                    feedbackSerial = "001";
                } else {
                    feedbackSerial = taskForBatch.getFeedbackSerial();
                    int serialInt = Integer.parseInt(feedbackSerial);
                    serialInt++;
                    feedbackSerial = String.format("%03d", serialInt);
                }

                // 更新任务单的父批次号和序列号
                taskForBatch.setParentBatchCode(parentBatchCode);
                taskForBatch.setSerial(serial);
                taskForBatch.setFeedbackSerial(feedbackSerial);

                taskService.updateTask(TaskConvert.INSTANCE.convert01(taskForBatch));

                String newFeedbackCode = new StringBuffer().append("AMBG01").append("-").append(addFeedback.getTaskCode()).append("-").append(feedbackSerial).toString();
                // AMGD01-202501006-001 拆分到最后一个"-"之前
                String batchBegin = feedbackDO.getBatchCode().substring(0, feedbackDO.getBatchCode().lastIndexOf("-"));
                addFeedback.setBatchCode(batchBegin + "-" + serial); // 更新子批次
                addFeedback.setFeedbackCode(newFeedbackCode);
                lineId = feedbackService.createFeedback(FeedbackConvert.INSTANCE.convert01(addFeedback));
            } finally {
                if (locked) {
                    try {
                        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                                "return redis.call('del', KEYS[1]) " +
                                "else return 0 end";
                        Long deleteResult = (Long) redisTemplate.execute(
                                new DefaultRedisScript<>(luaScript, Long.class),
                                Arrays.asList(lockKey),
                                lockValue
                        );
                    } catch (Exception e) {
                        System.out.println("释放分布式锁异常: " + e + ", Key: " + lockKey);
                    }
                }
            }

            // 已完成的单据视为已入库
            // 获取库存信息, 修改当前已入库数量
            MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
            exportReqVO.setItemCode(itemCode);
            exportReqVO.setBatchCode(feedbackDO.getBatchCode());
            exportReqVO.setRecptStatus("N"); // 已完成状态的单据为未入库("N");
            List<MaterialStockDO> materialStockDO = materialStockService.getMaterialStockList(exportReqVO);
            if (!materialStockDO.isEmpty()) {
                MaterialStockDO materialStock = materialStockDO.get(0);
                //构造原库存减少事务
                TransactionUpdateReqVO transaction_out = new TransactionUpdateReqVO();
                BeanUtils.copyBeanProp(transaction_out, materialStock);
                transaction_out.setTransactionType(transactionType_out);
                transaction_out.setTransactionFlag(-1);//库存减少
                transaction_out.setTransactionQuantity(quantityBig);
                transaction_out.setTransactionDate(LocalDateTime.now());
                transaction_out.setSourceDocId(feedbackDO.getWorkorderId());
                transaction_out.setSourceDocCode(workorderCode);
                transaction_out.setSourceDocLineId(feedbackDO.getId());
                transactionService.processTransaction(transaction_out);

                //再构造一条目的库存增加的事务
                TransactionUpdateReqVO transaction_in = new TransactionUpdateReqVO();
                BeanUtils.copyBeanProp(transaction_in, addFeedback);
                transaction_in.setTransactionType(transactionType_in);
                transaction_in.setTransactionFlag(1);//库存增加
                transaction_in.setTransactionQuantity(quantityBig);
                //由于是新增的库存记录所以需要将查询出来的库存记录ID置为空
                transaction_in.setMaterialStockId(null);
                //使用出库事务的供应商初始化入库事务的供应商
                transaction_in.setVendorId(transaction_out.getVendorId());
                transaction_in.setVendorCode(transaction_out.getVendorCode());
                transaction_in.setVendorName(transaction_out.getVendorName());
                transaction_in.setVendorNick(transaction_out.getVendorNick());
                transaction_in.setItemId(mdItemDO.getId().longValue());
                transaction_in.setItemCode(mdItemDO.getItemCode());
                transaction_in.setItemName(mdItemDO.getItemName());
                transaction_in.setSpecification(mdItemDO.getSpecification());
                transaction_in.setParentBatchCode(materialStock.getParentBatchCode());

                //库存,库区,库位信息继承原报工单
                WarehouseDO warehouse = warehouseService.selectWmWarehouseByWarehouseCode(materialStock.getWarehouseCode());
                transaction_in.setWarehouseId(warehouse.getId());
                transaction_in.setWarehouseCode(warehouse.getWarehouseCode());
                transaction_in.setWarehouseName(warehouse.getWarehouseName());
                StorageLocationDO location = storageLocationService.selectWmStorageLocationByLocationCode(materialStock.getLocationCode());
                transaction_in.setLocationId(location.getId());
                transaction_in.setLocationCode(location.getLocationCode());
                transaction_in.setLocationName(location.getLocationName());
                StorageAreaExportReqVO erv = new StorageAreaExportReqVO();
                erv.setLocationId(location.getId());
                erv.setAreaCode(materialStock.getAreaCode());
                List<StorageAreaDO> areaList = storageAreaService.getStorageAreaList(erv);
                StorageAreaDO area = areaList.get(0);
                transaction_in.setAreaId(area.getId());
                transaction_in.setAreaCode(area.getAreaCode());
                transaction_in.setAreaName(area.getAreaName());
                transaction_in.setSourceDocId(feedbackDO.getWorkorderId());
                transaction_in.setSourceDocCode(workorderCode);
                transaction_in.setSourceDocLineId(lineId);
                //设置入库相关联的出库事务ID
                transaction_in.setRelatedTransactionId(transaction_out.getId());
                transaction_in.setRecptStatus(materialStock.getRecptStatus());
                transactionService.processTransaction(transaction_in);
            }

            // 为拆分后的报工单创建班组成员信息
            for (FeedbackMemberDO originMember : originMemberList) {
                BigDecimal memberSplitQuantity = originMember.getQuantity().multiply(splitRatio).multiply(currentSplitRatio)
                        .setScale(0, RoundingMode.HALF_UP);

                FeedbackMemberDO memberDO = new FeedbackMemberDO();
                BeanUtils.copyProperties(originMember, memberDO); // 复制原班组人员信息
                memberDO.setId(null); // 清空ID
                memberDO.setFeedbackId(String.valueOf(lineId)); // 关联新增的报工单
                memberDO.setQuantity(memberSplitQuantity); // 按拆分比率计算的数量
                feedBackMemberService.createFeedbackMember(FeedbackMemberConvert.INSTANCE.convert01(memberDO));
            }

            // 获取原始缺陷项信息
            FeedbackDefectExportReqVO defectReq = new FeedbackDefectExportReqVO();
            defectReq.setFeedbackId(String.valueOf(feedbackDO.getId()));
            List<FeedbackDefectDO> originDefectList = feedbackDefectService.getFeedbackDefectList(defectReq);

            // 为拆分后的报工单创建缺陷项（按拆分比率分配）
            for (FeedbackDefectDO originDefect : originDefectList) {
                BigDecimal defectSplitMeter = new BigDecimal(originDefect.getDefectMeter())
                        .multiply(splitRatio).multiply(currentSplitRatio)
                        .setScale(4, RoundingMode.HALF_UP);

                FeedbackDefectDO defectDO = new FeedbackDefectDO();
                BeanUtils.copyProperties(originDefect, defectDO);
                defectDO.setId(null);
                defectDO.setFeedbackId(String.valueOf(lineId));

                defectDO.setDefectMeter(defectSplitMeter.toString());
                feedbackDefectService.createFeedbackDefect(
                        FeedbackDefectConvert.INSTANCE.convert01(defectDO));
            }

            // 更新原始报工单的缺陷项数量
            for (FeedbackDefectDO defect : originDefectList) {
                BigDecimal defectDeduction = new BigDecimal(defect.getDefectMeter())
                        .multiply(splitRatio)
                        .setScale(4, RoundingMode.HALF_UP);

                BigDecimal newDefectMeter = new BigDecimal(defect.getDefectMeter())
                        .subtract(defectDeduction);
                defect.setDefectMeter(newDefectMeter.toString());
                feedbackDefectService.updateFeedbackDefect(
                        FeedbackDefectConvert.INSTANCE.convert02(defect));
            }

        }

        // 原始报工单剩余的数量
        BigDecimal finQuantity = originalQuantity.subtract(
                updateCount.divide(originalQualifiedRate, 4, RoundingMode.HALF_UP)
        );

        // 计算原始报工单剩余的合格数量（按比例计算）
        BigDecimal finQualifiedQuantity = originalQualifiedQuantity.subtract(updateCount);

        BigDecimal finQualifiedUnQuantity = originalUnqualifiedQuantity.multiply(splitRatio).setScale(4, RoundingMode.HALF_UP)  ;

        feedbackDO.setQuantityFeedback(finQuantity.setScale(0, RoundingMode.HALF_UP).doubleValue()); // 更新报工数量
        feedbackDO.setQuantityQualified(finQualifiedQuantity.setScale(0, RoundingMode.HALF_UP).doubleValue()); // 更新合格数量
        feedbackDO.setQuantityUnquanlified(finQualifiedUnQuantity.setScale(0, RoundingMode.HALF_UP).doubleValue()); // 更新不合格数量
        // 设置原报工单拆分后的剩余工艺损耗数量
        feedbackDO.setQuantityExcess(feedbackDO.getQuantityExcess().subtract(sumSplitExcessQuantity).setScale(0, RoundingMode.HALF_UP));

        feedbackService.updateFeedback(FeedbackConvert.INSTANCE.convert02(feedbackDO));

        // 2025-8-11 更新原工单对应班组成员数量（按拆分比率减少，但保留剩余部分）
        for (FeedbackMemberDO member : originMemberList) {
            BigDecimal memberDeduction = member.getQuantity().multiply(splitRatio)
                    .setScale(6, RoundingMode.HALF_UP);

            BigDecimal newQuantity = member.getQuantity().subtract(memberDeduction);
            member.setQuantity(newQuantity);
            feedBackMemberService.updateFeedbackMember(FeedbackMemberConvert.INSTANCE.convert02(member));
        }

        // 2025-8-102 报工拆分会变更报工单流水号, 为了领料单的关联性, 在此处基于原报工单找寻上料记录, 并追加新的报工单
        List<IssueLineDO> IssueLineDOList = issueLineService.getIssueLineList(new IssueLineExportReqVO().setFeedbackCode(feedbackDO.getFeedbackCode()));
        if (IssueLineDOList.size() > 0 && !IssueLineDOList.isEmpty()) {
            // 存在对应的领料信息, 将当前拆分后的报工单追加
            for (IssueLineDO issueLineDO : IssueLineDOList) {
                // 获取现有报工单号集合
                String currentFeedbackCodes = issueLineDO.getFeedbackCode();
                Set<String> existingCodes = new HashSet<>(Arrays.asList(currentFeedbackCodes.split(",")));
                existingCodes.removeIf(String::isEmpty);
                Set<String> updatedCodes = new HashSet<>(existingCodes);
                updatedCodes.addAll(splitFeedbackCode);
                // 更新报工单
                if (updatedCodes.size() > existingCodes.size()) {
                    String newFeedbackCodes = String.join(",", updatedCodes);
                    issueLineDO.setFeedbackCode(newFeedbackCodes);
                }
            }
            issueLineService.updateIssueLineBatch(IssueLineDOList);
        }
        return success("操作成功");
    }


    @GetMapping("/checkWarehousing")
    @Operation(summary = "获得报工条码信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:feedback:query')")
    public CommonResult<String> checkWarehousing(@RequestParam("id") Long id) {
        FeedbackDO feedback = feedbackService.getFeedback(id);
        MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
        if(feedback.getBatchCode() == null){
            return success("N");
        }

        exportReqVO.setBatchCode(feedback.getBatchCode());
        exportReqVO.setItemCode(feedback.getItemCode());
        List<MaterialStockDO> stockList = materialStockService.getMaterialStockList(exportReqVO);
        if (stockList.isEmpty()) {
            return error(ErrorCodeConstants.MATERIAL_STOCK_NOT_EXISTS);
        }
        MaterialStockDO stock = stockList.get(0);
        return success(stock.getRecptStatus());
    }


    @PostMapping("/reFeedback")
    @Operation(summary = "撤销报工")
    @PreAuthorize("@ss.hasPermission('pro:feedback:create')")
    public CommonResult<Long> reFeedback(@Valid @RequestBody Integer headerId) {
        String transactionType_out = Constant.TRANSACTION_TYPE_WAREHOUSE_TRANS_OUT;
        String transactionType_in = Constant.TRANSACTION_TYPE_WAREHOUSE_TRANS_IN;
        // 基于Id获取当前的报工单
        FeedbackDO feedback = feedbackService.getFeedback(headerId.longValue());

        // 2025-10-21 追加判定当前的报工单是否已入库, 则追加错误提示
        if("WAREHOUSED".equals(feedback.getStatus())){
            return error(ErrorCodeConstants.INVENTORY_WAREHOUSING_EXISTS);
        }

        // 2025-10-16 需要考虑审核不通过的单据走撤销
        if("UNAPPROVED".equals(feedback.getStatus())){
            feedback.setStatus(Constant.ORDER_STATUS_PREPARE);
            feedback.setErpFeedbackStatus("N");
            feedbackService.updateFeedback(FeedbackConvert.INSTANCE.convert02(feedback));
            return success();
        }

        // 基于当前的报工单获取当前的任务
        TaskDO task = taskService.getTask(feedback.getTaskId());
        // 基于当前的任务获取当前的工单
        WorkorderDO workorder = workorderService.getWorkorder(task.getWorkorderId());
        // 打样工单 判定标识
        boolean proof = "AMGD01".equals(feedback.getWorkorderCode().split("-")[0]);

        // 基于当前的工单获取当前的产品
        MdItemDO mdItem = mdItemService.getMdItem(workorder.getProductId());
        // 基于当前的报工单获取当前的子批次
        String batchCode = feedback.getBatchCode();

        Map<String, Object> erpParams = new HashMap<>();
        if (feedback.getErpFeedback() != null && "Y".equals(feedback.getErpFeedbackStatus())) {
            // 基础信息
            erpParams.put("feedbackCode", feedback.getErpFeedback()); // ERP报工单号
            // 调用ERP接口
            String erpResult = workorderERPAPI.docRollback(erpParams);
            // String erpResult = "SUCCESS";
            // 解析响应结果
            if (!erpResult.contains("SUCCESS")) { // 根据实际接口返回判断
                return error(ErrorCodeConstants.FEEDBACK_ERP_ERROR);
            }
        }

        // 基于当前的子批次获取当前的产成品库存信息
        // 后续将产成品删除
        MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
        exportReqVO.setBatchCode(batchCode);
        exportReqVO.setItemCode(mdItem.getItemCode());
        List<MaterialStockDO> stockList = materialStockService.getMaterialStockList(exportReqVO);
        if (stockList.isEmpty()) {
            return error(ErrorCodeConstants.MATERIAL_STOCK_NOT_EXISTS);
        }
        // 产成品信息
        MaterialStockDO product = stockList.get(0);

        // 基于任务单获取生产领料单
        IssueheaderDTO issueHeader = new IssueheaderDTO();
        issueHeader.setTaskId(task.getId());
        issueHeader.setWorkorderCode(workorder.getWorkorderCode());
        List<IssueheaderDTO> issueHeaderList = issueApi.listIssueHeader(issueHeader);

        List<IssueLineDTO> issueLineList = new ArrayList<>();
        IssueheaderDTO issueHeaderDTO = null;
        if (proof) {
            if (issueHeaderList.isEmpty()) {
                return error(ErrorCodeConstants.ISSUE_NOT_EXISTS);
            }
        }
        if (!issueHeaderList.isEmpty()) {
            issueHeaderDTO = issueHeaderList.get(0);
            // 基于生产领料单获取生产领料单行
            IssueLineDTO issueLine = new IssueLineDTO();
            issueLine.setIssueId(issueHeaderDTO.getId());
            // 2025-1-17 追加报工单过滤(报工单与领料单身一对多)
            issueLine.setFeedbackStatus("Y");
            issueLine.setFeedbackCode(feedback.getFeedbackCode());
            issueLineList = issueApi.listIssueLine(issueLine);
        }

        /**
         * 流程撤销
         * 1. 生产领料单获取生产领料单身
         * 2. 单身对应库存追加回库存表
         * 3. 将产成品删除
         */
        // 1. 生产领料单获取生产领料单身
        for (IssueLineDTO issueLineDTO : issueLineList) {
            // 2. 单身对应库存追加回库存表
            MaterialStockDO materialStock = new MaterialStockDO();
            materialStock.setItemCode(issueLineDTO.getItemCode());
            materialStock.setBatchCode(issueLineDTO.getBatchCode());
            materialStock.setLocationId(issueLineDTO.getLocationId());
            materialStock.setAreaId(issueLineDTO.getAreaId());
            // 获取当前的bom库存信息
            MaterialStockExportReqVO reqBom = new MaterialStockExportReqVO();
            BeanUtils.copyProperties(materialStock, reqBom);
            List<MaterialStockDO> materialStockDOList = materialStockService.getMaterialStockListContainZero(reqBom);
            if (materialStockDOList.isEmpty()) {
                return error(ErrorCodeConstants.MATERIAL_STOCK_NOT_EXISTS);
            }
            MaterialStockDO bomStock = materialStockDOList.get(0);
            // 追加库存撤销报工事务
            TransactionUpdateReqVO transaction_in = new TransactionUpdateReqVO();
            BeanUtils.copyBeanProp(transaction_in, bomStock);
            transaction_in.setTransactionType(transactionType_in);
            transaction_in.setTransactionFlag(1);//库存增加
            transaction_in.setTransactionQuantity(issueLineDTO.getQuantityIssued());
            transaction_in.setMaterialStockId(bomStock.getId());
            //库存,库区,库位信息继承原领料单
            WarehouseDO warehouse = warehouseService.selectWmWarehouseByWarehouseCode(issueLineDTO.getWarehouseCode());
            transaction_in.setWarehouseId(warehouse.getId());
            transaction_in.setWarehouseCode(warehouse.getWarehouseCode());
            transaction_in.setWarehouseName(warehouse.getWarehouseName());
            StorageLocationDO location = storageLocationService.selectWmStorageLocationByLocationCode(issueLineDTO.getLocationCode());
            transaction_in.setLocationId(location.getId());
            transaction_in.setLocationCode(location.getLocationCode());
            transaction_in.setLocationName(location.getLocationName());
            StorageAreaDO area = storageAreaService.selectWmStorageAreaByAreaCode(issueLineDTO.getAreaCode());
            transaction_in.setAreaId(area.getId());
            transaction_in.setAreaCode(area.getAreaCode());
            transaction_in.setAreaName(area.getAreaName());
            transaction_in.setSourceDocId(issueHeaderDTO.getWorkorderId());
            transaction_in.setSourceDocCode(issueHeaderDTO.getWorkorderCode());
            transaction_in.setSourceDocLineId(issueLineDTO.getId());
            //设置入库相关联的出库事务ID
            transactionService.processTransaction(transaction_in);
        }
        // 构造产成品库存减少事务
        // 清空产成品数量
        TransactionUpdateReqVO transaction_out = new TransactionUpdateReqVO();
        BeanUtils.copyBeanProp(transaction_out, product);
        transaction_out.setTransactionType(transactionType_out);
        transaction_out.setTransactionFlag(-1);//库存减少
        BigDecimal transactionQuantity = new BigDecimal(String.valueOf(product.getQuantityOnhand()));
        transaction_out.setTransactionQuantity(transactionQuantity);
        transaction_out.setTransactionDate(LocalDateTime.now());

        Long sourceDocId = null;
        String sourceDocCode = null;
        Long sourceDocLineId = null;
        if (issueHeaderDTO != null) {
            sourceDocId = issueHeaderDTO.getWorkorderId() == null ? workorder.getId() : issueHeaderDTO.getWorkorderId();
            sourceDocCode = issueHeaderDTO.getWorkorderCode() == null ? workorder.getWorkorderCode() : issueHeaderDTO.getWorkorderCode();
            sourceDocLineId = issueHeaderDTO.getId() == null ? 0L : issueHeaderDTO.getId();
        }
        transaction_out.setSourceDocId(sourceDocId);
        transaction_out.setSourceDocCode(sourceDocCode);
        transaction_out.setSourceDocLineId(sourceDocLineId);
        transactionService.processTransaction(transaction_out);

        // 开始修改报工单状态
        // 撤销报工暂时不回滚上料信息
        feedback.setStatus(Constant.ORDER_STATUS_PREPARE);
        feedback.setErpFeedbackStatus("N");
        feedbackService.updateFeedback(FeedbackConvert.INSTANCE.convert02(feedback));

        // 回滚任务单生产数量
        // task.setQuantity(task.getQuantity() - feedback.getQuantityFeedback());
        task.setQuantityProduced(task.getQuantityProduced() - feedback.getQuantityFeedback());
        task.setQuantityQuanlify(task.getQuantityQuanlify() - feedback.getQuantityQualified());
        task.setQuantityUnquanlify(task.getQuantityUnquanlify() - feedback.getQuantityUnquanlified());
        task.setDeleted(false);
        taskService.updateTask(TaskConvert.INSTANCE.convert01(task));

        // 移除产品产出记录单信息
        ProductProduceDO productRecord = new ProductProduceDO();
        productRecord.setWorkorderId(workorder.getId());
        productRecord.setTaskCode(task.getTaskCode());
        productRecord.setTaskId(task.getId());
        ProductProduceExportReqVO produceVO = new ProductProduceExportReqVO();
        BeanUtils.copyProperties(productRecord, produceVO);
        List<ProductProduceDO> productProduceList = productProduceService.getProductProduceList(produceVO);
        if (productProduceList.isEmpty()) {
            return error(ErrorCodeConstants.PRODUCT_PRODUCE_NOT_EXISTS);
        }
        ProductProduceDO productProduce = productProduceList.get(0);
        productProduceService.deleteProductProduce(productProduce.getId());

        // 移除产品产出记录单行信息
        ProductProduceLineDO productProduceLine = new ProductProduceLineDO();
        productProduceLine.setRecordId(productProduce.getId());
        ProductProduceLineExportReqVO linereqVO = new ProductProduceLineExportReqVO();
        BeanUtils.copyProperties(productProduceLine, linereqVO);
        List<ProductProduceLineDO> productProduceLineList = productProduceLineMapper.selectList(linereqVO);
        for (ProductProduceLineDO productProduceLineDO : productProduceLineList) {
            productProduceLineMapper.deleteById(productProduceLineDO);
        }

        // 移除物料消耗单信息
        ItemConsumeDO itemConsume = new ItemConsumeDO();
        itemConsume.setWorkorderId(workorder.getId());
        itemConsume.setTaskCode(task.getTaskCode());
        itemConsume.setTaskId(task.getId());
        ItemConsumeExportReqVO consumeVO = new ItemConsumeExportReqVO();
        BeanUtils.copyProperties(itemConsume, consumeVO);
        List<ItemConsumeDO> itemConsumeList = itemConsumeService.getItemConsumeList(consumeVO);
        if (itemConsumeList.isEmpty()) {
            return error(ErrorCodeConstants.ITEM_CONSUME_NOT_EXISTS);
        }
        ItemConsumeDO itemConsumeDO = itemConsumeList.get(0);
        itemConsumeService.deleteItemConsume(itemConsumeDO.getId());
        // 移除物料消耗单行信息
        ItemConsumeLineDO itemConsumeLine = new ItemConsumeLineDO();
        itemConsumeLine.setRecordId(itemConsumeDO.getId());
        ItemConsumeLineExportReqVO lineVO = new ItemConsumeLineExportReqVO();
        BeanUtils.copyProperties(itemConsumeLine, lineVO);
        List<ItemConsumeLineDO> itemConsumeLineList = itemConsumeLineMapper.selectList(lineVO);
        for (ItemConsumeLineDO itemConsumeLineDO : itemConsumeLineList) {
            itemConsumeLineMapper.deleteById(itemConsumeLineDO);
        }

        // 将已报工状态回滚
        for (IssueLineDTO issueLineDTO : issueLineList) {
            String[] feedbackStr = issueLineDTO.getFeedbackCode().split(",");
            if (feedbackStr.length > 1) {
                // 存在多个报工单绑定, 不回滚报工状态仅删除当前报工单号码
                StringBuffer sb = new StringBuffer();
                for (String str : feedbackStr) {
                    if (!str.equals(feedback.getFeedbackCode())) {
                        sb.append(str).append(",");
                    }
                }
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
                    sb.deleteCharAt(sb.length() - 1);
                }
                issueLineDTO.setFeedbackCode(sb.toString());
            } else {
                issueLineDTO.setFeedbackCode(null);
                issueLineDTO.setFeedbackStatus("N");
            }
            issueLineService.updateIssueLine(IssueLineConvert.INSTANCE.convert02(issueLineDTO));
        }

        return success();
    }

    @PostMapping("/mergeFeedback")
    @Operation(summary = "合并生产报工单")
    @PreAuthorize("@ss.hasPermission('pro:feedback:merge')")
    @Transactional
    public CommonResult<Long> mergeFeedback(@RequestBody Map<String, Object> requestMap) {
        // 正确解析feedbackIds
        List<Long> feedbackIds = (List<Long>) requestMap.get("feedbackIds");
        // 追加baseFeedbackId卡控, 防止空指针异常
        Integer baseFeedbackId = Optional.ofNullable((Integer) requestMap.get("baseFeedbackId")).orElse(0);
        FeedbackDO baseFeedback = new FeedbackDO();
        if (baseFeedbackId != 0) {
            // 获取基础报工单信息
            baseFeedback = feedbackService.getFeedback(Long.valueOf(baseFeedbackId));
        }
        // 获取所有选中的报工单
        List<FeedbackDO> feedbackList = feedbackService.getFeedbackList(feedbackIds);

        if (feedbackList.isEmpty()) {
            return error(ErrorCodeConstants.FEEDBACK_NOT_EXISTS);
        }

        // 2025-8-31 移除仅允许同步ERP报工状态下的合并
        /*for (FeedbackDO feedback : feedbackList) {
            if ("N".equals(feedback.getErpFeedbackStatus())) {
                return error(ErrorCodeConstants.MERGE_FEEDBACK_NOT_AVALIBE);
            }
        }*/

        // 只有当所有ERP报工状态都为Y时才为Y，否则为N
        boolean allErpSynced = feedbackList.stream()
                .allMatch(f -> "Y".equals(f.getErpFeedbackStatus()));
        String mergedErpStatus = allErpSynced ? "Y" : "N";

        // 2025-8-20 允许跨工单合并, 当前基于合格数量排序, 以数量多的为基准进行继承
        feedbackList.sort(Comparator.comparing(FeedbackDO::getQuantityQualified).reversed());
        FeedbackDO first =baseFeedback.getFeedbackCode() != null ? baseFeedback : feedbackList.get(0); // 2025-10-21 若当前前端传递过来基础的报工信息, 则直接使用. 否则走先前的获取报工单逻辑

        // 合并报工单设备继承最后一条
        List<FeedbackDO> machineryList = new ArrayList<>(feedbackList);
        machineryList.sort(Comparator.comparing(FeedbackDO::getCreateTime).reversed());
        FeedbackDO machinery = machineryList.get(0);

        // 校验所有报工单是否具有相同的工单号、工序代码和产品代码
        boolean allSame = feedbackList.stream().allMatch(f ->
                        Objects.equals(f.getItemCode(), first.getItemCode()) &&
                        Objects.equals(f.getProcessCode(), first.getProcessCode()) &&
                        // 2025-8-20 修改为允许不同工单号合并, 但需要卡控相同物料
                        // Objects.equals(f.getWorkorderCode(), first.getWorkorderCode()) &&
                        Objects.equals(f.getItemCode(), first.getItemCode())
                        // 2025-8-31 移除仅允许同步ERP报工状态下的合并
                        // Objects.equals(f.getErpFeedbackStatus(), first.getErpFeedbackStatus())
        );

        if (!allSame) {
            return error(ErrorCodeConstants.FEEDBACK_NOT_SAME);
        }

        Long warehouseId = null;
        Long locationId = null;
        Long areaId = null;

        for (FeedbackDO feedback : feedbackList) {
            // 校验是否存在未入库单据信息
            MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
            exportReqVO.setBatchCode(feedback.getBatchCode());
            exportReqVO.setItemCode(feedback.getItemCode());
            List<MaterialStockDO> stockList = materialStockService.getMaterialStockList(exportReqVO);
            if (stockList.isEmpty()) {
                return error(ErrorCodeConstants.MATERIAL_STOCK_NOT_EXISTS);
            }
            MaterialStockDO stock = stockList.get(0);
            /*if (stock.getRecptStatus().equals("N")) {
                return error(ErrorCodeConstants.MATERIAL_STOCK_NOT_RECEPT);
            }*/
            warehouseId = stock.getWarehouseId();
            locationId = stock.getLocationId();
            areaId = stock.getAreaId();
        }


        WorkorderDO workorder = workorderService.getWorkorder(first.getWorkorderId());
        String transactionType_out = Constant.TRANSACTION_TYPE_WAREHOUSE_TRANS_OUT;
        String transactionType_in = Constant.TRANSACTION_TYPE_WAREHOUSE_TRANS_IN;

        // ========== 批次号生成开始 ==========
        String batchCode;
        String parentBatchCode;
        String feedbackSerial;
        String serial;

        // ========================== 分布式锁==========================
        String lockKey = "feedback:task:" + first.getTaskCode();
        String lockValue = UUID.randomUUID().toString();

        boolean locked = false;
        Long mergedId = null;

        try {
            locked = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofSeconds(60));
            if (!locked) {
                return error(ErrorCodeConstants.LOCK_FAIL);
            }
            TaskDO taskForBatch = taskService.getTaskWithLock(first.getTaskId());
            // 重新获取任务单信息
            // 生成批次号和报工单号
            parentBatchCode = taskForBatch.getParentBatchCode();
            if (parentBatchCode == null) {
                // 若母批次号为空，生成母批次号
                parentBatchCode = taskForBatch.getTaskCode();
                serial = "001";
                batchCode = parentBatchCode + "-" + serial;
            } else {
                serial = taskForBatch.getSerial();
                if (serial == null) {
                    serial = "001";
                } else {
                    int serialInt = Integer.parseInt(serial);
                    serialInt++;
                    serial = String.format("%03d", serialInt);
                }
                batchCode = parentBatchCode + "-" + serial;
            }

            if (taskForBatch.getFeedbackSerial() == null) {
                feedbackSerial = "001";
            } else {
                int serialInt = Integer.parseInt(taskForBatch.getFeedbackSerial());
                serialInt++;
                feedbackSerial = String.format("%03d", serialInt);
            }

            // 更新任务单的父批次号、序列号和报工单号
            taskForBatch.setParentBatchCode(parentBatchCode);
            taskForBatch.setSerial(serial);
            taskForBatch.setFeedbackSerial(feedbackSerial);
            taskService.updateTask(TaskConvert.INSTANCE.convert01(taskForBatch));
            // ========== 批次号生成结束 ==========

            // 合并报工单
            FeedbackDO mergedFeedback = new FeedbackDO();

            mergedFeedback.setFeedbackCode("AMBG01-" + first.getTaskCode() + "-" + feedbackSerial);
            mergedFeedback.setFeedbackType(first.getFeedbackType());
            mergedFeedback.setWorkorderId(first.getWorkorderId());
            mergedFeedback.setWorkorderCode(first.getWorkorderCode());
            mergedFeedback.setWorkorderName(first.getWorkorderName());
            mergedFeedback.setTaskId(first.getTaskId());
            mergedFeedback.setTaskCode(first.getTaskCode());
            mergedFeedback.setProcessCode(first.getProcessCode());
            mergedFeedback.setProcessName(first.getProcessName());
            mergedFeedback.setProcessId(first.getProcessId());
            mergedFeedback.setItemCode(first.getItemCode());
            mergedFeedback.setItemName(first.getItemName());
            mergedFeedback.setItemId(first.getItemId());
            mergedFeedback.setUnitOfMeasure(first.getUnitOfMeasure());
            mergedFeedback.setSpecification(first.getSpecification());
            mergedFeedback.setWorkstationName(first.getWorkstationName());
            mergedFeedback.setWorkstationCode(first.getWorkstationCode());
            mergedFeedback.setWorkstationId(first.getWorkstationId());
            mergedFeedback.setStatus(UserConstants.ORDER_STATUS_FINISHED);
            mergedFeedback.setMachineryId(machinery.getMachineryId());
            mergedFeedback.setMachineryName(machinery.getMachineryName());
            mergedFeedback.setMachineryCode(machinery.getMachineryCode());
            mergedFeedback.setTeamCode(first.getTeamCode());
            mergedFeedback.setPrincipalName(first.getPrincipalName());
            mergedFeedback.setTaskStatus(first.getTaskStatus());
            mergedFeedback.setBatchCode(batchCode);
            mergedFeedback.setProcessSequence(first.getProcessSequence());

            // 循环feedbackList, 将报工单号基于逗号拼接为字符串
            StringBuilder sb = new StringBuilder();
            for (FeedbackDO feedback : feedbackList) {
                sb.append(feedback.getFeedbackCode()).append(",");
            }
            mergedFeedback.setOriginCode(sb.substring(0, sb.length() - 1));

            // 获得用户基本信息
            Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
            AdminUserRespDTO userDTO = adminUserApi.getUser(loginUserId);
            mergedFeedback.setNickName(userDTO.getNickname());
            mergedFeedback.setUserName(userDTO.getUsername());

            mergedFeedback.setQuantity(first.getQuantity());
            mergedFeedback.setQuantityFeedback(feedbackList.stream().mapToDouble(FeedbackDO::getQuantityFeedback).sum());
            mergedFeedback.setQuantityQualified(feedbackList.stream().mapToDouble(FeedbackDO::getQuantityQualified).sum());
            mergedFeedback.setQuantityUnquanlified(feedbackList.stream().mapToDouble(FeedbackDO::getQuantityUnquanlified).sum());
            mergedFeedback.setQuantityExcess(feedbackList.stream().map(FeedbackDO::getQuantityExcess).reduce(BigDecimal.ZERO, BigDecimal::add));

            BigDecimal sumConverQuntity = BigDecimal.ZERO;
            BigDecimal sumConverQuntityUnQuality = BigDecimal.ZERO;
            for (FeedbackDO feedback : feedbackList) {
                BigDecimal conversionQuantity = feedback.getConversionQuantity() != null ? feedback.getConversionQuantity() : BigDecimal.ZERO;
                BigDecimal conversionQuantityUnqualified = feedback.getConversionQuantityUnquanlified() != null ? feedback.getConversionQuantityUnquanlified() : BigDecimal.ZERO;
                sumConverQuntity = sumConverQuntity.add(conversionQuantity);
                sumConverQuntityUnQuality = sumConverQuntityUnQuality.add(conversionQuantityUnqualified);
            }
            // 2025-9-5 若当前的sumConverQuntity与sumConverQuntityUnQuality为0, 则不赋值
            if (sumConverQuntity.compareTo(BigDecimal.ZERO) != 0 || sumConverQuntityUnQuality.compareTo(BigDecimal.ZERO) != 0) {
                mergedFeedback.setConversionQuantity(sumConverQuntity);
                mergedFeedback.setConversionQuantityUnquanlified(sumConverQuntityUnQuality);
            }

            mergedFeedback.setConversionUnit(first.getConversionUnit());
            // 2025-8-31 ERP报工状态由合并的工单决定
            // mergedFeedback.setErpFeedbackStatus("Y");
            mergedFeedback.setErpFeedbackStatus(mergedErpStatus);

            mergedId = feedbackService.createFeedback(FeedbackConvert.INSTANCE.convert01(mergedFeedback));

        } finally {
            // 确保锁被释放
            if (locked) {
                try {
                    String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                            "return redis.call('del', KEYS[1]) " +
                            "else return 0 end";
                    Long deleteResult = (Long) redisTemplate.execute(
                            new DefaultRedisScript<>(luaScript, Long.class),
                            Arrays.asList(lockKey),
                            lockValue
                    );
                } catch (Exception e) {
                    System.out.println("释放分布式锁异常: " + e + ", Key: " + lockKey);
                }
            }
        }

        // ========================== 分布式锁======================
        FeedbackDO merged = feedbackService.getFeedback(mergedId);

        // 产成品入库
        //生成产品产出记录单
        FeedbackDTO feedbackDTO = BeanUtil.toBean(merged, FeedbackDTO.class);
        ProductProduceDO productRecord = productProduceService.generateProductProduce(feedbackDTO, batchCode);
        //执行产品产出入线边库
        executeProductProduce(merged, productRecord, workorder, warehouseId, locationId, areaId, batchCode);
        // 移除原报工单库存信息
        for (FeedbackDO feedback : feedbackList) {
            // 移除原报工单库存信息
            MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
            exportReqVO.setBatchCode(feedback.getBatchCode());
            exportReqVO.setItemCode(feedback.getItemCode());
            List<MaterialStockDO> stockList = materialStockService.getMaterialStockList(exportReqVO);
            if (stockList.isEmpty()) {
                return error(ErrorCodeConstants.MATERIAL_STOCK_NOT_EXISTS);
            }
            MaterialStockDO stock = stockList.get(0); // 扣减库存
            // 追加库存撤销报工事务
            TransactionUpdateReqVO transaction_out = new TransactionUpdateReqVO();
            BeanUtils.copyBeanProp(transaction_out, stock);
            transaction_out.setTransactionType(transactionType_out);
            transaction_out.setTransactionFlag(-1);//库存减少
            BigDecimal transactionQuantity = new BigDecimal(String.valueOf(feedback.getQuantityQualified()));
            transaction_out.setTransactionQuantity(transactionQuantity);
            transaction_out.setMaterialStockId(stock.getId());
            //库存,库区,库位信息继承原单
            WarehouseDO warehouse = warehouseService.selectWmWarehouseByWarehouseCode(stock.getWarehouseCode());
            transaction_out.setWarehouseId(warehouse.getId()); //库区
            transaction_out.setWarehouseCode(warehouse.getWarehouseCode());
            transaction_out.setWarehouseName(warehouse.getWarehouseName()); //库区
            StorageLocationDO location = storageLocationService.selectWmStorageLocationByLocationCode(stock.getLocationCode());
            transaction_out.setLocationId(location.getId()); //库位
            transaction_out.setLocationCode(location.getLocationCode());
            transaction_out.setLocationName(location.getLocationName()); //库位
            // StorageAreaDO area = storageAreaService.selectWmStorageAreaByAreaCode(stock.getAreaCode());
            StorageAreaDO area = null;
            List<StorageAreaDO> areaList = storageAreaService.getStorageAreaList(new StorageAreaExportReqVO().setAreaCode(stock.getAreaCode()).setLocationId(location.getId()));
            if (areaList != null && !areaList.isEmpty()) {
                area = areaList.get(0);
            }
            if (area != null) {
                transaction_out.setAreaId(area.getId()); //库区
                transaction_out.setAreaCode(area.getAreaCode());
                transaction_out.setAreaName(area.getAreaName()); //库区
            }
            transaction_out.setSourceDocId(feedback.getWorkorderId()); //来源单据ID
            transaction_out.setSourceDocCode(feedback.getWorkorderCode()); //来源单据编号
            transaction_out.setSourceDocLineId(feedback.getId()); //来源单据行ID
            transaction_out.setRecptStatus("N"); // 合并的单据都是已完成
            //设置入库相关联的出库事务ID
            transactionService.processTransaction(transaction_out);
            // 将原报工单数量清零
       /* feedback.setQuantityQualified(0.0);
        feedback.setQuantityUnquanlified(0.0);
        feedback.setQuantityFeedback(0.0);*/
            // 2025-8-18 保留原合并单信息, 追加合并标识
            feedback.setMergeFlag("Y");
            // 2025-5-22 合并成功后, 合并需求改为N
            feedback.setMergeStatus("N");
            feedbackService.updateFeedback(FeedbackConvert.INSTANCE.convert02(feedback));
        }

        // 追加获取原先班组人员信息
        Set<FeedbackMemberDO> memberSet = new HashSet<>();
        Map<String, Object> memberMap = new HashMap<>();
        for (FeedbackDO feedbackDO : feedbackList) {
            FeedbackMemberExportReqVO req = new FeedbackMemberExportReqVO();
            req.setFeedbackId(String.valueOf(feedbackDO.getId()));
            List<FeedbackMemberDO> memberList = feedBackMemberService.getFeedbackMemberList(req);
            for (FeedbackMemberDO member : memberList) {
                // 找寻memberMap是否存在用户, 存在则叠加数量
                if (memberMap.containsKey(String.valueOf(member.getUserName()))) {
                    FeedbackMemberDO existMember = (FeedbackMemberDO) memberMap.get(String.valueOf(member.getUserName()));
                    BigDecimal quantity = existMember.getQuantity();
                    quantity = quantity.add(member.getQuantity());
                    member.setQuantity(quantity);
                }
                memberMap.put(String.valueOf(member.getUserName()), member);
            }
        }
        memberMap.forEach((k, v) -> {
            FeedbackMemberDO memberDO = new FeedbackMemberDO();
            BeanUtils.copyProperties(v, memberDO); // 复制原班组人员信息
            memberDO.setId(null); // 清空ID
            memberDO.setFeedbackId(String.valueOf(merged.getId())); // 关联新增的报工单
            feedBackMemberService.createFeedbackMember(FeedbackMemberConvert.INSTANCE.convert01(memberDO));
        });

        // 合并缺陷项
        Map<String, FeedbackDefectDO> defectMap = new HashMap<>();
        Map<String, Set<String>> teamCodeMap = new HashMap<>();
        Map<String, Set<String>> batchCodeMap = new HashMap<>();
        Map<String, Set<String>> feedbackCodeMap = new HashMap<>();

        for (FeedbackDO feedback : feedbackList) {
            FeedbackDefectExportReqVO defectReq = new FeedbackDefectExportReqVO();
            defectReq.setFeedbackId(String.valueOf(feedback.getId()));
            List<FeedbackDefectDO> defectList = feedbackDefectService.getFeedbackDefectList(defectReq);

            for (FeedbackDefectDO defect : defectList) {
                String key = defect.getDefectId() + "_" + defect.getProcessCode();

                // 处理来源信息
                if (defect.getOriginTeamCode() != null) {
                    teamCodeMap.computeIfAbsent(key, k -> new HashSet<>())
                            .addAll(Arrays.asList(defect.getOriginTeamCode().split(",\\s*")));
                }

                if (defect.getOriginBatchCode() != null) {
                    batchCodeMap.computeIfAbsent(key, k -> new HashSet<>())
                            .addAll(Arrays.asList(defect.getOriginBatchCode().split(",\\s*")));
                }

                if (defect.getOriginFeedbackCode() != null) {
                    feedbackCodeMap.computeIfAbsent(key, k -> new HashSet<>())
                            .addAll(Arrays.asList(defect.getOriginFeedbackCode().split(",\\s*")));
                }

                if (defectMap.containsKey(key)) {
                    // 累加缺陷数量
                    FeedbackDefectDO existingDefect = defectMap.get(key);
                    BigDecimal totalDefectMeter = new BigDecimal(existingDefect.getDefectMeter())
                            .add(new BigDecimal(defect.getDefectMeter()));
                    existingDefect.setDefectMeter(totalDefectMeter.toString());
                } else {
                    // 创建新的缺陷记录
                    FeedbackDefectDO newDefect = new FeedbackDefectDO();
                    BeanUtils.copyProperties(defect, newDefect);
                    newDefect.setId(null);
                    defectMap.put(key, newDefect);
                }
            }
        }

        // 为合并后的报工单创建缺陷项记录
        for (Map.Entry<String, FeedbackDefectDO> entry : defectMap.entrySet()) {
            String key = entry.getKey();
            FeedbackDefectDO defect = entry.getValue();

            // 设置合并后的来源信息
            if (teamCodeMap.containsKey(key)) {
                defect.setOriginTeamCode(String.join(", ", teamCodeMap.get(key)));
            }

            if (batchCodeMap.containsKey(key)) {
                defect.setOriginBatchCode(String.join(", ", batchCodeMap.get(key)));
            }

            if (feedbackCodeMap.containsKey(key)) {
                defect.setOriginFeedbackCode(String.join(", ", feedbackCodeMap.get(key)));
            }

            defect.setFeedbackId(String.valueOf(mergedId));
            feedbackDefectService.createFeedbackDefect(FeedbackDefectConvert.INSTANCE.convert01(defect));
        }

        // 合并改为已完成, 合并后经过接口调用入库
        FeedbackDO mergedFeedBack = feedbackService.getFeedback(mergedId);
        List<MaterialStockDO> materialStockList = materialStockService.getMaterialStockList(new MaterialStockExportReqVO().setBatchCode(mergedFeedBack.getBatchCode()));
        if (materialStockList.isEmpty()) {
            return error(ErrorCodeConstants.MATERIAL_STOCK_NOT_EXISTS);
        }
        materialStockList.get(0).setRecptStatus("N");
        materialStockService.updateMaterialStock(MaterialStockConvert.INSTANCE.convert02(materialStockList.get(0)));
        return success(mergedId);
    }

    @PutMapping("/updatePrintStatus")
    @Operation(summary = "生产报工变更打印状态")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:feedback:query')")
    public CommonResult<FeedbackRespVO> updatePrintStatus(@RequestParam("id") Long id) {
        FeedbackDO feedback = feedbackService.getFeedback(id);
        if (!Objects.equals(feedback.getStatus(), "WAREHOUSED")) {
            feedback.setStatus("PRINTED");
            feedbackService.updateFeedback(FeedbackConvert.INSTANCE.convert02(feedback));
        }
        return success();
    }

    /**
     * IOT: 各车间对应设备产量
     *
     * @return
     */
    @GetMapping("/initFeedBackByAcquisition")
    public Map<String, Object> initFeedBackByAcquisition(Map<String, Object> requestMap) {
        Map<String, Object> source = new HashMap<>();
        String machineryCode = String.valueOf(requestMap.get("machineryCode")) == null ? null : String.valueOf(requestMap.get("machineryCode")); // 初始化设备编码
        if (machineryCode == null) {
            source.put("code", 1);
            source.put("message", "设备编码不能为空");
            return source;
        }

        BigDecimal quantity = new BigDecimal(String.valueOf(requestMap.get("quantity")) == null ? "0" : String.valueOf(requestMap.get("quantity"))); // 初始化设备编码
        if (quantity.equals(BigDecimal.ZERO)) {
            source.put("code", 1);
            source.put("message", "设备产量不能为空");
            return source;
        }

        // 基于当前设备获取最新的领料记录
        List<IssueLineDTO> issueLineList = issueApi.listIssueLine(new IssueLineDTO().setMachineryCode(machineryCode).setStatus("Y").setFeedbackStatus("N"));
        if (issueLineList.isEmpty()) {
            source.put("code", 1);
            source.put("message", "未找到当前设备已上料未报工的领料信息!");
            return source;
        }
        IssueLineDTO line = issueLineList.get(0);

        IssueheaderDTO issueheaderDTO = issueApi.listIssueHeader(new IssueheaderDTO().setId(line.getIssueId())) == null ? null : issueApi.listIssueHeader(new IssueheaderDTO().setId(line.getIssueId())).get(0);
        if (issueheaderDTO == null) {
            source.put("code", 1);
            source.put("message", "获取领料单信息异常!");
            return source;
        }

        // 初始化设备信息
        DvMachineryDTO machineryDTO = dvMachineryApi.getMachineryInfo(machineryCode) == null ? null : dvMachineryApi.getMachineryInfo(machineryCode);
        if (machineryDTO == null) {
            source.put("code", 1);
            source.put("message", "获取设备信息异常!");
            return source;
        }

        WorkorderDO workorderDO = workorderService.getWorkorder(issueheaderDTO.getWorkorderId()) == null ? null : workorderService.getWorkorder(issueheaderDTO.getWorkorderId());
        if (workorderDO == null) {
            source.put("code", 1);
            source.put("message", "获取工单信息异常!");
            return source;
        }

        TaskDO taskDO = taskService.getTask(issueheaderDTO.getTaskCode()) == null ? null : taskService.getTask(issueheaderDTO.getTaskCode());
        if (taskDO == null) {
            source.put("code", 1);
            source.put("message", "获取任务单信息异常!");
            return source;
        }

        TeamDTO teamDTO = teamApi.getTeamByCode(taskDO.getAttr1()) == null ? null : teamApi.getTeamByCode(taskDO.getAttr1());
        if (teamDTO == null) {
            source.put("code", 1);
            source.put("message", "获取班组信息异常!");
            return source;
        }

        // 基于当前设备获取追加的报工记录
        FeedbackDO feedbackDO = new FeedbackDO();
        feedbackDO.setMachineryId(machineryDTO.getId());
        feedbackDO.setMachineryCode(machineryCode);
        feedbackDO.setMachineryName(machineryDTO.getMachineryName());

        feedbackDO.setWorkorderCode(issueheaderDTO.getWorkorderCode());
        feedbackDO.setItemId(workorderDO.getProductId());
        feedbackDO.setItemCode(workorderDO.getProductCode());
        feedbackDO.setItemName(workorderDO.getProductName());
        feedbackDO.setUnitOfMeasure(workorderDO.getUnitOfMeasure());

        feedbackDO.setTaskCode(issueheaderDTO.getTaskCode());
        feedbackDO.setFeedbackType("UNI");
        feedbackDO.setStatus("PREPARE");
        int random = (int) ((Math.random() * 9 + 1) * 100);
        feedbackDO.setFeedbackCode(new StringBuffer().append("AMBG01").append("-").append(feedbackDO.getTaskCode()).append("-").append(random).toString());

        feedbackDO.setQuantityFeedback(quantity.doubleValue());
        feedbackDO.setQuantityQualified(quantity.doubleValue());

        feedbackDO.setTeamCode(teamDTO.getTeamCode());
        feedbackDO.setPrincipalId(teamDTO.getPrincipalId());
        feedbackDO.setPrincipalName(teamDTO.getPrincipalName());

        // 初始化当前时间
        feedbackDO.setFeedbackTime(LocalDateTime.now());

        Long feedBackId = feedbackService.createFeedback(FeedbackConvert.INSTANCE.convert01(feedbackDO));
        if (feedBackId == null) {
            source.put("code", 1);
            source.put("message", "追加报工单信息失败!");
            return source;
        }

        // 开始追加报工班组信息
        // 基于当前时间进行判定, 早上7点至晚上7点时间段视为早班, 反之视为晚班
        String shift = LocalDateTime.now().getHour() > 7 && LocalDateTime.now().getHour() < 19 ? "0" : "1";
        List<TeamMemberDTO> memberList = teamApi.getTeamMemberById(teamDTO.getId(), shift);

        for (TeamMemberDTO member : memberList) {
            // 基于当前用户初始化岗位信息
            FeedbackMemberCreateReqVO req = new FeedbackMemberCreateReqVO();
            // 追加当前的岗位信息
            AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(member.getUserId());
            Set<Long> postIds = adminUserRespDTO.getPostIds();
            req.setPostIds(postIds.toString());
            req.setFeedbackId(feedBackId.toString());
            String userId = line.getCreator();
            AdminUserRespDTO adminUserInfo = adminUserApi.getUser(Long.valueOf(userId));
            req.setNickName(adminUserInfo.getNickname());
            req.setUserId(adminUserInfo.getId());
            req.setUserName(adminUserInfo.getUsername());
            req.setTaskCode(taskDO.getTaskCode());
            req.setTeamCode(teamDTO.getTeamCode());
            feedBackMemberService.createFeedbackMember(req);
        }
        source.put("code", 0);
        source.put("message", "追加报工单信息成功!");
        return source;
    }


    @PutMapping("/feedbackErp")
    @Operation(summary = "生产报工同步ERP")
    @PreAuthorize("@ss.hasPermission('pro:feedback:erpInterface')")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult feedbackErp(@RequestParam("id") Long id) {
        FeedbackDO feedback = feedbackService.getFeedback(id);

        if (feedback.getWorkorderCode().startsWith("MO")) {
            return error(ErrorCodeConstants.NO_NEED_ERP);
        }

        TaskDO task = taskService.getTask(feedback.getTaskCode());
        // 获取ERP设备编码
        DvMachineryDTO machineryDTO = dvMachineryApi.getMachineryInfo(feedback.getMachineryCode());
        WorkorderDO workorder = workorderService.getWorkorder(feedback.getWorkorderCode());
        String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
        // 获取工作序
        RouteDO route = routeService.getRoute(routeCode);
        RouteProcessExportReqVO exportReqVO = new RouteProcessExportReqVO();
        exportReqVO.setRouteId(route.getId());
        exportReqVO.setProcessCode(task.getProcessCode());
        List<RouteProcessDO> routeProcess = routeProcessService.getRouteProcessList(exportReqVO);
        RouteProcessDO process = routeProcess.get(0);
        Long workorderSequence = process.getWorkorderSequence();

        IssueheaderDTO issueHeader = issueApi.listIssueHeader(new IssueheaderDTO().setWorkorderCode(feedback.getWorkorderCode())) != null ? issueApi.listIssueHeader(new IssueheaderDTO().setWorkorderCode(feedback.getWorkorderCode())).get(0) : null;
        if (issueHeader == null) {
            return error(ErrorCodeConstants.TASK_ISSUE_NOT_EXISTS);
        }

        List<IssueLineDTO> issueLine = issueApi.listIssueLine(new IssueLineDTO().setIssueId(issueHeader.getId())) != null ? issueApi.listIssueLine(new IssueLineDTO().setIssueId(issueHeader.getId())) : null;

        if (issueLine.isEmpty()) {
            return error(ErrorCodeConstants.ISSUE_NOT_EXISTS);
        }
        Date issueTime = issueLine.get(0).getCreateTime(); // 获取最新一条的上料时间

        Map<String, Object> erpParams = new HashMap<>();
        // 基础信息
        erpParams.put("source_no", feedback.getFeedbackCode()); // MES报工单号
        erpParams.put("sffb002", feedback.getUserName()); // 报工人员工号
        erpParams.put("sffb005", feedback.getWorkorderCode()); // 工单单号
        erpParams.put("sffb007", feedback.getProcessCode()); // 作业编号(工序编号)
        erpParams.put("sffb009", feedback.getWorkstationCode()); // 工作站
        erpParams.put("sffb010", machineryDTO.getErpMachineryCode() + "#"); // 设备编号
        erpParams.put("sffb012", feedback.getFeedbackTime().toLocalDate()); // 完成日期
        erpParams.put("sffb013", feedback.getFeedbackTime().toLocalTime()); // 完成时间
        erpParams.put("sffb008", String.valueOf(workorderSequence)); // 完成时间
        Date feedbackDate = Date.from(feedback.getFeedbackTime().atZone(ZoneId.systemDefault()).toInstant());
        Date taskDate = Date.from(task.getCreateTime().atZone(ZoneId.systemDefault()).toInstant());
        long durationInMillis = feedbackDate.getTime() - issueTime.getTime(); // 计算时间跨度毫秒
        long durationInMinutes = durationInMillis / (60 * 1000); // 转为分钟

        erpParams.put("sffb014", durationInMinutes); // 工时（分）
        erpParams.put("sffb015", durationInMinutes); // 机时（分）

        if ("AM006".equals(feedback.getProcessCode()) && "BF".equals(feedback.getMachineryCode().split("-")[1].substring(0, 2))) {
            erpParams.put("sffb017", feedback.getConversionQuantity()); // 良品数量
            erpParams.put("sffb018", feedback.getConversionQuantityUnquanlified()); // 报废数量
            erpParams.put("sffb016", feedback.getConversionUnit()); // 单位
        } else {
            erpParams.put("sffb017", feedback.getQuantityQualified()); // 良品数量
            erpParams.put("sffb018", feedback.getQuantityUnquanlified()); // 报废数量
            erpParams.put("sffb016", feedback.getUnitOfMeasure()); // 单位
        }

        // 调用ERP接口
        String erpResult = workorderERPAPI.workOrderReportCreate(erpParams);
        // String erpResult = "SUCCESS" ;
        // 解析响应结果
        if (!erpResult.contains("SUCCESS")) { // 根据实际接口返回判断
            return error(ErrorCodeConstants.FEEDBACK_ERP_ERROR);
        }
        String erpFeedback = erpResult.split(",")[1];
        feedback.setErpFeedbackStatus("Y");
        feedback.setErpFeedback(erpFeedback);
        feedbackService.updateFeedback(FeedbackConvert.INSTANCE.convert02(feedback));
        return success();
    }

    /**
     * 手动同步ERP入库
     *
     * @param ids
     * @return
     */
    @PutMapping("/syncwarehousingErp")
    @Operation(summary = "完工入库同步ERP")
    // @PreAuthorize("@ss.hasPermission('pro:feedback:erpInterface')")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult syncwarehousingErp(@RequestParam("ids") List<Integer> ids) {
        // 准备调用ERP接口的参数容器
        Map<String, Object> erpParams = new HashMap<>();
        List<Map<String, Object>> workOrders = new ArrayList<>();

        for (Integer id : ids) {
            // 报工单进行数据转换
            // 构建goodsList明细（根据ERP接口要求）
            List<Map<String, Object>> goodsList = new ArrayList<>();

            // 追加校验, 判定当前完工单任务是否为末工序
            FeedbackDO feedbackDO = feedbackService.getFeedback(id.longValue());
            WorkorderDO workorder = workorderService.getWorkorder(feedbackDO.getWorkorderCode());
            TaskDO task = taskService.getTask(feedbackDO.getTaskCode());

            if (!workorder.getWorkorderCode().startsWith("MO")) {
                String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
                // 获取工艺路线详情
                RouteDO route = routeService.getRoute(routeCode);
                RouteProcessExportReqVO routeProcessExportReqVO = new RouteProcessExportReqVO();
                routeProcessExportReqVO.setRouteId(route.getId());
                routeProcessExportReqVO.setProcessCode(feedbackDO.getProcessCode());
                routeProcessExportReqVO.setProcessSequence(task.getProcessSequence());
                List<RouteProcessDO> routeProcess = routeProcessService.getRouteProcessList(routeProcessExportReqVO);
                RouteProcessDO process = routeProcess.get(0);
                String nextProcessCode = Optional.ofNullable(routeProcess.get(0).getNextProcessCode()).orElse(null);

                if (nextProcessCode != null) {
                    // 存在下道制程, 不调用ERP接口
                    return error(ErrorCodeConstants.ROUTE_PROCESS_HAS_DOWN);
                }

                Map<String, Object> detail = new HashMap<>();
                detail.put("sfeb001", feedbackDO.getWorkorderCode());       // 工单单号
                detail.put("sfeb003", "1");                             // 入库类型（示例值，需确认）
                detail.put("sfeb004", feedbackDO.getItemCode());            // 料号
                detail.put("sfeb005", "");       // 产品特征 ware.get("specification")
                //detail.put("sfeb008", ware.get("quantityFeedback"));    // 申请数量

                if ("AM006".equals(feedbackDO.getProcessCode()) && "BF".equals(feedbackDO.getMachineryCode().split("-")[1].substring(0, 2))) {
                    detail.put("sfeb008", feedbackDO.getConversionQuantity());    // 申请数量
                } else {
                    detail.put("sfeb008", feedbackDO.getQuantityQualified());    // 申请数量
                }

                // 基于当前的库存信息
                MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
                exportReqVO.setItemCode(feedbackDO.getItemCode());
                exportReqVO.setBatchCode(feedbackDO.getBatchCode()); // 子批次
                List<MaterialStockDO> materialStock = materialStockService.getMaterialStockList(exportReqVO);

                detail.put("sfeb013", materialStock.get(0).getLocationCode());   // 库位
                detail.put("sfeb014", materialStock.get(0).getAreaCode());       // 储位
                detail.put("sfeb015", feedbackDO.getTaskCode());        // 2025-06-08 改为母批号
                detail.put("source_seq", "");     // MES项次

                goodsList.add(detail);
                // 构建单个工单的master数据
                Map<String, Object> workOrder = new HashMap<>();
                workOrder.put("source_no", feedbackDO.getFeedbackCode());   // MES报工单号
                workOrder.put("sfeadocno", "");                  // 单别（示例值）
                workOrder.put("sfeadocdt", formatDate(new Date()));     // 单据日期
                workOrder.put("sfea001", formatDate(new Date()));       // 过账日期

                AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
                workOrder.put("sfea002", adminUserRespDTO.getUsername());         // 申请人员
                //workOrder.put("sfea002", "tiptop");         // 申请人员
                workOrder.put("goodsList", goodsList);                  // 当前工单明细
                workOrder.put("feedback", feedbackDO);
                workOrders.add(workOrder);
            }
        }
        // 组装最终ERP接口参数
        erpParams.put("workOrders", workOrders);

        // 校验当前完工入库是否为末工序, 末工序则回传ERP接口
        if (workOrders.size() > 0) {
            // 存在入库信息
            // 调用接口方法
            String result = workorderERPAPI.workOrderFinishCreate(erpParams);
            // String result = "ERROR";
            JSONObject jsonResult = JSONObject.parseObject(result);
            // 检查整体执行状态
            if (jsonResult == null ||
                    !jsonResult.getJSONObject("payload")
                            .getJSONObject("std_data")
                            .getJSONObject("execution")
                            .getString("code").equals("0")) {
                return error(ErrorCodeConstants.WAREHOUSING_ERP_ERROR);
            }

            // 解析成功和失败的报工单
            JSONObject parameter = jsonResult.getJSONObject("payload")
                    .getJSONObject("std_data")
                    .getJSONObject("parameter");

            // 2025-07-31 erp完工入库接口存在部分成功的情况, 故此处过滤success_return. 以获取ERP成功入库的报工单信息
            List<Map> successList = parameter.getJSONArray("success_return")
                    .toJavaList(Map.class);

            for (Map<String, Object> map : successList) {
                String feedbackCode = (String) map.get("source_no");
                List<FeedbackDO> feedbackDOS = feedbackService.getFeedbackList(new FeedbackExportReqVO().setFeedbackCode(feedbackCode));
                FeedbackDO queryFeedbackDO = feedbackDOS.get(0);
                queryFeedbackDO.setErpWarehousingStatus("Y");
                feedbackService.updateFeedback(FeedbackConvert.INSTANCE.convert02(queryFeedbackDO));

                // 更新库存表ERP过账状态
                List<MaterialStockDO> materialStockList = materialStockService.getMaterialStockList(new MaterialStockExportReqVO().setBatchCode(queryFeedbackDO.getBatchCode()));
                if (materialStockList.isEmpty()) {
                    return error(ErrorCodeConstants.MATERIALSTOCK_NOT_EXIST);
                }

                MaterialStockDO materialStockDO = materialStockList.get(0);
                materialStockDO.setConfirmStatus("Y");
                materialStockService.updateMaterialStock(MaterialStockConvert.INSTANCE.convert02(materialStockDO));
            }
        } else {
            return error(ErrorCodeConstants.INIT_ERP_PARAM_ERROR);
        }
        return success();
    }

    /**
     * ERP完工入库
     *
     * @param params
     * @return
     */
    @PostMapping("/warehousingErp")
    @Operation(summary = "完工入库同步ERP")
    public CommonResult<String> warehousingErp(@RequestBody Map<String, Object> params) {
        List<Map<String, Object>> objList = (List<Map<String, Object>>) params.get("wareList");
        // 用户手动选择的仓库, 库区 , 库位信息
        Integer warehouseId = (Integer) params.get("warehouseId");
        Integer locationId = (Integer) params.get("locationId");
        Integer areaId = (Integer) params.get("areaId");

        WarehouseDO warehouseDO = warehouseService.getWarehouse(Long.valueOf(warehouseId));
        StorageLocationDO locationDO = locationService.getStorageLocation(Long.valueOf(locationId));
        StorageAreaDO areaDO = areaService.getStorageArea(Long.valueOf(areaId));

        String transactionType_out = Constant.TRANSACTION_TYPE_WAREHOUSE_TRANS_OUT;
        String transactionType_in = Constant.TRANSACTION_TYPE_WAREHOUSE_TRANS_IN;

        List<FeedbackWarehousingLogDO> logList = new ArrayList<>(); // 存储日志的列表

        // 准备调用ERP接口的参数容器
        Map<String, Object> erpParams = new HashMap<>();
        List<Map<String, Object>> workOrders = new ArrayList<>();

        // 遍历每个报工单进行数据转换
        for (Map<String, Object> ware : objList) {
            // 构建日志对象
            FeedbackWarehousingLogDO logDO = new FeedbackWarehousingLogDO();

            // 构建goodsList明细（根据ERP接口要求）
            List<Map<String, Object>> goodsList = new ArrayList<>();
            // 追加校验, 判定当前完工单任务是否为末工序
            WorkorderDO workorder = workorderService.getWorkorder((String) ware.get("workorderCode"));
            if (workorder.getWorkorderCode().startsWith("MO")) {
                continue;
            }
            TaskDO task = taskService.getTask((String) ware.get("taskCode"));
            Integer id = (Integer) ware.get("id");
            FeedbackDO feedbackDO = feedbackService.getFeedback(id.longValue());
            logDO.setFeedbackCode(feedbackDO.getFeedbackCode());
            logDO.setWorkstationId(feedbackDO.getWorkstationId());
            logDO.setWorkstationCode(feedbackDO.getWorkstationCode());
            logDO.setWorkstationName(feedbackDO.getWorkstationName());
            logDO.setWorkorderId(workorder.getId());
            logDO.setWorkorderCode(workorder.getWorkorderCode());
            logDO.setWorkorderName(workorder.getWorkorderName());
            logDO.setProcessId(feedbackDO.getProcessId());
            logDO.setProcessCode(feedbackDO.getProcessCode());
            logDO.setProcessName(feedbackDO.getProcessName());
            logDO.setTaskId(task.getId());
            logDO.setTaskCode(task.getTaskCode());
            logDO.setItemId(workorder.getProductId());
            logDO.setItemCode(workorder.getProductCode());
            logDO.setItemName(workorder.getProductName());
            logDO.setUnitOfMeasure(workorder.getUnitOfMeasure());
            logDO.setSpecification(workorder.getProductSpc());
            logDO.setQuantityFeedback(feedbackDO.getQuantityFeedback());
            logDO.setQuantityQualified(feedbackDO.getQuantityQualified());
            logDO.setQuantityUnquanlified(feedbackDO.getQuantityUnquanlified());
            logDO.setUserName(feedbackDO.getUserName());
            logDO.setNickName(feedbackDO.getNickName());
            logDO.setBatchCode(feedbackDO.getBatchCode());
            logDO.setErpBatchCode(feedbackDO.getErpBatchCode());
            logDO.setMachineryName(feedbackDO.getMachineryName());
            logDO.setMachineryCode(feedbackDO.getMachineryCode());
            logDO.setMachineryId(feedbackDO.getMachineryId());
            logDO.setErpFeedback(feedbackDO.getErpFeedback());
            logDO.setErpFeedbackStatus(feedbackDO.getErpFeedbackStatus());
            logDO.setConversionQuantity(feedbackDO.getConversionQuantity());
            logDO.setConversionUnit(feedbackDO.getConversionUnit());
            logDO.setConversionQuantityUnquanlified(feedbackDO.getConversionQuantityUnquanlified());
            // 2025-7-15 修改入库位置
            // Number warehouseId = (Number) ware.get("warehouseId");
            logDO.setWarehouseId(warehouseId.longValue());
            logDO.setWarehouseName(String.valueOf(ware.get("warehouseName")));
            logDO.setWarehouseCode(String.valueOf(ware.get("warehouseCode")));
            // 2025-7-15 修改入库位置
            // Number locationId = (Number) ware.get("locationId");
            logDO.setLocationId(locationId.longValue());
            logDO.setLocationName(String.valueOf(ware.get("locationName")));
            logDO.setLocationCode(String.valueOf(ware.get("locationCode")));
            // 2025-7-15 修改入库位置
            //Number areaId = (Number) ware.get("areaId");
            logDO.setAreaId(areaId.longValue());
            logDO.setAreaName(String.valueOf(ware.get("areaName")));
            logDO.setAreaCode(String.valueOf(ware.get("areaCode")));

            String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
            // 获取工艺路线详情
            RouteDO route = routeService.getRoute(routeCode);
            RouteProcessExportReqVO routeProcessExportReqVO = new RouteProcessExportReqVO();
            routeProcessExportReqVO.setRouteId(route.getId());
            routeProcessExportReqVO.setProcessCode((String) ware.get("processCode"));
            routeProcessExportReqVO.setProcessSequence(task.getProcessSequence());
            List<RouteProcessDO> routeProcess = routeProcessService.getRouteProcessList(routeProcessExportReqVO);
            RouteProcessDO process = routeProcess.get(0);
            String nextProcessCode = Optional.ofNullable(routeProcess.get(0).getNextProcessCode()).orElse(null);
            logDO.setErpWarehousingStatus("N");

            if (nextProcessCode != null) {
                // 存在下道制程, 不调用ERP接口
                // logList.add(logDO);
                continue;
            }

            Map<String, Object> detail = new HashMap<>();
            detail.put("sfeb001", ware.get("workorderCode"));       // 工单单号
            detail.put("sfeb003", "1");                             // 入库类型（示例值，需确认）
            detail.put("sfeb004", ware.get("itemCode"));            // 料号
            detail.put("sfeb005", "");       // 产品特征 ware.get("specification")
            if ("AM006".equals(feedbackDO.getProcessCode()) && "BF".equals(feedbackDO.getMachineryCode().split("-")[1].substring(0, 2))) {
                detail.put("sfeb008", feedbackDO.getConversionQuantity());    // 申请数量
            } else {
                detail.put("sfeb008", feedbackDO.getQuantityQualified());    // 申请数量
            }
            // 基于当前的库存信息
            MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
            exportReqVO.setItemCode((String) ware.get("itemCode"));
            exportReqVO.setBatchCode((String) ware.get("batchCode")); // 子批次
            List<MaterialStockDO> materialStock = materialStockService.getMaterialStockList(exportReqVO);

            //detail.put("sfeb013", materialStock.get(0).getLocationCode());   // 库位
            //detail.put("sfeb014", materialStock.get(0).getAreaCode());       // 储位

            // 2025-7-17
            detail.put("sfeb013", locationDO.getLocationCode());   // 库位
            detail.put("sfeb014", areaDO.getAreaCode());       // 储位


            detail.put("sfeb015", ware.get("taskCode"));        // 2025-06-08 改为母批号
            detail.put("source_seq", "");     // MES项次

            goodsList.add(detail);
            // 构建单个工单的master数据
            Map<String, Object> workOrder = new HashMap<>();
            workOrder.put("source_no", ware.get("feedbackCode"));   // MES报工单号
            workOrder.put("sfeadocno", "");                  // 单别（示例值）
            workOrder.put("sfeadocdt", formatDate(new Date()));     // 单据日期
            workOrder.put("sfea001", formatDate(new Date()));       // 过账日期
            AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
            workOrder.put("sfea002", adminUserRespDTO.getUsername());         // 申请人员
            // workOrder.put("sfea002", feedbackDO.getUserName());         // 申请人员
            workOrder.put("goodsList", goodsList);                  // 当前工单明细
            workOrder.put("feedback", feedbackDO);
            workOrders.add(workOrder);
            // 追加日志记录
            logList.add(logDO);
        }

        // 批量插入所有日志记录（包括需要同步和不需要同步的）
        if (!logList.isEmpty()) {
            feedbackWarehousingLogService.createBatch(logList);
        }

        // 组装最终ERP接口参数
        erpParams.put("workOrders", workOrders);
        // 校验当前完工入库是否为末工序, 末工序则回传ERP接口
        if (workOrders.size() > 0) {
            // 存在入库信息
            // 调用接口方法
            String result = workorderERPAPI.workOrderFinishCreate(erpParams);
            JSONObject jsonResult = JSONObject.parseObject(result);
            // 检查整体执行状态
            if (jsonResult == null || !jsonResult.getJSONObject("payload").getJSONObject("std_data").getJSONObject("execution").getString("code").equals("0")) {
                return error(ErrorCodeConstants.WAREHOUSING_ERP_ERROR);
            }

            // 解析成功和失败的报工单
            JSONObject parameter = jsonResult.getJSONObject("payload").getJSONObject("std_data").getJSONObject("parameter");

            // 2025-07-31 erp完工入库接口存在部分成功的情况, 故此处过滤success_return. 以获取ERP成功入库的报工单信息
            List<Map> successList = parameter.getJSONArray("success_return").toJavaList(Map.class);

            List<Map> failList = parameter.getJSONArray("fail_return").toJavaList(Map.class);

            List<FeedbackDO> feedbackUpdateList = new ArrayList<>();
            // 当前仅对ERP成功入库的单据进行处理
            for (Map<String, Object> map : successList) {
                String feedbackCode = (String) map.get("source_no");
                List<FeedbackDO> feedbackDOS = feedbackService.getFeedbackList(new FeedbackExportReqVO().setFeedbackCode(feedbackCode));
                FeedbackDO feedbackDO = feedbackDOS.get(0);
                feedbackDO.setErpWarehousingStatus("Y");
                feedbackUpdateList.add(feedbackDO);
                // feedbackService.updateFeedback(FeedbackConvert.INSTANCE.convert02(feedbackDO));

                // 更新日志状态为成功
                for (FeedbackWarehousingLogDO logDO : logList) {
                    if (feedbackCode.equals(logDO.getFeedbackCode())) {
                        logDO.setErpWarehousingStatus("Y");
                        logDO.setWarehouseId(Long.valueOf(warehouseId));
                        logDO.setWarehouseCode(warehouseDO.getWarehouseCode());
                        logDO.setWarehouseName(warehouseDO.getWarehouseName());
                        logDO.setLocationId(locationDO.getId());
                        logDO.setLocationCode(locationDO.getLocationCode());
                        logDO.setLocationName(locationDO.getLocationName());
                        logDO.setAreaId(areaDO.getId());
                        logDO.setAreaCode(areaDO.getAreaCode());
                        logDO.setAreaName(areaDO.getAreaName());
                    }
                }
                // 更新库存表ERP过账状态
                List<MaterialStockDO> materialStockList = materialStockService.getMaterialStockList(new MaterialStockExportReqVO().setBatchCode(feedbackDO.getBatchCode()));
                if (materialStockList.isEmpty()) {
                    return error(ErrorCodeConstants.MATERIALSTOCK_NOT_EXIST);
                }

                MaterialStockDO materialStockDO = materialStockList.get(0);
                TransactionUpdateReqVO transaction_out = new TransactionUpdateReqVO();
                transaction_out.setTransactionType(transactionType_out);
                BeanUtils.copyBeanProp(transaction_out, materialStockDO);
                transaction_out.setWarehouseId(materialStockDO.getWarehouseId());
                transaction_out.setWarehouseCode(materialStockDO.getWarehouseCode());
                transaction_out.setWarehouseName(materialStockDO.getWarehouseName());
                transaction_out.setLocationId(materialStockDO.getLocationId());
                transaction_out.setLocationCode(materialStockDO.getLocationCode());
                transaction_out.setLocationName(materialStockDO.getLocationName());
                transaction_out.setAreaId(materialStockDO.getAreaId());
                transaction_out.setAreaCode(materialStockDO.getAreaCode());
                transaction_out.setAreaName(materialStockDO.getAreaName());
                transaction_out.setTransactionFlag(-1);//库存减少
                transaction_out.setTransactionQuantity(materialStockDO.getQuantityOnhand());
                transaction_out.setBatchCode(materialStockDO.getBatchCode());
                transactionService.processTransaction(transaction_out);

                //再执行入库
                TransactionUpdateReqVO transaction_in = new TransactionUpdateReqVO();
                transaction_in.setTransactionType(transactionType_in);
                BeanUtils.copyBeanProp(transaction_in, materialStockDO);
                transaction_in.setId(null);
                transaction_in.setWarehouseId(warehouseDO.getId());
                transaction_in.setWarehouseCode(warehouseDO.getWarehouseCode());
                transaction_in.setWarehouseName(warehouseDO.getWarehouseName());
                transaction_in.setLocationId(locationDO.getId());
                transaction_in.setLocationCode(locationDO.getLocationCode());
                transaction_in.setLocationName(locationDO.getLocationName());
                transaction_in.setAreaId(areaDO.getId());
                transaction_in.setAreaCode(areaDO.getAreaCode());
                transaction_in.setAreaName(areaDO.getAreaName());
                transaction_in.setTransactionFlag(1);//库存增加
                transaction_in.setTransactionDate(LocalDateTime.now());
                transaction_in.setRecptStatus("Y");
                transaction_in.setConfirmStatus("Y");
                //由于是新增的库存记录所以需要将查询出来的库存记录ID置为空
                transaction_in.setMaterialStockId(null);
                //设置入库相关联的出库事务ID
                transaction_in.setRelatedTransactionId(transaction_out.getId());
                transaction_in.setTransactionQuantity(materialStockDO.getQuantityOnhand());
                transaction_in.setBatchCode(materialStockDO.getBatchCode());
                transactionService.processTransaction(transaction_in);

                // materialStockDO.setConfirmStatus("Y");
                //materialStockService.updateMaterialStock(MaterialStockConvert.INSTANCE.convert02(materialStockDO));
            }


            // 更新报工单状态
            feedbackService.updateFeedbackBatch(feedbackUpdateList);

            // 更新日志中的成功状态
            feedbackWarehousingLogService.updateBatch(logList);
        }
        return success("SUCCESS");
    }


    @GetMapping("/checkProcess")
    @Operation(summary = "校验报工单所属工序")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:feedback:query')")
    public CommonResult<Map<String, Object>> checkProcess(@RequestParam("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        FeedbackDO feedback = feedbackService.getFeedback(id);
        TaskDO task = taskService.getTask(feedback.getTaskId());
        WorkorderDO workorder = workorderService.getWorkorder(feedback.getWorkorderId());
        String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
        // 获取工艺路线详情
        RouteDO route = routeService.getRoute(routeCode);
        RouteProcessExportReqVO exportReqVO = new RouteProcessExportReqVO();
        exportReqVO.setRouteId(route.getId());
        exportReqVO.setProcessCode(task.getProcessCode());
        exportReqVO.setProcessSequence(task.getProcessSequence());
        List<RouteProcessDO> routeProcess = routeProcessService.getRouteProcessList(exportReqVO);
        RouteProcessDO process = routeProcess.get(0);
        String nextProcessCode = Optional.ofNullable(routeProcess.get(0).getNextProcessCode()).orElse(null);
        // 不存在下道工序
        if (nextProcessCode == null) {
            // 开始汇总当前任务单总报工产量
            result.put("process", null);
            result.put("taskStatus", feedback.getTaskStatus());
            result.put("feedbackCode", feedback.getFeedbackCode());
            result.put("taskCode", task.getTaskCode());
            result.put("workorderCode", workorder.getWorkorderCode());
            result.put("planQuantity", task.getQuantity());
            // BigDecimal sumQuantity = BigDecimal.ZERO;
            Double sumQuantity = 0.0;
            List<FeedbackDO> feedbackDOList = feedbackService.getFeedbackList(new FeedbackExportReqVO().setTaskCode(task.getTaskCode()));
            for (FeedbackDO feedbackDO : feedbackDOList) {
                //sumQuantity.add(BigDecimal.valueOf(feedbackDO.getQuantityQualified()));
                sumQuantity += feedbackDO.getQuantityQualified();
            }
            result.put("qualityQuantity", sumQuantity);
            return success(result);
        } else {
            result.put("process", nextProcessCode);
            result.put("feedbackCode", feedback.getFeedbackCode());
            result.put("taskCode", task.getTaskCode());
            return success(result);
        }
    }

    /**
     * 看板: 各车间产量
     *
     * @return
     */
    @GetMapping("/workshop-capacity")
    public Map<String, Object> getWorkshopCapacity() {
        List<Map<String, Object>> source = feedbackService.getCapacity();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("dimensions", Arrays.asList("name", "value"));
        result.put("source", source);
        return result;
    }


    @GetMapping("/traceFeedbackPage")
    @Operation(summary = "获得生产报工记录分页")
    @PreAuthorize("@ss.hasPermission('pro:feedback:query')")
    public CommonResult<PageResult<FeedbackRespVO>> traceFeedbackPage(@Valid FeedbackPageReqVO pageVO) {
        String workorderCode = pageVO.getWorkorderCode();
        String taskCode = pageVO.getTaskCode();
        String batchCode = pageVO.getBatchCode();

        if (workorderCode == null && taskCode == null && batchCode == null) {
            return success();
        }

        Set<String> feedbackCodeSet = new HashSet<>();

        if (workorderCode != null && !"".equals(workorderCode)) {
            // 基于工单获取所有的报工单信息
            List<FeedbackDO> feedbackDOS = feedbackService.getFeedbackList(new FeedbackExportReqVO().setWorkorderCode(workorderCode));
            if (!feedbackDOS.isEmpty()) {
                for (FeedbackDO feedbackDO : feedbackDOS) {
                    feedbackCodeSet.add(feedbackDO.getFeedbackCode());
                }
            }
        }

        if (taskCode != null && taskCode != "") {
            // 基于任务单获取报工单信息
            List<FeedbackDO> feedbackDOS = feedbackService.getFeedbackList(new FeedbackExportReqVO().setTaskCode(taskCode));
            if (!feedbackDOS.isEmpty()) {
                for (FeedbackDO feedbackDO : feedbackDOS) {
                    feedbackCodeSet.add(feedbackDO.getFeedbackCode());
                }
            }
        }

        if (batchCode != null && batchCode != "") {
            // 基于批次获取报工单信息
            List<FeedbackDO> feedbackDOS = feedbackService.getFeedbackList(new FeedbackExportReqVO().setBatchCode(batchCode));
            if (!feedbackDOS.isEmpty()) {
                feedbackCodeSet.add(feedbackDOS.get(0).getFeedbackCode());
            }
        }

        if (feedbackCodeSet.isEmpty()) {
            return success();
        }

        List<String> feedbackCodeList = new ArrayList<>(feedbackCodeSet);
        pageVO.setFeedbackCodeList(feedbackCodeList);
        pageVO.setWorkorderCode(null);
        pageVO.setTaskCode(null);
        pageVO.setBatchCode(null);

        PageResult<FeedbackDO> pageResult = feedbackService.getFeedbackPage(pageVO);
        return success(FeedbackConvert.INSTANCE.convertPage(pageResult));
    }

    @PreAuthorize("@ss.hasPermission('pro:feedback:query')")
    @Operation(summary = "初始化IOT报工数据")
    @GetMapping("/getIotFeedbackData")
    public CommonResult<Number> getIotFeedbackData(@RequestParam("machineryCode") String machineryCode) {
        DvMachineryDTO machinery = dvMachineryApi.getMachineryInfo(machineryCode);
        if (machinery == null) {
            return error(ErrorCodeConstants.MACHINERY_NOT_EXISTS);
        }

        Map<String, Object> resultMap = feedbackService.getIotFeedbackLog(machinery.getErpMachineryCode());
        if (resultMap == null || resultMap.isEmpty()) {
            return error(ErrorCodeConstants.IOT_FEEDBACK_NOT_EXISTS);
        }
        Number iotQuantity = Optional.ofNullable((Number) resultMap.get("quantity")).orElse(0);
        return success(iotQuantity);
    }


    @Operation(summary = "报工合格证套打数据")
    @GetMapping("/initCertificate")
    public CommonResult<List<JSONObject>> initCertificate(@RequestParam("ids") List<Long> feedbackIds) {
        List<JSONObject> resultList = new ArrayList<>();

        for (Long feedbackId : feedbackIds) {
            try {
                FeedbackDO feedbackDO = feedbackService.getFeedback(feedbackId);
                if (feedbackDO == null) {
                    // 跳过不存在的报工单，继续处理其他ID
                    continue;
                }
                String specification = feedbackDO.getSpecification();
                String[] specifications = specification.split("\\*");
                List<String> numbers = Arrays.stream(specifications).map(this::extractNumber).collect(Collectors.toList());

                String quantitative = numbers.size() > 0 ? numbers.get(0) : null;
                String wide = numbers.size() > 1 ? numbers.get(1) : null;
                // 规格中存在密度, 若当前的截取规格发现宽幅为空, 则将其字段默认改为密度
                String width = numbers.size() > 2 ? numbers.get(2) : null;

                // 密度
                double density = 1.4  / 1000;

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("itemName", feedbackDO.getItemName());
                jsonObject.put("specification", feedbackDO.getSpecification());
                jsonObject.put("workorderCode", feedbackDO.getWorkorderCode());
                jsonObject.put("unitOfMeasure", feedbackDO.getUnitOfMeasure());
                String volumes = feedbackDO.getVolumesNumber().substring(feedbackDO.getVolumesNumber().length() - 3);
                String volumesNumber = volumes.substring(0, 2);
                String shiftCode = volumes.substring(2, 3);

                jsonObject.put("processCode", feedbackDO.getProcessCode());
                jsonObject.put("batchCode", feedbackDO.getBatchCode());

                jsonObject.put("productBatchCode", feedbackDO.getWorkorderCode() + "-" + volumesNumber);
                jsonObject.put("shiftCode", shiftCode);
                jsonObject.put("quantitative", quantitative);
                jsonObject.put("wide", wide);

                jsonObject.put("width", width);
                jsonObject.put("quantityQualified", feedbackDO.getQuantityQualified());

                jsonObject.put("volumesNumber", feedbackDO.getVolumesNumber());

                double quantitativeInt = 1.0;
                double wideInt = 1.0;
                double widthInt = 1.0;

                if (quantitative != null) {
                    quantitativeInt = Integer.parseInt(quantitative) / 1000.0;
                }
                if (wide != null) {
                    wideInt = Integer.parseInt(wide) / 1000.0;
                }
                if (width != null) {
                    widthInt = Double.parseDouble(width) / 1000.0;
                }

                BigDecimal bd = null;
                if(width == null && "米".equals(feedbackDO.getUnitOfMeasure()) && !"AM006".equals(feedbackDO.getProcessCode()) ){
                    bd = BigDecimal.valueOf(feedbackDO.getQuantityQualified() * quantitativeInt * wideInt * density).setScale(1, RoundingMode.HALF_UP);
                }else{
                    bd = BigDecimal.valueOf(feedbackDO.getQuantityQualified() * quantitativeInt * wideInt * widthInt).setScale(1, RoundingMode.HALF_UP);
                }

                jsonObject.put("weight", bd);

                LocalDateTime now = LocalDateTime.now();
                String nowStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                jsonObject.put("printDate", nowStr);
                jsonObject.put("id", feedbackDO.getId());

                JSONObject jsonQc = new JSONObject();
                jsonQc.put("id", feedbackDO.getId());
                jsonQc.put("type", "feedback");
                jsonObject.put("jsonQc", jsonQc);
                resultList.add(jsonObject);
            } catch (Exception e) {
                System.out.println("处理报工单ID " + feedbackId + " 时发生错误: " + e.getMessage());
            }
        }
        if (resultList.isEmpty()) {
            return error(ErrorCodeConstants.FEEDBACK_NOT_EXISTS);
        }
        return success(resultList);
    }

    @PostMapping("/feedbackReport")
    @Operation(summary = "获得生产报工记录分页")
    @PreAuthorize("@ss.hasPermission('pro:feedback:query')")
    public CommonResult<List<JSONObject>> feedbackReport(@RequestBody JSONObject json) {
        String workorderCode = json.getString("workorderCode");
        String beginTimeString = json.getString("beginTime");

        LocalDateTime beginTime = null;
        if (beginTimeString != null) {
            beginTime = LocalDateTime.parse(beginTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        String endTimeString = json.getString("endTime");
        LocalDateTime endTime = null;
        if (endTimeString != null) {
            endTime = LocalDateTime.parse(endTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        String processName = (String) json.get("processName") != null ? (String) json.get("processName") : null;
        String nikeName = (String) json.get("nikeName") != null ? (String) json.get("nikeName") : null;
        String processCode = (String) json.get("processCode") != null ? (String) json.get("processCode") : null;
        String participantKeyword = (String) json.get("participantKeyword") != null ? (String) json.get("participantKeyword") : null;
        String unitOfMeasure = (String) json.get("unitOfMeasure") != null ? (String) json.get("unitOfMeasure") : null;

        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("workorderCode", workorderCode);
        queryMap.put("beginTime", beginTime);
        queryMap.put("endTime", endTime);
        queryMap.put("processName", processName);
        queryMap.put("nikeName", nikeName);
        queryMap.put("processCode", processCode);
        queryMap.put("participantKeyword", participantKeyword);
        queryMap.put("unitOfMeasure", unitOfMeasure);

        // 计算合格数量和不合格数量的总和
        BigDecimal totalQuantityQualified = BigDecimal.ZERO;
        BigDecimal totalQuantityUnqualified = BigDecimal.ZERO;

        List<Map<String, Object>> feedbackList = feedbackService.initFeedbackReport(queryMap);
        BigDecimal totalUserProductionQuantity = feedbackList.stream().map(map -> {
                    BigDecimal userProductionQuantity = (BigDecimal) map.get("user_production_quantity");
                    return userProductionQuantity != null ? userProductionQuantity : null;
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
        List<JSONObject> jsonList = feedbackList.stream().map(map -> {
            JSONObject jsonObject = new JSONObject();
            map.forEach((key, value) -> {
                if ("user_production_quantity".equals(key) && value instanceof BigDecimal && ((BigDecimal) value).compareTo(BigDecimal.ZERO) == 0) {
                    jsonObject.put(key, null);
                } else {
                    jsonObject.put(key, value);
                }
            });
            return jsonObject;
        }).collect(Collectors.toList());

        for (Map<String, Object> map : feedbackList) {
            Object qualifiedObj = map.get("quantity_qualified");
            Object unqualifiedObj = map.get("quantity_unquanlified");

            BigDecimal qualified = BigDecimal.ZERO;
            BigDecimal unqualified = BigDecimal.ZERO;

            if (qualifiedObj != null) {
                if (qualifiedObj instanceof BigDecimal) {
                    qualified = (BigDecimal) qualifiedObj;
                } else if (qualifiedObj instanceof Number) {
                    qualified = BigDecimal.valueOf(((Number) qualifiedObj).doubleValue());
                }
            }

            if (unqualifiedObj != null) {
                if (unqualifiedObj instanceof BigDecimal) {
                    unqualified = (BigDecimal) unqualifiedObj;
                } else if (unqualifiedObj instanceof Number) {
                    unqualified = BigDecimal.valueOf(((Number) unqualifiedObj).doubleValue());
                }
            }

            totalQuantityQualified = totalQuantityQualified.add(qualified);
            totalQuantityUnqualified = totalQuantityUnqualified.add(unqualified);
        }

        JSONObject resultInfo = new JSONObject();
        resultInfo.put("participantKeyword", participantKeyword);
        if (totalUserProductionQuantity.compareTo(BigDecimal.ZERO) == 0) {
            totalUserProductionQuantity = null;
        }
        resultInfo.put("totalUserProductionQuantity", totalUserProductionQuantity);
        resultInfo.put("totalQuantityQualified", totalQuantityQualified);
        resultInfo.put("totalQuantityUnqualified", totalQuantityUnqualified);
        resultInfo.put("title", "汇总: ");
        if (!jsonList.isEmpty()) {
            jsonList.get(0).putAll(resultInfo);
        }
        return success(jsonList);
    }


    private String extractNumber(String input) {
        if (input == null) {
            return null;
        }
        String regex = "[^0-9]+";
        return input.replaceAll(regex, "");
    }


    /**
     * 撤销报工产成品入库
     * 将待入库状态改为已入库
     *
     * @param params
     * @return
     */
    @PostMapping("/reWareHousing")
    public CommonResult<String> reWareHousing(@RequestBody Map<String, Object> params) {
        List<Map<String, Object>> objList = (List<Map<String, Object>>) params.get("wareList");

        for (Map<String, Object> map : objList) {
            Integer feedbackId = (Integer) map.get("id");
            FeedbackDO queryFeedbackDO = feedbackService.getFeedback(feedbackId.longValue());
            if ("Y".equals(queryFeedbackDO.getErpWarehousingStatus())) {
                return error(ErrorCodeConstants.ERP_WAREHOUSING_EXISTS);
            }
        }

        for (Map<String, Object> map : objList) {
            // 基于当前的库存信息
            MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
            exportReqVO.setItemCode((String) map.get("itemCode"));
            exportReqVO.setBatchCode((String) map.get("batchCode"));
            exportReqVO.setRecptStatus("Y");
            List<MaterialStockDO> materialStock = materialStockService.getMaterialStockList(exportReqVO);
            if(materialStock.isEmpty()){
                return error(ErrorCodeConstants.MATERIAL_STOCK_NOT_EXISTS);
            }
            materialStock.get(0).setRecptStatus("N");
            materialStockService.updateMaterialStock(BeanUtil.toBean(materialStock.get(0), MaterialStockUpdateReqVO.class));

            // 修改当前单据状态为已完成
            Integer feedbackId = (Integer) map.get("id");
            FeedbackDO queryFeedbackDO = feedbackService.getFeedback(feedbackId.longValue());
            queryFeedbackDO.setStatus("FINISHED");
            feedbackService.updateFeedback(FeedbackConvert.INSTANCE.convert02(queryFeedbackDO));
        }
        return success("success");
    }


    @PutMapping("/updateMergeStatus")
    @Operation(summary = "更新生产报工合并状态")
    @PreAuthorize("@ss.hasPermission('pro:feedback:update')")
    @Transactional
    public CommonResult<Boolean> updateFeedback(@RequestParam("id") Long id) {
        FeedbackDO feedbackDO = feedbackService.getFeedback(id);
        if(feedbackDO == null){
            return error(ErrorCodeConstants.FEEDBACK_NOT_EXISTS);
        }
        String changeStatus = "Y".equals(feedbackDO.getMergeStatus()) ? "N" : "Y";
        feedbackDO.setMergeStatus(changeStatus);
        feedbackService.updateFeedback(FeedbackConvert.INSTANCE.convert02(feedbackDO));
        return success();
    }

    @PutMapping("/updateRemark")
    @Operation(summary = "更新生产报工记录备注")
    @PreAuthorize("@ss.hasPermission('pro:feedback:update')")
    @Transactional
    public CommonResult<Boolean> updateFeedbackRemark(@Valid @RequestBody FeedbackUpdateReqVO updateReqVO) {
        feedbackService.updateFeedback(updateReqVO);
        return success(true);
    }





}