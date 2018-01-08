/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

/**
 *
 * @author bowen
 */
public class RandomDiagonalLines extends DisplayPanelSingleThread {
    private int xSize = 192;
    private int ySize = 108 - 3;
    private int[][] board = new int[xSize][ySize];
    private int[][] distanceBoard = new int[xSize][ySize];
    private boolean[][] activeBoard = new boolean[xSize][ySize];
    
    private Random random = new Random(245);
    
    public RandomDiagonalLines(ScheduledExecutorService executorService, int fps) {
        super(executorService, fps);
    }
    
    
    @Override
    public void prePaint(PreciseTime dt) {
        if (random.nextInt(10) < 2) {
            int rx = random.nextInt(xSize/8)*8;
            int ry = random.nextInt(ySize/8)*8;
            int rd = (int)Math.abs(random.nextGaussian() * 10 + 30);
            if (!activeBoard[rx][ry]) {
                board[rx][ry] = 255;
                activeBoard[rx][ry] = true;
                distanceBoard[rx][ry] = rd;
            }
        }
        
        for (int i=0; i<xSize; i++) {
            for (int j=0; j<ySize; j++) {
                if (distanceBoard[i][j] > 0) {
                    try {
                        int ii = i;
                        int jj = j;
                        ii += 1;
                        jj += 1;
                        ii %= xSize;
                        jj %= ySize;

                        if (!activeBoard[ii][jj]) {
                            if (random.nextInt(3) == 0) {
                                board[ii][jj] = 255;//Math.min((int)((board[i][j] + board[ii][jj]) / 0.01D), 255);
                                activeBoard[ii][jj] = true;
                                distanceBoard[ii][jj] = distanceBoard[i][j] - 1;
                                distanceBoard[i][j] = 0;
                            }
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
                if (board[i][j] == 0) {
                    activeBoard[i][j] = false;
                }
            }
        }
        
    }

    @Override
    public void onPaint(Graphics g, PreciseTime dt) {
        //g.clearRect(0, 0, getWidth(), getHeight());
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
