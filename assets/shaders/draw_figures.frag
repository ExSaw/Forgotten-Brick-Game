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
uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

#define white vec3(1.0)
const vec3 red     = vec3(1.0, 0.0, 0.0);
const vec3 green   = vec3(0.0, 1.0, 0.0);
const vec3 blue    = vec3(0.0, 0.0, 1.0);

void circle(vec2 p, vec2 offset, float size, vec3 color, inout vec3 i) {
    float l = length(p - offset);
    if (l < size) {
        i = color;
    }
}

void rect(vec2 p, vec2 offset, float size, vec3 color, inout vec3 i) {
    vec2 q = (p - offset) / size;
    if (abs(q.x) < 1.0 && abs(q.y) < 1.0) {
        i = color;
    }
}

void ellipse(vec2 p, vec2 offset, vec2 prop, float size, vec3 color, inout vec3 i) {
    vec2 q = (p - offset) / prop;
    if (length(q) < size) {
        i = color;
    }
}

void main( void ) {

    vec3 destColor = white;
    vec2 p = (gl_FragCoord.xy * 2.0 - resolution) / min(resolution.x, resolution.y);

    circle (p, vec2( 0.0,  0.5), 0.55, red, destColor);
    rect   (p, vec2( 0.5, -0.5), 0.25, green, destColor);
    ellipse(p, vec2(-0.5, -0.5), vec2(1.0, 2.0), 0.2, blue, destColor);
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords)*vec4(destColor, 1.0);
}