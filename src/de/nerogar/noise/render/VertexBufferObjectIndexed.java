package de.nerogar.noise.render;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;

import de.nerogar.noise.log.Logger;

public class VertexBufferObjectIndexed extends VertexBufferObject {

	private int vboHandle;
	private int indexBufferHandle;

	private int vertexCount;
	private int indexCount;
	private int totalComponents;
	private int[] componentCounts;
	private int[] incrementalComponentCounts;

	/**
	 * @param componentCounts an array containing all component counts
	 * @param indexArary an array containing the index data
	 * @param attributes arays containing all components to use for this VBO
	 * 
	 * @throws ArrayIndexOutOfBoundsException if componentCounts.length does not equal the amount of attribute arrays
	 */
	public VertexBufferObjectIndexed(int[] componentCounts, int[] indexArary, float[]... attributes) {
		this(TRIANGLES, componentCounts, indexArary, attributes);
	}

	/**
	 * @param renderType type of rendered primitives. Either {@link VertexBufferObject#POINTS POINTS},
	 * {@link VertexBufferObject#TRIANGLES TRIANGLES} or {@link VertexBufferObject#LINES LINES}
	 * @param componentCounts an array containing all component counts
	 * @param indexArary an array containing the index data
	 * @param attributes arays containing all components to use for this VBO
	 * 
	 * @throws ArrayIndexOutOfBoundsException if componentCounts.length does not equal the amount of attribute arrays
	 */
	public VertexBufferObjectIndexed(int renderType, int[] componentCounts, int[] indexArary, float[]... attributes) {
		this.renderType = renderType;

		if (componentCounts.length != attributes.length) throw new ArrayIndexOutOfBoundsException();

		glContextVaoHandles = new HashMap<Long, Integer>();

		this.componentCounts = componentCounts;

		//create Buffer
		incrementalComponentCounts = new int[componentCounts.length];
		totalComponents = 0;

		for (int i = 0; i < componentCounts.length; i++) {
			incrementalComponentCounts[i] = totalComponents;
			totalComponents += componentCounts[i];
		}

		if (attributes.length > 0) {
			vertexCount = attributes[0].length / componentCounts[0];
		} else {
			vertexCount = 0;
		}
		indexCount = indexArary.length;

		float[] attribArray = new float[totalComponents * vertexCount];

		for (int attrib = 0; attrib < attributes.length; attrib++) {
			for (int vertex = 0; vertex < vertexCount; vertex++) {
				for (int component = 0; component < componentCounts[attrib]; component++) {
					attribArray[vertex * totalComponents + incrementalComponentCounts[attrib] + component] = attributes[attrib][component + vertex * componentCounts[attrib]];
				}
			}
		}

		FloatBuffer buffer = BufferUtils.createFloatBuffer(attribArray.length);
		buffer.put(attribArray);
		buffer.flip();

		IntBuffer indexBuffer = BufferUtils.createIntBuffer(indexArary.length);
		indexBuffer.put(indexArary);
		indexBuffer.flip();

		initVAO(buffer, indexBuffer);
	}

	private int initVAO(FloatBuffer vboBuffer, IntBuffer indexBuffer) {
		int vaoHandle = glGenVertexArrays();
		glContextVaoHandles.put(glfwGetCurrentContext(), vaoHandle);

		glBindVertexArray(vaoHandle);

		if (vboBuffer != null) {
			vboHandle = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vboHandle);
			glBufferData(GL_ARRAY_BUFFER, vboBuffer, GL_STATIC_DRAW);
		} else {
			glBindBuffer(GL_ARRAY_BUFFER, vboHandle);
		}

		for (int i = 0; i < componentCounts.length; i++) {
			glVertexAttribPointer(i, componentCounts[i], GL_FLOAT, false, totalComponents * Float.BYTES, incrementalComponentCounts[i] * Float.BYTES);

			glEnableVertexAttribArray(i);
		}

		glBindBuffer(GL_ARRAY_BUFFER, 0);

		if (indexBuffer != null) {
			indexBufferHandle = glGenBuffers();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferHandle);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
		} else {
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferHandle);
		}

		glBindVertexArray(0);
		return vaoHandle;
	}

	@Override
	public void render() {
		Integer vaoHandle = glContextVaoHandles.get(glfwGetCurrentContext());
		if (vaoHandle == null) vaoHandle = initVAO(null, null);

		glBindVertexArray(vaoHandle);
		glDrawElements(renderType, indexCount, GL_UNSIGNED_INT, 0);
		glBindVertexArray(0);
	}

	@Override
	public void cleanup() {
		long currentContext = glfwGetCurrentContext();
		glDeleteBuffers(vboHandle);
		glDeleteBuffers(indexBufferHandle);

		for (long context : glContextVaoHandles.keySet()) {
			glfwMakeContextCurrent(context);

			glDeleteVertexArrays(glContextVaoHandles.get(context));
		}

		glfwMakeContextCurrent(currentContext);

		deleted = true;
	}

	@Override
	protected void finalize() throws Throwable {
		if (!deleted) Logger.log(Logger.WARNING, "VBO not cleaned up. pointer: " + vboHandle);
	}

}