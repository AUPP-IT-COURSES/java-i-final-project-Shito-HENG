package move;

import main.Board;
import piece.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Input extends MouseAdapter {
    Board board;

    public Input(Board board){
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (board.selectedPiece == null) {
            int col = e.getX() / board.tileSize;
            int row = e.getY() / board.tileSize;

            Piece pieceXY = board.getPiece(col, row);
            if (pieceXY != null && pieceXY.isWhite == board.isWhiteTurn()) {
                board.selectedPiece = pieceXY;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (board.selectedPiece != null){
            board.selectedPiece.xPos = e.getX() - board.tileSize / 2;
            board.selectedPiece.yPos = e.getY() - board.tileSize / 2;

            board.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (board.selectedPiece != null) {
            int col = e.getX() / board.tileSize;
            int row = e.getY() / board.tileSize;

            Move move = new Move(board, board.selectedPiece, col, row);

            if (board.isValidMove(move)) {
                board.makeMove(move);
            } else {
                // Reset the position if the move is not valid
                board.selectedPiece.xPos = board.selectedPiece.col * board.tileSize;
                board.selectedPiece.yPos = board.selectedPiece.row * board.tileSize;
            }

            // Clear the selected piece, allowing the other player to move
            board.selectedPiece = null;
            board.repaint();
        }
    }
}
