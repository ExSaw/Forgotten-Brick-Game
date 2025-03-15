#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

#extension GL_OES_standard_derivatives : enable

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
/* sampler2D это специальный формат данных в  glsl для доступа к текстуре */
uniform sampler2D u_texture;

uniform vec2 resolution;

//star
float star (vec2 uv, float size, float flare)
{
    float star = 1. - length(uv*(1./size));
    return star;

}
void main( void ) {

    vec2 position = (gl_FragCoord.xy / resolution.xy) - .5;
    vec2 uv = (gl_FragCoord.xy -.5 * resolution.xy) / resolution.y;
    float color = 0.0;
    for (int i = 1; i < 5; i++)
    {
        color = star(uv, .5, 1.);
    }

    gl_FragColor = v_color * texture2D(u_texture, v_texCoords)*vec4(color, color, color, 1.0);
}

