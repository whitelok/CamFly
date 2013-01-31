package com.lok.camfly;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.lok.camfly.R;
import com.lok.eventlistener.CtrlStickListener;
import com.lok.eventlistener.FunctionButtonListener;
import com.lok.eventlistener.VideoConnListener;
import com.lok.utils.NetworkConstant;
import com.lok.utils.NetworkUtils;
import com.lok.utils.CmdCommunicator.Communicator;
import com.lok.widget.CtrlStick;
import com.lok.widget.EnergyStick;

import yizuoshe.WmiiManager.wiCam.WicamView;
import yizuoshe.WmiiManager.wiCam.WicamViewInterface;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CamFlyCtrl extends MultiTouchActivity implements
		WicamViewInterface {
	private String TAG = "CamCarCtrl";

	// 视频连接按钮
	private ToggleButton videoConnBtn = null;

	public int MENU_SCAN_TAG = 1;
	public int MENU_SETTING_TAG = 2;
	public int MENU_CTRLSETTING_TAG = 3;

	// 视频
	private WicamView wicamVideo = null;
	private VideoConnListener videoConnListener = null;

	// 控制socket
	private DatagramSocket ctrlSocket = null;

	// 传输参数
	private String PreferResolution = "qvga";
	private String PreferBitrate = "300k";

	// 摇杆
	private CtrlStick ctrlStick = null;
	// 油门
	private EnergyStick energyStick = null;

	private Button lock = null;

	private Button unlock = null;

	private Button avgLock = null;

	private Button autoLand = null;

	// 通讯工具
	private Communicator communicator = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "CamCarCtrl->onCreate");
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.camcarctrl);
		init();
	}

	public void init() {
		Log.i(TAG, "init");

		checkNetState(this);

		if (NetworkConstant.WICAM_IP == null) {
			Toast.makeText(this, "CamCar对象未选定，请先扫描camcar选定特定对象......",
					Toast.LENGTH_SHORT).show();
		}

		try {
			ctrlSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}

		// 找到WicamView，并且设置回调
		wicamVideo = (WicamView) findViewById(R.id.wicamVideo);
		wicamVideo.wc_callback_setup(this);
		// this.activeWiCamVideo();
		videoConnBtn = (ToggleButton) findViewById(R.id.tbtn_wicamon);
		videoConnListener = new VideoConnListener(videoConnBtn, wicamVideo,
				NetworkConstant.WICAM_IP, NetworkConstant.WICAM_VEIDO_PORT,
				PreferResolution, PreferBitrate, ctrlSocket,
				NetworkConstant.WICAM_CONTROL_PORT);

		energyStick = (EnergyStick) findViewById(R.id.energystick);
		ctrlStick = (CtrlStick) findViewById(R.id.ctrlstick);
		lock = (Button) findViewById(R.id.lock);
		unlock = (Button) findViewById(R.id.unlock);
		avgLock = (Button) findViewById(R.id.avglock);
		autoLand = (Button) findViewById(R.id.autoland);

		this.communicator = NetworkConstant.communicator;
		communicator.connect();

		energyStick.setCommunicator(communicator);
		ctrlStick.setCommunicator(communicator);
		ctrlStick.setListener(new CtrlStickListener(communicator));
		ctrlStick.setOnTouchListener(this);
		energyStick.setOnTouchListener(this);

		lock.setOnTouchListener(new FunctionButtonListener(communicator));
		unlock.setOnTouchListener(new FunctionButtonListener(communicator));
		avgLock.setOnTouchListener(new FunctionButtonListener(communicator));
		autoLand.setOnTouchListener(new FunctionButtonListener(communicator));
		autoLand.setEnabled(false);
		energyStick.setAutoLand(autoLand);

		videoConnBtn.setOnClickListener(videoConnListener);
	}

	// 激活视频模块
	private void activeWiCamVideo() {
		playVideo();
		byte[] cmd = null;
		DatagramPacket ctrlCmdPacket = null;
		cmd = NetworkUtils.hexStringToBytes("0xa50300000000");
		try {
			ctrlCmdPacket = new DatagramPacket(cmd, cmd.length);
			ctrlCmdPacket.setPort(NetworkConstant.WICAM_CONTROL_PORT);
			ctrlCmdPacket.setAddress(InetAddress
					.getByName(NetworkConstant.WICAM_IP));
		} catch (UnknownHostException e) {
			Log.e(TAG, e.getMessage());
		}
		try {
			for (int i = 0; i < 10; i++) {
				this.ctrlSocket.send(ctrlCmdPacket);
			}
		} catch (SocketException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
	}

	public void on_view_created(int width, int height) {

	}

	public void on_view_changed(int width, int height) {

	}

	public void on_view_destroyed() {

	}

	public void on_view_touch_down(int x, int y) {

	}

	public void on_view_touch_move(int x, int y) {

	}

	public void on_view_touch_up(int x, int y) {

	}

	public void on_view_update(Canvas canvas) {

	}

	public void on_stream_flag_info(int flag, byte[] inf, int inf_pos,
			int inf_len) {

	}

	// 报文校验错事件
	public void on_stream_checksum_err(int pktno) {

	}

	// 报文丢失事件
	public void on_stream_lost(int exp, int sn) {

	}

	// 信号强度
	public void on_stream_signal(int snr, int nf) {

	}

	// 定时保活通知
	public void on_stream_keepalive() {

	}

	public void on_stream_frame(byte[] frame, int len, int cnt, int last_nsn,
			int nsn) {
	}

	// 判断网络是否存在并对网络进行设置
	public void checkNetState(final Context context) {
		if (!isConnectNetwork(context)) {
			Builder b = new AlertDialog.Builder(context).setTitle("没有可用的网络")
					.setMessage("是否对网络进行设置？");
			b.setPositiveButton("是", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent mIntent = new Intent("/");
					ComponentName comp = new ComponentName(
							"com.android.settings",
							"com.android.settings.WirelessSettings");
					mIntent.setComponent(comp);
					mIntent.setAction("android.intent.action.VIEW");
					((CamFlyCtrl) context).startActivityForResult(mIntent, 0);
				}
			}).setNeutralButton("否", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			}).show();
		}
		return;
	}

	// 判断网络是否存在
	public static boolean isConnectNetwork(Context context) {
		boolean netSataus = false;
		ConnectivityManager cwjManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		cwjManager.getActiveNetworkInfo();
		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		return netSataus;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_SCAN_TAG, 1, R.string.menu_scanandlist);
		menu.add(0, MENU_SETTING_TAG, 2, R.string.menu_settingonweb);
		menu.add(0, MENU_CTRLSETTING_TAG, 3, R.string.menu_ctrlsetting);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int tag = item.getItemId();
		if (tag == MENU_SCAN_TAG) {
			Intent intent = new Intent();
			intent.setClass(this, CamFlyList.class);
			this.startActivity(intent);
			this.finish();
		}

		if (tag == MENU_SETTING_TAG) {
			if (NetworkConstant.WICAM_IP != null) {
				Intent intent = new Intent();
				Uri uri = Uri.parse(NetworkUtils
						.uriSetting(NetworkConstant.WICAM_IP));
				intent = new Intent(Intent.ACTION_VIEW, uri);
				this.startActivity(intent);
				this.finish();
			} else {
				return false;
			}
		}

		if (tag == MENU_CTRLSETTING_TAG) {
			Intent intent = new Intent();
			intent.setClass(this, CamFlySetting.class);
			this.startActivity(intent);
			this.finish();
		}

		return false;
	}

	@Override
	public void onPause() {

		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void playVideo() {
		this.wicamVideo.wc_start(NetworkConstant.WICAM_IP,
				NetworkConstant.WICAM_VEIDO_PORT, PreferResolution,
				PreferBitrate);
	}

}