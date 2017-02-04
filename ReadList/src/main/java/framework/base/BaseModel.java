package framework.base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * by y on 2016/11/10
 */

public class BaseModel<T> {

    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private String msg;

    public List<T> getNewsList() {
        return newsList;
    }

    @SerializedName("newslist")
    private List<T> newsList;

    public void setNewsList(List<T> newsList) {
        this.newsList = newsList;
    }


}
