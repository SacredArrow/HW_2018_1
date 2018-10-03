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
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.io.File;
import org.src.Filter;


public class MyBenchmark {
    @State(Scope.Thread)
    public static class SmallImage {
        public Filter filter;
        @Setup
        public void setup () {
            BufferedImage src = null;
            try {
                src = ImageIO.read(new File("/home/dmass/Projects/HW_2018_1/Blur_image/res/cat.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            filter = new Filter(src);
    }}
    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void small_horizontal_16_threads(SmallImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(16, org.src.Mode.HORIZONTAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void small_horizontal_8_threads(SmallImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(8, org.src.Mode.HORIZONTAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void small_horizontal_4_threads(SmallImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(4, org.src.Mode.HORIZONTAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void small_horizontal_2_threads(SmallImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(2, org.src.Mode.HORIZONTAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void small_horizontal_1_threads(SmallImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(1, org.src.Mode.HORIZONTAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void small_vertical_16_threads(SmallImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(16, org.src.Mode.VERTICAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void small_vertical_8_threads(SmallImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(8, org.src.Mode.VERTICAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void small_vertical_4_threads(SmallImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(4, org.src.Mode.VERTICAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void small_vertical_2_threads(SmallImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(2, org.src.Mode.VERTICAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void small_vertical_1_threads(SmallImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(1, org.src.Mode.VERTICAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @State(Scope.Thread)
    public static class BigImage {
        public Filter filter;
        @Setup
        public void setup () {
            BufferedImage src = null;
            try {
                src = ImageIO.read(new File("/home/dmass/Projects/HW_2018_1/Blur_image/res/wallpaper.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            filter = new Filter(src);
        }}
    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void big_horizontal_8_threads(BigImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(8, org.src.Mode.HORIZONTAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void big_horizontal_4_threads(BigImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(4, org.src.Mode.HORIZONTAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void big_horizontal_2_threads(BigImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(2, org.src.Mode.HORIZONTAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void big_horizontal_1_threads(BigImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(1, org.src.Mode.HORIZONTAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void big_vertical_16_threads(BigImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(16, org.src.Mode.VERTICAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void big_vertical_8_threads(BigImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(8, org.src.Mode.VERTICAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void big_vertical_4_threads(BigImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(4, org.src.Mode.VERTICAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void big_vertical_2_threads(BigImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(2, org.src.Mode.VERTICAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Benchmark @Fork(1) @Warmup(iterations = 1) @Measurement(iterations = 3) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.SECONDS)
    public void big_vertical_1_threads(BigImage state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        try {
            state.filter.process(1, org.src.Mode.VERTICAL_PROCESSING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
