/*
 * Matt Keefer
 * Parsing Calculator
 * 11/7/19
 */
package main;
import javax.swing.*;
import BreezySwing.*;

public class ParsingGUI extends GBFrame {

	JLabel label = addLabel("Enter operation:", 1,1,2,1);
	JTextField input = addTextField("", 2,1,2,1);
	JButton inputButton = addButton("Input", 3,1,1,1);
	JButton resetButton = addButton("Reset", 3,2,1,1);
	JLabel output = addLabel("", 4,1,2,1);
	Parser parser = new Parser();
	
	public static void main(String[] args) {
		JFrame frm = new ParsingGUI();
		frm.setTitle("Parsing Calculator");
		frm.setSize(350, 200);
		frm.setVisible(true);
	}
	
	public void buttonClicked(JButton button) {
		if(button==inputButton) {
			try {
				output.setText(parser.doTheMath(input.getText())); //performs specified math problem
			}
			catch(formatException e) { //displays error message
				output.setText(e.getMessage());
			}
		}
		if(button==resetButton) { //resets text fields
			input.setText("");
			output.setText("");
		}
	}
}