

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

			do {
				System.out.println();
				System.out.println();
				System.out.println("**********Cadastro de discos**********");
				System.out.println("Digite a opcao desejada:");
				System.out.println("L - Listar");
				System.out.println("S - Sair");

				opcao = leitor.readLine().trim();

				if (opcao.compareTo("L") == 0) {

					List<Disco> discos = bancoDeDados.listarDiscos();

					if (discos.size() > 0) {
						System.out.println();
						for (Disco disco : discos) {
							System.out.format("%-4d. %-20s - %-20s - %-4d  %-20s", disco.getId(), disco.getTitulo(),
									disco.getArtista() != null ? disco.getArtista().getNome() : "", disco.getAno(),
									disco.getGravadora() != null ? disco.getGravadora().getNome() : "");
						}
					} else {
						System.out.println("Nenhum disco cadastrado.");
					}
				}
			} while (opcao.compareTo("S") != 0);

		} catch (SQLException e) {
			System.err.println("Erro ao conectar com banco de dados (" + e.getErrorCode() + "): " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println("Classe n�o encontrada: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("N�o foi poss�vel ler a op��o selecionada pelo usu�rio: " + e.getMessage());
		} finally {
			bancoDeDados.desconectar();
		}
	}
}