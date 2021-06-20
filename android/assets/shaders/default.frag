/* #ifdef позволяет коду работать на слабых телефонах, и мощных пк.Если шейдер используется на телефоне(GL_ES) то
используется низкая разрядность (точность) данных.(highp – высокая точность; mediump – средняя точность; lowp – низкая точность) */
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
uniform float uniform_float_brightness;
uniform vec4 uniform_vec4_color;
void main(){
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);/* итоговый цвет пикселя */
    gl_FragColor.x=clamp(gl_FragColor.x*uniform_float_brightness*uniform_vec4_color.x,0.0,1.0);
    gl_FragColor.y=clamp(gl_FragColor.y*uniform_float_brightness*uniform_vec4_color.y,0.0,1.0);
    gl_FragColor.z=clamp(gl_FragColor.z*uniform_float_brightness*uniform_vec4_color.z,0.0,1.0);
}