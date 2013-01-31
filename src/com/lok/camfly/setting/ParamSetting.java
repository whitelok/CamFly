package com.lok.camfly.setting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lok.camfly.R;
import com.lok.utils.AppConstant;

public class ParamSetting extends Activity {

	private RadioGroup choiseGroup = null;

	private String TAG = "ParamSetting";

	private OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {

		public void onCheckedChanged(RadioGroup group, int checkedId) {

			int byWingInt = 1500;
			int upAndDownInt = 1500;
			int energyInt = 1500;
			int directionInt = 1500;
			int CH5Int = 1500;
			int CH6Int = 1500;
			int CH7Int = 1500;

			switch (checkedId) {
			case R.id.paramsetting_lock:
				break;
			case R.id.paramsetting_unlock:
				break;
			case R.id.paramsetting_avglock:
				break;
			case R.id.paramsetting_avgunlock:
				break;
			case R.id.autoland:
				break;
			}

			ParamSetting.this.energy.setHint(String.valueOf(energyInt));
			ParamSetting.this.byWing.setHint(String.valueOf(byWingInt));
			ParamSetting.this.direction.setHint(String.valueOf(directionInt));
			ParamSetting.this.upAndDown.setHint(String.valueOf(upAndDownInt));
			ParamSetting.this.ch5.setHint(String.valueOf(CH5Int));
			ParamSetting.this.ch6.setHint(String.valueOf(CH6Int));
			ParamSetting.this.ch7.setHint(String.valueOf(CH7Int));
		}

	};

	private EditText energy = null;
	private EditText byWing = null;
	private EditText direction = null;
	private EditText upAndDown = null;
	private EditText ch5 = null;
	private EditText ch6 = null;
	private EditText ch7 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.paramsetting);
		init();
	}

	private void init() {
		this.choiseGroup = (RadioGroup) findViewById(R.id.paramsetting_choisegroup);
		this.choiseGroup.setOnCheckedChangeListener(checkedChangeListener);

		this.energy = (EditText) findViewById(R.id.paramsetting_energy);
		this.byWing = (EditText) findViewById(R.id.paramsetting_bywing);
		this.direction = (EditText) findViewById(R.id.paramsetting_direction);
		this.upAndDown = (EditText) findViewById(R.id.paramsetting_upanddown);
		this.ch5 = (EditText) findViewById(R.id.paramsetting_ch5);
		this.ch6 = (EditText) findViewById(R.id.paramsetting_ch6);
		this.ch7 = (EditText) findViewById(R.id.paramsetting_ch7);

		this.energy.setHint(String.valueOf(AppConstant.lockParam.energyInt));
		this.byWing.setHint(String.valueOf(AppConstant.lockParam.byWingInt));
		this.direction.setHint(String
				.valueOf(AppConstant.lockParam.directionInt));
		this.upAndDown.setHint(String
				.valueOf(AppConstant.lockParam.upAndDownInt));
		this.ch5.setHint(String.valueOf(AppConstant.lockParam.CH5Int));
		this.ch6.setHint(String.valueOf(AppConstant.lockParam.CH6Int));
		this.ch7.setHint(String.valueOf(AppConstant.lockParam.CH7Int));
	}

}