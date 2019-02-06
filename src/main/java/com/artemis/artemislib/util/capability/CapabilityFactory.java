package com.artemis.artemislib.util.capability;

import java.util.concurrent.Callable;

public class CapabilityFactory implements Callable<ICap>
{
	@Override
	public ICap call() throws Exception
	{
		return new DeCap();
	}
}
