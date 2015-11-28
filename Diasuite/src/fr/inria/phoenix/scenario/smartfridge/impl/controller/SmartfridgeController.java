package fr.inria.phoenix.scenario.smartfridge.impl.controller;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.controller.smartfridgecontroller.AbstractSmartfridgeController;
import fr.inria.phoenix.diasuite.framework.context.smartfridgecontext.SmartfridgeContextValue;

/* (non-Javadoc)
 * The implementation of the SmartfridgeController context
 * @see fr.inria.phoenix.diasuite.framework.controller.smartfridgecontroller.AbstractSmartfridgeController
 */
public class SmartfridgeController extends AbstractSmartfridgeController {
    
    public SmartfridgeController(ServiceConfiguration serviceConfiguration) {
        super(serviceConfiguration);
    }

    /* (non-Javadoc)
     * @see fr.inria.phoenix.diasuite.framework.controller.smartfridgecontroller.AbstractSmartfridgeController#onSmartfridgeContext(SmartfridgeContextValue, DiscoverForSmartfridgeContext)
     */
    @Override
    protected void onSmartfridgeContext(SmartfridgeContextValue smartfridgeContext, DiscoverForSmartfridgeContext discover) {

    	discover.tablettes().anyOne().send(smartfridgeContext.value().getIsContact(), smartfridgeContext.value().getLocation());
    	
    }
}
