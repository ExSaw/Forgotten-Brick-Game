#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

#define iTime time
#define iResolution resolution
#define PI 3.142
#define saturate(x) clamp(x, 0.0, 1.0)
#define Rand(idx) fract(phase*pow(1.618,float(idx)))

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
/* sampler2D это специальный формат данных в  glsl для доступа к текстуре */
uniform sampler2D u_texture;

/* test shader */
uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

#define t (100.0*time+1324.)


void main( void ) {
vec2 uv = (gl_FragCoord.xy-.5*resolution.xy) /resolution.y;

float b = 0.0;
float size = 0.1;
float blur = 0.01;


for(float i=5.0;i<200.0;i++){
    size += (i/150000.0);
    b +=  smoothstep(size, size-blur, length(uv - (vec2(sin(t/5./+i+5./sin(i)),cos(t/5./(4.-i))/2.))));
}

gl_FragColor = vec4( b/7.,b/3.,b/4., 1.0 );
}
