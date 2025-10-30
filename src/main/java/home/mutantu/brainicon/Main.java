package home.mutantu.brainicon;

import home.mutantu.brainicon.controller.BrainFactory;
import home.mutantu.brainicon.controller.BrainRunner;
import home.mutantu.brainicon.model.Constants;
import home.mutantu.brainicon.ui.WorldFrame;

public class Main
{
	public static void main(String[] args)
	{
		new Thread(new BrainRunner(BrainFactory.createOneRectangleObjectWorld(0, 0, 200, 200, 3), new WorldFrame(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT))).start();
	}
}
