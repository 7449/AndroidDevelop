package com.compiler;

/**
 * @author y
 */
public class BindConst {

    public static final String PACKAGE_CONTENT = "android.content";
    public static final String PACKAGE_RES = "android.content.res";
    public static final String PACKAGE_VIEW = "android.view.View";

    public static final int TYPE_LONG_CLICK = -2;
    public static final int TYPE_CLICK = -1;
    public static final int TYPE_VIEW = 0;
    public static final int TYPE_STRING = 1;
    public static final int TYPE_COLOR = 2;
    public static final int TYPE_DIMEN = 3;
    public static final int TYPE_DRAWABLE = 4;
    public static final int TYPE_STRING_ARRAY = 6;
    public static final int TYPE_INT_ARRAY = 5;

    public static final String CLASS_SUFFIX = "_Bind";
    public static final String TARGET = "target";
    public static final String M_TARGET = "mTarget";
    public static final String VIEW = "view";
    public static final String RES = "res";

    public static final String METHOD_NAME = "bindView";
    public static final String STATEMENT_TARGET = "mTarget = target";
    public static final String STATEMENT_VIEW = "$N = view.findViewById($L)";
    public static final String STATEMENT_CONTEXT = "$T context = $N.getContext()";
    public static final String STATEMENT_RESOURCES = "$T res = context.getResources()";

    public static final String STATEMENT_STRING = "target.$N = $N.getString($L)";
    public static final String STATEMENT_TARGET_VIEW = "target.$N = $N.findViewById($L)";
    public static final String STATEMENT_COLOR = "target.$N = $N.getColor($L)";
    public static final String STATEMENT_DIMENSION = "target.$N = $N.getDimension($L)";
    public static final String STATEMENT_DRAWABLE = "target.$N = $N.getDrawable($L)";
    public static final String STATEMENT_STRING_ARRAY = "target.$N = $N.getStringArray($L)";
    public static final String STATEMENT_INT_ARRAY = "target.$N = $N.getIntArray($L)";
    public static final String STATEMENT_CLICK = "$N.setOnClickListener(new View.OnClickListener(){@Override public void onClick(View v){mTarget.$N(v);}})";
    public static final String STATEMENT_VIEW_CLICK = "target.$N.setOnClickListener(new View.OnClickListener(){@Override public void onClick(View v){mTarget.$N(v);}})";
    public static final String STATEMENT_LONG_CLICK = "$N.setOnLongClickListener(new View.OnLongClickListener(){@Override public boolean onLongClick(View v){return mTarget.$N(v);}})";
    public static final String STATEMENT_VIEW_LONG_CLICK = "target.$N.setOnLongClickListener(new View.OnLongClickListener(){@Override public boolean onLongClick(View v){return mTarget.$N(v);}})";

    public static class UnBindConst {
        public static final String METHOD_NAME = "unBind";
        public static final String STATEMENT_RETURN = "if (mTarget == null) return";
        public static final String STATEMENT_VIEW_NULL = "mTarget.$N = null";
        public static final String STATEMENT_CLICK_NULL = "$N.setOnClickListener(null)";
        public static final String STATEMENT_VIEW_CLICK_NULL = "mTarget.$N.setOnClickListener(null)";
        public static final String STATEMENT_LONG_CLICK_NULL = "$N.setOnLongClickListener(null)";
        public static final String STATEMENT_VIEW_LONG_CLICK_NULL = "mTarget.$N.setOnLongClickListener(null)";
        public static final String STATEMENT_TARGET_NULL = "mTarget = null";
        public static final String STATEMENT_NULL = "$N = null";
    }
}
