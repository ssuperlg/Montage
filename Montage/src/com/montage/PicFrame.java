package com.montage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PicFrame extends JFrame {

	private PicPanel contentPane;

	/**
	 * Create the frame.
	 */
	public PicFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new PicPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
