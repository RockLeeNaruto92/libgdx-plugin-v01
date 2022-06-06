package thubm.hust.libgdxpattern;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LibgdxPattern implements ApplicationListener {
	private Stage stage;
	private Skin skin;
	private SpriteBatch batch;
	private Design screen;
	
	@Override
	public void create() {
		stage = new Stage();
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("skin.json"));
		
		screen = new Design(stage, skin, batch);
	}

	@Override
	public void dispose() {
		skin.dispose();
		stage.dispose();
		batch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		batch.begin();
		screen.render(Gdx.graphics.getDeltaTime());
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
