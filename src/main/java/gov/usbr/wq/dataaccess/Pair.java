/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess;

public final class Pair<A,B>
{
	private final A _a;
	private final B _b;

	public Pair(A a ,B b)
	{
		_a = a;
		_b = b;
	}

	public A a()
	{
		return _a;
	}

	public B b()
	{
		return _b;
	}
}
