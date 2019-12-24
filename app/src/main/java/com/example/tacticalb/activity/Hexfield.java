package com.example.tacticalb.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.tacticalb.Hex;
import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;
import com.example.tacticalb.class_unit.Devil;
import com.example.tacticalb.class_unit.Hero;
import com.example.tacticalb.class_unit.Robber;
import com.example.tacticalb.class_unit.Unit;
import com.example.tacticalb.class_unit.Warrior;
import com.example.tacticalb.class_unit.Wizard;
import com.example.tacticalb.class_unit.Wolf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import static com.example.tacticalb.activity.MainActivity.scenario;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class Hexfield extends SurfaceView implements SurfaceHolder.Callback {
    /*
    #Класс, в котором создаются, загружаются карты, списки монстров, графика, всмпомогательные методы для Mainactivity,отображение хода игры
     */
    private Hero hero_top;
    private Context context;
    private Hex[][] field = new Hex[20][20];
    private int[][] activefield = new int[20][20];
    private int [][][][] peekfield = new int[20][20][6][2];
    private Path[][] pathsfield = new Path[20][20];
    private int fieldr;
    final Random random = new Random();
    private ArrayList<Rect> rect2List = new ArrayList<>();
    private ArrayList<Rect> rect1List = new ArrayList<>();
    private ArrayList<Unit> typeList = new ArrayList<>();
    private int x0,y0,x1=0,y1=0,dx=0,dy=0,x2,y2;
    private Hex prehex;
    private  int x12, y12;
    private boolean show;
    private int[] k=new int[6];
    private int[] l=new int[6];
    private Bitmap[] bitmaps = new Bitmap[15];
    private  Bitmap[] bitmaps2 = new Bitmap[5];
    private Bitmap[] bitmaps3 = new Bitmap[5];
    private Bitmap[] bitmaps4 = new Bitmap[5];
    private Bitmap[] bitmaps5 = new Bitmap[5];
    private Bitmap[] bitmaps6 = new Bitmap[5];
    private Bitmap[] bitmaps7 = new Bitmap[5];
    private ArrayList<Hex> movemain = new ArrayList<>();
    public int maxSt;
    int [] stagearr;
    ArrayList<Hex> mov = new ArrayList<>();
    private boolean lose =false;

    private ArrayList<Unit> hitList = new ArrayList<>();

    float sc=1,scal;

    private float len =0;
    private ArrayList<ArrayList<Hex>> movarray = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> flaglist1 = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> flaglist2 = new ArrayList<>();
    Unit anime;
    private int animflag =0;
    private int animflag2 = 0;
    private float scale = 1;
    private boolean start = true;
    Bitmap star;
    Bitmap hero1;
    Bitmap hero2;
    Bitmap warrior1;
    Bitmap warrior2;
    Bitmap wizard1;
    Bitmap wizard2;
    Bitmap wolf1;
    Bitmap wolf2;
    Bitmap devil1;
    Bitmap devil2;
    Bitmap wolf1rev;
    Bitmap wolf2rev;
    Bitmap robber1;
    Bitmap robber2;
    Bitmap knife;
    Bitmap tonnel1;
    Bitmap tonnel2;
    Bitmap tonnel3;
    Bitmap tonnel4;
    Bitmap arrow;
    Bitmap wolfhit;
    Bitmap castle;
    Bitmap healerhut;
    Bitmap forge;
    Bitmap boost;
    Bitmap block;
    Paint p1;
    Paint p2;
    Paint p3;
    Paint p4;
    Paint p5;
    Paint p6;
    Paint p7;
    Paint p8;
    Paint p9;

    Rect ton1;
    Rect ton2;
    Rect ton3;
    Rect ton4;
    Rect hut;
    Rect forg;
    Rect cast;
    private Hex chosen;
    //
    boolean resume=false;
    int stage =0;
    //
    private DrawThread drawThread;
    private Unit pool_unit;
    private ArrayList<int []> dList = new ArrayList<>();
    private int [] access = new int[2];

    public ArrayList<Hex> getMovemain() {
        return movemain;
    }

    public void setMovemain(ArrayList<Hex> movemain) {
        this.movemain = movemain;
    }

    public Hex getPrehex() {
        return prehex;
    }

    public void setPrehex(Hex prehex) {
        this.prehex = prehex;
    }


    public int[][][][] getPeekfield() {
        return peekfield;
    }

    public void setPeekfield(int[][][][] peekfield) {
        this.peekfield = peekfield;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public ArrayList<Unit> getHitList() {
        return hitList;
    }

    public Hero getHero_top() {
        return hero_top;
    }

    public void setHero_top(Hero hero_top) {
        this.hero_top = hero_top;
    }

    public void setHitList(ArrayList<Unit> hitList) {
        this.hitList = hitList;
    }

    Save_inf save_inf ;
    public void setY0(int y0) {
        this.y0 = y0;
    }

    public boolean isLose() {
        return lose;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getX0() {
        return x0;
    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public int getY0() {
        return y0;
    }

    public float getLen() {
        return len;
    }


    public void setLen(float len) {
        this.len = len;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getAnimflag() {
        return animflag;
    }

    public void setAnimflag(int animflag) {
        this.animflag = animflag;
    }


    public ArrayList<ArrayList<Integer>> getFlaglist1() {
        return flaglist1;
    }

    public void setFlaglist1(ArrayList<ArrayList<Integer>> flaglist1) {
        this.flaglist1 = flaglist1;
    }

    public ArrayList<ArrayList<Integer>> getFlaglist2() {
        return flaglist2;
    }

    public void setFlaglist2(ArrayList<ArrayList<Integer>> flaglist2) {
        this.flaglist2 = flaglist2;
    }

    public ArrayList<ArrayList<Hex>> getMovarray() {
        return movarray;
    }


    public void setMovarray(ArrayList<ArrayList<Hex>> movarray) {
        this.movarray = movarray;
    }

    public int [] getAccess() {
        return access;
    }

    public void setAccess(int[] access) {
        this.access = access;
    }




    public ArrayList<int[]> getdList() {
        return dList;
    }

    public void setdList(ArrayList<int[]> dList) {
        this.dList = dList;
    }


    public Unit getPool_unit() {
        return pool_unit;
    }

    public void setPool_unit(Unit pool_unit) {
        this.pool_unit = pool_unit;
    }


    public ArrayList<Rect> getRect2List() {
        return rect2List;
    }

    public void setRect2List(ArrayList<Rect> rect2List) {
        this.rect2List = rect2List;
    }

    public ArrayList<Rect> getRect1List() {
        return rect1List;
    }

    public void setRect1List(ArrayList<Rect> rect1List) {
        this.rect1List = rect1List;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }



    public ArrayList<Unit> getTypeList() {
        return typeList;
    }

    public void setTypeList(ArrayList<Unit> typeList) {
        this.typeList = typeList;
    }

    public Hex getChosen() {
        return chosen;
    }

    public void setChosen(Hex chosen) {
        this.chosen = chosen;
    }



    public Hex[][] getField() {
        return field;
    }

    public void setField(Hex[][] field) {
        this.field = field;
    }


    public int[][] getActivefield() {
        return activefield;
    }

    public void setActivefield(int[][] activefield) {
        this.activefield = activefield;
    }

    public int getFieldr() {
        return fieldr;
    }

    public void setFieldr(int fieldr) {
        this.fieldr = fieldr;
    }


    /*
        #Конструктор класса
       */
    public Hexfield(Context context) {
        super(context);
        this.context=context;
        getHolder().addCallback(this);
        block = BitmapFactory.decodeResource(getResources(), R.drawable.block);
        boost = BitmapFactory.decodeResource(getResources(), R.drawable.boost);
        healerhut = BitmapFactory.decodeResource(getResources(), R.drawable.hut);
        wolfhit = BitmapFactory.decodeResource(getResources(), R.drawable.wolfhit);
        star = BitmapFactory.decodeResource(getResources(), R.drawable.star);
        forge = BitmapFactory.decodeResource(getResources(), R.drawable.forge);
        castle = BitmapFactory.decodeResource(getResources(), R.drawable.castle);
        tonnel1 = BitmapFactory.decodeResource(getResources(), R.drawable.tonnel1);
        tonnel2 = BitmapFactory.decodeResource(getResources(), R.drawable.tonnel2);
        tonnel3 = BitmapFactory.decodeResource(getResources(), R.drawable.tonnel3);
        tonnel4 = BitmapFactory.decodeResource(getResources(), R.drawable.tonnel4);
        knife = BitmapFactory.decodeResource(getResources(), R.drawable.knife);
        hero1 = BitmapFactory.decodeResource(getResources(), R.drawable.hero1);
        hero2 = BitmapFactory.decodeResource(getResources(), R.drawable.hero2);
        robber1 = BitmapFactory.decodeResource(getResources(), R.drawable.robber1);
        robber2 = BitmapFactory.decodeResource(getResources(), R.drawable.robber2);
        warrior1 = BitmapFactory.decodeResource(getResources(), R.drawable.warrior1);
        warrior2 = BitmapFactory.decodeResource(getResources(), R.drawable.warrior2);
        wizard1 = BitmapFactory.decodeResource(getResources(), R.drawable.wizard1);
        wizard2 = BitmapFactory.decodeResource(getResources(), R.drawable.wizard2);
        wolf1 = BitmapFactory.decodeResource(getResources(), R.drawable.wolf1);
        wolf2 = BitmapFactory.decodeResource(getResources(), R.drawable.wolf2);
        wolf1rev = BitmapFactory.decodeResource(getResources(), R.drawable.wolf1rev);
        wolf2rev = BitmapFactory.decodeResource(getResources(), R.drawable.wolf2rev);
        devil1 = BitmapFactory.decodeResource(getResources(), R.drawable.devil1);
        devil2 = BitmapFactory.decodeResource(getResources(), R.drawable.devil2);
        arrow = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                bitmaps[5*i+j]=Bitmap.createBitmap(wolfhit,wolfhit.getWidth()*j/5,wolfhit.getHeight()*i/3,wolfhit.getWidth()/5,wolfhit.getHeight()/3);

            }
        }
        for (int i = 0; i < 5; i++) {
            bitmaps2[i]=Bitmap.createBitmap(block,0,block.getHeight()*i/5,block.getWidth()/4,block.getHeight()/5);

        }
        for (int i = 0; i < 5; i++) {
            bitmaps3[i]=Bitmap.createBitmap(block,block.getWidth()/4,block.getHeight()*i/5,block.getWidth()/4,block.getHeight()/5);

        }
        for (int i = 0; i < 5; i++) {
            bitmaps4[i]=Bitmap.createBitmap(boost,0,boost.getHeight()/2+boost.getHeight()/10*i,boost.getWidth()/4,boost.getHeight()/10);

        }
        for (int i = 0; i < 5; i++) {
            bitmaps5[i]=Bitmap.createBitmap(boost,boost.getWidth()/4,boost.getHeight()/10*i,boost.getWidth()/4,boost.getHeight()/10);
        }
        for (int i = 0; i < 5; i++) {
            bitmaps6[i]=Bitmap.createBitmap(boost,boost.getWidth()/2,boost.getHeight()/10*i,boost.getWidth()/4,boost.getHeight()/10);
        }
        for (int i = 0; i < 5; i++) {
            bitmaps7[i]=Bitmap.createBitmap(boost,boost.getWidth()/4,boost.getHeight()/2+boost.getHeight()/10*i,boost.getWidth()/4,boost.getHeight()/10);
        }
        p1 = new Paint();
        p1.setColor(getResources().getColor(R.color.grase));
        p2 = new Paint();
        p2.setColor(getResources().getColor(R.color.water));
        p3 = new Paint();
        p3.setColor(Color.BLACK);
        p3.setStrokeWidth(2);
        p5 = new Paint();
        p5.setColor(Color.RED);
        p6 = new Paint();
        p6.setColor(getResources().getColor(R.color.rock));
        p7 = new Paint();
        p7.setColor(getResources().getColor(R.color.sand));
        p8 = new Paint();
        p8.setColor(getResources().getColor(R.color.tundra));
        p9 = new Paint();
        p9.setColor(Color.BLUE);

        ton1 = new Rect(0,0,tonnel1.getWidth(),tonnel1.getHeight());
        ton2 = new Rect(0,0,tonnel2.getWidth(),tonnel2.getHeight());
        ton3 = new Rect(0,0,tonnel3.getWidth(),tonnel3.getHeight());
        ton4 = new Rect(0,0,tonnel4.getWidth(),tonnel4.getHeight());
        hut = new Rect(0,0,healerhut.getWidth(),healerhut.getHeight());
        forg = new Rect(0,0,forge.getWidth(),forge.getHeight());
        cast = new Rect(0,0,castle.getWidth(),castle.getHeight());


        access[0]=1;
        access[1]=1;



    }
    /*
        #Определение того, будет ли нанесён удар или он будет заблокирован
       */
    public int Hit(int armver, int damver){
        int a = random.nextInt(armver+damver);
        if(a<armver){
            a = 0 ;
        }
        else{
            a =1;
        }
        return a;
    }
    /*
         #Радиус гексов поля
        */
    public float Calcr(float k) {
        int a = (int) (k*this.getWidth() / 18.5);
        return a;
    }

    /*
             #Создание поля из гексов и массива юнитов
        */
    public Hex[][]  Makefield(int numb) {

        if ((MainActivity.scenka==-1 || MainActivity.scenka==-2)&& !resume) {
            for (int i = 0; i < numb; i++) {
                Unit unit = new Unit();
                typeList.add(unit);
            }
        }
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (MainActivity.scenka==-2 && !resume){
                    if((j<12)&&(j>8)&&random.nextInt(3)==0){
                        activefield[i][j]=3;
                    }
                    else if((j<3 ||j>16)&& random.nextInt(2)==0){
                        activefield[i][j]=4;

                    }
                    else {
                        int rand = random.nextInt(18);
                        if (rand < 6) {
                            activefield[i][j] = 0;

                        } else if (( rand == 6 || rand == 7 || rand == 8 || rand == 9 || rand == 10) && (j < 13) && (j > 7)) {
                            activefield[i][j] = 3;

                        } else if (rand == 11||rand==12) {
                            activefield[i][j] = 2;


                        } else if (( rand == 13 || rand == 14 || rand == 15 || rand == 16 || rand == 17 ) && ((j > 16) || (j < 3))) {
                            activefield[i][j] = 4;

                        } else {
                            activefield[i][j] = 1;
                        }


                    }


                }
                fieldr = (int) Calcr(scale);

                field[i][j] = new Hex();
                field[i][j].setCoord(new int[]{i,j});
                field[i][j].setActive(activefield[i][j]);
                field[i][j].setCenter(Center(i,j,fieldr));
            }
        }
        peekfield = Peeks(fieldr);
        pathsfield = Paths();
        if ( MainActivity.scenka==-2 && !resume){
            for (int i = field.length - 1; i >= 0; i--) {
                for (int j = field[0].length - 1; j >= 0; j--) {
                    int rockint = 0;
                    for (Hex bor : Neighbor(field[i][j])) {
                        if (bor.getActive() == 2) {
                            rockint += 1;
                        }
                    }
                    if (rockint == 0 && activefield[i][j] == 2) {
                        activefield[i][j] = 1;


                    }

                    if (rockint > random.nextInt(rockint + 3)) {
                        activefield[field[i][j].getCoord()[0]][field[i][j].getCoord()[1]] = 2;

                    }


                    field[i][j].setActive(activefield[i][j]);


                }
            }
            for (int i = field.length - 1; i >= 0; i--) {
                for (int j = field[0].length - 1; j >= 0; j--) {
                    int rockint = 0;
                    for (Hex bor : Neighbor(field[i][j])) {
                        if (bor.getActive() == 2) {
                            rockint += 1;
                        }
                    }

                    if (rockint >3) {
                        activefield[i][j] = 2;



                    }



                    field[i][j].setActive(activefield[i][j]);


                }
            }
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[0].length; j++) {
                    int waterint = 0;

                    for (Hex bor : Neighbor(field[i][j])) {
                        if (bor.getActive() == 0) {
                            waterint += 1;
                        }
                    }
                    if (waterint == 0 && activefield[i][j] == 0) {
                        activefield[i][j] = 1;


                    }
                    if (waterint == 2 && random.nextInt(5) == 0) {
                        activefield[field[i][j].getCoord()[0]][field[i][j].getCoord()[1]] = 0;
                    }
                    field[i][j].setActive(activefield[i][j]);
                }
            }
            for (int i = 0; i < 2; i++) {


                int[] coordton = new int[2];
                coordton[0] = random.nextInt(20);
                coordton[1] = random.nextInt(20);
                while (activefield[coordton[0]][coordton[1]] != 1 || activefield[coordton[0]][coordton[1]] > 9) {
                    coordton[0] = random.nextInt(20);
                    coordton[1] = random.nextInt(20);
                }
                activefield[coordton[0]][coordton[1]] = activefield[coordton[0]][coordton[1]] * 10;
                field[coordton[0]][coordton[1]].setActive(activefield[coordton[0]][coordton[1]]);
            }
            for (int i = 0; i < 2; i++) {


                int[] coordton = new int[2];
                coordton[0] = random.nextInt(20);
                coordton[1] = random.nextInt(20);
                while (activefield[coordton[0]][coordton[1]] != 2 || activefield[coordton[0]][coordton[1]] > 9) {
                    coordton[0] = random.nextInt(20);
                    coordton[1] = random.nextInt(20);
                }
                activefield[coordton[0]][coordton[1]] = activefield[coordton[0]][coordton[1]] * 10+1;
                field[coordton[0]][coordton[1]].setActive(activefield[coordton[0]][coordton[1]]);
            }
            for (int i = 0; i < 2; i++) {


                int[] coordton = new int[2];
                coordton[0] = random.nextInt(20);
                coordton[1] = random.nextInt(20);
                while (activefield[coordton[0]][coordton[1]] != 3 || activefield[coordton[0]][coordton[1]] > 9) {
                    coordton[0] = random.nextInt(20);
                    coordton[1] = random.nextInt(20);
                }
                activefield[coordton[0]][coordton[1]] = activefield[coordton[0]][coordton[1]] * 10+2;
                field[coordton[0]][coordton[1]].setActive(activefield[coordton[0]][coordton[1]]);
            }
            for (int i = 0; i < 2; i++) {


                int[] coordton = new int[2];
                coordton[0] = random.nextInt(20);
                coordton[1] = random.nextInt(20);
                while (activefield[coordton[0]][coordton[1]] != 4 || activefield[coordton[0]][coordton[1]] > 9) {
                    coordton[0] = random.nextInt(20);
                    coordton[1] = random.nextInt(20);
                }
                activefield[coordton[0]][coordton[1]] = activefield[coordton[0]][coordton[1]] * 10+3;
                field[coordton[0]][coordton[1]].setActive(activefield[coordton[0]][coordton[1]]);
            }
            for (int i = 0; i < 2; i++) {


                int[] coord = new int[2];
                coord[0] = random.nextInt(20);
                coord[1] = random.nextInt(20);
                while (activefield[coord[0]][coord[1]] > 9||activefield[coord[0]][coord[1]] == 0) {
                    coord[0] = random.nextInt(20);
                    coord[1] = random.nextInt(20);
                }
                activefield[coord[0]][coord[1]] = activefield[coord[0]][coord[1]] * 10+4;
                field[coord[0]][coord[1]].setActive(activefield[coord[0]][coord[1]]);
            }
            for (int i = 0; i < 2; i++) {


                int[] coord = new int[2];
                coord[0] = random.nextInt(20);
                coord[1] = random.nextInt(20);
                while (activefield[coord[0]][coord[1]] > 9||activefield[coord[0]][coord[1]] == 0) {
                    coord[0] = random.nextInt(20);
                    coord[1] = random.nextInt(20);
                }
                activefield[coord[0]][coord[1]] = activefield[coord[0]][coord[1]] * 10+5;
                field[coord[0]][coord[1]].setActive(activefield[coord[0]][coord[1]]);
            }
            for (int i = 0; i < 2; i++) {


                int[] coord = new int[2];
                coord[0] = random.nextInt(20);
                coord[1] = random.nextInt(20);
                while (activefield[coord[0]][coord[1]] > 9||activefield[coord[0]][coord[1]] == 0) {
                    coord[0] = random.nextInt(20);
                    coord[1] = random.nextInt(20);
                }
                activefield[coord[0]][coord[1]] = activefield[coord[0]][coord[1]] * 10+6;
                field[coord[0]][coord[1]].setActive(activefield[coord[0]][coord[1]]);
            }
        }
        ArrayList<Integer> copy = new ArrayList<>();
        if ((MainActivity.scenka==-1 || MainActivity.scenka==-2)&& !resume) {
            for (Unit unit : typeList) {
                int[] coordunit = new int[2];
                coordunit[0] = random.nextInt(20);
                coordunit[1] = random.nextInt(20);
                while (activefield[coordunit[0]][coordunit[1]]==0 ||activefield[coordunit[0]][coordunit[1]]==-1) {
                    coordunit[0] = random.nextInt(20);
                    coordunit[1] = random.nextInt(20);
                }
                copy.add(activefield[coordunit[0]][coordunit[1]]);
                activefield[coordunit[0]][coordunit[1]]=-1;
                field[coordunit[0]][coordunit[1]].setActive(-1);
                unit.setCoord(coordunit);
            }
            for (Unit unit:typeList
            ) {
                activefield[unit.getCoord()[0]][unit.getCoord()[1]]=copy.get(typeList.indexOf(unit));
                field[unit.getCoord()[0]][unit.getCoord()[1]].setActive(copy.get(typeList.indexOf(unit)));


            }
        }
        for (Unit unit :typeList){
            unit.setHex(field[unit.getCoord()[0]][unit.getCoord()[1]]);
        }

        return field;
    }
    /*
        #Заполнение массива, в котором хранятся координаты вершин гексов поля
       */
    public int[][][][] Peeks(int r){
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j <field[0].length ; j++) {

                for (int k = 0; k < 6; k++) {
                    peekfield[i][j][k][0] = (int) (field[i][j].getCenter()[0]+cos(PI*k/3)*r);
                    peekfield[i][j][k][1] = (int) (field[i][j].getCenter()[1]+sin(PI*k/3)*r);
                }


            }

        }
        return peekfield;
    }
    /*
     #Массив шестиугольников поля
    */
    public Path[][] Paths(){
        for (int i = 0; i <field.length ; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if(pathsfield[i][j] == null){
                    pathsfield[i][j] = new Path();
                }
                else{
                    pathsfield[i][j].reset();
                }
                pathsfield[i][j].moveTo(peekfield[i][j][0][0], peekfield[i][j][0][1]);
                for (int k=1; k<6; k++)
                    pathsfield[i][j].lineTo(peekfield[i][j][k][0], peekfield[i][j][k][1]);
            }

        }
        return pathsfield;

    }
    /*
     #Нахождение координат центра гекса по его индексу и радиусу в массиве гексов
    */
    public  int[] Center(int i,int j, int r){


        int [] cent = new int[2];
        if(j==0){
            cent[0]= (int) (r+1.5*i*r);
            if (i%2==0){
                cent[1] = (int) (r* sqrt(3)/2);
            }
            else{
                cent[1] = (int) (r * sqrt(3) );
            }
        }
        else{
            cent[0]= (int) (field[i][0].getCenter()[0]);
            cent[1]= (int)(field[i][0].getCenter()[1]+sqrt(3)*j*r);
        }
        return cent;}
    /*
#Заполнение массива юнитов конкретными значениями
*/
    private  ArrayList<Unit> Generateunit(int robbernumb, int heronumb, int wolfnumb, int devilnumb, int warriornumb, int wizardnumb){
        for (int i = 0; i < robbernumb; i++) {
            typeList.set(i,new Robber(typeList.get(i),35,5,20,1,60,40,6));

        }


        for (int i =robbernumb; i < heronumb+robbernumb; i++) {
            typeList.set(i,new Hero(typeList.get(i), 5));
        }
        for (int i = heronumb+robbernumb; i < heronumb+wolfnumb+robbernumb; i++) {
            typeList.set(i,new Wolf(typeList.get(i),30,4,25,1,50,60,4 ));
        }
        for (int i = heronumb+wolfnumb+robbernumb; i < heronumb+wolfnumb+devilnumb+robbernumb; i++) {
            typeList.set(i,new Devil(typeList.get(i),20,2,12,1,10,40,30,3));

        }

        for (int i = heronumb+wolfnumb+devilnumb+robbernumb; i < warriornumb+heronumb+ wolfnumb+devilnumb+robbernumb; i++) {
            typeList.set(i,new Warrior(typeList.get(i),40,10,15,1,70,50,2));
        }
        for (int i = warriornumb+heronumb+wolfnumb+devilnumb+robbernumb; i < warriornumb+heronumb+wizardnumb+ wolfnumb+devilnumb+robbernumb ; i++) {
            typeList.set(i,new Wizard(typeList.get(i),25,3,30,1,30,70,1));

        }

        return typeList;
    }
    /*
    #Создание массива прямоугольников картинок юнитов для растяжения
   */
    private ArrayList<Rect> Generaterect1(ArrayList<Unit> typeList){
        for (Unit type : typeList) {
            if(type instanceof Robber){
                rect1List.add(typeList.indexOf(type), new Rect(0, 0, robber1.getWidth(), robber1.getHeight()));

            }

            else if (type instanceof Hero) {
                rect1List.add(typeList.indexOf(type), new Rect(0, 0, hero1.getWidth(), hero1.getHeight()));
            }
            else if(type instanceof Devil){
                rect1List.add(typeList.indexOf(type), new Rect(0, 0, devil1.getWidth(), devil1.getHeight()));

            }
            else if(type instanceof Wolf){
                rect1List.add(typeList.indexOf(type), new Rect(0, 0, wolf1.getWidth(), wolf1.getHeight()));
            }
            else if (type instanceof Warrior) {
                rect1List.add(typeList.indexOf(type), new Rect(0, 0, warrior1.getWidth(), warrior1.getHeight()));
            } else if (type instanceof Wizard) {
                rect1List.add(typeList.indexOf(type), new Rect(0, 0, wizard1.getWidth(), wizard1.getHeight()));
            }
        }
        return rect1List;
    }
    /*
     #Создание массива прямоугольников для растяжения
    */
    private ArrayList<Rect> Generaterect2(int numb){
        for (int i = 0; i < numb; i++) {
            Rect rect = new Rect();
            rect2List.add(i,rect);
        }
        return rect2List;
    }

    /*
      #Создание массива чисел отвечающих за анимацию(3,7 места свободны)
     */
    private ArrayList<int []> Generatedlist(int numb){
        for (int i = 0; i < numb; i++) {
            int [] a =new int[9];
            a[2]=1;

            a[6]=1;
            a[8]=0;
            dList.add(i,a);
        }
        return dList;
    }
    /*
    #Создание массива гексов передвижения для каждого юнита
   */
    private ArrayList<ArrayList<Hex>> Generatemov(int numb){
        for (int i = 0; i < numb; i++) {
            ArrayList<Hex> arr = new ArrayList<>();
            if(typeList.get(i) instanceof Robber){
                for (int j = 0; j < 3; j++) {
                    arr.add(typeList.get(i).getHex());

                }
            }
            else if(typeList.get(i) instanceof Hero){
                for (int j = 0; j < 3; j++) {
                    arr.add(typeList.get(i).getHex());

                }
            }
            else if(typeList.get(i) instanceof Wolf){
                for (int j = 0; j < 4; j++) {
                    arr.add(typeList.get(i).getHex());

                }

            }
            else if(typeList.get(i) instanceof Devil){
                for (int j = 0; j < 3; j++) {
                    arr.add(typeList.get(i).getHex());

                }

            }
            else if(typeList.get(i) instanceof Warrior){
                for (int j = 0; j < 2; j++) {
                    arr.add(typeList.get(i).getHex());

                }

            }
            else if(typeList.get(i) instanceof Wizard){
                for (int j = 0; j < 2; j++) {
                    arr.add(typeList.get(i).getHex());

                }

            }
            movarray.add(i,arr);
        }
        return movarray;
    }
    /*
     #Создание массива юнитов, с которыми будут взимодействовать юниты
    */
    private ArrayList<Unit> Generatehitlist(int numb){
        for (int i = 0; i < numb; i++) {


            hitList.add(null);
        }
        return hitList;


    }
    /*
        #Создание массива флагов перемещения
       */
    private ArrayList<ArrayList<Integer>> Generateflaglist1(int numb){
        for (int i = 0; i < numb; i++) {
            ArrayList<Integer> arr = new ArrayList<>();
            if(typeList.get(i) instanceof Robber){
                for (int j = 0; j < 2; j++) {
                    arr.add(0);

                }
            }
            else if(typeList.get(i) instanceof Hero){
                for (int j = 0; j < 2; j++) {
                    arr.add(0);

                }
            }
            else if(typeList.get(i) instanceof Wolf){
                for (int j = 0; j < 3; j++) {
                    arr.add(0);

                }

            }
            else if(typeList.get(i) instanceof Devil){
                for (int j = 0; j < 2; j++) {
                    arr.add(0);

                }

            }
            else if(typeList.get(i) instanceof Warrior){
                for (int j = 0; j < 1; j++) {
                    arr.add(0);

                }

            }
            else if(typeList.get(i) instanceof Wizard){
                for (int j = 0; j < 1; j++) {
                    arr.add(0);

                }

            }
            flaglist1.add(i,arr);
        }
        return flaglist1;


    }
    /*
     #Создание массива флагов действия
    */
    private ArrayList<ArrayList<Integer>> Generateflaglist2(int numb){
        for (int i = 0; i < numb; i++) {
            ArrayList<Integer> arr = new ArrayList<>();
            if(typeList.get(i) instanceof Robber){
                for (int j = 0; j < 3; j++) {
                    arr.add(0);

                }
            }
            else if(typeList.get(i) instanceof Hero){
                for (int j = 0; j < 2; j++) {
                    arr.add(0);

                }
            }
            else if(typeList.get(i) instanceof Wolf){
                for (int j = 0; j < 3; j++) {
                    arr.add(0);

                }

            }
            else if(typeList.get(i) instanceof Devil){
                for (int j = 0; j < 2; j++) {
                    arr.add(0);

                }

            }
            else if(typeList.get(i) instanceof Warrior){
                for (int j = 0; j < 1; j++) {
                    arr.add(0);

                }

            }
            else if(typeList.get(i) instanceof Wizard){
                for (int j = 0; j < 1; j++) {
                    arr.add(0);

                }

            }
            flaglist2.add(i,arr);
        }
        return flaglist2;


    }
    /*
    #Нахождение здоровья юнита
   */
    public Integer Gethealth(Unit unit){
        int health = 0;
        if (unit instanceof Wizard) {
            health = ((Wizard) unit).getHealth();

        }
        else if (unit instanceof Hero) {
            health = hero_top.getWarheal();

        }
        else if (unit instanceof Warrior) {
            health = ((Warrior) unit).getHealth();

        } else if (unit instanceof Wolf) {
            health = ((Wolf) unit).getHealth();

        } else if (unit instanceof Devil) {
            health = ((Devil) unit).getHealth();

        } else if (unit instanceof Robber) {
            health = ((Robber) unit).getHealth();

        }
        return health;

    }
    /*
     #Нахождение значения брони
    */
    public Integer Getarmor(Unit unit){
        int armor = 0;
        if (unit instanceof Wizard) {
            armor = ((Wizard) unit).getArmor();

        }
        else if (unit instanceof Hero) {
            armor = hero_top.getArmor();

        }
        else if (unit instanceof Warrior) {
            armor = ((Warrior) unit).getArmor();

        } else if (unit instanceof Wolf) {
            armor = ((Wolf) unit).getArmor();

        } else if (unit instanceof Devil) {
            armor = ((Devil) unit).getArmor();

        } else if (unit instanceof Robber) {
            armor = ((Robber) unit).getArmor();

        }
        return armor;

    }
    /*
         #Нахождение значения атаки монстра
        */
    public Integer Getdamver(Unit unit){
        int health = 0;
        if (unit instanceof Wizard) {
            health = ((Wizard) unit).getDamver();

        }
        else if (unit instanceof Hero) {
            health = hero_top.getDamver();

        }
        else if (unit instanceof Warrior) {
            health = ((Warrior) unit).getDamver();

        } else if (unit instanceof Wolf) {
            health = ((Wolf) unit).getDamver();

        } else if (unit instanceof Devil) {
            health = ((Devil) unit).getDamver();

        } else if (unit instanceof Robber) {
            health = ((Robber) unit).getDamver();

        }
        return health;

    }
    /*
     #Нахождение значения защиты монстра
    */
    public Integer Getarmver(Unit unit){
        int armver = 0;
        if (unit instanceof Wizard) {
            armver = ((Wizard) unit).getArmver();

        }
        else if (unit instanceof Hero) {
            armver = hero_top.getArmver();

        }
        else if (unit instanceof Warrior) {
            armver = ((Warrior) unit).getArmver();

        } else if (unit instanceof Wolf) {
            armver = ((Wolf) unit).getArmver();

        } else if (unit instanceof Devil) {
            armver = ((Devil) unit).getArmver();

        } else if (unit instanceof Robber) {
            armver = ((Robber) unit).getArmver();

        }
        return armver;

    }
    /*
     #Штраф за передвиэение на определённом типе местности
    */
    public void Landscape(Unit unit, int land ){
        if(land == 4){
            if(Gethealth(unit)>10) {
                if (unit instanceof Wizard) {
                    ((Wizard) unit).setDamver(((Wizard) unit).getDamver()-1);

                }
                else if (unit instanceof Hero) {
                    hero_top.setDamver(hero_top.getDamver()-1);

                }else if (unit instanceof Warrior) {
                    ((Warrior) unit).setDamver(((Warrior) unit).getDamver()-1);

                } else if (unit instanceof Wolf) {
                    ((Wolf) unit).setDamver(((Wolf) unit).getDamver()-1);

                } else if (unit instanceof Devil) {
                    ((Devil) unit).setDamver(((Devil) unit).getDamver()-1);

                } else if (unit instanceof Robber) {
                    ((Robber) unit).setDamver(((Robber) unit).getDamver()-1);

                }
            }


        }
        else if (land == 3){
            if(Getarmver(unit)>10 & Getdamver(unit)>10){
                if (unit instanceof Wizard) {
                    ((Wizard) unit).setArmver(((Wizard) unit).getArmver()-1);
                }
                else if (unit instanceof Hero) {
                    hero_top.setArmver(hero_top.getArmver()-1);
                }else if (unit instanceof Warrior) {
                    ((Warrior) unit).setArmver(((Warrior) unit).getArmver()-1);


                } else if (unit instanceof Wolf) {
                    ((Wolf) unit).setArmver(((Wolf) unit).getArmver()-1);

                } else if (unit instanceof Devil) {
                    ((Devil) unit).setArmver(((Devil) unit).getArmver()-1);

                } else if (unit instanceof Robber) {
                    ((Robber) unit).setArmver(((Robber) unit).getArmver()-1);


                }

            }

        }


    }
    /*
     #Определение соседства гексов
    */
    public boolean Isneighbor(Hex chosen, Hex hex){
        if (fieldr*2>sqrt(pow(chosen.getCenter()[1]-hex.getCenter()[1],2) + pow(chosen.getCenter()[0]-hex.getCenter()[0],2))){
            return true;
        }
        else
            return false;
    }
    /*
     #Нахождение массива соседей гекса
    */
    public ArrayList<Hex> Neighbor(Hex hex){
        ArrayList<Hex> neighborList = new ArrayList<>();
        for (int i = hex.getCoord()[0]-1; i <= hex.getCoord()[0]+1; i++) {
            for (int j = hex.getCoord()[1]-1; j <= hex.getCoord()[1]+1; j++) {
                if (j>=0&&i>=0&&i<20&&j<20&&Isneighbor(hex,field[i][j])&&field[i][j]!=hex){
                    neighborList.add(field[i][j]);
                }
            }
        }
        return neighborList;
    }
    /*
     #Нахождение относительного расположения 2 соседних гексов
    */
    public int Direction(Hex neighbor, Hex hex){
        int a = 0;
        switch(hex.getCoord()[0]%2) {
            case 1:
                if ((neighbor.getCoord()[0] - hex.getCoord()[0] == 1) && (neighbor.getCoord()[1] - hex.getCoord()[1] == 1)) {
                    a=1;
                }
                else if ((neighbor.getCoord()[0] - hex.getCoord()[0] == 0) && (neighbor.getCoord()[1] - hex.getCoord()[1] == 1)) {
                    a=2;
                }
                else if ((neighbor.getCoord()[0] - hex.getCoord()[0] == -1) && (neighbor.getCoord()[1] - hex.getCoord()[1] == 1)) {
                    a=3;
                }
                else if ((neighbor.getCoord()[0] - hex.getCoord()[0] == -1) && (neighbor.getCoord()[1] - hex.getCoord()[1] == 0)) {
                    a=4;
                }
                else if ((neighbor.getCoord()[0] - hex.getCoord()[0] == 0) && (neighbor.getCoord()[1] - hex.getCoord()[1] == -1)) {
                    a=5;
                }
                else
                    a=6;
                break;

            case 0:
                if ((neighbor.getCoord()[0] - hex.getCoord()[0] == 1) && (neighbor.getCoord()[1] - hex.getCoord()[1] == 0)) {
                    a=1;
                }
                else if ((neighbor.getCoord()[0] - hex.getCoord()[0] == 0) && (neighbor.getCoord()[1] - hex.getCoord()[1] == 1)) {
                    a= 2;
                }
                else if ((neighbor.getCoord()[0] - hex.getCoord()[0] == -1) && (neighbor.getCoord()[1] - hex.getCoord()[1] == 0)) {
                    a = 3;
                }
                else if ((neighbor.getCoord()[0] - hex.getCoord()[0] == -1) && (neighbor.getCoord()[1] - hex.getCoord()[1] == -1)) {
                    a = 4;
                }
                else if ((neighbor.getCoord()[0] - hex.getCoord()[0] == 0) && (neighbor.getCoord()[1] - hex.getCoord()[1] == -1)) {
                    a =  5;
                }
                else
                    a = 6;
                break;

        }
        return a;
    }
    /*
     #Определение вхождения гекса в путь состоящий из гексов
    */
    public boolean Cross(ArrayList<Hex> arr,Hex h){
        boolean f =true;
        for (int i = 0; i < arr.size(); i++) {
            if(arr.get(i)==h){
                f = false;
            }

        }
        return f;
    }
    /*
     #Определение смещения с учётом выхода за границу
    */
    public int[] Checkborder(int a,int b){
        int [] cd =new int[2];
        cd[0]=a;
        cd[1]=b;
        if (peekfield[0][0][3][0]- a * 3>0){
            cd[0]=peekfield[0][0][3][0]/3;

        }
        if(peekfield[0][0][4][1]- b * 3>0){
            cd[1]=peekfield[0][0][4][1]/3;

        }
        if(peekfield[19][19][1][1]- b * 3<getHeight()){
            cd[1]=(peekfield[19][19][1][1]-getHeight())/3;

        }
        if(peekfield[19][19][0][0]- a * 3<getWidth()){
            cd[0]=(peekfield[19][19][0][0]-getWidth())/3;

        }
        return cd;

    }
    /*
         #Разворот массива
        */
    public ArrayList<Hex> Reverse(ArrayList<Hex> path0){
        ArrayList<Hex> path=new ArrayList<>();
        for (int i = path0.size()-1; i >-1; i--) {
            path.add(path0.get(i));


        }
        return path;
    }
    /*
         #Определение наносил или не наносил удар герой
        */
    public int isHit(ArrayList<ArrayList<Integer>> arr){
        int a =0;
        for (int i = 0; i < arr.get(typeList.indexOf(MainActivity.Findhero(typeList))).size(); i++) {
            if(arr.get(typeList.indexOf(MainActivity.Findhero(typeList))).get(i)==1){
                a=1;
                break;
            }
        }
        return a;
    }
    /*
     #Определение типа местности гекса
    */
    public int Activ0(Integer active){
        int activ=active;
        if(active>9){
            activ = (int)active/10;
        }

        return activ;
    }
    /*
     #Определение типа объекта гекса
    */
    public int Activ1(Integer active){
        int activ=-1;
        if(active>9){
            activ = (int)active%10;
        }

        return activ;
    }
    /*
     #Нахождение последнего не пустого элемента массива
    */
    public int Lasthex(ArrayList<Hex> arr){
        int k=-1;
        for (int i = arr.size()-1; i >=0 ; i--) {
            if(arr.get(i)!=null){
                k=i;
                break;
            }


        }
        return k;
    }



    /*
             #Нахождение пути для хода для типа монстра wizard
        */
    public ArrayList<Hex> getPath2(Hex begin, Hex end, int mov, int index) {
        ArrayList<Hex> path=new ArrayList<>();
        Queue<Hex> queue=new LinkedList<>();
        HashMap<Hex,Integer> hash = new HashMap<>();
        ArrayList<Hex> parts = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                hash.put(field[i][j],-1);
            }
        }
        hash.put(begin,0);
        queue.add(begin);
        while (!queue.isEmpty())
        {
            Hex hex=queue.poll();

            int l=hash.get(hex);
            ArrayList<Hex> neighbor=Neighbor(hex);
            for(Hex bor:neighbor)
            {
                if ( Activ0(bor.getActive())!=0&& hash.get(bor)==-1) {
                    if(mov>=l+1){
                        if(MainActivity.Findunit(bor,typeList,movarray,index)==null|| MainActivity.Findunit(bor,typeList,movarray,index)==pool_unit){
                            hash.put(bor, l + 1);
                            queue.add(bor);

                        }

                    }
                    else{
                        hash.put(bor, l + 1);
                        queue.add(bor);

                    }


                }
            }


        }


        Hex part=end;

        path.add(end);
        while (part!=begin) {
            if(hash.get(end)==-1){
                break;

            }
            for (Hex bor : Neighbor(part)) {
                if (Activ0(bor.getActive()) !=0 && hash.get(part) == hash.get(bor) + 1) {
                    parts.add(bor);
                }
            }
            if(parts.size()!=0){
                int a =random.nextInt(parts.size());
                path.add(parts.get(a));
                part = parts.get(a);}
            parts.clear();
        }




        queue.clear();
        hash.clear();
        path=Reverse(path);
        return path;
    }
    /*
         #Нахождение пути отступления для типа монстра devil
    */
    public ArrayList<Hex> getPath5(Hex begin1, Hex begin2, int index) {
        ArrayList<Hex> path=new ArrayList<>();
        Queue<Hex> queue=new LinkedList<>();
        HashMap<Hex,Integer> hash = new HashMap<>();
        HashMap<Hex,Integer> hash2 = new HashMap<>();
        ArrayList<Hex> parts = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                hash.put(field[i][j],-1);
            }
        }
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                hash2.put(field[i][j],-1);
            }
        }
        hash.put(begin1,0);
        queue.add(begin1);

        while (!queue.isEmpty())
        {
            Hex hex=queue.poll();

            int l=hash.get(hex);
            ArrayList<Hex> neighbor=Neighbor(hex);
            for(Hex bor:neighbor)
            {

                if (  hash.get(bor)==-1) {

                    if(MainActivity.Findunit(bor,typeList,movarray,index)==null|| MainActivity.Findunit(bor,typeList,movarray,index)==pool_unit){
                        hash.put(bor, l + 1);
                        queue.add(bor);
                    }




                }
            }

        }
        hash2.put(begin2,0);
        queue.add(begin2);
        while (!queue.isEmpty())
        {
            Hex hex=queue.poll();

            int l=hash2.get(hex);
            ArrayList<Hex> neighbor=Neighbor(hex);
            for(Hex bor:neighbor)
            {
                if (  hash2.get(bor)==-1) {

                    hash2.put(bor, l + 1);
                    queue.add(bor);

                }
            }
        }
        for (Map.Entry<Hex,Integer> u:hash.entrySet()) {
            if(u.getValue()!=-1) {
                u.setValue(hash2.get(u.getKey()) + hash.get(u.getKey()));
            }

        }
        Hex part=begin1;
        path.add(begin1);
        int k=1;

        while (k==1) {
            for (Hex bor : Neighbor(part))  {
                if (hash.get(part)+2==hash.get(bor) ) {

                    parts.add(bor);
                }
            }
            if(parts.size()!=0){
                int a =random.nextInt(parts.size());
                path.add(parts.get(a));
                part = parts.get(a);}
            else{
                for (Hex bor1 : Neighbor(part)) {
                    if ( hash.get(part) + 1 == hash.get(bor1)) {

                        parts.add(bor1);
                    }
                }
                if(parts.size()!=0){
                    int a =random.nextInt(parts.size());
                    path.add(parts.get(a));
                    part = parts.get(a);}
                else{
                    k=0;
                }
            }
            parts.clear();;
        }
        queue.clear();
        hash.clear();
        return path;
    }
    /*
             #Нахождение пути отступления для типов монстров wolf, robber
        */
    public ArrayList<Hex> getPath3(Hex begin1, Hex begin2, int index) {
        ArrayList<Hex> path=new ArrayList<>();
        Queue<Hex> queue=new LinkedList<>();
        HashMap<Hex,Integer> hash = new HashMap<>();
        HashMap<Hex,Integer> hash2 = new HashMap<>();
        ArrayList<Hex> parts = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                hash.put(field[i][j],-1);
            }
        }
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                hash2.put(field[i][j],-1);
            }
        }
        hash.put(begin1,0);
        queue.add(begin1);

        while (!queue.isEmpty())
        {
            Hex hex=queue.poll();

            int l=hash.get(hex);
            ArrayList<Hex> neighbor=Neighbor(hex);
            for(Hex bor:neighbor)
            {

                if ( Activ0(bor.getActive())!=0&& hash.get(bor)==-1) {

                    if(MainActivity.Findunit(bor,typeList,movarray,index)==null|| MainActivity.Findunit(bor,typeList,movarray,index)==pool_unit){
                        hash.put(bor, l + 1);
                        queue.add(bor);
                    }
                }
            }

        }
        hash2.put(begin2,0);
        queue.add(begin2);
        while (!queue.isEmpty())
        {
            Hex hex=queue.poll();

            int l=hash2.get(hex);
            ArrayList<Hex> neighbor=Neighbor(hex);
            for(Hex bor:neighbor)
            {
                if (Activ0( bor.getActive())!=0&& hash2.get(bor)==-1) {

                    hash2.put(bor, l + 1);
                    queue.add(bor);
                }
            }
        }
        for (Map.Entry<Hex,Integer> u:hash.entrySet()) {
            if(u.getValue()!=-1) {
                u.setValue(hash2.get(u.getKey()) + hash.get(u.getKey()));
            }

        }
        Hex part=begin1;
        path.add(begin1);
        int k=1;

        while (k==1) {
            for (Hex bor : Neighbor(part))  {
                if (Activ0(bor.getActive()) !=0 && hash.get(part)+2==hash.get(bor)) {

                    parts.add(bor);
                }
            }
            if(parts.size()!=0){
                int a =random.nextInt(parts.size());
                path.add(parts.get(a));
                part = parts.get(a);}
            else{
                for (Hex bor1 : Neighbor(part)) {
                    if (Activ0(bor1.getActive()) != 0 && hash.get(part) + 1 == hash.get(bor1)) {

                        parts.add(bor1);
                    }
                }
                if(parts.size()!=0){
                    int a =random.nextInt(parts.size());
                    path.add(parts.get(a));
                    part = parts.get(a);}
                else{
                    k=0;
                }
            }
            parts.clear();;
        }
        queue.clear();
        hash.clear();
        return path;
    }
    /*
           #Нахождение пути для хода для типов монстров devil
      */
    public ArrayList<Hex> getPath4(Hex begin, Hex end, int mov, int index){
        ArrayList<Hex> path=new ArrayList<>();
        Queue<Hex> queue=new LinkedList<>();
        HashMap<Hex,Integer> hash = new HashMap<>();
        ArrayList<Hex> parts = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                hash.put(field[i][j],-1);
            }
        }
        hash.put(begin,0);
        queue.add(begin);
        while (!queue.isEmpty())
        {
            Hex hex=queue.poll();

            int l=hash.get(hex);
            ArrayList<Hex> neighbor=Neighbor(hex);
            for(Hex bor:neighbor)
            {
                if (  hash.get(bor)==-1) {
                    if(mov>=l+1){
                        if(MainActivity.Findunit(bor,typeList,movarray,index)==null|| MainActivity.Findunit(bor,typeList,movarray,index)==pool_unit||bor==end){
                            hash.put(bor, l + 1);
                            queue.add(bor);

                        }

                    }
                    else{
                        hash.put(bor, l + 1);
                        queue.add(bor);

                    }


                }
            }
        }
        Hex part=end;
        path.add(end);
        while (part!=begin) {
            if(hash.get(end)==-1){
                break;

            }
            for (Hex bor : Neighbor(part)) {
                if ( hash.get(part) == hash.get(bor) + 1) {
                    parts.add(bor);
                }
            }
            if(parts.size()!=0){
                int a =random.nextInt(parts.size());
                path.add(parts.get(a));
                part = parts.get(a);}
            parts.clear();
        }
        queue.clear();
        hash.clear();
        path=Reverse(path);
        return path;

    }
    /*
           #Нахождение пути для хода для типов монстров wolf, robber, warrior
      */
    public ArrayList<Hex> getPath(Hex begin, Hex end, int mov, int index) {
        int weight=0;
        ArrayList<Hex> path=new ArrayList<>();
        Queue<Hex> queue=new LinkedList<>();
        HashMap<Hex,Integer> hash = new HashMap<>();
        ArrayList<Hex> parts = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                hash.put(field[i][j],-1);
            }
        }
        hash.put(begin,0);
        queue.add(begin);
        while (!queue.isEmpty())
        {
            Hex hex=queue.poll();

            int l=hash.get(hex);
            ArrayList<Hex> neighbor=Neighbor(hex);
            if(Activ0(hex.getActive())==1||Activ0(hex.getActive())==3||Activ0(hex.getActive())==4){
                weight=1;
            }
            else if(Activ0(hex.getActive())==2){
                weight=2;
            }
            for(Hex bor:neighbor)
            {


                if ( Activ0(bor.getActive())!=0&& hash.get(bor)==-1) {
                    if(mov>=l+1){
                        if(MainActivity.Findunit(bor,typeList,movarray,index)==null|| MainActivity.Findunit(bor,typeList,movarray,index)== MainActivity.Findhero(typeList)|| MainActivity.Findunit(bor,typeList,movarray,index)==pool_unit){
                            hash.put(bor,l+weight);
                            queue.add(bor);
                        }
                    }
                    else{
                        hash.put(bor,weight+l);
                        queue.add(bor);

                    }
                }
            }
        }
        Hex part=end;
        path.add(end);
        while (part!=begin) {
            if(hash.get(end)==-1){
                break;

            }

            for (Hex bor : Neighbor(part)) {
                if(Activ0(bor.getActive())==1||Activ0(bor.getActive())==3||Activ0(bor.getActive())==4){
                    weight=1;
                }
                else if(Activ0(bor.getActive())==2){
                    weight=2;
                }

                if (Activ0(bor.getActive()) !=0 && hash.get(part) == hash.get(bor) + weight) {
                    parts.add(bor);
                }
            }
            if(parts.size()!=0){
                int a =random.nextInt(parts.size());
                path.add(parts.get(a));
                part = parts.get(a);}
            parts.clear();
        }
        queue.clear();
        hash.clear();
        path=Reverse(path);
        return path;
    }




    /*
               #Анимация передвижения и действий юнитов
         */
    public void Animation(ArrayList<ArrayList<Hex>> arrhex, ArrayList<ArrayList<Integer>> arrflag1, ArrayList<ArrayList<Integer>> arrflag2, Canvas canvas) {
        if (animflag == 1) {
            int k = 1;
            for (Unit type : typeList) {
                int index = typeList.indexOf(type);

                if (type != pool_unit) {

                    for (int i = 0; i < arrhex.get(index).size() - 1; i++) {
                        int flag1 = arrflag1.get(index).get(i);//движение
                        int flag2 = arrflag2.get(index).get(i);//удары

                        if (flag1 != 0 && anime == null || flag1 != 0 && anime == type) {
                            if (anime == null) {
                                anime = type;
                            }
                            int ind1 = i;
                            k = 0;
                            if(flag1==1){
                                if (dList.get(index)[2] == 1) {
                                    dList.get(index)[2] = 9;
                                    dList.get(index)[1] = ((arrhex.get(index).get(ind1 + 1)).getCenter()[1] - (arrhex.get(index).get(ind1)).getCenter()[1]) / 8;
                                    dList.get(index)[0] = ((arrhex.get(index).get(ind1 + 1)).getCenter()[0] - (arrhex.get(index).get(ind1)).getCenter()[0]) / 8;

                                }


                                rect2List.set(index, new Rect(rect2List.get(index).left + (10 - dList.get(index)[2]) * dList.get(index)[0], rect2List.get(index).top + (10 - dList.get(index)[2]) * dList.get(index)[1],
                                        rect2List.get(index).right + (10 - dList.get(index)[2]) * dList.get(index)[0], rect2List.get(index).bottom + (10 - dList.get(index)[2]) * dList.get(index)[1]));
                                dList.get(typeList.indexOf(type))[2] -= 1;

                                if (dList.get(typeList.indexOf(type))[2] == 1) {
                                    Landscape(type,Activ0(arrhex.get(index).get(ind1 + 1).getActive()));
                                    rect2List.set(index, new Rect(peekfield[arrhex.get(index).get(ind1 + 1).getCoord()[0]][arrhex.get(index).get(ind1 + 1).getCoord()[1]][4][0],
                                            peekfield[arrhex.get(index).get(ind1 + 1).getCoord()[0]][arrhex.get(index).get(ind1 + 1).getCoord()[1]][4][1],
                                            peekfield[arrhex.get(index).get(ind1 + 1).getCoord()[0]][arrhex.get(index).get(ind1 + 1).getCoord()[1]][1][0],
                                            peekfield[arrhex.get(index).get(ind1 + 1).getCoord()[0]][arrhex.get(index).get(ind1 + 1).getCoord()[1]][1][1]));
                                    type.setHex(arrhex.get(index).get(ind1 + 1));
                                    int[] coord = new int[2];
                                    coord[0] = arrhex.get(index).get(ind1 + 1).getCoord()[0];
                                    coord[1] = arrhex.get(index).get(ind1 + 1).getCoord()[1];
                                    type.setCoord(coord);
                                    arrflag1.get(index).set(i, 0);
                                    anime = null;

                                }}
                            else  if(flag1 ==2){
                                if (animflag2 == 0) {
                                    animflag2 = 1;
                                }
                                else if(animflag2==1){
                                    animflag2 = 0;
                                    if (dList.get(index)[2] == 1) {
                                        dList.get(index)[2] = 11;
                                        dList.get(index)[1] = fieldr / 10;
                                        dList.get(index)[0] = fieldr / 10;

                                    }

                                    canvas.drawBitmap(star, new Rect(0, 0, star.getWidth(), star.getHeight()), new Rect(arrhex.get(index).get(ind1 + 1).getCenter()[0] - dList.get(index)[0] * (12 - dList.get(index)[2]),
                                            arrhex.get(index).get(ind1 + 1).getCenter()[1] - dList.get(index)[1] * (12 - dList.get(index)[2]),
                                            arrhex.get(index).get(ind1 + 1).getCenter()[0] + dList.get(index)[0] * (12 - dList.get(index)[2]),
                                            arrhex.get(index).get(ind1 + 1).getCenter()[1] + dList.get(index)[1] * (12 - dList.get(index)[2])
                                    ), p5);


                                    dList.get(typeList.indexOf(type))[2] -= 1;

                                    if (dList.get(typeList.indexOf(type))[2] == 1) {
                                        rect2List.set(index, new Rect(peekfield[arrhex.get(index).get(ind1 + 1).getCoord()[0]][arrhex.get(index).get(ind1 + 1).getCoord()[1]][4][0],
                                                peekfield[arrhex.get(index).get(ind1 + 1).getCoord()[0]][arrhex.get(index).get(ind1 + 1).getCoord()[1]][4][1],
                                                peekfield[arrhex.get(index).get(ind1 + 1).getCoord()[0]][arrhex.get(index).get(ind1 + 1).getCoord()[1]][1][0],
                                                peekfield[arrhex.get(index).get(ind1 + 1).getCoord()[0]][arrhex.get(index).get(ind1 + 1).getCoord()[1]][1][1]));
                                        type.setHex(arrhex.get(index).get(ind1 + 1));
                                        int[] coord = new int[2];
                                        coord[0] = arrhex.get(index).get(ind1 + 1).getCoord()[0];
                                        coord[1] = arrhex.get(index).get(ind1 + 1).getCoord()[1];
                                        type.setCoord(coord);
                                        arrflag1.get(index).set(i, 0);
                                        anime = null;

                                    }
                                }

                            }


                        }
                        else if (flag2 != 0 && anime == null || flag2 != 0 && anime == type) {
                            if (anime == null) {
                                anime = type;
                            }
                            int ind2 = i;
                            k = 0;
                            Hex her = arrhex.get(typeList.indexOf(MainActivity.Findhero(typeList))).get(2);
                            Hex her2 = MainActivity.Findhero(typeList).getHex();


                            if(type instanceof Robber){
                                if(flag2==1){
                                    if (animflag2 == 0) {
                                        animflag2 = 1;
                                    }
                                    else if(animflag2 == 1) {
                                        animflag2=0;
                                        if (dList.get(index)[6] == 1) {
                                            dList.get(index)[6] = 11;

                                            dList.get(index)[4] = (her2.getCenter()[0] - arrhex.get(index).get(ind2).getCenter()[0]) / 10;
                                            dList.get(index)[5] = (her2.getCenter()[1] - arrhex.get(index).get(ind2).getCenter()[1]) / 10;


                                        }

                                        Matrix matrix = new Matrix();
                                        int direction = Direction(her2, type.getHex()) - 1;
                                        matrix.postRotate(direction * 60 - 150);



                                        matrix.postScale((float) fieldr / knife.getWidth()*2, (float) fieldr / knife.getHeight()*2);
                                        if (direction != 5 && direction != 4)
                                            matrix.postTranslate(peekfield[her2.getCoord()[0]][her2.getCoord()[1]][direction + 2][0] + (6 - dList.get(index)[6]) * dList.get(index)[4],
                                                    peekfield[her2.getCoord()[0]][her2.getCoord()[1]][direction + 2][1] + (6 - dList.get(index)[6]) * dList.get(index)[5]);
                                        else {
                                            matrix.postTranslate(peekfield[her2.getCoord()[0]][her2.getCoord()[1]][direction - 4][0]+ (6 - dList.get(index)[6]) * dList.get(index)[4],
                                                    peekfield[her2.getCoord()[0]][her2.getCoord()[1]][direction - 4][1]+ (6 - dList.get(index)[6]) * dList.get(index)[5]);
                                        }
                                        canvas.drawBitmap(knife, matrix, p5);


                                        dList.get(index)[6] -= 1;


                                        if (dList.get(index)[6] == 1) {
                                            hero_top.setWarheal(hero_top.getWarheal() - (((Robber) type).getDamage()-hero_top.getArmor()));
                                            if (hero_top.getWarheal() <= 0) {
                                                lose = true;
                                            }
                                            arrflag2.get(index).set(i, 0);
                                            anime = null;

                                        }
                                    }}
                                else if (flag2 == 5){
                                    if (animflag2 == 0) {
                                        animflag2 = 1;
                                    }
                                    else if(animflag2 == 1) {
                                        animflag2=0;

                                        if (dList.get(typeList.indexOf(type))[6] == 1) {
                                            dList.get(typeList.indexOf(type))[6] =23;

                                        }

                                        Matrix matrix = new Matrix();

                                        matrix.postScale((float) fieldr / block.getWidth() * 8, (float) fieldr / block.getHeight() * 10);
                                        matrix.postTranslate(her2.getCenter()[0] - fieldr, her2.getCenter()[1] - fieldr);
                                        canvas.drawBitmap(bitmaps2[5 -(int)dList.get(index)[6]/4],matrix,p5);





                                        dList.get(index)[6] -= 1;


                                        if (dList.get(index)[6] == 3) {
                                            dList.get(index)[6] = 1;
                                            arrflag2.get(index).set(i, 0);
                                            anime = null;


                                        }

                                    }

                                }

                            }
                            else if (type instanceof Hero) {
                                if(flag2==1) {
                                    Unit hitunit = hitList.get(index);
                                    if (hitunit instanceof Wizard) {
                                        if (((Wizard) hitunit).getHealth() - (hero_top.getDamage()-((Wizard) hitunit).getArmor()) <= 0) {
                                            hitList.set(index, null);
                                            pool_unit = hitunit;
                                        }
                                    } else if (hitunit instanceof Warrior) {
                                        if (((Warrior) hitunit).getHealth() - (hero_top.getDamage()-((Warrior) hitunit).getArmor()) <= 0) {
                                            hitList.set(index, null);
                                            pool_unit = hitunit;
                                        }
                                    } else if (hitunit instanceof Wolf) {
                                        if (((Wolf) hitunit).getHealth() - (hero_top.getDamage()-((Wolf) hitunit).getArmor()) <= 0) {
                                            hitList.set(index, null);
                                            pool_unit = hitunit;
                                        }
                                    } else if (hitunit instanceof Devil) {
                                        if (((Devil) hitunit).getHealth() - (hero_top.getDamage()-((Devil) hitunit).getArmor()) <= 0) {
                                            hitList.set(index, null);
                                            pool_unit = hitunit;
                                        }
                                    } else if (hitunit instanceof Robber) {
                                        if (((Robber) hitunit).getHealth() - (hero_top.getDamage()-((Robber) hitunit).getArmor()) <= 0) {
                                            pool_unit = hitunit;
                                            hitList.set(index, null);
                                        }
                                    }

                                    if (pool_unit != null) {

                                        if (dList.get(index)[6] == 1) {

                                            dList.get(index)[6] = 18;

                                            dList.get(index)[4] = (pool_unit.getHex().getCenter()[0] - (arrhex.get(index).get(ind2)).getCenter()[0]) / 18;
                                            dList.get(index)[5] = (pool_unit.getHex().getCenter()[1] - (arrhex.get(index).get(ind2)).getCenter()[1]) / 18;

                                        }
                                        if (dList.get(index)[6] > 10) {
                                            rect2List.set(index, new Rect(rect2List.get(index).left + dList.get(index)[4] * (21 - dList.get(index)[6]), rect2List.get(index).top + dList.get(index)[5] * (21 - dList.get(index)[6]),
                                                    rect2List.get(index).right + dList.get(index)[4] * (21 - dList.get(index)[6]), rect2List.get(index).bottom + dList.get(index)[5] * (21 - dList.get(index)[6])));
                                        }
                                        if (dList.get(index)[6] <= 10) {
                                            rect2List.set(index, new Rect(rect2List.get(index).left + dList.get(index)[4] * (dList.get(index)[6] - 2), rect2List.get(index).top + dList.get(index)[5] * (dList.get(index)[6] - 2),
                                                    rect2List.get(index).right + dList.get(index)[4] * (dList.get(index)[6] - 2), rect2List.get(index).bottom + dList.get(index)[5] * (dList.get(index)[6] - 2)));
                                        }
                                        dList.get(index)[6] -= 1;


                                        if (dList.get(typeList.indexOf(type))[6] == 1) {
                                            arrflag2.get(index).set(i, 0);
                                            anime = null;


                                        }


                                    } else if (hitunit != null) {
                                        if (dList.get(index)[6] == 1) {
                                            dList.get(index)[6] = 14;
                                            dList.get(index)[4] = (hitunit.getHex().getCenter()[0] - (arrhex.get(index).get(ind2)).getCenter()[0]) / 10;
                                            dList.get(index)[5] = (hitunit.getHex().getCenter()[1] - (arrhex.get(index).get(ind2)).getCenter()[1]) / 10;
                                        }
                                        if (dList.get(index)[6] > 8) {
                                            rect2List.set(index, new Rect(rect2List.get(index).left + dList.get(index)[4] * (15 - dList.get(index)[6]), rect2List.get(index).top + dList.get(index)[5] * (15 - dList.get(index)[6]),
                                                    rect2List.get(index).right + dList.get(index)[4] * (15 - dList.get(index)[6]), rect2List.get(index).bottom + dList.get(index)[5] * (15 - dList.get(index)[6])));
                                        }
                                        if (dList.get(index)[6] <= 8) {
                                            rect2List.set(index, new Rect(rect2List.get(index).left + dList.get(index)[4] * (dList.get(index)[6] - 2), rect2List.get(index).top + dList.get(index)[5] * (dList.get(index)[6] - 2),
                                                    rect2List.get(index).right + dList.get(index)[4] * (dList.get(index)[6] - 2), rect2List.get(index).bottom + dList.get(index)[5] * (dList.get(index)[6] - 2)));
                                        }
                                        dList.get(index)[6] -= 1;


                                        if (dList.get(typeList.indexOf(type))[6] == 1) {
                                            if (hitunit instanceof Wizard) {
                                                ((Wizard) hitunit).setHealth(((Wizard) hitunit).getHealth() - (hero_top.getDamage()-((Wizard) hitunit).getArmor()));

                                            } else if (hitunit instanceof Warrior) {
                                                ((Warrior) hitunit).setHealth(((Warrior) hitunit).getHealth() - (hero_top.getDamage()-((Warrior) hitunit).getArmor()));

                                            } else if (hitunit instanceof Wolf) {
                                                ((Wolf) hitunit).setHealth(((Wolf) hitunit).getHealth() - (hero_top.getDamage()-((Wolf) hitunit).getArmor()));

                                            } else if (hitunit instanceof Devil) {
                                                ((Devil) hitunit).setHealth(((Devil) hitunit).getHealth() - (hero_top.getDamage()-((Devil) hitunit).getArmor()));

                                            } else if (hitunit instanceof Robber) {
                                                ((Robber) hitunit).setHealth(((Robber) hitunit).getHealth() - (hero_top.getDamage()-((Robber) hitunit).getArmor()));
                                            }

                                            hitList.set(index, null);
                                            arrflag2.get(index).set(i, 0);
                                            anime = null;


                                        }

                                    }
                                }
                                else if(flag2 ==2){

                                    if (animflag2 == 0) {
                                        animflag2 = 1;
                                    }
                                    else if(animflag2==1){
                                        animflag2 = 0;

                                        if (dList.get(typeList.indexOf(type))[6] == 1) {
                                            dList.get(typeList.indexOf(type))[6] =23;

                                        }

                                        Matrix matrix = new Matrix();

                                        matrix.postScale((float) fieldr / boost.getWidth() * 16, (float) fieldr / boost.getHeight() * 20);
                                        matrix.postTranslate(her.getCenter()[0] - 2*fieldr, her.getCenter()[1] - fieldr);
                                        if(dList.get(index)[6]!=2 && dList.get(index)[6]!=3)
                                            canvas.drawBitmap(bitmaps6[5 -(int)dList.get(index)[6]/4],matrix,p5);
                                        else {
                                            canvas.drawBitmap(bitmaps6[4],matrix,p5);
                                        }





                                        dList.get(index)[6] -= 1;


                                        if (dList.get(index)[6] == 1) {
                                            dList.get(index)[6] = 1;
                                            activefield[her.getCoord()[0]][her.getCoord()[1]] = Activ0(activefield[her.getCoord()[0]][her.getCoord()[1]]);
                                            field[her.getCoord()[0]][her.getCoord()[1]].setActive(activefield[her.getCoord()[0]][her.getCoord()[1]]);
                                            if (hero_top.getWarheal() + 30 <= hero_top.getHealth()) {
                                                hero_top.setWarheal(hero_top.getWarheal() + 30);
                                            } else {
                                                hero_top.setWarheal(hero_top.getHealth());

                                            }


                                            arrflag2.get(index).set(i, 0);

                                            anime = null;


                                        }
                                    }

                                }
                                else if(flag2 ==3){
                                    if (animflag2 == 0) {
                                        animflag2 = 1;
                                    }
                                    else if(animflag2==1){
                                        animflag2 = 0;
                                        if (dList.get(typeList.indexOf(type))[6] == 1) {
                                            dList.get(typeList.indexOf(type))[6] =23;

                                        }

                                        Matrix matrix = new Matrix();

                                        matrix.postScale((float) fieldr / boost.getWidth() * 16, (float) fieldr / boost.getHeight() * 20);
                                        matrix.postTranslate(her.getCenter()[0] - 2*fieldr, her.getCenter()[1] - fieldr);
                                        if(dList.get(index)[6]!=2 && dList.get(index)[6]!=3)
                                            canvas.drawBitmap(bitmaps4[5 -(int)dList.get(index)[6]/4],matrix,p5);
                                        else {
                                            canvas.drawBitmap(bitmaps4[4],matrix,p5);
                                        }





                                        dList.get(index)[6] -= 1;


                                        if (dList.get(index)[6] == 1) {
                                            dList.get(index)[6] = 1;
                                            activefield[her.getCoord()[0]][her.getCoord()[1]] = Activ0(activefield[her.getCoord()[0]][her.getCoord()[1]]);
                                            field[her.getCoord()[0]][her.getCoord()[1]].setActive(activefield[her.getCoord()[0]][her.getCoord()[1]]);
                                            if (hero_top.getDamver() + 5 <= hero_top.getMax_damver()) {
                                                hero_top.setDamver(hero_top.getDamver() + 5);
                                            } else {
                                                hero_top.setDamver(hero_top.getMax_damver());

                                            }


                                            arrflag2.get(index).set(i, 0);

                                            anime = null;


                                        }
                                    }

                                }
                                else if(flag2 ==4){
                                    if (animflag2 == 0) {
                                        animflag2 = 1;
                                    }
                                    else if(animflag2==1){
                                        animflag2=0;
                                        if (dList.get(typeList.indexOf(type))[6] == 1) {
                                            dList.get(typeList.indexOf(type))[6] =23;

                                        }

                                        Matrix matrix = new Matrix();
                                        matrix.postScale((float) fieldr / boost.getWidth() * 16, (float) fieldr / boost.getHeight() * 20);
                                        matrix.postTranslate(her.getCenter()[0] - 2*fieldr, her.getCenter()[1] - fieldr);
                                        if(dList.get(index)[6]!=2 && dList.get(index)[6]!=3)
                                            canvas.drawBitmap(bitmaps5[5 -(int)dList.get(index)[6]/4],matrix,p5);
                                        else {
                                            canvas.drawBitmap(bitmaps5[4],matrix,p5);
                                        }





                                        dList.get(index)[6] -= 1;


                                        if (dList.get(index)[6] == 1) {
                                            dList.get(index)[6] = 1;
                                            activefield[her.getCoord()[0]][her.getCoord()[1]] = Activ0(activefield[her.getCoord()[0]][her.getCoord()[1]]);
                                            field[her.getCoord()[0]][her.getCoord()[1]].setActive(activefield[her.getCoord()[0]][her.getCoord()[1]]);
                                            if (hero_top.getArmver() + 5 <= hero_top.getMax_armver()) {
                                                hero_top.setArmver(hero_top.getArmver() + 5);
                                            } else {
                                                hero_top.setArmver(hero_top.getMax_armver());

                                            }


                                            arrflag2.get(index).set(i, 0);

                                            anime = null;


                                        }
                                    }

                                }
                                else if(flag2==5){
                                    if (animflag2 == 0) {
                                        animflag2 = 1;
                                    }
                                    else if(animflag2 == 1) {
                                        animflag2=0;
                                        Unit hitunit = hitList.get(index);
                                        if (dList.get(typeList.indexOf(type))[6] == 1) {
                                            dList.get(typeList.indexOf(type))[6] =23;

                                        }

                                        Matrix matrix = new Matrix();

                                        matrix.postScale((float) fieldr / block.getWidth() * 8, (float) fieldr / block.getHeight() * 10);
                                        matrix.postTranslate(hitunit.getHex().getCenter()[0] - fieldr, hitunit.getHex().getCenter()[1] - fieldr);
                                        canvas.drawBitmap(bitmaps3[5 -(int)dList.get(index)[6]/4],matrix,p5);





                                        dList.get(index)[6] -= 1;


                                        if (dList.get(index)[6] == 3) {
                                            hitList.set(index, null);
                                            dList.get(index)[6] = 1;
                                            arrflag2.get(index).set(i, 0);
                                            anime = null;


                                        }

                                    }
                                }

                            }else if (type instanceof Wolf) {
                                if (flag2 ==1){
                                    if (animflag2 == 0) {
                                        animflag2 = 1;
                                    }
                                    else if(animflag2 == 1) {
                                        animflag2 = 0;

                                        if (dList.get(typeList.indexOf(type))[6] == 1) {
                                            dList.get(typeList.indexOf(type))[6] = 31;
                                    /*
                                    if (Direction(arrhex.get(index).get(ind2), her) == 1 ||
                                            Direction(arrhex.get(index).get(ind2), her) == 4) {
                                        dList.get(index)[4] = (her.Findpeaks((int) fieldr, her.getCenter())[5][0] - (arrhex.get(index).get(ind2)).getCenter()[0]) / 8;
                                        dList.get(index)[5] = (her.Findpeaks((int) fieldr, her.getCenter())[5][1] - (arrhex.get(index).get(ind2)).getCenter()[1]) / 8;
                                    } else if (Direction(arrhex.get(index).get(ind2), her) == 3 || Direction(arrhex.get(index).get(ind2), her) == 6) {
                                        dList.get(index)[4] = (her.Findpeaks((int) fieldr, her.getCenter())[4][0] - (arrhex.get(index).get(ind2)).getCenter()[0]) / 8;
                                        dList.get(index)[5] = (her.Findpeaks((int) fieldr, her.getCenter())[4][1] - (arrhex.get(index).get(ind2)).getCenter()[1]) / 8;
                                    } else {
                                        dList.get(index)[4] = (her.getCenter()[0] - arrhex.get(index).get(ind2).getCenter()[0]) / 8;
                                        dList.get(index)[5] = (her.getCenter()[1] - arrhex.get(index).get(ind2).getCenter()[1]) / 8;
                                    }
*/

                                        }/*
                                if (dList.get(index)[6] > 5) {
                                    rect2List.set(index, new Rect(rect2List.get(index).left + dList.get(index)[4] * (10 - dList.get(index)[6]), rect2List.get(index).top + dList.get(index)[5] * (10 - dList.get(index)[6]),
                                            rect2List.get(index).right + dList.get(index)[4] * (10 - dList.get(index)[6]), rect2List.get(index).bottom + dList.get(index)[5] * (10 - dList.get(index)[6])));
                                }

                                if (dList.get(index)[6] <= 5) {
                                    rect2List.set(index, new Rect(rect2List.get(index).left + dList.get(index)[4] * (dList.get(index)[6] - 1), rect2List.get(index).top + dList.get(index)[5] * (dList.get(index)[6] - 1),
                                            rect2List.get(index).right + dList.get(index)[4] * (dList.get(index)[6] - 1), rect2List.get(index).bottom + dList.get(index)[5] * (dList.get(index)[6] - 1)));
                                }*/
                                        Matrix matrix = new Matrix();

                                        matrix.postScale((float) fieldr / wolfhit.getWidth() * 15, (float) fieldr / wolfhit.getHeight() * 9);
                                        matrix.postTranslate(her.getCenter()[0] - 3 * fieldr / 2, her.getCenter()[1] - 3 * fieldr / 2);
                                        canvas.drawBitmap(bitmaps[15 - dList.get(index)[6] / 2], matrix, p5);
                                        dList.get(index)[6] -= 1;
                                        if (dList.get(index)[6] == 1) {
                                            hero_top.setWarheal(hero_top.getWarheal() - (((Wolf) type).getDamage()-hero_top.getArmor()));
                                            if (hero_top.getWarheal() <= 0) {
                                                lose = true;
                                            }
                                            arrflag2.get(index).set(i, 0);
                                            anime = null;


                                        }
                                    }

                                }
                                else if (flag2 == 5){
                                    if (animflag2 == 0) {
                                        animflag2 = 1;
                                    }
                                    else if(animflag2 == 1) {
                                        animflag2=0;

                                        if (dList.get(typeList.indexOf(type))[6] == 1) {
                                            dList.get(typeList.indexOf(type))[6] =23;

                                        }

                                        Matrix matrix = new Matrix();

                                        matrix.postScale((float) fieldr / block.getWidth() * 8, (float) fieldr / block.getHeight() * 10);
                                        matrix.postTranslate(her.getCenter()[0] - fieldr, her.getCenter()[1] - fieldr);
                                        canvas.drawBitmap(bitmaps2[5 -(int)dList.get(index)[6]/4],matrix,p5);





                                        dList.get(index)[6] -= 1;


                                        if (dList.get(index)[6] == 3) {
                                            dList.get(index)[6] = 1;
                                            arrflag2.get(index).set(i, 0);
                                            anime = null;


                                        }

                                    }

                                }
                            }
                            else if(type instanceof Devil){

                                if(flag2==1) {
                                    if (dList.get(typeList.indexOf(type))[6] == 1) {
                                        dList.get(typeList.indexOf(type))[6] = 11;
                                        dList.get(index)[4] = (her.getCenter()[0] - arrhex.get(index).get(ind2).getCenter()[0]) / 10;
                                        dList.get(index)[5] = (her.getCenter()[1] - arrhex.get(index).get(ind2).getCenter()[1]) / 10;
                                    }
                                    canvas.drawCircle(arrhex.get(index).get(ind2).getCenter()[0] + (12 - dList.get(index)[6]) * dList.get(index)[4], arrhex.get(index).get(ind2).getCenter()[1] + (12 - dList.get(index)[6]) * dList.get(index)[5], fieldr/2, p5);
                                    dList.get(index)[6] -= 1;


                                    if (dList.get(index)[6] == 1) {
                                        hero_top.setWarheal(hero_top.getWarheal() - (((Devil) type).getDamage()-hero_top.getArmor()));
                                        if (hero_top.getWarheal() <= 0) {
                                            lose = true;
                                        }
                                        arrflag2.get(index).set(i, 0);

                                        anime = null;


                                    }


                                }
                                else if(flag2==2) {

                                    if (animflag2 == 0) {
                                        animflag2 = 1;
                                    }
                                    else if (animflag2 == 1) {
                                        animflag2 = 0;


                                        Unit healunit = hitList.get(index);




                                        if (dList.get(typeList.indexOf(type))[6] == 1) {
                                            dList.get(typeList.indexOf(type))[6] =23;

                                        }

                                        Matrix matrix = new Matrix();

                                        matrix.postScale((float) fieldr / boost.getWidth() * 16, (float) fieldr / boost.getHeight() * 20);
                                        matrix.postTranslate(healunit.getHex().getCenter()[0] - 2*fieldr, healunit.getHex().getCenter()[1] - fieldr);
                                        if(dList.get(index)[6]!=2 && dList.get(index)[6]!=3)
                                            canvas.drawBitmap(bitmaps7[5 -(int)dList.get(index)[6]/4],matrix,p5);
                                        else {
                                            canvas.drawBitmap(bitmaps7[4],matrix,p5);
                                        }





                                        dList.get(index)[6] -= 1;


                                        if (dList.get(index)[6] == 1) {
                                            if (healunit instanceof Robber) {
                                                if(((Robber) healunit).getHealth()+ ((Devil) type).getHeal()<=((Robber) healunit).getMax_health()){
                                                    ((Robber) healunit).setHealth(((Robber) healunit).getHealth()+ ((Devil) type).getHeal());}
                                                else{
                                                    ((Robber) healunit).setHealth(((Robber) healunit).getMax_health());

                                                }
                                            }
                                            if (healunit instanceof Warrior) {
                                                if(((Warrior) healunit).getHealth()+ ((Devil) type).getHeal()<=((Warrior) healunit).getMax_health()){
                                                    ((Warrior) healunit).setHealth(((Warrior) healunit).getHealth()+ ((Devil) type).getHeal());}
                                                else{
                                                    ((Warrior) healunit).setHealth(((Warrior) healunit).getMax_health());

                                                }
                                            } else if (healunit instanceof Wolf) {
                                                if(((Wolf) healunit).getHealth()+ ((Devil) type).getHeal()<=((Wolf)healunit).getMax_health()){
                                                    ((Wolf) healunit).setHealth(((Wolf) healunit).getHealth()+ ((Devil) type).getHeal());}
                                                else{
                                                    ((Wolf) healunit).setHealth(((Wolf) healunit).getMax_health());

                                                }
                                            } else if (healunit instanceof Wizard) {
                                                if(((Wizard) healunit).getHealth()+ ((Devil) type).getHeal()<=((Wizard) healunit).getMax_health()){
                                                    ((Wizard) healunit).setHealth(((Wizard) healunit).getHealth()+ ((Devil) type).getHeal());}
                                                else{
                                                    ((Wizard) healunit).setHealth(((Wizard) healunit).getMax_health());

                                                }
                                            } else if (healunit instanceof Devil) {
                                                if(((Devil) healunit).getHealth()+ ((Devil) type).getHeal()<=((Devil) healunit).getMax_health()){
                                                    ((Devil) healunit).setHealth(((Devil) healunit).getHealth()+ ((Devil) type).getHeal());}
                                                else{
                                                    ((Devil) healunit).setHealth(((Devil) healunit).getMax_health());

                                                }
                                            }
                                            hitList.set(index, null);

                                            arrflag2.get(index).set(i, 0);

                                            anime = null;


                                        }


                                    }
                                }
                                else if (flag2 == 5){
                                    if (animflag2 == 0) {
                                        animflag2 = 1;
                                    }
                                    else if(animflag2 == 1) {
                                        animflag2=0;

                                        if (dList.get(typeList.indexOf(type))[6] == 1) {
                                            dList.get(typeList.indexOf(type))[6] =23;

                                        }

                                        Matrix matrix = new Matrix();

                                        matrix.postScale((float) fieldr / block.getWidth() * 8, (float) fieldr / block.getHeight() * 10);
                                        matrix.postTranslate(her.getCenter()[0] - fieldr, her.getCenter()[1] - fieldr);
                                        canvas.drawBitmap(bitmaps2[5 -(int)dList.get(index)[6]/4],matrix,p5);





                                        dList.get(index)[6] -= 1;


                                        if (dList.get(index)[6] == 3) {
                                            dList.get(index)[6] = 1;
                                            arrflag2.get(index).set(i, 0);
                                            anime = null;


                                        }

                                    }

                                }


                            }
                            else if (type instanceof Warrior) {
                                if(flag2==1){

                                    if (dList.get(index)[6] == 1) {
                                        dList.get(index)[6] = 11;
                                        dList.get(index)[4] = (her.getCenter()[0] - arrhex.get(index).get(ind2).getCenter()[0]) / 10;
                                        dList.get(index)[5] = (her.getCenter()[1] - arrhex.get(index).get(ind2).getCenter()[1]) / 10;


                                    }
                                    if (dList.get(index)[6] > 6) {
                                        rect2List.set(index, new Rect(rect2List.get(index).left + dList.get(index)[4] * (12 - dList.get(index)[6]), rect2List.get(index).top + dList.get(index)[5] * (12 - dList.get(index)[6]),
                                                rect2List.get(index).right + dList.get(index)[4] * (12 - dList.get(index)[6]), rect2List.get(index).bottom + dList.get(index)[5] * (12 - dList.get(index)[6])));
                                    }
                                    if (dList.get(index)[6] <= 6) {
                                        rect2List.set(index, new Rect(rect2List.get(index).left + dList.get(index)[4] * (dList.get(index)[6] - 1), rect2List.get(index).top + dList.get(index)[5] * (dList.get(index)[6] - 1),
                                                rect2List.get(index).right + dList.get(index)[4] * (dList.get(index)[6] - 1), rect2List.get(index).bottom + dList.get(index)[5] * (dList.get(index)[6] - 1)));
                                    }
                                    dList.get(index)[6] -= 1;


                                    if (dList.get(index)[6] == 1) {
                                        hero_top.setWarheal(hero_top.getWarheal() - (((Warrior) type).getDamage()-hero_top.getArmor()));
                                        if (hero_top.getWarheal() <= 0) {
                                            lose = true;
                                        }
                                        arrflag2.get(index).set(i, 0);
                                        anime = null;

                                    }}
                                else if (flag2 == 5){
                                    if (animflag2 == 0) {
                                        animflag2 = 1;
                                    }
                                    else if(animflag2 == 1) {
                                        animflag2=0;

                                        if (dList.get(typeList.indexOf(type))[6] == 1) {
                                            dList.get(typeList.indexOf(type))[6] =23;

                                        }

                                        Matrix matrix = new Matrix();

                                        matrix.postScale((float) fieldr / block.getWidth() * 8, (float) fieldr / block.getHeight() * 10);
                                        matrix.postTranslate(her.getCenter()[0] - fieldr, her.getCenter()[1] - fieldr);
                                        canvas.drawBitmap(bitmaps2[5 -(int)dList.get(index)[6]/4],matrix,p5);





                                        dList.get(index)[6] -= 1;


                                        if (dList.get(index)[6] == 3) {
                                            dList.get(index)[6] = 1;
                                            arrflag2.get(index).set(i, 0);
                                            anime = null;


                                        }

                                    }

                                }
                            } else if (type instanceof Wizard) {
                                if(flag2==1){
                                    if (dList.get(index)[6] == 1) {
                                        dList.get(index)[6] = 11;
                                        dList.get(index)[5] = (her.getCenter()[1] - arrhex.get(index).get(ind2).getCenter()[1]) / 10;
                                    }
                                    p5.setStrokeWidth(fieldr/5);

                                    canvas.drawLine((4 * arrhex.get(index).get(ind2).getCenter()[0] + 3 * peekfield[arrhex.get(index).get(ind2).getCoord()[0]][arrhex.get(index).get(ind2).getCoord()[1]][3][0]) / 7,
                                            arrhex.get(index).get(ind2).getCenter()[1],
                                            (3 * peekfield[arrhex.get(index).get(ind2).getCoord()[0]][arrhex.get(index).get(ind2).getCoord()[1]][3][0] + 4 * arrhex.get(index).get(ind2).getCenter()[0]) / 7,
                                            arrhex.get(index).get(ind2).getCenter()[1] + dList.get(index)[5] * (12 - dList.get(index)[6]), p5);
                                    dList.get(index)[6] -= 1;


                                    if (dList.get(index)[6] == 1) {
                                        hero_top.setWarheal(hero_top.getWarheal() - (((Wizard) type).getDamage()-hero_top.getArmor()));
                                        if (hero_top.getWarheal() <= 0) {
                                            lose = true;
                                        }

                                        arrflag2.get(index).set(i, 0);

                                        anime = null;


                                    }}


                                else if (flag2 == 5){
                                    if (animflag2 == 0) {
                                        animflag2 = 1;
                                    }
                                    else if(animflag2 == 1) {
                                        animflag2=0;

                                        if (dList.get(typeList.indexOf(type))[6] == 1) {
                                            dList.get(typeList.indexOf(type))[6] =23;

                                        }

                                        Matrix matrix = new Matrix();

                                        matrix.postScale((float) fieldr / block.getWidth() * 8, (float) fieldr / block.getHeight() * 10);
                                        matrix.postTranslate(her.getCenter()[0] - fieldr, her.getCenter()[1] - fieldr);

                                        canvas.drawBitmap(bitmaps2[5 -(int)dList.get(index)[6]/4],matrix,p5);





                                        dList.get(index)[6] -= 1;


                                        if (dList.get(index)[6] == 3) {
                                            dList.get(index)[6] = 1;
                                            arrflag2.get(index).set(i, 0);
                                            anime = null;


                                        }

                                    }

                                }
                            }

                        }
                        if (k == 0) {
                            break;
                        }
                    }
                }
                if (k == 0) {
                    break;
                }
            }
            if (k == 1 ) {
                if (pool_unit != null) {
                    if(pool_unit instanceof Wizard){
                        hero_top.setArmver( hero_top.getArmver()-1);
                    }
                    else if(pool_unit instanceof Warrior){
                        hero_top.setDamver( hero_top.getDamver()-1);
                    }
                    else if(pool_unit instanceof Robber){
                        hero_top.setDamver( hero_top.getDamver()-1);
                    }
                    else if(pool_unit instanceof Devil){
                        hero_top.setArmver( hero_top.getArmver()-1);
                        hero_top.setDamver( hero_top.getDamver()-1);
                    }
                    else if(pool_unit instanceof Wolf){
                        hero_top.setArmver( hero_top.getArmver()-1);

                    }

                    hero_top.setWarxp(hero_top.getWarxp()+1);

                    rect2List.remove(typeList.indexOf(pool_unit));
                    rect1List.remove(typeList.indexOf(pool_unit));
                    dList.remove(typeList.indexOf(pool_unit));
                    movarray.remove(typeList.indexOf(pool_unit));
                    flaglist1.remove(typeList.indexOf(pool_unit));
                    flaglist2.remove(typeList.indexOf(pool_unit));
                    hitList.remove(typeList.indexOf(pool_unit));
                    typeList.remove(pool_unit);
                    pool_unit=null;
                }
                if (typeList.size() == 1) {
                    if(scenario.getSTRAGE()-1>stage){
                        start =true;
                        stage+=1;
                        rect1List.clear();
                        rect2List.clear();
                        typeList.clear();
                        dList.clear();
                        hitList.clear();
                        movarray.clear();
                        flaglist1.clear();
                        flaglist2.clear();

                    }



                }
                animflag = 0;

            }
        }
    }
    /*
     #Определение типа монстра, который будет добавлен на следующем этапе
    */
    public  int [] Generatestage(){
        int [] arr = new int[6];
        for (int i = 0; i < arr.length; i++) {
            arr[i] =0;

        }
        if(random.nextInt(2)==0){
            if (random.nextInt(2)==0){
                if(random.nextInt(3)!=2){
                    arr[0]=1;
                }
                else{
                    arr[1]=1;
                }
            }
            else{
                if(random.nextInt(3)==0){
                    arr[1]=1;
                }
                else{
                    arr[2]=1;
                }
            }

        }
        else{
            if (random.nextInt(2)==0){
                if(random.nextInt(3)!=2){
                    arr[3]=1;
                }
                else{
                    arr[4]=1;
                }
            }
            else{
                if(random.nextInt(3)==0){
                    arr[4]=1;
                }
                else{
                    arr[5]=1;
                }
            }
        }

        return arr;


    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
    class DrawThread extends Thread {

        private boolean running = false;
        private SurfaceHolder surfaceHolder;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }
        /*
                   #Отрисовка объектов, юнитов, гексов, анимаций в отдельном потоке
               */
        @Override
        public void run() {
            Canvas canvas;
            save_inf = new Save_inf(context);

            while (running) {
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas == null)
                        continue;

                    if (start) {

                        if ((MainActivity.scenka == -1 || MainActivity.scenka == -2)&& !resume) {

                            if (MainActivity.scenka == -1) {
                                activefield = save_inf.getArInt(MainActivity.scenario.getACTIVIRYFIELD());
                            }
                            for (int k = 0; k < stage; k++) {


                                stagearr = Generatestage();
                                for (int i = 0; i < stagearr.length; i++) {
                                    if (stagearr[i] == 1 && i == 0) {
                                        MainActivity.scenario.setROBBER(MainActivity.scenario.getROBBER() + 1);
                                        break;
                                    }
                                    if (stagearr[i] == 1 && i == 1) {
                                        MainActivity.scenario.setDEVIL(MainActivity.scenario.getDEVIL() + 1);
                                        break;
                                    }
                                    if (stagearr[i] == 1 && i == 2) {
                                        MainActivity.scenario.setWOLF(MainActivity.scenario.getWOLF() + 1);
                                        break;
                                    }
                                    if (stagearr[i] == 1 && i == 3) {
                                        MainActivity.scenario.setWARRIOR(MainActivity.scenario.getWARRIOR() + 1);
                                        break;
                                    }
                                    if (stagearr[i] == 1 && i == 4) {
                                        MainActivity.scenario.setWIZARD(MainActivity.scenario.getWIZARD() + 1);
                                        break;
                                    }


                                }
                            }
                            field = Makefield(1 + MainActivity.scenario.sumUnit());
                            stagearr = Generatestage();
                            typeList = Generateunit(MainActivity.scenario.getROBBER(), 1, MainActivity.scenario.getWOLF(), MainActivity.scenario.getDEVIL(), MainActivity.scenario.getWARRIOR(), MainActivity.scenario.getWIZARD());
                            rect1List = Generaterect1(typeList);

                            rect2List = Generaterect2(1 + MainActivity.scenario.sumUnit());

                            movarray = Generatemov(1 + MainActivity.scenario.sumUnit());
                            dList = Generatedlist(1 + MainActivity.scenario.sumUnit());
                            hitList = Generatehitlist(1 + MainActivity.scenario.sumUnit());
                            flaglist1 = Generateflaglist1(1 + MainActivity.scenario.sumUnit());
                            flaglist2 = Generateflaglist2(1 + MainActivity.scenario.sumUnit());


                        } else {

                            activefield = save_inf.getArInt(MainActivity.scenario.getACTIVIRYFIELD());
                            if (MainActivity.scenka==2 )
                                //putListScenario
                                //typeList=save_inf.getListUn(MainActivity.scenario.getTYPELIST()).get(0);
                                typeList = save_inf.getTypeList(MainActivity.scenario.getTYPELIST()).get(stage);
                            else
                                typeList = save_inf.getUnit(MainActivity.scenario.getTYPELIST());
                            field = Makefield(typeList.size());
                            for (Unit unit : typeList) {
                                unit.setHex(field[unit.getCoord()[0]][unit.getCoord()[1]]);

                            }
                            rect1List = Generaterect1(typeList);
                            rect2List = Generaterect2(typeList.size());

                            dList = Generatedlist(typeList.size());
                            hitList = Generatehitlist(typeList.size());
                            movarray = Generatemov(typeList.size());
                            flaglist1 = Generateflaglist1(typeList.size());
                            flaglist2 = Generateflaglist2(typeList.size());
                            resume=false;
                        }

                        start=false;
                    }



                    canvas.drawColor(Color.WHITE);



                    if(Calcr(scale)!=fieldr ) {

                        x12 = (int) (getWidth()/2-field[0][0].getCenter()[0]+fieldr);
                        y12 = (int) (getHeight()/2-field[0][0].getCenter()[1]+fieldr* sqrt(3)/2);
                        if(Calcr(scale)>fieldr){
                            scal=sc;
                            sc+=0.1;
                            fieldr = (int) Calcr(sc);

                        }
                        else if(Calcr(scale)<fieldr){
                            scal=sc;
                            sc-=0.1;
                            fieldr = (int) Calcr(sc);


                        }

                        if(Math.abs(scale-sc)<0.1){
                            scale=sc;
                        }

                        for (int i = 0; i < field.length; i++) {
                            for (int j = 0; j < field[0].length; j++) {
                                field[i][j].getCenter()[0] =  Center(i, j,fieldr)[0];
                                field[i][j].getCenter()[1] =  Center(i, j,fieldr)[1];
                            }

                        }

                        for (int i = 0; i < field.length; i++) {
                            for (int j = 0; j < field[0].length; j++) {
                                field[i][j].getCenter()[0] = (int) (field[i][j].getCenter()[0]-x12/scal*sc+getWidth()/2 );
                                field[i][j].getCenter()[1] = (int) (field[i][j].getCenter()[1] -y12/scal*sc+getHeight()/2);

                            }

                        }
                        peekfield = Peeks(fieldr);
                        pathsfield = Paths();

                    }

                    final int a = dx;
                    final int b = dy;
                    final int c = Checkborder(a,b)[0];
                    final int d = Checkborder(a,b)[1];

                    for (int i = 0; i < field.length; i++) {
                        for (int j = 0; j < field[0].length; j++) {

                            field[i][j].getCenter()[0] = (int) (field[i][j].getCenter()[0] - c * 3);
                            field[i][j].getCenter()[1] = (int) (field[i][j].getCenter()[1] - d * 3);



                        }

                    }
                    peekfield = Peeks(fieldr);
                    pathsfield = Paths();
                    if(peekfield[typeList.get(0).getCoord()[0]][typeList.get(0).getCoord()[1]][4][0]!=rect2List.get(0).left ||animflag==1||
                            peekfield[typeList.get(0).getCoord()[0]][typeList.get(0).getCoord()[1]][4][1]!=rect2List.get(0).top||animflag2==1
                    ) {
                        for (Unit type : typeList) {
                            rect2List.set(typeList.indexOf(type), new Rect(peekfield[type.getCoord()[0]][type.getCoord()[1]][4][0], peekfield[type.getCoord()[0]][type.getCoord()[1]][4][1],
                                    peekfield[type.getCoord()[0]][type.getCoord()[1]][1][0], peekfield[type.getCoord()[0]][type.getCoord()[1]][1][1]));
                        }
                    }


                    for (int i = 0; i < field.length; i++) {
                        for (int j = 0; j < field[0].length; j++) {
                            if (Activ0(activefield[i][j] ) == 1) {
                                canvas.drawPath(pathsfield[i][j],p1);
                            }
                            else if(Activ0(activefield[i][j])  == 0){
                                canvas.drawPath(pathsfield[i][j],p2);}
                            else if(Activ0(activefield[i][j])  == 2){
                                canvas.drawPath(pathsfield[i][j],p6);
                            }
                            else if(Activ0(activefield[i][j])  == 3){
                                canvas.drawPath(pathsfield[i][j],p7);
                            }
                            else if(Activ0(activefield[i][j])  == 4){
                                canvas.drawPath(pathsfield[i][j],p8);
                            }

                            if(Activ1(activefield[i][j])==0 ){
                                canvas.drawBitmap(tonnel1,ton1,new Rect( peekfield[i][j][4][0],  peekfield[i][j][4][1],
                                        peekfield[i][j][1][0],  peekfield[i][j][1][1]),p3);
                            }
                            else if(Activ1(activefield[i][j])==1){
                                canvas.drawBitmap(tonnel2,ton2,new Rect( peekfield[i][j][4][0],  peekfield[i][j][4][1],
                                        peekfield[i][j][1][0],  peekfield[i][j][1][1]),p3);

                            }
                            else if(Activ1(activefield[i][j])==2){
                                canvas.drawBitmap(tonnel3,ton3,new Rect(peekfield[i][j][4][0],  peekfield[i][j][4][1],
                                        peekfield[i][j][1][0],  peekfield[i][j][1][1]),p3);

                            }
                            else if(Activ1(activefield[i][j])==3){
                                canvas.drawBitmap(tonnel4,ton4,new Rect(peekfield[i][j][4][0],  peekfield[i][j][4][1],
                                        peekfield[i][j][1][0],  peekfield[i][j][1][1]),p3);

                            }
                            else if(Activ1(activefield[i][j])==4){
                                canvas.drawBitmap(healerhut,hut,new Rect(peekfield[i][j][4][0],  peekfield[i][j][4][1],
                                        peekfield[i][j][1][0],  peekfield[i][j][1][1]),p3);

                            }
                            else if(Activ1(activefield[i][j])==5){
                                canvas.drawBitmap(forge,forg,new Rect(peekfield[i][j][4][0]-fieldr/5,  peekfield[i][j][4][1],
                                        peekfield[i][j][1][0]+fieldr/5,  peekfield[i][j][1][1]+fieldr/6),p3);

                            }
                            else if(Activ1(activefield[i][j])==6){
                                canvas.drawBitmap(castle,cast,new Rect(peekfield[i][j][4][0]-fieldr/5,  peekfield[i][j][4][1]-fieldr/3,
                                        peekfield[i][j][1][0]+fieldr/5,  peekfield[i][j][1][1]),p3);

                            }



                        }
                    }
                    for (int i = 0; i < field.length; i++) {
                        for (int j = 0; j < field[0].length; j++) {
                            field[i][j].DrawHex(canvas,p3,peekfield[i][j]);
                        }
                    }

                    Animation(movarray,flaglist1,flaglist2,canvas);

                    for (Unit type:typeList) {
                        int index = typeList.indexOf(type);

                        if(typeList.size()!=0 &&rect1List.size()!=0&&rect2List.size()!=0&& dList.size()!=0){
                            if((type != pool_unit || type == pool_unit && isHit(flaglist2)==1)&& type instanceof Robber){
                                if (dList.get(typeList.indexOf(type))[8] < 7) {
                                    canvas.drawBitmap(robber2, rect1List.get(index ), rect2List.get(typeList.indexOf(type)), p3);
                                } else {
                                    canvas.drawBitmap(robber1, rect1List.get(typeList.indexOf(type)), rect2List.get(typeList.indexOf(type)), p3);

                                }
                                dList.get(typeList.indexOf(type))[8] += 1;
                                if (dList.get(typeList.indexOf(type))[8] == 14) {
                                    dList.get(typeList.indexOf(type))[8] = 0;
                                }

                            }
                            else if ( type instanceof Hero) {
                                if (dList.get(typeList.indexOf(type))[8] < 7) {
                                    canvas.drawBitmap(hero2, rect1List.get(index), rect2List.get(typeList.indexOf(type)), p3);
                                    dList.get(typeList.indexOf(type))[8] += 1;


                                } else {
                                    canvas.drawBitmap(hero1, rect1List.get(index), rect2List.get(typeList.indexOf(type)), p3);
                                    dList.get(typeList.indexOf(type))[8] += 1;

                                }
                                if (dList.get(typeList.indexOf(type))[8] == 14) {
                                    dList.get(typeList.indexOf(type))[8] = 0;
                                }
                            }
                            else if ((type != pool_unit || type == pool_unit && isHit(flaglist2)==1)&& type instanceof Devil) {
                                if (dList.get(typeList.indexOf(type))[8] < 7) {
                                    canvas.drawBitmap(devil2, rect1List.get(typeList.indexOf(type)), rect2List.get(typeList.indexOf(type)), p3);



                                } else {
                                    canvas.drawBitmap(devil1, rect1List.get(typeList.indexOf(type)), rect2List.get(typeList.indexOf(type)), p3);

                                }
                                dList.get(typeList.indexOf(type))[8] += 1;
                                if (dList.get(typeList.indexOf(type))[8] == 14) {
                                    dList.get(typeList.indexOf(type))[8] = 0;
                                }

                            }
                            else if ((type != pool_unit || type == pool_unit && isHit(flaglist2)==1)&& type instanceof Wolf) {
                                if (dList.get(typeList.indexOf(type))[8] < 7) {
                                    if (type.getCoord()[0] > MainActivity.Findhero(typeList).getCoord()[0]) {
                                        canvas.drawBitmap(wolf2, rect1List.get(typeList.indexOf(type)), rect2List.get(typeList.indexOf(type)), p3);
                                    } else {
                                        canvas.drawBitmap(wolf2rev, rect1List.get(typeList.indexOf(type)), rect2List.get(typeList.indexOf(type)), p3);
                                    }
                                    dList.get(typeList.indexOf(type))[8] += 1;


                                } else {
                                    if (type.getCoord()[0] > MainActivity.Findhero(typeList).getCoord()[0]) {
                                        canvas.drawBitmap(wolf1, rect1List.get(typeList.indexOf(type)), rect2List.get(typeList.indexOf(type)), p3);
                                    } else {
                                        canvas.drawBitmap(wolf1rev, rect1List.get(typeList.indexOf(type)), rect2List.get(typeList.indexOf(type)), p3);
                                    }
                                    dList.get(typeList.indexOf(type))[8] += 1;

                                }
                                if (dList.get(typeList.indexOf(type))[8] == 14) {
                                    dList.get(typeList.indexOf(type))[8] = 0;
                                }
                            }
                            else if ((type != pool_unit || type == pool_unit && isHit(flaglist2)==1)&& type instanceof Warrior) {
                                if (dList.get(index)[8] < 7) {
                                    canvas.drawBitmap(warrior2, rect1List.get(index), rect2List.get(index), p3);
                                    dList.get(index)[8] += 1;


                                } else {
                                    canvas.drawBitmap(warrior1, rect1List.get(index), rect2List.get(index), p3);
                                    dList.get(index)[8] += 1;

                                }
                                if (dList.get(index)[8] == 14) {
                                    dList.get(index)[8] = 0;
                                }
                            } else if ((type != pool_unit || type == pool_unit && isHit(flaglist2)==1)&& type instanceof Wizard) {
                                if (dList.get(index)[8] < 7) {
                                    canvas.drawBitmap(wizard2, rect1List.get(index), rect2List.get(index), p3);
                                    dList.get(index)[8] += 1;


                                } else {
                                    canvas.drawBitmap(wizard1, rect1List.get(index), rect2List.get(index), p3);
                                    dList.get(index)[8] += 1;

                                }
                                if (dList.get(index)[8] == 14) {
                                    dList.get(index)[8] = 0;
                                }

                            }}
                    }

                    if(animflag2==1) {
                        Animation(movarray, flaglist1, flaglist2, canvas);
                    }
                    mov = (ArrayList<Hex>) movemain.clone();
                    if(show==true) {

                        for (int i = 0; i < mov.size() - 1; i++) {
                            if (mov.get(i) != mov.get(i + 1) && mov.get(i+1)!=null&&mov.get(i)!=null) {



                                Matrix matrix = new Matrix();
                                int direction = Direction(mov.get(i + 1), mov.get(i)) - 1;
                                matrix.postRotate(direction * 60 - 150);


                                matrix.postScale((float) fieldr / arrow.getWidth(), (float) fieldr/ arrow.getHeight());
                                if (direction != 4 && direction != 5)
                                    matrix.postTranslate((mov.get(i + 1).getCenter()[0] + peekfield[mov.get(i+1).getCoord()[0]][mov.get(i+1).getCoord()[1]][direction + 2][0]) / 2,
                                            (mov.get(i + 1).getCenter()[1] + peekfield[mov.get(i+1).getCoord()[0]][mov.get(i+1).getCoord()[1]][direction + 2][1]) / 2);
                                else {
                                    matrix.postTranslate((mov.get(i + 1).getCenter()[0] + peekfield[mov.get(i+1).getCoord()[0]][mov.get(i+1).getCoord()[1]][direction - 4][0]) / 2,
                                            (mov.get(i + 1).getCenter()[1] + peekfield[mov.get(i+1).getCoord()[0]][mov.get(i+1).getCoord()[1]][direction - 4][1]) / 2);
                                }
                                canvas.drawBitmap(arrow, matrix, p3);
                            }
                        }
                    }

                    if (chosen != null) {
                        //   canvas.drawCircle(chosen.getCenter()[0], chosen.getCenter()[1], (float) (fieldr / sqrt(2)), p4);
                        p5.setStrokeWidth(fieldr/5);
                        field[chosen.getCoord()[0]][chosen.getCoord()[1]].DrawHex(canvas,p5,peekfield[chosen.getCoord()[0]][chosen.getCoord()[1]]);

                    }
                    if (access[1] == 2) {
                        access[0] = 2;
                    }
                    while (access[0] == 2) {
                    }


                }

                finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

            }

        }

    }

}


