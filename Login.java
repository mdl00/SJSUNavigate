

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {

	public JFrame frame;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JTextField txtLogIn;
	private JTextField belowLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Login window = new Login();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(70, 130, 180));
		frame.setBounds(200, 200, 650, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SJSUNavigate Login");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblNewLabel.setBounds(222, 32, 206, 34);
		frame.getContentPane().add(lblNewLabel);
		
		txtUsername = new JTextField();
		txtUsername.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) 
			{
				if (txtUsername.getText().equals("Username"))
				{
					txtUsername.setText("");
				}
			}
			public void focusLost(FocusEvent e)
			{
				if (txtUsername.getText().equals(""))
				{
					txtUsername.setText("Username");
				}
			}
		});
		txtUsername.setText("Username");
		txtUsername.setToolTipText("Enter Username");
		txtUsername.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtUsername.setBounds(202, 95, 218, 42);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) 
			{
				if (txtPassword.getText().equals("Password"))
				{
					txtPassword.setEchoChar('*');
					txtPassword.setText("");
				}
				else
				{
					txtPassword.selectAll();
				}
			}
			public void focusLost(FocusEvent e)
			{
				if (txtPassword.getText().equals(""))
				{
					txtPassword.setText("Password");
					txtPassword.setEchoChar((char)(0));
				}
			}
		});
		txtPassword.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtPassword.setText("Password");
		txtPassword.setToolTipText("Enter Password");
		txtPassword.setEchoChar((char)(0));
		txtPassword.setBounds(202, 172, 218, 42);
		frame.getContentPane().add(txtPassword);
		
		JPanel loginBtn = new JPanel();
		loginBtn.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if (txtUsername.getText().contains("@sjsu.edu"))
				{
					belowLogin.setText("");
					JOptionPane.showMessageDialog(null, "Login successful!");
					frame.setVisible(false);
					frame.dispose();
					SJSUNav sn = new SJSUNav();
					sn.start();
				}
				else
				{
					belowLogin.setText("Username or Password is invalid!");
				}
			}
		});
		loginBtn.setBackground(SystemColor.menu);
		loginBtn.setBounds(170, 256, 284, 65);
		frame.getContentPane().add(loginBtn);
		loginBtn.setLayout(null);
		
		txtLogIn = new JTextField();
		txtLogIn.setForeground(Color.BLACK);
		txtLogIn.setEditable(false);
		txtLogIn.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if (txtUsername.getText().contains("@sjsu.edu"))
				{
					belowLogin.setText("");
					JOptionPane.showMessageDialog(null, "Login successful!");
					frame.setVisible(false);
					frame.dispose();
					SJSUNav sn = new SJSUNav();
					sn.start();
				}
				else
				{
					belowLogin.setText("Username or Password is invalid!");
				}
			}
		});
		txtLogIn.setBackground(SystemColor.menu);
		txtLogIn.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtLogIn.setText("Log In");
		txtLogIn.setBounds(121, 22, 39, 20);
		loginBtn.add(txtLogIn);
		txtLogIn.setColumns(10);
		txtLogIn.setBorder(null);
		
		belowLogin = new JTextField();
		belowLogin.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		belowLogin.setBackground(new Color(70, 130, 180));
		belowLogin.setEditable(false);
		belowLogin.setBounds(224, 332, 218, 20);
		frame.getContentPane().add(belowLogin);
		belowLogin.setColumns(10);
		belowLogin.setBorder(null);
	}
}
