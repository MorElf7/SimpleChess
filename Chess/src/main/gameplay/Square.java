package main.gameplay;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import main.constant.Constant;
import main.pieces.*;

@SuppressWarnings("serial")
public class Square extends JComponent {
	
	private int color;
	private boolean highlighted = false;
	private int xCor, yCor;
	private int xNum, yNum;
	
	private Board board;
	private Piece occupyingPiece;
	
	public Square(Board board, int xNum, int yNum, int color) {
		this.setBoard(board);
		this.xNum = xNum;
		xCor = xNum * Constant.SIDE_LENGTH;
		this.yNum = yNum;
		yCor = yNum * Constant.SIDE_LENGTH;
		this.color = color;
	}
	
	public void draw(Graphics g) {
		if (color == 0)
			g.setColor(new Color(238, 238, 210));
		else
			g.setColor(new Color(186, 202, 68));
		
		g.fillRect(xCor, yCor, Constant.SIDE_LENGTH, Constant.SIDE_LENGTH);
		
		if(highlighted) {
			g.setColor(Color.black);
			int r = 7;
			g.fillOval(xCor + 30, yCor + 30, r*2, r*2);
		}
		
		if(isDisplayPiece())
			occupyingPiece.draw(g);
	}
	
	public boolean move(Square des) {
		if (des.isOccupied()) {
			if (des.getOccupyingPiece().isWhite() == this.occupyingPiece.isWhite()) return false;
			this.capture(des);
		} else {
			if(this.occupyingPiece instanceof Pawn)
				((Pawn) this.occupyingPiece).setHasMoved(true);
			if(this.occupyingPiece instanceof Rook)
        ((Rook) this.occupyingPiece).setHasMoved(true);
			this.occupyingPiece.setPos(des);
			des.put(this.occupyingPiece);
			this.removePiece();
		}
		return true;
	}
	
	public boolean capture(Square des) {
		des.getOccupyingPiece().setKilled(true);
		des.put(this.occupyingPiece);
		this.occupyingPiece.setPos(des);
		this.removePiece();
		return true;
	}
	
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
	
	public boolean isHighlighted() {
		return this.highlighted;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}
	
	public boolean isDisplayPiece() {
		return occupyingPiece != null;
	}
	
	public Piece getOccupyingPiece() {
		return occupyingPiece;
	}

	public void put(Piece occupyingPiece) {
		this.occupyingPiece = occupyingPiece;
		occupyingPiece.setPos(this);
	}
	
	public Piece removePiece() {
    Piece p = this.occupyingPiece;
    this.occupyingPiece = null;
    return p;
}

	public int getYCor() {
		return yCor;
	}

	public void setYCor(int yCor) {
		this.yCor = yCor;
	}
	
	public boolean isOccupied() {
		return (occupyingPiece != null);
	}

	public int getXCor() {
		return xCor;
	}

	public void setXCor(int xCor) {
		this.xCor = xCor;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getXNum() {
		return xNum;
	}

	public void setXNum(int xNum) {
		this.xNum = xNum;
	}

	public int getYNum() {
		return yNum;
	}

	public void setYNum(int yNum) {
		this.yNum = yNum;
	}

}
