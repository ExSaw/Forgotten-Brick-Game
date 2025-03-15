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

    float a = 1.8 * abs(v_texCoords.x - 0.5);
    float b = 1.0 - a;
    vec4 color1 = vec4(1.0, 0.215, 0.725, 1.0);
    vec4 color2 = vec4(0.5, 0.925 , 1.0 , 1.0);

    vec4 result = color1 * a + color2 * b;

    gl_FragColor = v_color * texture2D(u_texture, v_texCoords)*result;

}