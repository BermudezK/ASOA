package modeloHidrologico;
import java.util.ArrayList;

/*
 * %METODO MULTIPLICATIVO DE LAS CONGRUENCIAS
%  PARAMETROS
%semilla   = valor inicial primo por el cual se generara la serie
%t         = Cualquier entero, usado para calcular el valor del parametro pa.
%p         = Uno de los siguientes valores:  3, 11, 13, 19, 21, 27, 29, 37, 53, 59, 61, 67, 69, 77, 83, 91;
%   t y p son usados para calcular el valor del parametro a)
%modulo    = es el valor representado po la unidad seguida de ceros 
%n         = el tamaño de la serie de numeros a generar

 * 
 * 
*/
public class MetodoMultiCongruencia {

   //se definene las variables

	int semilla;
	int a;
	int modulo;
	int n;
	
	public MetodoMultiCongruencia(int v_semilla, int v_t, int v_p, int v_modulo, int v_n) {
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
	public void setSemilla(int semilla) {
		this.semilla = semilla;
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public int getModulo() {
		return modulo;
	}
	public void setModulo(int modulo) {
		this.modulo = modulo;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
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
	public ArrayList <Double> ObtenerSerie() {
		ArrayList<Double> serie = new ArrayList<Double>();
		
		double aux = 0;
		
		for (int i = 0; i < this.getN(); i++) {
			aux = (double) this.getSemilla()/ (double) (this.getModulo());
			serie.add(aux);

			/**
			 * aplico la fórmula del método multiplicativo de las congruencias
			 */
			this.setSemilla((this.getA()*this.getSemilla())%this.getModulo());
		}
		return serie;
	}
}