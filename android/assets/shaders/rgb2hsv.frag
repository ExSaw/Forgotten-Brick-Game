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
uniform float time;
uniform vec2 u_resolution;
uniform vec2 bounds_x;
uniform vec2 bounds_y;
uniform float u_brightness;
uniform float u_saturation;
uniform float u_hue;
// uniform vec3 color;

uniform float u_time;

vec3 rgb2hsv(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}
//  Function from Iñigo Quiles
//  https://www.shadertoy.com/view/MsS3Wc
vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main(){

    vec2 st = gl_FragCoord.xy/u_resolution;
    gl_FragColor = v_color*texture2D(u_texture, v_texCoords);
    // vec3 color = rgb2hsb(gl_FragColor);
    vec4 color2 = gl_FragColor;
    color2=vec4(color2.rgb*u_brightness,color2.a);

    vec3 color = rgb2hsv(color2.rgb);
    // color = hsb2rgb(vec3(0.0,1.0,1.0));
    color.y=clamp(color.y + u_saturation, 0.0, 1.0); //-0.05
    if(u_hue!=1.0){color.x=mix(color.x,u_hue,1.0);} //u_hue is not used when u_hue set to 0
    //color.z=clamp(color.z - 0.05, 0.0, 1.0);
    color = hsv2rgb(color);
    // vec3 color2 = hsb2rgb(vec3(color.x,0.5,1.0));
    gl_FragColor = vec4(color.r,color.g,color.b,color2.a);
    //    gl_FragColor = v_color*texture2D(u_texture, v_texCoords);
}

