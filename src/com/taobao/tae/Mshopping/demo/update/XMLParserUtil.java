package com.taobao.tae.Mshopping.demo.update;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p></p>
 * User: <a href="mailto:xinyuan.ymm@alibaba-inc.com">心远</a>
 * Date: 14/7/24
 * Time: 下午4:51
 */
public class XMLParserUtil {
    /**
     * 获取版本更新信息
     *
     * @param is
     * 读取连接服务version.xml文档的输入流
     * @return
     */
    public static VersionInfo getUpdateInfo(InputStream is) {
        VersionInfo info = new VersionInfo();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("version".equals(parser.getName())) {
                            info.setVersion(parser.nextText());
                        } else if ("updateTime".equals(parser.getName())) {
                            info.setUpdateTime(parser.nextText());
                        } else if ("updateTime".equals(parser.getName())) {
                            info.setUpdateTime(parser.nextText());
                        } else if ("downloadURL".equals(parser.getName())) {
                            info.setDownloadURL(parser.nextText());
                        } else if ("displayMessage".equals(parser.getName())) {
                            info.setDisplayMessage(parseTxtFormat(parser.nextText(), "##"));
                        } else if ("apkName".equals(parser.getName())) {
                            info.setApkName(parser.nextText());
                        } else if ("versionCode".equals(parser.getName())) {
                            info.setVersionCode(Integer.parseInt(parser.nextText()));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 根据指定字符格式化字符串（换行）
     *
     * @param data
     *            需要格式化的字符串
     * @param formatChar
     *            指定格式化字符
     * @return
     */
    public static String parseTxtFormat(String data, String formatChar) {
        StringBuffer backData = new StringBuffer();
        String[] txts = data.split(formatChar);
        for (int i = 0; i < txts.length; i++) {
            backData.append(txts[i]);
            backData.append("\n");
        }
        return backData.toString();
    }
}
