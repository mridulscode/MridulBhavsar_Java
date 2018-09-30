package com.abc.delta;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.abc.delta.vo.TransactionVO;

/**
 * Test class for DeltaCal
 * @author mridul.bhavsar
 */
public class DeltaCalTest {
	
	private DeltaCal delta = new DeltaCal();

	/**
	 * Tests reading from input transactions file and finding the total buy and sell quantities
	 */
	@Test
	public void testReadTransactionsFile() {
		Map<String, TransactionVO> transactionMap = delta.readTransactionsFile("input/input_Transactions.txt");
		assertFalse(transactionMap.isEmpty());
		TransactionVO transVO =  transactionMap.get("AMZN");
		long expectedBuyQuantity = 200;
		long expectedSellQuantity = 15050;
		assertEquals(expectedBuyQuantity, transVO.getBuyQuantity());
		assertEquals(expectedSellQuantity,transVO.getSellQuantity());
	}

	/**
	 * Tests calculating the Final external quantity
	 */
	@Test
	public void testCalculateFinalExternalQuanity() {
		long quantity = 1700;
		long buyQuantity = 300; 
		long sellQuantity = 500;
		long expectedExternalQuantity = 1500;
		assertEquals(expectedExternalQuantity, delta.calculateFinalExternalQuantity(quantity, buyQuantity, sellQuantity));
	}

	/**
	 * Tests calculating the Final internal quantity
	 */
	@Test
	public void testCalculateFinalInternalQuantity() {
		long quantity = 1700;
		long buyQuantity = 300; 
		long sellQuantity = 500;
		long expectedExternalQuantity = 1900;
		assertEquals(expectedExternalQuantity, delta.calculateFinalInternalQuantity(quantity, buyQuantity, sellQuantity));
	}

}
