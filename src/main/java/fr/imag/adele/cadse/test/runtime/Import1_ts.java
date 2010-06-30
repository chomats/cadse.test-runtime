package fr.imag.adele.cadse.test.runtime;



import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;


@RunWith(Suite.class)
@SuiteClasses(value={
		Import1_TestCase.class
})


public class Import1_ts {
	public static Test suite() {
		return (Test) new JUnit4TestAdapter(Import1_ts.class);
	}
}
