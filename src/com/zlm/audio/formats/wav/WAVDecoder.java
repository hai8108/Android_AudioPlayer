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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.zlm.audio.model.AudioInfo;

/**
 * @Author: Denis Tulskiy
 * @Date: 30.06.2009
 */
public class WAVDecoder implements com.zlm.audio.Decoder {
	private InputStream audioInputStream;
	private AudioInfo inputFile;

	public boolean open(AudioInfo audioInfo) {
		try {
			this.inputFile = audioInfo;
			audioInputStream = new FileInputStream(audioInfo.getFile());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void seekSample(long sample) {
		open(inputFile);
		try {
			long toSkip = sample * inputFile.getFrameSize();
			long skipped = 0;
			while (skipped < toSkip) {
				long b = audioInputStream.skip(toSkip - skipped);
				if (b == 0)
					break;
				skipped += b;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int decode(byte[] buf) {
		try {
			return audioInputStream.read(buf, 0, 512);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void close() {
		try {
			if (audioInputStream != null)
				audioInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isFileSupported(String ext) {
		return ext.equalsIgnoreCase("wav");
	}
}
