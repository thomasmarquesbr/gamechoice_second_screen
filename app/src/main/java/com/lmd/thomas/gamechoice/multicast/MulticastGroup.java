package com.lmd.thomas.gamechoice.multicast;

import android.content.Context;
import android.util.Log;

public class MulticastGroup  extends MulticastManager {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;

    public MulticastGroup(Context context, String tag, String multicastIp, int multicastPort) {
        super(context, tag, multicastIp, multicastPort);
        this.mContext = context;
    }

    @Override
    protected Runnable getIncomingMessageAnalyseRunnable() {
        Log.e(TAG, incomingMessage.getMessage());
        return null;
    }
}
