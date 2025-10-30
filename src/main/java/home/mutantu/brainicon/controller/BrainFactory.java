package home.mutantu.brainicon.controller;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.imageio.ImageIO;

import home.mutantu.brainicon.model.Constants;
import home.mutantu.brainicon.model.Columnocon;
import home.mutantu.brainicon.model.Neurocon;
import home.mutantu.brainicon.model.BrainWorld;

public class BrainFactory
{
	public static BrainWorld create3PointsObjectWorld()
	{
		BrainWorld world  = new BrainWorld(10,10);
		Columnocon obj = new Columnocon();
		obj.addPoint(obj);
		obj.addPoint(obj);
		obj.addPoint(100,110,obj);
		obj.linkPoints(0, 1, 10);
		obj.linkPoints(0, 2, 10);
		obj.linkPoints(2, 1, 10);
		world.addObject(obj);
		
		return world;
	}
	
	public static BrainWorld createFromFile(String path)
	{
		BrainWorld world=null;;
        FileInputStream fis;
		try 
		{
			fis = new FileInputStream(path);
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        world = (BrainWorld) ois.readObject();
	        ois.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		if (world == null) world = new BrainWorld(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
		Columnocon circle = createCircle(200, 100, 8, 20);
		circle.controllable=true;
		world.addObject(circle);
		
		return world;

	}
	public static BrainWorld createOneSquareObjectWorld(int initX,int initY,int numberPointsOnEdge, double distance)
	{
		BrainWorld world  = new BrainWorld(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT);
		world.addObject(createSquare(initX, initY, numberPointsOnEdge, distance));
		return world;
	}
	
	public static BrainWorld createOneRectangleObjectWorld(int initX,int initY,int numberPointsOnW, int numberPointsOnH, double distance)
	{
		BrainWorld world  = new BrainWorld(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT);
		world.addObject(createRectangle(initX, initY, numberPointsOnW, numberPointsOnH, distance));
		return world;
	}
	
	public static BrainWorld createOneRoundObjectWorld(int initX,int initY,int numberPointsOnDiameter, double distance)
	{
		BrainWorld world  = new BrainWorld(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT);
		world.addObject(createCircle(initX, initY, numberPointsOnDiameter, distance));
		return world;
	}
	
	public static BrainWorld create2ObjectsWorld(int initX,int initY,int numberPoints, double distance)
	{
		BrainWorld world  = new BrainWorld(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT);
		world.addObject(createCircle(initX+200, initY, numberPoints, distance));
		world.addObject(createSquare(initX, initY, numberPoints, distance));
		return world;
	}
	
	public static BrainWorld create3ObjectsWorld(int initX,int initY,int numberPoints, double distance)
	{
		BrainWorld world  = new BrainWorld(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT);
		Columnocon circle = createCircle(initX+200, initY, numberPoints, distance);
		circle.controllable=true;
		
//		world.addObject(createCircle(initX+200, initY+105, numberPoints, distance));
//		world.addObject(createCircle(initX+200, initY+220, numberPoints, distance));
//		world.addObject(createSquare(initX+350, initY-100, numberPoints, distance));
//		world.addObject(createCircle(initX+350, initY+105, numberPoints, distance));
//		world.addObject(createSquare(initX+350, initY+220, numberPoints, distance));
//		world.addObject(createSquare(initX, initY, numberPoints, distance));
//		world.addObject(createSquare(initX, initY+105, numberPoints, distance));
		world.addObject(createSquare(initX, initY+220, numberPoints, distance));
		world.addObject(createSquare(initX-100, initY, numberPoints, distance));
		//world.addObject(createSquare(initX-100, initY+105, numberPoints, distance));
		//world.addObject(createSquare(initX-100, initY+220, numberPoints, distance));
		return world;
	}
	
	public static Columnocon createCircle(int initX,int initY,int numberPointsOnDiameter, double distance)
	{
		double distanceDiagonal = distance*Math.sqrt(2);
		Columnocon obj = new Columnocon();
		if (numberPointsOnDiameter%2==0)
		{
			numberPointsOnDiameter++;
		}
		Neurocon center = obj.addPoint(initX, initY, obj);
		
		double radius = distance*(numberPointsOnDiameter-1)/2;
		double initXRectangle = initX-radius;
		double initYRectangle = initY-radius;
		
		for (int y=0;y<numberPointsOnDiameter;y++)
		{
			for(int x=0;x<numberPointsOnDiameter;x++)
			{
				Neurocon toAdd = new Neurocon(0,obj);
				toAdd.t0.x=initXRectangle+x*distance;
				toAdd.t0.y=initYRectangle+y*distance;
				if (center.getDistanceFrom(toAdd)<=radius+Constants.EPSILON)
				{
					obj.addPoint(initXRectangle+x*distance,initYRectangle+y*distance,10,/*x>numberPointsOnEdge/2?10:-10*/0);
				}
			}
		}
		linkAllLessThanDistance(obj, distanceDiagonal*3);
		return obj;
	}
	public static Columnocon createSquare(int initX,int initY,int numberPointsOnEdge, double distance)
	{
		return createRectangle(initX, initY, numberPointsOnEdge, numberPointsOnEdge, distance);
	}
	
	public static Columnocon createRectangle(int initX,int initY,int numberPointsOnW,int numberPointsOnH, double distance)
	{
		double distanceDiagonal = distance*Math.sqrt(2);
		Columnocon obj = new Columnocon();
		
		for (int y=0;y<numberPointsOnH;y++)
		{
			for(int x=0;x<numberPointsOnW;x++)
			{
				obj.addPoint(initX+x*distance,initY+y*distance,20,0);
			}
		}
		linkAllLessThanDistance(obj, distanceDiagonal*3);
		return obj;
	}
	
	public static BrainWorld createWorldFromImage(int initX,int initY, double distance)
	{
		BrainWorld world  = new BrainWorld(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT);
		world.addObject(createObjectFromImage("/om1.bmp", initX, initY, distance));
		return world;
	}
	
	public static Columnocon createObjectFromImage(String imageResourcePath, int initX,int initY, double distance)
	{
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(BrainFactory.class.getResourceAsStream(imageResourcePath));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return null;
		}
		double distanceDiagonal = distance*Math.sqrt(2);
		Columnocon obj = new Columnocon();
		
		for (int y=0;y<image.getHeight();y++)
		{
			for(int x=0;x<image.getWidth();x++)
			{
				int color = image.getRGB(x,y) & 0x00ffffff;
				if (color == 0x00ffffff)
				{
					obj.addPoint(initX+x*distance,initY+y*distance,20,0);
				}
			}
		}
		linkAllLessThanDistance(obj, distanceDiagonal*3);
		return obj;
	}
	
	private static void linkAllLessThanDistance(Columnocon obj, double maxDistance)
	{
		for (int x=0;x<obj.getPointCount();x++)
		{
			for (int y=0;y<obj.getPointCount();y++)
			{
				double actualDistance = obj.getPoint(x).getDistanceFrom(obj.getPoint(y));
				if (actualDistance<=maxDistance+Constants.EPSILON && x!=y)
				{
					obj.linkPoints(x, y, actualDistance);
				}
			}
		}
	}
	
	public static BrainWorld createOneParallelogramObjectWorld(int initX,int initY,int numberPointsOnW, int numberPointsOnH, double distance)
	{
		BrainWorld world  = new BrainWorld(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT);
		world.addObject(createParallelogram(initX, initY, numberPointsOnW, numberPointsOnH, distance,false));
		return world;
	}
	
	public static Columnocon createParallelogram(int initX,int initY,int numberPointsOnW,int numberPointsOnH, double distance, boolean directionRight)
	{
		double distanceDiagonal = distance*Math.sqrt(2);
		Columnocon obj = new Columnocon();
		int dir = -1;
		if (directionRight)
		{
			dir=1;
		}
		for (int y=0;y<numberPointsOnH;y++)
		{
			for(int x=0;x<numberPointsOnW;x++)
			{
				obj.addPoint(initX+dir*y*distance+x*distance,initY+y*distance,20,0);
			}
		}
		linkAllLessThanDistance(obj, distanceDiagonal*3);
		return obj;
	}
}
