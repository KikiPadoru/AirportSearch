package com.company;

import java.io.*;
import java.util.ArrayList;

class OneLine {
    String comparable;
    String value;

    public OneLine(String comparable, String value) {
        this.comparable = comparable;
        this.value = value;
    }
}

class SortArrayList {
    private final ArrayList<OneLine> OurLines;

    public SortArrayList() {
        OurLines = new ArrayList<OneLine>();
    }

    public void AddSortArrayList(String comparable, String value) {
        boolean flag = true;
        for (int i = 0; i < OurLines.size() && flag; i++) {
            String a = OurLines.get(i).comparable;
            if (a.compareTo(comparable) >= 0) {
                OurLines.add(i, new OneLine(comparable, value));
                flag = false;
            }
        }
        if (OurLines.size() == 0) {
            OurLines.add(new OneLine(comparable, value));
            return;
        }
        if (flag) {
            OurLines.add(new OneLine(comparable, value));
        }
    }

    public int getsize() {
        return OurLines.size();
    }

    public String getcomparable(int index) {
        return OurLines.get(index).comparable;
    }

    public String getvalue(int index) {
        return OurLines.get(index).value;
    }
}

class AirportSearch {
    private int searchСolumn;

    private SortArrayList OurLines;
    private String fileName;
    private String line = "";

    public AirportSearch(String fileName, int searchСolumn) {
        this.fileName = fileName;
        this.searchСolumn = searchСolumn;
        this.OurLines = new SortArrayList();
    }

    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0) ? "" : (s.substring(1, s.length() - 1));
    }

    public AirportSearch(String fileName) {
        this.fileName = fileName;
        this.searchСolumn = 0;
        this.OurLines = new SortArrayList();
    }

    public void find() throws IOException {
        BufferedReader readerTerminal = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите строку: ");
        String wordSearch = readerTerminal.readLine();
        int numberOfLines = 0;

        long start = System.nanoTime();

        BufferedReader reader = new BufferedReader(new FileReader(fileName));


        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            boolean flag = false;
            String[] arrWords = line.split(",");
            if (arrWords[searchСolumn].charAt(0) == '\"') {
                arrWords[searchСolumn] = removeLastChar(arrWords[searchСolumn]);
            }
            if (arrWords[searchСolumn].startsWith(wordSearch)) {
                numberOfLines++;
                flag = true;
            }
            if (flag) {
                OurLines.AddSortArrayList(arrWords[searchСolumn], line);
            }
        }
        long finish = System.nanoTime();
        for (int i = 0; i < OurLines.getsize(); i++) {
            System.out.println(OurLines.getvalue(i));
        }
        System.out.println("Колличество найденных строк: " + numberOfLines + " Время, затраченное на поиск: " + ((finish - start) / 1000000)+" мс.");
        reader.close();
    }
}


public class Main {

    public static void main(String[] args) throws IOException {
        AirportSearch airportSearch;
        if (args.length == 1) {
            airportSearch = new AirportSearch(args[0]);//"static/airports.dat"
        }else if(args.length == 2){
            airportSearch = new AirportSearch(args[0], Integer.valueOf(args[1]));
        } else {
            airportSearch = new AirportSearch("static/airports.dat",13);
        }
        airportSearch.find();
    }
}
