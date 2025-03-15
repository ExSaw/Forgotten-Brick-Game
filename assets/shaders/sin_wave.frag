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
#extension GL_OES_standard_derivatives : enable

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

void main( void ) {

    vec2 position = (gl_FragCoord.xy * 2.0 - resolution) / min(resolution.x, resolution.y);

    vec3 destColor = vec3(0.7, 0.25, 1.0);
    vec3 destForm = vec3(0.0);

    for (float i = 0.0; i < 5.0; i++) {
        float j = i + 1.0;
        float q = position.y += sin(position.x * 2.0 + time * 2.0 * cos(j * 0.314)) * 0.05;
        float l = 0.0025 / abs(q);
        destForm += vec3(l);
    }

    gl_FragColor = vec4(vec3(destColor * destForm), 1.0);
}