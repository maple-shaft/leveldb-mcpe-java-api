package org.middlepath.leveldbmcpe.palette;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.middlepath.leveldbmcpe.generic.BedrockSerializable;
import org.middlepath.leveldbmcpe.utils.BinaryUtils;

/**
 * Local storage palette
 *
 * @author DAB
 */
public class BlockStoragePalette extends ArrayList<PaletteItem> implements BedrockSerializable {

	public BlockStoragePalette() {
		super();
	}
	
	public BlockStoragePalette(int size) {
		super(size);
	}
	
	@Override
	public byte[] write() throws Exception {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			BinaryUtils.concat(bos, this.iterator());
			bos.flush();
			return bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (Exception e) {}
		}
		return null;
	}
}