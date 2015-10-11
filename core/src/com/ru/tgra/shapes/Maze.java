package com.ru.tgra.shapes;

import java.util.Random;



public class Maze {
	public static Cell[][] cells;
	public static int width;
	public static int height;
	
	public Maze(int width, int height){
		Maze.width = width;
		Maze.height = height;
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
		
		//NORTH WALL
		ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(0,0,-20);
			ModelMatrix.main.addScale(20, 20, 0.1f);
			ModelMatrix.main.setShaderMatrix();
			BoxGraphic.drawSolidCube();
		ModelMatrix.main.popMatrix();
		
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
