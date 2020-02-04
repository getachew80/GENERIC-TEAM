package com.generic.controllertest;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.PRIVATE_MEMBER;


public class PseudoModelClasses {

	/**
	 * 	
	 * A pseudo Shipment class to help test WarehouseController.
	 * Contains basic constructor to create the object.
	 * @author Seyi Ola
	 *
	 */
	public class Shipment{
		private String shipmentID; // Holds the shipmentID
		private long warehouseID; // Holds the warehouseID
		private String shipmentMethod; // Holds the shipment method
		private String receiptDate; // Holds the raw format of 
									// receipt id, will be converted to date with a private method
		private double weight; // Holds the weight of shipment
		
		public Shipment(String shipmentID, long warehouseID, String shipmentMethod, double weight, String receiptDate)
		{
			this.shipmentID = shipmentID;
			this.warehouseID = warehouseID;
			this.shipmentMethod = shipmentMethod;
			this.receiptDate = receiptDate;
			this.weight = weight;
		}
		
		public long getWarehouseId()
		{
			return warehouseID;
		}
		
		public String getShipmentId()
		{
			return shipmentID;
		}

		public double getWeight() {
			// TODO Auto-generated method stub
			return weight;
		}

		public String getReceiptDate() {
			// TODO Auto-generated method stub
			return receiptDate;
		}
		
		
	
		
		
		
	}
	
	/**
	 * @author Seyi Ola
	 * A pseudo Warehouse class to help test WarehouseController.
	 * Contains constructor for object as well as 
	 */
	
	public class Warehouse{
		private long warehouseID;
		private boolean receiptEnabled;
		List<Shipment> shipmentReceived;
		
		public Warehouse (long warehouseID, boolean receiptEnabled, Shipment shipment) 
		{
			this.warehouseID = warehouseID;
			this.receiptEnabled = receiptEnabled;
			shipmentReceived = new ArrayList<Shipment>();
			
			shipmentReceived.add(shipment);
		}
		
		public long getWarehouseID()
		{
			return warehouseID;
		}
		
		public void enableFreightReceipt()
		{
			receiptEnabled = true;
		}
		
		public void disableFreightReceipt()
		{
			receiptEnabled = false;
		}
		
		
		public boolean addShipment(Shipment shipment)
		{
			boolean added = false;
			
			if (receiptEnabled)
			{
				shipmentReceived.add(shipment);	
				added = true;
			}	
			return added;
		}
		
		@Override
		public String toString()
		{
			
			
			String headerString  = String.format("|WAREHOUSEID: %d| FREIGHT RECEIPT STATUS: %s| SHIPMENT AVALIABLE: %d|"
												, warehouseID, (receiptEnabled) ? "ENABLED" : "ENDED", shipmentReceived.size());
			
			StringBuilder warehouseInfo = new StringBuilder("");
			
			for (int i = 0; i <= headerString.length(); i++)
			{
				warehouseInfo.append("-");
			}
			
			warehouseInfo.append("\n" + headerString);
			warehouseInfo.append("\n*****************************************************************************");
			warehouseInfo.append("\n          *SHIPMENT RECEIVED*");
			warehouseInfo.append("\n****************************************");
			int count = 0;
			for (Shipment shipment : shipmentReceived)
			{
				count++;
				String shipmentID = shipment.getShipmentId();
				double weight = shipment.getWeight();
				String receiptDate = shipment.getReceiptDate();
				
				
				String shipmentInfo = String.format("%d.Shipment_Id: %s\n  Weight: %.1f\n  Receipt_Date: %s",
													count, shipmentID, weight, milliToDate(receiptDate));
				
				warehouseInfo.append("\n" + shipmentInfo);
				warehouseInfo.append("\n****************************************");
				
			}
			
			return warehouseInfo.toString();
		
		}
		
		public int getNumOfShipments()
		{
			return shipmentReceived.size();
		}
		
		private String milliToDate(String milliDate)
		{
			DateFormat simple = new SimpleDateFormat("dd MMMMM yyyy HH:mm:ss");
			
			Date result = new Date(Long.valueOf(milliDate));
			
			return simple.format(result);
			
		}
	}
	
	
}
