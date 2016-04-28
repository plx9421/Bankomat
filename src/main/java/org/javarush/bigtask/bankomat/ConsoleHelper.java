package org.javarush.bigtask.bankomat;

import org.javarush.bigtask.bankomat.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

/**
 * Created by Alexey on 10.02.2016.
 */
public class ConsoleHelper
{
    private static ResourceBundle res =
            ResourceBundle.getBundle("com.javarush.test.level26.lesson15.big01.resources.common");

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message)
    {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException
    {
        String inputString = null;
        try
        {
            inputString = reader.readLine();
            if ("EXIT".equals(inputString.toUpperCase()))
            {
//                writeMessage(res.getString("the.end"));
                throw new InterruptOperationException();
            }
        }
        catch (IOException e)
        {
            //e.printStackTrace();
        }
        return inputString;
    }

    public static String askCurrencyCode() throws InterruptOperationException
    {
        String inPut;

        do
        {
            writeMessage(res.getString("choose.currency.code"));
            inPut = readString();
            if (inPut.length() == 3) break;
            writeMessage(res.getString("invalid.data"));
        }
        while (true);

        return inPut.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException
    {
        String[] result;
        String inputLine;
        do
        {
            writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
            inputLine = readString();

            if (inputLine != null)
                if ("".equals(inputLine)) continue;

            result = inputLine.split(" ");
            if (result.length == 2) break;
        }
        while (true);


        return result;
    }

    public static Operation askOperation() throws InterruptOperationException
    {
        int i = 0;

        do
        {
            writeMessage(res.getString("choose.operation"));
            writeMessage(res.getString("operation.INFO")    + " - 1, " +
                    res.getString("operation.DEPOSIT")      + " - 2, " +
                    res.getString("operation.WITHDRAW")     + " - 3, " +
                    res.getString("operation.EXIT")         + " - 4");

            try
            {
                i = Integer.parseInt(readString());
                if (i > 0 && i < 5) break;
                else continue;
            }
            catch (NumberFormatException e)
            {
            }
        }
        while (true);

        return Operation.getAllowableOperationByOrdinal(i);
    }

    public static void printExitMessage(){
        writeMessage(res.getString("the.end"));
    }
}
