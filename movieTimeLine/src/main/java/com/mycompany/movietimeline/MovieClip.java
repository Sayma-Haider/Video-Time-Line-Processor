package com.mycompany.movietimeline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sayma
 */
public class MovieClip {
    
    String name;
    int totalSeconds, offset, hours, minutes, seconds;
    
    TimeStamp startTimeStamp, endTimeStamp;
    TimeStampSpecifier specifier;

    public MovieClip(String name, int hours, int minutes, int seconds, TimeStampSpecifier specifier) {
        this.name = name;
        setTotalSeconds(hours*3600 + minutes*60 + seconds);
        this.offset = 0;
        this.specifier = specifier;
    }

    public MovieClip(String name, int hours, int minutes, int seconds) {
        this(name, hours, minutes, seconds, TimeStampSpecifier.DURATION);
    }
    
    public MovieClip setTimeStampSpecifier(TimeStampSpecifier specifier){
        this.specifier = specifier;
        return this;
    }

    public MovieClip setTotalSeconds(int totalSeconds) {
        this.totalSeconds = totalSeconds;
        hours = totalSeconds/3600;
        minutes = (totalSeconds - hours*3600)/60;
        seconds = (totalSeconds - hours*3600 - minutes*60);
        endTimeStamp = new TimeStamp(offset + totalSeconds);
        return this;
    }

    public MovieClip setOffset(int offset) {
        this.offset = offset;
        startTimeStamp = new TimeStamp(offset);
        endTimeStamp = new TimeStamp(offset + getTotalSeconds());
        return this;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public TimeStamp getStartTimeStamp() {
        if(startTimeStamp == null)
            startTimeStamp = new TimeStamp(offset);
        return startTimeStamp;
    }

    public TimeStamp getEndTimeStamp() {
        if(endTimeStamp == null)
            endTimeStamp = new TimeStamp(offset);
        return endTimeStamp;
    }
    
    @Override
    public String toString(){
        String str;
        
        switch(specifier){
            case START_TIME:
                str = "%s %s".formatted(getStartTimeStamp(), name);
                break;
            case END_TIME:
                str = "%s %s".formatted(getEndTimeStamp(), name);
                break;
            default:
                str = "%s %s".formatted(new TimeStamp(getTotalSeconds()), name);
                break;
        }
        return str;
    }
    
    public static MovieClip parseString(String str){
        String temp = str.trim();
        /*int index = 0;
        
        for(int i = 0; i < temp.length(); i++)
        {
            if(temp.charAt(i) == ' '){
                index = i;
                break;
            }
        }*/
        
        int index = temp.indexOf(' ');
        
        String timeStampStr = temp.substring(0, index);
        String[] args = timeStampStr.split(":");
        String name = temp.substring(index).trim();
        
        if(args.length == 3)
            return new MovieClip(name, Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        else if(args.length == 2)
            return new MovieClip(name, 0, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        else
            throw new RuntimeException("Time stamp not properly formatted");
    }
    
    public static void main(String[] args){
        /*MovieClip clip1 = new MovieClip("Doctor Strange", 2, 35, 62);
        System.out.println(clip1);
        clip1.setTimeStampSpecifier(TimeStampSpecifier.START_TIME);
        System.out.println(clip1);
        clip1.setOffset(355);
        System.out.println(clip1);
        clip1.setTimeStampSpecifier(TimeStampSpecifier.END_TIME);
        System.out.println(clip1);
        
        MovieClip clip1 = MovieClip.parseString("0:2:38 Iron Man");
        System.out.println(clip1);*/
        
        File file1 = new File("./src/main/java/resources/test.txt");
        //System.out.println(file1);
        
        Scanner scanner;
        try {
            scanner = new Scanner(file1);
            while(scanner.hasNextLine()){
                MovieClip clip1 = MovieClip.parseString(scanner.nextLine());
                System.out.println(clip1);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
}
