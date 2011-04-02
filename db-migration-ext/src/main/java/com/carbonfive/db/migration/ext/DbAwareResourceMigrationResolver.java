package com.carbonfive.db.migration.ext;

import org.apache.commons.lang.StringUtils;

import com.carbonfive.db.jdbc.DatabaseType;
import com.carbonfive.db.migration.ResourceMigrationResolver;

public class DbAwareResourceMigrationResolver
    extends ResourceMigrationResolver
{
    private static final String DB_TYPE_TOKEN = "{dbtype}";
    
    @Override
    protected String convertMigrationsLocation( String migrationsLocation, DatabaseType dbType )
    {
        String converted = super.convertMigrationsLocation( migrationsLocation, dbType );
        
        return StringUtils.replace( converted, DB_TYPE_TOKEN, dbType.name().toLowerCase() );
    }
}
