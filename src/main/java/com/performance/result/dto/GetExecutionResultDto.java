package com.performance.result.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetExecutionResultDto implements Serializable {
    static final long serialVersionUID = -8037843468314978769L;

    @NotBlank
    String query;

    DatabaseType databaseType;
}
