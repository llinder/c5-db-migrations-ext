package com.dtornkaew.db.migration;

public class MigrationConfiguration
{
    public static final String DEFAULT_VERSION_TABLE = "schema_version";
    public static final String DEFAULT_LOCATION = "classpath:/db/migrations/";
    
    private String versionTable = DEFAULT_VERSION_TABLE;
    private String migrationsLocation;
    private String dbType;
    private String prefix;
    
    public String getVersionTable()
    {
        return versionTable;
    }
    public void setVersionTable( String versionTable )
    {
        this.versionTable = versionTable;
    }
    public String getMigrationsLocation()
    {
        return migrationsLocation;
    }
    public void setMigrationsLocation( String migrationsLocation )
    {
        this.migrationsLocation = migrationsLocation;
    }
    public String getDbType()
    {
        return dbType;
    }
    public void setDbType( String dbType )
    {
        this.dbType = dbType;
    }
    public void setPrefix( String prefix )
    {
        this.prefix = prefix;
    }
    public String getPrefix()
    {
        return prefix;
    }
}
