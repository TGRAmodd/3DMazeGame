package com.ru.tgra.shapes;

import java.util.Random;



public class Maze {
	private static Cell[][] cells;
	private int width;
	private int height;
	
	public Maze(int width, int height){
		this.width = width;
		this.height = height;
		cells = new Cell[width][height];
		Random r = new Random();
		for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					cells[i][j] = new Cell(r.nextBoolean(), r.nextBoolean());
					
				}
			
		}
	}
	
	public void drawMaze(){
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(0,1,0);
		for(int i = 0; i < width; i++){
			ModelMatrix.main.pushMatrix();
				for(int j = 0; j < height; j++){
					cells[i][j].draw();
					ModelMatrix.main.addTranslation(0, 0, -1);
				}
			ModelMatrix.main.popMatrix();
			ModelMatrix.main.addTranslation(1,0,0);
		}
		ModelMatrix.main.popMatrix();
	}
}
