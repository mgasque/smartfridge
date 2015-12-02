package fr.inria.phoenix.scenario.smartfridge.impl.context;

import fr.inria.diagen.core.ServiceConfiguration;
import fr.inria.phoenix.diasuite.framework.context.smartfridgecontext.AbstractSmartfridgeContext;
import fr.inria.phoenix.diasuite.framework.datatype.smartfridgedata.SmartfridgeData;
import fr.inria.phoenix.diasuite.framework.device.contactsensor.StateFromContactSensor;

/* (non-Javadoc)
 * The implementation of the SmartfridgeContext context
 * @see fr.inria.phoenix.diasuite.framework.context.smartfridgecontext.AbstractSmartfridgeContext
 */
public class SmartfridgeContext extends AbstractSmartfridgeContext {
    
    public SmartfridgeContext(ServiceConfiguration serviceConfiguration) {
        super(serviceConfiguration);
    }
    
    /* (non-Javadoc)
     * @see fr.inria.phoenix.diasuite.framework.context.smartfridgecontext.AbstractSmartfridgeContext#onStateFromContactSensor(StateFromContactSensor)
     */
    @Override
    protected SmartfridgeData onStateFromContactSensor(StateFromContactSensor stateFromContactSensor) {
    	
    	SmartfridgeData message = new SmartfridgeData();
    	
    	message.setIsContact(stateFromContactSensor.value());
    	message.setLocation(stateFromContactSensor.sender().id());
    
    	return message;
    }
}
