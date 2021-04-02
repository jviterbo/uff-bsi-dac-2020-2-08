/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author viter
 */
@Stateful
@LocalBean
public class AsynCallerBean {

    public AsynCallerBean() {

    }

    @EJB
    private AsynCalculatorBean async;

    Future<String> futureResult;
    long startTime;

    @Asynchronous
    public void startOperation(String criteria) {

        startTime = System.currentTimeMillis();
        futureResult = async.calculate(criteria);
    }

    public String checkResult() {

        boolean ok = futureResult.isDone();

        if (ok) {
            return "ok";
        } else {
            long timeElapsed = System.currentTimeMillis() - startTime;
            long seconds = TimeUnit.MILLISECONDS.toSeconds(timeElapsed);
            return ("Decorreram " + Long.toString(seconds) + " segundos...");
        }

    }

    public void cancelOperation() {

        futureResult.cancel(true);

    }

    public String getResult() {

        String result;

        try {
            result = futureResult.get();
            System.out.println("AsynCallerBean: " + result);
        } catch (InterruptedException | ExecutionException ex) {
            result = "err";
        }
        return result;
    }

}
