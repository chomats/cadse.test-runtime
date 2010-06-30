package fr.imag.adele.cadse.test.runtime;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.osgi.framework.Bundle;

import fr.imag.adele.cadse.core.LogicalWorkspace;
import fr.imag.adele.cadse.core.WSModelState;
import fr.imag.adele.cadse.core.impl.CadseCore;

@RunWith(JUnit4.class)
public class Import1_TestCase extends TestCase {

	@BeforeClass
	public static void beforeClass() throws InterruptedException {
		while (true) {
			LogicalWorkspace wl = CadseCore.getLogicalWorkspace();
			if (wl != null && wl.getState() == WSModelState.RUN)
				return;
			Thread.sleep(100);
		}
	}
	
	@Test
	public void testname() throws Exception {
		String dir = System.getProperty("fr.image.adele.cadse.test.path");
		String resFile = dir+"/../workspace.zip";
		Bundle b = Activator.cxt.installBundle("file:"+resFile);
		b.start();
	}
}
