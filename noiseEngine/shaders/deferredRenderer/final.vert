#version 330 core

uniform mat4 projectionMatrix;
uniform vec2 inverseResolution;

layout (location = 0) in vec2 position;
layout (location = 1) in vec2 uv;

out DATA
{
	vec2 uv;
} vert_out;

void main(){
	gl_Position = projectionMatrix * vec4(position, 0.0, 1.0);

	vec3 pixelOffset = vec3(inverseResolution, 0.0);

	vert_out.uv = uv;
}
