package com.zlm.audio.formats.ape;

import java.io.File;

import android.media.AudioFormat;

import com.zlm.audio.AudioFileReader;
import com.zlm.audio.model.AudioInfo;

import davaguine.jmac.info.APEFileInfo;
import davaguine.jmac.info.APEHeader;
import davaguine.jmac.tools.RandomAccessFile;

/**
 * ape文件读取
 * 
 * @author zhangliangming
 * 
 */
public class APEFileReader extends AudioFileReader {

	@Override
	protected AudioInfo readSingle(AudioInfo audioInfo) {
		try {
			RandomAccessFile ras = new RandomAccessFile(new File(
					audioInfo.getFilePath()), "r");
			APEHeader header = new APEHeader(ras);
			APEFileInfo fileInfo = new APEFileInfo();
			header.Analyze(fileInfo);
			parseInfo(audioInfo, fileInfo);

			ras.close();
			return audioInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void parseInfo(AudioInfo audioInfo, APEFileInfo fileInfo) {
		audioInfo.setChannels(fileInfo.nChannels);
		int frameSize = audioInfo.getChannels()
				* AudioFormat.ENCODING_PCM_16BIT;
		audioInfo.setFrameSize(frameSize);
		audioInfo.setSampleRate(fileInfo.nSampleRate);
		audioInfo.setTotalSamples(fileInfo.nTotalBlocks);
		audioInfo.setPlayedProgress(0);
		audioInfo.setCodec("Monkey's Audio");
		audioInfo.setBitrate(fileInfo.nAverageBitrate);
	}

	@Override
	public boolean isFileSupported(String ext) {
		return ext.equalsIgnoreCase("ape");
	}
}
