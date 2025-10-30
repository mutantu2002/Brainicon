package home.mutantu.brainicon.controller;

import home.mutantu.brainicon.model.Constants;
import home.mutantu.brainicon.model.BrainWorld;
import home.mutantu.brainicon.ui.WorldFrame;

public class BrainRunner implements Runnable
{
	BrainWorld world;
	WorldFrame frame;
	private boolean running = true;
	
	public BrainRunner(BrainWorld world,WorldFrame frame)
	{
		this.world = world;
		this.frame = frame;
	}
	@Override
	public void run()
	{
		int frames=0;
		long t0=System.currentTimeMillis();
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
			world.next(frame.keyboardState);
			world.flipCoordinates();
			frame.buildFrame(world);
			frames++;
			if (frames==1000)
			{
				
				long t1 = System.currentTimeMillis();
				double framesperSec = (double)1000/((double)(t1-t0)/1000.);
				t0=System.currentTimeMillis();
				frames=0;
				frame.setTitle(new Double(framesperSec).toString());
			}
			
		}
	}

}
