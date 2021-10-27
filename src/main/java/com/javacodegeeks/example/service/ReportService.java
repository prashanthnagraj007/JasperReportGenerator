package com.javacodegeeks.example.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {


    List<Map<String, Object>> lineList = new ArrayList<>();

    @Bean
    CommandLineRunner runner() {
        return args -> {

            ObjectMapper mapper = new ObjectMapper();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("D:\\Desktop\\New folder (4)\\sample1.json"));
            System.out.println("object us" + obj);
            JsonNode nodes = mapper.readTree(new Gson().toJson(obj));
            ObjectMapper objectMapper = new ObjectMapper();
            for (JsonNode node : nodes) {
                //Student newJsonNode = objectMapper.treeToValue(node, Student.class);
                Map<String, Object> result = mapper.convertValue(node, new TypeReference<Map<String, Object>>() {
                });
                lineList.add(result);


            }

        };
    }

    public String exportReport(String reportFormat) throws IOException, JRException, ParseException {
        String path = "D:\\Desktop\\Report";
        FileWriter myWriter = new FileWriter("D:\\Desktop\\New folder (4)\\file.jrxml");
        myWriter.write(fResult());
        myWriter.close();
        System.out.println(fResult());
        File file = ResourceUtils.getFile("D:\\Desktop\\New folder (4)\\file.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lineList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Programmer");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\students.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\students.pdf");
        }
        return "report generated in path : " + path;
    }

    public static String fResult() throws UnsupportedEncodingException, FileNotFoundException, ParseException, JsonProcessingException {
        File file = new File("D:\\Desktop\\New folder (4)\\new.xml");
        File field = new File("D:\\Desktop\\New folder (4)\\field.xml");
        File staticText = new File("D:\\Desktop\\New folder (4)\\staticText.xml");
        File textField = new File("D:\\Desktop\\New folder (4)\\textField.xml");
        JSONParser parser1 = new JSONParser();

        String add = Read(file);

        String fieldTag = Read(field);
        String staticTextTag = Read(staticText);
        String textFieldTag = Read(textField);
        JSONParser parser2 = new JSONParser();
        Object object = parser2
                .parse(new FileReader("D:\\Desktop\\New folder (4)\\sample1.json"));
        System.out.println(object);

        List<String> list = JSONArrayToArrayList.jsonToList(object.toString());
        String fieldAdded = result1(add, fieldTag, list, "<background>");
        //System.out.println(fieldAdded);
        String staticTextAdded = result(fieldAdded, staticTextTag, list, "</band></columnHeader>");
        String textFieldAdded = result(staticTextAdded, textFieldTag, list, "</band></detail>");

        return textFieldAdded;
    }

    private static String result1(String xml, String add, List list, String tag) throws FileNotFoundException, JsonProcessingException, ParseException {
        JSONParser parser1 = new JSONParser();
        Object object = parser1
                .parse(new FileReader("D:\\Desktop\\New folder (4)\\sample1.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String, Object>> valueSet = objectMapper.readValue(object.toString(),
                new TypeReference<List<HashMap<String, Object>>>() {
                });

        List listOfValue = new ArrayList(valueSet.get(0).values());
        String re = null;
        StringBuilder both = null;
        for (int i = 0; i < listOfValue.size(); i++) {
            System.out.println(listOfValue.get(i).getClass().getName());
            // if (listOfValue.get(i).getClass().getName() == "java.lang.Integer") {
            String k = add.replace("java.lang.String", listOfValue.get(i).getClass().getName());
            System.out.println("replaced" + k);
            re = k.replace("A0", list.get(i).toString());
//            } else {
//                re = add.replace("A0", list.get(i).toString());
//            }
            both = new StringBuilder(xml)
                    .insert(xml.indexOf(tag), re);
            xml = both.toString();

            String s = both.toString();
        }
        return both.toString();
    }

    private static String result(String xml, String add, List list, String tag) {
        StringBuilder both = null;
        for (int i = 0; i < list.size(); i++) {
            String k = add.replace("D0", Integer.toString(i * 138));
            String re = k.replace("A0", list.get(i).toString());
            both = new StringBuilder(xml)
                    .insert(xml.indexOf(tag), re);
            xml = both.toString();

            String s = both.toString();
        }
        return both.toString();
    }

    private static String Read(File file) throws UnsupportedEncodingException {
        byte[] data;
        String input;
        try (FileInputStream fis = new FileInputStream(file)) {
            data = new byte[(int) file.length()];
            fis.read(data);
            input = new String(data, "UTF-8");

            return input;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}