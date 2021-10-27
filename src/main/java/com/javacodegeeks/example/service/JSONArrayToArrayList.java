package com.javacodegeeks.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONArrayToArrayList {

    public static void main(String[] args) throws FileNotFoundException, ParseException, JsonProcessingException {
       //String jsonStr = "[{\"name\":\"mkyong.com\",\"messages\":[\"msg 1\",\"msg 2\",\"msg 3\"],\"age\":100}]";
        JSONParser parser1 = new JSONParser();
        Object object = parser1
                .parse(new FileReader("D:\\Downloads\\sample1.json"));
       // System.out.println(object);

        List<String> list=jsonToList(object.toString());
        //Set<String> list=jsonToList(jsonStr);


        System.out.println(list.get(0));



    }

    public static List jsonToList(String jsonStr) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String, Object>> cropDetailsList = objectMapper.readValue(jsonStr,
                new TypeReference<List<HashMap<String, Object>>>(){});

        //System.out.println("rhgsgs"+cropDetailsList.get(0).values());

        List<String>list=new ArrayList<>(cropDetailsList.get(0).keySet());
        //System.out.println(cropDetailsList.get(0));

       return list;

    }
}