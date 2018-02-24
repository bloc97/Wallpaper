/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper.testbackgrounds;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import wallpaper.CustomWallpaper;
import wallpaper.PreciseTime;

/**
 *
 * @author bowen
 */
public class TriggerSquares extends CustomWallpaper {
    private int xSize = 300;
    private int ySize = 120;
    private int[][] board = new int[xSize][ySize];
    
    private Random random = new Random(245);
    
    @Override
    public void prePaint(PreciseTime dt) {
        board[random.nextInt(xSize)][random.nextInt(ySize)] = 255;
        
        for (int i=0; i<xSize; i++) {
            for (int j=0; j<ySize; j++) {
                if (random.nextInt(1000) > 995) {
                    try {
                        int ii = i;
                        int jj = j;
                        switch (random.nextInt(4)) {
                            case 0:
                                ii += 1;
                                break;
                            case 1:
                                ii -= 1;
                                break;
                            case 2:
                                jj += 1;
                                break;
                            case 3:
                                jj -=1;
                                break;
                        }

                        if (board[ii][jj] >= board[i][j]) {
                            board[i][j] = Math.min((int)((board[i][j] + board[ii][jj]) / 0.7D), 255);
                        }

                    } catch (Exception ex) {

                    }
                }
            }
        }
        
        for (int i=0; i<xSize; i++) {
            for (int j=0; j<ySize; j++) {
                double slope = 1D/board[i][j];
                if (slope > 0) {
                    board[i][j] = Math.max((int)(board[i][j] - slope), 0);
                }
            }
        }
        
    }

    @Override
    public void onPaint(Graphics g, PreciseTime dt) {
        g.clearRect(0, 0, getPanel().getWidth(), getPanel().getHeight());
        //g.drawRect(5, 5, 100, 100);
        //g.setColor(Color.BLACK);
        for (int i=0; i<xSize; i++) {
            for (int j=0; j<ySize; j++) {
                g.setColor(new Color(board[i][j], board[i][j], board[i][j]));
                g.fillRect(i * 20, j * 20, 20, 20);
            }
        }
    }

    @Override
    public void postPaint(PreciseTime dt) {
    }
    
}
