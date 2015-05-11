package thubm.hust.libgdxpattern;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.Gdx;

public class Design{
	private Stage stage;
	private Skin skin;
	private SpriteBatch batch;

	public Design(){
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("skin.json"));
		batch = new SpriteBatch();
		init();
	}

	public Design(Stage stage, Skin skin, SpriteBatch batch){
		this.stage = stage;
		this.skin = skin;
		this.batch = batch;
		init();
	}

	private void init(){
	}

	public void render(float deltaTime){
		// TODO: draw all animations, sprite by batch

	}
}