package com.yun.bidata.enums;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.yomahub.tlog.hutoolhttp.TLogHutoolhttpInterceptor;
import com.yun.bidata.dto.RequestDto;
import org.springframework.beans.factory.annotation.Value;

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
                    .addInterceptor(tLogHutoolhttpInterceptor)
                    .headerMap(JSONUtil.toBean(dto.getHeaders(), HashMap.class), true)
                    .body(dto.getBody())
                    .timeout(timeOut)
                    .execute()
                    .body();
        }
    }, POST {
        @Override
        public String conversion(RequestDto dto) {
            return HttpRequest.post(dto.getUrl())
                    .addInterceptor(tLogHutoolhttpInterceptor)
                    .headerMap(JSONUtil.toBean(dto.getHeaders(), HashMap.class), true)
                    .body(dto.getBody())
                    .timeout(timeOut)
                    .execute()
                    .body();
        }
    }, PUT {
        @Override
        public String conversion(RequestDto dto) {
            return HttpRequest.put(dto.getUrl())
                    .addInterceptor(tLogHutoolhttpInterceptor)
                    .headerMap(JSONUtil.toBean(dto.getHeaders(), HashMap.class), true)
                    .body(dto.getBody())
                    .timeout(timeOut)
                    .execute()
                    .body();
        }
    }, DELETE {
        @Override
        public String conversion(RequestDto dto) {
            return HttpRequest.delete(dto.getUrl())
                    .addInterceptor(tLogHutoolhttpInterceptor)
                    .headerMap(JSONUtil.toBean(dto.getHeaders(), HashMap.class), true)
                    .body(dto.getBody())
                    .timeout(timeOut)
                    .execute()
                    .body();
        }
    };

    /**
     * 超时时间
     */
    private static Integer timeOut;

    @Value("${smart.data.timeOut:3000}")
    public void setTimeOut(Integer timeOut) {
        RequestEnum.timeOut = timeOut;
    }

    /**
     * tlog 拦截器
     */
    private static TLogHutoolhttpInterceptor tLogHutoolhttpInterceptor = new TLogHutoolhttpInterceptor();

    /**
     * 转换工厂
     *
     * @param dto 数据
     * @return 结果
     */
    public abstract String conversion(RequestDto dto);
}
