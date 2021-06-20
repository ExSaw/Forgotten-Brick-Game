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

// glslsandbox uniforms
uniform float time;
uniform vec2 resolution;

// shadertoy emulation
#define iTime time
#define iResolution resolution

// --------[ Original ShaderToy begins here ]---------- //

#define SHARPNESS  2.0/iResolution.y

float merge(float s1, float s2) { return min(s1, s2); }
float intersect(float s1, float s2) { return max(s1, s2); }
float subtract(float s1, float s2) { return max(s1, -s2); }


float circle(vec2 pos, float radius)
{
    return length(pos)-radius;
}

float rectangle(vec2 pos, vec2 halfSize)
{
    vec2 edgeDist = abs(pos) - halfSize;
    float outDist = length(max(edgeDist, 0.0));
    float inDist = min(max(edgeDist.x, edgeDist.y), 0.0);
    return outDist + inDist;
}

vec2 rotate(vec2 pos, float rotation){
    const float PI = 3.14159;
    float angle = rotation * PI * 2. * -1.;
    float sine, cosine;
    sine = sin(angle);
    cosine = cos(angle);
    return vec2(cosine * pos.x + sine * pos.y, cosine * pos.y - sine * pos.x);
}

float cog(vec2 pos, float r, float insideR, int numTeeth, float spin, out float pupil) {
    float base = circle(pos, r-(r*0.2));
    pupil = circle(pos, insideR);
    float a = atan(pos.x, pos.y);
    float teeth = smoothstep(-0.5, 1.0, cos(a * float(numTeeth) + spin)) + length(pos)-r;
    teeth = merge(base, teeth);
    teeth = subtract(teeth, pupil);
    return teeth;
}

float fullClaw(vec2 pos, float t)
{
    float stageProgress = mod(t+0.5, 4.0);
    float stage = floor(stageProgress);
    vec2 clawOffset = vec2(smoothstep(1.0, 0.0, cos(t * 2. * 3.14159))*4.0, 0.0);
    float clawOpen = 1.0- smoothstep(0.8, 1.0, smoothstep(0.0, 0.7, abs(sin(t * 3.14159-1.0))));
    float rect = rectangle(rotate(pos+vec2(-0.1, 0.0)+clawOffset, 0.125), vec2(0.05)* (clawOpen +.8));
    float claw = subtract(subtract(circle(pos+clawOffset, 0.1), circle(pos+clawOffset, 0.08)), rect);
    float val = smoothstep(SHARPNESS, 0.0, claw)*0.3;
    val = max(val, smoothstep(SHARPNESS, 0.0, rectangle(pos+vec2(0.55, 0.0)+clawOffset, vec2(.45, 0.03)))*0.25);
    val = max(val, smoothstep(SHARPNESS, 0.0, rectangle(pos+vec2(1.4, 0.0)+clawOffset, vec2(.4, 0.05)))*0.2);
    return val;
}

float hash(float x)
{
    return fract(sin(x * 171.2972) * 18267.978 + 31.287);
}
vec2 hash2(in vec2 p)
{
    return fract(1965.5786 * vec2(sin(p.x * 591.32 + p.y * 154.077), cos(p.x * 391.32 + p.y * 49.077)));
}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec2 uv = fragCoord/iResolution.xy;
    uv = 2. * uv -1.;
    uv.x *= iResolution.x/iResolution.y;

    float t = iTime * 0.5;

    float stageProgress = mod(t+0.5, 5.0);
    float stage = floor(stageProgress);
    float id = floor((t+0.5) / 5.0);


    uv.x -= float(stage == 4.0) * (stageProgress - stage) * 4.0;

    vec2 dest;
    int istage = int(stage);
    if (istage == 0) //face
    dest = vec2(0.8, 0.0);
    else if (istage == 1) //left eye
    dest = vec2(0.29, -0.6);
    else if (istage == 2) //right eye
    dest = vec2(-0.6, -0.3);
    else //mouth
    dest = vec2(0.0, 0.6);
    float claw = float(stage < 4.0) * fullClaw(rotate(uv + dest, 0.25 * stage), t);

    bool mouthType = hash(id) > 0.5;
    bool eye1Type = hash(id+1.0) > 0.4;
    bool eye2Type = hash(id+2.0) > 0.4;
    bool faceType = hash(id+3.0) > 0.4;

    vec2 faceOffset = vec2(smoothstep(1.0, 0.0, max(stage+floor(stageProgress+0.5),cos(t * 2. * 3.14159)))*4.0, 0.0);
    vec2 eye1Offset = vec2(0.0, -smoothstep(1.0 , 0.0, max(stage-1.+floor(stageProgress-stage+0.5),cos(t * 2. * 3.14159)))*4.0);
    vec2 eye2Offset = vec2(-smoothstep(1.0 , 0.0, max(stage-2.+floor(stageProgress-stage+0.5),cos(t * 2. * 3.14159)))*4.0, 0.0);
    vec2 mouthOffset = vec2(0.0, smoothstep(1.0 , 0.0, max(stage-3.+floor(stageProgress-stage+0.5),cos(t * 2. * 3.14159)))*4.0);


    float eye1Pupil, eye2Pupil;
    vec2 pupilSize = hash2(vec2(id))*0.16 + 0.03;
    vec2 eyeSize = hash2(vec2(id+1.0))*0.2 + 0.1;
    int numGears = int(hash(id+4.0)*6.0)+6;
    float robo;

    if (eye1Type) {
        robo += float(stage >= 1.0) * smoothstep(SHARPNESS, 0.0, cog(uv+vec2(0.29, -0.3)+eye1Offset, 0.3, pupilSize.x, numGears, t * 2.0, eye1Pupil));
    } else {
        float whites = circle(uv+vec2(0.29, -0.3)+eye1Offset, eyeSize.x);
        eye1Pupil = circle(uv+vec2(0.29, -0.3)+eye1Offset, pupilSize.x);
        robo += float(stage >= 1.0) * smoothstep(SHARPNESS, 0.0, subtract(whites, eye1Pupil));
    }


    if (eye2Type) {
        robo += float(stage >= 2.0) * smoothstep(SHARPNESS, 0.0, cog(uv+vec2(-0.29, -0.3)+eye2Offset, 0.3, pupilSize.y, numGears, -t * 2.0 + 3.14, eye2Pupil));
    } else {
        float whites = circle(uv+vec2(-0.29, -0.3)+eye2Offset, eyeSize.y);
        eye2Pupil = circle(uv+vec2(-0.29, -0.3)+eye2Offset, pupilSize.y);
        robo += float(stage >= 2.0) * smoothstep(SHARPNESS, 0.0, subtract(whites, eye2Pupil));
    }


    float face;
    if (faceType) {
        face = rectangle(uv+faceOffset, vec2(0.7));
    } else {
        face = circle(uv+faceOffset, 0.8);
    }
    vec2 mouthSize = hash2(vec2(id+5.0));
    mouthSize.x = mouthSize.x * 0.3+0.2;
    mouthSize.y = mouthSize.y * 0.15+0.05;
    float mouth = rectangle(uv+vec2(0.0, 0.3)+mouthOffset, mouthSize);

    float chomp = rectangle(uv+vec2(0.0, 0.3)+mouthOffset, vec2(mouthSize.x+0.01, (mouthSize.y-0.02)* max(0.1, sin(t*3.1415))));
    float freq = hash(id+6.0)*60.0+5.0;
    float scared = rectangle(uv+vec2(0.0, 0.3+0.008*(sin(uv.x*freq+t*20.0)*2.0 - 1.0))+mouthOffset, vec2(mouthSize.x+0.01, mouthSize.y*0.25));


    if (stage >= 1.0)
    {
        face = subtract(face, eye1Pupil);
    }
    if (stage >= 2.0)
    {
        face = subtract(face, eye2Pupil);
    }
    if (stage >= 3.0)
    {
        face = subtract(face, mouthType ? scared : chomp);
    }


    robo += smoothstep(SHARPNESS, 0.0, face) * 0.5;
    robo += float(stage >= 3.0) * smoothstep(SHARPNESS, 0.0, subtract(mouth, mouthType ? scared : chomp));

    //Urgh good enough. Not really sure how to handle this.
    robo = mix(robo, claw, clamp(claw/robo, claw, 1.0));

    // Output to screen
    vec3 col = vec3(robo);
    fragColor = vec4(col,1.0);
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords)*fragColor;
}
// --------[ Original ShaderToy ends here ]---------- //

void main(void)
{
    mainImage(gl_FragColor, gl_FragCoord.xy);
}
