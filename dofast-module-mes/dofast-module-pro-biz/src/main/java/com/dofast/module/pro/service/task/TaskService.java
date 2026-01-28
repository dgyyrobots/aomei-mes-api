package com.dofast.module.pro.service.task;

import java.util.*;
import javax.validation.*;
import com.dofast.module.pro.controller.admin.task.vo.*;
import com.dofast.module.pro.dal.dataobject.task.TaskDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 生产任务 Service 接口
 *
 * @author 芋道源码
 */
public interface TaskService {

    /**
     * 创建生产任务
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTask(@Valid TaskCreateReqVO createReqVO);

    /**
     * 更新生产任务
     *
     * @param updateReqVO 更新信息
     */
    void updateTask(@Valid TaskUpdateReqVO updateReqVO);

    /**
     * 删除生产任务
     *
     * @param id 编号
     */
    void deleteTask(Long id);

    /**
     * 获得生产任务
     *
     * @param id 编号
     * @return 生产任务
     */
    TaskDO getTask(Long id);

    TaskDO getTask(String taskCode);

    TaskDO getTaskWithLock(Long id);

    /**
     * 获得我的生产任务
     *
     * @param pageReqVO 编号
     * @return 生产任务
     */
    PageResult<TaskDO> getMyTask(TaskPageReqVO pageReqVO);

    List<TaskDO> getMyTaskList(TaskExportReqVO taskExportReqVO);

    /**
     * 获得生产任务列表
     *
     * @param ids 编号
     * @return 生产任务列表
     */
    List<TaskDO> getTaskList(Collection<Long> ids);

    /**
     * 获得生产任务分页
     *
     * @param pageReqVO 分页查询
     * @return 生产任务分页
     */
    PageResult<TaskDO> getTaskPage(TaskPageReqVO pageReqVO);

    /**
     * 获得生产任务分页根据SourceCode
     *
     * @param pageReqVO 分页查询
     * @return 生产任务分页
     */
    public PageResult<TaskDO> getTaskPageBySourceCode(TaskPageReqVO pageReqVO);

    /**
     * 获得生产任务列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 生产任务列表
     */
    List<TaskDO> getTaskList(TaskExportReqVO exportReqVO);
    List<TaskDO> getTaskList(TaskListVO listVO);

    /**
     * 更新任务状态
     * @param taskId
     * @param status
     */
    void updateTaskStatus(Long taskId, String status);

    List<TaskDO> getTaskByOrder(Long workOrderId);

    /**
     * 获得生产任务
     *
     * @param id 编号
     * @return 生产任务
     */
    TaskDO getTask(Long id,Long processId);


    Boolean updatePrintById(Long id);

    /**
     * 获得生产任务
     *
     * @param teamCode 班组编号
     * @return 生产任务
     */
    List<TaskDO> getTaskByTeamCode(String teamCode);

    Map<String, Integer>  getCountMonthTaskLastYear();

    Map<String, Integer>  getCountMonthTaskThisYear();

}
