package benchmark;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
public class PlotGenerator {
    static class Row {
        String workload;
        String variant;
        String structure;
        int n;
        double time;
        long steps;
        long moves;
        long comparisons;
        Row(String workload, String variant, String structure,
            int n, double time, long steps, long moves, long comparisons) {
            this.workload = workload;
            this.variant = variant;
            this.structure = structure;
            this.n = n;
            this.time = time;
            this.steps = steps;
            this.moves = moves;
            this.comparisons = comparisons;
        }
    }
    public static void main(String[] args) throws Exception {
        List<Row> rows = readResults();
        File folder = new File("results/plots");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        createWorkloadCharts(
                rows,
                "W1",
                "-",
                "Random Access"
        );
        createWorkloadCharts(
                rows,
                "W2",
                "-",
                "Search"
        );
        createWorkloadCharts(
                rows,
                "W3",
                "head",
                "Insert & Remove - Head"
        );
        createWorkloadCharts(
                rows,
                "W3",
                "middle",
                "Insert & Remove - Middle"
        );
        createWorkloadCharts(
                rows,
                "W4",
                "-",
                "Priority Processing"
        );
        System.out.println();
        System.out.println("All plots created.");
        System.out.println("Open: results/plots/");
    }
    private static List<Row> readResults() throws Exception {
        List<Row> rows = new ArrayList<>();
        BufferedReader reader =
                new BufferedReader(
                        new FileReader("results/results.csv")
                );
        reader.readLine();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            Row row = new Row(
                    parts[0],
                    parts[1],
                    parts[2],
                    Integer.parseInt(parts[3]),
                    Double.parseDouble(parts[4]),
                    Long.parseLong(parts[5]),
                    Long.parseLong(parts[6]),
                    Long.parseLong(parts[7])
            );
            rows.add(row);
        }
        reader.close();
        return rows;
    }
    private static void createWorkloadCharts(
            List<Row> rows,
            String workload,
            String variant,
            String name
    ) throws Exception {
        createTimeChart(
                rows,
                workload,
                variant,
                name
        );
        createOverviewOperationsChart(
                rows,
                workload,
                variant,
                name
        );
        createSingleMetricChart(
                rows,
                workload,
                variant,
                name,
                "steps"
        );
        createSingleMetricChart(
                rows,
                workload,
                variant,
                name,
                "moves"
        );
        createSingleMetricChart(
                rows,
                workload,
                variant,
                name,
                "comparisons"
        );
        System.out.println(
                workload + " " + variant + " plots finished"
        );
    }
    private static XYChart createBaseChart(
            String title,
            String yAxis
    ) {
        XYChart chart =
                new XYChartBuilder()
                        .width(1100)
                        .height(700)
                        .title(title)
                        .xAxisTitle("Input size n")
                        .yAxisTitle(yAxis)
                        .theme(Styler.ChartTheme.XChart)
                        .build();
        chart.getStyler().setLegendPosition(
                Styler.LegendPosition.OutsideE
        );
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setXAxisTitleVisible(true);
        chart.getStyler().setYAxisTitleVisible(true);
        chart.getStyler().setPlotGridLinesVisible(true);
        chart.getStyler().setMarkerSize(8);
        return chart;
    }
    private static void createTimeChart(
            List<Row> rows,
            String workload,
            String variant,
            String name
    ) throws Exception {
        XYChart chart = createBaseChart(
                workload + " - " + name + " - Time vs Input Size",
                "Median time (ms)"
        );
        addTimeSeries(
                chart,
                rows,
                workload,
                variant,
                "DynamicArray"
        );
        addTimeSeries(
                chart,
                rows,
                workload,
                variant,
                "MyLinkedList"
        );
        addTimeSeries(
                chart,
                rows,
                workload,
                variant,
                "MinHeap"
        );
        save(
                chart,
                fileName(
                        workload,
                        variant,
                        "time"
                )
        );
    }
    private static void addTimeSeries(
            XYChart chart,
            List<Row> rows,
            String workload,
            String variant,
            String structure
    ) {
        List<Integer> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        for (Row row : rows) {
            if (row.workload.equals(workload)
                    && row.variant.equals(variant)
                    && row.structure.equals(structure)) {
                x.add(row.n);
                y.add(row.time);
            }
        }
        if (!x.isEmpty()) {
            chart.addSeries(
                    structure,
                    x,
                    y
            );
        }
    }
    private static void createOverviewOperationsChart(
            List<Row> rows,
            String workload,
            String variant,
            String name
    ) throws Exception {
        XYChart chart = createBaseChart(
                workload + " - " + name + " - Operation Counts",
                "Number of operations"
        );
        addAllMetrics(
                chart,
                rows,
                workload,
                variant,
                "DynamicArray"
        );
        addAllMetrics(
                chart,
                rows,
                workload,
                variant,
                "MyLinkedList"
        );
        addAllMetrics(
                chart,
                rows,
                workload,
                variant,
                "MinHeap"
        );
        save(
                chart,
                fileName(
                        workload,
                        variant,
                        "operations_overview"
                )
        );
    }
    private static void addAllMetrics(
            XYChart chart,
            List<Row> rows,
            String workload,
            String variant,
            String structure
    ) {
        List<Integer> x = new ArrayList<>();
        List<Long> steps = new ArrayList<>();
        List<Long> moves = new ArrayList<>();
        List<Long> comparisons = new ArrayList<>();
        for (Row row : rows) {
            if (row.workload.equals(workload)
                    && row.variant.equals(variant)
                    && row.structure.equals(structure)) {
                x.add(row.n);
                steps.add(row.steps);
                moves.add(row.moves);
                comparisons.add(row.comparisons);
            }
        }
        if (!x.isEmpty()) {
            chart.addSeries(
                    structure + " - steps",
                    x,
                    steps
            );
            chart.addSeries(
                    structure + " - moves",
                    x,
                    moves
            );
            chart.addSeries(
                    structure + " - comparisons",
                    x,
                    comparisons
            );
        }
    }
    private static void createSingleMetricChart(
            List<Row> rows,
            String workload,
            String variant,
            String name,
            String metric
    ) throws Exception {
        String metricTitle =
                Character.toUpperCase(metric.charAt(0))
                        + metric.substring(1);

        XYChart chart = createBaseChart(
                workload + " - " + name + " - "
                        + metricTitle + " vs Input Size",
                metricTitle
        );
        addMetricSeries(
                chart,
                rows,
                workload,
                variant,
                "DynamicArray",
                metric
        );
        addMetricSeries(
                chart,
                rows,
                workload,
                variant,
                "MyLinkedList",
                metric
        );
        addMetricSeries(
                chart,
                rows,
                workload,
                variant,
                "MinHeap",
                metric
        );
        save(
                chart,
                fileName(
                        workload,
                        variant,
                        metric
                )
        );
    }
    private static void addMetricSeries(
            XYChart chart,
            List<Row> rows,
            String workload,
            String variant,
            String structure,
            String metric
    ) {
        List<Integer> x = new ArrayList<>();
        List<Long> y = new ArrayList<>();
        for (Row row : rows) {
            if (row.workload.equals(workload)
                    && row.variant.equals(variant)
                    && row.structure.equals(structure)) {
                x.add(row.n);
                if (metric.equals("steps")) {
                    y.add(row.steps);
                }
                if (metric.equals("moves")) {
                    y.add(row.moves);
                }
                if (metric.equals("comparisons")) {
                    y.add(row.comparisons);
                }
            }
        }
        if (!x.isEmpty()) {
            chart.addSeries(
                    structure,
                    x,
                    y
            );
        }
    }
    private static String fileName(
            String workload,
            String variant,
            String type
    ) {
        String name = workload.toLowerCase();
        if (!variant.equals("-")) {
            name += "_" + variant;
        }
        return name + "_" + type;
    }
    private static void save(
            XYChart chart,
            String name
    ) throws Exception {
        BitmapEncoder.saveBitmap(
                chart,
                "results/plots/" + name,
                BitmapEncoder.BitmapFormat.PNG
        );
    }
}