#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

#define PI 3.142

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
/* sampler2D это специальный формат данных в  glsl для доступа к текстуре */
uniform sampler2D u_texture;

/* test shader */
uniform vec2 resolution;
uniform vec2 bounds_x;
uniform vec2 bounds_y;
uniform float uniform_float_brightness;
uniform vec4 uniform_vec4_color;

/* float PI = radians(180.); // So many people hardcode PI by typing out its digits. Why not use this instead? */

void main(){

    // vec2 p = gl_FragCoord.xy/resolution.xy; // coord from 0.0 to 1.0
    vec2 p = gl_FragCoord.xy;
    vec2 sx = bounds_x.xy;
    vec2 sy = bounds_y.xy;
    if(p.x>=sx.x&&p.x<=sx.y&&p.y>=bounds_y.x&&p.y<=bounds_y.y){
        gl_FragColor = v_color * texture2D(u_texture, v_texCoords);/* итоговый цвет пикселя */
        gl_FragColor.x=clamp(gl_FragColor.x*uniform_float_brightness*uniform_vec4_color.x,0.0,1.0);
        gl_FragColor.y=clamp(gl_FragColor.y*uniform_float_brightness*uniform_vec4_color.y,0.0,1.0);
        gl_FragColor.z=clamp(gl_FragColor.z*uniform_float_brightness*uniform_vec4_color.z,0.0,1.0);
    }
  //  else{gl_FragColor = vec4(0.0,0.0,0.0,0.0);}
    else{gl_FragColor = vec4(p.x/resolution.x,0.0,p.y/resolution.y,0.0);}
   // else{gl_FragColor = vec4(1.0,0.0,0.0,0.1);}

}
