/*
 * File is a modified version of the original DoubleData Java class file.
 * File modified by: Matthew Burr, Justin Homsi as students at Edith Cowan University
 * For use in the Applied IT Project 2018
 */

package ec.app.BTEvolve;
import ec.util.*;
import ec.*;
import ec.gp.*;

public class StringData extends GPData
    {
    public double x;    // return value

    public void copyTo(final GPData gpd)   // copy my stuff to another DoubleData
        { ((StringData)gpd).x = x; }
    }
