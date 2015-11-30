package fr.inria.phoenix.scenario.smartfridge.impl.controller;

import java.util.ArrayList;
import java.util.List;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.controller.smartfridgecontroller.AbstractSmartfridgeController;
import fr.inria.phoenix.diasuite.framework.context.smartfridgecontext.SmartfridgeContextValue;
import fr.inria.phoenix.diasuite.framework.datatype.contact.Contact;
import fr.inria.phoenix.diasuite.framework.datatype.file.File;

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

    	Contact contact = new Contact();
    	String title = new String();
		List<File> attachments = new ArrayList<File>();
    	
    	String message = new String();
    	message = smartfridgeContext.value().getIsContact().toString() + smartfridgeContext.value().getLocation();
    	
    	
		discover.messengers().anyOne().sendMessage(contact, title, message, attachments);
    	//discover.tablettes().anyOne().sendMessageToTablette(smartfridgeContext.value().getIsContact(), smartfridgeContext.value().getLocation());
    	
    }
}
