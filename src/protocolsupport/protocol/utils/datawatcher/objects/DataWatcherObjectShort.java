package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class DataWatcherObjectShort extends DataWatcherObjectNumber<Short> {

	public DataWatcherObjectShort() {
	}

	public DataWatcherObjectShort(short s) {
		value = s;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = from.readShort();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		to.writeShort(value);
	}

}
