package main.pieces;

import java.util.LinkedList;
import java.util.List;

import main.gameplay.Board;
import main.gameplay.Square;

@SuppressWarnings("serial")
public class King extends Piece {

   private boolean hasMoved;
   
	public King(boolean isWhite, String img, Square position) {
		super(isWhite, img, position);
		hasMoved = false;
	}

	public boolean getHasMoved() {
    return hasMoved;
  }

  public void setHasMoved(boolean hasMoved) {
    this.hasMoved = hasMoved;
  }
	
	public List<Square> getLegalMoves(Board b) {
		List<Square> legalMoves = new LinkedList<Square>();
		Square[][] board = b.getSquareArray();
		int x = this.getPos().getXNum();
    int y = this.getPos().getYNum();
       
    for (int i = 1; i > -2; i--) {
      for (int k = 1; k > -2; k--) {
        if(!(i == 0 && k == 0)) {
          try {
            if(!board[x + i][y + k].isOccupied() || 
                board[x + k][y + k].getOccupyingPiece().isWhite()!= this.isWhite()) {
              legalMoves.add(board[x + i][y + k]);
            }
          } catch (ArrayIndexOutOfBoundsException e) {
            continue;
          } catch (NullPointerException e) {
            continue;
          }
        }
      }
    }
       
      if (!hasMoved) {
        if (this.isWhite()) {
          if (!board[1][7].isOccupied() && !board[2][7].isOccupied() && !board[3][7].isOccupied() 
              && board[0][7].getOccupyingPiece() instanceof Rook && !((Rook) board[0][7].getOccupyingPiece()).getHasMoved() ) {
            legalMoves.add(board[2][7]);
          }
          
          if (!board[5][7].isOccupied() && !board[6][7].isOccupied()
              && board[7][7].getOccupyingPiece() instanceof Rook && !((Rook) board[7][7].getOccupyingPiece()).getHasMoved() ) {
            legalMoves.add(board[6][7]);
          }
        }
        
        if (!this.isWhite()) {
          if (!board[1][0].isOccupied() && !board[2][0].isOccupied() && !board[3][0].isOccupied() 
              && board[0][0].getOccupyingPiece() instanceof Rook && !((Rook) board[0][0].getOccupyingPiece()).getHasMoved() ) {
            legalMoves.add(board[2][0]);
          }
          
          if (!board[5][0].isOccupied() && !board[6][0].isOccupied()
              && board[7][0].getOccupyingPiece() instanceof Rook && !((Rook) board[7][0].getOccupyingPiece()).getHasMoved() ) {
            legalMoves.add(board[6][0]);
          }
        }
      }
       
      return legalMoves;
	}
}
