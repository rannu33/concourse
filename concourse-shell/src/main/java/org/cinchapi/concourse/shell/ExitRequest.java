/*
 * Copyright (c) 2013-2015 Cinchapi, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cinchapi.concourse.shell;

/**
 * A throwable that is used to indicate that the evaluation of input merits a
 * graceful exit.
 * 
 * @author Jeff Nelson
 */
@SuppressWarnings("serial")
public class ExitRequest extends IrregularEvaluationResult {

    /**
     * Construct a new instance.
     */
    public ExitRequest() {
        super("");
    }

}