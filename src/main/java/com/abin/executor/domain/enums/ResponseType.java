package com.abin.executor.domain.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseType {

    SUCCESS(200, "ok"),//
    BAD_REQUEST(400, "bad request"),//
    NEED_LOGIN(401, "用户未登陆"),
    NEED_LOGIN_TOAST(402, "已在其他设备登录"),//
    FORBIDDEN(403, "Forbidden"),//
    NOT_FOUND(404, "not found"),
    NOT_ROLE(410, "当前用户无权限"),
    LOGIN_ERROR(411, "用户名不存在或密码错误"),
    PARAM_ERROR(412, "请求参数错误"),
    PASSWORD_NOT_MATCH(413, "两次密码不一致"),
    VERIFICATION_CODE_ERROR(414, "验证码错误"),
    EMAIL_ALREADY_REGISTERED(415, "该邮箱已经注册"),

    //  题目相关
    PROBLEM_NOT_EXIST(420, "题目不存在"),
    JUDGE_PENDING(421, "评测中，请稍后查看结果"),

    GET_DEVICE_ID_FAILED(2001, "获取设备id失败"),
    USER_NOT_EXIST(2004, "用户不存在"),
    SYSTEM_ERROR(2005, "服务器暂时不可用,请稍后重试"),
    SEND_EMAIL_ERROR(2010, "邮件发送失败"),
    MAIL_TEMPLATE_ERROR(2011, "加载邮件模版失败"),
    USER_FEED_LIMIT(2012, "用户今日发表feed超过上限"),
    NOT_EXITS_FEED(2013, "feed不存在"),
    UID_ABSENT(2014, "获取用户id失败"),


    USER_PHONE_LOGIN_FAILED(2022, "账号或者密码不对\norz"),
    USER_WEIBO_REQUEST_ILLEGAL(2023, "微博用户请求非法"),
    USER_INFO_NOT_EXITS(2024, "用户信息不存在"),
    WECHAT_USER_REQUEST_ILLEGAL(2025, "微信用户请求非法"),
    WECHAT_USER_NOT_EXITS(2026, "微信用户不存在"),
    GRAPHCODE_INVALID(2027, "验证码不正确~"),
    GRAPHCODE_VERIFY_TIMEOUT(2028, "验证码无效或者过期咯！\n(o_o)"),
    GRAPHCODE_VERIFY_MISMATCH(2029, "验证码无效或者过期咯！\n(o_o)"),
    QQ_USER_REQUEST_ILLEGAL(2030, "qq用户请求非法"),
    QQ_USER_NOT_EXITS(2031, "qq用户不存在"),
    SMSCODE_NOT_VERIFYED(2032, "未通过验证码验证"),
    PHONE_ALREADY_REGISTERED(2033, "该手机号码的账号已经存在了哟～\n(´･Д･)」"),
    NICKNAME_IVALID(2034, "昵称无效，必须长度在3~50位"),
    NICKNAME_ALREADY_USED(2035, "这个昵称已经有人用过咯～\n>_<"),
    SMSCODE_IVALID(2036, "验证码不正确～"),
    SMSCODE_TOO_FREQUENCY(2037, "哎呀~你发送的频率太高了~"),
    SMSCODE_SEND_FAILURE(2038, "发送短信验证码失败，待会再试试吧～\n+_+"),
    SMSCODE_VERIFY_TIMEOUT(2039, "验证码无效或者过期咯！\n(o_o)"),
    SMSCODE_VERIFY_MISMATCH(2040, "验证码无效或者过期咯！\n(o_o)"),
    PHONE_NOT_REGISTERD(2041, "你的手机号还没注册啦！\nˊ_>ˋ"),
    PHONE_INVALID(2042, "填错了吧？这是无效的手机号！\n(´･_･`)"),
    PASSWORD_INVALID(2043, "密码无效,长度必须为8~30位 \n"),
    LOGIN_FAILED_TOO_FREQUENT(2044, "由于输入错误密码次数已达上限，您的账户将被锁定1小时，请稍候再试 \n"),
    LOGIN_FAILED_REACH_MAX_TIME(2045, "输入密码错误已达10次，系统将锁定该账户1小时 \n"),
    LOGIN_FAILED_REACH_REMIND_TIME(2046, "再输入3次错误密码，该账户将被锁定1小时 \n"),
    GRAPH_MISMATCH_TOO_FREQUENT(2047, "由于输入错误验证码次数已达上限，您的账户将被锁定半小时，请稍候再试 \n"),
    GRAPH_MISMATCH_REACH_MAX_TIME(2048, "输入验证码错误已达10次，系统将锁定该账户半小时 \n"),
    GRAPH_MISMATCH_REACH_REMIND_TIME(2049, "再输入3次错误验证码，该账户将被锁定半小时 \n"),

    EVEN_RELATION_STATUS(2101, "已经是要改变的状态"),
    NO_ALLOW_FOLLOWING_SELF(2102, "自己不能关注自己"),
    NO_FOLLOWING_RELATION_EXIST(2103, "关注关系不存在"),
    DAY_LIMIT_SMSCODE(2104, "超过每天发送验证码限制"),
    MIN_LIMIT_SMSCODE(2105, "一分钟内只能获取一次，请稍后再试"),


    //comment相关
    COMMENT_NOT_EXIST(2501, "评论不存在噢"),
    HAS_ACCUSED_COMMENT(2503, "您已举报过该评论"),
    COMMENT_CONTENT_LONG(2504, "评论内容过长"),
    COMMENT_CONTENT_SHOURT(2505, "评论内容过短"),
    WEB_HAS_ACCUSED_COMMENT(700, "您已举报过该评论"),


    //后台app设置相关
    CONFIG_ALREADY_EXISTS(3301, "设置已经存在!"),
    LOCATION_DUPLICATED(3302, "设置的地区之间存在重叠，请检查!"),

    //后台banner相关
    POSITION_DUPLICATE(3401, "Banner插入位置重复，请检查"),

    //oauth相关
    CODE_TOKEN_INVALID(4001, "codeToken过期"),
    ACESS_TOKEN_INVALID(4002, "acessToken过期"),
    INVALID_SIGN(4003, "sign非法"),
    INVALID_NAME(4004, "姓名非法"),
    INVALID_CER_ID(4005, "证件号非法"),

    //message相关
    MESSAGE_ID_INVALID(5001, "消息id为空"),
    MESSAGE_PARAM_INVALID(5002, "消息参数非法"),
    MESSAGE_USER_INVALID(5003, "消息用户非法"),

    //review相关
    REVIEW_ADD_FREQUENCY(5500, "请不要操作太频繁"),
    REVIEW_NOT_EXIST(5501, "漫评不存在"),
    REVIEW_TOO_SHORT(5502, "至少输入%s字才能投稿哦~"),
    REVIEW_TOO_LONG(5503, "最多支持输入%s字,请修改后再投稿"),
    REVIEW_DEL_FORBIDEN(5504, "你不是漫评的作者哦"),

    //点赞举报相关
    ACCUSE_CONTENT_EMPTY(6001, "举报理由不能为空哦!"),
    REPEATED_LIKE(6002, "重复点赞"),
    REPEATED_DISLIKE(6003, "重复取消点赞"),

    NOT_OPERATE_PRIVILEGE(9007, "您的账号出现异常，操作失败"),
    POST_LENGTH_NOT_ENOUGH(9008, "帖子内容太少了"),
    REPLY_NOT_EXIST(9009, "回复不存在"),
    OWNER_NOT_ALLOW_COMMENT(9010, "自己不能评论"),
    POST_NOT_EXIST(9011, "帖子不存在"),
;

    private final int code;

    private final String message;

    private static final Map<Integer, ResponseType> cache;

    static {
        cache = Arrays.stream(ResponseType.values()).collect(Collectors.toMap(ResponseType::getCode, Function.identity()));
    }

    public static ResponseType of(int code) {
        return cache.getOrDefault(code, ResponseType.SYSTEM_ERROR);
    }
}
