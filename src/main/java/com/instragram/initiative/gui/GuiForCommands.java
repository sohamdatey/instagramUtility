package com.instragram.initiative.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.instragram.initiative.io.IOHandler;
import com.instragram.initiative.like.HashTagHandler;

public class GuiForCommands extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
/*// JTextField
	static JTextField loginFileLocation;
	// JTextField
	static JTextField errorFileLocation;
	// JTextField
*/	static JTextField hashTagFileLocation;
/*
	static JLabel loginLocationLabel;
	static JLabel errorLocationLabel;*/
	static JLabel hashtagDataLabel;

	// JFrame
	static JFrame f;
	// JButton
	static JButton b;
	// label to diaplay text
	static JLabel l;
	// default constructor
	static JLabel processingAppenders;

	GuiForCommands() {
	}

	// main class
	public static void main(String[] args) {
		// create a new frame to stor text field and button
		f = new JFrame("Batch HashTag Processor");
		// create a label to display text
		l = new JLabel("");

		// create a new button
		b = new JButton("Run");
		// create a object of the text class
		GuiForCommands te = new GuiForCommands();
		// addActionListener to button
		b.addActionListener(te);
		// create a object of JTextField with 16 columns

		processingAppenders = new JLabel();
		//loginLocationLabel = new JLabel();
		hashtagDataLabel = new JLabel();
		//errorLocationLabel = new JLabel();

		//loginLocationLabel.setText("Enter Location");
		hashtagDataLabel.setText("Enter HashTag/login/error FileLocation Type");
		//errorLocationLabel.setText("Enter Font Name");

		//loginFileLocation = new JTextField(15);
		//errorFileLocation = new JTextField(15);
		hashTagFileLocation = new JTextField(15);

		JPanel p = new JPanel();

		// add buttons and textfield to panel
		//p.add(loginLocationLabel);
		//p.add(errorLocationLabel);
		//p.add(loginFileLocation);
		p.add(hashtagDataLabel);
		//p.add(errorFileLocation);
		p.add(hashTagFileLocation);
		p.add(b);
		p.add(l);
		p.add(processingAppenders);
		// add panel to frame
		f.add(p);
		// set the size of frame
		f.setSize(300, 500);

		f.show();
	}

	// if the button is pressed
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s.equals("Run")) {
			// set the text of the label to the text of the field
			if (hashTagFileLocation.getText().equals("")) {
				l.setText("Please Enter A HashTagFileLocation Location");
			} else {
				l.setText("You Entered Location : " + hashTagFileLocation.getText());

				String arguments[] = new String[4];
				arguments[0] = hashTagFileLocation.getText().trim();
			//	arguments[0] = loginFileLocation.getText().trim();
			//	arguments[2] = errorFileLocation.getText().trim();

				try {
				  HashTagHandler.handleHashTagFiles(arguments);
					processingAppenders.setText("Processing Done.");
				} catch (Exception exception) {
					IOHandler.appendErrorsToFile(exception, IOHandler.fileRecognizer("error.txt",  IOHandler.filesInFolder(arguments[0])));
					processingAppenders.setText(exception.toString());
				}
			}

		}
	}
}
