package com.optazen.cafe.solver;

import ai.timefold.solver.test.api.score.stream.ConstraintVerifier;
import com.optazen.cafe.domain.Employee;
import com.optazen.cafe.domain.Schedule;
import com.optazen.cafe.domain.Shift;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

class ScheduleConstraintProviderTest {
    ConstraintVerifier<ScheduleConstraintProvider, Schedule> constraintVerifier = ConstraintVerifier.build(
            new ScheduleConstraintProvider(), Schedule.class, Shift.class);
    @Test
    void skillMatch() {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setSkills(List.of("Barista"));
        employee.setWorkdays(List.of(DayOfWeek.MONDAY));

        Shift shift = new Shift();
        shift.setStartDateTime(LocalDateTime.of(2024, 12, 10, 19, 0));
        shift.setDuration(Duration.ofHours(4));
        shift.setSkills(List.of("Cleaner"));
        shift.setEmployee(employee);

        constraintVerifier
                .verifyThat(ScheduleConstraintProvider::skillMatch)
                .given(shift)
                .penalizesBy(1);

        Employee employee2 = new Employee();
        employee2.setName("Mary Smith");
        employee2.setSkills(List.of("Cleaner"));
        employee2.setWorkdays(List.of(DayOfWeek.MONDAY));

        shift.setEmployee(employee2);

        constraintVerifier
                .verifyThat(ScheduleConstraintProvider::skillMatch)
                .given(shift)
                .penalizesBy(0);
    }
}