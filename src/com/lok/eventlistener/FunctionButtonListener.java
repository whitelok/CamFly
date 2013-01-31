package com.lok.eventlistener;

import com.lok.camfly.R;
import com.lok.utils.NetworkConstant;
import com.lok.utils.CmdCommunicator.Communicator;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class FunctionButtonListener implements OnTouchListener {

	private Communicator communicator = null;

	private Button tempButtonView = null;

	private boolean avgLockTag = false;

	public FunctionButtonListener(Communicator communicator) {
		this.communicator = communicator;
	}

	public boolean onTouch(View view, MotionEvent event) {
		if (view.getId() == R.id.unlock) {
			// 解锁
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// 升降
				NetworkConstant.cmdMessage.setUpAndDown(1500);
				// 方向
				NetworkConstant.cmdMessage.setDirection(1500);
				// 油門
				NetworkConstant.cmdMessage.setEnergy(1050);
				// 副翼
				NetworkConstant.cmdMessage.setByWing(1050);
				// 通道5
				NetworkConstant.cmdMessage.setCH5(1500);
				// 通道6
				NetworkConstant.cmdMessage.setCH6(1500);
				// 通道7
				NetworkConstant.cmdMessage.setCH7(1500);
				this.communicator.send(NetworkConstant.cmdMessage);
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				NetworkConstant.cmdMessage.setByWing(1500);
			}
		}
		if (view.getId() == R.id.lock) {
			// 加锁
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// 升降
				NetworkConstant.cmdMessage.setUpAndDown(1500);
				// 方向
				NetworkConstant.cmdMessage.setDirection(1500);
				// 油門
				NetworkConstant.cmdMessage.setEnergy(1070);
				// 副翼
				NetworkConstant.cmdMessage.setByWing(1850);
				// 通道5
				NetworkConstant.cmdMessage.setCH5(1150);
				// 通道6
				NetworkConstant.cmdMessage.setCH6(1150);
				// 通道7
				NetworkConstant.cmdMessage.setCH7(1150);
				this.communicator.send(NetworkConstant.cmdMessage);
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {

			}
		}

		if (view.getId() == R.id.avglock) {
			// 锁定航向
			if (!avgLockTag) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					NetworkConstant.cmdMessage.setCH5(1850);
					this.communicator.send(NetworkConstant.cmdMessage);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					avgLockTag = true;
					tempButtonView = (Button) view;
					tempButtonView.setText("航向已锁定");
				}

			} else {
				// 解锁航向
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					NetworkConstant.cmdMessage.setEnergy(1050);
					NetworkConstant.cmdMessage.setByWing(1050);
					this.communicator.send(NetworkConstant.cmdMessage);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					avgLockTag = false;
					tempButtonView = (Button) view;
					tempButtonView.setText("航向未锁定");
				}
			}
		}
		if (view.getId() == R.id.autoland) {
			NetworkConstant.cmdMessage.setCH5(1150);
			NetworkConstant.cmdMessage.setCH5(1850);
			this.communicator.send(NetworkConstant.cmdMessage);
		}
		return false;
	}

	public Communicator getCommunicator() {
		return communicator;
	}

	public void setCommunicator(Communicator communicator) {
		this.communicator = communicator;
	}

}
