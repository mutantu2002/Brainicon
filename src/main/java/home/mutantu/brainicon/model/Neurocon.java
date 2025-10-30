package home.mutantu.brainicon.model;


import home.mutantu.brainicon.util.LinesUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Neurocon implements Serializable
{
	private static final long serialVersionUID = -8060160709469620140L;
	public int index;
	public Coordinates t0 = new Coordinates(100,100,0,0);
	public Coordinates t1 = new Coordinates(100,100,0,0);
	private Columnocon parent;
	List<Synapcon> links = new ArrayList<Synapcon>();
	private boolean nextDone = false;
	
	public Neurocon(int index, Columnocon parent)
	{
		this.index=index;
		this.parent=parent;
	}

	public void addLink(Neurocon point, double distance)
	{
		links.add(new Synapcon(point, distance));
	}

	public double getDistanceFrom(Neurocon point)
	{
		return Math.sqrt((t0.x-point.t0.x)*(t0.x-point.t0.x)+(t0.y-point.t0.y)*(t0.y-point.t0.y));
	}
	
	public boolean isLinkedTo(int index)
	{
		for (Synapcon link : links)
		{
			if (index == link.point.index)
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isLinkedTo(Neurocon rubberPoint)
	{
		for (Synapcon link : links)
		{
			if (rubberPoint == link.point)
			{
				return true;
			}
		}
		return false;
	}
	
	public void next(BrainWorld rubberWorld)
	{
		if (nextDone)
		{
			return;
		}
		euler(rubberWorld.keyboardState);
		checkCollisions(rubberWorld);
		checkLimits(rubberWorld);
		nextDone = true;
	}

	private Force calculateForce(Coordinates coord, KeyboardState keyboardState)
	{
		Force force = new Force();
		for (Synapcon link : links)
		{
			double realDistance = LinesUtil.distance(coord, link.point.t0);
			double valForce = Constants.ELASTIC_CONSTANTS*(link.distance-realDistance);//*Math.abs((link.distance-realDistance));//*(link.distance-realDistance);
			if (realDistance==0)
			{
				realDistance=0.1;
			}
			force.fx+=(coord.x-link.point.t0.x)/realDistance * valForce;
			force.fy+=(coord.y-link.point.t0.y)/realDistance * valForce;
		}

		force.fy+=Constants.GRAVITY;
		if (parent.controllable)
		{
			if (keyboardState!=null && keyboardState.isDownPressed)
			{
				force.fy+=Constants.GRAVITY*9;
			}
			
			if (keyboardState!=null && keyboardState.isRightPressed)
			{
				force.fx+=Constants.GRAVITY*3;
			}
	
			if (keyboardState!=null && keyboardState.isLeftPressed)
			{
				force.fx-=Constants.GRAVITY*3;
			}
		}
		
		force.fx-=coord.vx*Constants.FRICTION;
		force.fy-=coord.vy*Constants.FRICTION;
		
		return force;
	}

	private void checkCollisions(BrainWorld rubberWorld)
	{
		for (Columnocon obj : rubberWorld.getObjects())
		{
			if (obj==parent)
			{
				continue;
			}
			if (obj.isInside(t1))
			{
				t1 = obj.closestPointToContour(t1);
			}
		}
	}
	
	private void checkLimits(BrainWorld rubberWorld)
	{
//		if (t1.x<0)
//		{
//			t1.x=0;
//			t1.vx=0;
//		}
		if (t1.y<0)
		{
			t1.y=0;
			t1.vy=0;
		}
//		if (t1.x>=rubberWorld.getWidth()-1)
//		{
//			t1.x=rubberWorld.getWidth()-2;
//			t1.vx=0;
//		}
		if (t1.y>=rubberWorld.getHeight()-1)
		{
			t1.y=rubberWorld.getHeight()-2;
			t1.vy=0;
		}
	}

	public void flipCoordonates()
	{
		t0.x=t1.x;
		t0.y=t1.y;
		t0.vx=t1.vx;
		t0.vy=t1.vy;
		nextDone = false;
	}
	
	private void euler(KeyboardState keyboardState)
	{
		Force force = calculateForce(t0, keyboardState);
		t1.vx= t0.vx + force.fx*Constants.DT;
		t1.vy= t0.vy + force.fy*Constants.DT;
		
		t1.x = t0.x + t1.vx*Constants.DT;
		t1.y = t0.y + t1.vy*Constants.DT;
	}
	
	@SuppressWarnings("unused")
	private void rungeKutta2Order()
	{
		Force forceN = calculateForce(t0, null);
		double vxN_1 = t0.vx+forceN.fx*Constants.DT;
		double vyN_1 = t0.vy+forceN.fy*Constants.DT;
		Coordinates tmpCoord = new Coordinates(t0.x+vxN_1*Constants.DT, t0.y+vyN_1*Constants.DT, vxN_1, vyN_1);
		Force forceN_1 = calculateForce(tmpCoord, null);
		
		System.out.println(forceN.fx+" "+forceN_1.fx);
		
		t1.vx= t0.vx + ((forceN.fx + forceN_1.fx)/2.)*Constants.DT;
		t1.vy= t0.vy + ((forceN.fy + forceN_1.fy)/2.)*Constants.DT;
		
		t1.x = t0.x + ((t0.vx+vxN_1)/2.)*Constants.DT;
		t1.y = t0.y + ((t0.vy+vyN_1)/2.)*Constants.DT;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((t0 == null) ? 0 : t0.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Neurocon other = (Neurocon) obj;
		if (t0 == null)
		{
			if (other.t0 != null)
				return false;
		} else if (!t0.equals(other.t0))
			return false;
		return true;
	}
}
