
import java.sql.SQLException;
import java.util.List;
import java.io.*;

import discos.DadosDiscos;
import discos.entity.*;

public class CRUD {
	public static void main(String[] args) {

		DadosDiscos bancoDeDados = new DadosDiscos();

		try {
			bancoDeDados.conectar();
			System.out.println("Conectado ao banco de dados com sucesso.");

			String opcao = "";
			BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));

			int aux;
			Disco disco;
			
			do {
				exibirMenuDiscos();

				opcao = leitor.readLine().trim();

				if (opcao.compareTo("L") == 0) {

					listarDiscos(bancoDeDados);
				}
				else if (opcao.compareTo("B") == 0)
				{
					System.out.print("Informe o código do disco:");
					aux = Integer.parseInt(leitor.readLine().trim());
					
					buscarDisco(bancoDeDados, aux);
				}
				else if (opcao.compareTo("N") == 0)
				{
					disco = new Disco();
					
					lerDadosDisco(bancoDeDados, leitor, disco);
					
					bancoDeDados.salvarDisco(disco);
				}
				else if (opcao.compareTo("M") == 0)
				{
					System.out.print("Informe o código do disco:");
					aux = Integer.parseInt(leitor.readLine().trim());
					
					disco = bancoDeDados.buscarDisco(aux);
					
					lerDadosDisco(bancoDeDados, leitor, disco);
					
					bancoDeDados.modificarDisco(disco);
				}
				else if (opcao.compareTo("D") == 0)
				{
					System.out.print("Informe o código do disco:");
					aux = Integer.parseInt(leitor.readLine().trim());
					
					bancoDeDados.deletarDisco(aux);
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

	private static void lerDadosDisco(DadosDiscos bancoDeDados, BufferedReader leitor, Disco disco)
			throws IOException, SQLException {
		int aux;
		System.out.print("Informe o título do disco:");
		disco.setTitulo(leitor.readLine().trim());
		
		System.out.print("Informe o código do artista (0 para inserir novo):");
		aux = Integer.parseInt(leitor.readLine().trim());
		
		switch(aux)
		{
			case 0:
				Artista artista = new Artista();
				System.out.print("Informe o nome do artista:");
				artista.setNome(leitor.readLine().trim());
				disco.setArtista(artista);
				break;
			default:
				disco.setArtista(bancoDeDados.buscarArtista(aux));
		}
		
		System.out.print("Informe o ano de gravação do disco:");
		disco.setAno(Integer.parseInt(leitor.readLine().trim()));
		
		System.out.print("Informe o código da gravadora (0 para inserir novo):");
		aux = Integer.parseInt(leitor.readLine().trim());
		
		switch(aux)
		{
			case 0:
				Gravadora gravadora = new Gravadora();
				System.out.print("Informe o nome da gravadora:");
				gravadora.setNome(leitor.readLine().trim());
				disco.setGravadora(gravadora);
				break;
			default:
				disco.setGravadora(bancoDeDados.buscarGravadora(aux));
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
		System.out.println("S - Sair");
	}

	private static void buscarDisco(DadosDiscos bancoDeDados, int id) throws IOException, SQLException {
		Disco disco = bancoDeDados.buscarDisco(id);
		exibirDisco(disco);
	}

	private static void listarDiscos(DadosDiscos bancoDeDados) throws SQLException {
		List<Disco> discos = bancoDeDados.listarDiscos();

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
		System.out.format("%-4d. %-20s - %-20s - %-4d  %-20s - %-4d views", disco.getId(),
				disco.getTitulo(), disco.getArtista() != null ? disco.getArtista().getNome() : "",
				disco.getAno(), disco.getGravadora() != null ? disco.getGravadora().getNome() : "",
				disco.getVisualizacoes());
		System.out.println();
	}
}
