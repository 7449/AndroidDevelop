
## AndroidDevelop

`Android`开发`Demo`集合

## accessibility-service

利用残疾人模式自动安装`apk`，只适用于原生系统,如果使用`miui`或者`flyme`需要修改检测的包名

## aosp

> idegen.jar

编译`AOSP`源码生成的`jar`，如果想把源码导入`AndroidStudio`或者`IDEA`，有了这个`jar`，就不必需要花费近乎一个小时的时间去重新编译源码，而可以直接导入`IDE`

请把这个`jar`包放在 `out` --> `host` --> `darwin-x86` --> `framework`,然后执行`development/tools/idegen/idegen.sh`生成`android.ipr` and `android.imi`,用于导入`IDE`

如果没有编译过源码是没有 `out` 目录的，请一层一层的新建文件夹，直到新建到`framework`，放进去就OK

`out` 目录与 `.repo` 平级

至于如何编译`AOSP`源码,请查看这篇`Blog` ： [Android_编译Android源码并使用AS查看源码](https://7449.github.io/2017/02/10/Android_compile_aosp/)

## app-framework

开发一个简单的`app`需要的一些封装控件

## banner

首页`banner`封装

单独项目地址[BannerLayout](https://github.com/7449/BannerLayout)

[Blog](https://7449.github.io/2016/09/28/Android_AdBannerPackage/)

## bilibili-recommend-ui

`BiliBili`推荐页面，`Toolbar`搜索动画

## camera

`google`开源的[cameraview](https://github.com/google/cameraview)

[Album](https://github.com/7449/Album)

## collection

以前开发过程中封装的一些小控件

## cordova-plugin-network

* cordova 插件示例，调用方法：
* 使用时需要自行打印log去测试，java文件中什么都没有做


		<!DOCTYPE html>
		<html>
		<head>
		    <meta charset="utf-8">
		    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		    <title></title>
		    <meta name="description" content="">
		    <meta name="viewport" content="width=device-width">
		    <link rel="stylesheet" type="text/css" href="css/app.css"/>
		    <script>
				document.addEventListener("deviceready", init, false);
				        function init() {
				            function onSuccess(message) {
				                var options = {
				                    onError: function() {
				                        alert('ERROR');
				                    }
				                };
				            }
				
				            function onFail(message) {
				                alert('Failed because: ' + message);
				            }
				
				            document.querySelector("#network").addEventListener("touchend", function() {
				                navigator.network.start(onSuccess, onFail, {
				                		networkType: Network.NetWorkType.LOGIN,
				                });
				            });
				}
		    </script>
		</head>
		<body>
		<button id="network">插件示例</button>
		
		<script src="cordova.js"></script>
		</body>
		</html>
		
		
## dagger

使用[dagger](https://github.com/google/dagger)

## dottedline-animation

别人写的模仿知乎动态登录背景

原地址：[DottedLineAnimation](https://github.com/wuyongxiang/DottedLineAnimation)

## expandable-list

类似功能：qq好友分组

[Blog](https://7449.github.io/2017/01/01/Adnroid_expandablelist/)

## finger

指纹识别的Demo

[Blog](https://7449.github.io/2016/11/28/Android_finger/)

## fragment-lazy

懒加载`fragment`

[Blog](https://7449.github.io/2016/10/15/Android_LazyFragment/)

## fragment-tab

`FragmeLayout`切换`Fragment`

[Blog](https://7449.github.io/2016/10/05/Android_TabFragment/)

## fragment-viewpager

`ViewPagerFragment`滑动带有光标

[Blog](https://7449.github.io/2016/10/05/Android_ViewPager_Fragment/)

## fuckapp

通过`root`权限彻底隐藏系统垃圾应用

[Blog](https://7449.github.io/2017/01/03/Android_hideApp/)

## greendao

`greenDao2.X`版本使用示例

[greenDao增删改查](https://7449.github.io/2016/10/08/Android_greenDaoCRUD/)

## greendao-3.0

`greenDao3.X`版本使用示例

[greenDao增删改查](https://7449.github.io/2016/10/08/Android_greenDaoCRUD/)

## greendao-external

利用`greenDao`操作第三方数据表

[greenDao增删改查](https://7449.github.io/2016/10/08/Android_greenDaoCRUD/)

## greendao-multi-table

`greenDao`多表关联

[greenDao增删改查](https://7449.github.io/2016/10/08/Android_greenDaoCRUD/)

## image-select

简单的图片选择器

[Album](https://github.com/7449/Album)

## linktop

`CoordinatorLayout`的一种使用方法

## objectbox

`ObjectBox`数据库

[Blog](https://7449.github.io/2017/09/28/Android_objectBox/)

## objectbox-multi-table

`ObjectBox`数据库多表关联

[Blog](https://7449.github.io/2017/09/28/Android_objectBox/)

## react-native-realm

`react-native`中的`realm`数据库使用示例

[Blog](https://7449.github.io/2018/01/15/Android_react_native_realm/)

## rvfilter

`RecyclerView`写的高级筛选

![](https://github.com/7449/AndroidDevelop/blob/develop/rvfilter/rv_filter.gif)

## save-image

保存图片在本地

[Blog](https://7449.github.io/2016/11/21/Android_SaveImageView/)

## scan-app

![](http://i.imgur.com/bpP2vWA.gif)

## scan-wifi

扫描局域网的所有设备

## shortcuts

7.0新特性小图标

## splash

`splash`的另一种实现方式

## super-adapter

`RecyclerViewBaseAdapter`

[XAdapter](https://github.com/7449/XAdapter)

## test-js

`js`与`android`互调

## test-statusbar

高版本状态栏适配测试

[Blog](https://7449.github.io/2017/05/15/Android_statusbar/)

## test-tinker

`Tinker`的一个小`Demo`

[Blog](https://7449.github.io/2016/11/04/Android_tinker/)

## toolbar-example

`toolbar`简单示例

## view-number-picker

选择器

## view-refresh

下拉刷新

Demo修改自[Phoenix](https://github.com/Yalantis/Phoenix)

## view-wheel

省市县三级联动

[Blog](https://7449.github.io/2016/10/26/Android_Citylinkage/)

## wall

 java下载图片的简单Demo