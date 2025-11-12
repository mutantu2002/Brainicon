package home.mutantu.brainicon.controller;

import home.mutantu.brainicon.model.BrainWorld;
import home.mutantu.brainicon.model.Columnocon;

public class BrainFactory
{
	public static BrainWorld createOneRectangleObjectWorld(int numberPointsOnW, int numberPointsOnH)
	{
		BrainWorld world  = new BrainWorld(numberPointsOnW,numberPointsOnW);
		world.addObject(new Columnocon(numberPointsOnH,numberPointsOnW));
		return world;
	}

}
