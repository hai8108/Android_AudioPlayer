package com.zlm.audio;

import com.zlm.audio.model.AudioInfo;

/**
 * 解码器
 * 
 * @author zhangliangming
 * 
 */
public interface Decoder {
	/**
	 * 打开音频文件并解码
	 * 
	 * @param audioInfo
	 * @return
	 */
	public boolean open(AudioInfo audioInfo);

	/**
	 * 
	 * @param sample
	 */
	public void seekSample(long sample);

	/**
	 * 
	 * @param buf
	 * @return
	 */
	public int decode(byte[] buf);

	public abstract boolean isFileSupported(String ext);

	public void close();
}
