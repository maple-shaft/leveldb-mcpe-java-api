package org.middlepath.mcapi.palette;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.middlepath.mcapi.generic.BedrockSerializable;
import org.middlepath.mcapi.utils.BinaryUtils;

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