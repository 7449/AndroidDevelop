# 1.简单描述一下自定义View的测量（Measure）过程
## 1.1基本知识
### 1.1.1作用：测量View的宽 / 高

```
在某些情况下，需要多次测量（measure）才能确定View最终的宽/高；
该情况下，measure过程后得到的宽 / 高可能不准确；
此处建议：在layout过程中onLayout()去获取最终的宽 / 高
```
了解measure过程前，需要先了解传递尺寸（宽 / 高测量值）的2个类：

ViewGroup.LayoutParams类（）
MeasureSpecs 类（父视图对子视图的测量要求）

### 1.1.2 ViewGroup.LayoutParams

LayoutParams继承于Android.View.ViewGroup.LayoutParams相当于一个Layout的信息包，它封装了Layout的位置、高、宽等信息。假设在屏幕上一块区域是由一个Layout占领的，如果将一个View添加到一个Layout中，最好告诉Layout用户期望的布局方式，也就是将一个认可的layoutParams传递进去。

通俗地讲(这里借鉴了网上的一种说法)，LayoutParams类是用于child view(子视图)向parent view(父视图)传达自己的意愿的一个东西(孩子想变成什么样向其父亲说明)。

具体使用

```
通过以下参数指定
参数	解释
具体值	dp / px
fill_parent	强制性使子视图的大小扩展至与父视图大小相等（不含 padding )
match_parent	与fill_parent相同，用于Android 2.3 & 之后版本
wrap_content	自适应大小，强制性地使视图扩展以便显示其全部内容(含 padding )
```
### 1.1.3MeasureSpec

![image](https://upload-images.jianshu.io/upload_images/944365-0cf0a1ffd083cad1.png?imageMogr2/auto-orient/)

测量规格（MeasureSpec） = 测量模式（mode） + 测量大小（size）

![image](https://upload-images.jianshu.io/upload_images/944365-7d0f873cee3912bb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/380)

其中，测量模式（Mode）的类型有3种：UNSPECIFIED、EXACTLY 和
AT_MOST。

![image](https://upload-images.jianshu.io/upload_images/944365-e631b96ea1906e34.png?imageMogr2/auto-orient/)

MeasureSpec 被封装在View类中的一个内部类里：MeasureSpec类

MeasureSpec类 用1个变量封装了2个数据（size，mode）：通过使用二进制，将测量模式（mode） & 测量大小(size）打包成一个int值来，并提供了打包 & 解包的方法

* MeasureSpec如何测量得到

结论：子View的MeasureSpec值根据子View的布局参数（LayoutParams）和父容器的MeasureSpec值计算得来的，具体计算逻辑封装在getChildMeasureSpec()里
![image](https://upload-images.jianshu.io/upload_images/944365-d059b1afdeae0256.png?imageMogr2/auto-orient/)

源码分析：

```
/**
  * 源码分析：getChildMeasureSpec（）
  * 作用：根据父视图的MeasureSpec & 布局参数LayoutParams，计算单个子View的MeasureSpec
  * 注：子view的大小由父view的MeasureSpec值 和 子view的LayoutParams属性 共同决定
  **/

    public static int getChildMeasureSpec(int spec, int padding, int childDimension) {  

         //参数说明
         * @param spec 父view的详细测量值(MeasureSpec) 
         * @param padding view当前尺寸的的内边距和外边距(padding,margin) 
         * @param childDimension 子视图的布局参数（宽/高）

            //父view的测量模式
            int specMode = MeasureSpec.getMode(spec);     

            //父view的大小
            int specSize = MeasureSpec.getSize(spec);     
          
            //通过父view计算出的子view = 父大小-边距（父要求的大小，但子view不一定用这个值）   
            int size = Math.max(0, specSize - padding);  
          
            //子view想要的实际大小和模式（需要计算）  
            int resultSize = 0;  
            int resultMode = 0;  
          
            //通过父view的MeasureSpec和子view的LayoutParams确定子view的大小  


            // 当父view的模式为EXACITY时，父view强加给子view确切的值
           //一般是父view设置为match_parent或者固定值的ViewGroup 
            switch (specMode) {  
            case MeasureSpec.EXACTLY:  
                // 当子view的LayoutParams>0，即有确切的值  
                if (childDimension >= 0) {  
                    //子view大小为子自身所赋的值，模式大小为EXACTLY  
                    resultSize = childDimension;  
                    resultMode = MeasureSpec.EXACTLY;  

                // 当子view的LayoutParams为MATCH_PARENT时(-1)  
                } else if (childDimension == LayoutParams.MATCH_PARENT) {  
                    //子view大小为父view大小，模式为EXACTLY  
                    resultSize = size;  
                    resultMode = MeasureSpec.EXACTLY;  

                // 当子view的LayoutParams为WRAP_CONTENT时(-2)      
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {  
                    //子view决定自己的大小，但最大不能超过父view，模式为AT_MOST  
                    resultSize = size;  
                    resultMode = MeasureSpec.AT_MOST;  
                }  
                break;  
          
            // 当父view的模式为AT_MOST时，父view强加给子view一个最大的值。（一般是父view设置为wrap_content）  
            case MeasureSpec.AT_MOST:  
                // 道理同上  
                if (childDimension >= 0) {  
                    resultSize = childDimension;  
                    resultMode = MeasureSpec.EXACTLY;  
                } else if (childDimension == LayoutParams.MATCH_PARENT) {  
                    resultSize = size;  
                    resultMode = MeasureSpec.AT_MOST;  
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {  
                    resultSize = size;  
                    resultMode = MeasureSpec.AT_MOST;  
                }  
                break;  
          
            // 当父view的模式为UNSPECIFIED时，父容器不对view有任何限制，要多大给多大
            // 多见于ListView、GridView  
            case MeasureSpec.UNSPECIFIED:  
                if (childDimension >= 0) {  
                    // 子view大小为子自身所赋的值  
                    resultSize = childDimension;  
                    resultMode = MeasureSpec.EXACTLY;  
                } else if (childDimension == LayoutParams.MATCH_PARENT) {  
                    // 因为父view为UNSPECIFIED，所以MATCH_PARENT的话子类大小为0  
                    resultSize = 0;  
                    resultMode = MeasureSpec.UNSPECIFIED;  
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {  
                    // 因为父view为UNSPECIFIED，所以WRAP_CONTENT的话子类大小为0  
                    resultSize = 0;  
                    resultMode = MeasureSpec.UNSPECIFIED;  
                }  
                break;  
            }  
            return MeasureSpec.makeMeasureSpec(resultSize, resultMode);  
        }


```
![image](https://upload-images.jianshu.io/upload_images/944365-6088d2d291bbae09.png?imageMogr2/auto-orient/)

## 1.2 measure过程详解
![image](https://upload-images.jianshu.io/upload_images/944365-556bf094df91b9de.png?imageMogr2/auto-orient/)

### 1.2.1单一View的measure过程
在无现成的控件View满足需求、需自己实现时，则使用自定义单一View

![image](https://upload-images.jianshu.io/upload_images/944365-6fd614936d045071.png?imageMogr2/auto-orient/)

* 入口 = measure（）


```
/**
  * 源码分析：measure（）
  * 定义：Measure过程的入口；属于View.java类 & final类型，即子类不能重写此方法
  * 作用：基本测量逻辑的判断
  **/ 

    public final void measure(int widthMeasureSpec, int heightMeasureSpec) {

        // 参数说明：View的宽 / 高测量规格

        ...

        int cacheIndex = (mPrivateFlags & PFLAG_FORCE_LAYOUT) == PFLAG_FORCE_LAYOUT ? -1 :
                mMeasureCache.indexOfKey(key);

        if (cacheIndex < 0 || sIgnoreMeasureCache) {
            
            onMeasure(widthMeasureSpec, heightMeasureSpec);
            // 计算视图大小 ->>分析1

        } else {
            ...
      
    }

/**
  * 分析1：onMeasure（）
  * 作用：a. 根据View宽/高的测量规格计算View的宽/高值：getDefaultSize()
  *      b. 存储测量后的View宽 / 高：setMeasuredDimension()
  **/ 
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
    // 参数说明：View的宽 / 高测量规格

    setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),  
                         getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));  
    // setMeasuredDimension() ：获得View宽/高的测量值 ->>分析2
    // 传入的参数通过getDefaultSize()获得 ->>分析3
}

/**
  * 分析2：setMeasuredDimension()
  * 作用：存储测量后的View宽 / 高
  * 注：该方法即为我们重写onMeasure()所要实现的最终目的
  **/
    protected final void setMeasuredDimension(int measuredWidth, int measuredHeight) {  

    //参数说明：测量后子View的宽 / 高值

        // 将测量后子View的宽 / 高值进行传递
            mMeasuredWidth = measuredWidth;  
            mMeasuredHeight = measuredHeight;  
          
            mPrivateFlags |= PFLAG_MEASURED_DIMENSION_SET;  
        } 
    // 由于setMeasuredDimension（）的参数是从getDefaultSize()获得的
    // 下面我们继续看getDefaultSize()的介绍

/**
  * 分析3：getDefaultSize()
  * 作用：根据View宽/高的测量规格计算View的宽/高值
  **/
  public static int getDefaultSize(int size, int measureSpec) {  

        // 参数说明：
        // size：提供的默认大小
        // measureSpec：宽/高的测量规格（含模式 & 测量大小）

            // 设置默认大小
            int result = size; 
            
            // 获取宽/高测量规格的模式 & 测量大小
            int specMode = MeasureSpec.getMode(measureSpec);  
            int specSize = MeasureSpec.getSize(measureSpec);  
          
            switch (specMode) {  
                // 模式为UNSPECIFIED时，使用提供的默认大小 = 参数Size
                case MeasureSpec.UNSPECIFIED:  
                    result = size;  
                    break;  

                // 模式为AT_MOST,EXACTLY时，使用View测量后的宽/高值 = measureSpec中的Size
                case MeasureSpec.AT_MOST:  
                case MeasureSpec.EXACTLY:  
                    result = specSize;  
                    break;  
            }  

         // 返回View的宽/高值
            return result;  
        }


```
* 总结：getDefaultSize()计算View的宽/高值的逻辑
![image](https://upload-images.jianshu.io/upload_images/944365-bf6b3dc2261012dc.png?imageMogr2/auto-orient/)

至此，单一View的宽/高值已经测量完成，即对于单一View的measure过程已经完成。

![image](https://upload-images.jianshu.io/upload_images/944365-2ba9801fcad2b48c.png?imageMogr2/auto-orient/)

实际作用的方法：getDefaultSize() = 计算View的宽/高值、setMeasuredDimension（） = 存储测量后的View宽 / 高

### 1.2.2 ViewGroup的measure过程
* 应用场景
利用现有的组件根据特定的布局方式来组成新的组件.

* 具体使用
继承自ViewGroup 或 各种Layout；含有子 View

* 遍历 测量所有子View的尺寸;
合并将所有子View的尺寸进行，最终得到ViewGroup父视图的测量值

![image](https://upload-images.jianshu.io/upload_images/944365-1438a7fbd93d0987.png?imageMogr2/auto-orient/)

* 入口 = measure（）

若需进行自定义ViewGroup，则需重写onMeasure()，下文会提到


```
/**
  * 源码分析：measure()
  * 作用：基本测量逻辑的判断；调用onMeasure()
  * 注：与单一View measure过程中讲的measure()一致
  **/ 
  public final void measure(int widthMeasureSpec, int heightMeasureSpec) {
    ...
    int cacheIndex = (mPrivateFlags & PFLAG_FORCE_LAYOUT) == PFLAG_FORCE_LAYOUT ? -1 :
            mMeasureCache.indexOfKey(key);
    if (cacheIndex < 0 || sIgnoreMeasureCache) {

        // 调用onMeasure()计算视图大小
        onMeasure(widthMeasureSpec, heightMeasureSpec);
        mPrivateFlags3 &= ~PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT;
    } else {
        ...
}

/**
  * 分析1：onMeasure()
  * 作用：遍历子View & 测量
  * 注：ViewGroup = 一个抽象类 = 无重写View的onMeasure（），需自身复写
  **/


```
为什么ViewGroup的measure过程不像单一View的measure过程那样对onMeasure（）做统一的实现？----因为不同的ViewGroup子类（LinearLayout、RelativeLayout / 自定义ViewGroup子类等）具备不同的布局特性，这导致他们子View的测量方法各有不同.

因此，ViewGroup无法对onMeasure（）作统一实现。这个也是单一View的measure过程与ViewGroup过程最大的不同。

其实，在单一View measure过程中，getDefaultSize()只是简单的测量了宽高值，在实际使用时有时需更精细的测量。所以有时候也需重写onMeasure（）

在自定义ViewGroup中，关键在于：根据需求复写onMeasure()从而实现你的子View测量逻辑。复写onMeasure()的套路如下：
```
/**
  * 根据自身的测量逻辑复写onMeasure（），分为3步
  * 1. 遍历所有子View & 测量：measureChildren（）
  * 2. 合并所有子View的尺寸大小,最终得到ViewGroup父视图的测量值（自身实现）
  * 3. 存储测量后View宽/高的值：调用setMeasuredDimension()  
  **/ 

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  

        // 定义存放测量后的View宽/高的变量
        int widthMeasure ;
        int heightMeasure ;

        // 1. 遍历所有子View & 测量(measureChildren（）)
        // ->> 分析1
        measureChildren(widthMeasureSpec, heightMeasureSpec)；

        // 2. 合并所有子View的尺寸大小，最终得到ViewGroup父视图的测量值
         void measureCarson{
             ... // 自身实现
         }

        // 3. 存储测量后View宽/高的值：调用setMeasuredDimension()  
        // 类似单一View的过程，此处不作过多描述
        setMeasuredDimension(widthMeasure,  heightMeasure);  
  }
  // 从上可看出：
  // 复写onMeasure（）有三步，其中2步直接调用系统方法
  // 需自身实现的功能实际仅为步骤2：合并所有子View的尺寸大小

/**
  * 分析1：measureChildren()
  * 作用：遍历子View & 调用measureChild()进行下一步测量
  **/ 

    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        // 参数说明：父视图的测量规格（MeasureSpec）

                final int size = mChildrenCount;
                final View[] children = mChildren;

                // 遍历所有子view
                for (int i = 0; i < size; ++i) {
                    final View child = children[i];
                     // 调用measureChild()进行下一步的测量 ->>分析1
                    if ((child.mViewFlags & VISIBILITY_MASK) != GONE) {
                        measureChild(child, widthMeasureSpec, heightMeasureSpec);
                    }
                }
            }

/**
  * 分析2：measureChild()
  * 作用：a. 计算单个子View的MeasureSpec
  *      b. 测量每个子View最后的宽 / 高：调用子View的measure()
  **/ 
  protected void measureChild(View child, int parentWidthMeasureSpec,
            int parentHeightMeasureSpec) {

        // 1. 获取子视图的布局参数
        final LayoutParams lp = child.getLayoutParams();

        // 2. 根据父视图的MeasureSpec & 布局参数LayoutParams，计算单个子View的MeasureSpec
        // getChildMeasureSpec() 请看上面第2节储备知识处
        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,// 获取 ChildView 的 widthMeasureSpec
                mPaddingLeft + mPaddingRight, lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,// 获取 ChildView 的 heightMeasureSpec
                mPaddingTop + mPaddingBottom, lp.height);

        // 3. 将计算好的子View的MeasureSpec值传入measure()，进行最后的测量
        // 下面的流程即类似单一View的过程，此处不作过多描述
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }
    // 回到调用原处

```
![image](https://upload-images.jianshu.io/upload_images/944365-c9ea47e8b5e325bf.png?imageMogr2/auto-orient/)

### 1.2.3 ViewGroup的measure过程实例解析（LinearLayout）

```
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

      // 根据不同的布局属性进行不同的计算
      // 此处只选垂直方向的测量过程，即measureVertical()->>分析1
      if (mOrientation == VERTICAL) {
          measureVertical(widthMeasureSpec, heightMeasureSpec);
      } else {
          measureHorizontal(widthMeasureSpec, heightMeasureSpec);
      }

      
}

  /**
    * 分析1：measureVertical()
    * 作用：测量LinearLayout垂直方向的测量尺寸
    **/ 
  void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
      
      /**
       *  其余测量逻辑
       **/
          // 获取垂直方向上的子View个数
          final int count = getVirtualChildCount();

          // 遍历子View获取其高度，并记录下子View中最高的高度数值
          for (int i = 0; i < count; ++i) {
              final View child = getVirtualChildAt(i);

              // 子View不可见，直接跳过该View的measure过程，getChildrenSkipCount()返回值恒为0
              // 注：若view的可见属性设置为VIEW.INVISIBLE，还是会计算该view大小
              if (child.getVisibility() == View.GONE) {
                 i += getChildrenSkipCount(child, i);
                 continue;
              }

              // 记录子View是否有weight属性设置，用于后面判断是否需要二次measure
              totalWeight += lp.weight;

              if (heightMode == MeasureSpec.EXACTLY && lp.height == 0 && lp.weight > 0) {
                  // 如果LinearLayout的specMode为EXACTLY且子View设置了weight属性，在这里会跳过子View的measure过程
                  // 同时标记skippedMeasure属性为true，后面会根据该属性决定是否进行第二次measure
                // 若LinearLayout的子View设置了weight，会进行两次measure计算，比较耗时
                  // 这就是为什么LinearLayout的子View需要使用weight属性时候，最好替换成RelativeLayout布局
                
                  final int totalLength = mTotalLength;
                  mTotalLength = Math.max(totalLength, totalLength + lp.topMargin + lp.bottomMargin);
                  skippedMeasure = true;
              } else {
                  int oldHeight = Integer.MIN_VALUE;
     /**
       *  步骤1：遍历所有子View & 测量：measureChildren（）
       *  注：该方法内部，最终会调用measureChildren（），从而 遍历所有子View & 测量
       **/
            measureChildBeforeLayout(

                   child, i, widthMeasureSpec, 0, heightMeasureSpec,
                   totalWeight == 0 ? mTotalLength : 0);
                   ...
            }

      /**
       *  步骤2：合并所有子View的尺寸大小,最终得到ViewGroup父视图的测量值（自身实现）
       **/        
              final int childHeight = child.getMeasuredHeight();

              // 1. mTotalLength用于存储LinearLayout在竖直方向的高度
              final int totalLength = mTotalLength;

              // 2. 每测量一个子View的高度， mTotalLength就会增加
              mTotalLength = Math.max(totalLength, totalLength + childHeight + lp.topMargin +
                     lp.bottomMargin + getNextLocationOffset(child));
      
              // 3. 记录LinearLayout占用的总高度
              // 即除了子View的高度，还有本身的padding属性值
              mTotalLength += mPaddingTop + mPaddingBottom;
              int heightSize = mTotalLength;

      /**
       *  步骤3：存储测量后View宽/高的值：调用setMeasuredDimension()  
       **/ 
       setMeasureDimension(resolveSizeAndState(maxWidth,width))

    ...

  }
```

# 2.简单描述一下自定义View的布局（layout）过程
## 2.1 基本内容
* 作用：计算视图（View）的位置，即计算View的四个顶点位置：Left、Top、Right 和 Bottom
* 过程：
![image](https://upload-images.jianshu.io/upload_images/944365-6e978f448667eb52.png?imageMogr2/auto-orient/)

## 2.2 单一View的layout过程
![image](https://upload-images.jianshu.io/upload_images/944365-05b688ab79b57ecf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/360)

```
/**
  * 源码分析：layout（）
  * 作用：确定View本身的位置，即设置View本身的四个顶点位置
  */ 
  public void layout(int l, int t, int r, int b) {  

    // 当前视图的四个顶点
    int oldL = mLeft;  
    int oldT = mTop;  
    int oldB = mBottom;  
    int oldR = mRight;  
      
    // 1. 确定View的位置：setFrame（） / setOpticalFrame（）
    // 即初始化四个顶点的值、判断当前View大小和位置是否发生了变化 & 返回 
    // ->>分析1、分析2
    boolean changed = isLayoutModeOptical(mParent) ?
            setOpticalFrame(l, t, r, b) : setFrame(l, t, r, b);

    // 2. 若视图的大小 & 位置发生变化
    // 会重新确定该View所有的子View在父容器的位置：onLayout（）
    if (changed || (mPrivateFlags & PFLAG_LAYOUT_REQUIRED) == PFLAG_LAYOUT_REQUIRED) {  

        onLayout(changed, l, t, r, b);  
        // 对于单一View的laytou过程：由于单一View是没有子View的，故onLayout（）是一个空实现->>分析3
        // 对于ViewGroup的laytou过程：由于确定位置与具体布局有关，所以onLayout（）在ViewGroup为1个抽象方法，需重写实现（后面会详细说）
  ...

}  

/**
  * 分析1：setFrame（）
  * 作用：根据传入的4个位置值，设置View本身的四个顶点位置
  * 即：最终确定View本身的位置
  */ 
  protected boolean setFrame(int left, int top, int right, int bottom) {
        ...
    // 通过以下赋值语句记录下了视图的位置信息，即确定View的四个顶点
    // 从而确定了视图的位置
    mLeft = left;
    mTop = top;
    mRight = right;
    mBottom = bottom;

    mRenderNode.setLeftTopRightBottom(mLeft, mTop, mRight, mBottom);

    }

/**
  * 分析2：setOpticalFrame（）
  * 作用：根据传入的4个位置值，设置View本身的四个顶点位置
  * 即：最终确定View本身的位置
  */ 
  private boolean setOpticalFrame(int left, int top, int right, int bottom) {

        Insets parentInsets = mParent instanceof View ?
                ((View) mParent).getOpticalInsets() : Insets.NONE;

        Insets childInsets = getOpticalInsets();

        // 内部实际上是调用setFrame（）
        return setFrame(
                left   + parentInsets.left - childInsets.left,
                top    + parentInsets.top  - childInsets.top,
                right  + parentInsets.left + childInsets.right,
                bottom + parentInsets.top  + childInsets.bottom);
    }
    // 回到调用原处

/**
  * 分析3：onLayout（）
  * 注：对于单一View的laytou过程
  *    a. 由于单一View是没有子View的，故onLayout（）是一个空实现
  *    b. 由于在layout（）中已经对自身View进行了位置计算，所以单一View的layout过程在layout（）后就已完成了
  */ 
 protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

   // 参数说明
   // changed 当前View的大小和位置改变了 
   // left 左部位置
   // top 顶部位置
   // right 右部位置
   // bottom 底部位置

}

```
* 总结
![image](https://upload-images.jianshu.io/upload_images/944365-756f72f8ccc58d2c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/310)

## 2.3  ViewGroup的layout过程
计算自身ViewGroup的位置：layout（）

遍历子View & 确定自身子View在ViewGroup的位置（调用子View 的 layout（））：onLayout（）

![image](https://upload-images.jianshu.io/upload_images/944365-7ebd03609c758d47.png?imageMogr2/auto-orient/)

此处需注意：
ViewGroup 和 View 同样拥有layout（）和onLayout()，但二者不同的：

一开始计算ViewGroup位置时，调用的是ViewGroup的layout（）和onLayout()；
当开始遍历子View & 计算子View位置时，调用的是子View的layout（）和onLayout()


```
/**
  * 源码分析：layout（）
  * 作用：确定View本身的位置，即设置View本身的四个顶点位置
  * 注：与单一View的layout（）源码一致
  */ 
  public void layout(int l, int t, int r, int b) {  

    // 当前视图的四个顶点
    int oldL = mLeft;  
    int oldT = mTop;  
    int oldB = mBottom;  
    int oldR = mRight;  
      
    // 1. 确定View的位置：setFrame（） / setOpticalFrame（）
    // 即初始化四个顶点的值、判断当前View大小和位置是否发生了变化 & 返回 
    // ->>分析1、分析2
    boolean changed = isLayoutModeOptical(mParent) ?
            setOpticalFrame(l, t, r, b) : setFrame(l, t, r, b);

    // 2. 若视图的大小 & 位置发生变化
    // 会重新确定该View所有的子View在父容器的位置：onLayout（）
    if (changed || (mPrivateFlags & PFLAG_LAYOUT_REQUIRED) == PFLAG_LAYOUT_REQUIRED) {  

        onLayout(changed, l, t, r, b);  
        // 对于单一View的laytou过程：由于单一View是没有子View的，故onLayout（）是一个空实现（上面已分析完毕）
        // 对于ViewGroup的laytou过程：由于确定位置与具体布局有关，所以onLayout（）在ViewGroup为1个抽象方法，需重写实现 ->>分析3
  ...

}  

/**
  * 分析1：setFrame（）
  * 作用：确定View本身的位置，即设置View本身的四个顶点位置
  */ 
  protected boolean setFrame(int left, int top, int right, int bottom) {
        ...
    // 通过以下赋值语句记录下了视图的位置信息，即确定View的四个顶点
    // 从而确定了视图的位置
    mLeft = left;
    mTop = top;
    mRight = right;
    mBottom = bottom;

    mRenderNode.setLeftTopRightBottom(mLeft, mTop, mRight, mBottom);

    }

/**
  * 分析2：setOpticalFrame（）
  * 作用：确定View本身的位置，即设置View本身的四个顶点位置
  */ 
  private boolean setOpticalFrame(int left, int top, int right, int bottom) {

        Insets parentInsets = mParent instanceof View ?
                ((View) mParent).getOpticalInsets() : Insets.NONE;

        Insets childInsets = getOpticalInsets();

        // 内部实际上是调用setFrame（）
        return setFrame(
                left   + parentInsets.left - childInsets.left,
                top    + parentInsets.top  - childInsets.top,
                right  + parentInsets.left + childInsets.right,
                bottom + parentInsets.top  + childInsets.bottom);
    }
    // 回到调用原处

/**
  * 分析3：onLayout（）
  * 作用：计算该ViewGroup包含所有的子View在父容器的位置（）
  * 注： 
  *      a. 定义为抽象方法，需重写，因：子View的确定位置与具体布局有关，所以onLayout（）在ViewGroup没有实现
  *      b. 在自定义ViewGroup时必须复写onLayout（）！！！！！
  *      c. 复写原理：遍历子View 、计算当前子View的四个位置值 & 确定自身子View的位置（调用子View layout（））
  */ 
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

     // 参数说明
     // changed 当前View的大小和位置改变了 
     // left 左部位置
     // top 顶部位置
     // right 右部位置
     // bottom 底部位置

     // 1. 遍历子View：循环所有子View
          for (int i=0; i<getChildCount(); i++) {
              View child = getChildAt(i);   

              // 2. 计算当前子View的四个位置值
                // 2.1 位置的计算逻辑
                ...// 需自己实现，也是自定义View的关键

                // 2.2 对计算后的位置值进行赋值
                int mLeft  = Left
                int mTop  = Top
                int mRight = Right
                int mBottom = Bottom

              // 3. 根据上述4个位置的计算值，设置子View的4个顶点：调用子view的layout() & 传递计算过的参数
              // 即确定了子View在父容器的位置
              child.layout(mLeft, mTop, mRight, mBottom);
              // 该过程类似于单一View的layout过程中的layout（）和onLayout（），此处不作过多描述
          }
      }
  }

```
* 总结

![image](https://upload-images.jianshu.io/upload_images/944365-6e27d40b50081d60.png?imageMogr2/auto-orient/)

## 2.4 自定义View布局过程示例

上面讲的例子是系统提供的、已经封装好的ViewGroup子类：LinearLayout
但是，一般来说我们使用的都是自定义View；
接下来，我用一个简单的例子讲下自定义View的layout（）过程

![image](https://upload-images.jianshu.io/upload_images/944365-b5d0de9e0342ea19.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)

具体计算逻辑是指计算子View的位置，即计算四顶点位置 = 计算Left、Top、Right和Bottom；
主要是写在复写的onLayout（）
计算公式如下：

![image](https://upload-images.jianshu.io/upload_images/944365-cf72b2ef1fac691a.png?imageMogr2/auto-orient/)

因为其余方法同上，这里不作过多描述，所以这里只分析复写的onLayout（）

```
/**
  * 源码分析：LinearLayout复写的onLayout（）
  * 注：复写的逻辑 和 LinearLayout measure过程的 onMeasure()类似
  */ 
  @Override  
protected void onLayout(boolean changed, int l, int t, int r, int b) {  

     // 参数说明
     // changed 当前View的大小和位置改变了 
     // left 左部位置
     // top 顶部位置
     // right 右部位置
     // bottom 底部位置

        // 1. 遍历子View：循环所有子View
        // 注：本例中其实只有一个
        for (int i=0; i<getChildCount(); i++) {
            View child = getChildAt(i);

            // 取出当前子View宽 / 高
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();

            // 2. 计算当前子View的四个位置值
                // 2.1 位置的计算逻辑
                int mLeft = (r - width) / 2;
                int mTop = (b - height) / 2;
                int mRight =  mLeft + width；
                int mBottom =  mLeft + width；

            // 3. 根据上述4个位置的计算值，设置子View的4个顶点
            // 即确定了子View在父容器的位置
            child.layout(mLeft, mTop, mRight,mBottom);
        }
    }
}


```
布局文件如下：

```
<?xml version="1.0" encoding="utf-8"?>
<scut.carson_ho.layout_demo.Demo_ViewGroup xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:background="#eee998"
    tools:context="scut.carson_ho.layout_demo.MainActivity">

    <Button
        android:text="ChildView"
        android:layout_width="200dip"
        android:layout_height="200dip"
        android:background="#333444"
        android:id="@+id/ChildView" />
</scut.carson_ho.layout_demo.Demo_ViewGroup >


```

## 2.5 细节问题：getWidth() （ getHeight()）与 getMeasuredWidth() （getMeasuredHeight()）获取的宽 （高）有什么区别？
![image](https://upload-images.jianshu.io/upload_images/944365-6b27b9835d927e04.png?imageMogr2/auto-orient/)

# 3.简单描述一下自定义View的绘制（draw）过程
## 3.1 基本介绍
* 作用：绘制View视图

## 3.2 单一View的draw过程
原理（步骤）


```
View绘制自身（含背景、内容）
绘制装饰（滚动指示器、滚动条、和前景）
```
![image](https://upload-images.jianshu.io/upload_images/944365-1554a862ed0c3f95.png?imageMogr2/auto-orient/)


```
/**
  * 源码分析：draw（）
  * 作用：根据给定的 Canvas 自动渲染 View（包括其所有子 View）。
  * 绘制过程：
  *   1. 绘制view背景
  *   2. 绘制view内容
  *   3. 绘制子View
  *   4. 绘制装饰（渐变框，滑动条等等）
  * 注：
  *    a. 在调用该方法之前必须要完成 layout 过程
  *    b. 所有的视图最终都是调用 View 的 draw （）绘制视图（ ViewGroup 没有复写此方法）
  *    c. 在自定义View时，不应该复写该方法，而是复写 onDraw(Canvas) 方法进行绘制
  *    d. 若自定义的视图确实要复写该方法，那么需先调用 super.draw(canvas)完成系统的绘制，然后再进行自定义的绘制
  */ 
  public void draw(Canvas canvas) {

    ...// 仅贴出关键代码
  
    int saveCount;

    // 步骤1： 绘制本身View背景
        if (!dirtyOpaque) {
            drawBackground(canvas);
        }

    // 若有必要，则保存图层（还有一个复原图层）
    // 优化技巧：当不需绘制 Layer 时，“保存图层“和“复原图层“这两步会跳过
    // 因此在绘制时，节省 layer 可以提高绘制效率
    final int viewFlags = mViewFlags;
    if (!verticalEdges && !horizontalEdges) {

    // 步骤2：绘制本身View内容
        if (!dirtyOpaque) 
            onDraw(canvas);
        // View 中：默认为空实现，需复写
        // ViewGroup中：需复写

    // 步骤3：绘制子View
    // 由于单一View无子View，故View 中：默认为空实现
    // ViewGroup中：系统已经复写好对其子视图进行绘制我们不需要复写
        dispatchDraw(canvas);
        
    // 步骤4：绘制装饰，如滑动条、前景色等等
        onDrawScrollBars(canvas);

        return;
    }
    ...    
}

```

我们继续分析在draw（）中4个步骤调用的drawBackground（）、 onDraw()、dispatchDraw()、onDrawScrollBars(canvas)


```
/**
  * 步骤1：drawBackground(canvas)
  * 作用：绘制View本身的背景
  */
  private void drawBackground(Canvas canvas) {
        // 获取背景 drawable
        final Drawable background = mBackground;
        if (background == null) {
            return;
        }
        // 根据在 layout 过程中获取的 View 的位置参数，来设置背景的边界
        setBackgroundBounds();

        .....

        // 获取 mScrollX 和 mScrollY值 
        final int scrollX = mScrollX;
        final int scrollY = mScrollY;
        if ((scrollX | scrollY) == 0) {
            background.draw(canvas);
        } else {
            // 若 mScrollX 和 mScrollY 有值，则对 canvas 的坐标进行偏移
            canvas.translate(scrollX, scrollY);


            // 调用 Drawable 的 draw 方法绘制背景
            background.draw(canvas);
            canvas.translate(-scrollX, -scrollY);
        }
   } 

/**
  * 步骤2：onDraw(canvas)
  * 作用：绘制View本身的内容
  * 注：
  *   a. 由于 View 的内容各不相同，所以该方法是一个空实现
  *   b. 在自定义绘制过程中，需由子类去实现复写该方法，从而绘制自身的内容
  *   c. 谨记：自定义View中 必须 且 只需复写onDraw（）
  */
  protected void onDraw(Canvas canvas) {
      
        ... // 复写从而实现绘制逻辑

  }

/**
  * 步骤3： dispatchDraw(canvas)
  * 作用：绘制子View
  * 注：由于单一View中无子View，故为空实现
  */
  protected void dispatchDraw(Canvas canvas) {

        ... // 空实现

  }

/**
  * 步骤4： onDrawScrollBars(canvas)
  * 作用：绘制装饰，如 滚动指示器、滚动条、和前景等
  */
  public void onDrawForeground(Canvas canvas) {
        onDrawScrollIndicators(canvas);
        onDrawScrollBars(canvas);

        final Drawable foreground = mForegroundInfo != null ? mForegroundInfo.mDrawable : null;
        if (foreground != null) {
            if (mForegroundInfo.mBoundsChanged) {
                mForegroundInfo.mBoundsChanged = false;
                final Rect selfBounds = mForegroundInfo.mSelfBounds;
                final Rect overlayBounds = mForegroundInfo.mOverlayBounds;

                if (mForegroundInfo.mInsidePadding) {
                    selfBounds.set(0, 0, getWidth(), getHeight());
                } else {
                    selfBounds.set(getPaddingLeft(), getPaddingTop(),
                            getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
                }

                final int ld = getLayoutDirection();
                Gravity.apply(mForegroundInfo.mGravity, foreground.getIntrinsicWidth(),
                        foreground.getIntrinsicHeight(), selfBounds, overlayBounds, ld);
                foreground.setBounds(overlayBounds);
            }

            foreground.draw(canvas);
        }
    }

```
## 3.3ViewGroup的draw过程

![image](https://upload-images.jianshu.io/upload_images/944365-cf42edcab0a206fa.png?imageMogr2/auto-orient/)


```
/**
  * 源码分析：draw（）
  * 与单一View的draw（）流程类似
  * 作用：根据给定的 Canvas 自动渲染 View（包括其所有子 View）
  * 绘制过程：
  *   1. 绘制view背景
  *   2. 绘制view内容
  *   3. 绘制子View
  *   4. 绘制装饰（渐变框，滑动条等等）
  * 注：
  *    a. 在调用该方法之前必须要完成 layout 过程
  *    b. 所有的视图最终都是调用 View 的 draw （）绘制视图（ ViewGroup 没有复写此方法）
  *    c. 在自定义View时，不应该复写该方法，而是复写 onDraw(Canvas) 方法进行绘制
  *    d. 若自定义的视图确实要复写该方法，那么需先调用 super.draw(canvas)完成系统的绘制，然后再进行自定义的绘制
  */ 
  public void draw(Canvas canvas) {

    ...// 仅贴出关键代码
  
    int saveCount;

    // 步骤1： 绘制本身View背景
        if (!dirtyOpaque) {
            drawBackground(canvas);
        }

    // 若有必要，则保存图层（还有一个复原图层）
    // 优化技巧：当不需绘制 Layer 时，“保存图层“和“复原图层“这两步会跳过
    // 因此在绘制时，节省 layer 可以提高绘制效率
    final int viewFlags = mViewFlags;
    if (!verticalEdges && !horizontalEdges) {

    // 步骤2：绘制本身View内容
        if (!dirtyOpaque) 
            onDraw(canvas);
        // View 中：默认为空实现，需复写
        // ViewGroup中：需复写

    // 步骤3：绘制子View
    // ViewGroup中：系统已复写好对其子视图进行绘制，不需复写
        dispatchDraw(canvas);
        
    // 步骤4：绘制装饰，如滑动条、前景色等等
        onDrawScrollBars(canvas);

        return;
    }
    ...    
}

```
下面直接进入与单一View draw过程最大不同的步骤4：dispatchDraw()


```
/**
  * 源码分析：dispatchDraw（）
  * 作用：遍历子View & 绘制子View
  * 注：
  *   a. ViewGroup中：由于系统为我们实现了该方法，故不需重写该方法
  *   b. View中默认为空实现（因为没有子View可以去绘制）
  */ 
    protected void dispatchDraw(Canvas canvas) {
        ......

         // 1. 遍历子View
        final int childrenCount = mChildrenCount;
        ......

        for (int i = 0; i < childrenCount; i++) {
                ......
                if ((transientChild.mViewFlags & VISIBILITY_MASK) == VISIBLE ||
                        transientChild.getAnimation() != null) {
                  // 2. 绘制子View视图 ->>分析1
                    more |= drawChild(canvas, transientChild, drawingTime);
                }
                ....
        }
    }

/**
  * 分析1：drawChild（）
  * 作用：绘制子View
  */
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        // 最终还是调用了子 View 的 draw （）进行子View的绘制
        return child.draw(canvas, this, drawingTime);
    }

```
## 3.4 其他细节问题：View.setWillNotDraw()

```
/**
  * 源码分析：setWillNotDraw()
  * 定义：View 中的特殊方法
  * 作用：设置 WILL_NOT_DRAW 标记位；
  * 注：
  *   a. 该标记位的作用是：当一个View不需要绘制内容时，系统进行相应优化
  *   b. 默认情况下：View 不启用该标记位（设置为false）；ViewGroup 默认启用（设置为true）
  */ 

public void setWillNotDraw(boolean willNotDraw) {

    setFlags(willNotDraw ? WILL_NOT_DRAW : 0, DRAW_MASK);

}

// 应用场景
// a. setWillNotDraw参数设置为true：当自定义View继承自 ViewGroup 、且本身并不具备任何绘制时，设置为 true 后，系统会进行相应的优化。
// b. setWillNotDraw参数设置为false：当自定义View继承自 ViewGroup 、且需要绘制内容时，那么设置为 false，来关闭 WILL_NOT_DRAW 这个标记位。

```

# 4. 简单描述一下如何实现一个自定义View
![image](https://upload-images.jianshu.io/upload_images/944365-28d0ffc3fd995621.png?imageMogr2/auto-orient/)

![image](https://upload-images.jianshu.io/upload_images/944365-0b3407e5debd137e.png?imageMogr2/auto-orient/)

# 4.1 几个注意点
![image](https://upload-images.jianshu.io/upload_images/944365-ab0d19c418ffeeaa.png?imageMogr2/auto-orient/)

* 支持特殊属性

支持wrap_content
如果不在onMeasure（）中对wrap_content作特殊处理，那么wrap_content属性将失效。

详细看[这里](https://www.jianshu.com/p/ca118d704b5e)

支持padding & margin

如果不支持，那么padding和margin（ViewGroup情况）的属性将失效

对于继承View的控件，padding是在draw()中处理
对于继承ViewGroup的控件，padding和margin会直接影响measure和layout过程

* 多线程应直接使用post方式

View的内部本身提供了post系列的方法，完全可以替代Handler的作用，使用起来更加方便、直接。

* 避免内存泄露

主要针对View中含有线程或动画的情况：当View退出或不可见时，记得及时停止该View包含的线程和动画，否则会造成内存泄露问题。


```
启动或停止线程/ 动画的方式：

启动线程/ 动画：使用view.onAttachedToWindow（），因为该方法调用的时机是当包含View的Activity启动的时刻
停止线程/ 动画：使用view.onDetachedFromWindow（），因为该方法调用的时机是当包含View的Activity退出或当前View被remove的时刻
```
* 处理好滑动冲突

当View带有滑动嵌套情况时，必须要处理好滑动冲突，否则会严重影响View的显示效果。

Android开发艺术探索里面有详细的解决方案

# 4.2具体实例
1. 接下来，我将用自定义View中最常用的继承View来说明自定义View的具体应用和需要注意的点

![image](https://upload-images.jianshu.io/upload_images/944365-502bad8cac77b8f5.png?imageMogr2/auto-orient/)

实例说明：画一个实心圆

2. 创建自定义View类

```
// 用于绘制自定义View的具体内容
// 具体绘制是在复写的onDraw（）内实现

public class CircleView extends View {

    // 设置画笔变量
    Paint mPaint1;

    // 自定义View有四个构造函数
    // 如果View是在Java代码里面new的，则调用第一个构造函数
    public CircleView(Context context){
        super(context);

        // 在构造函数里初始化画笔的操作
        init();
    }


// 如果View是在.xml里声明的，则调用第二个构造函数
// 自定义属性是从AttributeSet参数传进来的
    public CircleView(Context context,AttributeSet attrs){
        super(context, attrs);
        init();

    }

// 不会自动调用
// 一般是在第二个构造函数里主动调用
// 如View有style属性时
    public CircleView(Context context,AttributeSet attrs,int defStyleAttr ){
        super(context, attrs,defStyleAttr);
        init();
    }


    //API21之后才使用
    // 不会自动调用
    // 一般是在第二个构造函数里主动调用
    // 如View有style属性时
    public  CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // 画笔初始化
    private void init() {

        // 创建画笔
        mPaint1 = new Paint ();
        // 设置画笔颜色为蓝色
        mPaint1.setColor(Color.BLUE);
        // 设置画笔宽度为10px
        mPaint1.setStrokeWidth(5f);
        //设置画笔模式为填充
        mPaint1.setStyle(Paint.Style.FILL);

    }


    // 复写onDraw()进行绘制  
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

       // 获取控件的高度和宽度
        int width = getWidth();
        int height = getHeight();

        // 设置圆的半径 = 宽,高最小值的2分之1
        int r = Math.min(width, height)/2;

        // 画出圆（蓝色）
        // 圆心 = 控件的中央,半径 = 宽,高最小值的2分之1
        canvas.drawCircle(width/2,height/2,r,mPaint1);

    }

}


```
3. 在布局文件中添加自定义View类的组件

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="scut.carson_ho.diy_view.MainActivity">

<!-- 注意添加自定义View组件的标签名：包名 + 自定义View类名-->
    <!--  控件背景设置为黑色-->
    <scut.carson_ho.diy_view.CircleView
        android:layout_width="match_parent"
        android:layout_height="150dp"
        android:background="#000000"

</RelativeLayout>

```
4. 在MainActivity类设置显示

```
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

```
5. 手动支持wrap_content属性

上面链接里面有

6. 支持padding属性

padding属性：用于设置控件内容相对控件边缘的边距；

区别与margin属性（同样称为：边距）：控件边缘相对父控件的边距（父控件控制），具体区别如下：

![image](https://upload-images.jianshu.io/upload_images/944365-fdbceb4095154a20.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/579)

绘制时考虑传入的padding属性值（四个方向）。


```
// 仅看复写的onDraw（）
@Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        // 获取传入的padding值
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();


        // 获取绘制内容的高度和宽度（考虑了四个方向的padding值）
        int width = getWidth() - paddingLeft - paddingRight ;
        int height = getHeight() - paddingTop - paddingBottom ;

        // 设置圆的半径 = 宽,高最小值的2分之1
        int r = Math.min(width, height)/2;

        // 画出圆(蓝色)
        // 圆心 = 控件的中央,半径 = 宽,高最小值的2分之1
        canvas.drawCircle(paddingLeft+width/2,paddingTop+height/2,r,mPaint1);

    }


```
7. 提供自定义属性


```
使用步骤有如下：
在values目录下创建自定义属性的xml文件
在自定义View的构造方法中解析自定义属性的值
在布局文件中使用自定义属性
```
* 步骤1：在values目录下创建自定义属性的xml文件

attrs_circle_view.xml


```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!--自定义属性集合:CircleView-->
    <!--在该集合下,设置不同的自定义属性-->
    <declare-styleable name="CircleView">
        <!--在attr标签下设置需要的自定义属性-->
        <!--此处定义了一个设置图形的颜色:circle_color属性,格式是color,代表颜色-->
        <!--格式有很多种,如资源id(reference)等等-->
        <attr name="circle_color" format="color"/>

    </declare-styleable>
</resources>

```

```
<-- 1. reference：使用某一资源ID -->
<declare-styleable name="名称">
    <attr name="background" format="reference" />
</declare-styleable>
// 使用格式
  // 1. Java代码
  private int ResID;
  private Drawable ResDraw;
  ResID = typedArray.getResourceId(R.styleable.SuperEditText_background, R.drawable.background); // 获得资源ID
  ResDraw = getResources().getDrawable(ResID); // 获得Drawble对象

  // 2. xml代码
<ImageView
    android:layout_width="42dip"
    android:layout_height="42dip"
    app:background="@drawable/图片ID" />

<--  2. color：颜色值 -->
<declare-styleable name="名称">
    <attr name="textColor" format="color" />
</declare-styleable>
// 格式使用
<TextView
    android:layout_width="42dip"
    android:layout_height="42dip"
    android:textColor="#00FF00" />

<-- 3. boolean：布尔值 -->
<declare-styleable name="名称">
    <attr name="focusable" format="boolean" />
</declare-styleable>
// 格式使用
<Button
    android:layout_width="42dip"
    android:layout_height="42dip"
    android:focusable="true" />

<-- 4. dimension：尺寸值 -->
<declare-styleable name="名称">
    <attr name="layout_width" format="dimension" />
</declare-styleable>
// 格式使用：
<Button
    android:layout_width="42dip"
    android:layout_height="42dip" />

<-- 5. float：浮点值 -->
<declare-styleable name="AlphaAnimation">
    <attr name="fromAlpha" format="float" />
    <attr name="toAlpha" format="float" />
</declare-styleable>
// 格式使用
<alpha
    android:fromAlpha="1.0"
    android:toAlpha="0.7" />

<-- 6. integer：整型值 -->
<declare-styleable name="AnimatedRotateDrawable">
    <attr name="frameDuration" format="integer" />
    <attr name="framesCount" format="integer" />
</declare-styleable>
// 格式使用
<animated-rotate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:frameDuration="100"
    android:framesCount="12"
 />

<-- 7. string：字符串 -->
<declare-styleable name="MapView">
    <attr name="apiKey" format="string" />
</declare-styleable>
// 格式使用
<com.google.android.maps.MapView
 android:apiKey="0jOkQ80oD1JL9C6HAja99uGXCRiS2CGjKO_bc_g" />


```
* 步骤2：在自定义View的构造方法中解析自定义属性的值

此处是需要解析circle_color属性的值


```
// 该构造函数需要重写
  public CircleView(Context context, AttributeSet attrs) {

        this(context, attrs,0);
        // 原来是：super(context,attrs);
        init();


public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 加载自定义属性集合CircleView
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CircleView);

        // 解析集合中的属性circle_color属性
        // 该属性的id为:R.styleable.CircleView_circle_color
        // 将解析的属性传入到画圆的画笔颜色变量当中（本质上是自定义画圆画笔的颜色）
        // 第二个参数是默认设置颜色（即无指定circle_color情况下使用）
        mColor = a.getColor(R.styleable.CircleView_circle_color,Color.RED);

        // 解析后释放资源
        a.recycle();

        init();


```
* 步骤3：在布局文件中使用自定义属性


```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
  <!--必须添加schemas声明才能使用自定义属性-->
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="scut.carson_ho.diy_view.MainActivity"
    >
  
<!-- 注意添加自定义View组件的标签名：包名 + 自定义View类名-->
    <!--  控件背景设置为黑色-->
    <scut.carson_ho.diy_view.CircleView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"

        android:background="#000000"
        android:padding="30dp"

    <!--设置自定义颜色-->
        app:circle_color="#FF4081"
         />
</RelativeLayout>


```



