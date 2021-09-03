package com.mycompany.movietimeline;

/**
 *
 * @author Sayma
 */
public class TimeStamp {
    
    int timeSeconds, hours, minutes, seconds;

    public TimeStamp(int timeSeconds) {
        this.timeSeconds = timeSeconds;
        doConversion();
    }
    
    private void doConversion(){
        hours = timeSeconds/3600;
        minutes = (timeSeconds - hours*3600)/60;
        seconds = (timeSeconds - hours*3600 - minutes*60);
        
    }
    
    @Override
    public String toString(){
        return String.format("%s:%s:%S", Utils.toDoubleDigit(hours), Utils.toDoubleDigit(minutes), Utils.toDoubleDigit(seconds));
    }
    
    public static void main(String[] args){
        TimeStamp ts1 = new TimeStamp(70);
        System.out.println(ts1);
    }
}
