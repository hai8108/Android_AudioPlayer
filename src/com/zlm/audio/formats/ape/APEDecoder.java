package com.zlm.audio.formats.ape;

import java.io.IOException;

import com.zlm.audio.Decoder;
import com.zlm.audio.model.AudioInfo;

import davaguine.jmac.decoder.IAPEDecompress;
import davaguine.jmac.tools.File;
import davaguine.jmac.tools.JMACException;

/**
 * APE解码器
 * 
 * @author zhangliangming
 * 
 */
public class APEDecoder implements Decoder {
	private IAPEDecompress decoder;
	private static final int BLOCKS_PER_DECODE = 4096 * 2;
	private int blockAlign;
	private AudioInfo audioInfo;

	@Override
	public boolean open(AudioInfo audioInfo) {
		this.audioInfo = audioInfo;
		try {
			File apeInputFile = File.createFile(audioInfo.getFilePath(), "r");
			decoder = IAPEDecompress.CreateIAPEDecompress(apeInputFile);
			blockAlign = decoder.getApeInfoBlockAlign();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
		return false;
	}

	@Override
	public void seekSample(long sample) {
		try {
			if (decoder.getApeInfoDecompressCurrentBlock() != sample) {
				decoder.Seek((int) sample);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int decode(byte[] buf) {
		try {
			int blocksDecoded = decoder.GetData(buf, BLOCKS_PER_DECODE);
			audioInfo.setBitrate(decoder.getApeInfoDecompressCurrentBitRate());
			if (blocksDecoded <= 0)
				return -1;
			return blocksDecoded * blockAlign;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JMACException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void close() {
		try {
			if (decoder != null) {
				audioInfo.setBitrate(decoder.getApeInfoAverageBitrate());
				decoder.getApeInfoIoSource().close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isFileSupported(String ext) {
		return ext.equalsIgnoreCase("ape");
	}
}
