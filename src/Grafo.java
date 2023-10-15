import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Grafo {
	private int[][] grafo;
	private ArrayList<Nodo> listaNodos;
	private int n;
	private int[][] matrizDistancia;
	private ArrayList<ArrayList<Integer>> subconjuntos = new ArrayList<ArrayList<Integer>>();
	private int tiempoDFS = 0;
	private boolean grafoCiclico;

	public Grafo(String dirArchivo) {
		inicializarGrafo(dirArchivo);
	}

	public void inicializarGrafo(String dirArchivo) {
		File doc = new File(dirArchivo);
		try {
			BufferedReader archivo = new BufferedReader(new FileReader(doc));
			String line;
			line = archivo.readLine();
			int n = line.split("\t").length;

			int i = 0;
			int[][] grafo = new int[n][n];
			ArrayList<Nodo> listaNodos = new ArrayList<Nodo>();
			boolean centinela = true;

			while (centinela) {
				String[] stringGrafo = line.split("\t");
				for (int j = 0; j < n; j++) {
					int pesoVertice = Integer.parseInt(stringGrafo[j]);
					if (pesoVertice < 0) {
						pesoVertice = 99999999;
					}
					grafo[i][j] = pesoVertice;
				}

				Nodo nodoActual = new Nodo(i);
				listaNodos.add(nodoActual);

				i++;

				line = archivo.readLine();
				if (line == null || line.equals("")) {
					centinela = false;
				}

			}
			archivo.close();
			this.grafo = grafo;
			this.listaNodos = listaNodos;
			this.n = n;
			this.matrizDistancia = new int[n][n];

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void relajar(int id_U, int id_V) {
		Nodo u = this.getListaNodos().get(id_U);
		Nodo v = this.getListaNodos().get(id_V);
		int pesoActual_U = u.getPesoActual();
		int pesoActual_V = v.getPesoActual();

		if (pesoActual_V > pesoActual_U + this.grafo[id_U][id_V]) {
			v.setPesoActual(pesoActual_U + this.grafo[id_U][id_V]);
			v.setIdNodoPredecesor(id_U);
		}
	}

	public void inicializarSingleSource(int idNodoOrigen) {
		for (Nodo nodoActual : this.listaNodos) {
			nodoActual.setPesoActual(99999999);
			nodoActual.setIdNodoPredecesor(-1);
		}
		this.listaNodos.get(idNodoOrigen).setPesoActual(0);
	}

	public boolean BellmanFord(int idNodoOrigen) {
		inicializarSingleSource(idNodoOrigen);
		n = this.getN();
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n; j++) {// Este doble-for recorre todos los ejes
				for (int k = 0; k < n; k++) {
					relajar(j, k);
				}
			}
		}

		for (int j = 0; j < n; j++) {
			for (int k = 0; k < n; k++) {
				Nodo u = this.getListaNodos().get(j);
				Nodo v = this.getListaNodos().get(k);
				int pesoActual_U = u.getPesoActual();
				int pesoActual_V = v.getPesoActual();
				if (pesoActual_V > pesoActual_U + this.grafo[j][k]) {
					return false;
				}
			}
		}

		return true;
	}

	public void matrizBellmanFord() {
		int[][] matrizBF = this.getMatrizDistancia().clone();
		for (int i = 0; i < this.getN(); i++) {
			BellmanFord(i);
			for (int j = 0; j < this.getN(); j++) {
				int pesoNodo = this.getListaNodos().get(j).getPesoActual();
				matrizBF[i][j] = pesoNodo;
			}
		}
		this.matrizDistancia = matrizBF;
	}

	public Nodo ExtraerMinimo(ArrayList<Nodo> nodosDijkstra) {
		int minimo = 99999999;
		Nodo nodoMinimo = null;
		for (Nodo nodoActual : nodosDijkstra) {
			if (nodoActual.getPesoActual() < minimo) {
				minimo = nodoActual.getPesoActual();
				nodoMinimo = nodoActual;
			} else if (nodoActual.getPesoActual() == minimo) {
				nodoMinimo = nodoActual;
			}
		}
		nodosDijkstra.remove(nodoMinimo);
		return nodoMinimo;
	}

	@SuppressWarnings("unchecked")
	public void Dijkstra(int idNodoOrigen) {
		inicializarSingleSource(idNodoOrigen);
		ArrayList<Nodo> nodosDijkstra = (ArrayList<Nodo>) this.getListaNodos().clone();
		while (!nodosDijkstra.isEmpty()) {
			Nodo nodoActual = ExtraerMinimo(nodosDijkstra);
			for (int j = 0; j < n; j++) {
				relajar(nodoActual.getIdNodo(), j);
			}
		}
	}

	public void matrizDijkstra() {
		int[][] matrizD = this.getMatrizDistancia().clone();
		for (int i = 0; i < this.getN(); i++) {
			Dijkstra(i);
			for (int j = 0; j < this.getN(); j++) {
				int pesoNodo = this.getListaNodos().get(j).getPesoActual();
				matrizD[i][j] = pesoNodo;
			}
		}
		this.matrizDistancia = matrizD;
	}

	public void FloydWarshall() {
		int[][] matrizFW = this.getGrafo().clone();
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (matrizFW[i][j] > matrizFW[i][k] + matrizFW[k][j]) {
						matrizFW[i][j] = matrizFW[i][k] + matrizFW[k][j];
					}
				}
			}
		}
		this.matrizDistancia = matrizFW;
	}

	public void BFS(int idNodoOrigen) {
		for (Nodo nodoActual : this.getListaNodos()) {
			nodoActual.setColor(0);
			nodoActual.setProfundidad(99999999);
			nodoActual.setIdNodoPredecesor(-1);
		}
		Nodo nodoOrigen = this.getListaNodos().get(idNodoOrigen);
		nodoOrigen.setColor(1);
		nodoOrigen.setProfundidad(0);
		nodoOrigen.setIdNodoPredecesor(-1);
		Queue<Nodo> queue = new LinkedList<Nodo>();
		queue.add(nodoOrigen);
		while (!queue.isEmpty()) {
			Nodo u = queue.remove();
			for (int i = 0; i < this.getN(); i++) {
				int pesoArco = this.getGrafo()[u.getIdNodo()][i];
				if (pesoArco != 99999999) {
					Nodo v = this.getListaNodos().get(i);
					if (v.getColor() == 0) {
						v.setColor(1);
						v.setProfundidad(u.getProfundidad() + 1);
						v.setIdNodoPredecesor(u.getIdNodo());
						queue.add(v);
					}
				}
			}
			u.setColor(2);
		}
	}

	public boolean grafoConectado() {
		for (Nodo nodoActual : this.getListaNodos()) {
			if (nodoActual.getColor() != 2) {
				return false;
			}
		}
		return true;
	}

	public int componentesConectados() {
		BFS(0);// Es necesario llamar BFS al menos una vez, pero no importa con respecto a que
				// nodo se haga.
		int numComponentes = 1;
		ArrayList<Integer> nodosHijos = new ArrayList<Integer>();
		ArrayList<Nodo> nodosAlcanzados = new ArrayList<Nodo>();
		boolean conectado = grafoConectado();
		boolean centinela = true;

		if (!conectado) {
			while (centinela) {
				for (Nodo nodoActual : this.getListaNodos()) {
					if (nodoActual.getColor() == 2 && !nodosAlcanzados.contains(nodoActual)) {
						nodosHijos.add(nodoActual.getIdNodo());
						nodosAlcanzados.add(nodoActual);
					}
				}
				this.getSubconjuntos().add(nodosHijos);

				for (Nodo nodoActual : this.getListaNodos()) {
					if (nodoActual.getColor() == 0 && nodosAlcanzados.contains(nodoActual) != true) {
						BFS(nodoActual.getIdNodo());
						numComponentes++;
						break;
					}
				}
				if (nodosAlcanzados.size() == this.getN()) {
					centinela = false;
				}
				nodosHijos = new ArrayList<Integer>();
			}
		}

		return numComponentes;
	}

	public void DFS() {
		for (int i = 0; i < this.getN(); i++) {
			Nodo u = this.getListaNodos().get(i);
			u.setColor(0);
			u.setIdNodoPredecesor(-1);
		}
		this.tiempoDFS = 0;
		this.setGrafoCiclico(false);// Esto inicializa la variable GrafoCiclico en falso, se asume que es aciclico
		for (int i = 0; i < this.getN(); i++) {
			Nodo u = this.getListaNodos().get(i);
			if (u.getColor() == 0) {
				DFS_Visit(u.getIdNodo());
			}
		}
	}

	public void DFS_Visit(int nodo) {
		Nodo u = this.getListaNodos().get(nodo);
		this.tiempoDFS = this.getTiempoDFS() + 1;
		u.setDescubrimiento(this.tiempoDFS);
		u.setColor(1);
		for (int j = 0; j < this.getN(); j++) {
			int pesoArco = this.getGrafo()[u.getIdNodo()][j];
			if (pesoArco != 99999999 && pesoArco != 0) {
				Nodo v = this.getListaNodos().get(j);
				if (v.getColor() == 1) {// Este es el check para ver si el grafo tiene ciclos
					this.setGrafoCiclico(true);
				}
				if (v.getColor() == 0) {
					v.setIdNodoPredecesor(u.getIdNodo());
					DFS_Visit(j);
				}
			}
		}
		u.setColor(2);
		this.tiempoDFS = this.getTiempoDFS() + 1;
		u.setFinalizacion(this.tiempoDFS);
	}

	public void imprimirSubconjuntosConectados(int numSubconjuntos) {
		for (int i = 0; i < numSubconjuntos; i++) {
			ArrayList<Integer> conjunto = this.getSubconjuntos().get(i);
			System.out.print("Subconjunto_" + (i + 1) + ":\t");
			for (int nodo : conjunto) {
				System.out.print(nodo + "\t");
			}
			System.out.println("\n");
		}
	}

	public void verificarBFS() {
		System.out.println("Nodo \tProfundidad \tColor");
		for (int i = 0; i < this.getN(); i++) {
			Nodo nodoActual = this.getListaNodos().get(i);
			System.out.println(i + "\t" + nodoActual.getProfundidad() + "\t\t" + nodoActual.getColor());
		}
	}

	public void imprimirMatrizRta(int idNodoOrigen) {
		System.out.println("Nodo \tDistancia");
		for (int i = 0; i < this.getN(); i++) {
			System.out.println(i + "\t" + this.getMatrizDistancia()[idNodoOrigen][i]);
		}
	}

	public void imprimirNodosPredecesores(int idNodoOrigen, int idNodoDestino) {// Esta función no se ejecuta
																				// correctamente para Floyd Warshall
		Nodo destino = this.getListaNodos().get(idNodoDestino);
		ArrayList<Integer> camino = new ArrayList<Integer>();
		boolean centinela = true;
		camino.add(destino.getIdNodo());
		while (centinela) {
			int nodoPredecesor = destino.getIdNodoPredecesor();
			if (nodoPredecesor != idNodoOrigen) {
				destino = this.getListaNodos().get(nodoPredecesor);
				camino.add(nodoPredecesor);
			} else {
				camino.add(nodoPredecesor);
				centinela = false;
			}
		}

		for (int i = camino.size() - 1; i >= 0; i--) {
			System.out.println(camino.get(i));
		}

	}

	public int[][] getGrafo() {
		return grafo;
	}

	public void setGrafo(int[][] grafo) {
		this.grafo = grafo;
	}

	public ArrayList<Nodo> getListaNodos() {
		return listaNodos;
	}

	public void setListaNodos(ArrayList<Nodo> listaNodos) {
		this.listaNodos = listaNodos;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int[][] getMatrizDistancia() {
		return matrizDistancia;
	}

	public void setMatrizDistancia(int[][] matrizDistancia) {
		this.matrizDistancia = matrizDistancia;
	}

	public ArrayList<ArrayList<Integer>> getSubconjuntos() {
		return subconjuntos;
	}

	public void setSubconjuntos(ArrayList<ArrayList<Integer>> subconjuntos) {
		this.subconjuntos = subconjuntos;
	}

	public int getTiempoDFS() {
		return tiempoDFS;
	}

	public void setTiempoDFS(int tiempoDFS) {
		this.tiempoDFS = tiempoDFS;
	}

	public boolean getGrafoCiclico() {
		return grafoCiclico;
	}

	public void setGrafoCiclico(boolean grafoCiclico) {
		this.grafoCiclico = grafoCiclico;
	}
}