package com.lok.camfly;

import java.util.ArrayList;
import java.util.HashMap;

import com.lok.camfly.R;
import com.lok.obj.CamCarInfo;
import com.lok.utils.CamFlyScanner;
import com.lok.utils.NetworkConstant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CamFlyList extends Activity {
	private ListView camcarList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.camcarlist);
		init();
	}

	private void init() {
		CamFlyScanner.setContext(this);
		CamFlyScanner.scan();

		camcarList = (ListView) findViewById(R.id.camcarmenu_listmenu);

		NetworkConstant.camcarsVector.clear();

		CamFlyScanner.scan();

		CamCarInfo tempCamcatInfo = null;
		ArrayList<HashMap<String, Object>> tempList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < NetworkConstant.camcarsVector.size(); i++) {
			HashMap<String, Object> tempMap = new HashMap<String, Object>();
			tempCamcatInfo = NetworkConstant.camcarsVector.get(i);
			tempMap.put("name", tempCamcatInfo.NickName);
			tempMap.put(
					"info",
					"IP: " + tempCamcatInfo.Ip + " Port: "
							+ String.valueOf(tempCamcatInfo.ServPort));
			tempList.add(tempMap);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, tempList,
				R.layout.camcarmenu_listitem, new String[] { "name", "info" },
				new int[] { R.id.menulist_item_name, R.id.menulist_item_info });
		if (camcarList == null) {
			Log.i("fasdfsdfsdfas", "fjsidjfdjsfiadsiof");
		}
		camcarList.setAdapter(adapter);
		camcarList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int itemId,
					long arg3) {
				CamCarInfo tempCamcatInfo = NetworkConstant.camcarsVector
						.get(itemId);
				NetworkConstant.WICAM_IP = tempCamcatInfo.Ip;
				NetworkConstant.WICAM_CONTROL_PORT = tempCamcatInfo.ServPort;
				Toast.makeText(CamFlyList.this,
						"已选定对象：" + NetworkConstant.WICAM_IP, Toast.LENGTH_SHORT)
						.show();
				Intent intent = new Intent();
				intent.setClass(CamFlyList.this, CamFlyCtrl.class);
				CamFlyList.this.startActivity(intent);
				CamFlyList.this.finish();
			}

		});
		if (NetworkConstant.camcarsVector.isEmpty()) {
			Intent intent = new Intent();
			intent.setClass(CamFlyList.this, CamFlyCtrl.class);
			CamFlyList.this.startActivity(intent);
			CamFlyList.this.finish();
		}
	}
}
