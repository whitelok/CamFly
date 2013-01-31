package com.lok.utils;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class MathUtils {
	public static int getLength(float x1, float y1, float x2, float y2) {
		return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public static Point getBorderPoint(Point a, Point b, int cutRadius) {
		float radian = getRadian(a, b);
		return new Point(a.x + (int) (cutRadius * Math.cos(radian)), a.x
				+ (int) (cutRadius * Math.sin(radian)));
	}

	public static float getRadian(Point a, Point b) {
		float lenA = b.x - a.x;
		float lenB = b.y - a.y;
		float lenC = (float) Math.sqrt(lenA * lenA + lenB * lenB);
		float ang = (float) Math.acos(lenA / lenC);
		ang = ang * (b.y < a.y ? -1 : 1);
		return ang;
	}

	public static int getLocalPointer(MotionEvent event, Rect rect) {
		int pointerCount = event.getPointerCount();
		for (int i = 0; i < pointerCount; i++) {
			if (event.getX(i) < rect.right && event.getX(i) > rect.left) {
				if (event.getY(i) < rect.bottom && event.getY(i) > rect.top) {
					return i;
				}
			}
		}
		return -1;
	}

	public static int calcDirection(int raw) {
		int temp = raw * 100 / 180;
		System.out.println(temp + 1450);
		return temp + 1450;
	}

	public static int calcEnergy(int raw) {
		int temp = raw * 590 / 280;
		System.out.println(1600 - temp);
		return 1600 - temp;
	}

	public static int calcByWing(int len, int angle) {
		// System.out.println(len);
		// System.out.println(angle);
		int temp = len * 115 / 85;
		double tempAngle = angle * 3.14 / 180;
		int result = 0;
		result = (int) (temp * Math.cos(tempAngle));
		result = 1500 + result;
		// System.out.println(result);
		return result;
	}

	public static int calcUpAndDown(int len, int angle) {
		int temp = len * 110 / 85;
		double tempAngle = angle * 3.14 / 180;
		int result = 0;
		result = (int) (temp * Math.sin(tempAngle));
		result = 1500 + result;
		// System.out.println(result);
		return result;
	}
}