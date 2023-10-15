import java.util.Scanner;

public class parte_3 {
	public static void main(String[] args) {

		System.out.println("Tarea 3 - Parte 3");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Escriba el nombre del archivo que desea abrir: ");
		String dirArchivo = scanner.next();
		System.out.println("\n");
		Grafo f = new Grafo("datos/" + dirArchivo);
		f.DFS();
		if (f.getGrafoCiclico()) {
			System.out.println("El grafo tiene ciclos.");
		} else {
			System.out.println("El grafo es aciclico");
		}
		scanner.close();
	}
}
