import java.util.Scanner;

public class parte_1 {
	public static void main(String[] args) {

		System.out.println("Tarea 3 - Parte 1");
		System.out.println("Porfavor escoja un grafo a analizar:");
		System.out.println("1. distances5\n" + "2. distances100\n" + "3. distances100_costosminimos\n"
				+ "4. distances1000_202110\n" + "5. distances1000_202110_costosminimos\n" + "6. distancesDisconnected\n"
						+ "7. Otro Archivo");
		System.out.print("Ingrese la opción: ");

		Scanner scanner = new Scanner(System.in);
		int eleccion = scanner.nextInt();
		boolean centinela = true;
		String dirArchivo = "";

		switch (eleccion) {
		case 1:
			dirArchivo = "distances5.txt";
			break;
		case 2:
			dirArchivo = "distances100.txt";
			break;
		case 3:
			dirArchivo = "distances100_costosminimos.txt";
			break;
		case 4:
			dirArchivo = "distances1000_202110.txt";
			break;
		case 5:
			dirArchivo = "distances1000_202110_costosminimos.txt";
			break;
		case 6:
			dirArchivo = "distancesDisconnected.txt";
			break;
		case 7:
			System.out.println("Escriba el nombre del archivo que desea abrir: ");
			dirArchivo = scanner.next();
			break;
		default:
			centinela = false;
			System.out.println("La opción no es valida. Porfavor intentelo de nuevo");

		}
		if (centinela) {
			Grafo f = new Grafo("datos/" + dirArchivo);

			boolean menu = true;
			long inicio;
			long fin;
			long tiempo = -1;
			while (menu) {
				System.out.println("¿Para que algoritmo desea calcular su tiempo de ejecución?");
				System.out.println("1. Bellman Ford\n" + "2. Dijkstra\n" + "3. Floyd Warshall");
				eleccion = scanner.nextInt();
				switch (eleccion) {
				case (1):
					inicio = System.currentTimeMillis();
					f.matrizBellmanFord();
					fin = System.currentTimeMillis();
					tiempo = fin - inicio;
					menu = false;
					System.out.println("Se demoro: " + tiempo + " milisegundos.");
					break;
				case (2):
					inicio = System.currentTimeMillis();
					f.matrizDijkstra();
					fin = System.currentTimeMillis();
					tiempo = fin - inicio;
					menu = false;
					System.out.println("Se demoro: " + tiempo + " milisegundos.");
					break;
				case (3):
					inicio = System.currentTimeMillis();
					f.FloydWarshall();
					fin = System.currentTimeMillis();
					tiempo = fin - inicio;
					menu = false;
					System.out.println("Se demoro: " + tiempo + " milisegundos.");
					break;
				default:
					System.out.println("La opción no es valida. Porfavor intentelo de nuevo\n");
				}
				if (tiempo != -1) {
					tiempo = -1;
					System.out.println("¿Desea analizar otro algoritmo?\n" + "1. Si\n" + "2. No");
					eleccion = scanner.nextInt();
					switch (eleccion) {
					case (1):
						menu = true;
						break;
					default:

					}

				}
			}
		}
		scanner.close();
	}
}
