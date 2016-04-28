package org.javarush.bigtask.bankomat.command;

import org.javarush.bigtask.bankomat.CashMachine;
import org.javarush.bigtask.bankomat.ConsoleHelper;
import org.javarush.bigtask.bankomat.exception.InterruptOperationException;

import java.util.ResourceBundle;

/**
 * Created by Alexey on 10.02.2016.
 */
class ExitCommand implements Command
{
    private ResourceBundle res =
            ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "exit");

    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("exit.question.y.n"));

        String inputString = ConsoleHelper.readString().toUpperCase();
        if (inputString != null)
            if (!"".equals(inputString))
                if (inputString.equals(res.getString("yes")))
                    ConsoleHelper.writeMessage(res.getString("thank.message"));
    }
}
