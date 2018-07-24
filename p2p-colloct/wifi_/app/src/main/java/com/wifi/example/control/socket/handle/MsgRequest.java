package com.wifi.example.control.socket.handle;

/**
 * @author keshuangjie
 * @version 1.0
 *          消息发送请求
 * @date 2014-12-4 下午7:41:14
 * @package com.jimmy.im.client.socket
 */
public class MsgRequest {

    private MsgParam mParam;
    private SendCallback mSendCallBack;

    public MsgRequest(MsgParam param, SendCallback callBack) {
        this.mParam = param;
        this.mSendCallBack = callBack;
    }

    public SendCallback getSendCallBack() {
        return mSendCallBack;
    }

    public void setSendCallBack(SendCallback mSendCallBack) {
        this.mSendCallBack = mSendCallBack;
    }

    public MsgParam getMsgParam() {
        return mParam;
    }

    public void setMsgParam(MsgParam param) {
        this.mParam = param;
    }

    public interface SendCallback {
        void onFinish();

        void onError();
    }

}
