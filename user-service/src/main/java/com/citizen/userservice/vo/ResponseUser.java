package com.citizen.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

@Data
// TODO: 2021-09-01 @JsonInclude. Annotation 속성에 따라 필드값을 제외하는 데 사용 됩니다.
@JsonInclude(Include.NON_NULL)
public class ResponseUser {

    private String email;
    private String name;
    private String userId;

    private List<ResponseOrder> orders;

}
