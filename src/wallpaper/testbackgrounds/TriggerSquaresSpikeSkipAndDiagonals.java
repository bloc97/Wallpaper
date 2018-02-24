/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper.testbackgrounds;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import wallpaper.CustomWallpaper;
import wallpaper.PreciseTime;

/**
 *
 * @author bowen
 */
public class TriggerSquaresSpikeSkipAndDiagonals extends CustomWallpaper {
    private int xSize = 300;
    private int ySize = 120;
    private int[][] board = new int[xSize][ySize];
    private int[][] distanceBoard = new int[xSize][ySize];
    private boolean[][] activeBoard = new boolean[xSize][ySize];
    
    private Random random = new Random(245);
    
    @Override
    public void prePaint(PreciseTime dt) {
        if (random.nextInt(10) < 2) {
            int rx = random.nextInt(xSize);
            int ry = random.nextInt(ySize);
            int rd = (int)Math.abs(random.nextGaussian() * 50);
            board[rx][ry] = 255;
            activeBoard[rx][ry] = true;
            distanceBoard[rx][ry] = rd;
        }
        
        for (int i=0; i<xSize; i++) {
            for (int j=0; j<ySize; j++) {
                if (distanceBoard[i][j] > 0) {
                    try {
                        int ii = i;
                        int jj = j;
                        switch (random.nextInt(8)) {
                                
                            case 0:
                                ii += 2;
                                break;
                            case 1:
                                jj -= 2;
                                break;
                            case 2:
                                jj += 2;
                                break;
                            case 3:
                                ii -= 2;
                                break;
                            case 4:
                                ii += 1;
                                jj += 1;
                                break;
                            case 5:
                                ii += 1;
                                jj -= 1;
                                break;
                            case 6:
                                ii -= 1;
                                jj += 1;
                                break;
                            case 7:
                                ii -= 1;
                                jj -= 1;
                                break;
                        }

                        if (!activeBoard[ii][jj]) {
                            //if (random.nextInt(2) == 0) {
                                board[ii][jj] = 255;//Math.min((int)((board[i][j] + board[ii][jj]) / 0.01D), 255);
                                activeBoard[ii][jj] = true;
                                distanceBoard[ii][jj] = distanceBoard[i][j] - 1;
                                distanceBoard[i][j] = 0;
                            //}
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
