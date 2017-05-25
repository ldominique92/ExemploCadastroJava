import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.albums.model.*;
import com.albums.service.*;


public class CadastroDisco {

	public static void main(String[] args) {

		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		AlbumService albumService = appContext.getBean("albumService", AlbumService.class);

		try {
			String option = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			int aux;
			Album album;

			do {
				showMenu();

				option = reader.readLine().trim();

				if (option.compareTo("L") == 0) {

					listAlbums(albumService.findAll());
				} else if (option.compareTo("B") == 0) {
					System.out.print("Informe o código do disco:");
					aux = Integer.parseInt(reader.readLine().trim());

					album = albumService.find(aux);
					showAlbum(album);
				} else if (option.compareTo("N") == 0) {
					List<Album> albums = new ArrayList<Album>();

					do {
						album = new Album();
						readAlbumData(appContext, reader, album);
						albums.add(album);

						System.out.print("Deseja incluir mais um disco (sim/nao)? ");
						option = reader.readLine().trim();
					} while (option.compareTo("sim") == 0);

					albumService.createBatch(albums);
				} else if (option.compareTo("M") == 0) {
					System.out.print("Informe o código do disco:");
					aux = Integer.parseInt(reader.readLine().trim());

					album = albumService.find(aux);

					readAlbumData(appContext, reader, album);

					albumService.edit(album);
				} else if (option.compareTo("D") == 0) {
					System.out.print("Informe o código do disco:");
					aux = Integer.parseInt(reader.readLine().trim());

					albumService.delete(aux);
				} else if (option.compareTo("A") == 0) {
					System.out.print("Informe o código do artista:");
					aux = Integer.parseInt(reader.readLine().trim());

					listAlbums(albumService.filter(aux, 0));
				}

			} while (option.compareTo("S") != 0);

		} catch (IOException e) {
			System.err.println("Não foi possível ler a opção selecionada pelo usuário: " + e.getMessage());
		}
	}

	private static void readAlbumData(ApplicationContext appContext, BufferedReader reader, Album album) throws IOException {
		int aux;
		ArtistService artistService = appContext.getBean("artistService", ArtistService.class);
		LabelService labelService = appContext.getBean("labelService", LabelService.class);
		
		System.out.print("Informe o título do disco:");
		album.setTitle(reader.readLine().trim());

		System.out.print("Informe o código do artista (0 para inserir novo):");
		aux = Integer.parseInt(reader.readLine().trim());

		switch (aux) {
		case 0:
			Artist artist = new Artist();
			System.out.print("Informe o nome do artista:");
			artist.setName(reader.readLine().trim());
			album.setArtist(artist);
			break;
		default:
			album.setArtist(artistService.find(aux));
		}

		System.out.print("Informe o ano de gravação do disco:");
		album.setYear(Integer.parseInt(reader.readLine().trim()));

		System.out.print("Informe o código da gravadora (0 para inserir novo):");
		aux = Integer.parseInt(reader.readLine().trim());

		switch (aux) {
		case 0:
			Label label = new Label();
			System.out.print("Informe o nome da gravadora:");
			label.setName(reader.readLine().trim());
			album.setLabel(label);
			break;
		default:
			album.setLabel(labelService.find(aux));
		}
	}

	private static void showMenu() {
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

	private static void listAlbums(List<Album> discos) {
		if (discos.size() > 0) {
			System.out.println();
			for (Album d : discos) {
				showAlbum(d);
			}
		} else {
			System.out.println("Nenhum disco cadastrado.");
		}
	}

	private static void showAlbum(Album disco) {
		System.out.format("%-4d. %-20s - %-4d. %-20s - %-4d  %-20s - %-4d views", disco.getId(), disco.getTitle(),
				disco.getArtist() != null ? disco.getArtist().getId() : "",
				disco.getArtist() != null ? disco.getArtist().getName() : "", disco.getYear(),
				disco.getLabel() != null ? disco.getLabel().getName() : "", disco.getViews());
		System.out.println();
	}
}
