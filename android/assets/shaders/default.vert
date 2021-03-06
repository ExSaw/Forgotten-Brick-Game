attribute vec4 a_position; /*позиция вершины*/
attribute vec4 a_color; /*цвет вершины*/
attribute vec2 a_texCoord0; /*координаты текстуры*/
uniform mat4 u_projTrans;  /*матрица, которая содержим данные для преобразования проекции и вида*/
varying vec4 v_color;  /*цвет который будет передан в фрагментный шейдер*/
varying vec2 v_texCoords;  /*координаты текстуры*/
void main(){
    v_color=a_color;
    /* При передаче цвет из SpriteBatch в шейдер, происходит преобразование из ABGR int цвета в float.
     что-бы избежать NAN  при преобразование, доступен не весь диапазон для альфы, а только значения от (0-254)
    чтобы полностью передать непрозрачность цвета, когда альфа во float равна 1, то всю альфу приходится умножать.
    это специфика libgdx и о ней надо помнить при переопределение  вершинного шейдера.*/
    v_color.a = v_color.a * (255.0/254.0);
    v_texCoords = a_texCoord0;
    /*применяем преобразование вида и проекции, можно не забивать себе этим голову
     тут происходят математические преобразование что-бы правильно учесть параметры камеры
     gl_Position это окончательная позиция вершины*/
    gl_Position =  u_projTrans * a_position;
}