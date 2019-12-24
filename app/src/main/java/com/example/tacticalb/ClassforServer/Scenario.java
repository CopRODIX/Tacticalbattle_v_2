package com.example.tacticalb.ClassforServer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//Класс карт
public class Scenario implements Serializable    {
        @Expose
        @SerializedName("_ID")
        private int _ID;
        @Expose
        @SerializedName("USER")
        private String USER;
        @Expose
        @SerializedName("NAME")
        private String NAME;
        @Expose
        @SerializedName("TYPE")
        private int TYPE;
        @Expose
        @SerializedName("LEVEL")
        private int LEVEL;
        @Expose
        @SerializedName("XP_SOLD")
        private int XP_SOLD;
        @Expose
        @SerializedName("WARRIOR")
        private int  WARRIOR;
        @Expose
        @SerializedName("WIZARD")
        private int  WIZARD;
        @Expose
        @SerializedName("WOLF")

        private int  WOLF;
        @Expose
        @SerializedName("GHOST")
        private int GHOST;
        @Expose
        @SerializedName("DEVIL")
        private int DEVIL;
        @Expose
        @SerializedName("ROBBER")
        private int 	ROBBER;
        @Expose
        @SerializedName("STRAGE")
        private int STRAGE;
        @Expose
        @SerializedName("ACTIVEFIELD")
        private String ACTIVIRYFIELD;
        @Expose
        @SerializedName("TYPELIST")
        private String TYPELIST;
        @Expose
        @SerializedName("ABOUT")
        private String ABOUT;
        @Expose
        @SerializedName("DOWNLOADS")
        private  int DOWNLOADS;
        private int NOWStrange;
        private int win;

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getNOWStrange() {
        return NOWStrange;
    }

    public void setNOWStrange(int NOWStrange) {
        this.NOWStrange = NOWStrange;
    }

    public Scenario(int _ID, String USER, String NAME, int TYPE, int LEVEL, int XP_SOLD, int WARRIOR, int WIZARD, int WOLF, int GHOST, int DEVIL, int ROBBER, int STRAGE, String ACTIVIRYFIELD, String TYPELIST, String ABOUT, int DOWNLOADS) {
        this._ID = _ID;
        this.USER = USER;
        this.NAME = NAME;
        this.TYPE = TYPE;
        this.LEVEL = LEVEL;
        this.XP_SOLD = XP_SOLD;
        this.WARRIOR = WARRIOR;
        this.WIZARD = WIZARD;
        this.WOLF = WOLF;
        this.GHOST = GHOST;
        this.DEVIL = DEVIL;
        this.ROBBER = ROBBER;
        this.STRAGE = STRAGE;
        this.ACTIVIRYFIELD = ACTIVIRYFIELD;
        this.TYPELIST = TYPELIST;
        this.ABOUT = ABOUT;
        this.DOWNLOADS = DOWNLOADS;
        NOWStrange=0;
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    public int getDOWNLOADS() {
            return DOWNLOADS;
        }

        public void setDOWNLOADS(int DOWNLOADS) {
            this.DOWNLOADS = DOWNLOADS;
        }

        public int get_ID() {
            return _ID;
        }

        public void set_ID(int _ID) {
            this._ID = _ID;
        }

        public String getUSER() {
            return USER;
        }

        public void setUSER(String USER) {
            this.USER = USER;
        }

        public int getWARRIOR() {
            return WARRIOR;
        }

        public void setWARRIOR(int WARRIOR) {
            this.WARRIOR = WARRIOR;
        }

        public int getWIZARD() {
            return WIZARD;
        }

        public void setWIZARD(int WIZARD) {
            this.WIZARD = WIZARD;
        }

        public int getWOLF() {
            return WOLF;
        }
public int sumUnit(){
            return WARRIOR+WIZARD+WOLF+GHOST+DEVIL+ROBBER;
}
        public void setWOLF(int WOLF) {
            this.WOLF = WOLF;
        }

        public int getGHOST() {
            return GHOST;
        }

        public void setGHOST(int GHOST) {
            this.GHOST = GHOST;
        }

        public int getDEVIL() {
            return DEVIL;
        }

        public void setDEVIL(int DEVIL) {
            this.DEVIL = DEVIL;
        }

        public int getROBBER() {
            return ROBBER;
        }

        public void setROBBER(int ROBBER) {
            this.ROBBER = ROBBER;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public int getLEVEL() {
            return LEVEL;
        }

        public void setLEVEL(int LEVEL) {
            this.LEVEL = LEVEL;
        }

        public int getXP_SOLD() {
            return XP_SOLD;
        }

        public void setXP_SOLD(int XP_SOLD) {
            this.XP_SOLD = XP_SOLD;
        }

        public int getSTRAGE() {
            return STRAGE;
        }

        public void setSTRAGE(int STRAGE) {
            this.STRAGE = STRAGE;
        }

        public String getACTIVIRYFIELD() {
            return ACTIVIRYFIELD;
        }

        public void setACTIVIRYFIELD(String ACTIVIRYFIELD) {
            this.ACTIVIRYFIELD = ACTIVIRYFIELD;
        }

        public String getTYPELIST() {
            return TYPELIST;
        }

        public void setTYPELIST(String TYPELIST) {
            this.TYPELIST = TYPELIST;
        }

        public String getABOUT() {
            return ABOUT;
        }

        public void setABOUT(String ABOUT) {
            this.ABOUT = ABOUT;
        }


    }

