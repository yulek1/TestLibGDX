package com.gdx.testgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class TestGame extends ApplicationAdapter {

	public class MyActor extends Actor {

		Texture texture = new Texture(Gdx.files.internal("data/test.png"));
		public boolean started = false;

		public MyActor() {
			setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
		}

		@Override
		public void draw(Batch batch, float alpha) {
			batch.draw(texture, this.getX(), getY());
		}
	}

	private Stage actionStage;
	private Viewport viewport;
	private Camera camera;
	private static mxGraph graph;
	private static mxGraphComponent graphComponent;
	private static Object v1;
	private static Object v2;

	public static void init() {
		graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		try {
			v1 = graph.insertVertex(parent, null, "1", 20, 20, 80, 30);
			v2 = graph.insertVertex(parent, null, "2", 240, 150, 80, 30);
			graph.insertEdge(parent, null, "path", v1, v2);
		} finally {
			graph.getModel().endUpdate();
		}

		graphComponent = new mxGraphComponent(graph);
	}

	@Override
	public void create() {

		// background - graph from JGraphX
		camera = new PerspectiveCamera();
		viewport = new FitViewport(400, 400, camera);
		actionStage = new Stage();
		Gdx.input.setInputProcessor(actionStage);

		mxCell myCell1 = (mxCell) ((mxGraphModel) graph.getModel())
				.getCell("1");
		mxCell myCell2 = (mxCell) ((mxGraphModel) graph.getModel())
				.getCell("2");

		MyActor myActor = new MyActor();
		myActor.setPosition((float) graph.getView().getState(myCell1)
				.getCenterX(), (float) graph.getView().getState(myCell1)
				.getCenterY());

		MoveToAction moveAction = new MoveToAction();
		moveAction.setPosition((float) graph.getView().getState(myCell2)
				.getCenterX(), (float) graph.getView().getState(myCell2)
				.getCenterY());
		moveAction.setDuration(1f);
		myActor.addAction(moveAction);

		actionStage.addActor(myActor);

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		actionStage.act(Gdx.graphics.getDeltaTime());
		actionStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//
//		init();
//
//		new LwjglApplication(new TestGame());

		//
		//
		// TestGame game = new TestGame();
		// LwjglAWTCanvas canvas = new LwjglAWTCanvas(game);
		//
		// frame.getContentPane().setLayout(new BorderLayout());
		// frame.getContentPane().add(canvas.getCanvas());
		//
		// // frame.getContentPane().add(graphComponent);
		// // graphComponent.setOpaque(false);
		// //frame.add(graphComponent);
		// frame.pack();
		//
		// frame.setVisible(true);
		// frame.setSize(400, 400);

//	}

}
