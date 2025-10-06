package com.walhalla.luckypunch.db;

import com.walhalla.luckypunch.App;

import com.walhalla.boilerplate.domain.executor.Executor;
import com.walhalla.boilerplate.domain.executor.MainThread;
import com.walhalla.boilerplate.domain.interactors.base.AbstractInteractor;
import com.walhalla.ui.DLog;

import java.util.List;

import rubikstudio.library.model.LuckyItem;


public class DatabaseManager extends AbstractInteractor {

    private final Executor executorService;
    private final MainThread mainThread;
    //private static DatabaseManager INSTANCE = new DatabaseManager();
    //private final ExecutorService executorService;
    private final ProductDao proxyDao;

    public DatabaseManager(Executor threadExecutor, MainThread mainThread) {
        super(threadExecutor, mainThread);
        this.executorService = threadExecutor;
        this.mainThread = mainThread;
        //executorService = Executors.newFixedThreadPool(1);
        AppDatabase db = App.getInstance().getDatabase();
        proxyDao = db.proxyDao();

    }

//    public static DatabaseManager getInstance() {
//        return INSTANCE;
//    }

    public void loadNotesList0(DatabaseCallBack<List<LuckyItem>> callback) {
        executorService.submit(() -> {
            final List<LuckyItem> notesList = proxyDao.getAll();
            if (callback != null) {
                mainThread.post(() -> callback.returnData(notesList));
            }
        });
    }

    public void insertAsc(LuckyItem proxy, DatabaseCallBack<Long> callBack) {
        executorService.submit(new InsertTask(proxy, callBack));//execute
    }

    public void getByIdAsc(long id, DatabaseCallBack<LuckyItem> callBack) {
        executorService.submit(() -> {
            try {
                LuckyItem aa = getById(id);
                if (callBack != null) {
                    mainThread.post(() -> callBack.returnData(aa));
                }
            } catch (Exception e) {
                DLog.handleException(e);
            }
        });
    }

    public LuckyItem getById(long id) {
        return proxyDao.getById(id);
    }

    public void update(LuckyItem proxy, DatabaseCallBack<Long> callBack) {
        try{
            executorService.submit(() -> {
                try {
                    long aa = proxyDao.insert(proxy);
                    if (callBack != null) {
                        mainThread.post(() -> callBack.returnData(aa));
                    }
                }catch (Exception e){
                    DLog.handleException(e);
                }
            });
        }catch (Exception e){
            DLog.handleException(e);
        }
    }

    public void delete(final LuckyItem proxy0, DatabaseCallBack<Integer> callBack) {
        try {
            executorService.submit(() -> {
                try {
                    DLog.d("@@@--> " + proxy0);
                    proxyDao.delete(proxy0);
                    if (callBack != null) {
                        mainThread.post(() -> callBack.returnData(1));
                    }
                } catch (Exception e) {
                    DLog.handleException(e);
                }
            });
        } catch (Exception e) {
            DLog.handleException(e);
        }
    }

    public void getProxyCount(DatabaseCallBack<Integer> callBack) {
        executorService.execute(() -> {
            try {
                final int proxyCount = proxyDao.getLuckyItemCount();
                if (callBack != null) {
                    mainThread.post(() -> callBack.returnData(proxyCount));
                }
            } catch (Exception e) {
                DLog.handleException(e);
            }

        });
    }

    @Override
    public void run() {

    }






    public class InsertTask implements Runnable {

        private final LuckyItem item;
        private final DatabaseManager.DatabaseCallBack<Long> callback;

        InsertTask(LuckyItem proxy, DatabaseManager.DatabaseCallBack<Long> callBack) {
            this.item = proxy;
            this.callback = callBack;
        }

        @Override
        public void run() {
            final long id = proxyDao.insert(item);
            if (callback != null) {
                mainThread.post(() -> callback.returnData(id));
            }
        }
    }

    public interface DatabaseCallBack<T> {
        void returnData(T data);
    }
}
