package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.GestaoDados;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class InterfaceInicial extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CadastrarFilme cadastrarFilme;
	public JButton btnListarFilmes;
	private GestaoDados gestaoDados;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceInicial frame = new InterfaceInicial();
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
	public InterfaceInicial() {
		gestaoDados = new GestaoDados("jdbc:sqlite:C:/Users/desuf/Desktop/testeBanco/bancoIMDB.db", this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 492);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titulo1 = new JLabel("Seja bem vindo ao");
		titulo1.setFont(new Font("Tahoma", Font.BOLD, 30));
		titulo1.setForeground(new Color(255, 215, 0));
		titulo1.setBounds(164, 11, 287, 53);
		contentPane.add(titulo1);
		
		JButton btnCadastrarFilme = new JButton("Cadastrar Filme");
		btnCadastrarFilme.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCadastrarFilme.setBounds(151, 310, 136, 34);
		contentPane.add(btnCadastrarFilme);
		btnCadastrarFilme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarFilme = new CadastrarFilme();
				cadastrarFilme.setVisible(true);
				setVisible(false);
			}
			
		});
		
		btnListarFilmes = new JButton("Listar Filmes");
		btnListarFilmes.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnListarFilmes.setBounds(339, 310, 136, 34);
		contentPane.add(btnListarFilmes);
		btnListarFilmes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gestaoDados.listarFilmes();
				
			}
		});
		
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\desuf\\Desktop\\IMDB\\imagens\\imdbb.jpg"));
		lblNewLabel.setBounds(39, 42, 543, 283);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setBackground(new Color(0, 0, 0));
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\desuf\\Desktop\\IMDB\\imagens\\imdb - Copia.jpg"));
		lblNewLabel_2.setBounds(0, 0, 614, 453);
		contentPane.add(lblNewLabel_2);
		
	}
}
