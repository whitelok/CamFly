package com.lok.utils;

import com.lok.obj.AutoLand;
import com.lok.obj.AvgLock;
import com.lok.obj.AvgUnLock;
import com.lok.obj.LockParam;
import com.lok.obj.UnLockParam;

public class AppConstant {
	public static int UP = 100;
	public static int DOWN = 101;
	public static int MOVE = 102;

	public static int BYWING_MAX = 1900;
	public static int BYWING_MIN = 1700;

	public static int ENERGY_MAX = 1700;
	public static int ENERGY_MIN = 1400;

	public static int UPANDDOWN_MAX = 1700;
	public static int UPANDDOWN_MIN = 1050;

	public static int DIRECTION_MAX = 1700;
	public static int DIRECTION_MIN = 1700;

	public static LockParam lockParam = new LockParam();

	public static UnLockParam unlockParam = new UnLockParam();

	public static AvgLock avgLock = new AvgLock();

	public static AvgUnLock avgUnLock = new AvgUnLock();

	public static AutoLand autoLand = new AutoLand();
}
