/*
 * Copyright (c) 2008, 2009, 2010, 2011 Denis Tulskiy
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

package com.zlm.audio.formats.flac;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.flac.FlacFileReader;
import org.jaudiotagger.audio.generic.GenericAudioHeader;

import com.zlm.audio.AudioFileReader;
import com.zlm.audio.model.AudioInfo;

/**
 * @Author: Denis Tulskiy
 * @Date: 26.06.2009
 */
public class FLACFileReader extends AudioFileReader {
	public AudioInfo readSingle(AudioInfo audioInfo) {
		try {
			/*
			 * if (Util.getFileExt(trackData.getFile()).equalsIgnoreCase("oga"))
			 * { OggFlacDecoder dec = new OggFlacDecoder(); dec.open(new
			 * RandomAccessFile(trackData.getFile(), "r")); StreamInfo
			 * streamInfo = dec.getStreamInfo();
			 * trackData.setSampleRate(streamInfo.getSampleRate());
			 * trackData.setBps(streamInfo.getBitsPerSample());
			 * trackData.setChannels(streamInfo.getChannels());
			 * trackData.setTotalSamples(streamInfo.getTotalSamples());
			 * 
			 * for (Metadata m : dec.getMetadata()) { if (m instanceof
			 * VorbisComment) { VorbisComment comment = (VorbisComment) m;
			 * VorbisCommentTag vorbisTag = new VorbisCommentTag();
			 * HashMap<String, String> map = comment.getComments(); for (String
			 * key : map.keySet()) { try { VorbisCommentFieldKey newKey =
			 * VorbisCommentFieldKey.valueOf(key);
			 * vorbisTag.add(vorbisTag.createTagField(newKey, map.get(key))); }
			 * catch (IllegalArgumentException e) {
			 * vorbisTag.add(vorbisTag.createTagField(key, map.get(key))); } }
			 * copyCommonTagFields(vorbisTag, track); } } } else
			 */{
				FlacFileReader reader = new FlacFileReader();
				AudioFile af1 = reader.read(audioInfo.getFile());

				GenericAudioHeader audioHeader = (GenericAudioHeader) af1
						.getAudioHeader();
				copyHeaderFields(audioHeader, audioInfo);
			}
		} catch (Exception e) {
			System.out.println("Couldn't read file: " + audioInfo.getFile());
		}
		return audioInfo;
	}

	public boolean isFileSupported(String ext) {
		// todo fix seeking with oga and uncomment
		return ext.equalsIgnoreCase("flac")/* || ext.equalsIgnoreCase("oga") */;
	}

	// @Override
	// public void copySpecificTagFields(Tag tag, Track track) {
	// FlacTag flacTag = (FlacTag) tag;
	// }

}
