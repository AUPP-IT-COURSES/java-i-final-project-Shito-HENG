package move;

import main.Board;
import piece.King;
import piece.Piece;

public class CheckScanner {

    Board board;
    private Piece King;

    public CheckScanner(Board board){
        this.board = board;
    }

    public boolean isKingChecked(Move move){

        Piece King = board.findKing(move.piece.isWhite);
        assert King != null;

        int kingCol = King.col;
        int kingRow = King.row;

        if (board.selectedPiece != null && board.selectedPiece.name.equals("King")){
            kingCol = move.newCol;
            kingRow = move.newRow;
        }

        return hitByRook(move.newCol, move.newRow, King, kingCol, kingRow, 0, 1) ||
                hitByRook(move.newCol, move.newRow, King, kingCol, kingRow, 1, 0) ||
                hitByRook(move.newCol, move.newRow, King, kingCol, kingRow, 0, -1) ||
                hitByRook(move.newCol, move.newRow, King, kingCol, kingRow, -1, 0) ||

                hitByBishop(move.newCol, move.newRow, King, kingCol, kingRow, -1, -1) ||
                hitByBishop(move.newCol, move.newRow, King, kingCol, kingRow, 1, -1) ||
                hitByBishop(move.newCol, move.newRow, King, kingCol, kingRow, 1, 1) ||
                hitByBishop(move.newCol, move.newRow, King, kingCol, kingRow, -1, 1) ||

                hitByKnight(move.newCol, move.newRow, King, kingCol, kingRow) ||

                hitByPawn(move.newCol, move.newRow, King, kingCol, kingRow) ||

                hitByKing(King, kingCol, kingRow);


    }

    private boolean hitByRook(int col, int row, Piece King, int kingCol, int kingRow, int colVal, int rowVal){
        for (int i = 1; i < 8; i++){
            if (kingCol + (i * colVal) == col && kingRow + (i * rowVal) == row){
                break;
            }

            Piece piece = board.getPiece(kingCol + (i * colVal), kingRow + (i * rowVal));
            if (piece != null && piece != board.selectedPiece){
                if (!board.sameTeam(piece, King) && (piece.name.equals("Rook") || piece.name.equals("Queen"))){
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByBishop(int col, int row, Piece King, int kingCol, int kingRow, int colVal, int rowVal){
        for (int i = 1; i < 8; i++){
            if (kingCol - (i * colVal) == col && kingRow - (i * rowVal) == row){
                break;
            }

            Piece piece = board.getPiece(kingCol - (i * colVal), kingRow - (i * rowVal));
            if (piece != null && piece != board.selectedPiece){
                if (!board.sameTeam(piece, King) && (piece.name.equals("Bishop") || piece.name.equals("Queen"))){
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByKnight(int col, int row, Piece King, int kingCol, int kingRow){
        return checkKnight(board.getPiece(kingCol - 1, kingRow - 2), King, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow - 2), King, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow - 1), King, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow + 1), King, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow + 2), King, col, row) ||
                checkKnight(board.getPiece(kingCol - 1, kingRow + 2), King, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow + 1), King, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow - 1), King, col, row);
    }

    private boolean checkKnight(Piece p, Piece k, int col, int row){
        return p != null && !board.sameTeam(p, k) && p.name.equals("Knight") && !(p.col == col && p.row == row);
    }

    private boolean hitByKing(Piece King, int kingCol, int kingRow){
        return checkKing(board.getPiece(kingCol - 1, kingRow - 1), King) ||
                checkKing(board.getPiece(kingCol + 1, kingRow - 1), King) ||
                checkKing(board.getPiece(kingCol, kingRow - 1), King) ||
                checkKing(board.getPiece(kingCol - 1, kingRow), King) ||
                checkKing(board.getPiece(kingCol + 1, kingRow), King) ||
                checkKing(board.getPiece(kingCol - 1, kingRow + 1), King) ||
                checkKing(board.getPiece(kingCol + 1, kingRow + 1), King) ||
                checkKing(board.getPiece(kingCol, kingRow + 1), King);
    }

    private boolean checkKing(Piece p, Piece k){
        return p != null && !board.sameTeam(p, k) && p.name.equals("King");
    }

    private boolean hitByPawn(int col, int row, Piece King, int kingCol, int kingRow){
        int colorVal = King.isWhite ? -1 : 1;
        return checkPawn(board.getPiece(kingCol + 1, kingRow + colorVal), King, col, row) ||
                checkPawn(board.getPiece(kingCol - 1, kingRow + colorVal), King, col, row);
    }

    private boolean checkPawn(Piece p, Piece k, int col, int row){
        return p != null && !board.sameTeam(p, k) && p.name.equals("Pawn");
    }

    public boolean isCheckmate(boolean isWhiteTurn) {
        // Checkmate logic goes here
        // You need to determine if the player with the current turn is in checkmate

        // For example, you might check if the king is in check and has no legal moves to escape

        Piece king = board.findKing(isWhiteTurn);
        if (king != null && isKingChecked(new Move(board, king, king.col, king.row))) {
            // Now, check if the king has no legal moves to escape
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newCol = king.col + i;
                    int newRow = king.row + j;

                    // Check if the move is within bounds and is a valid move
                    if (board.isValidMove(new Move(board, king, newCol, newRow)) &&
                            !board.checkScanner.isKingChecked(new Move(board, king, newCol, newRow))) {
                        // The king has at least one legal move, so it's not checkmate
                        return false;
                    }
                }
            }

            // If no legal moves are found, it's checkmate
            return true;
        }

        // If the king is not in check, it's not checkmate
        return false;
    }


}
