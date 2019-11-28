import edu.princeton.cs.algs4.StdRandom;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

public class Experiments {
    public static void experiment1() {
        int n = 5000;  // Number of items

        BST<Integer> bst = new BST<>();
        int[] items = StdRandom.permutation(100000, n);
        double[] numOfItems = new double[n];
        double[] averageDepths = new double[n];
        double[] optimalAverageDepths = new double[n];

        for (int i = 0; i < n; i++) {
            bst.add(items[i]);
            numOfItems[i] = i + 1;
            averageDepths[i] = bst.averageDepth();
            optimalAverageDepths[i] = ExperimentHelper.optimalAverageDepth(i + 1);
        }

        XYChart chart = new XYChartBuilder().width(800).height(600)
                .xAxisTitle("Number of Items").yAxisTitle("Average Depth").build();
        chart.addSeries("Random Average Depth", numOfItems, averageDepths);
        chart.addSeries("Optimal Average Depth", numOfItems, optimalAverageDepths);
        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        int m = 10000;  // Number of experiments
        int n = 5000;   // Number of items

        BST<Integer> bst = new BST<>();
        int[] items = StdRandom.permutation(100000, n);
        for (int item : items) {
            bst.add(item);
        }

        double[] numOfExperiments = new double[m + 1];
        double[] averageDepths = new double[m + 1];

        numOfExperiments[0] = 0;
        averageDepths[0] = bst.averageDepth();
        for (int i = 1; i <= m; i++) {
            ExperimentHelper.randomDeleteInsert(bst, false);
            numOfExperiments[i] = i;
            averageDepths[i] = bst.averageDepth();
        }

        XYChart chart = new XYChartBuilder().width(800).height(600)
                .xAxisTitle("Number of Experiments").yAxisTitle("Average Depth").build();
        chart.addSeries("Asymmetric Delete/Insert", numOfExperiments, averageDepths);
        new SwingWrapper(chart).displayChart();
    }

    public static void experiment3() {
        int m = 10000;  // Number of experiments
        int n = 5000;   // Number of items

        BST<Integer> bst = new BST<>();
        int[] items = StdRandom.permutation(100000, n);
        for (int item : items) {
            bst.add(item);
        }

        double[] numOfExperiments = new double[m + 1];
        double[] averageDepths = new double[m + 1];

        numOfExperiments[0] = 0;
        averageDepths[0] = bst.averageDepth();
        for (int i = 1; i <= m; i++) {
            ExperimentHelper.randomDeleteInsert(bst, true);
            numOfExperiments[i] = i;
            averageDepths[i] = bst.averageDepth();
        }

        XYChart chart = new XYChartBuilder().width(800).height(600)
                .xAxisTitle("Number of Experiments").yAxisTitle("Average Depth").build();
        chart.addSeries("Symmetric Delete/Insert", numOfExperiments, averageDepths);
        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) {
        experiment1();
        experiment2();
        experiment3();
    }
}
