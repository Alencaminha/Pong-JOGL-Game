package objects;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import graphics.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageResource {
    private Texture texture = null;
    private BufferedImage bufferedImage = null;

    public ImageResource(String path) {
        URL url = ImageResource.class.getResource(path);

        try {
            bufferedImage = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (bufferedImage != null) {
            bufferedImage.flush();
        }
    }

    public Texture getTexture() {
        if (bufferedImage == null) {
            return null;
        }

        if (texture == null) {
            texture = AWTTextureIO.newTexture(Renderer.getGlProfile(), bufferedImage, true);
        }

        return texture;
    }
}
