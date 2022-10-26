package com.yun.bidata.enums;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.yun.bidata.dto.RequestDto;

import java.util.HashMap;

/**
 * @author Yun
 */
@SuppressWarnings({"AlibabaEnumConstantsMustHaveComment", "unchecked"})
public enum RequestEnum {

    GET {
        @Override
        public String conversion(RequestDto dto) {
            return HttpRequest.get(dto.getUrl())
                    .headerMap(JSONUtil.toBean(dto.getHeaders(), HashMap.class), true)
                    .body(dto.getBody())
                    .timeout(3000)
                    .execute()
                    .body();
        }
    }, POST {
        @Override
        public String conversion(RequestDto dto) {
            return HttpRequest.post(dto.getUrl())
                    .headerMap(JSONUtil.toBean(dto.getHeaders(), HashMap.class), true)
                    .body(dto.getBody())
                    .timeout(3000)
                    .execute()
                    .body();
        }
    }, PUT {
        @Override
        public String conversion(RequestDto dto) {
            return HttpRequest.put(dto.getUrl())
                    .headerMap(JSONUtil.toBean(dto.getHeaders(), HashMap.class), true)
                    .body(dto.getBody())
                    .timeout(3000)
                    .execute()
                    .body();
        }
    }, DELETE {
        @Override
        public String conversion(RequestDto dto) {
            return HttpRequest.delete(dto.getUrl())
                    .headerMap(JSONUtil.toBean(dto.getHeaders(), HashMap.class), true)
                    .body(dto.getBody())
                    .timeout(3000)
                    .execute()
                    .body();
        }
    };

    /**
     * 转换工厂
     *
     * @param dto 数据
     * @return 结果
     */
    public abstract String conversion(RequestDto dto);
}
