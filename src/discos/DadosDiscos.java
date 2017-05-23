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
		try (Statement comando = this.conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
				ResultSet resultados = comando
						.executeQuery("SELECT Id, Titulo, IdArtista, IdGravadora, Ano, Visualizacoes FROM Discos");) {

			List<Disco> discos = new ArrayList<Disco>();

			while ((resultados.next())) {
				Disco disco = new Disco();
				disco.setId(resultados.getInt("Id"));
				disco.setTitulo(resultados.getString("Titulo"));
				disco.setArtista(this.buscarArtista(resultados.getInt("IdArtista")));
				disco.setGravadora(this.buscarGravadora(resultados.getInt("IdGravadora")));
				disco.setAno(resultados.getInt("Ano"));
				disco.setVisualizacoes(resultados.getInt("Visualizacoes"));

				discos.add(disco);
			}

			return discos;
		} catch (SQLException e) {
			throw e;
		}
	}

	public Disco buscarDisco(int id) throws SQLException {
		PreparedStatement comando = null;
		ResultSet resultados = null;
		
		try {
			
			String query = "SELECT Id, Titulo, IdArtista, IdGravadora, Ano, Visualizacoes FROM Discos WHERE Id = ?";
			comando = this.conexao.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			comando.setInt(1, id);
			
			resultados = comando.executeQuery();
			if (resultados.first()) {

				Disco disco = new Disco();
				disco.setId(resultados.getInt("Id"));
				disco.setTitulo(resultados.getString("Titulo"));
				disco.setArtista(this.buscarArtista(resultados.getInt("IdArtista")));
				disco.setGravadora(this.buscarGravadora(resultados.getInt("IdGravadora")));
				disco.setAno(resultados.getInt("Ano"));
				disco.setVisualizacoes(resultados.getInt("Visualizacoes"));

				resultados.updateInt("Visualizacoes", disco.getVisualizacoes() + 1);
				resultados.updateRow();

				return disco;
			}

			return null;
		} catch (SQLException e) {
			throw e;
		} finally {
			if(resultados != null)
				resultados.close();
			
			if(comando != null)
				comando.close();
		}
	}
	
	public void salvarDisco(Disco disco) throws SQLException
	{
		try (Statement comando = this.conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
				ResultSet resultados = comando.executeQuery(
						"SELECT Titulo, IdArtista, IdGravadora, Ano, Visualizacoes FROM Discos");) {
			
				if(disco.getArtista().getId() == 0)
				{
					this.salvarArtista(disco.getArtista());
				}
				
				if(disco.getGravadora().getId() == 0)
				{
					this.salvarGravadora(disco.getGravadora());
				}
			
				resultados.moveToInsertRow();
				resultados.updateString("Titulo", disco.getTitulo());
				resultados.updateInt("IdArtista", disco.getArtista().getId());
				resultados.updateInt("IdGravadora", disco.getGravadora().getId());
				resultados.updateInt("Ano", disco.getAno());
			
				resultados.insertRow();
				
				
				
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public void modificarDisco(Disco disco) throws SQLException
	{
		PreparedStatement comando = null;
		
		try {
			
			if(disco.getArtista().getId() == 0)
			{
				this.salvarArtista(disco.getArtista());
			}
			
			if(disco.getGravadora().getId() == 0)
			{
				this.salvarGravadora(disco.getGravadora());
			}
			
			String sql = "UPDATE Discos SET Titulo = ?, IdArtista = ?, IdGravadora = ?, Ano = ? WHERE Id = ?";
			comando = this.conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			comando.setString(1, disco.getTitulo());
			comando.setInt(2, disco.getArtista().getId());
			comando.setInt(3, disco.getGravadora().getId());
			comando.setInt(4, disco.getAno());
			comando.setInt(5, disco.getId());
			
			comando.executeUpdate();			
				
		} catch (SQLException e) {
			throw e;
		} finally {	
			if(comando != null)
				comando.close();
		}
	}
	
	public void deletarDisco(int id) throws SQLException
	{
		PreparedStatement comando = null;
		
		try {
			
			String sql = "DELETE FROM Discos WHERE Id = ?";
			comando = this.conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			comando.setInt(1, id);
			
			comando.executeUpdate();			
				
		} catch (SQLException e) {
			throw e;
		} finally {	
			if(comando != null)
				comando.close();
		}
	}

	public Gravadora buscarGravadora(int id) throws SQLException {
		PreparedStatement comando = null;
		ResultSet resultados = null;
		
		try {
			String query = "SELECT Id, Nome FROM Gravadoras WHERE Id = ?";
			comando = this.conexao.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			comando.setInt(1, id);
			
			resultados = comando.executeQuery();
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
			if(resultados != null)
				resultados.close();
			
			if(comando != null)
				comando.close();
		}
	}
	
	public void salvarGravadora(Gravadora gravadora) throws SQLException
	{
		try (Statement comando = this.conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
				ResultSet resultados = comando.executeQuery(
						"SELECT Id, Nome FROM Gravadoras");) {
			
				resultados.moveToInsertRow();
				resultados.updateString("Nome", gravadora.getNome());
				resultados.insertRow();
				
				resultados.last();
				gravadora.setId(resultados.getInt("Id"));
				
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public Artista buscarArtista(int id) throws SQLException {
		PreparedStatement comando = null;
		ResultSet resultados = null;
		
		try {
			String query = "SELECT Id, Nome FROM Artistas WHERE Id = ?";
			comando = this.conexao.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			comando.setInt(1, id);
			
			resultados = comando.executeQuery();
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
			if(resultados != null)
				resultados.close();
			
			if(comando != null)
				comando.close();
		}
	}
	
	public void salvarArtista(Artista artista) throws SQLException
	{
		try (Statement comando = this.conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
				ResultSet resultados = comando.executeQuery(
						"SELECT Id, Nome FROM Artistas");) {
			
				resultados.moveToInsertRow();
				resultados.updateString("Nome", artista.getNome());
				resultados.insertRow();
				
				resultados.last();
				artista.setId(resultados.getInt("Id"));
				
		} catch (SQLException e) {
			throw e;
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
