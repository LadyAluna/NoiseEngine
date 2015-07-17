#version 330 core

uniform sampler2D textureColor_N;
uniform sampler2D textureNormal_N;
uniform sampler2D textureLight_N;

layout (location = 0) out vec4 color_out_N;
layout (location = 1) out vec4 normal_out_N;
layout (location = 2) out vec4 position_out_N;
layout (location = 3) out vec4 light_out_N; //ambient, reflection

in DATA_N
{
	vec4 position;
	vec3 normal;
	vec3 tangent;
	vec3 bitangent;
	vec2 uv;
} frag_in_N;

void mainSurface(inout vec4 color, in vec2 uv, inout vec4 position, inout vec3 normal, inout float displace, inout vec4 light);

#parameter surfaceShaderFragment

void main(){
	color_out_N = texture(textureColor_N, frag_in_N.uv);

	vec3 normalSample = texture(textureNormal_N, frag_in_N.uv).xyz;
	mat3 worldSpaceMat = mat3(frag_in_N.tangent, frag_in_N.bitangent, frag_in_N.normal);
	normalSample = normalSample * 2.0 - 1.0;
	normal_out_N.xyz = normalize(worldSpaceMat * normalSample);

	position_out_N = frag_in_N.position;

	light_out_N = texture(textureLight_N, frag_in_N.uv);

	mainSurface(color_out_N, frag_in_N.uv, position_out_N, normal_out_N.xyz, normal_out_N.w, light_out_N);
}
