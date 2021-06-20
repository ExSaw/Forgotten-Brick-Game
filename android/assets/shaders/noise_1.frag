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
uniform float u_time;
uniform vec2 u_resolution;
uniform vec3 u_color;

/* float PI = radians(180.); // So many people hardcode PI by typing out its digits. Why not use this instead? */

void main(){
    vec2 st = gl_FragCoord.xy/u_resolution.xy;
    //st = st*0.2;
  //   vec2 st = vec2(gl_FragCoord.xy)*0.2;
   // vec3 color = vec3(1.0);
    float n1 = 0.0;
/*
    for(float i = 1.0; i < 10.0; i++){
        st.x /= 100.0 / i * cos(i * 8.0 * st.y + u_time + sin(u_time / 75.0));
        st.y /= 100.0 / i * sin(i * 42.0 - st.x * 20.0/st.y / st.x + u_time*0.3+ cos(u_time / 120.0));
        n1 = length(vec2(0, st.y + sin(st.x * PI * i * sin(u_time / 3.0))));
    }*/

    for(float i = 1.0; i < 7.0; i++){ // if i<2.0 => water noise
        st.x /=  i * cos(i * 20.0 * st.y + u_time + sin(u_time));
        st.y /=  i * sin(i * 42.0 - st.x * 35.0/st.y/st.x + u_time*0.3+ cos(u_time *0.008));
        n1 = length(vec2(0.0, st.y + sin(st.x * PI * i * sin(u_time * 0.3))));
    }

    float g = clamp(1.0-pow(n1, 0.1),0.0,1.0);

  //  vec3 color = vec3(0.1);
    gl_FragColor = vec4(u_color, g * 0.3); //0.4
}
