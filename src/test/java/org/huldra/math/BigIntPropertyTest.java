// -*- c-basic-offset: 8; indent-tabs-mode: t; -*-

package org.huldra.math;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import net.jqwik.api.*;
import org.opentest4j.AssertionFailedError;

class BigIntPropertyTest {
	private static final char[] hex = "0123456789ABCDEF".toCharArray();

	@Property
	void toByteBuffer(@ForAll long n) {
		BigInteger bigInteger = BigInteger.valueOf(n);
		BigInt bigInt = new BigInt(n);
		ByteBuffer expected = ByteBuffer.wrap(bigInteger.toByteArray());
		ByteBuffer actual = bigInt.toByteBuffer();
		assertEquals(expected, actual);
	}

	private static void assertEquals(ByteBuffer expected, ByteBuffer actual) {
		if (expected.equals(actual)) return;
		StringBuilder sb = new StringBuilder("expected: ");
		appendBuffer(sb, expected);
		sb.append(" but was: ");
		appendBuffer(sb, actual);
		throw new AssertionFailedError(sb.toString(), expected, actual);
	}

	private static void appendBuffer(StringBuilder sb, ByteBuffer buf) {
		sb.append("0x");
		for (int i = buf.position(); i < buf.capacity(); i++) {
			byte b = buf.get(i);
			sb.append(hex[(b >> 4) & 0xf]);
			sb.append(hex[b & 0xf]);
		}
	}
}
