package main.pieces;

import java.util.List;

import main.gameplay.Board;
import main.gameplay.Square;

@SuppressWarnings("serial")
public class Bishop extends Piece {

	public Bishop(boolean isWhite, String img, Square position) {
		super(isWhite, img, position);
	}

	
	public List<Square> getLegalMoves(Board b) {
		Square[][] board = b.getSquareArray();
    int x = this.getPos().getXNum();
    int y = this.getPos().getYNum();
    
    return getDiagonalMoves(board, x, y);
	}

}
