package com.performance.users.dto;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueryRequestDto implements Serializable {
    static final long serialVersionUID = -407408426305710486L;

    String query;
}
