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

import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import fr.imag.adele.cadse.core.CadseDomain;
import fr.imag.adele.cadse.core.CadseGCST;
import fr.imag.adele.cadse.core.Item;
import fr.imag.adele.cadse.core.ItemType;
import fr.imag.adele.cadse.core.LogicalWorkspace;
import fr.imag.adele.cadse.core.WSModelState;
import fr.imag.adele.cadse.core.impl.CadseCore;
import fr.imag.adele.cadse.core.transaction.LogicalWorkspaceTransaction;
import fr.imag.adele.cadse.core.ui.view.NewContext;
import fr.imag.adele.sam.composite.SamCompositeCST;
import fr.imag.adele.sam.core.SamCoreCST;
import junit.framework.TestCase;
import lig.adele.sam.selecta.service.selection.utils.Constraint;
import lig.adele.sam.selecta.service.selection.utils.SelectaUtils;
import lig.adele.sam.selecta.service.selection.utils.SelectionAlgorithmUtils;

@RunWith(JUnit4.class)
public class SamRuntimeTestCase extends TestCase {

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
		CadseDomain cadseDomain = CadseCore.getCadseDomain();
		LogicalWorkspace lw = cadseDomain.getLogicalWorkspace();
		System.out.println("LogicalWS: " + lw.toString());
		
		LogicalWorkspaceTransaction t = lw.createTransaction();
		
		Item specItem = t.createItem(SamCoreCST.SERVICE_SPECIFICATION, null, null);
		specItem.setName("Specification1");
		System.out.println("Specification item: " + specItem.getName());
		
		t.commit();
		specItem = specItem.getBaseItem();
		
		t = lw.createTransaction();
		NewContext c = new NewContext(specItem, SamCoreCST.SERVICE_SPECIFICATION_lt_PROVIDED_BY, SamCoreCST.SERVICE_IMPLEMENTATION);
		c.setGroupHead((ItemType) specItem, SamCoreCST.SERVICE_SPECIFICATION_lt_PROVIDED_BY);
		
		Item implItem = t.createItem(c);
		implItem.setName("Implementation1");
		implItem.setAttribute(CadseGCST.GROUP_EXT_ITEM_lt_MEMBER_OF, specItem);
		System.out.println("Implementation item: " + implItem.getName() + 
				"   /   " + implItem.getPartParent() + 
				"   /   " + implItem.getGroup());
		
		Item compositeItem = t.createItem(SamCompositeCST.COMPOSITE, specItem, SamCoreCST.SERVICE_SPECIFICATION_lt_PROVIDED_BY);
		compositeItem.setName("Composite1");
		System.out.println("Composite item: " + compositeItem.getName());
		
		t.commit();
		compositeItem = compositeItem.getBaseItem();
		implItem = implItem.getBaseItem();
		
		
		if ((compositeItem != null)) {
			// ask the question backtrack option or not
			// the response is given like parameters in the
			// executeSelectionAlgorithm method
			// false is the default
			boolean backTrackOption = true;
			// on execute l'algorithme
			Hashtable<String, List<Constraint>> constraintsMAP = new Hashtable<String, List<Constraint>>();
			Hashtable<Item, List<Constraint>> constrainsOfChoosedItem = new Hashtable<Item, List<Constraint>>();
			Hashtable<Item, Set<Item>> providedSpecification_ChoosedImplementation = new Hashtable<Item, Set<Item>>();

			// Utils.executeSelectionAlgorithm(compositeItem,
			// constraintsMAP, constrainsOfChoosedItem,
			// providedSpecification_ChoosedImplementation, false);
			SelectionAlgorithmUtils.executeSelectionAlgorithm(
					compositeItem, constraintsMAP, constrainsOfChoosedItem,
					providedSpecification_ChoosedImplementation,
					backTrackOption);

			/*************************************************************************/
			// Affichage de l'�tat du composite
			/*************************************************************************/
			SelectaUtils.displaysCompositeState(compositeItem);
		}
	}
}
