package home.mutantu.brainicon.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import home.mutantu.brainicon.model.Constants;
import home.mutantu.brainicon.model.KeyboardState;
import home.mutantu.brainicon.model.Columnocon;
import home.mutantu.brainicon.model.Neurocon;
import home.mutantu.brainicon.model.BrainWorld;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class WorldFrame extends JFrame implements KeyListener
{
	private int width = 800;
    private int height = 600;
    
    RasterPanel drawingPanel;
    public KeyboardState keyboardState = new KeyboardState();
    int viewportX = 0;

    
    public WorldFrame(int width, int height)
    {
    	this.width = width;
    	this.height = height;
    	setSize(this.width+20, this.height+50);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	drawingPanel = new RasterPanel (this.width,this.height);
        add (drawingPanel);
        setVisible(true);
        addKeyListener(this);
    }

	public void buildFrame(BrainWorld world)
	{
		drawingPanel.empty();
		for (Columnocon obj : world.getObjects())
		{
			if (obj.controllable)
			{
				int center=0;
				for (Neurocon point : obj.getPoints())
				{
					center += (int)point.t0.x - viewportX;
				}
				center/=obj.getPoints().size();
				if (center<Constants.MARGIN)
				{
					viewportX+=center-Constants.MARGIN;
				}
				if (center>width-Constants.MARGIN)
				{
					viewportX+=center-width+Constants.MARGIN;
				}
			}
			
			for (Neurocon point : obj.getPoints())
			{
				drawingPanel.set4Pixels((int)point.t0.x, (int)point.t0.y, viewportX);
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode()==40)
		{
			keyboardState.isDownPressed = true;
		}
		else if (e.getKeyCode()==37)
		{
			keyboardState.isLeftPressed  = true;
		}
		else if (e.getKeyCode()==39)
		{
			keyboardState.isRightPressed  = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if (e.getKeyCode()==40)
		{
			keyboardState.isDownPressed = false;
		}
		else if (e.getKeyCode()==37)
		{
			keyboardState.isLeftPressed  = false;
		}
		else if (e.getKeyCode()==39)
		{
			keyboardState.isRightPressed  = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
	}
}
