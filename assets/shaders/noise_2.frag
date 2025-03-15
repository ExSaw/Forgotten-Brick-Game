#ifdef GL_ES
#extension GL_OES_standard_derivatives : enable
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
uniform vec2 resolution;
uniform float time;
uniform float alpha;


float rand(vec2 p) {
    return fract(sin(dot(p + fract(time), vec2(123, 35))) * 12345.3);
}

void main( void ) {
    vec2 p = gl_FragCoord.xy/resolution.xy*0.5;

    float col0 = max(0.2, rand(p));
    float col1 = dot(p * vec2(1.5, 1.0), p);
    // gl_FragColor = vec4(1.0,1.0,1.0,(col0 + col1)*alpha);
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords)*vec4((col0 + col1)*alpha); //mix with texture
    //   /*v_color * texture2D(u_texture, v_texCoords)*0.1**/vec4((col0 + col1)*0.7,0.5);

}