package main.pieces;

import java.util.LinkedList;
import java.util.List;

import main.gameplay.Board;
import main.gameplay.Square;

@SuppressWarnings("serial")
public class Pawn extends Piece {
	
	private boolean hasMoved;

	public Pawn(boolean isWhite, String img, Square position) {
		super(isWhite, img, position);
		hasMoved = false;
	}
	
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	public boolean isHasMoved() {
		return this.hasMoved;
	}
	
	public List<Square> getLegalMoves(Board b) {
		Square[][] board = b.getSquareArray();
    int x = this.getPos().getXNum();
    int y = this.getPos().getYNum();
    boolean isWhite = this.isWhite();
    List<Square> legalMoves = new LinkedList<Square>();
    
    if (isWhite) {
    	if(!hasMoved) {
    		if(!board[x][y-2].isOccupied()) {
    			legalMoves.add(board[x][y-2]);
    		}
    	}
    	
    	if((y-1) >= 0) {
    		if(!board[x][y-1].isOccupied()) {
    			legalMoves.add(board[x][y-1]);
    		}  		
    	}
    	
    	if((y-1) >= 0 && (x+1) < 8) {
    		if(board[x+1][y-1].isOccupied() && board[x+1][y-1].getOccupyingPiece().isWhite() != this.isWhite()) {
    			legalMoves.add(board[x+1][y-1]);
    		}
    	}
    	
    	if((y-1) >= 0 && (x-1) >= 0) {
    		if(board[x-1][y-1].isOccupied() && board[x-1][y-1].getOccupyingPiece().isWhite() != this.isWhite()) {
    			legalMoves.add(board[x-1][y-1]);
    		}
    	}
    	
    } else {
    	if(!hasMoved) {
    		if(!board[x][y+2].isOccupied()) {
    			legalMoves.add(board[x][y+2]);
    		}
    	}
    	
    	if((y+1) >= 0) {
    		if(!board[x][y+1].isOccupied()) {
    			legalMoves.add(board[x][y+1]);
    		}  		
    	}
    	
    	if((y+1) >= 0 && (x+1) < 8) {
    		if(board[x+1][y+1].isOccupied() && board[x+1][y+1].getOccupyingPiece().isWhite() != this.isWhite()) {
    			legalMoves.add(board[x+1][y+1]);
    		}
    	}
    	
    	if((y+1) >= 0 && (x-1) >= 0) {
    		if(board[x-1][y+1].isOccupied() && board[x-1][y+1].getOccupyingPiece().isWhite() != this.isWhite()) {
    			legalMoves.add(board[x-1][y+1]);
    		}
    	}
    	
    }
    
		return legalMoves;
	}

}
