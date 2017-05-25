import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import com.discos.service.*;
import com.discos.model.*;


public class CadastroDisco {

	public static void main(String[] args) {

		DiscoService service = new DiscoServiceImpl();

		try {
			String opcao = "";
			BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));

			int aux;
			Disco disco;

			do {
				exibirMenuDiscos();

				opcao = leitor.readLine().trim();

				if (opcao.compareTo("L") == 0) {

					listarDiscos(service.findAll());
				} else if (opcao.compareTo("B") == 0) {
					System.out.print("Informe o código do disco:");
					aux = Integer.parseInt(leitor.readLine().trim());

					// disco = service.buscarDisco(aux);
					// exibirDisco(disco);
				} else if (opcao.compareTo("N") == 0) {
					List<Disco> discos = new ArrayList<Disco>();

					do {
						disco = new Disco();
						lerDadosDisco(service, leitor, disco);
						discos.add(disco);

						System.out.print("Deseja incluir mais um disco (sim/nao)? ");
						opcao = leitor.readLine().trim();
					} while (opcao.compareTo("sim") == 0);

					// service.salvarDiscos(discos);
				} else if (opcao.compareTo("M") == 0) {
					System.out.print("Informe o código do disco:");
					aux = Integer.parseInt(leitor.readLine().trim());

					// disco = service.buscarDisco(aux);

					// lerDadosDisco(service, leitor, disco);

					// service.modificarDisco(disco);
				} else if (opcao.compareTo("D") == 0) {
					System.out.print("Informe o código do disco:");
					aux = Integer.parseInt(leitor.readLine().trim());

					// service.deletarDisco(aux);
				} else if (opcao.compareTo("A") == 0) {
					System.out.print("Informe o código do artista:");
					aux = Integer.parseInt(leitor.readLine().trim());

					// listarDiscos(service.filtrarDiscos(aux, 0));
				}

			} while (opcao.compareTo("S") != 0);

		} catch (IOException e) {
			System.err.println("Não foi possível ler a opção selecionada pelo usuário: " + e.getMessage());
		}
	}

	private static void lerDadosDisco(DiscoService service, BufferedReader leitor, Disco disco)
			throws IOException {
		int aux;
		System.out.print("Informe o título do disco:");
		disco.setTitulo(leitor.readLine().trim());

		System.out.print("Informe o código do artista (0 para inserir novo):");
		aux = Integer.parseInt(leitor.readLine().trim());

		switch (aux) {
		case 0:
			Artista artista = new Artista();
			System.out.print("Informe o nome do artista:");
			artista.setNome(leitor.readLine().trim());
			disco.setArtista(artista);
			break;
		default:
			// disco.setArtista(service.buscarArtista(aux));
		}

		System.out.print("Informe o ano de gravação do disco:");
		disco.setAno(Integer.parseInt(leitor.readLine().trim()));

		System.out.print("Informe o código da gravadora (0 para inserir novo):");
		aux = Integer.parseInt(leitor.readLine().trim());

		switch (aux) {
		case 0:
			Gravadora gravadora = new Gravadora();
			System.out.print("Informe o nome da gravadora:");
			gravadora.setNome(leitor.readLine().trim());
			disco.setGravadora(gravadora);
			break;
		default:
			// disco.setGravadora(service.buscarGravadora(aux));
		}
	}

	private static void exibirMenuDiscos() {
		System.out.println();
		System.out.println();
		System.out.println("**********Cadastro de discos**********");
		System.out.println("Digite a opcao desejada:");
		System.out.println("L - Listar todos");
		System.out.println("B - Buscar disco");
		System.out.println("N - Novo disco");
		System.out.println("M - Modificar disco");
		System.out.println("D - Deletar disco");
		System.out.println("A - Discos de um artista");
		System.out.println("S - Sair");
	}

	private static void listarDiscos(List<Disco> discos) {
		if (discos.size() > 0) {
			System.out.println();
			for (Disco d : discos) {
				exibirDisco(d);
			}
		} else {
			System.out.println("Nenhum disco cadastrado.");
		}
	}

	private static void exibirDisco(Disco disco) {
		System.out.format("%-4d. %-20s - %-4d. %-20s - %-4d  %-20s - %-4d views", disco.getId(), disco.getTitulo(),
				disco.getArtista() != null ? disco.getArtista().getId() : "",
				disco.getArtista() != null ? disco.getArtista().getNome() : "", disco.getAno(),
				disco.getGravadora() != null ? disco.getGravadora().getNome() : "", disco.getVisualizacoes());
		System.out.println();
	}
}
