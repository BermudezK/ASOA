package modeloHidrologico;

import java.util.ArrayList;

/**
 * @author Orne
 *
 */
public class Imprime {
	/*esto necesito
	  QSA(i) = max(QDI); caudal maximo
	   QIA(i) = min(QDI); caudal minimo
	   TSQ(i) = vTSQ; tiempo superior
	   TIQ(i) = vTIQ; tiempo inferior
	   QMS(i) =  TOTAL_ACUM(i) / n; caudal medio
	*/
	private ArrayList<Integer> QSA = new ArrayList<Integer>();
	private ArrayList<Integer> QIA = new ArrayList<Integer>();
	private ArrayList<Integer> TSQ = new ArrayList<Integer>();
	private ArrayList<Integer> TIQ = new ArrayList<Integer>();
	private ArrayList<Integer> QMS = new ArrayList<Integer>();

	private ArrayList<Integer> total_acum= new ArrayList<Integer>();
	private int maximo;
	private int minimo;
	
	/* Get	 */
	  public ArrayList<Integer> getQSA() {
		return QSA; }
	  public ArrayList<Integer> getQIA() {
			return QIA; }
	  public ArrayList<Integer> getTSQ() {
			return TSQ; }
	  public ArrayList<Integer> getTIQ() {
			return TIQ; }
	  public ArrayList<Integer> getQMS() {
			return QMS; }
	  public ArrayList<Integer> getTotal_acum(){
			return total_acum; }
	  
	  public int getMaximo() {
		  return maximo;  }
	  public int getMinimo() {
		  return minimo;  }
	  public void setMinimo(int minimo) {
		  this.minimo= minimo; }
	  public void setMaximo(int maximo) {
		  this.maximo = maximo;}
	  
	  
	  
	  /*
	   *como obtengo el caudal maximo de una corrida 
	   *en el metodohidrico calcula el caudal maximo
	   *ahi deberia hacer un getMaximo() que me guarde en QSA 
	   */
	
	 
	   
}
