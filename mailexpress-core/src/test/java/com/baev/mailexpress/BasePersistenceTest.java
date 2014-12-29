package com.baev.mailexpress;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Base class for database integration tests.
 *
 * @author Maxim Baev
 */
@TransactionConfiguration(defaultRollback = false)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/test-appContext.xml"})
public abstract class BasePersistenceTest extends AbstractTransactionalJUnit4SpringContextTests {

    /**
     * Flag indicates is test data already inserted.
     */
    private static boolean dataInserted;

    /**
     * Data source.
     */
    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    /**
     * Initialize, insert test data.
     *
     * @throws Exception exception occurred
     */
    @Before
    public void insertData() throws Exception {
        if (!dataInserted) {
            BaseDbUnitTestUtils.insertData(getDatabaseConnection());
            dataInserted = true;
        }
    }

    private IDatabaseConnection getDatabaseConnection() throws SQLException {
        final IDatabaseConnection databaseConnection = new DatabaseDataSourceConnection(dataSource);
        final DatabaseConfig databaseConfig = databaseConnection.getConfig();
        databaseConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
        return databaseConnection;
    }
}
