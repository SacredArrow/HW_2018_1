/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;

import org.openjdk.jmh.annotations.*;
import org.src.Main;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;

@Fork(1)
@Warmup(iterations = 1, time = 5)
@Measurement(iterations = 4, time = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MyBenchmark {
    int sizeOfAString = 100000000;
    @Benchmark
    public void thread_1() {
        Main.setup(1, sizeOfAString);
        Main.main(new String[1]);

    }
    @Benchmark
    public void thread_2() {
        Main.setup(2, sizeOfAString);
        Main.main(new String[1]);

    }
    @Benchmark
    public void thread_4() {
        Main.setup(4, sizeOfAString);
        Main.main(new String[1]);

    }
    @Benchmark
    public void thread_8() {
        Main.setup(8, sizeOfAString);
        Main.main(new String[1]);

    }
    @Benchmark
    public void one_threaded() {
        Main.setup(1, sizeOfAString);
        int[] arr = Main.intArray;
        int sum=0;
        boolean flag = true;
        for (int i = 0;i < arr.length;i++) {
           sum+=arr[i];
           if (sum<0) {
               flag=false;
           }
        }
        if (sum != 0) {
            flag = false;
        }
    }

}
