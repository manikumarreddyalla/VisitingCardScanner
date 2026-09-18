package com.minorproject.cardscannerai.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.minorproject.cardscannerai.data.local.entity.ContactEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ContactDao_Impl implements ContactDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ContactEntity> __insertionAdapterOfContactEntity;

  private final EntityDeletionOrUpdateAdapter<ContactEntity> __deletionAdapterOfContactEntity;

  private final EntityDeletionOrUpdateAdapter<ContactEntity> __updateAdapterOfContactEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfMarkSynced;

  public ContactDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfContactEntity = new EntityInsertionAdapter<ContactEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `contacts` (`id`,`name`,`nameConfidence`,`phone`,`phoneConfidence`,`email`,`emailConfidence`,`company`,`companyConfidence`,`designation`,`designationConfidence`,`address`,`addressConfidence`,`website`,`websiteConfidence`,`category`,`rawText`,`duplicateOfId`,`cloudSynced`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ContactEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getNameConfidence() == null) {
          statement.bindNull(3);
        } else {
          statement.bindDouble(3, entity.getNameConfidence());
        }
        if (entity.getPhone() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhone());
        }
        if (entity.getPhoneConfidence() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getPhoneConfidence());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getEmail());
        }
        if (entity.getEmailConfidence() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getEmailConfidence());
        }
        if (entity.getCompany() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCompany());
        }
        if (entity.getCompanyConfidence() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getCompanyConfidence());
        }
        if (entity.getDesignation() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getDesignation());
        }
        if (entity.getDesignationConfidence() == null) {
          statement.bindNull(11);
        } else {
          statement.bindDouble(11, entity.getDesignationConfidence());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getAddress());
        }
        if (entity.getAddressConfidence() == null) {
          statement.bindNull(13);
        } else {
          statement.bindDouble(13, entity.getAddressConfidence());
        }
        if (entity.getWebsite() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getWebsite());
        }
        if (entity.getWebsiteConfidence() == null) {
          statement.bindNull(15);
        } else {
          statement.bindDouble(15, entity.getWebsiteConfidence());
        }
        statement.bindString(16, entity.getCategory());
        statement.bindString(17, entity.getRawText());
        if (entity.getDuplicateOfId() == null) {
          statement.bindNull(18);
        } else {
          statement.bindLong(18, entity.getDuplicateOfId());
        }
        final int _tmp = entity.getCloudSynced() ? 1 : 0;
        statement.bindLong(19, _tmp);
        statement.bindLong(20, entity.getCreatedAt());
        statement.bindLong(21, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfContactEntity = new EntityDeletionOrUpdateAdapter<ContactEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `contacts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ContactEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfContactEntity = new EntityDeletionOrUpdateAdapter<ContactEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `contacts` SET `id` = ?,`name` = ?,`nameConfidence` = ?,`phone` = ?,`phoneConfidence` = ?,`email` = ?,`emailConfidence` = ?,`company` = ?,`companyConfidence` = ?,`designation` = ?,`designationConfidence` = ?,`address` = ?,`addressConfidence` = ?,`website` = ?,`websiteConfidence` = ?,`category` = ?,`rawText` = ?,`duplicateOfId` = ?,`cloudSynced` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ContactEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getNameConfidence() == null) {
          statement.bindNull(3);
        } else {
          statement.bindDouble(3, entity.getNameConfidence());
        }
        if (entity.getPhone() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhone());
        }
        if (entity.getPhoneConfidence() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getPhoneConfidence());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getEmail());
        }
        if (entity.getEmailConfidence() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getEmailConfidence());
        }
        if (entity.getCompany() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCompany());
        }
        if (entity.getCompanyConfidence() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getCompanyConfidence());
        }
        if (entity.getDesignation() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getDesignation());
        }
        if (entity.getDesignationConfidence() == null) {
          statement.bindNull(11);
        } else {
          statement.bindDouble(11, entity.getDesignationConfidence());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getAddress());
        }
        if (entity.getAddressConfidence() == null) {
          statement.bindNull(13);
        } else {
          statement.bindDouble(13, entity.getAddressConfidence());
        }
        if (entity.getWebsite() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getWebsite());
        }
        if (entity.getWebsiteConfidence() == null) {
          statement.bindNull(15);
        } else {
          statement.bindDouble(15, entity.getWebsiteConfidence());
        }
        statement.bindString(16, entity.getCategory());
        statement.bindString(17, entity.getRawText());
        if (entity.getDuplicateOfId() == null) {
          statement.bindNull(18);
        } else {
          statement.bindLong(18, entity.getDuplicateOfId());
        }
        final int _tmp = entity.getCloudSynced() ? 1 : 0;
        statement.bindLong(19, _tmp);
        statement.bindLong(20, entity.getCreatedAt());
        statement.bindLong(21, entity.getUpdatedAt());
        statement.bindLong(22, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM contacts WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE contacts SET cloudSynced = 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ContactEntity entity, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfContactEntity.insertAndReturnId(entity);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final ContactEntity entity, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfContactEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final ContactEntity entity, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfContactEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markSynced(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkSynced.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ContactEntity>> observeAll() {
    final String _sql = "SELECT * FROM contacts ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"contacts"}, new Callable<List<ContactEntity>>() {
      @Override
      @NonNull
      public List<ContactEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfNameConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "nameConfidence");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfPhoneConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneConfidence");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfEmailConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "emailConfidence");
          final int _cursorIndexOfCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "company");
          final int _cursorIndexOfCompanyConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "companyConfidence");
          final int _cursorIndexOfDesignation = CursorUtil.getColumnIndexOrThrow(_cursor, "designation");
          final int _cursorIndexOfDesignationConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "designationConfidence");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfAddressConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "addressConfidence");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfWebsiteConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "websiteConfidence");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfRawText = CursorUtil.getColumnIndexOrThrow(_cursor, "rawText");
          final int _cursorIndexOfDuplicateOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "duplicateOfId");
          final int _cursorIndexOfCloudSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "cloudSynced");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ContactEntity> _result = new ArrayList<ContactEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ContactEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final Float _tmpNameConfidence;
            if (_cursor.isNull(_cursorIndexOfNameConfidence)) {
              _tmpNameConfidence = null;
            } else {
              _tmpNameConfidence = _cursor.getFloat(_cursorIndexOfNameConfidence);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final Float _tmpPhoneConfidence;
            if (_cursor.isNull(_cursorIndexOfPhoneConfidence)) {
              _tmpPhoneConfidence = null;
            } else {
              _tmpPhoneConfidence = _cursor.getFloat(_cursorIndexOfPhoneConfidence);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final Float _tmpEmailConfidence;
            if (_cursor.isNull(_cursorIndexOfEmailConfidence)) {
              _tmpEmailConfidence = null;
            } else {
              _tmpEmailConfidence = _cursor.getFloat(_cursorIndexOfEmailConfidence);
            }
            final String _tmpCompany;
            if (_cursor.isNull(_cursorIndexOfCompany)) {
              _tmpCompany = null;
            } else {
              _tmpCompany = _cursor.getString(_cursorIndexOfCompany);
            }
            final Float _tmpCompanyConfidence;
            if (_cursor.isNull(_cursorIndexOfCompanyConfidence)) {
              _tmpCompanyConfidence = null;
            } else {
              _tmpCompanyConfidence = _cursor.getFloat(_cursorIndexOfCompanyConfidence);
            }
            final String _tmpDesignation;
            if (_cursor.isNull(_cursorIndexOfDesignation)) {
              _tmpDesignation = null;
            } else {
              _tmpDesignation = _cursor.getString(_cursorIndexOfDesignation);
            }
            final Float _tmpDesignationConfidence;
            if (_cursor.isNull(_cursorIndexOfDesignationConfidence)) {
              _tmpDesignationConfidence = null;
            } else {
              _tmpDesignationConfidence = _cursor.getFloat(_cursorIndexOfDesignationConfidence);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final Float _tmpAddressConfidence;
            if (_cursor.isNull(_cursorIndexOfAddressConfidence)) {
              _tmpAddressConfidence = null;
            } else {
              _tmpAddressConfidence = _cursor.getFloat(_cursorIndexOfAddressConfidence);
            }
            final String _tmpWebsite;
            if (_cursor.isNull(_cursorIndexOfWebsite)) {
              _tmpWebsite = null;
            } else {
              _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            }
            final Float _tmpWebsiteConfidence;
            if (_cursor.isNull(_cursorIndexOfWebsiteConfidence)) {
              _tmpWebsiteConfidence = null;
            } else {
              _tmpWebsiteConfidence = _cursor.getFloat(_cursorIndexOfWebsiteConfidence);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpRawText;
            _tmpRawText = _cursor.getString(_cursorIndexOfRawText);
            final Long _tmpDuplicateOfId;
            if (_cursor.isNull(_cursorIndexOfDuplicateOfId)) {
              _tmpDuplicateOfId = null;
            } else {
              _tmpDuplicateOfId = _cursor.getLong(_cursorIndexOfDuplicateOfId);
            }
            final boolean _tmpCloudSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCloudSynced);
            _tmpCloudSynced = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ContactEntity(_tmpId,_tmpName,_tmpNameConfidence,_tmpPhone,_tmpPhoneConfidence,_tmpEmail,_tmpEmailConfidence,_tmpCompany,_tmpCompanyConfidence,_tmpDesignation,_tmpDesignationConfidence,_tmpAddress,_tmpAddressConfidence,_tmpWebsite,_tmpWebsiteConfidence,_tmpCategory,_tmpRawText,_tmpDuplicateOfId,_tmpCloudSynced,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ContactEntity>> observeFiltered(final String query) {
    final String _sql = "SELECT * FROM contacts WHERE (name LIKE '%' || ? || '%' OR company LIKE '%' || ? || '%' OR phone LIKE '%' || ? || '%' OR email LIKE '%' || ? || '%') ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    _argIndex = 4;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"contacts"}, new Callable<List<ContactEntity>>() {
      @Override
      @NonNull
      public List<ContactEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfNameConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "nameConfidence");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfPhoneConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneConfidence");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfEmailConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "emailConfidence");
          final int _cursorIndexOfCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "company");
          final int _cursorIndexOfCompanyConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "companyConfidence");
          final int _cursorIndexOfDesignation = CursorUtil.getColumnIndexOrThrow(_cursor, "designation");
          final int _cursorIndexOfDesignationConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "designationConfidence");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfAddressConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "addressConfidence");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfWebsiteConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "websiteConfidence");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfRawText = CursorUtil.getColumnIndexOrThrow(_cursor, "rawText");
          final int _cursorIndexOfDuplicateOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "duplicateOfId");
          final int _cursorIndexOfCloudSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "cloudSynced");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ContactEntity> _result = new ArrayList<ContactEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ContactEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final Float _tmpNameConfidence;
            if (_cursor.isNull(_cursorIndexOfNameConfidence)) {
              _tmpNameConfidence = null;
            } else {
              _tmpNameConfidence = _cursor.getFloat(_cursorIndexOfNameConfidence);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final Float _tmpPhoneConfidence;
            if (_cursor.isNull(_cursorIndexOfPhoneConfidence)) {
              _tmpPhoneConfidence = null;
            } else {
              _tmpPhoneConfidence = _cursor.getFloat(_cursorIndexOfPhoneConfidence);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final Float _tmpEmailConfidence;
            if (_cursor.isNull(_cursorIndexOfEmailConfidence)) {
              _tmpEmailConfidence = null;
            } else {
              _tmpEmailConfidence = _cursor.getFloat(_cursorIndexOfEmailConfidence);
            }
            final String _tmpCompany;
            if (_cursor.isNull(_cursorIndexOfCompany)) {
              _tmpCompany = null;
            } else {
              _tmpCompany = _cursor.getString(_cursorIndexOfCompany);
            }
            final Float _tmpCompanyConfidence;
            if (_cursor.isNull(_cursorIndexOfCompanyConfidence)) {
              _tmpCompanyConfidence = null;
            } else {
              _tmpCompanyConfidence = _cursor.getFloat(_cursorIndexOfCompanyConfidence);
            }
            final String _tmpDesignation;
            if (_cursor.isNull(_cursorIndexOfDesignation)) {
              _tmpDesignation = null;
            } else {
              _tmpDesignation = _cursor.getString(_cursorIndexOfDesignation);
            }
            final Float _tmpDesignationConfidence;
            if (_cursor.isNull(_cursorIndexOfDesignationConfidence)) {
              _tmpDesignationConfidence = null;
            } else {
              _tmpDesignationConfidence = _cursor.getFloat(_cursorIndexOfDesignationConfidence);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final Float _tmpAddressConfidence;
            if (_cursor.isNull(_cursorIndexOfAddressConfidence)) {
              _tmpAddressConfidence = null;
            } else {
              _tmpAddressConfidence = _cursor.getFloat(_cursorIndexOfAddressConfidence);
            }
            final String _tmpWebsite;
            if (_cursor.isNull(_cursorIndexOfWebsite)) {
              _tmpWebsite = null;
            } else {
              _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            }
            final Float _tmpWebsiteConfidence;
            if (_cursor.isNull(_cursorIndexOfWebsiteConfidence)) {
              _tmpWebsiteConfidence = null;
            } else {
              _tmpWebsiteConfidence = _cursor.getFloat(_cursorIndexOfWebsiteConfidence);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpRawText;
            _tmpRawText = _cursor.getString(_cursorIndexOfRawText);
            final Long _tmpDuplicateOfId;
            if (_cursor.isNull(_cursorIndexOfDuplicateOfId)) {
              _tmpDuplicateOfId = null;
            } else {
              _tmpDuplicateOfId = _cursor.getLong(_cursorIndexOfDuplicateOfId);
            }
            final boolean _tmpCloudSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCloudSynced);
            _tmpCloudSynced = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ContactEntity(_tmpId,_tmpName,_tmpNameConfidence,_tmpPhone,_tmpPhoneConfidence,_tmpEmail,_tmpEmailConfidence,_tmpCompany,_tmpCompanyConfidence,_tmpDesignation,_tmpDesignationConfidence,_tmpAddress,_tmpAddressConfidence,_tmpWebsite,_tmpWebsiteConfidence,_tmpCategory,_tmpRawText,_tmpDuplicateOfId,_tmpCloudSynced,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getById(final long id, final Continuation<? super ContactEntity> $completion) {
    final String _sql = "SELECT * FROM contacts WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ContactEntity>() {
      @Override
      @Nullable
      public ContactEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfNameConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "nameConfidence");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfPhoneConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneConfidence");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfEmailConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "emailConfidence");
          final int _cursorIndexOfCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "company");
          final int _cursorIndexOfCompanyConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "companyConfidence");
          final int _cursorIndexOfDesignation = CursorUtil.getColumnIndexOrThrow(_cursor, "designation");
          final int _cursorIndexOfDesignationConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "designationConfidence");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfAddressConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "addressConfidence");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfWebsiteConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "websiteConfidence");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfRawText = CursorUtil.getColumnIndexOrThrow(_cursor, "rawText");
          final int _cursorIndexOfDuplicateOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "duplicateOfId");
          final int _cursorIndexOfCloudSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "cloudSynced");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final ContactEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final Float _tmpNameConfidence;
            if (_cursor.isNull(_cursorIndexOfNameConfidence)) {
              _tmpNameConfidence = null;
            } else {
              _tmpNameConfidence = _cursor.getFloat(_cursorIndexOfNameConfidence);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final Float _tmpPhoneConfidence;
            if (_cursor.isNull(_cursorIndexOfPhoneConfidence)) {
              _tmpPhoneConfidence = null;
            } else {
              _tmpPhoneConfidence = _cursor.getFloat(_cursorIndexOfPhoneConfidence);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final Float _tmpEmailConfidence;
            if (_cursor.isNull(_cursorIndexOfEmailConfidence)) {
              _tmpEmailConfidence = null;
            } else {
              _tmpEmailConfidence = _cursor.getFloat(_cursorIndexOfEmailConfidence);
            }
            final String _tmpCompany;
            if (_cursor.isNull(_cursorIndexOfCompany)) {
              _tmpCompany = null;
            } else {
              _tmpCompany = _cursor.getString(_cursorIndexOfCompany);
            }
            final Float _tmpCompanyConfidence;
            if (_cursor.isNull(_cursorIndexOfCompanyConfidence)) {
              _tmpCompanyConfidence = null;
            } else {
              _tmpCompanyConfidence = _cursor.getFloat(_cursorIndexOfCompanyConfidence);
            }
            final String _tmpDesignation;
            if (_cursor.isNull(_cursorIndexOfDesignation)) {
              _tmpDesignation = null;
            } else {
              _tmpDesignation = _cursor.getString(_cursorIndexOfDesignation);
            }
            final Float _tmpDesignationConfidence;
            if (_cursor.isNull(_cursorIndexOfDesignationConfidence)) {
              _tmpDesignationConfidence = null;
            } else {
              _tmpDesignationConfidence = _cursor.getFloat(_cursorIndexOfDesignationConfidence);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final Float _tmpAddressConfidence;
            if (_cursor.isNull(_cursorIndexOfAddressConfidence)) {
              _tmpAddressConfidence = null;
            } else {
              _tmpAddressConfidence = _cursor.getFloat(_cursorIndexOfAddressConfidence);
            }
            final String _tmpWebsite;
            if (_cursor.isNull(_cursorIndexOfWebsite)) {
              _tmpWebsite = null;
            } else {
              _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            }
            final Float _tmpWebsiteConfidence;
            if (_cursor.isNull(_cursorIndexOfWebsiteConfidence)) {
              _tmpWebsiteConfidence = null;
            } else {
              _tmpWebsiteConfidence = _cursor.getFloat(_cursorIndexOfWebsiteConfidence);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpRawText;
            _tmpRawText = _cursor.getString(_cursorIndexOfRawText);
            final Long _tmpDuplicateOfId;
            if (_cursor.isNull(_cursorIndexOfDuplicateOfId)) {
              _tmpDuplicateOfId = null;
            } else {
              _tmpDuplicateOfId = _cursor.getLong(_cursorIndexOfDuplicateOfId);
            }
            final boolean _tmpCloudSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCloudSynced);
            _tmpCloudSynced = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new ContactEntity(_tmpId,_tmpName,_tmpNameConfidence,_tmpPhone,_tmpPhoneConfidence,_tmpEmail,_tmpEmailConfidence,_tmpCompany,_tmpCompanyConfidence,_tmpDesignation,_tmpDesignationConfidence,_tmpAddress,_tmpAddressConfidence,_tmpWebsite,_tmpWebsiteConfidence,_tmpCategory,_tmpRawText,_tmpDuplicateOfId,_tmpCloudSynced,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllOnce(final Continuation<? super List<ContactEntity>> $completion) {
    final String _sql = "SELECT * FROM contacts";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ContactEntity>>() {
      @Override
      @NonNull
      public List<ContactEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfNameConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "nameConfidence");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfPhoneConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneConfidence");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfEmailConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "emailConfidence");
          final int _cursorIndexOfCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "company");
          final int _cursorIndexOfCompanyConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "companyConfidence");
          final int _cursorIndexOfDesignation = CursorUtil.getColumnIndexOrThrow(_cursor, "designation");
          final int _cursorIndexOfDesignationConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "designationConfidence");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfAddressConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "addressConfidence");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfWebsiteConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "websiteConfidence");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfRawText = CursorUtil.getColumnIndexOrThrow(_cursor, "rawText");
          final int _cursorIndexOfDuplicateOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "duplicateOfId");
          final int _cursorIndexOfCloudSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "cloudSynced");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ContactEntity> _result = new ArrayList<ContactEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ContactEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final Float _tmpNameConfidence;
            if (_cursor.isNull(_cursorIndexOfNameConfidence)) {
              _tmpNameConfidence = null;
            } else {
              _tmpNameConfidence = _cursor.getFloat(_cursorIndexOfNameConfidence);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final Float _tmpPhoneConfidence;
            if (_cursor.isNull(_cursorIndexOfPhoneConfidence)) {
              _tmpPhoneConfidence = null;
            } else {
              _tmpPhoneConfidence = _cursor.getFloat(_cursorIndexOfPhoneConfidence);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final Float _tmpEmailConfidence;
            if (_cursor.isNull(_cursorIndexOfEmailConfidence)) {
              _tmpEmailConfidence = null;
            } else {
              _tmpEmailConfidence = _cursor.getFloat(_cursorIndexOfEmailConfidence);
            }
            final String _tmpCompany;
            if (_cursor.isNull(_cursorIndexOfCompany)) {
              _tmpCompany = null;
            } else {
              _tmpCompany = _cursor.getString(_cursorIndexOfCompany);
            }
            final Float _tmpCompanyConfidence;
            if (_cursor.isNull(_cursorIndexOfCompanyConfidence)) {
              _tmpCompanyConfidence = null;
            } else {
              _tmpCompanyConfidence = _cursor.getFloat(_cursorIndexOfCompanyConfidence);
            }
            final String _tmpDesignation;
            if (_cursor.isNull(_cursorIndexOfDesignation)) {
              _tmpDesignation = null;
            } else {
              _tmpDesignation = _cursor.getString(_cursorIndexOfDesignation);
            }
            final Float _tmpDesignationConfidence;
            if (_cursor.isNull(_cursorIndexOfDesignationConfidence)) {
              _tmpDesignationConfidence = null;
            } else {
              _tmpDesignationConfidence = _cursor.getFloat(_cursorIndexOfDesignationConfidence);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final Float _tmpAddressConfidence;
            if (_cursor.isNull(_cursorIndexOfAddressConfidence)) {
              _tmpAddressConfidence = null;
            } else {
              _tmpAddressConfidence = _cursor.getFloat(_cursorIndexOfAddressConfidence);
            }
            final String _tmpWebsite;
            if (_cursor.isNull(_cursorIndexOfWebsite)) {
              _tmpWebsite = null;
            } else {
              _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            }
            final Float _tmpWebsiteConfidence;
            if (_cursor.isNull(_cursorIndexOfWebsiteConfidence)) {
              _tmpWebsiteConfidence = null;
            } else {
              _tmpWebsiteConfidence = _cursor.getFloat(_cursorIndexOfWebsiteConfidence);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpRawText;
            _tmpRawText = _cursor.getString(_cursorIndexOfRawText);
            final Long _tmpDuplicateOfId;
            if (_cursor.isNull(_cursorIndexOfDuplicateOfId)) {
              _tmpDuplicateOfId = null;
            } else {
              _tmpDuplicateOfId = _cursor.getLong(_cursorIndexOfDuplicateOfId);
            }
            final boolean _tmpCloudSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCloudSynced);
            _tmpCloudSynced = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ContactEntity(_tmpId,_tmpName,_tmpNameConfidence,_tmpPhone,_tmpPhoneConfidence,_tmpEmail,_tmpEmailConfidence,_tmpCompany,_tmpCompanyConfidence,_tmpDesignation,_tmpDesignationConfidence,_tmpAddress,_tmpAddressConfidence,_tmpWebsite,_tmpWebsiteConfidence,_tmpCategory,_tmpRawText,_tmpDuplicateOfId,_tmpCloudSynced,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object unsynced(final Continuation<? super List<ContactEntity>> $completion) {
    final String _sql = "SELECT * FROM contacts WHERE cloudSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ContactEntity>>() {
      @Override
      @NonNull
      public List<ContactEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfNameConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "nameConfidence");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfPhoneConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneConfidence");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfEmailConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "emailConfidence");
          final int _cursorIndexOfCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "company");
          final int _cursorIndexOfCompanyConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "companyConfidence");
          final int _cursorIndexOfDesignation = CursorUtil.getColumnIndexOrThrow(_cursor, "designation");
          final int _cursorIndexOfDesignationConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "designationConfidence");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfAddressConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "addressConfidence");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfWebsiteConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "websiteConfidence");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfRawText = CursorUtil.getColumnIndexOrThrow(_cursor, "rawText");
          final int _cursorIndexOfDuplicateOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "duplicateOfId");
          final int _cursorIndexOfCloudSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "cloudSynced");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ContactEntity> _result = new ArrayList<ContactEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ContactEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final Float _tmpNameConfidence;
            if (_cursor.isNull(_cursorIndexOfNameConfidence)) {
              _tmpNameConfidence = null;
            } else {
              _tmpNameConfidence = _cursor.getFloat(_cursorIndexOfNameConfidence);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final Float _tmpPhoneConfidence;
            if (_cursor.isNull(_cursorIndexOfPhoneConfidence)) {
              _tmpPhoneConfidence = null;
            } else {
              _tmpPhoneConfidence = _cursor.getFloat(_cursorIndexOfPhoneConfidence);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final Float _tmpEmailConfidence;
            if (_cursor.isNull(_cursorIndexOfEmailConfidence)) {
              _tmpEmailConfidence = null;
            } else {
              _tmpEmailConfidence = _cursor.getFloat(_cursorIndexOfEmailConfidence);
            }
            final String _tmpCompany;
            if (_cursor.isNull(_cursorIndexOfCompany)) {
              _tmpCompany = null;
            } else {
              _tmpCompany = _cursor.getString(_cursorIndexOfCompany);
            }
            final Float _tmpCompanyConfidence;
            if (_cursor.isNull(_cursorIndexOfCompanyConfidence)) {
              _tmpCompanyConfidence = null;
            } else {
              _tmpCompanyConfidence = _cursor.getFloat(_cursorIndexOfCompanyConfidence);
            }
            final String _tmpDesignation;
            if (_cursor.isNull(_cursorIndexOfDesignation)) {
              _tmpDesignation = null;
            } else {
              _tmpDesignation = _cursor.getString(_cursorIndexOfDesignation);
            }
            final Float _tmpDesignationConfidence;
            if (_cursor.isNull(_cursorIndexOfDesignationConfidence)) {
              _tmpDesignationConfidence = null;
            } else {
              _tmpDesignationConfidence = _cursor.getFloat(_cursorIndexOfDesignationConfidence);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final Float _tmpAddressConfidence;
            if (_cursor.isNull(_cursorIndexOfAddressConfidence)) {
              _tmpAddressConfidence = null;
            } else {
              _tmpAddressConfidence = _cursor.getFloat(_cursorIndexOfAddressConfidence);
            }
            final String _tmpWebsite;
            if (_cursor.isNull(_cursorIndexOfWebsite)) {
              _tmpWebsite = null;
            } else {
              _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            }
            final Float _tmpWebsiteConfidence;
            if (_cursor.isNull(_cursorIndexOfWebsiteConfidence)) {
              _tmpWebsiteConfidence = null;
            } else {
              _tmpWebsiteConfidence = _cursor.getFloat(_cursorIndexOfWebsiteConfidence);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpRawText;
            _tmpRawText = _cursor.getString(_cursorIndexOfRawText);
            final Long _tmpDuplicateOfId;
            if (_cursor.isNull(_cursorIndexOfDuplicateOfId)) {
              _tmpDuplicateOfId = null;
            } else {
              _tmpDuplicateOfId = _cursor.getLong(_cursorIndexOfDuplicateOfId);
            }
            final boolean _tmpCloudSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCloudSynced);
            _tmpCloudSynced = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ContactEntity(_tmpId,_tmpName,_tmpNameConfidence,_tmpPhone,_tmpPhoneConfidence,_tmpEmail,_tmpEmailConfidence,_tmpCompany,_tmpCompanyConfidence,_tmpDesignation,_tmpDesignationConfidence,_tmpAddress,_tmpAddressConfidence,_tmpWebsite,_tmpWebsiteConfidence,_tmpCategory,_tmpRawText,_tmpDuplicateOfId,_tmpCloudSynced,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
