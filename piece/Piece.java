package piece;

import main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece{
    public int col, row, xPos, yPos, value;
    public boolean isWhite;
    public String name;

    public boolean isFirstMove = true;

    BufferedImage sheet;{
        try{
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("1920px-Chess_Pieces_Sprite.svg.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    protected int sheetScale = sheet.getWidth()/6;

    Image sprite;

    Board board;

    public Piece(Board board){
        this.board = board;
    }

    public boolean isValidMovement(int col, int row){
        return true;
    }

    public boolean moveCollidesWithPiece(int col, int row){
        return false;
    }

    public void paint(Graphics2D g2d){
        g2d.drawImage(sprite, xPos, yPos, null);
    }


}
