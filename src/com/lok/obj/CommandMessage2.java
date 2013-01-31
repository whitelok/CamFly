package com.lok.obj;

public class CommandMessage2 {
	public byte begin_flag1 = (byte) 0xa5;
	public byte begin_flag2 = (byte) 0x5a;
	public byte data_size = (byte) 0x12;
	public int data_sizeInt = 18;
	public byte data_begin_flag = (byte) 0xf0;
	public int data_begin_flagInt = 240;
	public byte[] byWing = new byte[2];
	public int byWingInt;
	public byte[] upAndDown = new byte[2];
	public int upAndDownInt;
	public byte[] energy = new byte[2];
	public int energyInt;
	public byte[] direction = new byte[2];
	public int directionInt;
	public byte[] CH5 = new byte[2];
	public int CH5Int;
	public byte[] CH6 = new byte[2];
	public int CH6Int;
	public byte[] CH7 = new byte[2];
	public int CH7Int;
	public byte sum;
	public byte end_flag = (byte) 0xaa;

	public CommandMessage2() {
		this.setByWing(1500);
		this.setUpAndDown(1500);
		this.setEnergy(1070);
		this.setDirection(1500);
		this.setSum();
	}

	public byte getBegin_flag1() {
		return begin_flag1;
	}

	public void setBegin_flag1(byte begin_flag1) {
		this.begin_flag1 = begin_flag1;
	}

	public byte getBegin_flag2() {
		return begin_flag2;
	}

	public void setBegin_flag2(byte begin_flag2) {
		this.begin_flag2 = begin_flag2;
	}

	public byte getData_size() {
		return data_size;
	}			


	public void setData_size(byte data_size) {
		this.data_size = data_size;
	}

	public byte getData_begin_flag() {
		return data_begin_flag;
	}

	public void setData_begin_flag(byte data_begin_flag) {
		this.data_begin_flag = data_begin_flag;
	}

	public byte[] getByWing() {
		return byWing;
	}

	public void setByWing(int byWingInt) {
		this.byWingInt = byWingInt;
		this.byWing = int2byte2(byWingInt);
	}

	public byte[] getUpAndDown() {
		return upAndDown;
	}

	public void setUpAndDown(int upAndDownInt) {
		this.upAndDownInt = upAndDownInt;
		this.upAndDown = int2byte2(upAndDownInt);
	}

	public byte[] getEnergy() {
		return energy;
	}

	public void setEnergy(int energyInt) {
		this.energyInt = energyInt;
		this.energy = int2byte2(energyInt);
	}

	public byte[] getDirection() {
		return direction;
	}

	public void setDirection(int directionInt) {
		this.directionInt = directionInt;
		this.direction = int2byte2(directionInt);
	}

	public byte[] getCH5() {
		return CH5;
	}

	public void setCH5(int cH5Int) {
		this.CH5Int = cH5Int;
		CH5 = int2byte2(cH5Int);
	}

	public byte[] getCH6() {
		return CH6;
	}

	public void setCH6(int cH6Int) {
		this.CH6Int = cH6Int;
		CH6 = int2byte2(cH6Int);
	}

	public byte[] getCH7() {
		return CH7;
	}

	public void setCH7(int cH7Int) {
		this.CH7Int = cH7Int;
		CH7 = int2byte2(cH7Int);
	}

	public byte getSum() {
		return sum;
	}

	public void setSum() {
		int temp = 18 + 240 + byWingInt % 256 + upAndDownInt % 256 + energyInt
				% 256 + directionInt % 256 + CH5Int % 256 + CH6Int % 256
				+ CH7Int % 256 + byWingInt / 256 + upAndDownInt / 256
				+ energyInt / 256 + directionInt / 256 + CH5Int / 256 + CH6Int
				/ 256 + CH7Int / 256;
		this.sum = (byte) (temp % 256);
	}

	public byte getEnd_flag() {
		return end_flag;
	}

	public void setEnd_flag(byte end_flag) {
		this.end_flag = end_flag;
	}

	private byte[] int2byte2(int data) {
		byte[] temp = new byte[4];
		temp[0] = (byte) ((data >> 24) & 0xFF);
		temp[1] = (byte) ((data >> 16) & 0xFF);
		temp[2] = (byte) ((data >> 8) & 0xFF);
		temp[3] = (byte) (data & 0xFF);
		byte[] result = new byte[2];
		result[0] = temp[2];
		result[1] = temp[3];
		return result;
	}

}
