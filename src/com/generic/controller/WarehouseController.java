package com.generic.controller;

import java.util.HashSet;
import java.util.Set;

import com.generic.controllertest.PseudoModelClasses;
import com.generic.controllertest.PseudoModelClasses.Shipment;
import com.generic.controllertest.PseudoModelClasses.Warehouse;


/**
 * 
 * @author Seyi Ola
 * 2/2/2020
 * This class controls data flow into model objects 
 * (Warehouse and Shipment) and updates the 
 * WarehouseView(User Interface) whenever data changes.
 * 
 * This class will keep track of the warehouses created using a set. 
 * Since a shipment can't exist without a warehouse,
 * we don't need a collection for them as they will
 * exist in their respective Warehouse objects.
 * 
 * This class also controls the 
 * 
 */


/*
 * NOTES FOR TEAM,
 * The engineering of this class stems from the idea that we
 * are using GSON which easily maps the variables in a JSON file
 * to a class attributes. If the main class can have a method
 * that reads a JSON from a file and maps it to a Shipment class,
 * we can easily pass the class to the addShipment(Shipment sModel)method.
 * The Shipment class essentially has all information(ATTRIBUTES)
 * needed to create a Warehouse Object. This is why we only need
 * to de-serialize the the JSON file to a Shipment object using GSON.
 * 
 * The person handling deserialization should engineer a method of parsing information
 * from the Json file by parsing one element of the JSON File into one shipment object.
 * Kindly let me know if you'd have any questions. I feel like collaboration
 * is needed to make this class and Json File input work.
 * 
 */

public class WarehouseController {
	
	private Set<Warehouse> warehouses; // A set of warehouses as 
									  // we can't have two different warehouses 
									 // with same id# which means Warehouse will
									// have to have a comparable later if we are
								   // going to build upon this. For now
								  // this isn't too important
	
	public WarehouseController ()
	{
		
		warehouses = new HashSet<Warehouse>(); // Initialize the set
		
	}
	
	
	/**
	 * Adds a new shipment to it's corresponding warehouse.
	 * If the warehouse does not exist, it creates a new 
	 * warehouse and adds necessary information.
	 * If we are not able to add a shipment to a warehouse due to
	 * it's freight receipt being disable(false), method returns a 0.
	 * @param sModel The shipment. Contains all the shipment details
	 * such as warehouseID, shipmentMethod, shipmentID, weight
	 * and receipt date. The
	 * @return returns 1 if add was successful, returns 0 if
	 * add wasn't successful due to freight receipt being disabled.
	 * 
	 */
	public int addShipment(Shipment sModel)
	{
		
		// Get the warehouseID from Shipment
		long warehouseID = sModel.getWarehouseId();
		
		// Flag to track if warehouse has already been created 
		boolean warehouseExists = false;
		
		
		int shipmentAdded = 0;
		
		// Loop through the set of warehouses available
		for (Warehouse warehouse : warehouses)
		{
			// Check warehouseID from shipment against existing warehouses
			if (warehouseID == warehouse.getWarehouseID())
			{
				// If warehouse exists and freight receipt is enabled(true),
				// Add shipment to the warehouse
				if (warehouse.addShipment(sModel) == true)
				{					
					shipmentAdded = 1; // Add successful
					
				}
			
				// Set the flag to true
				warehouseExists = true;
				// Break out of for loop
				break;
			}
		}
		// If warehouse doesn't exist it,
		// we create a new one, add the shipment
		// , and add it to the set of warehouses. 
		if (!warehouseExists)
		{
			
			PseudoModelClasses pseduoClasses = new PseudoModelClasses();
			/*??Is it safe to set freight receipt status to true
			 *  since we're creating a new warehouse object??,
			 *  please think about this from your perspective team-mates.
			 */
			Warehouse nWarehouse = pseduoClasses.new Warehouse(warehouseID, true, sModel);
			warehouses.add(nWarehouse);
						
			shipmentAdded = 1; // Add successful

		}
		
		return shipmentAdded;
		
	}
	
	
	/**
	 * Prints out the information of the warehouse with supplied ID.
	 * This information includes the all
	 * shipments available in the warehouse.
	 * @param warehouseID The warehouse ID
	 * @return false, if the warehouse with supplied ID# does not exist
	 */
	public boolean printWarehouseInfo (long warehouseID)
	{
		boolean warehouseExists = false;
		String warehouseInfo = "";
		
		for (Warehouse warehouse : warehouses)
		{
			if (warehouseID ==  warehouse.getWarehouseID())
			{
				warehouseExists = true;
				warehouseInfo = warehouse.toString();
				break;
			}
		}
		
		if (warehouseExists) 
		{
			// We should have a view be responsible for this
			System.out.println(warehouseInfo);
		}
		
		
		return warehouseExists;

	}
	
	/**
	 * Gets the number shipments in warehouse with supplied ID
	 * @param warehouseID The warehouse Id
	 * @return number of shipments in warehouse
	 */
	
	public int getNumOfShipments(long warehouseID)
	{
		int num = -1;
		
		for (Warehouse warehouse : warehouses)
		{
			if (warehouse.getWarehouseID() == warehouseID)
			{
				num = warehouse.getNumOfShipments();
			}
		}
		
		return num;
	}
	
	/**
	 * Enables the freight receipt of a warehouse with supplied id.
	 * @param warehouseID The warehouse ID#
	 * @return false, if the warehouse with supplied ID# does not exist.
	 */
	public boolean enableFreightReceipt(long warehouseID)
	{
		boolean warehouseExists = false;
		
		for (Warehouse warehouse : warehouses)
		{
			if (warehouseID ==  warehouse.getWarehouseID())
			{
				warehouse.enableFreightReceipt();
				warehouseExists = true;
				break;
			}
		}
		
		return warehouseExists;
		
	}
	/**
	 * Enables the freight receipt of a warehouse with supplied id.
	 * @param warehouseID The warehouse ID#
	 * @return false, if the warehouse with supplied ID# does not exist.
	 */
	
	public boolean endFreightReceipt(long warehouseID)
	{
		boolean warehouseExists = false;
		
		for (Warehouse warehouse : warehouses)
		{
			if (warehouseID ==  warehouse.getWarehouseID())
			{
				warehouse.disableFreightReceipt();
				warehouseExists = true;
				break;
			}
		}
		
		return warehouseExists;
		
	}
	
	
	/**
	 * Exports all shipment information to a single JSON file
	 * @param warehouseID The ID of warehouse to be exported.
	 * @param destPath    The path to write file to.
	 * @return false, if warehouse doesn't exist.
	 */
	
	public boolean exportWarehouseToJson (long warehouseID, String destPath)
	{
		boolean warehouseExists = false;
		
		for (Warehouse warehouse : warehouses)
		{
			if (warehouseID ==  warehouse.getWarehouseID())
			{
				warehouseExists = true;
				//warehouse.writeToJsonFile(destPath);
			}
		}
		
		return warehouseExists;
	}
	
	/**
	 * Number of warehouses created for QA Testing
	 * @return Number of available warehouses
	 */
	public int getNumWarehouses()
	{
		return warehouses.size();
	}
	
	
	
	
	
	

}
