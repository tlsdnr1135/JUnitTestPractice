package com.sulbin.junittest.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CMResDto<T>{

    private Integer code;
    private String msg;
    private T body;

    @Builder
    public CMResDto(Integer code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }
}
