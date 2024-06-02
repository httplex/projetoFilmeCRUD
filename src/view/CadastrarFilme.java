package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Diretor;
import model.DiretorDisponivel;
import model.GestaoDados;

import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import model.DiretorDisponivel;

public class CadastrarFilme extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField titulo;
	private JTextField ano;
	private JTextField genero;
	private JComboBox<Object> diretorComboBox;
	private GestaoDados gestaoDados;
	private InterfaceInicial interfaceInicial;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastrarFilme frame = new CadastrarFilme();
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
	public CadastrarFilme() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 492);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel tituloFilme = new JLabel("Título: ");
		tituloFilme.setBounds(124, 75, 46, 14);
		contentPane.add(tituloFilme);
		
		titulo = new JTextField();
		titulo.setBounds(198, 72, 86, 20);
		contentPane.add(titulo);
		titulo.setColumns(10);
		
		JLabel anoFilme = new JLabel("Ano: ");
		anoFilme.setBounds(110, 119, 46, 14);
		contentPane.add(anoFilme);
		
		ano = new JTextField();
		ano.setBounds(198, 116, 86, 20);
		contentPane.add(ano);
		ano.setColumns(10);
		
		JLabel generoFilme = new JLabel("Gênero(s): ");
		generoFilme.setBounds(95, 166, 61, 14);
		contentPane.add(generoFilme);
		
		genero = new JTextField();
		genero.setBounds(198, 163, 86, 20);
		contentPane.add(genero);
		genero.setColumns(10);
		
		JLabel diretorFilme = new JLabel("Diretor");
		diretorFilme.setBounds(97, 228, 46, 14);
		contentPane.add(diretorFilme);
		
		diretorComboBox = new JComboBox<>();
        diretorComboBox.addItem("Escolha o diretor");
        diretorComboBox.setBounds(153, 224, 131, 22);
        contentPane.add(diretorComboBox);
		
		String url = "jdbc:sqlite:C:/Users/desuf/Desktop/testeBanco/bancoIMDB.db";
        this.gestaoDados = new GestaoDados(url);
		
		preencherDiretoresComboBox();
		
		JButton btnSalvar = new JButton("Salvar Filme");
        btnSalvar.setBounds(278, 300, 120, 30);
        btnSalvar.addActionListener(e -> salvarFilme());
        contentPane.add(btnSalvar);
        
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(112, 300, 120, 30);
        contentPane.add(btnVoltar);
        btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interfaceInicial = new InterfaceInicial();
				interfaceInicial.setVisible(true);
				setVisible(false);
				
			}
		});
	}
	
	 private void preencherDiretoresComboBox() {
	        // Obtém a lista de diretores disponíveis
	        List<DiretorDisponivel> diretoresDisponiveis = gestaoDados.listarDiretoresDisponiveis();

	        // Adiciona os diretores disponíveis ao combobox
	        for (DiretorDisponivel diretor : diretoresDisponiveis) {
	            diretorComboBox.addItem(diretor);
	        }
	    }
	 
	 private void salvarFilme() {
	        String tituloFilme = titulo.getText();
	        int anoFilme = Integer.parseInt(ano.getText());
	        String generoFilme = genero.getText();
	        DiretorDisponivel diretorSelecionado = (DiretorDisponivel) diretorComboBox.getSelectedItem();

	        if (diretorSelecionado instanceof DiretorDisponivel && diretorSelecionado.getId() != -1) {
	            Diretor diretor = new Diretor(diretorSelecionado.getId(), diretorSelecionado.getNome(), null); // Supondo que a data de nascimento não seja necessária aqui
	            gestaoDados.salvarFilmeNoBanco(tituloFilme, anoFilme, generoFilme, diretor);
	        } else {
	            System.out.println("Por favor, selecione um diretor válido.");
	        }
	    }
}
