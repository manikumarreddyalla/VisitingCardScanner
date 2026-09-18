package com.minorproject.cardscannerai.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.minorproject.cardscannerai.data.db.Converters;
import com.minorproject.cardscannerai.data.model.ScannedCardEntity;
import com.minorproject.cardscannerai.data.model.SyncStatus;
import java.lang.Class;
import java.lang.Exception;
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
public final class ScannedCardDao_Impl implements ScannedCardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ScannedCardEntity> __insertionAdapterOfScannedCardEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<ScannedCardEntity> __deletionAdapterOfScannedCardEntity;

  private final EntityDeletionOrUpdateAdapter<ScannedCardEntity> __updateAdapterOfScannedCardEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  private final SharedSQLiteStatement __preparedStmtOfMarkSyncError;

  public ScannedCardDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfScannedCardEntity = new EntityInsertionAdapter<ScannedCardEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `scanned_cards` (`id`,`originalText`,`fieldsJson`,`syncStatus`,`syncError`,`lastSyncAttempt`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScannedCardEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getOriginalText());
        statement.bindString(3, entity.getFieldsJson());
        final String _tmp = __converters.syncStatusToString(entity.getSyncStatus());
        statement.bindString(4, _tmp);
        if (entity.getSyncError() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSyncError());
        }
        if (entity.getLastSyncAttempt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getLastSyncAttempt());
        }
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfScannedCardEntity = new EntityDeletionOrUpdateAdapter<ScannedCardEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `scanned_cards` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScannedCardEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfScannedCardEntity = new EntityDeletionOrUpdateAdapter<ScannedCardEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `scanned_cards` SET `id` = ?,`originalText` = ?,`fieldsJson` = ?,`syncStatus` = ?,`syncError` = ?,`lastSyncAttempt` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScannedCardEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getOriginalText());
        statement.bindString(3, entity.getFieldsJson());
        final String _tmp = __converters.syncStatusToString(entity.getSyncStatus());
        statement.bindString(4, _tmp);
        if (entity.getSyncError() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSyncError());
        }
        if (entity.getLastSyncAttempt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getLastSyncAttempt());
        }
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
        statement.bindString(9, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE scanned_cards SET syncStatus = ?, lastSyncAttempt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkSyncError = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE scanned_cards SET syncStatus = 'ERROR', syncError = ?, lastSyncAttempt = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ScannedCardEntity card, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfScannedCardEntity.insert(card);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final ScannedCardEntity card, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfScannedCardEntity.handle(card);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final ScannedCardEntity card, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfScannedCardEntity.handle(card);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSyncStatus(final String id, final SyncStatus status, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSyncStatus.acquire();
        int _argIndex = 1;
        final String _tmp = __converters.syncStatusToString(status);
        _stmt.bindString(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 3;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfUpdateSyncStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markSyncError(final String id, final String error, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkSyncError.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, error);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 3;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfMarkSyncError.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ScannedCardEntity>> getAll() {
    final String _sql = "SELECT * FROM scanned_cards ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"scanned_cards"}, new Callable<List<ScannedCardEntity>>() {
      @Override
      @NonNull
      public List<ScannedCardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOriginalText = CursorUtil.getColumnIndexOrThrow(_cursor, "originalText");
          final int _cursorIndexOfFieldsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "fieldsJson");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfSyncError = CursorUtil.getColumnIndexOrThrow(_cursor, "syncError");
          final int _cursorIndexOfLastSyncAttempt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAttempt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ScannedCardEntity> _result = new ArrayList<ScannedCardEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScannedCardEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpOriginalText;
            _tmpOriginalText = _cursor.getString(_cursorIndexOfOriginalText);
            final String _tmpFieldsJson;
            _tmpFieldsJson = _cursor.getString(_cursorIndexOfFieldsJson);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __converters.stringToSyncStatus(_tmp);
            final String _tmpSyncError;
            if (_cursor.isNull(_cursorIndexOfSyncError)) {
              _tmpSyncError = null;
            } else {
              _tmpSyncError = _cursor.getString(_cursorIndexOfSyncError);
            }
            final Long _tmpLastSyncAttempt;
            if (_cursor.isNull(_cursorIndexOfLastSyncAttempt)) {
              _tmpLastSyncAttempt = null;
            } else {
              _tmpLastSyncAttempt = _cursor.getLong(_cursorIndexOfLastSyncAttempt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ScannedCardEntity(_tmpId,_tmpOriginalText,_tmpFieldsJson,_tmpSyncStatus,_tmpSyncError,_tmpLastSyncAttempt,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getCardsByStatus(final SyncStatus status,
      final Continuation<? super List<ScannedCardEntity>> $completion) {
    final String _sql = "SELECT * FROM scanned_cards WHERE syncStatus = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.syncStatusToString(status);
    _statement.bindString(_argIndex, _tmp);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ScannedCardEntity>>() {
      @Override
      @NonNull
      public List<ScannedCardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOriginalText = CursorUtil.getColumnIndexOrThrow(_cursor, "originalText");
          final int _cursorIndexOfFieldsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "fieldsJson");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfSyncError = CursorUtil.getColumnIndexOrThrow(_cursor, "syncError");
          final int _cursorIndexOfLastSyncAttempt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAttempt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ScannedCardEntity> _result = new ArrayList<ScannedCardEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScannedCardEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpOriginalText;
            _tmpOriginalText = _cursor.getString(_cursorIndexOfOriginalText);
            final String _tmpFieldsJson;
            _tmpFieldsJson = _cursor.getString(_cursorIndexOfFieldsJson);
            final SyncStatus _tmpSyncStatus;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __converters.stringToSyncStatus(_tmp_1);
            final String _tmpSyncError;
            if (_cursor.isNull(_cursorIndexOfSyncError)) {
              _tmpSyncError = null;
            } else {
              _tmpSyncError = _cursor.getString(_cursorIndexOfSyncError);
            }
            final Long _tmpLastSyncAttempt;
            if (_cursor.isNull(_cursorIndexOfLastSyncAttempt)) {
              _tmpLastSyncAttempt = null;
            } else {
              _tmpLastSyncAttempt = _cursor.getLong(_cursorIndexOfLastSyncAttempt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ScannedCardEntity(_tmpId,_tmpOriginalText,_tmpFieldsJson,_tmpSyncStatus,_tmpSyncError,_tmpLastSyncAttempt,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getPendingCards(final int limit,
      final Continuation<? super List<ScannedCardEntity>> $completion) {
    final String _sql = "SELECT * FROM scanned_cards WHERE syncStatus = 'PENDING' LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ScannedCardEntity>>() {
      @Override
      @NonNull
      public List<ScannedCardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOriginalText = CursorUtil.getColumnIndexOrThrow(_cursor, "originalText");
          final int _cursorIndexOfFieldsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "fieldsJson");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfSyncError = CursorUtil.getColumnIndexOrThrow(_cursor, "syncError");
          final int _cursorIndexOfLastSyncAttempt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAttempt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ScannedCardEntity> _result = new ArrayList<ScannedCardEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScannedCardEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpOriginalText;
            _tmpOriginalText = _cursor.getString(_cursorIndexOfOriginalText);
            final String _tmpFieldsJson;
            _tmpFieldsJson = _cursor.getString(_cursorIndexOfFieldsJson);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __converters.stringToSyncStatus(_tmp);
            final String _tmpSyncError;
            if (_cursor.isNull(_cursorIndexOfSyncError)) {
              _tmpSyncError = null;
            } else {
              _tmpSyncError = _cursor.getString(_cursorIndexOfSyncError);
            }
            final Long _tmpLastSyncAttempt;
            if (_cursor.isNull(_cursorIndexOfLastSyncAttempt)) {
              _tmpLastSyncAttempt = null;
            } else {
              _tmpLastSyncAttempt = _cursor.getLong(_cursorIndexOfLastSyncAttempt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ScannedCardEntity(_tmpId,_tmpOriginalText,_tmpFieldsJson,_tmpSyncStatus,_tmpSyncError,_tmpLastSyncAttempt,_tmpCreatedAt,_tmpUpdatedAt);
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
