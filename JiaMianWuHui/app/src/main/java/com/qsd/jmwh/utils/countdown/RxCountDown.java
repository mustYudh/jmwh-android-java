package com.qsd.jmwh.utils.countdown;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


public class RxCountDown {

    private Disposable mDisposable;

    private TimeListener timeListener;

    private int mInitialDelay = 0;

    private int mPeriod = 1;

    private boolean isStart = false;

    private TimeUnit mTimeUnit = TimeUnit.SECONDS;

    public interface TimeListener {

        void onStart();

        void onNext(Integer time);

        void onError(Throwable e);

        void onComplete();
    }

    private boolean stop;

    public Disposable getDisposable() {
        return mDisposable;
    }

    public RxCountDown setInitialDelay(int initialDelay) {
        mInitialDelay = initialDelay;
        return this;
    }

    public RxCountDown setPeriod(int period) {
        mPeriod = period;
        return this;
    }

    public RxCountDown setTimeUnit(TimeUnit timeUnit) {
        mTimeUnit = timeUnit;
        return this;
    }

    public RxCountDown setCountDownTimeListener(RxCountDownAdapter timeListener) {
        this.timeListener = timeListener;
        return this;
    }


    public void restart(int time,boolean restart) {
        isStart = !restart;
        start(time);
    }

    public void start(int time) {
        if (!isStart) {
            if (time < 0) {
                time = 0;
            }
            isStart = true;
            if (timeListener != null) {
                timeListener.onStart();
            }
            final int currentTime = time;
            Observable.interval(mInitialDelay, mPeriod, mTimeUnit)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<Long, Integer>() {
                        @Override
                        public Integer apply(Long aLong) throws Exception {
                            return currentTime - aLong.intValue();
                        }
                    })
                    .take(currentTime + 1)
                    .subscribe(new Observer<Integer>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable = d;
                        }

                        @Override
                        public void onNext(Integer value) {
                            if (timeListener != null) {
                                timeListener.onNext(value);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            stop = true;
                            if (timeListener != null) {
                                timeListener.onError(e);
                            }
                            isStart = false;
                        }

                        @Override
                        public void onComplete() {
                            stop = true;
                            if (timeListener != null) {
                                timeListener.onComplete();
                            }
                            isStart = false;
                        }
                    });
        }
    }

    public void stop() {
        if (!stop && timeListener != null) {
            timeListener = null;
        }
        if (getDisposable() != null && !getDisposable().isDisposed()) {
            getDisposable().dispose();
        }
        isStart = false;
    }
}
