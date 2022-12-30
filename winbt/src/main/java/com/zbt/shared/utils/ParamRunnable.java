package com.zbt.shared.utils;

@FunctionalInterface
public interface ParamRunnable<T> {
    public abstract void run(T parameter);
}
