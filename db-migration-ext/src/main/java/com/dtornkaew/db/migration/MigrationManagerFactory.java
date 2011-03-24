package com.dtornkaew.db.migration;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;

import com.carbonfive.db.migration.DataSourceMigrationManager;
import com.carbonfive.db.migration.ResourceMigrationResolver;
import com.carbonfive.db.migration.SimpleVersionStrategy;
import com.carbonfive.db.migration.VersionStrategy;

public class MigrationManagerFactory
    implements InitializingBean
{
    private static final String DEFAULT_MANAGE = "com.carbonfive.db.migration.DataSourceMigrationManager";

    private static final String DEFAULT_RESOLVER = "com.dtornkaew.db.migration.DbAwareResourceMigrationResolver";

    private static final String DEFAULT_VERSION_STRATEGY = "com.dtornkaew.db.migration.MultiTableVersionStrategy";

    private DataSource dataSource;

    private String managerClass = DEFAULT_MANAGE;

    private String resolverClass = DEFAULT_RESOLVER;

    private String versionStrategyClass = DEFAULT_VERSION_STRATEGY;

    private List<MigrationConfiguration> configurations = new ArrayList<MigrationConfiguration>();

    public void afterPropertiesSet()
        throws Exception
    {
        for ( MigrationConfiguration config : configurations )
        {
            DataSourceMigrationManager manager = createManager( getManagerClass() );

            ResourceMigrationResolver resolver = createResolver( getResolverClass() );

            VersionStrategy strategy = createVersionStrategy( getVersionStrategyClass() );

            if ( SimpleVersionStrategy.class.isInstance( strategy ) )
                ( (SimpleVersionStrategy) strategy ).setVersionTable( config.getVersionTable() );

            if ( MultiTableVersionStrategy.class.isInstance( strategy ) )
            {
                ( (MultiTableVersionStrategy) strategy ).setVersionTable( config.getVersionTable() );
                ( (MultiTableVersionStrategy) strategy ).setPrefix( config.getPrefix() );
            }

            resolver.setMigrationsLocation( config.getMigrationsLocation() );

            manager.setMigrationResolver( resolver );
            manager.setVersionStrategy( strategy );

            manager.migrate();
        }
    }

    protected DataSourceMigrationManager createManager( String className )
    {
        DataSourceMigrationManager manager = null;

        try
        {
            Class<?> clazz = Class.forName( className );
            if ( !DataSourceMigrationManager.class.isAssignableFrom( clazz ) )
                throw new RuntimeException( "Class " + className
                    + " must be an instance of DataSourceMigrationManager." );

            Constructor<?> constructor = clazz.getConstructor( DataSource.class );
            manager = (DataSourceMigrationManager) constructor.newInstance( dataSource );

        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }

        return manager;
    }

    protected ResourceMigrationResolver createResolver( String className )
    {
        ResourceMigrationResolver resolver = null;

        try
        {
            Class<?> clazz = Class.forName( className );
            if ( !ResourceMigrationResolver.class.isAssignableFrom( clazz ) )
                throw new RuntimeException( "Class " + className + " must be an instance of ResourceMigrationResolver." );

            resolver = (ResourceMigrationResolver) clazz.newInstance();
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }

        return resolver;
    }

    protected VersionStrategy createVersionStrategy( String className )
    {
        VersionStrategy strategy = null;

        try
        {
            Class<?> clazz = Class.forName( className );
            if ( !VersionStrategy.class.isAssignableFrom( clazz ) )
                throw new RuntimeException( "Class " + className + " must be an instance of VersionStrategy." );

            strategy = (VersionStrategy) clazz.newInstance();
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }

        return strategy;
    }

    public void setDataSource( DataSource dataSource )
    {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource()
    {
        return dataSource;
    }

    public void setConfigurations( List<MigrationConfiguration> configurations )
    {
        this.configurations = configurations;
    }

    public List<MigrationConfiguration> getConfigurations()
    {
        return configurations;
    }

    public String getManagerClass()
    {
        return managerClass;
    }

    public void setManagerClass( String managerClass )
    {
        this.managerClass = managerClass;
    }

    public String getResolverClass()
    {
        return resolverClass;
    }

    public void setResolver( String resolverClass )
    {
        this.resolverClass = resolverClass;
    }

    public void setVersionStrategyClass( String versionStrategyClass )
    {
        this.versionStrategyClass = versionStrategyClass;
    }

    public String getVersionStrategyClass()
    {
        return versionStrategyClass;
    }
}
