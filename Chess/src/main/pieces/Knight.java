package main.pieces;

import java.util.LinkedList;
import java.util.List;

import main.gameplay.Board;
import main.gameplay.Square;

@SuppressWarnings("serial")
public class Knight extends Piece {

	public Knight(boolean isWhite, String img, Square position) {
		super(isWhite, img, position);
	}

	
	public List<Square> getLegalMoves(Board b) {
	   List<Square> legalMoves = new LinkedList<Square>();
   	Square[][] board = b.getSquareArray();
      int x = this.getPos().getXNum();
      int y = this.getPos().getYNum();
      for(int i = 1; i >= -1; i--) {
         if(i != 0) {
     	          
            try {
               if(!board[x+i][y+2].isOccupied() || board[x+i][y+2].getOccupyingPiece().isWhite() != this.isWhite())
                  legalMoves.add(board[x+i][y+2]);
            }
            catch(ArrayIndexOutOfBoundsException e) { }
            catch(NullPointerException e) { }
            
            try {
               if(!board[x+i][y-2].isOccupied() || board[x+i][y-2].getOccupyingPiece().isWhite() != this.isWhite())
                  legalMoves.add(board[x+i][y-2]);
            }
            catch(ArrayIndexOutOfBoundsException e) { }
            catch(NullPointerException e) { }
            
            try {
               if(!board[x+2][y+i].isOccupied() || board[x+2][y+i].getOccupyingPiece().isWhite() != this.isWhite())
                  legalMoves.add(board[x+2][y+i]);
            }
            catch(ArrayIndexOutOfBoundsException e) { }
            catch(NullPointerException e) { }
            
            try {
               if(!board[x-2][y+i].isOccupied() || board[x-2][y+i].getOccupyingPiece().isWhite() != this.isWhite())
                  legalMoves.add(board[x-2][y+i]);
            }
            catch(ArrayIndexOutOfBoundsException e) { }
            catch(NullPointerException e) { }
     	      
         }
	    
      }
		return legalMoves;
	}

}
