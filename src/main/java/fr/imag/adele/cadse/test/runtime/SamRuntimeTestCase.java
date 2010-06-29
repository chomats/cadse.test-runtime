package fr.imag.adele.cadse.test.runtime;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import fr.imag.adele.cadse.core.CadseDomain;
import fr.imag.adele.cadse.core.CadseGCST;
import fr.imag.adele.cadse.core.Item;
import fr.imag.adele.cadse.core.ItemType;
import fr.imag.adele.cadse.core.LogicalWorkspace;
import fr.imag.adele.cadse.core.impl.CadseCore;
import fr.imag.adele.cadse.core.transaction.LogicalWorkspaceTransaction;
import fr.imag.adele.cadse.core.ui.view.NewContext;
import fr.imag.adele.sam.composite.SamCompositeCST;
import fr.imag.adele.sam.core.SamCoreCST;
import junit.framework.TestCase;
import lig.adele.sam.selecta.service.selection.utils.Constraint;
import lig.adele.sam.selecta.service.selection.utils.SelectaUtils;
import lig.adele.sam.selecta.service.selection.utils.SelectionAlgorithmUtils;

public class SamRuntimeTestCase extends TestCase {

	
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
