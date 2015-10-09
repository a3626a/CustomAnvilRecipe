package oortcloud.customanvilrecipes.core.proxy;

import oortcloud.customanvilrecipes.api.nei.NEIHandler;

public class ClientProxy extends CommonProxy {
	public void initNEI() {
		NEIHandler.init();
	}
}
