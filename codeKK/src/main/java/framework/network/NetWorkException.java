package framework.network;

import framework.utils.UIUtils;

/**
 * by y on 2017/5/16
 */

class NetWorkException extends RuntimeException {

    NetWorkException(int code, String message) {
        UIUtils.toast(code + "---" + message);
    }

}