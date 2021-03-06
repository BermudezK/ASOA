package modeloHidrologico;
import java.util.*;
import java.util.ArrayList;
public class Main2 {

	public static void main (String[]args) {
		Scanner print = new Scanner (System.in);		
		System.out.println(" ");
		System.out.println("");
		System.out.println("'-----------------  INGRESO DE LOS VALORES PARA LLEVAR A CABO LA SIMULACION:-------------'");
		System.out.print(">>Ingrese el numero de corridas: ");
		Integer corridas = print.nextInt();
		System.out.print(">>Ingrese el numero de iteraciones de la simulacion: ");
		Integer simulacion = print.nextInt();

		System.out.print(" >> Cantidad de dias que se simularan (EXT): ");
		Integer EXT = print.nextInt();


		/* INICIO DE LA APLICACION DEL METODO MULTIPLICATIVO DE LAS CONGRUENCIAS (ejecucionMetMultiCongru())**/
		System.out.println("-----------------  INGRESO DE LOS VALORES PARA EL METODO MULTIPLICATIVO DE LAS CONGRUENCIAS:-------------");
		System.out.println("INGRESE LOS SIGUIENTES VALORES ENTEROS Y MAYORES A 0        ");

		System.out.print("  >> Semilla: ");
		Integer semilla = print.nextInt();
		System.out.print("  >>Modulo (debe ser la unidad (1) seguida de ceros(0) y mayor a la semilla):  ");
		Integer modulo = print.nextInt();


		System.out.println("Para calcular el parametro a necesitaremmos los siguientes valores :       \n");
		System.out.print("  >>Parametro t (un valor entero cualquiera):  ");
		Integer t = print.nextInt();

		System.out.println("Para el parametro p elija uno de los siguientes numeros ");
		System.out.println("3, 11, 13, 19, 21, 27, 29, 37, 53, 59, 61, 67, 69, 77, 83, 91");
		System.out.print("  >>Parametro \"p\": ");
		Integer p = print.nextInt();

		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println("             PRUEBA CHI CUADRADO PARA VERIFICAR QUE UNA SERIE DE NUMEROS             ");
		System.out.println("                    PSEUDOALEATORIOS SIGA UNA DISTRIBUCION UNIFORME                  ");
		System.out.println("-------------------------------------------------------------------------------------");

		System.out.print(">>Ingrese el numero de subintervalos considerados: ");
		Integer ni = print.nextInt();
		System.out.print(">>Ingrese nivel de significacion considerado entre [0,1]");
		System.out.println("");
		System.out.println("Sugeridos [0.01, 0.05, 0.10, 0.20, 0.25, 0.30, 0.40, 0.50, 0.60, 0.70, 0.75, 0.80, 0.90, 0.95, 0.99]: ");
		Double alpha = print.nextDouble();


		System.out.println("");
		System.out.println("_____________________________________________________________________________________");
		System.out.println("------------------------- INICIAREMOS EL MODELO HIDROLOGICO:-------------------------");
		System.out.println("_____________________________________________________________________________________");
		System.out.println("");
		System.out.print(" >>Ingrese el valor del caudal inicial (QIN):");
		int QIN = print.nextInt();
		System.out.print(" >>Ingrese el valor del caudal minimo alcanzado (CIN):");
		int CIN = print.nextInt();
		System.out.print(" >>Ingrese el valor del caudal maximo alcanzado (CSU)):");
		int CSU = print.nextInt();

		//MetodoMultiCongruencia(int v_semilla, int v_t, int v_p, int v_modulo, int v_n) 
		MetodoMultiCongruencia mmc = new MetodoMultiCongruencia(semilla, t, p, modulo, EXT*simulacion*corridas);
		ArrayList <Double> serie = mmc.obtenerSerie();
		/* FIN DE LA APLICACION DEL METODO MULTIPLICATIVO DE LAS CONGRUENCIAS**/


		if (!serie.isEmpty()) {
			/**
			 * generamos la Muestra Artificial del Incremento diario que se ajusta a la distribucion
			 * 
			 * ? porque dependiendo de el tipo de datos que maneje la tabla x de distribucion sera el tipo
			 * 	 de contenido que maneje la muestra
			 * %NO SE NOS EXIGE VER LOS RESULTADOS OBTENIDOS DE CHI POR LO QUE
			 * %DIRECTAMENTE LLAMAREMOS A LA FUNCION Y NO IMPRIMIREMOS NINGUN VALOR
			 * */
			/* INICIO BLOQUE DE CODIGO EjecucionChi()*/


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

			ArrayList <Integer>  muestraAleatoria = metNumInd.obtenerMuestra();

			ArrayList<Double> subSerie = new ArrayList<Double>();
			ArrayList<Integer> subMuestra = new ArrayList<Integer>();
			int inicioM = 0;
			int finM = EXT;
			
			int inicioC = 0;
			int finC = simulacion*EXT;
			
			for (int c=0; c < corridas; c++) {

				subSerie.clear();
				subMuestra.clear();
				//ASIGNAMOS UNA PORCION DE LA MUESTRA POR CADA CORRIDA DEL TAMANO DE EXT

				for (int sub = inicioC; sub < finC; sub++) {
					subSerie.add(serie.get(sub));
				}

				inicioC = finC;
				finC = finC + (simulacion*EXT);
				
				for (int sub = inicioM; sub < finM; sub++) {
					subMuestra.add(muestraAleatoria.get(sub));
				}
				
				inicioM = finM;
				finM = finM + EXT;
				
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
				
				//Chi2(ArrayList<Double> serie, int ni, double alpha )
				Chi2 mch2 = new Chi2(subSerie, ni, (double) alpha);

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

				System.out.println("");
				System.out.println("_____________________________________________________________________________________");
				System.out.println("--------------------------- TABLA DE RESULTADOS PARCIALES ---------------------------");
				System.out.println("_____________________________________________________________________________________");

				System.out.printf("%2sClases%8sIntervalo%8sF. Observadas%2sF. Esperada%2s((fo-fe)^2)/fe","","","","","");
				System.out.println("");
				for (int j	= 0;j<ni; j++) {
					System.out.printf("%5s%d%5s[%.4f ; %.4f)%8s%.4f%8s%.4f%8s%.4f","",j,"",((double)j/(double)ni),((double)(j+1)/(double)ni),"",fo.get(j),"",fe.get(j),"",cocientes.get(j));
					System.out.println("");
				}
				System.out.print("");
				System.out.println("");
				System.out.println("______________________________________________________________________________________");
				System.out.println("----------------------------------- RESULTADOS --------------------------------------");
				System.out.println("______________________________________________________________________________________");
				System.out.printf("%2s* Grados de libertad = %d", "", ni-1);
				System.out.println("");
				
				if(resultado) {
					System.out.printf("%2s** Chi2 Observado = %.3f < Chi2 Tabla = %.3f", "", chi2o,chi2t);
					System.out.println("");
					System.out.println("  *** Se acepta la hipotesis de que la serie tenga de una Distribucion Uniforme ");
				while ( i <= simulacion) {
					
					
						//[VQIN, QDI, vTOTAL_ACUM, vTSQ, vTIQ] =MetodoHidrico(MADel,QIN,CIN,CSU);
						MetodoHidrico metHid = new MetodoHidrico(subMuestra);
						metHid.aplicarMetodo(QIN, CIN, CSU);
						System.out.print("_____________________________________________________________________________________");
						System.out.println("");
						System.out.printf("Simulacion N %d", i);
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

						QSA.add(metHid.getMaximo());
						QIA.add(metHid.getMinimo());
						TSQ.add(metHid.getTiempoCaudalSuperior());
						TIQ.add(metHid.getTiempoCaudalInferior());
						QMS.add(metHid.getCaudalesAcumulados()/EXT);
						total_acum.add(metHid.getCaudalesAcumulados());

						i++;
						
					}}
					else {
						System.out.printf("%2s** Chi2 Observado = %.3f > Chi2 Tabla = %.3f", "", chi2o,chi2t);
						System.out.println("  *** No Se acepta la hipotesis de que la serie tenga de una Distribucion Uniforme ");
					
				}
				System.out.println("");
				System.out.println("_______________________________________________________________________________________________");
				System.out.println("------------ TABLA DE DETALLA UN RESUMEN DE LOS VALORES OBTENIDOS EN CADA Iteracion -------------");
				System.out.println("_______________________________________________________________________________________________");
				System.out.printf("%2sNro de Simulcion%4sCaudal Maximo%4sCaudal Minimos%2sT.Superior%2sT.Inferior%2sCaudal Medio","","","","","","","","");
				System.out.println("");

				for (int l=0; l<simulacion; l++){
					System.out.printf("");
					System.out.printf("%5s %d %16s %d %16s %d %10s %d %8s %d %5s %d ","",(l+1),"",QSA.get(l),"",QIA.get(l),"",TSQ.get(l),"",TIQ.get(l),"",QMS.get(l));
					System.out.println("");
				}
								

			}
			System.out.println("");
			System.out.println("FIN DE LA SIMULACION");
		}
		print.close();


	}

}

