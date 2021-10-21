package main.gameplay;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;


import main.constant.Constant;
import main.pieces.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {

	private static final String RESOURCES_WBISHOP_PNG = Constant.PATH + "wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = Constant.PATH + "bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = Constant.PATH + "wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = Constant.PATH + "bknight.png";
	private static final String RESOURCES_WROOK_PNG = Constant.PATH + "wrook.png";
	private static final String RESOURCES_BROOK_PNG = Constant.PATH + "brook.png";
	private static final String RESOURCES_WKING_PNG = Constant.PATH + "wking.png";
	private static final String RESOURCES_BKING_PNG = Constant.PATH + "bking.png";
	private static final String RESOURCES_BQUEEN_PNG = Constant.PATH + "bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = Constant.PATH + "wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = Constant.PATH + "wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = Constant.PATH + "bpawn.png";
	
	private Square[][] board;
	private boolean whiteTurn;
	private boolean hasClicked = false;
	
//	private List<Piece> wpiece = new LinkedList<Piece>();
//	private List<Piece> bpiece = new LinkedList<Piece>();
	private int[] lastSelectedSquare;
	private Piece currPiece;
	private Square lastMovedDestination;
	private Square lasMovedStart;
	private boolean check;
	
	public Board() {
		board = new Square[8][8];
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if((i+j) % 2 == 0) {
					Square square = new Square(this, i, j, 0);
					board[i][j] = square;
					this.add(square);
				}
				else {
					Square square = new Square(this, i, j, 1);
					board[i][j] = square;
					this.add(square);
				}
			}
		}
		
		initializePieces();
		
    this.setPreferredSize(new Dimension(600, 600));
    this.setMaximumSize(new Dimension(600, 600));
    this.setMinimumSize(this.getPreferredSize());
    this.setSize(new Dimension(600, 600));

    whiteTurn = true;
    check = false;
    
	}
	
	public void paint(Graphics g) {

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
//				if(board[i][j].isOccupied()) {
//					if(board[i][j].getOccupyingPiece().isWhite())
//						wpiece.add(board[i][j].getOccupyingPiece());
//					else bpiece.add(board[i][j].getOccupyingPiece());
//				}
				board[i][j].draw(g);
			}
		}
		
		g.dispose();
	}
	
	private void initializePieces() {
  	
    for (int x = 0; x < 8; x++) {
        board[x][1].put(new Pawn(false, RESOURCES_BPAWN_PNG, board[x][1]));
        board[x][6].put(new Pawn(true, RESOURCES_WPAWN_PNG, board[x][6]));
    }
    
    board[3][7].put(new Queen(true, RESOURCES_WQUEEN_PNG, board[3][7]));
    board[3][0].put(new Queen(false, RESOURCES_BQUEEN_PNG, board[3][0]));
    
    board[4][0].put(new King(false, RESOURCES_BKING_PNG, board[4][0]));
    board[4][7].put(new King(true, RESOURCES_WKING_PNG, board[4][7]));

    board[0][0].put(new Rook(false, RESOURCES_BROOK_PNG, board[0][0]));
    board[7][0].put(new Rook(false, RESOURCES_BROOK_PNG, board[7][0]));
    board[0][7].put(new Rook(true, RESOURCES_WROOK_PNG, board[0][7]));
    board[7][7].put(new Rook(true, RESOURCES_WROOK_PNG, board[7][7]));

    board[1][0].put(new Knight(false, RESOURCES_BKNIGHT_PNG, board[1][0]));
    board[6][0].put(new Knight(false, RESOURCES_BKNIGHT_PNG, board[6][0]));
    board[1][7].put(new Knight(true, RESOURCES_WKNIGHT_PNG, board[1][7]));
    board[6][7].put(new Knight(true, RESOURCES_WKNIGHT_PNG, board[6][7]));

    board[2][0].put(new Bishop(false, RESOURCES_BBISHOP_PNG, board[2][0]));
    board[5][0].put(new Bishop(false, RESOURCES_BBISHOP_PNG, board[5][0]));
    board[2][7].put(new Bishop(true, RESOURCES_WBISHOP_PNG, board[2][7]));
    board[5][7].put(new Bishop(true, RESOURCES_WBISHOP_PNG, board[5][7]));
	}
	
	public boolean move(Square start, Square destination) {
		if(!start.isOccupied())
			return false;
		List<Square> legalMoves = start.getOccupyingPiece().getLegalMoves(this);
		if (!legalMoves.contains(destination)) return false;
		if (start.getOccupyingPiece() instanceof King && !((King)start.getOccupyingPiece()).getHasMoved() && (destination.getXNum() == 2 || destination.getXNum() == 6)) {
      if (destination.getXNum() == 2) {
        board[0][start.getYNum()].move(board[3][start.getYNum()]);
        ((King)start.getOccupyingPiece()).setHasMoved(true);
      }
      if (destination.getXNum() == 6) {
        board[7][start.getYNum()].move(board[5][start.getYNum()]);
        ((King)start.getOccupyingPiece()).setHasMoved(true);
      }
    }
		start.move(destination);
		start.removePiece();
		for(Square square : legalMoves) {
			square.setHighlighted(false);
		}
		if (whiteTurn) {
			whiteTurn = false;
		} else whiteTurn = true;
		return true;
	}
	
	protected int[] getClickedSquareCor(int x, int y) {
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				int xCor = board[i][j].getXCor();
				int yCor = board[i][j].getYCor();
				if(new Rectangle(xCor, yCor, Constant.SIDE_LENGTH, Constant.SIDE_LENGTH).contains(new Point(x, y))) {
					int[] result = {i, j};
					return result;
				}
			}
		return null;
	}
	
	protected int[] getClickedSquare(Square square) {
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				if(board[i][j].getXNum() == square.getXNum() && board[i][j].getYNum() == square.getYNum()) {
					int[] result = {i, j};
					return result;
				}
			}
		return null;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		int[] cor = getClickedSquareCor(x, y);
		if(!hasClicked) {
			if(board[cor[0]][cor[1]].isOccupied()) {
				if(whiteTurn == board[cor[0]][cor[1]].getOccupyingPiece().isWhite()) {
					List<Square> legalMoves = board[cor[0]][cor[1]].getOccupyingPiece().getLegalMoves(this);
					
					if(legalMoves != null) {
					  for(Square square : legalMoves) {
					     int[] cordi = getClickedSquare(square);
	            
					     board[cordi[0]][cordi[1]].setHighlighted(true);
					  }

					  lastSelectedSquare = cor;

					  hasClicked = true;
					}
					
				}
			}
		} 
		else {
			if(Arrays.equals(lastSelectedSquare, cor)) {
				if(board[cor[0]][cor[1]].isOccupied()) {
			
					List<Square> legalMoves = board[cor[0]][cor[1]].getOccupyingPiece().getLegalMoves(this);
					
					for(Square square : legalMoves) {
						int[] cordi = getClickedSquare(square);
						int xNum = cordi[0];
						int yNum = cordi[1];
						board[xNum][yNum].setHighlighted(false);
					}
					
					lastSelectedSquare = new int[2];
				}

				hasClicked = false;
			} 
			else {
			  if(board[cor[0]][cor[1]].isHighlighted()) {
					move(board[lastSelectedSquare[0]][lastSelectedSquare[1]], board[cor[0]][cor[1]]);
					hasClicked = false;
					lastSelectedSquare = new int[2];
					
				}
			}
		}
		
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public Piece getCurrPiece() {
		return currPiece;
	}

	public void setCurrPiece(Piece currPiece) {
		this.currPiece = currPiece;
	}

	public boolean isWhiteTurn() {
		return whiteTurn;
	}

	public void setWhiteTurn(boolean isWhiteTurn) {
		this.whiteTurn = isWhiteTurn;
	}

	public Square[][] getSquareArray() {
		return board;
	}

	public Square getLastMovedDestination() {
		return lastMovedDestination;
	}

	public void setLastMovedDestination(Square lastMovedDestination) {
		this.lastMovedDestination = lastMovedDestination;
	}

	public Square getLasMovedStart() {
		return lasMovedStart;
	}

	public void setLasMovedStart(Square lasMovedStart) {
		this.lasMovedStart = lasMovedStart;
	}

}
