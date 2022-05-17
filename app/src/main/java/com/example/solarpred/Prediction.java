package com.example.solarpred;

public class Prediction {

    private int pred_seq;
    private int r_seq;
    private String pred_time;
    private double pred_aod;

    public Prediction(){}

    public Prediction(int pred_seq, int r_seq, String pred_time, double pred_aod) {
        this.pred_seq = pred_seq;
        this.r_seq = r_seq;
        this.pred_time = pred_time;
        this.pred_aod = pred_aod;
    }

    public int getPred_seq() {
        return pred_seq;
    }

    public void setPred_seq(int pred_seq) {
        this.pred_seq = pred_seq;
    }

    public int getR_seq() {
        return r_seq;
    }

    public void setR_seq(int r_seq) {
        this.r_seq = r_seq;
    }

    public String getPred_time() {
        return pred_time;
    }

    public void setPred_time(String pred_time) {
        this.pred_time = pred_time;
    }

    public double getPred_aod() {
        return pred_aod;
    }

    public void setPred_aod(double pred_aod) {
        this.pred_aod = pred_aod;
    }
}
