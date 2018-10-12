# 1.View的事件传递机制
## 1.基本认知
1. 传递对象：当用户触摸屏幕时（View 或 ViewGroup派生的控件），将产生点击事件（Touch事件），当一个点击事件（MotionEvent ）产生后，系统需把这个事件传递给一个具体的 View 去处理。
2. 本质：将点击事件（MotionEvent）传递到某个具体的View & 处理的整个过程
3. 哪些对象之间传递：Activity、ViewGroup、View

Android的UI界面由Activity、ViewGroup、View 及其派生类组成

![image](https://upload-images.jianshu.io/upload_images/944365-d0a7e6f3c2bbefcc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)

## 2.事件分发机制
1. 分发过程的U型图
![image](https://upload-images.jianshu.io/upload_images/966283-b9cb65aceea9219b.png?imageMogr2/auto-orient/)
![image](https://upload-images.jianshu.io/upload_images/944365-2064dcb69200fc6d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/505)
### 2.1 Activity事件的分发机制
* 源码分析
当一个点击事件发生时，事件最先传到Activity的dispatchTouchEvent()进行事件分发
```
/**
  * 源码分析：Activity.dispatchTouchEvent（）
  */ 
    public boolean dispatchTouchEvent(MotionEvent ev) {

            // 一般事件列开始都是DOWN事件 = 按下事件，故此处基本是true
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {

                onUserInteraction();
                // ->>分析1

            }

            // ->>分析2
            if (getWindow().superDispatchTouchEvent(ev)) {

                return true;
                // 若getWindow().superDispatchTouchEvent(ev)的返回true
                // 则Activity.dispatchTouchEvent（）就返回true，则方法结束。即 ：该点击事件停止往下传递 & 事件传递过程结束
                // 否则：继续往下调用Activity.onTouchEvent

            }
            // ->>分析4
            return onTouchEvent(ev);
        }

/**
  * 分析2：getWindow().superDispatchTouchEvent(ev)
  * 说明：
  *     a. getWindow() = 获取Window类的对象
  *     b. Window类是抽象类，其唯一实现类 = PhoneWindow类；即此处的Window类对象 = PhoneWindow类对象
  *     c. Window类的superDispatchTouchEvent() = 1个抽象方法，由子类PhoneWindow类实现
  */
    @Override
    public boolean superDispatchTouchEvent(MotionEvent event) {

        return mDecor.superDispatchTouchEvent(event);
        // mDecor = 顶层View（DecorView）的实例对象
        // ->> 分析3
    }

/**
  * 分析3：mDecor.superDispatchTouchEvent(event)
  * 定义：属于顶层View（DecorView）
  * 说明：
  *     a. DecorView类是PhoneWindow类的一个内部类
  *     b. DecorView继承自FrameLayout，是所有界面的父类
  *     c. FrameLayout是ViewGroup的子类，故DecorView的间接父类 = ViewGroup
  */
    public boolean superDispatchTouchEvent(MotionEvent event) {

        return super.dispatchTouchEvent(event);
        // 调用父类的方法 = ViewGroup的dispatchTouchEvent()
        // 即 将事件传递到ViewGroup去处理，详细请看ViewGroup的事件分发机制

    }
    // 回到最初的调用原处

最终调用了dispatchTouchEvent（ViewGroup的）
```

```
/**
  * 分析4：Activity.onTouchEvent（）
  * 定义：属于顶层View（DecorView）
  * 说明：
  *     a. DecorView类是PhoneWindow类的一个内部类
  *     b. DecorView继承自FrameLayout，是所有界面的父类
  *     c. FrameLayout是ViewGroup的子类，故DecorView的间接父类 = ViewGroup
  */
  public boolean onTouchEvent(MotionEvent event) {

        // 当一个点击事件未被Activity下任何一个View接收 / 处理时
        // 应用场景：处理发生在Window边界外的触摸事件
        // ->> 分析5
        if (mWindow.shouldCloseOnTouch(this, event)) {
            finish();
            return true;
        }
        
        return false;
        // 即 只有在点击事件在Window边界外才会返回true，一般情况都返回false，分析完毕
    }

/**
  * 分析5：mWindow.shouldCloseOnTouch(this, event)
  */
    public boolean shouldCloseOnTouch(Context context, MotionEvent event) {
    // 主要是对于处理边界外点击事件的判断：是否是DOWN事件，event的坐标是否在边界内等
    if (mCloseOnTouchOutside && event.getAction() == MotionEvent.ACTION_DOWN
            && isOutOfBounds(context, event) && peekDecorView() != null) {
        return true;
    }
    return false;
    // 返回true：说明事件在边界外，即 消费事件
    // 返回false：未消费（默认）
}
// 回到分析4调用原处

```
* 总结
当一个点击事件发生时，从Activity的事件分发开始
![image](https://upload-images.jianshu.io/upload_images/944365-f8fda76bbdad7b96.png?imageMogr2/auto-orient/)

![image](https://upload-images.jianshu.io/upload_images/944365-e186b0edcb590546.png?imageMogr2/auto-orient/)

那么，ViewGroup的dispatchTouchEvent()什么时候返回true / false？请继续往下看

### 2.2 ViewGroup事件的分发机制
从上面Activity事件分发机制可知，ViewGroup事件分发机制从dispatchTouchEvent()开始.

* 源码分析（5.0之前版本，原理类似）

```
/**
  * 源码分析：ViewGroup.dispatchTouchEvent（）
  */ 
    public boolean dispatchTouchEvent(MotionEvent ev) { 

    ... // 仅贴出关键代码

        // 重点分析1：ViewGroup每次事件分发时，都需调用onInterceptTouchEvent()询问是否拦截事件
            if (disallowIntercept || !onInterceptTouchEvent(ev)) {  

            // 判断值1：disallowIntercept = 是否禁用事件拦截的功能(默认是false)，可通过调用requestDisallowInterceptTouchEvent（）修改
            // 判断值2： !onInterceptTouchEvent(ev) = 对onInterceptTouchEvent()返回值取反
                    // a. 若在onInterceptTouchEvent()中返回false（即不拦截事件），就会让第二个值为true，从而进入到条件判断的内部
                    // b. 若在onInterceptTouchEvent()中返回true（即拦截事件），就会让第二个值为false，从而跳出了这个条件判断
                    // c. 关于onInterceptTouchEvent() ->>分析1

                ev.setAction(MotionEvent.ACTION_DOWN);  
                final int scrolledXInt = (int) scrolledXFloat;  
                final int scrolledYInt = (int) scrolledYFloat;  
                final View[] children = mChildren;  
                final int count = mChildrenCount;  

        // 重点分析2
            // 通过for循环，遍历了当前ViewGroup下的所有子View
            for (int i = count - 1; i >= 0; i--) {  
                final View child = children[i];  
                if ((child.mViewFlags & VISIBILITY_MASK) == VISIBLE  
                        || child.getAnimation() != null) {  
                    child.getHitRect(frame);  

                    // 判断当前遍历的View是不是正在点击的View，从而找到当前被点击的View
                    // 若是，则进入条件判断内部
                    if (frame.contains(scrolledXInt, scrolledYInt)) {  
                        final float xc = scrolledXFloat - child.mLeft;  
                        final float yc = scrolledYFloat - child.mTop;  
                        ev.setLocation(xc, yc);  
                        child.mPrivateFlags &= ~CANCEL_NEXT_UP_EVENT;  

                        // 条件判断的内部调用了该View的dispatchTouchEvent()
                        // 即 实现了点击事件从ViewGroup到子View的传递（具体请看下面的View事件分发机制）
                        if (child.dispatchTouchEvent(ev))  { 

                        mMotionTarget = child;  
                        return true; 
                        // 调用子View的dispatchTouchEvent后是有返回值的
                        // 若该控件可点击，那么点击时，dispatchTouchEvent的返回值必定是true，因此会导致条件判断成立
                        // 于是给ViewGroup的dispatchTouchEvent（）直接返回了true，即直接跳出
                        // 即把ViewGroup的点击事件拦截掉

                                }  
                            }  
                        }  
                    }  
                }  
            }  
            boolean isUpOrCancel = (action == MotionEvent.ACTION_UP) ||  
                    (action == MotionEvent.ACTION_CANCEL);  
            if (isUpOrCancel) {  
                mGroupFlags &= ~FLAG_DISALLOW_INTERCEPT;  
            }  
            final View target = mMotionTarget;  

        // 重点分析3
        // 若点击的是空白处（即无任何View接收事件） / 拦截事件（手动复写onInterceptTouchEvent（），从而让其返回true）
        if (target == null) {  
            ev.setLocation(xf, yf);  
            if ((mPrivateFlags & CANCEL_NEXT_UP_EVENT) != 0) {  
                ev.setAction(MotionEvent.ACTION_CANCEL);  
                mPrivateFlags &= ~CANCEL_NEXT_UP_EVENT;  
            }  
            
            return super.dispatchTouchEvent(ev);
            // 调用ViewGroup父类的dispatchTouchEvent()，即View.dispatchTouchEvent()
            // 因此会执行ViewGroup的onTouch() ->> onTouchEvent() ->> performClick（） ->> onClick()，即自己处理该事件，事件不会往下传递（具体请参考View事件的分发机制中的View.dispatchTouchEvent（））
            // 此处需与上面区别：子View的dispatchTouchEvent（）
        } 

        ... 

}

```

```
/**
  * 分析1：ViewGroup.onInterceptTouchEvent()
  * 作用：是否拦截事件
  * 说明：
  *     a. 返回true = 拦截，即事件停止往下传递（需手动设置，即复写onInterceptTouchEvent（），从而让其返回true）
  *     b. 返回false = 不拦截（默认）
  */
  public boolean onInterceptTouchEvent(MotionEvent ev) {  
    
    return false;

  } 
  // 回到调用原处

```
* 总结：结论：Android事件分发总是先传递到ViewGroup、再传递到View
![image](https://upload-images.jianshu.io/upload_images/944365-6ec2e864af7ffd37.png?imageMogr2/auto-orient/)

### 2.3 View事件分发机制
上面ViewGroup事件分发机制知道，View事件分发机制从dispatchTouchEvent()开始

* 源码分析
```
/**
  * 源码分析：View.dispatchTouchEvent（）
  */
  public boolean dispatchTouchEvent(MotionEvent event) {  

        if (mOnTouchListener != null && (mViewFlags & ENABLED_MASK) == ENABLED &&  
                mOnTouchListener.onTouch(this, event)) {  
            return true;  
        } 
        return onTouchEvent(event);  
  }
  // 说明：只有以下3个条件都为真，dispatchTouchEvent()才返回true；否则执行onTouchEvent()
  //     1. mOnTouchListener != null
  //     2. (mViewFlags & ENABLED_MASK) == ENABLED
  //     3. mOnTouchListener.onTouch(this, event)
  // 下面对这3个条件逐个分析


/**
  * 条件1：mOnTouchListener != null
  * 说明：mOnTouchListener变量在View.setOnTouchListener（）方法里赋值
  */
  public void setOnTouchListener(OnTouchListener l) { 

    mOnTouchListener = l;  
    // 即只要我们给控件注册了Touch事件，mOnTouchListener就一定被赋值（不为空）
        
} 

/**
  * 条件2：(mViewFlags & ENABLED_MASK) == ENABLED
  * 说明：
  *     a. 该条件是判断当前点击的控件是否enable
  *     b. 由于很多View默认enable，故该条件恒定为true
  */

/**
  * 条件3：mOnTouchListener.onTouch(this, event)
  * 说明：即 回调控件注册Touch事件时的onTouch（）；需手动复写设置，具体如下（以按钮Button为例）
  */
    button.setOnTouchListener(new OnTouchListener() {  
        @Override  
        public boolean onTouch(View v, MotionEvent event) {  
     
            return false;  
        }  
    });
    // 若在onTouch（）返回true，就会让上述三个条件全部成立，从而使得View.dispatchTouchEvent（）直接返回true，事件分发结束
    // 若在onTouch（）返回false，就会使得上述三个条件不全部成立，从而使得View.dispatchTouchEvent（）中跳出If，执行onTouchEvent(event)

```
继续看：onTouchEvent(event)的源码分析
```
/**
  * 源码分析：View.onTouchEvent（）
  */
  public boolean onTouchEvent(MotionEvent event) {  
    final int viewFlags = mViewFlags;  

    if ((viewFlags & ENABLED_MASK) == DISABLED) {  
         
        return (((viewFlags & CLICKABLE) == CLICKABLE ||  
                (viewFlags & LONG_CLICKABLE) == LONG_CLICKABLE));  
    }  
    if (mTouchDelegate != null) {  
        if (mTouchDelegate.onTouchEvent(event)) {  
            return true;  
        }  
    }  

    // 若该控件可点击，则进入switch判断中
    if (((viewFlags & CLICKABLE) == CLICKABLE ||  
            (viewFlags & LONG_CLICKABLE) == LONG_CLICKABLE)) {  

                switch (event.getAction()) { 

                    // a. 若当前的事件 = 抬起View（主要分析）
                    case MotionEvent.ACTION_UP:  
                        boolean prepressed = (mPrivateFlags & PREPRESSED) != 0;  

                            ...// 经过种种判断，此处省略

                            // 执行performClick() ->>分析1
                            performClick();  
                            break;  

                    // b. 若当前的事件 = 按下View
                    case MotionEvent.ACTION_DOWN:  
                        if (mPendingCheckForTap == null) {  
                            mPendingCheckForTap = new CheckForTap();  
                        }  
                        mPrivateFlags |= PREPRESSED;  
                        mHasPerformedLongPress = false;  
                        postDelayed(mPendingCheckForTap, ViewConfiguration.getTapTimeout());  
                        break;  

                    // c. 若当前的事件 = 结束事件（非人为原因）
                    case MotionEvent.ACTION_CANCEL:  
                        mPrivateFlags &= ~PRESSED;  
                        refreshDrawableState();  
                        removeTapCallback();  
                        break;

                    // d. 若当前的事件 = 滑动View
                    case MotionEvent.ACTION_MOVE:  
                        final int x = (int) event.getX();  
                        final int y = (int) event.getY();  
        
                        int slop = mTouchSlop;  
                        if ((x < 0 - slop) || (x >= getWidth() + slop) ||  
                                (y < 0 - slop) || (y >= getHeight() + slop)) {  
                            // Outside button  
                            removeTapCallback();  
                            if ((mPrivateFlags & PRESSED) != 0) {  
                                // Remove any future long press/tap checks  
                                removeLongPressCallback();  
                                // Need to switch from pressed to not pressed  
                                mPrivateFlags &= ~PRESSED;  
                                refreshDrawableState();  
                            }  
                        }  
                        break;  
                }  
                // 若该控件可点击，就一定返回true
                return true;  
            }  
             // 若该控件不可点击，就一定返回false
            return false;  
        }

/**
  * 分析1：performClick（）
  */  
    public boolean performClick() {  

        if (mOnClickListener != null) {  
            playSoundEffect(SoundEffectConstants.CLICK);  
            mOnClickListener.onClick(this);  
            return true;  
            // 只要我们通过setOnClickListener（）为控件View注册1个点击事件
            // 那么就会给mOnClickListener变量赋值（即不为空）
            // 则会往下回调onClick（） & performClick（）返回true
        }  
        return false;  
    }

```

*总结
![image](https://upload-images.jianshu.io/upload_images/944365-76ce9e8299386729.png?imageMogr2/auto-orient/)
注：onTouch（）的执行 先于  onClick（）

* demo验证

```
/**
  * 结论验证1：在回调onTouch()里返回false
  */
   // 1. 通过OnTouchListener()复写onTouch()，从而手动设置返回false
   button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("执行了onTouch(), 动作是:" + event.getAction());
           
                return false;
            }
        });

    // 2. 通过 OnClickListener（）为控件设置点击事件，为mOnClickListener变量赋值（即不为空），从而往下回调onClick（）
    button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("执行了onClick()");
            }

        });

/**
  * 结论验证2：在回调onTouch()里返回true
  */
   // 1. 通过OnTouchListener()复写onTouch()，从而手动设置返回true
   button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("执行了onTouch(), 动作是:" + event.getAction());
           
                return true;
            }
        });

    // 2. 通过 OnClickListener（）为控件设置点击事件，为mOnClickListener变量赋值（即不为空）
    // 但由于dispatchTouchEvent（）返回true，即事件不再向下传递，故不调用onClick()）
    button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("执行了onClick()");
            }
            
        });


```
### 2.4总结
![image](https://upload-images.jianshu.io/upload_images/944365-eeebede55f55b040.png?imageMogr2/auto-orient/)

## 3.工作流程
![image](https://upload-images.jianshu.io/upload_images/944365-aea821bbb613c195.png?imageMogr2/auto-orient/)

### 3.1三个方法图解

![image](https://upload-images.jianshu.io/upload_images/944365-9f340a39bdad520e.png?imageMogr2/auto-orient/)

### 3.2三者关系
下面，我用一段伪代码来阐述上述3个方法的关系 & 事件传递规则


```
/**
  * 点击事件产生后
  */ 
  // 步骤1：调用dispatchTouchEvent（）
  public boolean dispatchTouchEvent(MotionEvent ev) {

    boolean consume = false; //代表 是否会消费事件
    
    // 步骤2：判断是否拦截事件
    if (onInterceptTouchEvent(ev)) {
      // a. 若拦截，则将该事件交给当前View进行处理
      // 即调用onTouchEvent (）方法去处理点击事件
        consume = onTouchEvent (ev) ;

    } else {

      // b. 若不拦截，则将该事件传递到下层
      // 即 下层元素的dispatchTouchEvent（）就会被调用，重复上述过程
      // 直到点击事件被最终处理为止
      consume = child.dispatchTouchEvent (ev) ;
    }

    // 步骤3：最终返回通知 该事件是否被消费（接收 & 处理）
    return consume;

   }

作者：Carson_Ho
链接：https://www.jianshu.com/p/38015afcdb58
來源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```
## 4.常见的事件分发场景
### 4.1背景描述
讨论的布局如下：

![image](https://upload-images.jianshu.io/upload_images/944365-e0f526dd1b5731be.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/438)


```
情景
用户先触摸到屏幕上View C上的某个点（图中黄区）
Action_DOWN事件在此处产生
用户移动手指
最后离开屏幕
```
### 4.2一般的事件传递情况
一般的事件传递场景有：

- 默认情况
- 处理事件
- 拦截DOWN事件
- 拦截后续事件（MOVE、UP）
#### 4.2.1 默认情况

```
即不对控件里的方法（dispatchTouchEvent()、onTouchEvent()、onInterceptTouchEvent()）进行重写 或 更改返回值
那么调用的是这3个方法的默认实现：调用下层的方法 & 逐层返回
事件传递情况：（呈U型）
从上往下调用dispatchTouchEvent()
Activity A ->> ViewGroup B ->> View C
从下往上调用onTouchEvent()
View C ->> ViewGroup B ->> Activity A

```
![image](https://upload-images.jianshu.io/upload_images/944365-161a6e6fc8723248.png?imageMogr2/auto-orient/)

注：虽然ViewGroup B的onInterceptTouchEvent（）对DOWN事件返回了false，但后续的事件（MOVE、UP）依然会传递给它的onInterceptTouchEvent()
这一点与onTouchEvent（）的行为是不一样的：不再传递 & 接收该事件列的其他事件

#### 4.2.2 处理事件

```
设View C希望处理该点击事件，即：设置View C为可点击的（Clickable） 或 复写其onTouchEvent（）返回true

最常见的：设置Button按钮来响应点击事件
事件传递情况：（如下图）

DOWN事件被传递给C的onTouchEvent方法，该方法返回true，表示处理该事件
因为View C正在处理该事件，那么DOWN事件将不再往上传递给ViewGroup B 和 Activity A的onTouchEvent()；
该事件列的其他事件（Move、Up）也将传递给View C的onTouchEvent()

```

![image](https://upload-images.jianshu.io/upload_images/944365-77e933eb44682777.png?imageMogr2/auto-orient/)

#### 4.2.3 拦截DOWN事件
假设ViewGroup B希望处理该点击事件，即ViewGroup B复写了onInterceptTouchEvent()返回true、onTouchEvent()返回true

事件传递情况：（如下图）

- DOWN事件被传递给ViewGroup B的onInterceptTouchEvent()，该方法返回true，表示拦截该事件，即自己处理该事件（事件不再往下传递）

- 调用自身的onTouchEvent()处理事件（DOWN事件将不再往上传递给Activity A的onTouchEvent()）

- 该事件列的其他事件（Move、Up）将直接传递给ViewGroup B的onTouchEvent()

```
注：

该事件列的其他事件（Move、Up）将不会再传递给ViewGroup B的onInterceptTouchEvent（）；因：该方法一旦返回一次true，就再也不会被调用
逐层往dispatchTouchEvent() 返回，最终事件分发结束
```
![image](https://upload-images.jianshu.io/upload_images/944365-a5e7cfed2cba02c3.png?imageMogr2/auto-orient/)

#### 4.2.4 拦截DOWN的后续事件
- 若 ViewGroup 拦截了一个半路的事件（如MOVE），该事件将会被系统变成一个CANCEL事件 & 传递给之前处理该事件的子View；
- 该事件不会再传递给ViewGroup 的onTouchEvent()
- 只有再到来的事件才会传递到ViewGroup的onTouchEvent()

场景描述
ViewGroup B 无拦截DOWN事件（还是View C来处理DOWN事件），但它拦截了接下来的MOVE事件

即 DOWN事件传递到View C的onTouchEvent（），返回了true

实例讲解

- 在后续到来的MOVE事件，ViewGroup B 的onInterceptTouchEvent（）返回true拦截该MOVE事件，但该事件并没有传递给ViewGroup B ；这个MOVE事件将会被系统变成一个CANCEL事件传递给View C的onTouchEvent（）
- 后续又来了一个MOVE事件，该MOVE事件才会直接传递给ViewGroup B 的onTouchEvent()
- 后续事件将直接传递给ViewGroup B 的onTouchEvent()处理，而不会再传递给ViewGroup B 的onInterceptTouchEvent（），因该方法一旦返回一次true，就再也不会被调用了。

![image](https://upload-images.jianshu.io/upload_images/944365-1599f532038686cd.png?imageMogr2/auto-orient/)

## 5.关于MOVE和UP的传递
- 若给控件注册了Touch事件，每次点击都会触发一系列action事件（ACTION_DOWN，ACTION_MOVE，ACTION_UP等）
- 当dispatchTouchEvent（）事件分发时，只有前一个事件（如ACTION_DOWN）返回true，才会收到后一个事件（ACTION_MOVE和ACTION_UP）

即如果在执行ACTION_DOWN时返回false，后面一系列的ACTION_MOVE、ACTION_UP事件都不会执行

从上面对事件分发机制分析知：

- dispatchTouchEvent()、 onTouchEvent() 消费事件、终结事件传递（返回true）
- 而onInterceptTouchEvent 并不能消费事件，它相当于是一个分叉口起到分流导流的作用，对后续的ACTION_MOVE和ACTION_UP事件接收起到非常大的作用

请记住：接收了ACTION_DOWN事件的函数不一定能收到后续事件（ACTION_MOVE、ACTION_UP）

### 5.1结论一
若对象（Activity、ViewGroup、View）的dispatchTouchEvent()分发事件后消费了事件（返回true），那么收到ACTION_DOWN的函数也能收到ACTION_MOVE和ACTION_UP

- 黑线：ACTION_DOWN事件传递方向
- 红线：ACTION_MOVE 、 ACTION_UP事件传递方向


![image](https://upload-images.jianshu.io/upload_images/944365-93d0b1496e9e6ca4.png?imageMogr2/auto-orient/)

### 5.2结论2

若对象（Activity、ViewGroup、View）的onTouchEvent()处理了事件（返回true），那么ACTION_MOVE、ACTION_UP的事件从上往下传到该View后就不再往下传递，而是直接传给自己的onTouchEvent()& 结束本次事件传递过程。

- 黑线：ACTION_DOWN事件传递方向
- 红线：ACTION_MOVE、ACTION_UP事件传递方向

![image](https://upload-images.jianshu.io/upload_images/944365-9d639a0b9ebf7b4a.png?imageMogr2/auto-orient/)

### 5.3补充
一下子画了好多图，还有好几种情况就不再画了，相信你也看出规律了，对于在onTouchEvent消费事件的情况：在哪个View的onTouchEvent 返回true，那么ACTION_MOVE和ACTION_UP的事件从上往下传到这个View后就不再往下传递了，而直接传给自己的onTouchEvent 并结束本次事件传递过程。

对于ACTION_MOVE、ACTION_UP总结：ACTION_DOWN事件在哪个控件消费了（return true）， 那么ACTION_MOVE和ACTION_UP就会从上往下（通过dispatchTouchEvent）做事件分发往下传，就只会传到这个控件，不会继续往下传，如果ACTION_DOWN事件是在dispatchTouchEvent消费，那么事件到此为止停止传递，如果ACTION_DOWN事件是在onTouchEvent消费的，那么会把ACTION_MOVE或ACTION_UP事件传给该控件的onTouchEvent处理并结束传递。

## 6.onTouch()和onTouchEvent()的区别
- 该2个方法都是在View.dispatchTouchEvent（）中调用
- 但onTouch（）优先于onTouchEvent执行；若手动复写在onTouch（）中返回true（即 将事件消费掉），将不会再执行onTouchEvent（）

注：若1个控件不可点击（即非enable），那么给它注册onTouch事件将永远得不到执行，具体原因看如下代码


```
// &&为短路与，即如果前面条件为false，将不再往下执行
//  故：onTouch（）能够得到执行需2个前提条件：
     // 1. mOnTouchListener的值不能为空
     // 2. 当前点击的控件必须是enable的
mOnTouchListener != null && (mViewFlags & ENABLED_MASK) == ENABLED &&  
            mOnTouchListener.onTouch(this, event)

// 对于该类控件，若需监听它的touch事件，就必须通过在该控件中重写onTouchEvent（）来实现

```


# 2.事件的传递方式，View，ViewGroup
参考上一题


# 3.down、move、up事件的传递
参考上一题

