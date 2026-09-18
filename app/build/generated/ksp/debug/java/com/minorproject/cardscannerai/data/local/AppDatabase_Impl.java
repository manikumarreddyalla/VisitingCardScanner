package com.minorproject.cardscannerai.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.minorproject.cardscannerai.data.dao.ScannedCardDao;
import com.minorproject.cardscannerai.data.dao.ScannedCardDao_Impl;
import com.minorproject.cardscannerai.data.local.dao.ContactDao;
import com.minorproject.cardscannerai.data.local.dao.ContactDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ContactDao _contactDao;

  private volatile ScannedCardDao _scannedCardDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `contacts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `nameConfidence` REAL, `phone` TEXT, `phoneConfidence` REAL, `email` TEXT, `emailConfidence` REAL, `company` TEXT, `companyConfidence` REAL, `designation` TEXT, `designationConfidence` REAL, `address` TEXT, `addressConfidence` REAL, `website` TEXT, `websiteConfidence` REAL, `category` TEXT NOT NULL, `rawText` TEXT NOT NULL, `duplicateOfId` INTEGER, `cloudSynced` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `scanned_cards` (`id` TEXT NOT NULL, `originalText` TEXT NOT NULL, `fieldsJson` TEXT NOT NULL, `syncStatus` TEXT NOT NULL, `syncError` TEXT, `lastSyncAttempt` INTEGER, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '93783b194f5ad9fea219fd1e8d8791de')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `contacts`");
        db.execSQL("DROP TABLE IF EXISTS `scanned_cards`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsContacts = new HashMap<String, TableInfo.Column>(21);
        _columnsContacts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("nameConfidence", new TableInfo.Column("nameConfidence", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("phone", new TableInfo.Column("phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("phoneConfidence", new TableInfo.Column("phoneConfidence", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("emailConfidence", new TableInfo.Column("emailConfidence", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("company", new TableInfo.Column("company", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("companyConfidence", new TableInfo.Column("companyConfidence", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("designation", new TableInfo.Column("designation", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("designationConfidence", new TableInfo.Column("designationConfidence", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("address", new TableInfo.Column("address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("addressConfidence", new TableInfo.Column("addressConfidence", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("website", new TableInfo.Column("website", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("websiteConfidence", new TableInfo.Column("websiteConfidence", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("rawText", new TableInfo.Column("rawText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("duplicateOfId", new TableInfo.Column("duplicateOfId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("cloudSynced", new TableInfo.Column("cloudSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysContacts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesContacts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoContacts = new TableInfo("contacts", _columnsContacts, _foreignKeysContacts, _indicesContacts);
        final TableInfo _existingContacts = TableInfo.read(db, "contacts");
        if (!_infoContacts.equals(_existingContacts)) {
          return new RoomOpenHelper.ValidationResult(false, "contacts(com.minorproject.cardscannerai.data.local.entity.ContactEntity).\n"
                  + " Expected:\n" + _infoContacts + "\n"
                  + " Found:\n" + _existingContacts);
        }
        final HashMap<String, TableInfo.Column> _columnsScannedCards = new HashMap<String, TableInfo.Column>(8);
        _columnsScannedCards.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScannedCards.put("originalText", new TableInfo.Column("originalText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScannedCards.put("fieldsJson", new TableInfo.Column("fieldsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScannedCards.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScannedCards.put("syncError", new TableInfo.Column("syncError", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScannedCards.put("lastSyncAttempt", new TableInfo.Column("lastSyncAttempt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScannedCards.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScannedCards.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysScannedCards = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesScannedCards = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoScannedCards = new TableInfo("scanned_cards", _columnsScannedCards, _foreignKeysScannedCards, _indicesScannedCards);
        final TableInfo _existingScannedCards = TableInfo.read(db, "scanned_cards");
        if (!_infoScannedCards.equals(_existingScannedCards)) {
          return new RoomOpenHelper.ValidationResult(false, "scanned_cards(com.minorproject.cardscannerai.data.model.ScannedCardEntity).\n"
                  + " Expected:\n" + _infoScannedCards + "\n"
                  + " Found:\n" + _existingScannedCards);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "93783b194f5ad9fea219fd1e8d8791de", "cfa957f0535bb4110b647da1c0e2f34e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "contacts","scanned_cards");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `contacts`");
      _db.execSQL("DELETE FROM `scanned_cards`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ContactDao.class, ContactDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ScannedCardDao.class, ScannedCardDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ContactDao contactDao() {
    if (_contactDao != null) {
      return _contactDao;
    } else {
      synchronized(this) {
        if(_contactDao == null) {
          _contactDao = new ContactDao_Impl(this);
        }
        return _contactDao;
      }
    }
  }

  @Override
  public ScannedCardDao scannedCardDao() {
    if (_scannedCardDao != null) {
      return _scannedCardDao;
    } else {
      synchronized(this) {
        if(_scannedCardDao == null) {
          _scannedCardDao = new ScannedCardDao_Impl(this);
        }
        return _scannedCardDao;
      }
    }
  }
}
