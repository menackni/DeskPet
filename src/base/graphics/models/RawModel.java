package base.graphics.models;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.List;

public class RawModel {
	private int vaoID;
	private int vertexCount;

	private List<Integer> vaos;
	private List<Integer> vbos;

	public List<Integer> getVaos() { return vaos; }
	public List<Integer> getVbos() { return vbos; }

	private boolean canUse;

	public boolean canUse(){ return canUse; }

	public void cleanUp(){
		canUse=false;
		for(int a:vaos){
			GL30.glDeleteVertexArrays(a);
		}
		for(int a:vbos){
			GL15.glDeleteBuffers(a);
		}
		vaos.clear();
		vbos.clear();
	}

	public RawModel(int vaoID, int vertexCount, List<Integer>vaos, List<Integer> vbos){
		this.setVaoID(vaoID);
		this.setVertexCount(vertexCount);
		this.vaos=vaos;
		this.vbos=vbos;
		canUse=true;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	public int getVaoID() {
		return vaoID;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}
	public void setVaoID(int vaoID) {
		this.vaoID = vaoID;
	}

}
