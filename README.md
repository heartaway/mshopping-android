Android Demo 使用文档
=================
## 背景和目的：

TAE Android Demo 主要是为开发者提供一套TAE在无线端开放的代码实例，主要完成使用Native的方式实现淘宝商品的选择、购买和支付等交易链路环节，解决了之前导购类App只能通过H5的方式跳转到淘宝无线端的H5页面，打通了在一个App中购买商品的整个流程，让用户的购物体验更加完整和流程；其次，TAE作为阿里巴巴无线开放平台，还为开发者提供了更丰富的数据接口。此Android Demo基于开放的原则，开放出源代码方便开发者参考使用。

## Demo下载与试用：
手机扫码下载

![二维码](https://raw.githubusercontent.com/heartaway/gitimagerepo/master/mshoping-android/apk/1.0.0/tae-android-demo.png)

点击下载：
[APK下载](https://raw.github.com/heartaway/gitimagerepo/master/mshoping-android/apk/1.0.0/mshopping-android.apk)

## Demo设计说明：
1. 服务端设计
基于TAE开放的商品获取接口进行商品的手工录入和推送，目前可以添加淘宝和天猫的大部分商品，后续可能会开放处基于全站的智能化选品组件，方便独立开发者快捷高效地从海量商品中选择出更优质的商品。目前服务端托管在TAE平台之上。
2. 客户端设计
客户端整体实现基本全部采用Native的实现方式，包括商品列表展示、商品详情展示、商品购买SKU选择、商品订单创建与确认等等。

### 商品列表页
* 通过两栏纵向布局和三栏横向布局，展示出更丰富的商品：
![商品首页](https://raw.githubusercontent.com/heartaway/gitimagerepo/master/mshoping-android/index.png)

### 商品详情页
![商品详情](https://raw.githubusercontent.com/heartaway/gitimagerepo/master/mshoping-android/detail.png)

### 商品SKU选择页
![商品SKU选择](https://raw.githubusercontent.com/heartaway/gitimagerepo/master/mshoping-android/sku.png)

### 用户购买后确认订单页
![订单确认](https://raw.githubusercontent.com/heartaway/gitimagerepo/master/mshoping-android/order.png)

### 支付宝支付页
![订单支付](https://raw.githubusercontent.com/heartaway/gitimagerepo/master/mshoping-android/pay.png)

### 淘宝OAuth授权登录页
![买家登录](https://raw.githubusercontent.com/heartaway/gitimagerepo/master/mshoping-android/login.png)

### 淘宝授权页
![买家授权](https://raw.githubusercontent.com/heartaway/gitimagerepo/master/mshoping-android/oauth.png)

## 源代码使用说明
#### 修改配置项
如果期望本Demo代码能够在本地正常运行，需要首先在TAE平台申请开发者无线开放权限，平台会颁发给你一个appkey和secret，然后修改
 / src / com / taobao / tae / Mshopping / demo / constant / Constants.java 文件中 APP_KEY 和 APP_SECRET 参数。

