package home.mutantu.brainicon.model;

import java.io.Serializable;

public class Coordinates implements Serializable
{
	private static final long serialVersionUID = -3387208409285250465L;
	public double x;
	public double y;
	public double vx;
	public double vy;
	
	public Coordinates(double d, double e, double vxTmp, double vyTmp)
	{
		this.x=d;
		this.y=e;
		this.vx=vxTmp;
		this.vy=vyTmp;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Coordinates other = (Coordinates) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
}
