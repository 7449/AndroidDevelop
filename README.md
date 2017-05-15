# AndroidDevelop
Android开发Demo集合

> idegen.jar

编译`AOSP`源码生成的jar，如果想把源码导入`AndroidStudio`或者`IDEA`，有了这个jar，就不必需要花费近乎一个小时的时间去重新编译源码，而可以直接导入IDE

请把这个jar包放在 `out` --> `host` --> `darwin-x86` --> `framework`,然后执行`development/tools/idegen/idegen.sh`生成`android.ipr` and `android.imi`,用于导入IDE

如果没有编译过源码是没有 `out` 目录的，请一层一层的新建文件夹，直到新建到`framework`，放进去就OK

`out` 目录与 `.repo` 平级

至于如何编译AOSP源码,请查看这篇Blog ： [Android_编译Android源码并使用AS查看源码](https://7449.github.io/2017/02/10/Android_compile_aosp/)


> Rvfilter:RecyclerView写的高级筛选

![](https://github.com/7449/AndroidDevelop/blob/master/image/rv_filter.gif)


项目名称					|说明							 |博客地址  																					|单独项目地址
---    					|---   							 |---          																				|---
auxiliary				|一些简单工具类					 |无																							|无
banner					|首页banner封装					 |[首页banner封装](https://7449.github.io/2016/09/28/Android_AdBannerPackage/)				|改进版[BannerLayout](https://github.com/7449/BannerLayout)
biliRecommendUI			|Bili推荐页面，Toolbar搜索动画 	 |无 																						|无
camera			|试手google开源的[camera](https://github.com/google/cameraview)  |无 														|无
codeKK			|[codekk](http://p.codekk.com/)第三方客户端 |无 																			|无
expandableList	|类似功能：qq好友分组 				 |无 																						|无
finger				|指纹识别的Demo					 |[指纹识别测试](https://7449.github.io/2016/11/28/Android_finger/) 									|无
fuckApp					|通过root权限彻底隐藏系统垃圾应用 	 |[通过root权限彻底隐藏系统垃圾应用](https://7449.github.io/2017/01/03/Android_hideApp/) 																						|无
greenDao				|greenDao2.X版本使用示例 			 |[greenDao增删改查](https://7449.github.io/2016/10/08/Android_greenDaoCRUD/) 						|无
greendao_3.0			|greenDao3.X版本使用示例 			 |[greenDao增删改查](https://7449.github.io/2016/10/08/Android_greenDaoCRUD/) 						|无
greenDaoExternal     	|利用greenDao操作第三方数据表 		 |[greenDao增删改查](https://7449.github.io/2016/10/08/Android_greenDaoCRUD/) 						|无
jsoup 				|迁移至[https://github.com/7449/JsoupSample](https://github.com/7449/JsoupSample)	 |[Jsoup简单使用](https://7449.github.io/2016/10/31/Android_Jsoup/) 								|无
lazyFragment   			|Fragmentviewpager使用时懒加载   	 |[Fragment懒加载](https://7449.github.io/2016/10/15/Android_LazyFragment/) 								|[Retrofit_RxJava_MVP](https://github.com/7449/Retrofit_RxJava_MVP)
refreshLayout 			|Demo修改自[Yalantis/Phoenix](https://github.com/Yalantis/Phoenix) |无 														|无
saveImage				|保存图片在本地 					 |[ImageView保存本地](https://7449.github.io/2016/11/21/Android_SaveImageView/) 						|无
superAdapter			|RecyclerViewBaseAdapter		 |无 																						|[XAdapter](https://github.com/7449/XAdapter)
tabFragment				|FragmeLayout切换Fragment   		 |[FragmeLayout切换不同的Fragment](https://7449.github.io/2016/10/05/Android_TabFragment/) |无
tinker				|Tinker的一个小Demo   			 |[Tinker初次试用](https://7449.github.io/2016/11/04/Android_tinker/) 							|无
viewPagerFragment 		|ViewPagerFragment滑动带有光标	 |[ViewPager+Fragment滑动且带有光标](https://7449.github.io/2016/10/05/Android_ViewPager_Fragment/) |无
wheelView 		|省市县三级联动  					 |[省市县三级联动](https://7449.github.io/2016/10/26/Android_Citylinkage/) 								|无
slideView				|联系人侧栏快速索引 				 |[SlideView](https://7449.github.io/2016/10/07/Android_SlideView/) |[SlideView](https://github.com/7449/SlideView)
downloadProgressBar  	|下载进度展示 					 |[DownloadProgressBar](https://7449.github.io/2016/10/07/Android_DownloadProgressBar/) |[ProgressView](https://github.com/7449/ProgressView)
fractionView			|自定义view两个相反方向的嵌套转盘    |[FractionView](https://7449.github.io/2016/10/26/Android_FractionView/) |[FractionView](https://github.com/7449/FractionView)
bannerLayout			|最简单方式实现Banner				 |[BannerLayout](https://7449.github.io/2016/10/26/Android_BannerLayout/)  			|[BannerLayout](https://github.com/7449/BannerLayout)
lambda			|Android中使用Lambda				 |[Android_Lambda](https://7449.github.io/2017/02/08/Android_Lambda/)  			|[ZLSimple](https://github.com/7449/ZLSimple)
numberPickerVie			|自定义View				 |无  			|无
accessibility			|利用残疾人模式自动安装APP			 |无  			|无
imageSelect			|打开系统相机和图库选择照片,UCrop裁剪的简易图片选择器			 |无  			|无
shortcuts			|7.0新特性小图标			 |无  			|无
statusBarTest			|高版本状态栏适配测试			 |无  			|无
LinkTop			|用`Design`实现上拉悬停			 |无  			|无