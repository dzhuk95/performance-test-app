package com.performance.users.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "QueryRequestDto", description = "Model for sql query request")
public class QueryRequestDto implements Serializable {
    static final long serialVersionUID = -407408426305710486L;

    @NotBlank
    @ApiModelProperty(value = "Sql query for execution in particular db", required = true)
    String query;
}
