package org.middlepath.leveldbmcpe.generic;

public interface BedrockSerializable {

	/**
	 * Convert into bytes that can be persisted to an MC Bedrock world.
	 *
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public byte[] write() throws Exception;
	
}