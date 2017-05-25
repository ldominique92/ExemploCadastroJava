package com.albums.repository;

import java.sql.*;
import java.util.*;

import com.albums.model.*;

public class HibernateAlbumRepositoryImpl implements AlbumRepository {

	private static final String databaseUrl = "jdbc:sqlserver://CIT011924CPS\\SQLEXPRESS;databaseName=Discos";
	private static final String databaseUser = "discosadmin";
	private static final String databasePassword = "Suporte;123";

	private ArtistRepository artistRepository;
	private LabelRepository labelRepository;
	
	public HibernateAlbumRepositoryImpl() throws ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	}

	@Override
	public List<Album> findAll() {

		try (Connection conexao = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
				Statement comando = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet resultados = comando
						.executeQuery("SELECT Id, Titulo, IdArtista, IdGravadora, Ano, Visualizacoes FROM Discos");) {

			List<Album> discos = new ArrayList<Album>();

			while ((resultados.next())) {
				Album disco = new Album();
				disco.setId(resultados.getInt("Id"));
				disco.setTitle(resultados.getString("Titulo"));
				disco.setArtist(artistRepository.find(resultados.getInt("IdArtista")));
				disco.setLabel(this.buscarGravadora(resultados.getInt("IdGravadora")));
				disco.setYear(resultados.getInt("Ano"));
				disco.setViews(resultados.getInt("Visualizacoes"));

				discos.add(disco);
			}

			return discos;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Album find(int id) {
		PreparedStatement comando = null;
		ResultSet resultados = null;
		Connection conexao = null;

		try {

			conexao = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
			String query = "SELECT Id, Titulo, IdArtista, IdGravadora, Ano, Visualizacoes FROM Discos WHERE Id = ?";
			comando = conexao.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			comando.setInt(1, id);

			resultados = comando.executeQuery();
			if (resultados.first()) {

				Album disco = new Album();
				disco.setId(resultados.getInt("Id"));
				disco.setTitle(resultados.getString("Titulo"));
				disco.setArtist(this.buscarArtista(resultados.getInt("IdArtista")));
				disco.setLabel(this.buscarGravadora(resultados.getInt("IdGravadora")));
				disco.setYear(resultados.getInt("Ano"));
				disco.setViews(resultados.getInt("Visualizacoes"));

				resultados.updateInt("Visualizacoes", disco.getViews() + 1);
				resultados.updateRow();

				return disco;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultados.close();
				if (resultados != null)

					if (comando != null)
						comando.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public void createBatch(List<Album> discos) {
		CallableStatement comando = null;
		Connection conexao = null;

		try {

			conexao = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
			comando = conexao.prepareCall("{ call SP_INSERT_DISCOS(?,?,?,?,?,?) }");

			for (Album disco : discos) {
				comando.setString(1, disco.getTitle());

				comando.setInt(2, disco.getArtist().getId());
				comando.setString(3, disco.getArtist().getName());

				comando.setInt(4, disco.getLabel().getId());
				comando.setString(5, disco.getLabel().getName());

				comando.setInt(6, disco.getYear());

				comando.addBatch();
			}

			// comando.registerOutParameter(2, Types.INTEGER);
			// comando.registerOutParameter(4, Types.INTEGER);
			// disco.getArtista().setId(comando.getInt(2);
			// disco.getGravadora().setId(comando.getInt(4);

			comando.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				if (conexao != null)
					conexao.close();

				if (comando != null)
					comando.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void edit(Album disco) {
		PreparedStatement comando = null;
		Connection conexao = null;

		try {

			if (disco.getArtist().getId() == 0) {
				this.salvarArtista(disco.getArtist());
			}

			if (disco.getLabel().getId() == 0) {
				this.salvarGravadora(disco.getLabel());
			}

			String sql = "UPDATE Discos SET Titulo = ?, IdArtista = ?, IdGravadora = ?, Ano = ? WHERE Id = ?";
			conexao = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
			comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			comando.setString(1, disco.getTitle());
			comando.setInt(2, disco.getArtist().getId());
			comando.setInt(3, disco.getLabel().getId());
			comando.setInt(4, disco.getYear());
			comando.setInt(5, disco.getId());

			comando.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conexao != null)
					conexao.close();

				if (comando != null)
					comando.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void delete(int id) {
		PreparedStatement comando = null;
		Connection conexao = null;

		try {

			String sql = "DELETE FROM Discos WHERE Id = ?";
			conexao = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
			comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			comando.setInt(1, id);

			comando.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conexao != null)
					conexao.close();

				if (comando != null)
					comando.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Album> filter(int idArtista, int idGravadora) {

		ResultSet resultados = null;

		try (Connection conexao = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
				CallableStatement comando = conexao.prepareCall("{ call SP_GETDISCOS_BY_FILTER(?,?) }");) {
			comando.setInt(1, idArtista);
			comando.setInt(2, idGravadora);

			resultados = comando.executeQuery();

			List<Album> discos = new ArrayList<Album>();

			while ((resultados.next())) {
				Album disco = new Album();
				disco.setId(resultados.getInt("Id"));
				disco.setTitle(resultados.getString("Titulo"));
				disco.setArtist(this.buscarArtista(resultados.getInt("IdArtista")));
				disco.setLabel(this.buscarGravadora(resultados.getInt("IdGravadora")));
				disco.setYear(resultados.getInt("Ano"));
				disco.setViews(resultados.getInt("Visualizacoes"));

				discos.add(disco);
			}

			return discos;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultados != null)
					resultados.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
