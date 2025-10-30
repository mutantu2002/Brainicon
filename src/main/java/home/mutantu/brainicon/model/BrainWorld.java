package home.mutantu.brainicon.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BrainWorld implements Serializable
{
	private static final long serialVersionUID = 4945277472151111878L;
	List<Columnocon> objects = new ArrayList<Columnocon>();
	private int width = 800;
    private int height = 600;
    public KeyboardState keyboardState;
    
	public BrainWorld(int width, int height)
	{
		super();
		this.width = width;
		this.height = height;
	}

	public void next(KeyboardState keysState)
	{
		this.keyboardState = keysState;
		for (Columnocon obj : objects) 
		{
			obj.next(this);
		}
	}
	
	public List<Columnocon> getObjects()
	{
		return Collections.unmodifiableList(objects);
	}

	public void addObject(Columnocon obj)
	{
		objects.add(obj);
	}

	public void addObjects(List<Columnocon> listObj)
	{
		for (Columnocon rubberObject : listObj)
		{
			objects.add(rubberObject);
		}
	}
	
	public void flipCoordinates()
	{
		for (Columnocon obj : objects) 
		{
			obj.flipCoordinates();
		}		
	}

	public double getWidth()
	{
		return width;
	}
	public double getHeight()
	{
		return height;
	}

	public void removeObject(int index) 
	{
		if (objects.size()>0)
		{
			objects.remove(objects.size()-1);
		}
	}   
}
