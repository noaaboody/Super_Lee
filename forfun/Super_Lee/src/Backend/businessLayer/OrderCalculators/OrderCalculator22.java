package Backend.businessLayer.OrderCalculators;//package dev.Backend.SupInvPack.OrderCalculatores;
//import dev.Backend.SupplierPack.SupplierBusinessLayer.*;
//
//import java.util.*;
//
//public class OrderCalculator22 {
//	SupplierController controller;
//	Map<Integer, Order> supplierIdToOrder;
//	Integer OrderIdIndexer=0;
//	String currentOrderAddress;
//
//
//	public OrderCalculator22(SupplierController controller){
//		this.controller = controller;
//	}
//
//	/**
//	 * this method calculates and sets the best order for a given Item list
//	 * @param itemToQuantity a map of Item and the wanted quantity for that item
//	 * @param address the address of that the order should get to.
//	 * @return a Map of the chosen Supplier's ID and the order from that supplier
//	 * @throws Exception if there is an item that no supplier can supply
//	 */
//	public Map<Integer, Order> makeCheapOrder(Map<Integer, Integer> itemToQuantity, String address)  {
//		supplierIdToOrder = new HashMap<>();// initialize the output map.
//		currentOrderAddress = address;
//		int quantityNeeded;
//
//		for (Integer itemId : itemToQuantity.keySet()) {
//			quantityNeeded = itemToQuantity.get(itemId);
//			List<SupplierAgreement> agreementList = controller.getAgreementsByItem(itemId);
//			if(agreementList.isEmpty())// if there is no suppliers that supply an Item, throw an exception.
//				throw new RuntimeException("no suppliers available for item: "+ itemId);
//			if(!findBestSupplierNoSplit(itemId, quantityNeeded, agreementList))
//				findBestSuppliersSplit(itemId, quantityNeeded, agreementList);
//		}
//		this.applyAdditionalDiscountsForPrice();
//		return supplierIdToOrder;
//	}
//
//	public Map<Integer, Order> makeFastOrder(Map<Integer, Integer> itemToQuantity, String address)  {
//		supplierIdToOrder = new HashMap<>();// initialize the output map.
//		currentOrderAddress = address;
//		int quantity;
//
//		for (Integer itemId : itemToQuantity.keySet()) {
//			quantity = itemToQuantity.get(itemId);
//
//			List<SupplierAgreement> agreementList = controller.getFastestSupplierForItem(itemId,quantity);
//			if(agreementList.isEmpty())// if there is no suppliers that supply an Item, throw an exception.
//				throw new RuntimeException("no suppliers available for item: "+ itemId);
//			if(!findBestSupplierNoSplit(itemId, quantity, agreementList))
//				findBestSuppliersSplit(itemId, quantity, agreementList);
//		}
//		this.applyAdditionalDiscountsForPrice();
//		return supplierIdToOrder;
//	}
//
//	private void addItemToOrderMap(int supplierId, int itemId, int quantity, float initialPrice, float finalPrice){
//		if(!supplierIdToOrder.containsKey(supplierId))
//			supplierIdToOrder.put(supplierId,new Order(OrderIdIndexer++, supplierId, currentOrderAddress,0));
//		String itemName=controller.getAllItems().get(itemId).GetName();
//		supplierIdToOrder.get(supplierId).addItem(itemId,itemName,quantity,initialPrice,finalPrice);
//	}
//
//	private double getAgreementBestDiscountForItem(SupplierAgreement agreement, int itemId, int quantity){
//		double discount = 0;
//		for(ConditionForDiscount cond : agreement.getConditionsList()){
//			discount = Math.max(cond.GetDiscountForItem(itemId, quantity), discount);
//		}
//		return discount;
//	}
//
//	private boolean findBestSupplierNoSplit(int itemId, int quantity, List<SupplierAgreement> agreementList){
//		int supplierId =-1 ; // initialize arguments needed for calculation.
//		float finalPrice = Integer.MAX_VALUE;
//		float discount;
//		float initialPrice =-1 ;
//
//		boolean supplierFound = false;
//		for (SupplierAgreement agreement: agreementList) {
//			if(quantity <= agreement.getItemsToQuantity().get(itemId)){
//				discount = getAgreementBestDiscountForItem(agreement,itemId, quantity);
//				if (finalPrice > agreement.GetItemPrice(itemId) * (1 - discount/100)) {
//					initialPrice = agreement.GetItemPrice(itemId);
//					finalPrice = initialPrice * (1 - discount/100);
//					supplierId = agreement.getSupplierId();
//					supplierFound = true;
//				}
//			}
//		}
//		if(supplierFound){
//			addItemToOrderMap(supplierId, itemId, quantity, initialPrice, finalPrice);
//		}
//		return supplierFound;
//	}
//	private void findBestSuppliersSplit(int itemId, int quantity, List<SupplierAgreement> agreementList)  {
//		int currentQuantity = quantity;
//		List<Integer> chosenSupplierIds = new ArrayList<>();
//		float discount;
//		float finalPrice = Integer.MAX_VALUE;
//		float priceDiscounted; // price after discount;
//		SupplierAgreement idealAgreement = agreementList.get(0);// the agreement that is the cheapest in each iteration
//		for(int i=0; i < agreementList.size() && currentQuantity > 0; i++) {
//			for (SupplierAgreement agreement : agreementList) {
//				 discount = getAgreementBestDiscountForItem(agreement, itemId, currentQuantity);
//				priceDiscounted = agreement.GetItemPrice(itemId) *(1-discount/100);
//				 if(finalPrice > priceDiscounted && !chosenSupplierIds.contains(agreement.getSupplierId())){
//					 finalPrice = priceDiscounted;
//					 idealAgreement = agreement;
//				 }
//			}
//			int quan = Math.min(idealAgreement.getItemsToQuantity().get(itemId), currentQuantity);
//			this.addItemToOrderMap(idealAgreement.getSupplierId(), itemId, quan, idealAgreement.GetItemPrice(itemId),finalPrice);
//			chosenSupplierIds.add(idealAgreement.getSupplierId());
//			currentQuantity -= quan;
//			finalPrice = Integer.MAX_VALUE;
//		}
//		if(currentQuantity > 0)
//			throw new RuntimeException("there is no possible way to complete order, wanted quantity for item: "+ itemId+ " is too big");
//	}
//
//
//	private void applyAdditionalDiscountsForPrice(){
//		double discount = 0;
//		for(Order order : supplierIdToOrder.values()){
//			SupplierAgreement supplierAgreement = controller.getSupplierAgreement(order.GetSupplierId());
//			float acc = 0;
//			for(float finalPrice: order.GetItemTOFinalPrice().values()){
//				acc += finalPrice;
//			}
//			for(ConditionForDiscount condition : supplierAgreement.getConditionsList()){
//				if(condition.isForPrice())
//					discount = condition.GetDiscountPercentage();
//			}
//			applyDiscountToOrder(discount, order);
//		}
//	}
//
//	private void applyDiscountToOrder(double discount, Order order){
//		for (Map.Entry<Integer, Float> entry : order.GetItemTOFinalPrice().entrySet()) {
//			order.putItemFinalPrice(entry.getKey(), entry.getValue()*(discount/100));
//		}
//
//	}
//
//}
