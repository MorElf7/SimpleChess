package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Gameplay2P extends JPanel implements KeyListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean play = false;
	
	private int p1Score = 4, p2Score = 0;
	
	private int ballPosX = 341, ballPosY = 235;
	private int ballVelX = randomVel() * randomDir(), ballVelY = randomVel() * randomDir();
	
	private int p1Y = 210, p2Y = 210;
	
	private Timer timer;
	
	public Gameplay2P()
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
		
		//P1 paddle
		g.fillRect(10, p1Y, 10, 75);
		
		//P2 paddle
		g.fillRect(680, p2Y, 10, 75);
		
		//Player 1 score
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+p1Score, 300, 30);
		
		//Player 2 score
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+p2Score, 385, 30);
		
		//Ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		//When P1 wins
		if (p1Score == 5 && p1Score > p2Score) {
			play = false;
			ballVelX = 0;
			ballVelY = 0;
			
			g.setColor(Color.green);
			g.setFont(new Font("serif",Font.BOLD, 50));
			g.drawString("WIN", 100, 250);
			
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD, 50));
			g.drawString("LOSE", 480, 250);
		}
		
		//P1 score
		if (ballPosX >= 701) {
			play = false;
			ballVelX = 0;
			ballVelY = 0;
			p1Score++;
			
			if (p1Score == 5 && p1Score > p2Score) {
				play = false;
				ballVelX = 0;
				ballVelY = 0;
				
				repaint();
				g.setColor(Color.green);
				g.setFont(new Font("serif",Font.BOLD, 50));
				g.drawString("WIN", 100, 250);
				
				g.setColor(Color.red);
				g.setFont(new Font("serif",Font.BOLD, 50));
				g.drawString("LOSE", 480, 250);
			}
			else {
				g.setColor(Color.green);
				g.setFont(new Font("serif",Font.BOLD, 30));
				g.drawString("Press ENTER to continue", 200, 200);
			}
		}

		//P2 score
		if (ballPosX <= 0) {
			play = false;
			ballVelX = 0;
			ballVelY = 0;
			p2Score++;
			
			//When P2 wins
			if (p2Score == 5 && p1Score < p2Score) {
				play = false;
				ballVelX = 0;
				ballVelY = 0;
				
				repaint();
				g.setColor(Color.red);
				g.setFont(new Font("serif",Font.BOLD, 50));
				g.drawString("LOSE", 100, 250);
				
				g.setColor(Color.green);
				g.setFont(new Font("serif",Font.BOLD, 50));
				g.drawString("WIN", 480, 250);
			}
			else {
				g.setColor(Color.green);
				g.setFont(new Font("serif",Font.BOLD, 30));
				g.drawString("Press ENTER to continue", 200, 200);
			}
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (play) {
			//ball hits p1 paddle
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(10, p1Y, 10, 75))) {
				if (ballVelX < 0) {
					ballVelX = Math.abs(ballVelX) + 1;
				}
				else {
					ballVelX = -(ballVelX + 1);
				}
			}
			
			// ball hit p2 paddle
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(680, p2Y, 10, 75))) {
				if (ballVelX < 0) {
					ballVelX = Math.abs(ballVelX) + 1;
				}
				else {
					ballVelX = -(ballVelX + 1);
				}
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
			if (p2Y <= 10)
				p2Y = 0;
			else
				moveUp2(50);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (p2Y >= 400)
				p2Y = 400;
			else
				moveDown2(50);
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			if (p1Y <= 10)
				p1Y = 0;
			else
				moveUp1(50);
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			if (p1Y >= 400)
				p1Y = 400;
			else
				moveDown1(50);
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			play = true;
			reset();
			
			repaint();
		}
	}

	public void moveUp1(int vel) {
		play = true;
		p1Y -= vel;
	}
	
	public void moveUp2(int vel) {
		play = true;
		p2Y -= vel;
	}
	
	public void moveDown1(int vel) {
		play = true;
		p1Y += vel;
	}
	
	public void moveDown2(int vel) {
		play = true;
		p2Y += vel;
	}
	
	public void reset() {
		ballPosX = 341;
		ballPosY = 235;
		ballVelX = randomVel() * randomDir(); 
		ballVelY = randomVel() * randomDir();
		p1Y = 210;
		p2Y = 210;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

}
