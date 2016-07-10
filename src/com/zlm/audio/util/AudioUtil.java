package com.zlm.audio.util;

import java.util.ArrayList;

import com.zlm.audio.AudioFileReader;
import com.zlm.audio.Decoder;
import com.zlm.audio.formats.ape.APEDecoder;
import com.zlm.audio.formats.ape.APEFileReader;
import com.zlm.audio.formats.flac.FLACDecoder;
import com.zlm.audio.formats.flac.FLACFileReader;
import com.zlm.audio.formats.mp3.MP3Decoder;
import com.zlm.audio.formats.mp3.MP3FileReader;
import com.zlm.audio.formats.wav.WAVDecoder;
import com.zlm.audio.formats.wav.WAVFileReader;

/**
 * 
 * @author zhangliangming
 * 
 */
public class AudioUtil {
	private static ArrayList<AudioFileReader> readers;
	private static ArrayList<Decoder> decoders;

	static {
		readers = new ArrayList<AudioFileReader>();
		readers.add(new MP3FileReader());
		readers.add(new APEFileReader());
		readers.add(new FLACFileReader());
		readers.add(new WAVFileReader());

		decoders = new ArrayList<Decoder>();
		decoders.add(new MP3Decoder());
		decoders.add(new APEDecoder());
		decoders.add(new FLACDecoder());
		decoders.add(new WAVDecoder());
	}

	/**
	 * 通过文件路径获取格式读取器
	 * 
	 * @param filePath
	 * @return
	 */
	public static AudioFileReader getAudioFileReaderByFilePath(String filePath) {
		String ext = getFileExt(filePath);
		for (AudioFileReader reader : readers) {
			if (reader.isFileSupported(ext))
				return reader;
		}
		return null;
	}

	/**
	 * 通过文件类型获取格式读取器
	 * 
	 * @param fileExt
	 * @return
	 */
	public static AudioFileReader getAudioFileReaderByFileExt(String fileExt) {
		fileExt = fileExt.toLowerCase();
		for (AudioFileReader reader : readers) {
			if (reader.isFileSupported(fileExt))
				return reader;
		}
		return null;
	}

	/**
	 * 通过文件路径获取格式解码器
	 * 
	 * @param filePath
	 * @return
	 */
	public static Decoder getAudioDecoderByFilePath(String filePath) {
		String ext = getFileExt(filePath);
		for (Decoder decoder : decoders) {
			if (decoder.isFileSupported(ext))
				return decoder;
		}
		return null;
	}

	/**
	 * 通过文件类型获取格式解码器
	 * 
	 * @param fileExt
	 * @return
	 */
	public static Decoder getAudioDecoderByFileExt(String fileExt) {
		fileExt = fileExt.toLowerCase();
		for (Decoder decoder : decoders) {
			if (decoder.isFileSupported(fileExt))
				return decoder;
		}
		return null;
	}

	private static String getFileExt(String filePath) {
		int pos = filePath.lastIndexOf(".");
		if (pos == -1)
			return "";
		return filePath.substring(pos + 1).toLowerCase();
	}
}
