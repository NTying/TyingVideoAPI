package com.tying.constants;

/**
 * @author Tying
 * @version 1.0
 */
public enum AppHttpCodeEnum {

    // 成功
    SUCCESS(200, "操作成功"),
    // 未登录
    NEED_LOGIN(401, "需要登录后操作"),
    // 无操作权限
    NO_OPERATION_AUTH(403, "无操作权限"),
    // 用户名或者密码错误
    LOGIN_ERROR(405, "用户名或者密码错误"),
    // 用户名不能为空
    REQUIRED_USERNAME(406, "用户名不能为空"),
    // 昵称不能为空
    REQUIRED_NICKNAME(407, "昵称不能为空"),
    // 密码不能为空
    REQUIRED_PASSWORD(408, "密码不能为空"),
    // 邮箱不能为空
    REQUIRED_EMAIL(409, "邮箱不能为空"),

    // 未知错误
    SYSTEM_ERROR(500, "出现错误"),
    // 用户名已存在
    USERNAME_EXIST(501, "用户名已存在"),
    // 手机号已被注册
    PHONE_NUMBER_EXIST(502, "手机号已注册"),
    // 邮箱已被注册
    EMAIL_EXIST(503, "邮箱已注册"),

    // 文件类型错误
    FILE_TYPE_ERROR(601, "文件类型错误"),
    // OSS 上传文件失败
    OSS_UPLOAD_ERROR(602, "OSS上传文件错误");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
