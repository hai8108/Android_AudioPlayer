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

package com.zlm.audio;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Author: Denis Tulskiy Date: 1/15/11
 */
public class Buffer {
	private Queue<Integer> when = new LinkedList<Integer>();
	private RingBuffer buffer;
	private int bytesLeft = 0;

	public Buffer(int size) {
		buffer = new RingBuffer(size);
	}

	public Buffer() {
		this(65536);
	}

	public void open() {

		int bytesLeft = available();
		for (Integer left : when) {
			bytesLeft -= left;
		}
		// when.add(bytesLeft);
		this.bytesLeft = bytesLeft;

	}

	public void pollNextTrack() {
		buffer.setEOF(false);

		if (!when.isEmpty()) {
			bytesLeft = when.poll();
		} else {
			bytesLeft = -1;
		}
	}

	public void write(byte[] b, int off, int len) {
		buffer.put(b, off, len);
	}

	public int read(byte[] b, int off, int len) {
		if (bytesLeft > 0) {
			if (bytesLeft < len) {
				len = bytesLeft;
			}
			bytesLeft -= len;
		} else if (bytesLeft == 0) {
			return -1;
		}
		return buffer.get(b, off, len);
	}

	public synchronized int available() {
		return buffer.getAvailable();
	}

	public int size() {
		return buffer.size();
	}

	public void flush() {
		buffer.empty();
	}

}
