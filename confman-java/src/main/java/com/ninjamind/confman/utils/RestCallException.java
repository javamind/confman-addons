/**
 * Copyright (C) 2014 all@dev-mind.fr
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package com.ninjamind.confman.utils;

/**
 * Exception thrown on problem with rest calls
 *
 * @author Guillaume EHRET
 */
public class RestCallException extends RuntimeException{
    public RestCallException() {
    }

    public RestCallException(String message) {
        super(message);
    }

    public RestCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestCallException(Throwable cause) {
        super(cause);
    }

}
