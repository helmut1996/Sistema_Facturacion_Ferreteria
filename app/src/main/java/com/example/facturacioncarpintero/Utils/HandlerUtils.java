package com.example.facturacioncarpintero.Utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;

import java.lang.ref.SoftReference;


public class HandlerUtils {
    private static final long serialVersionUID = 0L;

    /**
     * 在使用handler的地方继承此接口，然后把实例化的引用给实例化的handler
     */
    public interface IHandlerIntent {
        void handlerIntent(Message message) throws RemoteException;
    }

    public static final class MyHandler extends Handler
    {
        private SoftReference<IHandlerIntent> owner;

        public MyHandler(IHandlerIntent t) {
            owner = new SoftReference<>(t);
        }

        public MyHandler(Looper looper, IHandlerIntent t) {
            super(looper);
            owner = new SoftReference<>(t);
        }

        @Override
        public void handleMessage(Message msg) {
            IHandlerIntent t = owner.get();
            if (null != t) {
                try {
                    t.handlerIntent(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
