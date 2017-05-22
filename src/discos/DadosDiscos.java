package discos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import discos.entity.Artista;
import discos.entity.Disco;
import discos.entity.Gravadora;

public class DadosDiscos {
	private static final String urlBanco = "jdbc:sqlserver://CIT011924CPS\\SQLEXPRESS;databaseName=Discos";
	private static final String usuario = "discosadmin";
	private static final String senha = "Suporte;123";

	private Connection conexao;

	public void conectar() throws SQLException, ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		this.conexao = DriverManager.getConnection(urlBanco, usuario, senha);
	}

	public List<Disco> listarDiscos() throws SQLException {
		Statement comando = null;
		ResultSet resultados = null;

		try {
			comando = this.conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultados = comando.executeQuery("SELECT Id, Titulo, IdArtista, IdGravadora, Ano FROM Discos");

			List<Disco> discos = new ArrayList<Disco>();

			while ((resultados.next())) 
			{
				Disco disco = new Disco();
				disco.setId(resultados.getInt("Id"));
				disco.setTitulo(resultados.getString("Titulo"));
				disco.setArtista(this.buscarArtista(resultados.getInt("IdArtista")));
				disco.setGravadora(this.buscarGravadora(resultados.getInt("IdGravadora")));
				disco.setAno(resultados.getInt("Ano"));

				discos.add(disco);
			}

			return discos;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (resultados != null) {
				resultados.close();
			}

			if (comando != null) {
				comando.close();
			}
		}
	}

	public Gravadora buscarGravadora(int id) throws SQLException {
		Statement comando = null;
		ResultSet resultados = null;

		try {
			comando = this.conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultados = comando.executeQuery("SELECT Id, Nome FROM Gravadoras WHERE Id = " + id);

			if (resultados.first()) {
				Gravadora gravadora = new Gravadora();
				gravadora.setId(resultados.getInt("Id"));
				gravadora.setNome(resultados.getString("Nome"));

				return gravadora;
			}

			return null;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (resultados != null) {
				resultados.close();
			}

			if (comando != null) {
				comando.close();
			}
		}
	}

	public Artista buscarArtista(int id) throws SQLException {
		Statement comando = null;
		ResultSet resultados = null;

		try {
			comando = this.conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultados = comando.executeQuery("SELECT Id, Nome FROM Artistas WHERE Id = " + id);

			if (resultados.first()) {
				Artista artista = new Artista();
				artista.setId(resultados.getInt("Id"));
				artista.setNome(resultados.getString("Nome"));

				return artista;
			}

			return null;
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (resultados != null) {
				resultados.close();
			}

			if (comando != null) {
				comando.close();
			}
		}
	}

	public void desconectar() {
		try {
			if (this.conexao != null) {
				this.conexao.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
