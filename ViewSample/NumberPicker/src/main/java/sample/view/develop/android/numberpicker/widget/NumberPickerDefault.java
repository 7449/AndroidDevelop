package sample.view.develop.android.numberpicker.widget;

/**
 * by y on 2017/3/16
 */

public class NumberPickerDefault {

    // message 的刷新参数，调用者为 mHandler
    public static final int HANDLER_WHAT_REFRESH = 1;
    // message's what argument to respond value changed event, used by mHandler
    public static final int HANDLER_WHAT_LISTENER_VALUE_CHANGED = 2;
    // message 的请求布局参数，调用者为 mHandlerInMainThread
    public static final int HANDLER_WHAT_REQUEST_LAYOUT = 3;
    // 未选中item的字体颜色
    static final int DEFAULT_TEXT_COLOR_NORMAL = 0XFF333333;
    // 选中item的字体颜色
    static final int DEFAULT_TEXT_COLOR_SELECTED = 0XFFF56313;
    // 未选中item的字体大小
    static final int DEFAULT_TEXT_SIZE_NORMAL_SP = 14;
    // 选中item的字体大小
    static final int DEFAULT_TEXT_SIZE_SELECTED_SP = 16;
    // 提示文本字体大小
    static final int DEFAULT_TEXT_SIZE_HINT_SP = 14;
    // 选中item的内容与提示文本的距离
    static final int DEFAULT_MARGIN_START_OF_HINT_DP = 8;
    // 提示文本距离 NumberPickerView 右侧之间的距离
    static final int DEFAULT_MARGIN_END_OF_HINT_DP = 8;
    // 分割线颜色
    static final int DEFAULT_DIVIDER_COLOR = 0XFFF56313;
    // 分割线高度
    static final int DEFAULT_DIVIDER_HEIGHT = 2;
    // 分割线在 NumberPickerView 的左右margin
    static final int DEFAULT_DIVIDER_MARGIN_HORIZONTAL = 0;
    // 显示几个item，默认为显示三个，第二个为选中状态
    static final int DEFAULT_SHOW_COUNT = 3;
    // 默认在 horizontal 模式下的padding,左右都是 5dp ，仅适用于 wrap_content
    static final int DEFAULT_ITEM_PADDING_DP_H = 5;
    // 默认在 vertical 模式下的padding,左右都是 2dp ，仅适用于 wrap_content
    static final int DEFAULT_ITEM_PADDING_DP_V = 2;
    // 间隔时间为滚动一个item的高度的距离，毫秒值
    static final int HANDLER_INTERVAL_REFRESH = 32;

    // 毫秒为单位，滚动item距离的默认持续时间
    static final int DEFAULT_INTERVAL_REVISE_DURATION = 300;

    // 从一个值滚动到另一个值时的最大和最小持续时间
    static final int DEFAULT_MIN_SCROLL_BY_INDEX_DURATION = DEFAULT_INTERVAL_REVISE_DURATION;
    static final int DEFAULT_MAX_SCROLL_BY_INDEX_DURATION = DEFAULT_INTERVAL_REVISE_DURATION * 2;


    static final String TEXT_ELLIPSIZE_START = "start";
    static final String TEXT_ELLIPSIZE_MIDDLE = "middle";
    static final String TEXT_ELLIPSIZE_END = "end";

    static final boolean DEFAULT_SHOW_DIVIDER = true;
    static final boolean DEFAULT_WRAP_SELECTOR_WHEEL = true;
    static final boolean DEFAULT_CURRENT_ITEM_INDEX_EFFECT = false;
    static final boolean DEFAULT_RESPOND_CHANGE_ON_DETACH = false;
    static final boolean DEFAULT_RESPOND_CHANGE_IN_MAIN_THREAD = true;
}
