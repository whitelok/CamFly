package com.lok.utils.CmdCommunicator;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.lok.obj.CommandMessage2;

import android.util.Log;

public class CommunicatorEncoder implements MessageEncoder<CommandMessage2> {

	private String TAG = "CommunicatorEncoder";

	public void encode(IoSession session, CommandMessage2 cmdMessage,
			ProtocolEncoderOutput out) throws Exception {

		// Log.i(TAG, "encode");
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);

		buf.put(cmdMessage.begin_flag1);
		buf.put(cmdMessage.begin_flag2);
		buf.put(cmdMessage.data_size);
		buf.put(cmdMessage.data_begin_flag);
		buf.put(cmdMessage.byWing);
		buf.put(cmdMessage.upAndDown);
		buf.put(cmdMessage.energy);
		buf.put(cmdMessage.direction);
		buf.put(cmdMessage.CH5);
		buf.put(cmdMessage.CH6);
		buf.put(cmdMessage.CH7);
		cmdMessage.setSum();
		// Log.i("encode", String.valueOf(cmdMessage.getSum()));
		buf.put(cmdMessage.getSum());
		buf.put(cmdMessage.end_flag);
		buf.flip();

		// Log.i(TAG, "编码:" + buf.toString());
		out.write(buf);
	}

}
