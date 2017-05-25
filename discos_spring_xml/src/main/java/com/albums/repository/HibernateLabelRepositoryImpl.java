package com.albums.repository;

import java.sql.*;
import java.util.*;

import com.albums.model.*;

public class HibernateLabelRepositoryImpl implements ArtistRepository {

	private static final String databaseUrl = "jdbc:sqlserver://CIT011924CPS\\SQLEXPRESS;databaseName=Discos";
	private static final String databaseUser = "discosadmin";
	private static final String databasePassword = "Suporte;123";

	public HibernateLabelRepositoryImpl() throws ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	}

	public Label buscarGravadora(int id) throws SQLException {
		Connection conexao = null;
		PreparedStatement comando = null;
		ResultSet resultados = null;

		try {
			String query = "SELECT Id, Nome FROM Gravadoras WHERE Id = ?";
			conexao = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
			comando = conexao.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			comando.setInt(1, id);

			resultados = comando.executeQuery();
			if (resultados.first()) {
				Label gravadora = new Label();
				gravadora.setId(resultados.getInt("Id"));
				gravadora.setName(resultados.getString("Nome"));

				return gravadora;
			}

			return null;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (conexao != null)
					conexao.close();
				
				if (resultados != null)
					resultados.close();

				if (comando != null)
					comando.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void salvarGravadora(Label gravadora) throws SQLException {
		try (Connection conexao = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
				Statement comando = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				ResultSet resultados = comando.executeQuery("SELECT Id, Nome FROM Gravadoras");) {

			resultados.moveToInsertRow();
			resultados.updateString("Nome", gravadora.getName());
			resultados.insertRow();

			resultados.last();
			gravadora.setId(resultados.getInt("Id"));

		} catch (SQLException e) {
			throw e;
		}
	}

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
