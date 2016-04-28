package org.javarush.bigtask.bankomat.command;

import org.javarush.bigtask.bankomat.exception.InterruptOperationException;

/**
 * Created by Alexey on 10.02.2016.
 */
interface Command
{
    public void execute() throws InterruptOperationException;
}
