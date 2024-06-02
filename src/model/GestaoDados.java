package model;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import view.InterfaceInicial;

public class GestaoDados {
	private List<Diretor> diretores;
	private List<Filme> filmes;
	
    private String url;
    private GestaoDados gestaoDados;
    private InterfaceInicial interfaceInicial;
    
    public GestaoDados(String url) {
        this.diretores = new ArrayList<>();
        this.filmes = new ArrayList<>();
        this.url = url; // URL do banco de dados
    } 
   
    // Construtor que aceita uma String como argumento
    public GestaoDados(String url, InterfaceInicial interfaceInicial) {
        this.diretores = new ArrayList<>();
        this.filmes = new ArrayList<>();
        this.url = url; // URL do banco de dados
        this.interfaceInicial = interfaceInicial;
    }

    public void adicionarDiretor(Diretor diretor) {
        diretores.add(diretor);
    }
    
    public void adicionarFilme(Filme filme) {
    	filmes.add(filme);
    }
    
    //---------------------------- LÓGICA PARA DIRETOR -------------------------------------------
    public void salvarDiretorNoBanco(String nome, LocalDate dataNascimento) {
        // Conectar ao banco de dados e inserir o diretor
        try (Connection conexao = DriverManager.getConnection(url)) {
            String sqlInsert = "INSERT INTO Diretor (nome, data_nascimento) VALUES (?, ?)";
            PreparedStatement pstmt = conexao.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, nome);
            pstmt.setString(2, dataNascimento.toString());
            pstmt.executeUpdate();

            // Obter o id gerado automaticamente
            int id = -1;
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            // Criar o objeto Diretor e adicioná-lo à lista
            Diretor diretor = new Diretor(id, nome, dataNascimento);
            adicionarDiretor(diretor);

            System.out.println("Diretor salvo com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public List<Diretor> listarDiretores() {
        List<Diretor> listaDiretores = new ArrayList<>();
        try (Connection conexao = DriverManager.getConnection(url)) {
            String sqlSelect = "SELECT * FROM Diretor";
            PreparedStatement pstmt = conexao.prepareStatement(sqlSelect);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                LocalDate dataNascimento = LocalDate.parse(rs.getString("data_nascimento"));
                Diretor diretor = new Diretor(id, nome, dataNascimento);
                listaDiretores.add(diretor);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return listaDiretores;
    }

    
    public List<DiretorDisponivel> listarDiretoresDisponiveis() {
        List<DiretorDisponivel> diretoresDisponiveis = new ArrayList<>();
        try (Connection conexao = DriverManager.getConnection(url)) {
            String sqlSelect = "SELECT id, nome FROM Diretor";
            PreparedStatement pstmt = conexao.prepareStatement(sqlSelect);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                DiretorDisponivel diretor = new DiretorDisponivel(id, nome);
                diretoresDisponiveis.add(diretor);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return diretoresDisponiveis;
    }
    
    public List<Diretor> getDiretores() {
    	return diretores;
    }
    
    //----------------------------------------------------------------------------------------------------
    //---------------------------- LÓGICA PARA FILME / CRUD OK -------------------------------------------
    
    public void salvarFilmeNoBanco(String titulo, int ano, String genero, Diretor diretor) {
        // Conectar ao banco de dados e inserir o filme
        try (Connection conexao = DriverManager.getConnection(url)) {
            String sqlInsert = "INSERT INTO Filme (titulo, ano, genero, diretor_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conexao.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, titulo);
            pstmt.setInt(2, ano);
            pstmt.setString(3, genero.toString());
            pstmt.setInt(4, diretor.getId());
            pstmt.executeUpdate();

            // Obter o id gerado automaticamente
            int id = -1;
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            // Criar o objeto Diretor e adicioná-lo à lista
            Filme filme = new Filme(id, titulo, ano, genero, diretor);
            adicionarFilme(filme);

            System.out.println("Filme salvo com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
    
    // ************************* USAR COMO MODELO PARA OS OUTROS ***************************
    public void listarFilmes() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Título");
        model.addColumn("Ano");
        model.addColumn("Gênero");
        model.addColumn("Diretor");

        // Conectar ao banco de dados e buscar os dados
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT f.id, f.titulo, f.ano, f.genero, d.nome AS diretor_nome FROM Filme f JOIN Diretor d ON f.diretor_id = d.id")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                int ano = rs.getInt("ano");
                String genero = rs.getString("genero");
                String nomeDiretor = rs.getString("diretor_nome");
                model.addRow(new Object[]{id, titulo, ano, genero, nomeDiretor});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnAlterar = new JButton("Alterar");
        btnAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                int selectedColumn = table.getSelectedColumn();
                if (selectedRow != -1 && selectedColumn != -1) {
                    String columnName = table.getColumnName(selectedColumn);
                    Object currentValue = table.getValueAt(selectedRow, selectedColumn);
                    String newValue = JOptionPane.showInputDialog(null, "Novo valor para " + columnName + ":", currentValue);
                    if (newValue != null && !newValue.isEmpty()) {
                        table.setValueAt(newValue, selectedRow, selectedColumn);
                        int id = (int) table.getValueAt(selectedRow, 0); // Assume que a primeira coluna é o ID
                        salvarAlteracoes(id, columnName, newValue); // Chamada para salvar as alterações no banco de dados
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um campo para alterar.");
                }
            }
        });

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) table.getValueAt(selectedRow, 0); // Assume que a primeira coluna é o ID
                    int confirmation = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este filme?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        excluirFilme(id);
                        model.removeRow(selectedRow);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um filme para excluir.");
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAlterar);
        buttonPanel.add(btnExcluir);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        JOptionPane.showMessageDialog(null, panel, "Lista de Filmes", JOptionPane.PLAIN_MESSAGE);
    }

    private void salvarAlteracoes(int id, String columnName, String newValue) {
        // Ajustar o nome da coluna se for "Diretor"
        if (columnName.equals("Diretor")) {
            // Aqui você precisará converter o nome do diretor para o ID correspondente.
            // Isso pode exigir uma consulta adicional ao banco de dados.
            columnName = "diretor_id";
            newValue = getDiretorIdByName(newValue);
        }

        // Atualizar o banco de dados com as novas informações
        String sql = "UPDATE Filme SET " + columnName + " = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Tentar definir o tipo de dado correto
            if (columnName.equals("ano")) {
                pstmt.setInt(1, Integer.parseInt(newValue));
            } else {
                pstmt.setString(1, newValue);
            }
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private String getDiretorIdByName(String nomeDiretor) {
        String diretorId = null;
        String sql = "SELECT id FROM Diretor WHERE nome = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomeDiretor);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    diretorId = rs.getString("id");
                } else {
                    JOptionPane.showMessageDialog(null, "Diretor não encontrado.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return diretorId;
    }

    private void excluirFilme(int id) {
        String sql = "DELETE FROM Filme WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
}
