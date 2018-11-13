package modeloHidrologico;

import java.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Main {
	
	public static void main (String[]args) {
		Scanner print = new Scanner (System.in);
		
		System.out.println("_________________________________________________________________________________________");
		System.out.println("'-----------------  INGRESO DE LOS VALORES PARA LLEVAR A CABO LA SIMULACION:-------------'");
		
		System.out.println(" " );
		System.out.println(">>Ingrese el numero de iteraciones de la simulacion: ");
		Integer corridas = print.nextInt();

		System.out.println(">> Cantidad de dias que se simularan (EXT): ");
		Integer EXT = print.nextInt();
		
		
		System.out.println("_________________________________________________________________________________________");
		/* INICIO DE LA APLICACION DEL METODO MULTIPLICATIVO DE LAS CONGRUENCIAS (ejecucionMetMultiCongru())**/
		System.out.println("-----------------  INGRESO DE LOS VALORES PARA EL METODO MULTIPLICATIVO DE LAS CONGRUENCIAS:-------------");
		System.out.println("INGRESE LOS SIGUIENTES VALORES ENTEROS Y MAYORES A 0        ");
		
		System.out.println(">> Semilla: ");
		Integer semilla = print.nextInt();
		System.out.println(">>Modulo (debe ser la unidad (1) seguida de ceros(0) y mayor a la semilla):  ");
		Integer modulo = print.nextInt();
		

		System.out.println("Para calcular el parametro a necesitaremmos los siguientes valores :       \n");
		System.out.println(">>Parámetro t (un valor entero cualquiera):  ");
		Integer t = print.nextInt();
		
		System.out.println("Para el parametro p elija uno de los siguientes números ");
		System.out.println("3, 11, 13, 19, 21, 27, 29, 37, 53, 59, 61, 67, 69, 77, 83, 91");
		System.out.println(">>Parámetro \"p\": ");
		Integer p = print.nextInt();
		

		//MetodoMultiCongruencia(int v_semilla, int v_t, int v_p, int v_modulo, int v_n) 
		MetodoMultiCongruencia mmc = new MetodoMultiCongruencia(semilla, t, p, modulo, EXT*corridas);
		ArrayList <Double> serie = mmc.ObtenerSerie();
		
		serie.forEach((s)->System.out.printf("%.4f - ", (double) s));
		/* FIN DE LA APLICACION DEL METODO MULTIPLICATIVO DE LAS CONGRUENCIAS**/
		
		
		if (!serie.isEmpty()) {
			/**
			 * %NO SE NOS EXIGE VER LOS RESULTADOS OBTENIDOS DE CHI POR LO QUE
     		 * %DIRECTAMENTE LLAMAREMOS A LA FUNCION Y NO IMPRIMIREMOS NINGUN VALOR
			 * */
			/* INICIO BLOQUE DE CODIGO EjecucionChi()*/
			System.out.println("-------------------------------------------------------------------------------------");
			System.out.println("             PRUEBA CHI CUADRADO PARA VERIFICAR QUE UNA SERIE DE NUMEROS             ");
			System.out.println("                    PSEUDOALEATORIOS SIGA UNA DISTRIBUCION UNIFORME                  ");
			System.out.println("-------------------------------------------------------------------------------------");
			
			System.out.print(">>Ingrese el número de subintervalos considerados: ");
			Integer ni = print.nextInt();
			System.out.print(">>Ingrese nivel de significación considerado entre [0,1]: (sugerido 0,05)");
			Double alpha = print.nextDouble();
			//Chi2(ArrayList<Double> serie, int ni, double alpha )
			Chi2 mch2 = new Chi2(serie, ni, alpha);
			
			//CHI2VALORES
			ArrayList <Double> fe = mch2.getFe();
			
			ArrayList <Double> fo = mch2.getFo();
			
			ArrayList <Double> cocientes = mch2.getCocientesDiferencias();
			//RESULTADO
			boolean resultado = mch2.getH0();
			//CHI2O
			double chi2o = mch2.getChi2Obtenido();
			//CHI2T
			double chi2t = mch2.getChi2Teorico();
			
			System.out.println("-------------------------------------------------------------------------------------");
			System.out.println("--------------------------- TABLA DE RESULTADOS PARCIALES ---------------------------");
			System.out.println("-------------------------------------------------------------------------------------");

			System.out.printf("%2sClases%8sIntervalo%8sF. Observadas%2sF. Esperada%2s((fo-fe)^2)/fe","","","","","");
			System.out.println("");
			for (int i= 0;i<ni; i++) {
				System.out.printf("%5s%d%5s[%.4f ; %.4f)%8s%.4f%8s%.4f%8s%.4f","",i,"",((double)i/(double)ni),((double)(i+1)/(double)ni),"",fo.get(i),"",fe.get(i),"",cocientes.get(i));
				System.out.println("");
			}
			System.out.print("");
			System.out.println("-------------------------------------------------------------------------------------");
			System.out.println("");

			

			System.out.println("-------------------------------------------------------------------------------------");
			System.out.println("----------------------------------- RESULTADOS --------------------------------------");
			System.out.println("-------------------------------------------------------------------------------------");
			System.out.printf("%2s* Grados de libertad = %d", "", ni-1);
			System.out.println("");
			if(resultado) {
				System.out.printf("%2s** Chi2 Observado = %.3f < Chi2 Tabla = %.3f", "", chi2o,chi2t);
				System.out.println("");
				System.out.println("  *** Se acepta la hipotesis de que la serie tenga de una Distribucion Uniforme ");
				
				
				/**
				 * generamos la tabla de distribucion de Empirica
				 * 
				*/
				DistribucionEmpirica distEmp = new DistribucionEmpirica();
				ArrayList <Double> fx = distEmp.getFx();
				ArrayList <Integer> x = distEmp.getX();
				
				/**
				 * generamos la Muestra Artificial del Incremento diario que se ajusta a la distribucion
				 * 
				 * ? porque dependiendo de el tipo de datos que maneje la tabla x de distribucion sera el tipo
				 * 	 de contenido que maneje la muestra
				 * */
				MetodoNumeroIndices metNumInd = new MetodoNumeroIndices(x,fx,serie);
				ArrayList <Integer>  muestraAleatoria = metNumInd.aplicaNumerosIndices();
				
				/**
				 * inicio de las corridas
				 * */
				System.out.println("-------------------------------------------------------------------------------------");
				System.out.println("------------------------- INICIAREMOS EL MODELO HIDROLOGICO -------------------------");
				System.out.println("-------------------------------------------------------------------------------------");
				
				System.out.println("");
				System.out.println(" >>Ingrese el valor del caudal inicial (QIN):");
				int QIN = print.nextInt();
				System.out.println(" >>Ingrese el valor del caudal minimo alcanzado (CIN):");
				int CIN = print.nextInt();
				System.out.println(" >>Ingrese el valor del caudal maximo alcanzado (CSU)):");
				int CSU = print.nextInt();
				
				//vector de los caudales medios obtenidos en cada una de las corridas
				ArrayList<Integer> QMS = new ArrayList<Integer>();
				//vector de los caudales maximos alcanzados
				ArrayList<Integer> QSA = new ArrayList<Integer>();
				//vector de los caudales minimos alcanzados
				ArrayList<Integer> QIA = new ArrayList<Integer>();
				//vector del tiempo en el que se obtuvo un caudal > a QSA
				ArrayList<Integer> TSQ = new ArrayList<Integer>();
				//vector del tiempo en el que se obtuvo un caudal < a QIA
				ArrayList<Integer> TIQ = new ArrayList<Integer>();
				//VECTOR DE LOS CAUDALES ACUMULADOS
				ArrayList<Integer> total_acum= new ArrayList<Integer>();
				int i=1;
				ArrayList<Integer> subMuestra = new ArrayList<Integer>();
				int inicio = 0;
				int fin = EXT;
				while ( i < corridas) {
					//ASIGNAMOS UNA PORCION DE LA MUESTRA POR CADA CORRIDA DEL TAMAÑO DE EXT
					for (int sub = inicio; sub <= fin; sub++) {
						subMuestra.add(muestraAleatoria.get(sub));
					}
					
					//[VQIN, QDI, vTOTAL_ACUM, vTSQ, vTIQ] =MetodoHidrico(MADel,QIN,CIN,CSU);
					MetodoHidrico metHid = new MetodoHidrico(subMuestra);
					metHid.aplicarMetodo(QIN, CIN, CSU);
					
					System.out.print("-------------------------------------------------------------------------------------");
					System.out.printf("Corrida N° %d", i);
					System.out.println("");
					System.out.printf("  >> Caudal Maximo obtenido: %d", metHid.getMaximo());
					System.out.println("");
					System.out.printf("  >> Caudal Minimo obtenido: %d", metHid.getMinimo());
					System.out.println("");
					System.out.printf("  >> TIEMPO EN EL QUE EL CAUDAL FUE SUPERIOR A %d, %d", CSU, metHid.getTiempoCaudalSuperior());
					System.out.println("");
					System.out.printf("  >> TIEMPO EN EL QUE EL CAUDAL FUE INFERIOR A %d, %d", CIN, metHid.getTiempoCaudalInferior());
					System.out.println("");
					System.out.printf("EL CAUDAL MEDIO DURANTE EL PERIDODO FUE DE: %.4f", (double)(metHid.getCaudalesAcumulados()/EXT));
					System.out.println("");
					System.out.print("-------------------------------------------------------------------------------------");
					System.out.println("");
					inicio = fin+1;
					fin = fin + EXT;
					i++;
					/* Grafica  */
									
					DefaultCategoryDataset metHidro = new DefaultCategoryDataset();
					
					String c1="Maximo";
					String c2="Minimo";
					String c3="Acumulados";
															
					metHidro.setValue(metHid.getMinimo(),c1 ,"Caudal minimo");
					metHidro.setValue(metHid.getMaximo(),c2,"Caudal Minimo");
					metHidro.setValue(metHid.getCaudalesAcumulados(),c3,"Caudal Acumulado");
					
					JFreeChart f = ChartFactory.createBarChart("Hidrologia"," ","Caudal",metHidro,PlotOrientation.VERTICAL, true, false, false);
				
					ChartFrame frame= new ChartFrame("Hidrologia" ,f);
					frame.pack();
					frame.setVisible(true);
					}
					
				
				
			}else {

				System.out.printf("%2s** Chi2 Observado = %.3f > Chi2 Tabla = %.3f", "", chi2o,chi2t);
				System.out.println("  *** No Se acepta la hipotesis de que la serie tenga de una Distribucion Uniforme ");
				
				/**
				 * TRABAJAMOS CON LA TABLA DE DISTRIBUCION EMPIRICA
				 * 
				*/
				
				/**generamos la tabla de distribucion de Empirica
				 * */
				
				/**
				 * % generamos la Muestra Artificial del Incremento diario que se ajusta a la distribucion
         		   % Empirica
				 * */
			}
			
			
			
			
			
		}
	
		print.close();
		
	}

}
