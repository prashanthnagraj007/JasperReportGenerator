package com.javacodegeeks.example.controller;

import com.javacodegeeks.example.service.ReportService;
import net.minidev.json.parser.ParseException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class JasperReportController {
    @Autowired
    private ReportService service;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Java Code Geeks!";
    }


    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) throws IOException, JRException, JRException, ParseException {
        return service.exportReport(format);
    }

}
