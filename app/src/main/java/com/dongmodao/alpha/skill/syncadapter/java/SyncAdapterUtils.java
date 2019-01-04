package com.dongmodao.alpha.skill.syncadapter.java;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

import static android.content.Context.ACCOUNT_SERVICE;

/**
 * author : tangqihao
 *
 * @time : 2019/1/3
 * @project : AlphaSkill
 */
public class SyncAdapterUtils {
    private static final String TAG = "SyncAdapterUtils---";

    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.dongmodao.alpha.skill.syncadapter.java.StubProvider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "alpha.skill.dongmodao.com";
    // The account name
    public static final String ACCOUNT = "AlphaSkillAccount";

    // Sync interval constants
    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 15L;
    public static final long SYNC_INTERVAL =
            SYNC_INTERVAL_IN_MINUTES *
                    SECONDS_PER_MINUTE;

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
            Log.e(TAG, "CreateSyncAccount: new account");
            return newAccount;
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
            Log.e(TAG, "CreateSyncAccount: old account");
            return newAccount;
        }
    }
}
