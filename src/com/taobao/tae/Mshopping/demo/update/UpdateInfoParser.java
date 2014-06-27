package com.taobao.tae.Mshopping.demo.update;

import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;


//解析xml
public class UpdateInfoParser {
	
	public static UpdateInfo getUpdateInfo(InputStream is) throws Exception {
		//建立一个info实例容器
		UpdateInfo info = new UpdateInfo();
		//解析一个输入流
		XmlPullParser xmlPullParser = Xml.newPullParser();
		xmlPullParser.setInput(is, "utf-8");
		int type = xmlPullParser.getEventType();
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_TAG:
				//取出version tag
				if (xmlPullParser.getName().equals("version")) {
					info.setVersion(xmlPullParser.nextText());
				} else if (xmlPullParser.getName().equals("description")) {
					info.setDescription(xmlPullParser.nextText());
				} else if (xmlPullParser.getName().equals("apkurl")) {
					info.setUrl(xmlPullParser.nextText());
				}
				break;

			default:
				break;
			}
			type = xmlPullParser.next();
		}
		return info;
	}

}
