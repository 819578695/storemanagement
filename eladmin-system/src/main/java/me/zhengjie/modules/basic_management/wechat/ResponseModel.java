package me.zhengjie.modules.basic_management.wechat;

/**
 * 共用返回参数
 *
 * @author zhixiang.meng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ResponseModel implements java.io.Serializable {

    /**
     */
    private static final long serialVersionUID = 1L;

    public static final String SUCCESS_CODE = "1";

    public static final String ERROR_CODE = "-1";
    /**
     * 返回code
     * 0000：表示成功
     */
    private String resultCode;
    /**
     * 返回消息，成功为null；失败有具体消息
     */
    private String resultMsg;
    /**
     * 返回的object类
     */
    private Object data;

    public ResponseModel() {}

    /**
     * @param resultCode
     * @param resultMsg
     * @param data
     */
    public ResponseModel(String resultCode, String resultMsg, Object data) {
        super();
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.data = data;
    }


    /**
     * @return the resultCode
     */
    public String getResultCode() {
        return resultCode;
    }
    /**
     * @param resultCode the resultCode to set
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    /**
     * @return the resultMsg
     */
    public String getResultMsg() {
        return resultMsg;
    }
    /**
     * @param resultMsg the resultMsg to set
     */
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
    /**
     * @return the date
     */
    public Object getData() {
        return data;
    }
    /**
     * @param data the date to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    public static ResponseModel error(String msg) {
        return error(msg, null);
    }

    public static ResponseModel error(String msg, Object obj) {
        return new ResponseModel(ERROR_CODE, msg, obj);
    }

    public static ResponseModel success(Object data) {
        return new ResponseModel(SUCCESS_CODE, "", data);
    }

    public static ResponseModel ok() {
        return new ResponseModel(SUCCESS_CODE, "success","");
    }

}
