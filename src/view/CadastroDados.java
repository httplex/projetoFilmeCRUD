package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Diretor;
import model.GestaoDados;

import javax.swing.JTextField;
import javax.swing.JLabel;

public class CadastroDados extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nome;
	private JTextField data_nascimento;
	private GestaoDados gestaoDados;
	private DefaultTableModel tableModel;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroDados frame = new CadastroDados();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CadastroDados() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nomeDiretor = new JLabel("Nome: ");
		nomeDiretor.setBounds(36, 39, 46, 14);
		contentPane.add(nomeDiretor);
		
		nome = new JTextField();
		nome.setBounds(92, 36, 86, 20);
		contentPane.add(nome);
		nome.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Data de nscimento: ");
		lblNewLabel_1.setBounds(36, 73, 107, 14);
		contentPane.add(lblNewLabel_1);
		
		data_nascimento = new JTextField();
		data_nascimento.setBounds(153, 70, 86, 20);
		contentPane.add(data_nascimento);
		data_nascimento.setColumns(10);
		
		JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(150, 120, 89, 23);
        contentPane.add(btnSalvar);

        // URL do banco de dados
        String url = "jdbc:sqlite:C:/Users/desuf/Desktop/testeBanco/bancoIMDB.db";
        gestaoDados = new GestaoDados(url);

        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomeDiretor = nome.getText();
                String dataNascimentoStr = data_nascimento.getText();

                try {
                    LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr);
                    gestaoDados.salvarDiretorNoBanco(nomeDiretor, dataNascimento);
                    System.out.println("Diretor salvo com sucesso!");
                } catch (DateTimeParseException ex) {
                    System.out.println("Formato de data inválido. Use o formato aaaa-mm-dd.");
                }
            }
        });
        
        JButton btnListar = new JButton("Listar");
        btnListar.setBounds(250, 120, 89, 23);
        contentPane.add(btnListar);

        btnListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarTabela();
            }
        });

        // Tabela para exibir diretores
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(36, 160, 500, 150);
        contentPane.add(scrollPane);

        tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Nome", "Data de Nascimento" }
        );
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);
    }

    private void atualizarTabela() {
        limparTabela();
        List<Diretor> diretores = gestaoDados.listarDiretores();
        for (Diretor diretor : diretores) {
            Object[] row = {diretor.getNome(), diretor.getData_nascimento() };
            tableModel.addRow(row);
        }
    }

    private void limparTabela() {
        tableModel.setRowCount(0);
    }
	
}
