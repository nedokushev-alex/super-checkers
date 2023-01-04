package kz.mathncode.domain;

import kz.mathncode.domain.enums.ActionType;
import kz.mathncode.domain.unit.Unit;

/**
 * @author Aleksandr Nedokushev
 * @created 04.01.2023
 */
public class Action {

    private ActionType actionType;

    private Unit victim;

    public Action(ActionType actionType, Unit victim) {

        this.actionType = actionType;
        this.victim = victim;
    }

    public ActionType getActionType() {

        return actionType;
    }

    public Unit getVictim() {

        return victim;
    }
}
