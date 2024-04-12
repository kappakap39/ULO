package flp.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {

    public static double avg(double[] numberArray){
        double sum = 0;
        for (double number : numberArray) {
            sum += number;
        }
        return sum / numberArray.length;
    }

    public static double roundTo(double number, int place) {
        if (place < 0) throw new IllegalArgumentException();
        BigDecimal bigDecimal = new BigDecimal(Double.toString(number));
        bigDecimal = bigDecimal.setScale(place, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
    public static double roundTo(Double number, int place) {
        if (place < 0) throw new IllegalArgumentException();
        BigDecimal bigDecimal = new BigDecimal(Double.toString(number));
        bigDecimal = bigDecimal.setScale(place, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

}
