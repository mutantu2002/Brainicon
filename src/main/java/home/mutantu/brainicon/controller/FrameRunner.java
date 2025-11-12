package home.mutantu.brainicon.controller;

import home.mutantu.brainicon.model.Constants;
import home.mutantu.brainicon.model.BrainWorld;
import home.mutantu.brainicon.ui.WorldFrame;

public class FrameRunner implements Runnable
{
	BrainWorld world;
	WorldFrame frame;
	private boolean running = true;
	
	public FrameRunner(BrainWorld world,WorldFrame frame)
	{
		this.world = world;
		this.frame = frame;
	}
	@Override
	public void run()
	{
		while(running)
		{
			if (Constants.PAUSE_MILISEC>0)
			{
				try
				{
					Thread.sleep(Constants.PAUSE_MILISEC);
				}
				catch (InterruptedException e)
				{
				}
			}
			frame.buildFrame(world);
		}
	}

}
