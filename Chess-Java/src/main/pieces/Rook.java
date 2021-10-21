package main.pieces;

import java.util.List;

import main.gameplay.Board;
import main.gameplay.Square;

@SuppressWarnings("serial")
public class Rook extends Piece {

  private boolean hasMoved;
  
	public Rook(boolean isWhite, String img, Square position) {
		super(isWhite, img, position);
		hasMoved = false;
	}
	
	public List<Square> getLegalMoves(Board b) {
		Square[][] board = b.getSquareArray();
    int x = this.getPos().getXNum();
    int y = this.getPos().getYNum();
    
    return getLinearMoves(board, x, y);
	}

  public boolean getHasMoved() {
    return hasMoved;
  }

  public void setHasMoved(boolean hasMoved) {
    this.hasMoved = hasMoved;
  }

}
