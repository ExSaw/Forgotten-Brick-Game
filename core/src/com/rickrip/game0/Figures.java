package com.rickrip.game0;

/**
 * Created by FREQUENCY on 30.07.2017.
 */

//База фигур
public class Figures extends GDX_ForgottenBrickGame {

    public static byte[][] getFigureType(int r){
        switch (r){
            case 0:typeN=type0;break;
            case 1:typeN=type1;break;
            case 2:typeN=type2;break;
            case 3:typeN=type3;break;
            case 4:typeN=type4;break;
            case 5:typeN=type5;break;
            case 6:typeN=type6;break;
            case 7:typeN=type7;break;
            case 8:typeN=type8;break;
            case 9:typeN=type9;break;
            case 10:typeN=type10;break;
            default:typeN=typeN;break;
        }
        return typeN;
    }

    public static byte[][] typeN = new byte[][]{//3x3 dynamic
            {1,1,1},
            {1,1,1},
            {1,1,1}
    };

    public static final byte[][] type0 = new byte[][]{//3x3
            {0,0,0},
            {0,1,0},
            {0,0,0}
    };
    public static final byte[][] type1 = new byte[][]{//3x3
            {0,0,0},
            {1,1,1},
            {0,0,0}
    };
    public static final byte[][] type2 = new byte[][]{//3x3
            {0,1,0},
            {0,1,0},
            {0,1,0}
    };
    public static final byte[][] type3 = new byte[][]{//3x3
            {0,0,0},
            {1,1,0},
            {0,0,0}
    };
    public static final byte[][] type4 = new byte[][]{//3x3
            {0,1,0},
            {0,1,0},
            {0,0,0}
    };
    public static final byte[][] type5 = new byte[][]{//3x3
            {1,1,0},
            {0,1,0},
            {0,0,0}
    };
    public static final byte[][] type6 = new byte[][]{//3x3
            {0,1,1},
            {0,1,0},
            {0,0,0}
    };
    public static final byte[][] type7 = new byte[][]{//3x3
            {0,1,0},
            {1,1,0},
            {0,0,0}
    };
    public static final byte[][] type8 = new byte[][]{//3x3
            {0,1,0},
            {0,1,1},
            {0,0,0}
    };
    public static final byte[][] type9 = new byte[][]{//3x3 used for 2X2 big bricks
            {1,1,0},
            {1,1,0},
            {0,0,0}
    };
    public static final byte[][] type10 = new byte[][]{//3x3 used for 3X3 big bricks
            {1,1,1},
            {1,1,1},
            {1,1,1}
    };
    public static  final int[][] testMainField = new int[][]{//6x13 1-5 colors 6-brick
            //1-red,2-green,3-blue,4-purple,5-yellow,6-cyan,7-stone
            {0,0,0,0,0,0},//0
            {0,0,0,0,0,0},//1
            {0,0,0,0,0,0},//2
            //---------------
            {0,0,0,0,0,0},//3
            {0,0,0,0,0,0},//4
            {1,0,0,0,0,0},//5
            {6,1,1,0,0,5},//6
            {4,2,3,0,1,4},//7
            {5,3,5,0,6,1},//8
            {4,3,4,0,3,6},//9
            {2,1,6,4,5,4},//10
            {6,4,3,5,2,1},//11
            {1,2,1,4,3,4} //12
    };
    public static  final int[][] testFallingField = new int[][]{//6x13 to 3x3 1-5 colors 6-brick
            // 1-red,2-green,3-blue,4-purple,5-yellow,6-cyan,7-stone
            {0,0,0,0,0,0},//0
            {0,0,0,0,0,0},//1
            {0,0,0,1,0,0},//2
            //---------------
            {0,0,0,0,0,0},//3
            {0,0,0,0,0,0},//4
            {0,0,0,0,0,0},//5
            {0,0,0,0,0,0},//6
            {0,0,0,0,0,0},//7
            {0,0,0,0,0,0},//8
            {0,0,0,0,0,0},//9
            {0,0,0,0,0,0},//10
            {0,0,0,0,0,0},//11
            {0,0,0,0,0,0} //12
    };
    public static  final int[][] testNextField = new int[][]{//6x13 to 3x3 1-5 colors 6-brick
            // 1-red,2-green,3-blue,4-purple,5-yellow,6-cyan,7-stone
            {0,0,0},//0
            {5,3,2},//1
            {0,0,0}//2
    };
}
/*
    public static  final int[][] testMainField = new int[][]{//from vid
                {0,0,0,0,0,0},//0
                {0,0,0,0,0,0},//1
                {0,0,0,0,0,0},//2
                //---------------
                {0,0,0,0,0,0},//3
                {0,0,0,0,0,0},//4
                {1,0,0,0,0,0},//5
                {5,1,1,0,0,5},//6
                {4,2,3,0,1,4},//7
                {5,3,5,0,5,1},//8
                {4,3,4,0,3,3},//9
                {2,1,4,4,5,4},//10
                {4,4,3,5,2,1},//11
                {1,2,1,4,3,4} //12
    };
*/
/*
    public static  final int[][] testMainField = new int[][]{//first test
            {0,0,0,0,0,0},//0
            {0,0,0,0,0,0},//1
            {0,0,0,0,0,0},//2
            //---------------
            {0,0,0,0,0,0},//3
            {0,0,0,0,0,0},//4
            {1,0,0,0,0,0},//5
            {6,1,1,0,0,5},//6
            {4,2,3,0,1,4},//7
            {5,3,5,0,6,1},//8
            {4,3,4,0,3,6},//9
            {2,1,6,4,5,4},//10
            {6,4,3,5,2,1},//11
            {1,2,1,4,3,4} //12
    };
    */