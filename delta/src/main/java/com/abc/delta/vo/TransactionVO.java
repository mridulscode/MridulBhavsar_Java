package com.abc.delta.vo;

public class TransactionVO {

	private String instrument;

	private long buyQuantity;

	private long sellQuantity;

	/**
	 * @return the instrument
	 */
	public String getInstrument() {
		return instrument;
	}

	/**
	 * @param instrument
	 *            the instrument to set
	 */
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	/**
	 * @return the buyQuantity
	 */
	public long getBuyQuantity() {
		return buyQuantity;
	}

	/**
	 * @param buyQuantity
	 *            the buyQuantity to set
	 */
	public void setBuyQuantity(long buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	/**
	 * @return the sellQuantity
	 */
	public long getSellQuantity() {
		return sellQuantity;
	}

	/**
	 * @param sellQuantity
	 *            the sellQuantity to set
	 */
	public void setSellQuantity(long sellQuantity) {
		this.sellQuantity = sellQuantity;
	}

	@Override
	public String toString() {
		return "Transaction [instrument=" + instrument + ", buyQuantity=" + buyQuantity + ", sellQuantity="
				+ sellQuantity + "]";
	}

}
