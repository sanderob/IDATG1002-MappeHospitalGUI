package no.ntnu.idatg2001.mappe_2_hospital.exceptions;

/**
 * Exception to be thrown when the switch cases in the factories are not able to find the inputed cases
 */
public class SwitchCaseException extends Throwable {

    private static final Long serialVersionUID = 1L;

    public SwitchCaseException(String errorMessage) {
        super(errorMessage);
    }
}
