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
uniform float time;

void main() {
   // gl_FragColor = vec4(mix(vec3(.1)-.2, vec3((1.+cos(time/2.))-.1, (1.+cos(time/3.))-.1, (1.+sin(time))-.1), map(p)), 1.);
    gl_FragColor =  v_color * texture2D(u_texture, v_texCoords)*vec4(vec3((1.+cos(time/2.))-.1, (1.+cos(time/3.))-.1, (1.+sin(time))-.1), 1.);
}
