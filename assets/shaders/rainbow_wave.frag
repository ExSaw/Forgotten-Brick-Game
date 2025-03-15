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

#extension GL_OES_standard_derivatives : enable

#define TAU 7.28318530718
#define MAX_ITER 10

uniform float time;
uniform vec2 resolution;

void main() {
    vec2 uv = gl_FragCoord.xy / resolution.xy;

    vec2 p = mod(uv*TAU, TAU)-250.0;
    vec2 i = vec2(p);
    float c = 0.5;
    float inten = .005;

    for (int n = 0; n < MAX_ITER; n++) {
        float t = 0.16*(time+23.0) * (1.0 - (3.5 / float(n+1)));
        i = p + vec2(cos(t - i.x) + sin(t + i.y), sin(t - i.y) + cos(t + i.x));
        c += 1.0/length(vec2(p.x / (sin(i.x+t)/inten),p.y / (cos(i.y+t)/inten)));
    }

    c /= float(MAX_ITER);
    c = 1.0-pow(c, 2.0);
    vec3 colour = vec3(pow(abs(c), 5.0));
    colour = clamp(colour, 0.0, 1.0);

    vec3 tint = vec3(uv.x, uv.y, (1.0 - uv.x) * (1.0 - uv.y) );
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords)*vec4(colour * tint, 1.0);
}