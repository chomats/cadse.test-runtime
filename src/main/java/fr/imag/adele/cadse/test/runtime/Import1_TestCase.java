/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * Copyright (C) 2006-2010 Adele Team/LIG/Grenoble University, France
 */
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
	
	@Test
	public void testimport2() throws Exception {
		String dir = System.getProperty("fr.image.adele.cadse.test.path");
		String resFile = dir+"/../workspace-2010-08-26-1531.jar";
		Bundle b = Activator.cxt.installBundle("file:"+resFile);
		b.start();
	}
}
