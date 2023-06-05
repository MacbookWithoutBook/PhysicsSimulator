package com.bignerdranch.android.physicsSim.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bignerdranch.android.physicsSim.entities.UserAccount;

import java.util.List;

@Dao
public interface UserAccountDao {
	@Query("SELECT rowid, name, password FROM useraccount")
	LiveData<List<UserAccount>> getAllUserAccounts();

	@Query("SELECT rowid, name, password FROM useraccount WHERE name LIKE :name AND password LIKE :password LIMIT 1")
	LiveData<UserAccount> findByName(String name, String password);

	@Query("SELECT rowid, name, password FROM useraccount WHERE rowid = CAST(:id AS INTEGER)")
	LiveData<UserAccount> findById(int id);

	@Query("SELECT EXISTS(SELECT * FROM useraccount WHERE name= :userName)")
	boolean hasUserName(String userName);

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insert(UserAccount userAccount);

	@Update
	void update(UserAccount... userAccounts);

	@Delete
	void delete(UserAccount... userAccounts);

	@Query("Delete FROM useraccount")
	void deleteAllUsers();
}
