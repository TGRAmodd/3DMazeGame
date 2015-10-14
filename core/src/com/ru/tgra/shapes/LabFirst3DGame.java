package com.ru.tgra.shapes;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;

public class LabFirst3DGame extends ApplicationAdapter {

	Shader shader;
	
	private float angle;
	private float objectRotationAngle;

	public static int colorLoc;
	
	private Camera cam;
	private Camera orthoCam;
	
	private float fov = 100.0f;
	
	private Maze maze;
	private float gravity;
	
	private Sound sound;
	private long track;
	private float volume;

	//private ModelMatrix modelMatrix;
	
	public void drawExtraObject() {
		ModelMatrix.main.pushMatrix();
		Gdx.gl.glUniform4f(colorLoc, 0.2f, 0.6f, 0.3f, 1.0f);
		ModelMatrix.main.addTranslation(4, 4f, -4f);
		ModelMatrix.main.addScale(2.0f, 2.0f, 2.0f);
		//ModelMatrix.main.setShaderMatrix();
		SphereGraphic.drawSolidSphere();
		ModelMatrix.main.popMatrix();
	}

	@Override
	public void create () {
		gravity = 4f;
		volume = 1;
		
		shader = new Shader();
		maze = new Maze(15, 15);

		

		BoxGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SphereGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SincGraphic.create(shader.getVertexPointer());
		CoordFrameGraphic.create(shader.getVertexPointer());

		Gdx.gl.glClearColor(0.3f, 0.3f, 0.8f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		//ModelMatrix.main.setShaderMatrix(modelMatrixLoc);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		cam = new Camera();
		cam.look(new Point3D(1.5f, 1f, -0.5f), new Point3D(0,1,-1), new Vector3D(0,1,0));
		//cam.look(new Point3D(5f, 1f, -16f), new Point3D(0,1,-1), new Vector3D(0,1,0));
		orthoCam = new Camera();
		orthoCam.orthographicProjection(-10, 10, -10, 10, 3.0f, 100);
		
		Gdx.input.setCursorCatched(true);
		sound = Gdx.audio.newSound(Gdx.files.internal("hall.mp3"));
		track = sound.play(1);
		
	}
	
	private void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();
			
			angle += 180.0f * deltaTime;
			
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {
				cam.slide(-3.0f * deltaTime, 0, 0);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D)) {
				cam.slide(3.0f * deltaTime, 0, 0);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.W)) {
				//cam.slide(0, 0, -3.0f * deltaTime);
				cam.walkForward(3.0f * deltaTime);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.S)) {
				//cam.slide(0, 0, 3.0f * deltaTime);
				cam.walkForward(-3.0f * deltaTime);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.R)) {
				//cam.slide(0, 3.0f * deltaTime, 0);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.F)) {
				//cam.slide(0, -3.0f * deltaTime, 0);
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
				if(volume == 1){
					volume = 0;
				}
				else{
					volume = 1;
				}
				sound.setVolume(track, volume);
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				//cam.yaw(-90.0f * deltaTime);
				cam.rotateY(90.0f * deltaTime);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				//cam.yaw(90.0f * deltaTime);
				cam.rotateY(-90.0f * deltaTime);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
				cam.pitch(90.0f * deltaTime);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				cam.pitch(-90.0f * deltaTime);
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
				Gdx.graphics.setDisplayMode(500, 500, false);
				sound.dispose();
				Gdx.app.exit();
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
				//cam.roll(-90.0f * deltaTime);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.E)) {
				//cam.roll(90.0f * deltaTime);
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.T)) {
				//fov -= 30.0f * deltaTime;
			}
			if(Gdx.input.isKeyPressed(Input.Keys.G)) {
				//fov += 30.0f * deltaTime;			
			}
		
		
		cam.rotateY(-0.2f * Gdx.input.getDeltaX());
		cam.pitch(-0.2f * Gdx.input.getDeltaY());
		
		//do all updates to the game
	}
	
	private void display()
	{
		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glUniform4f(LabFirst3DGame.colorLoc, 1.0f, 0.3f, 0.1f, 1.0f);
		for(int viewNum = 0; viewNum < 2; viewNum++)
		{
			if(viewNum == 0)
			{
				Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
				cam.perspectiveProjection(fov, 1.0f, 0.1f, 100.0f);
				shader.setViewMatrix(cam.getViewMatrix());
				shader.setProjectionMatrix(cam.getProjectionMatrix());
				shader.setEyePosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);
			}
			else
			{
				Gdx.gl.glViewport(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
				//orthoCam.look(new Point3D(cam.eye.x, 20.0f, cam.eye.z), cam.eye, new Vector3D(0,0,-1));
				orthoCam.look(new Point3D(7.0f, 40.0f, -7.0f), new Point3D(7.0f, 0.0f, -7.0f), new Vector3D(0,0,-1));
				shader.setViewMatrix(orthoCam.getViewMatrix());
				shader.setProjectionMatrix(orthoCam.getProjectionMatrix());
				shader.setEyePosition(orthoCam.eye.x, orthoCam.eye.y, orthoCam.eye.z, 1.0f);

			}
		
			ModelMatrix.main.loadIdentityMatrix();
			
			float s = (float)Math.sin(angle * Math.PI / 180.0);
			float c = (float)Math.cos(angle * Math.PI / 180.0);
			

			shader.setLightPosition(10.0f, 4.0f, -10.0f, 1.0f);
			//shader.setLightPosition(10 * s + 8.5f, 4.0f, 10 * c - 8.5f, 1.0f);

			shader.setLightPosition2(10 * s + 8.5f, 4.0f, 10 * c - 8.5f, 1.0f);
			
			float s2 = Math.abs((float)Math.sin((angle / 2.3) * Math.PI / 180.0));
			float c2 = Math.abs((float)Math.cos((angle * 1.3342) * Math.PI / 180.0));
			

			//shader.setLightColor(s2, 1.0f, c2, 1.0f);
			shader.setLightColor(1.0f, 1.0f, 1.0f, 1.0f);
			shader.setLightColor2(1.0f, 0.5f, 0.5f, 1.0f);
			
			shader.setGlobalAmbient(0.2f, 0.2f, 0.2f, 1);
			
			shader.setMaterialEmission(1.0f, 1.0f, 1.0f, 1.0f);
			shader.setMaterialDiffuse(0, 0, 0, 1);
			shader.setMaterialSpecular(0, 0, 0, 1);
			

			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(10.0f, 4.0f, -10.0f);
			//ModelMatrix.main.addTranslation(10 * s + 8.5f, 4.0f, 10 * c - 8.5f);
			ModelMatrix.main.addScale(0.1f, 0.1f, 0.1f);
			shader.setModelMatrix(ModelMatrix.main.getMatrix());
			SphereGraphic.drawSolidSphere();
			ModelMatrix.main.popMatrix();
			
			
			
			shader.setMaterialDiffuse(0.3f, 0.3f, 0.7f, 1.0f);
			shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
			shader.setMaterialEmission(0, 0, 0, 1);
			shader.setShininess(30.0f);
			

			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(8.0f, 10.0f, -8.0f);
			shader.setModelMatrix(ModelMatrix.main.getMatrix());
			SphereGraphic.drawSolidSphere();
			ModelMatrix.main.popMatrix();

			drawExtraObjects();
			
				
			maze.drawMaze();
			
			if(viewNum == 1)
			{
				shader.setMaterialDiffuse(1.0f, 0.3f, 0.1f, 1.0f);
				
				ModelMatrix.main.pushMatrix();
				ModelMatrix.main.addTranslation(cam.eye.x, cam.eye.y, cam.eye.z);

				ModelMatrix.main.addScale(0.25f, 0.25f, 0.25f);
				shader.setModelMatrix(ModelMatrix.main.getMatrix());
				SphereGraphic.drawSolidSphere();

				ModelMatrix.main.popMatrix();				
			}
		}
	}

	@Override
	public void render () {
		//put the code inside the update and display methods, depending on the nature of the code
		update();
		display();

	}
	
	public void drawExtraObjects(){
		// draw collidiable object
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(7.5f, 1, -16.0f);
		ModelMatrix.main.addScale(0.5f, 9f, 0.5f);
		objectRotationAngle += 45 * Gdx.graphics.getDeltaTime();
		ModelMatrix.main.addRotationY(objectRotationAngle);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		BoxGraphic.drawSolidCube();
		ModelMatrix.main.popMatrix();
		
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(7.5f, 4, -16.0f);
		objectRotationAngle += 45 * Gdx.graphics.getDeltaTime();
		ModelMatrix.main.addRotationY(objectRotationAngle);
		ModelMatrix.main.addScale(0.5f, 0.5f, 3f);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		BoxGraphic.drawSolidCube();
		ModelMatrix.main.popMatrix();
		
		//draw floating objects
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(7.5f, 10.0f, -14.0f);
		//ModelMatrix.main.addRotationZ(45);
		ModelMatrix.main.addScale(1, 2, 1);
		objectRotationAngle += 45 * Gdx.graphics.getDeltaTime();
		ModelMatrix.main.addRotationY(objectRotationAngle);
		ModelMatrix.main.addTranslation(0, 0, 1);
		ModelMatrix.main.addRotationX(-objectRotationAngle);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		BoxGraphic.drawSolidCube();
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addScale(0.25f, 0.25f, 0.25f);
		ModelMatrix.main.addRotationY(objectRotationAngle);
		ModelMatrix.main.addTranslation(6, 0, 0);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		SphereGraphic.drawSolidSphere();
		ModelMatrix.main.popMatrix();
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addScale(0.25f, 0.25f, 0.25f);
		ModelMatrix.main.addRotationY(-objectRotationAngle);
		ModelMatrix.main.addTranslation(0, 0, 8);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		SphereGraphic.drawSolidSphere();
		ModelMatrix.main.popMatrix();
		//SphereGraphic.drawSolidSphere();
		ModelMatrix.main.popMatrix();
	}


}