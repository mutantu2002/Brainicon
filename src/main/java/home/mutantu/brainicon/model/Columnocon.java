package home.mutantu.brainicon.model;

import java.io.Serializable;
import java.util.Random;

public class Columnocon implements Serializable {
	private static final long serialVersionUID = 7282824740730637433L;

	int X;
	int Y;
	double j=1.;
	double beta=0.5;
	public byte[][] spins;
	public Columnocon(int numberPointsOnH, int numberPointsOnW) {
		X=numberPointsOnW;
		Y=numberPointsOnH;
		spins = new byte[X][Y];
	}

	public void next(BrainWorld rubberWorld) {
		Random r= new Random();
		for (int i = 0; i < X*Y; i++) {
			int offset = r.nextInt(X*Y);
			int x = offset % X;
			int y = offset / X;
			double flipProbability = Math.exp(-beta * deltaE(x, y));
			if (flipProbability > 1) {
				flipProbability = 1;
			}
			if (r.nextDouble() <= flipProbability) {
				spins[x][y] = (byte) -spins[x][y];
			}

		}
	}

	private double deltaE(int x,int y){
		double neighborSum=getNeighborSum(x,y,1);
		return 2*j*spins[x][y]*neighborSum;
	}

	private double getEnergy(){
		double energy=0;
		for (int x=0;x<X;x++){
			for (int y=0;y<Y;y++){
				double neighborSum=getNeighborSum(x,y,1);
				energy+=-j*spins[x][y]*neighborSum/2;
			}
		}
		return energy;
	}

	private double getNeighborSum(int x,int y, int radius){
		double sum=0;
		for (int dx=-radius;dx<=radius;dx++){
			for (int dy=-radius;dy<=radius;dy++){
				if (dx==0 && dy==0) continue;
				int nx=(x+dx+X)%X;
				int ny=(y+dy+Y)%Y;
				sum+=spins[nx][ny];
			}
		}
		return sum;	
	}
}
