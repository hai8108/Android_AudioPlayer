/*
 * Copyright (c) 2008, 2009, 2010 Denis Tulskiy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * version 3 along with this work.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.zlm.audio.formats.wav;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.wav.WavFileReader;

import android.media.AudioFormat;
import android.media.MediaPlayer;

import com.zlm.audio.AudioFileReader;
import com.zlm.audio.model.AudioInfo;
import com.zlm.audio.util.AudioMathUtil;

/**
 * @Author: Denis Tulskiy
 * @Date: 30.06.2009
 */
public class WAVFileReader extends AudioFileReader {

	@Override
	protected AudioInfo readSingle(AudioInfo audioInfo) {
		try {

			org.jaudiotagger.audio.wav.WavFileReader fileReader = new WavFileReader();
			AudioFile audioFile = fileReader.read(new File(audioInfo
					.getFilePath()));
			AudioHeader audioHeader = audioFile.getAudioHeader();

			audioInfo.setChannels(Integer.parseInt(audioHeader.getChannels()));
			int frameSize = audioInfo.getChannels()
					* AudioFormat.ENCODING_PCM_16BIT;
			audioInfo.setFrameSize(frameSize);
			audioInfo.setSampleRate(audioHeader.getSampleRateAsNumber());

			// 这里通过MediaPlayer获取totalSamples
			MediaPlayer mediaPlayer = new MediaPlayer();
			mediaPlayer.setDataSource(audioInfo.getFilePath());
			mediaPlayer.prepare();
			long duration = mediaPlayer.getDuration();
			int totalSamples = (int) AudioMathUtil.millisToSamples(duration,
					audioInfo.getSampleRate());
			audioInfo.setTotalSamples(totalSamples);
			mediaPlayer.release();

			//
			audioInfo.setPlayedProgress(0);
			audioInfo.setCodec(getFileExt(audioInfo.getFilePath()));
			audioInfo.setBitrate((int) audioHeader.getBitRateAsNumber());

			return audioInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isFileSupported(String ext) {
		return ext.equalsIgnoreCase("wav");
	}

	private static String getFileExt(String filePath) {
		int pos = filePath.lastIndexOf(".");
		if (pos == -1)
			return "";
		return filePath.substring(pos + 1).toLowerCase();
	}
}
