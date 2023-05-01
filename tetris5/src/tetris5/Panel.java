package tetris5;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

public class Panel extends JPanel{
	
	private final Point[][][] Tetraminos = {
			

			// I-Piece
			{
				{ new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
			},
			
			// J-Piece
			{
				{ new Point(0, 2), new Point(1, 0), new Point(1, 1), new Point(1, 2) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 0) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) }
			},
			
			// L-Piece
			{
				{ new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
				{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(1, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
			},
			
			// O-Piece
			{
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
			},
			
			// S-Piece
			{
				{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
				{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
			},
			
			// T-Piece
			{
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
				{ new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(1, 1) },
				{ new Point(0, 1), new Point(1, 0), new Point(1, 1), new Point(1, 2) }
			},
			
			// Z-Piece
			{
				{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
				{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
			}
	};
	
	
	private final Color[] tetraminoColors = {
			new Color(0x2B5810), Color.blue, Color.orange, Color.yellow, Color.green, Color.pink, Color.red
		};
	
	public Point pieceOrigin;
	private int currentPiece;
	private int rotation;
	private ArrayList<Integer> nextPieces = new ArrayList<Integer>();
	public long score;
	
	
	int LocationX=500;//location of panel on the frame-x
	int LocationY=40;//location of panel on the frame-y
	int width=550;//width of game area
	int height=750;//height of game area
	int unit_size=50;//size of every block and piece
	int xSize=width/unit_size;// num of blokcs on x
	int ySize=height/unit_size;//num of blocks on y
	Color[][] color;
	Color   AreaColor=new Color(0x2B5876);
	

	
	Panel(){
		
		this.setBounds(LocationX,LocationY,width,height);
		this.setVisible(true);
		this.setOpaque(false);
		this.setFocusable(true);
		backGroundColor();
		newPiece();
	
	}
	
	//inserting background color into color array.
	public void backGroundColor() {
		color= new Color[xSize+2][ySize+2];
		for(int i=0;i<xSize;i++) {
			for(int j=0;j<ySize;j++) {
				color[i][j]=(AreaColor);
			}
		}
	}
	//create new piece if nextPiece arraylist is empty.
	public void newPiece() {
		
		pieceOrigin = new Point(xSize/2, 0);
		rotation = 0;
	
		if (nextPieces.isEmpty()) {
			Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6);
			Collections.shuffle(nextPieces);
		}
		currentPiece = nextPieces.get(0);
		nextPieces.remove(0);
		
		
	}
	
	
	
	// Collision test for the dropping piece
	private boolean collidesAt(int x, int y, int rotation) {
		for (Point p : Tetraminos[currentPiece][rotation]) {
			if (color[p.x + x][p.y + y] != AreaColor )  {
				return true;
			}
		}
		return false;
	}
	
	
	// Rotate the piece clockwise
	public void rotate(int i) {
		int newRotation = (rotation + i) % 4;
		if (newRotation < 0) {
			newRotation = 3;
		}
		if (!collidesAt(pieceOrigin.x, pieceOrigin.y, newRotation)) {
			rotation = newRotation;
		}
		repaint();
	}
	
	
	// Move the piece left or right
	public void move(int i) {
		if (!collidesAt(pieceOrigin.x + i, pieceOrigin.y, rotation) ) {
			pieceOrigin.x += i;	
		}
		repaint();
	}
	
	
	// Drops the piece one line or fixes it to the game area if it can't drop
	public void dropDown() {
		if (!collidesAt(pieceOrigin.x, pieceOrigin.y +1, rotation)) {
			pieceOrigin.y += 1;
		} else {
			fixGameArea();
		}	
		repaint();
	}
		
		// Make the dropping piece part of the game area, so it is available for
		// collision detection.
	public void fixGameArea() {
		for (Point p : Tetraminos[currentPiece][rotation]) {
			color[pieceOrigin.x + p.x][pieceOrigin.y + p.y] = tetraminoColors[currentPiece];
			
		}
		clearRows();
		newPiece();
	}
	
	//if the row is completely filled, it shifts the top ones one line down.
	public void deleteRow(int row) {
		for (int j = row-1; j > 0; j--) {
			for (int i = 0; i < xSize; i++) {
				color[i][j+1] = color[i][j];
			}
		}
	}
	
	// checks the rows and calls the delete row method if the entire row is different from the color of the playground(row is filled).
	public void clearRows() {
		boolean gap;
		int numClears = 0;
		
		for (int j = (ySize-1); j > 0; j--) {
			gap = false;
			for (int i = 0; i < (xSize+1); i++) {
				if (color[i][j] == AreaColor) {
					gap = true;
					break;
				}
			}
			if (!gap) {
				deleteRow(j);
				j += 1;
				numClears += 1;
			}
		}
		
		switch (numClears) {
		case 1:
			score += 100;
			break;
		case 2:
			score += 300;
			break;
		case 3:
			score += 500;
			break;
		case 4:
			score += 800;
			break;
		}
	}
	
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawBackground(g);
		drawPiece(g);
	}
	
	//color and size of the falling piece	
	private void drawPiece(Graphics g) {	
		
			
			for (Point p : Tetraminos[currentPiece][rotation]) {
				g.setColor(tetraminoColors[currentPiece]);
				g.fillRoundRect((p.x + pieceOrigin.x) * unit_size, 
						   (p.y + pieceOrigin.y) * unit_size, 
						   unit_size-2, unit_size-2,unit_size/3,unit_size/3);
				g.setColor(g.getColor().darker());
				g.drawRoundRect((p.x + pieceOrigin.x) * unit_size, 
						   (p.y + pieceOrigin.y) * unit_size, 
						   unit_size-2, unit_size-2,unit_size/3,unit_size/3);
			}
			
		}

	//keeps the color of the background and repaints it according to the color of the falling piece.
	void drawBackground(Graphics g) {
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				
				Graphics2D g2 = (Graphics2D) g;
			    g2.setStroke(new BasicStroke(4));
				
				g.setColor(color[i][j]);
				g.fillRoundRect(i*unit_size, unit_size*j, unit_size-2, unit_size-2, unit_size/3, unit_size/3);
				
				if(color[i][j]!=AreaColor) {
					g2.setColor(g.getColor().darker());
					g2.drawRoundRect(i*unit_size, unit_size*j, unit_size-2, unit_size-2,unit_size/3,unit_size/3);

				}
			}
		}

		
	}
	
	// when the game is over, if you press the play again button,
	//it will clear all the lines and make the game area ready to play
	void gameOver() {
		for(int i=0; i<xSize; i++) {
			for(int j=0 ; j<ySize ; j++) {
				color[i][j]=AreaColor;
			}
		}
	}
	

}
