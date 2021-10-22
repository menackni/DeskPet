package base.graphics.shaders;

import org.joml.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
//import org.lwjgl.opengl.GL32;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;



public abstract class ShaderProgram {
	
	public int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	//private int geometryShaderID;
	private static FloatBuffer matrixBuffer=BufferUtils.createFloatBuffer(16);
	
	//public ShaderProgram(String vertexFile, String fragmentFile , String geometryFile){
    //    System.out.println(fragmentFile+" "+vertexFile+" "+geometryFile);
	//	vertexShaderID  =loadShader(vertexFile  , GL20.GL_VERTEX_SHADER);
	//	fragmentShaderID=loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
	//	geometryShaderID=loadShader(geometryFile, GL32.GL_GEOMETRY_SHADER);
	//	programID=GL20.glCreateProgram();
	//	GL20.glAttachShader(programID, vertexShaderID);
	//	GL20.glAttachShader(programID, fragmentShaderID);
	//	GL20.glAttachShader(programID, geometryShaderID);
	//	bindAttributes();
	//	GL20.glLinkProgram(programID);
	//	GL20.glValidateProgram(programID);
	//	getAllUniformLocations();
	//}
	
	public ShaderProgram(String vertexFile, String fragmentFile){
        System.out.println(fragmentFile+" "+vertexFile);
		vertexShaderID  =loadShader(vertexFile  , GL20.GL_VERTEX_SHADER);
		fragmentShaderID=loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID=GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	public ShaderProgram(String fragmentFile){
        System.out.println(fragmentFile);
		fragmentShaderID=loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID=GL20.glCreateProgram();
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName){
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	
	public void start(){
		GL20.glUseProgram(programID);
	}
	
	public void stop(){
		GL20.glUseProgram(0);
	}
	
	public void cleanUp(){
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		//GL20.glDetachShader(programID, geometryShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		//GL20.glDeleteShader(geometryShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String variableName){
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}

	protected void loadFloat(int location, float value){
		GL20.glUniform1f(location, value);
	}
	
	protected void loadVector3f(int location, Vector3f vector){
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void loadVector4f(int location, Vector4f vector){
		GL20.glUniform4f(location, vector.x, vector.y, vector.z,vector.w);
	}
	
	protected void loadVector2f(int location, Vector2f vector){
		GL20.glUniform2f(location, vector.x, vector.y);
	}
	
	protected void loadTex(int location, int id){
		GL20.glUniform1i(location, id);
	}
	
	protected void loadArr(int location, Vector2i[] arr){
		FloatBuffer buff=BufferUtils.createFloatBuffer(arr.length*2);
		for(int i=0;i<arr.length;i++){
			buff.put(arr[i].x);
			buff.put(arr[i].y);
		}
		buff.flip();
		GL20.glUniform2fv(location, buff);
		buff.clear();
		
	}
	
	protected void loadArr(int location, float[] arr){
		FloatBuffer buff=BufferUtils.createFloatBuffer(arr.length);
		buff.put(arr);
		buff.flip();
		GL20.glUniform1fv(location, buff);
		buff.clear();
		
	}
	
	protected void loadInt(int location, int value){
		GL20.glUniform1i(location, value);
	}
	
	protected void loadBoolean(int location, boolean value){
		int toload=0;
		if(value){
			toload=1;
		}
		GL20.glUniform1i(location, toload);
	}

	public void store(FloatBuffer buf, Matrix4f matrix) {
		buf.put(matrix.m00());
		buf.put(matrix.m01());
		buf.put(matrix.m02());
		buf.put(matrix.m03());
		buf.put(matrix.m10());
		buf.put(matrix.m11());
		buf.put(matrix.m12());
		buf.put(matrix.m13());
		buf.put(matrix.m20());
		buf.put(matrix.m21());
		buf.put(matrix.m22());
		buf.put(matrix.m23());
		buf.put(matrix.m30());
		buf.put(matrix.m31());
		buf.put(matrix.m32());
		buf.put(matrix.m33());
	}

	protected void loadMatrix(int location,Matrix4f matrix){
		//TODO
		//matrix.store(matrixBuffer);
		store(matrixBuffer,matrix);
		matrixBuffer.flip();
		GL20.glUniformMatrix4fv(location, false, matrixBuffer);
	}
	
	
	private static int loadShader(String file,int type){
		StringBuilder shaderSource=new StringBuilder();
		try{
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line;
			while((line=reader.readLine())!=null){
				shaderSource.append(line).append("\n");
			}
			reader.close();
		}catch(IOException e){
			System.err.println("Could not read file!");
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID=GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS)==GL11.GL_FALSE){
			System.out.println(GL20.glGetShaderInfoLog(shaderID,500));
			System.err.println("Could not compile shader.");
			System.exit(-1);
		}
		return shaderID;
	}
	
}
