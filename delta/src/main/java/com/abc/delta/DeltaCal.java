package com.abc.delta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.abc.delta.utils.DeltaConstants;
import com.abc.delta.vo.EndOfDayPositionVO;
import com.abc.delta.vo.TransactionVO;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

/**
 * Takes transactions and start-of-day positions as input to calculate the net
 * volumes for external and internal account types.
 * 
 * @author mridul.bhavsar
 */
public class DeltaCal {

	private static final Logger LOGGER = Logger.getLogger(DeltaCal.class.getName());

	public static void main(String[] args) {
		
		LOGGER.info("Start of delta calculation process");

		DeltaCal deltaCal = new DeltaCal();

		// Reading the input_Transactions file and populating the TransactionVO
		Map<String, TransactionVO> transactionMap = deltaCal.readTransactionsFile("input/input_Transactions.txt");

		// Reading the Input_StartOfDay_Positions and calculating the net
		// volumes
		Map<String, EndOfDayPositionVO> eodPositionsMap = deltaCal
				.readInputStartPositions("input/Input_StartOfDay_Positions.txt", transactionMap);

		// Writing DELTA to EndOfDay_Positions file
		deltaCal.generateDeltaOutputFile(eodPositionsMap);
		
		LOGGER.info("End of process");

	}

	protected Map<String, TransactionVO> readTransactionsFile(String fileName) {
		Map<String, TransactionVO> transactionMap = new HashMap<>();
		JSONParser jsonParser = new JSONParser();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		JSONArray transactionArray;
		try {
			transactionArray = (JSONArray) jsonParser.parse(new FileReader(file));

			for (Object obj : transactionArray) {
				TransactionVO transaction;
				JSONObject jsonTrans = (JSONObject) obj;
				String instrument = (String) jsonTrans.get(DeltaConstants.INSTRUMENT);
				String type = (String) jsonTrans.get(DeltaConstants.TRANSACTION_TYPE);
				boolean isBuyTrans = DeltaConstants.BUY_TRANSACTION_TYPE.equals(type);
				boolean isSellTrans = DeltaConstants.SELL_TRANSACTION_TYPE.equals(type);
				long buyQuantity = isBuyTrans ? (Long) jsonTrans.get(DeltaConstants.TRANSACTION_QUANTITY) : 0;
				long sellQuantity = isSellTrans ? (Long) jsonTrans.get(DeltaConstants.TRANSACTION_QUANTITY) : 0;
				if (transactionMap.containsKey(instrument)) {
					transaction = transactionMap.get(instrument);
					if (isBuyTrans) {
						transaction.setBuyQuantity(transaction.getBuyQuantity() + buyQuantity);
					} else if (isSellTrans) {
						transaction.setSellQuantity(transaction.getSellQuantity() + sellQuantity);
					}
				} else {
					transaction = new TransactionVO();
					transaction.setInstrument(instrument);
					transaction.setBuyQuantity(buyQuantity);
					transaction.setSellQuantity(sellQuantity);
				}
				transactionMap.put(instrument, transaction);
			}
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Could not find the input transctions file", e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IO Exception while operating on input_Transactions file", e);
		} catch (ParseException e) {
			LOGGER.log(Level.SEVERE, "Error in parsing the input transactions file", e);
		}

		return transactionMap;
	}

	protected Map<String, EndOfDayPositionVO> readInputStartPositions(String fileName,
			Map<String, TransactionVO> transactionMap) {

		Map<String, EndOfDayPositionVO> eodPostionsMap = new TreeMap<>();

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file)).withSkipLines(1).build()) {

			csvReader.forEach(row -> {

				EndOfDayPositionVO eodPositionsVo;

				String instrument = row[0];
				String account = row[1];
				String accountType = row[2];
				long quantity = Long.parseLong(row[3]);

				TransactionVO transaction;
				if (transactionMap.containsKey(instrument)) {
					transaction = transactionMap.get(instrument);
				} else {
					transaction = new TransactionVO();
				}

				if (eodPostionsMap.containsKey(instrument)) {
					eodPositionsVo = eodPostionsMap.get(instrument);
				} else {
					eodPositionsVo = new EndOfDayPositionVO();
					eodPositionsVo.setInstrument(instrument);
				}
				if (DeltaConstants.EXTERNAL_ACCOUNT_TYPE.equals(accountType)) {
					eodPositionsVo.seteAccount(account);
					long finalExternalQuantity = calculateFinalExternalQuantity(quantity, transaction.getBuyQuantity(),
							transaction.getSellQuantity());
					eodPositionsVo.seteQuantity(finalExternalQuantity);
					eodPositionsVo.seteNetVolume(finalExternalQuantity - quantity);
				} else if (DeltaConstants.INTERNAL_ACCOUNT_TYPE.equals(accountType)) {
					eodPositionsVo.setiAccount(account);
					long finalInternalQuantity = calculateFinalInternalQuantity(quantity, transaction.getBuyQuantity(),
							transaction.getSellQuantity());
					eodPositionsVo.setiQuantity(finalInternalQuantity);
					eodPositionsVo.setiNetVolume(finalInternalQuantity - quantity);
				}
				eodPostionsMap.put(instrument, eodPositionsVo);
			});

		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Could not find the Input_StartOfDay_Positions file", e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IO Exception while operating on Input_StartOfDay_Positions file", e);
		}

		return eodPostionsMap;
	}

	protected long calculateFinalExternalQuantity(long quantity, long buyQuantity, long sellQuantity) {
		return quantity + buyQuantity - sellQuantity;
	}

	protected long calculateFinalInternalQuantity(long quantity, long buyQuantity, long sellQuantity) {
		return quantity - buyQuantity + sellQuantity;
	}

	protected void generateDeltaOutputFile(Map<String, EndOfDayPositionVO> eodPostionsMap) {
		try (FileWriter fw = new FileWriter("src/main/resources/output/EndOfDay_Positions.txt")) {
			fw.write("Instrument,Account,AccountType,Quantity,Delta");
			for (EndOfDayPositionVO vo : eodPostionsMap.values()) {
				fw.write(vo.toString());
			}
			LOGGER.info("Output written to EndOfDay_Positions file");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in writing to output file", e);
		}
	}

}
