package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Gameplay1P extends JPanel implements KeyListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6755726720920201929L;

	private boolean play = false;
	
	private int score = 0;
	
	private int ballPosX = 341, ballPosY = 235;
	private int ballVelX = randomVel() * randomDir(), ballVelY = randomVel() * randomDir();
	
	private int comY = ballPosY - 20, playerY = 210;
	
	private Timer timer;
	
	public Gameplay1P()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
        timer=new Timer(30,this);
		timer.start();
	}
	
	public int randomVel() {
		return (int) (Math.random()*3 + 2);
	}
	
	public int randomDir() {
		int number = (int) (Math.random() * 2);
		return number == 1 ? 1 : -1;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(1, 1, 700, 512);
		
		//Middle line
		g.setColor(Color.white);
		g.fillRect(350, 1, 2, 512);
		
		//computer paddle
		g.fillRect(10, comY, 10, 75);
		
		//player paddle
		g.fillRect(680, playerY, 10, 75);
		
		//Ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);

		//Player score
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+score, 600, 30);
		
		//when game ends
		if (ballPosX >= 701) {
			play = false;
			ballVelX = 0;
			ballVelY = 0;
			
			g.setColor(Color.green);
			g.setFont(new Font("serif",Font.BOLD, 30));
            g.drawString("Game Over, Scores: "+score, 200,250);
			
			g.setFont(new Font("serif",Font.BOLD, 30));
			g.drawString("Press ENTER to restart", 200, 200);
		}

		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (play) {		
			comY = ballPosY - 20;
			
			if (comY >= 400)
				comY = 400;
			if (comY <= 0)
				comY = 0;
			
			//ball hits computer paddle
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(10, comY, 10, 75))) {
				if (ballVelX < 0) {
					ballVelX = Math.abs(ballVelX) + 2;
				}
				else {
					ballVelX = -(ballVelX + 2);
				}
			}
			
			//when ball hit player paddle
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(680, playerY, 10, 75))) {
				if (ballVelX < 0) {
					ballVelX = Math.abs(ballVelX) + 2;
				}
				else {
					ballVelX = -(ballVelX + 2);
				}
				score++;
			}
			
			ballPosX += ballVelX;
			ballPosY += ballVelY;
			if(ballPosY < 0)
			{
				ballVelY = -ballVelY;
			}
			if(ballPosY >= 455)
			{
				ballVelY = -ballVelY;
			}		
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) { 
			if (playerY <= 10)
				playerY = 0;
			else
				moveUp(50);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (playerY >= 400)
				playerY = 400;
			else
				moveDown(50);
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			play = true;
			reset();
			
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	public void moveUp(int vel) {
		play = true;
		playerY -= vel;
	}
	
	public void moveDown(int vel) {
		play = true;
		
		playerY += vel;
	}
	
	public void reset() {
		score = 0;
		ballPosX = 341;
		ballPosY = 235;
		ballVelX = randomVel() * randomDir();
		ballVelY = randomVel() * randomDir();
		comY = ballPosY - 20;
		playerY = 210;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

}