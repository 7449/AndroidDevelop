package com.wifi.example.control.socket.data;

import java.util.concurrent.LinkedBlockingQueue;


public class MsgQueueManager {

    LinkedBlockingQueue<MsgEntity> mQueueList;

    private MsgQueueManager() {
        mQueueList = new LinkedBlockingQueue<MsgEntity>();
    }

    public static MsgQueueManager getInstance() {
        return Holder.sINSTANCE;
    }

    public MsgEntity poll() {
        MsgEntity entity = null;
        if (mQueueList != null) {
            entity = mQueueList.poll();
        }
        return entity;
    }

    public void push(MsgEntity entity) {
        try {
            if (mQueueList != null) {
                mQueueList = new LinkedBlockingQueue<MsgEntity>();
            }
            mQueueList.put(entity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static final class Holder {
        public static final MsgQueueManager sINSTANCE = new MsgQueueManager();
    }

}
