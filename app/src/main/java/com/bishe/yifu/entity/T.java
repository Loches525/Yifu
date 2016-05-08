package com.bishe.yifu.entity;

import android.content.Context;
import android.widget.Toast;

public class T {

	/**
	 * 显示提示消息
	 */
	public static void Show(Context context, String content) {
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}

}
