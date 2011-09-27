/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openxdata.modules.moveit.server.exceptions;

/**
 *
 * @author gmimano
 */
public class ParamNotSetException extends Exception{

    public ParamNotSetException() {
        super("Parameter not set in request");
    }

    public ParamNotSetException(String string) {
        super(string+ " parameter not set in request");
    }



}
