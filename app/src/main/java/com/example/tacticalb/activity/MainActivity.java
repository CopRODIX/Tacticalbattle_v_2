package com.example.tacticalb.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tacticalb.ClassforServer.Scenario;
import com.example.tacticalb.Hex;
import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;
import com.example.tacticalb.Service.VibrateService;
import com.example.tacticalb.class_unit.Devil;
import com.example.tacticalb.class_unit.Hero;
import com.example.tacticalb.class_unit.Robber;
import com.example.tacticalb.class_unit.Unit;
import com.example.tacticalb.class_unit.Warrior;
import com.example.tacticalb.class_unit.Wizard;
import com.example.tacticalb.class_unit.Wolf;
import com.example.tacticalb.dialog.DialogSc;
import com.example.tacticalb.dialog.Dialog_about;
import com.example.tacticalb.dialog.Shop_par;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static java.lang.Math.sqrt;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener, Shop_par.Dialog_Fr {
   /*
   #Активность игры, в которой происходит обработка нажатий, подготовка данных для обработки в классе Hexfield,
доступ к боевым параметрам, к просмотру дополнительной информации,управление
    */

    LinearLayout Layout;
    Hexfield hexfield;
    Boolean exit=false;
    MediaPlayer mediaPlayer;
    ArrayList<Hex> path1 = new ArrayList<>();
    ArrayList<Hex> path2 = new ArrayList<>();
    ArrayList<Hex> path3 = new ArrayList<>();
    ArrayList<Hex> path4 = new ArrayList<>();
    ArrayList<Hex> path5 = new ArrayList<>();
    ArrayList<Hex> way1 = new ArrayList<>();
    ArrayList<Hex> way2 = new ArrayList<>();
    private int[] k=new int[6];
    private int[] l=new int[6];
    private static final String TAG="Mainactivity";
    ArrayList<Mythread> threads = new ArrayList<>();
    boolean pos=false;
    CountDownTimer timer = new CountDownTimer(500,100) {
        @Override
        public void onTick(long millisUntilFinished) {

            final int w = hexfield.getX1();
            final int z = hexfield.getY1();
            if(w!=0 || z !=0 ) {
                for (int i = 0; i < hexfield.getField().length; i++) {
                    for (int j = 0; j < hexfield.getField()[0].length; j++) {
                        if (hexfield.getField()[i][j].Checkhex(w, z, hexfield.getPeekfield()[hexfield.getField()[i][j].getCoord()[0]][hexfield.getField()[i][j].getCoord()[1]],k,l) == 1) {
                            if (hexfield.getField()[i][j] != hexfield.getChosen()) {


                                timer.cancel();

                            }
                        }
                    }
                }
            }


        }

        @Override
        public void onFinish() {
            Info();

        }
    };
    int maxhel;
    int hel;
    boolean lose = false;
    static Scenario scenario;
    static boolean show = true;
    static int scenka;
    boolean pos1=true;
    private static  final int REQUEST_ACCESS_TYPE=1;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabas;
    Save_inf save_inf;
    TextView health_text,armor_text,damage_text,health,damage,armor,xpwar;
    ProgressBar health_bar,armor_bar,damage_bar;
    LinearLayout  armor_layout,damage_layout,health_layout,top,parametr;
ImageButton button;
    Hero hero;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeGame);

        setContentView(R.layout.activity_main);
        //
        System.out.println(1);

   button= findViewById(R.id.sc);
   button.setOnClickListener(this);
        armor_layout=findViewById(R.id.armor);
        health_layout=findViewById(R.id.health);
        damage_layout=findViewById(R.id.damage);
        armor_layout.setOnClickListener(this);
        damage_layout.setOnClickListener(this);
        health_layout.setOnClickListener(this);
        top=findViewById(R.id.top);
        top.setOnClickListener(this);
        health=findViewById(R.id.textView_hel);
        damage=findViewById(R.id.textViewdam);
        armor=findViewById(R.id.textViewarm);
        xpwar=findViewById(R.id.textView_xp);
        health_bar=findViewById(R.id.progress_health);
        health_text=findViewById(R.id.textView_heralth);
        armor_bar=findViewById(R.id.progress_armor);
        armor_text=findViewById(R.id.textView_arm);
        damage_bar=findViewById(R.id.progress_damage);
        damage_text=findViewById(R.id.textView_dam);

        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(armor_text, 12, 112, 10,
                TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(damage_text, 12, 112, 10,
                TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(health_text, 12, 112, 10,
                TypedValue.COMPLEX_UNIT_SP);

        save_inf=new Save_inf(this);

if (save_inf.getTypeGame()) {
    firebaseDatabas = FirebaseDatabase.getInstance();
    databaseReference = firebaseDatabas.getReference("USER").child(FirebaseAuth.getInstance().getUid().toString());
}



        Bundle arguments = getIntent().getExtras();

        hexfield = new Hexfield(this); save_inf= new Save_inf(this);
        if (arguments.getBoolean(Save_inf.REMEMBER_BOOLEAN_FOR_ARGUMENTS)){
            scenario=save_inf.getscenario(Save_inf.RESUME_SCENARIO_LOST_GAME);
            hexfield.setHero_top(save_inf.getHero(Save_inf.RESUME_HERO_LOST_GAME));

        }
        else{
            hero=save_inf.getHero();
            hexfield.setHero_top(new Hero(hero.get_ID(),hero.getHealth(),hero.getDamage(),hero.getArmor(),hero.getLevel(),50,50,0,hero.getHealth(),5));

            scenario= (Scenario) arguments.getSerializable(Scenario.class.getSimpleName());}
        switch (scenario.getTYPE()){
            case -2:scenka=-2;break;
            case 0:scenka=-2;break;
            case -1:scenka=-1;break;
            case 1:scenka=-1;break;
            case 2:scenka=2;break;
        }
        // Toast.makeText(getApplicationContext(),scenario.getTYPE()+"",Toast.LENGTH_SHORT).show();

        hexfield.maxSt=scenario.getSTRAGE();
        hexfield.stage=scenario.getNOWStrange();
        hexfield.resume=arguments.getBoolean(Save_inf.REMEMBER_BOOLEAN_FOR_ARGUMENTS);

        Layout = findViewById(R.id.layout);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        hexfield.setOnTouchListener(this);

        Layout.addView(hexfield, lParams);
        mediaPlayer= MediaPlayer.create(this,R.raw.fon);

        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.5f,0.5f);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        open_info_progress();
        // mediaPlayer.start();
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.start();

    }
    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();


    }
    /*
  #Нахождение расстояния между 2 точками
   */
    public float  LongWay(int x1, int x2, int y1, int y2){
        return (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1);

    }
    /*
  #Поиск героя среди массива юнитов
   */
    public static Unit Findhero(ArrayList<Unit> typeList) {
        int index = 0;
        for (Unit type : typeList
        ) {
            if (type instanceof Hero) {
                index = typeList.indexOf(type);
            }
        }
        return typeList.get(index);
    }
    /*
  #Нахождение юнита по гексу поля, на котором он находиться
   */
    public static Unit Findunit(Hex hex, ArrayList<Unit> typeList, ArrayList<ArrayList<Hex>> arr, int index) {
        Unit unit = null;
        for (Unit type : typeList) {
            if (index > typeList.indexOf(type)) {
                if (hex == arr.get(typeList.indexOf(type)).get(arr.get(typeList.indexOf(type)).size() - 1)) {
                    unit = type;
                }

            } else {
                if (hex == type.getHex()) {
                    unit = type;
                }
            }

        }
        return unit;
    }
    /* public void Skills(View view) {
         Intent intent=new Intent(this, HeroActivity.class);
         mediaPlayer.pause();
         intent.putExtra("seconds",mediaPlayer.getCurrentPosition());
         intent.putExtra(Hero.class.getSimpleName(),hexfield.getHero_top());
         startActivityForResult(intent, REQUEST_ACCESS_TYPE);
     }*/
  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_ACCESS_TYPE){
            if(resultCode ==RESULT_OK){
                hexfield.setHero_top((Hero) data.getExtras().getSerializable(Hero.class.getSimpleName()));
                mediaPlayer.seekTo(data.getIntExtra("seconds",2000));
                mediaPlayer.start();
            }


        }
        else{
            super.onActivityResult(requestCode, resultCode, data);}
    }*/
  /*
     #Просчёт веса пути без учёта первого и последнего гексов(вес гекса зависит от ландшафта)
      */
    public int Calcweight(ArrayList<Hex> path) {
        int count = 0;
        for (Hex bor : path) {
            if (path.indexOf(bor) != 0 && path.indexOf(bor) != path.size() - 1) {
                if (hexfield.Activ0(bor.getActive()) == 1||hexfield.Activ0(bor.getActive())==3||hexfield.Activ0(bor.getActive())==4) {
                    count += 1;
                } else if (hexfield.Activ0(bor.getActive()) == 2) {
                    count += 2;
                }
            }

        }
        return count;
    }
    /*
 #Поиск монстра, который в этом ходу был побеждён героем
  */
    public Unit Findpool(ArrayList<Unit> hitlist, ArrayList<Unit> typelist){
        Unit pool = null;
        for (Unit unit:typelist) {
            if(unit==hitlist.get(hexfield.getTypeList().indexOf(MainActivity.Findhero(hexfield.getTypeList()))) && hexfield.Gethealth(unit)-(hexfield.getHero_top().getDamage()-hexfield.Getarmor(unit))<=0
                    && (hexfield.getFlaglist2().get(hexfield.getTypeList().indexOf(MainActivity.Findhero(hexfield.getTypeList()))).get(0)==1||hexfield.getFlaglist2().get(hexfield.getTypeList().indexOf(MainActivity.Findhero(hexfield.getTypeList()))).get(1)==1)){
                pool = unit;
            }

        }
        return pool;
    }
    /*
 #Поиск другого выхода из тунеля
  */
    public Hex Findtonnel(Integer active, Hex[][] field, Hex hex1){
        Hex hex2 = null;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if(active == hexfield.Activ1(field[i][j].getActive()) && field[i][j]!=hex1){
                    hex2 = field[i][j];

                }


            }

        }
        return hex2;
    }
    /*
 #Заполнение массивов информацией о передвижениях и действиях юнитов, начало анимации по флагу
  */
    public void Start(View view) {
        open_info_progress();


        if(hexfield.getAnimflag()==0){
            for (Unit type : hexfield.getTypeList()) {
                Hex herpos = hexfield.getMovarray().get(hexfield.getTypeList().indexOf(MainActivity.Findhero(hexfield.getTypeList()))).get(2);
                Hex herpos2 = MainActivity.Findhero(hexfield.getTypeList()).getHex();

                if (type instanceof Robber) {


                    if (Findpool(hexfield.getHitList(), hexfield.getTypeList()) != type) {
                        int robpos = hexfield.getTypeList().indexOf(type);


                        path1 = hexfield.getPath(hexfield.getField()[type.getHex().getCoord()[0]][type.getHex().getCoord()[1]],
                                hexfield.getField()[herpos2.getCoord()[0]][herpos2.getCoord()[1]], 2, robpos);
                        path3 = hexfield.getPath3(hexfield.getField()[type.getHex().getCoord()[0]][type.getHex().getCoord()[1]], hexfield.getField()[herpos2.getCoord()[0]][herpos2.getCoord()[1]], robpos);
                        if (path1.size() >= 2 && path1.size() <= 8) {


                            if (path1.size() > 3) {
                                if (path1.size() == 4) {
                                    if (hexfield.random.nextInt(2) == 0) {
                                        hexfield.getMovarray().get(robpos).set(0, type.getHex());
                                        hexfield.getMovarray().get(robpos).set(1, path1.get(1));
                                        hexfield.getMovarray().get(robpos).set(2, path1.get(1));
                                    } else {
                                        hexfield.getMovarray().get(robpos).set(0, type.getHex());
                                        hexfield.getMovarray().get(robpos).set(1, path1.get(1));
                                        if (hexfield.Activ0(hexfield.getMovarray().get(robpos).get(1).getActive()) != 2) {
                                            hexfield.getMovarray().get(robpos).set(2, path1.get(2));
                                        } else {
                                            hexfield.getMovarray().get(robpos).set(2, path1.get(1));
                                        }

                                    }

                                } else {
                                    hexfield.getMovarray().get(robpos).set(0, type.getHex());
                                    hexfield.getMovarray().get(robpos).set(1, path1.get(1));
                                    if (hexfield.Activ0(hexfield.getMovarray().get(robpos).get(1).getActive()) != 2) {
                                        hexfield.getMovarray().get(robpos).set(2, path1.get(2));
                                    } else {
                                        hexfield.getMovarray().get(robpos).set(2, path1.get(1));

                                    }
                                }
                                hexfield.getFlaglist2().get(robpos).set(0, 0);
                                hexfield.getFlaglist2().get(robpos).set(1, 0);
                                for (int i = 0; i < 2; i++) {
                                    if (hexfield.getMovarray().get(robpos).get(i) == hexfield.getMovarray().get(robpos).get(i + 1)) {
                                        hexfield.getFlaglist1().get(robpos).set(i, 0);

                                    } else {
                                        hexfield.getFlaglist1().get(robpos).set(i, 1);

                                    }

                                }
                            } else if (Calcweight(path1) == 0) {
                                hexfield.getMovarray().get(robpos).set(0, type.getHex());
                                hexfield.getMovarray().get(robpos).set(1, type.getHex());
                                if (path3.size() >= 2) {
                                    hexfield.getMovarray().get(robpos).set(2, path3.get(1));
                                } else {
                                    hexfield.getMovarray().get(robpos).set(2, type.getHex());
                                }

                                if (hexfield.Hit(hexfield.getHero_top().getArmver(), ((Robber) type).getDamver()) == 1) {
                                    hexfield.getFlaglist2().get(robpos).set(0, 1);
                                } else {
                                    hexfield.getFlaglist2().get(robpos).set(0, 5);
                                }
                                hexfield.getFlaglist1().get(robpos).set(0, 0);
                                hexfield.getFlaglist1().get(robpos).set(1, 1);
                                hexfield.getFlaglist2().get(robpos).set(1, 0);
                            } else if (Calcweight(path1) != 0) {
                                hexfield.getMovarray().get(robpos).set(0, type.getHex());
                                hexfield.getMovarray().get(robpos).set(1, path1.get(1));
                                hexfield.getMovarray().get(robpos).set(2, hexfield.getMovarray().get(robpos).get(1));

                                if (hexfield.Hit(hexfield.getHero_top().getArmver(), ((Robber) type).getDamver()) == 1) {
                                    hexfield.getFlaglist2().get(robpos).set(1, 1);
                                } else {
                                    hexfield.getFlaglist2().get(robpos).set(1, 5);
                                }
                                hexfield.getFlaglist1().get(robpos).set(0, 1);
                                hexfield.getFlaglist1().get(robpos).set(1, 0);
                                hexfield.getFlaglist2().get(robpos).set(0, 0);

                            }

                        }
                        path1.clear();
                        path3.clear();
                    }


                } else if (type instanceof Hero) {
                    int heropos = hexfield.getTypeList().indexOf(Findhero(hexfield.getTypeList()));
                    if (hexfield.getMovemain().size() < 3) {
                        for (int i = hexfield.getMovemain().size(); i < 3; i++) {
                            hexfield.getMovemain().add(null);

                        }
                    }
                    for (int i = 0; i < hexfield.getMovemain().size(); i++) {
                        if(hexfield.getMovemain().get(i)==null){
                            for (int j = i; j <hexfield.getMovemain().size() ; j++) {
                                hexfield.getMovemain().set(j,null);

                            }
                            break;
                        }

                    }
                    for (int i = 0; i < 3; i++) {
                        if (i == 0) {
                            hexfield.getMovarray().get(heropos).set(i, MainActivity.Findhero(hexfield.getTypeList()).getHex());

                        } else if (hexfield.getMovemain().get(i) != null) {
                            hexfield.getMovarray().get(heropos).set(i, hexfield.getMovemain().get(i));

                        } else {
                            hexfield.getMovarray().get(heropos).
                                    set(i, hexfield.getMovarray().get(heropos).get(i - 1));

                        }

                    }
                    for(Hex h: hexfield.getMovemain()){
                        if(h!=null){
                            System.out.println(h.getCoord()[0]+" "+h.getCoord()[1]);}
                        else {
                            System.out.println("null");
                        }
                    }


                    Unit unit1 = Findunit(hexfield.getMovemain().get(1), hexfield.getTypeList(), hexfield.getMovarray(), heropos);
                    Unit unit2 = Findunit(hexfield.getMovemain().get(2), hexfield.getTypeList(), hexfield.getMovarray(), heropos);
                    if (hexfield.getMovemain().get(2) == null && hexfield.getMovemain().get(1) != null) {
                        if (unit1 == null) {

                            if (hexfield.Activ1(hexfield.getMovarray().get(heropos).get(1).getActive()) >= 0 && hexfield.Activ1(hexfield.getMovarray().get(heropos).get(1).getActive()) <= 3) {
                                hexfield.getMovarray().get(heropos).set(2, Findtonnel(hexfield.Activ1(hexfield.getMovarray().get(heropos).get(1).getActive()), hexfield.getField(), hexfield.getMovarray().get(heropos).get(1)));
                                hexfield.getFlaglist1().get(heropos).set(0, 1);
                                hexfield.getFlaglist1().get(heropos).set(1, 2);
                                hexfield.getFlaglist2().get(heropos).set(0, 0);
                                hexfield.getFlaglist2().get(heropos).set(1, 0);
                                hexfield.getMovemain().clear();

                            } else if (hexfield.Activ1(hexfield.getMovarray().get(heropos).get(1).getActive()) == 4) {
                                hexfield.getFlaglist1().get(heropos).set(0, 1);
                                hexfield.getFlaglist1().get(heropos).set(1, 0);
                                hexfield.getFlaglist2().get(heropos).set(0, 0);
                                hexfield.getFlaglist2().get(heropos).set(1, 2);
                                hexfield.getMovemain().remove(0);


                            } else if (hexfield.Activ1(hexfield.getMovarray().get(heropos).get(1).getActive()) == 5) {
                                hexfield.getFlaglist1().get(heropos).set(0, 1);
                                hexfield.getFlaglist1().get(heropos).set(1, 0);
                                hexfield.getFlaglist2().get(heropos).set(0, 0);
                                hexfield.getFlaglist2().get(heropos).set(1, 3);
                                hexfield.getMovemain().remove(0);

                            } else if (hexfield.Activ1(hexfield.getMovarray().get(heropos).get(1).getActive()) == 6) {
                                hexfield.getFlaglist1().get(heropos).set(0, 1);
                                hexfield.getFlaglist1().get(heropos).set(1, 0);
                                hexfield.getFlaglist2().get(heropos).set(0, 0);
                                hexfield.getFlaglist2().get(heropos).set(1, 4);
                                hexfield.getMovemain().remove(0);

                            } else {
                                hexfield.getFlaglist1().get(heropos).set(0, 1);
                                hexfield.getFlaglist1().get(heropos).set(1, 0);
                                hexfield.getFlaglist2().get(heropos).set(0, 0);
                                hexfield.getFlaglist2().get(heropos).set(1, 0);
                                hexfield.getMovemain().remove(0);
                            }

                        } else {

                            if (hexfield.Hit(hexfield.Getarmver(unit1), hexfield.getHero_top().getDamver()) == 1) {
                                hexfield.getFlaglist2().get(heropos).set(0, 1);
                            } else {
                                hexfield.getFlaglist2().get(heropos).set(0, 5);
                            }
                            hexfield.getHitList().set(heropos, unit1);
                            hexfield.getMovemain().clear();
                            hexfield.getMovarray().get(heropos).set(1, hexfield.getMovarray().get(heropos).get(0));
                            hexfield.getMovarray().get(heropos).set(2, hexfield.getMovarray().get(heropos).get(0));
                            hexfield.getFlaglist1().get(heropos).set(0, 0);
                            hexfield.getFlaglist1().get(heropos).set(1, 0);
                            hexfield.getFlaglist2().get(heropos).set(1, 0);

                        }
                    } else if (hexfield.getMovemain().get(2) != null) {
                        if (unit2 == null && unit1 == null) {
                            if (hexfield.Activ0(hexfield.getMovemain().get(1).getActive()) == 2) {
                                if (hexfield.Activ1(hexfield.getMovarray().get(heropos).get(1).getActive()) >= 0 && hexfield.Activ1(hexfield.getMovarray().get(heropos).get(1).getActive()) <= 3) {
                                    hexfield.getMovarray().get(heropos).set(2, Findtonnel(hexfield.Activ1(hexfield.getMovarray().get(heropos).get(1).getActive()), hexfield.getField(), hexfield.getMovarray().get(heropos).get(1)));
                                    hexfield.getFlaglist1().get(heropos).set(0, 1);
                                    hexfield.getFlaglist1().get(heropos).set(1, 2);
                                    hexfield.getFlaglist2().get(heropos).set(0, 0);
                                    hexfield.getFlaglist2().get(heropos).set(1, 0);
                                    hexfield.getMovemain().clear();

                                } else if (hexfield.Activ1(hexfield.getMovarray().get(heropos).get(1).getActive()) == 4) {
                                    hexfield.getFlaglist1().get(heropos).set(0, 1);
                                    hexfield.getFlaglist1().get(heropos).set(1, 0);
                                    hexfield.getFlaglist2().get(heropos).set(0, 0);
                                    hexfield.getFlaglist2().get(heropos).set(1, 2);
                                    hexfield.getMovemain().remove(0);

                                } else if (hexfield.Activ1(hexfield.getMovarray().get(heropos).get(1).getActive()) == 5) {
                                    hexfield.getFlaglist1().get(heropos).set(0, 1);
                                    hexfield.getFlaglist1().get(heropos).set(1, 0);
                                    hexfield.getFlaglist2().get(heropos).set(0, 0);
                                    hexfield.getFlaglist2().get(heropos).set(1, 3);
                                    hexfield.getMovemain().remove(0);


                                } else if (hexfield.Activ1(hexfield.getMovarray().get(heropos).get(1).getActive()) == 6) {
                                    hexfield.getFlaglist1().get(heropos).set(0, 1);
                                    hexfield.getFlaglist1().get(heropos).set(1, 0);
                                    hexfield.getFlaglist2().get(heropos).set(0, 0);
                                    hexfield.getFlaglist2().get(heropos).set(1, 4);
                                    hexfield.getMovemain().remove(0);

                                } else {
                                    hexfield.getMovarray().get(heropos).set(2, hexfield.getMovarray().get(heropos).get(1));
                                    hexfield.getFlaglist1().get(heropos).set(0, 1);
                                    hexfield.getFlaglist1().get(heropos).set(1, 0);
                                    hexfield.getFlaglist2().get(heropos).set(0, 0);
                                    hexfield.getFlaglist2().get(heropos).set(1, 0);
                                    hexfield.getMovemain().remove(0);
                                }
                            } else {

                                hexfield.getFlaglist1().get(heropos).set(0, 1);
                                hexfield.getFlaglist1().get(heropos).set(1, 1);
                                hexfield.getFlaglist2().get(heropos).set(0, 0);
                                hexfield.getFlaglist2().get(heropos).set(1, 0);
                                hexfield.getMovemain().remove(0);
                                hexfield.getMovemain().remove(0);
                            }
                        } else if (unit2 != null && unit1 == null) {
                            if (hexfield.Hit(hexfield.Getarmver(unit2), hexfield.getHero_top().getDamver()) == 1) {
                                hexfield.getFlaglist2().get(heropos).set(1, 1);
                            } else {
                                hexfield.getFlaglist2().get(heropos).set(1, 5);
                            }
                            hexfield.getHitList().set(heropos, unit2);
                            hexfield.getMovarray().get(heropos).set(2, hexfield.getMovarray().get(heropos).get(1));
                            hexfield.getFlaglist1().get(heropos).set(0, 1);
                            hexfield.getFlaglist1().get(heropos).set(1, 0);
                            hexfield.getFlaglist2().get(heropos).set(0, 0);
                            hexfield.getMovemain().clear();

                        } else {
                            if (hexfield.Hit(hexfield.Getarmver(unit1), hexfield.getHero_top().getDamver()) == 1) {
                                hexfield.getFlaglist2().get(heropos).set(0, 1);
                            } else {
                                hexfield.getFlaglist2().get(heropos).set(0, 5);
                            }
                            hexfield.getHitList().set(heropos, unit1);
                            hexfield.getMovarray().get(heropos).set(1, hexfield.getMovarray().get(heropos).get(0));
                            hexfield.getMovarray().get(heropos).set(2, hexfield.getMovarray().get(heropos).get(0));
                            hexfield.getFlaglist1().get(heropos).set(0, 0);
                            hexfield.getFlaglist1().get(heropos).set(1, 0);
                            hexfield.getMovemain().clear();
                            hexfield.getFlaglist2().get(heropos).set(1, 0);
                        }
                    } else {


                        hexfield.getFlaglist1().get(heropos).set(0, 0);
                        hexfield.getFlaglist1().get(heropos).set(1, 0);
                        hexfield.getFlaglist2().get(heropos).set(0, 0);
                        hexfield.getFlaglist2().get(heropos).set(1, 0);


                    }

                    hexfield.setChosen(null);


                } else if (type instanceof Wolf) {
                    if (Findpool(hexfield.getHitList(), hexfield.getTypeList()) != type) {

                        path1 = hexfield.getPath(hexfield.getField()[type.getHex().getCoord()[0]][type.getHex().getCoord()[1]], hexfield.getField()[herpos.getCoord()[0]][herpos.getCoord()[1]], 3, hexfield.getTypeList().indexOf(type));
                        int wolfpos = hexfield.getTypeList().indexOf(type);
                        int weight = 0;
                        int mov = 3;
                        int stop = 4;
                        if (path1.size() >= 2 && path1.size() <= 8) {


                            if (Calcweight(path1) > 2) {
                                if (Calcweight(path1) < 5) {
                                    Hex hex = FindHex(path1, Calcweight(path1) - 2);
                                    stop = path1.indexOf(hex);

                                }
                                if (stop > 4) {
                                    stop = 4;

                                }


                                hexfield.getMovarray().get(wolfpos).set(0, type.getHex());
                                for (int i = 1; i < stop; i++) {


                                    if (hexfield.Activ0(path1.get(i - 1).getActive()) == 2) {
                                        weight = 2;
                                    } else if (hexfield.Activ0(path1.get(i - 1).getActive()) == 1 || hexfield.Activ0(path1.get(i - 1).getActive()) == 3 || hexfield.Activ0(path1.get(i - 1).getActive()) == 4) {
                                        weight = 1;
                                    }
                                    if (mov - weight > 0) {
                                        hexfield.getMovarray().get(wolfpos).set(i, path1.get(i));
                                        if (i != 1) {
                                            mov -= weight;
                                        }
                                    } else {
                                        hexfield.getMovarray().get(wolfpos).set(i, hexfield.getMovarray().get(wolfpos).get(i - 1));

                                    }

                                }
                                for (int i = stop; i < 4; i++) {
                                    hexfield.getMovarray().get(wolfpos).set(i, hexfield.getMovarray().get(wolfpos).get(stop - 1));

                                }

                                for (int i = 0; i < 3; i++) {
                                    if (hexfield.getMovarray().get(wolfpos).get(i) == hexfield.getMovarray().get(wolfpos).get(i + 1)) {
                                        hexfield.getFlaglist1().get(wolfpos).set(i, 0);
                                    } else {
                                        hexfield.getFlaglist1().get(wolfpos).set(i, 1);
                                    }
                                    hexfield.getFlaglist2().get(wolfpos).set(i, 0);

                                }

                            } else if (Calcweight(path1) == 2 || Calcweight2(path1) == 1) {
                                int hitindex = 1;
                                for (int i = 1; i < path1.size(); i++) {
                                    if (path1.get(i) == herpos) {
                                        hitindex = i;
                                        break;
                                    }


                                }
                                hexfield.getMovarray().get(wolfpos).set(0, type.getHex());
                                for (int i = 1; i < 4; i++) {
                                    if (i < hitindex) {

                                        hexfield.getMovarray().get(wolfpos).set(i, path1.get(i));


                                    } else if (i >= hitindex) {
                                        hexfield.getMovarray().get(wolfpos).set(i, hexfield.getMovarray().get(wolfpos).get(i - 1));
                                    }


                                }

                                if (hexfield.Hit(hexfield.getHero_top().getArmver(), ((Wolf) type).getDamver()) == 1) {
                                    hexfield.getFlaglist2().get(wolfpos).set(hitindex - 1, 1);
                                } else {
                                    hexfield.getFlaglist2().get(wolfpos).set(hitindex - 1, 5);
                                }


                                for (int i = 0; i < 3; i++) {
                                    if (hexfield.getMovarray().get(wolfpos).get(i) == hexfield.getMovarray().get(wolfpos).get(i + 1)) {
                                        hexfield.getFlaglist1().get(wolfpos).set(i, 0);
                                    } else {
                                        hexfield.getFlaglist1().get(wolfpos).set(i, 1);
                                    }
                                    if (i != hitindex - 1) {
                                        hexfield.getFlaglist2().get(wolfpos).set(i, 0);
                                    }

                                }


                            } else if (Calcweight(path1) == 1) {

                                path3 = hexfield.getPath3(hexfield.getField()[path1.get(1).getCoord()[0]][path1.get(1).getCoord()[1]], hexfield.getField()[herpos.getCoord()[0]][herpos.getCoord()[1]], hexfield.getTypeList().indexOf(type));

                                int hitindex = 1;
                                for (int i = 1; i < path1.size(); i++) {
                                    if (path1.get(i) == herpos) {
                                        hitindex = i;
                                        break;
                                    }


                                }
                                hexfield.getMovarray().get(wolfpos).set(0, type.getHex());
                                for (int i = 1; i < 4; i++) {
                                    if (i < hitindex) {
                                        hexfield.getMovarray().get(wolfpos).set(i, path1.get(i));
                                    } else if (i == hitindex) {
                                        hexfield.getMovarray().get(wolfpos).set(i, hexfield.getMovarray().get(wolfpos).get(i - 1));


                                    } else if (i > hitindex && path3.size() >= 2) {

                                        hexfield.getMovarray().get(wolfpos).set(i, path3.get(1));

                                    } else {
                                        hexfield.getMovarray().get(wolfpos).set(i, hexfield.getMovarray().get(wolfpos).get(i - 1));

                                    }

                                }

                                if (hexfield.Hit(hexfield.getHero_top().getArmver(), ((Wolf) type).getDamver()) == 1) {
                                    hexfield.getFlaglist2().get(wolfpos).set(hitindex - 1, 1);
                                } else {
                                    hexfield.getFlaglist2().get(wolfpos).set(hitindex - 1, 5);
                                }
                                for (int i = 0; i < 3; i++) {
                                    if (hexfield.getMovarray().get(wolfpos).get(i) == hexfield.getMovarray().get(wolfpos).get(i + 1)) {
                                        hexfield.getFlaglist1().get(wolfpos).set(i, 0);
                                    } else {
                                        hexfield.getFlaglist1().get(wolfpos).set(i, 1);
                                    }
                                    if (i != hitindex - 1) {
                                        hexfield.getFlaglist2().get(wolfpos).set(i, 0);
                                    }


                                }


                            } else if (Calcweight(path1) == 0) {
                                path3 = hexfield.getPath3(hexfield.getField()[type.getCoord()[0]][type.getCoord()[1]], hexfield.getField()[herpos.getCoord()[0]][herpos.getCoord()[1]], hexfield.getTypeList().indexOf(type));

                                int rand = hexfield.random.nextInt(2);

                                if (rand == 0 && path3.size() >= 2 && hexfield.Activ0((path3.get(1).getActive())) != 2) {

                                    hexfield.getMovarray().get(wolfpos).set(0, type.getHex());
                                    for (int i = 1; i < 3; i++) {
                                        if (hexfield.Activ0(hexfield.getMovarray().get(wolfpos).get(i - 1).getActive()) == 2) {
                                            weight = 2;
                                        } else if (hexfield.Activ0(hexfield.getMovarray().get(wolfpos).get(i - 1).getActive()) == 1 || hexfield.Activ0(hexfield.getMovarray().get(wolfpos).get(i - 1).getActive()) == 3 ||
                                                hexfield.Activ0(hexfield.getMovarray().get(wolfpos).get(i - 1).getActive()) == 4) {
                                            weight = 1;
                                        }


                                        if (mov - weight > 0) {
                                            if (i == 1) {
                                                hexfield.getMovarray().get(wolfpos).set(i, path3.get(1));
                                            }
                                            if (i == 2) {
                                                hexfield.getMovarray().get(wolfpos).set(i, path3.get(0));
                                            }
                                            if (i != 1) {
                                                mov -= weight;

                                            }
                                        } else {
                                            hexfield.getMovarray().get(wolfpos).set(i, hexfield.getMovarray().get(wolfpos).get(i - 1));

                                        }

                                    }
                                    hexfield.getMovarray().get(wolfpos).set(3, hexfield.getMovarray().get(wolfpos).get(2));

                                    if (mov > 1) {
                                        if (hexfield.Hit(hexfield.getHero_top().getArmver(), ((Wolf) type).getDamver()) == 1) {
                                            hexfield.getFlaglist2().get(wolfpos).set(2, 1);
                                        } else {
                                            hexfield.getFlaglist2().get(wolfpos).set(2, 5);
                                        }
                                    } else {
                                        hexfield.getFlaglist2().get(wolfpos).set(2, 0);
                                    }
                                    for (int i = 0; i < 3; i++) {
                                        if (hexfield.getMovarray().get(wolfpos).get(i) == hexfield.getMovarray().get(wolfpos).get(i + 1)) {
                                            hexfield.getFlaglist1().get(wolfpos).set(i, 0);
                                        } else {
                                            hexfield.getFlaglist1().get(wolfpos).set(i, 1);
                                        }
                                        if (i != 2) {
                                            hexfield.getFlaglist2().get(wolfpos).set(i, 0);
                                        }


                                    }

                                } else {

                                    if (hexfield.Hit(hexfield.getHero_top().getArmver(), ((Wolf) type).getDamver()) == 1) {
                                        hexfield.getFlaglist2().get(wolfpos).set(0, 1);
                                    } else {
                                        hexfield.getFlaglist2().get(wolfpos).set(0, 5);
                                    }

                                    hexfield.getMovarray().get(wolfpos).set(0, type.getHex());
                                    hexfield.getMovarray().get(wolfpos).set(1, type.getHex());
                                    mov -= 1;
                                    for (int i = 2; i < 4; i++) {
                                        if (hexfield.Activ0(hexfield.getMovarray().get(wolfpos).get(i - 1).getActive()) == 2) {
                                            weight = 2;
                                        } else if (hexfield.Activ0(hexfield.getMovarray().get(wolfpos).get(i - 1).getActive()) == 1 || hexfield.Activ0(hexfield.getMovarray().get(wolfpos).get(i - 1).getActive()) == 3
                                                || hexfield.Activ0(hexfield.getMovarray().get(wolfpos).get(i - 1).getActive()) == 4) {
                                            weight = 1;
                                        }
                                        if (i == 2 && path3.size() >= 2) {
                                            hexfield.getMovarray().get(wolfpos).set(i, path3.get(i - 1));

                                        } else if (mov - weight > 0 && path3.size() >= 3) {
                                            hexfield.getMovarray().get(wolfpos).set(i, path3.get(i - 1));

                                            mov -= weight;

                                        } else {
                                            hexfield.getMovarray().get(wolfpos).set(i, hexfield.getMovarray().get(wolfpos).get(i - 1));

                                        }


                                    }
                                    for (int i = 0; i < 3; i++) {
                                        if (hexfield.getMovarray().get(wolfpos).get(i) == hexfield.getMovarray().get(wolfpos).get(i + 1)) {
                                            hexfield.getFlaglist1().get(wolfpos).set(i, 0);
                                        } else {
                                            hexfield.getFlaglist1().get(wolfpos).set(i, 1);
                                        }
                                        if (i != 0) {
                                            hexfield.getFlaglist2().get(wolfpos).set(i, 0);
                                        }


                                    }

                                }
                            }

                        }
                        path1.clear();
                        path3.clear();

                    }
                } else if (type instanceof Devil) {
                    if (Findpool(hexfield.getHitList(), hexfield.getTypeList()) != type) {
                        int devilpos = hexfield.getTypeList().indexOf(type);

                        for (Unit unit : hexfield.getTypeList()) {
                            if (!(unit instanceof Hero) && unit != Findpool(hexfield.getHitList(), hexfield.getTypeList()) && unit != type) {
                                if (unit instanceof Robber) {
                                    hel = ((Robber) unit).getHealth();
                                    maxhel = ((Robber) unit).getMax_health();
                                } else if (unit instanceof Warrior) {
                                    hel = ((Warrior) unit).getHealth();
                                    maxhel = ((Warrior) unit).getMax_health();
                                } else if (unit instanceof Wolf) {
                                    hel = ((Wolf) unit).getHealth();
                                    maxhel = ((Wolf) unit).getMax_health();
                                } else if (unit instanceof Wizard) {
                                    hel = ((Wizard) unit).getHealth();
                                    maxhel = ((Wizard) unit).getMax_health();
                                } else if (unit instanceof Devil) {
                                    hel = ((Devil) unit).getHealth();
                                    maxhel = ((Devil) unit).getMax_health();
                                }
                                if (maxhel != hel || hexfield.getHitList().get(hexfield.getTypeList().indexOf(MainActivity.Findhero(hexfield.getTypeList()))) == unit) {
                                    if (hexfield.getTypeList().indexOf(unit) < hexfield.getTypeList().indexOf(type)) {
                                        if (path2.size() < 2) {
                                            path2 = hexfield.getPath4(hexfield.getField()[type.getHex().getCoord()[0]][type.getHex().getCoord()[1]], hexfield.getMovarray().get(hexfield.getTypeList().indexOf(unit))
                                                    .get(hexfield.getMovarray().get(hexfield.getTypeList().indexOf(unit)).size() - 1), 2, devilpos);

                                        } else {
                                            path4 = hexfield.getPath4(hexfield.getField()[type.getHex().getCoord()[0]][type.getHex().getCoord()[1]], hexfield.getMovarray().get(hexfield.getTypeList().indexOf(unit))
                                                    .get(hexfield.getMovarray().get(hexfield.getTypeList().indexOf(unit)).size() - 1), 2, devilpos);
                                            if (path4.size() >= 2 && path4.size() < path2.size()) {
                                                path2 = path4;
                                            }

                                        }

                                    } else {
                                        if (path2.size() < 2) {
                                            path2 = hexfield.getPath4(hexfield.getField()[type.getHex().getCoord()[0]][type.getHex().getCoord()[1]], hexfield.getField()[unit.getHex().getCoord()[0]][unit.getHex().getCoord()[1]], 2, hexfield.getTypeList().indexOf(type));

                                        } else {
                                            path4 = hexfield.getPath4(hexfield.getField()[type.getHex().getCoord()[0]][type.getHex().getCoord()[1]], hexfield.getField()[unit.getHex().getCoord()[0]][unit.getHex().getCoord()[1]], 2, hexfield.getTypeList().indexOf(type));
                                            if (path4.size() >= 2 && path4.size() < path2.size()) {
                                                path2 = path4;
                                            }

                                        }

                                    }

                                }
                            }


                        }
                        if (path2.size() > 4 && path2.size() <= 12) {

                            hexfield.getMovarray().get(devilpos).set(0, type.getHex());
                            hexfield.getMovarray().get(devilpos).set(1, path2.get(1));
                            hexfield.getMovarray().get(devilpos).set(2, path2.get(2));
                            hexfield.getFlaglist2().get(devilpos).set(0, 0);
                            hexfield.getFlaglist2().get(devilpos).set(1, 0);
                            hexfield.getFlaglist1().get(devilpos).set(0, 1);
                            hexfield.getFlaglist1().get(devilpos).set(1, 1);


                        } else if (path2.size() == 4) {
                            hexfield.getHitList().set(devilpos, Findunit(path2.get(path2.size() - 1), hexfield.getTypeList(), hexfield.getMovarray(), devilpos));

                            hexfield.getMovarray().get(devilpos).set(0, type.getHex());
                            hexfield.getMovarray().get(devilpos).set(1, path2.get(1));
                            hexfield.getMovarray().get(devilpos).set(2, path2.get(1));


                            hexfield.getFlaglist2().get(devilpos).set(0, 0);
                            hexfield.getFlaglist2().get(devilpos).set(1, 2);
                            hexfield.getFlaglist1().get(devilpos).set(0, 1);
                            hexfield.getFlaglist1().get(devilpos).set(1, 0);


                        } else if (path2.size() == 3 || path2.size() == 2) {
                            hexfield.getHitList().set(devilpos, Findunit(path2.get(path2.size() - 1), hexfield.getTypeList(), hexfield.getMovarray(), devilpos));
                            path5 = hexfield.getPath5(hexfield.getField()[type.getHex().getCoord()[0]][type.getHex().getCoord()[1]],
                                    hexfield.getField()[herpos.getCoord()[0]][herpos.getCoord()[1]], hexfield.getTypeList().indexOf(type));
                            hexfield.getMovarray().get(devilpos).set(0, type.getHex());
                            hexfield.getMovarray().get(devilpos).set(1, type.getHex());
                            if (path5.size() >= 2) {
                                hexfield.getMovarray().get(devilpos).set(2, path5.get(1));
                            } else {
                                hexfield.getMovarray().get(devilpos).set(2, type.getHex());
                            }


                            hexfield.getFlaglist2().get(devilpos).set(0, 2);
                            hexfield.getFlaglist2().get(devilpos).set(1, 0);
                            hexfield.getFlaglist1().get(devilpos).set(0, 0);
                            hexfield.getFlaglist1().get(devilpos).set(1, 1);


                        } else {
                            path2 = hexfield.getPath4(hexfield.getField()[type.getHex().getCoord()[0]][type.getHex().getCoord()[1]],
                                    hexfield.getField()[herpos.getCoord()[0]][herpos.getCoord()[1]], 2, devilpos);
                            path5 = hexfield.getPath5(hexfield.getField()[type.getHex().getCoord()[0]][type.getHex().getCoord()[1]],
                                    hexfield.getField()[herpos.getCoord()[0]][herpos.getCoord()[1]], devilpos);
                            if (path2.size() > 4 && path2.size() <= 8) {
                                hexfield.getMovarray().get(devilpos).set(0, type.getHex());
                                hexfield.getMovarray().get(devilpos).set(1, path2.get(1));
                                hexfield.getMovarray().get(devilpos).set(2, path2.get(2));
                                hexfield.getFlaglist2().get(devilpos).set(0, 0);
                                hexfield.getFlaglist2().get(devilpos).set(1, 0);
                                hexfield.getFlaglist1().get(devilpos).set(0, 1);
                                hexfield.getFlaglist1().get(devilpos).set(1, 1);

                            } else if (path2.size() == 4) {
                                hexfield.getMovarray().get(devilpos).set(0, type.getHex());
                                hexfield.getMovarray().get(devilpos).set(1, path2.get(1));
                                hexfield.getMovarray().get(devilpos).set(2, path2.get(1));
                                if (hexfield.Hit(hexfield.getHero_top().getArmver(), ((Devil) type).getDamver()) == 1) {
                                    hexfield.getFlaglist2().get(devilpos).set(1, 1);
                                } else {
                                    hexfield.getFlaglist2().get(hexfield.getTypeList().indexOf(type)).set(1, 5);
                                }

                                hexfield.getFlaglist2().get(devilpos).set(0, 0);
                                hexfield.getFlaglist1().get(devilpos).set(0, 1);
                                hexfield.getFlaglist1().get(devilpos).set(1, 0);


                            } else if (path2.size() == 3 || path2.size() == 2) {
                                hexfield.getMovarray().get(devilpos).set(0, type.getHex());
                                hexfield.getMovarray().get(devilpos).set(1, type.getHex());
                                if (path5.size() >= 2) {
                                    hexfield.getMovarray().get(devilpos).set(2, path5.get(1));
                                } else {
                                    hexfield.getMovarray().get(devilpos).set(2, type.getHex());
                                }
                                if (hexfield.Hit(hexfield.getHero_top().getArmver(), ((Devil) type).getDamver()) == 1) {
                                    hexfield.getFlaglist2().get(devilpos).set(0, 1);
                                } else {
                                    hexfield.getFlaglist2().get(devilpos).set(0, 5);
                                }


                                hexfield.getFlaglist2().get(devilpos).set(1, 0);
                                hexfield.getFlaglist1().get(devilpos).set(0, 0);
                                hexfield.getFlaglist1().get(devilpos).set(1, 1);


                            }


                        }

                        path2.clear();
                        path4.clear();
                        path5.clear();
                    }


                } else if (type instanceof Warrior) {

                    if (Findpool(hexfield.getHitList(), hexfield.getTypeList()) != type) {
                        int warpos = hexfield.getTypeList().indexOf(type);
                        path1 = hexfield.getPath(hexfield.getField()[type.getHex().getCoord()[0]][type.getHex().getCoord()[1]],
                                hexfield.getField()[herpos.getCoord()[0]][herpos.getCoord()[1]], 1, warpos);
                        if (path1.size() >= 2 && path1.size() <= 8) {


                            if (Calcweight(path1) > 0) {
                                hexfield.getMovarray().get(warpos).set(0, type.getHex());
                                hexfield.getMovarray().get(warpos).set(1, path1.get(1));
                                hexfield.getFlaglist1().get(warpos).set(0, 1);
                                hexfield.getFlaglist2().get(warpos).set(0, 0);
                            } else if (Calcweight(path1) == 0) {
                                hexfield.getMovarray().get(warpos).set(0, type.getHex());
                                hexfield.getMovarray().get(warpos).set(1, type.getHex());
                                if (hexfield.Hit(hexfield.getHero_top().getArmver(), ((Warrior) type).getDamver()) == 1) {
                                    hexfield.getFlaglist2().get(warpos).set(0, 1);
                                } else {
                                    hexfield.getFlaglist2().get(warpos).set(0, 5);
                                }
                                hexfield.getFlaglist1().get(warpos).set(0, 0);
                            }
                        }
                        path1.clear();
                    }


                } else if (type instanceof Wizard) {
                    if (Findpool(hexfield.getHitList(), hexfield.getTypeList()) != type) {

                        int index = herpos.getCoord()[0];
                        int index2 = herpos.getCoord()[1];
                        if (index != type.getHex().getCoord()[0]) {

                            for (int j = 0; j < hexfield.getField().length; j++) {
                                if (hexfield.Activ0(hexfield.getField()[index][j].getActive()) != 0 && Math.abs(index - type.getHex().getCoord()[0]) <= 3
                                        && ((index2 >= type.getHex().getCoord()[1] && j < index2) || (index2 <= type.getHex().getCoord()[1] && j > index2))) {
                                    threads.add(new Mythread(hexfield.getField()[type.getHex().getCoord()[0]][type.getHex().getCoord()[1]], hexfield.getField()[index][j], 1, hexfield.getTypeList().indexOf(type)));


                                }


                            }

                            for (int i = 0; i < threads.size(); i++) {
                                try {
                                    threads.get(i).join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


                                if (way1.size() < 2) {
                                    way1 = threads.get(i).getResult();

                                } else {
                                    way2 = threads.get(i).getResult();


                                    if (way2.size() >= 2 && way2.size() < way1.size()) {
                                        way1 = way2;
                                    }
                                }
                            }


                            if (way1.size() >= 2 && way1.size() <= 4) {
                                hexfield.getMovarray().get(hexfield.getTypeList().indexOf(type)).set(0, type.getHex());
                                hexfield.getMovarray().get(hexfield.getTypeList().indexOf(type)).set(1, way1.get(1));
                                hexfield.getFlaglist1().get(hexfield.getTypeList().indexOf(type)).set(0, 1);
                                hexfield.getFlaglist2().get(hexfield.getTypeList().indexOf(type)).set(0, 0);

                            }


                        } else {
                            hexfield.getMovarray().get(hexfield.getTypeList().indexOf(type)).set(0, type.getHex());
                            hexfield.getMovarray().get(hexfield.getTypeList().indexOf(type)).set(1, type.getHex());
                            if (hexfield.Hit(hexfield.getHero_top().getArmver(), ((Wizard) type).getDamver()) == 1) {
                                hexfield.getFlaglist2().get(hexfield.getTypeList().indexOf(type)).set(0, 1);
                            } else {
                                hexfield.getFlaglist2().get(hexfield.getTypeList().indexOf(type)).set(0, 5);
                            }

                            hexfield.getFlaglist1().get(hexfield.getTypeList().indexOf(type)).set(0, 0);


                        }


                    }
                    threads.clear();
                    way1.clear();
                    way2.clear();
                }
            }


            hexfield.setAnimflag(1);

            if (hexfield.isLose()) {
                lose=true;
                if (save_inf.getTypeGame()){
                if (ConnectingToInternet())
                    databaseReference.child("xp").setValue(String.valueOf(save_inf.getHero().getXp() - scenario.getXP_SOLD())).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toasty.custom(getApplicationContext(), "You lose", R.drawable.lose, R.color.colorAccent, Toasty.LENGTH_LONG, true, true).show();

//                    Toast.makeText(getApplicationContext(), "You lose", Toast.LENGTH_LONG).show();


                            }
                        }
                    });

                scenario.setWin(-1);
              exit();
                }
                else
                {
                    save_inf.updateParHero(Hero.Type_xp,save_inf.getHero().getXp() - scenario.getXP_SOLD()  );
                    exit();
                }


            }

            if (hexfield.getTypeList().size() == 1) {

                if (scenario.getSTRAGE() - 1 <= hexfield.stage) {
                    if (save_inf.getTypeGame()){
                    if (ConnectingToInternet())
                        databaseReference.child("xp").setValue(String.valueOf(save_inf.getHero().getXp() + (hexfield.stage + 1) * scenario.getXP_SOLD() + scenario.sumUnit())).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toasty.custom(getApplicationContext(), "You win", R.drawable.win, R.color.appRegist, Toasty.LENGTH_LONG, true, true).show();


                                }
                            }
                        });
                    scenario.setWin(1);
                    exit();
                   }
                    else {
                        save_inf.updateParHero(Hero.Type_xp,save_inf.getHero().getXp() + (hexfield.stage + 1) * scenario.getXP_SOLD() + scenario.sumUnit());

                        exit();
                    }
                }


            }


        }

    }
    private void exit(){
        Intent intent = new Intent(getApplicationContext(), Difficulty.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    /*
 #Поиск гекса по весу в массиве пути
  */
    public Hex FindHex(ArrayList<Hex> path, int weight) {
        int count = 0;
        Hex hex = null;
        for (Hex bor : path) {
            if (path.indexOf(bor) != 0 && path.indexOf(bor) != path.size() - 1) {
                if (hexfield.Activ0(bor.getActive()) == 1||hexfield.Activ0(bor.getActive()) == 3||hexfield.Activ0(bor.getActive()) == 4) {
                    count += 1;
                } else if (bor.getActive() == 2) {
                    count += 2;
                }
            }
            if (count >= weight) {
                hex = path.get(path.indexOf(bor) + 1);
                break;
            }


        }
        return hex;
    }
    /*
        #Просчёт веса пути без учёта первого, предпоследнего и последнего гексов(вес гекса зависит от ландшафта)
         */
    public int Calcweight2(ArrayList<Hex> path) {
        int count = 0;
        for (Hex bor : path) {
            if (path.indexOf(bor) != 0 && path.indexOf(bor) !=  path.size() - 2 &&path.indexOf(bor) != path.size() - 1) {
                if (hexfield.Activ0(bor.getActive()) == 1||hexfield.Activ0(bor.getActive())==3||hexfield.Activ0(bor.getActive())==4) {
                    count += 1;
                } else if (hexfield.Activ0(bor.getActive()) == 2) {
                    count += 2;
                }
            }

        }
        return count;
    }
    /*
     #Настриавание маршрута героя
      */
    public void Turn() {
        if(hexfield.getAnimflag()==0){


            if(hexfield.getChosen()!=null && hexfield.Activ0(hexfield.getChosen().getActive())!=0){
                if(hexfield.getMovemain().size()>=1&&hexfield.getMovemain().get(0)!=null&&hexfield.Isneighbor(hexfield.getChosen(),hexfield.getMovemain().get(hexfield.Lasthex(hexfield.getMovemain())))&&
                        hexfield.Cross(hexfield.getMovemain(),hexfield.getChosen())
                ){
                    if(hexfield.getMovemain().size() == 3 && hexfield.getMovemain().get(2)==null){
                        hexfield.getMovemain().remove(2); }
                    hexfield.getMovemain().add(hexfield.getChosen());
                }
                else {
                    hexfield.setMovemain(hexfield.getPath(MainActivity.Findhero(hexfield.getTypeList()).getHex(), hexfield.getChosen(), 0, hexfield.getTypeList().indexOf(MainActivity.Findhero(hexfield.getTypeList()).getHex())));
                    if (hexfield.getMovemain().size() == 2) {
                        hexfield.getMovemain().add(null);

                    }
                    if (hexfield.getMovemain().size() ==1) {
                        hexfield.getMovemain().clear();
                        for (int i = 0; i < 3; i++) {
                            hexfield.getMovemain().add(null);

                        }
                    }
                }

            }
            else{
                hexfield.getMovemain().clear();
                for (int i = 0; i < 3; i++) {
                    hexfield.getMovemain().add(null);

                }

            }

            hexfield.setShow(true);}


    }
    /*
    #Выход из игры
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK & !lose & !isFinishing()){
            Dialog_closed();
        }

        return super.onKeyDown(keyCode, event);
    }
    private void Dialog_closed(){


        startService( new Intent(this, VibrateService.class));
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this,R.style.AlertDialogCustom);
        dialog.setTitle(getResources().getString(R.string.end)+"?").setMessage(getResources().getString(R.string.notcontinue)).setPositiveButton(getResources().getString(R.string.remain), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton(getResources().getString(R.string.leave), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exit=true;
                if (save_inf.getTypeGame()) {
                    if (ConnectingToInternet())
                        databaseReference.child("xp").setValue(String.valueOf(save_inf.getHero().getXp() - scenario.getXP_SOLD())).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                   exit();
                                }
                            }
                        });
                    else {
                        hexfield.setLose(true);
                        scenario.setWin(-1);
                        finish();
                    }
                }
                else {
                    save_inf.updateParHero(Hero.Type_xp,save_inf.getHero().getXp() - scenario.getXP_SOLD());
                    exit();
                }
            }
        }).show();
    }
    /*
     #Дополнительная информация о монстрах
      */
    public void Info() {
        Unit unit=Findunit(hexfield.getChosen(),hexfield.getTypeList(),hexfield.getMovarray(),0);
        Bundle args=new Bundle();
        int [] ggh = new int[0];
        if (unit !=null){
            if(unit instanceof Warrior){
                ggh=new int[]{((Warrior)unit).getHealth(),((Warrior)unit).getDamage(),((Warrior)unit).getMax_health(),(int)(((Warrior)unit).getMax_health()*0.1)};
                args.putString("type","Warrior");
            }
            else if(unit instanceof Wizard){
                ggh=new int[]{((Wizard)unit).getHealth(),((Wizard)unit).getDamage(),((Wizard)unit).getMax_health(),(int)(((Wizard)unit).getMax_health()*0.1)};
                args.putString("type","Wizard");
            } else if(unit instanceof Wolf){
                ggh=new int[]{((Wolf)unit).getHealth(),((Wolf)unit).getDamage(),((Wolf)unit).getMax_health(),(int)(((Wolf)unit).getMax_health()*0.1)};
                args.putString("type","Wolf");
            } else if (unit instanceof Devil){
                ggh=new int[]{((Devil)unit).getHealth(),((Devil)unit).getDamage(),((Devil)unit).getMax_health(),(int)(((Devil)unit).getMax_health()*0.1)};
                args.putString("type","Devil");
            }
            else if(unit instanceof Robber){
                ggh=new int[]{((Robber)unit).getHealth(),((Robber)unit).getDamage(),((Robber)unit).getMax_health(),(int)(((Robber)unit).getMax_health()*0.1)};
                args.putString("type","Robber");
            }

        }
        else{
            ggh=new int[]{-1,-1,-1,-1};
            if (hexfield.getChosen().getActive()/10>=1){
                switch (hexfield.Activ1(hexfield.getChosen().getActive())){
                    case 1: args.putString("type","Tunnel");break;
                    case 0: args.putString("type","Tunnel");break;
                    case 2: args.putString("type","Tunnel");break;
                    case 3: args.putString("type","Tunnel");break;
                    case 4: args.putString("type","Hut");break;
                    case 5: args.putString("type","Forge");break;
                    case 6: args.putString("type","Castle");
                        break;
                }
            }
            else {
                switch (hexfield.getChosen().getActive()){
                    case 1:args.putString("type","Plain");break;
                    case 2:args.putString("type","Hill");break;
                    case 3:args.putString("type","Sands");break;
                    case 4:args.putString("type","Tundra");break;
                    case 0:args.putString("type","Water");break;

                }

            }
        }
        Dialog_about dialog_about = new Dialog_about();
        args.putIntArray("info",ggh);
        dialog_about.setArguments(args);
        dialog_about.show(getSupportFragmentManager(),"hello");

    }
    @Override
    public boolean isFinishing() {
        return super.isFinishing();
    }
    /*
     #Проверка подключеня к интернету
      */
    public boolean ConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {

                        return true;
                    }

        }
        Toasty.error(getApplicationContext(),getResources().getString(R.string.nointernet), Toasty.LENGTH_LONG,true).show();
        return false;
    }
    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.pause();
        if (((!lose & !exit)||(!ConnectingToInternet()&save_inf.getTypeGame()))) {
            save_inf.putBoolean(Save_inf.RESUME_GAME, true);
            save_inf.putUser(Save_inf.RESUME_HERO_LOST_GAME,hexfield.getHero_top()   );

            if (scenka == 2) {


                List<String> ko=save_inf.getListStrang(scenario.getTYPELIST());

                ko.set(scenario.getNOWStrange(),save_inf.putTypelist_item(hexfield.getTypeList()));

                scenario.setTYPELIST(save_inf.putListString(ko));

            }else {

                scenario.setTYPELIST(save_inf.putTypelist_item(hexfield.getTypeList()));


                scenario.setACTIVIRYFIELD(save_inf.getStringActivity(hexfield.getActivefield()));



            }
            scenario.setNOWStrange(hexfield.stage);
            save_inf.putScenario(scenario,Save_inf.RESUME_SCENARIO_LOST_GAME);

        }else{
            save_inf.putBoolean(Save_inf.RESUME_GAME,false);

        }}
    /*
#Обработка точек касания для X1,Y1,X2,y2 для дальнейших действий(приближение-удаление, параллельный перенос карты, долгое зажатие, двойное касание)
*/
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int pointerCount = event.getPointerCount();
        hexfield.setX0 ((int) event.getX(0));
        hexfield.setY0(((int) event.getY(0)));

        if (pointerCount == 1) {


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    for (int i = 0; i < hexfield.getField().length; i++) {
                        for (int j = 0; j < hexfield.getField()[0].length; j++) {
                            if (hexfield.getField()[i][j].Checkhex(hexfield.getX0(), hexfield.getY0(), hexfield.getPeekfield()[hexfield.getField()[i][j].getCoord()[0]][hexfield.getField()[i][j].getCoord()[1]],k,l) == 1) {
                                if ( hexfield.getField()[i][j] != MainActivity.Findhero(hexfield.getTypeList()).getHex()) {
                                    hexfield.setChosen(hexfield.getField()[i][j]);
                                    if(hexfield.getPrehex()==hexfield.getChosen()){
                                        Turn();
                                        hexfield.setPrehex(null);
                                    }
                                    else{
                                        hexfield.setPrehex(hexfield.getField()[i][j]);
                                    }
                                    timer.start();

                                }
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:



                    if (hexfield.getX1() == 0 && hexfield.getY1() == 0) {

                        hexfield.setX1(hexfield.getX0());
                        hexfield.setY1(hexfield.getY0());
                    } else {
                        hexfield.setDx(hexfield.getX1() - hexfield.getX0());
                        hexfield.setDy(hexfield.getY1() - hexfield.getY0());
                        hexfield.setX1(hexfield.getX0());
                        hexfield.setY1(hexfield.getY0());

                    }


                    break;
                case MotionEvent.ACTION_UP:
                    timer.cancel();
                    hexfield.setX1(0);
                    hexfield.setY1(0);
                    hexfield.setDx(0);
                    hexfield.setDy(0);
                    hexfield.setLen(0);
                    break;

            }

        }
        else{
            hexfield.setX2((int) event.getX(1));
            hexfield.setY2((int) event.getY(1));
            switch (event.getAction()) {


                case MotionEvent.ACTION_MOVE:


                    if(hexfield.getLen()==0) {
                        hexfield.setLen(LongWay(hexfield.getX0(),hexfield.getX2(),hexfield.getY0(),hexfield.getY2()));


                    }
                    else {
                        if((float) (hexfield.getScale() * sqrt(sqrt(LongWay(hexfield.getX0(), hexfield.getX2(), hexfield.getY0(), hexfield.getY2()) / (hexfield.getLen())))) <2&&
                                (float) (hexfield.getScale() * sqrt(sqrt(LongWay(hexfield.getX0(), hexfield.getX2(), hexfield.getY0(), hexfield.getY2()) / (hexfield.getLen())))) >0.9) {


                            hexfield.setScale((float) (hexfield.getScale() * sqrt(sqrt(LongWay(hexfield.getX0(), hexfield.getX2(), hexfield.getY0(), hexfield.getY2()) / (hexfield.getLen())))));
                            hexfield.setLen(LongWay(hexfield.getX0(), hexfield.getX2(), hexfield.getY0(), hexfield.getY2()));
                        }
                    }
                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    hexfield.setLen(0);
                    break;
            }
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sc    :
            {
                DialogSc dialogSc= new DialogSc();
                dialogSc.show(getSupportFragmentManager(),TAG);
            }
            case R.id.top:
                if (pos) {
                    health_layout.setVisibility(View.GONE);
                    damage_layout.setVisibility(View.GONE);
                    armor_layout.setVisibility(View.GONE);

                }
                else
                {
                    health_layout.setVisibility(View.VISIBLE);
                    damage_layout.setVisibility(View.VISIBLE);
                    armor_layout.setVisibility(View.VISIBLE);               }
                pos=!pos;
                open_info_progress();break;
            case  R.id.armor:
            case R.id.damage:
            case R.id.health:
                if (pos1) {
                    pos1=false;
                    Shop_par shop_par = new Shop_par();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Save_inf.CONNECT_DIALOG_FOR_PARAM_TYPE, 1);
                    bundle.putSerializable(Hero.class.getSimpleName(), hexfield.getHero_top());
                    shop_par.setArguments(bundle);
                    shop_par.show(getSupportFragmentManager(), TAG);
                }
                break;
        }
    }

    public void open_info_progress() {
        armor_bar.setMax(hexfield.getHero_top().getMax_armver());
        armor_bar.setProgress(hexfield.getHero_top().getArmver());
        armor_text.setText("" + hexfield.getHero_top() .getArmver());
        armor.setText("" + hexfield.getHero_top() .getArmver());

        health_bar.setMax(hexfield.getHero_top().getHealth());
        health_bar.setProgress(hexfield.getHero_top() .getWarheal());
        health_text.setText("" + hexfield.getHero_top() .getWarheal());
        health.setText("" + hexfield.getHero_top() .getWarheal());
        damage_bar.setMax(hexfield.getHero_top() .getMax_damver());
        damage_bar.setProgress(hexfield.getHero_top() .getDamver());
        damage_text.setText("" + hexfield.getHero_top() .getDamver());
        damage.setText("" + hexfield.getHero_top() .getDamver());
        xpwar.setText(" "+hexfield.getHero_top().getWarxp());

    }

    @Override
    public void canal(Hero hero) {
        hexfield.getHero_top().Hero_param_update(hero);
        open_info_progress();
        pos1=true;
    }

    private class Mythread extends Thread{
        private  Hex begin;
        private Hex end;
        private  int mov;
        private  int index;
        private ArrayList<Hex> result = null;

        public ArrayList<Hex> getResult() {
            return result;
        }

        public Mythread(Hex begin, Hex end, int mov, int index){
            this.begin = begin;
            this.end = end;
            this.mov = mov;
            this.index = index;
            this.start();
        }

        @Override
        public void run() {
            super.run();
            result = hexfield.getPath2(begin, end, mov, index);
        }
    }
}
