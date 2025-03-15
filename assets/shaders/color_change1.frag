#version 120
#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
/* sampler2D это специальный формат данных в  glsl для доступа к текстуре */
uniform sampler2D u_texture;

/* test shader */
/* uniform vec2 resolution; */
uniform float time;

vec3 colorA = vec3(1.0,1.0,1.0);
vec3 colorB = vec3(1.0,0.0,1.0);

void main() {
    vec3 color = vec3(0.0);

    float pct = abs(sin(time));

    // Mix uses pct (a value from 0-1) to
    // mix the two colors
    color = mix(colorA, colorB, pct);

    gl_FragColor = v_color * texture2D(u_texture, v_texCoords)*vec4(color, 1.0);
}
