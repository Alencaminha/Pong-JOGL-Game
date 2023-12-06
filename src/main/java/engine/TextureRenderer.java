package engine;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;

public class TextureRenderer {
    private GL2 gl2;
    private Texture texture;

    public void loadTexture(GL2 gl2, String fileName) {
        this.gl2 = gl2;
        try {
            texture = TextureIO.newTexture(new File(fileName), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        texture.bind(gl2);
        texture.enable(gl2);

        texture.setTexParameteri(gl2, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        texture.setTexParameteri(gl2, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        texture.setTexParameteri(gl2, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_BORDER);
        texture.setTexParameteri(gl2, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_BORDER);
        gl2.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
    }

    public void disableTexture() {
        texture.disable(gl2);
        texture.destroy(gl2);
    }
}
