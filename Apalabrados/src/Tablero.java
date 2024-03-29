 
public class Tablero {
	
	Casilla[][] elTablero; 
	private Diccionario diccionario;
			
	Tablero(){
		diccionario = new Diccionario();
		elTablero = new Casilla[15][];
	    for (int i=0; i<15; i++){
		   //asigno en memoria para cada fila
		   elTablero[i]=new Casilla[15];
		   for(int j=0; j<15; j++){
		      //ahora creo un objeto para cada elemento
		      elTablero[i][j]=new Casilla();
	    	}
	    }
	    inicializa();
	    
	    /// zona de prueba

		if (diccionario.EsValida("prueba")) System.out.println("Cadena encontrada");
		else System.out.println("Cadena no encontrada");
	    
		/*if (palabrasEncontradas.setInsertarPalabra(new Palabras("prueba",25,true))) System.out.println("La palabra se ha introducido correctamente");
		else System.out.println("La palabra esta duplicada");
		
		if (palabrasEncontradas.setInsertarPalabra(new Palabras("prueba",25,true))) System.out.println("La palabra se ha introducido correctamente");
		else System.out.println("La palabra esta duplicada");
		
		System.out.println("lista de palabras: " + palabrasEncontradas.toString());
		
		if (palabrasEncontradas.EstaInsertada(new Palabras("prueba",25,true))) System.out.println("La palabra ya ha sido introducida");
		else System.out.println("La palabra aun no esta introducida");
		
		if (palabrasEncontradas.EstaInsertada(new Palabras("prueba2",25,true))) System.out.println("La palabra ya ha sido introducida");
		else System.out.println("La palabra aun no esta introducida");
*/
	    
	    /// fin zona de pruebas
	}
	
	/**
	 * Inicializa la matriz con los valores de las casillas especiales, 
	 * las del tipo (x,y,1) son de doble letra
	 * las del tipo (x,y,2) son de triple letra
	 * las del tipo (x,y,3) son de doble palabra
	 * las del tipo (x,y,4) son de triple palabra
	 */
	private void inicializa(){
		int matriz[][]={
				{2,2,1},{12,2,1},{6,4,1},{8,4,1},{4,6,1},{10,6,1},{4,8,1},{10,8,1},{6,10,1},{8,10,1},{2,12,1},{12,12,1},
				
				{4,0,2},{10,0,2},{1,1,2},{13,1,2},{6,2,2},{8,2,2},{3,3,2},{11,3,2},
				{0,4,2},{14,4,2},{5,5,2},{9,5,2},{2,6,2},{12,6,2},{2,8,2},{12,8,2},
				{5,9,2},{9,9,2},{0,10,2},{14,10,2},{3,11,2},{11,11,2},{6,12,2},{8,12,2},
				{1,13,2},{13,13,2},{4,14,2},{10,14,2},
				
				{5,1,3},{9,1,3},{7,3,3},{1,5,3},{13,5,3},{1,9,3},{13,9,3},{7,11,3},{5,13,3},{9,13,3},
				
				{0,2,4},{0,12,4},{2,0,4},{2,14,4},{12,0,4},{12,14,4},{14,2,4},{14,12,4}
		};
		for (int i=0;i<matriz.length;i++){
			//establecemos la casilla x,y del tipo especial
			elTablero[matriz[i][0]][matriz[i][1]].setEspecial(true);
			elTablero[matriz[i][0]][matriz[i][1]].setPosicionX(matriz[i][0]);
			elTablero[matriz[i][0]][matriz[i][1]].setPosicionY(matriz[i][1]);
			// segun el tercer parametro del vector establecemos el tipo de casilla especial.
			switch (matriz[i][2]){
				case 1: elTablero[matriz[i][0]][matriz[i][1]].setTCasilla(Datos.TipoCasilla.DL);break;
				case 2: elTablero[matriz[i][0]][matriz[i][1]].setTCasilla(Datos.TipoCasilla.TL);break;
				case 3: elTablero[matriz[i][0]][matriz[i][1]].setTCasilla(Datos.TipoCasilla.DP);break;
				case 4: elTablero[matriz[i][0]][matriz[i][1]].setTCasilla(Datos.TipoCasilla.TP);break;
			}
	
		}
		elTablero[7][7].setVacio(false);
		elTablero[7][7].setLetra('*');
		elTablero[7][7].setValor(0);
		elTablero[7][7].setPosicionX(7);
		elTablero[7][7].setPosicionY(7);
	}
	
	/**
	 * 
	 * @return devuelve el tablero de casillas actual
	 */
	public Casilla[][] getTablero(){
		return elTablero;
	}
	
	public void setCasilla(Casilla casilla){
		
	}
	
	
	// esta funcion habra que borrarla entera pero se deja por ahora para la puntuacion
	/**
	 * 
	 * @param direccion, si es true busca por filas y si es flase por columnas
	 * @return devuelve la puntuacion total de las nuevas fichas en la posicion vertical u 
	 * horizontal
	 */
	private int busquedaDireccion(boolean direccion){
		boolean palabra=false;
		// variable para identificar la posicion para poder guardar varias palabras.
		boolean inicioPalabra=false;
		int posicion=-1;
		//
		char cadena[] = new char[15];
		int puntuacion=0;
		int puntuacionTotal=0;
		int contador=0;
		int multiplicador=1;
		int multiplicadorPalabra=1;
		int x,y;
		
		for (int i=0; i<15;i++){
			for(int j=0;j<15;j++){
				if (direccion){
					x=j;
					y=i;
				}else{
					x=i;
					y=j;
				}
				if (!elTablero[x][y].isVacio()){
					if (elTablero[x][y].isPrimeraVez()){
						elTablero[x][y].setPrimeraVez(false); //ya ha sido evaluada para las DL y TL
						if (elTablero[x][y].isEspecial()){
							if (elTablero[x][y].getTCasilla()==Datos.TipoCasilla.DL)
								multiplicador=2;
							if (elTablero[x][y].getTCasilla()==Datos.TipoCasilla.TL)
								multiplicador=3;
						}
					}
					else{
						if (elTablero[x][y].isEspecial()){
							if (elTablero[x][y].getTCasilla()==Datos.TipoCasilla.DP)
								multiplicadorPalabra=multiplicadorPalabra*2;
							if (elTablero[x][y].getTCasilla()==Datos.TipoCasilla.TP)
								multiplicadorPalabra=multiplicadorPalabra*3;
						}
					}
					cadena[contador]=elTablero[x][y].getLetra();
					contador++;
					puntuacion=puntuacion + (elTablero[x][y].getValor()*multiplicador);	
					multiplicador=1;
					palabra=true;
					//control para guardar la posicion incio de la palabra
					if (inicioPalabra==false){
						inicioPalabra=true;
						posicion =y*15+x;
						
					}
				}
				else{
					/*
					if(palabra){ // palabras dentro de la fila/columna
					palabra=false;
					inicioPalabra=false;
					String str = new String(cadena);
					palabras.setCadena(str);
					palabras.setPosicion(posicion);
					palabras.setDireccion(direccion);
					if (!palabrasEncontradas.EstaInsertada(palabras)){
						palabrasEncontradas.setInsertarPalabra(palabras);
						if (diccionario.EsValida(str))
							puntuacionTotal = puntuacionTotal + (puntuacion*multiplicadorPalabra);
					}
					contador=0;
					puntuacion=0;
					posicion=-1;
					multiplicadorPalabra=1;
					for (int z=0;z<15;z++) cadena[z]=0;
					*/
				}
			}
			// ha terminado la busqueda en la fila/columna pero puede haber una palabra hasta la ultima casilla
			if(palabra){
				palabra=false;
				inicioPalabra=false;
				String str = new String(cadena);
				/*
				palabras.setCadena(str);
				palabras.setPosicion();
				palabras.setDireccion(direccion);				
				if (!palabrasEncontradas.EstaInsertada(palabras)){
					palabrasEncontradas.setInsertarPalabra(palabras);
					if (diccionario.EsValida(str))
						puntuacionTotal = puntuacionTotal + (puntuacion*multiplicadorPalabra);
				}
				*/
				contador=0;
				puntuacion=0;
				posicion=-1;
				multiplicadorPalabra=1;
				for (int z=0;z<15;z++) cadena[z]=0;
			}
		}
		return puntuacionTotal;
	}
	
	
	// esta funcion ya no es necesaria se realiza todo en turno, dentro de jugador.
	/**
	 * 
	 * @return devuelve la puntuacion total de las dos busquedas de las palabras, tanto vertical
	 * como horizontal
	 */
	
	/*
	public int busqueda(){
		return busquedaDireccion(true)+busquedaDireccion(false);
	}
	*/
	
	/**
	 * Funcion que pinta el tablero en la salida estandar
	 * el valor de la letra no se me ocurre como hacerlo aun.
	 */
	public void pintaTablero(){
		System.out.println("-------------------------------------------------------------");
		for (int i=0; i<15; i++){

			for(int j=0;j<15;j++){
				System.out.print("|");
				if (elTablero[j][i].isEspecial()) System.out.print(elTablero[j][i].getTCasilla()+" ");
				else if (elTablero[j][i].isVacio()) System.out.print("   ");
					 else System.out.print(" " + elTablero[j][i].getLetra()+" ");

			}
			System.out.println("|");
			System.out.println("-------------------------------------------------------------");
			
		}
	}
	
}

