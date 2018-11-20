/**
 * 
 */
package modeloHidrologico;

import java.util.ArrayList;
/**
 * @author Bermudez Karina S.
 *
 */
class MetodoHidrico {

//	MADel,QIN,CIN,CSU
	private ArrayList<Integer> muestra = new ArrayList<Integer>();
	private ArrayList<Integer> caudalesIniciales = new ArrayList<Integer>();
	private ArrayList<Integer> caudalesDiarios = new ArrayList<Integer>();
	private int tiempoCaudalInferior;
	private int tiempoCaudalSuperior;
	private int caudalesAcumulados;
	private int maximo;
	private int minimo;
	
	
	/**
	 * @param muestra
	 */
	MetodoHidrico(ArrayList<Integer> muestra) {
		this.setMuestra(muestra);
	}
	
	public ArrayList<Integer> getMuestra() {
		return muestra;
	}
	public ArrayList<Integer> getCaudalesIniciales() {
		return caudalesIniciales;
	}
	public ArrayList<Integer> getCaudalesDiarios() {
		return caudalesDiarios;
	}
	public int getTiempoCaudalInferior() {
		return tiempoCaudalInferior;
	}
	public int getTiempoCaudalSuperior() {
		return tiempoCaudalSuperior;
	}
	public int getCaudalesAcumulados() {
		return caudalesAcumulados;
	}
	public int getMinimo() {
		return minimo;
	}
	public int getMaximo() {
		return maximo;
	}
	private void setMuestra(ArrayList<Integer> muestra) {
		this.muestra = muestra;
	}
	private void setTiempoCaudalInferior(int tiempoCaudalInferior) {
		this.tiempoCaudalInferior = tiempoCaudalInferior;
	}
	private void setTiempoCaudalSuperior(int tiempoCaudalSuperior) {
		this.tiempoCaudalSuperior = tiempoCaudalSuperior;
	}
	private void setCaudalesAcumulados(int caudalesAcumulados) {
		this.caudalesAcumulados = caudalesAcumulados;
	}
	private void setMinimo(int minimo) {
		this.minimo = minimo;
	}
	private void setMaximo(int maximo) {
		this.maximo = maximo;
	}

	public void aplicarMetodo(int v_qin, int v_cin, int v_csu) {
		int qin = v_qin;
		int cin = v_cin;
		int csu = v_csu;
		//INICIALIZAMOS LOS VALORES PARA CADA UNA DE LAS CORRIDAS
		this.getCaudalesIniciales().clear();
		this.getCaudalesDiarios().clear();
		this.setCaudalesAcumulados(0);
		this.setTiempoCaudalInferior(0);	
		this.setTiempoCaudalInferior(0);
		
		this.setMaximo(cin);
		this.setMinimo(cin);
		long startTime = 0;
		long endTime = 0;

		startTime=System.nanoTime();
		// omp parallel public (qin, cin, csu)
		for (int dia=0; dia < this.getMuestra().size(); dia++) {
			int incremento = qin+this.getMuestra().get(dia);
			//calculamos el caudal diario
			this.getCaudalesDiarios().add(incremento);
			//vector de caudales diarios
			this.getCaudalesIniciales().add(qin);
			//total acumulado del caudal diario
			this.setCaudalesAcumulados(this.getCaudalesAcumulados()+incremento);

			if (incremento>csu) {
				this.setTiempoCaudalSuperior(this.getTiempoCaudalSuperior()+1);
			}
			if (incremento<cin) {
				this.setTiempoCaudalInferior(this.getTiempoCaudalInferior()+1);
			}

			if (this.getMaximo() < incremento) {
				this.setMaximo(incremento);
			}

			if (this.getMinimo() > incremento) {
				this.setMinimo(incremento);
			}
			qin = incremento;

		}
		endTime = System.nanoTime();
		System.out.println("~~~ Metodo Hidrico - Duracion " + (endTime - startTime)/1e6 + "ms");
		
		



	}
}
