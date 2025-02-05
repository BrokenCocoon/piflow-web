package cn.cnic.component.process.entity;

import cn.cnic.base.BaseModelUUIDNoCorpAgentId;
import cn.cnic.common.Eunm.PortType;
import cn.cnic.common.Eunm.StopState;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ProcessStop extends BaseModelUUIDNoCorpAgentId {

    private static final long serialVersionUID = 1L;

    private Process process;
    private String name;
    private String bundel;
    private String groups;
    private String owner;
    private String description;
    private String inports;
    private PortType inPortType;
    private String outports;
    private PortType outPortType;
    private StopState state = StopState.INIT;
    private Date startTime;
    private Date endTime;
    private String pageId;
    private List<ProcessStopProperty> processStopPropertyList = new ArrayList<ProcessStopProperty>();
    private List<ProcessStopCustomizedProperty> processStopCustomizedPropertyList = new ArrayList<>();
    private Boolean isDataSource = false;
}
