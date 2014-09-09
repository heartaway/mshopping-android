package com.taobao.tae.Mshopping.demo.constant;

public class Constants {

    public final static String OFFICIAL_URL = "http://gw.api.taobao.com/router/rest";

    /**
     * 淘宝开放平台的APP_KEY
     */
    public final static String TAOBAO_APP_KEY = "xxxxx";

    public final static String TAOBAO_APP_SECRET = "xxxxx";


    /**
     * 使用 腾讯开放平台注册的APP_ID
     */
    public final static String QQ_APP_ID = "222222";

    /**
     * 使用 新浪微博开放平台注册的APP_KEY
     */
    public final static String WEIBO_APP_KEY = "479089180";

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * <p/>
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String WEIBO_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * Scope 是 新浪微博 OAuth2.0 授权机制中 authorize 接口的一个参数。
     */
    public static final String WEIBO_SCOPE =
            "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog," + "invitation_write";


    /**
     * 用户操作行为标识
     */

    public final static int PULL_REFRESH_ACTION = 1; // 下拉刷新动作

    public final static int VIEW_MORE_ITEMS_ACTION = 2;//查看更多动作

    public final static int OTHER_ACTION = 3;//其它动作


    /**
     * 首页商品分类标识
     */

    public final static String NEW_CATEGORY = "1"; //最新

    public final static String SELECT_CATEGORY = "2";//精选

    public final static String FASHION_CATEGORY = "3";//潮流

}
