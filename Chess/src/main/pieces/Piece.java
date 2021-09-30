package main.pieces;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import main.gameplay.Board;
import main.gameplay.Square;

@SuppressWarnings("serial")
public abstract class Piece extends JComponent {

	private boolean killed = false;
	private boolean white = false;
	private Image img;
	private Square pos;
	
	public Piece(boolean isWhite, String path, Square position) {
		this.pos = position;
		this.white = isWhite;
		this.killed = false;
		try {
			img = ImageIO.read(new File(path));
    } catch (IOException e) {
      System.out.println("File not found: " + e.getMessage());
    }
	}
	
	public void draw(Graphics g) {
		int x = pos.getXCor() + 15;
		int y = pos.getYCor() + 15;
		g.drawImage(this.img, x, y, null);
	}

	public List<Square> getLinearMoves(Square[][] board, int x, int y) {
		List<Square> linearMoves = new LinkedList<Square>();
    
    for(int i = y - 1; i >= 0; i--) {
    	if(board[x][i].isOccupied()) {
    		if(board[x][i].getOccupyingPiece().isWhite() != this.white)
    			linearMoves.add(board[x][i]);
    		break;
    	}	else {
    		linearMoves.add(board[x][i]);
    	}
    }
    
    for(int i = y + 1; i < 8; i++) {
    	if(board[x][i].isOccupied()) {
    		if(board[x][i].getOccupyingPiece().isWhite() != this.white)
    			linearMoves.add(board[x][i]);
    		break;
    	}	else {
    		linearMoves.add(board[x][i]);
    	}
    }
    
    for(int i = x + 1; i < 8; i++) {
    	if(board[i][y].isOccupied()) {
    		if(board[i][y].getOccupyingPiece().isWhite() != this.white)
    			linearMoves.add(board[i][y]);
    		break;
    	}	else {
    		linearMoves.add(board[i][y]);
    	}
    }
    
    for(int i = x - 1; i >= 0; i--) {
    	if(board[i][y].isOccupied()) {
    		if(board[i][y].getOccupyingPiece().isWhite() != this.white)
    			linearMoves.add(board[i][y]);
    		break;
    	}	else {
    		linearMoves.add(board[i][y]);
    	}
    }
    
    return linearMoves;
}

public List<Square> getDiagonalMoves(Square[][] board, int x, int y) {
    LinkedList<Square> diagMoves = new LinkedList<Square>();
    
    int xNW = x - 1;
    int xSW = x - 1;
    int xNE = x + 1;
    int xSE = x + 1;
    int yNW = y - 1;
    int ySW = y + 1;
    int yNE = y - 1;
    int ySE = y + 1;
    
    while (xNW >= 0 && yNW >= 0) {
        if (board[xNW][yNW].isOccupied()) {
        	if(board[xNW][yNW].getOccupyingPiece().isWhite() != this.white)
        		diagMoves.add(board[xNW][yNW]);
          break;
        } else {
        	diagMoves.add(board[xNW][yNW]);
            yNW--;
            xNW--;
        }
    }
    
    while (xSW >= 0 && ySW < 8) {
      if (board[xSW][ySW].isOccupied()) {
      	if(board[xSW][ySW].getOccupyingPiece().isWhite() != this.white)
      		diagMoves.add(board[xSW][ySW]);
        break;
      } else {
      	diagMoves.add(board[xSW][ySW]);
          ySW++;
          xSW--;
      }
    }
    
    while (xSE < 8 && ySE < 8) {
      if (board[xSE][ySE].isOccupied()) {
      	if(board[xSE][ySE].getOccupyingPiece().isWhite() != this.white)
      		diagMoves.add(board[xSE][ySE]);
        break;
      } else {
      	diagMoves.add(board[xSE][ySE]);
          ySE++;
          xSE++;
      }
    }
    
    while (xNE < 8 && yNE >= 0) {
      if (board[xNE][yNE].isOccupied()) {
      	if(board[xNE][yNE].getOccupyingPiece().isWhite() != this.white)
      		diagMoves.add(board[xNE][yNE]);
        break;
      } else {
      	diagMoves.add(board[xNE][yNE]);
          yNE--;
          xNE++;
      }
    }
    
    return diagMoves;
	}
	
	public Image getImage() {
		return img;
	}

	public void setImage(Image img) {
		this.img = img;
	}
	
	public boolean isKilled() {
		return killed;
	}
	
	public void setKilled(boolean isKilled) {
		this.killed = isKilled;
	}
	
	public boolean isWhite() {
		return white;
	}
	
	public void setWhite(boolean isWhite) {
		this.white = isWhite;
	}

	public Square getPos() {
		return pos;
	}

	public void setPos(Square pos) {
		this.pos = pos;
	}

	public abstract List<Square> getLegalMoves(Board b);
}
