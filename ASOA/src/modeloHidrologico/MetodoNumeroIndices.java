package modeloHidrologico;
import java.util.ArrayList;
import java.util.stream.Stream;
class MetodoNumeroIndices {
	
	private ArrayList <Integer> x = new ArrayList <Integer>();
	private ArrayList <Double> fx = new ArrayList <Double>();
	private ArrayList <Double> serie = new ArrayList <Double>();
	
	/**
	 * @param x
	 * @param fx
	 * @param serie
	 * 
	 * <h1>MetodoNumeroIndices</h1>
	 * <p>
	 * constructor de la clase MetodoNumeroIndices
	 * </p>
	 */
	MetodoNumeroIndices(ArrayList<Integer> x, ArrayList<Double> fx, ArrayList<Double> serie) {
                
		this.setFx(fx);
		this.setX(x);
		this.setSerie(serie);
	}
	
	public ArrayList<Integer> getX() {
		return x;
	}
	public ArrayList<Double> getFx() {
		return fx;
	}
	public ArrayList<Double> getSerie() {
		return serie;
	}
	private void setX(ArrayList<Integer> x) {
		this.x = x;
	}
	private void setFx(ArrayList<Double> fx) {
		this.fx = fx;
	}
	private void setSerie(ArrayList<Double> serie) {
		this.serie = serie;
	}


	/**
	 * @return muestra Aleatoria
	 * <h1> aplicaNumerosIndices() </h1>
	 * <p>
	 * Generador de la Muestra Aleatoria aplicando el metodo de los Numeros Indices
	 * </p>
	 * 
	 */

	private int MetNumerosIndices(int indice) {
		int j=0;	
		while(this.getSerie().get(indice) > this.getFx().get(j)) {	
			j++;
		}
		
		return (this.getX().get(j));
	}
	
	public ArrayList<Integer> obtenerMuestra (){
		ArrayList <Integer> muestra = new ArrayList<Integer>();
		long init = 0;
		long finish = 0;
		init = System.nanoTime();
		//omp parallel
		{		
			Stream.iterate(1, x -> x + 1).limit(this.getSerie().size()).forEach(item -> muestra.add(MetNumerosIndices(item-1)));
		}
		finish = System.nanoTime();
		
		System.out.println("Duracion Metodo Numero Indices: " + (finish - init)/1e6 + " ms");
		return muestra;
	}
	
	
	
}