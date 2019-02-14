package com.zkkc.track.base;


public class BaseModel<T> {
//    /**
//     * 封装线程管理和订阅的过程
//     */
//    public void subscribe(Context context, final Observable observable, ObserverResponseListener<T> listener,
//                          ObservableTransformer<T, T> transformer, boolean isDialog, boolean cancelable) {
//        final Observer<T> observer = new ProObserver(context, listener, isDialog, cancelable);
//        observable.compose(transformer)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//    }
//
//    public void subscribe(Context context, final Observable observable, ObserverResponseListener<T> listener,
//                          ObservableTransformer<T, T> transformer) {
//        subscribe(context, observable, listener, transformer, true, true);
//    }
}
