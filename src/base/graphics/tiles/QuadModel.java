package base.graphics.tiles;

import base.graphics.models.RawModel;
import base.tools.Mapper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class QuadModel{
    private int maxQuads;
    private RawModel model;
    private Mapper quads=new Mapper();

    public int getMaxQuads(){ return maxQuads; }

    public RawModel getModel() { return model; }

    public Mapper getQuads() { return quads; }

    public QuadTile getQuadTile(String name){
        return (QuadTile)getQuads().get(name);
    }

    public QuadTile getQuadTile(int id){
        return (QuadTile)getQuads().get(id);
    }

    public void addQuadTile(QuadTile quadTile,String name){
        if(getQuads().size()<getMaxQuads()) getQuads().add(quadTile,name);
    }

    public QuadModel(int maxQuads){
        this.maxQuads=maxQuads;
        model=TileMapInit();
        for(int i=0;i<maxQuads;i++){
            ((QuadTile)getQuads().get(i)).setParentModel(getModel());
        }
    }

    public void upd(){
        for(int i=0;i<getQuads().size();i++){
            ((QuadTile)getQuads().get(i)).editFull();
        }
    }

    public RawModel TileMapInit(){
        List<Integer> vaos=new ArrayList<Integer>();
        List<Integer> vbos=new ArrayList<Integer>();

        int vaoID=GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);
        vaos.add(vaoID);

        IntBuffer indices=BufferUtils.createIntBuffer(getMaxQuads()*6);
        FloatBuffer vertices=BufferUtils.createFloatBuffer(getMaxQuads()*4*3);
        FloatBuffer texture=BufferUtils.createFloatBuffer(getMaxQuads()*4*2);

        int vertexsCounter=0;
        int indicesCounter=0;
        int pos=0;

        for(int i=0;i<maxQuads;i++){
            if(getQuads().get(i)==null)addQuadTile(new QuadTile(pos),"q"+pos);
            if(i<getQuads().size()&&getQuads().get(i)!=null){
                QuadTile qt=((QuadTile)getQuads().get(i));

                indices.put(getStandartVertexs(vertexsCounter));
                vertices.put(new float[]{
                        qt.getLt().x,qt.getLt().y,0,
                        qt.getRb().x,qt.getLt().y,0,
                        qt.getRb().x,qt.getRb().y,0,
                        qt.getLt().x,qt.getRb().y,0});
                texture.put(new float[]{
                        qt.getTexturePosLt().x,qt.getTexturePosLt().y,
                        qt.getTexturePosRb().x,qt.getTexturePosLt().y,
                        qt.getTexturePosRb().x,qt.getTexturePosRb().y,
                        qt.getTexturePosLt().x,qt.getTexturePosRb().y});
                pos++;
                indicesCounter+=6;
                vertexsCounter+=4;
            }
        }

        indices.flip();
        int indicesVboID= GL15.glGenBuffers();
        vbos.add(indicesVboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesVboID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);

        vertices.flip();
        int verticesVboID=GL15.glGenBuffers();
        vbos.add(verticesVboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesVboID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_DYNAMIC_DRAW);
        GL20.glVertexAttribPointer(0,3, GL11.GL_FLOAT,false,0,0);

        texture.flip();
        int textureVboID=GL15.glGenBuffers();
        vbos.add(textureVboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureVboID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texture, GL15.GL_DYNAMIC_DRAW);
        GL20.glVertexAttribPointer(1,2,GL11.GL_FLOAT,false,0,0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);

        indices.clear();
        vertices.clear();
        texture.clear();

        return new RawModel(vaoID,indicesCounter,vaos,vbos);
    }

    private int[] getStandartVertexs(int vertexsCounter){ return new int[] {vertexsCounter+0, vertexsCounter+1, vertexsCounter+2, vertexsCounter+3}; }
}
