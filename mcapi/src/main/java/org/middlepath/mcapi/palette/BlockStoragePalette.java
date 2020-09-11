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
		super();
	}
	
	@Override
	public void add(int index, PaletteItem element) {
		if (this.contains(element) || element.getBlockType() == null)
			return;
		super.add(index, element);
	}
	
	@Override
	public boolean add(PaletteItem e) {
		if (this.contains(e) || e.getBlockType() == null)
			return false;
		return super.add(e);
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