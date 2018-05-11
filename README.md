
## AndroidDevelop

Android开发Demo集合


## accessibility-service


## aosp

> idegen.jar

编译`AOSP`源码生成的jar，如果想把源码导入`AndroidStudio`或者`IDEA`，有了这个jar，就不必需要花费近乎一个小时的时间去重新编译源码，而可以直接导入IDE

请把这个jar包放在 `out` --> `host` --> `darwin-x86` --> `framework`,然后执行`development/tools/idegen/idegen.sh`生成`android.ipr` and `android.imi`,用于导入IDE

如果没有编译过源码是没有 `out` 目录的，请一层一层的新建文件夹，直到新建到`framework`，放进去就OK

`out` 目录与 `.repo` 平级

至于如何编译AOSP源码,请查看这篇Blog ： [Android_编译Android源码并使用AS查看源码](https://7449.github.io/2017/02/10/Android_compile_aosp/)

## app-framework


## banner


## bilibili-recommend-ui


## camera


## collection


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


## dottedline-animation


## example


## expandable-list


## finger


## fragment-lazy


## fragment-tab


## fragment-viewpager


## fuckapp


## greendao


## greendao-3.0


## greendao-external


## greendao-multi-table


## image-select


## linktop


## objectbox


## objectbox-multi-table


## react-native-realm


## rvfilter


## save-image


## scan-app

![](http://i.imgur.com/bpP2vWA.gif)

## scan-wifi


## shortcuts


## splash


## super-adapter


## test-js


## test-statusbar


## test-tinker


## toolbar-example


## view-number-picker


## view-refresh


## view-wheel


## wall

 java下载图片的简单Demo