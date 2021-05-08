package cn.cnic.controller.requestVo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(description = "testDataSchema")
public class RequestTestDataSchemaVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "testDataSchema id")
    private String id;
    
    @ApiModelProperty(value = "testDataSchema fieldName", required = true)
    private String fieldName;
    
    @ApiModelProperty(value = "testDataSchema fieldType", required = true)
    private String fieldType;
    
    @ApiModelProperty(value = "testDataSchema fieldDescription")
    private String fieldDescription;
    
    @ApiModelProperty(value = "testDataSchema fieldSoft", required = true, example = "0")
    private int fieldSoft;


}
