package home.mutantu.brainicon.util;

import home.mutantu.brainicon.model.Coordinates;

public class LinesUtil
{
	public static double distance(Coordinates coord1, Coordinates coord2)
	{
		return Math.sqrt((coord1.x-coord2.x)*(coord1.x-coord2.x)+(coord1.y-coord2.y)*(coord1.y-coord2.y));
	}
	
	public static double distanceSquared(Coordinates coord1, Coordinates coord2)
	{
		return (coord1.x-coord2.x)*(coord1.x-coord2.x)+(coord1.y-coord2.y)*(coord1.y-coord2.y);
	}
	
	public static double distanceToSegment(Coordinates point, Coordinates segEdge1, Coordinates segEdge2)
	{
		return distance(point, closestPointToSegment(point, segEdge1, segEdge2));
	}
	
	public static Coordinates closestPointOutsideToSegment(Coordinates point, Coordinates segEdge1, Coordinates segEdge2)
	{
		Coordinates closest = closestPointToSegment(point, segEdge1, segEdge2);
		return  new Coordinates(point.x + 0.5 * (closest.x - point.x), point.y + 0.5 * (closest.y - point.y), 0, 0);
	}
	public static Coordinates closestPointToSegment(Coordinates point, Coordinates segEdge1, Coordinates segEdge2)
	{
		double l2 = distanceSquared(segEdge1, segEdge2);
		if (l2 == 0) 
		{
			return segEdge1;
		}
		double t = ((point.x - segEdge1.x) * (segEdge2.x - segEdge1.x) + (point.y - segEdge1.y) * (segEdge2.y - segEdge1.y)) / l2;
		if (t < 0)
		{
			return segEdge1;
		}
		if (t > 1)
		{
			return segEdge2;
		}
		Coordinates perpendicular  = new Coordinates(segEdge1.x + t * (segEdge2.x - segEdge1.x), segEdge1.y + t * (segEdge2.y - segEdge1.y), 0, 0);
		return perpendicular;
	}
}
