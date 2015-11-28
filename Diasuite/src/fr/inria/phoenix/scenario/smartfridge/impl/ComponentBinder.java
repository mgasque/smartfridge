package fr.inria.phoenix.scenario.smartfridge.impl;
        
import fr.inria.phoenix.diasuite.framework.context.smartfridgecontext.AbstractSmartfridgeContext;
import fr.inria.phoenix.diasuite.framework.controller.smartfridgecontroller.AbstractSmartfridgeController;
import fr.inria.phoenix.diasuite.framework.misc.AppComponentBinder;

/* (non-Javadoc)
 * The binder to provides the various components of the application
 * @see fr.inria.phoenix.diasuite.framework.misc.AppComponentBinder
 */
public class ComponentBinder extends AppComponentBinder {

	@Override
	public Class<? extends AbstractSmartfridgeContext> getSmartfridgeContextClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends AbstractSmartfridgeController> getSmartfridgeControllerClass() {
		// TODO Auto-generated method stub
		return null;
	}
}
