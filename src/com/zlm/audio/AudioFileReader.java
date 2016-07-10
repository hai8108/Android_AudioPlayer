package com.zlm.audio;

import java.io.File;

import org.jaudiotagger.audio.generic.GenericAudioHeader;

import android.media.AudioFormat;

import com.zlm.audio.model.AudioInfo;
import com.zlm.audio.util.AudioMathUtil;
import com.zlm.audio.util.MediaUtil;

/**
 * 音频文件读取器
 * 
 * @author zhangliangming
 * 
 */
public abstract class AudioFileReader {
	public AudioInfo read(File file) {
		AudioInfo audioInfo = new AudioInfo();
		audioInfo.setFileSize(file.length());
		audioInfo.setFileSizeStr(MediaUtil.getFileSize(file.length()));
		String filePath = file.getPath();
		audioInfo.setFilePath(filePath);
		audioInfo.setFileExt(MediaUtil.getFileExt(filePath));
		return reload(audioInfo);
	}

	private AudioInfo reload(AudioInfo audioInfo) {
		AudioInfo res = readSingle(audioInfo);

		double totalMS = AudioMathUtil.samplesToMillis(
				audioInfo.getTotalSamples(), audioInfo.getSampleRate());
		long duration = Math.round(totalMS);

		audioInfo.setDuration(duration);

		String durationStr = MediaUtil.formatTime((int) duration);
		audioInfo.setDurationStr(durationStr);

		return res;
	}

	protected void copyHeaderFields(GenericAudioHeader header,
			AudioInfo audioInfo) {
		if (header != null && audioInfo != null) {
			audioInfo.setChannels(header.getChannelNumber());
			int frameSize = audioInfo.getChannels()
					* AudioFormat.ENCODING_PCM_16BIT;
			audioInfo.setFrameSize(frameSize);
			audioInfo.setTotalSamples(header.getTotalSamples());
			audioInfo.setSampleRate(header.getSampleRateAsNumber());
			audioInfo.setPlayedProgress(0);
			audioInfo.setCodec(header.getFormat());
			audioInfo.setBitrate((int) header.getBitRateAsNumber());
		}
	}

	protected abstract AudioInfo readSingle(AudioInfo audioInfo);

	public abstract boolean isFileSupported(String ext);

}
