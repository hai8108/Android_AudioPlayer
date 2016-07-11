/*
 *  COPYRIGHT NOTICE  
 *  Copyright (C) 2016, Jhuster <lujun.hust@gmail.com>
 *  https://github.com/Jhuster/AudioDemo
 *   
 *  @license under the Apache License, Version 2.0 
 *
 *  @file    WavFileReader.java
 *  
 *  @version 1.0     
 *  @author  Jhuster
 *  @date    2016/03/19
 */
package com.zlm.audio.formats.wav;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BaseWAVFileReader {

	private DataInputStream mDataInputStream;
	private WavFileHeader mWavFileHeader;

	public boolean openFile(String filePath) throws IOException {
		if (mDataInputStream != null) {
			closeFile();
		}
		mDataInputStream = new DataInputStream(new FileInputStream(filePath));
		return readHeader();
	}

	public void closeFile() throws IOException {
		if (mDataInputStream != null) {
			mDataInputStream.close();
			mDataInputStream = null;
		}
	}

	public WavFileHeader getmWavFileHeader() {
		return mWavFileHeader;
	}

	public int readData(byte[] buffer, int offset, int count) {

		if (mDataInputStream == null || mWavFileHeader == null) {
			return -1;
		}

		try {
			int nbytes = mDataInputStream.read(buffer, offset, count);
			if (nbytes == -1) {
				return 0;
			}
			return nbytes;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return -1;
	}

	private boolean readHeader() {

		if (mDataInputStream == null) {
			return false;
		}

		WavFileHeader header = new WavFileHeader();

		byte[] intValue = new byte[4];
		byte[] shortValue = new byte[2];
		try {
			header.mChunkID = "" + (char) mDataInputStream.readByte()
					+ (char) mDataInputStream.readByte()
					+ (char) mDataInputStream.readByte()
					+ (char) mDataInputStream.readByte();
			System.out.println("Read file chunkID:" + header.mChunkID);

			mDataInputStream.read(intValue);
			header.mChunkSize = byteArrayToInt(intValue);
			System.out.println("Read file chunkSize:" + header.mChunkSize);

			header.mFormat = "" + (char) mDataInputStream.readByte()
					+ (char) mDataInputStream.readByte()
					+ (char) mDataInputStream.readByte()
					+ (char) mDataInputStream.readByte();
			System.out.println("Read file format:" + header.mFormat);

			header.mSubChunk1ID = "" + (char) mDataInputStream.readByte()
					+ (char) mDataInputStream.readByte()
					+ (char) mDataInputStream.readByte()
					+ (char) mDataInputStream.readByte();
			System.out.println("Read fmt chunkID:" + header.mSubChunk1ID);

			mDataInputStream.read(intValue);
			header.mSubChunk1Size = byteArrayToInt(intValue);
			System.out.println("Read fmt chunkSize:" + header.mSubChunk1Size);

			mDataInputStream.read(shortValue);
			header.mAudioFormat = byteArrayToShort(shortValue);
			System.out.println("Read audioFormat:" + header.mAudioFormat);

			mDataInputStream.read(shortValue);
			header.mNumChannel = byteArrayToShort(shortValue);
			System.out.println("Read channel number:" + header.mNumChannel);

			mDataInputStream.read(intValue);
			header.mSampleRate = byteArrayToInt(intValue);
			System.out.println("Read samplerate:" + header.mSampleRate);

			mDataInputStream.read(intValue);
			header.mBiteRate = byteArrayToInt(intValue) / 100;
			System.out.println("Read byterate:" + header.mBiteRate);

			mDataInputStream.read(shortValue);
			header.mBlockAlign = byteArrayToShort(shortValue);
			System.out.println("Read blockalign:" + header.mBlockAlign);

			mDataInputStream.read(shortValue);
			header.mBitsPerSample = byteArrayToShort(shortValue);
			System.out.println("Read bitspersample:" + header.mBitsPerSample);

			header.mSubChunk2ID = "" + (char) mDataInputStream.readByte()
					+ (char) mDataInputStream.readByte()
					+ (char) mDataInputStream.readByte()
					+ (char) mDataInputStream.readByte();
			System.out.println("Read data chunkID:" + header.mSubChunk2ID);

			mDataInputStream.read(intValue);
			header.mSubChunk2Size = byteArrayToInt(intValue);
			System.out.println("Read data chunkSize:" + header.mSubChunk2Size);

			System.out.println("Read wav file success !");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		mWavFileHeader = header;

		return true;
	}

	private static short byteArrayToShort(byte[] b) {
		return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getShort();
	}

	private static int byteArrayToInt(byte[] b) {
		return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
}
