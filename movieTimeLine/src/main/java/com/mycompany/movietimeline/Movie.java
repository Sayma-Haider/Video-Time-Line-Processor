package com.mycompany.movietimeline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Sayma
 */
public class Movie {
    String movieName;
    ArrayList<MovieClip> movieClips;
    TimeStampSpecifier specifier;
    int totalSecondsElapsed = 0;

    public Movie(String movieName, TimeStampSpecifier specifier) {
        this.movieName = movieName;
        this.specifier = specifier;
        this.movieClips = new ArrayList<>();
    }

    public Movie setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public Movie setTimeStampSpecifier(TimeStampSpecifier specifier) {
        this.specifier = specifier;
        return this;
    }
    
    
    public Movie addMovieClip(MovieClip clip){
        clip.setTimeStampSpecifier(specifier);
        clip.setOffset(totalSecondsElapsed);
        totalSecondsElapsed += clip.getTotalSeconds();
        movieClips.add(clip);
        return this;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(movieClips.size()*15);
        String str = "MOVIE: %s\n".formatted(movieName);
        sb.append(str).append(System.lineSeparator());
        sb.append("*".repeat(str.length())).append(System.lineSeparator());
        for(MovieClip clip : movieClips){
            sb.append(clip.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }
    
    public void saveToFile(String fileName){
        File textFile = new File(fileName);
        
        try{
            FileWriter writer = new FileWriter(textFile);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(toString());
            out.flush();
            out.close();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public static Movie parseTextFile(String fileName, TimeStampSpecifier inputSpecifier, TimeStampSpecifier outputSpecifier){
        Movie movie = new Movie("null", outputSpecifier);
        
        try{
            Scanner scanner = new Scanner(new File(fileName));
            while(scanner.hasNextLine()){
                MovieClip clip = MovieClip.parseString(scanner.nextLine());
                clip.setTimeStampSpecifier(inputSpecifier);
                movie.addMovieClip(clip);
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        
        return movie;
    }
    
    public static void main(String[] args){
//        MovieClip clip1 = MovieClip.parseString("0:2:3 movie1");
//        MovieClip clip2 = MovieClip.parseString("0:13:3 movie2");
//        MovieClip clip3 = MovieClip.parseString("0:22:3 movie3");
//        
//        Movie movie = new Movie("Joined movies", TimeStampSpecifier.START_TIME);
//        movie.addMovieClip(clip1)
//                .addMovieClip(clip2)
//                .addMovieClip(clip3);
//        
//        System.out.println(movie);
//        
//        movie.saveToFile("./src/main/java/resources/test1.txt");

//          String fileName = "./src/main/java/resources/test1.txt";
//          
//          Movie movie = Movie.parseTextFile(fileName, TimeStampSpecifier.DURATION, TimeStampSpecifier.START_TIME);
//          movie.setMovieName("Movie 1");
//          System.out.println(movie);

            String fileName = "./src/main/java/resources/example.txt";
            Movie movie = Movie.parseTextFile(fileName, TimeStampSpecifier.DURATION, TimeStampSpecifier.START_TIME);
            movie.setMovieName("Movies");
            movie.saveToFile("./src/main/java/resources/example_timelines.txt");
    }
}
