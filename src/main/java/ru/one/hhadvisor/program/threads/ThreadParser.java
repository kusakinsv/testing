package ru.one.hhadvisor.program.threads;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.one.hhadvisor.output.Vacancy;
import ru.one.hhadvisor.program.exp.Experience;
import ru.one.hhadvisor.program.exp.ModelForExperience;
import ru.one.hhadvisor.program.model.Models;
import ru.one.hhadvisor.services.VacancyParser;

import java.util.ArrayList;
import java.util.List;

public class ThreadParser extends Thread {
    //VacancyParser.restTemplate = new RestTemplate();
    public static List<Vacancy> listOfVacancies = new ArrayList<>();
    public static int integercountVacancy = 0;
    public static int threadCounter = 0;
    public static int counterIDs = 1; //default = 1
    final String expurl = "https://api.hh.ru/vacancies/";
    RestTemplate restTemplate = new RestTemplate();



    @Override
    public void run() {
        System.out.println("Начинаю поиск " + getName());

        if (VacancyParser.round == VacancyParser.countpages) VacancyParser.icount = VacancyParser.leftover; // ??
        for (int i = 0; i < VacancyParser.icount; i++) {
            VacancyParser.countProtector++;
            if (VacancyParser.response.getItems().get(i).getSalary().getFrom() == null && VacancyParser.response.getItems().get(i).getSalary().getTo() == null) continue;
            if (!VacancyParser.response.getItems().get(i).getSalary().getCurrency().equals("RUR")) continue;
            integercountVacancy++;
            listOfVacancies.add(new Vacancy(null, ThreadParser.counterIDs,
                    VacancyParser.response.getItems().get(i).getName(),
                    VacancyParser.response.getItems().get(i).getEmployer().getName(),
                    VacancyParser.response.getItems().get(i).getArea().getName(),
                    VacancyParser.response.getItems().get(i).getSalary().getFrom(),
                    VacancyParser.response.getItems().get(i).getSalary().getTo(),
                    VacancyParser.response.getItems().get(i).getSalary().getCurrency(),
                    null,
                    null,
                    Integer.parseInt(VacancyParser.response.getItems().get(i).getArea().getId()),
                    Integer.parseInt(VacancyParser.response.getItems().get(i).getId()))

            );
//            String queryUrl = expurl+VacancyParser.response.getItems().get(i).getId();
//            ModelForExperience responseExp = restTemplate.getForObject(queryUrl, ModelForExperience.class);
//            System.out.println(listOfVacancies.get(i).getName());
////            listOfVacancies.get(i).setExperienceId(responseExp.getExperience().getId());
////            listOfVacancies.get(i).setExperienceName(responseExp.getExperience().getName());
//            System.out.println("---------------");
////            listOfVacancies.get(i).setExperienceId("1111");
////            listOfVacancies.get(i).setExperienceName("0000");
            ThreadParser.counterIDs++;
            if (ThreadParser.counterIDs > 2000) break;
            if (VacancyParser.countProtector > 2000) break;
        }



        System.out.println("Поток " +getName()+ " завершен");
        threadCounter++;
    }

    public static List<Vacancy> getListOfVacancies() {
        return listOfVacancies;
    }

    public static void setListOfVacancies(List<Vacancy> listOfVacancies) {
        ThreadParser.listOfVacancies = listOfVacancies;
    }

    public static int getThreadCounter() {
        return threadCounter;
    }

    public static void setThreadCounter(int threadCounter) {
        ThreadParser.threadCounter = threadCounter;
    }
}
