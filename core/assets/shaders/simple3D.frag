
#ifdef GL_ES
precision mediump float;
#endif

uniform vec4 u_lightDiffuse;

uniform vec4 u_materialDiffuse;

uniform float u_materialShininess;

varying vec4 v_normal;
varying vec4 v_s;
varying vec4 v_h;

void main()
{
	//Lighting
	
	float lambert = dot(v_normal, v_s) / (length(v_normal) * length(v_s)); //The intensity of how the light hits the surfice.
	float phong = dot(v_normal, v_h) / (length(v_normal) * length(v_h));
	
	gl_FragColor = lambert * u_lightDiffuse * u_materialDiffuse + pow(phong, u_materialShininess) * u_lightDiffuse * vec4(1,0.4,0.4,1);
	
}