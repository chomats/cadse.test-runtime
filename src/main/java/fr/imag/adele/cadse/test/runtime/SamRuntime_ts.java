package fr.imag.adele.cadse.test.runtime;



import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;


@RunWith(Suite.class)
@SuiteClasses(value={
		SamRuntimeTestCase.class
})


public class SamRuntime_ts {
	public static Test suite() {
		return (Test) new JUnit4TestAdapter(SamRuntime_ts.class);
	}
}
