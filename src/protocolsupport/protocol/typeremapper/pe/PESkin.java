package protocolsupport.protocol.typeremapper.pe;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.Validate;

import protocolsupport.utils.Utils;

public class PESkin {

	public static final HashMap<String, byte[]> SKIN_CACHE = new HashMap<>();

	public static void init() {
	}

	public static final byte[] STEVE = new Callable<byte[]>() {
		@Override
		public byte[] call() {
			try {
				return toNetworkData(ImageIO.read(Utils.getResource("steve_skin.png")));
			} catch (IOException e) {
				return new byte[0];
			}
		}
	}.call();

	public static final byte[] fromUsername(String username)	{
		byte[] fromCache = SKIN_CACHE.getOrDefault(username, null);
		if (fromCache != null)	{
			return fromCache;
		}
		try {
			BufferedImage img = ImageIO.read(new URL("https://crafatar.com/skins/" + username + "?default=MHF_Steve"));
			byte[] toReturn = toNetworkData(img);
			SKIN_CACHE.put(username, toReturn);
			return toReturn;
		} catch (MalformedURLException e)	{
			//this shouldn't ever happen unless a bad username is given, so error
			e.printStackTrace();
		} catch (IOException e)	{
			e.printStackTrace();
		}

		return STEVE;
	}

	public static byte[] toNetworkData(BufferedImage skin) {
		Validate.isTrue(skin.getWidth() == 64, "Must be 64 pixels wide");
		Validate.isTrue(skin.getHeight() == 64, "Must be 64 pixels high");
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		for (int y = 0; y < skin.getHeight(); y++) {
			for (int x = 0; x < skin.getWidth(); x++) {
                Color color = new Color(skin.getRGB(x, y), true);
                stream.write(color.getRed());
                stream.write(color.getGreen());
                stream.write(color.getBlue());
                stream.write(color.getAlpha());
			}
		}
		return stream.toByteArray();
	}

}
