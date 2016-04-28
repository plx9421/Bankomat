package org.javarush.bigtask.bankomat;

import org.javarush.bigtask.bankomat.command.CommandExecutor;
import org.javarush.bigtask.bankomat.exception.InterruptOperationException;

import java.util.Locale;

/**
 * Created by Alexey on 10.02.2016.
 */
public class CashMachine
{
    public static final String RESOURCE_PATH = "org.javarush.bigtask.bankomat.resources.";

    public static void main(String[] args)
    {
        Locale.setDefault(Locale.ENGLISH);
        try
        {
            Operation operation = Operation.LOGIN;
            do
            {
                CommandExecutor.execute(operation);
                operation = ConsoleHelper.askOperation();
            }
            while (!Operation.EXIT.equals(operation));
        }
        catch (InterruptOperationException e)
        {
            ConsoleHelper.printExitMessage();
        }
    }

}
