package test;

import javax.swing.JFrame;

import main.*;

public class Main {

	public static void main(String[] args) {
		JFrame obj=new JFrame();
		Gameplay1P gamePlay = new Gameplay1P();
//		Gameplay2P gamePlay = new Gameplay2P();
		
		obj.setBounds(200, 200, 715, 512);
		obj.setTitle("Ping Pong");		
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
        obj.setVisible(true);
	}

}