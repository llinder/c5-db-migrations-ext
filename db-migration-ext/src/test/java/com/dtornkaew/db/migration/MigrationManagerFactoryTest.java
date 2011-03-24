package com.dtornkaew.db.migration;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import com.carbonfive.db.migration.MigrationManager;

public class MigrationManagerFactoryTest
{
    private MigrationManagerFactory factory;
    
    @Before
    public void setup()
    {
        DataSource ds = mock( DataSource.class );
        
        factory = new MigrationManagerFactory();
        factory.setDataSource( ds );
    }
    
    @Test
    public void testCreateManager()
    {
        MigrationManager mm = factory.createManager( "com.carbonfive.db.migration.DataSourceMigrationManager" );
        
        assertNotNull(  mm );
    }
}
