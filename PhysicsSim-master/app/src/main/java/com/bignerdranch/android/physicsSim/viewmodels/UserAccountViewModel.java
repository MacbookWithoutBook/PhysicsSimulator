package com.bignerdranch.android.physicsSim.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bignerdranch.android.physicsSim.AppRepository;
import com.bignerdranch.android.physicsSim.entities.UserAccount;

import java.util.List;
import java.util.Objects;

/**
 * ViewModel for the user account storage.
 */
public class UserAccountViewModel extends AndroidViewModel {

	private final AppRepository mRepository;
	private LiveData<List<UserAccount>> mAllUserAccounts;

	public UserAccountViewModel(@NonNull Application application) {
		super(application);
		mRepository = AppRepository.getInstance(application);
		mAllUserAccounts = mRepository.getAllUserAccounts();
	}

	public LiveData<UserAccount> getCurrentUser() {
		return mRepository.getCurrentUserAccount();
	}

	public void updateUser(UserAccount account) {
		mRepository.updateUserAccount(account);
	}

	public void deleteUser(UserAccount account) {
		mRepository.deleteUserAccount(account);
	}

	public LiveData<UserAccount> getUserAccount(UserAccount userAccount) {
		return mRepository.findUserAccountByName(userAccount);
	}

	public boolean hasUserName(String name) {
		return mRepository.hasUserName(name);
	}

	public void deleteAllUsers() {
		mRepository.deleteAllUsers();
	}

	public LiveData<List<UserAccount>> getAllUserAccounts() { return mRepository.getAllUserAccounts(); }

	public void insert(UserAccount userAccount) {
		mRepository.insertUserAccount(userAccount);
		mAllUserAccounts = mRepository.getAllUserAccounts();
	}

	public void setUid(int uid) {
		mRepository.setCurrentUid(uid);
	}

	public boolean containsUserAccount(UserAccount userAccount) {
		boolean accountInList = false;

		LiveData<UserAccount> userAccountLiveData = mRepository.findUserAccountByName(userAccount);
		UserAccount theUserAccount = userAccountLiveData.getValue();
		if (theUserAccount == null) {
			return false;
		} else if (Objects.requireNonNull(theUserAccount).getName().equals(userAccount.getName()) &&
				Objects.requireNonNull(theUserAccount).getPassword().equals(userAccount.getPassword())) {
			accountInList = true;
		}

		return accountInList;
	}
}

