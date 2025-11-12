package home.mutantu.brainicon;

import home.mutantu.brainicon.controller.BrainFactory;
import home.mutantu.brainicon.controller.FrameRunner;
import home.mutantu.brainicon.model.BrainWorld;
import home.mutantu.brainicon.ui.WorldFrame;

public class Main
{
	public static void main(String[] args)
	{
		BrainWorld world = BrainFactory.createOneRectangleObjectWorld(200, 200);
		new Thread(new FrameRunner(world, new WorldFrame(200,200))).start();
	}
}
