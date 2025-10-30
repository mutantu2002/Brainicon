package home.mutantu.brainicon.model;


import home.mutantu.brainicon.util.ConvexHull;
import home.mutantu.brainicon.util.LinesUtil;

import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Columnocon implements Serializable
{
	private static final long serialVersionUID = 7282824740730637433L;
	Map<Integer, Neurocon> points = new HashMap<Integer,Neurocon>();
	List<Neurocon> contour = new ArrayList<Neurocon>();
	Point center = new Point();
	public boolean controllable = false;

	public int getPointCount()
	{
		return points.size();
	}

	public synchronized void addPoint(Columnocon parent)
	{
		Neurocon point = new Neurocon(points.size(), parent);
		point.t0.x=100+10*(points.size());
		point.t0.y=100+10*(points.size());
		points.put(points.size(),point);
	}
	
	public synchronized Neurocon addPoint(int x, int y, Columnocon parent)
	{
		Neurocon point = new Neurocon(points.size(), parent);
		point.t0.x=x;
		point.t0.y=y;
		points.put(points.size(),point);
		return point;
	}

	public synchronized Neurocon addPoint(int x, int y)
	{
		return addPoint(x, y, this);
	}
	
	public synchronized void addPoint(double d, double e, double vx, double vy)
	{
		int size = points.size();
		Neurocon point = new Neurocon(size, this);
		point.t0.x=d;
		point.t0.y=e;
		point.t0.vx=vx;
		point.t0.vy=vy;
		points.put(size,point);
	}
	
	public void linkPoints(int index1, int index2, double distance)
	{
		if (index1>=points.size() || index2>= points.size())
		{
			throw new IndexOutOfBoundsException();
		}
		if (areLinked(index1, index2))
		{
			return;
		}
		points.get(index1).addLink(points.get(index2),distance);
		points.get(index2).addLink(points.get(index1),distance);
	}

	public boolean areLinked(int index1, int index2)
	{
		if (index1>=points.size() || index2>= points.size())
		{
			throw new IndexOutOfBoundsException();
		}
		return points.get(index1).isLinkedTo(points.get(index2));
	}

	public void next(BrainWorld rubberWorld)
	{
		for (Integer pointIndex : points.keySet())
		{
			points.get(pointIndex).next(rubberWorld);
		}
		computeContour();
	}

	public void computeContour() 
	{
		if (points.size()>1)
		{
			contour = ConvexHull.compute(getPoints());
		}
		else
		{
			contour = new ArrayList<Neurocon>();
		}
	}

	public List<Neurocon> get2ClosestPoints(Coordinates coord)
	{
		List<Neurocon> result = new ArrayList<Neurocon>();
		double distance = Double.MAX_VALUE;
		int index = 0;
		for (int i=0;i<contour.size();i++)
		{
			double distanceTmp = LinesUtil.distanceToSegment(coord,  contour.get(i).t0,  contour.get((i+1)%contour.size()).t0);
			if (distance>distanceTmp)
			{
				distance = distanceTmp;
				index = i;
			}
		}
		result.add(contour.get(index));
		result.add(contour.get((index+1)%contour.size()));
		return result;
	}
	public Coordinates closestPointToContour(Coordinates coord)
	{
		List<Neurocon> closer = get2ClosestPoints(coord);
		return LinesUtil.closestPointOutsideToSegment(coord, closer.get(0).t0, closer.get(1).t0);
	}
	public boolean isInside(Coordinates coord)
	{
		Polygon contourPolygon = new Polygon();
		for (Neurocon pointContour : contour)
		{
			contourPolygon.addPoint((int)pointContour.t0.x, (int)pointContour.t0.y);
		}
		return contourPolygon.contains(coord.x, coord.y);
	}
	public List<Neurocon> getPoints()
	{
		return Collections.unmodifiableList(new ArrayList<Neurocon>(points.values()));
	}

	public Neurocon getPoint(int index )
	{
		return points.get(index);
	}
	
	public void flipCoordinates()
	{
		for (Integer pointIndex : points.keySet())
		{
			points.get(pointIndex).flipCoordonates();
		}
	}

	public List<Neurocon> getContour()
	{
		return Collections.unmodifiableList(contour);
	}

	public boolean hasPoint(Neurocon point)
	{
		for (Integer pointIndex : points.keySet())
		{
			if (point.equals(points.get(pointIndex)))
			{
				return true;
			}
		}
		return false;
	}

	public Neurocon hasPoint(double x, double y)
	{
		Coordinates coord = new Coordinates(x, y, 0, 0);
		for (Integer pointIndex : points.keySet())
		{
			if (coord.equals(points.get(pointIndex).t0))
			{
				return points.get(pointIndex);
			}
		}
		return null;
	}

	public synchronized void addPoint(Neurocon point)
	{
		int size = points.size();
		points.put(size,point);
	}

	public void clear() 
	{
		points.clear();
		contour = new ArrayList<Neurocon>();
	}
}
