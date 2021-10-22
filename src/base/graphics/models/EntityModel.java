package base.graphics.models;

import base.graphics.textures.Texture;
import org.joml.Vector3f;

public class EntityModel {
    private RawModel raw;
    private Texture texture;
    private Vector3f position;
    private Vector3f rotarion;
    private Vector3f scale;

    public EntityModel(RawModel raw, Texture texture){
        this.raw=raw;
        this.texture=texture;
        position=new Vector3f(0);
        rotarion=new Vector3f(0);
        scale=new Vector3f(1);
    }

    public EntityModel(RawModel raw, Texture texture, Vector3f position, Vector3f rotarion, Vector3f scale){
        this.raw=raw;
        this.texture=texture;
        this.position=position;
        this.rotarion=rotarion;
        this.scale=scale;
    }

    public RawModel getRaw() {
        return raw;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotarion() {
        return rotarion;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setRaw(RawModel raw) {
        this.raw = raw;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotarion(Vector3f rotarion) {
        this.rotarion = rotarion;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}
