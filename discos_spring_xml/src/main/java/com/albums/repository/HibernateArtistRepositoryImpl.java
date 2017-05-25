package com.albums.repository;

import java.sql.*;

import com.albums.model.*;

public class HibernateArtistRepositoryImpl implements ArtistRepository {

	private static final String databaseUrl = "jdbc:sqlserver://CIT011924CPS\\SQLEXPRESS;databaseName=Discos";
	private static final String databaseUser = "discosadmin";
	private static final String databasePassword = "Suporte;123";

	public HibernateArtistRepositoryImpl() throws ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	}

	public Artist find(int id) {
		Connection conexao = null;
		PreparedStatement comando = null;
		ResultSet resultados = null;

		try {
			String query = "SELECT Id, Nome FROM Artistas WHERE Id = ?";
			conexao = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
			comando = conexao.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			comando.setInt(1, id);

			resultados = comando.executeQuery();
			if (resultados.first()) {
				Artist artista = new Artist();
				artista.setId(resultados.getInt("Id"));
				artista.setName(resultados.getString("Nome"));

				return artista;
			}

			return null;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (resultados != null)
					resultados.close();

				if (comando != null)
					comando.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	public void create(Artist artista) {
		try (Connection conexao = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
				Statement comando = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				ResultSet resultados = comando.executeQuery("SELECT Id, Nome FROM Artistas");) {

			resultados.moveToInsertRow();
			resultados.updateString("Nome", artista.getName());
			resultados.insertRow();

			resultados.last();
			artista.setId(resultados.getInt("Id"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
