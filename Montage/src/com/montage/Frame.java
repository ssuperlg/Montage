package com.montage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Frame extends JFrame {

	private MyPanel contentPane;
	private JTextField text;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					File src = new File("d:\\img");
					File target = new File("d:\\target");
					src.mkdirs();
					target.mkdirs();
					Frame frame = new Frame();
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
	public Frame() {
		setTitle("Search From Github");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 322);
		contentPane = new MyPanel();
		getContentPane().add(contentPane);
		contentPane.setLayout(null);
		setContentPane(contentPane);
		text = new JTextField();
		text.setBounds(171, 113, 93, 35);
		text.setBackground(Color.GRAY);
		text.setColumns(10);
		contentPane.add(text);
		JButton button = new JButton("GO");
		button.setBounds(298, 229, 93, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String s = text.getText();
				String url = "https://github.com/search?q="+s+"&type=Users&utf8=%E2%9C%93";
				Model.catchImg(url);
				System.out.println(Model.getI());
				DealPic dp = new DealPic("d:\\img", "d:\\target",Model.getI());
				dp.run();
				System.out.println("OK");
				PicFrame picFrame = new PicFrame();
				picFrame.setVisible(true);
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.setBackground(UIManager.getColor("Button.darkShadow"));
		button.setForeground(Color.PINK);
		contentPane.add(button);
	}
}
