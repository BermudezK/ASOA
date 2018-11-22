package modeloHidrologico;
import java.util.ArrayList;
/*
 * %el metodo calcula si una cierta serie sigue una distribucion uniforme 
%PARAMETROS
% serie: serie pseudoaleatoria a evaluar
% ni: numero de subintervalis que analisaremos
% alpha: es el minimo error que podemos cometer al rechazar la hipotesis cuando esta es
% verdadera [0;1]

%RETORNO
%valores: VECTOR DE VALORES OBTENIDOS DE LA APLICACION DE METODO DE CHI^2
%[FRECUENCIA OBSERVADA, FRECUENCIA ESPERADA, COCIENTES]
%ho: resultado final de la aplicacion de metodo {true, false}
%chi2o: el valor de chi obtenido 
%chi2t: el valor de la tabla de chi de acuerdo a los grados de libertad (ni-1) y
%nivel de significancia (alpha)
*/
public class Chi2 {


	private ArrayList<Double> serie = new ArrayList<Double>();
	private int ni;
	private double alpha;
	private ArrayList<Double> fo = new ArrayList<Double>();
	private ArrayList<Double> fe = new ArrayList<Double>();
	private ArrayList<Double> cocientesDiferencias = new ArrayList<Double>();
	private double chi2Obtenido;
	private double chi2Teorico;
	private boolean h0;

		/**
		 * @param serie
		 * @param ni
		 * @param alpha
		 */
		public Chi2(ArrayList<Double> serie, int ni, double alpha ) {
		this.setSerie(serie);
		this.setNi(ni);
		this.setAlpha(alpha);
		
//		cargamos los valores en los arrays de las frecuencias y los cocientes y calculamos el test de hipotesis
		this.obtenerFrecuenciaEsperada();
		this.obtenerFrecuenciaObtenida();
		this.obtenerCocientes();			
		
		this.setChi2Teorico(new Chi2Tabla().getValue(1-this.getAlpha(), this.getNi()-1));

		this.TestHipotesis();
		
	}
	
	/**
	 * BLOQUE SE SETERS Y GETERS
	 * */
	private ArrayList<Double> getSerie() {
		return serie;
	}
	private void setSerie(ArrayList<Double> serie) {
		this.serie = serie;
		
	}
	private int getNi() {
		return ni;
	}
	private void setNi(int ni) {
		this.ni = ni;
	}
	private double getAlpha() {
		return alpha;
	}
	private void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	
	public ArrayList<Double> getFo() {
		return fo;
	}
	public ArrayList<Double> getFe() {
		return fe;
	}
	public ArrayList<Double> getCocientesDiferencias() {
		return cocientesDiferencias;
	}
	public double getChi2Obtenido() {
		return chi2Obtenido;
	}
	private void setChi2Obtenido(double chi2Obtenido) {
		this.chi2Obtenido = chi2Obtenido;
	}
	public boolean getH0() {
		return h0;
	}
	private void setH0(boolean h0) {
		this.h0 = h0;
	}
	public double getChi2Teorico() {
		return chi2Teorico;
	}
	private void setChi2Teorico(double chi2Teorico) {
		this.chi2Teorico = chi2Teorico;
	}

	/**
	 * <h1> obtenerFrecuenciaEsperada() </h1>
	 * <p> permite cargar el vector de frecuencias esperadas que se conforma de la division entre el tamano de la serie y el numero de subintervalos que se analiza </p>
	 * <br>
	 * <p>Vector de frecuencia esperada</p>
	 */
	private void obtenerFrecuenciaEsperada() {
		double valor;
		int n = this.getSerie().size();
		for (int i=0;i< this.getNi();i++) {
			valor = (double) (n / this.getNi());
			this.getFe().add(valor);
		}
	}
	
	//Vector de frecuencia obtenidas
	/**
	 * <h1> obtenerFrecuenciaObtenida() </h1>
	 * <p> permite cargar el vector de frecuencias obtenidas que se conforma de las veces que un valor se encuentra en la serie</p>
	 * <br>
	 * <p>Vector de frecuencia obtenidas</p>
	 */
	private void obtenerFrecuenciaObtenida() {
		int n = this.getSerie().size();

		for (int i=0;i < this.getNi();i++) {

			this.getFo().add(0.00);
			for (int k = 0; k < n; k++) {
				
				double inicio= i/(double)this.getNi();
				double fin = (i+1)/(double)this.getNi();


				if((this.getSerie().get(k) > inicio ) && (this.getSerie().get(k) <= fin )) {
					double acu = this.getFo().get(i) + 1;
					this.getFo().set(i,acu);
				}
			}
		}
		
		
	}
	
	/**
	 * <h1> cocientes() </h1>
	 * <p> calcula el cociente obtenido de cada uno de los valores de los vectores de Frecuescias Obtenidas y Frecuencias Esperadas mediante la aplicacion de la formula ((fo(i)-fe(i))^2)/fe(i). <br> calcula el chi2 Obtenido</p>
	 */
	private void obtenerCocientes(){
		double valor;
		
		for (int i=0; i<this.getNi();i++) {
			valor = Math.pow(((double)this.getFo().get(i) - (double)this.getFe().get(i)),2.00)/((double)this.getFe().get(i));
			this.getCocientesDiferencias().add(i, valor );
			this.setChi2Obtenido(this.getChi2Obtenido() + valor);
		}
	}
	
	
	//coc es el cocinete y se define como vector 
	
	/**
	 * <h1> TestHipotesis() </h1>
	 * <p> Evalua si  el chi2 obtenido es menor que el teorico segun los grados de libertad y la probabilidad pasadas </p>
	 * <br>
	 * <p>Vector de frecuencia obtenidas</p>
	 */
	private void TestHipotesis() {
		if ( this.getChi2Obtenido() < this.getChi2Teorico() ){
			this.setH0(true);
		}else {
			this.setH0(false);
		}
	}
}