import java.util.Scanner;

public class parte_2 {
	public static void main(String[] args) {

		System.out.println("Tarea 3 - Parte 2");
		System.out.println("Porfavor escoja un grafo a analizar:");
		System.out.println("1. distances5\n" + "2. distances100\n" + "3. distances100_costosminimos\n"
				+ "4. distances1000_202110\n" + "5. distances1000_202110_costosminimos\n" + "6. distancesDisconnected\n"
				+ "7. Otro Archivo");
		System.out.print("Ingrese la opción: ");

		Scanner scanner = new Scanner(System.in);
		int eleccion = scanner.nextInt();
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
			System.out.println("La opción no es valida. Porfavor intentelo de nuevo");

		}
		System.out.println("\n");
		Grafo f = new Grafo("datos/" + dirArchivo);
		int numComponentesConectados = f.componentesConectados();
		if (numComponentesConectados == 1) {
			System.out.println("El grafo esta conectado. No hay subcomponentes");
		} else {
			System.out.println("El grafo tiene " + numComponentesConectados + " subconjuntos conectados.");
		}

		if (f.componentesConectados() > 1) {
			f.imprimirSubconjuntosConectados(f.componentesConectados());
		}
		scanner.close();
	}
}
