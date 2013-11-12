package com.noveogroup.mailexpress;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import java.net.MalformedURLException;
import java.sql.Connection;

/**
 * @author Maxim Baev
 */
public class BaseDbUnitTestUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDbUnitTestUtils.class);

	/**
	 * Table names ordered by dependency
	 * IDataSet filtered = new FilteredDataSet(new
	 * DatabaseSequenceFilter(databaseConnection), dataSet);
	 * <p/>
	 * for (String name : filtered.getTableNames()) {
	 * <p/>
	 * System.out.println(name);
	 * <p/>
	 * }
	 */
	private static final String[] tables = new String[]{
			"contact",
			"folder",
			"message",
			"message_contact",
			"attachment"
	};

	public static void insertData(final IDatabaseConnection databaseConnection) throws Exception {
		// Insert all needed test data

		final Connection connection = databaseConnection.getConnection();
		try {
			connection.setAutoCommit(true);

			final IDataSet[] dataSets = new IDataSet[tables.length];
			for (int i = 0; i < tables.length; i++) {
				try {
					dataSets[i] = getDataSet(tables[i]);
				} catch (DataSetException e) {
					if (e.getCause().getClass().equals(MalformedURLException.class)) {
						LOGGER.info("File {}.xml doesn't exist, ignoring.", tables[i]);
					} else {
						LOGGER.error("Exception reading " + tables[i] + ".xml", e);
						throw e;
					}
				}
			}

			//truncate from tail
			for (int i = tables.length - 1; i >= 0; i--) {
				final String table = tables[i];
				try {
					final IDataSet dataSet = new DefaultDataSet(new DefaultTable(tables[i].toUpperCase()));
					DatabaseOperation.DELETE_ALL.execute(databaseConnection, dataSet);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("problem cleaning table: " + table, e);
				}
			}

			//insert from head
			for (int i = 0; i < dataSets.length; i++) {
				final String table = tables[i];
				try {
					final IDataSet dataSet = dataSets[i];
					if (dataSet == null)
						continue;//could not be read
					DatabaseOperation.INSERT.execute(databaseConnection, dataSet);
				} catch (Exception e) {
					throw new Exception("problem inserting: " + table, e);
				}
			}
		} finally {
			if (connection != null) {
				connection.setAutoCommit(false);
				connection.close();
			}
		}
	}

	/**
	 * Returns dataset from XML file.
	 *
	 * @param tableName table name.
	 * @return Dataset loaded from file.
	 * @throws org.dbunit.dataset.DataSetException
	 *          is some error occurs.
	 */
	public static IDataSet getDataSet(final String tableName) throws DataSetException {
		final String fileName = "dbunit/" + tableName + ".xml";
		final FlatXmlProducer producer = new FlatXmlProducer(new InputSource(
				BasePersistenceTest.class.getClassLoader().getResourceAsStream(fileName)),
				false,
				/*column sensing*/true);
		return new FlatXmlDataSet(producer);
	}
}
