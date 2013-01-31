package com.lok.widget;

import com.lok.eventlistener.EnergyStickListener;
import com.lok.obj.CommandMessage2;
import com.lok.utils.AppConstant;
import com.lok.utils.MathUtils;
import com.lok.utils.NetworkConstant;
import com.lok.utils.CmdCommunicator.Communicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.widget.Button;

public class EnergyStick extends SurfaceView implements Runnable, Callback {
	private static String TAG = "EnergyStick";

	public int pointer = 10;

	// 画面控制器
	private SurfaceHolder mHolder;
	// 停止标记
	private boolean isStop = false;
	// 图像更新线程
	private Thread mThread;
	// 画笔
	private Paint mPaint;

	// 主控杆位置(左上角)
	private Point hBarPosition = new Point(70, 0);

	// 横控杆位置(左上角)
	private Point vBarPosition = new Point(0, 260);

	// 轴位置(中心)
	private Point stickPosition = new Point(90, 280);
	// 轴半径
	private int stickRadius = 20;

	private EnergyStickListener listener = null;

	private Communicator communicator = null;

	private CommandMessage2 cmdMessage = null;

	public boolean continueFlag = true;

	private Button autoLand = null;

	public EnergyStick(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.i(TAG, "onCreate");

		this.setKeepScreenOn(true);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mThread = new Thread(this);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);

		setFocusable(false);
		setFocusableInTouchMode(false);
		setZOrderOnTop(true);
		// 设置背景透明
		mHolder.setFormat(PixelFormat.TRANSPARENT);

		this.cmdMessage = NetworkConstant.cmdMessage;
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		Log.i(TAG, "surfaceChanged");
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		Log.i(TAG, "surfaceCreated");
		mThread.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.i(TAG, "surfaceDestroyed");
		isStop = true;
	}

	public void run() {
		Canvas canvas = null;
		while (!isStop) {
			try {
				canvas = mHolder.lockCanvas();
				canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
				mPaint.setColor(Color.YELLOW);
				mPaint.setAlpha(50);
				// 绘制主控杆
				canvas.drawRect(hBarPosition.x, hBarPosition.y,
						hBarPosition.x + 40, hBarPosition.y + 300, mPaint);
				// 绘制横控杆
				canvas.drawRect(vBarPosition.x, vBarPosition.y,
						vBarPosition.x + 180, vBarPosition.y + 40, mPaint);

				mPaint.setColor(Color.LTGRAY);
				// 绘制轴
				canvas.drawCircle(stickPosition.x, stickPosition.y,
						stickRadius, mPaint);
				// if (autoLand != null && this.cmdMessage.energyInt > 1300) {
				// if (!autoLand.isEnabled()) {
				// autoLand.setEnabled(true);
				// }
				// }
				// if (autoLand != null && this.cmdMessage.energyInt < 1300) {
				// if (autoLand.isEnabled()) {
				// autoLand.setEnabled(false);
				// }
				// }
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					mHolder.unlockCanvasAndPost(canvas);
				}
				if (this.communicator != null) {
					this.communicator.send(this.cmdMessage);
				}
			}
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			vBarPosition.y = (int) event.getY() - 20;
			stickPosition.y = vBarPosition.y + 20;
			stickPosition.x = (int) event.getX();

			if (vBarPosition.y < 0) {
				vBarPosition.y = 0;
				stickPosition.y = stickRadius;
			}
			if (vBarPosition.y + 40 > 300) {
				vBarPosition.y = 260;
				stickPosition.y = 300 - stickRadius;
			}
			if (stickPosition.x - stickRadius < 0) {
				stickPosition.x = stickRadius;
			}
			if (stickPosition.x + stickRadius > 180) {
				stickPosition.x = 180 - stickRadius;
			}
			if (stickPosition.x > 110 || stickPosition.x < 70) {
				this.cmdMessage.setDirection(MathUtils
						.calcDirection(stickPosition.x));
			}
			this.cmdMessage.setEnergy(MathUtils.calcEnergy((vBarPosition.y)));
			this.communicator.send(this.cmdMessage);
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			stickPosition.x = vBarPosition.x + 90;
			stickPosition.y = vBarPosition.y + 20;
			this.cmdMessage.setDirection(MathUtils
					.calcDirection(stickPosition.x));
		}
		return continueFlag;
	}

	public boolean isInView(int x, int y) {
		if (this.getLeft() < x && x < this.getRight()) {
			if (this.getTop() < y && y < this.getBottom()) {
				return true;
			}
		}
		return false;
	}

	public void onMultiTouch(int x, int y, int action) {
		if (action == AppConstant.DOWN || action == AppConstant.MOVE) {
			vBarPosition.y = y - 20;
			stickPosition.y = vBarPosition.y + 20;
			stickPosition.x = x;

			if (vBarPosition.y < 0) {
				vBarPosition.y = 0;
				stickPosition.y = stickRadius;
			}
			if (vBarPosition.y + 40 > 300) {
				vBarPosition.y = 260;
				stickPosition.y = 300 - stickRadius;
			}
			if (stickPosition.x - stickRadius < 0) {
				stickPosition.x = stickRadius;
			}
			if (stickPosition.x + stickRadius > 180) {
				stickPosition.x = 180 - stickRadius;
			}
			if (stickPosition.x > 110 || stickPosition.x < 70) {
				this.cmdMessage.setDirection(MathUtils
						.calcDirection(stickPosition.x));
			}
			this.cmdMessage.setEnergy(MathUtils.calcEnergy((vBarPosition.y)));
			this.communicator.send(this.cmdMessage);
		}
		if (action == AppConstant.UP) {
			stickPosition.x = vBarPosition.x + 90;
			stickPosition.y = vBarPosition.y + 20;
			this.cmdMessage.setDirection(MathUtils
					.calcDirection(stickPosition.x));
		}
	}

	public void backToInit() {
		stickPosition.x = vBarPosition.x + 90;
		stickPosition.y = vBarPosition.y + 20;
		this.cmdMessage.setDirection(MathUtils.calcDirection(stickPosition.x));
	}

	public EnergyStickListener getListener() {
		return listener;
	}

	public void setListener(EnergyStickListener listener) {
		this.listener = listener;
	}

	public Communicator getCommunicator() {
		return communicator;
	}

	public void setCommunicator(Communicator communicator) {
		this.communicator = communicator;
	}

	public Button getAutoLand() {
		return autoLand;
	}

	public void setAutoLand(Button autoLand) {
		this.autoLand = autoLand;
	}
}
