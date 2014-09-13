package com.ninjamind.confman.conf;

/**
 * Each configuration object had to expose a checker to validate its params
 * @author Guillaume EHRET
 */
public interface ConfChecker<T> {
    T check();
}
