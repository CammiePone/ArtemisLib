package com.artemis.artemislib.util.capability.resizing;

import java.util.concurrent.Callable;

public class ResizeCapFactory implements Callable<IResizeCap>
{
	@Override
	public IResizeCap call() throws Exception
	{
		return new DesizeCap();
	}
}
