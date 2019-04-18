- **孝信通 SDK 简介**

* `SDK 功能` - **为集成此 sdk 的 APP 提供调起 SOS 紧急呼救界面（被动）、地图展示求救者位置并与孝信通终端进行语音对讲通话的能力**
* `SDK 说明` - **目前此 sdk 仅支持 Android 系统**
* `SDK demo下载` - **[https://github.com/xiao-xin-com/XiaoxinSdkDemo](https://github.com/xiao-xin-com/XiaoxinSdkDemo)**

- **SDK 集成方式**

	- a) 由于 sdk 中用到了地图，而地图服务与 app 包名相关联，因此需要向百度地图申请 ak。
		申请地址：[http://lbsyun.baidu.com](http://lbsyun.baidu.com)
		申请到 ak 后，修改 `AndroidManifest.xml` 文件中的以下内容：`com.baidu.lbsapi.API_KEY`

	- b) 在 build.gradle 文件中添加以下内容：
		- 将其添加到存储库末尾的根build.gradle中        
		```groovy
			allprojects {
				repositories {
				...
				maven { url 'https://jitpack.io' }
				}
			}
		```
		- 添加依赖项
		```groovy
			dependencies {
				implementation 'com.gitlab.xiaoxin.xiaoxinsdk:sdk:1.1_rc12'
			}
		```
	- c) 向孝信通申请 appKey 与 privateKeyString
	- d) 在 APP 启动时（建议在 Application 的 onCreate 方法中）调用 sdk 的 init 方法：
		```java
		    Xiaoxin.init(this, appKey, privateKeyString, new OnCallXiaoxin() {
			@Override
			public void init() {
			    Log.d(TAG, "onSuccessXX() called inti");
			}
			@Override
			public void onSuccessXX(String data) {
			    Log.d(TAG, "onSuccessXX() called with: data = [" + data + "]");
			}
			@Override
			public void onErrorXX(Throwable throwable) {
			    Log.d(TAG, "onErrorXX() called with: throwable = [" + throwable + "]");
			}
			@Override
			public void onSuccessRG(String data) {
			    Log.d(TAG, "onSuccessRG() called with: data = [" + data + "]");
			}
			@Override
			public void onErrorRG(RongIMClient.ErrorCode errorCode) {
			    Log.d(TAG, "onErrorRG() called with: errorCode = [" + errorCode + "]");
			}
		    });
		```
			参数说明：
			------
			this                    Context
			appKey                  孝信通提供的 appKey
			privateKeyString        孝信通提供的 Private Key
			OnCallXiaoxin           init 回调方法
	- e) 在合适的时机（APP 的服务端收到孝信通转发的 SOS 信息后，由 APP 服务端通知 APP）调用 sdk 的 startSOSActivity 接口：
		```java
		SOSActivity.startSOSActivity(sosId)
		```
			参数说明：
			------
			sosId                   孝信通转发给 APP 服务端 SOS 信息的 sosId
	     关于 APP 服务端如何订阅孝信通设备的 SOS 信息，请参考 [订阅孝信通 SOS 报警信息](https://xxtserver.xiao-xin.com/apidocument/api_push_sos/)
