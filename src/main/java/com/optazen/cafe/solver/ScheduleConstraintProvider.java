package com.optazen.cafe.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import com.optazen.cafe.domain.Shift;
import org.jspecify.annotations.NonNull;


public class ScheduleConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint @NonNull [] defineConstraints(@NonNull ConstraintFactory constraintFactory) {
        return new Constraint[]{
                oneShiftADay(constraintFactory),
                oneShiftAtATime(constraintFactory),
                skillMatch(constraintFactory),
                worktime(constraintFactory),
        };
    }

    protected Constraint oneShiftADay(@NonNull ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Shift.class,
                        Joiners.equal(shift -> shift.getStartDateTime().toLocalDate()),
                        Joiners.equal(Shift::getEmployee))
                .penalize(HardSoftScore.ofHard(1))
                .asConstraint("One shift a day");
    }

    protected Constraint worktime(@NonNull ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Shift.class)
                .filter(shift -> !shift.getEmployee().getWorkdays().contains(shift.getStartDateTime().getDayOfWeek()))
                .penalize(HardSoftScore.ofHard(1))
                .asConstraint("Work time");
    }

    protected Constraint skillMatch(@NonNull ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Shift.class)
                .filter(shift -> !shift.getEmployee().getSkills().containsAll(shift.getSkills()))
                .penalize(HardSoftScore.ofHard(1))
                .asConstraint("Skill match");
    }

    protected Constraint oneShiftAtATime(@NonNull ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Shift.class,
                        Joiners.equal(Shift::getEmployee),
                        Joiners.overlapping(Shift::getStartDateTime, shift -> shift.getStartDateTime().plus(shift.getDuration())))
                .penalize(HardSoftScore.ofHard(1))
                .asConstraint("One Shift at a time");
    }
}