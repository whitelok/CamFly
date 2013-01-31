package com.lok.camfly;

import java.util.ArrayList;
import java.util.HashMap;

import com.lok.camfly.setting.ControlRangeSetting;
import com.lok.camfly.setting.ParamSetting;
import com.lok.utils.NetworkConstant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CamFlySetting extends Activity {
	private String TAG = "CamFlySetting";

	private ListView settingList = null;

	private Intent intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.camflysetting);
		init();
	}

	private void init() {
		this.settingList = (ListView) findViewById(R.id.camflysettinglist);
		ArrayList<HashMap<String, Object>> tempList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("paramName", "水平校准");
		tempList.add(tempMap);

		tempMap = new HashMap<String, Object>();
		tempMap.put("paramName", "罗盘校准");
		tempList.add(tempMap);

		tempMap = new HashMap<String, Object>();
		tempMap.put("paramName", "行程设置");
		tempList.add(tempMap);

		tempMap = new HashMap<String, Object>();
		tempMap.put("paramName", "按键设置");
		tempList.add(tempMap);

		tempMap = new HashMap<String, Object>();
		tempMap.put("paramName", "返回控制台");
		tempList.add(tempMap);

		SimpleAdapter adapter = new SimpleAdapter(this, tempList,
				R.layout.camflysetting_listitem, new String[] { "paramName" },
				new int[] { R.id.settinglist_item_title });

		settingList.setAdapter(adapter);
		settingList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int itemId,
					long arg3) {
				switch (itemId) {
				case 0:
					if (NetworkConstant.communicator != null) {
						for (int i = 0; i < 50; i++) {
							NetworkConstant.cmdMessage.setByWing(1500);
							NetworkConstant.cmdMessage.setUpAndDown(1850);
							NetworkConstant.cmdMessage.setEnergy(1050);
							NetworkConstant.cmdMessage.setDirection(1850);
							NetworkConstant.communicator
									.send(NetworkConstant.cmdMessage);
						}
					} else {
						Toast.makeText(CamFlySetting.this, "请先连接目标CamFly",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case 1:
					if (NetworkConstant.communicator != null) {
						for (int i = 0; i < 50; i++) {
							NetworkConstant.cmdMessage.setByWing(1500);
							NetworkConstant.cmdMessage.setUpAndDown(1850);
							NetworkConstant.cmdMessage.setEnergy(1050);
							NetworkConstant.cmdMessage.setDirection(1050);
							NetworkConstant.communicator
									.send(NetworkConstant.cmdMessage);
						}
					} else {
						Toast.makeText(CamFlySetting.this, "请先连接目标CamFly",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case 2:
					intent = new Intent();
					intent.setClass(CamFlySetting.this,
							ControlRangeSetting.class);
					CamFlySetting.this.startActivityForResult(intent, 0);
					break;
				case 3:
					intent = new Intent();
					intent.setClass(CamFlySetting.this, ParamSetting.class);
					CamFlySetting.this.startActivityForResult(intent, 0);
					break;
				case 4:
					intent = new Intent();
					intent.setClass(CamFlySetting.this, CamFlyCtrl.class);
					CamFlySetting.this.startActivity(intent);
					CamFlySetting.this.finish();
					break;
				}

			}

		});
	}
}
