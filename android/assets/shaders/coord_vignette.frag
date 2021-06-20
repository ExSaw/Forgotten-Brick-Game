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
#ifdef GL_ES
precision lowp float;
#endif

#extension GL_OES_standard_derivatives : enable

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;
const int iterations = 1;

float width = 10000.0;
float range = 50000.0;

void main( void ) {

    float a = mouse.x * resolution.x;
    float b = mouse.y * resolution.y;

    float x = (gl_FragCoord.x - a) * (gl_FragCoord.x - a);
    float y = (gl_FragCoord.y - b) * (gl_FragCoord.y - b);

    width = time * 1.0;

    if (abs((x + y) - width) < (range))
    {
        gl_FragColor = v_color * texture2D(u_texture, v_texCoords) * vec4((1.0 - (abs((x + y) - width)) / range) * (sin(time * 3.0) + 1.0) / 2.0);

    }
}