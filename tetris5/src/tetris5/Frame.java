package tetris5;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Frame extends JFrame implements ActionListener {
	
	Panel game = new Panel();
	
	ImageIcon background = new ImageIcon("backGround.png");
	ImageIcon logoIcon = new ImageIcon("tetris.png");
	
	JLabel labelB = new JLabel();
	JLabel logo = new JLabel();
	
	
	JLabel scoreLabel = new JLabel("Score: " + String.valueOf(game.score));
	JLabel LevelLabel = new JLabel("Level: 0");
	
	JButton startB = new JButton();
	JButton pauseB = new JButton();
	
	int BH=100;//height and width of buttons
	int BW=40;
	
	
	Thread starter;

	int control=0;//0 -> game paused , 1 -> game is playing. start/stop
	int gameOver=0;//0-> game playing , 1-> game is over	restart/stop
	
	Frame(){

		//start button properties
		startB.setBounds(game.LocationX-BW*3,game.LocationY,BH,BW);
		startB.setText("Start");
		startB.setVisible(true);
		startB.addActionListener(this);
		startB.setFocusable(false);
		
		//pause button properties
		pauseB.setBounds(game.LocationX-BW*3, game.LocationY, BH, BW);
		pauseB.setText("Pause");
		pauseB.addActionListener(this);
		pauseB.setFocusable(false);
		pauseB.setVisible(false);
		
		//tetris logo 
		logo.setIcon(logoIcon);
		logo.setBounds(20,20,200,71);
		
		//background picture
		labelB.setIcon(background);
		labelB.setSize(1520,1013);
		labelB.setVisible(true);
		
		//score label properties
		scoreLabel.setBounds(game.LocationX+game.width+10, game.LocationY+40, 300, 50);
	    scoreLabel.setFont(new Font("Serif", Font.PLAIN, 30));
	    scoreLabel.setForeground(new Color(0x2B5876));
	    scoreLabel.setVisible(true);
	    this.add(scoreLabel);
		
		
	    //level label properties
	    LevelLabel.setBounds(game.LocationX+game.width+10, game.LocationY, 300, 50);
	    LevelLabel.setFont(new Font("Serif", Font.PLAIN, 30));
	    LevelLabel.setForeground(new Color(0x2B5876));
	    LevelLabel.setVisible(true);
	    this.add(LevelLabel);
	    

		//frame properties
		this.setTitle("TETRIS");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.getContentPane().setBackground(new Color(0x2B5876));
		this.setLayout(null);
		this.setVisible(true);
		this.setFocusable(true);
		this.setSize(labelB.getSize());
		this.add(startB);
		this.add(pauseB);
		this.add(game);
		this.add(logo);
		this.add(labelB);
		this.addKeyListener(new MyKeyListener());
	
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getSource()==startB) {
			
			//START(play again) BUTTON IF THE GAME IS OVER
			if(gameOver==1) {
				startB.setText("Start");
				startB.setBounds(game.LocationX-BW*3,game.LocationY,BH,BW);
				game.gameOver();
				gameOver=0;
				 scoreLabel.setText("Score: " + 0);
				
			}
			
			control=1;
			
			starter = new Thread() {
				@Override public void run() {
					while (true) {
						try {
							if(control==1) {	
								
								for(int i=game.xSize/2-2;i<game.xSize/2+2;i++ ) {
									for(int j=0;j<3;j++) {
										if(game.color[i][j]!=game.AreaColor) {
											startB.setText("PLAY AGAIN...");
											startB.setBounds(game.LocationX,game.height/2,game.width,BW);
											pauseB.setVisible(false);
											startB.setVisible(true);
											control=0;
											gameOver=1;
											game.score=0;
											break;
										}
									}
									
								}
								if(gameOver==0) {
									
									
									//setting speed of game depends on the score.
									if(game.score <= 1000) {
										Thread.sleep(600);
										LevelLabel.setText("Level: 0" );
									}
									else if(game.score > 1000 && game.score <= 2500 ) {
										Thread.sleep(525);
										LevelLabel.setText("Level: 1" );
									}
									else if(game.score > 2500 && game.score <= 4000 ) {
										Thread.sleep(450);
										LevelLabel.setText("Level: 2");
									}
									else if(game.score > 4000 && game.score <= 5000 ) {
										Thread.sleep(375);
										LevelLabel.setText("Level: 3");
									}
									else if(game.score > 5000 && game.score <= 6000) {
										Thread.sleep(300);
										LevelLabel.setText("Level: 4");
									}
									else if(game.score > 6000 && game.score <= 7000 ) {
										Thread.sleep(150);
										LevelLabel.setText("Level: 5");
									}
									
									
									
									
									 
									 
									 //keeps updating score during game playing.
			                         long previousScore = game.score;
			                         game.dropDown();
			                         if (game.score != previousScore) {
			                        	 scoreLabel.setText("Score: " + String.valueOf(game.score));
			                         }
								}
							}
				
						} catch ( InterruptedException e ) {}
					}
				}
			};starter.start();

			startB.setVisible(false);
			pauseB.setVisible(true);
		}
		
		
		//reactions of pause button.
		if(e.getSource()==pauseB) {
			startB.setVisible(true);
			pauseB.setVisible(false);
			control=0;
		}
	}
	

	//key controls of the game , also updates score if you press space.
	public class MyKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if(control==1) 
					game.rotate(+1);
				
				break;
			case KeyEvent.VK_DOWN:
				if(control==1)
				game.rotate(+1);
				break;
			case KeyEvent.VK_LEFT:
				if(game.pieceOrigin.x!=0 && control==1)
				game.move(-1);
				break;
			case KeyEvent.VK_RIGHT:
				if(control==1)
				game.move(+1);
				break;
			case KeyEvent.VK_SPACE:
				if(control==1) {
				game.dropDown();
				game.score += 1;
				scoreLabel.setText("Score: " + String.valueOf(game.score));
				}
				break;
			} 
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
			
		}
		
	}
	
}

	
