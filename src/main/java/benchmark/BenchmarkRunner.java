package benchmark;
import structures.DynamicArray;
import structures.MyLinkedList;
import structures.MinHeap;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
public class BenchmarkRunner {
    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int RUNS = 5;
    static class Result {
        double time;
        long steps;
        long moves;
        long comparisons;
        Result(double time, long steps, long moves, long comparisons) {
            this.time = time;
            this.steps = steps;
            this.moves = moves;
            this.comparisons = comparisons;
        }
    }
    public static void main(String[] args) throws Exception {
        File folder = new File("results");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        PrintWriter writer = new PrintWriter("results/results.csv");
        writer.println(
                "workload,variant,structure,n,time_ms,steps,moves,comparisons"
        );
        for (int n : SIZES) {
            System.out.println("Running n = " + n);
            benchmarkW1Array(n, writer);
            benchmarkW1List(n, writer);
            benchmarkW2Array(n, writer);
            benchmarkW2List(n, writer);
            benchmarkW3Array(n, "head", writer);
            benchmarkW3List(n, "head", writer);
            benchmarkW3Array(n, "middle", writer);
            benchmarkW3List(n, "middle", writer);
            benchmarkW4Heap(n, writer);
        }
        writer.close();
        System.out.println("Finished.");
        System.out.println("File created: results/results.csv");
    }
    private static int[] createData(int n) {
        Random random = new Random(42);
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(1000000);
        }
        return data;
    }
    //W1
    private static Result runW1Array(int n) {
        int[] data = createData(n);
        DynamicArray array = new DynamicArray();
        for (int value : data) {
            array.add(value);
        }
        array.resetMetrics();
        Random random = new Random(42);
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            int index = random.nextInt(n);
            array.get(index);
        }
        long end = System.nanoTime();
        return new Result(
                (end - start) / 1_000_000.0,
                array.getSteps(),
                array.getMoves(),
                array.getComparisons()
        );
    }
    private static void benchmarkW1Array(int n, PrintWriter writer) {
        runW1Array(n);
        Result[] results = new Result[RUNS];
        for (int i = 0; i < RUNS; i++) {
            results[i] = runW1Array(n);
        }
        writeResult(
                writer,
                "W1",
                "-",
                "DynamicArray",
                n,
                results
        );
    }
    private static Result runW1List(int n) {
        int[] data = createData(n);
        MyLinkedList list = new MyLinkedList();
        for (int value : data) {
            list.add(value);
        }
        list.resetMetrics();
        Random random = new Random(42);
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            int index = random.nextInt(n);
            list.get(index);
        }
        long end = System.nanoTime();
        return new Result(
                (end - start) / 1_000_000.0,
                list.getSteps(),
                list.getMoves(),
                list.getComparisons()
        );
    }
    private static void benchmarkW1List(int n, PrintWriter writer) {
        runW1List(n);
        Result[] results = new Result[RUNS];
        for (int i = 0; i < RUNS; i++) {
            results[i] = runW1List(n);
        }
        writeResult(
                writer,
                "W1",
                "-",
                "MyLinkedList",
                n,
                results
        );
    }
    //W2

    private static Result runW2Array(int n) {
        int[] data = createData(n);
        DynamicArray array = new DynamicArray();
        for (int value : data) {
            array.add(value);
        }
        array.resetMetrics();
        long start = System.nanoTime();
        for (int i = 0; i < 500; i++) {
            array.contains(data[i % n]);
        }
        for (int i = 0; i < 500; i++) {
            array.contains(-1 - i);
        }
        long end = System.nanoTime();
        return new Result(
                (end - start) / 1_000_000.0,
                array.getSteps(),
                array.getMoves(),
                array.getComparisons()
        );
    }
    private static void benchmarkW2Array(int n, PrintWriter writer) {
        runW2Array(n);
        Result[] results = new Result[RUNS];
        for (int i = 0; i < RUNS; i++) {
            results[i] = runW2Array(n);
        }
        writeResult(
                writer,
                "W2",
                "-",
                "DynamicArray",
                n,
                results
        );
    }
    private static Result runW2List(int n) {
        int[] data = createData(n);
        MyLinkedList list = new MyLinkedList();
        for (int value : data) {
            list.add(value);
        }
        list.resetMetrics();
        long start = System.nanoTime();
        for (int i = 0; i < 500; i++) {
            list.contains(data[i % n]);
        }
        for (int i = 0; i < 500; i++) {
            list.contains(-1 - i);
        }
        long end = System.nanoTime();
        return new Result(
                (end - start) / 1_000_000.0,
                list.getSteps(),
                list.getMoves(),
                list.getComparisons()
        );
    }
    private static void benchmarkW2List(int n, PrintWriter writer) {
        runW2List(n);
        Result[] results = new Result[RUNS];
        for (int i = 0; i < RUNS; i++) {
            results[i] = runW2List(n);
        }
        writeResult(
                writer,
                "W2",
                "-",
                "MyLinkedList",
                n,
                results
        );
    }
    //W3
    private static Result runW3Array(int n, String variant) {
        int[] data = createData(n);
        DynamicArray array = new DynamicArray();
        for (int value : data) {
            array.add(value);
        }
        array.resetMetrics();
        long start = System.nanoTime();
        if (variant.equals("head")) {
            for (int i = 0; i < 1000; i++) {
                array.add(0, 1000000 + i);
            }
            for (int i = 0; i < 1000; i++) {
                array.remove(0);
            }
        } else {
            int index = n / 2;
            for (int i = 0; i < 1000; i++) {
                array.add(index, 1000000 + i);
            }
            for (int i = 0; i < 1000; i++) {
                array.remove(index);
            }
        }
        long end = System.nanoTime();
        return new Result(
                (end - start) / 1_000_000.0,
                array.getSteps(),
                array.getMoves(),
                array.getComparisons()
        );
    }
    private static void benchmarkW3Array(
            int n,
            String variant,
            PrintWriter writer
    ) {
        runW3Array(n, variant);
        Result[] results = new Result[RUNS];
        for (int i = 0; i < RUNS; i++) {
            results[i] = runW3Array(n, variant);
        }
        writeResult(
                writer,
                "W3",
                variant,
                "DynamicArray",
                n,
                results
        );
    }
    private static Result runW3List(int n, String variant) {
        int[] data = createData(n);
        MyLinkedList list = new MyLinkedList();
        for (int value : data) {
            list.add(value);
        }
        list.resetMetrics();
        long start = System.nanoTime();
        if (variant.equals("head")) {
            for (int i = 0; i < 1000; i++) {
                list.add(0, 1000000 + i);
            }
            for (int i = 0; i < 1000; i++) {
                list.remove(0);
            }
        } else {
            int index = n / 2;
            for (int i = 0; i < 1000; i++) {
                list.add(index, 1000000 + i);
            }
            for (int i = 0; i < 1000; i++) {
                list.remove(index);
            }
        }
        long end = System.nanoTime();
        return new Result(
                (end - start) / 1_000_000.0,
                list.getSteps(),
                list.getMoves(),
                list.getComparisons()
        );
    }
    private static void benchmarkW3List(
            int n,
            String variant,
            PrintWriter writer
    ) {
        runW3List(n, variant);
        Result[] results = new Result[RUNS];
        for (int i = 0; i < RUNS; i++) {
            results[i] = runW3List(n, variant);
        }
        writeResult(
                writer,
                "W3",
                variant,
                "MyLinkedList",
                n,
                results
        );
    }
    //W4
    private static Result runW4Heap(int n) {
        int[] data = createData(n);
        MinHeap heap = new MinHeap();
        long start = System.nanoTime();
        for (int value : data) {
            heap.insert(value);
        }
        int previous = Integer.MIN_VALUE;
        while (!heap.isEmpty()) {
            int current = heap.extractMin();
            if (current < previous) {
                throw new IllegalStateException(
                        "Heap output is not sorted"
                );
            }
            previous = current;
        }
        long end = System.nanoTime();
        return new Result(
                (end - start) / 1_000_000.0,
                heap.getSteps(),
                heap.getMoves(),
                heap.getComparisons()
        );
    }
    private static void benchmarkW4Heap(int n, PrintWriter writer) {
        runW4Heap(n);
        Result[] results = new Result[RUNS];
        for (int i = 0; i < RUNS; i++) {
            results[i] = runW4Heap(n);
        }
        writeResult(
                writer,
                "W4",
                "-",
                "MinHeap",
                n,
                results
        );
    }

    //RESULTS
    private static void writeResult(
            PrintWriter writer,
            String workload,
            String variant,
            String structure,
            int n,
            Result[] results
    ) {
        double[] times = new double[RUNS];
        for (int i = 0; i < RUNS; i++) {
            times[i] = results[i].time;
        }
        Arrays.sort(times);
        double median = times[RUNS / 2];
        Result last = results[RUNS - 1];
        writer.println(
                workload + "," +
                        variant + "," +
                        structure + "," +
                        n + "," +
                        median + "," +
                        last.steps + "," +
                        last.moves + "," +
                        last.comparisons
        );
        writer.flush();
        System.out.println(
                workload + " " +
                        variant + " " +
                        structure + " n=" +
                        n + " done"
        );
    }
}