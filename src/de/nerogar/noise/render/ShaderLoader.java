package de.nerogar.noise.render;

import de.nerogar.noise.util.FileUtil;
import de.nerogar.noise.util.ProgramLoader;

import java.util.Map;

/**
 * Utility class for easy shader loading. Special syntax can be used as defined in {@link ProgramLoader ProgramLoader}.
 */
public class ShaderLoader {

	/**
	 * Loads a shader from files.
	 * All file paths are {@link ProgramLoader file IDs}.
	 *
	 * @param vertexShaderFile   path to the vertex shader
	 * @param fragmentShaderFile path to the fragment shader
	 * @return the new shader
	 */
	public static Shader loadShader(String vertexShaderFile, String fragmentShaderFile) {
		return loadShader(vertexShaderFile, fragmentShaderFile, (Map<String, String>) null);
	}

	/**
	 * Loads a shader from files.
	 * All file paths are {@link ProgramLoader file IDs}.
	 * More info on parameters {@link ProgramLoader here}.
	 *
	 * @param vertexShaderFile   path to the vertex shader
	 * @param fragmentShaderFile path to the fragment shader
	 * @param parameters         a map containing all parameters
	 * @return the new shader
	 */
	public static Shader loadShader(String vertexShaderFile, String fragmentShaderFile, Map<String, String> parameters) {
		String vertexShader = ProgramLoader.readFile(FileUtil.decodeFilename(null, FileUtil.SHADER_SUBFOLDER, vertexShaderFile), parameters);
		String fragmentShader = ProgramLoader.readFile(FileUtil.decodeFilename(null, FileUtil.SHADER_SUBFOLDER, fragmentShaderFile), parameters);

		Shader shader = new Shader(vertexShaderFile + " " + fragmentShaderFile);
		shader.setVertexShader(vertexShader);
		shader.setFragmentShader(fragmentShader);
		shader.compile();

		return shader;
	}

	/**
	 * Loads a shader from files.
	 * All file paths are {@link ProgramLoader file IDs}.
	 *
	 * @param vertexShaderFile   path to the vertex shader
	 * @param geometryShaderFile path to the geometry shader
	 * @param fragmentShaderFile path to the fragment shader
	 * @return the new shader
	 */
	public static Shader loadShader(String vertexShaderFile, String geometryShaderFile, String fragmentShaderFile) {
		return loadShader(vertexShaderFile, geometryShaderFile, fragmentShaderFile, (Map<String, String>) null);
	}

	/**
	 * Loads a shader from files.
	 * All file paths are {@link ProgramLoader file IDs}.
	 * More info on parameters {@link ProgramLoader here}.
	 *
	 * @param vertexShaderFile   path to the vertex shader
	 * @param geometryShaderFile path to the geometry shader
	 * @param fragmentShaderFile path to the fragment shader
	 * @param parameters         a map containing all parameters
	 * @return the new shader
	 */
	public static Shader loadShader(String vertexShaderFile, String geometryShaderFile, String fragmentShaderFile, Map<String, String> parameters) {
		String vertexShader = ProgramLoader.readFile(FileUtil.decodeFilename(null, FileUtil.SHADER_SUBFOLDER, vertexShaderFile), parameters);
		String geometryShader = ProgramLoader.readFile(FileUtil.decodeFilename(null,FileUtil.SHADER_SUBFOLDER,  geometryShaderFile), parameters);
		String fragmentShader = ProgramLoader.readFile(FileUtil.decodeFilename(null, FileUtil.SHADER_SUBFOLDER, fragmentShaderFile), parameters);

		Shader shader = new Shader(vertexShaderFile + " " + fragmentShaderFile);
		shader.setVertexShader(vertexShader);
		shader.setGeometryShader(geometryShader);
		shader.setFragmentShader(fragmentShader);
		shader.compile();

		return shader;
	}

}
