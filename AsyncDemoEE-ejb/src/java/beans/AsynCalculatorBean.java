/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author viter
 */
@Stateless
@LocalBean
public class AsynCalculatorBean {

    public AsynCalculatorBean() {

    }

    @Asynchronous
    public Future<String> calculate(String criteria) {

        long startTime = System.currentTimeMillis();

        int k = 0;

        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 10000; j++) {
                for (int m = 0; m < 100000; m++) {
                    for (int n = 0; n < 100000; n++) {
                    }
                }
                k++;
            }
        }
        long timeElapsed = System.currentTimeMillis() - startTime;

        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeElapsed);

        String result = "tempo de cálculo = " + Long.toString(seconds) + " segundos";
        System.out.println("AsynCalculatorBean: " + result);
        return new AsyncResult<>(result);
    }
}
