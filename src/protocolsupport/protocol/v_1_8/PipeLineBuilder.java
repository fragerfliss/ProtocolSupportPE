package protocolsupport.protocol.v_1_8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_8_R2.NetworkManager;
import net.minecraft.server.v1_8_R2.PacketPrepender;
import net.minecraft.server.v1_8_R2.PacketSplitter;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ChannelHandlers;
import protocolsupport.protocol.IPipeLineBuilder;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, ProtocolVersion version) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManager networkmanager = (NetworkManager) pipeline.get(ChannelHandlers.NETWORK_MANAGER);
		networkmanager.a(new HandshakeListener(networkmanager));
		pipeline.replace(ChannelHandlers.SPLITTER, ChannelHandlers.SPLITTER, new PacketSplitter());
		pipeline.replace(ChannelHandlers.PREPENDER, ChannelHandlers.PREPENDER, new PacketPrepender());
		ChannelHandlers.getDecoder(pipeline).setRealDecoder(new PacketDecoder());
		ChannelHandlers.getEncoder(pipeline).setRealEncoder(new PacketEncoder());
	}

}
