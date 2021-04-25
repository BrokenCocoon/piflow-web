package cn.cnic.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cnic.base.util.HttpUtils;
import cn.cnic.base.util.LoggerUtil;
import cn.cnic.base.util.SessionUserUtil;
import cn.cnic.component.process.service.IProcessPathService;
import cn.cnic.component.process.service.IProcessService;
import cn.cnic.component.process.service.IProcessStopService;
import cn.cnic.component.process.vo.DebugDataRequest;
import cn.cnic.third.service.IFlow;

@Controller
@RequestMapping("/process")
public class ProcessCtrl {

    /**
     * Introduce the log, note that all are under the "org.slf4j" package
     */
    Logger logger = LoggerUtil.getLogger();

    @Autowired
    IProcessService processServiceImpl;

    @Autowired
    IFlow flowImpl;

    @Autowired
    IProcessStopService processStopServiceImpl;

    @Autowired
    IProcessPathService processPathServiceImpl;


    /**
     * Query and enter the process list
     *
     * @param page
     * @param limit
     * @param param
     * @return
     */
    @RequestMapping("/processListPage")
    @ResponseBody
    public String processAndProcessGroupListPage(Integer page, Integer limit, String param) {
        String username = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return processServiceImpl.getProcessVoListPage(username, isAdmin, page, limit, param);
    }

    /**
     * Enter the front page of the drawing board
     *
     * @param request
     * @return
     */
    @RequestMapping("/drawingBoardData")
    @ResponseBody
    public String drawingBoardData(HttpServletRequest request) {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        String loadId = request.getParameter("loadId");
        String parentAccessPath = request.getParameter("parentAccessPath");
        return processServiceImpl.drawingBoardData(currentUsername, isAdmin, loadId, parentAccessPath);
    }

    /**
     * Query Process basic information
     *
     * @param request
     * @return
     */
    @RequestMapping("/queryProcessData")
    @ResponseBody
    public String queryProcessData(HttpServletRequest request) {
        String processId = request.getParameter("processId");
        String username = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return processServiceImpl.getProcessVoById(username, isAdmin, processId);
    }

    /**
     * Query ProcessStop basic information
     *
     * @param request
     * @return
     */
    @RequestMapping("/queryProcessStopData")
    @ResponseBody
    public String queryProcessStopData(HttpServletRequest request) {
        String processId = request.getParameter("processId");
        String pageId = request.getParameter("pageId");
        return processStopServiceImpl.getProcessStopVoByPageId(processId, pageId);
    }

    /**
     * Query ProcessPath basic information
     *
     * @param request
     * @return
     */
    @RequestMapping("/queryProcessPathData")
    @ResponseBody
    public String queryProcessPathData(HttpServletRequest request) {
        String processId = request.getParameter("processId");
        String pageId = request.getParameter("pageId");
        return processPathServiceImpl.getProcessPathVoByPageId(processId, pageId);
    }

    /**
     * Start Process
     *
     * @param request
     * @return
     */
    @RequestMapping("/runProcess")
    @ResponseBody
    public String runProcess(HttpServletRequest request) {
        String id = request.getParameter("id");
        String checkpoint = request.getParameter("checkpointStr");
        String runMode = request.getParameter("runMode");
        String username = SessionUserUtil.getCurrentUsername();
        return processServiceImpl.startProcess(username, id, checkpoint, runMode);
    }

    /**
     * Stop Process
     *
     * @param request
     * @return
     */
    @RequestMapping("/stopProcess")
    @ResponseBody
    public String stopProcess(HttpServletRequest request) {
        String processId = request.getParameter("processId");
        String username = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return processServiceImpl.stopProcess(username, isAdmin, processId);
    }

    /**
     * Delete Process
     *
     * @param request
     * @return
     */
    @RequestMapping("/delProcess")
    @ResponseBody
    public String delProcess(HttpServletRequest request) {
        String processID = request.getParameter("processID");
        String username = SessionUserUtil.getCurrentUsername();
        return processServiceImpl.delProcess(username, processID);
    }

    /**
     * Get the address of the log of the flow
     *
     * @param request
     * @return
     */
    @RequestMapping("/getLogUrl")
    @ResponseBody
    public String getLogUrl(HttpServletRequest request) {
        String appId = request.getParameter("appId");
        return processServiceImpl.getLogUrl(appId);
    }

    /**
     * Climb to the log by the address of the flow log
     *
     * @param request
     * @return
     */
    @RequestMapping("/getLog")
    @ResponseBody
    public String getLog(HttpServletRequest request) {
        String rtnMsg = "";
        String urlStr = request.getParameter("url");
        if (StringUtils.isNotBlank(urlStr)) {
            if ("Interface call failed".equals(urlStr)) {
                rtnMsg = "Interface call failed";
            } else {
                rtnMsg = HttpUtils.getHtml(urlStr);
            }
        } else {
            logger.warn("urlStr is null");
        }

        return rtnMsg;
    }

    /**
     * Monitoring query appinfo
     *
     * @param request
     * @return
     */
    @RequestMapping("/getAppInfo")
    @ResponseBody
    public String getAppInfo(HttpServletRequest request) {
        String appid = request.getParameter("appid");
        return processServiceImpl.getAppInfoByAppId(appid);
    }

    /**
     * Monitoring query appinfoList
     *
     * @param request
     * @return
     */
    @RequestMapping("/getAppInfoList")
    @ResponseBody
    public String getAppInfoList(HttpServletRequest request) {
        String[] arrayObj = request.getParameterValues("arrayObj");
        return processServiceImpl.getProgressByAppIds(arrayObj);
    }


    /**
     * Call the interface to return Checkpoint for the user to choose
     *
     * @param request
     * @return
     */
    @RequestMapping("/getCheckpointData")
    @ResponseBody
    public String getCheckpoint(HttpServletRequest request) {
        String pID = request.getParameter("pID");
        String parentProcessId = request.getParameter("parentProcessId");
        return processServiceImpl.getCheckpoints(parentProcessId, pID);
    }

    @RequestMapping("/getDebugData")
    @ResponseBody
    public String getDebugData(HttpServletRequest request) {
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("code", 500);
        String appId = request.getParameter("appId");
        String stopName = request.getParameter("stopName");
        String portName = request.getParameter("portName");
        String startFileName = request.getParameter("startFileName");
        String startLineStr = request.getParameter("startLine");
        int startLine = 0;
        if (StringUtils.isNotBlank(startLineStr)) {
            startLine = Integer.parseInt(startLineStr);
        }
        if ("Default".equals(portName)) {
            portName = portName.toLowerCase();
        }
        return processServiceImpl.getDebugData(new DebugDataRequest(appId, stopName, portName, startFileName, startLine));
    }

    @RequestMapping("/getVisualizationData")
    @ResponseBody
    public String getVisualizationData(HttpServletRequest request) {
        String appId = request.getParameter("appId");
        String stopName = request.getParameter("stopName");
        String visualizationType = request.getParameter("visualizationType");

        return processServiceImpl.getVisualizationData(appId, stopName, visualizationType);
    }

    /**
     * Get the `list'running under `flow'
     *
     * @param request
     * @return
     */
    @RequestMapping("/getRunningProcessListData")
    @ResponseBody
    public String getRunningProcessList(HttpServletRequest request) {
        String flowId = request.getParameter("flowId");
        return processServiceImpl.getRunningProcessVoList(flowId);
    }
}
