package com.generic.controllertest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.generic.controller.WarehouseController;
import com.generic.controllertest.PseudoModelClasses.Shipment;
import com.generic.controllertest.PseudoModelClasses.Warehouse;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.PUSH;

import java.util.List;

public class ControllerTester {
	
	
	
	WarehouseController wController;
	
	PseudoModelClasses modelClasses;
	
	//Warehouse warehouse1;
	//Warehouse warehouse2;
	//Warehouse warehouse3;
	
	
	Shipment shipment1;
	Shipment shipment2;
	Shipment shipment3;
	Shipment shipment4;
	
	

	@Test
	void test() {
		
		// Add new shipment will return 1 if add is successful
		assertEquals(1, wController.addShipment(shipment1), "Adding shipment1 failed");
		assertEquals(1, wController.addShipment(shipment2), "Adding shipment2 failed");
		
		
		
		
		// End freight receipt should return true if warehouse exists
		// and false if the warehouse does not exist
		assertEquals(false, wController.endFreightReceipt(15567), "Ending freight receipt on a non existent warehouse failed");
		assertEquals(true, wController.endFreightReceipt(15566),  "Ending freight receipt on an existing warehouse failed");
		
		
		// Add new shipment should return false if shipment has ended
		// freight receipt
		assertEquals(0, wController.addShipment(shipment3),  "Adding shipment to a warehouse with a disabled freight receipt failed");
	
		//Freight receipt is enabled again, so add new shipment should return true
		assertEquals(true, wController.enableFreightReceipt(15566),  "Enabling freight receipt on an existing warehouse failed");
		assertEquals(1, wController.addShipment(shipment3),  "Adding shipment3 failed" );
		assertEquals(1, wController.addShipment(shipment4),  "Adding shipment4 failed" );

		
		assertEquals(3, wController.getNumWarehouses());
		
		
		assertEquals(2, wController.getNumOfShipments(15566), "Getting number of shipments failed");
		assertEquals(true, wController.printWarehouseInfo(15566));
		
	}
	


	
	public ControllerTester() 
	{
		
		wController = new WarehouseController();
		
		modelClasses = new PseudoModelClasses();
		
		
		shipment1 = modelClasses.new Shipment("48934j", 12315, "air", 84, "1515354694451");
		//warehouse1 = modelClasses.new Warehouse(12513, true, shipment1);
		
		shipment2 = modelClasses.new Shipment("1adf4", 15566, "truck", 354, "1515354694451");
		//warehouse2 = modelClasses.new Warehouse(15566, true, shipment2);
		
		shipment3 = modelClasses.new Shipment("1a545", 15566, "ship", 20.6, "1515354694451");
		
		
		shipment4 = modelClasses.new Shipment("85545", 336558, "rail", 760, "1515354694451");
		//warehouse3 = modelClasses.new Warehouse(336558, true, shipment4);
		
	}
	
	
	

}
