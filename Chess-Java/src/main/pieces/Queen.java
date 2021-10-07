package main.pieces;

import java.util.LinkedList;
import java.util.List;

import main.gameplay.Board;
import main.gameplay.Square;

@SuppressWarnings("serial")
public class Queen extends Piece {

	public Queen(boolean isWhite, String img, Square position) {
		super(isWhite, img, position);
	}
	
	public List<Square> getLegalMoves(Board b) {
		Square[][] board = b.getSquareArray();
    int x = this.getPos().getXNum();
    int y = this.getPos().getYNum();
    
    List<Square> linear = new LinkedList<Square>();
    linear= this.getLinearMoves(board, x, y);
    List<Square> diag= new LinkedList<Square>();
    diag = this.getDiagonalMoves(board, x, y);
    linear.addAll(diag);
		return linear;
	}
	
}
