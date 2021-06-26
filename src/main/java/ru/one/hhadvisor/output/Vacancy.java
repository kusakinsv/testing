package ru.one.hhadvisor.output;

public class Vacancy {
    private  Integer id;
    private  String name;
    private  String area;
    private  Integer salaryFrom;
    private  Integer salaryTo;
    private  String salaryCurrency;
    private  Integer getAreaid;

    public Vacancy() {
    }

    public Vacancy(Integer id, String name, String area, Integer salaryFrom, Integer salaryTo, String salaryCcurrency, Integer getAreaid, Integer uniqueId) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.salaryFrom = salaryFrom;
        this.salaryTo = salaryTo;
        this.salaryCurrency = salaryCcurrency;
        this.getAreaid = getAreaid;
        this.uniqueId = uniqueId;
    }

    private  Integer uniqueId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(Integer salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public Integer getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(Integer salaryTo) {
        this.salaryTo = salaryTo;
    }

    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(String salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    public Integer getGetAreaid() {
        return getAreaid;
    }

    public void setGetAreaid(Integer getAreaid) {
        this.getAreaid = getAreaid;
    }

    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }
}
