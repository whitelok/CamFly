package com.lok.eventlistener;

import android.util.Log;

import com.lok.obj.CommandMessage2;
import com.lok.utils.MathUtils;
import com.lok.utils.NetworkConstant;
import com.lok.utils.CmdCommunicator.Communicator;

public class CtrlStickListener {

	private String TAG = "CtrlStickListener";

	private Communicator communicator = null;
	private CommandMessage2 cmdMessage = null;

	public CtrlStickListener(Communicator communicator) {
		Log.i(TAG, "init");
		this.communicator = communicator;
		this.cmdMessage = NetworkConstant.cmdMessage;
	}

	public void onSteeringWheelChanged(int angle, int relateLen) {
		if (relateLen <= 20) {
			this.communicator.send(cmdMessage);
			return;
		}
		this.cmdMessage.setByWing(MathUtils.calcByWing(relateLen, angle));
		this.cmdMessage.setUpAndDown(MathUtils.calcUpAndDown(relateLen, angle));
	}
}
