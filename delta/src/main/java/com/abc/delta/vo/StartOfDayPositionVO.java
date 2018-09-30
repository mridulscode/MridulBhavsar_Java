package com.abc.delta.vo;

/**
 * @author mridul.bhavsar
 */
public class StartOfDayPositionVO {

	private String instrument;

	private String accountNumber;

	private long eQuantity;

	private long iQuantity;

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
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the eQuantity
	 */
	public long geteQuantity() {
		return eQuantity;
	}

	/**
	 * @param eQuantity
	 *            the eQuantity to set
	 */
	public void seteQuantity(long eQuantity) {
		this.eQuantity = eQuantity;
	}

	/**
	 * @return the iQuantity
	 */
	public long getiQuantity() {
		return iQuantity;
	}

	/**
	 * @param iQuantity
	 *            the iQuantity to set
	 */
	public void setiQuantity(long iQuantity) {
		this.iQuantity = iQuantity;
	}

	@Override
	public String toString() {
		return "StartOfDayPosition [instrument=" + instrument + ", accountNumber=" + accountNumber + ", eQuantity="
				+ eQuantity + ", iQuantity=" + iQuantity + "]";
	}

}
