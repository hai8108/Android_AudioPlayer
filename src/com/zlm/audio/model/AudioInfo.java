package com.zlm.audio.model;

import java.io.File;

/**
 * 歌曲信息类
 * 
 * @author zhangliangming
 * 
 */
public class AudioInfo {
	// song info
	private int sampleRate;
	private int channels;
	private int bps;
	private int bitrate;
	private long totalSamples;
	private boolean cueEmbedded;
	private String cueLocation;
	private String codec;
	private String encoder;

	private String filePath;
	private String fileExt;
	private long playedProgress;
	private long duration;
	private String durationStr;
	private long fileSize;
	private String fileSizeStr;
	private int frameSize;

	public int getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}

	public int getChannels() {
		return channels;
	}

	public void setChannels(int channels) {
		this.channels = channels;
	}

	public int getBps() {
		return bps;
	}

	public void setBps(int bps) {
		this.bps = bps;
	}

	public int getBitrate() {
		return bitrate;
	}

	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

	public long getTotalSamples() {
		return totalSamples;
	}

	public void setTotalSamples(long totalSamples) {
		this.totalSamples = totalSamples;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public boolean isCueEmbedded() {
		return cueEmbedded;
	}

	public void setCueEmbedded(boolean cueEmbedded) {
		this.cueEmbedded = cueEmbedded;
	}

	public String getCueLocation() {
		return cueLocation;
	}

	public void setCueLocation(String cueLocation) {
		this.cueLocation = cueLocation;
	}

	public String getCodec() {
		return codec;
	}

	public void setCodec(String codec) {
		this.codec = codec;
	}

	public String getEncoder() {
		return encoder;
	}

	public void setEncoder(String encoder) {
		this.encoder = encoder;
	}

	public long getPlayedProgress() {
		return playedProgress;
	}

	public void setPlayedProgress(long playedProgress) {
		this.playedProgress = playedProgress;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getDurationStr() {
		return durationStr;
	}

	public void setDurationStr(String durationStr) {
		this.durationStr = durationStr;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileSizeStr() {
		return fileSizeStr;
	}

	public void setFileSizeStr(String fileSizeStr) {
		this.fileSizeStr = fileSizeStr;
	}

	public File getFile() {
		return new File(getFilePath());
	}

	public int getFrameSize() {
		return frameSize;
	}

	public void setFrameSize(int frameSize) {
		this.frameSize = frameSize;
	}

}
