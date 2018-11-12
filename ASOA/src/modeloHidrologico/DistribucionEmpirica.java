package modeloHidrologico;

import java.util.*;

public class DistribucionEmpirica {
	
	private	ArrayList<Integer> x = new ArrayList <Integer>();
	private ArrayList<Double> fx = new ArrayList <Double>();
	
	public DistribucionEmpirica() {
		this.setX(new ArrayList<Integer> (Arrays.asList(-30,-20,-10,0,10,20,30)));
		this.setFx(new ArrayList<Double>(Arrays.asList(0.0372,0.2507,0.4357,0.5642,0.7493,0.9628,1.0000)));
	}	
	public ArrayList<Integer> getX() {
		return x;
	}
	public ArrayList<Double> getFx() {
		return fx;
	}
	private void setX(ArrayList<Integer> x) {
		this.x = x;
	}
	private void setFx(ArrayList<Double> fx) {
		this.fx = fx;
	}

	
		
}
