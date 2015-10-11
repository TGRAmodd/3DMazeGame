package com.ru.tgra.shapes;

public class Cell {
	public boolean northWall;
	public boolean eastWall;
	public float wallWidth;
	
	public Cell(boolean north, boolean east){
		this.northWall = north;
		this.eastWall = east;
		wallWidth = 0.1f;
	}
	
	public void draw(){
		if(northWall){
		ModelMatrix.main.pushMatrix();

			ModelMatrix.main.addTranslation(0.5f, 0, 1);
			ModelMatrix.main.addScale(1,1, wallWidth);
			ModelMatrix.main.setShaderMatrix();
			BoxGraphic.drawSolidCube();

		ModelMatrix.main.popMatrix();
	
		}
		if(eastWall){
			ModelMatrix.main.pushMatrix();
			
				ModelMatrix.main.addTranslation(1, 0, 0.5f);
				ModelMatrix.main.addScale(wallWidth,1, 1);
				ModelMatrix.main.setShaderMatrix();
				BoxGraphic.drawSolidCube();
				
			ModelMatrix.main.popMatrix();
		}
	}
}
