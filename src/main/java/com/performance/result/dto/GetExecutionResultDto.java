package com.performance.result.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "GetExecutionResultDto", description = "Model for retrieving sql query execution requests")
public class GetExecutionResultDto implements Serializable {
    static final long serialVersionUID = -8037843468314978769L;

    @NotBlank
    @ApiModelProperty(value = "Requested sql query", required = true)
    String query;

    @ApiModelProperty(value = "Database type", allowableValues = "ACTIVE_USER, INACTIVE_USER, LEGACY_USER")
    DatabaseType databaseType;
}
