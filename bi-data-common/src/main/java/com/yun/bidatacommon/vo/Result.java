package com.yun.bidatacommon.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yun
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    private boolean success = true;

    /**
     * 返回处理消息
     */
    private String message = "";

    /**
     * 返回代码
     */
    private Integer code = 200;
    /**
     * 返回数据对象 data
     */
    private T result;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    public Result() {
    }

    /**
     * 兼容VUE3版token失效不跳转登录页面
     *
     * @param code
     * @param message
     */
    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result<T> SUCCESS(String message) {
        this.message = message;
        this.code = ResultEnum.SUCCESS.respCode;
        this.success = true;
        return this;
    }


    public static <T> Result<T> OK() {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(ResultEnum.SUCCESS.respCode);
        return r;
    }

    public static <T> Result<T> OK(String msg) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(ResultEnum.SUCCESS.respCode);
        r.setMessage(msg);
        r.setResult((T) msg);
        return r;
    }

    public static <T> Result<T> OK(T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(ResultEnum.SUCCESS.respCode);
        r.setResult(data);
        return r;
    }

    public static <T> Result<T> OK(String msg, T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(ResultEnum.SUCCESS.respCode);
        r.setMessage(msg);
        r.setResult(data);
        return r;
    }

    public static <T> Result<T> ERROR(String msg, T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(false);
        r.setCode(ResultEnum.ERROR.respCode);
        r.setMessage(msg);
        r.setResult(data);
        return r;
    }

    public static <T> Result<T> ERROR(ResultEnum resultEnum, T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(false);
        r.setCode(resultEnum.respCode);
        r.setMessage(resultEnum.respDesc);
        r.setResult(data);
        return r;
    }

    public static <T> Result<T> ERROR(ResultEnum resultEnum) {
        Result<T> r = new Result<T>();
        r.setSuccess(false);
        r.setCode(resultEnum.respCode);
        r.setMessage(resultEnum.respDesc);
        r.setResult(null);
        return r;
    }

    public static Result<Object> ERROR(String msg) {
        return ERROR(ResultEnum.ERROR.respCode, msg);
    }

    public static Result<Object> ERROR(int code, String msg) {
        Result<Object> r = new Result<Object>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    public enum ResultEnum {
        //定义返回值内容
        // 0-100000 公共
        // 100000  data
        // 200000  apiManage
        // 300000  dataStorage
        // 400000  fileManage
        SUCCESS(200, "处理成功"),
        ERROR(500, "处理失败"),
        ILLEGAL_REQUEST(401, "非法请求"),
        ROLE_TOKEN_DOES_NOT_EXIST(100002, "角色获取Token失败"),
        NO_SUCH_DATA_PROCESSING_TYPE(100001, "无此数据处理类型!"),
        DATA_FORMAT_ERROR(100003, "数据格式错误!"),
        FORMAT_CONVERSION_ERROR(100004, "数据转换格式错误!"),
        DATA_FUSION_ERROR(100005, "数据融合失败!"),
        ITEM_DOES_NOT_EXIST(100006, "项目不存在!"),
        METRIC_TYPE_DOES_NOT_EXIST(100006, "指标类型不存在!"),
        DATA_SOURCE_DOES_NOT_EXIST(300001, "数据源不存在!"),
        DATA_SOURCE_TYPE_DOES_NOT_EXIST(300002, "数据源类型不存在!"),
        FAILED_TO_CREATE_DATA_SOURCE_LINK(300003, "创建数据源链接失败!"),
        PROHIBIT_SENSITIVE_OPERATIONS(300004, "禁止敏感操作!"),
        SCRIPT_DOES_NOT_EXIST(300005, "SQL 脚本不存在!"),
        STORAGE_TABLE_DOES_NOT_EXIST(300006, "SQL 存储表不存在!"),
        DATA_NOT_OBTAINED(300007, "未获取到数据!"),
        FAILED_TO_CLEAR_TABLE_DATA(300008, "清除表数据失败!"),
        FAILED_TO_CREATE_TABLE(300009, "创建表失败!"),
        THE_FILE_ENTRY_TABLE_ALREADY_EXISTS(300010, "文件入库表已存在!"),
        DROP_TABLE_ERROR(300011, "删除表失败!"),
        FAILED_TO_SAVE_THE_FILE(400000, "保存文件失败!"),
        ERROR_GETTING_FILE_TYPE(400001, "获取文件类型出错!"),
        JSON_TYPE_ERROR(400002, "JSON类型有误 解析失败!"),
        FILE_DOES_NOT_EXIST(400002, "文件不存在!"),
        UNKNOWN_EXCEPTION(50000, "未知异常!"),
        INTERFACE_DOES_NOT_EXIST(50001, "接口不存在!");
        private Integer respCode;
        private String respDesc;

        ResultEnum(Integer respCode, String respDesc) {
            this.respCode = respCode;
            this.respDesc = respDesc;
        }

        public Integer getRespCode() {
            return respCode;
        }

        public String getRespDesc() {
            return respDesc;
        }
    }

}
