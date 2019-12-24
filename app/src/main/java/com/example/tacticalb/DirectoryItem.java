package com.example.tacticalb;

public class DirectoryItem {

        int id;
        String massege;
        String title;

        public DirectoryItem(int id, String massege, String title) {
            this.id = id;
            this.massege = massege;
            this.title = title;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMassege() {
        return massege;
    }

    public void setMassege(String massege) {
        this.massege = massege;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
