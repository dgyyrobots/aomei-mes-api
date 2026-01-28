package com.dofast.module.pro.job;

import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.pro.controller.admin.workorder.vo.WorkorderListVO;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.module.pro.service.workorder.WorkorderOracleService;
import com.dofast.module.pro.service.workorder.WorkorderService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class closeWorkorderJob implements JobHandler {

    @Resource
    private WorkorderOracleService workorderOracleService;

    @Resource
    private WorkorderService workorderService;

    @Override
    public String execute(String param) {
        try {
            // 初始化ERP已发放工单信息
            List<Map<String, Object>> workOrderList = workorderOracleService.initWorkorderInfo();
            if (workOrderList.isEmpty()) {
                return "未获取到工单信息!";
            }

            // 初始化ERP结案工单信息
            List<Map<String, Object>> closeWorkOrderList = workorderOracleService.initCloseWorkorderInfo();
            if (closeWorkOrderList.isEmpty()) {
                return "未获取到结案工单信息!";
            }

            // 获取当前系统的工单
            List<WorkorderDO> mesWorkorderList = workorderService.getWorkorderList(new WorkorderListVO());

            Map<String, WorkorderDO> mesWorkorderMap = mesWorkorderList.stream()
                    .collect(Collectors.toMap(WorkorderDO::getWorkorderCode, workorder -> workorder));

            // 处理需要结案的工单
            List<WorkorderDO> toCloseWorkorders = new ArrayList<>();
            for (Map<String, Object> erpCloseWorkorder : closeWorkOrderList) {
                String workorderCode = (String) erpCloseWorkorder.get("WORKORDER_CODE");
                WorkorderDO mesWorkorder = mesWorkorderMap.get(workorderCode);
                if (mesWorkorder != null && !"Y".equals(mesWorkorder.getCloseFlag())) {
                    mesWorkorder.setCloseFlag("Y");
                    mesWorkorder.setStatus("FINISHED");
                    toCloseWorkorders.add(mesWorkorder);
                }
            }

            // 处理需要开启的工单
            List<WorkorderDO> toOpenWorkorders = new ArrayList<>();
            for (Map<String, Object> erpWorkorder : workOrderList) {
                String workorderCode = (String) erpWorkorder.get("WORKORDER_CODE");
                WorkorderDO mesWorkorder = mesWorkorderMap.get(workorderCode);
                if (mesWorkorder != null && !"N".equals(mesWorkorder.getCloseFlag())) {
                    mesWorkorder.setCloseFlag("N");
                    mesWorkorder.setStatus("CONFIRMED");
                    toOpenWorkorders.add(mesWorkorder);
                }
            }

            // 更新数据库
            if (!toCloseWorkorders.isEmpty()) {
                workorderService.updateBatch(toCloseWorkorders);
                System.out.println("成功结案工单数量: " + toCloseWorkorders.size());
            }

            if (!toOpenWorkorders.isEmpty()) {
                workorderService.updateBatch(toOpenWorkorders);
                System.out.println("成功开启工单数量: " + toOpenWorkorders.size());
            }
            return String.format("处理完成。结案工单：%d个，开启工单：%d个", toCloseWorkorders.size(), toOpenWorkorders.size());
        } catch (Exception e) {
            return "处理工单状态更新失败：" + e.getMessage();
        }
    }


}
