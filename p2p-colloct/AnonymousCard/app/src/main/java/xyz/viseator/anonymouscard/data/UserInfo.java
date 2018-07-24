package xyz.viseator.anonymouscard.data;

import java.io.Serializable;

/**
 * Created by viseator on 2016/12/23.
 * Wudi
 * viseator@gmail.com
 */

public class UserInfo implements Serializable {
    final long serialVersionUID = 66666666L;

    private int candys;

    public UserInfo() {
        candys = 20;
    }

    public int getCandys() {
        return candys;
    }

    public void setCandys(int candys) {
        this.candys = candys;
    }
}
