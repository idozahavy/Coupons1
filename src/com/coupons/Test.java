package com.coupons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import com.coupons.tests.AllTests;
import com.coupons.tests.Table100;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {

		/* !! choose at least one option to see all the prints fully !! */

		/*
		 * Writes the System prints into a file named 'output.txt'. This option is
		 * because the output is too long for console. Notepad++ is prefered for viewing
		 * the tables. This way the console will show only err prints which are all the
		 * unwanted prints.
		 */
		if (true) {
			File file = new File("output.txt");
			System.setOut(new PrintStream(file));
		}

		/*
		 * Disables coupons attribute from showing in tables, because writing all the
		 * coupons takes about 70k of letters.
		 */
		if (true) {
			Table100.noShowFields.add("coupons");
		}

		AllTests.main(args);
	}
}
