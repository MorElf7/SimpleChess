package main;

import javax.swing.JFrame;

import main.gameplay.*;
import main.pieces.Piece;

public class Main {

	public static void main(String[] args) {
		JFrame obj = new JFrame();
		Board game = new Board();
		obj.setBounds(150, 100, 617, 640);
		obj.setTitle("Chess");
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(game);
		obj.setVisible(true);
	}
}
