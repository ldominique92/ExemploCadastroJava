
import java.sql.SQLException;
import java.util.List;
import java.io.*;

import discos.DadosDiscos;
import discos.entity.Disco;

public class CRUD {
	public static void main(String[] args) {

		DadosDiscos bancoDeDados = new DadosDiscos();

		try {
			bancoDeDados.conectar();
			System.out.println("Conectado ao banco de dados com sucesso.");

			String opcao = "";
			BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));

			List<Disco> discos;
			
			do {
				System.out.println();
				System.out.println();
				System.out.println("**********Cadastro de discos**********");
				System.out.println("Digite a opcao desejada:");
				System.out.println("L - Listar todos");
				System.out.println("B - Buscar disco");
				System.out.println("T - Top Five");
				System.out.println("S - Sair");

				opcao = leitor.readLine().trim();

				if (opcao.compareTo("L") == 0) {

					discos = bancoDeDados.listarDiscos();

					if (discos.size() > 0) {
						System.out.println();
						for (Disco disco : discos) {
							exibirDisco(disco);
						}
					} else {
						System.out.println("Nenhum disco cadastrado.");
					}
				}
				else if (opcao.compareTo("B") == 0)
				{
					System.out.print("Informe o código do disco:");
					int id = Integer.parseInt(leitor.readLine().trim());
					Disco disco = bancoDeDados.buscarDisco(id);
					exibirDisco(disco);
				}
			} while (opcao.compareTo("S") != 0);

		} catch (SQLException e) {
			System.err.println("Erro ao conectar com banco de dados (" + e.getErrorCode() + "): " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println("Classe não encontrada: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Não foi possível ler a opção selecionada pelo usuário: " + e.getMessage());
		} finally {
			bancoDeDados.desconectar();
		}
	}

	private static void exibirDisco(Disco disco) {
		System.out.format("%-4d. %-20s - %-20s - %-4d  %-20s - %-4d views", disco.getId(),
				disco.getTitulo(), disco.getArtista() != null ? disco.getArtista().getNome() : "",
				disco.getAno(), disco.getGravadora() != null ? disco.getGravadora().getNome() : "",
				disco.getVisualizacoes());
	}
}
