package ru.one.hhadvisor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.one.hhadvisor.entity.repos.VacancyRepo;
import ru.one.hhadvisor.output.Vacancy;
import ru.one.hhadvisor.program.models.model.Models;
import ru.one.hhadvisor.program.statistics.MinMaxStat;
import ru.one.hhadvisor.services.VacancyParser;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("/")
public class Controller {


    //    private String url = "https://api.hh.ru/search";
//    private final String testurl = "https://api.hh.ru/vacancies?per_page=4&page=22&text=Java";
//    private final String hhurl = "https://api.hh.ru/vacancies";
//    private final String page1 = "&per_page=20";
//    private final String perpage1 = "&page=1";
    RestTemplate restTemplatethis = new RestTemplate();

    @Autowired
    private VacancyParser vacancyParser;

    @Autowired
    public Controller(VacancyParser vacancyParser) {
        this.vacancyParser = vacancyParser;
    }

    @Autowired
    public VacancyRepo vacancyRepo;

    @GetMapping("search") //  Погружение в БД
    public ResponseEntity searchParams(@RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "area", required = false) String area
    ) throws SQLException, InterruptedException {
        restoreDefaults();
//        VacancyParser parser = new VacancyParser();
        MinMaxStat stat = new MinMaxStat();
        boolean b;
        if (name == null && area == null) {
            System.out.println("Error: no parameters");
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("system", "no parameters");
            }});
        } else if (area == null) {
            List<Vacancy> vacancies = vacancyParser.doParseWithName(name);
            System.out.println("DB write operations....");
            vacancies.parallelStream().forEach(x -> vacancyRepo.save(x));
            //vacancyRepo.saveAll(vacancies); старый метод
            System.out.println("DB operations complete");
            if (vacancies.size() > 1) stat.doStat(vacancies);
            return ResponseEntity.ok(stat);
        } else if (name == null) {
            List<Vacancy> vacancies = vacancyParser.doParseWithArea(area);
            System.out.println("DB write operations....");
            vacancies.parallelStream().forEach(x -> vacancyRepo.save(x));
            System.out.println("DB operations complete");
            if (vacancies.size() > 1) stat.doStat(vacancies);
            return ResponseEntity.ok(stat);
        } else {
            List<Vacancy> vacancies = vacancyParser.doParseWithAreas(name, area);
            System.out.println("DB write operations....");
            vacancies.parallelStream().forEach(x -> vacancyRepo.save(x));
            //vacancyRepo.saveAll(vacancies); старый метод
            //DBWriter.toWrite(vacancies);
            //System.out.println("TOTAL " + ThreadSaver.vacancyListFoeDBSaver.size());
            System.out.println("DB operations complete");
            if (vacancies.size() > 1) stat.doStat(vacancies);
            return ResponseEntity.ok(stat);
        }
    }


    @GetMapping("take") //================Спрятал на время теста
    public List<Vacancy> take() {
        return StreamSupport
                .stream(vacancyRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void restoreDefaults() {
        VacancyParser.unionvaclist.clear();
        VacancyParser.countpages = 1; //возвращаем значение общей переменной
        VacancyParser.icount = vacancyParser.getPerPage();
        VacancyParser.countProtector = 1;
        VacancyParser.leftover = 0;
        VacancyParser.round = 0;
        VacancyParser.response = restTemplatethis.getForObject("https://api.hh.ru/vacancies", Models.class);
    }

    @GetMapping(value = "test")
    ResponseEntity<?> test() {
        return new ResponseEntity<>(HttpStatus.valueOf(200), HttpStatus.OK);
    }

}







