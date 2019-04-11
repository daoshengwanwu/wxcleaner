package com.example.wechat_clean;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView)findViewById(R.id.text);
		String text;
		text = "微信清理服务操作流程：\n\n"+"设置->辅助服务->微信清理服务->开启";
		tv.setText(text);
	}
}
