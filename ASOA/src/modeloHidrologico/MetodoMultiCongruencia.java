package modeloHidrologico;
import java.util.ArrayList;
import java.util.stream.Stream;

/*
 * %METODO MULTIPLICATIVO DE LAS CONGRUENCIAS
%  PARAMETROS
%semilla   = valor inicial primo por el cual se generara la serie
%t         = Cualquier entero, usado para calcular el valor del parametro pa.
%p         = Uno de los siguientes valores:  3, 11, 13, 19, 21, 27, 29, 37, 53, 59, 61, 67, 69, 77, 83, 91;
%   t y p son usados para calcular el valor del parametro a)
%modulo    = es el valor representado po la unidad seguida de ceros 
%n         = el tamano de la serie de numeros a generar

 * 
 * 
*/
class MetodoMultiCongruencia {

   //se definene las variables

	private int semilla;
	private int a;
	private int modulo;
	private int n;
	
	MetodoMultiCongruencia(int v_semilla, int v_t, int v_p, int v_modulo, int v_n) {
		this.setA(200*v_t + v_p);
		this.setModulo(v_modulo);
		this.setN(v_n);
		this.setSemilla(v_semilla);
		

		/**
		 * Evaluamos si el valor de la semilla es un numero primo, si no lo es 
		 * entonces evaluamos el siguiente valor 
		 */
		while (!this.esPrimo(this.getSemilla())){
			this.setSemilla(this.getSemilla()+1);
		}
	}
	
	public int getSemilla() {
		return semilla;
	}
	private void setSemilla(int semilla) {
		this.semilla = semilla;
	}
	public int getA() {
		return a;
	}
	private void setA(int a) {
		this.a = a;
	}
	public int getModulo() {
		return modulo;
	}
	private void setModulo(int modulo) {
		this.modulo = modulo;
	}
	public int getN() {
		return n;
	}
	private void setN(int n) {
		this.n = n;
	}
	//se evalua si el valor es primo
	private boolean esPrimo(int semilla){
		boolean esPrimo = true;
		
		for (int i=2; i < semilla; i++) {
			if (semilla%i == 0) {
				esPrimo = false;
			}
		}
		return esPrimo;
	}
	
	/**
	 * @ArrayList 
	 * permitira obtener un array list con aquellos valores de la serie obtenida por el metodo
	 */
	private double MetMultiCong(){
    	double aux = (double) semilla/ (double) (modulo);
		semilla = (a* semilla)% modulo;
		return aux;
    }
	
	public ArrayList <Double> ObtenerSerie() {
		ArrayList<Double> serie = new ArrayList<Double>();
		long init = 0;
		long finish = 0;
		
		init = System.nanoTime();
		//omp parallel
		{
			
			Stream.iterate(1, x -> x + 1).limit(n).forEach(item -> serie.add(MetMultiCong()));
			
		}
		finish = System.nanoTime();
		
		System.out.println("Duración: " + ( finish - init)/1e6 + " ms");
		return serie;
		
	}
}