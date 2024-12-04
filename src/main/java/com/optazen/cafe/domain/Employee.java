package com.optazen.cafe.domain;

import ai.timefold.solver.core.api.domain.lookup.PlanningId;

import java.time.DayOfWeek;
import java.util.List;

public class Employee {
    @PlanningId
    private String name;
    private List<String> skills;
    private List<DayOfWeek> workdays;

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<DayOfWeek> getWorkdays() {
        return workdays;
    }

    public void setWorkdays(List<DayOfWeek> workdays) {
        this.workdays = workdays;
    }
}
