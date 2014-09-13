package com.ninjamind.confman;

import com.ninjamind.confman.conf.ApplicationConfig;
import com.ninjamind.confman.conf.ServerConfig;

/**
 * An operation that Confman will be execute.
 * @author Guillaume EHRET
 */
public interface Operation<T> {

    /**
     * Execute an operation in Confman
     * @return
     */
    T execute();


}
