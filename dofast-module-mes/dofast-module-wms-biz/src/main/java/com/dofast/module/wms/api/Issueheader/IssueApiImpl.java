package com.dofast.module.wms.api.Issueheader;

import cn.hutool.core.bean.BeanUtil;
import com.dofast.module.wms.api.Issueheader.dto.IssueLineDTO;
import com.dofast.module.wms.api.Issueheader.dto.IssueheaderDTO;
import com.dofast.module.wms.controller.admin.issueheader.vo.IssueHeaderExcelVO;
import com.dofast.module.wms.controller.admin.issueheader.vo.IssueHeaderExportReqVO;
import com.dofast.module.wms.controller.admin.issueline.vo.IssueLineExportReqVO;
import com.dofast.module.wms.convert.issueheader.IssueHeaderConvert;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueHeaderDO;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import com.dofast.module.wms.service.issueheader.IssueHeaderService;
import com.dofast.module.wms.service.issueline.IssueLineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class IssueApiImpl implements IssueApi{

    @Resource
    private IssueHeaderService issueheaderService;

    @Resource
    private IssueLineService issuelineService;

    @Override
    public List<IssueheaderDTO> listIssueHeader(IssueheaderDTO issueheaderDTO) {
        IssueHeaderExportReqVO req = new IssueHeaderExportReqVO();
        BeanUtil.copyProperties(issueheaderDTO, req);
        List<IssueHeaderDO> issueHeaderList = issueheaderService.getIssueHeaderList(req);
        List<IssueheaderDTO> finList = new ArrayList<>();
        for (IssueHeaderDO issueHeaderDO : issueHeaderList) {
            IssueheaderDTO addDTO = new IssueheaderDTO();
            BeanUtil.copyProperties(issueHeaderDO, addDTO);
            finList.add(addDTO);
        }
        return finList;
    }

    @Override
    public List<IssueLineDTO> listIssueLine(IssueLineDTO issueLineDTO) {
        IssueLineExportReqVO req = new IssueLineExportReqVO();
        BeanUtil.copyProperties(issueLineDTO, req);
        List<IssueLineDO> issueLineList = issuelineService.getIssueLineList(req);
        List<IssueLineDTO> finList = new ArrayList<>();
        for (IssueLineDO issueLineDO : issueLineList) {
            IssueLineDTO addDTO = new IssueLineDTO();
            BeanUtil.copyProperties(issueLineDO, addDTO);
            finList.add(addDTO);
        }
        return finList;
    }


}
